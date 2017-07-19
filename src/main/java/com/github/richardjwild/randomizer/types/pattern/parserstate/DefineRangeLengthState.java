package com.github.richardjwild.randomizer.types.pattern.parserstate;

import com.github.richardjwild.randomizer.types.pattern.StringPatternBuilder;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;

import java.util.List;

import static com.github.richardjwild.randomizer.localization.Messages.getMessage;
import static java.lang.Integer.parseInt;

public class DefineRangeLengthState extends ParserState {

    private final List<Character> permittedCharacters;
    private final StringBuilder lengthBuilder = new StringBuilder();

    public DefineRangeLengthState(StringPatternParser parser, StringPatternBuilder builder, List<Character> permittedCharacters) {
        super(parser, builder);
        this.permittedCharacters = permittedCharacters;
    }

    @Override
    public ParserState handle(char c) {
        ParserState nextState = this;
        if (c == '}') {
            setElementLength(lengthBuilder.toString());
            nextState = new DefineLiteralPatternState(parser, builder);
        } else {
            lengthBuilder.append(c);
        }
        return nextState;
    }

    @Override
    public void patternEnded() {
        if (lengthBuilder.length() == 0)
            throw new StringPatternParserException(getMessage("pattern.parser.endedunexpectedly.wantednumber"));
        throw new StringPatternParserException(getMessage("pattern.parser.endedunexpectedly.wantednumbercommaclosecurlybrace"));
    }

    private void setElementLength(String elementLengthDefinition) {
        String[] parts = elementLengthDefinition.split(",");
        switch (parts.length) {
            case 1:
                if (elementLengthDefinition.endsWith(","))
                    throw new StringPatternParserException(getMessage("pattern.parser.lengthrange.missingvalue"));
                builder.addFixedLengthElement(tryParseInt(parts[0]), permittedCharacters);
                break;
            case 2:
                builder.addRandomLengthElement(tryParseInt(parts[0]), tryParseInt(parts[1]), permittedCharacters);
                break;
            default:
                throw new StringPatternParserException(getMessage("pattern.parser.lengthrange.toomanyfields",
                        Integer.valueOf(parts.length).toString()));
        }
    }

    private int tryParseInt(String s) {
        try {
            if (s.isEmpty())
                throw new StringPatternParserException(getMessage("pattern.parser.lengthrange.missingvalue"));
            return parseInt(s);
        } catch (NumberFormatException e) {
            throw new StringPatternParserException(getMessage("pattern.parser.lengthrange.notanumber"));
        }
    }
}
