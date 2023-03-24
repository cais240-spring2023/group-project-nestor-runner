package edu.wsu.model;

import edu.wsu.model.enums.EntityType;

public interface Entity {

    double getX();
    double getY();
    double getWidth();
    double getHeight();
    void update(double deltaTime);
    boolean leftCollidesWith(Entity other);
    boolean rightCollidesWith(Entity other);

    EntityType getEntityType();
}
