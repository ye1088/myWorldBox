package io.netty.handler.codec.http;

import com.j256.ormlite.stmt.query.SimpleComparison;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class HttpUtil {
    private static final AsciiString CHARSET_EQUALS = AsciiString.of(HttpHeaderValues.CHARSET + SimpleComparison.EQUAL_TO_OPERATION);
    @Deprecated
    static final EmptyHttpHeaders EMPTY_HEADERS = new EmptyHttpHeaders();
    private static final AsciiString SEMICOLON = AsciiString.of(";");

    private HttpUtil() {
    }

    public static boolean isOriginForm(URI uri) {
        return uri.getScheme() == null && uri.getSchemeSpecificPart() == null && uri.getHost() == null && uri.getAuthority() == null;
    }

    public static boolean isAsteriskForm(URI uri) {
        return WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD.equals(uri.getPath()) && uri.getScheme() == null && uri.getSchemeSpecificPart() == null && uri.getHost() == null && uri.getAuthority() == null && uri.getQuery() == null && uri.getFragment() == null;
    }

    public static boolean isKeepAlive(HttpMessage message) {
        CharSequence connection = message.headers().get(HttpHeaderNames.CONNECTION);
        if (connection != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection)) {
            return false;
        }
        if (!message.protocolVersion().isKeepAliveDefault()) {
            return HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(connection);
        }
        if (HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection)) {
            return false;
        }
        return true;
    }

    public static void setKeepAlive(HttpMessage message, boolean keepAlive) {
        setKeepAlive(message.headers(), message.protocolVersion(), keepAlive);
    }

    public static void setKeepAlive(HttpHeaders h, HttpVersion httpVersion, boolean keepAlive) {
        if (httpVersion.isKeepAliveDefault()) {
            if (keepAlive) {
                h.remove(HttpHeaderNames.CONNECTION);
            } else {
                h.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            }
        } else if (keepAlive) {
            h.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        } else {
            h.remove(HttpHeaderNames.CONNECTION);
        }
    }

    public static long getContentLength(HttpMessage message) {
        String value = message.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (value != null) {
            return Long.parseLong(value);
        }
        long webSocketContentLength = (long) getWebSocketContentLength(message);
        if (webSocketContentLength >= 0) {
            return webSocketContentLength;
        }
        throw new NumberFormatException("header not found: " + HttpHeaderNames.CONTENT_LENGTH);
    }

    public static long getContentLength(HttpMessage message, long defaultValue) {
        String value = message.headers().get(HttpHeaderNames.CONTENT_LENGTH);
        if (value != null) {
            return Long.parseLong(value);
        }
        long webSocketContentLength = (long) getWebSocketContentLength(message);
        if (webSocketContentLength < 0) {
            return defaultValue;
        }
        return webSocketContentLength;
    }

    public static int getContentLength(HttpMessage message, int defaultValue) {
        return (int) Math.min(2147483647L, getContentLength(message, (long) defaultValue));
    }

    private static int getWebSocketContentLength(HttpMessage message) {
        HttpHeaders h = message.headers();
        if (message instanceof HttpRequest) {
            if (HttpMethod.GET.equals(((HttpRequest) message).method()) && h.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) && h.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2)) {
                return 8;
            }
        } else if ((message instanceof HttpResponse) && ((HttpResponse) message).status().code() == 101 && h.contains(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) && h.contains(HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) {
            return 16;
        }
        return -1;
    }

    public static void setContentLength(HttpMessage message, long length) {
        message.headers().set(HttpHeaderNames.CONTENT_LENGTH, Long.valueOf(length));
    }

    public static boolean isContentLengthSet(HttpMessage m) {
        return m.headers().contains(HttpHeaderNames.CONTENT_LENGTH);
    }

    public static boolean is100ContinueExpected(HttpMessage message) {
        if (!(message instanceof HttpRequest) || message.protocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
            return false;
        }
        CharSequence value = message.headers().get(HttpHeaderNames.EXPECT);
        if (value == null) {
            return false;
        }
        if (HttpHeaderValues.CONTINUE.contentEqualsIgnoreCase(value)) {
            return true;
        }
        return message.headers().contains(HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE, true);
    }

    public static void set100ContinueExpected(HttpMessage message, boolean expected) {
        if (expected) {
            message.headers().set(HttpHeaderNames.EXPECT, HttpHeaderValues.CONTINUE);
        } else {
            message.headers().remove(HttpHeaderNames.EXPECT);
        }
    }

    public static boolean isTransferEncodingChunked(HttpMessage message) {
        return message.headers().contains(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED, true);
    }

    public static void setTransferEncodingChunked(HttpMessage m, boolean chunked) {
        if (chunked) {
            m.headers().add(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
            m.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
            return;
        }
        List<String> encodings = m.headers().getAll(HttpHeaderNames.TRANSFER_ENCODING);
        if (!encodings.isEmpty()) {
            Iterable values = new ArrayList(encodings);
            Iterator<CharSequence> valuesIt = values.iterator();
            while (valuesIt.hasNext()) {
                if (HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase((CharSequence) valuesIt.next())) {
                    valuesIt.remove();
                }
            }
            if (values.isEmpty()) {
                m.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else {
                m.headers().set(HttpHeaderNames.TRANSFER_ENCODING, values);
            }
        }
    }

    public static Charset getCharset(HttpMessage message) {
        return getCharset(message, CharsetUtil.ISO_8859_1);
    }

    public static Charset getCharset(HttpMessage message, Charset defaultCharset) {
        CharSequence charsetCharSequence = getCharsetAsString(message);
        if (charsetCharSequence != null) {
            try {
                defaultCharset = Charset.forName(charsetCharSequence.toString());
            } catch (UnsupportedCharsetException e) {
            }
        }
        return defaultCharset;
    }

    public static CharSequence getCharsetAsString(HttpMessage message) {
        CharSequence contentTypeValue = message.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentTypeValue != null) {
            int indexOfCharset = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, CHARSET_EQUALS, 0);
            if (indexOfCharset != -1) {
                int indexOfEncoding = indexOfCharset + CHARSET_EQUALS.length();
                if (indexOfEncoding < contentTypeValue.length()) {
                    return contentTypeValue.subSequence(indexOfEncoding, contentTypeValue.length());
                }
            }
        }
        return null;
    }

    public static CharSequence getMimeType(HttpMessage message) {
        CharSequence contentTypeValue = message.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentTypeValue == null) {
            return null;
        }
        int indexOfSemicolon = AsciiString.indexOfIgnoreCaseAscii(contentTypeValue, SEMICOLON, 0);
        if (indexOfSemicolon != -1) {
            return contentTypeValue.subSequence(0, indexOfSemicolon);
        }
        if (contentTypeValue.length() <= 0) {
            return null;
        }
        return contentTypeValue;
    }

    static void encodeAscii0(CharSequence seq, ByteBuf buf) {
        int length = seq.length();
        for (int i = 0; i < length; i++) {
            buf.writeByte(c2b(seq.charAt(i)));
        }
    }

    private static byte c2b(char c) {
        return c > 'ÿ' ? (byte) 63 : (byte) c;
    }
}
