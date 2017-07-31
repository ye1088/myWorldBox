package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http2.Http2Headers.PseudoHeaderName;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.URI;
import java.util.Iterator;
import java.util.Map.Entry;

public final class HttpConversionUtil {
    private static final AsciiString EMPTY_REQUEST_PATH = new AsciiString((CharSequence) "/");
    private static final CharSequenceMap<AsciiString> HTTP_TO_HTTP2_HEADER_BLACKLIST = new CharSequenceMap();
    public static final HttpMethod OUT_OF_MESSAGE_SEQUENCE_METHOD = HttpMethod.OPTIONS;
    public static final String OUT_OF_MESSAGE_SEQUENCE_PATH = "";
    public static final HttpResponseStatus OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE = HttpResponseStatus.OK;

    public enum ExtensionHeaderNames {
        STREAM_ID("x-http2-stream-id"),
        SCHEME("x-http2-scheme"),
        PATH("x-http2-path"),
        STREAM_PROMISE_ID("x-http2-stream-promise-id"),
        STREAM_DEPENDENCY_ID("x-http2-stream-dependency-id"),
        STREAM_WEIGHT("x-http2-stream-weight");
        
        private final AsciiString text;

        private ExtensionHeaderNames(String text) {
            this.text = new AsciiString((CharSequence) text);
        }

        public AsciiString text() {
            return this.text;
        }
    }

    private static final class Http2ToHttpHeaderTranslator {
        private static final CharSequenceMap<AsciiString> REQUEST_HEADER_TRANSLATIONS = new CharSequenceMap();
        private static final CharSequenceMap<AsciiString> RESPONSE_HEADER_TRANSLATIONS = new CharSequenceMap();
        private final HttpHeaders output;
        private final int streamId;
        private final CharSequenceMap<AsciiString> translations;

        static {
            RESPONSE_HEADER_TRANSLATIONS.add(PseudoHeaderName.AUTHORITY.value(), HttpHeaderNames.HOST);
            RESPONSE_HEADER_TRANSLATIONS.add(PseudoHeaderName.SCHEME.value(), ExtensionHeaderNames.SCHEME.text());
            REQUEST_HEADER_TRANSLATIONS.add(RESPONSE_HEADER_TRANSLATIONS);
            RESPONSE_HEADER_TRANSLATIONS.add(PseudoHeaderName.PATH.value(), ExtensionHeaderNames.PATH.text());
        }

        Http2ToHttpHeaderTranslator(int streamId, HttpHeaders output, boolean request) {
            this.streamId = streamId;
            this.output = output;
            this.translations = request ? REQUEST_HEADER_TRANSLATIONS : RESPONSE_HEADER_TRANSLATIONS;
        }

        public void translate(Entry<CharSequence, CharSequence> entry) throws Http2Exception {
            CharSequence name = (CharSequence) entry.getKey();
            CharSequence value = (CharSequence) entry.getValue();
            AsciiString translatedName = (AsciiString) this.translations.get(name);
            if (translatedName != null) {
                this.output.add(translatedName, AsciiString.of(value));
            } else if (!PseudoHeaderName.isPseudoHeader(name)) {
                if (name.length() == 0 || name.charAt(0) == ':') {
                    throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 header '%s' encountered in translation to HTTP/1.x", name);
                } else if (HttpHeaderNames.COOKIE.equals(name)) {
                    String existingCookie = this.output.get(HttpHeaderNames.COOKIE);
                    HttpHeaders httpHeaders = this.output;
                    CharSequence charSequence = HttpHeaderNames.COOKIE;
                    if (existingCookie != null) {
                        value = existingCookie + "; " + value;
                    }
                    httpHeaders.set(charSequence, value);
                } else {
                    this.output.add(name, value);
                }
            }
        }
    }

    static {
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.CONNECTION, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.KEEP_ALIVE, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.PROXY_CONNECTION, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.TRANSFER_ENCODING, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.HOST, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(HttpHeaderNames.UPGRADE, AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.STREAM_ID.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.SCHEME.text(), AsciiString.EMPTY_STRING);
        HTTP_TO_HTTP2_HEADER_BLACKLIST.add(ExtensionHeaderNames.PATH.text(), AsciiString.EMPTY_STRING);
    }

    private HttpConversionUtil() {
    }

    public static HttpResponseStatus parseStatus(CharSequence status) throws Http2Exception {
        try {
            HttpResponseStatus result = HttpResponseStatus.parseLine(status);
            if (result != HttpResponseStatus.SWITCHING_PROTOCOLS) {
                return result;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 status code '%d'", Integer.valueOf(result.code()));
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable t) {
            Http2Exception connectionError = Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, t, "Unrecognized HTTP status code '%s' encountered in translation to HTTP/1.x", status);
        }
    }

    public static FullHttpResponse toHttpResponse(int streamId, Http2Headers http2Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http2Exception {
        FullHttpResponse msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, parseStatus(http2Headers.status()), alloc.buffer(), validateHttpHeaders);
        try {
            addHttp2ToHttpHeaders(streamId, http2Headers, msg, false);
            return msg;
        } catch (Http2Exception e) {
            msg.release();
            throw e;
        } catch (Throwable t) {
            msg.release();
            Http2Exception streamError = Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static FullHttpRequest toFullHttpRequest(int streamId, Http2Headers http2Headers, ByteBufAllocator alloc, boolean validateHttpHeaders) throws Http2Exception {
        FullHttpRequest msg = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(((CharSequence) ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x")).toString()), ((CharSequence) ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x")).toString(), alloc.buffer(), validateHttpHeaders);
        try {
            addHttp2ToHttpHeaders(streamId, http2Headers, msg, false);
            return msg;
        } catch (Http2Exception e) {
            msg.release();
            throw e;
        } catch (Throwable t) {
            msg.release();
            Http2Exception streamError = Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static HttpRequest toHttpRequest(int streamId, Http2Headers http2Headers, boolean validateHttpHeaders) throws Http2Exception {
        HttpRequest msg = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(((CharSequence) ObjectUtil.checkNotNull(http2Headers.method(), "method header cannot be null in conversion to HTTP/1.x")).toString()), ((CharSequence) ObjectUtil.checkNotNull(http2Headers.path(), "path header cannot be null in conversion to HTTP/1.x")).toString(), validateHttpHeaders);
        try {
            addHttp2ToHttpHeaders(streamId, http2Headers, msg.headers(), msg.protocolVersion(), false, true);
            return msg;
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable t) {
            Http2Exception streamError = Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static void addHttp2ToHttpHeaders(int streamId, Http2Headers sourceHeaders, FullHttpMessage destinationMessage, boolean addToTrailer) throws Http2Exception {
        addHttp2ToHttpHeaders(streamId, sourceHeaders, addToTrailer ? destinationMessage.trailingHeaders() : destinationMessage.headers(), destinationMessage.protocolVersion(), addToTrailer, destinationMessage instanceof HttpRequest);
    }

    public static void addHttp2ToHttpHeaders(int streamId, Http2Headers inputHeaders, HttpHeaders outputHeaders, HttpVersion httpVersion, boolean isTrailer, boolean isRequest) throws Http2Exception {
        Http2ToHttpHeaderTranslator translator = new Http2ToHttpHeaderTranslator(streamId, outputHeaders, isRequest);
        try {
            for (Entry<CharSequence, CharSequence> entry : inputHeaders) {
                translator.translate(entry);
            }
            outputHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
            outputHeaders.remove(HttpHeaderNames.TRAILER);
            if (!isTrailer) {
                outputHeaders.setInt(ExtensionHeaderNames.STREAM_ID.text(), streamId);
                HttpUtil.setKeepAlive(outputHeaders, httpVersion, true);
            }
        } catch (Http2Exception ex) {
            throw ex;
        } catch (Throwable t) {
            Http2Exception streamError = Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, t, "HTTP/2 to HTTP/1.x headers conversion error", new Object[0]);
        }
    }

    public static Http2Headers toHttp2Headers(HttpMessage in, boolean validateHeaders) {
        HttpHeaders inHeaders = in.headers();
        Http2Headers out = new DefaultHttp2Headers(validateHeaders, inHeaders.size());
        if (in instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) in;
            URI requestTargetUri = URI.create(request.uri());
            out.path(toHttp2Path(requestTargetUri));
            out.method(request.method().asciiName());
            setHttp2Scheme(inHeaders, requestTargetUri, out);
            if (!(HttpUtil.isOriginForm(requestTargetUri) || HttpUtil.isAsteriskForm(requestTargetUri))) {
                String host = inHeaders.getAsString(HttpHeaderNames.HOST);
                if (host == null || host.isEmpty()) {
                    host = requestTargetUri.getAuthority();
                }
                setHttp2Authority(host, out);
            }
        } else if (in instanceof HttpResponse) {
            out.status(new AsciiString(Integer.toString(((HttpResponse) in).status().code())));
        }
        toHttp2Headers(inHeaders, out);
        return out;
    }

    public static Http2Headers toHttp2Headers(HttpHeaders inHeaders, boolean validateHeaders) {
        if (inHeaders.isEmpty()) {
            return EmptyHttp2Headers.INSTANCE;
        }
        Http2Headers out = new DefaultHttp2Headers(validateHeaders, inHeaders.size());
        toHttp2Headers(inHeaders, out);
        return out;
    }

    public static void toHttp2Headers(HttpHeaders inHeaders, Http2Headers out) {
        Iterator<Entry<CharSequence, CharSequence>> iter = inHeaders.iteratorCharSequence();
        while (iter.hasNext()) {
            Entry<CharSequence, CharSequence> entry = (Entry) iter.next();
            AsciiString aName = AsciiString.of((CharSequence) entry.getKey()).toLowerCase();
            if (!HTTP_TO_HTTP2_HEADER_BLACKLIST.contains(aName)) {
                if (aName.contentEqualsIgnoreCase(HttpHeaderNames.TE) && !AsciiString.contentEqualsIgnoreCase((CharSequence) entry.getValue(), HttpHeaderValues.TRAILERS)) {
                    throw new IllegalArgumentException("Invalid value for " + HttpHeaderNames.TE + ": " + entry.getValue());
                } else if (aName.contentEqualsIgnoreCase(HttpHeaderNames.COOKIE)) {
                    AsciiString value = AsciiString.of((CharSequence) entry.getValue());
                    int index = value.forEachByte(ByteProcessor.FIND_SEMI_COLON);
                    if (index != -1) {
                        int start = 0;
                        do {
                            out.add(HttpHeaderNames.COOKIE, value.subSequence(start, index, false));
                            start = index + 2;
                            if (start >= value.length()) {
                                break;
                            }
                            index = value.forEachByte(start, value.length() - start, ByteProcessor.FIND_SEMI_COLON);
                        } while (index != -1);
                        if (start >= value.length()) {
                            throw new IllegalArgumentException("cookie value is of unexpected format: " + value);
                        }
                        try {
                            out.add(HttpHeaderNames.COOKIE, value.subSequence(start, value.length(), false));
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    out.add(HttpHeaderNames.COOKIE, value);
                } else {
                    out.add(aName, entry.getValue());
                }
            }
        }
    }

    private static AsciiString toHttp2Path(URI uri) {
        StringBuilder pathBuilder = new StringBuilder(((StringUtil.length(uri.getRawPath()) + StringUtil.length(uri.getRawQuery())) + StringUtil.length(uri.getRawFragment())) + 2);
        if (!StringUtil.isNullOrEmpty(uri.getRawPath())) {
            pathBuilder.append(uri.getRawPath());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawQuery())) {
            pathBuilder.append('?');
            pathBuilder.append(uri.getRawQuery());
        }
        if (!StringUtil.isNullOrEmpty(uri.getRawFragment())) {
            pathBuilder.append('#');
            pathBuilder.append(uri.getRawFragment());
        }
        CharSequence path = pathBuilder.toString();
        return path.isEmpty() ? EMPTY_REQUEST_PATH : new AsciiString(path);
    }

    private static void setHttp2Authority(String autority, Http2Headers out) {
        if (autority != null) {
            int endOfUserInfo = autority.indexOf(64);
            if (endOfUserInfo < 0) {
                out.authority(new AsciiString((CharSequence) autority));
            } else if (endOfUserInfo + 1 < autority.length()) {
                out.authority(new AsciiString(autority.substring(endOfUserInfo + 1)));
            } else {
                throw new IllegalArgumentException("autority: " + autority);
            }
        }
    }

    private static void setHttp2Scheme(HttpHeaders in, URI uri, Http2Headers out) {
        CharSequence value = uri.getScheme();
        if (value != null) {
            out.scheme(new AsciiString(value));
            return;
        }
        CharSequence cValue = in.get(ExtensionHeaderNames.SCHEME.text());
        if (cValue != null) {
            out.scheme(AsciiString.of(cValue));
        } else if (uri.getPort() == HttpScheme.HTTPS.port()) {
            out.scheme(HttpScheme.HTTPS.name());
        } else if (uri.getPort() == HttpScheme.HTTP.port()) {
            out.scheme(HttpScheme.HTTP.name());
        } else {
            throw new IllegalArgumentException(":scheme must be specified. see https://tools.ietf.org/html/rfc7540#section-8.1.2.3");
        }
    }
}
