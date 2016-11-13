package com.github.richardjwild.randomizer.exception;

public class ShouldNeverHappenException extends RuntimeException {

    public ShouldNeverHappenException(Exception cause) {
        super(cause);
    }
}
