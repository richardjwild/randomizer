package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class DateRandomizerTest {

    private static final long ONE_DAY = 1000 * 60 * 60 * 24;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomDate() {
        Date value = Randomizer.forType(Date.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void randomDateInTheFuture() {
        Date minimum = currentTimePlusOffset(ONE_DAY);
        Date value = Randomizer.forType(Date.class).min(minimum).value();
        assertThat(value.compareTo(minimum)).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void randomDateInThePast() {
        Date maximum = currentTimePlusOffset(-ONE_DAY);
        Date value = Randomizer.forType(Date.class).max(maximum).value();
        assertThat(value.compareTo(maximum)).isLessThanOrEqualTo(0);
    }

    @Test
    public void randomDateBetweenTwoBounds() {
        Date minimum = currentTimePlusOffset(-ONE_DAY);
        Date maximum = currentTimePlusOffset(ONE_DAY);
        Date value = Randomizer.forType(Date.class).min(minimum).max(maximum).value();
        assertThat(value.compareTo(minimum)).isGreaterThanOrEqualTo(0);
        assertThat(value.compareTo(maximum)).isLessThanOrEqualTo(0);
    }

    @Test
    public void lengthNotSupportedForDateRandomizer() {
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Date.class).length(1);
    }

    private Date currentTimePlusOffset(long offset) {
        Date date = new Date();
        date.setTime(date.getTime() + offset);
        return date;
    }
}
