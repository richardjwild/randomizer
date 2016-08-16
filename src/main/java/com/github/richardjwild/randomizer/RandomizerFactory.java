package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.*;

import java.util.Date;

public class RandomizerFactory {

    public <T> Randomizer<T> randomizerFor(Class<T> type) {
        if (type == String.class)
            return (Randomizer<T>) new StringRandomizer();
        else if (type == Date.class)
            return (Randomizer<T>) new DateRandomizer();
        else if (type == Integer.class)
            return (Randomizer<T>) new IntegerRandomizer();
        else if (type == Long.class)
            return (Randomizer<T>) new LongRandomizer();
        else if (type == Double.class)
            return (Randomizer<T>) new DoubleRandomizer();
        else if (type == Float.class)
            return (Randomizer<T>) new FloatRandomizer();
        else if (type == Character.class)
            return (Randomizer<T>) new CharacterRandomizer();
        throw new IllegalArgumentException();
    }
}
