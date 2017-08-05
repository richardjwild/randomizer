package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import com.github.richardjwild.randomizer.streams.Characters;
import com.github.richardjwild.randomizer.types.pattern.StringPatternElement;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.richardjwild.randomizer.localization.Messages.*;
import static com.github.richardjwild.randomizer.validation.Validator.check;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * Creates randomized String values suitable for use as test data in automated tests.<p>
 * Usage: <code>String randomValue = Randomizer.forType(String.class).length(&lt;length&gt;).value();</code><p>
 * This class implements the builder patternElements to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: {@link #length}, {@link #maxLength}, {@link #minLength}, {@link #maxChar} and
 * {@link #minChar}. If any other constraint method is called on this
 * class an <code>UnsupportedOperationException</code> will be thrown.<p>
 * Either one of <code>length</code> and <code>maxLength</code> must be specified, but it is illegal to specify both at
 * the same time. You may optionally specify a minimum length with <code>minLength</code> in conjunction with
 * <code>maxLength</code> but it is illegal to specify a minimum length in conjunction with <code>length</code>. If a
 * minimum length is specified, it must be less than or equal to the maximum length.<p>
 * You may optionally constrain the randomly generated characters that comprise the string with {@link #minChar} and
 * {@link #maxChar}. The defaults are <code>' '</code> (space) and {@link Character#MAX_VALUE} respectively. For
 * example, <code>Randomizer.forType(String.class).length(3).minChar('a').maxChar('a').value()</code> will always
 * return the string <code>aaa</code>.
 */
public class StringRandomizer extends Randomizer<String> {

    private Integer length = null;
    private Integer maxLength = null;
    private Integer minLength = null;
    private int maxChar = (int) Character.MAX_VALUE;
    private int minChar = (int) ' ';
    private String pattern;

    @Override
    public String value() {
        validateConstraints();
        return randomString();
    }

    private void validateConstraints() {
        check(() -> pattern == null && length == null && maxLength == null,
                LENGTH_MAXLENGTH_OR_PATTERN_MUST_BE_SPECIFIED);
        check(() -> length != null && maxLength != null,
                LENGTH_AND_MAXLENGTH_CANNOT_BE_SPECIFIED_SIMULTANEOUSLY);
        check(() -> length != null && minLength != null,
                LENGTH_AND_MINLENGTH_CANNOT_BE_SPECIFIED_SIMULTANEOUSLY);
        check(() -> maxLength != null && maxLength <= 0,
                MAXLENGTH_MUST_BE_GREATER_THAN_ZERO);
        check(() -> minLength != null && minLength <= 0,
                MINLENGTH_MUST_BE_GREATER_THAN_ZERO);
        check(() -> length != null && length <= 0,
                LENGTH_MUST_BE_GREATER_THAN_ZERO);
        check(() -> minLength != null && maxLength != null && minLength > maxLength,
                MINLENGTH_MUST_BE_LESS_THAN_MAXLENGTH);
        check(() -> minChar > maxChar,
                MINCHAR_MUST_BE_LESS_THAN_MAXCHAR);
    }

    private String randomString() {
        return patternElements().stream()
                .map(this::buildStringElement)
                .collect(Collectors.joining());
    }

    private List<StringPatternElement> patternElements() {
        return ofNullable(pattern)
                .map(StringPatternParser::new)
                .map(StringPatternParser::parseAndCreatePatternElements)
                .orElseGet(this::createSinglePatternElement);
    }

    private List<StringPatternElement> createSinglePatternElement() {
        return singletonList(new StringPatternElement(length, minLength, maxLength, allCharactersBetween(minChar, maxChar)));
    }

    private List<Character> allCharactersBetween(int minChar, int maxChar) {
        return Characters.from((char) minChar).limit(maxChar - minChar).collect(toList());
    }

    private String buildStringElement(StringPatternElement element) {
        StringBuilder builder = new StringBuilder();
        int length = elementLength(element);
        for (int c = 0; c < length; c++)
            builder.append(randomCharacterFrom(element.permissibleCharacters()));
        return builder.toString();
    }

    private int elementLength(StringPatternElement pe) {
        return ofNullable(pe.length()).orElseGet(() -> randomLength(pe));
    }

    private int randomLength(StringPatternElement pe) {
        Integer minLength = ofNullable(pe.minLength()).orElse(1);
        return randomInt(minLength, pe.maxLength());
    }

    private char randomCharacterFrom(List<Character> setOfCharacters) {
        int r = randomInt(1, setOfCharacters.size() + 1);
        return setOfCharacters.get(r - 1);
    }

    private int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    @Override
    public StringRandomizer length(int length) {
        this.length = length;
        return this;
    }

    @Override
    public StringRandomizer maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    @Override
    public StringRandomizer minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    @Override
    public StringRandomizer minChar(char minChar) {
        this.minChar = minChar;
        return this;
    }

    @Override
    public StringRandomizer maxChar(char maxChar) {
        this.maxChar = maxChar;
        return this;
    }

    @Override
    public StringRandomizer pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}
