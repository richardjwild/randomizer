package com.github.richardjwild.randomizer.types.pattern;

import com.github.richardjwild.randomizer.types.pattern.parserstate.DefineLiteralPatternState;
import com.github.richardjwild.randomizer.types.pattern.parserstate.ParserState;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class StringPatternParser {

    private int requestedCharacterIndex;

    public static Supplier<StringPatternParserException> exception(String messageKey) {
        return () -> new StringPatternParserException(messageKey);
    }

    private final char[] pattern;
    private int currentChar;

    public StringPatternParser(String pattern) {
        this.pattern = pattern.toCharArray();
    }

    public List<StringPatternElement> parseAndCreatePatternElements() {
        currentChar = 0;
        StringPatternBuilder builder = new StringPatternBuilder();
        ParserState state = initialState(builder);
        parseStringPattern(state);
        return builder.getElements();
    }

    private ParserState initialState(StringPatternBuilder builder) {
        return new DefineLiteralPatternState(this, builder);
    }

    private void parseStringPattern(ParserState state) {
        for (currentChar = 0; currentChar < pattern.length; currentChar++)
            state = state.handle(pattern[currentChar]);
        state.patternEnded();
    }

    public Optional<Character> lookAhead(int chars) {
        requestedCharacterIndex = currentChar + chars;
        if (pattern.length > requestedCharacterIndex)
            return of(pattern[currentChar + chars]);
        return empty();
    }

    public void skip(int chars) {
        currentChar += chars;
    }
}
