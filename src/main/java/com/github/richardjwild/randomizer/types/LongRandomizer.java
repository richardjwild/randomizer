package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Long values suitable for use as test data in automated tests.<p>
 * Usage: <code>Long randomValue = Randomizer.forType(Long.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class LongRandomizer extends Randomizer<Long> {

    private long maxValue = Long.MAX_VALUE;
    private Long minValue = Long.MIN_VALUE;

    @Override
    public Long value() {
        double maximum = (double) maxValue;
        double minimum = (double) minValue;
        return (long) (random.nextDouble() * (maximum - minimum) + minimum);
    }

    @Override
    public Randomizer<Long> max(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    @Override
    public Randomizer<Long> min(Long minValue) {
        this.minValue = minValue;
        return this;
    }
}
