package com.huawei.android.pushselfshow;

import android.content.Context;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a.a;
import com.huawei.android.pushselfshow.utils.b.b;
import com.tencent.connect.common.Constants;
import java.io.File;
import java.util.ArrayList;

class SelfShowReceiver$a extends Thread {
    Context a;
    String b;

    public SelfShowReceiver$a(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public void run() {
        ArrayList a = a.a(this.a, this.b);
        int size = a.size();
        e.e("PushSelfShowLog", "receive package add ,arrSize " + size);
        for (int i = 0; i < size; i++) {
            com.huawei.android.pushselfshow.utils.a.a(this.a, Constants.VIA_REPORT_TYPE_START_WAP, (String) a.get(i), PushConstants.EXTRA_APP);
        }
        if (size > 0) {
            a.b(this.a, this.b);
        }
        com.huawei.android.pushselfshow.utils.a.b(new File(b.a(this.a)));
    }
}
