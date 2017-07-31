package io.netty.channel.rxtx;

public enum RxtxChannelConfig$Databits {
    DATABITS_5(5),
    DATABITS_6(6),
    DATABITS_7(7),
    DATABITS_8(8);
    
    private final int value;

    private RxtxChannelConfig$Databits(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static RxtxChannelConfig$Databits valueOf(int value) {
        for (RxtxChannelConfig$Databits databit : values()) {
            if (databit.value == value) {
                return databit;
            }
        }
        throw new IllegalArgumentException("unknown " + RxtxChannelConfig$Databits.class.getSimpleName() + " value: " + value);
    }
}
