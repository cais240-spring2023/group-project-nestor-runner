package edu.wsu.model.Entities;

import edu.wsu.model.Entities.Entity;

public class CannonBall implements Entity {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
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
        return 30;
    }

    @Override
    public int getHeight() {
        return 30;
    }

    public boolean hasCollided(Entity entity) {
        if (entity == null) return false;

        return (x + WIDTH > entity.getX() &&
                y + HEIGHT > entity.getY());
    }

    public void moveLeft(int amount) {
        x += amount;
    }

    @Override
    public void moveRight(int amount) {

    }

    @Override
    public void moveUp(int amount) {

    }

    @Override
    public void moveDown(int amount) {

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
