package hlx.ui.userguide;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.n;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import hlx.module.userguide.b;
import java.util.List;

public class UserGuideActivity extends HTBaseLoadingActivity {
    public static final String cgv = "EXTRA_GUIDE_TYPE";
    public static final int cgx = 0;
    public static final int cgy = 1;
    private TextView cgA;
    private GestureDetectorCompat cgB;
    private FragmentStatePagerAdapter cgw;
    private List<hlx.module.userguide.a.a> cgz;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ UserGuideActivity cgC;

        {
            this.cgC = this$0;
        }

        @MessageHandler(message = 2823)
        public void onRecvAppGuide(boolean succ, hlx.module.userguide.a info) {
            if (this.cgC.mType == 0) {
                if (succ) {
                    this.cgC.cgz = info.list;
                    this.cgC.cgw.notifyDataSetChanged();
                    if (!UtilsFunction.empty(this.cgC.cgz)) {
                        this.cgC.cgA.setText("1/" + this.cgC.cgz.size());
                    }
                    this.cgC.FC();
                    return;
                }
                this.cgC.FB();
            }
        }

        @MessageHandler(message = 2824)
        public void onRecvMcGuide(boolean succ, hlx.module.userguide.a info) {
            if (this.cgC.mType == 1) {
                if (succ) {
                    this.cgC.cgz = info.list;
                    this.cgC.cgw.notifyDataSetChanged();
                    if (!UtilsFunction.empty(this.cgC.cgz)) {
                        this.cgC.cgA.setText("1/" + this.cgC.cgz.size());
                    }
                    this.cgC.FC();
                    return;
                }
                this.cgC.FB();
            }
        }
    };
    private ViewPager mPager;
    private int mType;

    private class ImageAdapter extends FragmentStatePagerAdapter {
        final /* synthetic */ UserGuideActivity cgC;

        public ImageAdapter(UserGuideActivity userGuideActivity, FragmentManager fm) {
            this.cgC = userGuideActivity;
            super(fm);
        }

        public Fragment getItem(int i) {
            return TipImageFragment.hO(((hlx.module.userguide.a.a) this.cgC.cgz.get(i)).imgUrl);
        }

        public int getCount() {
            return this.cgC.cgz == null ? 0 : this.cgC.cgz.size();
        }
    }

    public @interface a {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_user_guide);
        this.mType = getIntent().getIntExtra(cgv, 0);
        if (this.mType == 1) {
            IE();
            setRequestedOrientation(0);
        }
        this.cgA = (TextView) findViewById(g.tv_page);
        this.mPager = (ViewPager) findViewById(g.pager);
        this.cgw = new ImageAdapter(this, getSupportFragmentManager());
        this.mPager.setAdapter(this.cgw);
        this.mPager.setOffscreenPageLimit(2);
        this.mPager.addOnPageChangeListener(new SimpleOnPageChangeListener(this) {
            final /* synthetic */ UserGuideActivity cgC;

            {
                this.cgC = this$0;
            }

            public void onPageSelected(int i) {
                if (UtilsFunction.empty(this.cgC.cgz)) {
                    this.cgC.cgA.setText("0/0");
                } else {
                    this.cgC.cgA.setText((i + 1) + "/" + this.cgC.cgz.size());
                }
            }
        });
        this.cgB = new GestureDetectorCompat(this, new SimpleOnGestureListener(this) {
            final /* synthetic */ UserGuideActivity cgC;

            {
                this.cgC = this$0;
            }

            public boolean onDown(MotionEvent e) {
                return true;
            }

            public boolean onSingleTapUp(MotionEvent e) {
                if (!this.cgC.VZ()) {
                    return super.onSingleTapUp(e);
                }
                this.cgC.mPager.setCurrentItem(this.cgC.mPager.getCurrentItem() + 1, true);
                return true;
            }
        });
        this.mPager.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ UserGuideActivity cgC;

            {
                this.cgC = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                return this.cgC.cgB.onTouchEvent(event);
            }
        });
        EventNotifyCenter.add(n.class, this.mCallback);
        Fy();
        Gy();
    }

    private void IE() {
        findViewById(g.search_header).setVisibility(8);
        this.aIU.setVisibility(8);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    private void Gy() {
        if (this.mType == 0) {
            b.TT();
        } else {
            b.TU();
        }
    }

    protected void EX() {
        super.EX();
        Fy();
        Gy();
    }

    private boolean VZ() {
        return this.mPager.getCurrentItem() + 1 < this.cgw.getCount();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
