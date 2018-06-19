package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class IntegerRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_an_integer() {
        Integer value = Randomizer.forType(Integer.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void return_an_integer_less_than_or_equal_to_maximum() {
        int maxValue = 100;
        int value = Randomizer.forType(Integer.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_an_integer_greater_than_or_equal_to_minimum() {
        int minValue = 2000000000;
        int value = Randomizer.forType(Integer.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void return_an_integer_between_min_and_max() {
        int minValue = 5;
        int maxValue = 10;
        int value = Randomizer.forType(Integer.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_negative_integer() {
        int minValue = Integer.MIN_VALUE;
        int maxValue = -1;
        int value = Randomizer.forType(Integer.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void not_support_length() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Integer.class).length(anyLength);
    }
}