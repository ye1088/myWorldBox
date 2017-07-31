package io.netty.buffer;

import io.netty.util.ByteProcessor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

@Deprecated
public class SlicedByteBuf extends AbstractUnpooledSlicedByteBuf {
    private int length;

    public /* bridge */ /* synthetic */ ByteBufAllocator alloc() {
        return super.alloc();
    }

    public /* bridge */ /* synthetic */ byte[] array() {
        return super.array();
    }

    public /* bridge */ /* synthetic */ int arrayOffset() {
        return super.arrayOffset();
    }

    public /* bridge */ /* synthetic */ ByteBuf capacity(int i) {
        return super.capacity(i);
    }

    public /* bridge */ /* synthetic */ ByteBuf copy(int i, int i2) {
        return super.copy(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf duplicate() {
        return super.duplicate();
    }

    public /* bridge */ /* synthetic */ int forEachByte(int i, int i2, ByteProcessor byteProcessor) {
        return super.forEachByte(i, i2, byteProcessor);
    }

    public /* bridge */ /* synthetic */ int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) {
        return super.forEachByteDesc(i, i2, byteProcessor);
    }

    public /* bridge */ /* synthetic */ byte getByte(int i) {
        return super.getByte(i);
    }

    public /* bridge */ /* synthetic */ ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        return super.getBytes(i, byteBuffer);
    }

    public /* bridge */ /* synthetic */ CharSequence getCharSequence(int i, int i2, Charset charset) {
        return super.getCharSequence(i, i2, charset);
    }

    public /* bridge */ /* synthetic */ int getInt(int i) {
        return super.getInt(i);
    }

    public /* bridge */ /* synthetic */ int getIntLE(int i) {
        return super.getIntLE(i);
    }

    public /* bridge */ /* synthetic */ long getLong(int i) {
        return super.getLong(i);
    }

    public /* bridge */ /* synthetic */ long getLongLE(int i) {
        return super.getLongLE(i);
    }

    public /* bridge */ /* synthetic */ short getShort(int i) {
        return super.getShort(i);
    }

    public /* bridge */ /* synthetic */ short getShortLE(int i) {
        return super.getShortLE(i);
    }

    public /* bridge */ /* synthetic */ int getUnsignedMedium(int i) {
        return super.getUnsignedMedium(i);
    }

    public /* bridge */ /* synthetic */ int getUnsignedMediumLE(int i) {
        return super.getUnsignedMediumLE(i);
    }

    public /* bridge */ /* synthetic */ boolean hasArray() {
        return super.hasArray();
    }

    public /* bridge */ /* synthetic */ boolean hasMemoryAddress() {
        return super.hasMemoryAddress();
    }

    public /* bridge */ /* synthetic */ boolean isDirect() {
        return super.isDirect();
    }

    public /* bridge */ /* synthetic */ long memoryAddress() {
        return super.memoryAddress();
    }

    public /* bridge */ /* synthetic */ ByteBuffer nioBuffer(int i, int i2) {
        return super.nioBuffer(i, i2);
    }

    public /* bridge */ /* synthetic */ int nioBufferCount() {
        return super.nioBufferCount();
    }

    public /* bridge */ /* synthetic */ ByteBuffer[] nioBuffers(int i, int i2) {
        return super.nioBuffers(i, i2);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ ByteOrder order() {
        return super.order();
    }

    public /* bridge */ /* synthetic */ ByteBuf setByte(int i, int i2) {
        return super.setByte(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        return super.setBytes(i, byteBuffer);
    }

    public /* bridge */ /* synthetic */ int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return super.setCharSequence(i, charSequence, charset);
    }

    public /* bridge */ /* synthetic */ ByteBuf setInt(int i, int i2) {
        return super.setInt(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setIntLE(int i, int i2) {
        return super.setIntLE(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setLong(int i, long j) {
        return super.setLong(i, j);
    }

    public /* bridge */ /* synthetic */ ByteBuf setLongLE(int i, long j) {
        return super.setLongLE(i, j);
    }

    public /* bridge */ /* synthetic */ ByteBuf setMedium(int i, int i2) {
        return super.setMedium(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setMediumLE(int i, int i2) {
        return super.setMediumLE(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setShort(int i, int i2) {
        return super.setShort(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf setShortLE(int i, int i2) {
        return super.setShortLE(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf slice(int i, int i2) {
        return super.slice(i, i2);
    }

    public /* bridge */ /* synthetic */ ByteBuf unwrap() {
        return super.unwrap();
    }

    public SlicedByteBuf(ByteBuf buffer, int index, int length) {
        super(buffer, index, length);
    }

    final void initLength(int length) {
        this.length = length;
    }

    final int length() {
        return this.length;
    }

    public int capacity() {
        return this.length;
    }
}
