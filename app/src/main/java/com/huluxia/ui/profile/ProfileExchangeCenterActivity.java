package com.huluxia.ui.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.j;
import com.huluxia.data.profile.ProductList;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.List;

public class ProfileExchangeCenterActivity extends HTBaseActivity {
    public static final String bfT = "KEY_CREDITS_LIST";
    public static final String bfU = "KEY_INTEGRAL_LIST";
    protected BroadcastReceiver aRV;
    private PagerSlidingTabStrip baN;
    private ViewPager bfV;
    private ArrayList<View> bfW;
    private GiftLayout bfX = null;
    private GiftLayout bfY = null;
    private View bfZ = null;
    private int bga = 0;
    private TextView bgb;
    private TextView bgc;
    private ProductList bgd;
    private ProductList bge;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ProfileExchangeCenterActivity bgf;

        {
            this.bgf = this$0;
        }

        @MessageHandler(message = 550)
        public void onRecvProfileInfo(boolean succ, ProfileInfo info, long userId) {
            if (j.ep().ey() && userId == j.ep().getUserid()) {
                this.bgf.cs(false);
                if (succ && info != null) {
                    this.bgf.bgb.setText(String.valueOf(info.getCredits()));
                    this.bgf.bgc.setText(String.valueOf(info.getIntegral()));
                }
            }
        }

        @MessageHandler(message = 551)
        public void onRecvProductList(boolean succ, ProductList info, int typeId) {
            this.bgf.cs(false);
            if (!succ || info == null) {
                if (typeId == 0) {
                    if (this.bgf.bfX.getCurrentPage() == 0) {
                        this.bgf.bfX.FB();
                        return;
                    }
                } else if (this.bgf.bfY.getCurrentPage() == 0) {
                    this.bgf.bfY.FB();
                    return;
                }
                t.n(this.bgf, "加载失败");
            } else if (typeId == 0) {
                this.bgf.a(info);
                this.bgf.bfX.FC();
            } else {
                this.bgf.b(info);
                this.bgf.bfY.FC();
            }
        }
    };

    private class ViewPagerAdapter extends PagerAdapter {
        public List<View> aOg;
        private final String[] baP = new String[]{"葫芦", "贡献值", "规则"};
        final /* synthetic */ ProfileExchangeCenterActivity bgf;
        final String bgg = "葫芦";

        public ViewPagerAdapter(ProfileExchangeCenterActivity profileExchangeCenterActivity, List<View> mListViews) {
            this.bgf = profileExchangeCenterActivity;
            this.aOg = mListViews;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) this.aOg.get(arg1));
        }

        public int getCount() {
            return this.aOg.size();
        }

        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView((View) this.aOg.get(arg1), 0);
            return this.aOg.get(arg1);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public CharSequence getPageTitle(int position) {
            return this.baP[position];
        }
    }

    private class a extends BroadcastReceiver {
        final /* synthetic */ ProfileExchangeCenterActivity bgf;

        private a(ProfileExchangeCenterActivity profileExchangeCenterActivity) {
            this.bgf = profileExchangeCenterActivity;
        }

        public void onReceive(Context context, Intent intent) {
            this.bgf.Jm();
            this.bgf.Gy();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_exchange_center);
        this.bga = getIntent().getIntExtra("curIdx", 0);
        if (savedInstanceState != null) {
            this.bgd = (ProductList) savedInstanceState.getSerializable(bfT);
            this.bge = (ProductList) savedInstanceState.getSerializable(bfU);
        }
        Jl();
        Iu();
        this.bgb = (TextView) findViewById(g.tv_hulu);
        this.bgc = (TextView) findViewById(g.tv_integral);
        this.aRV = new a();
        com.huluxia.service.i.c(this.aRV);
        EventNotifyCenter.add(h.class, this.mCallback);
        Gy();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        if (this.aRV != null) {
            com.huluxia.service.i.unregisterReceiver(this.aRV);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(bfT, this.bgd);
        outState.putSerializable(bfU, this.bge);
    }

    private void Jl() {
        this.aIs.setVisibility(8);
        this.aIR.setVisibility(0);
        ((ImageButton) findViewById(g.sys_header_right_img)).setVisibility(8);
        Button button = (Button) findViewById(g.sys_header_right);
        button.setText("兑换记录");
        button.setVisibility(0);
        button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileExchangeCenterActivity bgf;

            {
                this.bgf = this$0;
            }

            public void onClick(View v) {
                t.ap(this.bgf);
            }
        });
        ej("兑换中心");
    }

    private void Gy() {
        Jm();
        if (this.bgd == null) {
            com.huluxia.module.profile.g.Eb().jT(0);
            this.bfX.Fy();
        } else {
            a(this.bgd);
            this.bfX.FC();
        }
        if (this.bge == null) {
            com.huluxia.module.profile.g.Eb().jT(1);
            this.bfY.Fy();
            return;
        }
        b(this.bge);
        this.bfY.FC();
    }

    private void Iu() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        this.bfV = (ViewPager) findViewById(g.vpListView);
        this.baN = (PagerSlidingTabStrip) findViewById(g.homeTabs);
        this.baN.setTextSize(at.dipToPx(this, 15));
        this.baN.setIndicatorTextColor(true);
        this.baN.setTextColorResource(d.p(this, 16842808));
        this.baN.setIndicatorColorResource(d.p(this, c.textColorGreen));
        this.baN.setDividerColor(getResources().getColor(b.d.transparent));
        this.baN.setShouldExpand(true);
        this.bfW = new ArrayList();
        this.bfX = new GiftLayout(this, 0);
        this.bfY = new GiftLayout(this, 1);
        this.bfX.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ ProfileExchangeCenterActivity bgf;

            {
                this.bgf = this$0;
            }

            public void onRetryClick(View view) {
                com.huluxia.module.profile.g.Eb().jT(0);
                this.bgf.bfX.Fy();
            }
        });
        this.bfY.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ ProfileExchangeCenterActivity bgf;

            {
                this.bgf = this$0;
            }

            public void onRetryClick(View view) {
                com.huluxia.module.profile.g.Eb().jT(1);
                this.bgf.bfY.Fy();
            }
        });
        this.bfW.add(this.bfX);
        this.bfW.add(this.bfY);
        this.bfZ = mInflater.inflate(i.include_video_detail_intro, null);
        this.bfW.add(this.bfZ);
        this.bfV.setAdapter(new ViewPagerAdapter(this, this.bfW));
        this.baN.setViewPager(this.bfV);
        this.bfV.setCurrentItem(this.bga);
    }

    private void a(ProductList product) {
        ((TextView) this.bfZ.findViewById(g.vdetail_info)).setText(product.getRuleText());
        this.bfX.setGift(product);
        if (product.getUser() != null) {
            this.bfX.setUser(product);
        }
        this.bfX.Ff();
    }

    private void b(ProductList product) {
        this.bfY.setGift(product);
        if (product.getUser() != null) {
            this.bfY.setUser(product);
        }
        this.bfY.Ff();
    }

    private void Jm() {
        if (j.ep().ey()) {
            com.huluxia.module.profile.g.Eb().aR(j.ep().getUserid());
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault).aY(g.ll_my_credit, c.backgroundDim).a(this.bgb, c.textColorGreen).ba(g.my_credit, c.textColorCredit).a(this.bgc, c.textColorCredit).a(this.bgb, c.drawableHulu, 2).a(this.bgc, c.drawableJifen, 2).a(this.bfX).a(this.bfY);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.baN != null) {
            this.baN.FG();
        }
    }
}
