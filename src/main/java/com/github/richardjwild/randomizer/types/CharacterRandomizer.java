package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

public class CharacterRandomizer extends Randomizer<Character> {

    private Character minValue = Character.MIN_VALUE;
    private Character maxValue = Character.MAX_VALUE;

    @Override
    public Character value() {
        int maximum = (int) maxValue;
        int minimum = (int) minValue;
        return (char) (random.nextInt(maximum - minimum) + minimum);
    }

    @Override
    public Randomizer<Character> min(Character minValue) {
        this.minValue = minValue;
        return this;
    }

    @Override
    public Randomizer<Character> max(Character maxValue) {
        this.maxValue = maxValue;
        return this;
    }
}
