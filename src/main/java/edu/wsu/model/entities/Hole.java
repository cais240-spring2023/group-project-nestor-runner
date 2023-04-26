package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Hole implements Entity {

    private int x;
    private int y;

    public Hole() {
        x = START_X;
        y = GROUND_Y;
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
        return 125;
    }

    @Override
    public int getHeight() {
        return 480 - GROUND_Y;
    }

    @Override
    public boolean hasPassedLeft() {
        return x + getWidth() <= 0;
    }

    @Override
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

    @Override
    public Type type() {
        return Type.Hole;
    }

}
