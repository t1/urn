package com.github.t1.urn;

import static java.util.Locale.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.experimental.theories.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class UrnTest {
    @DataPoints("validPrefixes")
    public static String[] validPrefixes = { "urn:", "URN:", "uRn:", "urN:" };
    @DataPoints("invalidPrefixes")
    public static String[] invalidPrefixes = { "", ":", "urx:", "::", "1", "*" };

    @DataPoints("validNids")
    public static String[] validNids = { "nid", "NiD", "nId", "ab", "a1", "1a", "a-z", "a-" };
    @DataPoints("invalidNids")
    public static String[] invalidNids = { "", ":", "a", "-a", "a b", "(", ")", "[", "]", "äöü", "::", "urn",
            "the-urn-is-too-long-the-max-is-32" };

    @DataPoints("validNsss")
    public static String[] validNsss = { "nss", "NsS", "nSs", "()+,-.:=@;$_!*'", "-" };
    @DataPoints("invalidNsss")
    public static String[] invalidNsss = { "", " ", "\n", "\r", "\t", "/", "\\", "|", "[", "]", "{", "}" };
    @DataPoints("validEscapedNsss")
    public static String[][] validEscapedNsss = { //
            { "%20", " " }, { "-%20-", "- -" }, { "%25", "%" }, { "%0D", "\r" }, { "%0a", "\n" } };
    @DataPoints("invalidEscapedNsss")
    public static String[] invalidEscapedNsss = { "%", "%1", "%am", "%-10", "%00" };

    // <other> ::= "(" | ")" | "+" | "," | "-" | "." |
    // ":" | "=" | "@" | ";" | "$" |
    // "_" | "!" | "*" | "'"

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private void expectInvalidUrn() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid urn");
    }

    @Theory
    public void shouldParseValidPrefix(@FromDataPoints("validPrefixes") String prefix) {
        String string = prefix + "nid:nss";

        URN urn = URN.parse(string);

        assertEquals(string.toLowerCase(US), urn.toString());
    }

    @Theory
    public void shouldFailToParseInvalidPrefix(@FromDataPoints("invalidPrefixes") String prefix) {
        expectInvalidUrn();

        URN.parse(prefix + "nid:nss");
    }

    @Theory
    public void shouldParseValidNids(@FromDataPoints("validNids") String nid) {
        String string = "urn:" + nid + ":nss";
        URN urn = URN.parse(string);

        assertEquals(nid.toLowerCase(US), urn.getNamespaceIdentifier());
        assertEquals(string.toLowerCase(US), urn.toString());
    }

    @Theory
    public void shouldFailToParseInvalidNids(@FromDataPoints("invalidNids") String nid) {
        expectInvalidUrn();

        URN.parse("urn:" + nid + ":nss");
    }

    @Theory
    public void shouldParseValidNsss(@FromDataPoints("validNsss") String nss) {
        String string = "urn:nid:" + nss;

        URN urn = URN.parse(string);

        assertEquals(nss, urn.getNamespaceSpecificString());
        assertEquals(string, urn.toString());
    }

    @Theory
    public void shouldFailToParseInvalidNsss(@FromDataPoints("invalidNsss") String nss) {
        expectInvalidUrn();

        URN.parse("urn:nid:" + nss);
    }

    @Theory
    public void shouldParseValidEscapedNsss(@FromDataPoints("validEscapedNsss") String[] nss) {
        String string = "urn:nid:" + nss[0];

        URN urn = URN.parse(string);

        assertEquals(nss[1], urn.getNamespaceSpecificString());
        assertEquals(string.toLowerCase(US), urn.toString());
    }

    @Theory
    @Ignore
    public void shouldFailToParseInvalidEscapedNsss(@FromDataPoints("invalidEscapedNsss") String nss) {
        expectInvalidUrn();

        URN.parse("urn:nid:" + nss);
    }
}
