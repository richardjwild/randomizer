package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.IntegerRandomizer;
import com.github.richardjwild.randomizer.types.StringRandomizer;

public class RandomizerFactory {

    public <T> Randomizer<T> randomizerFor(Class<T> type) {
        if (type == String.class)
            return (Randomizer<T>) new StringRandomizer();
        else if (type == Integer.class)
            return (Randomizer<T>) new IntegerRandomizer();
        throw new IllegalArgumentException();
    }
}
