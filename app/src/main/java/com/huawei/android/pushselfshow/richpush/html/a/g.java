package com.huawei.android.pushselfshow.richpush.html.a;

import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.a.f.a;
import com.huawei.android.pushselfshow.richpush.html.api.d;
import org.json.JSONObject;

class g implements Runnable {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    public void run() {
        try {
            e.e("PushSelfShowLog", "getPlayingStatusRb getPlayingStatus this.state = " + this.a.e);
            if (a.MEDIA_RUNNING.ordinal() == this.a.e.ordinal()) {
                long i = this.a.i();
                float b = this.a.k();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("current_postion", i);
                    jSONObject.put("duration", (double) b);
                    jSONObject.put("url", this.a.f);
                    this.a.j.a(this.a.a, d.a.OK, "status", jSONObject);
                } catch (Throwable e) {
                    e.d("PushSelfShowLog", "getPlayingStatus error", e);
                }
            }
        } catch (Throwable e2) {
            e.d("PushSelfShowLog", "getPlayingStatusRb run error", e2);
        }
        if (a.MEDIA_NONE.ordinal() != this.a.e.ordinal() && a.MEDIA_STOPPED.ordinal() != this.a.e.ordinal()) {
            this.a.b.postDelayed(this, (long) this.a.g);
        }
    }
}
