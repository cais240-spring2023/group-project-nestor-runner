package edu.wsu.model.entities;

public interface Entity {

    // these need to mirror the Sprite file names.
    enum Type {
        Flyer, Hole, Coin, SmallObstacle,
        Shield, LargeObstacle, Cannon,
        Nestor, CannonBall
    }

    /**
     * Constructs Entities from an Entity.Type.
     * @param entityType type of entity you want to construct.
     * @return a new instance of entityType, null if entityType is for some reason not in Entity.Type.
     */
    static Entity init(Entity.Type entityType)  {
        switch (entityType) {
            case Flyer: return new Flyer();
            case Hole: return new Hole();
            case Coin: return new Coin();
            case SmallObstacle: return new SmallObstacle();
            case Shield: return new Shield();
            case LargeObstacle: return new LargeObstacle();
            case Cannon: return new Cannon();
            case Nestor: return new Nestor();
            case CannonBall: return new CannonBall();
        }
        return null;
    }

    int START_X = 640;

    int getX();
    int getY();

    void setX(int x);
    void setY(int y);

    int getWidth();
    int getHeight();

    void moveLeft(int amountPixels);
    void moveRight(int amountPixels);
    void moveUp(int amountPixels);
    void moveDown(int amountPixels);

    /*
    todo: Checks like these require data from view, which is just asking for coupling.
            The responsibility for these checks should somehow be moved.
     */
    boolean hasPassedLeft();

    Type type();

}
