package io.netty.buffer;

import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

final class UnsafeByteBufUtil {
    private static final boolean UNALIGNED = PlatformDependent.isUnaligned();
    private static final byte ZERO = (byte) 0;

    static byte getByte(long address) {
        return PlatformDependent.getByte(address);
    }

    static short getShort(long address) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte(address) << 8) | (PlatformDependent.getByte(1 + address) & 255));
        }
        short v = PlatformDependent.getShort(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Short.reverseBytes(v);
    }

    static short getShortLE(long address) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte(address) & 255) | (PlatformDependent.getByte(1 + address) << 8));
        }
        short v = PlatformDependent.getShort(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Short.reverseBytes(v);
        }
        return v;
    }

    static int getUnsignedMedium(long address) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(address) & 255) << 16) | ((PlatformDependent.getByte(1 + address) & 255) << 8)) | (PlatformDependent.getByte(address + 2) & 255);
        }
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return (PlatformDependent.getByte(address) & 255) | ((PlatformDependent.getShort(1 + address) & 65535) << 8);
        }
        return ((Short.reverseBytes(PlatformDependent.getShort(address)) & 65535) << 8) | (PlatformDependent.getByte(address + 2) & 255);
    }

    static int getUnsignedMediumLE(long address) {
        if (!UNALIGNED) {
            return ((PlatformDependent.getByte(address) & 255) | ((PlatformDependent.getByte(address + 1) & 255) << 8)) | ((PlatformDependent.getByte(address + 1) & 255) << 16);
        }
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return ((Short.reverseBytes(PlatformDependent.getShort(address)) & 65535) << 8) | (PlatformDependent.getByte(2 + address) & 255);
        }
        return (PlatformDependent.getByte(address) & 255) | ((PlatformDependent.getShort(address + 1) & 65535) << 8);
    }

    static int getInt(long address) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(address) << 24) | ((PlatformDependent.getByte(1 + address) & 255) << 16)) | ((PlatformDependent.getByte(2 + address) & 255) << 8)) | (PlatformDependent.getByte(3 + address) & 255);
        }
        int v = PlatformDependent.getInt(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Integer.reverseBytes(v);
    }

    static int getIntLE(long address) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(address) & 255) | ((PlatformDependent.getByte(1 + address) & 255) << 8)) | ((PlatformDependent.getByte(2 + address) & 255) << 16)) | (PlatformDependent.getByte(3 + address) << 24);
        }
        int v = PlatformDependent.getInt(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Integer.reverseBytes(v);
        }
        return v;
    }

    static long getLong(long address) {
        if (!UNALIGNED) {
            return (((((((((long) PlatformDependent.getByte(address)) << 56) | ((((long) PlatformDependent.getByte(1 + address)) & 255) << 48)) | ((((long) PlatformDependent.getByte(2 + address)) & 255) << 40)) | ((((long) PlatformDependent.getByte(3 + address)) & 255) << 32)) | ((((long) PlatformDependent.getByte(4 + address)) & 255) << 24)) | ((((long) PlatformDependent.getByte(5 + address)) & 255) << 16)) | ((((long) PlatformDependent.getByte(6 + address)) & 255) << 8)) | (((long) PlatformDependent.getByte(7 + address)) & 255);
        }
        long v = PlatformDependent.getLong(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Long.reverseBytes(v);
    }

    static long getLongLE(long address) {
        if (!UNALIGNED) {
            return (((((((((long) PlatformDependent.getByte(address)) & 255) | ((((long) PlatformDependent.getByte(1 + address)) & 255) << 8)) | ((((long) PlatformDependent.getByte(2 + address)) & 255) << 16)) | ((((long) PlatformDependent.getByte(3 + address)) & 255) << 24)) | ((((long) PlatformDependent.getByte(4 + address)) & 255) << 32)) | ((((long) PlatformDependent.getByte(5 + address)) & 255) << 40)) | ((((long) PlatformDependent.getByte(6 + address)) & 255) << 48)) | (((long) PlatformDependent.getByte(7 + address)) << 56);
        }
        long v = PlatformDependent.getLong(address);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Long.reverseBytes(v);
        }
        return v;
    }

    static void setByte(long address, int value) {
        PlatformDependent.putByte(address, (byte) value);
    }

    static void setShort(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short) value : Short.reverseBytes((short) value));
            return;
        }
        PlatformDependent.putByte(address, (byte) (value >>> 8));
        PlatformDependent.putByte(1 + address, (byte) value);
    }

    static void setShortLE(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) value) : (short) value);
            return;
        }
        PlatformDependent.putByte(address, (byte) value);
        PlatformDependent.putByte(1 + address, (byte) (value >>> 8));
    }

    static void setMedium(long address, int value) {
        if (!UNALIGNED) {
            PlatformDependent.putByte(address, (byte) (value >>> 16));
            PlatformDependent.putByte(address + 1, (byte) (value >>> 8));
            PlatformDependent.putByte(address + 2, (byte) value);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putByte(address, (byte) value);
            PlatformDependent.putShort(address + 1, (short) (value >>> 8));
        } else {
            PlatformDependent.putShort(address, Short.reverseBytes((short) (value >>> 8)));
            PlatformDependent.putByte(address + 2, (byte) value);
        }
    }

    static void setMediumLE(long address, int value) {
        if (!UNALIGNED) {
            PlatformDependent.putByte(address, (byte) value);
            PlatformDependent.putByte(address + 1, (byte) (value >>> 8));
            PlatformDependent.putByte(address + 2, (byte) (value >>> 16));
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putShort(address, Short.reverseBytes((short) (value >>> 8)));
            PlatformDependent.putByte(address + 2, (byte) value);
        } else {
            PlatformDependent.putByte(address, (byte) value);
            PlatformDependent.putShort(address + 1, (short) (value >>> 8));
        }
    }

    static void setInt(long address, int value) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Integer.reverseBytes(value);
            }
            PlatformDependent.putInt(address, value);
            return;
        }
        PlatformDependent.putByte(address, (byte) (value >>> 24));
        PlatformDependent.putByte(1 + address, (byte) (value >>> 16));
        PlatformDependent.putByte(2 + address, (byte) (value >>> 8));
        PlatformDependent.putByte(3 + address, (byte) value);
    }

    static void setIntLE(long address, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Integer.reverseBytes(value);
            }
            PlatformDependent.putInt(address, value);
            return;
        }
        PlatformDependent.putByte(address, (byte) value);
        PlatformDependent.putByte(1 + address, (byte) (value >>> 8));
        PlatformDependent.putByte(2 + address, (byte) (value >>> 16));
        PlatformDependent.putByte(3 + address, (byte) (value >>> 24));
    }

    static void setLong(long address, long value) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Long.reverseBytes(value);
            }
            PlatformDependent.putLong(address, value);
            return;
        }
        PlatformDependent.putByte(address, (byte) ((int) (value >>> 56)));
        PlatformDependent.putByte(1 + address, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(2 + address, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(3 + address, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(4 + address, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(5 + address, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(6 + address, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(7 + address, (byte) ((int) value));
    }

    static void setLongLE(long address, long value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Long.reverseBytes(value);
            }
            PlatformDependent.putLong(address, value);
            return;
        }
        PlatformDependent.putByte(address, (byte) ((int) value));
        PlatformDependent.putByte(1 + address, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(2 + address, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(3 + address, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(4 + address, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(5 + address, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(6 + address, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(7 + address, (byte) ((int) (value >>> 56)));
    }

    static byte getByte(byte[] array, int index) {
        return PlatformDependent.getByte(array, index);
    }

    static short getShort(byte[] array, int index) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte(array, index) << 8) | (PlatformDependent.getByte(array, index + 1) & 255));
        }
        short v = PlatformDependent.getShort(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Short.reverseBytes(v);
    }

    static short getShortLE(byte[] array, int index) {
        if (!UNALIGNED) {
            return (short) ((PlatformDependent.getByte((long) index) & 255) | (PlatformDependent.getByte((long) (index + 1)) << 8));
        }
        short v = PlatformDependent.getShort(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Short.reverseBytes(v);
        }
        return v;
    }

    static int getUnsignedMedium(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(array, index) & 255) << 16) | ((PlatformDependent.getByte(array, index + 1) & 255) << 8)) | (PlatformDependent.getByte(array, index + 2) & 255);
        }
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return (PlatformDependent.getByte(array, index) & 255) | ((PlatformDependent.getShort(array, index + 1) & 65535) << 8);
        }
        return ((Short.reverseBytes(PlatformDependent.getShort(array, index)) & 65535) << 8) | (PlatformDependent.getByte(array, index + 2) & 255);
    }

    static int getUnsignedMediumLE(byte[] array, int index) {
        if (!UNALIGNED) {
            return ((PlatformDependent.getByte(array, index) & 255) | ((PlatformDependent.getByte(array, index + 1) & 255) << 8)) | ((PlatformDependent.getByte(array, index + 2) & 255) << 16);
        }
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return ((Short.reverseBytes(PlatformDependent.getShort(array, index)) & 65535) << 8) | (PlatformDependent.getByte(array, index + 2) & 255);
        }
        return (PlatformDependent.getByte(array, index) & 255) | ((PlatformDependent.getShort(array, index + 1) & 65535) << 8);
    }

    static int getInt(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(array, index) << 24) | ((PlatformDependent.getByte(array, index + 1) & 255) << 16)) | ((PlatformDependent.getByte(array, index + 2) & 255) << 8)) | (PlatformDependent.getByte(array, index + 3) & 255);
        }
        int v = PlatformDependent.getInt(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Integer.reverseBytes(v);
    }

    static int getIntLE(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((PlatformDependent.getByte(array, index) & 255) | ((PlatformDependent.getByte(array, index + 1) & 255) << 8)) | ((PlatformDependent.getByte(array, index + 2) & 255) << 16)) | (PlatformDependent.getByte(array, index + 3) << 24);
        }
        int v = PlatformDependent.getInt(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Integer.reverseBytes(v);
        }
        return v;
    }

    static long getLong(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((((((((long) PlatformDependent.getByte(array, index)) << 56) | ((((long) PlatformDependent.getByte(array, index + 1)) & 255) << 48)) | ((((long) PlatformDependent.getByte(array, index + 2)) & 255) << 40)) | ((((long) PlatformDependent.getByte(array, index + 3)) & 255) << 32)) | ((((long) PlatformDependent.getByte(array, index + 4)) & 255) << 24)) | ((((long) PlatformDependent.getByte(array, index + 5)) & 255) << 16)) | ((((long) PlatformDependent.getByte(array, index + 6)) & 255) << 8)) | (((long) PlatformDependent.getByte(array, index + 7)) & 255);
        }
        long v = PlatformDependent.getLong(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return v;
        }
        return Long.reverseBytes(v);
    }

    static long getLongLE(byte[] array, int index) {
        if (!UNALIGNED) {
            return (((((((((long) PlatformDependent.getByte(array, index)) & 255) | ((((long) PlatformDependent.getByte(array, index + 1)) & 255) << 8)) | ((((long) PlatformDependent.getByte(array, index + 2)) & 255) << 16)) | ((((long) PlatformDependent.getByte(array, index + 3)) & 255) << 24)) | ((((long) PlatformDependent.getByte(array, index + 4)) & 255) << 32)) | ((((long) PlatformDependent.getByte(array, index + 5)) & 255) << 40)) | ((((long) PlatformDependent.getByte(array, index + 6)) & 255) << 48)) | (((long) PlatformDependent.getByte(array, index + 7)) << 56);
        }
        long v = PlatformDependent.getLong(array, index);
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            return Long.reverseBytes(v);
        }
        return v;
    }

    static void setByte(byte[] array, int index, int value) {
        PlatformDependent.putByte(array, index, (byte) value);
    }

    static void setShort(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short) value : Short.reverseBytes((short) value));
            return;
        }
        PlatformDependent.putByte(array, index, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 1, (byte) value);
    }

    static void setShortLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short) value) : (short) value);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) value);
        PlatformDependent.putByte(array, index + 1, (byte) (value >>> 8));
    }

    static void setMedium(byte[] array, int index, int value) {
        if (!UNALIGNED) {
            PlatformDependent.putByte(array, index, (byte) (value >>> 16));
            PlatformDependent.putByte(array, index + 1, (byte) (value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte) value);
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putByte(array, index, (byte) value);
            PlatformDependent.putShort(array, index + 1, (short) (value >>> 8));
        } else {
            PlatformDependent.putShort(array, index, Short.reverseBytes((short) (value >>> 8)));
            PlatformDependent.putByte(array, index + 2, (byte) value);
        }
    }

    static void setMediumLE(byte[] array, int index, int value) {
        if (!UNALIGNED) {
            PlatformDependent.putByte(array, index, (byte) value);
            PlatformDependent.putByte(array, index + 1, (byte) (value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte) (value >>> 16));
        } else if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            PlatformDependent.putShort(array, index, Short.reverseBytes((short) (value >>> 8)));
            PlatformDependent.putByte(array, index + 2, (byte) value);
        } else {
            PlatformDependent.putByte(array, index, (byte) value);
            PlatformDependent.putShort(array, index + 1, (short) (value >>> 8));
        }
    }

    static void setInt(byte[] array, int index, int value) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Integer.reverseBytes(value);
            }
            PlatformDependent.putInt(array, index, value);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) (value >>> 24));
        PlatformDependent.putByte(array, index + 1, (byte) (value >>> 16));
        PlatformDependent.putByte(array, index + 2, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 3, (byte) value);
    }

    static void setIntLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Integer.reverseBytes(value);
            }
            PlatformDependent.putInt(array, index, value);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) value);
        PlatformDependent.putByte(array, index + 1, (byte) (value >>> 8));
        PlatformDependent.putByte(array, index + 2, (byte) (value >>> 16));
        PlatformDependent.putByte(array, index + 3, (byte) (value >>> 24));
    }

    static void setLong(byte[] array, int index, long value) {
        if (UNALIGNED) {
            if (!PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Long.reverseBytes(value);
            }
            PlatformDependent.putLong(array, index, value);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) ((int) (value >>> 56)));
        PlatformDependent.putByte(array, index + 1, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(array, index + 2, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(array, index + 3, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(array, index + 4, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(array, index + 5, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(array, index + 6, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(array, index + 7, (byte) ((int) value));
    }

    static void setLongLE(byte[] array, int index, long value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                value = Long.reverseBytes(value);
            }
            PlatformDependent.putLong(array, index, value);
            return;
        }
        PlatformDependent.putByte(array, index, (byte) ((int) value));
        PlatformDependent.putByte(array, index + 1, (byte) ((int) (value >>> 8)));
        PlatformDependent.putByte(array, index + 2, (byte) ((int) (value >>> 16)));
        PlatformDependent.putByte(array, index + 3, (byte) ((int) (value >>> 24)));
        PlatformDependent.putByte(array, index + 4, (byte) ((int) (value >>> 32)));
        PlatformDependent.putByte(array, index + 5, (byte) ((int) (value >>> 40)));
        PlatformDependent.putByte(array, index + 6, (byte) ((int) (value >>> 48)));
        PlatformDependent.putByte(array, index + 7, (byte) ((int) (value >>> 56)));
    }

    static void setZero(byte[] array, int index, int length) {
        if (length != 0) {
            PlatformDependent.setMemory(array, index, (long) length, (byte) 0);
        }
    }

    static ByteBuf copy(AbstractByteBuf buf, long addr, int index, int length) {
        buf.checkIndex(index, length);
        ByteBuf copy = buf.alloc().directBuffer(length, buf.maxCapacity());
        if (length != 0) {
            if (copy.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr, copy.memoryAddress(), (long) length);
                copy.setIndex(0, length);
            } else {
                copy.writeBytes(buf, index, length);
            }
        }
        return copy;
    }

    static int setBytes(AbstractByteBuf buf, long addr, int index, InputStream in, int length) throws IOException {
        buf.checkIndex(index, length);
        ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
        try {
            byte[] tmp = tmpBuf.array();
            int offset = tmpBuf.arrayOffset();
            int readBytes = in.read(tmp, offset, length);
            if (readBytes > 0) {
                PlatformDependent.copyMemory(tmp, offset, addr, (long) readBytes);
            }
            tmpBuf.release();
            return readBytes;
        } catch (Throwable th) {
            tmpBuf.release();
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuf dst, int dstIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(dst, "dst");
        if (MathUtil.isOutOfBounds(dstIndex, length, dst.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        } else if (dst.hasMemoryAddress()) {
            long j = addr;
            PlatformDependent.copyMemory(j, ((long) dstIndex) + dst.memoryAddress(), (long) length);
        } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dstIndex, (long) length);
        } else {
            dst.setBytes(dstIndex, buf, index, length);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, byte[] dst, int dstIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(dst, "dst");
        if (MathUtil.isOutOfBounds(dstIndex, length, dst.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        } else if (length != 0) {
            PlatformDependent.copyMemory(addr, dst, dstIndex, (long) length);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer dst) {
        buf.checkIndex(index);
        int bytesToCopy = Math.min(buf.capacity() - index, dst.remaining());
        if (bytesToCopy != 0) {
            if (dst.isDirect()) {
                if (dst.isReadOnly()) {
                    throw new ReadOnlyBufferException();
                }
                PlatformDependent.copyMemory(addr, PlatformDependent.directBufferAddress(dst) + ((long) dst.position()), (long) bytesToCopy);
                dst.position(dst.position() + bytesToCopy);
            } else if (dst.hasArray()) {
                PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dst.position(), (long) bytesToCopy);
                dst.position(dst.position() + bytesToCopy);
            } else {
                dst.put(buf.nioBuffer());
            }
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuf src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(src, "src");
        if (MathUtil.isOutOfBounds(srcIndex, length, src.capacity())) {
            throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
        } else if (length == 0) {
        } else {
            if (src.hasMemoryAddress()) {
                PlatformDependent.copyMemory(src.memoryAddress() + ((long) srcIndex), addr, (long) length);
            } else if (src.hasArray()) {
                PlatformDependent.copyMemory(src.array(), src.arrayOffset() + srcIndex, addr, (long) length);
            } else {
                src.getBytes(srcIndex, buf, index, length);
            }
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, byte[] src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            PlatformDependent.copyMemory(src, srcIndex, addr, (long) length);
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer src) {
        buf.checkIndex(index, src.remaining());
        int length = src.remaining();
        if (length != 0) {
            if (src.isDirect()) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(src) + ((long) src.position()), addr, (long) src.remaining());
                src.position(src.position() + length);
            } else if (src.hasArray()) {
                PlatformDependent.copyMemory(src.array(), src.arrayOffset() + src.position(), addr, (long) length);
                src.position(src.position() + length);
            } else {
                ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
                try {
                    byte[] tmp = tmpBuf.array();
                    src.get(tmp, tmpBuf.arrayOffset(), length);
                    PlatformDependent.copyMemory(tmp, tmpBuf.arrayOffset(), addr, (long) length);
                } finally {
                    tmpBuf.release();
                }
            }
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, OutputStream out, int length) throws IOException {
        buf.checkIndex(index, length);
        if (length != 0) {
            ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
            try {
                byte[] tmp = tmpBuf.array();
                int offset = tmpBuf.arrayOffset();
                PlatformDependent.copyMemory(addr, tmp, offset, (long) length);
                out.write(tmp, offset, length);
            } finally {
                tmpBuf.release();
            }
        }
    }

    static void setZero(AbstractByteBuf buf, long addr, int index, int length) {
        if (length != 0) {
            buf.checkIndex(index, length);
            PlatformDependent.setMemory(addr, (long) length, (byte) 0);
        }
    }

    static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new UnpooledUnsafeNoCleanerDirectByteBuf(alloc, initialCapacity, maxCapacity);
        }
        return new UnpooledUnsafeDirectByteBuf(alloc, initialCapacity, maxCapacity);
    }

    private UnsafeByteBufUtil() {
    }
}
