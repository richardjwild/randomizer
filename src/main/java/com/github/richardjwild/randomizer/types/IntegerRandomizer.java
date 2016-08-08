package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

import java.util.Random;

public class IntegerRandomizer extends Randomizer<Integer> {

    private final Random random = new Random();
    private Integer maxValue = Integer.MAX_VALUE;
    private Integer minValue = 0;

    @Override
    public Integer randomValue() {
        return random.nextInt(maxValue - minValue) + minValue;
    }

    @Override
    public Randomizer<Integer> maximum(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    @Override
    public Randomizer<Integer> minimum(Integer minValue) {
        this.minValue = minValue;
        return this;
    }
}
