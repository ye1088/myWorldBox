package hlx.widget.news;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.huluxia.framework.R;
import com.huluxia.mctool.f$p;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class FlipperView extends FrameLayout {
    private static final int cgP = 5000;
    private static final int cgQ = 2000;
    private boolean bvn;
    private View cgR;
    private View cgS;
    private View cgT;
    private int cgU;
    private Animator cgV;
    private Animator cgW;
    private AnimatorSet cgX;
    private a cgY;
    private long cgZ;
    private long cha;
    private Runnable chb;
    private Handler mHandler;
    private boolean mRunning;
    private boolean mStarted;

    public interface a {
        void onClick(View view);
    }

    public FlipperView(Context context) {
        this(context, null);
    }

    public FlipperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.cgU = 5000;
        this.mRunning = false;
        this.mStarted = false;
        this.bvn = false;
        this.chb = new Runnable(this) {
            final /* synthetic */ FlipperView chc;

            {
                this.chc = this$0;
            }

            public void run() {
                this.chc.Wd();
                this.chc.mHandler.postDelayed(this.chc.chb, (long) this.chc.cgU);
            }
        };
        init(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalStateException("FlipperView必须包含两个Child");
        }
        this.cgR = getChildAt(0);
        this.cgS = getChildAt(1);
        ViewHelper.setAlpha(this.cgS, 0.0f);
        this.cgT = this.cgR;
        setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FlipperView chc;

            {
                this.chc = this$0;
            }

            public void onClick(View v) {
                this.chc.cgY.onClick(this.chc.cgT);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        this.mHandler = new Handler(Looper.getMainLooper());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, f$p.FlipperView, 0, 0);
        try {
            this.cgU = a.getInt(2, 5000);
            this.cgZ = (long) context.getResources().getInteger(R.integer.card_flip_duration_full);
            this.cha = (long) context.getResources().getInteger(R.integer.card_flip_duration_half);
        } finally {
            a.recycle();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.bvn = false;
        NZ();
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.bvn = visibility == 0;
        NZ();
    }

    private void Wd() {
        if (this.cgX != null && this.cgX.isStarted()) {
            this.cgX.end();
        }
        this.cgV = S(this.cgT == this.cgR ? this.cgS : this.cgR);
        this.cgW = T(this.cgT);
        this.cgX = new AnimatorSet();
        this.cgX.playTogether(this.cgV, this.cgW);
        this.cgX.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ FlipperView chc;

            {
                this.chc = this$0;
            }

            public void onAnimationEnd(Animator animation) {
                this.chc.cgX.removeListener(this);
                this.chc.cgT = this.chc.cgT == this.chc.cgR ? this.chc.cgS : this.chc.cgR;
            }
        });
        this.cgX.start();
    }

    private Animator S(View target) {
        AnimatorSet flipIn = new AnimatorSet();
        Animator showDelay = ObjectAnimator.ofFloat((Object) target, "alpha", 1.0f).setDuration(0);
        showDelay.setStartDelay(this.cha);
        r2 = new Animator[3];
        r2[0] = ObjectAnimator.ofFloat((Object) target, "alpha", 0.0f).setDuration(0);
        r2[1] = ObjectAnimator.ofFloat((Object) target, "rotationY", -180.0f, 0.0f).setDuration(this.cgZ);
        r2[2] = showDelay;
        flipIn.playTogether(r2);
        return flipIn;
    }

    private Animator T(View target) {
        AnimatorSet flipOut = new AnimatorSet();
        ObjectAnimator.ofFloat((Object) target, "alpha", 0.0f).setDuration(0).setStartDelay(this.cha);
        flipOut.playTogether(ObjectAnimator.ofFloat((Object) target, "rotationY", 0.0f, 180.0f).setDuration(this.cgZ), hideDelay);
        return flipOut;
    }

    public void startFlipping() {
        this.mStarted = true;
        NZ();
    }

    public void stopFlipping() {
        this.mStarted = false;
        NZ();
    }

    private void NZ() {
        boolean running = this.mStarted && this.bvn;
        if (running != this.mRunning) {
            this.mRunning = running;
            if (this.mRunning) {
                this.mHandler.removeCallbacks(this.chb);
                this.mHandler.postDelayed(this.chb, (long) this.cgU);
                return;
            }
            this.mHandler.removeCallbacks(this.chb);
        }
    }

    public void setAnimationInterval(int interval) {
        this.cgU = Math.max(interval, cgQ);
    }

    public void setListener(a listener) {
        this.cgY = listener;
    }
}
