package com.huawei.android.pushselfshow.b;

import android.text.TextUtils;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements Serializable {
    public String A;
    public String B;
    public String C;
    public String D;
    public String E = "";
    public int F = 1;
    public int G = 0;
    public String H;
    public String I;
    public String J;
    public int K = com.huawei.android.pushselfshow.c.a.STYLE_1.ordinal();
    public int L = 0;
    public String[] M = null;
    public String[] N = null;
    public String[] O = null;
    public int P = 0;
    public String[] Q = null;
    public String R = "";
    public String S = "";
    public String a;
    public String b;
    public String c;
    public String d;
    public int e;
    public String f;
    public int g;
    public String h;
    public int i;
    public int j;
    public String k;
    public String l = "";
    public String m;
    public String n;
    public String o;
    public String p;
    public String q = "";
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    public String x;
    public String y = "";
    public String z;

    public a(byte[] bArr, byte[] bArr2) {
        try {
            this.I = new String(bArr, "UTF-8");
            this.J = new String(bArr2, "UTF-8");
        } catch (Exception e) {
            e.d("PushSelfShowLog", "get msg byte arr error");
        }
    }

    private boolean a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("param");
            if (jSONObject2.has("autoClear")) {
                this.e = jSONObject2.getInt("autoClear");
            } else {
                this.e = 0;
            }
            if (PushConstants.EXTRA_APP.equals(this.o) || "cosa".equals(this.o)) {
                if (jSONObject2.has("acn")) {
                    this.A = jSONObject2.getString("acn");
                    this.f = this.A;
                }
                if (jSONObject2.has("intentUri")) {
                    this.f = jSONObject2.getString("intentUri");
                }
                if (jSONObject2.has("appPackageName")) {
                    this.z = jSONObject2.getString("appPackageName");
                } else {
                    e.a("PushSelfShowLog", "appPackageName is null");
                    return false;
                }
            } else if ("email".equals(this.o)) {
                if (jSONObject2.has("emailAddr") && jSONObject2.has("emailSubject")) {
                    this.w = jSONObject2.getString("emailAddr");
                    this.x = jSONObject2.getString("emailSubject");
                    if (jSONObject2.has("emailContent")) {
                        this.y = jSONObject2.getString("emailContent");
                    }
                } else {
                    e.a("PushSelfShowLog", "emailAddr or emailSubject is null");
                    return false;
                }
            } else if (com.huluxia.data.profile.a.qe.equals(this.o)) {
                if (jSONObject2.has("phoneNum")) {
                    this.v = jSONObject2.getString("phoneNum");
                } else {
                    e.a("PushSelfShowLog", "phoneNum is null");
                    return false;
                }
            } else if ("url".equals(this.o)) {
                if (jSONObject2.has("url")) {
                    this.B = jSONObject2.getString("url");
                    if (jSONObject2.has("inBrowser")) {
                        this.F = jSONObject2.getInt("inBrowser");
                    }
                    if (jSONObject2.has("needUserId")) {
                        this.G = jSONObject2.getInt("needUserId");
                    }
                    if (jSONObject2.has("sign")) {
                        this.H = jSONObject2.getString("sign");
                    }
                    if (jSONObject2.has("rpt") && jSONObject2.has("rpl")) {
                        this.C = jSONObject2.getString("rpl");
                        this.D = jSONObject2.getString("rpt");
                        if (jSONObject2.has("rpct")) {
                            this.E = jSONObject2.getString("rpct");
                        }
                    }
                } else {
                    e.a("PushSelfShowLog", "url is null");
                    return false;
                }
            } else if ("rp".equals(this.o)) {
                if (jSONObject2.has("rpt") && jSONObject2.has("rpl")) {
                    this.C = jSONObject2.getString("rpl");
                    this.D = jSONObject2.getString("rpt");
                    if (jSONObject2.has("rpct")) {
                        this.E = jSONObject2.getString("rpct");
                    }
                    if (jSONObject2.has("needUserId")) {
                        this.G = jSONObject2.getInt("needUserId");
                    }
                } else {
                    e.a("PushSelfShowLog", "rpl or rpt is null");
                    return false;
                }
            }
            return true;
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "ParseParam error ", e);
            return false;
        }
    }

    private boolean b(JSONObject jSONObject) {
        e.a("PushSelfShowLog", "enter parseNotifyParam");
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("notifyParam");
            if (!jSONObject2.has("style")) {
                return false;
            }
            int i;
            String str;
            this.K = jSONObject2.getInt("style");
            e.a("PushSelfShowLog", "style:" + this.K);
            if (jSONObject2.has("btnCount")) {
                this.L = jSONObject2.getInt("btnCount");
            }
            if (this.L > 0) {
                if (this.L > 3) {
                    this.L = 3;
                }
                e.a("PushSelfShowLog", "btnCount:" + this.L);
                this.M = new String[this.L];
                this.N = new String[this.L];
                this.O = new String[this.L];
                for (i = 0; i < this.L; i++) {
                    str = "btn" + (i + 1) + "Text";
                    String str2 = "btn" + (i + 1) + "Image";
                    String str3 = "btn" + (i + 1) + "Event";
                    if (jSONObject2.has(str)) {
                        this.M[i] = jSONObject2.getString(str);
                    }
                    if (jSONObject2.has(str2)) {
                        this.N[i] = jSONObject2.getString(str2);
                    }
                    if (jSONObject2.has(str3)) {
                        this.O[i] = jSONObject2.getString(str3);
                    }
                }
            }
            com.huawei.android.pushselfshow.c.a aVar = com.huawei.android.pushselfshow.c.a.STYLE_1;
            if (this.K >= 0 && this.K < com.huawei.android.pushselfshow.c.a.values().length) {
                aVar = com.huawei.android.pushselfshow.c.a.values()[this.K];
            }
            switch (aVar) {
                case STYLE_4:
                    if (jSONObject2.has("iconCount")) {
                        this.P = jSONObject2.getInt("iconCount");
                    }
                    if (this.P > 0) {
                        if (this.P > 6) {
                            this.P = 6;
                        }
                        e.a("PushSelfShowLog", "iconCount:" + this.P);
                        this.Q = new String[this.P];
                        for (i = 0; i < this.P; i++) {
                            str = "icon" + (i + 1);
                            if (jSONObject2.has(str)) {
                                this.Q[i] = jSONObject2.getString(str);
                            }
                        }
                        break;
                    }
                    break;
                case STYLE_5:
                    if (jSONObject2.has("subTitle")) {
                        this.R = jSONObject2.getString("subTitle");
                        e.a("PushSelfShowLog", "subTitle:" + this.R);
                        break;
                    }
                    break;
                case STYLE_6:
                case STYLE_8:
                    if (jSONObject2.has("bigPic")) {
                        this.S = jSONObject2.getString("bigPic");
                        e.a("PushSelfShowLog", "bigPicUrl:" + this.S);
                        break;
                    }
                    break;
            }
            return true;
        } catch (JSONException e) {
            e.b("PushSelfShowLog", e.toString());
            return false;
        }
    }

    public String a() {
        e.a("PushSelfShowLog", "msgId =" + this.l);
        return this.l;
    }

    public boolean b() {
        try {
            if (this.J == null || this.J.length() == 0) {
                e.a("PushSelfShowLog", "token is null");
                return false;
            }
            this.h = this.J;
            if (this.I == null || this.I.length() == 0) {
                e.a("PushSelfShowLog", "msg is null");
                return false;
            }
            JSONObject jSONObject = new JSONObject(this.I);
            this.g = jSONObject.getInt("msgType");
            if (this.g != 1) {
                e.a("PushSelfShowLog", "not a selefShowMsg");
                return false;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("msgContent");
            if (jSONObject2 == null) {
                e.b("PushSelfShowLog", "msgObj == null");
                return false;
            } else if (jSONObject2.has("msgId")) {
                Object obj = jSONObject2.get("msgId");
                if (obj instanceof String) {
                    this.l = (String) obj;
                } else if (obj instanceof Integer) {
                    this.l = String.valueOf(((Integer) obj).intValue());
                }
                if (jSONObject2.has("dispPkgName")) {
                    this.m = jSONObject2.getString("dispPkgName");
                }
                if (jSONObject2.has("rtn")) {
                    this.j = jSONObject2.getInt("rtn");
                } else {
                    this.j = 1;
                }
                if (jSONObject2.has("fm")) {
                    this.i = jSONObject2.getInt("fm");
                } else {
                    this.i = 1;
                }
                if (jSONObject2.has("ap")) {
                    String string = jSONObject2.getString("ap");
                    StringBuilder stringBuilder = new StringBuilder();
                    if (TextUtils.isEmpty(string) || string.length() >= 48) {
                        this.k = string.substring(0, 48);
                    } else {
                        for (int i = 0; i < 48 - string.length(); i++) {
                            stringBuilder.append("0");
                        }
                        stringBuilder.append(string);
                        this.k = stringBuilder.toString();
                    }
                }
                if (jSONObject2.has("extras")) {
                    this.n = jSONObject2.getJSONArray("extras").toString();
                }
                if (!jSONObject2.has("psContent")) {
                    return false;
                }
                jSONObject = jSONObject2.getJSONObject("psContent");
                if (jSONObject == null) {
                    return false;
                }
                this.o = jSONObject.getString("cmd");
                if (jSONObject.has("content")) {
                    this.p = jSONObject.getString("content");
                } else {
                    this.p = "";
                }
                if (jSONObject.has("notifyIcon")) {
                    this.q = jSONObject.getString("notifyIcon");
                } else {
                    this.q = "" + this.l;
                }
                if (jSONObject.has("statusIcon")) {
                    this.s = jSONObject.getString("statusIcon");
                }
                if (jSONObject.has("notifyTitle")) {
                    this.r = jSONObject.getString("notifyTitle");
                }
                if (jSONObject.has("notifyParam")) {
                    b(jSONObject);
                }
                return jSONObject.has("param") ? a(jSONObject) : false;
            } else {
                e.b("PushSelfShowLog", "msgId == null");
                return false;
            }
        } catch (Throwable e) {
            e.a("PushSelfShowLog", e.toString(), e);
            return false;
        }
    }

    public byte[] c() {
        try {
            String str = "";
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("autoClear", this.e);
            jSONObject4.put("s", this.a);
            jSONObject4.put(net.lingala.zip4j.util.e.clf, this.b);
            jSONObject4.put("smsC", this.c);
            jSONObject4.put("mmsUrl", this.d);
            jSONObject4.put("url", this.B);
            jSONObject4.put("inBrowser", this.F);
            jSONObject4.put("needUserId", this.G);
            jSONObject4.put("sign", this.H);
            jSONObject4.put("rpl", this.C);
            jSONObject4.put("rpt", this.D);
            jSONObject4.put("rpct", this.E);
            jSONObject4.put("appPackageName", this.z);
            jSONObject4.put("acn", this.A);
            jSONObject4.put("intentUri", this.f);
            jSONObject4.put("emailAddr", this.w);
            jSONObject4.put("emailSubject", this.x);
            jSONObject4.put("emailContent", this.y);
            jSONObject4.put("phoneNum", this.v);
            jSONObject4.put("replyToSms", this.u);
            jSONObject4.put("smsNum", this.t);
            jSONObject3.put("cmd", this.o);
            jSONObject3.put("content", this.p);
            jSONObject3.put("notifyIcon", this.q);
            jSONObject3.put("notifyTitle", this.r);
            jSONObject3.put("statusIcon", this.s);
            jSONObject3.put("param", jSONObject4);
            jSONObject2.put("dispPkgName", this.m);
            jSONObject2.put("msgId", this.l);
            jSONObject2.put("fm", this.i);
            jSONObject2.put("ap", this.k);
            jSONObject2.put("rtn", this.j);
            jSONObject2.put("psContent", jSONObject3);
            if (this.n != null && this.n.length() > 0) {
                jSONObject2.put("extras", new JSONArray(this.n));
            }
            jSONObject.put("msgType", this.g);
            jSONObject.put("msgContent", jSONObject2);
            return jSONObject.toString().getBytes("UTF-8");
        } catch (Throwable e) {
            e.a("PushSelfShowLog", "getMsgData failed JSONException:", e);
            return new byte[0];
        } catch (Throwable e2) {
            e.a("PushSelfShowLog", "getMsgData failed UnsupportedEncodingException:", e2);
            return new byte[0];
        }
    }

    public byte[] d() {
        try {
            if (this.h != null && this.h.length() > 0) {
                return this.h.getBytes("UTF-8");
            }
        } catch (Throwable e) {
            e.a("PushSelfShowLog", "getToken getByte failed ", e);
        }
        return new byte[0];
    }
}
