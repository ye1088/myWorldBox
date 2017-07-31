package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SctpMessageCompletionHandler extends MessageToMessageDecoder<SctpMessage> {
    private final Map<Integer, ByteBuf> fragments = new HashMap();

    protected void decode(ChannelHandlerContext ctx, SctpMessage msg, List<Object> out) throws Exception {
        ByteBuf frag;
        ByteBuf byteBuf = msg.content();
        int protocolIdentifier = msg.protocolIdentifier();
        int streamIdentifier = msg.streamIdentifier();
        boolean isComplete = msg.isComplete();
        boolean isUnordered = msg.isUnordered();
        if (this.fragments.containsKey(Integer.valueOf(streamIdentifier))) {
            frag = (ByteBuf) this.fragments.remove(Integer.valueOf(streamIdentifier));
        } else {
            frag = Unpooled.EMPTY_BUFFER;
        }
        if (isComplete && !frag.isReadable()) {
            out.add(msg);
        } else if (!isComplete && frag.isReadable()) {
            this.fragments.put(Integer.valueOf(streamIdentifier), Unpooled.wrappedBuffer(new ByteBuf[]{frag, byteBuf}));
        } else if (isComplete && frag.isReadable()) {
            this.fragments.remove(Integer.valueOf(streamIdentifier));
            out.add(new SctpMessage(protocolIdentifier, streamIdentifier, isUnordered, Unpooled.wrappedBuffer(new ByteBuf[]{frag, byteBuf})));
        } else {
            this.fragments.put(Integer.valueOf(streamIdentifier), byteBuf);
        }
        byteBuf.retain();
    }
}
