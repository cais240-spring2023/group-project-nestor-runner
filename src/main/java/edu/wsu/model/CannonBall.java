package edu.wsu.model;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class CannonBall {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public int x = 100;
    private int y;

    public CannonBall(int nestorY) {
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

}
