package com.MCWorld.utils;

class UtilsWifiDatabase$a {
    public long bnJ;
    public String bnK;
    public String bnL;
    public String password;

    public void clear() {
        String str = "";
        this.bnL = str;
        this.password = str;
        this.bnK = str;
        this.bnJ = 0;
    }

    UtilsWifiDatabase$a() {
        clear();
    }

    UtilsWifiDatabase$a(UtilsWifiDatabase$a old) {
        this.bnK = old.bnK;
        this.bnL = old.bnL;
        this.password = old.password;
    }
}
