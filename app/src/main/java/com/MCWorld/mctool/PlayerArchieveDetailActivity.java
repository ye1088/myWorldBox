package com.MCWorld.mctool;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.j;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.framework.base.image.Config.NetFormat;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.l;
import com.MCWorld.mctool.structure.a;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.as;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.utils.y;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.d;

public class PlayerArchieveDetailActivity extends HTBaseActivity {
    private int apE;
    ProfileInfo apF;
    private int mValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_player_archieve_detail);
        Dj();
        a(this.apE, this.mValue, this.apF);
    }

    private void Dj() {
        this.apE = getIntent().getIntExtra("detailIndex", 0);
        this.mValue = getIntent().getIntExtra("value", 0);
        this.apF = (ProfileInfo) getIntent().getParcelableExtra("ext_info");
    }

    private void a(int detailIndex, int value, ProfileInfo info) {
        Drawable drawable;
        String _ArchieveName;
        String _strPlayerArchieve;
        String _strArchieveDetail;
        EmojiTextView nick = (EmojiTextView) findViewById(g.nick);
        nick.setText(aw.go(info.getNick()));
        nick.setTextColor(as.g(this, info.getRole(), info.getGender()));
        ((PaintView) findViewById(g.avatar)).setUri(UtilUri.getUriOrNull(info.getAvatar()), NetFormat.FORMAT_80).radius(5.0f).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        TextView tv_score = (TextView) findViewById(g.tvArchieveValue);
        a(info);
        b(info);
        switch (detailIndex) {
            case 0:
                drawable = getResources().getDrawable(j.ico_player_achieve_continue_logon);
                _ArchieveName = "登陆";
                int _days = 0;
                a _tmp = e.Dk().Dl();
                if (_tmp != null) {
                    _days = _tmp.apJ;
                }
                _strPlayerArchieve = String.format(d.aps, new Object[]{Integer.valueOf(_days)});
                _strArchieveDetail = d.a;
                break;
            case 1:
                drawable = getResources().getDrawable(j.ico_player_achieve_game_time);
                _ArchieveName = "游戏";
                _strPlayerArchieve = String.format(d.apt, new Object[]{az.lw(e.Dk().wx())});
                _strArchieveDetail = d.b;
                break;
            case 2:
                drawable = getResources().getDrawable(j.ico_player_achieve_ins_zone);
                _ArchieveName = "副本";
                _strPlayerArchieve = String.format(d.apu, new Object[]{Integer.valueOf(e.Dk().Dm())});
                _strArchieveDetail = d.c;
                break;
            case 3:
                drawable = getResources().getDrawable(j.ico_player_achieve_story);
                _ArchieveName = "故事";
                _strPlayerArchieve = String.format(d.apv, new Object[]{az.lw(e.Dk().Dn())});
                _strArchieveDetail = d.d;
                break;
            case 4:
                drawable = getResources().getDrawable(j.ico_player_achieve_dig_block);
                _ArchieveName = "挖掘";
                _strPlayerArchieve = String.format(d.apw, new Object[]{Integer.valueOf(e.Dk().Dq())});
                _strArchieveDetail = d.e;
                break;
            case 5:
                drawable = getResources().getDrawable(j.ico_player_achieve_enchat);
                _ArchieveName = "附魔";
                _strPlayerArchieve = String.format(d.apx, new Object[]{Integer.valueOf(e.Dk().Dr())});
                _strArchieveDetail = d.f;
                break;
            case 6:
                drawable = getResources().getDrawable(j.ico_player_achieve_kill_monster);
                _ArchieveName = "击杀";
                _strPlayerArchieve = String.format(d.apy, new Object[]{Integer.valueOf(e.Dk().Do())});
                _strArchieveDetail = d.g;
                break;
            case 7:
                drawable = getResources().getDrawable(j.ico_player_achieve_player_die_count);
                _ArchieveName = "死亡";
                _strPlayerArchieve = String.format(d.apz, new Object[]{Integer.valueOf(e.Dk().Dp())});
                _strArchieveDetail = d.h;
                break;
            case 8:
                drawable = getResources().getDrawable(j.ico_player_achieve_map_download);
                _ArchieveName = "地图";
                _strPlayerArchieve = String.format(d.apA, new Object[]{Integer.valueOf(e.Dk().Ds())});
                _strArchieveDetail = d.i;
                break;
            case 9:
                drawable = getResources().getDrawable(j.ico_player_achieve_js_download);
                _ArchieveName = "插件";
                _strPlayerArchieve = String.format(d.apB, new Object[]{Integer.valueOf(e.Dk().Dt())});
                _strArchieveDetail = d.j;
                break;
            case 10:
                drawable = getResources().getDrawable(j.ico_player_achieve_skin_download);
                _ArchieveName = "皮肤";
                _strPlayerArchieve = String.format(d.apC, new Object[]{Integer.valueOf(e.Dk().Du())});
                _strArchieveDetail = d.k;
                break;
            case 11:
                drawable = getResources().getDrawable(j.ico_player_achieve_wood_download);
                _ArchieveName = "材质";
                _strPlayerArchieve = String.format(d.apD, new Object[]{Integer.valueOf(e.Dk().Dv())});
                _strArchieveDetail = d.l;
                break;
            default:
                drawable = getResources().getDrawable(f.ic_space_jifen);
                _ArchieveName = "贡献值";
                _strPlayerArchieve = "";
                _strArchieveDetail = "";
                break;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_score.setCompoundDrawables(null, null, drawable, null);
        tv_score.setText(String.valueOf(value));
        ej(_ArchieveName);
        TextView tvArchieveDetail = (TextView) findViewById(g.tvArchieveDetail);
        ((TextView) findViewById(g.tvPlayerArchieve)).setText(_strPlayerArchieve);
        tvArchieveDetail.setText(_strArchieveDetail);
    }

    private void a(ProfileInfo user) {
        ((TextView) findViewById(g.user_age)).setText(Integer.toString(user.getAge()));
        View genderBg = findViewById(g.rl_sex_age);
        ImageView genderMark = (ImageView) findViewById(g.userlist_gender_mark);
        if (user.getGender() == 1) {
            genderBg.setBackgroundResource(f.bg_gender_female);
            genderMark.setImageResource(f.user_female);
            return;
        }
        genderBg.setBackgroundResource(f.bg_gender_male);
        genderMark.setImageResource(f.user_male);
    }

    @TargetApi(16)
    private void b(ProfileInfo user) {
        View honor_flag = findViewById(g.honor_flag);
        if (user.getIdentityColor() == 0 || UtilsFunction.empty(user.getIdentityTitle())) {
            honor_flag.setVisibility(8);
            return;
        }
        TextView tv_honor = (TextView) findViewById(g.tv_honor);
        tv_honor.setText(user.getIdentityTitle());
        tv_honor.setBackgroundDrawable(y.e(this, (int) user.getIdentityColor(), 2));
        honor_flag.setVisibility(0);
    }
}
