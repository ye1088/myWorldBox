package hlx.launch.game;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.mcinterface.e;
import com.MCWorld.mcinterface.h;
import com.MCWorld.r;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.j;
import com.MCWorld.utils.u;
import com.MCWorld.widget.Constants;
import com.MCWorld.widget.dialog.g;
import hlx.ui.a;

/* compiled from: MCPreLaunchCheck */
public class d {
    private static void d(int ver, Context context) {
        if (ver == 8 || UtilsVersion.hasNouga() || ((ver == 7 || ver == 5) && !c.Sg().Si())) {
            Su();
        } else {
            a.ch(context);
        }
    }

    public static void bR(Context context) {
        int _tmpVersion = c.Sg().Sh();
        if (mK(_tmpVersion)) {
            d(_tmpVersion, context);
        } else {
            bS(context);
        }
    }

    public static void f(Context context, String strVersions, int resType) {
        if (mK(c.Sg().Sh())) {
            h(context, strVersions, resType);
        } else {
            bS(context);
        }
    }

    public static void g(Context context, String strVersions, int resType) {
        if (mK(c.Sg().Sh())) {
            h(context, strVersions, resType);
        } else {
            bS(context);
        }
    }

    private static void h(Context context, String strVersions, int resType) {
        int _tmpVersion = c.Sg().Sh();
        if (_tmpVersion == 10) {
            c(context, strVersions, true);
            return;
        }
        switch (resType) {
            case 1:
                d(_tmpVersion, context);
                return;
            case 2:
            case 4:
                if (aE(strVersions, c.Sg().getMCVersion(true))) {
                    d(_tmpVersion, context);
                    return;
                } else {
                    c(context, strVersions, false);
                    return;
                }
            case 3:
                d(_tmpVersion, context);
                return;
            default:
                return;
        }
    }

    public static void b(Context context, com.MCWorld.data.server.a.a info) {
        boolean z = true;
        int _tmpVersion = c.Sg().Sh();
        if (!mK(_tmpVersion)) {
            bS(context);
        } else if (aE(info.ver, c.Sg().getMCVersion(true))) {
            h.g(info.name, info.addr, String.valueOf(info.port));
            d(_tmpVersion, context);
        } else {
            String str = info.ver;
            if (c.Sg().Sh() != 10) {
                z = false;
            }
            c(context, str, z);
        }
    }

    private static void Su() {
        r.ck().K_umengEvent(hlx.data.tongji.a.bNT);
        u.C("com.mojang.minecraftpe");
    }

    public static boolean aE(String allVer, String subVer) {
        return allVer == null || allVer.length() == 0 || allVer.contains(subVer);
    }

    private static void c(Context context, String versions, boolean isStoryMode) {
        r.ck().K_umengEvent(hlx.data.tongji.a.bNQ);
        g dia = new g((Activity) context, new 1(isStoryMode, context));
        if (isStoryMode) {
            dia.az(hlx.data.localstore.a.bKA_TIPS, String.format(hlx.data.localstore.a.bKG, new Object[]{hlx.data.localstore.a.bJA, versions}));
            dia.u(hlx.data.localstore.a.bKE, null, hlx.data.localstore.a.bKB_bt_cancel);
        } else {
            dia.az(hlx.data.localstore.a.bKA_TIPS, String.format(hlx.data.localstore.a.bKG, new Object[]{c.Sg().getMCVersion(true), versions}));
            dia.u(hlx.data.localstore.a.bKE, null, hlx.data.localstore.a.bKD);
        }
        dia.showDialog();
    }

    public static boolean mK(int version) {
        boolean _tmpFlag = false;
        String zipPath;
        String _MD5;
        switch (version) {
            case 0:
                zipPath = UtilsFile.get_mctool_path() + Constants.bsu + ".zip";
                _MD5 = e.ajc;
                if (j.isExist(zipPath) && j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                break;
            case 1:
                zipPath = UtilsFile.get_mctool_path() + Constants.bsv + ".zip";
                _MD5 = e.ajd;
                if (j.isExist(zipPath) && j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                break;
            case 2:
                zipPath = UtilsFile.get_mctool_path() + Constants.bsw + ".zip";
                if (VERSION.SDK_INT < 11) {
                    _MD5 = e.aiY;
                } else {
                    _MD5 = e.aja;
                }
                if (j.isExist(zipPath) && j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                break;
            case 3:
                zipPath = UtilsFile.get_mctool_path() + Constants.bsx + ".zip";
                if (VERSION.SDK_INT < 11) {
                    _MD5 = e.aiZ;
                } else {
                    _MD5 = e.ajb;
                }
                if (j.isExist(zipPath) && j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                break;
            case 4:
                zipPath = UtilsFile.get_mctool_path() + Constants.bsy + ".zip";
                if (VERSION.SDK_INT < 11) {
                    _MD5 = e.aiZ;
                } else {
                    _MD5 = e.aje;
                }
                if (j.isExist(zipPath) && j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                break;
            case 5:
                _tmpFlag = b.a_isRightVersion(u.getPackageManager(), "com.mojang.minecraftpe", e.ahK_v0141);
                if (UtilsFile.fe(hlx.data.localstore.a.bJP) < hlx.data.localstore.a.bJQ || UtilsFile.fe(hlx.data.localstore.a.bJP) > hlx.data.localstore.a.bJR) {
                    UtilsFile.deleteFile(hlx.data.localstore.a.bJP);
                    HLog.error("TAG", "DTPrint 14 check so size error!", new Object[0]);
                    break;
                }
            case 7:
                _tmpFlag = b.a_isRightVersion(u.getPackageManager(), "com.mojang.minecraftpe", e.ahL_v01540);
                _MD5 = hlx.data.localstore.a.bJW;
                if (j.isExist(hlx.data.localstore.a.bJP)) {
                    if (!j.getFileMD5(hlx.data.localstore.a.bJP).equalsIgnoreCase(_MD5)) {
                        UtilsFile.deleteFile(hlx.data.localstore.a.bJP);
                        HLog.error("TAG", "DTPrint 15 check so md5 error!", new Object[0]);
                    } else if (UtilsFile.fe(hlx.data.localstore.a.bJP) < 23000000 || UtilsFile.fe(hlx.data.localstore.a.bJP) > 24000000) {
                        UtilsFile.deleteFile(hlx.data.localstore.a.bJP);
                        HLog.error("TAG", "DTPrint 15 check so size error!", new Object[0]);
                    }
                }
                if (UtilsFile.fe(hlx.data.localstore.a.bJP) < 23000000 || UtilsFile.fe(hlx.data.localstore.a.bJP) > 24000000) {
                    UtilsFile.deleteFile(hlx.data.localstore.a.bJP);
                    HLog.error("TAG", "DTPrint 15 check so size error!", new Object[0]);
                    break;
                }
            case 8:
                _tmpFlag = b.a_isRightVersion(u.getPackageManager(), "com.mojang.minecraftpe", e.ahM_v01410);
                String mcOptionsPath = j.Kz();
                if (j.isExist(mcOptionsPath)) {
                    UtilsFile.deleteFile(mcOptionsPath);
                    break;
                }
                break;
        }
        return _tmpFlag;
    }

    private static void bS(Context context) {
        r.ck().K_umengEvent(hlx.data.tongji.a.bNP);
        new DialogManager(context).showOkCancelDialog(hlx.data.localstore.a.bKA_TIPS, (CharSequence) "当前版本没有检测到资源包，请下载或切换版本", hlx.data.localstore.a.bKC_bt_ok, hlx.data.localstore.a.bKB_bt_cancel, true, new 2(context));
    }
}
