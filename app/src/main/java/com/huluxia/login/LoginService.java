package com.huluxia.login;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.IBinder;
import android.os.RemoteException;
import com.huluxia.framework.BaseHttpMgr;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsJson;
import com.huluxia.login.LoginError.LoginErrCode;
import com.huluxia.login.c.a;
import com.huluxia.login.utils.b;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginService extends Service {
    private static final String TAG = "LoginService";
    public static String fo;
    public static String rO = CMPVersion.CMP2_0;
    private Map<String, b> Nl = new HashMap();
    private Map<String, List<String>> Nm = new HashMap();
    private final a Nn = new a(this) {
        final /* synthetic */ LoginService No;

        {
            this.No = this$0;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21) throws android.os.RemoteException {
            /*
            r17 = this;
            r0 = r17;
            r5 = r0.No;
            r0 = r18;
            r5 = r5.bR(r0);
            if (r5 != 0) goto L_0x0039;
        L_0x000c:
            r0 = r17;
            r5 = r0.No;
            r5 = r5.Nl;
            r0 = r18;
            r4 = r5.get(r0);
            r4 = (com.huluxia.login.b) r4;
            if (r4 == 0) goto L_0x0038;
        L_0x001e:
            r5 = 0;
            r10 = "";
            r11 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
            r12 = "请求参数非法[1]";
            r6 = com.huluxia.login.LoginError.LoginErrCode.LOGIN_CLIENT_VERIFY_ERROR;
            r13 = r6.ordinal();
            r6 = r18;
            r7 = r19;
            r8 = r20;
            r9 = r21;
            r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13);
        L_0x0038:
            return;
        L_0x0039:
            r5 = com.huluxia.framework.base.utils.UtilsFunction.empty(r19);
            if (r5 != 0) goto L_0x0045;
        L_0x003f:
            r5 = com.huluxia.framework.base.utils.UtilsFunction.empty(r20);
            if (r5 == 0) goto L_0x0072;
        L_0x0045:
            r0 = r17;
            r5 = r0.No;
            r5 = r5.Nl;
            r0 = r18;
            r4 = r5.get(r0);
            r4 = (com.huluxia.login.b) r4;
            if (r4 == 0) goto L_0x0038;
        L_0x0057:
            r5 = 0;
            r10 = "";
            r11 = 104; // 0x68 float:1.46E-43 double:5.14E-322;
            r12 = "请求参数非法[2]";
            r6 = com.huluxia.login.LoginError.LoginErrCode.LOGIN_INVALID_ARGUEMENT_ERROR;
            r13 = r6.ordinal();
            r6 = r18;
            r7 = r19;
            r8 = r20;
            r9 = r21;
            r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13);
            goto L_0x0038;
        L_0x0072:
            r5 = "LoginService";
            r6 = "begin login client %s, email %s";
            r7 = 2;
            r7 = new java.lang.Object[r7];
            r9 = 0;
            r7[r9] = r18;
            r9 = 1;
            r7[r9] = r19;
            com.huluxia.framework.base.log.HLog.info(r5, r6, r7);
            r5 = com.huluxia.login.j.pV();
            r0 = r18;
            r16 = r5.bS(r0);
            if (r16 == 0) goto L_0x00e3;
        L_0x0090:
            r0 = r16;
            r5 = r0.email;
            r0 = r19;
            r5 = r0.equals(r5);
            if (r5 == 0) goto L_0x00e3;
        L_0x009c:
            r0 = r20;
            r1 = r20;
            r5 = r0.equals(r1);
            if (r5 == 0) goto L_0x00e3;
        L_0x00a6:
            r5 = "LoginService";
            r6 = "login succ in session %s";
            r7 = 1;
            r7 = new java.lang.Object[r7];
            r9 = 0;
            r7[r9] = r19;
            com.huluxia.framework.base.log.HLog.info(r5, r6, r7);
            r0 = r17;
            r5 = r0.No;
            r5 = r5.Nl;
            r0 = r18;
            r4 = r5.get(r0);
            r4 = (com.huluxia.login.b) r4;
            if (r4 == 0) goto L_0x0038;
        L_0x00c7:
            r5 = 1;
            r10 = com.huluxia.framework.base.utils.UtilsJson.toJsonString(r16);
            r11 = 0;
            r12 = "";
            r6 = com.huluxia.login.LoginError.LoginErrCode.LOGIN_STATUS_1;
            r13 = r6.ordinal();
            r6 = r18;
            r7 = r19;
            r8 = r20;
            r9 = r21;
            r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13);
            goto L_0x0038;
        L_0x00e3:
            r5 = com.huluxia.login.j.pV();
            r0 = r18;
            r5.clear(r0);
            r8 = new java.util.HashMap;
            r8.<init>();
            r5 = "email";
            r0 = r19;
            r8.put(r5, r0);
            r5 = "password";
            r0 = r20;
            r8.put(r5, r0);
            r5 = com.huluxia.framework.base.utils.UtilsFunction.empty(r21);
            if (r5 != 0) goto L_0x010f;
        L_0x0107:
            r5 = "openid";
            r0 = r21;
            r8.put(r5, r0);
        L_0x010f:
            r15 = 0;
            r0 = r17;
            r5 = r0.No;
            r6 = r5.Nm;
            monitor-enter(r6);
            r0 = r17;
            r5 = r0.No;	 Catch:{ all -> 0x0185 }
            r5 = r5.Nm;	 Catch:{ all -> 0x0185 }
            r0 = r19;
            r14 = r5.get(r0);	 Catch:{ all -> 0x0185 }
            r14 = (java.util.List) r14;	 Catch:{ all -> 0x0185 }
            r5 = com.huluxia.framework.base.utils.UtilsFunction.empty(r14);	 Catch:{ all -> 0x0185 }
            if (r5 == 0) goto L_0x017a;
        L_0x012f:
            r14 = new java.util.ArrayList;	 Catch:{ all -> 0x0185 }
            r14.<init>();	 Catch:{ all -> 0x0185 }
            r0 = r18;
            r14.add(r0);	 Catch:{ all -> 0x0185 }
            r0 = r17;
            r5 = r0.No;	 Catch:{ all -> 0x0185 }
            r5 = r5.Nm;	 Catch:{ all -> 0x0185 }
            r0 = r19;
            r5.put(r0, r14);	 Catch:{ all -> 0x0185 }
            r15 = 1;
        L_0x0147:
            monitor-exit(r6);	 Catch:{ all -> 0x0185 }
            if (r15 == 0) goto L_0x0038;
        L_0x014a:
            r5 = com.huluxia.login.f.pT();
            r0 = r17;
            r6 = r0.No;
            r7 = com.huluxia.login.download_toast.Nv;
            r6 = r6.aE(r7);
            r7 = 0;
            r9 = new com.huluxia.login.LoginService$1$1;
            r0 = r17;
            r1 = r19;
            r2 = r20;
            r3 = r21;
            r9.<init>(r0, r1, r2, r3);
            r10 = new com.huluxia.login.LoginService$1$2;
            r0 = r17;
            r1 = r19;
            r2 = r20;
            r3 = r21;
            r10.<init>(r0, r1, r2, r3);
            r11 = 0;
            r12 = 0;
            r5.performPostStringRequest(r6, r7, r8, r9, r10, r11, r12);
            goto L_0x0038;
        L_0x017a:
            r0 = r18;
            r5 = r14.contains(r0);	 Catch:{ all -> 0x0185 }
            if (r5 == 0) goto L_0x0188;
        L_0x0182:
            monitor-exit(r6);	 Catch:{ all -> 0x0185 }
            goto L_0x0038;
        L_0x0185:
            r5 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x0185 }
            throw r5;
        L_0x0188:
            r0 = r18;
            r14.add(r0);	 Catch:{ all -> 0x0185 }
            goto L_0x0147;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huluxia.login.LoginService.1.a(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
        }

        public void a(final String clientid, final String email, String encrytPwd, String nick, int gender, long birthday, String avatarFid, String openid, String accessToken) throws RemoteException {
            Map<String, String> param = new HashMap();
            param.put("email", email);
            param.put("password", encrytPwd);
            param.put("nick", nick);
            param.put("gender", String.valueOf(gender));
            param.put("birthday", String.valueOf(birthday));
            param.put("avatar_fid", avatarFid);
            param.put("openid", openid);
            param.put("access_token", accessToken);
            f.pT().performPostStringRequest(this.No.aE(l.Nu), null, param, new Listener<String>(this) {
                final /* synthetic */ AnonymousClass1 Nq;

                public void onResponse(String response) {
                    b callback;
                    try {
                        HLog.info(LoginService.TAG, "register response %s", response);
                        JSONObject json = new JSONObject(response);
                        if (json.optInt("status") == 1) {
                            callback = (b) this.Nq.No.Nl.get(clientid);
                            if (callback != null) {
                                callback.a(true, email, 0, "", LoginErrCode.REGISTER_STATUS_1.ordinal());
                                return;
                            }
                            return;
                        }
                        int code = json.optInt("code", 0);
                        String msg = json.optString("msg", "");
                        callback = (b) this.Nq.No.Nl.get(clientid);
                        if (callback != null) {
                            callback.a(false, email, code, msg, LoginErrCode.REGISTER_STATUS_0.ordinal());
                        }
                    } catch (JSONException e) {
                        try {
                            HLog.error(LoginService.TAG, "register failed %s", e, new Object[0]);
                            callback = (b) this.Nq.No.Nl.get(clientid);
                            if (callback != null) {
                                callback.a(false, email, 104, "解析错误，请稍候重试", LoginErrCode.REGISTER_JSON_ERROR.ordinal());
                            }
                        } catch (RemoteException e2) {
                            HLog.error(LoginService.TAG, "callbck register fail1", new Object[0]);
                        }
                    }
                }
            }, new ErrorListener(this) {
                final /* synthetic */ AnonymousClass1 Nq;

                public void onErrorResponse(VolleyError error) {
                    try {
                        b callback = (b) this.Nq.No.Nl.get(clientid);
                        if (callback != null) {
                            callback.a(false, email, 104, "服务器请求失败，请稍后重试", LoginErrCode.REGISTER_RESPONSE_ERROR.ordinal());
                        }
                    } catch (RemoteException e) {
                        HLog.error(LoginService.TAG, "callbck register fail2", new Object[0]);
                    }
                }
            }, false, false);
        }

        public void bO(String clientid) throws RemoteException {
            b callback;
            if (this.No.bR(clientid)) {
                k sessionInfo = j.pV().bS(clientid);
                if (sessionInfo == null || UtilsFunction.empty(sessionInfo.key) || sessionInfo.userID <= 0) {
                    HLog.error(LoginService.TAG, "auto login persist error %s", sessionInfo);
                    j.pV().clear(clientid);
                    callback = (b) this.No.Nl.get(clientid);
                    if (callback != null) {
                        callback.a(false, clientid, "", LoginErrCode.AUTO_LOGIN_PERSIST_ACCOUNT_ERROR.ordinal());
                        return;
                    }
                    return;
                }
                callback = (b) this.No.Nl.get(clientid);
                if (callback != null) {
                    callback.a(true, clientid, UtilsJson.toJsonString(sessionInfo), LoginErrCode.AUTO_LOGIN_NONE.ordinal());
                    return;
                }
                return;
            }
            callback = (b) this.No.Nl.get(clientid);
            if (callback != null) {
                callback.a(false, clientid, "", LoginErrCode.AUTO_LOGIN_VERIFY_ERROR.ordinal());
            }
        }

        public void bP(String clientid) throws RemoteException {
            j.pV().clear(clientid);
            f.pT().performStringRequest(l.Nw, new Listener<String>(this) {
                final /* synthetic */ AnonymousClass1 Nq;

                {
                    this.Nq = this$1;
                }

                public void onResponse(String response) {
                    HLog.info(LoginService.TAG, "logout response %s", response);
                }
            }, new ErrorListener(this) {
                final /* synthetic */ AnonymousClass1 Nq;

                {
                    this.Nq = this$1;
                }

                public void onErrorResponse(VolleyError error) {
                    HLog.error(LoginService.TAG, "logout error %s", error, new Object[0]);
                }
            });
        }

        public void pQ() throws RemoteException {
            for (b callback : this.No.Nl.values()) {
                callback.cP(this.No.version);
            }
        }

        public void a(String clientid, b callback) throws RemoteException {
            if (callback != null) {
                this.No.Nl.remove(clientid);
                this.No.Nl.put(clientid, callback);
            }
        }
    };
    private int version = -1;

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.version = -1;
        try {
            this.version = getPackageManager().getServiceInfo(new ComponentName(getPackageName(), "com.huluxia.login.LoginService"), 128).metaData.getInt("version", -1);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public IBinder onBind(Intent intent) {
        int targetVersion = intent.getIntExtra("version", -1);
        HLog.info(TAG, "login service version %d, target version %d", Integer.valueOf(this.version), Integer.valueOf(targetVersion));
        return this.Nn;
    }

    private String aE(String url) {
        Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(BaseHttpMgr.PARAM_APP_VERSION, rO);
        builder.appendQueryParameter("platform", "2");
        builder.appendQueryParameter("marketID", fo);
        builder.appendQueryParameter(BaseHttpMgr.PARAM_DEVICE_CODE, b.getDeviceId());
        return builder.toString();
    }

    private boolean bR(String clientid) {
        return !UtilsFunction.empty((CharSequence) clientid);
    }
}
