package com.MCWorld.data;

import com.MCWorld.r;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: VersionInfo */
public class n {
    private String address;
    private String message;
    private boolean pA;
    private boolean pB = false;
    private boolean pC = true;
    private String pz;
    private int updateType;
    private int versionCode;

    /* compiled from: VersionInfo */
    public static class a {
        public boolean pA;
        public boolean pC;
        public String pD;

        public a(String para_packmd5, boolean para_kroot, boolean para_search) {
            this.pD = para_packmd5;
            this.pA = para_kroot;
            this.pC = para_search;
        }
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int ez() {
        return this.updateType;
    }

    public boolean eA() {
        return this.pB;
    }

    public void U(int updateType) {
        this.updateType = updateType;
    }

    public String eB() {
        return this.pz;
    }

    public void az(String newVersion) {
        this.pz = newVersion;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public n(JSONObject obj) throws JSONException {
        if (!obj.isNull(com.MCWorld.data.profile.a.qf)) {
            this.address = obj.getString(com.MCWorld.data.profile.a.qf);
        }
        if (!obj.isNull("message")) {
            this.message = obj.getString("message");
        }
        if (!obj.isNull("newVersion")) {
            this.pz = obj.getString("newVersion");
        }
        if (!obj.isNull("updateType")) {
            this.updateType = obj.getInt("updateType");
        }
        if (!obj.isNull("versionCode")) {
            this.versionCode = obj.getInt("versionCode");
        }
        if (!obj.isNull("kroot")) {
            this.pA = obj.getBoolean("kroot");
        }
        if (!obj.isNull("showRedPacket")) {
            this.pB = obj.getBoolean("showRedPacket");
        }
        if (!obj.isNull(r.gR)) {
            this.pC = obj.getBoolean(r.gR);
        }
    }

    public void a(a sInfo) {
        sInfo.pA = this.pA;
        sInfo.pC = this.pC;
    }
}
