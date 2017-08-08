package com.MCWorld.utils;

import com.baidu.android.pushservice.PushConstants;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.module.s;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tools.ant.taskdefs.optional.j2ee.HotDeploymentTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UtilsDetectUrl */
public class x {
    private static final String bkX = "http://pan.baidu.com/api/sharedownload";
    private static final String bkY = "http://pan.baidu.com/api/getcaptcha";
    static Pattern bkZ = Pattern.compile("var _context \\= \\{(\".*?\")\\};");
    static Pattern bla = Pattern.compile("\\{\".*\"\\}");
    static Pattern blb = Pattern.compile("\"bdstoken\":(.*?)");
    static Pattern blc = Pattern.compile("\"sign\":\"(.*?)\"");
    static Pattern bld = Pattern.compile("\"timestamp\":(\\d+)");
    static Pattern ble = Pattern.compile("\"shareid\":(\\d+)");
    static Pattern blf = Pattern.compile("\"uk\":(\\d+)");

    public static JSONObject fE(String html) {
        Matcher m = bkZ.matcher(html);
        if (m.find()) {
            Matcher m2 = bla.matcher(m.group());
            if (m2.find()) {
                try {
                    return new JSONObject(m2.group());
                } catch (JSONException e) {
                    HLog.error("UtilsDetectUrl", " getVarContext e " + e.getMessage(), new Object[0]);
                }
            }
        }
        return null;
    }

    public static String fF(String html) {
        Matcher m = blb.matcher(html);
        String strFs = "";
        if (m.find()) {
            strFs = m.group();
            strFs = strFs.substring(11, strFs.length() - 1);
        }
        if (strFs == "null") {
            return "";
        }
        return strFs;
    }

    public static String fG(String html) {
        Matcher m = blc.matcher(html);
        String strMatch = "";
        if (!m.find()) {
            return strMatch;
        }
        strMatch = m.group();
        return strMatch.substring(8, strMatch.length() - 1);
    }

    public static String fH(String html) {
        Matcher m = bld.matcher(html);
        String strMatch = "";
        if (!m.find()) {
            return strMatch;
        }
        strMatch = m.group();
        return strMatch.substring(12, strMatch.length() - 1);
    }

    public static String fI(String html) {
        Matcher m = ble.matcher(html);
        String strMatch = "";
        if (!m.find()) {
            return strMatch;
        }
        strMatch = m.group();
        return strMatch.substring(10, strMatch.length() - 1);
    }

    private static String e(JSONObject html) {
        JSONArray listArr = html.optJSONArray(HotDeploymentTool.ACTION_LIST);
        if (listArr == null || listArr.length() == 0) {
            return null;
        }
        return listArr.optJSONObject(0).optString(PushConstants.EXTRA_APP_ID);
    }

    public static String f(JSONObject html) {
        JSONArray listArr = html.optJSONArray(HotDeploymentTool.ACTION_LIST);
        if (listArr == null || listArr.length() == 0) {
            return null;
        }
        return "[" + listArr.optJSONObject(0).optString("fs_id") + "]";
    }

    public static String fJ(String html) {
        Matcher m = blf.matcher(html);
        String strMatch = "";
        if (m.find()) {
            return m.group().substring(5);
        }
        return strMatch;
    }

    public static List<NameValuePair> g(JSONObject html) {
        long timeStamp = html.optLong("timestamp");
        String sign = html.optString("sign");
        String bdStoken = html.optString("bdstoken", "");
        if (bdStoken.equals("null")) {
            bdStoken = "";
        }
        String appid = e(html.optJSONObject("file_list"));
        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair(PushConstants.EXTRA_APP_ID, appid));
        nvps.add(new BasicNameValuePair("channel", "chunlei"));
        nvps.add(new BasicNameValuePair("clienttype", Long.toString(0)));
        nvps.add(new BasicNameValuePair("web", Long.toString(1)));
        nvps.add(new BasicNameValuePair("sign", sign));
        nvps.add(new BasicNameValuePair("timestamp", String.valueOf(timeStamp)));
        nvps.add(new BasicNameValuePair("bdstoken", bdStoken));
        return nvps;
    }

    public static List<NameValuePair> a(JSONObject html, String vcode_input, String strvcode_str) {
        long timeStamp = html.optLong("timestamp");
        String sign = html.optString("sign");
        if (html.optString("bdstoken", "").equals("null")) {
            String bdStoken = "";
        }
        long uk = html.optLong("uk");
        long primaryid = html.optLong("shareid");
        String fid_list = f(html.optJSONObject("file_list"));
        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair("encrypt", Long.toString(0)));
        nvps.add(new BasicNameValuePair("product", s.SHARE));
        nvps.add(new BasicNameValuePair("vcode_input", vcode_input));
        nvps.add(new BasicNameValuePair("vcode_str", strvcode_str));
        nvps.add(new BasicNameValuePair("uk", String.valueOf(uk)));
        nvps.add(new BasicNameValuePair("primaryid", String.valueOf(primaryid)));
        nvps.add(new BasicNameValuePair("fid_list", fid_list));
        return nvps;
    }

    public static List<NameValuePair> h(JSONObject html) {
        long timeStamp = html.optLong("timestamp");
        String sign = html.optString("sign");
        if (html.optString("bdstoken", "").equals("null")) {
            String bdStoken = "";
        }
        long uk = html.optLong("uk");
        long primaryid = html.optLong("shareid");
        String fid_list = f(html.optJSONObject("file_list"));
        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair("encrypt", Long.toString(0)));
        nvps.add(new BasicNameValuePair("product", s.SHARE));
        nvps.add(new BasicNameValuePair("uk", String.valueOf(uk)));
        nvps.add(new BasicNameValuePair("primaryid", String.valueOf(primaryid)));
        nvps.add(new BasicNameValuePair("fid_list", fid_list));
        return nvps;
    }

    public static List<NameValuePair> i(JSONObject html) {
        String appid = e(html.optJSONObject("file_list"));
        String bdStoken = html.optString("bdstoken", "");
        if (bdStoken.equals("null")) {
            bdStoken = "";
        }
        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair("prod", s.SHARE));
        nvps.add(new BasicNameValuePair("bdstoken", bdStoken));
        nvps.add(new BasicNameValuePair("channel", "chunlei"));
        nvps.add(new BasicNameValuePair("clienttype", "0"));
        nvps.add(new BasicNameValuePair("web", "1"));
        nvps.add(new BasicNameValuePair(PushConstants.EXTRA_APP_ID, appid));
        return nvps;
    }
}
