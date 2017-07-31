package com.huluxia.mcmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.huluxia.mojang.converter.InventorySlot;
import com.huluxia.mojang.entity.EntityItem;
import com.huluxia.ui.maptool.MapEntityActivity;
import com.huluxia.ui.maptool.MapEntityAddActivity;
import com.huluxia.ui.maptool.MapEntityModifyActivity;
import com.huluxia.ui.maptool.MapPackActivity;
import com.huluxia.ui.maptool.MapPackAddActivity;
import com.huluxia.ui.maptool.MapPackModifyActivity;
import com.huluxia.ui.maptool.MapToolMainActivity;

/* compiled from: MCMapViewInterface */
public class c {
    public static void u(Context context, String inputMapName) {
        Intent intent = new Intent(context, MapToolMainActivity.class);
        intent.putExtra("mapName", inputMapName);
        context.startActivity(intent);
    }

    public static void aR(Context context) {
        Intent in = new Intent();
        in.setClass(context, MapPackActivity.class);
        context.startActivity(in);
    }

    public static void j(Activity context) {
        Intent in = new Intent();
        in.setClass(context, MapPackAddActivity.class);
        context.startActivityForResult(in, 100);
    }

    public static void a(Activity context, InventorySlot slot) {
        Intent in = new Intent();
        in.putExtra("InventorySlot", slot);
        in.setClass(context, MapPackModifyActivity.class);
        context.startActivityForResult(in, 100);
    }

    public static void aS(Context context) {
        Intent in = new Intent();
        in.setClass(context, MapEntityActivity.class);
        context.startActivity(in);
    }

    public static void k(Activity context) {
        Intent in = new Intent();
        in.setClass(context, MapEntityAddActivity.class);
        context.startActivityForResult(in, 100);
    }

    public static void a(Activity context, EntityItem item) {
        Intent in = new Intent();
        in.putExtra("EntityItem", item);
        in.setClass(context, MapEntityModifyActivity.class);
        context.startActivityForResult(in, 100);
    }
}
