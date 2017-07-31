package io.netty.handler.codec.haproxy;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import org.apache.tools.tar.TarConstants;

public enum HAProxyProxiedProtocol {
    UNKNOWN((byte) 0, AddressFamily.AF_UNSPEC, TransportProtocol.UNSPEC),
    TCP4(BinaryMemcacheOpcodes.SETQ, AddressFamily.AF_IPv4, TransportProtocol.STREAM),
    TCP6(BinaryMemcacheOpcodes.SASL_AUTH, AddressFamily.AF_IPv6, TransportProtocol.STREAM),
    UDP4(BinaryMemcacheOpcodes.ADDQ, AddressFamily.AF_IPv4, TransportProtocol.DGRAM),
    UDP6((byte) 34, AddressFamily.AF_IPv6, TransportProtocol.DGRAM),
    UNIX_STREAM(TarConstants.LF_LINK, AddressFamily.AF_UNIX, TransportProtocol.STREAM),
    UNIX_DGRAM(TarConstants.LF_SYMLINK, AddressFamily.AF_UNIX, TransportProtocol.DGRAM);
    
    private final AddressFamily addressFamily;
    private final byte byteValue;
    private final TransportProtocol transportProtocol;

    public enum AddressFamily {
        private static final /* synthetic */ AddressFamily[] $VALUES = null;
        public static final AddressFamily AF_IPv4 = null;
        public static final AddressFamily AF_IPv6 = null;
        public static final AddressFamily AF_UNIX = null;
        public static final AddressFamily AF_UNSPEC = null;
        private static final byte FAMILY_MASK = (byte) -16;
        private final byte byteValue;

        public static AddressFamily valueOf(String name) {
            return (AddressFamily) Enum.valueOf(AddressFamily.class, name);
        }

        public static AddressFamily[] values() {
            return (AddressFamily[]) $VALUES.clone();
        }

        static {
            AF_UNSPEC = new AddressFamily("AF_UNSPEC", 0, (byte) 0);
            AF_IPv4 = new AddressFamily("AF_IPv4", 1, (byte) 16);
            AF_IPv6 = new AddressFamily("AF_IPv6", 2, (byte) 32);
            AF_UNIX = new AddressFamily("AF_UNIX", 3, TarConstants.LF_NORMAL);
            $VALUES = new AddressFamily[]{AF_UNSPEC, AF_IPv4, AF_IPv6, AF_UNIX};
        }

        private AddressFamily(String str, int i, byte byteValue) {
            this.byteValue = byteValue;
        }

        public static AddressFamily valueOf(byte tpafByte) {
            int addressFamily = tpafByte & -16;
            switch ((byte) addressFamily) {
                case (byte) 0:
                    return AF_UNSPEC;
                case (byte) 16:
                    return AF_IPv4;
                case (byte) 32:
                    return AF_IPv6;
                case (byte) 48:
                    return AF_UNIX;
                default:
                    throw new IllegalArgumentException("unknown address family: " + addressFamily);
            }
        }

        public byte byteValue() {
            return this.byteValue;
        }
    }

    public enum TransportProtocol {
        private static final /* synthetic */ TransportProtocol[] $VALUES = null;
        public static final TransportProtocol DGRAM = null;
        public static final TransportProtocol STREAM = null;
        private static final byte TRANSPORT_MASK = (byte) 15;
        public static final TransportProtocol UNSPEC = null;
        private final byte transportByte;

        public static TransportProtocol valueOf(String name) {
            return (TransportProtocol) Enum.valueOf(TransportProtocol.class, name);
        }

        public static TransportProtocol[] values() {
            return (TransportProtocol[]) $VALUES.clone();
        }

        static {
            UNSPEC = new TransportProtocol("UNSPEC", 0, (byte) 0);
            STREAM = new TransportProtocol("STREAM", 1, (byte) 1);
            DGRAM = new TransportProtocol("DGRAM", 2, (byte) 2);
            $VALUES = new TransportProtocol[]{UNSPEC, STREAM, DGRAM};
        }

        private TransportProtocol(String str, int i, byte transportByte) {
            this.transportByte = transportByte;
        }

        public static TransportProtocol valueOf(byte tpafByte) {
            int transportProtocol = tpafByte & 15;
            switch ((byte) transportProtocol) {
                case (byte) 0:
                    return UNSPEC;
                case (byte) 1:
                    return STREAM;
                case (byte) 2:
                    return DGRAM;
                default:
                    throw new IllegalArgumentException("unknown transport protocol: " + transportProtocol);
            }
        }

        public byte byteValue() {
            return this.transportByte;
        }
    }

    private HAProxyProxiedProtocol(byte byteValue, AddressFamily addressFamily, TransportProtocol transportProtocol) {
        this.byteValue = byteValue;
        this.addressFamily = addressFamily;
        this.transportProtocol = transportProtocol;
    }

    public static HAProxyProxiedProtocol valueOf(byte tpafByte) {
        switch (tpafByte) {
            case (byte) 0:
                return UNKNOWN;
            case (byte) 17:
                return TCP4;
            case (byte) 18:
                return UDP4;
            case (byte) 33:
                return TCP6;
            case (byte) 34:
                return UDP6;
            case (byte) 49:
                return UNIX_STREAM;
            case (byte) 50:
                return UNIX_DGRAM;
            default:
                throw new IllegalArgumentException("unknown transport protocol + address family: " + (tpafByte & 255));
        }
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public AddressFamily addressFamily() {
        return this.addressFamily;
    }

    public TransportProtocol transportProtocol() {
        return this.transportProtocol;
    }
}
