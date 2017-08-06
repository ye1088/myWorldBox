package com.huluxia.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import com.huluxia.framework.base.utils.UtilsFunction;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.StringUtil;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"DefaultLocale"})
/* compiled from: UtilsString */
public class aw {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d*$");
    private static final int bne = 16;

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

    public static String T(String color, int number) {
        return String.format("<font color=\"%s\"><b>%d</b></font>", new Object[]{color, Integer.valueOf(number)});
    }

    public static String b(String color, String text, boolean isBigger) {
        if (isBigger) {
            return String.format("<font color=\"%s\"><b>%s</b></font>", new Object[]{color, text});
        }
        return String.format("<font color=\"%s\">%s</font>", new Object[]{color, text});
    }

    public static String gm(String in) {
        if (in == null || in.length() <= 4) {
            return in;
        }
        byte[] head = in.getBytes();
        if (head[0] == (byte) -17 || head[1] == (byte) -69 || head[2] == (byte) -65) {
            return in.substring(1);
        }
        return in;
    }

    public static String e(ByteBuffer buff) {
        String strResult = "";
        if (buff.remaining() < 2) {
            return strResult;
        }
        short size = buff.getShort();
        if (buff.remaining() < size) {
            return strResult;
        }
        byte[] strText = new byte[size];
        buff.get(strText, 0, size);
        return new String(strText);
    }

    public static int U(String value, int defaultValue) {
        try {
            defaultValue = Integer.valueOf(value).intValue();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static long h(String value, long defaultValue) {
        try {
            defaultValue = Long.valueOf(value).longValue();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static int V(String value, int defaultValue) {
        try {
            defaultValue = Integer.parseInt(value, 16);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static long i(String value, long defaultValue) {
        try {
            defaultValue = Long.parseLong(value, 16);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static float d(String value, float defaultValue) {
        try {
            defaultValue = Float.valueOf(value).floatValue();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static double a(String value, double defaultValue) {
        try {
            defaultValue = Double.valueOf(value).doubleValue();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static int[] au(String value, String sub) {
        String[] strarray = value.split(sub);
        int[] uzInput = new int[strarray.length];
        for (int n = 0; n < strarray.length; n++) {
            uzInput[n] = U(strarray[n], 0);
        }
        return uzInput;
    }

    public static String gn(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a_isRightVersion', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : mdInst.digest()) {
                int i = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = i + 1;
                str[i] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean validEmail(String email) {
        if (EMAIL_PATTERN.matcher(email).find()) {
            return true;
        }
        return false;
    }

    public static boolean validNumber(String str) {
        if (NUMBER_PATTERN.matcher(str).find()) {
            return true;
        }
        return false;
    }

    public static String W(String text, int nlimit) {
        String result = text;
        if (text == null) {
            return "";
        }
        if (nlimit >= text.length()) {
            return result;
        }
        return text.substring(0, nlimit) + "..";
    }

    public static String X(String text, int nlimit) {
        String result = text;
        if (nlimit >= text.length()) {
            return result;
        }
        return text.substring(0, nlimit);
    }

    public static String d(String text, String trimTxt, int nlimit) {
        String result = text;
        if (text.startsWith(trimTxt) && text.length() > trimTxt.length()) {
            result = text.substring(trimTxt.length());
            if (nlimit < result.length()) {
                return result.substring(0, nlimit);
            }
            return result;
        } else if (nlimit < result.length()) {
            return result.substring(0, nlimit);
        } else {
            return result;
        }
    }

    public static String go(String nick) {
        if (UtilsFunction.empty((CharSequence) nick)) {
            return null;
        }
        return W(nick, 16);
    }

    public static String gp(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static String gq(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    public static boolean gr(String IP) {
        IP = gq(IP);
        if (!IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            return false;
        }
        String[] s = IP.split("\\.");
        if (Integer.parseInt(s[0]) >= 255 || Integer.parseInt(s[1]) >= 255 || Integer.parseInt(s[2]) >= 255 || Integer.parseInt(s[3]) >= 255) {
            return false;
        }
        return true;
    }

    public static boolean isHttpUrl(String content) {
        if (UtilsFunction.empty((CharSequence) content)) {
            return false;
        }
        if (content.indexOf("http://") >= 0 || content.indexOf("https://") >= 0) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> gs(String content) {
        ArrayList<String> urlList = new ArrayList();
        Matcher matcher = Pattern.compile("(http://|ftp://|https://|www)[^一-龥\\s]*?[^一-龥\\s]*").matcher(content);
        while (matcher.find()) {
            urlList.add(matcher.group(0));
        }
        return urlList;
    }

    public static String gt(String content) {
        String code = null;
        String txt = "360云盘提取码(";
        if (content.length() <= txt.length() || !content.startsWith(txt)) {
            return null;
        }
        Matcher m = Pattern.compile("(^[a-zA-Z0-9]+\\))?").matcher(content.substring(txt.length()));
        if (m.find()) {
            code = m.group(0);
            if (code != null && code.length() > 2) {
                code = code.substring(0, code.length() - 1);
            }
        }
        return code;
    }

    public static String bz(long bytes) {
        return q(bytes, 2);
    }

    public static String q(long bytes, int scale) {
        BigDecimal filesize = new BigDecimal(bytes);
        float returnValue = filesize.divide(new BigDecimal(1048576), scale, 0).floatValue();
        if (returnValue > 1.0f) {
            return returnValue + "MB";
        }
        return filesize.divide(new BigDecimal(1024), scale, 0).floatValue() + "KB";
    }

    public static String bA(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        float returnValue = filesize.divide(new BigDecimal(1048576), 0, 0).floatValue();
        if (returnValue > 1.0f) {
            return ((int) returnValue) + "MB";
        }
        return ((int) filesize.divide(new BigDecimal(1024), 0, 0).floatValue()) + "KB";
    }

    public static String gu(String src) {
        if (src == null) {
            return null;
        }
        return String.format("%s_160x0.jpeg", new Object[]{src});
    }

    public static String gv(String src) {
        if (src == null) {
            return null;
        }
        return String.format("%s_320x0.jpeg", new Object[]{src});
    }

    public static String gw(String source) {
        if (source == null) {
            return "";
        }
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (b(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    public static boolean gx(String source) {
        boolean isInclude = false;
        if (source == null) {
            return 0;
        }
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if (b(source.charAt(i))) {
                isInclude = true;
                break;
            }
        }
        return isInclude;
    }

    private static boolean b(char codePoint) {
        return codePoint == '\u0000' || codePoint == '\t' || codePoint == '\n' || codePoint == StringUtil.CARRIAGE_RETURN || ((codePoint >= HttpConstants.SP_CHAR && codePoint <= '퟿') || ((codePoint >= '' && codePoint <= '�') || (codePoint >= '\u0000' && codePoint <= '￿')));
    }

    public static String H(float speed) {
        if (speed > 1048576.0f) {
            return ((int) (speed / 1048576.0f)) + "m/s";
        }
        if (speed > 1024.0f) {
            return ((int) (speed / 1024.0f)) + "kb/s";
        }
        return ((int) speed) + "b/s";
    }

    public static int Y(String colorStr, int defaultValue) {
        try {
            return Color.parseColor(colorStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
