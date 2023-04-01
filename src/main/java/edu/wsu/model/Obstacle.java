/*
package edu.wsu.model;

import edu.wsu.model.Entities.Entity;
import edu.wsu.model.enums.CollisionType;
import edu.wsu.model.enums.EntityType;
import edu.wsu.view.View;

public class Obstacle implements Entity {
    public static final int SPEED = 200;
    private final int width;
    private final int height;
    private double x;
    private final double y;

    public final EntityType entityType;
    private CollisionType collisionType;

    public Obstacle(EntityType entityType, int ground) {
        this.entityType = entityType;
        switch (this.entityType){
            case BIG_BUILDING:
                this.width = 54;
                this.height = 75;
                collisionType = CollisionType.OBSTACLE;
                break;
            case SMALL_BUILDING:
                this.width = 70;
                this.height = 70;
                collisionType = CollisionType.OBSTACLE;
                break;
            case HOLE:
                this.width = 60;
                this.height = 5;
                collisionType = CollisionType.HOLE;
                break;
            case PROJECTILE:
                this.width = 30;
                this.height = 20;
                collisionType = CollisionType.OBSTACLE;
                break;
            case COIN:
                this.width = 30;
                this.height = 30;
                collisionType = CollisionType.COIN;
                break;
            case SHIELD:
                this.width = 26;
                this.height = 36;
                collisionType = CollisionType.SHIELD;
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
    public EntityType type() {
        return this.entityType;
    }

    @Override
    public CollisionType getCollisionType() {
        return collisionType;
    }
}
*/
