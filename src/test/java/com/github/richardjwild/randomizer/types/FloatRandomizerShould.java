package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class FloatRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_a_float() {
        Float actual = Randomizer.forType(Float.class).value();
        assertThat(actual).isNotNull();
    }

    @Test
    public void return_a_float_less_than_or_equal_to_maximum() {
        float maxValue = 100.0f;
        float value = Randomizer.forType(Float.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_float_greater_than_or_equal_to_minimum() {
        float minValue = 2000000000.0f;
        float value = Randomizer.forType(Float.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void return_a_float_between_min_and_max() {
        float minValue = 5.0f;
        float maxValue = 10.0f;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void return_a_negative_float() {
        float minValue = Float.MAX_VALUE * -1.0f;
        float maxValue = -1;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void not_support_length() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Float.class).length(anyLength);
    }
}
