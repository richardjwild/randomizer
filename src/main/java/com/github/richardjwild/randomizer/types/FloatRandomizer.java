package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

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
