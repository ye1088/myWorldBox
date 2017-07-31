package io.netty.channel.rxtx;

public enum RxtxChannelConfig$Paritybit {
    NONE(0),
    ODD(1),
    EVEN(2),
    MARK(3),
    SPACE(4);
    
    private final int value;

    private RxtxChannelConfig$Paritybit(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static RxtxChannelConfig$Paritybit valueOf(int value) {
        for (RxtxChannelConfig$Paritybit paritybit : values()) {
            if (paritybit.value == value) {
                return paritybit;
            }
        }
        throw new IllegalArgumentException("unknown " + RxtxChannelConfig$Paritybit.class.getSimpleName() + " value: " + value);
    }
}
