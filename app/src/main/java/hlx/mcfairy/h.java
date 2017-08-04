package hlx.mcfairy;

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
import com.huluxia.mcfloat.a;
import com.huluxia.utils.ah;

/* compiled from: FloatLogoBase */
public class h {
    private static final String bTP = "Float_Logo_Pos_X_DEFAULT";
    private static final String bTQ = "Float_Logo_Pos_Y_DEFAULT";
    protected OnClickListener QF = null;
    protected ImageView Vd = null;
    protected LayoutParams Ve = null;
    private boolean Vf = true;
    protected boolean Vg = false;
    @SuppressLint({"ClickableViewAccessibility"})
    protected OnTouchListener Vh = new OnTouchListener(this) {
        float UT;
        float UV;
        float UW;
        float UX;
        final /* synthetic */ h bTW;

        {
            this.bTW = this$0;
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (this.bTW.Vg && this.bTW.bTR) {
                float x = event.getRawX();
                float y = event.getRawY();
                switch (event.getAction()) {
                    case 0:
                        this.UW = x;
                        this.UT = x;
                        this.UX = y;
                        this.UV = y;
                        this.bTW.Vd.setBackgroundResource(this.bTW.bTV);
                        break;
                    case 1:
                        this.bTW.Vd.setBackgroundResource(this.bTW.bTU);
                        int minMove = (int) (9.0f * a.qu());
                        if (Math.abs(x - this.UT) <= ((float) minMove) && Math.abs(y - this.UV) <= ((float) minMove)) {
                            this.bTW.QF.onClick(this.bTW.Vd);
                            break;
                        }
                        ah.KZ().Q(this.bTW.bTS, this.bTW.Ve.x);
                        ah.KZ().Q(this.bTW.bTT, this.bTW.Ve.y);
                        break;
                        break;
                    case 2:
                        LayoutParams layoutParams;
                        if (this.bTW.Ve.gravity == 17) {
                            layoutParams = this.bTW.Ve;
                            layoutParams.x = (int) (((float) layoutParams.x) + (x - this.UW));
                        } else if (this.bTW.Ve.gravity == 53) {
                            layoutParams = this.bTW.Ve;
                            layoutParams.x = (int) (((float) layoutParams.x) + (this.UW - x));
                        } else {
                            layoutParams = this.bTW.Ve;
                            layoutParams.x = (int) (((float) layoutParams.x) + (x - this.UW));
                        }
                        layoutParams = this.bTW.Ve;
                        layoutParams.y = (int) (((float) layoutParams.y) + (y - this.UX));
                        this.bTW.mWindowManager.updateViewLayout(this.bTW.Vd, this.bTW.Ve);
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
    protected boolean bTR;
    protected String bTS;
    protected String bTT;
    protected int bTU;
    protected int bTV;
    protected Context mContext = null;
    protected WindowManager mWindowManager = null;

    public h(Context c, OnClickListener l) {
        this.mContext = c;
        this.QF = l;
        this.bTR = true;
        this.Vf = true;
        this.bTU = R.drawable.icon_entry_normal;
        this.bTV = R.drawable.icon_entry_down;
        this.mWindowManager = (WindowManager) c.getSystemService("window");
        this.Vd = new ImageView(this.mContext);
        Te();
        this.Ve = new LayoutParams();
        this.Ve.format = 1;
        this.Ve.width = (int) (a.qu() * 50.0f);
        this.Ve.height = (int) (a.qu() * 50.0f);
        this.Ve.gravity = 17;
        this.Ve.flags = 40;
        this.bTS = bTP;
        this.bTT = bTQ;
    }

    public void bn(int normalResId, int pressedResId) {
        this.bTU = normalResId;
        this.bTV = pressedResId;
        this.Vd.setBackgroundResource(this.bTU);
        this.Vd.setId(normalResId);
    }

    public void setSize(int width, int height) {
        this.Ve.width = (int) (((float) width) * a.qu());
        this.Ve.height = (int) (((float) height) * a.qu());
    }

    public void mU(int gravity) {
        if (this.Ve != null) {
            this.Ve.gravity = gravity;
            this.mWindowManager.updateViewLayout(this.Vd, this.Ve);
        }
    }

    public boolean Td() {
        return this.Vg;
    }

    public void b(boolean isShow, int locationX, int locationY) {
        if (this.Vg != isShow) {
            this.Vg = isShow;
            if (isShow) {
                this.mWindowManager.addView(this.Vd, this.Ve);
                if (this.Vf) {
                    this.Vf = false;
                    this.Ve.x = locationX;
                    this.Ve.y = locationY;
                    if (this.Ve.x + this.Ve.y == 0) {
                        this.Ve.x = ah.KZ().get_config_sp_intVal(this.bTS, 0);
                        this.Ve.y = ah.KZ().get_config_sp_intVal(this.bTT, 0);
                    }
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

    public void dC(boolean isShow) {
        b(isShow, 0, 0);
    }

    public void aF(String inputKeyPosX, String inputKeyPosY) {
        this.bTS = inputKeyPosX;
        this.bTT = inputKeyPosY;
    }

    public ImageView sc() {
        return this.Vd;
    }

    protected void Te() {
        this.Vd.setOnTouchListener(this.Vh);
    }

    public void dD(boolean isCanMove) {
        this.bTR = isCanMove;
    }

    public void recycle() {
        if (this.Vg) {
            this.mWindowManager.removeView(this.Vd);
        }
    }
}
