package io.netty.resolver.dns;

import com.huluxia.data.profile.a;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class DnsServerAddresses {
    private static final DnsServerAddresses DEFAULT_NAME_SERVERS = sequential(DEFAULT_NAME_SERVER_ARRAY);
    private static final InetSocketAddress[] DEFAULT_NAME_SERVER_ARRAY;
    private static final List<InetSocketAddress> DEFAULT_NAME_SERVER_LIST;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsServerAddresses.class);

    public abstract DnsServerAddressStream stream();

    static {
        Object defaultNameServers = new ArrayList(2);
        try {
            Class<?> configClass = Class.forName("sun.net.dns.ResolverConfiguration");
            for (String a : (List) configClass.getMethod("nameservers", new Class[0]).invoke(configClass.getMethod("open", new Class[0]).invoke(null, new Object[0]), new Object[0])) {
                if (a != null) {
                    defaultNameServers.add(new InetSocketAddress(InetAddress.getByName(a), 53));
                }
            }
        } catch (Exception e) {
        }
        if (defaultNameServers.isEmpty()) {
            Collections.addAll(defaultNameServers, new InetSocketAddress[]{new InetSocketAddress("8.8.8.8", 53), new InetSocketAddress("8.8.4.4", 53)});
            if (logger.isWarnEnabled()) {
                logger.warn("Default DNS servers: {} (Google Public DNS as a_isRightVersion fallback)", defaultNameServers);
            }
        } else if (logger.isDebugEnabled()) {
            logger.debug("Default DNS servers: {} (sun.net.dns.ResolverConfiguration)", defaultNameServers);
        }
        DEFAULT_NAME_SERVER_LIST = Collections.unmodifiableList(defaultNameServers);
        DEFAULT_NAME_SERVER_ARRAY = (InetSocketAddress[]) defaultNameServers.toArray(new InetSocketAddress[defaultNameServers.size()]);
    }

    public static List<InetSocketAddress> defaultAddressList() {
        return DEFAULT_NAME_SERVER_LIST;
    }

    public static DnsServerAddresses defaultAddresses() {
        return DEFAULT_NAME_SERVERS;
    }

    public static DnsServerAddresses sequential(Iterable<? extends InetSocketAddress> addresses) {
        return sequential0(sanitize((Iterable) addresses));
    }

    public static DnsServerAddresses sequential(InetSocketAddress... addresses) {
        return sequential0(sanitize(addresses));
    }

    private static DnsServerAddresses sequential0(InetSocketAddress... addresses) {
        if (addresses.length == 1) {
            return singleton(addresses[0]);
        }
        return new DefaultDnsServerAddresses("sequential", addresses) {
            public DnsServerAddressStream stream() {
                return new SequentialDnsServerAddressStream(this.addresses, 0);
            }
        };
    }

    public static DnsServerAddresses shuffled(Iterable<? extends InetSocketAddress> addresses) {
        return shuffled0(sanitize((Iterable) addresses));
    }

    public static DnsServerAddresses shuffled(InetSocketAddress... addresses) {
        return shuffled0(sanitize(addresses));
    }

    private static DnsServerAddresses shuffled0(InetSocketAddress[] addresses) {
        if (addresses.length == 1) {
            return singleton(addresses[0]);
        }
        return new DefaultDnsServerAddresses("shuffled", addresses) {
            public DnsServerAddressStream stream() {
                return new ShuffledDnsServerAddressStream(this.addresses);
            }
        };
    }

    public static DnsServerAddresses rotational(Iterable<? extends InetSocketAddress> addresses) {
        return rotational0(sanitize((Iterable) addresses));
    }

    public static DnsServerAddresses rotational(InetSocketAddress... addresses) {
        return rotational0(sanitize(addresses));
    }

    private static DnsServerAddresses rotational0(InetSocketAddress[] addresses) {
        if (addresses.length == 1) {
            return singleton(addresses[0]);
        }
        return new RotationalDnsServerAddresses(addresses);
    }

    public static DnsServerAddresses singleton(InetSocketAddress address) {
        if (address == null) {
            throw new NullPointerException(a.qf);
        } else if (!address.isUnresolved()) {
            return new SingletonDnsServerAddresses(address);
        } else {
            throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + address);
        }
    }

    private static InetSocketAddress[] sanitize(Iterable<? extends InetSocketAddress> addresses) {
        if (addresses == null) {
            throw new NullPointerException("addresses");
        }
        List<InetSocketAddress> list;
        if (addresses instanceof Collection) {
            list = new ArrayList(((Collection) addresses).size());
        } else {
            list = new ArrayList(4);
        }
        for (InetSocketAddress a : addresses) {
            if (a == null) {
                break;
            } else if (a.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + a);
            } else {
                list.add(a);
            }
        }
        if (!list.isEmpty()) {
            return (InetSocketAddress[]) list.toArray(new InetSocketAddress[list.size()]);
        }
        throw new IllegalArgumentException("empty addresses");
    }

    private static InetSocketAddress[] sanitize(InetSocketAddress[] addresses) {
        if (addresses == null) {
            throw new NullPointerException("addresses");
        }
        List<InetSocketAddress> list = InternalThreadLocalMap.get().arrayList(addresses.length);
        InetSocketAddress[] arr$ = addresses;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            InetSocketAddress a = arr$[i$];
            if (a == null) {
                break;
            } else if (a.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + a);
            } else {
                list.add(a);
                i$++;
            }
        }
        if (list.isEmpty()) {
            return DEFAULT_NAME_SERVER_ARRAY;
        }
        return (InetSocketAddress[]) list.toArray(new InetSocketAddress[list.size()]);
    }
}
