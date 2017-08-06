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
 * This class implements the builder patternElements to allow boundaries (constraints) to be specified on the generated
 * random value. These constraints are: {@link #length}, {@link #maxLength}, {@link #minLength}, {@link #maxChar},
 * {@link #minChar} and {@link #pattern}. If any other constraint method is called on this class an
 * <code>UnsupportedOperationException</code> will be thrown.<p>
 * If <code>pattern</code> is specified then no other constraint may be specified. If <code>pattern</code> is not
 * specified then either one of <code>length</code> and <code>maxLength</code> must be specified, but it is illegal to
 * specify both <code>length</code> and <code>maxLength</code> at the same time. You may optionally use
 * <code>minLength</code> to specify a minimum length, in conjunction with <code>maxLength</code>, but it is illegal to
 * specify a minimum length in conjunction with <code>length</code>. If a minimum length is specified, it must be less
 * than or equal to the maximum length.<p>
 * In the absence of a pattern, you may optionally constrain the randomly generated characters that comprise the string
 * with {@link #minChar} and {@link #maxChar}. The defaults are <code>'\u0020'</code> (space) and
 * {@link Character#MAX_VALUE} respectively. <code>minChar</code> must always be less than or equal to
 * <code>maxChar</code>. If they are equal then the random string is constrained to a single character. For example,
 * <code>Randomizer.forType(String.class).length(3).minChar('a').maxChar('a').value()</code> will always return the
 * string <code>aaa</code>.
 */
public class StringRandomizer extends Randomizer<String> {

    private Integer length = null, maxLength = null, minLength = null;
    private Character maxChar = null, minChar = null;
    private String pattern;

    /**
     * Gets the generated random string value, within any specified constraints.
     * @return The generated random string.
     * @throws IllegalArgumentException The specified combination of constraints was invalid.
     * @throws com.github.richardjwild.randomizer.types.pattern.StringPatternParserException The specified pattern could
     * not be parsed.
     */
    @Override
    public String value() {
        validateConstraints();
        return randomString();
    }

    private void validateConstraints() {
        check(() -> pattern == null && length == null && maxLength == null,
                LENGTH_MAXLENGTH_OR_PATTERN_MUST_BE_SPECIFIED);
        check(() -> pattern != null && anyOtherConstraintSpecified(),
                PATTERN_AND_ANY_OTHER_CONSTRAINT);
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
        check(() -> minChar != null && maxChar != null && minChar > maxChar,
                MINCHAR_MUST_BE_LESS_THAN_MAXCHAR);
    }

    private boolean anyOtherConstraintSpecified() {
        return minLength != null || maxLength != null || length != null || minChar != null || maxChar != null;
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

    private List<Character> allCharactersBetween(Character minChar, Character maxChar) {
        char min = ofNullable(minChar).orElse(' ');
        char max = ofNullable(maxChar).orElse(Character.MAX_VALUE);
        return Characters.from(min).limit(max - min).collect(toList());
    }

    private String buildStringElement(StringPatternElement element) {
        StringBuilder builder = new StringBuilder();
        int length = elementLength(element);
        for (int c = 0; c < length; c++)
            builder.append(randomCharacterFrom(element.permissibleCharacters()));
        return builder.toString();
    }

    private int elementLength(StringPatternElement pe) {
        return pe.length().orElseGet(() -> randomLength(pe));
    }

    private int randomLength(StringPatternElement pe) {
        return randomInt(pe.minLength().orElse(1), pe.maxLength());
    }

    private char randomCharacterFrom(List<Character> setOfCharacters) {
        int r = randomInt(1, setOfCharacters.size() + 1);
        return setOfCharacters.get(r - 1);
    }

    private int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * Sets the required length for the generated string. This is mandatory unless {@link #maxLength} is specified
     * instead. It is not allowed to specify both <code>length</code> and <code>maxLength</code> at the same time.
     * @param length The required length for the generated random string.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer length(int length) {
        this.length = length;
        return this;
    }

    /**
     * Sets the maximum length for the generated string. This is mandatory unless <code>length</code> is specified. It
     * is not permitted to specify both <code>maxLength</code> and <code>length</code> at the same time.
     * @param maxLength The maximum length for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    /**
     * Sets the minimum length for the generated random value. It is not permitted to specify both minLength and
     * <code>length</code> at the same time.
     * @param minLength The maximum length for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    /**
     * Sets the minimum value for every character in the generated random value. The default is <code>'\u0020'</code>,
     * i.e. a space character.
     * @param minChar The minimum character value that will appear in the generated string.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer minChar(char minChar) {
        this.minChar = minChar;
        return this;
    }

    /**
     * Sets the maximum value for every character in the generated random value. The default is <code>'\uFFFF'</code>.
     * @param maxChar The maximum character value that will appear in the generated string.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer maxChar(char maxChar) {
        this.maxChar = maxChar;
        return this;
    }

    /**
     * Defines the pattern that the generated random value will follow. The pattern syntax is a subset of the regular
     * expression syntax. Example usages:<p>
     * <code>[a-z]{1}</code> will produce a single random character from a to z.<p>
     * <code>[a-z0-9]{2}</code> will produce two random characters from a to z or 0 to 9.<p>
     * <code>[AEIOU]{5}</code> will produce five random uppercase vowels.<p>
     * <code>[a-zA-Z]{1,10}</code> will produce between 1 and 10 random alphabetic characters. The length will be
     * chosen randomly.<p>
     * <code>abc</code> will produce the literal string "abc".<p>
     * <code>\[\]</code> will produce the literal string "[]", i.e. backslash causes the next character to be treated as
     * a literal character.
     * <code>[\[\]]{10}</code> will produce a string of length 10 randomly constructed from [ and ] characters.<p>
     * <code>[a\-c]{10}</code> will produce a string of length 10 randomly constructed from a, - and c characters.<p>
     * <code>[a-zA-Z]{5,10}[0-9]{2}@[a-z]{5,10}.com</code> will produce a string consisting of 5-10 random alpha
     * characters followed by 2 random digits, then an '@' symbol, then 5-10 random random lowercase letters, followed
     * by the literal string ".com". In other words, a random string that resembles an email address.
     * @param pattern The pattern for the randomized string to adhere to
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     */
    @Override
    public StringRandomizer pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}
