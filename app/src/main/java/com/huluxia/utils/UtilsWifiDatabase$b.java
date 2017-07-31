package com.huluxia.utils;

import com.huluxia.utils.ap.a;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class UtilsWifiDatabase$b extends a {
    private boolean bnM;
    private List<UtilsWifiDatabase$a> bnN;
    final /* synthetic */ UtilsWifiDatabase bnO;

    private UtilsWifiDatabase$b(UtilsWifiDatabase utilsWifiDatabase) {
        this.bnO = utilsWifiDatabase;
        this.bnM = false;
        this.bnN = new ArrayList();
    }

    public boolean MN() {
        return this.bnM;
    }

    public void MO() {
        this.bnN.clear();
        for (UtilsWifiDatabase$a info : UtilsWifiDatabase.a(this.bnO).values()) {
            if (!(info.bnJ == 1 || info.password == null || info.password.length() < 8)) {
                this.bnN.add(new UtilsWifiDatabase$a(info));
                if (this.bnN.size() >= 20) {
                    break;
                }
            }
        }
        if (this.bnN.size() > 0) {
            dD("http://wifi.huluxia.net/wifi/saveBatch");
        }
    }

    protected void dv(String strText) {
        if (strText != null && strText.equals("1")) {
            for (UtilsWifiDatabase$a info : this.bnN) {
                UtilsWifiDatabase.a(this.bnO, info);
                if (info == null) {
                    return;
                }
            }
        }
        this.bnM = false;
    }

    protected List<NameValuePair> Ci() {
        try {
            String jsonText = MP();
            if (jsonText.length() == 0) {
                return null;
            }
            String strTime = "" + System.currentTimeMillis();
            String strKeyText = aw.gn(strTime + "hlxsystem");
            jsonText = new w().encrypt(jsonText);
            if (jsonText == null) {
                return null;
            }
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("time", strTime));
            params.add(new BasicNameValuePair("key", strKeyText));
            params.add(new BasicNameValuePair("content", jsonText));
            this.bnM = true;
            return params;
        } catch (JSONException e) {
            return null;
        }
    }

    private String MP() throws JSONException {
        if (this.bnN.size() == 0) {
            return "";
        }
        JSONArray jsonMembers = new JSONArray();
        for (UtilsWifiDatabase$a info : this.bnN) {
            JSONObject jsonInfo = new JSONObject();
            jsonInfo.put("password", info.password);
            jsonInfo.put("ssid", info.bnK);
            jsonMembers.put(jsonInfo);
            info.bnJ = 1;
        }
        return jsonMembers.toString();
    }
}
