package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BigDecimalRandomizerTest {

    @Test
    public void randomBigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).value();
        assertNotNull(value);
    }

    @Test
    public void randomBigDecimalBetweenOneAndTen() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).max(BigDecimal.TEN).value();
        assertTrue(value.compareTo(bd(1)) >= 0);
        assertTrue(value.compareTo(bd(10)) <= 0);
    }

    @Test
    public void randomNegativeBigDecimal() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).max(new BigDecimal(-1)).value();
        assertTrue(value.compareTo(bd(-1)) <= 0);
    }

    @Test
    public void randomPositiveBigDecimalWithNoUpperBound() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ONE).value();
        assertTrue(value.compareTo(bd(1)) >= 0);
    }

    @Test
    public void randomNegativeBigDecimalBetweenBounds() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(new BigDecimal(-10)).max(new BigDecimal(-1)).value();
        assertTrue(value.compareTo(bd(-10)) >= 0);
        assertTrue(value.compareTo(bd(-1)) <= 0);
    }

    @Test
    public void randomBigDecimalWithScale() {
        BigDecimal value = Randomizer.forType(BigDecimal.class).min(BigDecimal.ZERO).max(BigDecimal.ONE).scale(3).value();
        assertTrue(value.compareTo(bd(0)) >= 0);
        assertTrue(value.compareTo(bd(1)) <= 0);
        assertEquals(3, value.scale());
    }

    private BigDecimal bd(int value) {
        return new BigDecimal(value);
    }
}
