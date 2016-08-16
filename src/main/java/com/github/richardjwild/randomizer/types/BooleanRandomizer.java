package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

public class BooleanRandomizer extends Randomizer<Boolean> {

    @Override
    public Boolean value() {
        return (random.nextInt() & 1) == 1;
    }
}
