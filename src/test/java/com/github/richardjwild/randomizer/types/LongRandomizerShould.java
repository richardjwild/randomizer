package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LongRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_a_long() {
        Long value = Randomizer.forType(Long.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void return_a_long_less_than_or_equal_to_maximum() {
        long maxValue = 100;
        long value = Randomizer.forType(Long.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_long_greater_than_or_equal_to_minimum() {
        long minValue = 2000000000;
        long value = Randomizer.forType(Long.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void return_a_long_between_min_and_max() {
        long minValue = 5;
        long maxValue = 10;
        long value = Randomizer.forType(Long.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_negative_long() {
        long minValue = Integer.MIN_VALUE;
        long maxValue = -1;
        long value = Randomizer.forType(Long.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void not_support_length() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Long.class).length(anyLength);
    }
}
