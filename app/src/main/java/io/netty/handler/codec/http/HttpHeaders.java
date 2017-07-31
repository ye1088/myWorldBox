package io.netty.handler.codec.http;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public abstract class HttpHeaders implements Iterable<Entry<String, String>> {
    @Deprecated
    public static final HttpHeaders EMPTY_HEADERS = EmptyHttpHeaders.instance();

    @Deprecated
    public static final class Names {
        public static final String ACCEPT = "Accept";
        public static final String ACCEPT_CHARSET = "Accept-Charset";
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ACCEPT_PATCH = "Accept-Patch";
        public static final String ACCEPT_RANGES = "Accept-Ranges";
        public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
        public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
        public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
        public static final String AGE = "Age";
        public static final String ALLOW = "Allow";
        public static final String AUTHORIZATION = "Authorization";
        public static final String CACHE_CONTROL = "Cache-Control";
        public static final String CONNECTION = "Connection";
        public static final String CONTENT_BASE = "Content-Base";
        public static final String CONTENT_ENCODING = "Content-Encoding";
        public static final String CONTENT_LANGUAGE = "Content-Language";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String CONTENT_LOCATION = "Content-Location";
        public static final String CONTENT_MD5 = "Content-MD5";
        public static final String CONTENT_RANGE = "Content-Range";
        public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String COOKIE = "Cookie";
        public static final String DATE = "Date";
        public static final String ETAG = "ETag";
        public static final String EXPECT = "Expect";
        public static final String EXPIRES = "Expires";
        public static final String FROM = "From";
        public static final String HOST = "Host";
        public static final String IF_MATCH = "If-Match";
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        public static final String IF_NONE_MATCH = "If-None-Match";
        public static final String IF_RANGE = "If-Range";
        public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        public static final String LAST_MODIFIED = "Last-Modified";
        public static final String LOCATION = "Location";
        public static final String MAX_FORWARDS = "Max-Forwards";
        public static final String ORIGIN = "Origin";
        public static final String PRAGMA = "Pragma";
        public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
        public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
        public static final String RANGE = "Range";
        public static final String REFERER = "Referer";
        public static final String RETRY_AFTER = "Retry-After";
        public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
        public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
        public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
        public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
        public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
        public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
        public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
        public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
        public static final String SERVER = "Server";
        public static final String SET_COOKIE = "Set-Cookie";
        public static final String SET_COOKIE2 = "Set-Cookie2";
        public static final String TE = "TE";
        public static final String TRAILER = "Trailer";
        public static final String TRANSFER_ENCODING = "Transfer-Encoding";
        public static final String UPGRADE = "Upgrade";
        public static final String USER_AGENT = "User-Agent";
        public static final String VARY = "Vary";
        public static final String VIA = "Via";
        public static final String WARNING = "Warning";
        public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
        public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
        public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

        private Names() {
        }
    }

    @Deprecated
    public static final class Values {
        public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String BASE64 = "base64";
        public static final String BINARY = "binary";
        public static final String BOUNDARY = "boundary";
        public static final String BYTES = "bytes";
        public static final String CHARSET = "charset";
        public static final String CHUNKED = "chunked";
        public static final String CLOSE = "close";
        public static final String COMPRESS = "compress";
        public static final String CONTINUE = "100-continue";
        public static final String DEFLATE = "deflate";
        public static final String GZIP = "gzip";
        public static final String IDENTITY = "identity";
        public static final String KEEP_ALIVE = "keep-alive";
        public static final String MAX_AGE = "max-age";
        public static final String MAX_STALE = "max-stale";
        public static final String MIN_FRESH = "min-fresh";
        public static final String MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String MUST_REVALIDATE = "must-revalidate";
        public static final String NONE = "none";
        public static final String NO_CACHE = "no-cache";
        public static final String NO_STORE = "no-store";
        public static final String NO_TRANSFORM = "no-transform";
        public static final String ONLY_IF_CACHED = "only-if-cached";
        public static final String PRIVATE = "private";
        public static final String PROXY_REVALIDATE = "proxy-revalidate";
        public static final String PUBLIC = "public";
        public static final String QUOTED_PRINTABLE = "quoted-printable";
        public static final String S_MAXAGE = "s-maxage";
        public static final String TRAILERS = "trailers";
        public static final String UPGRADE = "Upgrade";
        public static final String WEBSOCKET = "WebSocket";

        private Values() {
        }
    }

    public abstract HttpHeaders add(String str, Iterable<?> iterable);

    public abstract HttpHeaders add(String str, Object obj);

    public abstract HttpHeaders addInt(CharSequence charSequence, int i);

    public abstract HttpHeaders addShort(CharSequence charSequence, short s);

    public abstract HttpHeaders clear();

    public abstract boolean contains(String str);

    public abstract List<Entry<String, String>> entries();

    public abstract String get(String str);

    public abstract List<String> getAll(String str);

    public abstract int getInt(CharSequence charSequence, int i);

    public abstract Integer getInt(CharSequence charSequence);

    public abstract Short getShort(CharSequence charSequence);

    public abstract short getShort(CharSequence charSequence, short s);

    public abstract long getTimeMillis(CharSequence charSequence, long j);

    public abstract Long getTimeMillis(CharSequence charSequence);

    public abstract boolean isEmpty();

    @Deprecated
    public abstract Iterator<Entry<String, String>> iterator();

    public abstract Iterator<Entry<CharSequence, CharSequence>> iteratorCharSequence();

    public abstract Set<String> names();

    public abstract HttpHeaders remove(String str);

    public abstract HttpHeaders set(String str, Iterable<?> iterable);

    public abstract HttpHeaders set(String str, Object obj);

    public abstract HttpHeaders setInt(CharSequence charSequence, int i);

    public abstract HttpHeaders setShort(CharSequence charSequence, short s);

    public abstract int size();

    @Deprecated
    public static boolean isKeepAlive(HttpMessage message) {
        return HttpUtil.isKeepAlive(message);
    }

    @Deprecated
    public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
        HttpUtil.setKeepAlive(message, keepAlive);
    }

    @Deprecated
    public static String getHeader(HttpMessage message, String name) {
        return message.headers().get(name);
    }

    @Deprecated
    public static String getHeader(HttpMessage message, CharSequence name) {
        return message.headers().get(name);
    }

    @Deprecated
    public static String getHeader(HttpMessage message, String name, String defaultValue) {
        return message.headers().get(name, defaultValue);
    }

    @Deprecated
    public static String getHeader(HttpMessage message, CharSequence name, String defaultValue) {
        return message.headers().get(name, defaultValue);
    }

    @Deprecated
    public static void setHeader(HttpMessage message, String name, Object value) {
        message.headers().set(name, value);
    }

    @Deprecated
    public static void setHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().set(name, value);
    }

    @Deprecated
    public static void setHeader(HttpMessage message, String name, Iterable<?> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void setHeader(HttpMessage message, CharSequence name, Iterable<?> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void addHeader(HttpMessage message, String name, Object value) {
        message.headers().add(name, value);
    }

    @Deprecated
    public static void addHeader(HttpMessage message, CharSequence name, Object value) {
        message.headers().add(name, value);
    }

    @Deprecated
    public static void removeHeader(HttpMessage message, String name) {
        message.headers().remove(name);
    }

    @Deprecated
    public static void removeHeader(HttpMessage message, CharSequence name) {
        message.headers().remove(name);
    }

    @Deprecated
    public static void clearHeaders(HttpMessage message) {
        message.headers().clear();
    }

    @Deprecated
    public static int getIntHeader(HttpMessage message, String name) {
        return getIntHeader(message, (CharSequence) name);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage message, CharSequence name) {
        String value = message.headers().get(name);
        if (value != null) {
            return Integer.parseInt(value);
        }
        throw new NumberFormatException("header not found: " + name);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage message, String name, int defaultValue) {
        return message.headers().getInt(name, defaultValue);
    }

    @Deprecated
    public static int getIntHeader(HttpMessage message, CharSequence name, int defaultValue) {
        return message.headers().getInt(name, defaultValue);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage message, String name, int value) {
        message.headers().setInt(name, value);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().setInt(name, value);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage message, String name, Iterable<Integer> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void setIntHeader(HttpMessage message, CharSequence name, Iterable<Integer> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void addIntHeader(HttpMessage message, String name, int value) {
        message.headers().add(name, Integer.valueOf(value));
    }

    @Deprecated
    public static void addIntHeader(HttpMessage message, CharSequence name, int value) {
        message.headers().addInt(name, value);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage message, String name) throws ParseException {
        return getDateHeader(message, (CharSequence) name);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage message, CharSequence name) throws ParseException {
        String value = message.headers().get(name);
        if (value != null) {
            return HttpHeaderDateFormat.get().parse(value);
        }
        throw new ParseException("header not found: " + name, 0);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage message, String name, Date defaultValue) {
        return getDateHeader(message, (CharSequence) name, defaultValue);
    }

    @Deprecated
    public static Date getDateHeader(HttpMessage message, CharSequence name, Date defaultValue) {
        String value = getHeader(message, name);
        if (value != null) {
            try {
                defaultValue = HttpHeaderDateFormat.get().parse(value);
            } catch (ParseException e) {
            }
        }
        return defaultValue;
    }

    @Deprecated
    public static void setDateHeader(HttpMessage message, String name, Date value) {
        setDateHeader(message, (CharSequence) name, value);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage message, CharSequence name, Date value) {
        if (value != null) {
            message.headers().set(name, HttpHeaderDateFormat.get().format(value));
        } else {
            message.headers().set(name, null);
        }
    }

    @Deprecated
    public static void setDateHeader(HttpMessage message, String name, Iterable<Date> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void setDateHeader(HttpMessage message, CharSequence name, Iterable<Date> values) {
        message.headers().set(name, (Iterable) values);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage message, String name, Date value) {
        message.headers().add(name, (Object) value);
    }

    @Deprecated
    public static void addDateHeader(HttpMessage message, CharSequence name, Date value) {
        message.headers().add(name, (Object) value);
    }

    @Deprecated
    public static long getContentLength(HttpMessage message) {
        return HttpUtil.getContentLength(message);
    }

    @Deprecated
    public static long getContentLength(HttpMessage message, long defaultValue) {
        return HttpUtil.getContentLength(message, defaultValue);
    }

    @Deprecated
    public static void setContentLength(HttpMessage message, long length) {
        HttpUtil.setContentLength(message, length);
    }

    @Deprecated
    public static String getHost(HttpMessage message) {
        return message.headers().get(HttpHeaderNames.HOST);
    }

    @Deprecated
    public static String getHost(HttpMessage message, String defaultValue) {
        return message.headers().get(HttpHeaderNames.HOST, defaultValue);
    }

    @Deprecated
    public static void setHost(HttpMessage message, String value) {
        message.headers().set(HttpHeaderNames.HOST, (Object) value);
    }

    @Deprecated
    public static void setHost(HttpMessage message, CharSequence value) {
        message.headers().set(HttpHeaderNames.HOST, (Object) value);
    }

    @Deprecated
    public static Date getDate(HttpMessage message) throws ParseException {
        return getDateHeader(message, HttpHeaderNames.DATE);
    }

    @Deprecated
    public static Date getDate(HttpMessage message, Date defaultValue) {
        return getDateHeader(message, HttpHeaderNames.DATE, defaultValue);
    }

    @Deprecated
    public static void setDate(HttpMessage message, Date value) {
        message.headers().set(HttpHeaderNames.DATE, (Object) value);
    }

    @Deprecated
    public static boolean is100ContinueExpected(HttpMessage message) {
        return HttpUtil.is100ContinueExpected(message);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage message) {
        HttpUtil.set100ContinueExpected(message, true);
    }

    @Deprecated
    public static void set100ContinueExpected(HttpMessage message, boolean set) {
        HttpUtil.set100ContinueExpected(message, set);
    }

    @Deprecated
    public static boolean isTransferEncodingChunked(HttpMessage message) {
        return HttpUtil.isTransferEncodingChunked(message);
    }

    @Deprecated
    public static void removeTransferEncodingChunked(HttpMessage m) {
        HttpUtil.setTransferEncodingChunked(m, false);
    }

    @Deprecated
    public static void setTransferEncodingChunked(HttpMessage m) {
        HttpUtil.setTransferEncodingChunked(m, true);
    }

    @Deprecated
    public static boolean isContentLengthSet(HttpMessage m) {
        return HttpUtil.isContentLengthSet(m);
    }

    @Deprecated
    public static boolean equalsIgnoreCase(CharSequence name1, CharSequence name2) {
        return AsciiString.contentEqualsIgnoreCase(name1, name2);
    }

    static void encode(HttpHeaders headers, ByteBuf buf) throws Exception {
        Iterator<Entry<CharSequence, CharSequence>> iter = headers.iteratorCharSequence();
        while (iter.hasNext()) {
            Entry<CharSequence, CharSequence> header = (Entry) iter.next();
            HttpHeadersEncoder.encoderHeader((CharSequence) header.getKey(), (CharSequence) header.getValue(), buf);
        }
    }

    public static void encodeAscii(CharSequence seq, ByteBuf buf) {
        if (seq instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString) seq, 0, buf, seq.length());
        } else {
            HttpUtil.encodeAscii0(seq, buf);
        }
    }

    @Deprecated
    public static CharSequence newEntity(String name) {
        return new AsciiString(name);
    }

    protected HttpHeaders() {
    }

    public String get(CharSequence name) {
        return get(name.toString());
    }

    public String get(CharSequence name, String defaultValue) {
        String value = get(name);
        return value == null ? defaultValue : value;
    }

    public List<String> getAll(CharSequence name) {
        return getAll(name.toString());
    }

    public boolean contains(CharSequence name) {
        return contains(name.toString());
    }

    public HttpHeaders add(CharSequence name, Object value) {
        return add(name.toString(), value);
    }

    public HttpHeaders add(CharSequence name, Iterable<?> values) {
        return add(name.toString(), (Iterable) values);
    }

    public HttpHeaders add(HttpHeaders headers) {
        if (headers == null) {
            throw new NullPointerException("headers");
        }
        Iterator i$ = headers.iterator();
        while (i$.hasNext()) {
            Entry<String, String> e = (Entry) i$.next();
            add((String) e.getKey(), e.getValue());
        }
        return this;
    }

    public HttpHeaders set(CharSequence name, Object value) {
        return set(name.toString(), value);
    }

    public HttpHeaders set(CharSequence name, Iterable<?> values) {
        return set(name.toString(), (Iterable) values);
    }

    public HttpHeaders set(HttpHeaders headers) {
        ObjectUtil.checkNotNull(headers, "headers");
        clear();
        if (!headers.isEmpty()) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                Entry<String, String> entry = (Entry) i$.next();
                add((String) entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public HttpHeaders setAll(HttpHeaders headers) {
        ObjectUtil.checkNotNull(headers, "headers");
        if (!headers.isEmpty()) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                Entry<String, String> entry = (Entry) i$.next();
                set((String) entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public HttpHeaders remove(CharSequence name) {
        return remove(name.toString());
    }

    public boolean contains(String name, String value, boolean ignoreCase) {
        List<String> values = getAll(name);
        if (values.isEmpty()) {
            return false;
        }
        for (String v : values) {
            if (ignoreCase) {
                if (v.equalsIgnoreCase(value)) {
                    return true;
                }
            } else if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(CharSequence name, CharSequence value, boolean ignoreCase) {
        List<String> values = getAll(name);
        if (values.isEmpty()) {
            return false;
        }
        for (String v : values) {
            if (contains(v, value, ignoreCase)) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(String value, CharSequence expected, boolean ignoreCase) {
        String[] parts = value.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        if (ignoreCase) {
            for (String s : parts) {
                if (AsciiString.contentEqualsIgnoreCase(expected, s.trim())) {
                    return true;
                }
            }
        } else {
            for (String s2 : parts) {
                if (AsciiString.contentEquals(expected, s2.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final String getAsString(CharSequence name) {
        return get(name);
    }

    public final List<String> getAllAsString(CharSequence name) {
        return getAll(name);
    }

    public final Iterator<Entry<String, String>> iteratorAsString() {
        return iterator();
    }

    public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
        return contains(name.toString(), value.toString(), ignoreCase);
    }
}
