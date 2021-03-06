package com.MCWorld.framework.base.http.toolbox;

import com.MCWorld.framework.base.http.datasource.cache.Cache.Entry;
import com.MCWorld.framework.base.http.io.NetworkResponse;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.handler.codec.http.HttpHeaders.Values;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class HttpHeaderParser {
    public static Entry parseCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();
        Map<String, String> headers = response.headers;
        long serverDate = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long maxAge = 0;
        boolean hasCacheControl = false;
        String headerValue = (String) headers.get("Date");
        if (headerValue != null) {
            serverDate = parseDateAsEpoch(headerValue);
        }
        headerValue = (String) headers.get("Cache-Control");
        if (headerValue != null) {
            hasCacheControl = true;
            String[] tokens = headerValue.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            for (String trim : tokens) {
                String token = trim.trim();
                if (token.equals("no-cache") || token.equals(Values.NO_STORE)) {
                    return null;
                }
                if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception e) {
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    maxAge = 0;
                }
            }
        }
        headerValue = (String) headers.get("Expires");
        if (headerValue != null) {
            serverExpires = parseDateAsEpoch(headerValue);
        }
        String serverEtag = (String) headers.get("ETag");
        if (hasCacheControl) {
            softExpire = now + (1000 * maxAge);
        } else if (serverDate > 0 && serverExpires >= serverDate) {
            softExpire = now + (serverExpires - serverDate);
        }
        Entry entry = new Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = entry.softTtl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;
        return entry;
    }

    public static long parseDateAsEpoch(String dateStr) {
        try {
            return DateUtils.parseDate(dateStr).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }

    public static String parseCharset(Map<String, String> headers) {
        String contentType = (String) headers.get("Content-Type");
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split(SimpleComparison.EQUAL_TO_OPERATION);
                if (pair.length == 2 && pair[0].equals("charset")) {
                    return pair[1];
                }
            }
        }
        return "UTF-8";
    }
}
