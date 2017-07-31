package com.huawei.android.pushagent.c.a.a;

import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import java.security.MessageDigest;

public class h {
    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer(40);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append('0');
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            e.a(BLocation.TAG, "getSHA256str:" + stringBuffer.toString());
            str = stringBuffer.toString();
        } catch (Throwable e) {
            e.c(BLocation.TAG, e.toString(), e);
        } catch (Throwable e2) {
            e.c(BLocation.TAG, e2.toString(), e2);
        } catch (Throwable e22) {
            e.c(BLocation.TAG, e22.toString(), e22);
        }
        return str;
    }
}
