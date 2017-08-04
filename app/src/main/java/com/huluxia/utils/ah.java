package com.huluxia.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.baidu.android.pushservice.PushConstants;
import com.huluxia.HTApplication;
import com.huluxia.data.HTSysBannerInfo;
import com.huluxia.data.c;
import com.huluxia.data.e;
import com.huluxia.data.g;
import com.huluxia.data.k;
import com.huluxia.data.l;
import com.huluxia.data.map.j;
import com.huluxia.data.storymode.d;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.util.UUID;

/* compiled from: UtilsLocalStore */
public class ah {
    private static final String SESSION = "session";
    private static ah blU = new ah();
    private static final String blV = "account";
    private static final String blW = "pwd";
    private static final String blX = "token";
    private static final String blY = "userinfo";
    private static final String blZ = "-openid";
    private static final String bma = "-sessionkey";
    public static final String bmb = "homeBannerDot";

    /* compiled from: UtilsLocalStore */
    public static class a {
        public static int ALL = 1;
        public static int bmc = 0;
        public static int bmd = 2;
    }

    /* compiled from: UtilsLocalStore */
    public static class b {
        public static int bme = 0;
        public static int bmf = 1;
    }

    public static synchronized ah KZ() {
        ah ahVar;
        synchronized (ah.class) {
            if (blU == null) {
                blU = new ah();
            }
            ahVar = blU;
        }
        return ahVar;
    }

    public String getToken() {
        return com.huluxia.pref.b.Em().getString(blX);
    }

    public String fU(String email) {
        return com.huluxia.pref.b.Em().getString(email + bma);
    }

    public void ai(String email, String openid) {
        com.huluxia.pref.b.Em().putString(email + bma, openid);
    }

    public void setToken(String token) {
        com.huluxia.pref.b.Em().putString(blX, token);
    }

    public void La() {
        com.huluxia.pref.b.Em().remove(blX);
    }

    public void Lb() {
        com.huluxia.pref.b.Em().remove(blW);
    }

    public void Lc() {
        com.huluxia.pref.b.Em().remove(blY);
    }

    public void Ld() {
        com.huluxia.pref.b.Em().remove(SESSION);
    }

    public void Le() {
        com.huluxia.pref.b.Em().remove("loginmi");
    }

    public g ew() {
        String session = com.huluxia.pref.b.Em().getString(blY);
        if (UtilsFunction.empty((CharSequence) session)) {
            return null;
        }
        g loginUserInfo = null;
        try {
            return (g) Json.parseJsonObject(session, g.class);
        } catch (Exception e) {
            return loginUserInfo;
        }
    }

    public void a(g info) {
        com.huluxia.pref.b.Em().putString(blY, ag.toJsonString(info));
    }

    private SharedPreferences get_config_sp() {
        return HTApplication.getAppContext().getSharedPreferences("config", 0);
    }

    private SharedPreferences Lf() {
        return HTApplication.getAppContext().getSharedPreferences("update", 0);
    }

    public int P(String key, int def) {
        return get_config_sp().getInt(key, def);
    }

    public void Q(String key, int val) {
        get_config_sp().edit().putInt(key, val).commit();
    }

    public float b(String key, float def) {
        return get_config_sp().getFloat(key, def);
    }

    public void c(String key, float val) {
        get_config_sp().edit().putFloat(key, val).commit();
    }

    public boolean j(String key, boolean def) {
        return get_config_sp().getBoolean(key, def);
    }

    public void k(String key, boolean val) {
        get_config_sp().edit().putBoolean(key, val).commit();
    }

    public String aj(String key, String def) {
        return get_config_sp().getString(key, def);
    }

    public void ak(String key, String val) {
        get_config_sp().edit().putString(key, val).commit();
    }

    public void bT(String account) {
        Editor editor = get_config_sp().edit();
        editor.putString(blV, account);
        editor.commit();
    }

    public void o(long uid, int status) {
        com.huluxia.pref.b.Em().putLong(uid + "-checkstatus", (long) status);
    }

    public void p(long uid, int status) {
        com.huluxia.pref.b.Em().putLong(uid + "-qinfostatus", (long) status);
    }

    public int g(String email, long uid) {
        return com.huluxia.pref.b.Em().getInt(uid + "-qinfostatus", 0) | com.huluxia.pref.b.Em().getInt(email + "-qinfostatus", 0);
    }

    public void pY() {
        Editor editor = get_config_sp().edit();
        editor.remove(blV);
        editor.commit();
    }

    public String pZ() {
        return get_config_sp().getString(blV, "");
    }

    public void setPassword(String pwd) {
        Editor editor = get_config_sp().edit();
        editor.putString(blW, pwd);
        editor.commit();
    }

    public void qa() {
        Editor editor = get_config_sp().edit();
        editor.remove(blW);
        editor.commit();
    }

    public String getPassword() {
        return get_config_sp().getString(blW, "");
    }

    public void a(k info) {
        Editor editor = get_config_sp().edit();
        editor.putString(SESSION, ag.toJsonString(info));
        editor.commit();
    }

    public void Lg() {
        Editor editor = get_config_sp().edit();
        editor.remove(SESSION);
        editor.commit();
    }

    public k eo() {
        return (k) ag.toObjectNoExp(get_config_sp().getString(SESSION, ""), k.class);
    }

    public void G(long uid) {
        Editor editor = get_config_sp().edit();
        editor.putLong("miuid", uid);
        editor.commit();
    }

    public void Lh() {
        Editor editor = get_config_sp().edit();
        editor.remove("miuid");
        editor.commit();
    }

    public long fH() {
        return get_config_sp().getLong("miuid", 0);
    }

    public void b(l info) {
        Editor editor = get_config_sp().edit();
        editor.putString("misession", ag.toJsonString(info));
        editor.commit();
    }

    public void Li() {
        Editor editor = get_config_sp().edit();
        editor.remove("misession");
        editor.commit();
    }

    public l Lj() {
        return (l) ag.toObjectNoExp(get_config_sp().getString("misession", ""), l.class);
    }

    public void fV(String version) {
        Editor editor = Lf().edit();
        editor.putString("skip_version", version);
        editor.commit();
    }

    public void bt(long time) {
        Editor editor = Lf().edit();
        editor.putLong("skip_version_time", time);
        editor.commit();
    }

    public String Lk() {
        return Lf().getString("skip_version", "");
    }

    public long Ll() {
        return Lf().getLong("skip_version_time", -1);
    }

    public String qb() {
        SharedPreferences preferences = HTApplication.getAppContext().getSharedPreferences(PushConstants.EXTRA_APP, 0);
        String duuid = preferences.getString("DeviceUUID", null);
        if (duuid != null) {
            return duuid;
        }
        Editor editor = preferences.edit();
        duuid = UUID.randomUUID().toString();
        editor.putString("DeviceUUID", duuid);
        editor.commit();
        return duuid;
    }

    public String qc() {
        SharedPreferences preferences = HTApplication.getAppContext().getSharedPreferences(PushConstants.EXTRA_APP, 0);
        String duuid = preferences.getString("ComDeviceUUID", null);
        if (duuid != null) {
            return duuid;
        }
        Editor editor = preferences.edit();
        duuid = UUID.randomUUID().toString();
        editor.putString("ComDeviceUUID", duuid);
        editor.commit();
        return duuid;
    }

    public void lh(int val) {
        Editor editor = get_config_sp().edit();
        editor.putInt("TopicPic", val);
        editor.commit();
    }

    public int Lm() {
        return get_config_sp().getInt("TopicPic", a.bmc);
    }

    public void li(int val) {
        com.huluxia.pref.b.Em().putInt("TopicVideo", val);
    }

    public int Ln() {
        return com.huluxia.pref.b.Em().getInt("TopicVideo", a.bmd);
    }

    public int Lo() {
        return com.huluxia.pref.b.Em().getInt("themeMode", 0);
    }

    public void lj(int themeMode) {
        com.huluxia.pref.b.Em().putInt("themeMode", themeMode);
    }

    public boolean Lp() {
        return com.huluxia.pref.b.Em().getBoolean("firstBbsForum", true);
    }

    public void cY(boolean first) {
        com.huluxia.pref.b.Em().putBoolean("firstBbsForum", first);
    }

    public void a(e msgRemind) {
        Editor editor = get_config_sp().edit();
        editor.putString("MsgRemind", ag.toJsonString(msgRemind));
        editor.commit();
    }

    public void a(long uid, e msgRemind) {
        com.huluxia.pref.b.Em().putString(uid + "MsgRemind", ag.toJsonString(msgRemind));
    }

    public e bI() {
        String json = get_config_sp().getString("MsgRemind", null);
        if (json != null) {
            return (e) ag.toObjectNoExp(json, e.class);
        }
        return null;
    }

    public void a(long uid, com.huluxia.module.topic.g info) {
        com.huluxia.pref.b.Em().put(uid + "createPower", ag.toJsonString(info));
    }

    public com.huluxia.module.topic.g bu(long uid) {
        String str = com.huluxia.pref.b.Em().getString(uid + "createPower");
        if (UtilsFunction.empty((CharSequence) str)) {
            return null;
        }
        com.huluxia.module.topic.g info = null;
        try {
            return (com.huluxia.module.topic.g) Json.parseJsonObject(str, com.huluxia.module.topic.g.class);
        } catch (Exception e) {
            return info;
        }
    }

    public boolean Lq() {
        return Lf().getBoolean("openapp", true);
    }

    public void cZ(boolean value) {
        Editor editor = Lf().edit();
        editor.putBoolean("openapp", value);
        editor.commit();
    }

    public boolean Lr() {
        return Lf().getBoolean("firstnew", true);
    }

    public void da(boolean value) {
        Editor editor = Lf().edit();
        editor.putBoolean("firstnew", value);
        editor.commit();
    }

    public void a(HTSysBannerInfo obj) {
        Editor editor = get_config_sp().edit();
        editor.putString("SysBannerInfo", ag.toJsonString(obj));
        editor.commit();
    }

    public HTSysBannerInfo Ls() {
        String json = get_config_sp().getString("SysBannerInfo", null);
        if (json != null) {
            return (HTSysBannerInfo) ag.toObjectNoExp(json, HTSysBannerInfo.class);
        }
        return null;
    }

    public boolean Lt() {
        return Lf().getBoolean("bbdiatip", true);
    }

    public void db(boolean value) {
        Editor editor = Lf().edit();
        editor.putBoolean("bbdiatip", value);
        editor.commit();
    }

    public boolean Lu() {
        return Lf().getBoolean("roothometip", true);
    }

    public void dc(boolean value) {
        Editor editor = Lf().edit();
        editor.putBoolean("roothometip", value);
        editor.commit();
    }

    public String Lv() {
        return Lf().getString("BaiduMobAd_CHANNEL", null);
    }

    public void fW(String flag) {
        Editor editor = Lf().edit();
        editor.putString("BaiduMobAd_CHANNEL", flag);
        editor.commit();
    }

    public boolean Lw() {
        return get_config_sp().getBoolean("TipBaidu", false);
    }

    public void Lx() {
        Editor editor = get_config_sp().edit();
        editor.putBoolean("TipBaidu", true);
        editor.commit();
    }

    public int bO() {
        return get_config_sp().getInt("cartooncount", 0);
    }

    public void m(int count) {
        Editor editor = get_config_sp().edit();
        editor.putInt("cartooncount", count);
        editor.commit();
    }

    public long Ly() {
        return get_config_sp().getLong("gamelimitsize", 52428801);
    }

    public void bv(long size) {
        Editor editor = get_config_sp().edit();
        editor.putLong("gamelimitsize", size);
        editor.commit();
    }

    public boolean Lz() {
        return get_config_sp().getBoolean("relief", false);
    }

    public void LA() {
        Editor editor = get_config_sp().edit();
        editor.putBoolean("relief", true);
        editor.commit();
    }

    public boolean lk(int bizIndex) {
        return get_config_sp().getBoolean("emupath_" + bizIndex, false);
    }

    public void ll(int bizIndex) {
        Editor editor = get_config_sp().edit();
        editor.putBoolean("emupath_" + bizIndex, true);
        editor.commit();
    }

    public String LB() {
        return Lf().getString("LOCAL_CHANNEL", null);
    }

    public void fX(String flag) {
        Editor editor = Lf().edit();
        editor.putString("LOCAL_CHANNEL", flag);
        editor.commit();
    }

    public String LC() {
        return Lf().getString("UMENG_CHANNEL", null);
    }

    public void fY(String flag) {
        Editor editor = Lf().edit();
        editor.putString("UMENG_CHANNEL", flag);
        editor.commit();
    }

    public boolean LD() {
        return Lf().getBoolean("TIP_MARKET", false);
    }

    public void LE() {
        Editor editor = Lf().edit();
        editor.putBoolean("TIP_MARKET", true);
        editor.commit();
    }

    public boolean LF() {
        return get_config_sp().getBoolean("TIP_VERNOTSUPPORT", false);
    }

    public void LG() {
        Editor editor = get_config_sp().edit();
        editor.putBoolean("TIP_VERNOTSUPPORT", true);
        editor.commit();
    }

    public boolean R(String map, int attr) {
        return HTApplication.getAppContext().getSharedPreferences("mapattr", 0).getBoolean(map.hashCode() + "_" + attr, false);
    }

    public void c(String map, int attr, boolean val) {
        String key = map.hashCode() + "_" + attr;
        Editor editor = HTApplication.getAppContext().getSharedPreferences("mapattr", 0).edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public float al(String map, String axis) {
        return HTApplication.getAppContext().getSharedPreferences("mapattr", 0).getFloat(map.hashCode() + "_" + axis, Float.valueOf(0.0f).floatValue());
    }

    public void a(String map, String axis, float val) {
        String key = map.hashCode() + "_" + axis;
        Editor editor = HTApplication.getAppContext().getSharedPreferences("mapattr", 0).edit();
        editor.putFloat(key, val);
        editor.commit();
    }

    public String fZ(String path) {
        return HTApplication.getAppContext().getSharedPreferences("mapattr", 0).getString(path.hashCode() + "_date", null);
    }

    public void am(String path, String time) {
        String key = path.hashCode() + "_date";
        Editor editor = HTApplication.getAppContext().getSharedPreferences("mapattr", 0).edit();
        editor.putString(key, time);
        editor.commit();
    }

    public String LH() {
        return get_config_sp().getString("usingmap", null);
    }

    public void ga(String name) {
        Editor editor = get_config_sp().edit();
        editor.putString("usingmap", name);
        editor.commit();
    }

    public String LI() {
        return get_config_sp().getString("usingskin", null);
    }

    public void gb(String name) {
        Editor editor = get_config_sp().edit();
        editor.putString("usingskin", name);
        editor.commit();
    }

    public String LJ() {
        return get_config_sp().getString("usingwood", null);
    }

    public void gc(String name) {
        Editor editor = get_config_sp().edit();
        editor.putString("usingwood", name);
        editor.commit();
    }

    public void setMapCount(int count) {
        aB(count, 0);
    }

    public void setJsCount(int count) {
        aB(count, 1);
    }

    public void setSkinCount(int count) {
        aB(count, 2);
    }

    public void setWoodCount(int count) {
        aB(count, 3);
    }

    public void setServerCount(int count) {
        aB(count, 4);
    }

    public void setSeedCount(int count) {
        aB(count, 5);
    }

    public void setAdCount(int count) {
        aB(count, 6);
    }

    private void aB(int count, int type) {
        Editor editor = get_config_sp().edit();
        switch (type) {
            case 0:
                HLog.verbose("UtilsLocalStore", "set mapCount %d", Integer.valueOf(count));
                editor.putInt("mapCount", count);
                break;
            case 1:
                HLog.verbose("UtilsLocalStore", "set jsCount %d", Integer.valueOf(count));
                editor.putInt("jsCount", count);
                break;
            case 2:
                HLog.verbose("UtilsLocalStore", "set skinCount %d", Integer.valueOf(count));
                editor.putInt("skinCount", count);
                break;
            case 3:
                HLog.verbose("UtilsLocalStore", "set woodCount %d", Integer.valueOf(count));
                editor.putInt("woodCount", count);
                break;
            case 4:
                HLog.verbose("UtilsLocalStore", "set serverCount %d", Integer.valueOf(count));
                editor.putInt("serverCount", count);
                break;
            case 5:
                editor.putInt("seedCount", count);
                break;
            case 6:
                editor.putInt("adCount", count);
                break;
        }
        editor.commit();
    }

    public int getMapCount() {
        HLog.verbose("UtilsLocalStore", "get mapCount %d", Integer.valueOf(get_config_sp().getInt("mapCount", 0)));
        return get_config_sp().getInt("mapCount", 0);
    }

    public int getJsCount() {
        HLog.verbose("UtilsLocalStore", "get jsCount %d", Integer.valueOf(get_config_sp().getInt("jsCount", 0)));
        return get_config_sp().getInt("jsCount", 0);
    }

    public int getSkinCount() {
        HLog.verbose("UtilsLocalStore", "get skinCount %d", Integer.valueOf(get_config_sp().getInt("skinCount", 0)));
        return get_config_sp().getInt("skinCount", 0);
    }

    public int getWoodCount() {
        HLog.verbose("UtilsLocalStore", "get woodCount %d", Integer.valueOf(get_config_sp().getInt("woodCount", 0)));
        return get_config_sp().getInt("woodCount", 0);
    }

    public int getServerCount() {
        HLog.verbose("UtilsLocalStore", "get serverCount %d", Integer.valueOf(get_config_sp().getInt("serverCount", 0)));
        return get_config_sp().getInt("serverCount", 0);
    }

    public int getSeedCount() {
        return get_config_sp().getInt("seedCount", 0);
    }

    public int getAdCount() {
        return get_config_sp().getInt("adCount", 0);
    }

    public int LK() {
        return get_config_sp().getInt("apply_enter_studio", 0);
    }

    public void lm(int count) {
        Editor edit = get_config_sp().edit();
        edit.putInt("apply_enter_studio", count);
        edit.commit();
    }

    public boolean LL() {
        boolean ret = get_config_sp().getBoolean("fristLoadJs", true);
        Editor editor = get_config_sp().edit();
        editor.putBoolean("fristLoadJs", false);
        editor.commit();
        return ret;
    }

    public boolean LM() {
        boolean ret = get_config_sp().getBoolean("fristLoadWood", true);
        Editor editor = get_config_sp().edit();
        editor.putBoolean("fristLoadWood", false);
        editor.commit();
        return ret;
    }

    public boolean LN() {
        boolean ret = get_config_sp().getBoolean("fristLoadSkin", true);
        Editor editor = get_config_sp().edit();
        editor.putBoolean("fristLoadSkin", false);
        editor.commit();
        return ret;
    }

    public boolean LO() {
        boolean ret = get_config_sp().getBoolean("firstRecoverJsState", true);
        Editor editor = get_config_sp().edit();
        editor.putBoolean("firstRecoverJsState", false);
        editor.commit();
        return ret;
    }

    public int LP() {
        return get_config_sp().getInt("vercode", 0);
    }

    public void ln(int gameVerCode) {
        Editor editor = get_config_sp().edit();
        editor.putInt("lastGameStartVer", gameVerCode);
        editor.commit();
    }

    public int LQ() {
        return get_config_sp().getInt("lastGameStartVer", 0);
    }

    public void lo(int flag) {
        Editor editor = get_config_sp().edit();
        editor.putInt("lastGameRepairFlag", flag);
        editor.commit();
    }

    public int LR() {
        return get_config_sp().getInt("lastGameRepairFlag", 0);
    }

    public void lp(int vercode) {
        Editor editor = get_config_sp().edit();
        editor.putInt("vercode", vercode);
        editor.commit();
    }

    public String LS() {
        return get_config_sp().getString("loadapp", null);
    }

    public void LT() {
        Editor editor = get_config_sp().edit();
        editor.putString("loadapp", "1");
        editor.commit();
    }

    public void lq(int val) {
        Editor editor = get_config_sp().edit();
        editor.putInt(bmb, val);
        editor.commit();
    }

    public int LU() {
        return get_config_sp().getInt(bmb, 0);
    }

    public void a(j draft) {
        if (draft != null) {
            get_config_sp().edit().putString("resDraft", ag.toJsonString(draft)).commit();
        }
    }

    public j LV() {
        return (j) ag.toObjectNoExp(get_config_sp().getString("resDraft", ""), j.class);
    }

    public void LW() {
        get_config_sp().edit().remove("resDraft").commit();
    }

    public void a(d archive, String storyName) {
        if (archive != null) {
            get_config_sp().edit().putString("storyModeArchive_new_" + storyName, ag.toJsonString(archive)).commit();
        }
    }

    public d gd(String storyName) {
        return (d) ag.toObjectNoExp(get_config_sp().getString("storyModeArchive_new_" + storyName, ""), d.class);
    }

    public void ge(String storyName) {
        get_config_sp().edit().remove("storyModeArchive_new_" + storyName).commit();
    }

    public void a(int insZoneMode, com.huluxia.data.inszone.a scoreRecore) {
        if (scoreRecore != null) {
            get_config_sp().edit().putString("insZone" + insZoneMode, ag.toJsonString(scoreRecore)).commit();
        }
    }

    public com.huluxia.data.inszone.a lr(int insZoneMode) {
        return (com.huluxia.data.inszone.a) ag.toObjectNoExp(get_config_sp().getString("insZone" + insZoneMode, ""), com.huluxia.data.inszone.a.class);
    }

    public void bw(long count) {
        if (com.huluxia.data.j.ep().ey()) {
            long uid = com.huluxia.data.j.ep().getUserid();
            if (uid != 0) {
                com.huluxia.pref.b.Em().putLong(uid + "_followingMsgCount", count);
            }
        }
    }

    public long LX() {
        if (!com.huluxia.data.j.ep().ey()) {
            return 0;
        }
        long uid = com.huluxia.data.j.ep().getUserid();
        if (uid != 0) {
            return com.huluxia.pref.b.Em().getLong(uid + "_followingMsgCount");
        }
        return 0;
    }

    public long LY() {
        return com.huluxia.pref.b.Em().getLong("version_flag", 0);
    }

    public void bx(long versioncode) {
        com.huluxia.pref.b.Em().putLong("version_flag", versioncode);
    }

    public void dd(boolean val) {
        com.huluxia.pref.b.Em().putBoolean("newupdate", val);
    }

    public boolean LZ() {
        return com.huluxia.pref.b.Em().getBoolean("newupdate", true);
    }

    public c Ma() {
        String str = com.huluxia.pref.b.Em().getString("cloudidinfo");
        if (UtilsFunction.empty((CharSequence) str)) {
            return null;
        }
        c info = null;
        try {
            return (c) Json.parseJsonObject(str, c.class);
        } catch (Exception e) {
            return info;
        }
    }

    public void a(c info) {
        com.huluxia.pref.b.Em().put("cloudidinfo", ag.toJsonString(info));
    }

    public void Mb() {
        com.huluxia.pref.b.Em().remove("cloudidinfo");
    }

    public void de(boolean val) {
        com.huluxia.pref.b.Em().putBoolean("resetcloudid", val);
    }

    public boolean Mc() {
        return com.huluxia.pref.b.Em().getBoolean("resetcloudid", false);
    }
}
