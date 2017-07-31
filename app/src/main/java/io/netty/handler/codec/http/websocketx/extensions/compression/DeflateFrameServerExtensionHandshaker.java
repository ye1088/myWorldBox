package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import java.util.Collections;

public final class DeflateFrameServerExtensionHandshaker implements WebSocketServerExtensionHandshaker {
    static final String DEFLATE_FRAME_EXTENSION = "deflate-frame";
    static final String X_WEBKIT_DEFLATE_FRAME_EXTENSION = "x-webkit-deflate-frame";
    private final int compressionLevel;

    private static class DeflateFrameServerExtension implements WebSocketServerExtension {
        private final int compressionLevel;
        private final String extensionName;

        public DeflateFrameServerExtension(int compressionLevel, String extensionName) {
            this.extensionName = extensionName;
            this.compressionLevel = compressionLevel;
        }

        public int rsv() {
            return 4;
        }

        public WebSocketExtensionEncoder newExtensionEncoder() {
            return new PerFrameDeflateEncoder(this.compressionLevel, 15, false);
        }

        public WebSocketExtensionDecoder newExtensionDecoder() {
            return new PerFrameDeflateDecoder(false);
        }

        public WebSocketExtensionData newReponseData() {
            return new WebSocketExtensionData(this.extensionName, Collections.emptyMap());
        }
    }

    public DeflateFrameServerExtensionHandshaker() {
        this(6);
    }

    public DeflateFrameServerExtensionHandshaker(int compressionLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        this.compressionLevel = compressionLevel;
    }

    public WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData) {
        if ((X_WEBKIT_DEFLATE_FRAME_EXTENSION.equals(extensionData.name()) || DEFLATE_FRAME_EXTENSION.equals(extensionData.name())) && extensionData.parameters().isEmpty()) {
            return new DeflateFrameServerExtension(this.compressionLevel, extensionData.name());
        }
        return null;
    }
}
