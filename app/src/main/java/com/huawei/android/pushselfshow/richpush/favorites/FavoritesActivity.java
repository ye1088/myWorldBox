package com.huawei.android.pushselfshow.richpush.favorites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.HtmlViewer;
import com.huawei.android.pushselfshow.utils.a;
import com.huawei.android.pushselfshow.utils.a.b;
import com.huawei.android.pushselfshow.utils.c;
import com.huawei.android.pushselfshow.utils.c$a;
import com.huawei.android.pushselfshow.utils.d;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;

public class FavoritesActivity implements c$a {
    c a = new c(this);
    private Activity b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private b f;
    private ListView g;
    private LinearLayout h;
    private a i;
    private ImageView j;
    private ImageView k;
    private TextView l;
    private boolean m = false;
    private byte[] n = null;
    private byte[] o = null;
    private AlertDialog p = null;

    private ContentValues a(Cursor cursor, String str) {
        ContentValues contentValues = new ContentValues();
        if (cursor == null || TextUtils.isEmpty(str)) {
            e.d("PushSelfShowLog", "getContentValues, cursor or table is null");
        } else if ("notify".equals(str)) {
            r1 = cursor.getString(cursor.getColumnIndex("url"));
            r2 = cursor.getBlob(cursor.getColumnIndex("bmp"));
            contentValues.put("url", r1);
            contentValues.put("bmp", r2);
        } else if ("pushmsg".equals(str)) {
            r1 = cursor.getString(cursor.getColumnIndex("url"));
            r2 = cursor.getBlob(cursor.getColumnIndex("msg"));
            contentValues.put("url", r1);
            try {
                contentValues.put("token", " ".getBytes("UTF-8"));
            } catch (Throwable e) {
                e.c("PushSelfShowLog", e.toString(), e);
            }
            contentValues.put("msg", r2);
        }
        return contentValues;
    }

    private View a() {
        View inflate = this.b.getLayoutInflater().inflate(d.c(this.b, "hwpush_collection_listview"), null);
        this.g = (ListView) inflate.findViewById(d.d(this.b, "hwpush_collection_list"));
        this.i = new a(this.b);
        this.g.setAdapter(this.i);
        this.g.setLongClickable(true);
        this.g.setOnItemLongClickListener(new d(this, null));
        this.g.setOnItemClickListener(new e(this, null));
        return inflate;
    }

    private synchronized void a(Context context) {
        try {
            e.a("PushSelfShowLog", "enter syncDb");
            String c = a.c(context, "push.db");
            File file = new File(c);
            if (file.exists()) {
                e.b("PushSelfShowLog", "sync db from sdcard");
                a(context, c, "notify");
                a(context, c, "pushmsg");
                if (!file.delete()) {
                    e.d("PushSelfShowLog", "delete sdcard db failed!");
                }
            } else {
                e.b("PushSelfShowLog", "sdcard db is not exist");
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    private synchronized void a(Context context, String str, String str2) {
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2;
        Throwable th;
        Cursor cursor2;
        SQLiteDatabase readableDatabase;
        e.b("PushSelfShowLog", "enter syncTable:" + str2);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            e.d("PushSelfShowLog", "syncTable, dbPath or table is null");
        } else {
            SQLiteDatabase sQLiteDatabase3 = null;
            Cursor cursor3 = null;
            Cursor query;
            try {
                SQLiteDatabase writableDatabase = b.a(context).getWritableDatabase();
                int i = 1000;
                try {
                    Cursor query2;
                    query = writableDatabase.query(str2, null, null, null, null, null, null, null);
                    if (query != null) {
                        try {
                            i = 1000 - query.getCount();
                        } catch (Throwable e) {
                            cursor = null;
                            sQLiteDatabase = writableDatabase;
                            sQLiteDatabase2 = null;
                            th = e;
                            cursor2 = query;
                            try {
                                e.c("PushSelfShowLog", th.toString(), th);
                                if (cursor != null) {
                                    try {
                                        cursor.close();
                                    } catch (Throwable th2) {
                                        e.c("PushSelfShowLog", th2.toString(), th2);
                                    }
                                }
                                if (cursor2 != null) {
                                    try {
                                        cursor2.close();
                                    } catch (Throwable th22) {
                                        e.c("PushSelfShowLog", th22.toString(), th22);
                                    }
                                }
                                if (sQLiteDatabase2 != null) {
                                    try {
                                        sQLiteDatabase2.close();
                                    } catch (Throwable th222) {
                                        e.c("PushSelfShowLog", th222.toString(), th222);
                                    }
                                }
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.close();
                                    } catch (Throwable th2222) {
                                        e.c("PushSelfShowLog", th2222.toString(), th2222);
                                    }
                                }
                            } catch (Throwable th3) {
                                th2222 = th3;
                                query = cursor2;
                                cursor3 = cursor;
                                sQLiteDatabase3 = sQLiteDatabase2;
                                if (cursor3 != null) {
                                    try {
                                        cursor3.close();
                                    } catch (Throwable e2) {
                                        e.c("PushSelfShowLog", e2.toString(), e2);
                                    }
                                }
                                if (query != null) {
                                    try {
                                        query.close();
                                    } catch (Throwable e22) {
                                        e.c("PushSelfShowLog", e22.toString(), e22);
                                    }
                                }
                                if (sQLiteDatabase3 != null) {
                                    try {
                                        sQLiteDatabase3.close();
                                    } catch (Throwable e222) {
                                        e.c("PushSelfShowLog", e222.toString(), e222);
                                    }
                                }
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.close();
                                    } catch (Throwable e2222) {
                                        e.c("PushSelfShowLog", e2222.toString(), e2222);
                                    }
                                }
                                throw th2222;
                            }
                        } catch (Throwable e22222) {
                            sQLiteDatabase = writableDatabase;
                            th2222 = e22222;
                            if (cursor3 != null) {
                                cursor3.close();
                            }
                            if (query != null) {
                                query.close();
                            }
                            if (sQLiteDatabase3 != null) {
                                sQLiteDatabase3.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            throw th2222;
                        }
                    }
                    e.a("PushSelfShowLog", "canInsertDataNum is:" + i);
                    if (i > 0) {
                        readableDatabase = b.a(context, str).getReadableDatabase();
                        try {
                            query2 = readableDatabase.query(true, str2, null, null, null, null, null, null, null);
                            if (query2 != null) {
                                while (query2.moveToNext() && query2.getPosition() < i) {
                                    try {
                                        writableDatabase.insert(str2, null, a(query2, str2));
                                    } catch (Throwable e3) {
                                        sQLiteDatabase2 = readableDatabase;
                                        cursor2 = query;
                                        Cursor cursor4 = query2;
                                        sQLiteDatabase = writableDatabase;
                                        th2222 = e3;
                                        cursor = cursor4;
                                    } catch (Throwable e32) {
                                        cursor3 = query2;
                                        sQLiteDatabase3 = readableDatabase;
                                        sQLiteDatabase = writableDatabase;
                                        th2222 = e32;
                                    }
                                }
                            }
                        } catch (Throwable e322) {
                            sQLiteDatabase = writableDatabase;
                            sQLiteDatabase2 = readableDatabase;
                            th2222 = e322;
                            cursor2 = query;
                            cursor = null;
                            e.c("PushSelfShowLog", th2222.toString(), th2222);
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (sQLiteDatabase2 != null) {
                                sQLiteDatabase2.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                        } catch (Throwable e3222) {
                            sQLiteDatabase = writableDatabase;
                            sQLiteDatabase3 = readableDatabase;
                            th2222 = e3222;
                            if (cursor3 != null) {
                                cursor3.close();
                            }
                            if (query != null) {
                                query.close();
                            }
                            if (sQLiteDatabase3 != null) {
                                sQLiteDatabase3.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            throw th2222;
                        }
                    }
                    query2 = null;
                    readableDatabase = null;
                    if (query2 != null) {
                        try {
                            query2.close();
                        } catch (Throwable e32222) {
                            e.c("PushSelfShowLog", e32222.toString(), e32222);
                        }
                    }
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Throwable e322222) {
                            e.c("PushSelfShowLog", e322222.toString(), e322222);
                        }
                    }
                    if (readableDatabase != null) {
                        try {
                            readableDatabase.close();
                        } catch (Throwable e222222) {
                            e.c("PushSelfShowLog", e222222.toString(), e222222);
                        }
                    }
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.close();
                        } catch (Throwable th22222) {
                            e.c("PushSelfShowLog", th22222.toString(), th22222);
                        }
                    }
                } catch (Throwable e2222222) {
                    cursor = null;
                    sQLiteDatabase = writableDatabase;
                    sQLiteDatabase2 = null;
                    th22222 = e2222222;
                    cursor2 = null;
                    e.c("PushSelfShowLog", th22222.toString(), th22222);
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.close();
                    }
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                } catch (Throwable e22222222) {
                    query = null;
                    sQLiteDatabase = writableDatabase;
                    th22222 = e22222222;
                    if (cursor3 != null) {
                        cursor3.close();
                    }
                    if (query != null) {
                        query.close();
                    }
                    if (sQLiteDatabase3 != null) {
                        sQLiteDatabase3.close();
                    }
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    throw th22222;
                }
            } catch (Exception e4) {
                th22222 = e4;
                cursor = null;
                sQLiteDatabase = null;
                sQLiteDatabase2 = null;
                cursor2 = null;
                e.c("PushSelfShowLog", th22222.toString(), th22222);
                if (cursor != null) {
                    cursor.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (sQLiteDatabase2 != null) {
                    sQLiteDatabase2.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th4) {
                th22222 = th4;
                query = null;
                sQLiteDatabase = null;
                if (cursor3 != null) {
                    cursor3.close();
                }
                if (query != null) {
                    query.close();
                }
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th22222;
            }
        }
    }

    private void a(Context context, boolean z) {
        if (z) {
            this.l.setText(d.a(context, "hwpush_unselectall"));
            this.l.setTextColor(context.getResources().getColor(d.e(context, "hwpush_select_color")));
            Drawable drawable = context.getResources().getDrawable(d.f(context, "hwpush_ic_toolbar_multiple1"));
            try {
                int identifier = context.getResources().getIdentifier("colorful_emui", "color", "androidhwext");
                if (identifier != 0) {
                    identifier = context.getResources().getColor(identifier);
                    if (identifier != 0) {
                        drawable.setTint(identifier);
                        this.l.setTextColor(identifier);
                    }
                }
            } catch (NotFoundException e) {
                e.d("PushSelfShowLog", e.toString());
            } catch (Exception e2) {
                e.d("PushSelfShowLog", e2.toString());
            }
            this.k.setBackgroundDrawable(drawable);
            return;
        }
        this.k.setBackgroundDrawable(context.getResources().getDrawable(d.f(context, "hwpush_ic_toolbar_multiple")));
        this.l.setText(d.a(context, "hwpush_selectall"));
        this.l.setTextColor(context.getResources().getColor(d.e(context, "hwpush_text_color_history_url")));
    }

    private void b() {
        if (this.i != null && this.g != null && this.h != null) {
            e.a("PushSelfShowLog", "count:" + this.i.getCount());
            if (this.i.getCount() == 0) {
                this.g.setVisibility(8);
                this.h.setVisibility(0);
                return;
            }
            this.g.setVisibility(0);
            this.h.setVisibility(8);
        }
    }

    private int c() {
        if (this.i == null) {
            return 0;
        }
        int i = 0;
        for (f fVar : this.i.a()) {
            int i2 = (fVar == null || !fVar.a()) ? i : i + 1;
            i = i2;
        }
        e.a("PushSelfShowLog", "selectItemsNum:" + i);
        return i;
    }

    private void d() {
        this.c.setVisibility(0);
        this.d.setText(d.a(this.b, "hwpush_deltitle"));
        this.f.b();
        this.g.setOnItemClickListener(new c(this, this.b, null));
        this.i.a(false);
        this.g.setLongClickable(false);
        if (1 == this.i.a().size()) {
            a(this.b, true);
        } else {
            a(this.b, false);
        }
    }

    private void e() {
        Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
        intent.putExtra("type", "html");
        intent.putExtra("selfshow_info", this.n);
        intent.putExtra("selfshow_token", this.o);
        intent.setFlags(268468240);
        intent.setPackage(this.b.getPackageName());
        this.b.finish();
        this.b.startActivity(intent);
    }

    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 1000:
                    e.a("PushSelfShowLog", "mHandler MSG_LOAD_DONE");
                    this.g.setAdapter(this.i);
                    b();
                    if (this.m) {
                        d();
                        return;
                    }
                    return;
                case ErrorCode.ERROR_HOSTAPP_UNAVAILABLE /*1001*/:
                    e.a("PushSelfShowLog", "mHandler MSG_DELETE_DONE");
                    if (this.m) {
                        e();
                        return;
                    }
                    this.g.setAdapter(this.i);
                    this.c.performClick();
                    b();
                    return;
                default:
                    return;
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "handleMessage error:" + message.what + MiPushClient.ACCEPT_TIME_SEPARATOR + e.toString(), e);
        }
        e.c("PushSelfShowLog", "handleMessage error:" + message.what + MiPushClient.ACCEPT_TIME_SEPARATOR + e.toString(), e);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        e.a("PushSelfShowLog", "FavoritesActivity onActivityResult");
    }

    public void onCreate(Intent intent) {
        e.a("PushSelfShowLog", "FavoritesActivity onCreate");
        try {
            this.m = intent.getBooleanExtra("selfshowMsgOutOfBound", false);
            this.n = intent.getByteArrayExtra("selfshow_info");
            this.o = intent.getByteArrayExtra("selfshow_token");
            View relativeLayout = new RelativeLayout(this.b);
            View a = a();
            this.h = (LinearLayout) a.findViewById(d.d(this.b, "hwpush_no_collection_view"));
            e.a("PushSelfShowLog", "mNoCollectionLayout:" + this.h);
            relativeLayout.addView(a);
            new Thread(new c(this)).start();
            this.b.setContentView(relativeLayout);
            this.f = new b(this.b);
            this.f.a(relativeLayout);
            this.f.a();
            this.c = (ImageView) this.b.findViewById(d.d(this.b, "hwpush_bt_delete"));
            this.d = (TextView) this.b.findViewById(d.d(this.b, "hwpush_txt_delitem"));
            this.e = (TextView) this.b.findViewById(d.d(this.b, "hwpush_txt_delnum"));
            a.a(this.b, this.d);
            a.a(this.b, this.e);
            if (a.d()) {
                int j = a.j(this.b);
                if (-1 != j) {
                    if (j == 0) {
                        j = this.b.getResources().getColor(d.e(this.b, "hwpush_black"));
                        this.c.setImageDrawable(this.b.getResources().getDrawable(d.f(this.b, "hwpush_ic_cancel_light")));
                        this.e.setBackground(this.b.getResources().getDrawable(d.f(this.b, "hwpush_pic_ab_number_light")));
                    } else {
                        j = this.b.getResources().getColor(d.e(this.b, "hwpush_white"));
                        this.c.setImageDrawable(this.b.getResources().getDrawable(d.f(this.b, "hwpush_ic_cancel")));
                        this.e.setBackground(this.b.getResources().getDrawable(d.f(this.b, "hwpush_pic_ab_number")));
                    }
                    this.d.setTextColor(j);
                    this.e.setTextColor(j);
                }
            }
            this.k = (ImageView) this.b.findViewById(d.d(this.b, "hwpush_bt_selectall_img"));
            this.j = (ImageView) this.b.findViewById(d.d(this.b, "hwpush_bt_delete_img"));
            this.l = (TextView) this.b.findViewById(d.d(this.b, "hwpush_bt_selectall_txt"));
            this.c.setOnClickListener(new a(this, this.b, null));
            this.k.setOnClickListener(new f(this, this.b, null));
            this.j.setOnClickListener(new b(this, this.b, null));
            if (this.m) {
                this.f.a(this.j);
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "call" + HtmlViewer.class.getName() + " onCreate(Intent intent) err: " + e.toString(), e);
        }
    }

    public void onDestroy() {
        e.a("PushSelfShowLog", "FavoritesActivity onDestroy");
        if (this.p != null && this.p.isShowing()) {
            this.p.dismiss();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        e.a("PushSelfShowLog", "FavoritesActivity onKeyDown");
        if (i == 4 && keyEvent.getAction() == 0) {
            boolean z = this.c.getVisibility() == 0;
            if (this.m) {
                e();
            } else if (z) {
                this.c.performClick();
            } else {
                this.b.finish();
            }
        }
        return true;
    }

    public void onPause() {
        e.a("PushSelfShowLog", "FavoritesActivity onPause");
    }

    public void onRestart() {
        e.a("PushSelfShowLog", "FavoritesActivity onRestart");
    }

    public void onResume() {
        e.a("PushSelfShowLog", "FavoritesActivity onResume");
    }

    public void onStart() {
        e.a("PushSelfShowLog", "FavoritesActivity onStart");
    }

    public void onStop() {
        e.a("PushSelfShowLog", "FavoritesActivity onStop");
    }

    public void setActivity(Activity activity) {
        this.b = activity;
    }
}
