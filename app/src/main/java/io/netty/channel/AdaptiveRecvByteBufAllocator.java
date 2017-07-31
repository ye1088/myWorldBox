package io.netty.channel;

import io.netty.channel.RecvByteBufAllocator.Handle;
import java.util.ArrayList;
import java.util.List;

public class AdaptiveRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    @Deprecated
    public static final AdaptiveRecvByteBufAllocator DEFAULT = new AdaptiveRecvByteBufAllocator();
    static final int DEFAULT_INITIAL = 1024;
    static final int DEFAULT_MAXIMUM = 65536;
    static final int DEFAULT_MINIMUM = 64;
    private static final int INDEX_DECREMENT = 1;
    private static final int INDEX_INCREMENT = 4;
    private static final int[] SIZE_TABLE;
    private final int initial;
    private final int maxIndex;
    private final int minIndex;

    static {
        int i;
        List<Integer> sizeTable = new ArrayList();
        for (i = 16; i < 512; i += 16) {
            sizeTable.add(Integer.valueOf(i));
        }
        for (i = 512; i > 0; i <<= 1) {
            sizeTable.add(Integer.valueOf(i));
        }
        SIZE_TABLE = new int[sizeTable.size()];
        for (i = 0; i < SIZE_TABLE.length; i++) {
            SIZE_TABLE[i] = ((Integer) sizeTable.get(i)).intValue();
        }
    }

    private static int getSizeTableIndex(int size) {
        int low = 0;
        int high = SIZE_TABLE.length - 1;
        while (high >= low) {
            if (high == low) {
                return high;
            }
            int mid = (low + high) >>> 1;
            int a = SIZE_TABLE[mid];
            if (size > SIZE_TABLE[mid + 1]) {
                low = mid + 1;
            } else if (size < a) {
                high = mid - 1;
            } else if (size == a) {
                return mid;
            } else {
                return mid + 1;
            }
        }
        return low;
    }

    public AdaptiveRecvByteBufAllocator() {
        this(64, 1024, 65536);
    }

    public AdaptiveRecvByteBufAllocator(int minimum, int initial, int maximum) {
        if (minimum <= 0) {
            throw new IllegalArgumentException("minimum: " + minimum);
        } else if (initial < minimum) {
            throw new IllegalArgumentException("initial: " + initial);
        } else if (maximum < initial) {
            throw new IllegalArgumentException("maximum: " + maximum);
        } else {
            int minIndex = getSizeTableIndex(minimum);
            if (SIZE_TABLE[minIndex] < minimum) {
                this.minIndex = minIndex + 1;
            } else {
                this.minIndex = minIndex;
            }
            int maxIndex = getSizeTableIndex(maximum);
            if (SIZE_TABLE[maxIndex] > maximum) {
                this.maxIndex = maxIndex - 1;
            } else {
                this.maxIndex = maxIndex;
            }
            this.initial = initial;
        }
    }

    public Handle newHandle() {
        return new HandleImpl(this, this.minIndex, this.maxIndex, this.initial);
    }
}
