package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DateRandomizerTest {

    private static final long ONE_DAY = 1000 * 60 * 60 * 24;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomDate() {
        Date value = Randomizer.forType(Date.class).value();
        assertNotNull(value);
    }

    @Test
    public void randomDateInTheFuture() {
        Date minimum = currentTimePlusOffset(ONE_DAY);
        Date value = Randomizer.forType(Date.class).min(minimum).value();
        assertTrue(value.compareTo(minimum) >= 0);
    }

    @Test
    public void randomDateInThePast() {
        Date maximum = currentTimePlusOffset(-ONE_DAY);
        Date value = Randomizer.forType(Date.class).max(maximum).value();
        assertTrue(value.compareTo(maximum) <= 0);
    }

    @Test
    public void randomDateBetweenTwoBounds() {
        Date minimum = currentTimePlusOffset(-ONE_DAY);
        Date maximum = currentTimePlusOffset(ONE_DAY);
        Date value = Randomizer.forType(Date.class).min(minimum).max(maximum).value();
        assertTrue(value.compareTo(minimum) >= 0);
        assertTrue(value.compareTo(maximum) <= 0);
    }

    @Test
    public void lengthNotSupportedForDateRandomizer() {
        int anyLength = 1;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Date.class).length(anyLength);
    }

    private Date currentTimePlusOffset(long offset) {
        Date date = new Date();
        date.setTime(date.getTime() + offset);
        return date;
    }
}
