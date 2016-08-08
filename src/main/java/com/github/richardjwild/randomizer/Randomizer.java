package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.StringRandomizer;

public abstract class Randomizer<T> {

    public static <T> Randomizer<T> forType(Class<T> type) {
        return (Randomizer<T>) new StringRandomizer();
    }

    public abstract T randomValue();
}
