package io.netty.handler.codec.http2.internal.hpack;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.MathUtil;
import java.util.Arrays;

public final class Encoder {
    static final /* synthetic */ boolean $assertionsDisabled = (!Encoder.class.desiredAssertionStatus());
    private int capacity;
    private final byte hashMask;
    private final HeaderEntry head;
    private final HeaderEntry[] headerFields;
    private final HuffmanEncoder huffmanEncoder;
    private int size;

    private static class HeaderEntry extends HeaderField {
        HeaderEntry after;
        HeaderEntry before;
        int hash;
        int index;
        HeaderEntry next;

        HeaderEntry(int hash, CharSequence name, CharSequence value, int index, HeaderEntry next) {
            super(name, value);
            this.index = index;
            this.hash = hash;
            this.next = next;
        }

        private void remove() {
            this.before.after = this.after;
            this.after.before = this.before;
            this.before = null;
            this.after = null;
            this.next = null;
        }

        private void addBefore(HeaderEntry existingEntry) {
            this.after = existingEntry;
            this.before = existingEntry.before;
            this.before.after = this;
            this.after.before = this;
        }
    }

    public Encoder(int maxHeaderTableSize) {
        this(maxHeaderTableSize, 16);
    }

    public Encoder(int maxHeaderTableSize, int arraySizeHint) {
        this.head = new HeaderEntry(-1, AsciiString.EMPTY_STRING, AsciiString.EMPTY_STRING, Integer.MAX_VALUE, null);
        this.huffmanEncoder = new HuffmanEncoder();
        if (maxHeaderTableSize < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + maxHeaderTableSize);
        }
        this.capacity = maxHeaderTableSize;
        this.headerFields = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(arraySizeHint, 128)))];
        this.hashMask = (byte) (this.headerFields.length - 1);
        HeaderEntry headerEntry = this.head;
        HeaderEntry headerEntry2 = this.head;
        HeaderEntry headerEntry3 = this.head;
        headerEntry2.after = headerEntry3;
        headerEntry.before = headerEntry3;
    }

    public void encodeHeader(ByteBuf out, CharSequence name, CharSequence value, boolean sensitive) {
        ByteBuf byteBuf;
        CharSequence charSequence;
        CharSequence charSequence2;
        if (sensitive) {
            byteBuf = out;
            charSequence = name;
            charSequence2 = value;
            encodeLiteral(byteBuf, charSequence, charSequence2, IndexType.NEVER, getNameIndex(name));
        } else if (this.capacity == 0) {
            staticTableIndex = StaticTable.getIndex(name, value);
            if (staticTableIndex == -1) {
                byteBuf = out;
                charSequence = name;
                charSequence2 = value;
                encodeLiteral(byteBuf, charSequence, charSequence2, IndexType.NONE, StaticTable.getIndex(name));
                return;
            }
            encodeInteger(out, 128, 7, staticTableIndex);
        } else {
            int headerSize = HeaderField.sizeOf(name, value);
            if (headerSize > this.capacity) {
                byteBuf = out;
                charSequence = name;
                charSequence2 = value;
                encodeLiteral(byteBuf, charSequence, charSequence2, IndexType.NONE, getNameIndex(name));
                return;
            }
            HeaderEntry headerField = getEntry(name, value);
            if (headerField != null) {
                encodeInteger(out, 128, 7, getIndex(headerField.index) + StaticTable.length);
                return;
            }
            staticTableIndex = StaticTable.getIndex(name, value);
            if (staticTableIndex != -1) {
                encodeInteger(out, 128, 7, staticTableIndex);
                return;
            }
            ensureCapacity(headerSize);
            encodeLiteral(out, name, value, IndexType.INCREMENTAL, getNameIndex(name));
            add(name, value);
        }
    }

    public void setMaxHeaderTableSize(ByteBuf out, int maxHeaderTableSize) {
        if (maxHeaderTableSize < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + maxHeaderTableSize);
        } else if (this.capacity != maxHeaderTableSize) {
            this.capacity = maxHeaderTableSize;
            ensureCapacity(0);
            encodeInteger(out, 32, 5, maxHeaderTableSize);
        }
    }

    public int getMaxHeaderTableSize() {
        return this.capacity;
    }

    private static void encodeInteger(ByteBuf out, int mask, int n, int i) {
        if ($assertionsDisabled || (n >= 0 && n <= 8)) {
            int nbits = 255 >>> (8 - n);
            if (i < nbits) {
                out.writeByte(mask | i);
                return;
            }
            out.writeByte(mask | nbits);
            int length = i - nbits;
            while ((length & -128) != 0) {
                out.writeByte((length & 127) | 128);
                length >>>= 7;
            }
            out.writeByte(length);
            return;
        }
        throw new AssertionError("N: " + n);
    }

    private void encodeStringLiteral(ByteBuf out, CharSequence string) {
        int huffmanLength = this.huffmanEncoder.getEncodedLength(string);
        if (huffmanLength < string.length()) {
            encodeInteger(out, 128, 7, huffmanLength);
            this.huffmanEncoder.encode(out, string);
            return;
        }
        encodeInteger(out, 0, 7, string.length());
        if (string instanceof AsciiString) {
            AsciiString asciiString = (AsciiString) string;
            out.writeBytes(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
            return;
        }
        out.writeCharSequence(string, CharsetUtil.ISO_8859_1);
    }

    private void encodeLiteral(ByteBuf out, CharSequence name, CharSequence value, IndexType indexType, int nameIndex) {
        boolean nameIndexValid;
        if (nameIndex != -1) {
            nameIndexValid = true;
        } else {
            nameIndexValid = false;
        }
        switch (indexType) {
            case INCREMENTAL:
                if (!nameIndexValid) {
                    nameIndex = 0;
                }
                encodeInteger(out, 64, 6, nameIndex);
                break;
            case NONE:
                if (!nameIndexValid) {
                    nameIndex = 0;
                }
                encodeInteger(out, 0, 4, nameIndex);
                break;
            case NEVER:
                if (!nameIndexValid) {
                    nameIndex = 0;
                }
                encodeInteger(out, 16, 4, nameIndex);
                break;
            default:
                throw new Error("should not reach here");
        }
        if (!nameIndexValid) {
            encodeStringLiteral(out, name);
        }
        encodeStringLiteral(out, value);
    }

    private int getNameIndex(CharSequence name) {
        int index = StaticTable.getIndex(name);
        if (index != -1) {
            return index;
        }
        index = getIndex(name);
        if (index >= 0) {
            return index + StaticTable.length;
        }
        return index;
    }

    private void ensureCapacity(int headerSize) {
        while (this.size + headerSize > this.capacity && length() != 0) {
            remove();
        }
    }

    int length() {
        return this.size == 0 ? 0 : (this.head.after.index - this.head.before.index) + 1;
    }

    int size() {
        return this.size;
    }

    HeaderField getHeaderField(int index) {
        HeaderEntry entry = this.head;
        int index2 = index;
        while (true) {
            index = index2 - 1;
            if (index2 < 0) {
                return entry;
            }
            entry = entry.before;
            index2 = index;
        }
    }

    private HeaderEntry getEntry(CharSequence name, CharSequence value) {
        if (length() == 0 || name == null || value == null) {
            return null;
        }
        int h = AsciiString.hashCode(name);
        HeaderEntry e = this.headerFields[index(h)];
        while (e != null) {
            if (e.hash == h && (HpackUtil.equalsConstantTime(name, e.name) & HpackUtil.equalsConstantTime(value, e.value)) != 0) {
                return e;
            }
            e = e.next;
        }
        return null;
    }

    private int getIndex(CharSequence name) {
        if (length() == 0 || name == null) {
            return -1;
        }
        int h = AsciiString.hashCode(name);
        HeaderEntry e = this.headerFields[index(h)];
        while (e != null) {
            if (e.hash == h && HpackUtil.equalsConstantTime(name, e.name) != 0) {
                return getIndex(e.index);
            }
            e = e.next;
        }
        return -1;
    }

    private int getIndex(int index) {
        return index == -1 ? -1 : (index - this.head.before.index) + 1;
    }

    private void add(CharSequence name, CharSequence value) {
        int headerSize = HeaderField.sizeOf(name, value);
        if (headerSize > this.capacity) {
            clear();
            return;
        }
        while (this.size + headerSize > this.capacity) {
            remove();
        }
        int h = AsciiString.hashCode(name);
        int i = index(h);
        int i2 = this.head.before.index - 1;
        HeaderEntry e = new HeaderEntry(h, name, value, i2, this.headerFields[i]);
        this.headerFields[i] = e;
        e.addBefore(this.head);
        this.size += headerSize;
    }

    private HeaderField remove() {
        if (this.size == 0) {
            return null;
        }
        HeaderField eldest = this.head.after;
        int i = index(eldest.hash);
        HeaderField prev = this.headerFields[i];
        HeaderField e = prev;
        while (e != null) {
            HeaderField next = e.next;
            if (e == eldest) {
                if (prev == eldest) {
                    this.headerFields[i] = next;
                } else {
                    prev.next = next;
                }
                eldest.remove();
                this.size -= eldest.size();
                return eldest;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    private void clear() {
        Arrays.fill(this.headerFields, null);
        HeaderEntry headerEntry = this.head;
        HeaderEntry headerEntry2 = this.head;
        HeaderEntry headerEntry3 = this.head;
        headerEntry2.after = headerEntry3;
        headerEntry.before = headerEntry3;
        this.size = 0;
    }

    private int index(int h) {
        return this.hashMask & h;
    }
}
