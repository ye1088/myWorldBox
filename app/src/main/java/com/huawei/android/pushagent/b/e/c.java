package com.huawei.android.pushagent.b.e;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.h;
import java.io.File;
import org.json.JSONObject;

public class c {

    private static class a {
        String a;
        int b;
        String c;
        Object d;

        private a() {
            this.a = "";
            this.b = -1;
            this.c = "";
            this.d = null;
        }
    }

    private static a a(String str) {
        a aVar = new a();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("file")) {
                aVar.a = jSONObject.getString("file");
                e.a("PushLogAC2705", "ModifyStruct mFileName is " + aVar.a);
            }
            if (jSONObject.has("type")) {
                aVar.b = jSONObject.getInt("type");
                e.a("PushLogAC2705", "ModifyStruct mModifyType is " + aVar.b);
            }
            if (jSONObject.has("name")) {
                aVar.c = jSONObject.getString("name");
                e.a("PushLogAC2705", "ModifyStruct mName is " + aVar.c);
            }
            if (!jSONObject.has("val")) {
                return aVar;
            }
            aVar.d = jSONObject.get("val");
            e.a("PushLogAC2705", "ModifyStruct mVal is " + aVar.d);
            return aVar;
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return null;
        }
    }

    public static synchronized void a(Context context, String str) {
        synchronized (c.class) {
            e.a("PushLogAC2705", "enter ModifyConfigs modify jsonStr is : " + str);
            a a = a(str);
            if (a != null) {
                if (!TextUtils.isEmpty(a.a)) {
                    switch (a.b) {
                        case 1:
                            if (!TextUtils.isEmpty(a.c)) {
                                if (!new h(context, a.a).a(a.c, a.d)) {
                                    e.d("PushLogAC2705", "enter ModifyConfigs saveString failed!");
                                    break;
                                } else {
                                    e.a("PushLogAC2705", "enter ModifyConfigs saveString sucessfully! filename is " + a.a + ",itemName is " + a.c + ",itemValue is " + a.d);
                                    break;
                                }
                            }
                            e.d("PushLogAC2705", "enter ModifyConfigs saveString failed! mName or mVal is null");
                            break;
                        case 2:
                            if (!TextUtils.isEmpty(a.c)) {
                                File file = new File(context.getCacheDir().getParent() + File.separator + "shared_prefs" + File.separator + a.a + ".xml");
                                if (file.isFile() && file.exists()) {
                                    if (!new h(context, a.a).f(a.c)) {
                                        e.d("PushLogAC2705", "enter ModifyConfigs removeKey failed, maybe the key is not exist!");
                                        break;
                                    } else {
                                        e.a("PushLogAC2705", "enter ModifyConfigs removeKey sucessfully! the fileName is " + a.a + ",the key is " + a.c);
                                        break;
                                    }
                                }
                                e.d("PushLogAC2705", "the file is not exist! file path is" + file);
                                break;
                            }
                            e.d("PushLogAC2705", "enter ModifyConfigs removeKey failed! mName is null");
                            break;
                            break;
                        case 3:
                            String str2 = context.getCacheDir().getParent() + File.separator + "shared_prefs" + File.separator + a.a + ".xml";
                            File file2 = new File(str2);
                            if (file2.isFile() && file2.exists()) {
                                if (!file2.delete()) {
                                    e.d("PushLogAC2705", "delete failed! file path is " + str2);
                                    break;
                                } else {
                                    e.a("PushLogAC2705", "delete success! file path is " + str2);
                                    break;
                                }
                            }
                            e.d("PushLogAC2705", "the file is not exist! file path is" + str2);
                            break;
                            break;
                        default:
                            e.d("PushLogAC2705", "the modifyType:" + a.b + " is not supported! ");
                            break;
                    }
                }
                e.d("PushLogAC2705", "enter ModifyConfigs struct failed to create sharepreference file!");
            } else {
                e.d("PushLogAC2705", "enter ModifyConfigs struct is null !");
            }
        }
    }
}
