package com.huawei.android.pushagent.a;

public class a {
    public String a;
    public Object b;
    public Class c;

    public a(String str, Class cls, Object obj) {
        this.a = str;
        this.c = cls;
        this.b = obj;
    }

    public a(String str, Class cls, String str2) {
        this.a = str;
        this.c = cls;
        a(str2);
    }

    public void a(String str) {
        if (String.class == this.c) {
            this.b = str;
        } else if (Integer.class == this.c) {
            this.b = Integer.valueOf(Integer.parseInt(str));
        } else if (Long.class == this.c) {
            this.b = Long.valueOf(Long.parseLong(str));
        } else if (Boolean.class == this.c) {
            this.b = Boolean.valueOf(Boolean.parseBoolean(str));
        } else {
            this.b = null;
        }
    }

    public String toString() {
        return new StringBuffer().append(this.a).append(":").append(this.b).append(":").append(this.c.getSimpleName()).toString();
    }
}
