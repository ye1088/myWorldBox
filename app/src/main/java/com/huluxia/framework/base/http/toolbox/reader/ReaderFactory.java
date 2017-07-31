package com.huluxia.framework.base.http.toolbox.reader;

import com.huluxia.framework.base.utils.ByteArrayPool;

public class ReaderFactory {
    public static final int ENCODE_XOR_ENHANCED_LIMIT_BANDWIDTH_TYPE = 5;
    public static final int ENCODE_XOR_ENHANCED_TYPE = 4;
    public static final int ENCODE_XOR_TYPE = 3;
    public static final int LIMIT_BANDWIDTH_TYPE = 2;
    public static final int NORMAL = 1;

    public static DownloadReader getReader(ByteArrayPool byteArrayPool, int type) {
        switch (type) {
            case 2:
                return new BandwidthLimitReader(byteArrayPool);
            case 3:
                return new XorReader(byteArrayPool);
            case 4:
                return new XorEnhancedReader(byteArrayPool);
            case 5:
                return new XorEnhancedLimitBandwidthReader(byteArrayPool);
            default:
                return new NormalReader(byteArrayPool);
        }
    }
}
