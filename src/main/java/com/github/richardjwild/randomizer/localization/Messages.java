package com.github.richardjwild.randomizer.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static final String UNEXPECTED_PATTERN_END_WANTED_CHARACTER_DASH_CLOSESQUAREBRACE = "pattern.parser.endedunexpectedly.wantedcharacterdashclosesquarebrace";
    public static final String UNEXPECTED_PATTERN_END_WANTED_OPENCURLYBRACE = "pattern.parser.endedunexpectedly.wantedopencurlybrace";
    public static final String UNEXPECTED_PATTERN_END_WANTED_CLOSESQUAREBRACE = "pattern.parser.endedunexpectedly.wantedclosesquarebrace";
    public static final String UNEXPECTED_CHARACTER_WANTED_OPENCURLYBRACE = "pattern.parser.unexpectedcharacter.wantedopencurlybrace";
    public static final String UNEXPECTED_PATTERN_END_WANTED_CHARACTER = "pattern.parser.endedunexpectedly.wantedcharacter";
    public static final String TYPE_CANNOT_BE_NULL = "randomizer.type.null";
    public static final String UNEXPECTED_PATTERN_END_WANTED_NUMBER = "pattern.parser.endedunexpectedly.wantednumber";
    public static final String UNEXPECTED_PATTERN_END_WANTED_NUMBER_COMMA_CLOSECURLYBRACE = "pattern.parser.endedunexpectedly.wantednumbercommaclosecurlybrace";
    public static final String MISSING_VALUE_IN_LENGTH_DEFINITION = "pattern.parser.lengthrange.missingvalue";
    public static final String TOO_MANY_FIELDS_IN_LENGTH_DEFINITION = "pattern.parser.lengthrange.toomanyfields";
    public static final String INVALID_NUMBER_IN_LENGTH_DEFINITION = "pattern.parser.lengthrange.notanumber";
    public static final String NO_RANDOMIZER_FOUND_FOR_TYPE = "randomizer.type.notsupported";
    public static final String LENGTH_MAXLENGTH_OR_PATTERN_MUST_BE_SPECIFIED = "randomizer.string.validation.lengthmaxlengthorpattern";
    public static final String LENGTH_AND_MAXLENGTH_CANNOT_BE_SPECIFIED_SIMULTANEOUSLY = "randomizer.string.validation.lengthandmaxlength";
    public static final String LENGTH_AND_MINLENGTH_CANNOT_BE_SPECIFIED_SIMULTANEOUSLY = "randomizer.string.validation.lengthandminlength";
    public static final String MAXLENGTH_MUST_BE_GREATER_THAN_ZERO = "randomizer.string.validation.maxlengthgreaterzero";
    public static final String MINLENGTH_MUST_BE_GREATER_THAN_ZERO = "randomizer.string.validation.minlengthgreaterzero";
    public static final String LENGTH_MUST_BE_GREATER_THAN_ZERO = "randomizer.string.validation.lengthgreaterzero";
    public static final String MINLENGTH_MUST_BE_LESS_THAN_MAXLENGTH = "randomizer.string.validation.minlengthlessthanmaxlength";
    public static final String MINCHAR_MUST_BE_LESS_THAN_MAXCHAR = "randomizer.string.validation.mincharlessthanmaxchar";
    public static final String PATTERN_AND_ANY_OTHER_CONSTRAINT = "randomizer.string.validation.patternandanyotherconstraint";

    public static String getMessage(String messageKey, String... argumentValues) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        String s = messages.getString(messageKey);
        return insertArguments(s, argumentValues);
    }

    private static String insertArguments(String message, String[] argumentValues) {
        int i = 0;
        for (String argumentValue: argumentValues)
            message = message.replace("{" + i++ + "}", argumentValue);
        return message;
    }
}
