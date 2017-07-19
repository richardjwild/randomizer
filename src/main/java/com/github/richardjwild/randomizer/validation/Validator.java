package com.github.richardjwild.randomizer.validation;

import com.github.richardjwild.randomizer.localization.Messages;

import java.util.function.Supplier;

public class Validator {

    public static void check(Supplier<Boolean> failureCondition, String messageKey) {
        if (failureCondition.get())
            throw new IllegalArgumentException(Messages.getMessage(messageKey));
    }
}
