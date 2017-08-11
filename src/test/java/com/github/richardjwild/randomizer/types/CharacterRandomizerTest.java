package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CharacterRandomizerTest {

    @Test
    public void randomValue() {
        Character value = Randomizer.forType(Character.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void randomValueGreaterThanMinimum() {
        char minimum = 'A';
        char value = Randomizer.forType(Character.class).min(minimum).value();
        assertThat(value).isGreaterThanOrEqualTo(minimum);
    }

    @Test
    public void randomValueSmallerThanMaximum() {
        char maximum = 'z';
        char value = Randomizer.forType(Character.class).max(maximum).value();
        assertThat(value).isLessThanOrEqualTo(maximum);
    }

    @Test
    public void randomValueBetweenTwoBounds() {
        char minimum = 'A';
        char maximum = 'z';
        char value = Randomizer.forType(Character.class).min(minimum).max(maximum).value();
        assertThat(value).isGreaterThanOrEqualTo(minimum);
        assertThat(value).isLessThanOrEqualTo(maximum);
    }
}
