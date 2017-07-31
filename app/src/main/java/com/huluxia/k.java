package com.huluxia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.huluxia.data.j;
import com.huluxia.data.map.MapProfileInfo.MapProfileItem;
import com.huluxia.data.server.a.a;
import com.huluxia.data.studio.b;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.mconline.activity.RoomCreateActivity;
import com.huluxia.studio.AnnounceDetailActivity;
import com.huluxia.studio.EditAnnounceActivity;
import com.huluxia.studio.StudioAnnounceActivity;
import com.huluxia.studio.StudioMembersActivity;
import com.huluxia.ui.base.WapActivity;
import com.huluxia.ui.mctool.FileSelectActivity;
import com.huluxia.ui.mctool.FollowingResActivity;
import com.huluxia.ui.mctool.JsImportActivity;
import com.huluxia.ui.mctool.JsListActivity;
import com.huluxia.ui.mctool.JsSearchActivity;
import com.huluxia.ui.mctool.MapExportActivity;
import com.huluxia.ui.mctool.MapImportActivity;
import com.huluxia.ui.mctool.MapPreExportActivity;
import com.huluxia.ui.mctool.MapSearchActivity;
import com.huluxia.ui.mctool.ProfileResListActivity;
import com.huluxia.ui.mctool.ServerDetailActivity;
import com.huluxia.ui.mctool.ServerListActivity;
import com.huluxia.ui.mctool.SkinImportActivity;
import com.huluxia.ui.mctool.SkinSearchActivity;
import com.huluxia.ui.mctool.WoodImportActivity;
import com.huluxia.ui.mctool.WoodSearchActivity;
import hlx.home.activity.GameOptionActivity;
import hlx.ui.personalstudio.PersonalStudioActivity;
import hlx.ui.publishres.PublishResourceActivity;

/* compiled from: McUIHelper */
public class k extends t {
    public static void N(Context context) {
        Intent in = new Intent();
        in.setClass(context, JsListActivity.class);
        context.startActivity(in);
    }

    public static void O(Context context) {
        Intent in = new Intent();
        in.setClass(context, MapImportActivity.class);
        context.startActivity(in);
    }

    public static void P(Context context) {
        Intent in = new Intent();
        in.setClass(context, MapPreExportActivity.class);
        context.startActivity(in);
    }

    public static void d(Context context, String path, String name) {
        Intent in = new Intent();
        in.putExtra("mappath", path);
        in.putExtra("mapname", name);
        in.setClass(context, MapExportActivity.class);
        context.startActivity(in);
    }

    public static void Q(Context context) {
        Intent in = new Intent();
        in.setClass(context, MapSearchActivity.class);
        context.startActivity(in);
    }

    public static void R(Context context) {
        Intent in = new Intent();
        in.setClass(context, JsSearchActivity.class);
        context.startActivity(in);
    }

    public static void S(Context context) {
        Intent in = new Intent();
        in.setClass(context, WoodSearchActivity.class);
        context.startActivity(in);
    }

    public static void T(Context context) {
        Intent in = new Intent();
        in.setClass(context, SkinSearchActivity.class);
        context.startActivity(in);
    }

    public static void k(Context context, String url) {
        Intent in = new Intent();
        in.putExtra("url", url);
        in.putExtra("title", "致葫芦丝");
        in.setClass(context, WapActivity.class);
        context.startActivity(in);
    }

    public static void e(Context context, String url, String title) {
        Intent in = new Intent();
        in.putExtra("url", url);
        in.putExtra("title", title);
        in.setClass(context, WapActivity.class);
        context.startActivity(in);
    }

    public static void U(Context context) {
        Intent in = new Intent();
        in.setClass(context, JsImportActivity.class);
        context.startActivity(in);
    }

    public static void V(Context context) {
        Intent in = new Intent();
        in.setClass(context, SkinImportActivity.class);
        context.startActivity(in);
    }

    public static void W(Context context) {
        Intent in = new Intent();
        in.setClass(context, WoodImportActivity.class);
        context.startActivity(in);
    }

    public static void a(Context context, a server_data) {
        Intent in = new Intent();
        in.setClass(context, ServerDetailActivity.class);
        in.putExtra(ServerDetailActivity.bbM, server_data);
        context.startActivity(in);
    }

    public static void X(Context context) {
        Intent in = new Intent();
        in.setClass(context, ServerListActivity.class);
        context.startActivity(in);
    }

    public static void C(String strName) {
        try {
            Intent intent = HTApplication.getAppContext().getPackageManager().getLaunchIntentForPackage(strName);
            intent.setFlags(268435456);
            HTApplication.getAppContext().startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void g(Activity context) {
        a(context, null);
    }

    public static void a(Activity context, MapProfileItem item) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("map", item);
            in.setClass(context, PublishResourceActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        t.an(context);
    }

    public static void a(Activity context, MapProfileItem item, int resType, int state) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("map", item);
            in.putExtra("resType", resType);
            in.putExtra(DownloadRecord.COLUMN_STATE, state);
            in.setClass(context, PublishResourceActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        t.an(context);
    }

    public static void h(Activity context) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.setClass(context, FollowingResActivity.class);
            context.startActivity(in);
            return;
        }
        t.an(context);
    }

    public static void b(Activity context, int resType) {
        Intent in = new Intent();
        in.putExtra("ResType", resType);
        in.setClass(context, FileSelectActivity.class);
        context.startActivityForResult(in, 1);
    }

    public static void a(Activity context, int resType, int requestCode) {
        Intent in = new Intent();
        in.putExtra("ResType", resType);
        in.setClass(context, FileSelectActivity.class);
        context.startActivityForResult(in, requestCode);
    }

    public static void Y(Context context) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.setClass(context, ProfileResListActivity.class);
            context.startActivity(in);
            return;
        }
        t.an(context);
    }

    public static void Z(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GameOptionActivity.class);
        context.startActivity(intent);
    }

    public static void startPersonalStudio(Context context, int studioId) {
        Intent intent = new Intent();
        intent.putExtra("STUDIO_ID", studioId);
        intent.setClass(context, PersonalStudioActivity.class);
        context.startActivity(intent);
    }

    public static void aa(Context context) {
        if (j.ep().ey()) {
            context.startActivity(new Intent(context, RoomCreateActivity.class));
        } else {
            t.an(context);
        }
    }

    public static void b(Context context, int studioId, int userRole) {
        Intent intent = new Intent();
        intent.putExtra("STUDIO_ID", studioId);
        intent.putExtra(StudioAnnounceActivity.aDV, userRole);
        intent.setClass(context, StudioAnnounceActivity.class);
        context.startActivity(intent);
    }

    public static void c(Context context, int studioId) {
        Intent intent = new Intent();
        intent.putExtra("STUDIO_ID", studioId);
        intent.setClass(context, EditAnnounceActivity.class);
        context.startActivity(intent);
    }

    public static void a(Context context, int studioId, int userRole, b.a item) {
        Intent intent = new Intent();
        intent.putExtra("STUDIO_ID", studioId);
        intent.putExtra(AnnounceDetailActivity.aDV, userRole);
        intent.putExtra(AnnounceDetailActivity.aDU, item);
        intent.setClass(context, AnnounceDetailActivity.class);
        context.startActivity(intent);
    }

    public static void d(Context context, int studioId) {
        Intent intent = new Intent(context, StudioMembersActivity.class);
        intent.putExtra("STUDIO_ID", studioId);
        context.startActivity(intent);
    }
}
