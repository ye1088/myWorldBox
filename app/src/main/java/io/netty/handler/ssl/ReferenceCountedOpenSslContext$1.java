package io.netty.handler.ssl;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;

class ReferenceCountedOpenSslContext$1 extends AbstractReferenceCounted {
    final /* synthetic */ ReferenceCountedOpenSslContext this$0;

    ReferenceCountedOpenSslContext$1(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        this.this$0 = referenceCountedOpenSslContext;
    }

    public ReferenceCounted touch(Object hint) {
        if (ReferenceCountedOpenSslContext.access$000(this.this$0) != null) {
            ReferenceCountedOpenSslContext.access$000(this.this$0).record(hint);
        }
        return this.this$0;
    }

    protected void deallocate() {
        this.this$0.destroy();
        if (ReferenceCountedOpenSslContext.access$000(this.this$0) != null) {
            ReferenceCountedOpenSslContext.access$000(this.this$0).close();
        }
    }
}
