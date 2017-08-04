package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.richardjwild.randomizer.localization.Messages.*;
import static com.github.richardjwild.randomizer.streams.Characters.from;

public class DefineCharacterRangeState extends ParserState {

    private final List<Character> permittedCharacters = new ArrayList<>();

    public DefineCharacterRangeState(StringPatternParser parser, StringPatternBuilder builder) {
        super(parser, builder);
    }

    @Override
    public ParserState handle(char c) {
        ParserState nextState = this;
        switch (c) {
            case '\\':
                char nextChar = parser.lookAhead(1, UNEXPECTED_PATTERN_END_WANTED_CHARACTER);
                parser.skip(1);
                addPermittedCharacterOrDefineRange(nextChar);
                break;
            case ']':
                nextState = moveToDefineRangeLengthState();
                break;
            default:
                addPermittedCharacterOrDefineRange(c);
        }
        return nextState;
    }

    private void addPermittedCharacterOrDefineRange(char nextChar) {
        if ('-' == parser.lookAhead(1, UNEXPECTED_PATTERN_END_WANTED_CHARACTER_DASH_CLOSESQUAREBRACE))
            addRangeOfPermittedCharacters(nextChar);
        else
            permittedCharacters.add(nextChar);
    }

    private ParserState moveToDefineRangeLengthState() {
        checkNextCharacterIsCorrectForLengthDefinition();
        parser.skip(1);
        return new DefineRangeLengthState(parser, builder, permittedCharacters);
    }

    private void checkNextCharacterIsCorrectForLengthDefinition() {
        if ('{' != parser.lookAhead(1, UNEXPECTED_PATTERN_END_WANTED_OPENCURLYBRACE))
            throw new StringPatternParserException(getMessage(UNEXPECTED_CHARACTER_WANTED_OPENCURLYBRACE));
    }

    private void addRangeOfPermittedCharacters(char minChar) {
        char maxChar = getMaxChar();
        allCharsBetween(minChar, maxChar).forEach(permittedCharacters::add);
    }

    private char getMaxChar() {
        char maxChar = parser.lookAhead(2, UNEXPECTED_PATTERN_END_WANTED_CHARACTER);
        parser.skip(2);
        if (maxChar == '\\') {
            maxChar = parser.lookAhead(1, UNEXPECTED_PATTERN_END_WANTED_CHARACTER);
            parser.skip(1);
        }
        return maxChar;
    }

    private Stream<Character> allCharsBetween(char minChar, char maxChar) {
        if (minChar <= maxChar)
            return from(minChar).limit(maxChar - minChar);
        return from(maxChar).limit(minChar - maxChar);
    }

    @Override
    public void patternEnded() {
        throw new StringPatternParserException(getMessage(UNEXPECTED_PATTERN_END_WANTED_CLOSESQUAREBRACE));
    }
}
