package edu.wsu.model;

import edu.wsu.model.enums.EntityType;
import edu.wsu.view.View;

public class Obstacle implements Entity {

    private final int speed;

    private static final int canvasHeight = 400;
    private static final int canvasWidth = 600;

    private final int width;
    private final int height;
    private double x;
    private final double y;

    private EntityType entityType;

    public Obstacle(EntityType entityType, int obstacleSpeed) {
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
                this.width = 30;
                this.height = 2;
                this.x = canvasWidth;
                this.y = canvasHeight - this.height;
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
        this.y = ground - this.height;
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
        x = x - (speed * deltaTime);
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
