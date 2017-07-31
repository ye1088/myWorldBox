package io.netty.handler.codec.compression;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;
import org.apache.tools.tar.TarConstants;

final class Bzip2MoveToFrontTable {
    private final byte[] mtf = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, BinaryMemcacheOpcodes.SETQ, BinaryMemcacheOpcodes.ADDQ, BinaryMemcacheOpcodes.REPLACEQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.GAT, BinaryMemcacheOpcodes.GATQ, (byte) 31, (byte) 32, BinaryMemcacheOpcodes.SASL_AUTH, (byte) 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, HttpConstants.COMMA, (byte) 45, (byte) 46, (byte) 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, HttpConstants.COLON, HttpConstants.SEMICOLON, (byte) 60, HttpConstants.EQUALS, (byte) 62, (byte) 63, (byte) 64, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, TarConstants.LF_GNUTYPE_SPARSE, (byte) 84, (byte) 85, (byte) 86, (byte) 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 89, (byte) 90, (byte) 91, (byte) 92, (byte) 93, (byte) 94, (byte) 95, (byte) 96, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 121, (byte) 122, (byte) 123, (byte) 124, (byte) 125, (byte) 126, Byte.MAX_VALUE, DefaultBinaryMemcacheRequest.REQUEST_MAGIC_BYTE, DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -117, (byte) -116, (byte) -115, (byte) -114, (byte) -113, (byte) -112, (byte) -111, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -101, (byte) -100, (byte) -99, (byte) -98, (byte) -97, (byte) -96, (byte) -95, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -85, (byte) -84, (byte) -83, (byte) -82, (byte) -81, (byte) -80, (byte) -79, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -69, (byte) -68, (byte) -67, (byte) -66, (byte) -65, (byte) -64, (byte) -63, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -53, (byte) -52, (byte) -51, (byte) -50, (byte) -49, (byte) -48, (byte) -47, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -37, (byte) -36, (byte) -35, (byte) -34, (byte) -33, (byte) -32, (byte) -31, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -21, (byte) -20, (byte) -19, (byte) -18, (byte) -17, (byte) -16, (byte) -15, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -5, (byte) -4, (byte) -3, (byte) -2, (byte) -1};

    Bzip2MoveToFrontTable() {
    }

    int valueToFront(byte value) {
        int index = 0;
        byte temp = this.mtf[0];
        if (value != temp) {
            this.mtf[0] = value;
            while (value != temp) {
                index++;
                byte temp2 = temp;
                temp = this.mtf[index];
                this.mtf[index] = temp2;
            }
        }
        return index;
    }

    byte indexToFront(int index) {
        byte value = this.mtf[index];
        System.arraycopy(this.mtf, 0, this.mtf, 1, index);
        this.mtf[0] = value;
        return value;
    }
}
