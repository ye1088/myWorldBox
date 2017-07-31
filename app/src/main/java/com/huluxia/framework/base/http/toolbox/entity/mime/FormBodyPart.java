package com.huluxia.framework.base.http.toolbox.entity.mime;

import com.huluxia.framework.base.http.toolbox.entity.ContentType;
import com.huluxia.framework.base.http.toolbox.entity.mime.content.AbstractContentBody;
import com.huluxia.framework.base.http.toolbox.entity.mime.content.ContentBody;
import org.apache.http.protocol.HTTP;

public class FormBodyPart {
    private final ContentBody body;
    private final Header header;
    private final String name;

    FormBodyPart(String name, ContentBody body, Header header) {
        this.name = name;
        this.body = body;
        if (header == null) {
            header = new Header();
        }
        this.header = header;
    }

    @Deprecated
    public FormBodyPart(String name, ContentBody body) {
        this.name = name;
        this.body = body;
        this.header = new Header();
        generateContentDisp(body);
        generateContentType(body);
        generateTransferEncoding(body);
    }

    public String getName() {
        return this.name;
    }

    public ContentBody getBody() {
        return this.body;
    }

    public Header getHeader() {
        return this.header;
    }

    public void addField(String name, String value) {
        this.header.addField(new MinimalField(name, value));
    }

    @Deprecated
    protected void generateContentDisp(ContentBody body) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("form-data; name=\"");
        buffer.append(getName());
        buffer.append("\"");
        if (body.getFilename() != null) {
            buffer.append("; filename=\"");
            buffer.append(body.getFilename());
            buffer.append("\"");
        }
        addField("Content-Disposition", buffer.toString());
    }

    @Deprecated
    protected void generateContentType(ContentBody body) {
        ContentType contentType;
        if (body instanceof AbstractContentBody) {
            contentType = ((AbstractContentBody) body).getContentType();
        } else {
            contentType = null;
        }
        if (contentType != null) {
            addField("Content-Type", contentType.toString());
            return;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(body.getMimeType());
        if (body.getCharset() != null) {
            buffer.append(HTTP.CHARSET_PARAM);
            buffer.append(body.getCharset());
        }
        addField("Content-Type", buffer.toString());
    }

    @Deprecated
    protected void generateTransferEncoding(ContentBody body) {
        addField("Content-Transfer-Encoding", body.getTransferEncoding());
    }
}
