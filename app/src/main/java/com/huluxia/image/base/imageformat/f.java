package com.huluxia.image.base.imageformat;

import com.huluxia.framework.base.utils.Preconditions;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* compiled from: ImageFormatCheckerUtils */
public class f {
    public static byte[] bk(String value) {
        Preconditions.checkNotNull(value);
        try {
            return value.getBytes(HTTP.ASCII);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("ASCII not found!", uee);
        }
    }

    public static boolean c(byte[] byteArray, byte[] pattern) {
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkNotNull(pattern);
        if (pattern.length > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i] != pattern[i]) {
                return false;
            }
        }
        return true;
    }

    private f() {
    }
}
