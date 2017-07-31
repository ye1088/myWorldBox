package com.tencent.mm.platformtools;

public class CnToSpell {
    public static String getFullSpell(String str) {
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        char[] toCharArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (char spell : toCharArray) {
            String spell2 = SpellMap.getSpell(spell);
            if (spell2 != null) {
                stringBuffer.append(spell2);
            }
        }
        return stringBuffer.toString();
    }

    public static String getInitial(String str) {
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        char[] toCharArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        int length = toCharArray.length;
        for (int i = 0; i < length; i++) {
            if (!Character.isSpace(toCharArray[i])) {
                String spell = SpellMap.getSpell(toCharArray[i]);
                if (spell != null && spell.length() > 0) {
                    stringBuffer.append(spell.charAt(0));
                }
            }
        }
        return stringBuffer.toString().toUpperCase();
    }
}
