package com.github.richardjwild.randomizer.types.pattern;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
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

    public Optional<Integer> length() {
        return ofNullable(length);
    }

    public Optional<Integer> minLength() {
        return ofNullable(minLength);
    }

    public Integer maxLength() {
        return maxLength;
    }

    public List<Character> permissibleCharacters() {
        return permissibleCharacters;
    }
}
