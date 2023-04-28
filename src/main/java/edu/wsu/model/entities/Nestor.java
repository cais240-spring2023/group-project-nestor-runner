package edu.wsu.model.entities;

import static edu.wsu.model.NestorRunner.GROUND_Y;

public class Nestor extends Entity {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 50;
    public static final int FALL_SPEED = 10;
    public static final int BASE_JUMP_SPEED = 400; // m/s
    public static final int BASE_Y_POS = GROUND_Y - HEIGHT;
    public static final int BASE_X_POS = 50;
    private double jumpSpeed;
    private boolean isJumping;

    public Nestor() {
        super();
        setX(BASE_X_POS);
        setY(BASE_Y_POS);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public Type type() {
        return Type.Nestor;
    }

    public boolean isCollidingWith(Entity other, boolean isShielded, int bubbleRadius) {
        if (other == null) return false;

        int thisLeftHitBox = getX() + getWidth();
        int thisRightHitBox = getX();
        int thisBottomHitBox = getY() + getHeight();
        int thisTopHitBox = getY();

        if (isShielded) {
            thisLeftHitBox += bubbleRadius - Nestor.WIDTH/2;
            thisRightHitBox -= bubbleRadius - Nestor.WIDTH/2;
            thisTopHitBox -= bubbleRadius;
        }

        int otherLeftHitBox = other.getX() + other.getWidth();
        int otherRightHitBox = other.getX();
        int otherBottomHitBox = other.getY() + other.getHeight();
        int otherTopHitBox = other.getY();

        // checks for any overlap between Nestor and any other
        return thisLeftHitBox >= otherRightHitBox && thisRightHitBox <= otherLeftHitBox
                && thisBottomHitBox >= otherTopHitBox && thisTopHitBox <= otherBottomHitBox;
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

    public void jump(double deltaTime, double gravity) {
        jumpSpeed += gravity * deltaTime;
        setY((int) (getY() + jumpSpeed*deltaTime));

        if (getY() >= GROUND_Y - HEIGHT) {
            setY(GROUND_Y - HEIGHT);
            jumpSpeed = 0;
            isJumping = false;
        }
    }

}