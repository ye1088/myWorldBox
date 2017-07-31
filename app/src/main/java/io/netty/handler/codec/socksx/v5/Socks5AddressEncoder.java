package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

public interface Socks5AddressEncoder {
    public static final Socks5AddressEncoder DEFAULT = new Socks5AddressEncoder() {
        public void encodeAddress(Socks5AddressType addrType, String addrValue, ByteBuf out) throws Exception {
            byte typeVal = addrType.byteValue();
            if (typeVal == Socks5AddressType.IPv4.byteValue()) {
                if (addrValue != null) {
                    out.writeBytes(NetUtil.createByteArrayFromIpAddressString(addrValue));
                } else {
                    out.writeInt(0);
                }
            } else if (typeVal == Socks5AddressType.DOMAIN.byteValue()) {
                if (addrValue != null) {
                    byte[] bndAddr = addrValue.getBytes(CharsetUtil.US_ASCII);
                    out.writeByte(bndAddr.length);
                    out.writeBytes(bndAddr);
                    return;
                }
                out.writeByte(1);
                out.writeByte(0);
            } else if (typeVal != Socks5AddressType.IPv6.byteValue()) {
                throw new EncoderException("unsupported addrType: " + (addrType.byteValue() & 255));
            } else if (addrValue != null) {
                out.writeBytes(NetUtil.createByteArrayFromIpAddressString(addrValue));
            } else {
                out.writeLong(0);
                out.writeLong(0);
            }
        }
    };

    void encodeAddress(Socks5AddressType socks5AddressType, String str, ByteBuf byteBuf) throws Exception;
}
