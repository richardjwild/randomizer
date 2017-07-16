package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Integer values suitable for use as test data in automated tests.<p>
 * Usage: <code>Integer randomValue = Randomizer.forType(Integer.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class IntegerRandomizer extends Randomizer<Integer> {

    private Integer maxValue = Integer.MAX_VALUE;
    private Integer minValue = 0;

    @Override
    public Integer value() {
        return random.nextInt(maxValue - minValue) + minValue;
    }

    @Override
    public Randomizer<Integer> max(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    @Override
    public Randomizer<Integer> min(Integer minValue) {
        this.minValue = minValue;
        return this;
    }
}
