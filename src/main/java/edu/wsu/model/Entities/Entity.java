package edu.wsu.model.Entities;

public interface Entity {
    int START_X = 600;
    int GROUND_Y = 325;
    int GROUND_HEIGHT = 75;
    int SPEED = 200;

    int getX();
    void setX(int x);
    int getY();
    int getWidth();
    int getHeight();

    String type();
}
