package edu.wsu.model.Entities;

public interface Entity {
    enum Type {
        Nestor, BigBuilding, SMALL_BUILDING, Projectile,
        Hole, Coin, SmallObstacle, Shield, LargeObstacle,
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
