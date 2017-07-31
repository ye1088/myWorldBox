package io.netty.channel.rxtx;

public enum RxtxChannelConfig$Stopbits {
    STOPBITS_1(1),
    STOPBITS_2(2),
    STOPBITS_1_5(3);
    
    private final int value;

    private RxtxChannelConfig$Stopbits(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static RxtxChannelConfig$Stopbits valueOf(int value) {
        for (RxtxChannelConfig$Stopbits stopbit : values()) {
            if (stopbit.value == value) {
                return stopbit;
            }
        }
        throw new IllegalArgumentException("unknown " + RxtxChannelConfig$Stopbits.class.getSimpleName() + " value: " + value);
    }
}
