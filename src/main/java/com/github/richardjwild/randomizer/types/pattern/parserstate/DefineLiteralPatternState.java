package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;

public class DefineLiteralPatternState extends ParserState {

    public DefineLiteralPatternState(StringPatternParser parser, StringPatternBuilder builder) {
        super(parser, builder);
    }

    @Override
    public ParserState handle(char c) {
        ParserState nextState = this;
        switch (c) {
            case '\\':
                char next = parser.lookAhead(1, "pattern.parser.endedunexpectedly.wantedcharacter");
                builder.addSingleCharacterElement(next);
                parser.skip(1);
                break;
            case '[':
                nextState = new DefineCharacterRangeState(parser, builder);
                break;
            default:
                builder.addSingleCharacterElement(c);
        }
        return nextState;
    }

    @Override
    public void patternEnded() {
        // nothing needs be done here
    }
}
