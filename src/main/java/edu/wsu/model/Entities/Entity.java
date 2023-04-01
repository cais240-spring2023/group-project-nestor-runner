package edu.wsu.model.Entities;

public interface Entity {
    int START_X = 640;
    int GROUND_Y = 400;
    int GROUND_HEIGHT = 175;
    int SPEED = 200;

    int getX();
    void setX(int x);
    int getY();
    int getWidth();
    int getHeight();

    String type();
}
