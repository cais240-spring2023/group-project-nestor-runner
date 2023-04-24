package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import java.util.*;

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
    private EntityFactory entityFactory;
    private int ticks;
    private ArrayList<Entity> entities;
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
        moveEntitiesLeft(deltaTimeModified);

        if (nestor.getX() < 50) nestor.moveRight(1);
        if (cannonTimer > 0) cannonTimer--;
        if (cannonBall != null) {
            cannonBall.moveLeft();
            if (cannonBall.hasPassedRight()) cannonBall = null;
        }
        if (ticks % 10 == 0) {
            score++;
            if (deltaTimeModifier < 3.5) deltaTimeModifier += 0.005;
            if (entitySpacing > 60) entitySpacing--;
        }
        if (shieldTimer > 0) shieldTimer--;
        if (ticks % entitySpacing == 0) entities.add(entityFactory.generate());

        if (nestor.getY() >= 480) {
            state = GameState.OVER;
            return;
        }

        Entity headEntity;
        try {
            headEntity = entities.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            headEntity = null;
        }
        if (headEntity != null && headEntity.hasPassedLeft()) entities.remove(0);
        if (nestor.isJumping()) nestor.jump(deltaTimeModified, GRAVITY);
        ticks++;
    }

    /**
     * Returns a stack of CollisionEvents that happened during the tick the method was called in.
     * CannonBall collisions take precedence, and three-way collisions aren't a thing.
     * That means if both nestor and a cannonBall collide with the same entity, nestor's collisionEvent is ignored.
     * (Where the game is right now, this system is slightly overkill. However, it's very robust and will serve us well
     * if things get more complicated.)
     */
    private Stack<CollisionEvent<?>> getCollisions() {
        Stack<CollisionEvent<?>> collisionEvents = new Stack<>();
        for (Entity entity : entities) {
            if (cannonBall != null && cannonBall.hasCollided(entity)) {
                collisionEvents.push(new CollisionEvent<>(cannonBall, entity));
            }
            else if (nestor.hasCollided(entity, shieldTimer > 0, bubbleRadius)) {
                collisionEvents.push(new CollisionEvent<>(nestor, entity));
            }
        }
        return collisionEvents;
    }

    /**
     * goes through each collision in the current tick and handles it.
     * @param collisionEventStack stack of collisionEvents that happened in the current tick.
     */
    private void handleCollisions(Stack<CollisionEvent<?>> collisionEventStack) {
        while (!collisionEventStack.empty()) {
            CollisionEvent<?> collisionEvent = collisionEventStack.pop();

            Entity collided = collisionEvent.getCollided();

            // cannonBall collisions first
            if (collisionEvent.getCollider() instanceof CannonBall) {

                if (collided.type() == Entity.Type.LargeObstacle ||
                        collided.type() == Entity.Type.SmallObstacle ||
                        collided.type() == Entity.Type.Flyer) {
                    entities.remove(collided);
                    cannonBall = null;
                    score += 100;
                }
            }
            else {
                switch (collided.type()) {
                    case Coin:
                        // coin is consumed and score is increased
                        score += 10;
                        entities.remove(collided);
                        break;
                    case Shield:
                        // shield is consumed and equipped
                        shieldTimer = 500;
                        entities.remove(collided);
                        break;
                    case Cannon:
                        cannonTimer = 1_000;
                        entities.remove(collided);
                        break;
                    case Hole:
                        // if nestor still on screen, keep falling
                        nestor.fall(FALL_SPEED);
                        break;
                    default:
                        if (shieldTimer > 0) {
                            shieldTimer = 0;
                            entities.remove(collided);
                        } else {
                            state = GameState.OVER;
                            return;
                        }
                        break;
                }
            }
        }
    }

    /**
     * Move each entity in entities to the left.
     */
    private void moveEntitiesLeft(double deltaTimeModified) {
        for (Entity entity : entities) entity.moveLeft(entitySpeed, deltaTimeModified);
    }

    public void startNewGame() {
        nestor = new Nestor();
        cannonBall = null;
        entityFactory = new EntityFactory();
        entities = new ArrayList<>();
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

    public ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}

