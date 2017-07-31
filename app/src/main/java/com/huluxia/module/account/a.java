package com.huluxia.module.account;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.data.j;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.utils.UtilsSystem;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.ab;
import com.huluxia.module.h;
import java.util.HashMap;
import java.util.Map;

/* compiled from: AccountModule */
public class a {
    private static final String TAG = "AccountModule";
    public static final int aBE = 1;
    public static final int aBF = 2;
    private static a aBG;

    /* compiled from: AccountModule */
    public static class a extends com.huluxia.module.a {
        public static final Creator<a> CREATOR = new 1();
        int harry = 0;
        int notice = 0;
        int shake = 0;
        int sound = 0;

        public boolean isNotify() {
            return isSucc() && this.notice == 1;
        }

        public boolean isHarry() {
            return isSucc() && this.harry == 1;
        }

        public boolean isSound() {
            return isSucc() && this.sound == 1;
        }

        public boolean isVibration() {
            return isSucc() && this.shake == 1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.notice);
            dest.writeInt(this.harry);
            dest.writeInt(this.sound);
            dest.writeInt(this.shake);
        }

        protected a(Parcel in) {
            super(in);
            this.notice = in.readInt();
            this.harry = in.readInt();
            this.sound = in.readInt();
            this.shake = in.readInt();
        }
    }

    private a() {
    }

    public static synchronized a DU() {
        a aVar;
        synchronized (a.class) {
            if (aBG == null) {
                aBG = new a();
            }
            aVar = aBG;
        }
        return aVar;
    }

    public void c(String token, String openid, String accsee_token, String qqinfo) {
        HashMap<String, String> param = new HashMap();
        param.put("_key", token);
        param.put("openid", openid);
        param.put("access_token", accsee_token);
        param.put("qqinfo", qqinfo);
        HttpMgr.getInstance().performPostStringRequest(ab.aAW, null, param, new 1(this), new 12(this), true, false);
    }

    public void h(String token, String openid, String qqinfo) {
        HashMap<String, String> param = new HashMap();
        param.put("_key", token);
        param.put("openid", openid);
        param.put("qqinfo", qqinfo);
        HttpMgr.getInstance().performPostStringRequest(ab.aAX, null, param, new 23(this), new 33(this), true, false);
    }

    public void a(String email, String password, String openid, String qqtoken, String qqinfo) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        param.put("password", password);
        param.put("openid", openid);
        param.put("access_token", qqtoken);
        param.put("qqinfo", qqinfo);
        HttpMgr.getInstance().performPostStringRequest(ab.aAY, null, param, new 34(this, email), new 35(this), true, false);
    }

    public void a(String email, String password, String sessionKey, String qqinfo) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        param.put("password", password);
        param.put("session_key", sessionKey);
        param.put("qqinfo", qqinfo);
        HttpMgr.getInstance().performPostStringRequest(ab.aAZ, null, param, new 36(this, email), new 37(this), true, false);
    }

    public void O(String email, String password) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        param.put("password", UtilsMD5.getMD5String(password));
        HttpMgr.getInstance().performPostStringRequest(ab.aBa, null, param, new 38(this), new 2(this), true, false);
    }

    public void DV() {
        HttpMgr.getInstance().performPostStringRequest(ab.aBb, null, null, new 3(this), new 4(this), true, false);
    }

    public void DW() {
        HttpMgr.getInstance().performPostStringRequest(ab.aBi, null, null, new 5(this), new 6(this), true, false);
    }

    public void P(String openid, String qtoken) {
        HashMap<String, String> param = new HashMap();
        param.put("openid", openid);
        param.put("access_token", qtoken);
        HttpMgr.getInstance().performStringRequest(ab.aBc, param, new 7(this), new 8(this));
    }

    public void Q(String openid, String qqtoken) {
        HashMap<String, String> param = new HashMap();
        param.put("openid", openid);
        param.put("access_token", qqtoken);
        HttpMgr.getInstance().performPostStringRequest(ab.aBd, null, param, new 9(this), new 10(this), true, false);
    }

    public void eb(String email) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        HttpMgr.getInstance().performPostStringRequest(ab.aBj, null, param, new 11(this), new 13(this), true, false);
    }

    public void DX() {
        if (j.ep().ey()) {
            Map map = null;
            HttpMgr.getInstance().performPostStringRequest(ab.aBk, null, map, new 14(this, j.ep().getUserid()), new 15(this), true, false);
        }
    }

    public void a(boolean notice, int type_id) {
        if (j.ep().ey()) {
            HashMap<String, String> param = new HashMap();
            param.put("notice_status", notice ? "1" : "0");
            param.put("type_id", String.valueOf(type_id));
            HttpMgr.getInstance().performPostStringRequest(ab.aBm, null, param, new 16(this, notice, type_id), new 17(this, notice, type_id), true, false);
        }
    }

    public void DY() {
        x(0, 1, 0, 0);
    }

    public void x(int notice, int harry, int sound, int shake) {
        HLog.info(TAG, "sendMsgCount enter", new Object[0]);
        if (j.ep().ey()) {
            if (notice == 0) {
                sound = 0;
                shake = 0;
            }
            if (harry == 1) {
                int hour = UtilsSystem.getNowHour();
                if (hour >= 23 || hour < 8) {
                    sound = 0;
                    shake = 0;
                    harry = 1;
                } else {
                    harry = 0;
                }
            }
            HttpMgr.getInstance().performPostStringRequest(ab.aBh, null, null, new 18(this, notice == 1, harry == 1, sound == 1, shake == 1), new 19(this), true, false);
        }
    }

    public void L(String start, int count) {
        b(1, start, count);
    }

    public void M(String start, int count) {
        b(2, start, count);
    }

    private void b(int type, String start, int count) {
        if (j.ep().ey()) {
            HashMap<String, String> param = new HashMap();
            param.put("type_id", String.valueOf(type));
            param.put("start", start);
            param.put("count", String.valueOf(count));
            int httpEvent = type == 1 ? h.arE : h.arF;
            HttpMgr.getInstance().performPostStringRequest(ab.aAf, null, param, new 20(this, type, httpEvent, start), new 21(this, httpEvent, start), true, false);
        }
    }

    public void a(long post_id, boolean isTopic, String score, String score_txt) {
        int type_id = isTopic ? 1 : 2;
        HashMap<String, String> param = new HashMap();
        param.put("post_id", String.valueOf(post_id));
        param.put("type_id", String.valueOf(type_id));
        param.put("isadmin", "0");
        param.put("score", score);
        param.put("score_txt", score_txt);
        HttpMgr.getInstance().performPostStringRequest(ab.aBo, null, param, new 22(this), new 24(this), true, false, true, 10000);
    }

    public void i(String email, String openid, String access_token) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        param.put("openid", openid);
        param.put("access_token", access_token);
        HttpMgr.getInstance().performPostStringRequest(ab.aBq, null, param, new 25(this), new 26(this), true, false, true, 10000);
    }

    public void R(String email, String vcode) {
        HashMap<String, String> param = new HashMap();
        param.put("email", email);
        param.put("voice_code", vcode);
        HttpMgr.getInstance().performPostStringRequest(ab.aBl, null, param, new 27(this), new 28(this), true, false);
    }

    public void j(String account, String openid, String qtoken) {
        HashMap<String, String> param = new HashMap();
        param.put("email", account);
        param.put("openid", openid);
        param.put("access_token", qtoken);
        HttpMgr.getInstance().performStringRequest(ab.aBg, param, new 29(this), new 30(this));
    }

    public void d(String account, String password, String openid, String qtoken) {
        HashMap<String, String> param = new HashMap();
        param.put("email", account);
        param.put("password", UtilsMD5.MD5Code(password));
        param.put("openid", openid);
        param.put("access_token", qtoken);
        HttpMgr.getInstance().performStringRequest(ab.aBf, param, new 31(this), new 32(this));
    }
}
