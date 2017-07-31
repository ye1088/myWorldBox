package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpHeaderDateFormat;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.text.ParsePosition;
import java.util.Date;

public final class ClientCookieDecoder extends CookieDecoder {
    public static final ClientCookieDecoder LAX = new ClientCookieDecoder(false);
    public static final ClientCookieDecoder STRICT = new ClientCookieDecoder(true);

    private static class CookieBuilder {
        private final DefaultCookie cookie;
        private String domain;
        private int expiresEnd;
        private int expiresStart;
        private final String header;
        private boolean httpOnly;
        private long maxAge = Long.MIN_VALUE;
        private String path;
        private boolean secure;

        public CookieBuilder(DefaultCookie cookie, String header) {
            this.cookie = cookie;
            this.header = header;
        }

        private long mergeMaxAgeAndExpires() {
            int i = 0;
            if (this.maxAge != Long.MIN_VALUE) {
                return this.maxAge;
            }
            String expires = computeValue(this.expiresStart, this.expiresEnd);
            if (expires != null) {
                Date expiresDate = HttpHeaderDateFormat.get().parse(expires, new ParsePosition(0));
                if (expiresDate != null) {
                    long maxAgeMillis = expiresDate.getTime() - System.currentTimeMillis();
                    long j = maxAgeMillis / 1000;
                    if (maxAgeMillis % 1000 != 0) {
                        i = 1;
                    }
                    return ((long) i) + j;
                }
            }
            return Long.MIN_VALUE;
        }

        public Cookie cookie() {
            this.cookie.setDomain(this.domain);
            this.cookie.setPath(this.path);
            this.cookie.setMaxAge(mergeMaxAgeAndExpires());
            this.cookie.setSecure(this.secure);
            this.cookie.setHttpOnly(this.httpOnly);
            return this.cookie;
        }

        public void appendAttribute(int keyStart, int keyEnd, int valueStart, int valueEnd) {
            int length = keyEnd - keyStart;
            if (length == 4) {
                parse4(keyStart, valueStart, valueEnd);
            } else if (length == 6) {
                parse6(keyStart, valueStart, valueEnd);
            } else if (length == 7) {
                parse7(keyStart, valueStart, valueEnd);
            } else if (length == 8) {
                parse8(keyStart, valueStart, valueEnd);
            }
        }

        private void parse4(int nameStart, int valueStart, int valueEnd) {
            if (this.header.regionMatches(true, nameStart, CookieHeaderNames.PATH, 0, 4)) {
                this.path = computeValue(valueStart, valueEnd);
            }
        }

        private void parse6(int nameStart, int valueStart, int valueEnd) {
            if (this.header.regionMatches(true, nameStart, CookieHeaderNames.DOMAIN, 0, 5)) {
                this.domain = computeValue(valueStart, valueEnd);
                return;
            }
            if (this.header.regionMatches(true, nameStart, CookieHeaderNames.SECURE, 0, 5)) {
                this.secure = true;
            }
        }

        private void setMaxAge(String value) {
            try {
                this.maxAge = Math.max(Long.parseLong(value), 0);
            } catch (NumberFormatException e) {
            }
        }

        private void parse7(int nameStart, int valueStart, int valueEnd) {
            if (this.header.regionMatches(true, nameStart, "Expires", 0, 7)) {
                this.expiresStart = valueStart;
                this.expiresEnd = valueEnd;
                return;
            }
            if (this.header.regionMatches(true, nameStart, CookieHeaderNames.MAX_AGE, 0, 7)) {
                setMaxAge(computeValue(valueStart, valueEnd));
            }
        }

        private void parse8(int nameStart, int valueStart, int valueEnd) {
            if (this.header.regionMatches(true, nameStart, CookieHeaderNames.HTTPONLY, 0, 8)) {
                this.httpOnly = true;
            }
        }

        private String computeValue(int valueStart, int valueEnd) {
            return (valueStart == -1 || valueStart == valueEnd) ? null : this.header.substring(valueStart, valueEnd);
        }
    }

    private ClientCookieDecoder(boolean strict) {
        super(strict);
    }

    public Cookie decode(String header) {
        int headerLen = ((String) ObjectUtil.checkNotNull(header, "header")).length();
        if (headerLen == 0) {
            return null;
        }
        CookieBuilder cookieBuilder = null;
        int i = 0;
        while (i != headerLen) {
            char c = header.charAt(i);
            if (c == StringUtil.COMMA) {
                break;
            } else if (c == '\t' || c == '\n' || c == '\u000b' || c == '\f' || c == StringUtil.CARRIAGE_RETURN || c == HttpConstants.SP_CHAR || c == ';') {
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
                if (valueEnd > 0 && header.charAt(valueEnd - 1) == StringUtil.COMMA) {
                    valueEnd--;
                }
                if (cookieBuilder == null) {
                    DefaultCookie cookie = initCookie(header, nameBegin, nameEnd, valueBegin, valueEnd);
                    if (cookie == null) {
                        return null;
                    }
                    cookieBuilder = new CookieBuilder(cookie, header);
                } else {
                    cookieBuilder.appendAttribute(nameBegin, nameEnd, valueBegin, valueEnd);
                }
            }
        }
        return cookieBuilder.cookie();
    }
}
