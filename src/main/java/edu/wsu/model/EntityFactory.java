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

        if (entitySelector <= 0.1) return new Coin();                       // 10% chance
        else if (entitySelector <= 0.3) return new Hole();                  // 20% chance
        else if (entitySelector <= 0.5) return new Flyer();                 // 20% chance
        else if (entitySelector <= 0.7) return new SmallObstacle();         // 20% chance
        else if (entitySelector <= 0.8) return new Shield();                // 10% chance
        else if (entitySelector <= 0.85) return new Cannon();               //  5% chance
        else return new LargeObstacle();                                    // 15% chance

    }
}
