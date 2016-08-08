package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.types.IntegerRandomizer;
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

    @Test
    public void getIntRandomizer() {
        Object actual = Randomizer.forType(Integer.class);
        assertNotNull(actual);
        assertSame(IntegerRandomizer.class, actual.getClass());
    }
}
