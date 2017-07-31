package com.huluxia.ui.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.profile.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseThemeActivity;
import java.util.ArrayList;

public class SpaceStyleDetailActivity extends HTBaseThemeActivity {
    public static final String bhC = "EXTRA_FROM_HOME";
    public static final String bhG = "EXTRA_SPACE_STYLE_POSITION";
    public static final String bhH = "EXTRA_SPACE_STYLES";
    private TextView aIg;
    private boolean aLf;
    private SelectedViewPager bhD;
    private ArrayList<h> bhI;
    private b bhJ;
    private a bhK;
    private int mPosition;

    private class a extends BroadcastReceiver {
        final /* synthetic */ SpaceStyleDetailActivity bhL;

        private a(SpaceStyleDetailActivity spaceStyleDetailActivity) {
            this.bhL = spaceStyleDetailActivity;
        }

        public void onReceive(Context context, Intent intent) {
            if (this.bhL.aIg != null) {
                this.bhL.aIg.setVisibility(4);
            }
        }
    }

    private class b extends BroadcastReceiver {
        final /* synthetic */ SpaceStyleDetailActivity bhL;

        private b(SpaceStyleDetailActivity spaceStyleDetailActivity) {
            this.bhL = spaceStyleDetailActivity;
        }

        public void onReceive(Context context, Intent intent) {
            this.bhL.Fr();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_space_style_detail);
        if (savedInstanceState != null) {
            this.bhI = savedInstanceState.getParcelableArrayList(bhH);
            this.aLf = savedInstanceState.getBoolean("EXTRA_FROM_HOME", true);
            this.mPosition = savedInstanceState.getInt(bhG, 0);
        } else {
            this.bhI = getIntent().getParcelableArrayListExtra(bhH);
            this.aLf = getIntent().getBooleanExtra("EXTRA_FROM_HOME", true);
            this.mPosition = getIntent().getIntExtra(bhG, 0);
        }
        EP();
        this.bhD = (SelectedViewPager) findViewById(g.viewpager);
        this.bhD.setOffscreenPageLimit(3);
        this.bhD.setOnPageChangeListener(new SimpleOnPageChangeListener());
        this.bhD.setAdapter(new PagerSelectedAdapter<SpaceStyleDetailFragment>(this, getSupportFragmentManager()) {
            final /* synthetic */ SpaceStyleDetailActivity bhL;

            public /* synthetic */ PagerFragment getItem(int i) {
                return kY(i);
            }

            public int getCount() {
                return this.bhL.bhI.size();
            }

            public SpaceStyleDetailFragment kY(int position) {
                return SpaceStyleDetailFragment.a(this.bhL.aLf, (h) this.bhL.bhI.get(position));
            }
        });
        this.bhD.setCurrentItem(this.mPosition);
    }

    private void EP() {
        this.bhJ = new b();
        this.bhK = new a();
        com.huluxia.service.i.e(this.bhJ);
        com.huluxia.service.i.f(this.bhK);
        TitleBar titleBar = (TitleBar) findViewById(g.title_bar);
        titleBar.setLeftLayout(i.include_topiclist_titlebar_left);
        titleBar.setRightLayout(i.include_topiclist_titlebar_right);
        ((Button) titleBar.findViewById(g.sys_header_back)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SpaceStyleDetailActivity bhL;

            {
                this.bhL = this$0;
            }

            public void onClick(View v) {
                this.bhL.finish();
            }
        });
        this.aIg = (TextView) titleBar.findViewById(g.tv_msg);
        ImageButton imgMsg = (ImageButton) titleBar.findViewById(g.img_msg);
        imgMsg.setVisibility(0);
        imgMsg.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SpaceStyleDetailActivity bhL;

            {
                this.bhL = this$0;
            }

            public void onClick(View v) {
                t.a(this.bhL, HTApplication.bM());
            }
        });
        titleBar.findViewById(g.sys_header_flright_img).setVisibility(8);
        titleBar.findViewById(g.ll_topic_left).setVisibility(8);
        Fr();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bhJ != null) {
            com.huluxia.service.i.unregisterReceiver(this.bhJ);
            this.bhJ = null;
        }
        if (this.bhK != null) {
            com.huluxia.service.i.unregisterReceiver(this.bhK);
            this.bhK = null;
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(bhH, this.bhI);
        outState.putBoolean("EXTRA_FROM_HOME", this.aLf);
        outState.putInt(bhG, this.mPosition);
    }

    protected void Fr() {
        if (this.aIg != null) {
            MsgCounts msgCounts = HTApplication.bM();
            long allCount = msgCounts == null ? 0 : msgCounts.getAll();
            if (allCount > 0) {
                this.aIg.setVisibility(0);
                if (allCount > 99) {
                    this.aIg.setText("99+");
                    return;
                } else {
                    this.aIg.setText(String.valueOf(msgCounts.getAll()));
                    return;
                }
            }
            this.aIg.setVisibility(8);
        }
    }
}
