package io.netty.handler.codec.haproxy;

import com.huluxia.data.profile.a;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.haproxy.HAProxyProxiedProtocol.AddressFamily;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

public final class HAProxyMessage {
    private static final HAProxyMessage V1_UNKNOWN_MSG = new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private static final HAProxyMessage V2_LOCAL_MSG = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.LOCAL, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private static final HAProxyMessage V2_UNKNOWN_MSG = new HAProxyMessage(HAProxyProtocolVersion.V2, HAProxyCommand.PROXY, HAProxyProxiedProtocol.UNKNOWN, null, null, 0, 0);
    private final HAProxyCommand command;
    private final String destinationAddress;
    private final int destinationPort;
    private final HAProxyProtocolVersion protocolVersion;
    private final HAProxyProxiedProtocol proxiedProtocol;
    private final String sourceAddress;
    private final int sourcePort;

    private HAProxyMessage(HAProxyProtocolVersion protocolVersion, HAProxyCommand command, HAProxyProxiedProtocol proxiedProtocol, String sourceAddress, String destinationAddress, String sourcePort, String destinationPort) {
        this(protocolVersion, command, proxiedProtocol, sourceAddress, destinationAddress, portStringToInt(sourcePort), portStringToInt(destinationPort));
    }

    private HAProxyMessage(HAProxyProtocolVersion protocolVersion, HAProxyCommand command, HAProxyProxiedProtocol proxiedProtocol, String sourceAddress, String destinationAddress, int sourcePort, int destinationPort) {
        if (proxiedProtocol == null) {
            throw new NullPointerException("proxiedProtocol");
        }
        AddressFamily addrFamily = proxiedProtocol.addressFamily();
        checkAddress(sourceAddress, addrFamily);
        checkAddress(destinationAddress, addrFamily);
        checkPort(sourcePort);
        checkPort(destinationPort);
        this.protocolVersion = protocolVersion;
        this.command = command;
        this.proxiedProtocol = proxiedProtocol;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
    }

    static HAProxyMessage decodeHeader(ByteBuf header) {
        if (header == null) {
            throw new NullPointerException("header");
        } else if (header.readableBytes() < 16) {
            throw new HAProxyProtocolException("incomplete header: " + header.readableBytes() + " bytes (expected: 16+ bytes)");
        } else {
            header.skipBytes(12);
            byte verCmdByte = header.readByte();
            try {
                HAProxyProtocolVersion ver = HAProxyProtocolVersion.valueOf(verCmdByte);
                if (ver != HAProxyProtocolVersion.V2) {
                    throw new HAProxyProtocolException("version 1 unsupported: 0x" + Integer.toHexString(verCmdByte));
                }
                try {
                    HAProxyCommand cmd = HAProxyCommand.valueOf(verCmdByte);
                    if (cmd == HAProxyCommand.LOCAL) {
                        return V2_LOCAL_MSG;
                    }
                    try {
                        HAProxyProxiedProtocol protAndFam = HAProxyProxiedProtocol.valueOf(header.readByte());
                        if (protAndFam == HAProxyProxiedProtocol.UNKNOWN) {
                            return V2_UNKNOWN_MSG;
                        }
                        String srcAddress;
                        String dstAddress;
                        int addressInfoLen = header.readUnsignedShort();
                        int srcPort = 0;
                        int dstPort = 0;
                        AddressFamily addressFamily = protAndFam.addressFamily();
                        int addressLen;
                        if (addressFamily != AddressFamily.AF_UNIX) {
                            if (addressFamily == AddressFamily.AF_IPv4) {
                                if (addressInfoLen < 12 || header.readableBytes() < 12) {
                                    throw new HAProxyProtocolException("incomplete IPv4 address information: " + Math.min(addressInfoLen, header.readableBytes()) + " bytes (expected: 12+ bytes)");
                                }
                                addressLen = 4;
                            } else if (addressFamily != AddressFamily.AF_IPv6) {
                                throw new HAProxyProtocolException("unable to parse address information (unkown address family: " + addressFamily + ')');
                            } else if (addressInfoLen < 36 || header.readableBytes() < 36) {
                                throw new HAProxyProtocolException("incomplete IPv6 address information: " + Math.min(addressInfoLen, header.readableBytes()) + " bytes (expected: 36+ bytes)");
                            } else {
                                addressLen = 16;
                            }
                            srcAddress = ipBytestoString(header, addressLen);
                            dstAddress = ipBytestoString(header, addressLen);
                            srcPort = header.readUnsignedShort();
                            dstPort = header.readUnsignedShort();
                        } else if (addressInfoLen < 216 || header.readableBytes() < 216) {
                            throw new HAProxyProtocolException("incomplete UNIX socket address information: " + Math.min(addressInfoLen, header.readableBytes()) + " bytes (expected: 216+ bytes)");
                        } else {
                            int startIdx = header.readerIndex();
                            int addressEnd = header.forEachByte(startIdx, 108, ByteProcessor.FIND_NUL);
                            if (addressEnd == -1) {
                                addressLen = 108;
                            } else {
                                addressLen = addressEnd - startIdx;
                            }
                            srcAddress = header.toString(startIdx, addressLen, CharsetUtil.US_ASCII);
                            startIdx += 108;
                            addressEnd = header.forEachByte(startIdx, 108, ByteProcessor.FIND_NUL);
                            if (addressEnd == -1) {
                                addressLen = 108;
                            } else {
                                addressLen = addressEnd - startIdx;
                            }
                            dstAddress = header.toString(startIdx, addressLen, CharsetUtil.US_ASCII);
                        }
                        return new HAProxyMessage(ver, cmd, protAndFam, srcAddress, dstAddress, srcPort, dstPort);
                    } catch (Throwable e) {
                        throw new HAProxyProtocolException(e);
                    }
                } catch (Throwable e2) {
                    throw new HAProxyProtocolException(e2);
                }
            } catch (Throwable e22) {
                throw new HAProxyProtocolException(e22);
            }
        }
    }

    static HAProxyMessage decodeHeader(String header) {
        if (header == null) {
            throw new HAProxyProtocolException("header");
        }
        String[] parts = header.split(" ");
        int numParts = parts.length;
        if (numParts < 2) {
            throw new HAProxyProtocolException("invalid header: " + header + " (expected: 'PROXY' and proxied protocol values)");
        } else if ("PROXY".equals(parts[0])) {
            try {
                HAProxyProxiedProtocol protAndFam = HAProxyProxiedProtocol.valueOf(parts[1]);
                if (protAndFam != HAProxyProxiedProtocol.TCP4 && protAndFam != HAProxyProxiedProtocol.TCP6 && protAndFam != HAProxyProxiedProtocol.UNKNOWN) {
                    throw new HAProxyProtocolException("unsupported v1 proxied protocol: " + parts[1]);
                } else if (protAndFam == HAProxyProxiedProtocol.UNKNOWN) {
                    return V1_UNKNOWN_MSG;
                } else {
                    if (numParts == 6) {
                        return new HAProxyMessage(HAProxyProtocolVersion.V1, HAProxyCommand.PROXY, protAndFam, parts[2], parts[3], parts[4], parts[5]);
                    }
                    throw new HAProxyProtocolException("invalid TCP4/6 header: " + header + " (expected: 6 parts)");
                }
            } catch (Throwable e) {
                throw new HAProxyProtocolException(e);
            }
        } else {
            throw new HAProxyProtocolException("unknown identifier: " + parts[0]);
        }
    }

    private static String ipBytestoString(ByteBuf header, int addressLen) {
        StringBuilder sb = new StringBuilder();
        if (addressLen == 4) {
            sb.append(header.readByte() & 255);
            sb.append('.');
            sb.append(header.readByte() & 255);
            sb.append('.');
            sb.append(header.readByte() & 255);
            sb.append('.');
            sb.append(header.readByte() & 255);
        } else {
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
            sb.append(':');
            sb.append(Integer.toHexString(header.readUnsignedShort()));
        }
        return sb.toString();
    }

    private static int portStringToInt(String value) {
        try {
            int port = Integer.parseInt(value);
            if (port > 0 && port <= 65535) {
                return port;
            }
            throw new HAProxyProtocolException("invalid port: " + value + " (expected: 1 ~ 65535)");
        } catch (NumberFormatException e) {
            throw new HAProxyProtocolException("invalid port: " + value, e);
        }
    }

    private static void checkAddress(String address, AddressFamily addrFamily) {
        if (addrFamily == null) {
            throw new NullPointerException("addrFamily");
        }
        switch (addrFamily) {
            case AF_UNSPEC:
                if (address != null) {
                    throw new HAProxyProtocolException("unable to validate an AF_UNSPEC address: " + address);
                }
                return;
            case AF_UNIX:
                return;
            default:
                if (address == null) {
                    throw new NullPointerException(a.qf);
                }
                switch (addrFamily) {
                    case AF_IPv4:
                        if (!NetUtil.isValidIpV4Address(address)) {
                            throw new HAProxyProtocolException("invalid IPv4 address: " + address);
                        }
                        return;
                    case AF_IPv6:
                        if (!NetUtil.isValidIpV6Address(address)) {
                            throw new HAProxyProtocolException("invalid IPv6 address: " + address);
                        }
                        return;
                    default:
                        throw new Error();
                }
        }
    }

    private static void checkPort(int port) {
        if (port < 0 || port > 65535) {
            throw new HAProxyProtocolException("invalid port: " + port + " (expected: 1 ~ 65535)");
        }
    }

    public HAProxyProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    public HAProxyCommand command() {
        return this.command;
    }

    public HAProxyProxiedProtocol proxiedProtocol() {
        return this.proxiedProtocol;
    }

    public String sourceAddress() {
        return this.sourceAddress;
    }

    public String destinationAddress() {
        return this.destinationAddress;
    }

    public int sourcePort() {
        return this.sourcePort;
    }

    public int destinationPort() {
        return this.destinationPort;
    }
}
