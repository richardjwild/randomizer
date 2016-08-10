package com.github.richardjwild.randomizer.types;

import com.github.richardjwild.randomizer.Randomizer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class StringRandomizer extends Randomizer<String> {

    private static Charset charset = Charset.forName("UTF-8");

    private Integer length = null;

    @Override
    public String value() {
        if (length == null || length <= 0)
            throw new IllegalArgumentException();
        return randomString();
    }

    private String randomString() {
        byte[] bytes = randomBytes();
        clearTopBits(bytes);
        excludeNonPrintingCharacters(bytes);
        return decodeUtf8String(bytes);
    }

    private byte[] randomBytes() {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private void clearTopBits(byte[] bytes) {
        for (int i=0; i<bytes.length; i++)
            bytes[i] &= 0x7f;
    }

    private void excludeNonPrintingCharacters(byte[] bytes) {
        for (int i=0; i<bytes.length; i++)
            if (bytes[i] < 32)
                bytes[i] += 32;
    }

    private String decodeUtf8String(byte[] bytes) {
        CharBuffer charBuffer = CharBuffer.allocate(length);
        CharsetDecoder decoder = charset.newDecoder();
        decoder.decode(ByteBuffer.wrap(bytes), charBuffer, true);
        decoder.flush(charBuffer);
        charBuffer.flip();
        return charBuffer.toString();
    }

    @Override
    public Randomizer<String> length(int length) {
        this.length = length;
        return this;
    }
}
