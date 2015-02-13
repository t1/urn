package com.github.t1.urn;

import static java.util.Locale.*;
import static java.util.regex.Pattern.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.*;

class UrnParser {
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final Charset ASCII = Charset.forName("ASCII");

    static final String NSS_CHARS = "a-z0-9()+,\\-.:=@;$_!*'";

    private static final Pattern URN_PATTERN = Pattern.compile( //
            "urn:" //
                    + "(?<NID>[a-z0-9][a-z0-9-]{1,31}):" //
                    + "(?<NSS>[" + NSS_CHARS + "%]+)", //
            CASE_INSENSITIVE);

    private final Matcher matcher;

    public UrnParser(String string) {
        this.matcher = URN_PATTERN.matcher(string);
        if (!matcher.matches())
            throw new IllegalArgumentException("invalid urn");
    }

    public String nid() {
        String nid = matcher.group("NID").toLowerCase(US);
        if ("urn".equals(nid))
            throw new IllegalArgumentException("invalid urn");
        return nid;
    }

    public String nss() {
        return decodeNss(matcher.group("NSS"));
    }

    static String decodeNss(String string) {
        byte[] bytes = string.getBytes(ASCII);
        int maxSize = bytes.length; // just can get smaller, but this is the max
        ByteBuffer out = ByteBuffer.allocate(maxSize);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == '%') {
                ByteBuffer hex = ByteBuffer.allocate(2);
                hex.put(bytes[++i]);
                hex.put(bytes[++i]);
                b = Byte.parseByte(new String(hex.array()), 16);
            }
            out.put(b);
        }
        return new String(out.array(), 0, out.position(), UTF_8);
    }
}
