package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

public class CannonBall {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int SPEED = 10;
    private int x;
    private final int y;

    public CannonBall(int nestorY) {
        x = 100;
        y = nestorY + 15;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean hasPassedRight() {
        return x > Entity.START_X;
    }

    public boolean hasCollided(Entity entity) {
        if (entity == null) return false;

        return (x + WIDTH > entity.getX() &&
                y + HEIGHT > entity.getY());
    }

    public void moveLeft() {
        x = x + SPEED;
    }

}
