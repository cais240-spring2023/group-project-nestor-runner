package edu.wsu.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Nestor implements Entity {

    private static final int JUMP_SPEED = 400;
    private static final double GRAVITY = 600;
    private final int width;
    private final int height;
    final private double x;
    private double y;
    private double ySpeed;
    private boolean isJumping;

    public Nestor(double startXPos, double startYPos) {
        x = startXPos;
        y = startYPos;
        ySpeed = 0;
        isJumping = false;
        this.width = 50;
        this.height = 50;
    }

    public Nestor(double startXPos, double startYPos, int width, int height) {
        x = startXPos;
        y = startYPos;
        ySpeed = 0;
        isJumping = false;
        this.width = width;
        this.height = height;
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            ySpeed = -JUMP_SPEED;
        }
    }

    /**
     * updates y, ySpeed, and isJumping.
     *
     * @param deltaTime change in time, can be changed later if we decide to do something else.
     */
    @Override
    public void update(double deltaTime, Canvas canvas) {
        ySpeed += GRAVITY * deltaTime;
        y += ySpeed * deltaTime;

        if (y >= ((int) canvas.getHeight()) - height) {
            y = ((int) canvas.getHeight()) - height;
            ySpeed = 0;
            isJumping = false;
        }
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

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
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
        return Color.BLUE;
    }

}