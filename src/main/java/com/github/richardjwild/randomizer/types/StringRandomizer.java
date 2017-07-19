package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import com.github.richardjwild.randomizer.streams.Characters;
import com.github.richardjwild.randomizer.types.pattern.StringPatternElement;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParser;

import java.util.List;

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
                "randomizer.string.validation.lengthmaxlengthorpattern");
        check(() -> length != null && maxLength != null,
                "randomizer.string.validation.lengthandmaxlength");
        check(() -> length != null && minLength != null,
                "randomizer.string.validation.lengthandminlength");
        check(() -> maxLength != null && maxLength <= 0,
                "randomizer.string.validation.maxlengthgreaterzero");
        check(() -> minLength != null && minLength <= 0,
                "randomizer.string.validation.minlengthgreaterzero");
        check(() -> length != null && length <= 0,
                "randomizer.string.validation.lengthgreaterzero");
        check(() -> minLength != null && maxLength != null && minLength > maxLength,
                "randomizer.string.validation.minlengthlessthanmaxlength");
        check(() -> minChar > maxChar,
                "randomizer.string.validation.mincharlessthanmaxchar");
    }

    private String randomString() {
        final StringBuilder sb = new StringBuilder();
        patternElements().forEach(pe -> buildAndAppendStringElement(sb, pe));
        return sb.toString();
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

    private void buildAndAppendStringElement(StringBuilder sb, StringPatternElement pe) {
        int length = elementLength(pe);
        for (int c = 0; c < length; c++)
            sb.append(randomCharacterFrom(pe.permissibleCharacters()));
    }

    private int elementLength(StringPatternElement pe) {
        return ofNullable(pe.length())
                .orElseGet(() -> randomInt(ofNullable(pe.minLength()).orElse(1), pe.maxLength()));
    }

    private char randomCharacterFrom(List<Character> permissibleCharacters) {
        int r = randomInt(1, permissibleCharacters.size() + 1);
        return permissibleCharacters.get(r - 1);
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
