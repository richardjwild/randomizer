package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class DoubleRandomizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void randomValue() {
        Double actual = Randomizer.forType(Double.class).value();
        assertThat(actual).isNotNull();
    }

    @Test
    public void randomValueMaximumOneHundred() {
        double maxValue = 100.0D;
        double value = Randomizer.forType(Double.class).max(maxValue).value();
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void randomValueMinimumTwoBillion() {
        double minValue = 2000000000.0D;
        double value = Randomizer.forType(Double.class).min(minValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        double minValue = 5.0D;
        double maxValue = 10.0D;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void randomNegativeValue() {
        double minValue = Double.MAX_VALUE * -1.0D;
        double maxValue = -1;
        double value = Randomizer.forType(Double.class).min(minValue).max(maxValue).value();
        assertThat(value).isGreaterThanOrEqualTo(minValue);
        assertThat(value).isLessThanOrEqualTo(maxValue);
    }

    @Test
    public void lengthMethodNotSupportedForDoubleRandomizer() {
        int anyLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Double.class).length(anyLength);
    }
}
