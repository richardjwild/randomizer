package com.github.richardjwild.randomizer;

import java.util.Random;

public abstract class Randomizer<T> {

    private static RandomizerFactory randomizerFactory = new RandomizerFactory();

    public static <T> Randomizer<T> forType(Class<T> type) {
        return randomizerFactory.randomizerFor(type);
    }

    protected final Random random = new Random();

    public abstract T value();

    public Randomizer<T> max(T maxValue) {
        throw new UnsupportedOperationException();
    }

    public Randomizer<T> min(T minValue) {
        throw new UnsupportedOperationException();
    }

    public Randomizer<T> length(int length) {
        throw new UnsupportedOperationException();
    }
}
