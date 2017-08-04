package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.huluxia.framework.R;
import com.huluxia.utils.ah;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: FloatMainLogo */
public class o {
    private static final String Vb = "Float_LogoPos_X";
    private static final String Vc = "Float_LogoPos_Y";
    private OnClickListener QF = null;
    private ImageView Vd = null;
    private LayoutParams Ve = null;
    private boolean Vf = false;
    private boolean Vg = false;
    @SuppressLint({"ClickableViewAccessibility"})
    private OnTouchListener Vh = new OnTouchListener(this) {
        float UT;
        float UV;
        float UW;
        float UX;
        final /* synthetic */ o Vi;

        {
            this.Vi = this$0;
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (this.Vi.Vg) {
                float x = event.getRawX();
                float y = event.getRawY();
                switch (event.getAction()) {
                    case 0:
                        this.UW = x;
                        this.UT = x;
                        this.UX = y;
                        this.UV = y;
                        this.Vi.Vd.setBackgroundResource(R.drawable.icon_entry_down);
                        break;
                    case 1:
                        this.Vi.Vd.setBackgroundResource(R.drawable.icon_entry_normal);
                        int minMove = (int) (9.0f * a.qu());
                        if (Math.abs(x - this.UT) <= ((float) minMove) && Math.abs(y - this.UV) <= ((float) minMove)) {
                            this.Vi.QF.onClick(this.Vi.Vd);
                            break;
                        }
                        ah.KZ().Q(o.Vb, this.Vi.Ve.x);
                        ah.KZ().Q(o.Vc, this.Vi.Ve.y);
                        break;
                    case 2:
                        LayoutParams c = this.Vi.Ve;
                        c.x = (int) (((float) c.x) + (x - this.UW));
                        c = this.Vi.Ve;
                        c.y = (int) (((float) c.y) + (y - this.UX));
                        this.Vi.mWindowManager.updateViewLayout(this.Vi.Vd, this.Vi.Ve);
                        this.UW = x;
                        this.UX = y;
                        break;
                    default:
                        break;
                }
            }
            return false;
        }
    };
    private Context mContext = null;
    private WindowManager mWindowManager = null;

    public o(Context c, OnClickListener l) {
        this.mContext = c;
        this.QF = l;
        this.mWindowManager = (WindowManager) c.getSystemService("window");
        this.Vd = new ImageView(this.mContext);
        this.Vd.setId(R.id.logo_entry);
        this.Vd.setOnTouchListener(this.Vh);
        this.Vd.setBackgroundResource(R.drawable.icon_entry_normal);
        this.Ve = new LayoutParams();
        this.Ve.format = 1;
        this.Ve.width = (int) (a.qu() * 50.0f);
        this.Ve.height = (int) (a.qu() * 50.0f);
        this.Ve.gravity = 17;
        this.Ve.flags = 40;
    }

    public boolean sb() {
        return this.Vg;
    }

    public void aI(boolean bShow) {
        if (this.Vg != bShow) {
            this.Vg = bShow;
            if (bShow) {
                this.mWindowManager.addView(this.Vd, this.Ve);
                if (!this.Vf) {
                    this.Vf = true;
                    this.Ve.x = ah.KZ().get_config_sp_intVal(Vb, 0);
                    this.Ve.y = ah.KZ().get_config_sp_intVal(Vc, 0);
                    if (this.Ve.x + this.Ve.y != 0) {
                        this.mWindowManager.updateViewLayout(this.Vd, this.Ve);
                        return;
                    }
                    return;
                }
                return;
            }
            this.mWindowManager.removeView(this.Vd);
        }
    }

    public ImageView sc() {
        return this.Vd;
    }

    public void recycle() {
        if (this.Vg) {
            this.mWindowManager.removeView(this.Vd);
        }
        new Timer().schedule(new TimerTask(this) {
            final /* synthetic */ o Vi;

            {
                this.Vi = this$0;
            }

            public void run() {
            }
        }, 10);
    }
}
