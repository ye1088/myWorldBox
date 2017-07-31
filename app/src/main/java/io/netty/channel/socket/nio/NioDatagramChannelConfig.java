package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.PlatformDependent;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;

class NioDatagramChannelConfig extends DefaultDatagramChannelConfig {
    private static final Method GET_OPTION;
    private static final Object IP_MULTICAST_IF;
    private static final Object IP_MULTICAST_LOOP;
    private static final Object IP_MULTICAST_TTL;
    private static final Method SET_OPTION;
    private final DatagramChannel javaChannel;

    static {
        ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        Class<?> socketOptionType = null;
        try {
            socketOptionType = Class.forName("java.net.SocketOption", true, classLoader);
        } catch (Exception e) {
        }
        Class<?> stdSocketOptionType = null;
        try {
            stdSocketOptionType = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        } catch (Exception e2) {
        }
        Object ipMulticastTtl = null;
        Object ipMulticastIf = null;
        Object ipMulticastLoop = null;
        Method getOption = null;
        Method setOption = null;
        if (socketOptionType != null) {
            try {
                ipMulticastTtl = stdSocketOptionType.getDeclaredField("IP_MULTICAST_TTL").get(null);
                try {
                    ipMulticastIf = stdSocketOptionType.getDeclaredField("IP_MULTICAST_IF").get(null);
                    try {
                        ipMulticastLoop = stdSocketOptionType.getDeclaredField("IP_MULTICAST_LOOP").get(null);
                        Class<?> networkChannelClass = null;
                        try {
                            networkChannelClass = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
                        } catch (Throwable th) {
                        }
                        if (networkChannelClass == null) {
                            getOption = null;
                            setOption = null;
                        } else {
                            try {
                                getOption = networkChannelClass.getDeclaredMethod("getOption", new Class[]{socketOptionType});
                                try {
                                    setOption = networkChannelClass.getDeclaredMethod("setOption", new Class[]{socketOptionType, Object.class});
                                } catch (Exception e3) {
                                    throw new Error("cannot locate the setOption() method", e3);
                                }
                            } catch (Exception e32) {
                                throw new Error("cannot locate the getOption() method", e32);
                            }
                        }
                    } catch (Exception e322) {
                        throw new Error("cannot locate the IP_MULTICAST_LOOP field", e322);
                    }
                } catch (Exception e3222) {
                    throw new Error("cannot locate the IP_MULTICAST_IF field", e3222);
                }
            } catch (Exception e32222) {
                throw new Error("cannot locate the IP_MULTICAST_TTL field", e32222);
            }
        }
        IP_MULTICAST_TTL = ipMulticastTtl;
        IP_MULTICAST_IF = ipMulticastIf;
        IP_MULTICAST_LOOP = ipMulticastLoop;
        GET_OPTION = getOption;
        SET_OPTION = setOption;
    }

    NioDatagramChannelConfig(NioDatagramChannel channel, DatagramChannel javaChannel) {
        super(channel, javaChannel.socket());
        this.javaChannel = javaChannel;
    }

    public int getTimeToLive() {
        return ((Integer) getOption0(IP_MULTICAST_TTL)).intValue();
    }

    public DatagramChannelConfig setTimeToLive(int ttl) {
        setOption0(IP_MULTICAST_TTL, Integer.valueOf(ttl));
        return this;
    }

    public InetAddress getInterface() {
        NetworkInterface inf = getNetworkInterface();
        if (inf == null) {
            return null;
        }
        Enumeration<InetAddress> addresses = inf.getInetAddresses();
        if (addresses.hasMoreElements()) {
            return (InetAddress) addresses.nextElement();
        }
        return null;
    }

    public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
        try {
            setNetworkInterface(NetworkInterface.getByInetAddress(interfaceAddress));
            return this;
        } catch (Throwable e) {
            throw new ChannelException(e);
        }
    }

    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface) getOption0(IP_MULTICAST_IF);
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        setOption0(IP_MULTICAST_IF, networkInterface);
        return this;
    }

    public boolean isLoopbackModeDisabled() {
        return ((Boolean) getOption0(IP_MULTICAST_LOOP)).booleanValue();
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
        setOption0(IP_MULTICAST_LOOP, Boolean.valueOf(loopbackModeDisabled));
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    protected void autoReadCleared() {
        ((NioDatagramChannel) this.channel).clearReadPending0();
    }

    private Object getOption0(Object option) {
        if (GET_OPTION == null) {
            throw new UnsupportedOperationException();
        }
        try {
            return GET_OPTION.invoke(this.javaChannel, new Object[]{option});
        } catch (Throwable e) {
            throw new ChannelException(e);
        }
    }

    private void setOption0(Object option, Object value) {
        if (SET_OPTION == null) {
            throw new UnsupportedOperationException();
        }
        try {
            SET_OPTION.invoke(this.javaChannel, new Object[]{option, value});
        } catch (Throwable e) {
            throw new ChannelException(e);
        }
    }
}
