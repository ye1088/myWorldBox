package io.netty.channel.rxtx;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.rxtx.RxtxChannelConfig.Databits;
import io.netty.channel.rxtx.RxtxChannelConfig.Paritybit;
import io.netty.channel.rxtx.RxtxChannelConfig.Stopbits;
import java.util.Map;

final class DefaultRxtxChannelConfig extends DefaultChannelConfig implements RxtxChannelConfig {
    private volatile int baudrate = 115200;
    private volatile Databits databits = Databits.DATABITS_8;
    private volatile boolean dtr;
    private volatile Paritybit paritybit = Paritybit.NONE;
    private volatile int readTimeout = 1000;
    private volatile boolean rts;
    private volatile Stopbits stopbits = Stopbits.STOPBITS_1;
    private volatile int waitTime;

    DefaultRxtxChannelConfig(RxtxChannel channel) {
        super(channel);
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), RxtxChannelOption.BAUD_RATE, RxtxChannelOption.DTR, RxtxChannelOption.RTS, RxtxChannelOption.STOP_BITS, RxtxChannelOption.DATA_BITS, RxtxChannelOption.PARITY_BIT, RxtxChannelOption.WAIT_TIME);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == RxtxChannelOption.BAUD_RATE) {
            return Integer.valueOf(getBaudrate());
        }
        if (option == RxtxChannelOption.DTR) {
            return Boolean.valueOf(isDtr());
        }
        if (option == RxtxChannelOption.RTS) {
            return Boolean.valueOf(isRts());
        }
        if (option == RxtxChannelOption.STOP_BITS) {
            return getStopbits();
        }
        if (option == RxtxChannelOption.DATA_BITS) {
            return getDatabits();
        }
        if (option == RxtxChannelOption.PARITY_BIT) {
            return getParitybit();
        }
        if (option == RxtxChannelOption.WAIT_TIME) {
            return Integer.valueOf(getWaitTimeMillis());
        }
        if (option == RxtxChannelOption.READ_TIMEOUT) {
            return Integer.valueOf(getReadTimeout());
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == RxtxChannelOption.BAUD_RATE) {
            setBaudrate(((Integer) value).intValue());
        } else if (option == RxtxChannelOption.DTR) {
            setDtr(((Boolean) value).booleanValue());
        } else if (option == RxtxChannelOption.RTS) {
            setRts(((Boolean) value).booleanValue());
        } else if (option == RxtxChannelOption.STOP_BITS) {
            setStopbits((Stopbits) value);
        } else if (option == RxtxChannelOption.DATA_BITS) {
            setDatabits((Databits) value);
        } else if (option == RxtxChannelOption.PARITY_BIT) {
            setParitybit((Paritybit) value);
        } else if (option == RxtxChannelOption.WAIT_TIME) {
            setWaitTimeMillis(((Integer) value).intValue());
        } else if (option != RxtxChannelOption.READ_TIMEOUT) {
            return super.setOption(option, value);
        } else {
            setReadTimeout(((Integer) value).intValue());
        }
        return true;
    }

    public RxtxChannelConfig setBaudrate(int baudrate) {
        this.baudrate = baudrate;
        return this;
    }

    public RxtxChannelConfig setStopbits(Stopbits stopbits) {
        this.stopbits = stopbits;
        return this;
    }

    public RxtxChannelConfig setDatabits(Databits databits) {
        this.databits = databits;
        return this;
    }

    public RxtxChannelConfig setParitybit(Paritybit paritybit) {
        this.paritybit = paritybit;
        return this;
    }

    public int getBaudrate() {
        return this.baudrate;
    }

    public Stopbits getStopbits() {
        return this.stopbits;
    }

    public Databits getDatabits() {
        return this.databits;
    }

    public Paritybit getParitybit() {
        return this.paritybit;
    }

    public boolean isDtr() {
        return this.dtr;
    }

    public RxtxChannelConfig setDtr(boolean dtr) {
        this.dtr = dtr;
        return this;
    }

    public boolean isRts() {
        return this.rts;
    }

    public RxtxChannelConfig setRts(boolean rts) {
        this.rts = rts;
        return this;
    }

    public int getWaitTimeMillis() {
        return this.waitTime;
    }

    public RxtxChannelConfig setWaitTimeMillis(int waitTimeMillis) {
        if (waitTimeMillis < 0) {
            throw new IllegalArgumentException("Wait time must be >= 0");
        }
        this.waitTime = waitTimeMillis;
        return this;
    }

    public RxtxChannelConfig setReadTimeout(int readTimeout) {
        if (readTimeout < 0) {
            throw new IllegalArgumentException("readTime must be >= 0");
        }
        this.readTimeout = readTimeout;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public RxtxChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Deprecated
    public RxtxChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public RxtxChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public RxtxChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public RxtxChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    public RxtxChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Deprecated
    public RxtxChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Deprecated
    public RxtxChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    public RxtxChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }
}
