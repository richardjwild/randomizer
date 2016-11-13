package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.exception.NoRandomizerFoundException;
import com.github.richardjwild.randomizer.types.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class RandomizerFactory {

    private Map<Class<?>, Class<? extends Randomizer>> randomizerClasses = new HashMap<>();

    RandomizerFactory() {
        randomizerClasses.put(String.class, StringRandomizer.class);
        randomizerClasses.put(Date.class, DateRandomizer.class);
        randomizerClasses.put(Integer.class, IntegerRandomizer.class);
        randomizerClasses.put(Long.class, LongRandomizer.class);
        randomizerClasses.put(Double.class, DoubleRandomizer.class);
        randomizerClasses.put(Float.class, FloatRandomizer.class);
        randomizerClasses.put(Character.class, CharacterRandomizer.class);
        randomizerClasses.put(Boolean.class, BooleanRandomizer.class);
    }

    <T> Randomizer<T> randomizerFor(Class<T> type) {
        if (randomizerClasses.containsKey(type)) {
            Class<? extends Randomizer> randomizerClass = randomizerClasses.get(type);
            return instanceOf(randomizerClass);
        }
        throw new NoRandomizerFoundException(type.getName());
    }

    private static <T> T instanceOf(Class<T> t) {
        try {
            return t.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
