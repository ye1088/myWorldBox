package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DatagramChannelConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;

public final class EpollDatagramChannelConfig extends EpollChannelConfig implements DatagramChannelConfig {
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
    private boolean activeOnOpen;
    private final EpollDatagramChannel datagramChannel;

    EpollDatagramChannelConfig(EpollDatagramChannel channel) {
        super(channel);
        this.datagramChannel = channel;
        setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, EpollChannelOption.SO_REUSEPORT);
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == ChannelOption.SO_BROADCAST) {
            return Boolean.valueOf(isBroadcast());
        }
        if (option == ChannelOption.SO_RCVBUF) {
            return Integer.valueOf(getReceiveBufferSize());
        }
        if (option == ChannelOption.SO_SNDBUF) {
            return Integer.valueOf(getSendBufferSize());
        }
        if (option == ChannelOption.SO_REUSEADDR) {
            return Boolean.valueOf(isReuseAddress());
        }
        if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return Boolean.valueOf(isLoopbackModeDisabled());
        }
        if (option == ChannelOption.IP_MULTICAST_ADDR) {
            return getInterface();
        }
        if (option == ChannelOption.IP_MULTICAST_IF) {
            return getNetworkInterface();
        }
        if (option == ChannelOption.IP_MULTICAST_TTL) {
            return Integer.valueOf(getTimeToLive());
        }
        if (option == ChannelOption.IP_TOS) {
            return Integer.valueOf(getTrafficClass());
        }
        if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return Boolean.valueOf(this.activeOnOpen);
        }
        if (option == EpollChannelOption.SO_REUSEPORT) {
            return Boolean.valueOf(isReusePort());
        }
        return super.getOption(option);
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == ChannelOption.SO_BROADCAST) {
            setBroadcast(((Boolean) value).booleanValue());
        } else if (option == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) value).intValue());
        } else if (option == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) value).intValue());
        } else if (option == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) value).booleanValue());
        } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            setLoopbackModeDisabled(((Boolean) value).booleanValue());
        } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
            setInterface((InetAddress) value);
        } else if (option == ChannelOption.IP_MULTICAST_IF) {
            setNetworkInterface((NetworkInterface) value);
        } else if (option == ChannelOption.IP_MULTICAST_TTL) {
            setTimeToLive(((Integer) value).intValue());
        } else if (option == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) value).intValue());
        } else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            setActiveOnOpen(((Boolean) value).booleanValue());
        } else if (option != EpollChannelOption.SO_REUSEPORT) {
            return super.setOption(option, value);
        } else {
            setReusePort(((Boolean) value).booleanValue());
        }
        return true;
    }

    private void setActiveOnOpen(boolean activeOnOpen) {
        if (this.channel.isRegistered()) {
            throw new IllegalStateException("Can only changed before channel was registered");
        }
        this.activeOnOpen = activeOnOpen;
    }

    public EpollDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    @Deprecated
    public EpollDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Deprecated
    public EpollDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    public EpollDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public EpollDatagramChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    public EpollDatagramChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    public EpollDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public EpollDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public EpollDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public EpollDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Deprecated
    public EpollDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public int getSendBufferSize() {
        try {
            return this.datagramChannel.fd().getSendBufferSize();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
        try {
            this.datagramChannel.fd().setSendBufferSize(sendBufferSize);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getReceiveBufferSize() {
        try {
            return this.datagramChannel.fd().getReceiveBufferSize();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        try {
            this.datagramChannel.fd().setReceiveBufferSize(receiveBufferSize);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getTrafficClass() {
        try {
            return Native.getTrafficClass(this.datagramChannel.fd().intValue());
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setTrafficClass(int trafficClass) {
        try {
            Native.setTrafficClass(this.datagramChannel.fd().intValue(), trafficClass);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isReuseAddress() {
        try {
            return Native.isReuseAddress(this.datagramChannel.fd().intValue()) == 1;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setReuseAddress(boolean reuseAddress) {
        try {
            Native.setReuseAddress(this.datagramChannel.fd().intValue(), reuseAddress ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isBroadcast() {
        try {
            return Native.isBroadcast(this.datagramChannel.fd().intValue()) == 1;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setBroadcast(boolean broadcast) {
        try {
            Native.setBroadcast(this.datagramChannel.fd().intValue(), broadcast ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isLoopbackModeDisabled() {
        return false;
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        throw new UnsupportedOperationException("Multicast not supported");
    }

    public int getTimeToLive() {
        return -1;
    }

    public EpollDatagramChannelConfig setTimeToLive(int ttl) {
        throw new UnsupportedOperationException("Multicast not supported");
    }

    public InetAddress getInterface() {
        return null;
    }

    public EpollDatagramChannelConfig setInterface(InetAddress interfaceAddress) {
        throw new UnsupportedOperationException("Multicast not supported");
    }

    public NetworkInterface getNetworkInterface() {
        return null;
    }

    public EpollDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        throw new UnsupportedOperationException("Multicast not supported");
    }

    public EpollDatagramChannelConfig setEpollMode(EpollMode mode) {
        super.setEpollMode(mode);
        return this;
    }

    public boolean isReusePort() {
        try {
            return Native.isReusePort(this.datagramChannel.fd().intValue()) == 1;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollDatagramChannelConfig setReusePort(boolean reusePort) {
        try {
            Native.setReusePort(this.datagramChannel.fd().intValue(), reusePort ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }
}
