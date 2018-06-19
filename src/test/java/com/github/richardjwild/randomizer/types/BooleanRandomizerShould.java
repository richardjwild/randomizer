package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class BooleanRandomizerShould {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_a_boolean() {
        Boolean value = Randomizer.forType(Boolean.class).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void not_support_length() {
        int someLength = 0;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).length(someLength);
    }

    @Test
    public void not_support_max() {
        boolean someMax = false;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).max(someMax);
    }

    @Test
    public void not_support_min() {
        boolean someMin = false;
        thrown.expect(UnsupportedOperationException.class);
        Randomizer.forType(Boolean.class).min(someMin);
    }
}
