package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public final class ServerCookieDecoder extends CookieDecoder {
    public static final ServerCookieDecoder LAX = new ServerCookieDecoder(false);
    private static final String RFC2965_DOMAIN = "$Domain";
    private static final String RFC2965_PATH = "$Path";
    private static final String RFC2965_PORT = "$Port";
    private static final String RFC2965_VERSION = "$Version";
    public static final ServerCookieDecoder STRICT = new ServerCookieDecoder(true);

    private ServerCookieDecoder(boolean strict) {
        super(strict);
    }

    public Set<Cookie> decode(String header) {
        int headerLen = ((String) ObjectUtil.checkNotNull(header, "header")).length();
        if (headerLen == 0) {
            return Collections.emptySet();
        }
        Set<Cookie> cookies = new TreeSet();
        int i = 0;
        boolean rfc2965Style = false;
        if (header.regionMatches(true, 0, RFC2965_VERSION, 0, RFC2965_VERSION.length())) {
            i = header.indexOf(59) + 1;
            rfc2965Style = true;
        }
        while (i != headerLen) {
            char c = header.charAt(i);
            if (c == '\t' || c == '\n' || c == '\u000b' || c == '\f' || c == StringUtil.CARRIAGE_RETURN || c == HttpConstants.SP_CHAR || c == StringUtil.COMMA || c == ';') {
                i++;
            } else {
                int nameBegin = i;
                int nameEnd = i;
                int valueBegin = -1;
                int valueEnd = -1;
                if (i != headerLen) {
                    do {
                        char curChar = header.charAt(i);
                        if (curChar == ';') {
                            nameEnd = i;
                            valueEnd = -1;
                            valueBegin = -1;
                            break;
                        } else if (curChar == '=') {
                            nameEnd = i;
                            i++;
                            if (i == headerLen) {
                                valueEnd = 0;
                                valueBegin = 0;
                            } else {
                                valueBegin = i;
                                int semiPos = header.indexOf(59, i);
                                if (semiPos > 0) {
                                    i = semiPos;
                                } else {
                                    i = headerLen;
                                }
                                valueEnd = i;
                            }
                        } else {
                            i++;
                        }
                    } while (i != headerLen);
                    nameEnd = headerLen;
                    valueEnd = -1;
                    valueBegin = -1;
                }
                if (rfc2965Style) {
                    if (!header.regionMatches(nameBegin, RFC2965_PATH, 0, RFC2965_PATH.length())) {
                        if (!header.regionMatches(nameBegin, RFC2965_DOMAIN, 0, RFC2965_DOMAIN.length())) {
                            if (header.regionMatches(nameBegin, RFC2965_PORT, 0, RFC2965_PORT.length())) {
                            }
                        }
                    }
                }
                DefaultCookie cookie = initCookie(header, nameBegin, nameEnd, valueBegin, valueEnd);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
        }
        return cookies;
    }
}
