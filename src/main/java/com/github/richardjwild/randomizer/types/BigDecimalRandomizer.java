package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

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

    private BigDecimal maximum = new BigDecimal(Long.MAX_VALUE);
    private BigDecimal minimum = new BigDecimal(Long.MIN_VALUE);
    private int scale = 0;

    @Override
    public BigDecimal value() {
        Double range = maximum.subtract(minimum).doubleValue();
        Double randomDouble = random.nextDouble() * range;
        return BigDecimal.valueOf(randomDouble).add(minimum).setScale(scale, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimalRandomizer max(BigDecimal maximum) {
        this.maximum = maximum;
        return this;
    }

    @Override
    public BigDecimalRandomizer min(BigDecimal minimum) {
        this.minimum = minimum;
        return this;
    }

    @Override
    public BigDecimalRandomizer scale(int scale) {
        this.scale = scale;
        return this;
    }
}
