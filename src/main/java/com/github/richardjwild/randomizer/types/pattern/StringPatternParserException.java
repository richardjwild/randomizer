package com.github.richardjwild.randomizer.types.pattern;

import com.github.richardjwild.randomizer.localization.Messages;

public class StringPatternParserException extends RuntimeException {

    public StringPatternParserException(String messageKey) {
        super(Messages.getMessage(messageKey));
    }

    public StringPatternParserException(String messageKey, String... arguments) {
        super(Messages.getMessage(messageKey, arguments));
    }
}
