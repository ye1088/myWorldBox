package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.io.IOException;
import java.nio.charset.Charset;

public class DiskAttribute extends AbstractDiskHttpData implements Attribute {
    public static String baseDirectory = null;
    public static boolean deleteOnExitTemporaryFile = true;
    public static final String postfix = ".att";
    public static final String prefix = "Attr_";

    public DiskAttribute(String name) {
        this(name, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, long definedSize) {
        this(name, definedSize, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, Charset charset) {
        super(name, charset, 0);
    }

    public DiskAttribute(String name, long definedSize, Charset charset) {
        super(name, charset, definedSize);
    }

    public DiskAttribute(String name, String value) throws IOException {
        this(name, value, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, String value, Charset charset) throws IOException {
        super(name, charset, 0);
        setValue(value);
    }

    public HttpDataType getHttpDataType() {
        return HttpDataType.Attribute;
    }

    public String getValue() throws IOException {
        return new String(get(), getCharset());
    }

    public void setValue(String value) throws IOException {
        if (value == null) {
            throw new NullPointerException("value");
        }
        byte[] bytes = value.getBytes(getCharset());
        checkSize((long) bytes.length);
        ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
        if (this.definedSize > 0) {
            this.definedSize = (long) buffer.readableBytes();
        }
        setContent(buffer);
    }

    public void addContent(ByteBuf buffer, boolean last) throws IOException {
        long newDefinedSize = this.size + ((long) buffer.readableBytes());
        checkSize(newDefinedSize);
        if (this.definedSize > 0 && this.definedSize < newDefinedSize) {
            this.definedSize = newDefinedSize;
        }
        super.addContent(buffer, last);
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Attribute)) {
            return false;
        }
        return getName().equalsIgnoreCase(((Attribute) o).getName());
    }

    public int compareTo(InterfaceHttpData o) {
        if (o instanceof Attribute) {
            return compareTo((Attribute) o);
        }
        throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
    }

    public int compareTo(Attribute o) {
        return getName().compareToIgnoreCase(o.getName());
    }

    public String toString() {
        try {
            return getName() + '=' + getValue();
        } catch (IOException e) {
            return getName() + '=' + e;
        }
    }

    protected boolean deleteOnExit() {
        return deleteOnExitTemporaryFile;
    }

    protected String getBaseDirectory() {
        return baseDirectory;
    }

    protected String getDiskFilename() {
        return getName() + postfix;
    }

    protected String getPostfix() {
        return postfix;
    }

    protected String getPrefix() {
        return prefix;
    }

    public Attribute copy() {
        ByteBuf content = content();
        return replace(content != null ? content.copy() : null);
    }

    public Attribute duplicate() {
        ByteBuf content = content();
        return replace(content != null ? content.duplicate() : null);
    }

    public Attribute retainedDuplicate() {
        ByteBuf content = content();
        if (content == null) {
            return replace(null);
        }
        content = content.retainedDuplicate();
        boolean success = false;
        try {
            Attribute duplicate = replace(content);
            success = true;
            return duplicate;
        } finally {
            if (!success) {
                content.release();
            }
        }
    }

    public Attribute replace(ByteBuf content) {
        DiskAttribute attr = new DiskAttribute(getName());
        attr.setCharset(getCharset());
        if (content != null) {
            try {
                attr.setContent(content);
            } catch (Throwable e) {
                throw new ChannelException(e);
            }
        }
        return attr;
    }

    public Attribute retain(int increment) {
        super.retain(increment);
        return this;
    }

    public Attribute retain() {
        super.retain();
        return this;
    }

    public Attribute touch() {
        super.touch();
        return this;
    }

    public Attribute touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
