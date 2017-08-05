package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.richardjwild.randomizer.localization.Messages.*;
import static com.github.richardjwild.randomizer.streams.Characters.from;
import static com.github.richardjwild.randomizer.types.pattern.StringPatternParser.exception;

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
                treatNextCharacterAsLiteral();
                break;
            case ']':
                if (nextCharacterDefinesLength())
                    parser.skip(1);
                else
                    throw new StringPatternParserException(UNEXPECTED_CHARACTER_WANTED_OPENCURLYBRACE);
                nextState = new DefineRangeLengthState(parser, builder, permittedCharacters);
                break;
            default:
                addPermittedCharacterOrDefineRange(c);
        }
        return nextState;
    }

    private void treatNextCharacterAsLiteral() {
        char nextChar = parser.lookAhead(1).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_CHARACTER));
        parser.skip(1);
        addPermittedCharacterOrDefineRange(nextChar);
    }

    private void addPermittedCharacterOrDefineRange(char nextChar) {
        if (nextCharacterDefinesRange())
            addRangeOfPermittedCharacters(nextChar);
        else
            permittedCharacters.add(nextChar);
    }

    private boolean nextCharacterDefinesRange() {
        return '-' == parser.lookAhead(1).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_CHARACTER_DASH_CLOSESQUAREBRACE));
    }

    private void addRangeOfPermittedCharacters(char minChar) {
        char maxChar = getMaxChar();
        allCharsBetween(minChar, maxChar).forEach(permittedCharacters::add);
    }

    private char getMaxChar() {
        char maxChar = parser.lookAhead(2).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_CHARACTER));
        parser.skip(2);
        if (maxChar == '\\') {
            maxChar = parser.lookAhead(1).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_CHARACTER));
            parser.skip(1);
        }
        return maxChar;
    }

    private Stream<Character> allCharsBetween(char minChar, char maxChar) {
        if (minChar <= maxChar)
            return from(minChar).limit(maxChar - minChar);
        return from(maxChar).limit(minChar - maxChar);
    }

    private boolean nextCharacterDefinesLength() {
        return '{' == parser.lookAhead(1).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_OPENCURLYBRACE));
    }

    @Override
    public void patternEnded() {
        throw new StringPatternParserException(UNEXPECTED_PATTERN_END_WANTED_CLOSESQUAREBRACE);
    }
}
