package com.github.t1.urn;

import org.junit.jupiter.api.Test;

import static com.github.t1.urn.UrnPrinter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UrnEncodingTest {
    @Test void shouldEncodeSimpleString() {
        String encoded = encodeNss("simple");

        assertEquals("simple", encoded);
    }

    @Test void shouldEncodeSimpleHex() {
        String encoded = encodeNss(" ");

        assertEquals("%20", encoded);
    }

    @Test void shouldEncodeEmbeddedHex() {
        String encoded = encodeNss("- -");

        assertEquals("-%20-", encoded);
    }

    @Test void shouldEncodeThreeHex() {
        String encoded = encodeNss("{ }");

        assertEquals("%7b%20%7d", encoded);
    }

    @Test void shouldEncodeUmlaut() {
        String encoded = encodeNss("äöü");

        assertEquals("%c3%a4%c3%b6%c3%bc", encoded);
    }
}
