package com.github.t1.urn;

import static com.github.t1.urn.UrnPrinter.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UrnEncodingTest {
    @Test
    public void shouldEncodeSimpleString() {
        String encoded = encodeNss("simple");

        assertEquals("simple", encoded);
    }

    @Test
    public void shouldEncodeSimpleHex() {
        String encoded = encodeNss(" ");

        assertEquals("%20", encoded);
    }

    @Test
    public void shouldEncodeEmbeddedHex() {
        String encoded = encodeNss("- -");

        assertEquals("-%20-", encoded);
    }

    @Test
    public void shouldEncodeThreeHex() {
        String encoded = encodeNss("{ }");

        assertEquals("%7b%20%7d", encoded);
    }

    @Test
    public void shouldEncodeUmlaut() {
        String encoded = encodeNss("äöü");

        assertEquals("%c3%a4%c3%b6%c3%bc", encoded);
    }
}
