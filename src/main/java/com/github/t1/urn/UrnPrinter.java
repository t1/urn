package com.github.t1.urn;

import static com.github.t1.urn.UrnParser.*;
import static java.util.Locale.*;
import static java.util.regex.Pattern.*;

import java.nio.ByteBuffer;
import java.util.Formatter;
import java.util.regex.Pattern;

class UrnPrinter {
    private static final Pattern NSS_CHAR = Pattern.compile("[" + NSS_CHARS + "]", CASE_INSENSITIVE);

    static String encodeNss(String string) {
        byte[] bytes = string.getBytes(UTF_8);
        int maxSize = bytes.length * 3; // one escaped byte takes three characters
        ByteBuffer out = ByteBuffer.allocate(maxSize);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (!isAllowed(b)) {
                out.put((byte) '%');
                try (Formatter formatter = new Formatter(US)) {
                    String hex = formatter.format("%02x", b).toString();
                    out.put(hex.getBytes(ASCII));
                }
            } else {
                out.put(b);
            }
        }
        return new String(out.array(), 0, out.position(), UTF_8);
    }

    private static boolean isAllowed(byte b) {
        return NSS_CHAR.matcher(Character.toString((char) b)).matches();
    }
}
