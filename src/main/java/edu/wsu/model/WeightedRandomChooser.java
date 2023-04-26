package edu.wsu.model;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedRandomChooser<Choice> {

    private static class WeightedChoice<Ch> {
        Ch choice;
        int accumulatedWeight;
    }

    public static final int COMMON = 20;
    public static final int UNCOMMON = 10;
    public static final int RARE = 5;
    // public static final int VERY_RARE = 1;

    private final List<WeightedChoice<Choice>> weightedChoices;
    private final Random random;
    private int accumulatedWeight;

    public WeightedRandomChooser() {
        weightedChoices = new ArrayList<>();
        random = new Random();
    }

    /**
     * Adds a choice to the list of possible choices.
     *
     * @param choice A new possible choice.
     * @param weight Weight applied that dictates how often that choice is randomly selected.
     */
    public void addChoice(Choice choice, int weight) {
        //TODO: During runtime, choices cannot be removed from choice list once added.
        accumulatedWeight += weight;
        WeightedChoice<Choice> weightedChoice = new WeightedChoice<>();
        weightedChoice.choice = choice;
        weightedChoice.accumulatedWeight = accumulatedWeight;
        weightedChoices.add(weightedChoice);
    }

    /**
     * Returns a list of all possible choices.
     * @return a list of all possible choices.
     */
    public List<Choice> getChoices() {
        return weightedChoices.stream()
                .map(weightedChoice -> weightedChoice.choice)
                .collect(Collectors.toList());
    }

    /**
     * Returns a random choice, weighted, from the list of possible choices.
     *
     * @return a random choice, weighted, from the list of possible choices.
     */
    public Choice choose() {
        double randomDouble = random.nextDouble() * accumulatedWeight;

        for (WeightedChoice<Choice> weightedChoice: weightedChoices) {
            if (weightedChoice.accumulatedWeight >= randomDouble) {
                return weightedChoice.choice;
            }
        }
        return null;
    }
}
