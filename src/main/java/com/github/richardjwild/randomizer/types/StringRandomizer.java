package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

import static java.util.Optional.ofNullable;

public class StringRandomizer extends Randomizer<String> {

    private Integer length = null;
    private Integer maxLength = null;
    private final int maxChar = (int) Character.MAX_VALUE;
    private final int minChar = (int) ' ';

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
        if (maxLength != null && maxLength <= 0)
            throw new IllegalArgumentException("Maximum length must be greater than zero");
        if (length != null && length <= 0)
            throw new IllegalArgumentException("Length must be greater than zero");
    }

    private String randomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLength(); i++)
            sb.append(randomCharacter());
        return sb.toString();
    }

    private int getLength() {
        return ofNullable(length).orElseGet(() -> randomInt(1, maxLength));
    }

    private char randomCharacter() {
        return (char) randomInt(minChar, maxChar);
    }

    private int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    @Override
    public Randomizer<String> length(int length) {
        this.length = length;
        return this;
    }

    @Override
    public Randomizer<String> maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }
}
