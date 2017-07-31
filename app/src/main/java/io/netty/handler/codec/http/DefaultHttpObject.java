package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

public class DefaultHttpObject implements HttpObject {
    private static final int HASH_CODE_PRIME = 31;
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected DefaultHttpObject() {
    }

    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Deprecated
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    public void setDecoderResult(DecoderResult decoderResult) {
        if (decoderResult == null) {
            throw new NullPointerException("decoderResult");
        }
        this.decoderResult = decoderResult;
    }

    public int hashCode() {
        return this.decoderResult.hashCode() + 31;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttpObject)) {
            return false;
        }
        return decoderResult().equals(((DefaultHttpObject) o).decoderResult());
    }
}
