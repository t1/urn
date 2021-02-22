package com.github.t1.urn;

import org.junit.jupiter.api.Test;

import static com.github.t1.urn.UrnParser.decodeNss;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UrnDecodingTest {
    @Test void shouldDecodeSimpleString() {
        String decoded = decodeNss("simple");

        assertEquals("simple", decoded);
    }

    @Test void shouldDecodeSimpleHexEncoding() {
        String decoded = decodeNss("%20");

        assertEquals(" ", decoded);
    }

    @Test void shouldDecodeUmlautHexEncoding() {
        String decoded = decodeNss("%20");

        assertEquals(" ", decoded);
    }
}
