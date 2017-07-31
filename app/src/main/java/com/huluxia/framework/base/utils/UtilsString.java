package com.huluxia.framework.base.utils;

import java.util.Vector;
import java.util.regex.Pattern;
import org.bytedeco.javacpp.avcodec;

public class UtilsString {
    private static final Pattern COLOR_PATTERN = Pattern.compile("^\\#[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");
    static final long HMULT = 7664345821815920749L;
    static final long HSTART = -4953706369002393500L;
    public static final boolean IGNORE_CASE = true;
    public static final boolean IGNORE_WIDTH = true;
    private static final Pattern LETTER_PATTERN = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d*$");
    static final long[] byteTable = createLookupTable();

    private static final long[] createLookupTable() {
        long[] byteTable = new long[256];
        long h = 6074001001750140548L;
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 31; j++) {
                h ^= h >>> 7;
                h ^= h << 11;
                h ^= h >>> 10;
            }
            byteTable[i] = h;
        }
        return byteTable;
    }

    public static long hashCode(CharSequence cs) {
        long h = HSTART;
        long[] ht = byteTable;
        int len = cs.length();
        for (int i = 0; i < len; i++) {
            char ch = cs.charAt(i);
            h = (((h * HMULT) ^ ht[ch & 255]) * HMULT) ^ ht[(ch >>> 8) & 255];
        }
        return h;
    }

    public static boolean isAllWhitespaces(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllDigits(String str) {
        if (UtilsFunction.empty((CharSequence) str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean equal(String s1, String s2) {
        return equal(s1, s2, false);
    }

    public static boolean equal(String s1, String s2, boolean ignoreCase) {
        if (s1 == null || s2 == null) {
            return s1 == null && s2 == null;
        } else {
            if (ignoreCase) {
                return s1.equalsIgnoreCase(s2);
            }
            return s1.equals(s2);
        }
    }

    public static Vector<String> parseMediaUrls(String str, String beginTag, String endTag) {
        Vector<String> list = new Vector();
        if (!UtilsFunction.empty((CharSequence) str)) {
            int beginIndex = str.indexOf(beginTag, 0);
            int endIndex = str.indexOf(endTag, 0);
            while (beginIndex != -1 && endIndex != -1 && endIndex > beginIndex) {
                CharSequence imgUrl = str.substring(beginIndex + beginTag.length(), endIndex);
                if (!(UtilsFunction.empty(imgUrl) || imgUrl.charAt(0) == '[')) {
                    list.add(imgUrl);
                }
                endIndex += endTag.length() + endIndex;
                beginIndex = str.indexOf(beginTag, endIndex);
                endIndex = str.indexOf(endTag, endIndex);
            }
        }
        return list;
    }

    public static int find(String pattern, String s) {
        return find(pattern, s, false);
    }

    public static int find(String pattern, String s, boolean ignoreCase) {
        return find(pattern, s, ignoreCase, false);
    }

    public static int find(String pattern, String s, boolean ignoreCase, boolean ignoreWidth) {
        if (UtilsFunction.empty((CharSequence) s)) {
            return -1;
        }
        pattern = UtilsFunction.ref(pattern);
        if (ignoreCase) {
            pattern = pattern.toLowerCase();
            s = s.toLowerCase();
        }
        if (ignoreWidth) {
            pattern = narrow(pattern);
            s = narrow(s);
        }
        return s.indexOf(pattern);
    }

    public static String narrow(String s) {
        if (UtilsFunction.empty((CharSequence) s)) {
            return "";
        }
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            cs[i] = narrow(cs[i]);
        }
        return new String(cs);
    }

    public static char narrow(char c) {
        char code = c;
        if (code >= '！' && code <= '｝') {
            return (char) (code - 65248);
        }
        if (code == avcodec.MB_TYPE_L0) {
            return (char) ((code - 12288) + 32);
        }
        if (code == '｡') {
            return '。';
        }
        if (code == '・') {
            return '·';
        }
        if (code == '•') {
            return '·';
        }
        if (code == ' ') {
            return '\n';
        }
        return c;
    }

    public static int ord(char c) {
        if ('a' <= c && c <= 'z') {
            return c;
        }
        if ('A' > c || c > 'Z') {
            return '\u0000';
        }
        return (c - 65) + 97;
    }

    public static boolean validEmail(String email) {
        if (EMAIL_PATTERN.matcher(email).find()) {
            return true;
        }
        return false;
    }

    public static boolean validNumber(String str) {
        if (str == null || str.length() == 0 || !NUMBER_PATTERN.matcher(str).find()) {
            return false;
        }
        return true;
    }

    public static boolean validColor(String color) {
        if (color != null && COLOR_PATTERN.matcher(color).find()) {
            return true;
        }
        return false;
    }

    public static boolean validPhone(String str) {
        if (!validNumber(str)) {
            return false;
        }
        try {
            long phone = Long.valueOf(str).longValue();
            if (phone <= 10000000000L || phone >= 20000000000L) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validPwd(String str) {
        if (str == null || str.length() == 0 || validNumber(str) || LETTER_PATTERN.matcher(str).find()) {
            return false;
        }
        return true;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(simpleName(self.getClass()), null);
    }

    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(simpleName(clazz), null);
    }

    public static ToStringHelper toStringHelper(String className) {
        return new ToStringHelper(className, null);
    }

    private static String simpleName(Class<?> clazz) {
        String name = clazz.getName().replaceAll("\\$[0-9]+", "\\$");
        int start = name.lastIndexOf(36);
        if (start == -1) {
            start = name.lastIndexOf(46);
        }
        return name.substring(start + 1);
    }

    public static String humanReadableByteCount(int bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        String pre = "KMGTPE".charAt(((int) (Math.log((double) bytes) / Math.log((double) unit))) - 1) + "";
        return String.format("%.1f %sB", new Object[]{Double.valueOf(((double) bytes) / Math.pow((double) unit, (double) ((int) (Math.log((double) bytes) / Math.log((double) unit))))), pre});
    }

    public static String GetProgSizeText(int cur, int max) {
        String curText = humanReadableByteCount(cur, false);
        return curText + "/" + humanReadableByteCount(max, false);
    }

    public static String getHttpDomain(String url) {
        if (url == null || url.length() <= 0) {
            return null;
        }
        String[] strs = url.split("/");
        if (strs == null || strs.length <= 2) {
            return null;
        }
        return strs[2];
    }
}
