package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class FloatRandomizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomValue() {
        Float actual = Randomizer.forType(Float.class).value();
        assertThat(actual).isNotNull();
    }

    @Test
    public void randomValueMaximumOneHundred() {
        float maxValue = 100.0f;
        float value = Randomizer.forType(Float.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        float minValue = 2000000000.0f;
        float value = Randomizer.forType(Float.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        float minValue = 5.0f;
        float maxValue = 10.0f;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void randomNegativeValue() {
        float minValue = Float.MAX_VALUE * -1.0f;
        float maxValue = -1;
        float value = Randomizer.forType(Float.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void lengthMethodNotSupportedForFloatRandomizer() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Float.class).length(anyLength);
    }
}
