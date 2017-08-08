package io.netty.handler.codec.http2.internal.hpack;

import com.MCWorld.framework.base.image.Config;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.util.AsciiString;
import io.netty.util.internal.ConstantTimeUtils;
import io.netty.util.internal.PlatformDependent;

final class HpackUtil {
    static final int[] HUFFMAN_CODES = new int[]{8184, 8388568, 268435426, 268435427, 268435428, 268435429, 268435430, 268435431, 268435432, 16777194, 1073741820, 268435433, 268435434, 1073741821, 268435435, 268435436, 268435437, 268435438, 268435439, 268435440, 268435441, 268435442, 1073741822, 268435443, 268435444, 268435445, 268435446, 268435447, 268435448, 268435449, 268435450, 268435451, 20, 1016, 1017, 4090, 8185, 21, 248, 2042, 1018, 1019, 249, 2043, Config.DEFAULT_FADE_DURATION, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, 251, 32764, 32, 4091, 1020, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 252, 115, 253, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, 121, 122, 123, 32766, 2044, 16381, 8189, 268435452, 1048550, 4194258, 1048551, 1048552, 4194259, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 8388573, 8388574, 16777195, 8388575, 16777196, 16777197, 4194263, 8388576, 16777198, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 8388583, 16777199, 4194266, 2097117, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 4194269, 4194270, 16777200, 2097119, 4194271, 8388587, 8388588, 2097120, 2097121, 4194272, 2097122, 8388589, 4194273, 8388590, 8388591, 1048554, 4194274, 4194275, 4194276, 8388592, 4194277, 4194278, 8388593, 67108832, 67108833, 1048555, 524273, 4194279, 8388594, 4194280, 33554412, 67108834, 67108835, 67108836, 134217694, 134217695, 67108837, 16777201, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 16777202, 2097124, 2097125, 67108840, 67108841, 268435453, 134217699, 134217700, 134217701, 1048556, 16777203, 1048557, 2097126, 4194281, 2097127, 2097128, 8388595, 4194282, 4194283, 33554414, 33554415, 16777204, 16777205, 67108842, 8388596, 67108843, 134217702, 67108844, 67108845, 134217703, 134217704, 134217705, 134217706, 134217707, 268435454, 134217708, 134217709, 134217710, 134217711, 134217712, 67108846, 1073741823};
    static final byte[] HUFFMAN_CODE_LENGTHS = new byte[]{(byte) 13, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.GATQ, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.GATQ, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.GATQ, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.TOUCH, (byte) 6, (byte) 10, (byte) 10, (byte) 12, (byte) 13, (byte) 6, (byte) 8, (byte) 11, (byte) 10, (byte) 10, (byte) 8, (byte) 11, (byte) 8, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 5, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 7, (byte) 8, (byte) 15, (byte) 6, (byte) 12, (byte) 10, (byte) 13, (byte) 6, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 8, (byte) 7, (byte) 8, (byte) 13, BinaryMemcacheOpcodes.REPLACEQ, (byte) 13, (byte) 14, (byte) 6, (byte) 15, (byte) 5, (byte) 6, (byte) 5, (byte) 6, (byte) 5, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 7, (byte) 7, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 6, (byte) 7, (byte) 6, (byte) 5, (byte) 5, (byte) 6, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 7, (byte) 15, (byte) 11, (byte) 14, (byte) 13, BinaryMemcacheOpcodes.TOUCH, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.REPLACEQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, (byte) 27, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.REPLACEQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, (byte) 27, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.TOUCH, (byte) 27, (byte) 27, (byte) 27, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.DELETEQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.INCREMENTQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.DECREMENTQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.APPENDQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.FLUSHQ, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.QUITQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.PREPENDQ, (byte) 27, (byte) 27, (byte) 27, (byte) 27, (byte) 27, BinaryMemcacheOpcodes.TOUCH, (byte) 27, (byte) 27, (byte) 27, (byte) 27, (byte) 27, BinaryMemcacheOpcodes.PREPENDQ, BinaryMemcacheOpcodes.GATQ};
    static final int HUFFMAN_EOS = 256;

    enum IndexType {
        INCREMENTAL,
        NONE,
        NEVER
    }

    static int equalsConstantTime(CharSequence s1, CharSequence s2) {
        if (!(s1 instanceof AsciiString) || !(s2 instanceof AsciiString)) {
            return ConstantTimeUtils.equalsConstantTime(s1, s2);
        }
        if (s1.length() != s2.length()) {
            return 0;
        }
        AsciiString s1Ascii = (AsciiString) s1;
        AsciiString s2Ascii = (AsciiString) s2;
        return PlatformDependent.equalsConstantTime(s1Ascii.array(), s1Ascii.arrayOffset(), s2Ascii.array(), s2Ascii.arrayOffset(), s1.length());
    }

    private HpackUtil() {
    }
}
