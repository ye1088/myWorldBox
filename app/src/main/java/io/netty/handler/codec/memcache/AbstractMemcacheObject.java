package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.AbstractReferenceCounted;

public abstract class AbstractMemcacheObject extends AbstractReferenceCounted implements MemcacheObject {
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected AbstractMemcacheObject() {
    }

    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    public void setDecoderResult(DecoderResult result) {
        if (result == null) {
            throw new NullPointerException("DecoderResult should not be null.");
        }
        this.decoderResult = result;
    }
}
