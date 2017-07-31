package com.huluxia.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;
import com.huluxia.bbs.b.d;
import com.huluxia.mcfloat.InstanceZones.e;
import hlx.mcstorymode.c;
import java.util.HashMap;

/* compiled from: UtilsDrawable */
public class y {
    static HashMap<String, GradientDrawable> blA = new HashMap();
    static HashMap<String, GradientDrawable> blB = new HashMap();
    private static SparseArray<GradientDrawable> blC = new SparseArray();
    private static SparseArray<ShapeDrawable> blD = new SparseArray();
    private static SparseArray<ShapeDrawable> blE = new SparseArray();
    private static SparseArray<GradientDrawable> blF = new SparseArray();
    private static final HashMap<String, Integer> blu = new HashMap();
    private static SparseArray<ShapeDrawable> blv = new SparseArray();
    private static final HashMap<String, Integer> blw = new HashMap();
    private static final HashMap<String, Integer> blx = new HashMap();
    private static final HashMap<String, Integer> bly = new HashMap();
    private static final HashMap<String, Integer> blz = new HashMap();
    static int[] mColors = new int[]{d.tag_orange, d.tag_green_dark, d.tag_blue, d.tag_yellow, d.tag_violet, d.tag_purple, d.tag_violet2, d.tag_brown, d.progress_orange, d.tag_grey, d.tag_red};

    static {
        blz.put("实用工具", Integer.valueOf(mColors[0]));
        blz.put("动作射击", Integer.valueOf(mColors[1]));
        blz.put("格斗快打", Integer.valueOf(mColors[2]));
        blz.put("飞行空战", Integer.valueOf(mColors[3]));
        blz.put("格斗快打", Integer.valueOf(mColors[4]));
        blz.put("角色扮演", Integer.valueOf(mColors[5]));
        blz.put("酷跑闯关", Integer.valueOf(mColors[6]));
        blz.put("恋爱养成", Integer.valueOf(mColors[7]));
        blz.put("模拟经营", Integer.valueOf(mColors[8]));
        blz.put("赛车竞速", Integer.valueOf(mColors[9]));
        blz.put("探秘解谜", Integer.valueOf(mColors[0]));
        blz.put("体育竞技", Integer.valueOf(mColors[1]));
        blz.put("消除游戏", Integer.valueOf(mColors[2]));
        blz.put("休闲益智", Integer.valueOf(mColors[3]));
        blw.put("经典建筑", Integer.valueOf(mColors[0]));
        blw.put("红石科技", Integer.valueOf(mColors[1]));
        blw.put("精美模型", Integer.valueOf(mColors[2]));
        blw.put("解密探索", Integer.valueOf(mColors[3]));
        blw.put("娱乐跑酷", Integer.valueOf(mColors[4]));
        blw.put("生存冒险", Integer.valueOf(mColors[5]));
        blw.put("玩家对抗", Integer.valueOf(mColors[6]));
        blw.put("惊险山车", Integer.valueOf(mColors[7]));
        blw.put("激情闯关", Integer.valueOf(mColors[8]));
        blw.put("常用功能", Integer.valueOf(mColors[0]));
        blw.put("恶搞娱乐", Integer.valueOf(mColors[1]));
        blw.put("创新玩法", Integer.valueOf(mColors[2]));
        blw.put("究极武器", Integer.valueOf(mColors[3]));
        blw.put("一键生成", Integer.valueOf(mColors[4]));
        blw.put("炫酷光影", Integer.valueOf(mColors[0]));
        blw.put("真实世界", Integer.valueOf(mColors[1]));
        blw.put("萌系动漫", Integer.valueOf(mColors[2]));
        blw.put("柔和唯美", Integer.valueOf(mColors[3]));
        blw.put("其他系列", Integer.valueOf(mColors[4]));
        blw.put("精品动漫", Integer.valueOf(mColors[0]));
        blw.put(c.bUs, Integer.valueOf(mColors[1]));
        blw.put("热门游戏", Integer.valueOf(mColors[2]));
        blw.put("名人明星", Integer.valueOf(mColors[3]));
        blw.put("影视小说", Integer.valueOf(mColors[4]));
        blw.put("动物家族", Integer.valueOf(mColors[5]));
        blw.put("默认皮肤", Integer.valueOf(mColors[6]));
        blx.put("沼泽", Integer.valueOf(mColors[0]));
        blx.put("雪地", Integer.valueOf(mColors[1]));
        blx.put("空岛", Integer.valueOf(mColors[2]));
        blx.put("矿洞", Integer.valueOf(mColors[3]));
        blx.put("山丘", Integer.valueOf(mColors[4]));
        blx.put("沙漠", Integer.valueOf(mColors[5]));
        blx.put("海洋", Integer.valueOf(mColors[6]));
        blx.put("丛林", Integer.valueOf(mColors[7]));
        blx.put("村庄", Integer.valueOf(mColors[8]));
        blx.put("无限", Integer.valueOf(mColors[9]));
        blx.put("平原", Integer.valueOf(mColors[0]));
        blx.put("城市", Integer.valueOf(mColors[1]));
        bly.put("冒险", Integer.valueOf(mColors[0]));
        bly.put("小游戏", Integer.valueOf(mColors[1]));
        bly.put("PVP", Integer.valueOf(mColors[2]));
        bly.put("经济系统", Integer.valueOf(mColors[3]));
        bly.put("阵营", Integer.valueOf(mColors[4]));
        bly.put(e.Xc, Integer.valueOf(mColors[5]));
        bly.put("饥饿游戏", Integer.valueOf(mColors[6]));
        bly.put("生存", Integer.valueOf(mColors[7]));
        bly.put("角色扮演", Integer.valueOf(mColors[8]));
        bly.put("PVE", Integer.valueOf(mColors[9]));
        bly.put("原生世界", Integer.valueOf(mColors[0]));
        bly.put("等级系统", Integer.valueOf(mColors[1]));
        blu.put("分类", Integer.valueOf(-7945998));
        blu.put("大型", Integer.valueOf(-1000069));
        blu.put("热门", Integer.valueOf(-33149));
        blu.put("独家", Integer.valueOf(-157336));
        blu.put("破解", Integer.valueOf(-6305911));
        blu.put("汉化", Integer.valueOf(-6567025));
        blu.put("谷歌", Integer.valueOf(-8658356));
    }

    public static int C(Context context, String cate) {
        Integer res = (Integer) blw.get(cate);
        if (res != null) {
            return context.getResources().getColor(res.intValue());
        }
        return context.getResources().getColor(d.tag_yellow);
    }

    public static int D(Context context, String cate) {
        Integer res = (Integer) blx.get(cate);
        if (res != null) {
            return context.getResources().getColor(res.intValue());
        }
        return context.getResources().getColor(d.tag_yellow);
    }

    public static int E(Context context, String cate) {
        Integer res = (Integer) bly.get(cate);
        if (res != null) {
            return context.getResources().getColor(res.intValue());
        }
        return context.getResources().getColor(d.tag_yellow);
    }

    public static int F(Context context, String cate) {
        Integer res = (Integer) blz.get(cate);
        if (res != null) {
            return context.getResources().getColor(res.intValue());
        }
        return context.getResources().getColor(d.tag_yellow);
    }

    public static Drawable G(Context context, String cate) {
        int color = F(context, cate);
        GradientDrawable drawable = (GradientDrawable) blA.get(cate);
        if (drawable != null) {
            return drawable;
        }
        drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, 40));
        drawable.setStroke(at.dipToPx(context, 1), color);
        blA.put(cate, drawable);
        return drawable;
    }

    public static Drawable H(Context context, String cate) {
        int color = E(context, cate);
        GradientDrawable drawable = (GradientDrawable) blB.get(cate);
        if (drawable != null) {
            return drawable;
        }
        drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius((float) at.dipToPx(context, 2));
        blB.put(cate, drawable);
        return drawable;
    }

    public static Drawable I(Context context, String cate) {
        int color = D(context, cate);
        GradientDrawable drawable = (GradientDrawable) blC.get(color);
        if (drawable != null) {
            return drawable;
        }
        drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, 40));
        drawable.setStroke(at.dipToPx(context, 1), color);
        blC.put(color, drawable);
        return drawable;
    }

    public static Drawable J(Context context, String cate) {
        int color = C(context, cate);
        GradientDrawable drawable = (GradientDrawable) blF.get(color);
        if (drawable != null) {
            return drawable;
        }
        drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, 40));
        drawable.setStroke(at.dipToPx(context, 1), color);
        blF.put(color, drawable);
        return drawable;
    }

    public static Drawable K(Context context, String cate) {
        int color = C(context, cate);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, 40));
        drawable.setStroke(at.dipToPx(context, 1), color);
        return drawable;
    }

    public static Drawable c(Context context, String cate, int radiusPx) {
        int color = C(context, cate);
        if (radiusPx < 0) {
            radiusPx = 0;
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius((float) radiusPx);
        return drawable;
    }

    public static Drawable z(int strokeWidthPx, int strokeColor, int radiusPx, int solidColor) {
        if (strokeWidthPx < 0) {
            strokeWidthPx = 0;
        }
        if (radiusPx < 0) {
            radiusPx = 0;
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(strokeWidthPx, strokeColor);
        drawable.setColor(solidColor);
        drawable.setCornerRadius((float) radiusPx);
        return drawable;
    }

    public static Drawable le(int color) {
        ShapeDrawable shapeDrawable = (ShapeDrawable) blv.get(color);
        if (shapeDrawable != null) {
            return shapeDrawable;
        }
        shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f}, null, null));
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setStyle(Style.FILL);
        blv.put(color, shapeDrawable);
        return shapeDrawable;
    }

    public static Drawable fK(String tag) {
        int color = -6305911;
        if (blu.containsKey(tag)) {
            color = ((Integer) blu.get(tag)).intValue();
        }
        return le(color);
    }

    public static Drawable fL(String cate) {
        int len = 2;
        if (cate != null) {
            len = cate.length();
        }
        int color = 7065814 + len;
        if (blu.containsKey("分类")) {
            color = ((Integer) blu.get("分类")).intValue();
        }
        return le(color);
    }

    public static Drawable lf(int color) {
        ShapeDrawable shapeDrawable = (ShapeDrawable) blD.get(color);
        if (shapeDrawable != null) {
            return shapeDrawable;
        }
        shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f}, null, null));
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setStyle(Style.FILL);
        blD.put(color, shapeDrawable);
        return shapeDrawable;
    }

    public static Drawable lg(int color) {
        ShapeDrawable shapeDrawable = (ShapeDrawable) blE.get(color);
        if (shapeDrawable != null) {
            return shapeDrawable;
        }
        shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f}, null, null));
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setStyle(Style.FILL);
        blE.put(color, shapeDrawable);
        return shapeDrawable;
    }

    public static Drawable c(Context context, int radius, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius((float) at.dipToPx(context, radius));
        return drawable;
    }

    public static int fM(String tag) {
        int color = d.map_day;
        if (blu.containsKey(tag)) {
            return ((Integer) blu.get(tag)).intValue();
        }
        return color;
    }

    public static Drawable d(Context context, int radius, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, radius));
        drawable.setStroke(at.dipToPx(context, 1), color);
        return drawable;
    }

    public static Drawable e(Context context, int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius((float) at.dipToPx(context, radius));
        return drawable;
    }

    public static Drawable d(Context context, String tag, int radius) {
        int color = d.map_day;
        if (blu.containsKey(tag)) {
            color = ((Integer) blu.get(tag)).intValue();
        }
        GradientDrawable drawable = (GradientDrawable) blF.get(color);
        if (drawable != null) {
            return drawable;
        }
        drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius((float) at.dipToPx(context, radius));
        drawable.setStroke(at.dipToPx(context, 1), context.getResources().getColor(color));
        blF.put(color, drawable);
        return drawable;
    }

    public static Bitmap v(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void a(View view, Drawable drawable) {
        if (VERSION.SDK_INT < 21) {
            int paddingTop = view.getPaddingTop();
            int paddingLeft = view.getPaddingLeft();
            int paddingRight = view.getPaddingRight();
            int paddingBottom = view.getPaddingBottom();
            view.setBackgroundDrawable(drawable);
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            return;
        }
        view.setBackgroundDrawable(drawable);
    }

    public static void b(View view, int drawableRes) {
        if (VERSION.SDK_INT < 21) {
            int paddingTop = view.getPaddingTop();
            int paddingLeft = view.getPaddingLeft();
            int paddingRight = view.getPaddingRight();
            int paddingBottom = view.getPaddingBottom();
            view.setBackgroundResource(drawableRes);
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            return;
        }
        view.setBackgroundResource(drawableRes);
    }

    public static Drawable a(Context context, int colorStroke, int colorCenter, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(at.dipToPx(context, 1), colorStroke);
        drawable.setColor(colorCenter);
        drawable.setCornerRadius((float) at.dipToPx(context, radius));
        return drawable;
    }

    public static StateListDrawable a(Context context, int colorNormal, int colorPressed, int colorStorke, int radius) {
        Drawable drawableNormal = a(context, colorStorke, colorNormal, radius);
        Drawable drawablePressed = a(context, colorStorke, colorPressed, radius);
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842910, 16842908}, drawableNormal);
        sd.addState(new int[]{16842919, 16842910}, drawablePressed);
        sd.addState(new int[]{16842908}, drawableNormal);
        sd.addState(new int[]{16842919}, drawablePressed);
        sd.addState(new int[]{16842910}, drawableNormal);
        sd.addState(new int[0], drawableNormal);
        return sd;
    }

    public static StateListDrawable b(Context context, int colorNormal, int colorChecked, int colorStorke, int radius) {
        Drawable drawableNormal = a(context, colorStorke, colorNormal, radius);
        Drawable drawableChecked = a(context, colorStorke, colorChecked, radius);
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842912}, drawableChecked);
        sd.addState(new int[0], drawableNormal);
        return sd;
    }
}
