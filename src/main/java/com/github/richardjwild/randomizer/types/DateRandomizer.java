package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Creates randomized Date values suitable for use as test data in automated tests.<p>
 * Usage: <code>Date randomValue = Randomizer.forType(Date.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class DateRandomizer extends Randomizer<Date> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateRandomizer.class);

    private long maximum = Long.MAX_VALUE;
    private long minimum = Long.MIN_VALUE;

    /**
     * Gets the generated random date value, within any specified constraints.
     * @return The generated random date.
     */
    @Override
    public Date value() {
        double max = (double) maximum;
        double min = (double) minimum;
        long randomTime = (long) (random.nextDouble() * (max - min) + min);
        Date value = new Date();
        value.setTime(randomTime);
        LOGGER.info("Random Date: " + value);
        return value;
    }

    /**
     * Sets the minimum boundary for the generated random date. The minimum is inclusive, i.e. the generated value may
     * be greater than or equal to the minimum boundary.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public DateRandomizer min(Date minValue) {
        this.minimum = minValue.getTime();
        return this;
    }

    /**
     * Sets the maximum boundary for the generated random date. The maximum is inclusive, i.e. the generated value may
     * be less than or equal to the maximum boundary.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public DateRandomizer max(Date maxValue) {
        this.maximum = maxValue.getTime();
        return this;
    }
}
