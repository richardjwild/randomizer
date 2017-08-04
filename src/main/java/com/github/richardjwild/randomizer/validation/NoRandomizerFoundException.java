package com.github.richardjwild.randomizer.validation;

import com.github.richardjwild.randomizer.localization.Messages;

import static com.github.richardjwild.randomizer.localization.Messages.NO_RANDOMIZER_FOUND_FOR_TYPE;

public class NoRandomizerFoundException extends RuntimeException {

    public NoRandomizerFoundException(String className) {
        super(Messages.getMessage(NO_RANDOMIZER_FOUND_FOR_TYPE, className));
    }
}
