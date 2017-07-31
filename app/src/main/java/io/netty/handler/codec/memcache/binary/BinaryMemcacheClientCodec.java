package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class BinaryMemcacheClientCodec extends CombinedChannelDuplexHandler<BinaryMemcacheResponseDecoder, BinaryMemcacheRequestEncoder> {
    private final boolean failOnMissingResponse;
    private final AtomicLong requestResponseCounter;

    private final class Decoder extends BinaryMemcacheResponseDecoder {
        Decoder(int chunkSize) {
            super(chunkSize);
        }

        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            int oldSize = out.size();
            super.decode(ctx, in, out);
            if (BinaryMemcacheClientCodec.this.failOnMissingResponse) {
                int size = out.size();
                for (int i = oldSize; i < size; i++) {
                    if (out.get(i) instanceof LastMemcacheContent) {
                        BinaryMemcacheClientCodec.this.requestResponseCounter.decrementAndGet();
                    }
                }
            }
        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            if (BinaryMemcacheClientCodec.this.failOnMissingResponse) {
                long missingResponses = BinaryMemcacheClientCodec.this.requestResponseCounter.get();
                if (missingResponses > 0) {
                    ctx.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)"));
                }
            }
        }
    }

    private final class Encoder extends BinaryMemcacheRequestEncoder {
        private Encoder() {
        }

        protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
            super.encode(ctx, msg, out);
            if (BinaryMemcacheClientCodec.this.failOnMissingResponse && (msg instanceof LastMemcacheContent)) {
                BinaryMemcacheClientCodec.this.requestResponseCounter.incrementAndGet();
            }
        }
    }

    public BinaryMemcacheClientCodec() {
        this(8192);
    }

    public BinaryMemcacheClientCodec(int decodeChunkSize) {
        this(decodeChunkSize, false);
    }

    public BinaryMemcacheClientCodec(int decodeChunkSize, boolean failOnMissingResponse) {
        this.requestResponseCounter = new AtomicLong();
        this.failOnMissingResponse = failOnMissingResponse;
        init(new Decoder(decodeChunkSize), new Encoder());
    }
}
