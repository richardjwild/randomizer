package com.github.richardjwild.randomizer.types.pattern;

import com.github.richardjwild.randomizer.types.pattern.parserstate.DefineLiteralPatternState;
import com.github.richardjwild.randomizer.types.pattern.parserstate.ParserState;

import java.util.List;

import static com.github.richardjwild.randomizer.localization.Messages.getMessage;

public class StringPatternParser {

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

    public char lookAhead(int chars, String errorMessageKey) {
        if (currentChar + chars >= pattern.length)
            throw new StringPatternParserException(getMessage(errorMessageKey));
        return pattern[currentChar + chars];
    }

    public void skip(int chars) {
        currentChar += chars;
    }
}
