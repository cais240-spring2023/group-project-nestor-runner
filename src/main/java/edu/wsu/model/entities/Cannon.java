package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Cannon extends Entity {

    public Cannon() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight() - 7);
    }

    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public int getHeight() {
        return 60;
    }

    @Override
    public Type type() {
        return Type.Cannon;
    }
}
