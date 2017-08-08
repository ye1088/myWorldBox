package com.MCWorld.framework.base.http.toolbox.entity.mime;

import com.MCWorld.framework.base.http.toolbox.entity.ContentType;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.AbstractContentBody;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.ContentBody;
import org.apache.http.protocol.HTTP;

public class FormBodyPartBuilder {
    private ContentBody body;
    private final Header header;
    private String name;

    public static FormBodyPartBuilder create(String name, ContentBody body) {
        return new FormBodyPartBuilder(name, body);
    }

    public static FormBodyPartBuilder create() {
        return new FormBodyPartBuilder();
    }

    FormBodyPartBuilder(String name, ContentBody body) {
        this();
        this.name = name;
        this.body = body;
    }

    FormBodyPartBuilder() {
        this.header = new Header();
    }

    public FormBodyPartBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FormBodyPartBuilder setBody(ContentBody body) {
        this.body = body;
        return this;
    }

    public FormBodyPartBuilder addField(String name, String value) {
        this.header.addField(new MinimalField(name, value));
        return this;
    }

    public FormBodyPartBuilder setField(String name, String value) {
        this.header.setField(new MinimalField(name, value));
        return this;
    }

    public FormBodyPartBuilder removeFields(String name) {
        this.header.removeFields(name);
        return this;
    }

    public FormBodyPart build() {
        StringBuilder buffer;
        Header headerCopy = new Header();
        for (MinimalField field : this.header.getFields()) {
            headerCopy.addField(field);
        }
        if (headerCopy.getField("Content-Disposition") == null) {
            buffer = new StringBuilder();
            buffer.append("form-data; name=\"");
            buffer.append(this.name);
            buffer.append("\"");
            if (this.body.getFilename() != null) {
                buffer.append("; filename=\"");
                buffer.append(this.body.getFilename());
                buffer.append("\"");
            }
            headerCopy.addField(new MinimalField("Content-Disposition", buffer.toString()));
        }
        if (headerCopy.getField("Content-Type") == null) {
            ContentType contentType;
            if (this.body instanceof AbstractContentBody) {
                contentType = ((AbstractContentBody) this.body).getContentType();
            } else {
                contentType = null;
            }
            if (contentType != null) {
                headerCopy.addField(new MinimalField("Content-Type", contentType.toString()));
            } else {
                buffer = new StringBuilder();
                buffer.append(this.body.getMimeType());
                if (this.body.getCharset() != null) {
                    buffer.append(HTTP.CHARSET_PARAM);
                    buffer.append(this.body.getCharset());
                }
                headerCopy.addField(new MinimalField("Content-Type", buffer.toString()));
            }
        }
        if (headerCopy.getField("Content-Transfer-Encoding") == null) {
            headerCopy.addField(new MinimalField("Content-Transfer-Encoding", this.body.getTransferEncoding()));
        }
        return new FormBodyPart(this.name, this.body, headerCopy);
    }
}
