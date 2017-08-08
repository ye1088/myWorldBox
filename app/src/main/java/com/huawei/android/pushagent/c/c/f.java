package com.huawei.android.pushagent.c.c;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.e;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.g;
import com.huawei.android.pushagent.c.a.i;
import com.MCWorld.ui.profile.ProfileScoreActivity;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.tools.ant.taskdefs.condition.Os;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.ant.util.ProxySetup;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    public static e a(Context context, String str) {
        HttpsURLConnection httpsURLConnection;
        Throwable e;
        InputStream inputStream;
        BufferedReader bufferedReader;
        Throwable th;
        String a = a(context, str, false);
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "queryTrs runing");
        InputStream inputStream2 = null;
        BufferedReader bufferedReader2 = null;
        try {
            HttpsURLConnection a2 = a(context, a, str, false, false);
            if (a2 != null) {
                httpsURLConnection = a2;
            } else {
                try {
                    a2 = a(context, a, str, true, false);
                    if (a2 != null) {
                        httpsURLConnection = a2;
                    } else {
                        a2 = a(context, a, str, false, true);
                        if (a2 != null) {
                            httpsURLConnection = a2;
                        } else {
                            a2 = a(context, a, str, true, true);
                            if (a2 != null) {
                                httpsURLConnection = a2;
                            } else {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "after all, trs connect is null");
                                if (null != null) {
                                    try {
                                        inputStream2.close();
                                    } catch (IOException e2) {
                                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                                    }
                                }
                                if (null != null) {
                                    try {
                                        bufferedReader2.close();
                                    } catch (IOException e3) {
                                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                                    }
                                }
                                if (a2 == null) {
                                    return null;
                                }
                                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                                a2.disconnect();
                                return null;
                            }
                        }
                    }
                } catch (UnsupportedEncodingException e4) {
                    e = e4;
                    httpsURLConnection = a2;
                    inputStream = null;
                    bufferedReader = null;
                    try {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e.toString(), e);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e5) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e6) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                            }
                        }
                        if (httpsURLConnection != null) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                            httpsURLConnection.disconnect();
                        }
                        try {
                            Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                        } catch (Throwable e7) {
                            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e7.toString(), e7);
                        }
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e8) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e9) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                            }
                        }
                        if (httpsURLConnection != null) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                            httpsURLConnection.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e10) {
                    e7 = e10;
                    httpsURLConnection = a2;
                    inputStream = null;
                    bufferedReader = null;
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e7.toString(), e7);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e11) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e12) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                        }
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                } catch (Exception e13) {
                    e7 = e13;
                    httpsURLConnection = a2;
                    inputStream = null;
                    bufferedReader = null;
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e7.toString(), e7);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e14) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e15) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                        }
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                } catch (Throwable e72) {
                    httpsURLConnection = a2;
                    inputStream = null;
                    bufferedReader = null;
                    th = e72;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    throw th;
                }
            }
            try {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "queryTrs connect() success");
                inputStream = httpsURLConnection.getInputStream();
            } catch (UnsupportedEncodingException e16) {
                e72 = e16;
                bufferedReader = null;
                inputStream = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e72.toString(), e72);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (IOException e17) {
                e72 = e17;
                bufferedReader = null;
                inputStream = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e72.toString(), e72);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (Exception e18) {
                e72 = e18;
                bufferedReader = null;
                inputStream = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e72.toString(), e72);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (Throwable e722) {
                bufferedReader = null;
                inputStream = null;
                th = e722;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                throw th;
            }
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        e eVar = new e(context, readLine);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e19) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e20) {
                                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                            }
                        }
                        if (httpsURLConnection != null) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                            httpsURLConnection.disconnect();
                        }
                        return eVar;
                    }
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "response is null");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e21) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close is err");
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e22) {
                            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "close br err");
                        }
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                } catch (UnsupportedEncodingException e23) {
                    e722 = e23;
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                } catch (IOException e24) {
                    e722 = e24;
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                } catch (Exception e25) {
                    e722 = e25;
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (httpsURLConnection != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                        httpsURLConnection.disconnect();
                    }
                    Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                    return null;
                }
            } catch (UnsupportedEncodingException e26) {
                e722 = e26;
                bufferedReader = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (IOException e27) {
                e722 = e27;
                bufferedReader = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (Exception e28) {
                e722 = e28;
                bufferedReader = null;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e722.toString(), e722);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
                return null;
            } catch (Throwable e7222) {
                bufferedReader = null;
                th = e7222;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpsURLConnection != null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                    httpsURLConnection.disconnect();
                }
                throw th;
            }
        } catch (UnsupportedEncodingException e29) {
            e7222 = e29;
            bufferedReader = null;
            httpsURLConnection = null;
            inputStream = null;
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e7222.toString(), e7222);
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpsURLConnection != null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                httpsURLConnection.disconnect();
            }
            Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
            return null;
        } catch (IOException e30) {
            e7222 = e30;
            bufferedReader = null;
            httpsURLConnection = null;
            inputStream = null;
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e7222.toString(), e7222);
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpsURLConnection != null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                httpsURLConnection.disconnect();
            }
            Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
            return null;
        } catch (Exception e31) {
            e7222 = e31;
            bufferedReader = null;
            httpsURLConnection = null;
            inputStream = null;
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "query trs err:" + e7222.toString(), e7222);
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpsURLConnection != null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                httpsURLConnection.disconnect();
            }
            Thread.sleep(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "get IP/PORT failed, retry again.");
            return null;
        } catch (Throwable e72222) {
            bufferedReader = null;
            httpsURLConnection = null;
            inputStream = null;
            th = e72222;
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpsURLConnection != null) {
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "disconnect the socket");
                httpsURLConnection.disconnect();
            }
            throw th;
        }
    }

    private static String a(Context context, String str, boolean z) {
        String a = a("push.hicloud.com", str);
        if (z && a != null && a.length() > 0) {
            a = a.split(":")[0] + ":5222";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https://").append(a).append("/TRSServer/TRSRequest3");
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "url:" + stringBuffer.toString());
        return stringBuffer.toString();
    }

    private static String a(String str) {
        return str == null ? "" : str;
    }

    private static String a(String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "belongId is null or trsAddress is null");
        } else if (str.startsWith("push")) {
            try {
                int parseInt = Integer.parseInt(str2.trim());
                if (parseInt <= 0) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "belongId is invalid:" + parseInt);
                } else {
                    int indexOf = str.indexOf(".");
                    if (indexOf > -1) {
                        str = new StringBuffer().append(str.substring(0, indexOf)).append(parseInt).append(str.substring(indexOf)).toString();
                    }
                }
            } catch (Throwable e) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "belongId parseInt error: " + str2, e);
            } catch (Throwable e2) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e2.getMessage(), e2);
            }
        } else {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "trsAddress is invalid:" + str);
        }
        return str;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static javax.net.ssl.HttpsURLConnection a(android.content.Context r7, java.lang.String r8, java.lang.String r9, boolean r10, boolean r11) {
        /*
        r0 = 0;
        if (r11 == 0) goto L_0x0007;
    L_0x0003:
        r8 = a_isRightVersion(r7, r9, r11);
    L_0x0007:
        if (r8 != 0) goto L_0x0013;
    L_0x0009:
        r1 = "PushLogAC2705";
        r2 = "TRSUrl is null, cannot create connection.";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r2);
    L_0x0012:
        return r0;
    L_0x0013:
        r1 = "PushLogAC2705";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "TRS httpUrl = ";
        r2 = r2.append(r3);
        r2 = r2.append(r8);
        r3 = ",useDefaultPort = ";
        r2 = r2.append(r3);
        r2 = r2.append(r11);
        r2 = r2.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r2);
        r1 = b(r7, r8, r10);	 Catch:{ IOException -> 0x0100, Exception -> 0x00fd }
        r2 = "PushLogAC2705";
        r3 = "get connection success.";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r3);	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        r1.connect();	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        r3 = r1.getOutputStream();	 Catch:{ Exception -> 0x00a2, all -> 0x00e9 }
        r2 = b(r7, r9);	 Catch:{ Exception -> 0x0106 }
        r4 = "UTF-8";
        r2 = r2.getBytes(r4);	 Catch:{ Exception -> 0x0106 }
        r3.write(r2);	 Catch:{ Exception -> 0x0106 }
        r3.flush();	 Catch:{ Exception -> 0x0106 }
        if (r3 == 0) goto L_0x0062;
    L_0x005f:
        r3.close();	 Catch:{ Exception -> 0x006d, IOException -> 0x0079 }
    L_0x0062:
        r2 = "PushLogAC2705";
        r3 = "call conn.connect() success";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r3);	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        r0 = r1;
        goto L_0x0012;
    L_0x006d:
        r2 = move-exception;
        r3 = "PushLogAC2705";
        r4 = r2.toString();	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r3, r4, r2);	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        goto L_0x0062;
    L_0x0079:
        r2 = move-exception;
        r6 = r2;
        r2 = r1;
        r1 = r6;
    L_0x007d:
        r3 = "PushLogAC2705";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "connect to TRS cause IOException:";
        r4 = r4.append(r5);
        r1 = r1.toString();
        r1 = r4.append(r1);
        r1 = r1.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.d(r3, r1);
        if (r2 == 0) goto L_0x0012;
    L_0x009d:
        r2.disconnect();
        goto L_0x0012;
    L_0x00a2:
        r2 = move-exception;
        r3 = r0;
    L_0x00a4:
        r4 = "PushLogAC2705";
        r5 = r2.toString();	 Catch:{ all -> 0x0104 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r4, r5, r2);	 Catch:{ all -> 0x0104 }
        if (r3 == 0) goto L_0x0062;
    L_0x00b0:
        r3.close();	 Catch:{ Exception -> 0x00b4, IOException -> 0x0079 }
        goto L_0x0062;
    L_0x00b4:
        r2 = move-exception;
        r3 = "PushLogAC2705";
        r4 = r2.toString();	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r3, r4, r2);	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        goto L_0x0062;
    L_0x00c0:
        r2 = move-exception;
        r6 = r2;
        r2 = r1;
        r1 = r6;
    L_0x00c4:
        r3 = "PushLogAC2705";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "connect to TRS cause Exception:";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r3, r4, r1);
        if (r2 == 0) goto L_0x0012;
    L_0x00e4:
        r2.disconnect();
        goto L_0x0012;
    L_0x00e9:
        r2 = move-exception;
        r3 = r0;
    L_0x00eb:
        if (r3 == 0) goto L_0x00f0;
    L_0x00ed:
        r3.close();	 Catch:{ Exception -> 0x00f1, IOException -> 0x0079 }
    L_0x00f0:
        throw r2;	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
    L_0x00f1:
        r3 = move-exception;
        r4 = "PushLogAC2705";
        r5 = r3.toString();	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.c(r4, r5, r3);	 Catch:{ IOException -> 0x0079, Exception -> 0x00c0 }
        goto L_0x00f0;
    L_0x00fd:
        r1 = move-exception;
        r2 = r0;
        goto L_0x00c4;
    L_0x0100:
        r1 = move-exception;
        r2 = r0;
        goto L_0x007d;
    L_0x0104:
        r2 = move-exception;
        goto L_0x00eb;
    L_0x0106:
        r2 = move-exception;
        goto L_0x00a4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushagent.c.c.f.a_isRightVersion(android.content.Context, java.lang.String, java.lang.String, boolean, boolean):javax.net.ssl.HttpsURLConnection");
    }

    private static String b(Context context, String str) {
        String h = a.h(context);
        String a = a.a(context);
        String a2 = a.a(context);
        String str2 = "";
        String g = a.g(context);
        String c = a.c();
        String packageName = context.getPackageName();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mccmnc", a(h));
            jSONObject.put("PushID", a(a));
            jSONObject.put("udid", a(a2));
            jSONObject.put("belongid", a(str));
            jSONObject.put("version", a(g));
            jSONObject.put("protocolversion", CMPVersion.CMP2_0);
            jSONObject.put(ProfileScoreActivity.bhf, 0);
            jSONObject.put("channel", a(packageName));
            jSONObject.put(Values.MODE, a(Build.MODEL));
            jSONObject.put(Os.FAMILY_MAC, a(str2));
            jSONObject.put("intelligent", 3);
            jSONObject.put("emV", a(c));
            jSONObject.put("rV", a(Build.DISPLAY));
        } catch (JSONException e) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e.toString());
        }
        return jSONObject.toString();
    }

    private static HttpsURLConnection b(Context context, String str, boolean z) throws Exception {
        String property;
        int parseInt;
        Exception e;
        HttpsURLConnection httpsURLConnection;
        SSLContext instance = SSLContext.getInstance(SSLSocketFactory.TLS);
        instance.init(null, new TrustManager[]{new i(context)}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(new g(instance.getSocketFactory()));
        HttpsURLConnection.setDefaultHostnameVerifier(new g());
        if (z && 1 != b.a(context)) {
            try {
                if (VERSION.SDK_INT >= 11) {
                    property = System.getProperty(ProxySetup.HTTP_PROXY_HOST);
                    try {
                        String property2 = System.getProperty(ProxySetup.HTTP_PROXY_PORT);
                        if (property2 == null) {
                            property2 = "-1";
                        }
                        parseInt = Integer.parseInt(property2);
                    } catch (Exception e2) {
                        e = e2;
                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "get proxy ip or port error:" + e.getMessage());
                        parseInt = -1;
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "use Proxy " + property + ":" + parseInt + " to open:" + str);
                        httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(property, parseInt)));
                        if (httpsURLConnection == null) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "direct to open " + str);
                            httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                        }
                        httpsURLConnection.setConnectTimeout(((int) com.huawei.android.pushagent.b.b.a.a(context).u()) * 1000);
                        httpsURLConnection.setReadTimeout((int) (com.huawei.android.pushagent.b.b.a.a(context).v() * 1000));
                        httpsURLConnection.setDoOutput(true);
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setRequestProperty("Content-type", "json/text; charset=UTF-8");
                        return httpsURLConnection;
                    }
                    if (!(property == null || property.length() <= 0 || parseInt == -1)) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "use Proxy " + property + ":" + parseInt + " to open:" + str);
                        httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(property, parseInt)));
                        if (httpsURLConnection == null) {
                            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "direct to open " + str);
                            httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                        }
                        httpsURLConnection.setConnectTimeout(((int) com.huawei.android.pushagent.b.b.a.a(context).u()) * 1000);
                        httpsURLConnection.setReadTimeout((int) (com.huawei.android.pushagent.b.b.a.a(context).v() * 1000));
                        httpsURLConnection.setDoOutput(true);
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setRequestProperty("Content-type", "json/text; charset=UTF-8");
                        return httpsURLConnection;
                    }
                }
                property = android.net.Proxy.getHost(context);
                parseInt = android.net.Proxy.getPort(context);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "use Proxy " + property + ":" + parseInt + " to open:" + str);
                httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(property, parseInt)));
                if (httpsURLConnection == null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "direct to open " + str);
                    httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                }
                httpsURLConnection.setConnectTimeout(((int) com.huawei.android.pushagent.b.b.a.a(context).u()) * 1000);
                httpsURLConnection.setReadTimeout((int) (com.huawei.android.pushagent.b.b.a.a(context).v() * 1000));
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setRequestProperty("Content-type", "json/text; charset=UTF-8");
                return httpsURLConnection;
            } catch (Exception e3) {
                e = e3;
                property = null;
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "get proxy ip or port error:" + e.getMessage());
                parseInt = -1;
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "use Proxy " + property + ":" + parseInt + " to open:" + str);
                httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(property, parseInt)));
                if (httpsURLConnection == null) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "direct to open " + str);
                    httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                }
                httpsURLConnection.setConnectTimeout(((int) com.huawei.android.pushagent.b.b.a.a(context).u()) * 1000);
                httpsURLConnection.setReadTimeout((int) (com.huawei.android.pushagent.b.b.a.a(context).v() * 1000));
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setRequestProperty("Content-type", "json/text; charset=UTF-8");
                return httpsURLConnection;
            }
        }
        httpsURLConnection = null;
        if (httpsURLConnection == null) {
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "direct to open " + str);
            httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
        }
        httpsURLConnection.setConnectTimeout(((int) com.huawei.android.pushagent.b.b.a.a(context).u()) * 1000);
        httpsURLConnection.setReadTimeout((int) (com.huawei.android.pushagent.b.b.a.a(context).v() * 1000));
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Content-type", "json/text; charset=UTF-8");
        return httpsURLConnection;
    }
}
