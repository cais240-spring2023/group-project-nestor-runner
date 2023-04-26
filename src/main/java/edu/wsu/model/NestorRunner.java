package edu.wsu.model;

import edu.wsu.model.Entities.CannonBall;
import edu.wsu.model.Entities.Entity;
import edu.wsu.model.Entities.Nestor;

import java.util.*;

/**
 * Singleton for managing model interactions + other metadata.
 */
public class NestorRunner {
    private static NestorRunner gameInstance;
    public GameState state;

    public static final int FALL_SPEED = 10;

    public static final int GROUND_Y = 400;
    public static final int CANNON_RECOIL = 30;
    public static final double GRAVITY = 600; // m/s^2
    public final int bubbleRadius = 55;
    private static int entitySpacing;
    private Nestor nestor;
    private CannonBall cannonBall;
    private EntitySpawner entitySpawner;
    private int ticks;
    private ArrayList<Entity> scrollingEntities;
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

        // collision detection and response
        handleCollisions(getCollisions());
        moveScrollingEntitiesLeft(deltaTimeModified);
        if (nestor.getX() < 50) nestor.moveRight(1);
        if (cannonTimer > 0) cannonTimer--;
        if (cannonBall != null) {
            cannonBall.moveLeft(CannonBall.SPEED);
            if (cannonBall.hasPassedRight()) cannonBall = null;
        }
        if (ticks % 10 == 0) {
            score++;
            if (deltaTimeModifier < 3.5) deltaTimeModifier += 0.005;
            if (entitySpacing > 60) entitySpacing--;
        }
        if (shieldTimer > 0) shieldTimer--;
        if (ticks % entitySpacing == 0) scrollingEntities.add(entitySpawner.spawnEntity());

        if (nestor.getY() >= 480) {
            state = GameState.OVER;
            return;
        }
        Entity headEntity;
        try {
            headEntity = scrollingEntities.get(0);
        } catch (IndexOutOfBoundsException e) {
            headEntity = null;
        }
        if (headEntity != null && headEntity.hasPassedLeft()) scrollingEntities.remove(0);
        if (nestor.isJumping()) nestor.jump(deltaTimeModified, GRAVITY);
        ticks++;
    }

    /**
     * @return a stack of CollisionEvents that happened during the tick the method was called in.
     * Three-way Collisions are a thing, in which a single entity can be colliding with multiple scrollingEntities at once.
     * However, as it is now, if cannonBall and Nestor collide with an entity at the same time, the CannonBall's collision
     * is prioritized.
     *
     * (Where the game is right now, this system is overkill. However, it's very robust and will serve us well
     * if things get more complicated.)
     */
    private Stack<CollisionEvent> getCollisions() {
        Stack<CollisionEvent> collisionEvents = new Stack<>();
        for (Entity entity : scrollingEntities) {
            if (cannonBall != null && cannonBall.hasCollided(entity)) {
                collisionEvents.push(new CollisionEvent(cannonBall, entity));
            }
            if (nestor.hasCollided(entity, shieldTimer > 0, bubbleRadius)) {
                collisionEvents.push(new CollisionEvent(nestor, entity));
            }
        }
        return collisionEvents;
    }

    /**
     * Goes through each collision in the current tick and handles it.
     * @param collisionEventStack stack of collisionEvents that happened in the current tick.
     */
    private void handleCollisions(Stack<CollisionEvent> collisionEventStack) {
        while (!collisionEventStack.empty()) {
            CollisionEvent collisionEvent = collisionEventStack.pop();

            Entity collided = collisionEvent.getCollided();

            // if a collision has already been handled involving collided
            // that resulted in collided being removed, then skip the processing.
            if (!scrollingEntities.contains(collided)) continue;

            // cannonBall collisions first
            if (collisionEvent.getCollider() instanceof CannonBall) {

                if (collided.type() == Entity.Type.LargeObstacle ||
                        collided.type() == Entity.Type.SmallObstacle ||
                        collided.type() == Entity.Type.Flyer) {
                    if (scrollingEntities.remove(collided)) {
                        cannonBall = null;
                        score += 100;
                    }
                }
            } else {
                switch (collided.type()) {
                    case Coin:
                        // coin is consumed and score is increased
                        score += 10;
                        scrollingEntities.remove(collided);
                        break;
                    case Shield:
                        // shield is consumed and equipped
                        shieldTimer = 500;
                        scrollingEntities.remove(collided);
                        break;
                    case Cannon:
                        cannonTimer = 1_000;
                        scrollingEntities.remove(collided);
                        break;
                    case Hole:
                        nestor.moveDown(FALL_SPEED);
                        break;
                    default:
                        if (shieldTimer > 0) {
                            shieldTimer = 0;
                            scrollingEntities.remove(collided);
                        }
                        else {
                            state = GameState.OVER;
                            return;
                        }
                        break;
                }
            }
        }
    }

    /**
     * Move each entity in scrollingEntities to the left.
     */
    private void moveScrollingEntitiesLeft(double deltaTimeModified) {
        for (Entity entity : scrollingEntities) entity.moveLeft((int) (entitySpeed * deltaTimeModified * 1.5));
    }

    public void startNewGame() {
        entitySpawner = new EntitySpawner();
        nestor = new Nestor();
        cannonBall = null;
        scrollingEntities = new ArrayList<>();
        ticks = 0;
        score = 0;
        shieldTimer = 0;
        cannonTimer = 0;
        deltaTimeModifier = 1;
        entitySpacing = 200;

        state = GameState.PLAYING;
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

    public void fireCannon() {
        if (cannonBall != null) return;

        assert cannonTimer > 0;

        nestor.moveLeft(CANNON_RECOIL);
        cannonBall = new CannonBall(getNestorY());
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

    public ArrayList<Entity> getScrollingEntities() {
        return new ArrayList<>(scrollingEntities);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}

