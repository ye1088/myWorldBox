package com.huluxia.data.profile;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ExchangeExt */
public class a {
    public static final String pZ = "giftName";
    public static final String qa = "hulu";
    public static final String qb = "cashType";
    public static final String qc = "QQ";
    public static final String qd = "recipient";
    public static final String qe = "phone";
    public static final String qf = "address";
    public static final String qg = "alipayAccount";
    public static final String qh = "alipayNick";

    public static String a(GiftInfo info, String qqNum) throws JSONException {
        JSONObject json = a(info);
        json.put("QQ", qqNum);
        return json.toString();
    }

    public static String a(GiftInfo info, String recipient, String tel, String address) throws JSONException {
        JSONObject json = a(info);
        json.put(qd, recipient);
        json.put(qe, tel);
        json.put(qf, address);
        return json.toString();
    }

    public static String b(GiftInfo info, String tel) throws JSONException {
        JSONObject json = a(info);
        json.put(qe, tel);
        return json.toString();
    }

    public static String a(GiftInfo info, String account, String nick) throws JSONException {
        JSONObject json = a(info);
        json.put(qg, account);
        json.put(qh, nick);
        return json.toString();
    }

    private static JSONObject a(GiftInfo info) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(pZ, info.getName());
        json.put(qa, info.getCredits());
        json.put(qb, info.getCashType());
        return json;
    }
}
