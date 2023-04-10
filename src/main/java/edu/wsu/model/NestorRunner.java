package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class NestorRunner {
    private static NestorRunner gameInstance;
    public GameState state;

    public static final int GROUND_Y = 400;
    public static final int GROUND_HEIGHT = 50;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final double GRAVITY = 600; // m/s^2
    public static int entitySpacing;
    private Nestor nestor;
    private EntityFactory entityFactory;
    private int ticks;
    private Queue<Entity> entities;
    private int score;
    private boolean isShielded;
    private boolean isDead;
    private boolean isJumping;
    private double jumpSpeed;
    private int entitySpeed;
    Difficulty difficulty;
    private double deltaTimeCounter;
    private double deltaTimeModifier;

    private boolean isPaused;

    private NestorRunner() {
        state = GameState.MAIN_MENU;
        start();
    }
    public static NestorRunner getInstance() {
        if (gameInstance == null) {
            gameInstance = new NestorRunner();
        }
        return gameInstance;
    }

    public int getNestorX() {
        return nestor.getX();
    }

    public int getNestorY() {
        return nestor.getY();
    }

    public void togglePause(){
        if (isPaused){
            isPaused = false;
        } else {
            isPaused = true;
        }
    }

    public void update(double deltaTime) {
        double deltaTimeModified = deltaTime * deltaTimeModifier;
        if (!isPaused){
            if (state == GameState.PLAYING) {
                if (ticks % 10 == 0){
                    score++;
                    if (deltaTimeModifier < 3.5){
                        deltaTimeModifier += 0.01;
                    }
                    if (entitySpacing > 60){
                        entitySpacing--;
                    }
                }
                if ((ticks % (10 * entitySpacing) == 0) && isShielded) isShielded = false;
                moveEntities(deltaTimeModified);
                if (ticks % entitySpacing == 0) {
                    entities.add(entityFactory.generate());
                }
                if (hasCollided()) handleCollision();
                if (entityOffScreen()) entities.poll();
                if (isJumping()) jump(deltaTimeModified);
                ticks++;
            }
        }
    }

    public void start() {
        nestor = new Nestor();
        entityFactory = new EntityFactory();
        entities = new LinkedList<>();
        state = GameState.PLAYING;
        ticks = 0;
        score = 0;
        isShielded = false;
        isDead = false;
        isJumping = false;
        jumpSpeed = 0;
        isPaused = false;
        deltaTimeModifier = 1;
        entitySpacing = 200;
    }

    public void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                this.difficulty = Difficulty.EASY;
                entitySpeed = 75;
                break;
            case MEDIUM:
                this.difficulty = Difficulty.MEDIUM;
                entitySpeed = 100;
                break;
            case HARD:
                this.difficulty = Difficulty.HARD;
                entitySpeed = 125;
                break;
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

    private void jump(double deltaTime) {
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
        if (entity != null){
            /*
            if (isShielded && (entity.type() == Entity.Type.LargeObstacle
                    || entity.type() == Entity.Type.Projectile
                    || entity.type() == Entity.Type.SmallObstacle))
                return false;

             */
            // checks for any overlap between Nestor and any entity
            return ((nestor.getX() + nestor.getWidth()) > entity.getX())
                    && (nestor.getX() < (entity.getX() + entity.getWidth()))
                    && ((nestor.getY() + nestor.getHeight()) > entity.getY())
                    && (nestor.getY() < (entity.getY() + entity.getHeight()));
        }
        // ignores collision with certain obstacles while shielded
        return false;
    }

    private void handleCollision() {
        assert entities.peek() != null;
        Entity entity = entities.peek();
        Entity.Type entityType = entity.type();

        switch (entityType) {
            case Coin:
                // coin is consumed and score is increased
                score += 10;
                entities.poll();
                break;
            case Shield:
                // shield is consumed and equipped
                isShielded = true;
                entities.poll();
                break;
            case Hole:
                // if nestor still on screen, keep falling
                while (nestor.getY() < 400) {
                    nestor.setY(nestor.getY() + BASE_JUMP_SPEED);
                }
                state = GameState.OVER;
                break;
            default:
                if (isShielded){
                    isShielded = false;
                    entities.poll();
                } else {
                    state = GameState.OVER;
                }
                break;
        }
    }

    private boolean entityOffScreen() {
        Entity headEntity = entities.peek();
        assert headEntity != null;
        if (headEntity != null){
            return (headEntity.getX() + headEntity.getWidth() <= 0);
        }
        return false;
        //return (headEntity.getX() + headEntity.getWidth() <= 0);
    }

    private void moveEntities(double deltaTime) {
        for (Entity entity : entities) {
            entity.setX(entity.getX() - (int) (entitySpeed * deltaTime * 1.5));;
        }
    }

    public Integer getScore(){
        return score;
    }

    public boolean isShielded() {
        return isShielded;
    }

    public Queue<Entity> getEntities() {
        return entities;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}

