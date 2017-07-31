package com.huawei.android.pushagent.b.d.a;

import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

public class a implements b {
    public long a;
    public long b;
    public long c;
    public long d;

    public a(long j, long j2) {
        this.a = j;
        this.b = j2;
        this.c = 0;
        this.d = 0;
    }

    public String a() {
        String str = ";";
        return new StringBuffer().append(4).append(str).append(this.a).append(str).append(this.b).append(str).append(this.c).append(str).append(this.d).toString();
    }

    public boolean a(long j) {
        e.a("PushLogAC2705", "enter FlowSimpleControl::canApply(num:" + j + ", curVol:" + this.c + ", maxVol:" + this.b + ")");
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        if (valueOf.longValue() < this.d || valueOf.longValue() - this.d >= this.a) {
            e.a("PushLogAC2705", " fistrControlTime:" + new Date(this.d) + " interval:" + (valueOf.longValue() - this.d) + " statInterval:" + this.a + " change fistrControlTime to cur");
            this.d = valueOf.longValue();
            this.c = 0;
        } else {
            try {
                Calendar instance = Calendar.getInstance(Locale.getDefault());
                instance.setTimeInMillis(this.d);
                int i = instance.get(2);
                instance.setTimeInMillis(valueOf.longValue());
                if (i != instance.get(2)) {
                    this.d = valueOf.longValue();
                    this.c = 0;
                }
            } catch (Throwable e) {
                e.c("PushLogAC2705", e.toString(), e);
            } catch (Throwable e2) {
                e.c("PushLogAC2705", e2.toString(), e2);
            } catch (Throwable e22) {
                e.c("PushLogAC2705", e22.toString(), e22);
            }
        }
        return this.c + j <= this.b;
    }

    public boolean a(b bVar) {
        if (!(bVar instanceof a)) {
            return false;
        }
        a aVar = (a) bVar;
        return this.a == aVar.a && this.b == aVar.b;
    }

    public boolean a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                e.b("PushLogAC2705", "in loadFromString, info is empty!");
                return false;
            }
            e.a("PushLogAC2705", "begin to parse:" + str);
            String[] split = str.split(";");
            if (split.length == 0) {
                return false;
            }
            int parseInt = Integer.parseInt(split[0]);
            if (parseInt == 4 && parseInt == split.length - 1) {
                this.a = Long.parseLong(split[1]);
                this.b = Long.parseLong(split[2]);
                this.c = Long.parseLong(split[3]);
                this.d = Long.parseLong(split[4]);
                return true;
            }
            e.d("PushLogAC2705", "in fileNum:" + parseInt + ", but need " + 4 + " parse " + str + " failed");
            return false;
        } catch (PatternSyntaxException e) {
            return false;
        } catch (NumberFormatException e2) {
            return false;
        } catch (Exception e3) {
            return false;
        }
    }

    public boolean b(long j) {
        this.c += j;
        return true;
    }
}
