package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;

public abstract class ParserState {

    protected final StringPatternParser parser;
    protected final StringPatternBuilder builder;

    public ParserState(StringPatternParser parser, StringPatternBuilder builder) {
        this.parser = parser;
        this.builder = builder;
    }

    public abstract ParserState handle(char c);

    public abstract void patternEnded();
}
