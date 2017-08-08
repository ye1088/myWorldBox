package com.MCWorld.mcfloat.InstanceZones;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.mcfloat.InstanceZones.config.c;
import com.MCWorld.mcinterface.h;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.service.j;
import com.MCWorld.utils.ah;
import com.MCWorld.widget.Constants;
import hlx.ui.heroslist.b;
import hlx.utils.d;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.tools.ant.util.DateUtils;

/* compiled from: InsZonesScoreBoard */
public class g extends Dialog {
    private static final boolean Xm = true;
    private int Wk = -1;
    private TextView XA;
    private int XB = 0;
    private int XC = 0;
    private int XD;
    private CallbackHandler XE = new 3(this);
    private a Xn = null;
    private g Xo;
    private LinearLayout Xp;
    private TextView Xq;
    private TextView Xr;
    private ImageView Xs;
    private LinearLayout Xt;
    private TextView Xu;
    private TextView Xv;
    private TextView Xw;
    private TextView Xx;
    private LinearLayout Xy;
    private LinearLayout Xz;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ g XF;

        {
            this.XF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivInsZonesScoreBoardConfirm:
                    this.XF.sK();
                    return;
                default:
                    return;
            }
        }
    };
    private Activity mContext = null;
    private long pM;

    /* compiled from: InsZonesScoreBoard */
    public interface a {
        void rd();

        void sM();
    }

    public g(Activity context, a callback, int insZoneMode) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.Xn = callback;
        this.Wk = insZoneMode;
        this.Xo = this;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floor_inszones_score_board);
        sJ();
    }

    private void sJ() {
        this.Xp = (LinearLayout) findViewById(R.id.llyInsZonesScoreBoard);
        this.Xq = (TextView) findViewById(R.id.tvChalengeResult);
        this.Xr = (TextView) findViewById(R.id.tvInsZonesScoreBoardPlayerName);
        this.Xt = (LinearLayout) findViewById(R.id.llyShowType01);
        this.Xu = (TextView) findViewById(R.id.tvScoreBoardBasicScore);
        this.Xv = (TextView) findViewById(R.id.tvScoreBoardLostBload);
        this.Xw = (TextView) findViewById(R.id.tvScoreBoardLostBloadScore);
        this.Xx = (TextView) findViewById(R.id.tvScoreBoardSumScore);
        this.Xy = (LinearLayout) findViewById(R.id.llyScoreBoardItem);
        this.Xz = (LinearLayout) findViewById(R.id.llyShowType02);
        this.XA = (TextView) findViewById(R.id.tvShowDetail);
        this.Xs = (ImageView) findViewById(R.id.ivInsZonesScoreBoardConfirm);
        this.Xs.setOnClickListener(this.mClickListener);
        findViewById(R.id.tvInsZoneScoreShare).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ g XF;

            {
                this.XF = this$0;
            }

            public void onClick(View v) {
                String _Desc;
                switch (this.XF.Wk) {
                    case 1:
                        _Desc = String.format(e.WU, new Object[]{e.WW, Integer.valueOf(this.XF.XB)});
                        break;
                    case 2:
                        String str = e.WU;
                        r1 = new Object[2];
                        r1[0] = String.format("%s-第%d关", new Object[]{e.WX, Integer.valueOf(this.XF.XC)});
                        r1[1] = Integer.valueOf(this.XF.XB);
                        _Desc = String.format(str, r1);
                        break;
                    case 3:
                        _Desc = String.format(e.WU, new Object[]{e.WY, Integer.valueOf(this.XF.XB)});
                        break;
                    case 4:
                        _Desc = String.format(e.WU, new Object[]{e.WZ, Integer.valueOf(this.XF.XB)});
                        break;
                    case 5:
                        _Desc = String.format(e.WV, new Object[]{this.XF.XB + hlx.data.localstore.a.bLu, e.Xa});
                        break;
                    case 6:
                        _Desc = String.format(e.WV, new Object[]{this.XF.XB + hlx.data.localstore.a.bLu, e.Xb});
                        break;
                    case 7:
                        _Desc = String.format(e.WV, new Object[]{this.XF.XB + hlx.data.localstore.a.bLu, e.Xc});
                        break;
                    default:
                        _Desc = "";
                        break;
                }
                j.m(this.XF.mContext).a(0, "葫芦侠我的世界", _Desc, "http://cdn.u1.huluxia.com/g1/M00/01/77/wKgBB1Z4yh2AAIDmAAAZ4_tYmNk647.jpg", Constants.bsz);
                r.ck().K_umengEvent(hlx.data.tongji.a.bOk);
            }
        });
    }

    public void dismiss() {
        if (this.Xn != null) {
            this.Xn.sM();
        }
        super.dismiss();
        EventNotifyCenter.remove(this.XE);
    }

    public void showDialog() {
        super.show();
        this.XD = 2;
        EventNotifyCenter.add(n.class, this.XE);
    }

    protected void sK() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.Xo.dismiss();
        }
        if (this.Xn != null) {
            this.Xn.rd();
        }
    }

    public void a(int insZoneMode, c curMissonPara, boolean isSuc, int curMissionIndex) {
        String _tmpResult;
        this.Xt.setVisibility(0);
        this.Xz.setVisibility(8);
        sL();
        String _tmpNick = com.MCWorld.data.j.ep().getNick();
        if (_tmpNick.length() <= 0) {
            _tmpNick = "未注册用户";
        }
        cl(_tmpNick);
        int _basicScore = curMissonPara.XS * curMissonPara.XR;
        int _sumScore = _basicScore;
        dR(_sumScore);
        for (com.MCWorld.mcfloat.InstanceZones.c.a _tmpCalculate : d.a(curMissonPara)) {
            int _tmpScore = _tmpCalculate.Wu;
            _sumScore += _tmpScore;
            r(_tmpCalculate.Ws + "×" + _tmpCalculate.Wt, String.valueOf(_tmpScore));
        }
        if (isSuc) {
            int remainderBlood = h.zp();
            if (remainderBlood > curMissonPara.XS) {
                remainderBlood = curMissonPara.XS;
            }
            B(curMissonPara.XS - remainderBlood, curMissonPara.XR);
            _sumScore -= (curMissonPara.XS - remainderBlood) * curMissonPara.XR;
        } else {
            _sumScore -= _basicScore;
            B(curMissonPara.XS, curMissonPara.XR);
        }
        dS(_sumScore);
        this.XC = curMissionIndex;
        this.XB = _sumScore;
        this.XD--;
        A(insZoneMode, _sumScore);
        switch (insZoneMode) {
            case 1:
                _tmpResult = isSuc ? "挑战成功！" : "挑战失败！";
                break;
            case 2:
                _tmpResult = isSuc ? String.format("训练%d-成功！", new Object[]{Integer.valueOf(curMissionIndex)}) : String.format("训练%d-失败！", new Object[]{Integer.valueOf(curMissionIndex)});
                break;
            case 3:
                _tmpResult = isSuc ? "闯关成功！" : "闯关失败！";
                break;
            case 4:
                _tmpResult = isSuc ? "挑战成功！" : "挑战失败！";
                break;
            case 5:
                _tmpResult = isSuc ? "逃亡成功！" : "逃亡失败！";
                break;
            case 6:
                _tmpResult = isSuc ? "成功！" : "失败！";
                break;
            case 7:
                _tmpResult = isSuc ? "成功！" : "失败！";
                break;
            default:
                _tmpResult = "统计";
                break;
        }
        a(isSuc, _tmpResult);
        showDialog();
    }

    public void a(int insZoneMode, boolean isSuc, int time) {
        String _tmpResult;
        this.Xz.setVisibility(0);
        this.Xt.setVisibility(8);
        String _tmpNick = com.MCWorld.data.j.ep().getNick();
        if (_tmpNick.length() <= 0) {
            _tmpNick = "未注册用户";
        }
        cl(_tmpNick);
        this.XB = time;
        this.XD--;
        A(insZoneMode, time);
        switch (insZoneMode) {
            case 5:
                _tmpResult = isSuc ? "逃亡成功！" : "逃亡失败！";
                break;
            case 6:
                _tmpResult = isSuc ? "成功！" : "失败！";
                break;
            case 7:
                _tmpResult = isSuc ? "成功！" : "失败！";
                break;
            default:
                _tmpResult = "统计";
                break;
        }
        this.XA.setText(isSuc ? "恭喜您！耗时：" + d.nR(time) : "很遗憾！");
        a(isSuc, _tmpResult);
        showDialog();
    }

    private void A(int insZoneMode, int score) {
        long _userId = com.MCWorld.data.j.ep().getUserid();
        this.pM = _userId;
        if (_userId != 0) {
            String _curDate = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date());
            int _todatyMaxScore = 0;
            if (dQ(this.Wk) == 0) {
                _todatyMaxScore = -1;
            } else if (dQ(this.Wk) == 1) {
                _todatyMaxScore = 2000000000;
            }
            com.MCWorld.data.inszone.a _tmpRecore = ah.KZ().lr(insZoneMode);
            if (_tmpRecore != null && _userId == _tmpRecore.pM && _curDate.equals(_tmpRecore.pN)) {
                _todatyMaxScore = _tmpRecore.pO;
            }
            if (dQ(this.Wk) == 0) {
                if (score > _todatyMaxScore) {
                    h(insZoneMode, score, dQ(this.Wk));
                }
            } else if (dQ(this.Wk) == 1 && score < _todatyMaxScore) {
                h(insZoneMode, score, dQ(this.Wk));
            }
        }
    }

    private int dQ(int insZoneMode) {
        switch (insZoneMode) {
            case 1:
            case 2:
            case 3:
            case 4:
                return 0;
            case 5:
            case 6:
            case 7:
                return 1;
            default:
                return 0;
        }
    }

    private void h(int insZoneMode, int score, int orderType) {
        switch (insZoneMode) {
            case 1:
                b.TZ().br(score, orderType);
                return;
            case 2:
                b.TZ().bq(score, orderType);
                return;
            case 3:
                b.TZ().bp(score, orderType);
                return;
            case 4:
                b.TZ().bo(score, orderType);
                return;
            case 5:
                b.TZ().bs(score, orderType);
                return;
            case 6:
                b.TZ().bt(score, orderType);
                return;
            case 7:
                b.TZ().bu(score, orderType);
                return;
            default:
                return;
        }
    }

    public void sL() {
        this.Xy.removeAllViews();
    }

    public void a(boolean isSuc, String result) {
        if (isSuc) {
            this.Xp.setBackgroundResource(R.drawable.bg_float_ins_zones_dlg_success);
            this.Xq.setText(result);
            this.Xq.setTextColor(SupportMenu.CATEGORY_MASK);
            this.Xq.setTextSize(15.0f);
            return;
        }
        this.Xp.setBackgroundResource(R.drawable.bg_float_ins_zones_dlg_failure);
        this.Xq.setText(result);
        this.Xq.setTextColor(InputDeviceCompat.SOURCE_ANY);
        this.Xq.setTextSize(15.0f);
    }

    public void cl(String playerName) {
        if (playerName != null) {
            this.Xr.setVisibility(0);
            this.Xr.setText(playerName);
            return;
        }
        this.Xr.setVisibility(8);
    }

    public void dR(int basicScore) {
        this.Xu.setText(String.valueOf(basicScore));
    }

    public void r(String name, String score) {
        RelativeLayout _itemLayout = new RelativeLayout(this.mContext);
        LayoutParams logoParams = new LayoutParams(-1, -2);
        TextView _leftText = new TextView(this.mContext);
        _leftText.setText(name);
        _leftText.setTextColor(-13355980);
        _leftText.setLayoutParams(new LayoutParams(-2, -2));
        _itemLayout.addView(_leftText);
        TextView _rightText = new TextView(this.mContext);
        _rightText.setText(score);
        _rightText.setTextColor(-13355980);
        LayoutParams _rightParams = new LayoutParams(-2, -2);
        _rightParams.addRule(11);
        _rightText.setLayoutParams(_rightParams);
        _itemLayout.addView(_rightText);
        this.Xy.addView(_itemLayout, logoParams);
    }

    public void B(int deductBlood, int scorePerBlood) {
        this.Xv.setText(String.format("失血×%d", new Object[]{Integer.valueOf(deductBlood)}));
        this.Xw.setText(String.format("-%d", new Object[]{Integer.valueOf(deductBlood * scorePerBlood)}));
    }

    public void dS(int sumScore) {
        this.Xx.setText(String.format("总分：%d", new Object[]{Integer.valueOf(sumScore)}));
    }
}
