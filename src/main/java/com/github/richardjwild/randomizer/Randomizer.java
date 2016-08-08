package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.IntegerRandomizer;
import com.github.richardjwild.randomizer.types.StringRandomizer;

public abstract class Randomizer<T> {

    public static <T> Randomizer<T> forType(Class<T> type) {
        if (type == String.class)
            return (Randomizer<T>) new StringRandomizer();
        else if (type == Integer.class)
            return (Randomizer<T>) new IntegerRandomizer();
        return null;
    }

    public abstract T randomValue();
}
