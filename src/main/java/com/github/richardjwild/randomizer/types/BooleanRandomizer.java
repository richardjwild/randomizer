package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Boolean values suitable for use as test data in automated tests.<p>
 * Usage: <code>Boolean randomValue = Randomizer.forType(Boolean.class).value();</code><p>
 * This class does not implement any constraints; if any constraint method is called on this class an
 * <code>UnsupportedOperationException</code> will be thrown.
 */
public class BooleanRandomizer extends Randomizer<Boolean> {

    @Override
    public Boolean value() {
        return (random.nextInt() & 1) == 1;
    }
}
