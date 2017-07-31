package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2HeadersDecoder.Configuration;
import io.netty.handler.codec.http2.internal.hpack.Decoder;

public class DefaultHttp2HeadersDecoder implements Http2HeadersDecoder, Configuration {
    private static final float HEADERS_COUNT_WEIGHT_HISTORICAL = 0.8f;
    private static final float HEADERS_COUNT_WEIGHT_NEW = 0.2f;
    private final Decoder decoder;
    private float headerArraySizeAccumulator;
    private final Http2HeaderTable headerTable;
    private final int maxHeaderSize;
    private final boolean validateHeaders;

    private final class Http2HeaderTableDecoder extends DefaultHttp2HeaderTableListSize implements Http2HeaderTable {
        private Http2HeaderTableDecoder() {
        }

        public void maxHeaderTableSize(int max) throws Http2Exception {
            if (max < 0) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be non-negative but was %d", Integer.valueOf(max));
            }
            try {
                DefaultHttp2HeadersDecoder.this.decoder.setMaxHeaderTableSize(max);
            } catch (Throwable t) {
                Http2Exception connectionError = Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, t.getMessage(), t);
            }
        }

        public int maxHeaderTableSize() {
            return DefaultHttp2HeadersDecoder.this.decoder.getMaxHeaderTableSize();
        }
    }

    public DefaultHttp2HeadersDecoder() {
        this(true);
    }

    public DefaultHttp2HeadersDecoder(boolean validateHeaders) {
        this(8192, 4096, validateHeaders, 32);
    }

    public DefaultHttp2HeadersDecoder(int maxHeaderSize, int maxHeaderTableSize, boolean validateHeaders, int initialHuffmanDecodeCapacity) {
        this.headerArraySizeAccumulator = 8.0f;
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be positive: " + maxHeaderSize);
        }
        this.decoder = new Decoder(maxHeaderSize, maxHeaderTableSize, initialHuffmanDecodeCapacity);
        this.headerTable = new Http2HeaderTableDecoder();
        this.maxHeaderSize = maxHeaderSize;
        this.validateHeaders = validateHeaders;
    }

    public Http2HeaderTable headerTable() {
        return this.headerTable;
    }

    public int maxHeaderSize() {
        return this.maxHeaderSize;
    }

    public Configuration configuration() {
        return this;
    }

    public Http2Headers decodeHeaders(ByteBuf headerBlock) throws Http2Exception {
        try {
            Http2Headers headers = new DefaultHttp2Headers(this.validateHeaders, (int) this.headerArraySizeAccumulator);
            this.decoder.decode(headerBlock, headers);
            this.headerArraySizeAccumulator = (HEADERS_COUNT_WEIGHT_NEW * ((float) headers.size())) + (HEADERS_COUNT_WEIGHT_HISTORICAL * this.headerArraySizeAccumulator);
            return headers;
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable e2) {
            Http2Exception connectionError = Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, e2, e2.getMessage(), new Object[0]);
        }
    }
}
