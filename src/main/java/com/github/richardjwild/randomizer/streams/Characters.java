package com.github.richardjwild.randomizer.streams;

import java.util.stream.Stream;

public class Characters {

    public static Stream<Character> from(char from) {
        return new Characters(from).getStream();
    }

    private char value;

    private Characters(char from) {
        value = from;
    }

    private Stream<Character> getStream() {
        return Stream.generate(() -> value++);
    }
}