package edu.wsu.model;

import edu.wsu.model.Entities.*;

import java.util.*;

public class EntitySpawner {

    public static final int COMMON = 20;
    public static final int UNCOMMON = 10;
    public static final int RARE = 5;
    // public static final int VERY_RARE = 1;

    private static class SpawnRateEntry {
        Entity.Type entityType;
        int accumulatedWeight;
    }

    private final List<SpawnRateEntry> spawnRates = new ArrayList<>();
    private final Random random;
    private int accumulatedWeight;

    public EntitySpawner() {
        random = new Random();

        // populate spawnRates:
        addEntityType(Entity.Type.Coin, UNCOMMON);
        addEntityType(Entity.Type.Hole, COMMON);
        addEntityType(Entity.Type.Flyer, COMMON);
        addEntityType(Entity.Type.SmallObstacle, COMMON);
        addEntityType(Entity.Type.Shield, UNCOMMON);
        addEntityType(Entity.Type.Cannon, RARE);
        addEntityType(Entity.Type.LargeObstacle, COMMON);
    }

    /**
     * Adds an entityType to the list of possible Entities to generate.
     *
     * @param entityType Type of entity to add to EntitySpawner's spawn list.
     * @param weight Weight applied that dictates how often entityType is randomly selected.
     */
    public void addEntityType(Entity.Type entityType, int weight) {
        //TODO: During runtime, entities cannot be removed from Spawn list once added.
        accumulatedWeight += weight;
        SpawnRateEntry spawnRateEntry = new SpawnRateEntry();
        spawnRateEntry.entityType = entityType;
        spawnRateEntry.accumulatedWeight = accumulatedWeight;
        spawnRates.add(spawnRateEntry);
    }

    /**
     * This method will randomly generate an entity based on their unique probability of spawning.
     * Entities that help Nestor are less likely to spawn than obstacle entities.
     *
     * @return the randomly generated entity.
     */
    public Entity spawnEntity() {
        double randDouble = random.nextDouble() * accumulatedWeight;

        for (SpawnRateEntry spawnRateEntry: spawnRates) {
            if (spawnRateEntry.accumulatedWeight >= randDouble) {
                return Entity.init(spawnRateEntry.entityType);
            }
        }
        return null;
    }
}
