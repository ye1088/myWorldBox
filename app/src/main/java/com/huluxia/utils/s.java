package com.huluxia.utils;

import java.lang.Character.UnicodeBlock;
import java.util.regex.Pattern;

/* compiled from: UtilsCode */
public class s {
    private static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        if (ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    public static boolean fy(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean fz(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("[\\u4E00-\\u9FBF]+").matcher(str.trim()).find();
    }

    public static boolean fA(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("\\p{InCJK Unified Ideographs}&&\\P{Cn}").matcher(str.trim()).find();
    }
}
