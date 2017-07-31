package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Iterator;

final class SelectedSelectionKeySet extends AbstractSet<SelectionKey> {
    private boolean isA = true;
    private SelectionKey[] keysA = new SelectionKey[1024];
    private int keysASize;
    private SelectionKey[] keysB = ((SelectionKey[]) this.keysA.clone());
    private int keysBSize;

    SelectedSelectionKeySet() {
    }

    public boolean add(SelectionKey o) {
        if (o == null) {
            return false;
        }
        int size;
        int size2;
        if (this.isA) {
            size = this.keysASize;
            size2 = size + 1;
            this.keysA[size] = o;
            this.keysASize = size2;
            if (size2 == this.keysA.length) {
                doubleCapacityA();
            }
            size = size2;
        } else {
            size = this.keysBSize;
            size2 = size + 1;
            this.keysB[size] = o;
            this.keysBSize = size2;
            if (size2 == this.keysB.length) {
                doubleCapacityB();
            }
            size = size2;
        }
        return true;
    }

    private void doubleCapacityA() {
        SelectionKey[] newKeysA = new SelectionKey[(this.keysA.length << 1)];
        System.arraycopy(this.keysA, 0, newKeysA, 0, this.keysASize);
        this.keysA = newKeysA;
    }

    private void doubleCapacityB() {
        SelectionKey[] newKeysB = new SelectionKey[(this.keysB.length << 1)];
        System.arraycopy(this.keysB, 0, newKeysB, 0, this.keysBSize);
        this.keysB = newKeysB;
    }

    SelectionKey[] flip() {
        if (this.isA) {
            this.isA = false;
            this.keysA[this.keysASize] = null;
            this.keysBSize = 0;
            return this.keysA;
        }
        this.isA = true;
        this.keysB[this.keysBSize] = null;
        this.keysASize = 0;
        return this.keysB;
    }

    public int size() {
        if (this.isA) {
            return this.keysASize;
        }
        return this.keysBSize;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }
}
