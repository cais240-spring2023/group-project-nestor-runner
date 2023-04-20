package edu.wsu.model.Entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Hole implements Entity {

    public int width = 85;
    public int height = 480 - GROUND_Y + 1;
    public int x = START_X;
    public int y = GROUND_Y - 1;

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
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Type type() {
        return Type.Hole;
    }

}
