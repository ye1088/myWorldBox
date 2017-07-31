package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.a.b;
import com.huawei.android.pushselfshow.richpush.favorites.f;
import com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a;
import java.util.ArrayList;

public class c {
    public static ArrayList a(Context context, String str) {
        String str2;
        String[] strArr;
        Cursor cursor = null;
        ArrayList arrayList = new ArrayList();
        String str3 = "";
        if (str == null) {
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url order by pushmsg._id desc limit 1000;";
            strArr = null;
        } else {
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url and pushmsg.url = ? order by pushmsg._id desc";
            strArr = new String[]{str};
        }
        try {
            cursor = b.a().a(context, a.f, str2, strArr);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
        if (cursor == null) {
            e.a("PushSelfShowLog", "cursor is null.");
        } else {
            while (cursor.moveToNext()) {
                int i = cursor.getInt(0);
                byte[] blob = cursor.getBlob(1);
                if (blob == null) {
                    e.d("PushSelfShowLog", "msg is null");
                } else {
                    try {
                        com.huawei.android.pushselfshow.b.a aVar = new com.huawei.android.pushselfshow.b.a(blob, " ".getBytes("UTF-8"));
                        if (!aVar.b()) {
                            e.a("PushSelfShowLog", "parseMessage failed");
                        }
                        str2 = cursor.getString(3);
                        f fVar = new f();
                        fVar.a(i);
                        fVar.a(str2);
                        fVar.a(aVar);
                        arrayList.add(fVar);
                    } catch (Throwable e2) {
                        e.c("TAG", "query favo error " + e2.toString(), e2);
                    } finally {
                        cursor.close();
                    }
                }
            }
            e.e("PushSelfShowLog", "query favo size is " + arrayList.size());
        }
        return arrayList;
    }

    public static void a(Context context, int i) {
        try {
            Context context2 = context;
            b.a().a(context2, a.g, "pushmsg", "_id = ?", new String[]{"" + i});
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public static boolean a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (context == null || str == null || aVar == null) {
            try {
                e.e("PushSelfShowLog", "insertPushMsginfo ilegle param");
                return false;
            } catch (Throwable e) {
                e.d("PushSelfShowLog", "insertBmpinfo error", e);
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", str);
        contentValues.put("msg", aVar.c());
        contentValues.put("token", " ".getBytes("UTF-8"));
        e.a("PushSelfShowLog", "insertPushMsginfo select url is %s ,rpl is %s", new Object[]{str, aVar.C});
        ArrayList a = a(context, str);
        String str2 = aVar.C;
        int i = 0;
        while (i < a.size()) {
            if (((f) a.get(i)).b() == null || !str2.equals(((f) a.get(i)).b().C)) {
                i++;
            } else {
                e.a("PushSelfShowLog", str2 + " already exist");
                return true;
            }
        }
        e.e("PushSelfShowLog", "insertPushMsginfo " + contentValues.toString());
        b.a().a(context, a.e, "pushmsg", contentValues);
        return true;
    }
}
