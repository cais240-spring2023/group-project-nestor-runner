package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

public class CollisionEvent {

    private final Entity collider;
    private final Entity collided;

    public CollisionEvent(Entity collider, Entity collided) {
        this.collider = collider;
        this.collided = collided;
    }

    public Entity getCollider() {
        return collider;
    }

    public Entity getCollided() {
        return collided;
    }

}
