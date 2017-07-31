package io.netty.handler.codec.http.websocketx;

import com.tencent.connect.common.Constants;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.URI;

public class WebSocketClientHandshaker13 extends WebSocketClientHandshaker {
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
    private final boolean allowExtensions;
    private final boolean allowMaskMismatch;
    private String expectedChallengeResponseString;
    private final boolean performMasking;

    public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
        this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true, false);
    }

    public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch) {
        super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength);
        this.allowExtensions = allowExtensions;
        this.performMasking = performMasking;
        this.allowMaskMismatch = allowMaskMismatch;
    }

    protected FullHttpRequest newHandshakeRequest() {
        URI wsURL = uri();
        String path = WebSocketClientHandshaker.rawPath(wsURL);
        String key = WebSocketUtil.base64(WebSocketUtil.randomBytes(16));
        this.expectedChallengeResponseString = WebSocketUtil.base64(WebSocketUtil.sha1((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(CharsetUtil.US_ASCII)));
        if (logger.isDebugEnabled()) {
            logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
        }
        int wsPort = WebSocketClientHandshaker.websocketPort(wsURL);
        String host = wsURL.getHost();
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
        HttpHeaders headers = request.headers();
        headers.add(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET).add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE).add(HttpHeaderNames.SEC_WEBSOCKET_KEY, key).add(HttpHeaderNames.HOST, host + ':' + wsPort).add(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, WebSocketClientHandshaker.websocketOriginValue(host, wsPort));
        String expectedSubprotocol = expectedSubprotocol();
        if (!(expectedSubprotocol == null || expectedSubprotocol.isEmpty())) {
            headers.add(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
        }
        headers.add(HttpHeaderNames.SEC_WEBSOCKET_VERSION, Constants.VIA_REPORT_TYPE_JOININ_GROUP);
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        return request;
    }

    protected void verify(FullHttpResponse response) {
        HttpResponseStatus status = HttpResponseStatus.SWITCHING_PROTOCOLS;
        HttpHeaders headers = response.headers();
        if (response.status().equals(status)) {
            CharSequence upgrade = headers.get(HttpHeaderNames.UPGRADE);
            if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(upgrade)) {
                throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
            } else if (headers.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true)) {
                CharSequence accept = headers.get(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
                if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
                    throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[]{accept, this.expectedChallengeResponseString}));
                }
                return;
            } else {
                throw new WebSocketHandshakeException("Invalid handshake response connection: " + headers.get(HttpHeaderNames.CONNECTION));
            }
        }
        throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.status());
    }

    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket13FrameDecoder(false, this.allowExtensions, maxFramePayloadLength(), this.allowMaskMismatch);
    }

    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket13FrameEncoder(this.performMasking);
    }
}
