package com.github.t1.urn;

import static com.github.t1.urn.UrnPrinter.*;
import lombok.*;

/**
 * Uniform Resource Names (URNs) are intended to serve as persistent, location-independent, resource identifiers.
 * 
 * @see <a href="http://tools.ietf.org/html/rfc2141">rfc-2141-</a>
 */
@Value
@AllArgsConstructor
public class URN {
    private final String namespaceIdentifier;
    private final String namespaceSpecificString;

    public static URN parse(String string) {
        UrnParser parser = new UrnParser(string);
        String nid = parser.nid();
        String nss = parser.nss();

        return new URN(nid, nss);
    }

    @Override
    public String toString() {
        return "urn:" + namespaceIdentifier + ":" + encodeNss(namespaceSpecificString);
    }
}
