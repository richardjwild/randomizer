package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.exception.NoRandomizerFoundException;
import com.github.richardjwild.randomizer.types.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.*;

class RandomizerFactory {

    private Map<Class<?>, Class<? extends Randomizer>> randomizerClasses = new HashMap<>();

    RandomizerFactory() {
        add(String.class, StringRandomizer.class);
        add(Date.class, DateRandomizer.class);
        add(Integer.class, IntegerRandomizer.class);
        add(Long.class, LongRandomizer.class);
        add(Double.class, DoubleRandomizer.class);
        add(Float.class, FloatRandomizer.class);
        add(Character.class, CharacterRandomizer.class);
        add(Boolean.class, BooleanRandomizer.class);
        add(BigDecimal.class, BigDecimalRandomizer.class);
    }

    private void add(Class<?> type, Class<? extends Randomizer> randomizerClassForType) {
        randomizerClasses.put(type, randomizerClassForType);
    }

    <T> Randomizer<T> create(Class<T> type) {
        return ofNullable(randomizerClasses.get(type))
                .map(c -> (Randomizer<T>) instanceOf(c))
                .orElseThrow(() -> new NoRandomizerFoundException(type.getName()));
    }

    private <T> T instanceOf(Class<T> t) {
        try {
            return t.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
