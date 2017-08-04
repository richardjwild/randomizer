package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;
import com.github.richardjwild.randomizer.types.pattern.StringPatternParserException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringRandomizerTest {

    Randomizer<String> testObj = Randomizer.forType(String.class);

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void maximumMethodNotSupportedForStringRandomizer() {
        String maxValue = "this is not supported for a string";
        thrown.expect(UnsupportedOperationException.class);
        testObj.max(maxValue).value();
    }

    @Test
    public void minimumMethodNotSupportedForStringRandomizer() {
        String minValue = "this is not supported for a string";
        thrown.expect(UnsupportedOperationException.class);
        testObj.min(minValue).value();
    }

    @Test
    public void getValueWithoutSpecifyingLengthOrMaxLengthThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("One of length, maxLength or a pattern must be specified");
        testObj.value();
    }

    @Test
    public void getValueWithLengthZeroThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Length must be greater than zero");
        testObj.length(0).value();
    }

    @Test
    public void getValueWithLengthNegativeThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Length must be greater than zero");
        testObj.length(-1).value();
    }

    @Test
    public void getValueWithMaxLengthZeroThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Maximum length must be greater than zero");
        testObj.maxLength(0).value();
    }

    @Test
    public void getValueWithMaxLengthNegativeThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Maximum length must be greater than zero");
        testObj.maxLength(-1).value();
    }

    @Test
    public void getValueWithMinLengthZeroThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Minimum length must be greater than zero");
        testObj.maxLength(1).minLength(0).value();
    }

    @Test
    public void getValueWithMinLengthNegativeThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Minimum length must be greater than zero");
        testObj.maxLength(1).minLength(-1).value();
    }

    @Test
    public void getValueWithMinLengthGreaterThanMaxLengthThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Minimum length must be less than or equal to maximum length");
        testObj.minLength(2).maxLength(1).value();
    }

    @Test
    public void getValueWithMinCharGreaterThanMaxCharThrowsException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Minimum character must be less than or equal to maximum character");
        testObj.length(1).minChar('b').maxChar('a').value();
    }

    @Test
    public void lengthAndMaximumLengthMayNotBeSpecifiedAtTheSameTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Length and maxLength may not be specified simultaneously");
        testObj.length(1).maxLength(1).value();
    }

    @Test
    public void lengthAndMinimumLengthMayNotBeSpecifiedAtTheSameTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Length and minLength may not be specified simultaneously");
        testObj.length(1).minLength(1).value();
    }

    @Test
    public void getValueWithLengthSpecified() {
        int length = 100;
        String value = testObj.length(length).value();
        assertNotNull(value);
        assertEquals(length, value.length());
    }

    @Test
    public void getValueWithMaxLengthSpecified() {
        int maxLength = 100;
        String value = testObj.maxLength(maxLength).value();
        assertTrue(value.length() <= maxLength);
    }

    @Test
    public void getValueWithMaxAndMinLengthSpecified() {
        int maxLength = 100;
        int minLength = 90;
        String value = testObj.minLength(minLength).maxLength(maxLength).value();
        assertTrue(minLength <= value.length());
        assertTrue(value.length() <= maxLength);
    }

    @Test
    public void getValueWithMinCharAndMaxCharSpecified() {
        char minChar = 'a';
        char maxChar = 'z';
        String value = testObj.minChar(minChar).maxChar(maxChar).length(100).value();
        for (char c : value.toCharArray()) {
            assertTrue(minChar <= c);
            assertTrue(c <= maxChar);
        }
    }

    @Test
    public void patternWithSingleLiteralCharacter() {
        String value = testObj.pattern("a").value();
        assertEquals("a", value);
    }

    @Test
    public void patternWithLiteralCharacterSequence() {
        String value = testObj.pattern("ab").value();
        assertEquals("ab", value);
    }

    @Test
    public void patternWithSetOfPermittedCharacters() {
        String value = testObj.pattern("[abc]{1}").value();
        assertEquals(1, value.length());
        assertTrue('a' <= value.charAt(0));
        assertTrue(value.charAt(0) <= 'c');
    }

    @Test
    public void patternWithRangeOfPermittedCharacters() {
        String value = testObj.pattern("[a-c]{1}").value();
        assertEquals(1, value.length());
        assertTrue('a' <= value.charAt(0));
        assertTrue(value.charAt(0) <= 'c');
    }

    @Test
    public void patternWithRangeWithLengthGreaterThanOne() {
        String value = testObj.pattern("[a-c]{3}").value();
        assertEquals(3, value.length());
        for (char c : value.toCharArray()) {
            assertTrue('a' <= c);
            assertTrue(c <= 'c');
        }
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated1() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: ']', '-', a character");
        testObj.pattern("[a").value();
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated2() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: a character");
        testObj.pattern("[a-").value();
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminated3() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: ']'");
        testObj.pattern("[a-b").value();
    }

    @Test
    public void patternWithRangeUnexpectedlyTerminatedAfterEscapedCharacter() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: ']', '-', a character");
        testObj.pattern("[\\[").value();
    }

    @Test
    public void patternWithRangeWithoutLengthSpecified1() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: '{'");
        testObj.pattern("[a-c]").value();
    }

    @Test
    public void patternWithRangeWithoutLengthSpecified2() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected character after range definition, was expecting: '{'");
        testObj.pattern("[a]a").value();
    }

    @Test
    public void overlappingCharacterRanges() {
        testObj.pattern("[a-bb-c]{1}").value();
    }

    @Test
    public void patternWithRangeWithLengthMoreThanOneDigit() {
        String value = testObj.pattern("[a-c]{10}").value();
        assertEquals(10, value.length());
        for (char c : value.toCharArray()) {
            assertTrue('a' <= c);
            assertTrue(c <= 'c');
        }
    }

    @Test
    public void patternWithLengthRange() {
        String value = testObj.pattern("[a-c]{1,10}").value();
        assertTrue(1 <= value.length());
        assertTrue(value.length() <= 10);
        for (char c : value.toCharArray()) {
            assertTrue('a' <= c);
            assertTrue(c <= 'c');
        }
    }

    @Test
    public void patternWithTooManyFieldsInLengthRange() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Too many fields in pattern length definition, was expecting: 2 but got: 3");
        testObj.pattern("[a-c]{1,1,1}").value();
    }

    @Test
    public void patternWithNanInLengthRange1() {
        patternWithNanInLengthRange("[a]{a}");
    }

    @Test
    public void patternWithNanInLengthRange2() {
        patternWithNanInLengthRange("[a]{a,1}");
    }

    @Test
    public void patternWithNanInLengthRange3() {
        patternWithNanInLengthRange("[a]{1,a}");
    }

    private void patternWithNanInLengthRange(String pattern) {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Not-a-number in pattern length definition");
        testObj.pattern(pattern).value();
    }

    @Test
    public void patternWithMissingValueInLengthRange1() {
        patternWithMissingValueInLengthRange("[a]{}");
    }

    @Test
    public void patternWithMissingValueInLengthRange2() {
        patternWithMissingValueInLengthRange("[a]{,1}");
    }

    @Test
    public void patternWithMissingValueInLengthRange3() {
        patternWithMissingValueInLengthRange("[a]{1,}");
    }

    private void patternWithMissingValueInLengthRange(String pattern) {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Missing value in pattern length definition");
        testObj.pattern(pattern).value();
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange1() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: a number");
        testObj.pattern("[a]{").value();
    }

    @Test
    public void patternWithUnexpectedTerminationOfLengthRange2() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: ',' '}', a number");
        testObj.pattern("[a]{1").value();
    }

    @Test
    public void patternWithEscapedCharacterInLiteralSequence() {
        String value = testObj.pattern("\\[").value();
        assertEquals("[", value);
    }

    @Test
    public void patternWithEscapeCharacterInLiteralSequenceAtEndOfPattern() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: a character");
        testObj.pattern("\\").value();
    }

    @Test
    public void patternWithEscapedCharactersInSetOfPermittedCharacters() {
        String result = testObj.pattern("[\\[\\]]{10}").value();
        for (char c: result.toCharArray())
            assertTrue(c == '[' || c == ']');
    }

    @Test
    public void patternWithEscapedCharacterInRangeDefinition1() {
        String value = testObj.pattern("[a\\-]{10}").value();
        for (char c : value.toCharArray())
            assertTrue(c == '-' || c == 'a');
    }

    @Test
    public void patternWithEscapedCharacterInRangeDefinition2() {
        String value = testObj.pattern("[a-\\]]{10}").value();
        for (char c : value.toCharArray()) {
            assertTrue(']' <= c);
            assertTrue(c <= 'a');
        }
    }

    @Test
    public void patternWithTwoEscapedCharactersInRangeDefinition() {
        String value = testObj.pattern("[\\a-\\z]{100}").value();
        for (char c : value.toCharArray()) {
            assertTrue('a' <= c);
            assertTrue(c <= 'z');
        }
    }

    @Test
    public void patternWithRangeDefinitionThatEndsUnexpectedlyAfterEscapeCharacter1() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: a character");
        testObj.pattern("[\\").value();
    }

    @Test
    public void patternWithRangeDefinitionThatEndsUnexpectedlyAfterEscapeCharacter2() {
        thrown.expect(StringPatternParserException.class);
        thrown.expectMessage("Unexpected end of pattern input, was expecting: a character");
        testObj.pattern("[a-\\").value();
    }

    @Ignore("Run this test to see the effect of various patterns")
    @Test
    public void variousPatterns() {
        System.out.println(testObj.pattern("[a-z]{1}").value());
        System.out.println(testObj.pattern("[a-z0-9]{2}").value());
        System.out.println(testObj.pattern("[AEIOU]{50}").value());
        System.out.println(testObj.pattern("[a-zA-Z]{1,10}").value());
        System.out.println(testObj.pattern("This is a literal string").value());
        System.out.println(testObj.pattern("\\[This is a literal string with escaped characters\\]").value());
        System.out.println(testObj.pattern("[\\[\\]\\-]{10}").value());
        System.out.println(testObj.pattern("[a\\-c]{10}").value());
        System.out.println(testObj.pattern("[a-zA-Z]{5,10}[0-9]{2}@[a-z]{5,10}.com").value());
    }
}