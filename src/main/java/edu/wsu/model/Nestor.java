package edu.wsu.model;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Nestor {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 50;
    public static final int X = 50;
    private int y = GROUND_Y - HEIGHT;

    public int getX() {
        return X;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}