package edu.wsu.model;

import edu.wsu.model.Entities.Entity;

public class CollisionEvent<T> {

    private final T collider;
    private final Entity collided;

    public CollisionEvent(T collider, Entity collided) {
        this.collider = collider;
        this.collided = collided;
    }

    public T getCollider() {
        return collider;
    }

    public Entity getCollided() {
        return collided;
    }

}
