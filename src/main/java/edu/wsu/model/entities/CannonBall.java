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

    @Override
    public Type type() {
        return Type.CannonBall;
    }

}
