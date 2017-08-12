package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates randomized Integer values suitable for use as test data in automated tests.<p>
 * Usage: <code>Integer randomValue = Randomizer.forType(Integer.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class IntegerRandomizer extends Randomizer<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerRandomizer.class);

    private Integer maxValue = Integer.MAX_VALUE;
    private Integer minValue = 0;

    /**
     * Gets the generated random integer value, within any specified constraints.
     * @return The generated random value.
     */
    @Override
    public Integer value() {
        int value = random.nextInt(maxValue - minValue) + minValue;
        LOGGER.info("Random Integer: " + value);
        return value;
    }

    /**
     * Sets the maximum boundary for the generated random integer. The maximum is inclusive, i.e. the generated value
     * may be less than or equal to the maximum boundary.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Integer> max(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    /**
     * Sets the minimum boundary for the generated random integer. The minimum is inclusive, i.e. the generated value
     * may be greater than or equal to the minimum boundary.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Integer> min(Integer minValue) {
        this.minValue = minValue;
        return this;
    }
}
