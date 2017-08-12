package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates randomized Boolean values suitable for use as test data in automated tests.<p>
 * Usage: <code>Boolean randomValue = Randomizer.forType(Boolean.class).value();</code><p>
 * This class does not implement any constraints; if any constraint method is called on this class an
 * <code>UnsupportedOperationException</code> will be thrown.
 */
public class BooleanRandomizer extends Randomizer<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanRandomizer.class);

    /**
     * Gets the generated random boolean value for the specified type.
     * @return The generated random boolean value.
     */
    @Override
    public Boolean value() {
        boolean value = (random.nextInt() & 1) == 1;
        LOGGER.info("Random Boolean: " + value);
        return value;
    }
}
