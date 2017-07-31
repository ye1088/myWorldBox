package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpServerChannel;
import com.sun.nio.sctp.SctpStandardSocketOptions;
import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.NetUtil;
import java.io.IOException;
import java.util.Map;

public class DefaultSctpServerChannelConfig extends DefaultChannelConfig implements SctpServerChannelConfig {
    private volatile int backlog = NetUtil.SOMAXCONN;
    private final SctpServerChannel javaChannel;

    public DefaultSctpServerChannelConfig(SctpServerChannel channel, SctpServerChannel javaChannel) {
        super(channel);
        if (javaChannel == null) {
            throw new NullPointerException("javaChannel");
        }
        this.javaChannel = javaChannel;
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return Integer.valueOf(getSendBufferSize());
        }
        if (option == SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
            return getInitMaxStreams();
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) value).intValue());
        } else if (option == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) value).intValue());
        } else if (option != SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
            return super.setOption(option, value);
        } else {
            setInitMaxStreams((InitMaxStreams) value);
        }
        return true;
    }

    public int getSendBufferSize() {
        try {
            return ((Integer) this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF)).intValue();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public SctpServerChannelConfig setSendBufferSize(int sendBufferSize) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, Integer.valueOf(sendBufferSize));
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getReceiveBufferSize() {
        try {
            return ((Integer) this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF)).intValue();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public SctpServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, Integer.valueOf(receiveBufferSize));
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public InitMaxStreams getInitMaxStreams() {
        try {
            return (InitMaxStreams) this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public SctpServerChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams) {
        try {
            this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getBacklog() {
        return this.backlog;
    }

    public SctpServerChannelConfig setBacklog(int backlog) {
        if (backlog < 0) {
            throw new IllegalArgumentException("backlog: " + backlog);
        }
        this.backlog = backlog;
        return this;
    }

    @Deprecated
    public SctpServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public SctpServerChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public SctpServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    public SctpServerChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public SctpServerChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    public SctpServerChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Deprecated
    public SctpServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Deprecated
    public SctpServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    public SctpServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }
}
