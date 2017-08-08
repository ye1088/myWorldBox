package com.MCWorld.mcmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.entity.EntityItem;
import com.MCWorld.ui.maptool.MapEntityActivity;
import com.MCWorld.ui.maptool.MapEntityAddActivity;
import com.MCWorld.ui.maptool.MapEntityModifyActivity;
import com.MCWorld.ui.maptool.MapPackActivity;
import com.MCWorld.ui.maptool.MapPackAddActivity;
import com.MCWorld.ui.maptool.MapPackModifyActivity;
import com.MCWorld.ui.maptool.MapToolMainActivity;

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
