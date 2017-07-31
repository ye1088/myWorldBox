package com.huluxia.ui.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.module.h;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;

public class MessageHistoryActivity extends HTBaseActivity implements OnClickListener {
    private BroadcastReceiver aIn;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ MessageHistoryActivity bfE;

        {
            this.bfE = this$0;
        }

        @MessageHandler(message = 770)
        public void onRecvClearMsgTip() {
            if (this.bfE.bfy != null) {
                this.bfE.bfy.setVisibility(8);
            }
        }
    };
    private MessageHistoryActivity bfA;
    private UserMsgFragment bfB = UserMsgFragment.Kh();
    private SysMsgFragment bfC = SysMsgFragment.JY();
    OnPageChangeListener bfD = new OnPageChangeListener(this) {
        final /* synthetic */ MessageHistoryActivity bfE;

        {
            this.bfE = this$0;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            if (position == 0) {
                this.bfE.bft = this.bfE.bfs;
                if (this.bfE.bfr != null) {
                    this.bfE.bfr.updateTabTitle(0, this.bfE.bft);
                }
                this.bfE.bfB.Ka();
            } else if (position == 1) {
                this.bfE.bfw = this.bfE.bfv;
                if (this.bfE.bfr != null) {
                    this.bfE.bfr.updateTabTitle(1, this.bfE.bfw);
                }
                this.bfE.bfC.Ka();
            }
        }
    };
    private PagerSlidingTabStrip bfr;
    private String bfs = "用户消息";
    private String bft;
    private int bfu = 0;
    private String bfv = "系统消息";
    private String bfw;
    private int bfx = 1;
    private View bfy;
    private TextView bfz;
    private MsgCounts ft = null;
    private ViewPager mPager;

    private class a extends BroadcastReceiver {
        final /* synthetic */ MessageHistoryActivity bfE;

        private a(MessageHistoryActivity messageHistoryActivity) {
            this.bfE = messageHistoryActivity;
        }

        public void onReceive(Context context, Intent intent) {
            String msg = "";
            MsgCounts msgCounts = HTApplication.bM();
            if ((msgCounts == null ? 0 : msgCounts.getAll()) > 0) {
                this.bfE.bfy.setVisibility(0);
                if (msgCounts.getReply() > 0) {
                    this.bfE.bfz.setText(this.bfE.bfA.getString(m.msg_banner_user, new Object[]{Long.valueOf(msgCounts.getReply())}));
                    return;
                } else {
                    this.bfE.bfz.setText(this.bfE.bfA.getString(m.msg_banner_sys, new Object[]{Long.valueOf(msgCounts.getSys())}));
                    return;
                }
            }
            this.bfE.bfy.setVisibility(8);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bfA = this;
        setContentView(i.activity_profile_exchange);
        this.ft = (MsgCounts) getIntent().getSerializableExtra("msgCounts");
        if (this.ft == null) {
            this.bfw = this.bfv;
            this.bft = this.bfs;
        } else {
            if (this.ft.getSys() > 0) {
                this.bfw = this.bfv + "(" + String.valueOf(this.ft.getSys() + ")");
            } else {
                this.bfw = this.bfv;
            }
            if (this.ft.getReply() > 0) {
                this.bft = this.bfs + "(" + String.valueOf(this.ft.getReply() + ")");
            } else {
                this.bft = this.bfs;
            }
        }
        setTitle("我的消息");
        this.aIs.setVisibility(8);
        this.bfy = findViewById(g.rly_msg_banner);
        this.bfz = (TextView) findViewById(g.msg_banner);
        this.bfz.setOnClickListener(this);
        if (j.ep().ey()) {
            Iu();
            r.ck().dt();
            this.aIn = new a();
            com.huluxia.service.i.e(this.aIn);
            EventNotifyCenter.add(h.class, this.aky);
            return;
        }
        t.b((Activity) this, 528, 529);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 528 && resultCode == 529 && data != null && data.getBooleanExtra("ok", false)) {
            Iu();
        }
    }

    protected void onStop() {
        super.onStop();
        Ji();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.aIn != null) {
            com.huluxia.service.i.unregisterReceiver(this.aIn);
            this.aIn = null;
        }
        EventNotifyCenter.remove(this.aky);
    }

    private void Iu() {
        cp(false);
        this.aIs.setOnClickListener(null);
        this.bfr = (PagerSlidingTabStrip) findViewById(g.homeTabs);
        this.bfr.setTextSize(at.dipToPx(this, 15));
        this.bfr.setIndicatorTextColor(true);
        this.bfr.setDividerColor(getResources().getColor(d.transparent));
        this.bfr.setTextColorResource(com.simple.colorful.d.p(this, 16842808));
        this.bfr.setIndicatorColorResource(com.simple.colorful.d.p(this, c.textColorGreen));
        this.bfr.setShouldExpand(true);
        Jh();
        if (this.ft == null || this.ft.getAll() == 0) {
            this.mPager.setCurrentItem(this.bfu);
            this.bfD.onPageSelected(this.bfu);
        } else if (this.ft.getReply() > 0) {
            this.mPager.setCurrentItem(this.bfu);
            this.bfD.onPageSelected(this.bfu);
        } else if (this.ft.getSys() > 0) {
            this.mPager.setCurrentItem(this.bfx);
            this.bfD.onPageSelected(this.bfx);
        } else {
            this.mPager.setCurrentItem(this.bfu);
            this.bfD.onPageSelected(this.bfu);
        }
    }

    public void eJ(String msg) {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MessageHistoryActivity bfE;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.bfE.bfA.finish();
                t.an(this.bfE.bfA);
            }
        });
    }

    private void Jh() {
        this.mPager = (ViewPager) findViewById(g.vpListView);
        this.mPager.setAdapter(new PagerSelectedAdapter(this, getSupportFragmentManager()) {
            final /* synthetic */ MessageHistoryActivity bfE;

            public PagerFragment getItem(int position) {
                switch (position) {
                    case 0:
                        return this.bfE.bfB;
                    case 1:
                        return this.bfE.bfC;
                    default:
                        return null;
                }
            }

            public int getCount() {
                return 2;
            }

            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return this.bfE.bft;
                    case 1:
                        return this.bfE.bfw;
                    default:
                        return super.getPageTitle(position);
                }
            }
        });
        this.mPager.setOffscreenPageLimit(2);
        this.bfr.setOnPageChangeListener(this.bfD);
        this.bfr.setViewPager(this.mPager);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.sys_header_right) {
            t.av(this.bfA);
        } else if (id == g.fl_msg) {
            refresh();
        } else if (id == g.msg_banner) {
            refresh();
            this.bfy.setVisibility(8);
            if (this.mPager.getCurrentItem() == this.bfu && this.bfB != null) {
                this.bfB.JZ();
            } else if (this.mPager.getCurrentItem() == this.bfx && this.bfC != null) {
                this.bfC.JZ();
            }
        }
    }

    private void refresh() {
        MsgCounts msgCounts = HTApplication.bM();
        HTApplication.a(null);
        com.huluxia.service.h.EC().ED();
        com.huluxia.service.i.EI();
        if (msgCounts != null && msgCounts.getReply() > 0) {
            this.mPager.setCurrentItem(this.bfu);
            this.bfB.reload();
        } else if (msgCounts != null && msgCounts.getSys() > 0) {
            this.mPager.setCurrentItem(this.bfx);
            this.bfC.reload();
        } else if (this.mPager.getCurrentItem() == this.bfu) {
            this.bfB.reload();
        } else if (this.mPager.getCurrentItem() == this.bfx) {
            this.bfC.reload();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.bfB != null) {
            this.bfB.a(builder);
        }
        if (this.bfC != null) {
            this.bfC.a(builder);
        }
        builder.aY(16908290, c.backgroundDefault).aY(g.rly_msg_banner, c.backgroundDim).aY(g.msg_banner, c.backgroundMsgBanner).ba(g.msg_banner, 16842809);
    }

    protected void kj(int themeId) {
        if (this.bfB != null) {
            this.bfB.kj(themeId);
        }
        if (this.bfC != null) {
            this.bfC.kj(themeId);
        }
        if (this.bfr != null) {
            this.bfr.FG();
        }
        super.kj(themeId);
    }

    private void Ji() {
        HTApplication.a(null);
        HTApplication.bC();
        com.huluxia.service.h.EC().ED();
        com.huluxia.service.i.EI();
    }
}
