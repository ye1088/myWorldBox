package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class JZlibDecoder extends ZlibDecoder {
    private byte[] dictionary;
    private volatile boolean finished;
    private final Inflater z;

    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public JZlibDecoder(ZlibWrapper wrapper) {
        this.z = new Inflater();
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
        if (resultCode != 0) {
            ZlibUtil.fail(this.z, "initialization failure", resultCode);
        }
    }

    public JZlibDecoder(byte[] dictionary) {
        this.z = new Inflater();
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        this.dictionary = dictionary;
        int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
        if (resultCode != 0) {
            ZlibUtil.fail(this.z, "initialization failure", resultCode);
        }
    }

    public boolean isClosed() {
        return this.finished;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (this.finished) {
            in.skipBytes(in.readableBytes());
            return;
        }
        int inputLength = in.readableBytes();
        if (inputLength != 0) {
            int oldNextInIndex;
            ByteBuf decompressed;
            try {
                this.z.avail_in = inputLength;
                if (in.hasArray()) {
                    this.z.next_in = in.array();
                    this.z.next_in_index = in.arrayOffset() + in.readerIndex();
                } else {
                    byte[] array = new byte[inputLength];
                    in.getBytes(in.readerIndex(), array);
                    this.z.next_in = array;
                    this.z.next_in_index = 0;
                }
                oldNextInIndex = this.z.next_in_index;
                int maxOutputLength = inputLength << 1;
                decompressed = ctx.alloc().heapBuffer(maxOutputLength);
                while (true) {
                    this.z.avail_out = maxOutputLength;
                    decompressed.ensureWritable(maxOutputLength);
                    this.z.next_out = decompressed.array();
                    this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
                    int oldNextOutIndex = this.z.next_out_index;
                    int resultCode = this.z.inflate(2);
                    int outputLength = this.z.next_out_index - oldNextOutIndex;
                    if (outputLength > 0) {
                        decompressed.writerIndex(decompressed.writerIndex() + outputLength);
                    }
                    switch (resultCode) {
                        case -5:
                            if (this.z.avail_in <= 0) {
                                break;
                            }
                            continue;
                        case 0:
                            break;
                        case 1:
                            this.finished = true;
                            this.z.inflateEnd();
                            break;
                        case 2:
                            if (this.dictionary != null) {
                                resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                                if (resultCode == 0) {
                                    break;
                                }
                                ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
                                break;
                            }
                            ZlibUtil.fail(this.z, "decompression failure", resultCode);
                            continue;
                        default:
                            ZlibUtil.fail(this.z, "decompression failure", resultCode);
                            continue;
                    }
                    in.skipBytes(this.z.next_in_index - oldNextInIndex);
                    if (decompressed.isReadable()) {
                        out.add(decompressed);
                    } else {
                        decompressed.release();
                    }
                    this.z.next_in = null;
                    this.z.next_out = null;
                    return;
                }
            } catch (Throwable th) {
                this.z.next_in = null;
                this.z.next_out = null;
            }
        }
    }
}
