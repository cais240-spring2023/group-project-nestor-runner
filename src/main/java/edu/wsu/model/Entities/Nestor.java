package edu.wsu.model.Entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Nestor implements Entity {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 50;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final int BASE_Y_POS = GROUND_Y - HEIGHT;
    private int x;
    private int y;
    private double jumpSpeed;
    private boolean isJumping;

    public Nestor() {
        x = 50;
        y = BASE_Y_POS;
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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return 40;
    }

    @Override
    public int getHeight() {
        return 50;
    }

    @Override
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

    @Override
    public void moveLeft(int amount) {
        x -= amount;
    }

    @Override
    public void moveRight(int amount) {
        x += amount;
    }

    @Override
    public void moveUp(int amount) {
        y -= amount;
    }

    @Override
    public void moveDown(int amount) {
        y += amount;
    }

    @Override
    public boolean hasPassedLeft() {
        return x + getWidth() <= 0;
    }

    @Override
    public Type type() {
        return Type.Nestor;
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