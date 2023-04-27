package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class SmallObstacle extends Entity {

    public SmallObstacle() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight());
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return 50;
    }

    @Override
    public Type type() {
        return Type.SmallObstacle;
    }

}
