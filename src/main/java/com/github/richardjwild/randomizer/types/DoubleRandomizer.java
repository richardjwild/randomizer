package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Double values suitable for use as test data in automated tests.<p>
 * Usage: <code>Double randomValue = Randomizer.forType(Double.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class DoubleRandomizer extends Randomizer<Double> {

    private Double maxValue = Double.MAX_VALUE;
    private Double minValue = Double.MAX_VALUE * -1.0D;

    @Override
    public Double value() {
        return (random.nextDouble() * (maxValue - minValue)) + minValue;
    }

    @Override
    public Randomizer<Double> max(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    @Override
    public Randomizer<Double> min(Double minValue) {
        this.minValue = minValue;
        return this;
    }
}
