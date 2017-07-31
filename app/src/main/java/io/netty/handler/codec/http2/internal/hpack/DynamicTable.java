package io.netty.handler.codec.http2.internal.hpack;

final class DynamicTable {
    private int capacity = -1;
    int head;
    HeaderField[] headerFields;
    private int size;
    int tail;

    DynamicTable(int initialCapacity) {
        setCapacity(initialCapacity);
    }

    public int length() {
        if (this.head < this.tail) {
            return (this.headerFields.length - this.tail) + this.head;
        }
        return this.head - this.tail;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public HeaderField getEntry(int index) {
        if (index <= 0 || index > length()) {
            throw new IndexOutOfBoundsException();
        }
        int i = this.head - index;
        if (i < 0) {
            return this.headerFields[this.headerFields.length + i];
        }
        return this.headerFields[i];
    }

    public void add(HeaderField header) {
        int headerSize = header.size();
        if (headerSize > this.capacity) {
            clear();
            return;
        }
        while (this.size + headerSize > this.capacity) {
            remove();
        }
        HeaderField[] headerFieldArr = this.headerFields;
        int i = this.head;
        this.head = i + 1;
        headerFieldArr[i] = header;
        this.size += header.size();
        if (this.head == this.headerFields.length) {
            this.head = 0;
        }
    }

    public HeaderField remove() {
        HeaderField removed = this.headerFields[this.tail];
        if (removed == null) {
            return null;
        }
        this.size -= removed.size();
        HeaderField[] headerFieldArr = this.headerFields;
        int i = this.tail;
        this.tail = i + 1;
        headerFieldArr[i] = null;
        if (this.tail != this.headerFields.length) {
            return removed;
        }
        this.tail = 0;
        return removed;
    }

    public void clear() {
        while (this.tail != this.head) {
            HeaderField[] headerFieldArr = this.headerFields;
            int i = this.tail;
            this.tail = i + 1;
            headerFieldArr[i] = null;
            if (this.tail == this.headerFields.length) {
                this.tail = 0;
            }
        }
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        } else if (this.capacity != capacity) {
            this.capacity = capacity;
            if (capacity == 0) {
                clear();
            } else {
                while (this.size > capacity) {
                    remove();
                }
            }
            int maxEntries = capacity / 32;
            if (capacity % 32 != 0) {
                maxEntries++;
            }
            if (this.headerFields == null || this.headerFields.length != maxEntries) {
                HeaderField[] tmp = new HeaderField[maxEntries];
                int len = length();
                int i = 0;
                int cursor = this.tail;
                while (i < len) {
                    int cursor2 = cursor + 1;
                    tmp[i] = this.headerFields[cursor];
                    if (cursor2 == this.headerFields.length) {
                        cursor2 = 0;
                    }
                    i++;
                    cursor = cursor2;
                }
                this.tail = 0;
                this.head = this.tail + len;
                this.headerFields = tmp;
            }
        }
    }
}
