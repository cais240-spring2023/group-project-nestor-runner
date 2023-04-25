package edu.wsu.model.Entities;

public interface Entity {

    // these need to mirror the Sprite file names.
    enum Type {
        Flyer, Hole, Coin, SmallObstacle,
        Shield, LargeObstacle, Cannon
    }

    /**
     * Constructs Entities if given an Entity.Type.
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

    void moveLeft(int amount);
    void moveRight(int amount);
    void moveUp(int amount);
    void moveDown(int amount);

    boolean hasPassedLeft();

    Type type();

}
