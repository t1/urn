package com.github.t1.urn;

import static com.github.t1.urn.UrnParser.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UrnDecodingTest {
    @Test
    public void shouldDecodeSimpleString() {
        String decoded = decodeNss("simple");

        assertEquals("simple", decoded);
    }

    @Test
    public void shouldDecodeSimpleHexEncoding() {
        String decoded = decodeNss("%20");

        assertEquals(" ", decoded);
    }

    @Test
    public void shouldDecodeUmlautHexEncoding() {
        String decoded = decodeNss("%20");

        assertEquals(" ", decoded);
    }
}
