package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.huluxia.mcfloat.achieve.a;
import com.huluxia.mcfloat.potion.c;
import com.huluxia.mcinterface.h;
import com.huluxia.r;

/* compiled from: FloatManager */
public class q {
    private static q VU;
    private o VR = null;
    private p VS = null;
    private a VT = null;
    private OnClickListener mClickListener = new 2(this);
    private Activity mContext = null;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new 1(this);

    public static synchronized void aJ(boolean isShow) {
        synchronized (q.class) {
            if (VU != null) {
                VU.VR.aI(isShow);
                VU.VS.aI(isShow);
            }
        }
    }

    public static synchronized void aK(boolean isShow) {
        synchronized (q.class) {
            if (VU != null) {
                VU.VR.aI(isShow);
            }
        }
    }

    public static ImageView sc() {
        if (VU != null) {
            return VU.VR.sc();
        }
        return null;
    }

    public q(Activity c, View view) {
        VU = this;
        this.mContext = c;
        c.init(c);
        c.uQ();
        this.VR = new o(c, this.mClickListener);
        this.VS = new p(c, this.mClickListener);
        this.VT = new a();
        this.VR.aI(true);
        h.zM().a(this.VS);
        h.zM().a(this.VT);
        String tongjiText = "";
        int ver = h.zx();
        if (ver == 0) {
            tongjiText = r.a.jI;
        }
        if (ver == 1) {
            tongjiText = r.a.jJ;
        }
        if (ver == 2) {
            tongjiText = r.a.jM;
        }
        if (ver == 3) {
            if (com.huluxia.mcsdk.dtlib.h.CW().CY() == 0) {
                tongjiText = r.a.jN;
            } else {
                tongjiText = r.a.jO;
            }
        }
        if (ver == 5) {
            if (com.huluxia.mcsdk.dtlib.h.CW().CY() == 0) {
                tongjiText = r.a.jR;
            } else {
                tongjiText = r.a.jP;
            }
        }
        r.ck().K(tongjiText);
    }

    public void recycle() {
        this.VR.aI(false);
        this.VS.aI(false);
        VU = null;
    }
}
