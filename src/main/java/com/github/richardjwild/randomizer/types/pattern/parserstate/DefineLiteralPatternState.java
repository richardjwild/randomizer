package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;

import static com.github.richardjwild.randomizer.localization.Messages.UNEXPECTED_PATTERN_END_WANTED_CHARACTER;
import static com.github.richardjwild.randomizer.types.pattern.StringPatternParser.exception;

public class DefineLiteralPatternState extends ParserState {

    public DefineLiteralPatternState(StringPatternParser parser, StringPatternBuilder builder) {
        super(parser, builder);
    }

    @Override
    public ParserState handleCharacter(char c) {
        switch (c) {
            case '\\':
                char next = parser.lookAhead(1).orElseThrow(exception(UNEXPECTED_PATTERN_END_WANTED_CHARACTER));
                builder.addSingleCharacterElement(next);
                parser.skip(1);
                return this;
            case '[':
                return new DefineCharacterRangeState(parser, builder);
            default:
                builder.addSingleCharacterElement(c);
                return this;
        }
    }

    @Override
    public void patternEnded() {
        // nothing needs be done here
    }
}
