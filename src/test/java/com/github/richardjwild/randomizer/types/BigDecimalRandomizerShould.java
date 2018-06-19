package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class BigDecimalRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_a_BigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void return_a_BigDecimal_between_1_and_10() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).max(BigDecimal.TEN).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(1));
        assertThat(value).isLessThanOrEqualTo(bd(10));
    }

    @Test
    public void return_a_negative_BigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).max(new BigDecimal(-1)).value();
        assertThat(value).isLessThanOrEqualTo(bd(-1));
    }

    @Test
    public void return_a_positive_BigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(1));
    }

    @Test
    public void return_a_negative_BigDecimal_in_specified_range() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(new BigDecimal(-10)).max(new BigDecimal(-1)).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(-10));
        assertThat(value).isLessThanOrEqualTo(bd(-1));
    }

    @Test
    public void return_BigDecimal_with_specified_scale() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ZERO).max(BigDecimal.ONE).scale(3).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(0));
        assertThat(value).isLessThanOrEqualTo(bd(1));
        assertThat(value.scale()).isEqualTo(3);
    }

    private BigDecimal bd(int value) {
        return new BigDecimal(value);
    }
}
