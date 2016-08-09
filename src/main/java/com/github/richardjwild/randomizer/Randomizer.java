package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.IntegerRandomizer;
import com.github.richardjwild.randomizer.types.StringRandomizer;

import java.util.Random;

public abstract class Randomizer<T> {

    protected final Random random = new Random();

    public static <T> Randomizer<T> forType(Class<T> type) {
        if (type == String.class)
            return (Randomizer<T>) new StringRandomizer();
        else if (type == Integer.class)
            return (Randomizer<T>) new IntegerRandomizer();
        throw new IllegalArgumentException();
    }

    public abstract T value();

    public Randomizer<T> max(T maxValue) {
        throw new UnsupportedOperationException();
    }

    public Randomizer<T> min(T minValue) {
        throw new UnsupportedOperationException();
    }
}
