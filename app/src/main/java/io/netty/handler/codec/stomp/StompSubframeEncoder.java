package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiHeadersEncoder;
import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;
import io.netty.handler.codec.AsciiHeadersEncoder.SeparatorType;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import java.util.Map.Entry;

public class StompSubframeEncoder extends MessageToMessageEncoder<StompSubframe> {
    protected void encode(ChannelHandlerContext ctx, StompSubframe msg, List<Object> out) throws Exception {
        if (msg instanceof StompFrame) {
            StompFrame frame = (StompFrame) msg;
            out.add(encodeFrame(frame, ctx));
            out.add(encodeContent(frame, ctx));
        } else if (msg instanceof StompHeadersSubframe) {
            out.add(encodeFrame((StompHeadersSubframe) msg, ctx));
        } else if (msg instanceof StompContentSubframe) {
            out.add(encodeContent((StompContentSubframe) msg, ctx));
        }
    }

    private static ByteBuf encodeContent(StompContentSubframe content, ChannelHandlerContext ctx) {
        if (!(content instanceof LastStompContentSubframe)) {
            return content.content().retain();
        }
        ByteBuf buf = ctx.alloc().buffer(content.content().readableBytes() + 1);
        buf.writeBytes(content.content());
        buf.writeByte(0);
        return buf;
    }

    private static ByteBuf encodeFrame(StompHeadersSubframe frame, ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(frame.command().toString().getBytes(CharsetUtil.US_ASCII));
        buf.writeByte(10);
        AsciiHeadersEncoder headersEncoder = new AsciiHeadersEncoder(buf, SeparatorType.COLON, NewlineType.LF);
        for (Entry<CharSequence, CharSequence> entry : frame.headers()) {
            headersEncoder.encode(entry);
        }
        buf.writeByte(10);
        return buf;
    }
}
