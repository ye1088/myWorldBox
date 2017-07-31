package com.google.protobuf;

import java.io.IOException;

public class LazyFieldLite {
    private ByteString bytes;
    private ExtensionRegistryLite extensionRegistry;
    private volatile boolean isDirty = false;
    protected volatile MessageLite value;

    public LazyFieldLite(ExtensionRegistryLite extensionRegistry, ByteString bytes) {
        this.extensionRegistry = extensionRegistry;
        this.bytes = bytes;
    }

    public static LazyFieldLite fromValue(MessageLite value) {
        LazyFieldLite lf = new LazyFieldLite();
        lf.setValue(value);
        return lf;
    }

    public boolean containsDefaultInstance() {
        return this.value == null && this.bytes == null;
    }

    public void clear() {
        this.bytes = null;
        this.value = null;
        this.extensionRegistry = null;
        this.isDirty = true;
    }

    public MessageLite getValue(MessageLite defaultInstance) {
        ensureInitialized(defaultInstance);
        return this.value;
    }

    public MessageLite setValue(MessageLite value) {
        MessageLite originalValue = this.value;
        this.value = value;
        this.bytes = null;
        this.isDirty = true;
        return originalValue;
    }

    public void merge(LazyFieldLite value) {
        if (!value.containsDefaultInstance()) {
            if (this.bytes == null) {
                this.bytes = value.bytes;
            } else {
                this.bytes.concat(value.toByteString());
            }
            this.isDirty = false;
        }
    }

    public void setByteString(ByteString bytes, ExtensionRegistryLite extensionRegistry) {
        this.bytes = bytes;
        this.extensionRegistry = extensionRegistry;
        this.isDirty = false;
    }

    public ExtensionRegistryLite getExtensionRegistry() {
        return this.extensionRegistry;
    }

    public int getSerializedSize() {
        if (this.isDirty) {
            return this.value.getSerializedSize();
        }
        return this.bytes.size();
    }

    public ByteString toByteString() {
        if (!this.isDirty) {
            return this.bytes;
        }
        synchronized (this) {
            if (this.isDirty) {
                if (this.value == null) {
                    this.bytes = ByteString.EMPTY;
                } else {
                    this.bytes = this.value.toByteString();
                }
                this.isDirty = false;
                ByteString byteString = this.bytes;
                return byteString;
            }
            byteString = this.bytes;
            return byteString;
        }
    }

    protected void ensureInitialized(MessageLite defaultInstance) {
        if (this.value == null) {
            synchronized (this) {
                if (this.value != null) {
                    return;
                }
                try {
                    if (this.bytes != null) {
                        this.value = (MessageLite) defaultInstance.getParserForType().parseFrom(this.bytes, this.extensionRegistry);
                    } else {
                        this.value = defaultInstance;
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}
