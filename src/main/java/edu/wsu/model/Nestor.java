package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Nestor {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 50;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final int BASE_HEIGHT = GROUND_Y - HEIGHT;
    private int x;
    private int y;
    private double jumpSpeed;
    private boolean isJumping;

    public Nestor() {
        x = 50;
        y = BASE_HEIGHT;
        jumpSpeed = 0;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJump(boolean cannonIsActive) {
        if (!isJumping) {
            if (cannonIsActive) jumpSpeed = -BASE_JUMP_SPEED - 100;
            else jumpSpeed = -BASE_JUMP_SPEED;
            isJumping = true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void jump(double deltaTime, double gravity) {
        jumpSpeed += gravity * deltaTime;
        y = (int) (y + jumpSpeed*deltaTime);

        if (y >= GROUND_Y - HEIGHT) {
            y = GROUND_Y - HEIGHT;
            jumpSpeed = 0;
            isJumping = false;
        }
    }

    public void moveRight(int amount) {
        x += amount;
    }

    public void fall(int amount) {
        y += amount;
    }

    /**
     * This method checks for a valid collision between any
     * entity and Nestor. A collision is valid if Nestor is
     * not shielded.
     * @return true if Nestor overlaps with an entity,
     * false otherwise
     */
    public boolean hasCollided(Entity entity, boolean isShielded, int bubbleRadius) {
        if (entity == null) return false;

        int leftHitBox = x + Nestor.WIDTH;
        int rightHitBox = x;
        int bottomHitBox = y + Nestor.HEIGHT;
        int topHitBox = y;

        if (isShielded) {
            leftHitBox += bubbleRadius - Nestor.WIDTH/2;
            rightHitBox -= bubbleRadius - Nestor.WIDTH/2;
            topHitBox -= bubbleRadius;
        }

        // checks for any overlap between Nestor and any entity
        return (leftHitBox > entity.getX())
                && (rightHitBox < (entity.getX() + entity.getWidth()))
                && ((bottomHitBox) > entity.getY())
                && (topHitBox < (entity.getY() + entity.getHeight()));
    }
}