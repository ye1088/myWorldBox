package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public final class EpollServerSocketChannelConfig extends EpollServerChannelConfig implements ServerSocketChannelConfig {
    EpollServerSocketChannelConfig(EpollServerSocketChannel channel) {
        super(channel);
        setReuseAddress(true);
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), EpollChannelOption.SO_REUSEPORT, EpollChannelOption.IP_FREEBIND, EpollChannelOption.TCP_DEFER_ACCEPT);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == EpollChannelOption.SO_REUSEPORT) {
            return Boolean.valueOf(isReusePort());
        }
        if (option == EpollChannelOption.IP_FREEBIND) {
            return Boolean.valueOf(isFreeBind());
        }
        if (option == EpollChannelOption.TCP_DEFER_ACCEPT) {
            return Integer.valueOf(getTcpDeferAccept());
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == EpollChannelOption.SO_REUSEPORT) {
            setReusePort(((Boolean) value).booleanValue());
        } else if (option == EpollChannelOption.IP_FREEBIND) {
            setFreeBind(((Boolean) value).booleanValue());
        } else if (option == EpollChannelOption.TCP_MD5SIG) {
            setTcpMd5Sig((Map) value);
        } else if (option != EpollChannelOption.TCP_DEFER_ACCEPT) {
            return super.setOption(option, value);
        } else {
            setTcpDeferAccept(((Integer) value).intValue());
        }
        return true;
    }

    public EpollServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }

    public EpollServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    public EpollServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        return this;
    }

    public EpollServerSocketChannelConfig setBacklog(int backlog) {
        super.setBacklog(backlog);
        return this;
    }

    public EpollServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Deprecated
    public EpollServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public EpollServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public EpollServerSocketChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    @Deprecated
    public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Deprecated
    public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    public EpollServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    public EpollServerSocketChannelConfig setTcpMd5Sig(Map<InetAddress, byte[]> keys) {
        try {
            ((EpollServerSocketChannel) this.channel).setTcpMd5Sig(keys);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isReusePort() {
        try {
            return Native.isReusePort(this.channel.fd().intValue()) == 1;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setReusePort(boolean reusePort) {
        try {
            Native.setReusePort(this.channel.fd().intValue(), reusePort ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isFreeBind() {
        try {
            return Native.isIpFreeBind(this.channel.fd().intValue()) != 0;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setFreeBind(boolean freeBind) {
        try {
            Native.setIpFreeBind(this.channel.fd().intValue(), freeBind ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setTcpDeferAccept(int deferAccept) {
        try {
            this.channel.fd().setTcpDeferAccept(deferAccept);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getTcpDeferAccept() {
        try {
            return this.channel.fd().getTcpDeferAccept();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }
}
