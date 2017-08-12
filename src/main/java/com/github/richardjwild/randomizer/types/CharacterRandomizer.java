package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates randomized Character values suitable for use as test data in automated tests.<p>
 * Usage: <code>Character randomValue = Randomizer.forType(Character.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class CharacterRandomizer extends Randomizer<Character> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterRandomizer.class);

    private Character minValue = Character.MIN_VALUE;
    private Character maxValue = Character.MAX_VALUE;

    /**
     * Gets the generated random character value for the specified type, within any specified constraints.
     * @return The generated random character value.
     */
    @Override
    public Character value() {
        int maximum = (int) maxValue;
        int minimum = (int) minValue;
        char value = (char) (random.nextInt(maximum - minimum) + minimum);
        LOGGER.info("Random Character: " + value);
        return value;
    }

    /**
     * Sets the minimum boundary for the generated random value. The minimum is inclusive, i.e. the generated value may
     * be greater than or equal to the minimum boundary. The default is {@link Character#MIN_VALUE}.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Character> min(Character minValue) {
        this.minValue = minValue;
        return this;
    }

    /**
     * Sets the maximum boundary for the generated random value. The maximum is inclusive, i.e. the generated value may
     * be less than or equal to the maximum boundary. The default is {@link Character#MAX_VALUE}.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Character> max(Character maxValue) {
        this.maxValue = maxValue;
        return this;
    }
}
