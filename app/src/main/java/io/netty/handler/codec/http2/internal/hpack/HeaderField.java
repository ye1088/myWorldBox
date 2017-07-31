package io.netty.handler.codec.http2.internal.hpack;

import io.netty.util.internal.ObjectUtil;

class HeaderField {
    static final int HEADER_ENTRY_OVERHEAD = 32;
    final CharSequence name;
    final CharSequence value;

    static int sizeOf(CharSequence name, CharSequence value) {
        return (name.length() + value.length()) + 32;
    }

    HeaderField(CharSequence name, CharSequence value) {
        this.name = (CharSequence) ObjectUtil.checkNotNull(name, "name");
        this.value = (CharSequence) ObjectUtil.checkNotNull(value, "value");
    }

    int size() {
        return (this.name.length() + this.value.length()) + 32;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HeaderField)) {
            return false;
        }
        HeaderField other = (HeaderField) obj;
        if ((HpackUtil.equalsConstantTime(this.name, other.name) & HpackUtil.equalsConstantTime(this.value, other.value)) == 0) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.name + ": " + this.value;
    }
}
