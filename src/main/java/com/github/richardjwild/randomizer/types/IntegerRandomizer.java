package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

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
