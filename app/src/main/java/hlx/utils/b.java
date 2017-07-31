package hlx.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

@SuppressLint({"HandlerLeak"})
/* compiled from: CountDownTimer */
public class b {
    private static final int cgH = 0;
    protected Handler Vo = new Handler(this) {
        final /* synthetic */ b cgJ;

        {
            this.cgJ = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    this.cgJ.cgI = this.cgJ.cgI - 1;
                    if (this.cgJ.cgI > 0) {
                        this.cgJ.Vo.sendEmptyMessageDelayed(0, 1000);
                        return;
                    } else if (this.cgJ.cgG != null) {
                        this.cgJ.cgG.qZ();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private a cgG;
    private int cgI;

    /* compiled from: CountDownTimer */
    public static abstract class a {
        public abstract void qZ();
    }

    public b(a cb) {
        this.cgG = cb;
    }

    public b(a cb, int countDownValue, boolean isBegin) {
        this.cgG = cb;
        this.cgI = countDownValue;
        if (isBegin) {
            this.Vo.sendEmptyMessage(0);
        }
    }

    public void nP(int countDownValue) {
        this.Vo.removeMessages(0);
        this.cgI = countDownValue;
    }

    public void Wa() {
        this.Vo.sendEmptyMessage(0);
    }

    public void pause() {
        this.Vo.removeMessages(0);
    }

    public int Wb() {
        return this.cgI;
    }

    public void Wc() {
        this.Vo.removeMessages(0);
        this.cgI = 0;
    }
}
