package io.netty.channel;

public final class WriteBufferWaterMark {
    public static final WriteBufferWaterMark DEFAULT = new WriteBufferWaterMark(32768, 65536, false);
    private static final int DEFAULT_HIGH_WATER_MARK = 65536;
    private static final int DEFAULT_LOW_WATER_MARK = 32768;
    private final int high;
    private final int low;

    public WriteBufferWaterMark(int low, int high) {
        this(low, high, true);
    }

    WriteBufferWaterMark(int low, int high, boolean validate) {
        if (validate) {
            if (low < 0) {
                throw new IllegalArgumentException("write buffer's low water mark must be >= 0");
            } else if (high < low) {
                throw new IllegalArgumentException("write buffer's high water mark cannot be less than  low water mark (" + low + "): " + high);
            }
        }
        this.low = low;
        this.high = high;
    }

    public int low() {
        return this.low;
    }

    public int high() {
        return this.high;
    }

    public String toString() {
        return "WriteBufferWaterMark(low: " + this.low + ", high: " + this.high + ")";
    }
}
