package hlx.utils;

import hlx.data.localstore.a;

/* compiled from: LocalTimeUtil */
public class d {
    public static String nQ(int second) {
        String _StrTime = "";
        int _hour = (second / 3600) % 24;
        int _minute = (second / 60) % 60;
        int _second = second % 60;
        if (_hour > 9) {
            _StrTime = _StrTime + _hour + ":";
        } else if (_hour > 0) {
            _StrTime = _StrTime + "0" + _hour + ":";
        }
        if (_minute > 9) {
            _StrTime = _StrTime + _minute + ":";
        } else {
            _StrTime = _StrTime + "0" + _minute + ":";
        }
        if (_second > 9) {
            return _StrTime + _second;
        }
        return _StrTime + "0" + _second;
    }

    public static String nR(int second) {
        String _StrTime = "";
        int _hour = (second / 3600) % 24;
        int _minute = (second / 60) % 60;
        int _second = second % 60;
        if (_hour > 9) {
            _StrTime = _StrTime + _hour + a.bLs;
        } else if (_hour > 0) {
            _StrTime = _StrTime + "0" + _hour + a.bLs;
        }
        if (_minute > 9) {
            _StrTime = _StrTime + _minute + a.bLt;
        } else if (_minute > 0) {
            _StrTime = _StrTime + "0" + _minute + a.bLt;
        }
        if (_second > 9) {
            return _StrTime + _second + a.bLu;
        }
        return _StrTime + "0" + _second + a.bLu;
    }
}
