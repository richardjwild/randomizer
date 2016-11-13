package com.github.richardjwild.randomizer.exception;

public class NoRandomizerFoundException extends RuntimeException {

    public NoRandomizerFoundException(String className) {
        super(String.format("No randomizer found for class: %s", className));
    }
}
