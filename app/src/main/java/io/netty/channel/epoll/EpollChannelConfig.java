package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.IOException;
import java.util.Map;

public class EpollChannelConfig extends DefaultChannelConfig {
    final AbstractEpollChannel channel;

    EpollChannelConfig(AbstractEpollChannel channel) {
        super(channel);
        this.channel = channel;
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), EpollChannelOption.EPOLL_MODE);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == EpollChannelOption.EPOLL_MODE) {
            return getEpollMode();
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option != EpollChannelOption.EPOLL_MODE) {
            return super.setOption(option, value);
        }
        setEpollMode((EpollMode) value);
        return true;
    }

    public EpollChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Deprecated
    public EpollChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public EpollChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public EpollChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public EpollChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public EpollChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    @Deprecated
    public EpollChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Deprecated
    public EpollChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    public EpollChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public EpollChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    public EpollMode getEpollMode() {
        return this.channel.isFlagSet(Native.EPOLLET) ? EpollMode.EDGE_TRIGGERED : EpollMode.LEVEL_TRIGGERED;
    }

    public EpollChannelConfig setEpollMode(EpollMode mode) {
        if (mode == null) {
            throw new NullPointerException(Values.MODE);
        }
        try {
            switch (1.$SwitchMap$io$netty$channel$epoll$EpollMode[mode.ordinal()]) {
                case 1:
                    checkChannelNotRegistered();
                    this.channel.setFlag(Native.EPOLLET);
                    break;
                case 2:
                    checkChannelNotRegistered();
                    this.channel.clearFlag(Native.EPOLLET);
                    break;
                default:
                    throw new Error();
            }
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    private void checkChannelNotRegistered() {
        if (this.channel.isRegistered()) {
            throw new IllegalStateException("EpollMode can only be changed before channel is registered");
        }
    }

    protected final void autoReadCleared() {
        this.channel.clearEpollIn();
    }
}
