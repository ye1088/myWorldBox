package com.huluxia;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.huluxia.data.j;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.widget.UtilsAndroid;
import com.huluxia.ui.profile.FriendListActivity;
import com.huluxia.utils.ah;
import com.huluxia.utils.ap;
import com.huluxia.utils.o;
import com.huluxia.utils.u;
import com.huluxia.widget.Constants;
import com.huluxia.widget.Constants.Model;
import com.huluxia.widget.Constants.PushMsgType;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;
import org.apache.http.message.BasicNameValuePair;

/* compiled from: StatisticsApp */
public class r {
    private static final String TAG = "StatisticsApp";
    private static final String gA = "hlxsystem";
    private static final String gF = "http://stat.huluxia.net/stat/gamedown";
    private static final String gG = "http://stat.huluxia.net/stat/service/event";
    private static final String gH = "http://stat.huluxia.net/stat/down/begin";
    private static final String gI = "http://stat.huluxia.net/stat/down/complete";
    private static final String gJ = "http://stat.huluxia.net/stat/install/complete";
    private static final String gK = "tool";
    public static final String gL = "fine";
    public static final String gM = "new";
    public static final String gN = "cate";
    public static final String gO = "topic";
    public static final String gP = "rank";
    public static final String gQ = "detail";
    public static final String gR = "search";
    public static final String gS = "other";
    private static final String gT = "http://test.mc.huluxia.com/mc/screen/stat/ANDROID/1.1";
    private static final String gU = "http://stat.huluxia.com/downstat/down/begin";
    private static final String gV = "http://stat.huluxia.com/downstat/down/complete";
    private static final String gW = "http://stat.huluxia.com/downstat/install/begin";
    private static final String gX = "http://stat.huluxia.com/downstat/install/complete";
    private static String gY = "mctool";
    private static String gZ = null;
    private static r gu = null;
    public static final int gv = 1;
    public static final int gw = 2;
    public static final int gx = 3;
    public static final int gy = 4;
    public static final int gz = 5;
    private Thread gB = null;
    private boolean gC = false;
    private Vector<b> gD = null;
    private Runnable gE = new 1(this);
    protected Context mContext = null;

    /* compiled from: StatisticsApp */
    public static class a {
        public static String hE = "bbs_opentopic";
        public static String hF = "bbs_recommend";
        public static String hG = "err_appzero";
        public static String hH = "push_baidu_game";
        public static String hI = "push_xiaomi_game";
        public static String hJ = "push_huawei_game";
        public static String hK = "push_baidu_topic";
        public static String hL = "push_xiaomi_topic";
        public static String hM = "push_huawei_topic";
        public static String hN = "push_baidu_update";
        public static String hO = "push_xiaomi_update";
        public static String hP = "push_huawei_update";
        public static String hQ = "click_baidu_game";
        public static String hR = "click_xiaomi_game";
        public static String hS = "click_huawei_game";
        public static String hT = "click_baidu_topic";
        public static String hU = "click_xiaomi_topic";
        public static String hV = "click_huawei_topic";
        public static String hW = "click_baidu_update";
        public static String hX = "click_xiaomi_update";
        public static String hY = "click_huawei_update";
        public static String hZ = "open_psp_game";
        public static String hb = "tool_start";
        public static String hc = "root_start";
        public static String hd = "root_run";
        public static String he = "root_error";
        public static String hf = "qzone_set";
        public static String hg = "qzong_rec";
        public static String hh = "screen_cap";
        public static String hi = "screen_shd";
        public static String hj = "location_set";
        public static String hk = "time_speed";
        public static String hl = "search_value";
        public static String hm = "plugin_bbattack";
        public static String hn = "plugin_bbartifact";
        public static String ho = "app_comp_2300";
        public static String hp = "app_comp_2304";
        public static String hq = "app_comp_2317";
        public static String hr = "banner_click";
        public static String hs = "tag_click";
        public static String ht = "resource_fine";
        public static String hu = "resource_new";
        public static String hv = "resource_cate";
        public static String hw = "resource_topic";
        public static String hx = "resource_rank";
        public static String hy = "bbs_enterclass";
        public static String hz = "bbs_html5";
        public static String iA = "hlx_skin_enable";
        public static String iB = "import_map";
        public static String iC = "import_js";
        public static String iD = "import_skin";
        public static String iE = "import_wood";
        public static String iF = "share_mc";
        public static String iG = "start_mcfloat";
        public static String iH = "finish_mcfloat";
        public static String iI = "home_start_server";
        public static String iJ = "recommend_tab_server";
        public static String iK = "new_tab_server";
        public static String iL = "open_server";
        public static String iM = "enter_server_from_list";
        public static String iN = "enter_server_from_detail";
        public static String iO = "click_map_tab";
        public static String iP = "click_home_tab";
        public static String iQ = "click_bbs_tab";
        public static String iR = "click_me_tab";
        public static String iS = "click_map_cate";
        public static String iT = "click_map_subject";
        public static String iU = "click_map_home_recommend";
        public static String iV = "click_map_more";
        public static String iW = "click_map_more_tab";
        public static String iX = "click_js_cate";
        public static String iY = "click_js_subject";
        public static String iZ = "click_js_home_recommend";
        public static String ia = "open_gba_game";
        public static String ib = "open_gbc_game";
        public static String ic = "open_nds_game";
        public static String ie = "open_nes_game";
        public static String if = "open_sfc_game";
        public static String ig = "open_jji_game";
        public static String ih = "open_smd_game";
        public static String ii = "open_n64_game";
        public static String ij = "open_ngp_game";
        public static String ik = "open_ps_game";
        public static String il = "reboot_download";
        public static String im = "more_map";
        public static String io = "search_map";
        public static String ip = "search_js";
        public static String iq = "search_wood";
        public static String ir = "search_skin";
        public static String iu = "start_download";
        public static String iw = "show_cate";
        public static String ix = "js_mgr";
        public static String iy = "js_eanble";
        public static String iz = "hlx_wood_enable";
        public static String jA = "share_community_map_good_qqzone";
        public static String jB = "share_community_map_good_qqfriend";
        public static String jC = "share_community_map_good_wxfriend";
        public static String jD = "click_mc_good";
        public static String jE = "click_main_banner";
        public static String jF = "hlx_error_res_publish_check_request_fail";
        public static String jG = "hlx_error_res_publish_submit_fail";
        public static String jH = "hlx_error_res_publish_update_fail";
        public static String jI = "start_game105";
        public static String jJ = "start_game110";
        public static String jK = "start_game121";
        public static String jL = "start_game122";
        public static String jM = "start_game123";
        public static String jN = "start_game130";
        public static String jO = "start_game131";
        public static String jP = "start_game140";
        public static String jQ = "start_game143";
        public static String jR = "start_game1501";
        public static String jS = "map_run_local";
        public static String jT = "map_run_netex";
        public static String jU = "open_float_window";
        public static String jV = "close_float_window";
        public static String jW = "float_tab_commonuse";
        public static String jX = "float_tab_role";
        public static String jY = "float_tab_netplayer";
        public static String jZ = "float_tab_inszones";
        public static String ja = "click_js_more";
        public static String jc = "click_js_more_tab";
        public static String jd = "click_skin_cate";
        public static String je = "click_skin_subject";
        public static String jf = "click_skin_home_recommend";
        public static String jg = "click_skin_more";
        public static String jh = "click_skin_more_tab";
        public static String ji = "click_wood_cate";
        public static String jj = "click_wood_subject";
        public static String jk = "click_wood_home_recommend";
        public static String jl = "click_wood_more";
        public static String jm = "click_wood_more_tab";
        public static String jn = "download_map_good";
        public static String jo = "download_community_map_submit";
        public static String jp = "download_community_map_good";
        public static String jq = "share_map_good";
        public static String jr = "share_map_good_qqzone";
        public static String jt = "share_map_good_qqfriend";
        public static String ju = "share_map_good_wxfriend";
        public static String jv = "share_community_map_submit";
        public static String jw = "share_community_map_submit_qqzone";
        public static String jx = "share_community_map_submit_qqfriend";
        public static String jy = "share_community_map_submit_wxfriend";
        public static String jz = "share_community_map_good";
        public static String kA = "float_role_fallnodamage";
        public static String kB = "float_netplayer_trace";
        public static String kC = "float_inszones_experience";
        public static String kD = "float_inszones_exp_chalenge_suc";
        public static String kE = "float_inszones_new_train";
        public static String kF = "float_inszones_new_train_over";
        public static String kG = "float_inszones_break_through";
        public static String kH = "float_inszones_break_through_over";
        public static String kI = "float_inszones_unexhaustible";
        public static String kJ = "float_inszones_unexhaustible_over";
        public static String kK = "float_inszones_maze";
        public static String kL = "float_inszones_maze_success";
        public static String kM = "float_inszones_trap";
        public static String kN = "float_inszones_trap_success";
        public static String kO = "float_inszones_parkour";
        public static String kP = "float_inszones_parkour_success";
        public static String kQ = "float_bag_collect";
        public static String kR = "float_bag_v121new";
        public static String kS = "float_bag_medicine";
        public static String kT = "float_bag_enchant";
        public static String kU = "float_senior_enchant";
        public static String kV = "float_potion_buffmode";
        public static String kW = "float_potion_debuffmode";
        public static String kX = "float_potion_buffcancel";
        public static String kY = "float_potion_debuffcancel";
        public static String kZ = "float_potion_buffeat";
        public static String ka = "float_tab_bag";
        public static String kb = "float_tab_deliver";
        public static String kc = "float_tab_animal";
        public static String kd = "float_tab_build";
        public static String ke = "float_tab_backup";
        public static String kf = "float_tab_screenshot";
        public static String kg = "float_tab_weaponstore";
        public static String kh = "float_tab_potion";
        public static String ki = "float_tab_enchant";
        public static String kj = "float_commonuse_create";
        public static String kk = "float_commonuse_survival";
        public static String kl = "float_commonuse_settime";
        public static String km = "float_commonuse_showpos";
        public static String kn = "float_commonuse_showblood";
        public static String ko = "float_commonuse_setFovScale";
        public static String kp = "float_commonuse_smallmap";
        public static String kq = "float_commonuse_locktime";
        public static String kr = "float_commonuse_setplayerlevel";
        public static String ks = "float_commonuse_setGUISize";
        public static String kt = "float_commonuse_setWheatherRain";
        public static String ku = "float_commonuse_setWheatherlightning";
        public static String kv = "float_role_sprint";
        public static String kw = "float_role_jump";
        public static String kx = "float_role_invincible";
        public static String ky = "float_role_deathfall";
        public static String kz = "float_role_fly";
        public static String lA = "push_click";
        public static String la = "float_potion_debuffeat";
        public static String lc = "float_potion_buffall";
        public static String ld = "float_potion_debuffall";
        public static String le = "float_potion_buffmaxeffect";
        public static String lf = "float_potion_debuffmaxeffect";
        public static String lg = "float_potion_attack";
        public static String lh = "float_potion_attackbuff_eat";
        public static String li = "float_potion_attackbuff_cancel";
        public static String lj = "float_deliver_spawn";
        public static String lk = "float_deliver_suicide";
        public static String ll = "float_deliver_death_point";
        public static String lm = "float_build_straight_line";
        public static String ln = "float_build_cuboid";
        public static String lo = "hlx_float_build_cylindroid";
        public static String lq = "float_screenshot_save";
        public static String lr = "float_screenshot_click";
        public static String ls = "float_screenshot_close";
        public static String lt = "float_screenshot_quick";
        public static String lu = "float_screenshot_unsave";
        public static String lv = "main_publish_map";
        public static String lw = "homebanner_download_apk_start";
        public static String lx = "homebanner_download_apk_finish";
        public static String ly = "homebanner_install_apk_start";
        public static String lz = "homebanner_install_apk_finish";
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getDeviceId()).append("@").append(HTApplication.bJ()).append("@").append(gY).append("@ver").append(o.fetchVersionName());
        gZ = sb.toString();
    }

    public static synchronized r ck() {
        r rVar;
        synchronized (r.class) {
            if (gu == null) {
                gu = new r();
            }
            rVar = gu;
        }
        return rVar;
    }

    public void ad(Context context) {
        this.mContext = context;
        String channel = u.ca("UMENG_CHANNEL");
        if (ah.KZ().LC() != null) {
            channel = ah.KZ().LC();
            if (channel.equalsIgnoreCase("tool_360")) {
                channel = u.ca("UMENG_CHANNEL");
            }
        }
        ah.KZ().fY(channel);
        AnalyticsConfig.setChannel(channel);
        AnalyticsConfig.setAppkey("550474b1fd98c50686000bc5");
        MobclickAgent.updateOnlineConfig(this.mContext);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.onResume(this.mContext);
        channel = HTApplication.bJ();
        if (ah.KZ().LB() != null) {
            channel = ah.KZ().LB();
        }
        ah.KZ().fX(channel);
        HTApplication.z(channel);
        this.gC = true;
        this.gD = new Vector(20);
        this.gB = new Thread(this.gE);
        this.gB.start();
    }

    public void cl() {
        MobclickAgent.onEvent(this.mContext, "tool-start");
        R(a.hb);
    }

    public void cm() {
        MobclickAgent.onEvent(this.mContext, "root-start");
        R(a.hc);
    }

    public void cn() {
        MobclickAgent.onEvent(this.mContext, "root-run");
        R(a.hd);
    }

    public void co() {
        MobclickAgent.onEvent(this.mContext, "root-error");
        R(a.he);
    }

    public void cp() {
        MobclickAgent.onEvent(this.mContext, "qzone-set");
        R(a.hf);
    }

    public void cq() {
        MobclickAgent.onEvent(this.mContext, "qzong-rec");
        R(a.hg);
    }

    public void cr() {
        MobclickAgent.onEvent(this.mContext, "screen-cap");
        R(a.hh);
    }

    public void cs() {
        MobclickAgent.onEvent(this.mContext, "screen-shd");
        R(a.hi);
    }

    public void ct() {
        MobclickAgent.onEvent(this.mContext, "location-set");
        R(a.hj);
    }

    public void cu() {
        MobclickAgent.onEvent(this.mContext, "time-speed");
        R(a.hk);
    }

    public void cv() {
        MobclickAgent.onEvent(this.mContext, "search-value");
        R(a.hl);
    }

    public void E(String id) {
        if (id.equals(Constants.bsL) || id.equals(Constants.bsM) || id.equals(Constants.bsN) || id.equals(Constants.bsO)) {
            ck().F(id);
        }
        MobclickAgent.onEvent(this.mContext, "down-click");
    }

    public void F(String id) {
        MobclickAgent.onEvent(this.mContext, "down-app", String.valueOf(id));
    }

    public void G(String id) {
        MobclickAgent.onEvent(this.mContext, "down-comp", id);
    }

    public void cw() {
        MobclickAgent.onEvent(this.mContext, "plugin-bbattack");
        R(a.hm);
    }

    public void cx() {
        MobclickAgent.onEvent(this.mContext, "plugin-bbartifact");
        R(a.hn);
    }

    public void f(int index, int topicId) {
        MobclickAgent.onEvent(this.mContext, "spec-rtopic", String.valueOf(index) + "_" + String.valueOf(topicId));
    }

    public void g(int index, int activityId) {
        MobclickAgent.onEvent(this.mContext, "spec-rtool", String.valueOf(index) + "_" + String.valueOf(activityId));
    }

    public void u(int index) {
        MobclickAgent.onEvent(this.mContext, "spec-index-" + String.valueOf(index), String.valueOf(index));
    }

    public void v(int index) {
        MobclickAgent.onEvent(this.mContext, "spec-art-" + String.valueOf(index), String.valueOf(index));
    }

    public void j(long index) {
        MobclickAgent.onEvent(this.mContext, "recom-click", String.valueOf(index));
    }

    public void H(String pos) {
        MobclickAgent.onEvent(this.mContext, "banner-click", pos);
    }

    public void I(String pos) {
        MobclickAgent.onEvent(this.mContext, "tag-click-" + pos, 100);
    }

    public void cy() {
        MobclickAgent.onEvent(this.mContext, "resource-fine");
        R(a.ht);
    }

    public void cz() {
        MobclickAgent.onEvent(this.mContext, "resource-new");
        R(a.hu);
    }

    public void cA() {
        MobclickAgent.onEvent(this.mContext, "resource-cate");
        R(a.hv);
    }

    public void cB() {
        MobclickAgent.onEvent(this.mContext, "resource-topic");
        R(a.hw);
    }

    public void cC() {
        MobclickAgent.onEvent(this.mContext, "resource-rank");
        R(a.hx);
    }

    public void cD() {
        MobclickAgent.onEvent(this.mContext, "test-umeng");
    }

    public void cE() {
        MobclickAgent.onEvent(this.mContext, "bbs-idol");
    }

    public void cF() {
        MobclickAgent.onEvent(this.mContext, "bbs-favor");
    }

    public void J(String id) {
        if (id != null) {
            MobclickAgent.onEvent(this.mContext, "bbs-enterclass", id);
            R(a.hy);
        }
    }

    public void cG() {
        MobclickAgent.onEvent(this.mContext, "bbs-html5");
        R(a.hz);
    }

    public void cH() {
        MobclickAgent.onEvent(this.mContext, "err-appzero");
        R(a.hG);
    }

    public void k(long catid) {
        MobclickAgent.onEvent(this.mContext, "bbs-opentopic", String.valueOf(catid));
        R(a.hE);
    }

    public void cI() {
        R(a.hF);
    }

    public void h(int model, int type) {
        if (model == Model.BAIDU.Value()) {
            z(type);
        } else if (model == Model.XIAOMI.Value()) {
            A(type);
        } else if (model == Model.HUAWEI.Value()) {
            B(type);
        }
    }

    private void z(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hH);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hK);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hN);
        }
    }

    private void A(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hI);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hL);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hO);
        }
    }

    private void B(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hJ);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hM);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hP);
        }
    }

    public void i(int model, int type) {
        if (model == Model.BAIDU.Value()) {
            C(type);
        } else if (model == Model.XIAOMI.Value()) {
            D(type);
        } else if (model == Model.HUAWEI.Value()) {
            E(type);
        }
    }

    private void C(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hQ);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hT);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hW);
        }
    }

    private void D(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hR);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hU);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hX);
        }
    }

    private void E(int type) {
        if (type == PushMsgType.GAME.Value()) {
            R(a.hS);
        } else if (type == PushMsgType.TOPIC.Value()) {
            R(a.hV);
        } else if (type == PushMsgType.UPDATE.Value()) {
            R(a.hY);
        }
    }

    public void cJ() {
        R(a.hZ);
    }

    public void cK() {
        R(a.ia);
    }

    public void cL() {
        R(a.ia);
    }

    public void cM() {
        R(a.ic);
    }

    public void cN() {
        R(a.ie);
    }

    public void cO() {
        R(a.if);
    }

    public void cP() {
        R(a.ig);
    }

    public void cQ() {
        R(a.ih);
    }

    public void cR() {
        R(a.ii);
    }

    public void cS() {
        R(a.ij);
    }

    public void cT() {
        R(a.ik);
    }

    public void h(String inputId, String inputTypeId) {
        l(inputId, inputTypeId);
    }

    public void cU() {
        MobclickAgent.onEvent(this.mContext, "netpan-interpret-fail");
    }

    public void cV() {
        MobclickAgent.onEvent(this.mContext, "netpan-download-fail");
    }

    public void cW() {
        MobclickAgent.onEvent(this.mContext, "user-cancle-download");
    }

    public void cX() {
        MobclickAgent.onEvent(this.mContext, "file-verify-fail");
    }

    public void K(String eventName) {
        MobclickAgent.onEvent(this.mContext, eventName);
    }

    public void j(String eventName, String evArg) {
        MobclickAgent.onEvent(this.mContext, eventName, evArg);
    }

    public void cY() {
        MobclickAgent.onEvent(this.mContext, a.im);
    }

    public void cZ() {
        MobclickAgent.onEvent(this.mContext, a.io);
    }

    public void da() {
        MobclickAgent.onEvent(this.mContext, a.iu);
    }

    public void db() {
        MobclickAgent.onEvent(this.mContext, a.iw);
    }

    public void dc() {
        MobclickAgent.onEvent(this.mContext, a.ix);
    }

    public void dd() {
        MobclickAgent.onEvent(this.mContext, a.iB);
    }

    public void de() {
        MobclickAgent.onEvent(this.mContext, a.iC);
    }

    public void df() {
        MobclickAgent.onEvent(this.mContext, a.iD);
    }

    public void dg() {
        MobclickAgent.onEvent(this.mContext, a.iE);
    }

    public void L(String name) {
        MobclickAgent.onEvent(this.mContext, a.iy, name);
    }

    public void M(String name) {
        MobclickAgent.onEvent(this.mContext, a.iz, name);
    }

    public void N(String name) {
        MobclickAgent.onEvent(this.mContext, a.iA, name);
    }

    public void dh() {
        MobclickAgent.onEvent(this.mContext, a.iF);
    }

    public void di() {
        MobclickAgent.onEvent(this.mContext, a.iG);
    }

    public void dj() {
        MobclickAgent.onEvent(this.mContext, a.iH);
    }

    public void O(String src) {
        MobclickAgent.onEvent(this.mContext, src);
    }

    public void P(String src) {
        MobclickAgent.onEvent(this.mContext, src);
    }

    public void dk() {
        MobclickAgent.onEvent(this.mContext, a.jn);
    }

    public void dl() {
        MobclickAgent.onEvent(this.mContext, a.jo);
    }

    public void dm() {
        MobclickAgent.onEvent(this.mContext, a.jp);
    }

    public void dn() {
        MobclickAgent.onEvent(this.mContext, a.jr);
    }

    public void do() {
        MobclickAgent.onEvent(this.mContext, a.jt);
    }

    public void dp() {
        MobclickAgent.onEvent(this.mContext, a.ju);
    }

    public void dq() {
        MobclickAgent.onEvent(this.mContext, a.jA);
    }

    public void dr() {
        MobclickAgent.onEvent(this.mContext, a.jB);
    }

    public void ds() {
        MobclickAgent.onEvent(this.mContext, a.jC);
    }

    public void Q(String url) {
        MobclickAgent.onEvent(this.mContext, a.jD, url);
    }

    public void dt() {
        R("boot_all");
        String strBootType = "";
        String packName = HTApplication.getAppContext().getPackageName();
        if (packName.equals("com.huati")) {
            strBootType = "boot_floor";
        }
        if (packName.equals(com.huluxia.mctool.a.APPLICATION_ID)) {
            strBootType = "boot_mc";
        }
        if (packName.equals("com.huluxia.gametools")) {
            strBootType = "boot_tool";
        }
        if (strBootType.length() == 0) {
            strBootType = "boot_unknow";
        }
        R(strBootType);
    }

    public void du() {
        String strTypeNameBootAll = "float_build";
        b(strTypeNameBootAll, null, strTypeNameBootAll);
    }

    private void a(b info) {
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    private void dv() throws InterruptedException {
        while (this.gC) {
            synchronized (this.gD) {
                int nLastIdx = this.gD.size();
                if (nLastIdx == 0) {
                    this.gD.wait();
                }
                if (nLastIdx == 0) {
                } else {
                    b info = (b) this.gD.get(nLastIdx - 1);
                    this.gD.remove(nLastIdx - 1);
                    if (info != null) {
                        String strResult;
                        if (info.params == null) {
                            strResult = ap.dE(info.lB);
                        } else {
                            strResult = ap.c(info.lB, info.params);
                        }
                        if (!(strResult.length() == 0 || info.lC == null)) {
                            g.v(info.lC);
                        }
                    }
                }
            }
        }
    }

    private void dw() {
        String strTypeName = "UserApp";
        if (!g.w(strTypeName)) {
            HLog.verbose(TAG, "SendTongji_UserApp ...", new Object[0]);
            PackageManager pm = this.mContext.getApplicationContext().getPackageManager();
            Collection appList = null;
            try {
                appList = pm.getInstalledApplications(8192);
            } catch (Exception e) {
                HLog.debug(TAG, "getInstalledApplications transaction too large, " + e, new Object[0]);
            }
            if (!UtilsFunction.empty((Collection) appList)) {
                pm.getInstalledPackages(0);
                StringBuilder sb = new StringBuilder();
                String strSelfPackname = this.mContext.getPackageName();
                sb.append(strSelfPackname).append("||").append(o.getDeviceId());
                for (ApplicationInfo appinfo : appList) {
                    if (!((appinfo.flags & 1) > 0 || strSelfPackname.equals(appinfo.processName) || "com.huati".equals(appinfo.processName) || "com.huluxia.gametools".equals(appinfo.processName) || com.huluxia.mctool.a.APPLICATION_ID.equals(appinfo.processName))) {
                        sb.append("||").append(appinfo.loadLabel(pm).toString());
                    }
                }
                String strTime = "" + System.currentTimeMillis();
                String strKeyText = UtilsMD5.getMD5String(strTime + gA);
                b info = new b(null);
                info.lC = strTypeName;
                info.lB = gF;
                info.params = new ArrayList();
                info.params.add(new BasicNameValuePair("time", strTime));
                info.params.add(new BasicNameValuePair("key", strKeyText));
                info.params.add(new BasicNameValuePair("applogs", sb.toString()));
                a(info);
            }
        }
    }

    public void R(String event) {
        b(event, null, null);
    }

    public void k(String event, String datas) {
        b(event, datas, null);
    }

    public void b(String event, String datas, String dayType) {
        String strTime = "" + System.currentTimeMillis();
        String strKeyText = UtilsMD5.getMD5String(strTime + gA);
        b info = new b(null);
        info.lC = dayType;
        info.lB = gG;
        info.params = new ArrayList();
        info.params.add(new BasicNameValuePair("time", strTime));
        info.params.add(new BasicNameValuePair("key", strKeyText));
        info.params.add(new BasicNameValuePair("globalID", o.getDeviceId()));
        info.params.add(new BasicNameValuePair("eventType", event));
        info.params.add(new BasicNameValuePair("datas", datas));
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    private void l(String inputId, String inputTypeId) {
        String url = gT;
        String strTime = "" + System.currentTimeMillis();
        String strKeyText = UtilsMD5.getMD5String(strTime + gA);
        b info = new b(null);
        info.lB = url;
        info.params = new ArrayList();
        info.params.add(new BasicNameValuePair("time", strTime));
        info.params.add(new BasicNameValuePair("key", strKeyText));
        info.params.add(new BasicNameValuePair("id", inputId));
        info.params.add(new BasicNameValuePair("type_id", inputTypeId));
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    public void a(String gameID, int srcType, String clickPos, boolean isComp) {
        String url = gH;
        String downPara = "downBegin";
        if (isComp) {
            url = gI;
            downPara = "downComplete";
        }
        String strTime = "" + System.currentTimeMillis();
        String strKeyText = UtilsMD5.getMD5String(strTime + gA);
        b info = new b(null);
        info.lB = url;
        info.params = new ArrayList();
        info.params.add(new BasicNameValuePair("time", strTime));
        info.params.add(new BasicNameValuePair("key", strKeyText));
        StringBuilder sb = new StringBuilder();
        sb.append(o.getDeviceId()).append("@").append(gameID).append("@").append(srcType).append("@").append(gK).append("@").append(o.getVersionName()).append("@").append(clickPos);
        info.params.add(new BasicNameValuePair(downPara, sb.toString()));
        HLog.verbose(TAG, "AddDownTongji  type:%s  id:%s", downPara, gameID);
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    public void e(String gameID, int srcType) {
        String url = gJ;
        String strTime = "" + System.currentTimeMillis();
        String strKeyText = UtilsMD5.getMD5String(strTime + gA);
        b info = new b(null);
        info.lB = url;
        info.params = new ArrayList();
        info.params.add(new BasicNameValuePair("time", strTime));
        info.params.add(new BasicNameValuePair("key", strKeyText));
        StringBuilder sb = new StringBuilder();
        sb.append(o.getDeviceId()).append("@").append(gameID).append("@").append(srcType).append("@").append(gK).append("@").append(o.getVersionName());
        info.params.add(new BasicNameValuePair("installComplete", sb.toString()));
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    private static b dx() {
        b info = new b(null);
        String strTime = "" + System.currentTimeMillis();
        String strKeyText = UtilsMD5.getMD5String(strTime + gA);
        info.params = new ArrayList();
        info.params.add(new BasicNameValuePair("time", strTime));
        info.params.add(new BasicNameValuePair("key", strKeyText));
        return info;
    }

    private void b(b info) {
        synchronized (this.gD) {
            this.gD.add(info);
            this.gD.notify();
        }
    }

    public void b(long appid, String biz, String page) {
        b info = dx();
        info.lB = gU;
        StringBuilder sb = new StringBuilder(gZ);
        sb.append("@").append(appid).append("@").append(biz).append("@").append(page);
        info.params.add(new BasicNameValuePair("downBegin", sb.toString()));
        b(info);
    }

    public void c(long appid, String biz, String page) {
        b info = dx();
        info.lB = gV;
        StringBuilder sb = new StringBuilder(gZ);
        sb.append("@").append(appid).append("@").append(biz).append("@").append(page);
        info.params.add(new BasicNameValuePair("downComplete", sb.toString()));
        b(info);
    }

    public void c(long appid, String biz) {
        b info = dx();
        info.lB = gW;
        StringBuilder sb = new StringBuilder(gZ);
        sb.append("@").append(appid).append("@").append(biz);
        info.params.add(new BasicNameValuePair("installBegin", sb.toString()));
        b(info);
    }

    public void d(long appid, String biz) {
        b info = dx();
        info.lB = gX;
        StringBuilder sb = new StringBuilder(gZ);
        sb.append("@").append(appid).append("@").append(biz);
        info.params.add(new BasicNameValuePair("installComplete", sb.toString()));
        b(info);
    }

    public void a(int type, long key, long assistkey, int mcrestype) {
        a(false, type, key, assistkey, mcrestype);
    }

    public void b(int type, long key, long assistkey, int mcrestype) {
        a(true, type, key, assistkey, mcrestype);
    }

    private void a(boolean isClick, int type, long key, long assistkey, int mcrestype) {
        Properties properties = new Properties();
        properties.put("version", UtilsAndroid.fetchVersionName());
        properties.put("channel", HTApplication.bJ());
        properties.put("clienttype", gY);
        properties.put("type", String.valueOf(type));
        properties.put("mcrestype", String.valueOf(mcrestype));
        properties.put("key", String.valueOf(key));
        if (assistkey != 0) {
            properties.put("assistkey", Long.valueOf(assistkey));
        } else {
            properties.put("assistkey", "");
        }
        if (mcrestype != 0) {
            properties.put("mcrestype", Integer.valueOf(mcrestype));
        } else {
            properties.put("mcrestype", "");
        }
        if (j.ep().ey()) {
            properties.put(FriendListActivity.EXTRA_USER_ID, Long.valueOf(j.ep().getUserid()));
        } else {
            properties.put(FriendListActivity.EXTRA_USER_ID, "");
        }
        if (isClick) {
            properties.put("bizname", "open");
        } else {
            properties.put("bizname", "arrive");
        }
        properties.put("deviceid", o.getDeviceId());
        properties.put("devicemd5", UtilsMD5.MD5Code(o.getDeviceId()));
        k(a.lA, Json.toJson(properties));
    }
}
