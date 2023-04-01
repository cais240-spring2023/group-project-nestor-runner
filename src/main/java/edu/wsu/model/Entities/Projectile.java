package edu.wsu.model.Entities;

public class Projectile implements Entity {

    public int width = 30;
    public int height = 20;
    public int x = START_X;
    public int y = GROUND_Y - height - 60;

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
        return "Projectile";
    }
}
