package edu.wsu.model;

import edu.wsu.model.enums.EntityType;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Obstacle implements Entity {
    private static final int SPEED = 200;

    private static final int canvasHeight = 400;
    private static final int canvasWidth = 600;
    private final int width;
    private final int height;
    private double x;
    private final double y;

    private EntityType entityType;

    public Obstacle() {
        this.width = 50;
        this.height = 70;
        this.x = canvasWidth;
        this.y = canvasHeight - 70;
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
                this.width = 50;
                this.height = 70;
                this.x = canvasWidth;
                this.y = canvasHeight - this.height;
                break;
            case SMALL_BUILDING:
                this.width = 50;
                this.height = 50;
                this.x = canvasWidth;
                this.y = canvasHeight - this.height;
                break;
            case HOLE:
                this.width = 60;
                this.height = 5;
                this.x = canvasWidth;
                this.y = canvasHeight - this.height;
                break;
            case PROJECTILE:
                this.width = 30;
                this.height = 30;
                this.x = canvasWidth;
                this.y = canvasHeight - 50 - this.height;
                break;
            default:
                this.width = 50;
                this.height = 70;
                this.x = canvasWidth;
                this.y = canvasHeight - this.height;
                break;
        }
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
