package com.huluxia.data;

import android.util.Log;
import com.huluxia.HTApplication;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.login.d;
import com.huluxia.login.k;
import com.huluxia.utils.ah;
import com.huluxia.utils.ax;
import com.huluxia.widget.Constants;
import com.huluxia.widget.Constants.MiVer;
import org.json.JSONException;

/* compiled from: Session */
public class j {
    private static j py = null;
    private k pq;
    private l pr;
    private String pu = "";
    private boolean pv = false;
    private int pw = 0;
    private l px;

    public static j ep() {
        if (py == null) {
            py = new j();
        }
        return py;
    }

    public void a(k sessionInfo) {
        this.pq = sessionInfo;
        if (sessionInfo != null && sessionInfo._key != null) {
            ah.KZ().setToken(sessionInfo._key);
            ah.KZ().a(sessionInfo.user);
        }
    }

    public void eq() {
        ep().clear();
    }

    public l er() {
        boolean notXiaomi;
        boolean validMiUid;
        CharSequence historyAccount = ah.KZ().pZ();
        CharSequence historyPwd = ah.KZ().getPassword();
        if (ax.MG() == MiVer.nomi || !HTApplication.bJ_mctool_huluxia_string().equals(Constants.btb)) {
            notXiaomi = true;
        } else {
            notXiaomi = false;
        }
        if (ah.KZ().fH() != 0) {
            validMiUid = true;
        } else {
            validMiUid = false;
        }
        HLog.verbose(this, "get session info history account %s, password %s, not xiaomi %b, validmiuid %b", historyAccount, historyPwd, Boolean.valueOf(notXiaomi), Boolean.valueOf(validMiUid));
        if (UtilsFunction.empty(historyAccount) && UtilsFunction.empty(historyPwd) && !notXiaomi && validMiUid) {
            this.pr = es();
            if (this.pr != null) {
                return this.pr;
            }
        }
        if (this.pr == null) {
            k session = d.pR().pS();
            if (session != null) {
                try {
                    this.pr = new l(session);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d("[session]", "local key: " + String.valueOf(this.pr));
        }
        return this.pr;
    }

    public void a(l sessionInfo) {
        this.px = sessionInfo;
        if (sessionInfo != null) {
            ah.KZ().b(sessionInfo);
        }
    }

    private l es() {
        if (this.px == null) {
            this.px = ah.KZ().Lj();
        }
        return this.px;
    }

    public String et() {
        if (this.pu == null || this.pu.length() == 0) {
            return null;
        }
        return this.pu;
    }

    public void ay(String cloudUserID) {
        this.pu = cloudUserID;
    }

    public void eu() {
        this.pu = "";
    }

    public int ev() {
        return this.pw;
    }

    public void P(int msgMode) {
        this.pw = msgMode;
    }

    public g ew() {
        return ah.KZ().ew();
    }

    public long getUserid() {
        g info = ew();
        if (info != null) {
            return info.userID;
        }
        return 0;
    }

    public String getNick() {
        g info = ew();
        if (info != null) {
            return info.nick;
        }
        return "";
    }

    public boolean ex() {
        return this.pv;
    }

    public void D(boolean isBind) {
        this.pv = isBind;
    }

    public int getRole() {
        g info = ew();
        if (info != null) {
            return info.role;
        }
        return 0;
    }

    public String getKey() {
        return ah.KZ().getToken();
    }

    public String getAvatar() {
        g info = ew();
        if (info != null) {
            return info.avatar;
        }
        return "";
    }

    public String getToken() {
        return ah.KZ().getToken();
    }

    public boolean ey() {
        return ep().getToken() != null;
    }

    public void clear() {
        try {
            this.pr = null;
            ah.KZ().Lg();
            ah.KZ().Li();
            ah.KZ().Lh();
            ah.KZ().Ld();
            ah.KZ().Le();
            D(false);
            ah.KZ().Lb();
            ah.KZ().La();
            ah.KZ().Lc();
        } catch (Exception e) {
        }
    }

    public int getLevel() {
        g info = ew();
        if (info != null) {
            return info.level;
        }
        return 0;
    }
}
