package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class DoubleRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_a_double() {
        Double actual = Randomizer.forType(Double.class).value();
        assertThat(actual).isNotNull();
    }

    @Test
    public void return_a_double_less_than_or_equal_to_maximum() {
        double maxValue = 100.0D;
        double value = Randomizer.forType(Double.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_double_greater_than_or_equal_to_minimum() {
        double minValue = 2000000000.0D;
        double value = Randomizer.forType(Double.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void return_a_double_between_min_and_max() {
        double minValue = 5.0D;
        double maxValue = 10.0D;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_negative_double() {
        double minValue = Double.MAX_VALUE * -1.0D;
        double maxValue = -1;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void not_support_length() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Double.class).length(anyLength);
    }
}
