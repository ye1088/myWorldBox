package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map.Entry;

public final class InboundHttp2ToHttpPriorityAdapter extends InboundHttp2ToHttpAdapter {
    private static final AsciiString OUT_OF_MESSAGE_SEQUENCE_METHOD = new AsciiString(HttpConversionUtil.OUT_OF_MESSAGE_SEQUENCE_METHOD.toString());
    private static final AsciiString OUT_OF_MESSAGE_SEQUENCE_PATH = new AsciiString((CharSequence) "");
    private static final AsciiString OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE = new AsciiString(HttpConversionUtil.OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE.toString());
    private final PropertyKey outOfMessageFlowHeadersKey;

    InboundHttp2ToHttpPriorityAdapter(Http2Connection connection, int maxContentLength, boolean validateHttpHeaders, boolean propagateSettings) {
        super(connection, maxContentLength, validateHttpHeaders, propagateSettings);
        this.outOfMessageFlowHeadersKey = connection.newKey();
    }

    private HttpHeaders getOutOfMessageFlowHeaders(Http2Stream stream) {
        return (HttpHeaders) stream.getProperty(this.outOfMessageFlowHeadersKey);
    }

    private void putOutOfMessageFlowHeaders(Http2Stream stream, HttpHeaders headers) {
        stream.setProperty(this.outOfMessageFlowHeadersKey, headers);
    }

    private HttpHeaders removeOutOfMessageFlowHeaders(Http2Stream stream) {
        return (HttpHeaders) stream.removeProperty(this.outOfMessageFlowHeadersKey);
    }

    private static HttpHeaders getActiveHeaders(FullHttpMessage msg) {
        return msg.content().isReadable() ? msg.trailingHeaders() : msg.headers();
    }

    private void importOutOfMessageFlowHeaders(Http2Stream stream, HttpHeaders headers) {
        HttpHeaders outOfMessageFlowHeader = getOutOfMessageFlowHeaders(stream);
        if (outOfMessageFlowHeader == null) {
            putOutOfMessageFlowHeaders(stream, headers);
        } else {
            outOfMessageFlowHeader.setAll(headers);
        }
    }

    private void exportOutOfMessageFlowHeaders(Http2Stream stream, HttpHeaders headers) {
        HttpHeaders outOfMessageFlowHeader = getOutOfMessageFlowHeaders(stream);
        if (outOfMessageFlowHeader != null) {
            headers.setAll(outOfMessageFlowHeader);
        }
    }

    private static void removePriorityRelatedHeaders(HttpHeaders headers) {
        headers.remove(ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text());
        headers.remove(ExtensionHeaderNames.STREAM_WEIGHT.text());
    }

    private void initializePseudoHeaders(Http2Headers headers) {
        if (this.connection.isServer()) {
            headers.method(OUT_OF_MESSAGE_SEQUENCE_METHOD).path(OUT_OF_MESSAGE_SEQUENCE_PATH);
        } else {
            headers.status(OUT_OF_MESSAGE_SEQUENCE_RETURN_CODE);
        }
    }

    private static void addHttpHeadersToHttp2Headers(HttpHeaders httpHeaders, Http2Headers http2Headers) {
        Iterator<Entry<CharSequence, CharSequence>> iter = httpHeaders.iteratorCharSequence();
        while (iter.hasNext()) {
            Entry<CharSequence, CharSequence> entry = (Entry) iter.next();
            http2Headers.add(AsciiString.of((CharSequence) entry.getKey()), AsciiString.of((CharSequence) entry.getValue()));
        }
    }

    protected void fireChannelRead(ChannelHandlerContext ctx, FullHttpMessage msg, boolean release, Http2Stream stream) {
        exportOutOfMessageFlowHeaders(stream, getActiveHeaders(msg));
        super.fireChannelRead(ctx, msg, release, stream);
    }

    protected FullHttpMessage processHeadersBegin(ChannelHandlerContext ctx, Http2Stream stream, Http2Headers headers, boolean endOfStream, boolean allowAppend, boolean appendToTrailer) throws Http2Exception {
        FullHttpMessage msg = super.processHeadersBegin(ctx, stream, headers, endOfStream, allowAppend, appendToTrailer);
        if (msg != null) {
            exportOutOfMessageFlowHeaders(stream, getActiveHeaders(msg));
        }
        return msg;
    }

    public void onPriorityTreeParentChanged(Http2Stream stream, Http2Stream oldParent) {
        Http2Stream parent = stream.parent();
        FullHttpMessage msg = getMessage(stream);
        if (msg == null) {
            if (parent != null && !parent.equals(this.connection.connectionStream())) {
                HttpHeaders headers = new DefaultHttpHeaders();
                headers.setInt(ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), parent.id());
                importOutOfMessageFlowHeaders(stream, headers);
            }
        } else if (parent == null) {
            removePriorityRelatedHeaders(msg.headers());
            removePriorityRelatedHeaders(msg.trailingHeaders());
        } else if (!parent.equals(this.connection.connectionStream())) {
            getActiveHeaders(msg).setInt(ExtensionHeaderNames.STREAM_DEPENDENCY_ID.text(), parent.id());
        }
    }

    public void onWeightChanged(Http2Stream stream, short oldWeight) {
        HttpHeaders headers;
        FullHttpMessage msg = getMessage(stream);
        if (msg == null) {
            headers = new DefaultHttpHeaders();
            importOutOfMessageFlowHeaders(stream, headers);
        } else {
            headers = getActiveHeaders(msg);
        }
        headers.setShort(ExtensionHeaderNames.STREAM_WEIGHT.text(), stream.weight());
    }

    public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
        Http2Stream stream = this.connection.stream(streamId);
        if (stream != null && getMessage(stream) == null) {
            HttpHeaders httpHeaders = removeOutOfMessageFlowHeaders(stream);
            if (httpHeaders == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Priority Frame recieved for unknown stream id %d", Integer.valueOf(streamId));
            }
            Http2Headers http2Headers = new DefaultHttp2Headers(this.validateHttpHeaders, httpHeaders.size());
            initializePseudoHeaders(http2Headers);
            addHttpHeadersToHttp2Headers(httpHeaders, http2Headers);
            fireChannelRead(ctx, newMessage(stream, http2Headers, this.validateHttpHeaders, ctx.alloc()), false, stream);
        }
    }
}
