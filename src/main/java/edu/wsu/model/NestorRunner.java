package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import java.io.File;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class NestorRunner extends Observable {
    public static final int GROUND_Y = 400;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final double GRAVITY = 600; // m/s^2
    public static final int ENTITY_SPACING = 200;
    private final Nestor nestor;
    private final EntityFactory entityFactory;
    private final String[] ENTITY_TYPES = {"Coin", "Hole", "LargeObstacle", "Projectile", "Shield", "SmallObstacle"};
    private int ticks;
    private Queue<Entity> entities;
    private int score;
    private boolean isShielded;
    private boolean isDead;
    private boolean isPaused;
    private boolean isJumping;
    private double jumpSpeed;
    private int entitySpeed;
    int entityCountdown;
    String difficulty;

    // handle sounds in view or controller
    private final String deathSound = "src/main/resources/edu/wsu/sound/deathSound.wav";
    private final String jumpSound = "src/main/resources/edu/wsu/sound/jumpSound.mp3";
    private final Media deathMedia = new Media(new File(deathSound).toURI().toString());
    private final Media jumpMedia = new Media(new File(jumpSound).toURI().toString());
    private final MediaPlayer deathPlayer = new MediaPlayer(deathMedia);
    private final MediaPlayer jumpPlayer = new MediaPlayer(jumpMedia);

    public NestorRunner() {
        nestor = new Nestor();
        entityFactory = new EntityFactory();
        entities = new LinkedList<>();
    }

    public int getNestorX() {
        return nestor.getX();
    }

    public int getNestorY() {
        return nestor.getY();
    }

    public void startGame() {
        score = 0;
        ticks = 0;
        entityCountdown = 0;
        isShielded = false;
        isDead = false;
        isJumping = false;
        if (entities.size() > 0) {
            for (Entity entity : entities)
                entities.poll();
        }
        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void setDifficulty(String difficulty){
        switch (difficulty) {
            case "Easy":
                entitySpeed = 75;
                break;
            case "Normal":
                entitySpeed = 100;
                break;
            case "Hard":
                entitySpeed = 125;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty " + difficulty);
        }
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJump() {
        if (!isJumping()) {
            jumpSpeed = -BASE_JUMP_SPEED;
            isJumping = true;
        }
    }

    public void jump(double deltaTime) {
        jumpSpeed += GRAVITY * deltaTime;
        nestor.setY((int)(nestor.getY() + (jumpSpeed * deltaTime)));

        if (nestor.getY() >= GROUND_Y - nestor.getHeight()) {
            nestor.setY(GROUND_Y - nestor.getHeight());
            jumpSpeed = 0;
            isJumping = false;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    /**
     * This method checks for a valid collision between any
     * entity and Nestor. A collision is valid if Nestor is
     * not shielded.
     * @return true if Nestor overlaps with an entity,
     * false otherwise
     */
    public boolean hasCollided() {
        assert entities.peek() != null;
        Entity entity = entities.peek();
        // ignores collision with certain obstacles while shielded
        if (isShielded && (entity.type().equals("LargeObstacle")
                || entity.type().equals("Projectile")
                || entity.type().equals("SmallObstacle")))
            return false;
        // checks for any overlap between Nestor and any entity
        return ((nestor.getX() + nestor.getWidth()) > entity.getX())
                && (nestor.getX() < (entity.getX() + entity.getWidth()))
                && ((nestor.getY() + nestor.getHeight()) > entity.getY())
                && (nestor.getY() < (entity.getY() + entity.getHeight()));
    }

    public void handleCollision() {
        assert entities.peek() != null;
        Entity entity = entities.peek();
        String entityType = entity.type();

        switch (entityType) {
            case "Coin":
                // coin is consumed and score is increased
                score += 10;
                entities.poll();
                break;
            case "Shield":
                // shield is consumed and equipped
                isShielded = true;
                entities.poll();
                break;
            case "Hole":
                // if nestor still on screen, keep falling
                while (nestor.getY() < 400) {
                    nestor.setY(nestor.getY() + BASE_JUMP_SPEED);
                }
                isDead = true;
                break;
            default:
                isDead = true;
                break;
        }
    }

    public boolean entityOffScreen() {
        Entity headEntity = entities.peek();
        assert headEntity != null;

        return (headEntity.getX() + headEntity.getWidth() <= 0);
    }

    public void update(double deltaTime) {
        updateScore();
        moveEntities(deltaTime);
        entityCountdown -= entitySpeed * deltaTime;
        if (entityCountdown <= 0) {
            entityCountdown = ENTITY_SPACING;
            entities.add(randEntityGenerator());
        }
        if (hasCollided()) handleCollision();
        if (entityOffScreen()) entities.poll();
        if (isJumping()) jump(deltaTime);
        setChanged();
        notifyObservers(this);
        clearChanged();
    }

    public void moveEntities(double deltaTime) {
        for (Entity entity : entities) {
            entity.setX(entity.getX() - (int) (entitySpeed * deltaTime));;
        }
    }

    public Integer getScore(){
        return score;
    }

    public void updateScore() {
        if (++ticks % 10 == 0) score++;
    }

    public boolean isShielded() {
        return isShielded;
    }

    public Queue<Entity> getEntities() {
        return entities;
    }

    public String getDifficulty() {
        return difficulty;
    }

    /**
     * This method will randomly generate an entity
     * based on probability. Entities that help Nestor
     * are less likely to spawn than obstacle entities.
     * @return the randomly generated entity
     */
    private Entity randEntityGenerator() {
        double entitySelector = Math.random();
        String newEntityType = "";

        if (entitySelector <= 0.1) newEntityType = "Coin";                  // 10% chance
        else if (entitySelector <= 0.3) newEntityType = "Hole";             // 20% chance
        else if (entitySelector <= 0.5) newEntityType = "LargeObstacle";    // 20% chance
        else if (entitySelector <= 0.7) newEntityType = "Projectile";       // 20% chance
        else if (entitySelector <= 0.8) newEntityType = "Shield";           // 10% chance
        else newEntityType = "SmallObstacle";                               // 20% chance

        return entityFactory.createEntity(newEntityType);
    }
}
