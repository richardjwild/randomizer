package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

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
