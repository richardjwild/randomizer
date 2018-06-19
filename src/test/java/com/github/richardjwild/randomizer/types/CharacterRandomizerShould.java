package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CharacterRandomizerShould {

    @Test
    public void return_a_character() {
        Character value = Randomizer.forType(Character.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void return_a_character_greater_than_or_equal_to_minimum() {
        char minimum = 'A';
        char value = Randomizer.forType(Character.class).min(minimum).value();
        assertThat(value).isGreaterThanOrEqualTo(minimum);
    }

    @Test
    public void return_a_character_less_than_or_equal_to_maximum() {
        char maximum = 'z';
        char value = Randomizer.forType(Character.class).max(maximum).value();
        assertThat(value).isLessThanOrEqualTo(maximum);
    }

    @Test
    public void return_a_character_between_minimum_and_maximum() {
        char minimum = 'A';
        char maximum = 'z';
        char value = Randomizer.forType(Character.class).min(minimum).max(maximum).value();
        assertThat(value).isGreaterThanOrEqualTo(minimum);
        assertThat(value).isLessThanOrEqualTo(maximum);
    }
}
