package edu.wsu.model.entities;

public class CannonBall implements Entity {

    public static final int WIDTH = 35;
    public static final int HEIGHT = 35;
    public static final int SPEED = 10;
    private int x;
    private int y;

    public CannonBall() {
        x = 100;
        y = 15;
    }

    public CannonBall(int nestorY) {
        x = 100;
        y = nestorY + 15;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
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

        return x + WIDTH > entity.getX() &&
                y + HEIGHT > entity.getY();
    }

    public void moveLeft(int amountPixels) {
        x -= amountPixels;
    }

    @Override
    public void moveRight(int amountPixels) {
        x += amountPixels;
    }

    @Override
    public void moveUp(int amountPixels) {
        y -= amountPixels;
    }

    @Override
    public void moveDown(int amountPixels) {
        y += amountPixels;
    }

    public boolean hasPassedRight() {
        return x > Entity.START_X;
    }

    @Override
    public boolean hasPassedLeft() {
        return x + getWidth() <= 0;
    }

    @Override
    public Type type() {
        return Type.CannonBall;
    }

}
