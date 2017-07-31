package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

class EntityEnclosingRequestWrapper$EntityWrapper extends HttpEntityWrapper {
    final /* synthetic */ EntityEnclosingRequestWrapper this$0;

    EntityEnclosingRequestWrapper$EntityWrapper(EntityEnclosingRequestWrapper entityEnclosingRequestWrapper, HttpEntity entity) {
        this.this$0 = entityEnclosingRequestWrapper;
        super(entity);
    }

    @Deprecated
    public void consumeContent() throws IOException {
        EntityEnclosingRequestWrapper.access$002(this.this$0, true);
        super.consumeContent();
    }

    public InputStream getContent() throws IOException {
        EntityEnclosingRequestWrapper.access$002(this.this$0, true);
        return super.getContent();
    }

    public void writeTo(OutputStream outstream) throws IOException {
        EntityEnclosingRequestWrapper.access$002(this.this$0, true);
        super.writeTo(outstream);
    }
}
