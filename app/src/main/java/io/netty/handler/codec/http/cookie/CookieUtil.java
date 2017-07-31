package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import java.util.BitSet;

final class CookieUtil {
    private static final BitSet VALID_COOKIE_ATTRIBUTE_VALUE_OCTETS = validCookieAttributeValueOctets();
    private static final BitSet VALID_COOKIE_NAME_OCTETS = validCookieNameOctets();
    private static final BitSet VALID_COOKIE_VALUE_OCTETS = validCookieValueOctets();

    private static BitSet validCookieNameOctets() {
        BitSet bits = new BitSet();
        for (int i = 32; i < 127; i++) {
            bits.set(i);
        }
        for (int separator : new int[]{40, 41, 60, 62, 64, 44, 59, 58, 92, 34, 47, 91, 93, 63, 61, 123, 125, 32, 9}) {
            bits.set(separator, false);
        }
        return bits;
    }

    private static BitSet validCookieValueOctets() {
        int i;
        BitSet bits = new BitSet();
        bits.set(33);
        for (i = 35; i <= 43; i++) {
            bits.set(i);
        }
        for (i = 45; i <= 58; i++) {
            bits.set(i);
        }
        for (i = 60; i <= 91; i++) {
            bits.set(i);
        }
        for (i = 93; i <= 126; i++) {
            bits.set(i);
        }
        return bits;
    }

    private static BitSet validCookieAttributeValueOctets() {
        BitSet bits = new BitSet();
        for (int i = 32; i < 127; i++) {
            bits.set(i);
        }
        bits.set(59, false);
        return bits;
    }

    static StringBuilder stringBuilder() {
        return InternalThreadLocalMap.get().stringBuilder();
    }

    static String stripTrailingSeparatorOrNull(StringBuilder buf) {
        return buf.length() == 0 ? null : stripTrailingSeparator(buf);
    }

    static String stripTrailingSeparator(StringBuilder buf) {
        if (buf.length() > 0) {
            buf.setLength(buf.length() - 2);
        }
        return buf.toString();
    }

    static void add(StringBuilder sb, String name, long val) {
        sb.append(name);
        sb.append('=');
        sb.append(val);
        sb.append(';');
        sb.append(HttpConstants.SP_CHAR);
    }

    static void add(StringBuilder sb, String name, String val) {
        sb.append(name);
        sb.append('=');
        sb.append(val);
        sb.append(';');
        sb.append(HttpConstants.SP_CHAR);
    }

    static void add(StringBuilder sb, String name) {
        sb.append(name);
        sb.append(';');
        sb.append(HttpConstants.SP_CHAR);
    }

    static void addQuoted(StringBuilder sb, String name, String val) {
        if (val == null) {
            val = "";
        }
        sb.append(name);
        sb.append('=');
        sb.append(StringUtil.DOUBLE_QUOTE);
        sb.append(val);
        sb.append(StringUtil.DOUBLE_QUOTE);
        sb.append(';');
        sb.append(HttpConstants.SP_CHAR);
    }

    static int firstInvalidCookieNameOctet(CharSequence cs) {
        return firstInvalidOctet(cs, VALID_COOKIE_NAME_OCTETS);
    }

    static int firstInvalidCookieValueOctet(CharSequence cs) {
        return firstInvalidOctet(cs, VALID_COOKIE_VALUE_OCTETS);
    }

    static int firstInvalidOctet(CharSequence cs, BitSet bits) {
        for (int i = 0; i < cs.length(); i++) {
            if (!bits.get(cs.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    static CharSequence unwrapValue(CharSequence cs) {
        int len = cs.length();
        if (len <= 0 || cs.charAt(0) != StringUtil.DOUBLE_QUOTE) {
            return cs;
        }
        if (len < 2 || cs.charAt(len - 1) != StringUtil.DOUBLE_QUOTE) {
            return null;
        }
        return len == 2 ? "" : cs.subSequence(1, len - 1);
    }

    static String validateAttributeValue(String name, String value) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.isEmpty()) {
            return null;
        }
        int i = firstInvalidOctet(value, VALID_COOKIE_ATTRIBUTE_VALUE_OCTETS);
        if (i == -1) {
            return value;
        }
        throw new IllegalArgumentException(name + " contains the prohibited characters: " + value.charAt(i));
    }

    private CookieUtil() {
    }
}
