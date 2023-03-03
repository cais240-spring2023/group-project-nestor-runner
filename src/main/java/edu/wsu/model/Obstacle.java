package edu.wsu.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Obstacle implements Entity {
    private static final int SPEED = 200;
    private final int width;
    private final int height;
    private double x;
    private final double y;

    public Obstacle(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 50;
    }
    public Obstacle(double x, double y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void update(double deltaTime, Canvas canvas) {
        x = x - (SPEED * deltaTime);
    }

    @Override
    public boolean leftCollidesWith(Entity other) {
        return (other.getX() + other.getWidth() > x && other.getX() < x + width &&
                other.getY() + other.getHeight() > y && other.getY() < y + height);
    }

    @Override
    public boolean rightCollidesWith(Entity other) {
        return (other.getX() < x + width && other.getX() + other.getWidth() > x &&
                other.getY() < y + height && other.getY() + other.getHeight() > y);
    }
}
