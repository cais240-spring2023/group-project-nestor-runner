package edu.wsu.model.entities;

public abstract class Entity {

    // these need to mirror the Sprite file names.
    public enum Type {
        Flyer, Coin, SmallObstacle,
        Shield, LargeObstacle, Cannon,
        Nestor, CannonBall, FloorPiece
    }

    private int x;
    private int y;

    /**
     * Constructs Entities from an Entity.Type.
     * @param entityType type of entity you want to construct.
     * @return a new instance of entityType, null if entityType is for some reason not in Entity.Type.
     */
    public static Entity init(Entity.Type entityType)  {
        switch (entityType) {
            case Flyer: return new Flyer();
            case Coin: return new Coin();
            case SmallObstacle: return new SmallObstacle();
            case Shield: return new Shield();
            case LargeObstacle: return new LargeObstacle();
            case Cannon: return new Cannon();
            case Nestor: return new Nestor();
            case CannonBall: return new CannonBall();
            case FloorPiece: return new FloorPiece();
        }
        return null;
    }

    public static int START_X = 640;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public abstract int getWidth();
    public abstract int getHeight();

    public void moveLeft(int amountPixels) {
        x -= amountPixels;
    }

    public void moveRight(int amountPixels) {
        x += amountPixels;
    }

    public void moveUp(int amountPixels) {
        y -= amountPixels;
    }

    public void moveDown(int amountPixels) {
        y += amountPixels;
    }

    public boolean isLeftOf(int xPosPixels) {
        return x + getWidth() < xPosPixels;
    }

    public boolean isRightOf(int xPosPixels) {
        return x > xPosPixels;
    }

    public boolean isAbove(int yPosPixels) {
        return y + getHeight() < yPosPixels;
    }

    public boolean isBelow(int yPosPixels) {
        return y > yPosPixels;
    }

    public boolean isCollidingWith(Entity other) {
        if (other == null) return false;

        int thisLeftHitBox = getX() + getWidth();
        int thisRightHitBox = getX();
        int thisBottomHitBox = getY() + getHeight();
        int thisTopHitBox = getY();

        int otherLeftHitBox = other.getX() + other.getWidth();
        int otherRightHitBox = other.getX();
        int otherBottomHitBox = other.getY() + other.getHeight();
        int otherTopHitBox = other.getY();

        // checks for any overlap between Nestor and any other
        return thisLeftHitBox >= otherRightHitBox && thisRightHitBox <= otherLeftHitBox
                && thisBottomHitBox >= otherTopHitBox && thisTopHitBox <= otherBottomHitBox;
    }

    public abstract Type type();

}
