package hlx.home.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.ah;
import com.simple.colorful.d;
import hlx.data.localstore.a;
import hlx.launch.game.c;

public class GameOptionActivity extends HTBaseActivity {
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ GameOptionActivity bPm;

        {
            this.bPm = this$0;
        }

        public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
            switch (checkbox.getId()) {
                case R.id.chkOpenFloatWindow:
                    c.Sg().dy(checked);
                    return;
                case R.id.rdobtnGameLaunchModeSecurity:
                    if (checked) {
                        c.Sg().mH(0);
                        this.bPm.bPi.setText(a.bKq[0]);
                        return;
                    }
                    return;
                case R.id.rdobtnGameLaunchModeLimited:
                    if (checked) {
                        c.Sg().mH(1);
                        this.bPm.bPi.setText(a.bKq[1]);
                        return;
                    }
                    return;
                case R.id.rdobtnGameLaunchModeUnlimited:
                    if (checked) {
                        c.Sg().mH(2);
                        this.bPm.bPi.setText(a.bKq[2]);
                        return;
                    }
                    return;
                case R.id.chkOpenHelpFairy:
                    ah.KZ().k(a.bLv, checked);
                    return;
                case R.id.chkOpenBlackPack:
                    c.Sg().dz(checked);
                    return;
                case R.id.chkEnableClearSystemMemory:
                    c.Sg().dA(checked);
                    return;
                default:
                    return;
            }
        }
    };
    private RelativeLayout bPc;
    private TextView bPd;
    private CheckBox bPe;
    private CheckBox bPf;
    private CheckBox bPg;
    private CheckBox bPh;
    private TextView bPi;
    private RadioButton bPj;
    private RadioButton bPk;
    private RadioButton bPl;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ GameOptionActivity bPm;

        {
            this.bPm = this$0;
        }

        @MessageHandler(message = 2825)
        public void onVersionUpdate() {
            if (this.bPm.bPd != null) {
                this.bPm.bPd.setText(c.Sg().getMCVersion(false));
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_game_option);
        EventNotifyCenter.add(n.class, this.mCallback);
        initView();
        EP();
        RK();
        RL();
    }

    private void initView() {
        this.bPc = (RelativeLayout) findViewById(R.id.rlyVersionSelect);
        this.bPd = (TextView) findViewById(R.id.tvVersionSelect);
        this.bPj = (RadioButton) findViewById(R.id.rdobtnGameLaunchModeSecurity);
        this.bPk = (RadioButton) findViewById(R.id.rdobtnGameLaunchModeLimited);
        this.bPl = (RadioButton) findViewById(R.id.rdobtnGameLaunchModeUnlimited);
        this.bPi = (TextView) findViewById(R.id.tvHomePageGameModeTips);
        this.bPe = (CheckBox) findViewById(R.id.chkOpenFloatWindow);
        this.bPf = (CheckBox) findViewById(R.id.chkOpenHelpFairy);
        this.bPg = (CheckBox) findViewById(R.id.chkOpenBlackPack);
        this.bPh = (CheckBox) findViewById(R.id.chkEnableClearSystemMemory);
    }

    private void EP() {
        if (d.isDayMode()) {
            this.aIU.setBackgroundColor(getResources().getColor(R.color.home_titlebar_bg));
            this.aIR.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.game_option_back_selector), null, null, null);
        } else {
            this.aIU.setBackgroundColor(getResources().getColor(R.color.home_titlebar_bg_night));
            this.aIR.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.game_option_back_night_selector), null, null, null);
        }
        ej("游戏选项");
        this.aIs.setVisibility(8);
    }

    private void RK() {
        this.bPc.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ GameOptionActivity bPm;

            {
                this.bPm = this$0;
            }

            public void onClick(View v) {
                hlx.ui.a.e(this.bPm, false);
                r.ck().K_umengEvent(hlx.data.tongji.a.bMe);
            }
        });
        this.bPj.setOnCheckedChangeListener(this.QH);
        this.bPk.setOnCheckedChangeListener(this.QH);
        this.bPl.setOnCheckedChangeListener(this.QH);
        this.bPe.setOnCheckedChangeListener(this.QH);
        this.bPf.setOnCheckedChangeListener(this.QH);
        this.bPg.setOnCheckedChangeListener(this.QH);
        this.bPh.setOnCheckedChangeListener(this.QH);
    }

    private void RL() {
        this.bPe.setChecked(c.Sg().Si());
        this.bPf.setChecked(ah.KZ().j(a.bLv, true));
        this.bPg.setChecked(c.Sg().Sj());
        this.bPh.setChecked(c.Sg().Sk());
        this.bPi.setText(a.bKq[c.Sg().Sl()]);
        switch (c.Sg().Sl()) {
            case 0:
                this.bPj.setChecked(true);
                return;
            case 1:
                this.bPk.setChecked(true);
                return;
            case 2:
                this.bPl.setChecked(true);
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        this.bPd.setText(c.Sg().getMCVersion(false));
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
