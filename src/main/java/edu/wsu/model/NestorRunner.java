package edu.wsu.model;

import edu.wsu.model.entities.*;

import java.util.*;

/**
 * Singleton for managing model interactions + other metadata.
 */
public class NestorRunner {
    private static NestorRunner gameInstance;

    public static final int GROUND_Y = 400;
    public static final int KILL_BOX = 480;
    public static final int CANNON_RECOIL = 30;
    public static final double GRAVITY = 600; // m/s^2
    public final int bubbleRadius = 55;
    private final WeightedRandomChooser<Entity.Type> entityFactory;
    public GameState state;

    private static int entitySpacing;
    private Nestor nestor;
    private List<CannonBall> cannonBalls;
    private List<Entity> scrollingEntities;
    private List<FloorPiece> floorPieces;
    private int ticks;
    private int score;
    private int shieldTimer;
    private int cannonTimer;
    private int entitySpeed;
    Difficulty difficulty;
    private double deltaTimeModifier;

    private NestorRunner() {
        state = GameState.MAIN_MENU;
        entityFactory = new WeightedRandomChooser<>();

        entityFactory.addChoice(Entity.Type.Coin, WeightedRandomChooser.UNCOMMON);
        entityFactory.addChoice(Entity.Type.Flyer, WeightedRandomChooser.COMMON);
        entityFactory.addChoice(Entity.Type.SmallObstacle, WeightedRandomChooser.COMMON);
        entityFactory.addChoice(Entity.Type.Shield, WeightedRandomChooser.UNCOMMON);
        entityFactory.addChoice(Entity.Type.Cannon, WeightedRandomChooser.RARE);
        entityFactory.addChoice(Entity.Type.LargeObstacle, WeightedRandomChooser.COMMON);

        startNewGame();
    }

    public static NestorRunner getInstance() {
        if (gameInstance == null) {
            gameInstance = new NestorRunner();
        }
        return gameInstance;
    }

    public void startNewGame() {
        nestor = new Nestor();
        cannonBalls = new ArrayList<>();
        scrollingEntities = new ArrayList<>();
        floorPieces = new ArrayList<>();
        floorPieces.add(new FloorPiece(640));
        ticks = 0;
        score = 0;
        shieldTimer = 0;
        cannonTimer = 0;
        deltaTimeModifier = 1;
        entitySpacing = 200;

        state = GameState.PLAYING;
    }

    public int getNestorX() {
        return nestor.getX();
    }

    public int getNestorY() {
        return nestor.getY();
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
        double deltaTimeModified = deltaTime * deltaTimeModifier;

        // collision detection and response
        handleCollisions(getCollisions());

        moveScrollingEntitiesLeft(deltaTimeModified);
        moveCannonBalls();

        if (nestor.getX() < Nestor.BASE_X_POS) nestor.moveRight(1);
        if (cannonTimer > 0) cannonTimer--;
        if (ticks % 10 == 0) {
            score++;
            if (deltaTimeModifier < 3.5) deltaTimeModifier += 0.005;
            if (entitySpacing > 60) entitySpacing--;
        }
        if (shieldTimer > 0) shieldTimer--;
        if (ticks % entitySpacing == 0) {
            scrollingEntities.add(Entity.init(entityFactory.choose()));
        }

        if (nestor.getY() >= KILL_BOX) {
            state = GameState.OVER;
            return;
        }
        Entity headEntity;
        try {
            headEntity = scrollingEntities.get(0);
        } catch (IndexOutOfBoundsException e) {
            headEntity = null;
        }
        if (headEntity != null && headEntity.isLeftOf(0)) scrollingEntities.remove(0);
        if (cannonBalls.size() > 0 && cannonBalls.get(0).isRightOf(640)) cannonBalls.remove(0);
        if (nestor.isJumping()) nestor.jump(deltaTimeModified, GRAVITY);
        ticks++;
    }

    /**
     * @return a stack of CollisionEvents that happened during the tick the method was called in.
     * Three-way Collisions are a thing, in which a single entity can be colliding with multiple scrollingEntities at once.
     * However, as it is now, if cannonBall and Nestor collide with an entity at the same time, the CannonBall's collision
     * is prioritized.
     */
    private Stack<CollisionEvent> getCollisions() {
        /*
        todo: should instead be some "onCollide" thing. This won't do.
         */
        Stack<CollisionEvent> collisionEvents = new Stack<>();
        for (Entity entity : scrollingEntities) {
            for (CannonBall cannonBall : cannonBalls) {
                if (cannonBall.isCollidingWith(entity)) {
                    collisionEvents.push(new CollisionEvent(cannonBall, entity));
                }
            }
            if (nestor.isCollidingWith(entity, shieldTimer > 0, bubbleRadius)) {
                collisionEvents.push(new CollisionEvent(nestor, entity));
            }
        }
        for (FloorPiece floorPiece : floorPieces) {
            if (nestor.isCollidingWith(floorPiece, shieldTimer > 0, bubbleRadius)) {
                collisionEvents.push(new CollisionEvent(nestor, floorPiece));
            }
        }
        return collisionEvents;
    }

    /**
     * Handles/processes a stack of CollisionEvents.
     * @param collisionEventStack stack of collisionEvents.
     */
    private void handleCollisions(Stack<CollisionEvent> collisionEventStack) {
        while (!collisionEventStack.empty()) {
            CollisionEvent collisionEvent = collisionEventStack.pop();

            Entity collided = collisionEvent.getCollided();
            if (!scrollingEntities.contains(collided)) continue;

            if (collisionEvent.getCollider() instanceof CannonBall) {
                if (collided.type() == Entity.Type.LargeObstacle ||
                        collided.type() == Entity.Type.SmallObstacle ||
                        collided.type() == Entity.Type.Flyer) {
                    if (scrollingEntities.remove(collided)) {
                        cannonBalls.remove((CannonBall) collisionEvent.getCollider());
                        score += 100;
                    }
                }
            }
            else {
                switch (collided.type()) {
                    case Coin:
                        score += 10;
                        break;
                    case Shield:
                        shieldTimer = 500;
                        break;
                    case Cannon:
                        cannonTimer = 1_000;
                        break;
                    case FloorPiece:
                        System.out.println("On Floor");
                        break;
                    default:
                        if (shieldTimer > 0) {
                            shieldTimer = 0;
                            break;
                        }
                        else {
                            state = GameState.OVER;
                            return;
                        }
                }
                scrollingEntities.remove(collided);
            }
        }
    }

    /**
     * Move each entity in scrollingEntities to the left.
     */
    private void moveScrollingEntitiesLeft(double deltaTimeModified) {
        for (Entity entity : scrollingEntities) {
            entity.moveLeft((int) (entitySpeed * deltaTimeModified * 1.5));
        }
    }

    private void moveCannonBalls() {
        for (CannonBall cannonBall : cannonBalls) {
            cannonBall.moveRight(CannonBall.SPEED);
        }
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
        assert cannonTimer > 0;
        if (nestor.getX() < Nestor.BASE_X_POS) return;

        nestor.moveLeft(CANNON_RECOIL);
        cannonBalls.add(new CannonBall(getNestorY()));
    }

    public int getCannonTimer() {
        return cannonTimer;
    }

    public int getShieldTimer() {
        return shieldTimer;
    }

    public int getScore(){
        return score;
    }

    /**
     * @return a new array of all scrolling entities at the time of calling.
     */
    public Entity[] getScrollingEntities() {
        return scrollingEntities.toArray(new Entity[scrollingEntities.size()]);
    }

    /**
     * @return a new array of all cannonballs at the time of calling.
     */
    public CannonBall[] getCannonBalls() {
        return cannonBalls.toArray(new CannonBall[cannonBalls.size()]);
    }
    public FloorPiece[] getFloorPieces() {
        return floorPieces.toArray(new FloorPiece[floorPieces.size()]);
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }
}
