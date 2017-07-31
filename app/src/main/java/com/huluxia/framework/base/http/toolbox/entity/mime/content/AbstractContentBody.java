package com.huluxia.framework.base.http.toolbox.entity.mime.content;

import com.huluxia.framework.base.http.toolbox.entity.ContentType;
import java.nio.charset.Charset;

public abstract class AbstractContentBody implements ContentBody {
    private final ContentType contentType;

    public AbstractContentBody(ContentType contentType) {
        this.contentType = contentType;
    }

    @Deprecated
    public AbstractContentBody(String mimeType) {
        this(ContentType.parse(mimeType));
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public String getMimeType() {
        return this.contentType.getMimeType();
    }

    public String getMediaType() {
        String mimeType = this.contentType.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(0, i);
        }
        return mimeType;
    }

    public String getSubType() {
        String mimeType = this.contentType.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(i + 1);
        }
        return null;
    }

    public String getCharset() {
        Charset charset = this.contentType.getCharset();
        return charset != null ? charset.name() : null;
    }
}
