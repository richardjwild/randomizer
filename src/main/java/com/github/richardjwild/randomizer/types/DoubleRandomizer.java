package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates randomized Double values suitable for use as test data in automated tests.<p>
 * Usage: <code>Double randomValue = Randomizer.forType(Double.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class DoubleRandomizer extends Randomizer<Double> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleRandomizer.class);

    private Double maxValue = Double.MAX_VALUE;
    private Double minValue = Double.MAX_VALUE * -1.0D;

    /**
     * Gets the generated random double value, within any specified constraints.
     * @return The generated random double.
     */
    @Override
    public Double value() {
        double value = (random.nextDouble() * (maxValue - minValue)) + minValue;
        LOGGER.info("Random Double: " + value);
        return value;
    }

    /**
     * Sets the maximum boundary for the generated random double. The maximum is inclusive, i.e. the generated value may
     * be less than or equal to the maximum boundary.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Double> max(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    /**
     * Sets the minimum boundary for the generated random double. The minimum is inclusive, i.e. the generated value may
     * be greater than or equal to the minimum boundary.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public Randomizer<Double> min(Double minValue) {
        this.minValue = minValue;
        return this;
    }
}
