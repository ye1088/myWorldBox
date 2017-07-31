package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpHeaderDateFormat;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ServerCookieEncoder extends CookieEncoder {
    public static final ServerCookieEncoder LAX = new ServerCookieEncoder(false);
    public static final ServerCookieEncoder STRICT = new ServerCookieEncoder(true);

    private ServerCookieEncoder(boolean strict) {
        super(strict);
    }

    public String encode(String name, String value) {
        return encode(new DefaultCookie(name, value));
    }

    public String encode(Cookie cookie) {
        String name = ((Cookie) ObjectUtil.checkNotNull(cookie, "cookie")).name();
        String value = cookie.value() != null ? cookie.value() : "";
        validateCookie(name, value);
        StringBuilder buf = CookieUtil.stringBuilder();
        if (cookie.wrap()) {
            CookieUtil.addQuoted(buf, name, value);
        } else {
            CookieUtil.add(buf, name, value);
        }
        if (cookie.maxAge() != Long.MIN_VALUE) {
            CookieUtil.add(buf, CookieHeaderNames.MAX_AGE, cookie.maxAge());
            CookieUtil.add(buf, "Expires", HttpHeaderDateFormat.get().format(new Date((cookie.maxAge() * 1000) + System.currentTimeMillis())));
        }
        if (cookie.path() != null) {
            CookieUtil.add(buf, CookieHeaderNames.PATH, cookie.path());
        }
        if (cookie.domain() != null) {
            CookieUtil.add(buf, CookieHeaderNames.DOMAIN, cookie.domain());
        }
        if (cookie.isSecure()) {
            CookieUtil.add(buf, CookieHeaderNames.SECURE);
        }
        if (cookie.isHttpOnly()) {
            CookieUtil.add(buf, CookieHeaderNames.HTTPONLY);
        }
        return CookieUtil.stripTrailingSeparator(buf);
    }

    private List<String> dedup(List<String> encoded, Map<String, Integer> nameToLastIndex) {
        boolean[] isLastInstance = new boolean[encoded.size()];
        for (Integer intValue : nameToLastIndex.values()) {
            isLastInstance[intValue.intValue()] = true;
        }
        List<String> dedupd = new ArrayList(nameToLastIndex.size());
        int n = encoded.size();
        for (int i = 0; i < n; i++) {
            if (isLastInstance[i]) {
                dedupd.add(encoded.get(i));
            }
        }
        return dedupd;
    }

    public List<String> encode(Cookie... cookies) {
        if (((Cookie[]) ObjectUtil.checkNotNull(cookies, "cookies")).length == 0) {
            return Collections.emptyList();
        }
        List<String> encoded = new ArrayList(cookies.length);
        Map<String, Integer> nameToIndex = (!this.strict || cookies.length <= 1) ? null : new HashMap();
        boolean hasDupdName = false;
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            encoded.add(encode(c));
            if (nameToIndex != null) {
                hasDupdName |= nameToIndex.put(c.name(), Integer.valueOf(i)) != null ? 1 : 0;
            }
        }
        return hasDupdName ? dedup(encoded, nameToIndex) : encoded;
    }

    public List<String> encode(Collection<? extends Cookie> cookies) {
        if (((Collection) ObjectUtil.checkNotNull(cookies, "cookies")).isEmpty()) {
            return Collections.emptyList();
        }
        List<String> encoded = new ArrayList(cookies.size());
        Map<String, Integer> nameToIndex = (!this.strict || cookies.size() <= 1) ? null : new HashMap();
        int i = 0;
        boolean hasDupdName = false;
        for (Cookie c : cookies) {
            encoded.add(encode(c));
            if (nameToIndex != null) {
                hasDupdName |= nameToIndex.put(c.name(), Integer.valueOf(i)) != null ? 1 : 0;
                i++;
            }
        }
        return hasDupdName ? dedup(encoded, nameToIndex) : encoded;
    }

    public List<String> encode(Iterable<? extends Cookie> cookies) {
        Iterator<? extends Cookie> cookiesIt = ((Iterable) ObjectUtil.checkNotNull(cookies, "cookies")).iterator();
        if (!cookiesIt.hasNext()) {
            return Collections.emptyList();
        }
        int i;
        boolean hasDupdName;
        List<String> encoded = new ArrayList();
        Cookie firstCookie = (Cookie) cookiesIt.next();
        Map<String, Integer> nameToIndex = (this.strict && cookiesIt.hasNext()) ? new HashMap() : null;
        int i2 = 0;
        encoded.add(encode(firstCookie));
        if (nameToIndex != null) {
            i = 0 + 1;
            if (nameToIndex.put(firstCookie.name(), Integer.valueOf(0)) != null) {
                hasDupdName = true;
                i2 = i;
            } else {
                hasDupdName = false;
                i2 = i;
            }
        } else {
            hasDupdName = false;
        }
        while (cookiesIt.hasNext()) {
            Cookie c = (Cookie) cookiesIt.next();
            encoded.add(encode(c));
            if (nameToIndex != null) {
                int i3;
                i = i2 + 1;
                if (nameToIndex.put(c.name(), Integer.valueOf(i2)) != null) {
                    i3 = 1;
                } else {
                    i3 = 0;
                }
                hasDupdName |= i3;
                i2 = i;
            }
        }
        return hasDupdName ? dedup(encoded, nameToIndex) : encoded;
    }
}
