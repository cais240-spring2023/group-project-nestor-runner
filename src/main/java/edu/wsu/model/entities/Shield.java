package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Shield extends Entity {

    public Shield() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight() - 5);
    }

    @Override
    public int getWidth() {
        return 35;
    }

    @Override
    public int getHeight() {
        return 45;
    }

    @Override
    public Type type() {
        return Type.Shield;
    }

}
