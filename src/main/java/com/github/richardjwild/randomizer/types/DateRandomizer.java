package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

import java.util.Date;

/**
 * Creates randomized Date values suitable for use as test data in automated tests.<p>
 * Usage: <code>Date randomValue = Randomizer.forType(Date.class).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code> and <code>min</code>. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.
 */
public class DateRandomizer extends Randomizer<Date> {

    private long maximum = Long.MAX_VALUE;
    private long minimum = Long.MIN_VALUE;

    @Override
    public Date value() {
        double max = (double) maximum;
        double min = (double) minimum;
        long randomTime = (long) (random.nextDouble() * (max - min) + min);
        Date randomDate = new Date();
        randomDate.setTime(randomTime);
        return randomDate;
    }

    @Override
    public DateRandomizer min(Date minimum) {
        this.minimum = minimum.getTime();
        return this;
    }

    @Override
    public DateRandomizer max(Date maximum) {
        this.maximum = maximum.getTime();
        return this;
    }
}
