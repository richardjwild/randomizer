package com.github.richardjwild.randomizer.validation;

import com.github.richardjwild.randomizer.localization.Messages;

public class NoRandomizerFoundException extends RuntimeException {

    public NoRandomizerFoundException(String className) {
        super(Messages.getMessage("randomizer.type.notsupported", className));
    }
}
