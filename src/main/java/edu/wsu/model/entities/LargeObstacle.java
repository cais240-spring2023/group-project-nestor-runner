package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class LargeObstacle extends Entity {

    public LargeObstacle() {
        super();
        setX(START_X);
        setY(GROUND_Y - getHeight());
    }

    @Override
    public int getWidth() {
        return 55;
    }

    @Override
    public int getHeight() {
        return 75;
    }

    @Override
    public Type type() {
        return Type.LargeObstacle;
    }

}
