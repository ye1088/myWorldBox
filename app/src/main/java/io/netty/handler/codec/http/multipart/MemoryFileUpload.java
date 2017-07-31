package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.nio.charset.Charset;

public class MemoryFileUpload extends AbstractMemoryHttpData implements FileUpload {
    private String contentTransferEncoding;
    private String contentType;
    private String filename;

    public MemoryFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
        super(name, charset, size);
        setFilename(filename);
        setContentType(contentType);
        setContentTransferEncoding(contentTransferEncoding);
    }

    public HttpDataType getHttpDataType() {
        return HttpDataType.FileUpload;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        if (filename == null) {
            throw new NullPointerException("filename");
        }
        this.filename = filename;
    }

    public int hashCode() {
        return FileUploadUtil.hashCode(this);
    }

    public boolean equals(Object o) {
        return (o instanceof FileUpload) && FileUploadUtil.equals(this, (FileUpload) o);
    }

    public int compareTo(InterfaceHttpData o) {
        if (o instanceof FileUpload) {
            return compareTo((FileUpload) o);
        }
        throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
    }

    public int compareTo(FileUpload o) {
        return FileUploadUtil.compareTo(this, o);
    }

    public void setContentType(String contentType) {
        if (contentType == null) {
            throw new NullPointerException("contentType");
        }
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getContentTransferEncoding() {
        return this.contentTransferEncoding;
    }

    public void setContentTransferEncoding(String contentTransferEncoding) {
        this.contentTransferEncoding = contentTransferEncoding;
    }

    public String toString() {
        return HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + this.filename + "\"\r\n" + HttpHeaderNames.CONTENT_TYPE + ": " + this.contentType + (getCharset() != null ? "; " + HttpHeaderValues.CHARSET + '=' + getCharset().name() + "\r\n" : "\r\n") + HttpHeaderNames.CONTENT_LENGTH + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory();
    }

    public FileUpload copy() {
        ByteBuf content = content();
        if (content != null) {
            content = content.copy();
        }
        return replace(content);
    }

    public FileUpload duplicate() {
        ByteBuf content = content();
        if (content != null) {
            content = content.duplicate();
        }
        return replace(content);
    }

    public FileUpload retainedDuplicate() {
        ByteBuf content = content();
        if (content == null) {
            return replace(null);
        }
        content = content.retainedDuplicate();
        boolean success = false;
        try {
            FileUpload duplicate = replace(content);
            success = true;
            return duplicate;
        } finally {
            if (!success) {
                content.release();
            }
        }
    }

    public FileUpload replace(ByteBuf content) {
        MemoryFileUpload upload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
        if (content != null) {
            try {
                upload.setContent(content);
            } catch (Throwable e) {
                throw new ChannelException(e);
            }
        }
        return upload;
    }

    public FileUpload retain() {
        super.retain();
        return this;
    }

    public FileUpload retain(int increment) {
        super.retain(increment);
        return this;
    }

    public FileUpload touch() {
        super.touch();
        return this;
    }

    public FileUpload touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
