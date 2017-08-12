package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;
import java.util.function.Supplier;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class StringRandomizerTest {

    Randomizer<String> testObj = Randomizer.forType(String.class);

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void checkNotSupported(Supplier<String> testSupplier) {
        thrown.expect(UnsupportedOperationException.class);
        testSupplier.get();
    }

    @Test
    public void maximumMethodNotSupportedForStringRandomizer() {
        checkNotSupported(() -> testObj.max("this is not supported").value());
    }

    @Test
    public void minimumMethodNotSupportedForStringRandomizer() {
        checkNotSupported(() -> testObj.min("this is not supported").value());
    }

    private void checkValidation(Supplier<String> testSupplier, String expectedErrorMessage) {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedErrorMessage);
        testSupplier.get();
    }

    @Test
    public void getValueWithoutSpecifyingLengthOrMaxLengthThrowsException() {
        checkValidation(() -> testObj.value(),
                "One of length, maxLength or a pattern must be specified");
    }

    @Test
    public void getValueWithLengthZeroThrowsException() {
        checkValidation(() -> testObj.length(0).value(),
                "Length must be greater than zero");
    }

    @Test
    public void getValueWithLengthNegativeThrowsException() {
        checkValidation(() -> testObj.length(-1).value(),
                "Length must be greater than zero");
    }

    @Test
    public void getValueWithMaxLengthZeroThrowsException() {
        checkValidation(() -> testObj.maxLength(0).value(),
                "Maximum length must be greater than zero");
    }

    @Test
    public void getValueWithMaxLengthNegativeThrowsException() {
        checkValidation(() -> testObj.maxLength(-1).value(),
                "Maximum length must be greater than zero");
    }

    @Test
    public void getValueWithMinLengthZeroThrowsException() {
        checkValidation(() -> testObj.maxLength(1).minLength(0).value(),
                "Minimum length must be greater than zero");
    }

    @Test
    public void getValueWithMinLengthNegativeThrowsException() {
        checkValidation(() -> testObj.maxLength(1).minLength(-1).value(),
                "Minimum length must be greater than zero");
    }

    @Test
    public void getValueWithMinLengthGreaterThanMaxLengthThrowsException() {
        checkValidation(() -> testObj.minLength(2).maxLength(1).value(),
                "Minimum length must be less than or equal to maximum length");
    }

    @Test
    public void getValueWithMinCharGreaterThanMaxCharThrowsException() {
        checkValidation(() -> testObj.length(1).minChar('b').maxChar('a').value(),
                "Minimum character must be less than or equal to maximum character");
    }

    @Test
    public void lengthAndMaximumLengthMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.length(1).maxLength(1).value(),
                "Length and maxLength may not be specified simultaneously");
    }

    @Test
    public void lengthAndMinimumLengthMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.length(1).minLength(1).value(),
                "Length and minLength may not be specified simultaneously");
    }

    @Test
    public void patternAndMinimumLengthMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.minLength(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void patternAndMaximumLengthMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.maxLength(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void patternAndLengthMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.length(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void patternAndMinCharMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.minChar('a').pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void patternAndMaxCharMayNotBeSpecifiedAtTheSameTime() {
        checkValidation(() -> testObj.maxChar('a').pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void getValueReturnsSomething() {
        String value = testObj.length(1).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void getValueWithLengthSpecified() {
        int length = 100;
        String value = testObj.length(length).value();
        assertThat(value).hasSize(length);
    }

    @Test
    public void getValueWithMaxLengthSpecified() {
        int maxLength = 100;
        String value = testObj.maxLength(maxLength).value();
        assertThat(value.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    public void getValueWithMaxAndMinLengthSpecified() {
        int maxLength = 100;
        int minLength = 90;
        String value = testObj.minLength(minLength).maxLength(maxLength).value();
        assertThat(value.length()).isGreaterThanOrEqualTo(minLength);
        assertThat(value.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    public void getValueWithMinCharAndMaxCharSpecified() {
        char minChar = 'a';
        char maxChar = 'z';
        String value = testObj.minChar(minChar).maxChar(maxChar).length(100).value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo(minChar);
            assertThat(c).isLessThanOrEqualTo(maxChar);
        }
    }

    @Test
    public void patternWithSingleLiteralCharacter() {
        String value = testObj.pattern("a").value();
        assertThat(value).isEqualTo("a");
    }

    @Test
    public void patternWithLiteralCharacterSequence() {
        String value = testObj.pattern("ab").value();
        assertThat(value).isEqualTo("ab");
    }

    @Test
    public void patternWithSetOfPermittedCharacters() {
        String value = testObj.pattern("[abc]{1}").value();
        assertThat(value).hasSize(1);
        assertThat("abc").contains(value);
    }

    @Test
    public void patternWithRangeOfPermittedCharacters() {
        String value = testObj.pattern("[a-c]{1}").value();
        assertThat(value).hasSize(1);
        assertThat("abc").contains(value);
    }

    @Test
    public void patternWithRangeOfPermittedCharactersWrongWayRound() {
        String value = testObj.pattern("[c-a]{1}").value();
        assertThat(value).hasSize(1);
        assertThat("abc").contains(value);
    }

    private void assertAllCharactersContainedIn(String containedIn, String value) {
        char[] charArray = containedIn.toCharArray();
        for (char c : value.toCharArray())
            assertThat(charArray).contains(c);
    }

    @Test
    public void patternWithRangeWithLengthGreaterThanOne() {
        String value = testObj.pattern("[a-c]{3}").value();
        assertEquals(3, value.length());
        assertAllCharactersContainedIn("abc", value);
    }

    @Test
    public void overlappingCharacterRanges() {
        String value = testObj.pattern("[a-bb-c]{1}").value();
        assertThat("abc").contains(value);
    }

    @Test
    public void patternWithRangeWithLengthMoreThanOneDigit() {
        String value = testObj.pattern("[a-c]{10}").value();
        assertEquals(10, value.length());
        assertAllCharactersContainedIn("abc", value);
    }

    @Test
    public void patternWithLengthRange() {
        String value = testObj.pattern("[a-c]{1,10}").value();
        assertThat(value.length()).isGreaterThanOrEqualTo(1);
        assertThat(value.length()).isLessThanOrEqualTo(10);
        assertAllCharactersContainedIn("abc", value);
    }

    private void checkPatternParsing(String pattern, String expectedErrorMessage) {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage(expectedErrorMessage);
        testObj.pattern(pattern).value();
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated1() {
        checkPatternParsing("[a",
                "Unexpected end of pattern input, was expecting: ']', '-', a character");
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated2() {
        checkPatternParsing("[a-",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated3() {
        checkPatternParsing("[a-b", "Unexpected end of pattern input, was expecting: ']'");
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminatedAfterEscapedCharacter() {
        checkPatternParsing("[\\[",
                "Unexpected end of pattern input, was expecting: ']', '-', a character");
    }

    @Test
    public void patternWithRangeWithoutLengthSpecified1() {
        checkPatternParsing("[a-c]", "Unexpected end of pattern input, was expecting: '{'");
    }

    @Test
    public void patternWithRangeWithoutLengthSpecified2() {
        checkPatternParsing("[a]a",
                "Unexpected character after range definition, was expecting: '{'");
    }

    @Test
    public void patternWithTooManyFieldsInLengthRange() {
        checkPatternParsing("[a-c]{1,1,1}",
                "Too many fields in pattern length definition, was expecting: 2 but got: 3");
    }

    @Test
    public void patternWithNanInLengthRange() {
        checkPatternParsing("[a]{a}", "Not-a-number in pattern length definition");
    }

    @Test
    public void patternWithNanInLengthRangeLowerLimit() {
        checkPatternParsing("[a]{a,1}", "Not-a-number in pattern length definition");
    }

    @Test
    public void patternWithNanInLengthRangeUpperLimit() {
        checkPatternParsing("[a]{1,a}", "Not-a-number in pattern length definition");
    }

    @Test
    public void patternWithMissingLimitsInLengthRange() {
        checkPatternParsing("[a]{}", "Missing value in pattern length definition");
    }

    @Test
    public void patternWithMissingLowerLimitInLengthRange() {
        checkPatternParsing("[a]{,1}", "Missing value in pattern length definition");
    }

    @Test
    public void patternWithMissingUpperLimitInLengthRange3() {
        checkPatternParsing("[a]{1,}", "Missing value in pattern length definition");
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange1() {
        checkPatternParsing("[a]{",
                "Unexpected end of pattern input, was expecting: a number");
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange2() {
        checkPatternParsing("[a]{1",
                "Unexpected end of pattern input, was expecting: ',' '}', a number");
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange3() {
        checkPatternParsing("[\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange4() {
        checkPatternParsing("[a-\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void patternWithEscapeCharacterInLiteralSequenceAtEndOfPattern() {
        checkPatternParsing("\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void patternWithEscapedCharacterInLiteralSequence() {
        String value = testObj.pattern("\\[").value();
        assertThat(value).isEqualTo("[");
    }

    @Test
    public void patternWithEscapedCharactersInSetOfPermittedCharacters() {
        String value = testObj.pattern("[\\[\\]]{10}").value();
        assertAllCharactersContainedIn("[]", value);
    }

    @Test
    public void patternWithEscapedCharacterInRangeDefinition1() {
        String value = testObj.pattern("[a\\-]{10}").value();
        assertAllCharactersContainedIn("-a", value);
    }

    @Test
    public void patternWithEscapedCharacterInRangeDefinition2() {
        String value = testObj.pattern("[a-\\]]{10}").value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo(']');
            assertThat(c).isLessThanOrEqualTo('a');
        }
    }

    @Test
    public void patternWithTwoEscapedCharactersInRangeDefinition() {
        String value = testObj.pattern("[\\a-\\z]{100}").value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo('a');
            assertThat(c).isLessThanOrEqualTo('z');
        }
    }

    @Ignore("Run this test to see the effect of various patterns")
    @Test
    public void variousPatterns() {
        testObj.pattern("[a-z]{1}").value();
        testObj.pattern("[a-z0-9]{2}").value();
        testObj.pattern("[AEIOU]{50}").value();
        testObj.pattern("[a-zA-Z]{1,10}").value();
        testObj.pattern("Hello world!").value();
        testObj.pattern("\\[Hello world!\\]").value();
        testObj.pattern("[\\[\\]\\-]{10}").value();
        testObj.pattern("[a\\-c]{10}").value();
        testObj.pattern("[a-zA-Z]{5,10}[0-9]{2}@[a-z]{5,10}.com").value();
    }
}