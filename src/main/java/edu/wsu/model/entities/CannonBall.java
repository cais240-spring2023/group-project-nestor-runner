package edu.wsu.model.entities;

public class CannonBall extends Entity {

    public static final int WIDTH = 35;
    public static final int HEIGHT = 35;
    public static final int SPEED = 15;

    public CannonBall(int nestorY) {
        super();
        setX(100);
        setY(nestorY + 15);
    }

    public CannonBall() {
        this(0);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public boolean isCollidingWith(Entity entity) {
        if (entity == null) return false;

        int leftHitBox = getX() + CannonBall.WIDTH;
        int rightHitBox = getX();
        int bottomHitBox = getY() + CannonBall.HEIGHT;
        int topHitBox = getY();

        // checks for any overlap between Nestor and any entity
        return (leftHitBox > entity.getX())
                && (rightHitBox < (entity.getX() + entity.getWidth()))
                && ((bottomHitBox) > entity.getY())
                && (topHitBox < (entity.getY() + entity.getHeight()));
    }

    @Override
    public Type type() {
        return Type.CannonBall;
    }

}
