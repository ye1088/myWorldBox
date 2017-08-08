package hlx.ui.localresmgr.utils;

import com.MCWorld.widget.Constants.ReStartSoftFlag;

/* compiled from: ResUtils */
public class a {
    private static final int cbj = 7;
    private static final String cbk = ",";

    public static String hK(String vers) {
        String _name = "";
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_NORMAL.Value()))) {
            _name = hlx.data.localstore.a.bJq;
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0105.Value()))) {
            _name = hlx.data.localstore.a.bJr;
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0111.Value()))) {
            if (_name.length() > 0) {
                _name = _name + ",0.11";
            } else {
                _name = hlx.data.localstore.a.bJs;
            }
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0121.Value()))) {
            if (_name.length() > 0) {
                _name = _name + ",0.12";
            } else {
                _name = hlx.data.localstore.a.bJt;
            }
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0130.Value())) || vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0131.Value()))) {
            if (_name.length() > 0) {
                _name = _name + ",0.13";
            } else {
                _name = "0.13";
            }
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0140.Value()))) {
            if (_name.length() > 0) {
                _name = _name + ",0.14";
            } else {
                _name = "0.14";
            }
        }
        if (vers.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value()))) {
            if (_name.length() > 0) {
                _name = _name + ",0.15";
            } else {
                _name = hlx.data.localstore.a.bJy;
            }
        }
        if (6 == aO(_name, ",")) {
            return "通用";
        }
        return _name;
    }

    public static int aO(String str1, String str2) {
        if (str1.contains(str2)) {
            return aO(str1.substring(str1.indexOf(str2) + str2.length()), str2) + 1;
        }
        return 0;
    }
}
