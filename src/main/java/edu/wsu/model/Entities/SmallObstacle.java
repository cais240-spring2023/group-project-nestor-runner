package edu.wsu.model.Entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class SmallObstacle implements Entity {

    private int x;
    private int y;

    public SmallObstacle() {
        x = START_X;
        y = GROUND_Y - getHeight();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 50;
    }

    @Override
    public boolean hasPassedLeft() {
        return (x + getWidth() <= 0);
    }

    @Override
    public void moveLeft(int amount) {
        x -= amount;
    }

    @Override
    public void moveRight(int amount) {
        x += amount;
    }

    @Override
    public void moveUp(int amount) {
        y -= amount;
    }

    @Override
    public void moveDown(int amount) {
        y += amount;
    }

    @Override
    public Type type() {
        return Type.SmallObstacle;
    }

}
