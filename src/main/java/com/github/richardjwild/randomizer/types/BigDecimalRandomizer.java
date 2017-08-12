package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Creates randomized BigDecimal values suitable for use as test data in automated tests.<p>
 * Usage: <code>BigDecimal randomValue = Randomizer.forType(BigDecimal.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code>, <code>min</code>, and <code>scale</code>. If any other constraint
 * method is called on this class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class BigDecimalRandomizer extends Randomizer<BigDecimal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BigDecimalRandomizer.class);

    private BigDecimal maximum = new BigDecimal(Long.MAX_VALUE);
    private BigDecimal minimum = new BigDecimal(Long.MIN_VALUE);
    private int scale = 0;

    /**
     * Gets the generated random BigDecimal value.
     * @return The generated random value.
     */
    @Override
    public BigDecimal value() {
        Double range = maximum.subtract(minimum).doubleValue();
        Double randomDouble = random.nextDouble() * range;
        BigDecimal value = BigDecimal.valueOf(randomDouble).add(minimum).setScale(scale, RoundingMode.HALF_UP);
        LOGGER.info("Random BigDecimal: " + value);
        return value;
    }

    /**
     * Sets the maximum boundary for the generated BigDecimal value. The maximum is inclusive, i.e. the generated value
     * may be less than or equal to the maximum boundary.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public BigDecimalRandomizer max(BigDecimal maxValue) {
        this.maximum = maxValue;
        return this;
    }

    /**
     * Sets the minimum boundary for the generated BigDecimal value. The minimum is inclusive, i.e. the generated value
     * may be greater than or equal to the minimum boundary.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public BigDecimalRandomizer min(BigDecimal minValue) {
        this.minimum = minValue;
        return this;
    }

    /**
     * Sets the required scale for the generated BigDecimal value.
     * @param scale The required scale for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public BigDecimalRandomizer scale(int scale) {
        this.scale = scale;
        return this;
    }
}
