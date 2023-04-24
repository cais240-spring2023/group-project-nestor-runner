package edu.wsu.model.Entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Shield implements Entity {

    private int x;
    private int y;

    public Shield() {
        x = START_X;
        y = GROUND_Y - getHeight() - 5;
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
        return 35;
    }

    @Override
    public int getHeight() {
        return 45;
    }

    @Override
    public boolean hasPassedLeft() {
        return (x + getWidth() <= 0);
    }

    @Override
    public void moveLeft(int entitySpeed, double deltaTime) {
        x -= (int) (entitySpeed * deltaTime * 1.5);
    }

    @Override
    public Type type() {
        return Type.Shield;
    }

}
