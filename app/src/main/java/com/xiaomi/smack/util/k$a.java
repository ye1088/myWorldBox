package com.xiaomi.smack.util;

import android.text.TextUtils;

class k$a {
    public String a = "";
    public long b = 0;
    public int c = -1;
    public int d = -1;
    public String e = "";
    public long f = 0;

    public k$a(String str, long j, int i, int i2, String str2, long j2) {
        this.a = str;
        this.b = j;
        this.c = i;
        this.d = i2;
        this.e = str2;
        this.f = j2;
    }

    public boolean a(k$a com_xiaomi_smack_util_k_a) {
        return TextUtils.equals(com_xiaomi_smack_util_k_a.a, this.a) && TextUtils.equals(com_xiaomi_smack_util_k_a.e, this.e) && com_xiaomi_smack_util_k_a.c == this.c && com_xiaomi_smack_util_k_a.d == this.d && Math.abs(com_xiaomi_smack_util_k_a.b - this.b) <= 5000;
    }
}
