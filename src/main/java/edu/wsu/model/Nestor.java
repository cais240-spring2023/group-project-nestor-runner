package edu.wsu.model;

import edu.wsu.model.enums.EntityType;

public class Nestor implements Entity {


    private int jump_speed = 400; // pixels per second
    private double gravity = 600; // pixels per second squared
    private static final int canvasHeight = 400;

    private final int width;
    private final int height;
    private double x;
    private double xSpeed;
    private double y;
    private double ySpeed;
    private boolean isJumping;

    public Nestor(int ground) {
        this.ground = ground;
        y = ground;
        x = START_X;
        ySpeed = 0;
        isJumping = false;
        this.width = 50;
        this.height = 50;
    }

    public Nestor(int ground, double startXPos) {
        this.ground = ground;
        x = startXPos;
        y = ground;
        ySpeed = 0;
        isJumping = false;
        this.width = 50;
        this.height = 50;
    }

    // issue: actual jump calculations are done based on change in time


    public void jump() {
        if (!isJumping) {
            isJumping = true;
            ySpeed = -jump_speed;
        }
    }

    public boolean getJumpingStatus() {
        return isJumping;
    }

    /**
     * updates y, ySpeed, and isJumping.
     *
     * @param deltaTime change in time in seconds
     */
    @Override
    public void update(double deltaTime) {
        ySpeed += gravity * deltaTime;
        y += ySpeed * deltaTime;

        // done

        if (y >= ground - height) {
            y = (ground - height);
            ySpeed = 0;
            isJumping = false;
        }
    }

    /**
     * checks from the top left corner, if other entity is within width or height distance from the top left corner of nestor
     * @param other the entity we are checking for collisions with
     * @return true if collision between nestor and other
     */
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
    public EntityType getEntityType() {
        return EntityType.NESTOR;
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
}