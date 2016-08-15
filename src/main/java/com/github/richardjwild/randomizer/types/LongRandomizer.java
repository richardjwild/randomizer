package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

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
