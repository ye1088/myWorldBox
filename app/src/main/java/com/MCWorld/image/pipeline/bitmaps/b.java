package com.MCWorld.image.pipeline.bitmaps;

import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.base.imagepipeline.memory.f;
import com.MCWorld.image.core.common.references.a;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;
import java.io.IOException;
import org.apache.tools.tar.TarConstants;

/* compiled from: EmptyJpegGenerator */
public class b {
    private static final byte[] Em = new byte[]{(byte) -1, (byte) -40, (byte) -1, (byte) -37, (byte) 0, (byte) 67, (byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -64, (byte) 0, BinaryMemcacheOpcodes.SETQ, (byte) 8};
    private static final byte[] En = new byte[]{(byte) 3, (byte) 1, (byte) 34, (byte) 0, (byte) 2, BinaryMemcacheOpcodes.SETQ, (byte) 0, (byte) 3, BinaryMemcacheOpcodes.SETQ, (byte) 0, (byte) -1, (byte) -60, (byte) 0, (byte) 31, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) -1, (byte) -60, (byte) 0, (byte) -75, (byte) 16, (byte) 0, (byte) 2, (byte) 1, (byte) 3, (byte) 3, (byte) 2, (byte) 4, (byte) 3, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 125, (byte) 1, (byte) 2, (byte) 3, (byte) 0, (byte) 4, BinaryMemcacheOpcodes.SETQ, (byte) 5, BinaryMemcacheOpcodes.ADDQ, BinaryMemcacheOpcodes.SASL_AUTH, TarConstants.LF_LINK, (byte) 65, (byte) 6, BinaryMemcacheOpcodes.REPLACEQ, (byte) 81, (byte) 97, (byte) 7, (byte) 34, (byte) 113, BinaryMemcacheOpcodes.DELETEQ, TarConstants.LF_SYMLINK, DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE, (byte) -111, (byte) -95, (byte) 8, BinaryMemcacheOpcodes.GATK, (byte) 66, (byte) -79, (byte) -63, BinaryMemcacheOpcodes.INCREMENTQ, (byte) 82, (byte) -47, (byte) -16, BinaryMemcacheOpcodes.GATKQ, TarConstants.LF_CHR, (byte) 98, (byte) 114, (byte) -126, (byte) 9, (byte) 10, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, HttpConstants.COLON, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, TarConstants.LF_GNUTYPE_SPARSE, (byte) 84, (byte) 85, (byte) 86, (byte) 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 89, (byte) 90, (byte) 99, (byte) 100, (byte) 101, (byte) 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 104, (byte) 105, (byte) 106, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 121, (byte) 122, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -31, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -15, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -1, (byte) -60, (byte) 0, (byte) 31, (byte) 1, (byte) 0, (byte) 3, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) -1, (byte) -60, (byte) 0, (byte) -75, BinaryMemcacheOpcodes.SETQ, (byte) 0, (byte) 2, (byte) 1, (byte) 2, (byte) 4, (byte) 4, (byte) 3, (byte) 4, (byte) 7, (byte) 5, (byte) 4, (byte) 4, (byte) 0, (byte) 1, (byte) 2, (byte) 119, (byte) 0, (byte) 1, (byte) 2, (byte) 3, BinaryMemcacheOpcodes.SETQ, (byte) 4, (byte) 5, BinaryMemcacheOpcodes.SASL_AUTH, TarConstants.LF_LINK, (byte) 6, BinaryMemcacheOpcodes.ADDQ, (byte) 65, (byte) 81, (byte) 7, (byte) 97, (byte) 113, BinaryMemcacheOpcodes.REPLACEQ, (byte) 34, TarConstants.LF_SYMLINK, DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE, (byte) 8, BinaryMemcacheOpcodes.DELETEQ, (byte) 66, (byte) -111, (byte) -95, (byte) -79, (byte) -63, (byte) 9, BinaryMemcacheOpcodes.GATK, TarConstants.LF_CHR, (byte) 82, (byte) -16, BinaryMemcacheOpcodes.INCREMENTQ, (byte) 98, (byte) 114, (byte) -47, (byte) 10, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.GATKQ, TarConstants.LF_BLK, (byte) -31, (byte) 37, (byte) -15, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, HttpConstants.COLON, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, TarConstants.LF_GNUTYPE_SPARSE, (byte) 84, (byte) 85, (byte) 86, (byte) 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 89, (byte) 90, (byte) 99, (byte) 100, (byte) 101, (byte) 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 104, (byte) 105, (byte) 106, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 121, (byte) 122, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -1, (byte) -38, (byte) 0, (byte) 12, (byte) 3, (byte) 1, (byte) 0, (byte) 2, BinaryMemcacheOpcodes.SETQ, (byte) 3, BinaryMemcacheOpcodes.SETQ, (byte) 0, (byte) 63, (byte) 0, (byte) -114, (byte) -118, (byte) 40, (byte) -96, (byte) 15, (byte) -1, (byte) -39};
    private final d Eo;

    public b(d pooledByteBufferFactory) {
        this.Eo = pooledByteBufferFactory;
    }

    public a<PooledByteBuffer> b(short width, short height) {
        f os = null;
        try {
            os = this.Eo.bu((Em.length + En.length) + 4);
            os.write(Em);
            os.write((byte) (height >> 8));
            os.write((byte) (height & 255));
            os.write((byte) (width >> 8));
            os.write((byte) (width & 255));
            os.write(En);
            a<PooledByteBuffer> b = a.b(os.ih());
            if (os != null) {
                os.close();
            }
            return b;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable th) {
            if (os != null) {
                os.close();
            }
        }
    }
}
