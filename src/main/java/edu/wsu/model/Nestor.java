package edu.wsu.model;

import javafx.scene.paint.Color;

public class Nestor implements Entity {

    private static final int JUMP_SPEED = 400; // m/s
    private static final double GRAVITY = 600; // m/s^2
    private static final int canvasHeight = 400;
    private static final int canvasWidth = 600;
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

    // issue: actual jump calculations are done based on change in time
    public double getMaxJumpHeight() {
        double jumpHeight = 0;
        /*
        // gravity = 600 m/s^2
        // jump speed = 400 m/s

        // time = (final velocity - initial velocity) / acceleration
        // we want final velocity to be zero, because that is when we reach the peak
        double jumpTime = (0 - JUMP_SPEED)/(-GRAVITY);

        // delta y = initial velocity * time - 1/2 * acceleration * (time^2)
        jumpHeight = (JUMP_SPEED * jumpTime) + ((GRAVITY * (jumpTime * jumpTime))/2);

         */

        // alternate approach: h = (v^2) / (2g)
        jumpHeight = (JUMP_SPEED * JUMP_SPEED) / (2 * GRAVITY);
        return jumpHeight;
    }

    public void jump() {
        if (isJumping) return;
        isJumping = true;
        ySpeed = -JUMP_SPEED;
    }

    /**
     * updates y, ySpeed, and isJumping.
     *
     * @param deltaTime change in time, can be changed later if we decide to do something else.
     */
    @Override
    public void update(double deltaTime) {
        ySpeed += GRAVITY * deltaTime;
        y += ySpeed * deltaTime;

        if (y >= ((int) canvasHeight) - height) {
            y = ((int) canvasHeight) - height;
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