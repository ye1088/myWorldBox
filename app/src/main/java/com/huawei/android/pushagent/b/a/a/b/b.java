package com.huawei.android.pushagent.b.a.a.b;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.b.j;
import com.huawei.android.pushagent.a.e;
import com.huawei.android.pushagent.b.a.a;
import com.huawei.android.pushagent.b.a.a$a;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.h;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.mm.sdk.platformtools.LocaleUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.net.Socket;
import java.util.Set;
import org.apache.tools.ant.taskdefs.WaitFor;

public class b extends com.huawei.android.pushagent.b.a.a.b {
    public long e = 7200000;
    private boolean f = false;
    private long g = this.e;
    private long h = this.e;
    private int i = 0;
    private String j = "";
    private String k = "";
    private String l = null;

    public b(Context context) {
        super(context);
    }

    private void a(e eVar, String str) {
        Object obj = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                Object obj2;
                Set<String> keySet = eVar.S().keySet();
                if (keySet.size() > 0) {
                    for (String str2 : keySet) {
                        if (str2.contains(str)) {
                            String str3 = (String) eVar.S().get(str2);
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "apnName is:" + str2 + ",apnHeartBeat is:" + str3);
                            String[] split = str3.split("_");
                            this.g = Long.parseLong(split[0]) * 1000;
                            this.h = Long.parseLong(split[1]) * 1000;
                            obj2 = 1;
                            break;
                        }
                    }
                }
                obj2 = null;
                obj = obj2;
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e.toString(), e);
            }
        }
        if (obj == null) {
            this.g = eVar.i() * 1000;
            this.h = eVar.j() * 1000;
        }
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "after all, minHeartBeat is :" + this.g + ",maxHeartBeat is:" + this.h);
    }

    private String j() {
        String str = "";
        try {
            if (a.e() != null) {
                Socket c = a.e().c();
                if (c != null) {
                    str = c.getLocalAddress().getHostAddress();
                }
            }
        } catch (Exception e) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", e.toString());
        }
        return str == null ? "" : str;
    }

    private Long k() {
        String a = c.a(this.c, "cloudpush_fixHeatBeat", "");
        try {
            long parseLong = 1000 * Long.parseLong(a.trim());
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get heart beat from config, value:" + parseLong + " so neednot ajust");
            return Long.valueOf(parseLong);
        } catch (NumberFormatException e) {
            if ((2 == this.d && 5 != this.d) || 1 != com.huawei.android.pushagent.c.a.b.a(this.c)) {
                return null;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "in wifi and in charging, cannot ajust heartBeat");
            return Long.valueOf(60000);
        } catch (Throwable e2) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get cloudpush_fixHeatBeat:" + a + " cause:" + e2.toString(), e2);
            if (2 == this.d) {
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "in wifi and in charging, cannot ajust heartBeat");
            return Long.valueOf(60000);
        }
    }

    private boolean l() {
        int a = com.huawei.android.pushagent.c.a.b.a(this.c);
        String h = com.huawei.android.pushagent.c.a.h(this.c);
        String j = com.huawei.android.pushagent.c.a.j(this.c);
        if (1 == a) {
            j = "wifi";
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("HasFindHeartBeat_" + h + "_" + a + "_" + j, Boolean.valueOf(this.f));
        contentValues.put("HearBeatInterval_" + h + "_" + a + "_" + j, Long.valueOf(this.e));
        contentValues.put("ClientIP_" + h + "_" + a, this.l);
        if (this.f) {
            j = com.huawei.android.pushagent.c.a.a(System.currentTimeMillis() + com.huawei.android.pushagent.b.b.a.a(this.c).ad(), "yyyy-MM-dd HH:mm:ss SSS");
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "when find best heart beat,save the valid end time " + j + " to xml.");
            contentValues.put("HeartBeatValid", j);
        }
        return new h(this.c, c()).a(contentValues);
    }

    public long b(boolean z) {
        if (-1 == com.huawei.android.pushagent.c.a.b.a(this.c)) {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "no network, use no network heartbeat");
            return com.huawei.android.pushagent.b.b.a.a(this.c).p() * 1000;
        }
        Long k = k();
        if (k != null) {
            return k.longValue();
        }
        if (h()) {
            i();
        }
        long j = this.e;
        if (this.f) {
            return j;
        }
        j = z ? this.e : this.e + com.MCWorld.video.recorder.b.bpg;
        return j <= this.g ? this.g : j >= this.h ? this.h : j;
    }

    public boolean b(long j) {
        return true;
    }

    public String c() {
        return "PushHearBeat";
    }

    public void c(boolean z) {
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "enter adjustHeartBeat:(findHeartBeat:" + this.f + " RspTimeOut:" + z + " beatInterval:" + this.e + " range:[" + this.g + MiPushClient.ACCEPT_TIME_SEPARATOR + this.h + "]," + "isHearBeatTimeReq:" + this.b + " batteryStatus:" + this.d + ")");
        if (k() != null || this.f) {
            return;
        }
        if (this.b) {
            a(false);
            this.e = b(z);
            if (z || this.e <= this.g || this.e >= this.h) {
                this.f = true;
                com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "after all the best heartBeat Interval:" + this.e + LocaleUtil.MALAY);
            } else {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "set current heartBeatInterval " + this.e + LocaleUtil.MALAY);
            }
            l();
            return;
        }
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "It is not hearBeatTimeReq");
    }

    public /* synthetic */ com.huawei.android.pushagent.b.a.a.b f() {
        return i();
    }

    public void g() {
        try {
            com.huawei.android.pushagent.c.a.a.c(PushService.a().getContext(), new Intent("com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT").putExtra("timer_reason", "timeOutWaitPushSrvRsp").putExtra("connect_mode", a$a.ConnectEntity_Push.ordinal()).setPackage(this.c.getPackageName()), com.huawei.android.pushagent.b.b.a.a(this.c).Q());
            a(System.currentTimeMillis());
            com.huawei.android.pushagent.a.b jVar = new j();
            jVar.a((byte) ((int) Math.ceil((((double) b(false)) * 1.0d) / 60000.0d)));
            a.e().a(jVar);
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call pushChannel.send cause Exception:" + e.toString(), e);
        }
    }

    protected boolean h() {
        int a = com.huawei.android.pushagent.c.a.b.a(this.c);
        String h = com.huawei.android.pushagent.c.a.h(this.c);
        switch (a) {
            case 0:
                return (a == this.i && h.equals(this.j) && com.huawei.android.pushagent.c.a.j(this.c).equals(this.k)) ? false : true;
            case 1:
                return (a == this.i && h.equals(this.j) && j().equals(this.l)) ? false : true;
            default:
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "isEnvChange:netType:" + a + false);
                return false;
        }
    }

    public b i() {
        try {
            if (a.e() == null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "system is in start, wait net for heartBeat");
                return null;
            }
            String asString;
            this.l = j();
            ContentValues a = new h(this.c, c()).a();
            if (a != null) {
                asString = a.getAsString("HeartBeatValid");
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "hear beat valid from xml is " + asString);
                if (!TextUtils.isEmpty(asString) && (System.currentTimeMillis() >= com.huawei.android.pushagent.c.a.a(asString) || System.currentTimeMillis() + com.huawei.android.pushagent.b.b.a.a(this.c).ad() < com.huawei.android.pushagent.c.a.a(asString))) {
                    PushService.a(new Intent("com.huawei.android.push.intent.HEARTBEAT_VALID_ARRIVED"));
                }
            } else {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "PushHearBeat preferences is null");
            }
            this.i = com.huawei.android.pushagent.c.a.b.a(this.c);
            this.j = com.huawei.android.pushagent.c.a.h(this.c);
            e a2 = com.huawei.android.pushagent.b.b.a.a(this.c);
            this.g = a2.i() * 1000;
            this.h = a2.j() * 1000;
            this.f = false;
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "in loadHeartBeat netType:" + this.i + " mccMnc:" + this.j);
            ContentValues a3 = new h(this.c, c()).a();
            switch (this.i) {
                case -1:
                    this.e = a2.p() * 1000;
                    return this;
                case 0:
                    this.k = com.huawei.android.pushagent.c.a.j(this.c);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "in loadHeartBeat apnName:" + this.k);
                    a(a2, this.k);
                    break;
                case 1:
                    this.g = a2.g() * 1000;
                    this.h = a2.h() * 1000;
                    this.k = "wifi";
                    this.e = this.g;
                    if (a3 != null) {
                        asString = a3.getAsString("ClientIP_" + this.j + "_" + this.i);
                        if (this.l == null || !this.l.equals(asString)) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "curIP:" + this.l + " oldIP:" + asString + ", there are diff, so need find heartBeat again");
                            return this;
                        }
                    }
                    break;
                default:
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "unKnow net type");
                    return this;
            }
            this.e = this.g;
            if (a3 == null) {
                return this;
            }
            if (a3.containsKey("HasFindHeartBeat_" + this.j + "_" + this.i + "_" + this.k) && a3.containsKey("HearBeatInterval_" + this.j + "_" + this.i + "_" + this.k)) {
                this.f = a3.getAsBoolean("HasFindHeartBeat_" + this.j + "_" + this.i + "_" + this.k).booleanValue();
                Integer asInteger = a3.getAsInteger("HearBeatInterval_" + this.j + "_" + this.i + "_" + this.k);
                int intValue = asInteger != null ? asInteger.intValue() : 0;
                if (((long) intValue) < WaitFor.DEFAULT_MAX_WAIT_MILLIS) {
                    return this;
                }
                this.e = (long) intValue;
                return this;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "have no this heartbeat config, use default");
            return this;
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call loadHeartBeat cause:" + e.toString(), e);
            return this;
        }
    }

    public String toString() {
        String str = SimpleComparison.EQUAL_TO_OPERATION;
        String str2 = " ";
        return new StringBuffer().append("HasFindHeartBeat").append(str).append(this.f).append(str2).append("HearBeatInterval").append(str).append(this.e).append(str2).append("minHeartBeat").append(str).append(this.g).append(str2).append("maxHeartBeat").append(str).append(this.h).toString();
    }
}
