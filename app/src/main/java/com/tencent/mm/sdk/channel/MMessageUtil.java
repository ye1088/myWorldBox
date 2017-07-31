package com.tencent.mm.sdk.channel;

import com.tencent.mm.algorithm.MD5;

class MMessageUtil {
    private MMessageUtil() {
    }

    static byte[] a(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append(553910273);
        stringBuffer.append(str2);
        stringBuffer.append("mMcShCsTr");
        return MD5.getMessageDigest(stringBuffer.toString().substring(1, 9).getBytes()).getBytes();
    }
}
