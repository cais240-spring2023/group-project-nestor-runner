package edu.wsu.model.Entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class LargeObstacle implements Entity {

    public int width = 55;
    public int height = 75;
    public int x = START_X;
    public int y = GROUND_Y - height;

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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Type type() {
        return Type.LargeObstacle;
    }

}
