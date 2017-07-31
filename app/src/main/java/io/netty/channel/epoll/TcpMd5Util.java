package io.netty.channel.epoll;

import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.tools.ant.taskdefs.optional.vss.MSVSSConstants;

final class TcpMd5Util {
    static Collection<InetAddress> newTcpMd5Sigs(AbstractEpollChannel channel, Collection<InetAddress> current, Map<InetAddress, byte[]> newKeys) throws IOException {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(current, MSVSSConstants.TIME_CURRENT);
        ObjectUtil.checkNotNull(newKeys, "newKeys");
        for (Entry<InetAddress, byte[]> e : newKeys.entrySet()) {
            byte[] key = (byte[]) e.getValue();
            if (e.getKey() == null) {
                throw new IllegalArgumentException("newKeys contains an entry with null address: " + newKeys);
            } else if (key == null) {
                throw new NullPointerException("newKeys[" + e.getKey() + ']');
            } else if (key.length == 0) {
                throw new IllegalArgumentException("newKeys[" + e.getKey() + "] has an empty key.");
            } else if (key.length > Native.TCP_MD5SIG_MAXKEYLEN) {
                throw new IllegalArgumentException("newKeys[" + e.getKey() + "] has a key with invalid length; should not exceed the maximum length (" + Native.TCP_MD5SIG_MAXKEYLEN + ')');
            }
        }
        for (InetAddress addr : current) {
            if (!newKeys.containsKey(addr)) {
                Native.setTcpMd5Sig(channel.fd().intValue(), addr, null);
            }
        }
        if (newKeys.isEmpty()) {
            return Collections.emptySet();
        }
        Collection<InetAddress> addresses = new ArrayList(newKeys.size());
        for (Entry<InetAddress, byte[]> e2 : newKeys.entrySet()) {
            Native.setTcpMd5Sig(channel.fd().intValue(), (InetAddress) e2.getKey(), (byte[]) e2.getValue());
            addresses.add(e2.getKey());
        }
        return addresses;
    }

    private TcpMd5Util() {
    }
}
