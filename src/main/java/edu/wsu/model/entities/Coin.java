package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Coin extends Entity {

    public Coin() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight() - 50);
    }

    @Override
    public int getWidth() {
        return 30;
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public Type type() {
        return Type.Coin;
    }

}
