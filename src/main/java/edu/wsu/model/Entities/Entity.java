package edu.wsu.model.Entities;

public interface Entity {

    // these need to mirror the Sprite file names.
    enum Type {
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
    boolean hasPassedLeft();
    void moveLeft(int entitySpeed, double deltaTime);

    Type type();
}
