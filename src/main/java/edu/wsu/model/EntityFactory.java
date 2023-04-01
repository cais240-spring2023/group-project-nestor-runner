package edu.wsu.model;

import edu.wsu.model.Entities.*;

public class EntityFactory {

    public Entity createEntity(String entityType) {
        if (entityType == null || entityType.isEmpty()) return null;

        switch (entityType) {
            case "Coin":
                return new Coin();
            case "Hole":
                return new Hole();
            case "LargeObstacle":
                return new LargeObstacle();
            case "Projectile":
                return new Projectile();
            case "Shield":
                return new Shield();
            case "SmallObstacle":
                return new SmallObstacle();
            default:
                throw new IllegalArgumentException("Invalid entityType " + entityType);
        }
    }
}
