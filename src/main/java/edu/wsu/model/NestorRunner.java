package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import java.util.LinkedList;
import java.util.Queue;

public class NestorRunner {
    private static NestorRunner gameInstance;
    public GameState state;

    public static final int FALL_SPEED = 10;

    public static final int GROUND_Y = 400;
    public static final double GRAVITY = 600; // m/s^2
    public final int bubbleRadius = 55;
    private static int entitySpacing;
    private Nestor nestor;
    private CannonBall cannonBall;
    private EntityFactory entityFactory;
    private int ticks;
    private Queue<Entity> entities;
    private int score;
    public int shieldTimer;
    public int cannonTimer;
    private int entitySpeed;
    Difficulty difficulty;
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

    public void pause() {
        if (state != GameState.PLAYING) return;
        state = GameState.PAUSED;
    }
    public void unPause() {
        if (state != GameState.PAUSED) return;
        state = GameState.PLAYING;
    }

    public void update(double deltaTime) {
        if (state == GameState.PAUSED) return;

        double deltaTimeModified = deltaTime * deltaTimeModifier;
        Entity headEntity = entities.peek();

        moveEntitiesLeft(deltaTimeModified);
        if (nestor.getX() < 50) nestor.moveRight(1);
        if (cannonTimer > 0) cannonTimer--;
        if (cannonBall != null) {
            cannonBall.moveLeft();
            if (cannonBall.hasPassedRight()) cannonBall = null;
            else if (cannonBall.hasCollided(headEntity)) {
                assert headEntity != null;
                handleCannonBallCollision(headEntity);
            }
        }
        if (ticks % 10 == 0) {
            score++;
            if (deltaTimeModifier < 3.5) deltaTimeModifier += 0.005;
            if (entitySpacing > 60) entitySpacing--;
        }
        if (shieldTimer > 0) shieldTimer--;
        if (ticks % entitySpacing == 0) entities.add(entityFactory.generate());
        if (nestor.hasCollided(headEntity, shieldTimer > 0, bubbleRadius)) {
            assert headEntity != null;
            handleCollision(headEntity);
        }
        if (nestor.getY() >= 480) {
            state = GameState.OVER;
            return;
        }
        if (headEntity != null && headEntity.hasPassedLeft()) entities.poll();
        if (nestor.isJumping()) nestor.jump(deltaTimeModified, GRAVITY);
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
        return nestor.isJumping();
    }

    public void setJump() {
        nestor.setJump(cannonTimer > 0);
    }

    private void handleCollision(Entity entity) {
        switch (entity.type()) {
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
                nestor.fall(FALL_SPEED);
                break;
            default:
                if (shieldTimer > 0) {
                    shieldTimer = 0;
                    entities.poll();
                } else {
                    state = GameState.OVER;
                    return;
                }
                break;
        }
    }

    private void moveEntitiesLeft(double deltaTime) {
        for (Entity entity : entities) {
            entity.setX(entity.getX() - (int) (entitySpeed * deltaTime * 1.5));;
        }
    }

    public void fireCannon() {
        if (cannonBall != null) return;

        assert cannonTimer > 0;
        int recoil = 30;

        nestor.setX(nestor.getX() - recoil);
        cannonBall = new CannonBall(getNestorY());
    }

    private void handleCannonBallCollision(Entity entity) {
        Entity.Type entityType = entity.type();

        if (entityType == Entity.Type.LargeObstacle ||
                entityType == Entity.Type.SmallObstacle ||
                entityType == Entity.Type.Flyer) {
            entities.poll();
            cannonBall = null;
            score += 100;
        }
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

