package io.netty.handler.codec.http;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public final class CookieDecoder {
    private static final String COMMENT = "Comment";
    private static final String COMMENTURL = "CommentURL";
    private static final String DISCARD = "Discard";
    private static final CookieDecoder LAX = new CookieDecoder(false);
    private static final String PORT = "Port";
    private static final CookieDecoder STRICT = new CookieDecoder(true);
    private static final String VERSION = "Version";
    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());
    private final boolean strict;

    public static Set<Cookie> decode(String header) {
        return decode(header, true);
    }

    public static Set<Cookie> decode(String header, boolean strict) {
        return (strict ? STRICT : LAX).doDecode(header);
    }

    private Set<Cookie> doDecode(String header) {
        List<String> arrayList = new ArrayList(8);
        arrayList = new ArrayList(8);
        extractKeyValuePairs(header, arrayList, arrayList);
        if (arrayList.isEmpty()) {
            return Collections.emptySet();
        }
        int i;
        int version = 0;
        if (((String) arrayList.get(0)).equalsIgnoreCase(VERSION)) {
            try {
                version = Integer.parseInt((String) arrayList.get(0));
            } catch (NumberFormatException e) {
            }
            i = 1;
        } else {
            i = 0;
        }
        if (arrayList.size() <= i) {
            return Collections.emptySet();
        }
        Set<Cookie> cookies = new TreeSet();
        while (i < arrayList.size()) {
            String name = (String) arrayList.get(i);
            String value = (String) arrayList.get(i);
            if (value == null) {
                value = "";
            }
            Cookie c = initCookie(name, value);
            if (c == null) {
                return cookies;
            }
            boolean discard = false;
            boolean secure = false;
            boolean httpOnly = false;
            String comment = null;
            String commentURL = null;
            String domain = null;
            String path = null;
            long maxAge = Long.MIN_VALUE;
            List<Integer> arrayList2 = new ArrayList(2);
            int j = i + 1;
            while (j < arrayList.size()) {
                name = (String) arrayList.get(j);
                value = (String) arrayList.get(j);
                if (!DISCARD.equalsIgnoreCase(name)) {
                    if (!CookieHeaderNames.SECURE.equalsIgnoreCase(name)) {
                        if (!CookieHeaderNames.HTTPONLY.equalsIgnoreCase(name)) {
                            if (!COMMENT.equalsIgnoreCase(name)) {
                                if (!COMMENTURL.equalsIgnoreCase(name)) {
                                    if (!CookieHeaderNames.DOMAIN.equalsIgnoreCase(name)) {
                                        if (!CookieHeaderNames.PATH.equalsIgnoreCase(name)) {
                                            if (!"Expires".equalsIgnoreCase(name)) {
                                                if (!CookieHeaderNames.MAX_AGE.equalsIgnoreCase(name)) {
                                                    if (!VERSION.equalsIgnoreCase(name)) {
                                                        if (!PORT.equalsIgnoreCase(name)) {
                                                            break;
                                                        }
                                                        for (String s1 : value.split(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
                                                            try {
                                                                arrayList2.add(Integer.valueOf(s1));
                                                            } catch (NumberFormatException e2) {
                                                            }
                                                        }
                                                    } else {
                                                        version = Integer.parseInt(value);
                                                    }
                                                } else {
                                                    maxAge = (long) Integer.parseInt(value);
                                                }
                                            } else {
                                                try {
                                                    long maxAgeMillis = HttpHeaderDateFormat.get().parse(value).getTime() - System.currentTimeMillis();
                                                    maxAge = (maxAgeMillis / 1000) + ((long) (maxAgeMillis % 1000 != 0 ? 1 : 0));
                                                } catch (ParseException e3) {
                                                }
                                            }
                                        } else {
                                            path = value;
                                        }
                                    } else {
                                        domain = value;
                                    }
                                } else {
                                    commentURL = value;
                                }
                            } else {
                                comment = value;
                            }
                        } else {
                            httpOnly = true;
                        }
                    } else {
                        secure = true;
                    }
                } else {
                    discard = true;
                }
                j++;
                i++;
            }
            c.setVersion(version);
            c.setMaxAge(maxAge);
            c.setPath(path);
            c.setDomain(domain);
            c.setSecure(secure);
            c.setHttpOnly(httpOnly);
            if (version > 0) {
                c.setComment(comment);
            }
            if (version > 1) {
                c.setCommentUrl(commentURL);
                c.setPorts((Iterable) arrayList2);
                c.setDiscard(discard);
            }
            cookies.add(c);
            i++;
        }
        return cookies;
    }

    private static void extractKeyValuePairs(String header, List<String> names, List<String> values) {
        int headerLen = header.length();
        int i = 0;
        while (i != headerLen) {
            switch (header.charAt(i)) {
                case '\t':
                case '\n':
                case '\u000b':
                case '\f':
                case '\r':
                case ' ':
                case ',':
                case ';':
                    i++;
                    break;
                default:
                    while (i != headerLen) {
                        if (header.charAt(i) != '$') {
                            String name;
                            String value;
                            if (i == headerLen) {
                                name = null;
                                value = null;
                            } else {
                                int newNameStart = i;
                                do {
                                    switch (header.charAt(i)) {
                                        case ';':
                                            name = header.substring(newNameStart, i);
                                            value = null;
                                            break;
                                        case '=':
                                            name = header.substring(newNameStart, i);
                                            i++;
                                            if (i != headerLen) {
                                                int newValueStart = i;
                                                char c = header.charAt(i);
                                                if (c != StringUtil.DOUBLE_QUOTE && c != '\'') {
                                                    int semiPos = header.indexOf(59, i);
                                                    if (semiPos <= 0) {
                                                        value = header.substring(newValueStart);
                                                        i = headerLen;
                                                        break;
                                                    }
                                                    value = header.substring(newValueStart, semiPos);
                                                    i = semiPos;
                                                    break;
                                                }
                                                StringBuilder newValueBuf = new StringBuilder(header.length() - i);
                                                char q = c;
                                                boolean hadBackslash = false;
                                                int i2 = i + 1;
                                                while (i2 != headerLen) {
                                                    if (hadBackslash) {
                                                        hadBackslash = false;
                                                        i = i2 + 1;
                                                        c = header.charAt(i2);
                                                        switch (c) {
                                                            case '\"':
                                                            case '\'':
                                                            case '\\':
                                                                newValueBuf.setCharAt(newValueBuf.length() - 1, c);
                                                                i2 = i;
                                                                break;
                                                            default:
                                                                newValueBuf.append(c);
                                                                i2 = i;
                                                                break;
                                                        }
                                                    }
                                                    i = i2 + 1;
                                                    c = header.charAt(i2);
                                                    if (c == q) {
                                                        value = newValueBuf.toString();
                                                        break;
                                                    }
                                                    newValueBuf.append(c);
                                                    if (c == '\\') {
                                                        hadBackslash = true;
                                                        i2 = i;
                                                    } else {
                                                        i2 = i;
                                                    }
                                                }
                                                value = newValueBuf.toString();
                                                i = i2;
                                                break;
                                            }
                                            value = "";
                                            break;
                                            break;
                                        default:
                                            i++;
                                            break;
                                    }
                                } while (i != headerLen);
                                name = header.substring(newNameStart);
                                value = null;
                            }
                            names.add(name);
                            values.add(value);
                            break;
                        }
                        i++;
                    }
                    return;
            }
        }
    }

    private CookieDecoder(boolean strict) {
        this.strict = strict;
    }

    private DefaultCookie initCookie(String name, String value) {
        if (name == null || name.length() == 0) {
            this.logger.debug("Skipping cookie with null name");
            return null;
        } else if (value == null) {
            this.logger.debug("Skipping cookie with null value");
            return null;
        } else {
            CharSequence unwrappedValue = CookieUtil.unwrapValue(value);
            if (unwrappedValue == null) {
                this.logger.debug("Skipping cookie because starting quotes are not properly balanced in '{}'", unwrappedValue);
                return null;
            }
            int invalidOctetPos;
            if (this.strict) {
                invalidOctetPos = CookieUtil.firstInvalidCookieNameOctet(name);
                if (invalidOctetPos >= 0) {
                    if (!this.logger.isDebugEnabled()) {
                        return null;
                    }
                    this.logger.debug("Skipping cookie because name '{}' contains invalid char '{}'", name, Character.valueOf(name.charAt(invalidOctetPos)));
                    return null;
                }
            }
            boolean wrap = unwrappedValue.length() != value.length();
            if (this.strict) {
                invalidOctetPos = CookieUtil.firstInvalidCookieValueOctet(unwrappedValue);
                if (invalidOctetPos >= 0) {
                    if (!this.logger.isDebugEnabled()) {
                        return null;
                    }
                    this.logger.debug("Skipping cookie because value '{}' contains invalid char '{}'", unwrappedValue, Character.valueOf(unwrappedValue.charAt(invalidOctetPos)));
                    return null;
                }
            }
            DefaultCookie cookie = new DefaultCookie(name, unwrappedValue.toString());
            cookie.setWrap(wrap);
            return cookie;
        }
    }
}
