package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Flyer extends Entity {

    private int x;
    private int y;

    public Flyer() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight() - 60);
    }

    @Override
    public int getWidth() {
        return 30;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public Type type() {
        return Type.Flyer;
    }
}
