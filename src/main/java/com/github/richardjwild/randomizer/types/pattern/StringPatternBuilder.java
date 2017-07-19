package com.github.richardjwild.randomizer.types.pattern;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class StringPatternBuilder {

    private ArrayList<StringPatternElement> elements;

    public StringPatternBuilder() {
        this.elements = new ArrayList<>();
    }

    public ArrayList<StringPatternElement> getElements() {
        return elements;
    }

    public void addSingleCharacterElement(char c) {
        elements.add(new StringPatternElement(1, null, null, singletonList(c)));
    }

    public void addFixedLengthElement(int length, List<Character> permittedCharacters) {
        elements.add(new StringPatternElement(length, null, null, permittedCharacters));
    }

    public void addRandomLengthElement(int minLength, int maxLength, List<Character> permittedCharacters) {
        elements.add(new StringPatternElement(null, minLength, maxLength, permittedCharacters));
    }
}