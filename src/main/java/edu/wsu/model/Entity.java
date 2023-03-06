package edu.wsu.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public interface Entity {

    double getX();
    double getY();
    double getWidth();
    double getHeight();
    Color getColor();
    void update(double deltaTime);
    boolean leftCollidesWith(Entity other);
    boolean rightCollidesWith(Entity other);
}
