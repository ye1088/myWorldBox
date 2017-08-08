package com.MCWorld.ui.bbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.ui.bbs.TopicListFragment.c;
import com.MCWorld.ui.bbs.TopicListMenuFragment.b;
import com.MCWorld.utils.at;
import com.MCWorld.widget.menudrawer.MenuDrawer;
import com.MCWorld.widget.menudrawer.MenuDrawer.Type;
import com.MCWorld.widget.menudrawer.Position;
import com.simple.colorful.a.a;
import java.util.List;

public class TopicListDrawerActivity extends HTBaseThemeActivity implements c, b {
    private TopicCategory aJO;
    private MenuDrawer aPB;
    private TopicListFragment aPC;
    private TopicListMenuFragment aPD;
    private Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        if (savedInstanceState == null) {
            this.aJO = (TopicCategory) getIntent().getParcelableExtra("category");
        } else {
            this.aJO = (TopicCategory) savedInstanceState.getParcelable("category");
        }
        this.aPB = MenuDrawer.a((Activity) this, Type.OVERLAY, Position.RIGHT, 1);
        this.aPB.setMenuView(i.layout_place_holder);
        this.aPB.setMenuSize((at.getScreenPixWidth(this) * 6) / 11);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.aPC = TopicListFragment.a(this.aJO);
        ft.replace(this.aPB.getContentContainer().getId(), this.aPC);
        this.aPD = TopicListMenuFragment.b(this.aJO);
        ft.replace(g.holder_container, this.aPD);
        ft.commitAllowingStateLoss();
        this.aPB.setDropShadowEnabled(false);
    }

    public void c(List<Long> ids, List<String> titles) {
        this.aPD.d(ids, titles);
    }

    public void cz(boolean isSubscribe) {
        this.aPD.cC(isSubscribe);
    }

    public void cA(boolean isSubscribe) {
        this.aPC.cC(isSubscribe);
    }

    public void Hb() {
        this.aPB.Pt();
    }

    public void bk(long tagId) {
        this.aPB.Pt();
        this.aPC.bl(tagId);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("category", this.aJO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.aPC.b(resultCode, resultCode, data);
    }

    public void onBackPressed() {
        int drawerState = this.aPB.getDrawerState();
        if (drawerState == 8 || drawerState == 4) {
            this.aPB.Pv();
        } else {
            super.onBackPressed();
        }
    }

    protected void onStop() {
        super.onStop();
        int drawerState = this.aPB.getDrawerState();
        if (drawerState == 8 || drawerState == 4) {
            this.aPB.Pv();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void a(a builder) {
        super.a(builder);
    }
}
