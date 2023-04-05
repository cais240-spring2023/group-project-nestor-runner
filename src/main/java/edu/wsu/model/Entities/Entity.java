package edu.wsu.model.Entities;

public interface Entity {
    enum Type {  // these need to mirror the Sprite file names in order to work.
        Flyer, Hole, Coin, SmallObstacle,
        Shield, LargeObstacle, Cannon
    }

    int START_X = 640;
    int SPEED = 200;

    int getX();
    void setX(int x);
    int getY();
    int getWidth();
    int getHeight();

    Type type();
}
