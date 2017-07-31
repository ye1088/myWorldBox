package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class SocksMessageEncoder extends MessageToByteEncoder<SocksMessage> {
    protected void encode(ChannelHandlerContext ctx, SocksMessage msg, ByteBuf out) throws Exception {
        msg.encodeAsByteBuf(out);
    }
}
