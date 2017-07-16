package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Float values suitable for use as test data in automated tests.<p>
 * Usage: <code>Float randomValue = Randomizer.forType(Float.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class FloatRandomizer extends Randomizer<Float> {

    private Float maxValue = Float.MAX_VALUE;
    private Float minValue = Float.MAX_VALUE * -1.0f;

    @Override
    public Float value() {
        return (random.nextFloat() * (maxValue - minValue)) + minValue;
    }

    @Override
    public Randomizer<Float> max(Float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    @Override
    public Randomizer<Float> min(Float minValue) {
        this.minValue = minValue;
        return this;
    }
}
