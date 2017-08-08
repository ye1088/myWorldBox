package com.MCWorld.ui.profile;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.j;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.ui.home.ProfileMeFragment;
import com.MCWorld.widget.g;
import com.simple.colorful.a.a;

public class ProfileActivity extends HTBaseThemeActivity {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private static final String TAG = "ProfileActivity";
    public static final String bfF = "USER_BASE_INFO";
    private boolean aKH = false;
    private UserBaseInfo aKy;
    Fragment mFragment = null;
    private long pM = 0;

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_blank);
        g.x(this);
        this.pM = getIntent().getLongExtra(EXTRA_USER_ID, 0);
        this.aKy = (UserBaseInfo) getIntent().getParcelableExtra(bfF);
        if (this.pM != j.ep().getUserid()) {
            z = true;
        } else {
            z = false;
        }
        this.aKH = z;
        int statusBarHeight = 0;
        if (VERSION.SDK_INT >= 19) {
            statusBarHeight = Jj();
        }
        HLog.info(TAG, "statusBarHeight %d", Integer.valueOf(statusBarHeight));
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(16908290) == null) {
            HLog.verbose(TAG, "content is null", new Object[0]);
            this.mFragment = this.aKH ? ProfileOtherFragment.a(this.pM, this.aKy, statusBarHeight) : ProfileMeFragment.c(false, statusBarHeight);
            fm.beginTransaction().add(16908290, this.mFragment).commit();
            return;
        }
        HLog.verbose(TAG, "content not null", new Object[0]);
        this.mFragment = fm.findFragmentById(b.g.content);
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault);
    }

    public int Jj() {
        int sbar = 38;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            sbar = getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
