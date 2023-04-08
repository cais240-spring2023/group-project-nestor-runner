package edu.wsu.model;

import edu.wsu.model.Entities.*;

public class EntityFactory {

    /**
     * This method will randomly generate an entity
     * based on probability. Entities that help Nestor
     * are less likely to spawn than obstacle entities.
     * @return the randomly generated entity
     *
     * @author Noah Kelly
     */
    public Entity generate() {
        double entitySelector = Math.random();
        Entity.Type newEntityType;

        if (entitySelector <= 0.1) newEntityType = Entity.Type.Coin;                    // 10% chance
        else if (entitySelector <= 0.3) newEntityType = Entity.Type.Hole;               // 20% chance
        else if (entitySelector <= 0.5) newEntityType = Entity.Type.Flyer;              // 20% chance
        else if (entitySelector <= 0.7) newEntityType = Entity.Type.SmallObstacle;      // 20% chance
        else if (entitySelector <= 0.8) newEntityType = Entity.Type.Shield;             // 10% chance
        else if (entitySelector <= 0.85) newEntityType = Entity.Type.Cannon;            //  5% chance
        else newEntityType = Entity.Type.LargeObstacle;                                 // 15% chance

        switch (newEntityType) {
            case Coin:
                return new Coin();
            case Hole:
                return new Hole();
            case LargeObstacle:
                return new LargeObstacle();
            case Flyer:
                return new Flyer();
            case Shield:
                return new Shield();
            case Cannon:
                return new Cannon();
            case SmallObstacle:
                return new SmallObstacle();
            default:
                throw new IllegalArgumentException("Invalid entityType " + newEntityType);
        }
    }
}
