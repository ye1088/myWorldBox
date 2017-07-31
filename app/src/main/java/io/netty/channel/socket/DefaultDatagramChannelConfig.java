package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;

public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
    private volatile boolean activeOnOpen;
    private final DatagramSocket javaSocket;

    public DefaultDatagramChannelConfig(DatagramChannel channel, DatagramSocket javaSocket) {
        super(channel, new FixedRecvByteBufAllocator(2048));
        if (javaSocket == null) {
            throw new NullPointerException("javaSocket");
        }
        this.javaSocket = javaSocket;
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION);
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
        } else if (option != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return super.setOption(option, value);
        } else {
            setActiveOnOpen(((Boolean) value).booleanValue());
        }
        return true;
    }

    private void setActiveOnOpen(boolean activeOnOpen) {
        if (this.channel.isRegistered()) {
            throw new IllegalStateException("Can only changed before channel was registered");
        }
        this.activeOnOpen = activeOnOpen;
    }

    public boolean isBroadcast() {
        try {
            return this.javaSocket.getBroadcast();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setBroadcast(boolean broadcast) {
        if (broadcast) {
            try {
                if (!(this.javaSocket.getLocalAddress().isAnyLocalAddress() || PlatformDependent.isWindows() || PlatformDependent.isRoot())) {
                    logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
                }
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        this.javaSocket.setBroadcast(broadcast);
        return this;
    }

    public InetAddress getInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) this.javaSocket).getInterface();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) this.javaSocket).setInterface(interfaceAddress);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public boolean isLoopbackModeDisabled() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) this.javaSocket).getLoopbackMode();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) this.javaSocket).setLoopbackMode(loopbackModeDisabled);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public NetworkInterface getNetworkInterface() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) this.javaSocket).getNetworkInterface();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) this.javaSocket).setNetworkInterface(networkInterface);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setReuseAddress(boolean reuseAddress) {
        try {
            this.javaSocket.setReuseAddress(reuseAddress);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        try {
            this.javaSocket.setReceiveBufferSize(receiveBufferSize);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setSendBufferSize(int sendBufferSize) {
        try {
            this.javaSocket.setSendBufferSize(sendBufferSize);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public int getTimeToLive() {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) this.javaSocket).getTimeToLive();
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public DatagramChannelConfig setTimeToLive(int ttl) {
        if (this.javaSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) this.javaSocket).setTimeToLive(ttl);
                return this;
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        throw new UnsupportedOperationException();
    }

    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setTrafficClass(int trafficClass) {
        try {
            this.javaSocket.setTrafficClass(trafficClass);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    public DatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    public DatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Deprecated
    public DatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    public DatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    public DatagramChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Deprecated
    public DatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Deprecated
    public DatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    public DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }
}
