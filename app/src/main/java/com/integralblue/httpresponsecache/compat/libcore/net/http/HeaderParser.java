package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;

public final class HeaderParser {

    public interface CacheControlHandler {
        void handle(String str, String str2);
    }

    public static void parseCacheControl(String value, CacheControlHandler handler) {
        int pos = 0;
        while (pos < value.length()) {
            int tokenStart = pos;
            pos = skipUntil(value, pos, "=,");
            String directive = value.substring(tokenStart, pos).trim();
            if (pos == value.length() || value.charAt(pos) == StringUtil.COMMA) {
                pos++;
                handler.handle(directive, null);
            } else {
                String parameter;
                pos = skipWhitespace(value, pos + 1);
                int parameterStart;
                if (pos >= value.length() || value.charAt(pos) != StringUtil.DOUBLE_QUOTE) {
                    parameterStart = pos;
                    pos = skipUntil(value, pos, MiPushClient.ACCEPT_TIME_SEPARATOR);
                    parameter = value.substring(parameterStart, pos).trim();
                } else {
                    pos++;
                    parameterStart = pos;
                    pos = skipUntil(value, pos, "\"");
                    parameter = value.substring(parameterStart, pos);
                    pos++;
                }
                handler.handle(directive, parameter);
            }
        }
    }

    public static List<Challenge> parseChallenges(RawHeaders responseHeaders, String challengeHeader) {
        List<Challenge> result = new ArrayList();
        for (int h = 0; h < responseHeaders.length(); h++) {
            if (challengeHeader.equalsIgnoreCase(responseHeaders.getFieldName(h))) {
                String value = responseHeaders.getValue(h);
                int pos = 0;
                while (pos < value.length()) {
                    int tokenStart = pos;
                    pos = skipUntil(value, pos, " ");
                    String scheme = value.substring(tokenStart, pos).trim();
                    pos = skipWhitespace(value, pos);
                    if (value.regionMatches(pos, "realm=\"", 0, "realm=\"".length())) {
                        pos += "realm=\"".length();
                        int realmStart = pos;
                        pos = skipUntil(value, pos, "\"");
                        String realm = value.substring(realmStart, pos);
                        pos = skipWhitespace(value, skipUntil(value, pos + 1, MiPushClient.ACCEPT_TIME_SEPARATOR) + 1);
                        result.add(new Challenge(scheme, realm));
                    }
                }
            }
        }
        return result;
    }

    private static int skipUntil(String input, int pos, String characters) {
        while (pos < input.length() && characters.indexOf(input.charAt(pos)) == -1) {
            pos++;
        }
        return pos;
    }

    private static int skipWhitespace(String input, int pos) {
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c != HttpConstants.SP_CHAR && c != '\t') {
                break;
            }
            pos++;
        }
        return pos;
    }

    public static int parseSeconds(String value) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (seconds < 0) {
                return 0;
            }
            return (int) seconds;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
