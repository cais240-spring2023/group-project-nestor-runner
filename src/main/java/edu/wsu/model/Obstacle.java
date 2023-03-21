package edu.wsu.model;

import edu.wsu.model.enums.EntityType;
import edu.wsu.view.View;

public class Obstacle implements Entity {
    public static final int SPEED = 200;
    private final int width;
    private final int height;
    private double x;
    private final double y;

    private EntityType entityType;

    public Obstacle() {
        this.width = 50;
        this.height = 70;
        this.x = View.SCENE_WIDTH;
        this.y = View.GROUND_Y - 70;
    }
    public Obstacle(double x, double y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Obstacle(EntityType entityType) {
        this.entityType = entityType;
        switch (this.entityType){
            case BIG_BUILDING:
                this.width = 54;
                this.height = 75;
                break;
            case SMALL_BUILDING:
                this.width = 70;
                this.height = 70;
                break;
            case HOLE:
                this.width = 60;
                this.height = 5;
                break;
            case PROJECTILE:
                this.width = 30;
                this.height = 30;
                break;
            default:
                this.width = 50;
                this.height = 70;
                break;
        }
        this.x = View.SCENE_WIDTH;
        this.y = View.GROUND_Y- this.height;
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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void update(double deltaTime) {
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

    @Override
    public EntityType getEntityType() {
        return this.entityType;
    }
}
