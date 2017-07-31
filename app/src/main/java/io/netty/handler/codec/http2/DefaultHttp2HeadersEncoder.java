package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.Http2HeadersEncoder.Configuration;
import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;
import io.netty.handler.codec.http2.internal.hpack.Encoder;
import io.netty.util.internal.ObjectUtil;
import java.util.Map.Entry;

public class DefaultHttp2HeadersEncoder implements Http2HeadersEncoder, Configuration {
    private final Encoder encoder;
    private final Http2HeaderTable headerTable;
    private final SensitivityDetector sensitivityDetector;
    private final ByteBuf tableSizeChangeOutput;

    private final class Http2HeaderTableEncoder extends DefaultHttp2HeaderTableListSize implements Http2HeaderTable {
        private Http2HeaderTableEncoder() {
        }

        public void maxHeaderTableSize(int max) throws Http2Exception {
            if (max < 0) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be non-negative but was %d", Integer.valueOf(max));
            }
            try {
                DefaultHttp2HeadersEncoder.this.encoder.setMaxHeaderTableSize(DefaultHttp2HeadersEncoder.this.tableSizeChangeOutput, max);
            } catch (Throwable t) {
                Http2Exception http2Exception = new Http2Exception(Http2Error.PROTOCOL_ERROR, t.getMessage(), t);
            }
        }

        public int maxHeaderTableSize() {
            return DefaultHttp2HeadersEncoder.this.encoder.getMaxHeaderTableSize();
        }
    }

    public DefaultHttp2HeadersEncoder() {
        this(4096, NEVER_SENSITIVE);
    }

    public DefaultHttp2HeadersEncoder(int maxHeaderTableSize, SensitivityDetector sensitivityDetector) {
        this.tableSizeChangeOutput = Unpooled.buffer();
        this.sensitivityDetector = (SensitivityDetector) ObjectUtil.checkNotNull(sensitivityDetector, "sensitiveDetector");
        this.encoder = new Encoder(maxHeaderTableSize);
        this.headerTable = new Http2HeaderTableEncoder();
    }

    public void encodeHeaders(Http2Headers headers, ByteBuf buffer) throws Http2Exception {
        try {
            if (headers.size() > this.headerTable.maxHeaderListSize()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Number of headers (%d) exceeds maxHeaderListSize (%d)", Integer.valueOf(headers.size()), Integer.valueOf(this.headerTable.maxHeaderListSize()));
            }
            if (this.tableSizeChangeOutput.isReadable()) {
                buffer.writeBytes(this.tableSizeChangeOutput);
                this.tableSizeChangeOutput.clear();
            }
            for (Entry<CharSequence, CharSequence> header : headers) {
                encodeHeader(buffer, (CharSequence) header.getKey(), (CharSequence) header.getValue());
            }
        } catch (Http2Exception e) {
            throw e;
        } catch (Throwable t) {
            Http2Exception connectionError = Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, t, "Failed encoding headers block: %s", t.getMessage());
        }
    }

    public Http2HeaderTable headerTable() {
        return this.headerTable;
    }

    public Configuration configuration() {
        return this;
    }

    private void encodeHeader(ByteBuf out, CharSequence key, CharSequence value) {
        this.encoder.encodeHeader(out, key, value, this.sensitivityDetector.isSensitive(key, value));
    }
}
