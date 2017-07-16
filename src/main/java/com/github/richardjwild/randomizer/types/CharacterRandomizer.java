package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

/**
 * Creates randomized Character values suitable for use as test data in automated tests.<p>
 * Usage: <code>Character randomValue = Randomizer.forType(Character.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
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
