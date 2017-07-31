package io.netty.handler.codec.http2;

class DefaultHttp2HeaderTableListSize {
    private int maxHeaderListSize = Integer.MAX_VALUE;

    DefaultHttp2HeaderTableListSize() {
    }

    public void maxHeaderListSize(int max) throws Http2Exception {
        if (max < 0) {
            this.maxHeaderListSize = Integer.MAX_VALUE;
        } else {
            this.maxHeaderListSize = max;
        }
    }

    public int maxHeaderListSize() {
        return this.maxHeaderListSize;
    }
}
