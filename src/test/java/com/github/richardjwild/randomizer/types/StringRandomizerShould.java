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

public class StringRandomizerShould {

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
    public void not_support_max() {
        checkNotSupported(() -> testObj.max("this is not supported").value());
    }

    @Test
    public void not_support_min() {
        checkNotSupported(() -> testObj.min("this is not supported").value());
    }

    private void checkValidation(Supplier<String> testSupplier, String expectedErrorMessage) {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedErrorMessage);
        testSupplier.get();
    }

    @Test
    public void require_one_of_length_or_max_length_or_a_pattern() {
        checkValidation(() -> testObj.value(),
                "One of length, maxLength or a pattern must be specified");
    }

    @Test
    public void reject_zero_length() {
        checkValidation(() -> testObj.length(0).value(),
                "Length must be greater than zero");
    }

    @Test
    public void reject_negative_length() {
        checkValidation(() -> testObj.length(-1).value(),
                "Length must be greater than zero");
    }

    @Test
    public void reject_zero_max_length() {
        checkValidation(() -> testObj.maxLength(0).value(),
                "Maximum length must be greater than zero");
    }

    @Test
    public void reject_negative_max_length() {
        checkValidation(() -> testObj.maxLength(-1).value(),
                "Maximum length must be greater than zero");
    }

    @Test
    public void reject_zero_min_length() {
        checkValidation(() -> testObj.maxLength(1).minLength(0).value(),
                "Minimum length must be greater than zero");
    }

    @Test
    public void reject_negative_min_length() {
        checkValidation(() -> testObj.maxLength(1).minLength(-1).value(),
                "Minimum length must be greater than zero");
    }

    @Test
    public void reject_min_length_greater_than_max_length() {
        checkValidation(() -> testObj.minLength(2).maxLength(1).value(),
                "Minimum length must be less than or equal to maximum length");
    }

    @Test
    public void reject_min_char_greater_than_max_char() {
        checkValidation(() -> testObj.length(1).minChar('b').maxChar('a').value(),
                "Minimum character must be less than or equal to maximum character");
    }

    @Test
    public void reject_length_and_max_length_simultaneously() {
        checkValidation(() -> testObj.length(1).maxLength(1).value(),
                "Length and maxLength may not be specified simultaneously");
    }

    @Test
    public void reject_length_and_min_length_simultaneously() {
        checkValidation(() -> testObj.length(1).minLength(1).value(),
                "Length and minLength may not be specified simultaneously");
    }

    @Test
    public void reject_pattern_and_min_length_simultaneously() {
        checkValidation(() -> testObj.minLength(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void reject_pattern_and_max_length_simultaneously() {
        checkValidation(() -> testObj.maxLength(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void reject_pattern_and_length_simultaneously() {
        checkValidation(() -> testObj.length(1).pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void reject_pattern_and_min_char_simultaneously() {
        checkValidation(() -> testObj.minChar('a').pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void reject_pattern_and_max_char_simultaneously() {
        checkValidation(() -> testObj.maxChar('a').pattern("a").value(),
                "Pattern may not be specified simultaneously with any other constraint");
    }

    @Test
    public void return_a_value() {
        String value = testObj.length(1).value();
        assertThat(value).isNotNull();
    }

    @Test
    public void return_a_value_of_fixed_length() {
        int length = 100;
        String value = testObj.length(length).value();
        assertThat(value).hasSize(length);
    }

    @Test
    public void return_a_value_of_max_length() {
        int maxLength = 100;
        String value = testObj.maxLength(maxLength).value();
        assertThat(value.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    public void return_a_value_with_length_between_min_and_max() {
        int maxLength = 100;
        int minLength = 90;
        String value = testObj.minLength(minLength).maxLength(maxLength).value();
        assertThat(value.length()).isGreaterThanOrEqualTo(minLength);
        assertThat(value.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    public void return_a_single_character_between_min_and_max() {
        char minChar = 'a';
        char maxChar = 'z';
        String value = testObj.minChar(minChar).maxChar(maxChar).length(100).value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo(minChar);
            assertThat(c).isLessThanOrEqualTo(maxChar);
        }
    }

    @Test
    public void return_value_matching_pattern_single_literal_character() {
        String value = testObj.pattern("a").value();
        assertThat(value).isEqualTo("a");
    }

    @Test
    public void return_value_matching_pattern_literal_character_sequence() {
        String value = testObj.pattern("ab").value();
        assertThat(value).isEqualTo("ab");
    }

    @Test
    public void return_value_matching_pattern_single_character_from_set() {
        String value = testObj.pattern("[abc]{1}").value();
        assertThat(value).hasSize(1);
        assertThat("abc").contains(value);
    }

    @Test
    public void return_value_matching_pattern_single_character_from_range() {
        String value = testObj.pattern("[a-c]{1}").value();
        assertThat(value).hasSize(1);
        assertThat("abc").contains(value);
    }

    @Test
    public void return_value_matching_pattern_single_character_from_inverted_range() {
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
    public void return_value_matching_pattern_multiple_characters_from_range() {
        String value = testObj.pattern("[a-c]{3}").value();
        assertEquals(3, value.length());
        assertAllCharactersContainedIn("abc", value);
    }

    @Test
    public void return_value_matching_pattern_single_character_from_overlapping_ranges() {
        String value = testObj.pattern("[a-bb-c]{1}").value();
        assertThat("abc").contains(value);
    }

    @Test
    public void return_value_matching_pattern_sequence_length_more_than_1_digit() {
        String value = testObj.pattern("[a-c]{10}").value();
        assertEquals(10, value.length());
        assertAllCharactersContainedIn("abc", value);
    }

    @Test
    public void return_value_matching_pattern_sequence_length_between_min_and_max() {
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
    public void reject_pattern_terminated_unexpectedly() {
        checkPatternParsing("[a",
                "Unexpected end of pattern input, was expecting: ']', '-', a character");
    }

    @Test
    public void reject_pattern_terminated_expecting_character_range_max() {
        checkPatternParsing("[a-",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void reject_pattern_terminated_expecting_close_brace() {
        checkPatternParsing("[a-b", "Unexpected end of pattern input, was expecting: ']'");
    }

    @Test
    public void reject_pattern_terminated_unexpectedly_after_escaped_character() {
        checkPatternParsing("[\\[",
                "Unexpected end of pattern input, was expecting: ']', '-', a character");
    }

    @Test
    public void reject_pattern_without_sequence_length() {
        checkPatternParsing("[a-c]", "Unexpected end of pattern input, was expecting: '{'");
    }

    @Test
    public void reject_pattern_with_invalid_character_where_sequence_length_expected() {
        checkPatternParsing("[a]a",
                "Unexpected character after range definition, was expecting: '{'");
    }

    @Test
    public void reject_pattern_with_too_many_fields_in_sequence_length() {
        checkPatternParsing("[a-c]{1,1,1}",
                "Too many fields in pattern length definition, was expecting: 2 but got: 3");
    }

    @Test
    public void reject_pattern_with_not_a_number_in_sequence_length() {
        checkPatternParsing("[a]{a}", "Not-a-number in pattern length definition");
    }

    @Test
    public void reject_pattern_with_not_a_number_in_sequence_length_min() {
        checkPatternParsing("[a]{a,1}", "Not-a-number in pattern length definition");
    }

    @Test
    public void reject_pattern_with_not_a_number_in_sequence_length_max() {
        checkPatternParsing("[a]{1,a}", "Not-a-number in pattern length definition");
    }

    @Test
    public void reject_pattern_with_empty_sequence_length() {
        checkPatternParsing("[a]{}", "Missing value in pattern length definition");
    }

    @Test
    public void reject_pattern_with_missing_sequence_length_min() {
        checkPatternParsing("[a]{,1}", "Missing value in pattern length definition");
    }

    @Test
    public void reject_pattern_with_missing_sequence_length_max() {
        checkPatternParsing("[a]{1,}", "Missing value in pattern length definition");
    }

    @Test
    public void reject_pattern_terminated_expecting_sequence_length_value() {
        checkPatternParsing("[a]{",
                "Unexpected end of pattern input, was expecting: a number");
    }

    @Test
    public void reject_pattern_terminated_expecting_close_curly_brace_or_sequence_length_range() {
        checkPatternParsing("[a]{1",
                "Unexpected end of pattern input, was expecting: ',' '}', a number");
    }

    @Test
    public void reject_pattern_terminated_expecting_character() {
        checkPatternParsing("[\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void reject_pattern_terminated_expecting_max_character() {
        checkPatternParsing("[a-\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void reject_pattern_with_escape_character_in_literal_sequence_at_end() {
        checkPatternParsing("\\",
                "Unexpected end of pattern input, was expecting: a character");
    }

    @Test
    public void return_pattern_matching_escaped_literal_character() {
        String value = testObj.pattern("\\[").value();
        assertThat(value).isEqualTo("[");
    }

    @Test
    public void return_pattern_matching_set_of_escaped_literal_characters() {
        String value = testObj.pattern("[\\[\\]]{10}").value();
        assertAllCharactersContainedIn("[]", value);
    }

    @Test
    public void return_pattern_including_escaped_range_definition_character() {
        String value = testObj.pattern("[a\\-]{10}").value();
        assertAllCharactersContainedIn("-a", value);
    }

    @Test
    public void return_pattern_of_sequence_where_range_maximum_is_escaped_character() {
        String value = testObj.pattern("[a-\\]]{10}").value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo(']');
            assertThat(c).isLessThanOrEqualTo('a');
        }
    }

    @Test
    public void return_pattern_of_sequence_where_range_min_and_max_are_escaped_characters() {
        String value = testObj.pattern("[\\a-\\z]{100}").value();
        for (char c : value.toCharArray()) {
            assertThat(c).isGreaterThanOrEqualTo('a');
            assertThat(c).isLessThanOrEqualTo('z');
        }
    }

    @Ignore("Run this test to see the effect of various patterns")
    @Test
    public void return_random_strings_of_various_patterns() {
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