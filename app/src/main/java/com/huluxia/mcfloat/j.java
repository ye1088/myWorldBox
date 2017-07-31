package com.huluxia.mcfloat;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mcfloat.p.a;
import com.huluxia.mcinterface.h;
import hlx.utils.e;

/* compiled from: FloatLayoutInstanceZonesView */
public class j implements a {
    private View Pu = null;
    private Activity SE;
    private TextView SF;
    private TextView SG;
    private TextView SH;
    private TextView SI;
    private TextView SJ;
    private TextView SK;
    private TextView SL;
    private e SM = new e(this) {
        final /* synthetic */ j SN;

        {
            this.SN = this$0;
        }

        public void c(View v) {
            switch (v.getId()) {
                case R.id.tvFloatInstanceZonesExperienceEnter:
                    if (((int) h.zG()) > 90) {
                        hlx.ui.a.n(this.SN.SE, "俗话说：“爬得高，摔得疼！”\n找低一些的位置再开启吧？");
                        return;
                    } else if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(1);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInstanceZonesTrainingEnter:
                    if (((int) h.zG()) > 90) {
                        hlx.ui.a.n(this.SN.SE, "俗话说：“爬得高，摔得疼！”\n找低一些的位置再开启吧？");
                        return;
                    } else if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(2);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInstanceZonesBreakthroughEnter:
                    if (((int) h.zG()) > 90) {
                        hlx.ui.a.n(this.SN.SE, "俗话说：“爬得高，摔得疼！”\n找低一些的位置再开启吧？");
                        return;
                    } else if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(3);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInstanceZonesUnExhaustibleEnter:
                    if (((int) h.zG()) > 90) {
                        hlx.ui.a.n(this.SN.SE, "俗话说：“爬得高，摔得疼！”\n找低一些的位置再开启吧？");
                        return;
                    } else if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(4);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInsZonesMazeEnter:
                    if (((int) h.zG()) > 90) {
                        hlx.ui.a.n(this.SN.SE, "俗话说：“爬得高，摔得疼！”\n找低一些的位置再开启吧？");
                        return;
                    } else if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(5);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInsZonesTrapEnter:
                    if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(6);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                case R.id.tvFloatInsZonesParkourEnter:
                    if (h.vN()) {
                        hlx.ui.a.n(this.SN.SE, "抱歉！使用了JS则无法进入副本！");
                        return;
                    } else if (h.vK() > 0) {
                        hlx.ui.a.n(this.SN.SE, "添加了好多好多方块，好累哟！再等等吧！");
                        return;
                    } else if (h.zp() > 0 || h.getGameType() == 1) {
                        q.aJ(false);
                        com.huluxia.mcfloat.InstanceZones.a.sl().dJ(7);
                        return;
                    } else {
                        hlx.ui.a.n(this.SN.SE, "抱歉！请复活后再进入副本！");
                        return;
                    }
                default:
                    return;
            }
        }
    };

    public j(Activity aty, View view) {
        this.SE = aty;
        this.Pu = view;
        this.Pu.setVisibility(8);
        this.SF = (TextView) view.findViewById(R.id.tvFloatInstanceZonesExperienceEnter);
        this.SF.setOnClickListener(this.SM);
        this.SG = (TextView) view.findViewById(R.id.tvFloatInstanceZonesTrainingEnter);
        this.SG.setOnClickListener(this.SM);
        this.SH = (TextView) view.findViewById(R.id.tvFloatInstanceZonesBreakthroughEnter);
        this.SH.setOnClickListener(this.SM);
        this.SI = (TextView) view.findViewById(R.id.tvFloatInstanceZonesUnExhaustibleEnter);
        this.SI.setOnClickListener(this.SM);
        this.SJ = (TextView) view.findViewById(R.id.tvFloatInsZonesMazeEnter);
        this.SJ.setOnClickListener(this.SM);
        this.SK = (TextView) view.findViewById(R.id.tvFloatInsZonesTrapEnter);
        this.SK.setOnClickListener(this.SM);
        this.SL = (TextView) view.findViewById(R.id.tvFloatInsZonesParkourEnter);
        this.SL.setOnClickListener(this.SM);
    }

    public void qN() {
        com.huluxia.mcfloat.InstanceZones.a.sl().aL(false);
    }

    public void cd(String mapName) {
    }

    public void qO() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (isShow) {
            qO();
        }
    }
}
