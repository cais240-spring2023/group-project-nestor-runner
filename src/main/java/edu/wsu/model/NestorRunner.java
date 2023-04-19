package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class NestorRunner {
    private static NestorRunner gameInstance;
    public GameState state;

    public static final int GROUND_Y = 400;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final double GRAVITY = 600; // m/s^2
    public static int entitySpacing;
    private Nestor nestor;
    private CannonBall cannonBall;
    private EntityFactory entityFactory;
    private int ticks;
    private Queue<Entity> entities;
    private int score;
    public int shieldTimer;
    public int cannonTimer;
    private boolean isJumping;
    private double jumpSpeed;
    private int entitySpeed;
    Difficulty difficulty;
    private double deltaTimeCounter;
    private double deltaTimeModifier;

    private NestorRunner() {
        state = GameState.MAIN_MENU;
        startNewGame();
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
    public int getCannonBallX() {
        if (cannonBall == null) return -1;
        return cannonBall.getX();
    }
    public int getCannonBallY() {
        if (cannonBall == null) return -1;
        return cannonBall.getY();
    }

    public void togglePause() {
        if (state == GameState.PAUSED) {
            state = GameState.PLAYING;
        } else {
            state = GameState.PAUSED;
        }
    }

    public void update(double deltaTime) {
        if (state == GameState.PAUSED) return;

        double deltaTimeModified = deltaTime * deltaTimeModifier;
        moveEntities(deltaTimeModified);

        // recoil from cannon
        if (nestor.getX() < 50) nestor.setX(nestor.getX() + 1);
        if (cannonTimer > 0) cannonTimer--;
        if (cannonBall != null) {
            moveCannonBall();
            if (cannonBallOffScreen()) cannonBall = null;
            if (cannonBallHasHit()) handleCannonBallCollision();
        }


        if (ticks % 10 == 0) {
            score++;
            if (deltaTimeModifier < 3.5){
                deltaTimeModifier += 0.01;
            }
            if (entitySpacing > 60){
                entitySpacing--;
            }
        }
        if (shieldTimer > 0) shieldTimer--;
        if (ticks % entitySpacing == 0) {
            entities.add(entityFactory.generate());
        }
        if (hasCollided()) handleCollision();
        if (entityPassedLeft()) entities.poll();
        if (isJumping()) jump(deltaTimeModified);
        ticks++;
    }

    public void startNewGame() {
        nestor = new Nestor();
        cannonBall = null;
        entityFactory = new EntityFactory();
        entities = new LinkedList<>();
        state = GameState.PLAYING;
        ticks = 0;
        score = 0;
        shieldTimer = 0;
        cannonTimer = 0;
        isJumping = false;
        jumpSpeed = 0;
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
            if (cannonTimer > 0) jumpSpeed = -BASE_JUMP_SPEED - 100;
            else jumpSpeed = -BASE_JUMP_SPEED;
            isJumping = true;
        }
    }

    private void jump(double deltaTime) {
        jumpSpeed += GRAVITY * deltaTime;
        nestor.setY((int)(nestor.getY() + (jumpSpeed * deltaTime)));

        if (nestor.getY() >= GROUND_Y - Nestor.HEIGHT) {
            nestor.setY(GROUND_Y - Nestor.HEIGHT);
            jumpSpeed = 0;
            isJumping = false;
        }
    }

    /**
     * This method checks for a valid collision between any
     * entity and Nestor. A collision is valid if Nestor is
     * not shielded.
     * @return true if Nestor overlaps with an entity,
     * false otherwise
     */
    public boolean hasCollided() {
        Entity entity = entities.peek();
        if (entity == null) return false;

        // checks for any overlap between Nestor and any entity
        return ((nestor.getX() + Nestor.WIDTH) > entity.getX())
                && (nestor.getX() < (entity.getX() + entity.getWidth()))
                && ((nestor.getY() + Nestor.HEIGHT) > entity.getY())
                && (nestor.getY() < (entity.getY() + entity.getHeight()));
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
                shieldTimer = 500;
                entities.poll();
                break;
            case Cannon:
                cannonTimer = 1_000;
                entities.poll();
                break;
            case Hole:
                // if nestor still on screen, keep falling
                if (nestor.getY() < 400) {
                    nestor.setY(nestor.getY() + 2);
                }
                state = GameState.OVER;
                break;
            default:
                if (shieldTimer > 0) {
                    shieldTimer = 0;
                    entities.poll();
                } else {
                    state = GameState.OVER;
                }
                break;
        }
    }

    private boolean entityPassedLeft() {
        Entity headEntity = entities.peek();
        if (headEntity == null) return false;

        return (headEntity.getX() + headEntity.getWidth() <= 0);
        //return (headEntity.getX() + headEntity.getWidth() <= 0);
    }

    private void moveEntities(double deltaTime) {
        for (Entity entity : entities) {
            entity.setX(entity.getX() - (int) (entitySpeed * deltaTime * 1.5));;
        }
    }

    public void fireCannon() {
        assert cannonTimer > 0;
        int recoil = 30;

        if (cannonBall != null) return;
        nestor.setX(nestor.getX() - recoil);
        cannonBall = new CannonBall(getNestorY());
    }

    private void moveCannonBall() {
        assert cannonBall != null;
        cannonBall.setX(cannonBall.getX() + CannonBall.SPEED);
    }

    private boolean cannonBallHasHit() {
        Entity headEntity = entities.peek();
        if (headEntity == null || cannonBall == null) return false;

        return (cannonBall.getX() + CannonBall.WIDTH > headEntity.getX() &&
                cannonBall.getY() + CannonBall.HEIGHT > headEntity.getY());
    }

    private void handleCannonBallCollision() {
        assert entities.peek() != null;
        Entity entity = entities.peek();
        Entity.Type entityType = entity.type();

        if (entityType == Entity.Type.LargeObstacle ||
                entityType == Entity.Type.SmallObstacle ||
                entityType == Entity.Type.Flyer) {
            entities.poll();
            cannonBall = null;
            score += 100;
        }
    }

    private boolean cannonBallOffScreen() {
        assert cannonBall != null;
        return cannonBall.getX() > Entity.START_X;
    }


    public int getCannonTimer() {
        return cannonTimer;
    }

    public int getShieldTimer() {
        return shieldTimer;
    }
    public boolean cannonBallIsActive() {
        return cannonBall != null;
    }

    public int getScore(){
        return score;
    }

    public Queue<Entity> getEntities() {
        return entities;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}

