package edu.wsu.model.Entities;

public interface Entity {
    enum Type {  // these need to mirror the Sprite file names.
        Flyer, Hole, Coin, SmallObstacle,
        Shield, LargeObstacle, Cannon
    }

    int START_X = 640;

    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getWidth();
    int getHeight();

    Type type();
}
