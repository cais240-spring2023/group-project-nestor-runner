package edu.wsu.model.Entities;

public class Hole implements Entity {

    public int width = 75;
    public int height = GROUND_HEIGHT;
    public int x = START_X;
    public int y = GROUND_Y;

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
        return "Hole";
    }
}
