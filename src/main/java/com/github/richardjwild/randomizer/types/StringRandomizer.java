package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

import static java.util.Optional.ofNullable;

/**
 * Creates randomized String values suitable for use as test data in automated tests.<p>
 * Usage: <code>String randomValue = Randomizer.forType(String.class).length(&lt;length&gt;).value();</code><p>
 * This class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
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

    @Override
    public String value() {
        validateConstraints();
        return randomString();
    }

    private void validateConstraints() {
        if (length == null && maxLength == null)
            throw new IllegalArgumentException("Either length or maxLength must be specified");
        if (length != null && maxLength != null)
            throw new IllegalArgumentException("Length and maxLength may not be specified simultaneously");
        if (length != null && minLength != null)
            throw new IllegalArgumentException("Length and minLength may not be specified simultaneously");
        if (maxLength != null && maxLength <= 0)
            throw new IllegalArgumentException("Maximum length must be greater than zero");
        if (length != null && length <= 0)
            throw new IllegalArgumentException("Length must be greater than zero");
        if (minLength != null && maxLength != null && minLength > maxLength)
            throw new IllegalArgumentException("Minimum length must be less than or equal to maximum length");
        if (minChar > maxChar)
            throw new IllegalArgumentException("Minimum character must be less than or equal to maximum character");
    }

    private String randomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLength(); i++)
            sb.append(randomCharacter());
        return sb.toString();
    }

    private int getLength() {
        return ofNullable(length).orElseGet(() -> randomInt(ofNullable(minLength).orElse(1), maxLength));
    }

    private char randomCharacter() {
        return (char) randomInt(minChar, maxChar);
    }

    private int randomInt(int min, int max) {
        return random.nextInt(max - (min - 1)) + min;
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
}
