package com.MCWorld.http.bbs.topic;

import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.module.ab;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ComplaintRequest */
public class f extends a {
    public static final long sB = 1;
    public static final long sC = 2;
    public static final long sD = 3;
    private long sA;
    private long sy;
    private long sz;

    public String eU() {
        return String.format(Locale.getDefault(), ab.aAl, new Object[0]);
    }

    public void a(d response, JSONObject json) throws JSONException {
    }

    public void g(List<NameValuePair> nvps) {
        nvps.add(new BasicNameValuePair("type_id", Long.toString(this.sy)));
        nvps.add(new BasicNameValuePair("file_id", Long.toString(this.sz)));
        nvps.add(new BasicNameValuePair("complaint_type", Long.toString(this.sA)));
    }

    public long fx() {
        return this.sy;
    }

    public void z(long type_id) {
        this.sy = type_id;
    }

    public long fy() {
        return this.sz;
    }

    public void A(long file_id) {
        this.sz = file_id;
    }

    public long fz() {
        return this.sA;
    }

    public void B(long complaint_type) {
        this.sA = complaint_type;
    }
}
