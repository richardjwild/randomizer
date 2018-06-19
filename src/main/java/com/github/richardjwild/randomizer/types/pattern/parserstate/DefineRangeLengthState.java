package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;

import java.util.List;

import static com.github.richardjwild.randomizer.localization.Messages.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class DefineRangeLengthState extends ParserState {

    private final List<Character> permittedCharacters;
    private final StringBuilder lengthDefinition = new StringBuilder();

    public DefineRangeLengthState(
            StringPatternParser parser,
            StringPatternBuilder builder,
            List<Character> permittedCharacters)
    {
        super(parser, builder);
        this.permittedCharacters = permittedCharacters;
    }

    @Override
    public ParserState handleCharacter(char c) {
        if (c == '}') {
            addStringPatternElement(lengthDefinition.toString());
            return new DefineLiteralPatternState(parser, builder);
        } else {
            lengthDefinition.append(c);
            return this;
        }
    }

    @Override
    public void patternEnded() {
        throw new StringPatternParserException((lengthDefinition.length() == 0)
                ? UNEXPECTED_PATTERN_END_WANTED_NUMBER
                : UNEXPECTED_PATTERN_END_WANTED_NUMBER_COMMA_CLOSECURLYBRACE);
    }

    private void addStringPatternElement(String lengthDefinition) {
        String[] parts = lengthDefinition.split(",");
        if (parts.length == 1) {
            if (lengthDefinition.endsWith(","))
                throw new StringPatternParserException(MISSING_VALUE_IN_LENGTH_DEFINITION);
            int length = readInt(parts[0]);
            builder.addFixedLengthElement(length, permittedCharacters);
        } else if (parts.length == 2) {
            int minLength = readInt(parts[0]);
            int maxLength = readInt(parts[1]);
            builder.addRandomLengthElement(minLength, maxLength, permittedCharacters);
        } else {
            throw new StringPatternParserException(
                    TOO_MANY_FIELDS_IN_LENGTH_DEFINITION,
                    format("%d", parts.length));
        }
    }

    private int readInt(String s) {
        if (s.isEmpty())
            throw new StringPatternParserException(MISSING_VALUE_IN_LENGTH_DEFINITION);
        else
            return tryParseInt(s);
    }

    private int tryParseInt(String s) {
        try {
            return parseInt(s);
        } catch (NumberFormatException e) {
            throw new StringPatternParserException(INVALID_NUMBER_IN_LENGTH_DEFINITION);
        }
    }
}
