package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.StringRandomizer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class RandomizerTest {

    @Test
    public void getStringRandomizer() {
        Object actual = Randomizer.forType(String.class);
        assertNotNull(actual);
        assertSame(StringRandomizer.class, actual.getClass());
    }
}
