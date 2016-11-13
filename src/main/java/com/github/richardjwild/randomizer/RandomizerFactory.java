package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.exception.NoRandomizerFoundException;
import com.github.richardjwild.randomizer.exception.ShouldNeverHappenException;
import com.github.richardjwild.randomizer.types.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class RandomizerFactory {

    private Map<Class<?>, Class<? extends Randomizer>> randomizers = new HashMap<>();

    RandomizerFactory() {
        randomizers.put(String.class, StringRandomizer.class);
        randomizers.put(Date.class, DateRandomizer.class);
        randomizers.put(Integer.class, IntegerRandomizer.class);
        randomizers.put(Long.class, LongRandomizer.class);
        randomizers.put(Double.class, DoubleRandomizer.class);
        randomizers.put(Float.class, FloatRandomizer.class);
        randomizers.put(Character.class, CharacterRandomizer.class);
        randomizers.put(Boolean.class, BooleanRandomizer.class);
    }

    <T> Randomizer<T> randomizerFor(Class<T> type) {
        if (randomizers.containsKey(type)) {
            try {
                return (Randomizer<T>) randomizers.get(type).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ShouldNeverHappenException(e);
            }
        }
        throw new NoRandomizerFoundException(type.getName());
    }
}
