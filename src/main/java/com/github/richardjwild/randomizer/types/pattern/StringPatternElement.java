package com.github.richardjwild.randomizer.types.pattern;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StringPatternElement {

    private final Integer length, minLength, maxLength;
    private final List<Character> permissibleCharacters;

    public StringPatternElement(Integer length, Integer minLength, Integer maxLength, List<Character> permissibleCharacters) {
        this.length = length;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.permissibleCharacters = permissibleCharacters.stream().distinct().collect(toList());
    }

    public Integer length() {
        return length;
    }

    public Integer minLength() {
        return minLength;
    }

    public Integer maxLength() {
        return maxLength;
    }

    public List<Character> permissibleCharacters() {
        return permissibleCharacters;
    }
}
