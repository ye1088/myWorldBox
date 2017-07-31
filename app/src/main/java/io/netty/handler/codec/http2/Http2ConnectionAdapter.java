package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2Connection.Listener;

public class Http2ConnectionAdapter implements Listener {
    public void onStreamAdded(Http2Stream stream) {
    }

    public void onStreamActive(Http2Stream stream) {
    }

    public void onStreamHalfClosed(Http2Stream stream) {
    }

    public void onStreamClosed(Http2Stream stream) {
    }

    public void onStreamRemoved(Http2Stream stream) {
    }

    public void onGoAwaySent(int lastStreamId, long errorCode, ByteBuf debugData) {
    }

    public void onGoAwayReceived(int lastStreamId, long errorCode, ByteBuf debugData) {
    }

    public void onPriorityTreeParentChanged(Http2Stream stream, Http2Stream oldParent) {
    }

    public void onPriorityTreeParentChanging(Http2Stream stream, Http2Stream newParent) {
    }

    public void onWeightChanged(Http2Stream stream, short oldWeight) {
    }
}
