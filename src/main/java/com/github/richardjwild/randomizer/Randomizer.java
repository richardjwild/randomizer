package com.github.richardjwild.randomizer;

import com.github.richardjwild.randomizer.localization.Messages;
import com.github.richardjwild.randomizer.validation.NoRandomizerFoundException;

import java.util.Random;

import static com.github.richardjwild.randomizer.localization.Messages.getMessage;
import static java.util.Optional.ofNullable;

/**
 * Creates randomized values suitable for use as test data in automated tests.<p>
 * Usage: <code>&lt;Type&gt; randomValue = Randomizer.forType(&lt;Type&gt;.class).value();</code><p>
 * The class implements the builder pattern to allow boundaries (constraints) to be specified on the generated random
 * value. These constraints are: <code>max</code>, <code>min</code>, <code>length</code>, <code>minLength</code>,
 * <code>maxLength</code>, <code>minChar</code>, <code>maxChar</code> and <code>scale</code>. Not all constraints are
 * applicable to all supported types: if a constraint method is called for a type it is not applicable to, an
 * <code>UnsupportedOperationException</code> will be thrown.
 * @param <T> The type of randomized value to be generated.
 */
public abstract class Randomizer<T> {

    private static RandomizerFactory randomizerFactory = new RandomizerFactory();

    /**
     * Gets a {@link Randomizer} for the specified type.
     * @param type The class of the type for which a random value is to be generated.
     * @param <T> The type of the random value to be generated.
     * @return A {@link Randomizer} for the specified type.
     * @throws IllegalArgumentException The <code>type</code> parameter was passed a null reference.
     * @throws NoRandomizerFoundException No randomizer is implemented for the requested type.
     */
    public static <T> Randomizer<T> forType(Class<T> type) {
        return ofNullable(type)
                .map(randomizerFactory::create)
                .orElseThrow(() -> new IllegalArgumentException(getMessage("randomizer.type.null")));
    }

    protected final Random random = new Random();

    /**
     * Gets the generated random value for the specified type, within any specified constraints.
     * @return The generated random value.
     */
    public abstract T value();

    /**
     * Sets the maximum boundary for the generated random value. The maximum is inclusive, i.e. the generated value may
     * be less than or equal to the maximum boundary.
     * @param maxValue The maximum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support maximum boundaries.
     */
    public Randomizer<T> max(T maxValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the minimum boundary for the generated random value. The minimum is inclusive, i.e. the generated value may
     * be greater than or equal to the minimum boundary.
     * @param minValue The minimum bound for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support minimum boundaries.
     */
    public Randomizer<T> min(T minValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the required length for the generated random value. This is only applicable to type String, for which it is
     * mandatory unless <code>maxLength</code> is specified instead. It is not allowed to specify both length and
     * maxLength at the same time.
     * @param length The required length for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support length.
     */
    public Randomizer<T> length(int length) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maximum length for the generated random value. This is only applicable to type String, for which it is
     * mandatory unless <code>length</code> is specified instead. It is not permitted to specify both maxLength and
     * length at the same time.
     * @param maxLength The maximum length for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support length.
     */
    public Randomizer<T> maxLength(int maxLength) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the minimum length for the generated random value. This is only applicable to type String. It is not
     * permitted to specify both minLength and length at the same time.
     * @param minLength The maximum length for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support length.
     */
    public Randomizer<T> minLength(int minLength) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the required scale for the generated random value. This is only applicable to type BigDecimal.
     * @param scale The required scale for the generated random value.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support scale.
     */
    public Randomizer<T> scale(int scale) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the minimum value for every character in the generated random value. This is only applicable to type String.
     * @param minChar The minimum character value that will appear in the generated string.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support minChar.
     */
    public Randomizer<T> minChar(char minChar) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maximum value for every character in the generated random value. This is only applicable to type String.
     * @param maxChar The maximum character value that will appear in the generated string.
     * @return A reference to the <code>Randomizer</code> instance so that method calls can be chained.
     * @throws UnsupportedOperationException The randomizer for the requested type does not support maxChar.
     */
    public Randomizer<T> maxChar(char maxChar) {
        throw new UnsupportedOperationException();
    }

    public Randomizer<T> pattern(String pattern) {
        throw new UnsupportedOperationException();
    }
}
