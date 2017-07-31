package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DiskFileUpload extends AbstractDiskHttpData implements FileUpload {
    public static String baseDirectory = null;
    public static boolean deleteOnExitTemporaryFile = true;
    public static final String postfix = ".tmp";
    public static final String prefix = "FUp_";
    private String contentTransferEncoding;
    private String contentType;
    private String filename;

    public DiskFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
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
        File file = null;
        try {
            file = getFile();
        } catch (IOException e) {
        }
        return HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + this.filename + "\"\r\n" + HttpHeaderNames.CONTENT_TYPE + ": " + this.contentType + (getCharset() != null ? "; " + HttpHeaderValues.CHARSET + '=' + getCharset().name() + "\r\n" : "\r\n") + HttpHeaderNames.CONTENT_LENGTH + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory() + "\r\nRealFile: " + (file != null ? file.getAbsolutePath() : "null") + " DefaultDeleteAfter: " + deleteOnExitTemporaryFile;
    }

    protected boolean deleteOnExit() {
        return deleteOnExitTemporaryFile;
    }

    protected String getBaseDirectory() {
        return baseDirectory;
    }

    protected String getDiskFilename() {
        return new File(this.filename).getName();
    }

    protected String getPostfix() {
        return postfix;
    }

    protected String getPrefix() {
        return prefix;
    }

    public FileUpload copy() {
        ByteBuf content = content();
        return replace(content != null ? content.copy() : null);
    }

    public FileUpload duplicate() {
        ByteBuf content = content();
        return replace(content != null ? content.duplicate() : null);
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
        DiskFileUpload upload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
        if (content != null) {
            try {
                upload.setContent(content);
            } catch (Throwable e) {
                throw new ChannelException(e);
            }
        }
        return upload;
    }

    public FileUpload retain(int increment) {
        super.retain(increment);
        return this;
    }

    public FileUpload retain() {
        super.retain();
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
