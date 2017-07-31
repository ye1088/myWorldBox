package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

class PemValue extends AbstractReferenceCounted implements PemEncoded {
    private final ByteBuf content;
    private final boolean sensitive;

    public PemValue(ByteBuf content, boolean sensitive) {
        this.content = (ByteBuf) ObjectUtil.checkNotNull(content, "content");
        this.sensitive = sensitive;
    }

    public boolean isSensitive() {
        return this.sensitive;
    }

    public ByteBuf content() {
        int count = refCnt();
        if (count > 0) {
            return this.content;
        }
        throw new IllegalReferenceCountException(count);
    }

    public PemValue copy() {
        return replace(this.content.copy());
    }

    public PemValue duplicate() {
        return replace(this.content.duplicate());
    }

    public PemValue retainedDuplicate() {
        return replace(this.content.retainedDuplicate());
    }

    public PemValue replace(ByteBuf content) {
        return new PemValue(content, this.sensitive);
    }

    public PemValue touch() {
        return (PemValue) super.touch();
    }

    public PemValue touch(Object hint) {
        this.content.touch(hint);
        return this;
    }

    public PemValue retain() {
        return (PemValue) super.retain();
    }

    public PemValue retain(int increment) {
        return (PemValue) super.retain(increment);
    }

    protected void deallocate() {
        if (this.sensitive) {
            SslUtils.zeroout(this.content);
        }
        this.content.release();
    }
}
