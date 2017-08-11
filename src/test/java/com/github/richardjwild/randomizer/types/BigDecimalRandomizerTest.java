package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class BigDecimalRandomizerTest {

    @Test
    public void randomBigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void randomBigDecimalBetweenOneAndTen() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).max(BigDecimal.TEN).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(1));
        assertThat(value).isLessThanOrEqualTo(bd(10));
    }

    @Test
    public void randomNegativeBigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).max(new BigDecimal(-1)).value();
        assertThat(value).isLessThanOrEqualTo(bd(-1));
    }

    @Test
    public void randomPositiveBigDecimalWithNoUpperBound() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(1));
    }

    @Test
    public void randomNegativeBigDecimalBetweenBounds() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(new BigDecimal(-10)).max(new BigDecimal(-1)).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(-10));
        assertThat(value).isLessThanOrEqualTo(bd(-1));
    }

    @Test
    public void randomBigDecimalWithScale() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ZERO).max(BigDecimal.ONE).scale(3).value();
        assertThat(value).isGreaterThanOrEqualTo(bd(0));
        assertThat(value).isLessThanOrEqualTo(bd(1));
        assertThat(value.scale()).isEqualTo(3);
    }

    private BigDecimal bd(int value) {
        return new BigDecimal(value);
    }
}
