package com.MCWorld.ui.mctool;

import android.content.Context;
import android.content.Intent;
import com.MCWorld.ui.mctool.subject.JsRankListActivity;
import com.MCWorld.ui.mctool.subject.JsSubjectListActivity;
import com.MCWorld.ui.mctool.subject.MapRankListActivity;
import com.MCWorld.ui.mctool.subject.MapSubjectListActivity;
import com.MCWorld.ui.mctool.subject.SkinRankListActivity;
import com.MCWorld.ui.mctool.subject.SkinSubjectListActivity;
import com.MCWorld.ui.mctool.subject.WoodRankListActivity;
import com.MCWorld.ui.mctool.subject.WoodSubjectListActivity;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ResourceMgrView */
public class d {
    private static Map<String, Class<?>> bbL;

    public static void ID() {
        bbL = new HashMap();
        bbL.put("map_cate", MapSubjectListActivity.class);
        bbL.put("map_rank", MapRankListActivity.class);
        bbL.put("map_subject", MapSubjectListActivity.class);
        bbL.put("js_cate", JsSubjectListActivity.class);
        bbL.put("js_rank", JsRankListActivity.class);
        bbL.put("js_subject", JsSubjectListActivity.class);
        bbL.put("wood_cate", WoodSubjectListActivity.class);
        bbL.put("wood_rank", WoodRankListActivity.class);
        bbL.put("wood_subject", WoodSubjectListActivity.class);
        bbL.put("skin_cate", SkinSubjectListActivity.class);
        bbL.put("skin_rank", SkinRankListActivity.class);
        bbL.put("skin_subject", SkinSubjectListActivity.class);
    }

    public static void z(Context context, String name) {
        Intent in = new Intent();
        in.setClass(context, (Class) bbL.get(name));
        context.startActivity(in);
    }

    public static void b(Context context, String name, int id, String title) {
        Intent in = new Intent();
        in.setClass(context, (Class) bbL.get(name));
        in.putExtra("subject_type", 0);
        in.putExtra("subject_id", id);
        in.putExtra("subject_name", title);
        context.startActivity(in);
    }

    public static void c(Context context, String name, int id, String title) {
        Intent in = new Intent();
        in.setClass(context, (Class) bbL.get(name));
        in.putExtra("subject_type", 1);
        in.putExtra("subject_id", id);
        in.putExtra("subject_name", title);
        context.startActivity(in);
    }

    static {
        ID();
    }
}
