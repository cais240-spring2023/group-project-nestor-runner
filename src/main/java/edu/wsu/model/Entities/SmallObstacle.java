package edu.wsu.model.Entities;

public class SmallObstacle implements Entity {

    public int width = 50;
    public int height = 50;
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
    public String type() {
        return "SmallObstacle";
    }

}
