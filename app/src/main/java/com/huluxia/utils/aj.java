package com.huluxia.utils;

import android.annotation.SuppressLint;
import com.baidu.frontia.FrontiaError;
import com.huluxia.framework.R;
import com.huluxia.image.base.imageutils.d;
import com.huluxia.image.pipeline.memory.b;
import com.huluxia.mcfloat.InstanceZones.e;
import com.huluxia.module.n;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.EntityItem;
import hlx.launch.game.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpStatus;
import org.bytedeco.javacpp.avcodec.AVCodecContext;
import org.bytedeco.javacpp.avformat.AVStream;
import org.bytedeco.javacpp.avutil;

@SuppressLint({"UseSparseArrays", "DefaultLocale"})
/* compiled from: UtilsMcMap */
public class aj {
    private static Map<Integer, ItemStack> bmA = new HashMap();
    private static Map<Integer, ItemStack> bmB = new HashMap();
    private static Map<Integer, ItemStack> bmC = new HashMap();
    private static boolean bmD = false;
    private static Map<Integer, String> bmE;
    private static List<ItemStack> bmo = null;
    private static List<ItemStack> bmp = null;
    private static List<ItemStack> bmq = null;
    private static List<ItemStack> bmr = null;
    private static List<ItemStack> bms = null;
    private static List<ItemStack> bmt = null;
    private static List<ItemStack> bmu = null;
    private static List<ItemStack> bmv = null;
    private static List<ItemStack> bmw = null;
    private static List<ItemStack> bmx = null;
    private static List<ItemStack> bmy = null;
    private static ItemStack bmz = new ItemStack(0, 0, false, "未知物品", R.drawable.ic_unknown);

    public static List<ItemStack> Mf() {
        return bmo;
    }

    public static List<ItemStack> Mg() {
        return bmp;
    }

    public static List<ItemStack> Mh() {
        return bmq;
    }

    public static List<ItemStack> Mi() {
        return bmr;
    }

    public static List<ItemStack> Mj() {
        return bms;
    }

    public static List<ItemStack> Mk() {
        return bmt;
    }

    public static List<ItemStack> Ml() {
        return bmu;
    }

    public static List<ItemStack> Mm() {
        return bmv;
    }

    public static List<ItemStack> Mn() {
        return bmw;
    }

    public static List<ItemStack> Mo() {
        return bmx;
    }

    public static List<ItemStack> Mp() {
        return bmy;
    }

    public static ItemStack aC(int itemId, int dmg) {
        ItemStack item = (ItemStack) bmA.get(Integer.valueOf((itemId * 10000) + dmg));
        if (item == null) {
            item = (ItemStack) bmB.get(Integer.valueOf(itemId));
        }
        if (item == null) {
            return bmz;
        }
        return item;
    }

    public static ItemStack ls(int itemId) {
        if (bmC.size() == 0) {
            return null;
        }
        return (ItemStack) bmC.get(Integer.valueOf(itemId));
    }

    public static void d(String name, List<ItemStack> list) {
        if (list != null && name.length() != 0) {
            list.clear();
            name = name.toLowerCase();
            for (ItemStack item : bmr) {
                if (item.getItemName().toLowerCase().contains(name)) {
                    list.add(item);
                }
            }
            for (ItemStack item2 : bms) {
                if (item2.getItemName().toLowerCase().contains(name)) {
                    list.add(item2);
                }
            }
            for (ItemStack item22 : bmp) {
                if (item22.getItemName().toLowerCase().contains(name)) {
                    list.add(item22);
                }
            }
            for (ItemStack item222 : bmo) {
                if (item222.getItemName().toLowerCase().contains(name)) {
                    list.add(item222);
                }
            }
            for (ItemStack item2222 : bmq) {
                if (item2222.getItemName().toLowerCase().contains(name)) {
                    list.add(item2222);
                }
            }
            for (ItemStack item22222 : bmt) {
                if (item22222.getItemName().toLowerCase().contains(name)) {
                    list.add(item22222);
                }
            }
            for (ItemStack item222222 : bmu) {
                if (item222222.getItemName().toLowerCase().contains(name)) {
                    list.add(item222222);
                }
            }
            for (ItemStack item2222222 : bmv) {
                if (item2222222.getItemName().toLowerCase().contains(name)) {
                    list.add(item2222222);
                }
            }
            for (ItemStack item22222222 : bmw) {
                if (item22222222.getItemName().toLowerCase().contains(name)) {
                    list.add(item22222222);
                }
            }
            if (c.Sg().Sh() == 3 || c.Sg().Sh() == 4 || c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                for (ItemStack item222222222 : bmx) {
                    if (item222222222.getItemName().toLowerCase().contains(name)) {
                        list.add(item222222222);
                    }
                }
                for (ItemStack item2222222222 : bmy) {
                    if (item2222222222.getItemName().toLowerCase().contains(name)) {
                        list.add(item2222222222);
                    }
                }
            }
        }
    }

    public static String aD(int typeId, int dmg) {
        String _tmpTitle = null;
        for (ItemStack item : bmr) {
            if (item.getTypeId() == typeId && item.getDurability() == dmg) {
                _tmpTitle = item.getItemName();
            }
        }
        return _tmpTitle;
    }

    public static String aE(int typeId, int dmg) {
        return dmg == 0 ? "item_" + typeId : "item_" + typeId + "_" + dmg;
    }

    public static String lt(int typeId) {
        return ls(typeId).getItemName();
    }

    public static String lu(int typeId) {
        return "animal_" + typeId;
    }

    public static void a(List<ItemStack> list, ItemStack item) {
        list.add(item);
        bmA.put(Integer.valueOf((item.getItemId() * 10000) + item.getItemDmg()), item);
        if (((ItemStack) bmB.get(Integer.valueOf(item.getItemId()))) == null) {
            bmB.put(Integer.valueOf(item.getItemId()), item);
        }
    }

    public static void Mq() {
        a(bmx, new ItemStack(125, 0, false, "投掷器", R.drawable.item_125_0));
        a(bmx, new ItemStack(404, 0, false, "红石比较器", R.drawable.item_404_0));
        a(bmx, new ItemStack(23, 0, false, "发射器", R.drawable.item_23_0));
        a(bmx, new ItemStack(356, 0, false, "红石中继器", R.drawable.item_356_0));
        a(bmx, new ItemStack(HttpStatus.SC_GONE, 0, false, "漏斗", R.drawable.item_410_0));
        a(bmx, new ItemStack(HttpStatus.SC_REQUEST_TIMEOUT, 0, false, "漏斗矿车", R.drawable.item_408_0));
        a(bmx, new ItemStack(395, 0, false, "空地图", R.drawable.item_395_0));
        a(bmx, new ItemStack(380, 0, false, "炼药锅", R.drawable.item_380_0));
        a(bmx, new ItemStack(342, 0, false, "运输矿车", R.drawable.item_342_0));
        a(bmx, new ItemStack(389, 0, false, "物品展示框", R.drawable.item_389_0));
        a(bmx, new ItemStack(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED, 0, false, "TNT矿车", R.drawable.item_407_0));
        a(bmx, new ItemStack(358, 0, false, "地图", R.drawable.item_358_0));
    }

    public static void Mr() {
        a(bmx, new ItemStack(avutil.AV_PIX_FMT_BAYER_GRBG16LE, 0, false, "红石", R.drawable.item_331));
        a(bmx, new ItemStack(152, 0, true, "红石块", R.drawable.item_152));
        a(bmx, new ItemStack(76, 0, false, "红石火把", R.drawable.item_76_0));
        a(bmx, new ItemStack(123, 0, false, "红石灯", R.drawable.item_123_0));
        a(bmx, new ItemStack(143, 5, false, "木质按钮", R.drawable.item_143_5));
        a(bmx, new ItemStack(77, 5, false, "石质按钮", R.drawable.item_77_5));
        a(bmx, new ItemStack(72, 0, false, "木质压力板", R.drawable.item_72_0));
        a(bmx, new ItemStack(70, 0, false, "石质压力板", R.drawable.item_70_0));
        a(bmx, new ItemStack(126, 0, false, "激活铁轨", R.drawable.item_27_0));
        a(bmx, new ItemStack(28, 0, false, "探测铁轨", R.drawable.item_28_0));
        a(bmx, new ItemStack(27, 0, true, "动力铁轨", R.drawable.item_27));
        a(bmx, new ItemStack(25, 0, false, "音符盒", R.drawable.item_25_0));
        a(bmx, new ItemStack(131, 0, false, "绊线钩", R.drawable.item_131_0));
        a(bmx, new ItemStack(147, 0, false, "测重压力板 (轻质)", R.drawable.item_147_0));
        a(bmx, new ItemStack(148, 0, false, "测重压力板 (重质)", R.drawable.item_148_0));
        a(bmx, new ItemStack(69, 0, false, "拉杆", R.drawable.item_69_0));
        a(bmx, new ItemStack(151, 0, false, "阳光传感器", R.drawable.item_151_0));
        a(bmx, new ItemStack(146, 0, false, "陷阱箱", R.drawable.item_146_0));
    }

    public static void Ms() {
        a(bmy, new ItemStack(383, 45, false, "女巫蛋", R.drawable.item_383_45));
    }

    public static void Mt() {
        a(bmy, new ItemStack(383, 18, false, "兔子", R.drawable.item_383_18));
        a(bmy, new ItemStack(383, 22, false, "豹猫", R.drawable.item_383_22));
        a(bmy, new ItemStack(383, 43, false, "火焰幼体", R.drawable.item_383_43));
        a(bmy, new ItemStack(383, 10, false, "鸡", R.drawable.item_383_10));
        a(bmy, new ItemStack(383, 11, false, "牛", R.drawable.item_383_11));
        a(bmy, new ItemStack(383, 12, false, "猪", R.drawable.item_383_12));
        a(bmy, new ItemStack(383, 13, false, "羊", R.drawable.item_383_13));
        a(bmy, new ItemStack(383, 14, false, "狼", R.drawable.item_383_14));
        a(bmy, new ItemStack(383, 15, false, "村民", R.drawable.item_383_15));
        a(bmy, new ItemStack(383, 16, false, "哞菇", R.drawable.item_383_16));
        a(bmy, new ItemStack(383, 17, false, "鱿鱼", R.drawable.item_383_17));
        a(bmy, new ItemStack(383, 19, false, "蝙蝠", R.drawable.item_383_19));
        a(bmy, new ItemStack(383, 20, false, "铁傀儡", R.drawable.item_383_10));
        a(bmy, new ItemStack(383, 21, false, "雪傀儡", R.drawable.item_383_10));
        a(bmy, new ItemStack(383, 32, false, "僵尸", R.drawable.item_383_32));
        a(bmy, new ItemStack(383, 33, false, "爬行者", R.drawable.item_383_33));
        a(bmy, new ItemStack(383, 34, false, "骷髅", R.drawable.item_383_34));
        a(bmy, new ItemStack(383, 35, false, "蜘蛛", R.drawable.item_383_35));
        a(bmy, new ItemStack(383, 36, false, "僵尸猪人", R.drawable.item_383_36));
        a(bmy, new ItemStack(383, 37, false, "史莱姆", R.drawable.item_383_37));
        a(bmy, new ItemStack(383, 38, false, "末影人", R.drawable.item_383_38));
        a(bmy, new ItemStack(383, 39, false, "蠹虫", R.drawable.item_383_39));
        a(bmy, new ItemStack(383, 40, false, "洞穴蜘蛛", R.drawable.item_383_40));
        a(bmy, new ItemStack(383, 41, false, "恶魂", R.drawable.item_383_41));
        a(bmy, new ItemStack(383, 42, false, "岩浆怪", R.drawable.item_383_42));
    }

    public static void Mu() {
        if (!bmD) {
            bmD = true;
            Mv();
            if (c.Sg().Sh() == 7) {
                bmC.put(Integer.valueOf(23), new ItemStack(23, 0, false, "马", R.drawable.animal_23));
                bmC.put(Integer.valueOf(24), new ItemStack(24, 0, false, "骡子", R.drawable.animal_24));
                bmC.put(Integer.valueOf(25), new ItemStack(25, 0, false, "驴子", R.drawable.animal_25));
                bmC.put(Integer.valueOf(26), new ItemStack(26, 0, false, "骷髅马", R.drawable.animal_26));
                bmC.put(Integer.valueOf(27), new ItemStack(27, 0, false, "僵尸马", R.drawable.animal_27));
                bmC.put(Integer.valueOf(47), new ItemStack(47, 0, false, "剥皮者", R.drawable.animal_47));
                bmC.put(Integer.valueOf(48), new ItemStack(48, 0, false, "凋零骷髅", R.drawable.animal_48));
            }
            bmC.put(Integer.valueOf(10), new ItemStack(10, 0, false, "鸡", R.drawable.animal_10));
            bmC.put(Integer.valueOf(11), new ItemStack(11, 0, false, "牛", R.drawable.animal_11));
            bmC.put(Integer.valueOf(12), new ItemStack(12, 0, false, "猪", R.drawable.animal_12));
            bmC.put(Integer.valueOf(13), new ItemStack(13, 0, false, "羊", R.drawable.animal_13));
            bmC.put(Integer.valueOf(14), new ItemStack(14, 0, false, "狼", R.drawable.animal_14));
            bmC.put(Integer.valueOf(15), new ItemStack(15, 0, false, "村民", R.drawable.animal_15));
            bmC.put(Integer.valueOf(16), new ItemStack(16, 0, false, "哞菇", R.drawable.animal_16));
            bmC.put(Integer.valueOf(32), new ItemStack(32, 0, false, "僵尸", R.drawable.animal_32));
            bmC.put(Integer.valueOf(33), new ItemStack(33, 0, false, "爬行者", R.drawable.animal_33));
            bmC.put(Integer.valueOf(34), new ItemStack(34, 0, false, "骷髅", R.drawable.animal_34));
            bmC.put(Integer.valueOf(35), new ItemStack(35, 0, false, "蜘蛛", R.drawable.animal_35));
            bmC.put(Integer.valueOf(36), new ItemStack(36, 0, false, "僵尸猪人", R.drawable.animal_36));
            bmC.put(Integer.valueOf(37), new ItemStack(37, 0, false, "史莱姆", R.drawable.animal_37));
            bmC.put(Integer.valueOf(38), new ItemStack(38, 0, false, "末影人", R.drawable.animal_38));
            bmC.put(Integer.valueOf(39), new ItemStack(39, 0, false, "蠧虫", R.drawable.animal_39));
            bmC.put(Integer.valueOf(41), new ItemStack(41, 0, false, "恶魂", R.drawable.animal_41));
            if (c.Sg().Sh() == 2 || c.Sg().Sh() == 3 || c.Sg().Sh() == 4 || c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                bmC.put(Integer.valueOf(17), new ItemStack(17, 0, false, "鱿鱼", R.drawable.animal_17));
                bmC.put(Integer.valueOf(19), new ItemStack(19, 0, false, "蝙蝠", R.drawable.animal_19));
                bmC.put(Integer.valueOf(20), new ItemStack(20, 0, false, "铁傀儡", R.drawable.animal_20));
                bmC.put(Integer.valueOf(21), new ItemStack(21, 0, false, "雪傀儡", R.drawable.animal_21));
                bmC.put(Integer.valueOf(22), new ItemStack(22, 0, false, "豹猫", R.drawable.animal_22));
                bmC.put(Integer.valueOf(40), new ItemStack(40, 0, false, "洞穴蜘蛛", R.drawable.animal_40));
                bmC.put(Integer.valueOf(42), new ItemStack(42, 0, false, "岩浆怪", R.drawable.animal_42));
                bmC.put(Integer.valueOf(43), new ItemStack(43, 0, false, "烈焰人", R.drawable.animal_43));
                bmC.put(Integer.valueOf(44), new ItemStack(44, 0, false, "僵尸村民", R.drawable.animal_44));
                bmC.put(Integer.valueOf(18), new ItemStack(18, 0, false, "兔子", R.drawable.animal_18));
                if (c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                    bmC.put(Integer.valueOf(45), new ItemStack(45, 0, false, "女巫", R.drawable.animal_45));
                }
            }
            bmr = new ArrayList();
            a(bmr, new ItemStack(2, 0, true, "草方块", R.drawable.item_2));
            a(bmr, new ItemStack(3, 0, true, "泥土", R.drawable.item_3));
            a(bmr, new ItemStack(1, 0, true, "石头", R.drawable.item_1));
            a(bmr, new ItemStack(7, 0, true, "基岩", R.drawable.item_7));
            a(bmr, new ItemStack(87, 0, true, "地狱岩", R.drawable.item_87));
            a(bmr, new ItemStack(4, 0, true, "圆石", R.drawable.item_4));
            a(bmr, new ItemStack(48, 0, true, "苔石", R.drawable.item_48));
            a(bmr, new ItemStack(121, 0, true, "末地石", R.drawable.item_121));
            a(bmr, new ItemStack(255, 0, true, "光滑石", R.drawable.item_255));
            a(bmr, new ItemStack(49, 0, true, "黑曜石", R.drawable.item_49));
            a(bmr, new ItemStack(246, 0, true, "黑曜石", R.drawable.item_246));
            a(bmr, new ItemStack(98, 0, true, "石砖", R.drawable.item_98));
            a(bmr, new ItemStack(98, 1, true, "苔石砖", R.drawable.item_98_1));
            a(bmr, new ItemStack(98, 2, true, "裂石砖", R.drawable.item_98_2));
            a(bmr, new ItemStack(112, 0, true, "地狱砖", R.drawable.item_112));
            a(bmr, new ItemStack(155, 0, true, "石英块", R.drawable.item_155));
            a(bmr, new ItemStack(155, 1, true, "錾石英", R.drawable.item_155_1));
            a(bmr, new ItemStack(155, 2, true, "柱石英", R.drawable.item_155_2));
            a(bmr, new ItemStack(12, 0, true, "沙子", R.drawable.item_12));
            a(bmr, new ItemStack(13, 0, true, "沙砾", R.drawable.item_13));
            a(bmr, new ItemStack(24, 0, true, "沙石", R.drawable.item_24));
            a(bmr, new ItemStack(24, 1, true, "錾制沙石", R.drawable.item_24_1));
            a(bmr, new ItemStack(24, 2, true, "平滑沙石", R.drawable.item_24_2));
            a(bmr, new ItemStack(5, 0, true, "木板", R.drawable.item_5));
            a(bmr, new ItemStack(17, 0, true, "木头", R.drawable.item_17));
            a(bmr, new ItemStack(17, 1, true, "松木", R.drawable.item_17_1));
            a(bmr, new ItemStack(17, 2, true, "桦木", R.drawable.item_17_2));
            a(bmr, new ItemStack(18, 0, true, "树叶", R.drawable.item_18));
            a(bmr, new ItemStack(18, 1, true, "松树叶", R.drawable.item_18_1));
            a(bmr, new ItemStack(18, 2, true, "桦树叶", R.drawable.item_18_2));
            a(bmr, new ItemStack(60, 0, true, "耕地", R.drawable.item_60));
            a(bmr, new ItemStack(110, 0, true, "菌丝砖", R.drawable.item_110));
            a(bmr, new ItemStack(243, 0, true, "灰化土", R.drawable.item_243));
            a(bmr, new ItemStack(102, 0, true, "玻璃板", R.drawable.item_102));
            a(bmr, new ItemStack(170, 0, true, "干草块", R.drawable.item_170));
            a(bmr, new ItemStack(95, 0, true, "隐形基岩", R.drawable.item_95));
            a(bmr, new ItemStack(52, 0, true, "刷怪笼", R.drawable.item_52));
            a(bmr, new ItemStack(172, 0, true, "硬化粘土", R.drawable.item_172));
            a(bmr, new ItemStack(47, 0, true, "书架", R.drawable.item_47));
            a(bmr, new ItemStack(174, 0, true, "浮冰", R.drawable.item_174));
            a(bmr, new ItemStack(30935, 17096, false, "雪", R.drawable.item_30935_17096, 83));
            a(bmr, new ItemStack(19, 0, true, "海绵", R.drawable.item_19));
            a(bmr, new ItemStack(78, 0, false, "雪", R.drawable.item_78));
            a(bmr, new ItemStack(44, 0, true, "石台阶", R.drawable.item_44));
            a(bmr, new ItemStack(44, 1, true, "沙石台阶", R.drawable.item_44_1));
            a(bmr, new ItemStack(44, 2, true, "木台阶", R.drawable.item_44_2));
            a(bmr, new ItemStack(44, 3, true, "圆台阶", R.drawable.item_44_3));
            a(bmr, new ItemStack(44, 4, true, "砖台阶", R.drawable.item_44_4));
            a(bmr, new ItemStack(44, 5, true, "石砖台阶", R.drawable.item_44_5));
            a(bmr, new ItemStack(44, 6, true, "石英台阶", R.drawable.item_44_6));
            a(bmr, new ItemStack(67, 0, false, "石楼梯", R.drawable.item_67));
            a(bmr, new ItemStack(53, 0, false, "木楼梯", R.drawable.item_53));
            a(bmr, new ItemStack(108, 0, false, "砖楼梯", R.drawable.item_108));
            a(bmr, new ItemStack(109, 0, false, "石砖楼梯", R.drawable.item_109));
            a(bmr, new ItemStack(114, 0, false, "地狱梯", R.drawable.item_114));
            a(bmr, new ItemStack(128, 0, false, "沙石楼梯", R.drawable.item_128));
            a(bmr, new ItemStack(134, 0, false, "云杉梯", R.drawable.item_134));
            a(bmr, new ItemStack(135, 0, false, "桦木梯", R.drawable.item_135));
            a(bmr, new ItemStack(136, 0, false, "从林梯", R.drawable.item_136));
            a(bmr, new ItemStack(156, 0, false, "石英梯", R.drawable.item_156));
            a(bmr, new ItemStack(163, 0, false, "双木梯", R.drawable.item_163));
            a(bmr, new ItemStack(164, 0, false, "橡木楼梯", R.drawable.item_164));
            a(bmr, new ItemStack(159, 0, true, "白色粘土", R.drawable.item_159_0));
            a(bmr, new ItemStack(159, 1, true, "橙色粘土", R.drawable.item_159_1));
            a(bmr, new ItemStack(159, 2, true, "品红粘土", R.drawable.item_159_2));
            a(bmr, new ItemStack(159, 3, true, "淡蓝粘土", R.drawable.item_159_3));
            a(bmr, new ItemStack(159, 4, true, "黄色粘土", R.drawable.item_159_4));
            a(bmr, new ItemStack(159, 5, true, "黄绿粘土", R.drawable.item_159_5));
            a(bmr, new ItemStack(159, 6, true, "粉色粘土", R.drawable.item_159_6));
            a(bmr, new ItemStack(159, 7, true, "灰色粘土", R.drawable.item_159_7));
            a(bmr, new ItemStack(159, 8, true, "淡灰粘土", R.drawable.item_159_8));
            a(bmr, new ItemStack(159, 9, true, "青色粘土", R.drawable.item_159_9));
            a(bmr, new ItemStack(159, 10, true, "紫色粘土", R.drawable.item_159_10));
            a(bmr, new ItemStack(159, 11, true, "蓝色粘土", R.drawable.item_159_11));
            a(bmr, new ItemStack(159, 12, true, "棕色粘土", R.drawable.item_159_12));
            a(bmr, new ItemStack(159, 13, true, "绿色粘土", R.drawable.item_159_13));
            a(bmr, new ItemStack(159, 14, true, "红色粘土", R.drawable.item_159_14));
            a(bmr, new ItemStack(159, 15, true, "黑色粘土", R.drawable.item_159_15));
            a(bmr, new ItemStack(35, 0, true, "白羊毛", R.drawable.item_35));
            a(bmr, new ItemStack(35, 1, true, "橙羊毛", R.drawable.item_35_1));
            a(bmr, new ItemStack(35, 2, true, "品红羊毛", R.drawable.item_35_2));
            a(bmr, new ItemStack(35, 3, true, "淡蓝羊毛", R.drawable.item_35_3));
            a(bmr, new ItemStack(35, 4, true, "黄绿羊毛", R.drawable.item_35_4));
            a(bmr, new ItemStack(35, 5, true, "淡绿羊", R.drawable.item_35_5));
            a(bmr, new ItemStack(35, 6, true, "粉羊毛", R.drawable.item_35_6));
            a(bmr, new ItemStack(35, 7, true, "灰羊毛", R.drawable.item_35_7));
            a(bmr, new ItemStack(35, 8, true, "淡灰羊毛", R.drawable.item_35_8));
            a(bmr, new ItemStack(35, 9, true, "青羊毛", R.drawable.item_35_9));
            a(bmr, new ItemStack(35, 10, true, "紫羊毛", R.drawable.item_35_10));
            a(bmr, new ItemStack(35, 11, true, "蓝羊毛", R.drawable.item_35_11));
            a(bmr, new ItemStack(35, 12, true, "棕羊毛", R.drawable.item_35_12));
            a(bmr, new ItemStack(35, 13, true, "绿羊毛", R.drawable.item_35_13));
            a(bmr, new ItemStack(35, 14, true, "红羊毛", R.drawable.item_35_14));
            a(bmr, new ItemStack(35, 15, true, "黑羊毛", R.drawable.item_35_15));
            a(bmr, new ItemStack(171, 0, true, "白色毯", R.drawable.item_171));
            a(bmr, new ItemStack(171, 1, true, "橙色毯", R.drawable.item_171_1));
            a(bmr, new ItemStack(171, 2, true, "品红毯", R.drawable.item_171_2));
            a(bmr, new ItemStack(171, 3, true, "淡蓝毯", R.drawable.item_171_3));
            a(bmr, new ItemStack(171, 4, true, "黄地毯", R.drawable.item_171_4));
            a(bmr, new ItemStack(171, 5, true, "黄绿毯", R.drawable.item_171_5));
            a(bmr, new ItemStack(171, 6, true, "粉红毯", R.drawable.item_171_6));
            a(bmr, new ItemStack(171, 7, true, "灰色毯", R.drawable.item_171_7));
            a(bmr, new ItemStack(171, 8, true, "淡灰毯", R.drawable.item_171_8));
            a(bmr, new ItemStack(171, 9, true, "青色毯", R.drawable.item_171_9));
            a(bmr, new ItemStack(171, 10, true, "紫色毯", R.drawable.item_171_10));
            a(bmr, new ItemStack(171, 11, true, "蓝色毯", R.drawable.item_171_11));
            a(bmr, new ItemStack(171, 12, true, "棕色毯", R.drawable.item_171_12));
            a(bmr, new ItemStack(171, 13, true, "绿色毯", R.drawable.item_171_13));
            a(bmr, new ItemStack(171, 14, true, "红色毯", R.drawable.item_171_14));
            a(bmr, new ItemStack(171, 15, true, "黑色毯", R.drawable.item_171_15));
            if (c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                a(bmr, new ItemStack(165, 0, false, "粘液块", R.drawable.item_165_0));
            }
            bms = new ArrayList();
            a(bms, new ItemStack(14, 0, true, "金矿石", R.drawable.item_14));
            a(bms, new ItemStack(41, 0, true, "金块", R.drawable.item_41));
            a(bms, new ItemStack(266, 0, false, "金锭", R.drawable.item_266));
            a(bms, new ItemStack(56, 0, true, "钻石矿", R.drawable.item_56));
            a(bms, new ItemStack(57, 0, true, "钻石块", R.drawable.item_57));
            a(bms, new ItemStack(264, 0, false, "钻石", R.drawable.item_264));
            a(bms, new ItemStack(15, 0, true, "铁矿石", R.drawable.item_15));
            a(bms, new ItemStack(42, 0, true, "铁块", R.drawable.item_42));
            a(bms, new ItemStack(265, 0, false, "铁锭", R.drawable.item_265));
            a(bms, new ItemStack(16, 0, true, "煤矿石", R.drawable.item_16));
            a(bms, new ItemStack(173, 0, true, "煤炭块", R.drawable.item_173));
            a(bms, new ItemStack(263, 0, false, "煤炭", R.drawable.item_263));
            a(bms, new ItemStack(21, 0, true, "青金石矿", R.drawable.item_21));
            a(bms, new ItemStack(22, 0, true, "青金石块", R.drawable.item_22));
            a(bms, new ItemStack(73, 0, true, "红石矿", R.drawable.item_73));
            a(bms, new ItemStack(74, 0, true, "发光红石", R.drawable.item_74));
            if (c.Sg().Sh() == 0 || c.Sg().Sh() == 1 || c.Sg().Sh() == 2) {
                a(bms, new ItemStack(152, 0, true, "红石块", R.drawable.item_152));
                a(bms, new ItemStack(avutil.AV_PIX_FMT_BAYER_GRBG16LE, 0, false, "红石", R.drawable.item_331));
            }
            a(bms, new ItemStack(129, 0, true, "绿宝石矿", R.drawable.item_129));
            a(bms, new ItemStack(133, 0, true, "绿宝石", R.drawable.item_133));
            a(bms, new ItemStack(89, 0, true, "萤石", R.drawable.item_89));
            a(bms, new ItemStack(348, 0, false, "萤石粉", R.drawable.item_348));
            bmp = new ArrayList();
            a(bmp, new ItemStack(54, 0, false, "箱子", R.drawable.item_54));
            a(bmp, new ItemStack(58, 0, false, "工作台", R.drawable.item_58));
            a(bmp, new ItemStack(61, 0, false, "熔炉", R.drawable.item_61));
            a(bmp, new ItemStack(245, 0, false, "切石机", R.drawable.item_245));
            a(bmp, new ItemStack(261, 0, false, "弓", R.drawable.item_261));
            a(bmp, new ItemStack(262, 0, false, "箭", R.drawable.item_262));
            a(bmp, new ItemStack(259, 0, false, "打火石", R.drawable.item_259));
            a(bmp, new ItemStack(268, 0, false, "木剑", R.drawable.item_268));
            a(bmp, new ItemStack(272, 0, false, "石剑", R.drawable.item_272));
            a(bmp, new ItemStack(267, 0, false, "铁剑", R.drawable.item_267));
            a(bmp, new ItemStack(283, 0, false, "金剑", R.drawable.item_283));
            a(bmp, new ItemStack(276, 0, false, "钻石剑", R.drawable.item_276));
            a(bmp, new ItemStack(269, 0, false, "木锹", R.drawable.item_269));
            a(bmp, new ItemStack(270, 0, false, "木镐", R.drawable.item_270));
            a(bmp, new ItemStack(271, 0, false, "木斧", R.drawable.item_271));
            a(bmp, new ItemStack(n.awa, 0, false, "木锄", R.drawable.item_290));
            a(bmp, new ItemStack(273, 0, false, "石锹", R.drawable.item_273));
            a(bmp, new ItemStack(d.xI, 0, false, "石镐", R.drawable.item_274));
            a(bmp, new ItemStack(275, 0, false, "石斧", R.drawable.item_275));
            a(bmp, new ItemStack(291, 0, false, "石锄", R.drawable.item_291));
            a(bmp, new ItemStack(256, 0, false, "铁锹", R.drawable.item_256));
            a(bmp, new ItemStack(257, 0, false, "铁镐", R.drawable.item_257));
            a(bmp, new ItemStack(258, 0, false, "铁斧", R.drawable.item_258));
            a(bmp, new ItemStack(292, 0, false, "铁锄", R.drawable.item_292));
            a(bmp, new ItemStack(284, 0, false, "金锹", R.drawable.item_284));
            a(bmp, new ItemStack(285, 0, false, "金镐", R.drawable.item_285));
            a(bmp, new ItemStack(286, 0, false, "金斧", R.drawable.item_286));
            a(bmp, new ItemStack(294, 0, false, "金锄", R.drawable.item_294));
            a(bmp, new ItemStack(277, 0, false, "钻石锹", R.drawable.item_277));
            a(bmp, new ItemStack(278, 0, false, "钻石镐", R.drawable.item_278));
            a(bmp, new ItemStack(279, 0, false, "钻石斧", R.drawable.item_279));
            a(bmp, new ItemStack(293, 0, false, "钻石锄", R.drawable.item_293));
            a(bmp, new ItemStack(298, 0, false, "皮革帽", R.drawable.item_298));
            a(bmp, new ItemStack(299, 0, false, "皮外套", R.drawable.item_299));
            a(bmp, new ItemStack(300, 0, false, "皮裤子", R.drawable.item_300));
            a(bmp, new ItemStack(301, 0, false, "皮鞋子", R.drawable.item_301));
            a(bmp, new ItemStack(302, 0, false, "铁链帽", R.drawable.item_302));
            a(bmp, new ItemStack(303, 0, false, "铁链甲", R.drawable.item_303));
            a(bmp, new ItemStack(304, 0, false, "铁链腿", R.drawable.item_304));
            a(bmp, new ItemStack(305, 0, false, "铁链鞋", R.drawable.item_305));
            a(bmp, new ItemStack(306, 0, false, "铁头盔", R.drawable.item_306));
            a(bmp, new ItemStack(307, 0, false, "铁胸甲", R.drawable.item_307));
            a(bmp, new ItemStack(308, 0, false, "铁护腿", R.drawable.item_308));
            a(bmp, new ItemStack(309, 0, false, "铁鞋子", R.drawable.item_309));
            a(bmp, new ItemStack(314, 0, false, "金头盔", R.drawable.item_314));
            a(bmp, new ItemStack(315, 0, false, "金胸甲", R.drawable.item_315));
            a(bmp, new ItemStack(316, 0, false, "金护腿", R.drawable.item_316));
            a(bmp, new ItemStack(317, 0, false, "金鞋子", R.drawable.item_317));
            a(bmp, new ItemStack(310, 0, false, "钻石盔", R.drawable.item_310));
            a(bmp, new ItemStack(311, 0, false, "钻石甲", R.drawable.item_311));
            a(bmp, new ItemStack(312, 0, false, "钻石腿", R.drawable.item_312));
            a(bmp, new ItemStack(313, 0, false, "钻石鞋", R.drawable.item_313));
            bmo = new ArrayList();
            a(bmo, new ItemStack(86, 0, false, "南瓜", R.drawable.item_86));
            a(bmo, new ItemStack(92, 0, false, "蛋糕", R.drawable.item_92));
            a(bmo, new ItemStack(141, 0, false, "胡萝卜", R.drawable.item_141));
            a(bmo, new ItemStack(142, 0, false, "马铃薯", R.drawable.item_142));
            a(bmo, new ItemStack(AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE, 0, false, "甜菜根", R.drawable.item_244));
            a(bmo, new ItemStack(260, 0, false, "红苹果", R.drawable.item_260));
            a(bmo, new ItemStack(282, 0, false, "蘑菇汤", R.drawable.item_282));
            a(bmo, new ItemStack(297, 0, false, "面包", R.drawable.item_297));
            a(bmo, new ItemStack(319, 0, false, "生猪排", R.drawable.item_319));
            a(bmo, new ItemStack(320, 0, false, "热猪排", R.drawable.item_320));
            a(bmo, new ItemStack(338, 0, false, "甘蔗", R.drawable.item_338));
            a(bmo, new ItemStack(353, 0, false, "糖", R.drawable.item_353));
            a(bmo, new ItemStack(354, 0, false, "蛋糕", R.drawable.item_354));
            a(bmo, new ItemStack(357, 0, false, "饼干", R.drawable.item_357));
            a(bmo, new ItemStack(e.Wx, 0, false, "西瓜片", R.drawable.item_360));
            a(bmo, new ItemStack(363, 0, false, "生牛肉", R.drawable.item_363));
            a(bmo, new ItemStack(364, 0, false, "牛排", R.drawable.item_364));
            a(bmo, new ItemStack(365, 0, false, "生鸡肉", R.drawable.item_365));
            a(bmo, new ItemStack(366, 0, false, "熟鸡肉", R.drawable.item_366));
            a(bmo, new ItemStack(391, 0, false, "胡萝卜", R.drawable.item_391));
            a(bmo, new ItemStack(392, 0, false, "土豆", R.drawable.item_392));
            a(bmo, new ItemStack(393, 0, false, "烤土豆", R.drawable.item_393));
            a(bmo, new ItemStack(400, 0, false, "南瓜饼", R.drawable.item_400));
            a(bmo, new ItemStack(457, 0, false, "甜菜根", R.drawable.item_457));
            a(bmo, new ItemStack(459, 0, false, "甜菜汤", R.drawable.item_459));
            a(bmo, new ItemStack(349, 0, false, "生鱼", R.drawable.item_349));
            a(bmo, new ItemStack(350, 0, false, "熟鱼", R.drawable.item_350));
            a(bmo, new ItemStack(367, 0, false, "腐肉", R.drawable.item_367));
            bmq = new ArrayList();
            a(bmq, new ItemStack(6, 0, false, "橡树苗", R.drawable.item_6));
            a(bmq, new ItemStack(6, 1, false, "云杉苗", R.drawable.item_6_1));
            a(bmq, new ItemStack(6, 2, false, "桦树苗", R.drawable.item_6_2));
            a(bmq, new ItemStack(37, 0, false, "蒲公英", R.drawable.item_37));
            a(bmq, new ItemStack(38, 0, false, "青色花", R.drawable.item_38));
            a(bmq, new ItemStack(39, 0, false, "棕蘑菇", R.drawable.item_39));
            a(bmq, new ItemStack(40, 0, false, "红蘑菇", R.drawable.item_40));
            a(bmq, new ItemStack(81, 0, false, "仙人掌", R.drawable.item_81));
            a(bmq, new ItemStack(83, 0, false, "甘蔗", R.drawable.item_83));
            a(bmq, new ItemStack(103, 0, false, "西瓜", R.drawable.item_103));
            a(bmq, new ItemStack(104, 0, false, "南瓜梗", R.drawable.item_104));
            a(bmq, new ItemStack(105, 0, false, "西瓜梗", R.drawable.item_105));
            a(bmq, new ItemStack(106, 0, false, "葡萄树", R.drawable.item_106));
            a(bmq, new ItemStack(111, 0, false, "睡莲", R.drawable.item_111));
            a(bmq, new ItemStack(175, 0, false, "向日葵", R.drawable.item_175));
            a(bmq, new ItemStack(175, 1, false, "丁香花", R.drawable.item_175_1));
            a(bmq, new ItemStack(175, 2, false, "高杆草", R.drawable.item_175_2));
            a(bmq, new ItemStack(175, 3, false, "蕨类", R.drawable.item_175_3));
            a(bmq, new ItemStack(175, 4, false, "蔷薇", R.drawable.item_175_4));
            a(bmq, new ItemStack(175, 5, false, "牡丹", R.drawable.item_175_5));
            a(bmq, new ItemStack(295, 0, false, "小麦种", R.drawable.item_295));
            a(bmq, new ItemStack(296, 0, false, "小麦", R.drawable.item_296));
            a(bmq, new ItemStack(361, 0, false, "南瓜子", R.drawable.item_361));
            a(bmq, new ItemStack(362, 0, false, "西瓜子", R.drawable.item_362));
            a(bmq, new ItemStack(458, 0, false, "甜菜子", R.drawable.item_458));
            bmt = new ArrayList();
            a(bmt, new ItemStack(351, 0, false, "墨囊", R.drawable.item_351));
            a(bmt, new ItemStack(351, 1, false, "红玫瑰", R.drawable.item_351_1));
            a(bmt, new ItemStack(351, 2, false, "仙人掌", R.drawable.item_351_2));
            a(bmt, new ItemStack(351, 3, false, "可可豆", R.drawable.item_351_3));
            a(bmt, new ItemStack(351, 4, false, "青金石", R.drawable.item_351_4));
            a(bmt, new ItemStack(351, 5, false, "紫染料", R.drawable.item_351_5));
            a(bmt, new ItemStack(351, 6, false, "青染料", R.drawable.item_351_6));
            a(bmt, new ItemStack(351, 7, false, "淡灰料", R.drawable.item_351_7));
            a(bmt, new ItemStack(351, 8, false, "灰染料", R.drawable.item_351_8));
            a(bmt, new ItemStack(351, 9, false, "粉染料", R.drawable.item_351_9));
            a(bmt, new ItemStack(351, 10, false, "黄绿料", R.drawable.item_351_10));
            a(bmt, new ItemStack(351, 11, false, "蒲公英", R.drawable.item_351_11));
            a(bmt, new ItemStack(351, 12, false, "淡蓝料", R.drawable.item_351_12));
            a(bmt, new ItemStack(351, 13, false, "品红料", R.drawable.item_351_13));
            a(bmt, new ItemStack(351, 14, false, "橙色料", R.drawable.item_351_14));
            a(bmt, new ItemStack(351, 15, false, "骨粉", R.drawable.item_351_15));
            bmu = new ArrayList();
            a(bmu, new ItemStack(8, 0, true, "水", R.drawable.item_8));
            a(bmu, new ItemStack(9, 0, true, "静止的水", R.drawable.item_9));
            a(bmu, new ItemStack(10, 0, true, "岩浆", R.drawable.item_10));
            a(bmu, new ItemStack(11, 0, true, "静止的浆", R.drawable.item_11));
            a(bms, new ItemStack(318, 0, false, "燧石", R.drawable.item_318));
            a(bmr, new ItemStack(45, 0, true, "砖块", R.drawable.item_45));
            a(bmu, new ItemStack(avutil.AV_PIX_FMT_YUV440P12BE, 0, false, "砖头", R.drawable.item_336));
            a(bms, new ItemStack(82, 0, true, "粘土块", R.drawable.item_82));
            a(bmu, new ItemStack(avutil.AV_PIX_FMT_NB, 0, false, "粘土", R.drawable.item_337));
            a(bmr, new ItemStack(139, 0, true, "圆石墙", R.drawable.item_139));
            a(bmr, new ItemStack(139, 1, true, "苔石墙", R.drawable.item_139_1));
            a(bmu, new ItemStack(66, 0, true, "铁轨", R.drawable.item_66));
            a(bmu, new ItemStack(26, 0, false, "残缺床", R.drawable.item_26));
            a(bmu, new ItemStack(355, 0, false, "床", R.drawable.item_355));
            a(bmu, new ItemStack(63, 0, false, "残缺木牌", R.drawable.item_63));
            a(bmu, new ItemStack(68, 0, false, "墙告示", R.drawable.item_68));
            a(bmu, new ItemStack(323, 0, false, "告示牌", R.drawable.item_323));
            a(bmu, new ItemStack(64, 0, false, "残缺木门", R.drawable.item_64));
            a(bmu, new ItemStack(324, 0, false, "木门", R.drawable.item_324));
            if (c.Sg().Sh() == 0 || c.Sg().Sh() == 1) {
                a(bmu, new ItemStack(71, 0, false, "残缺铁门", R.drawable.item_71));
                a(bmu, new ItemStack(330, 0, false, "铁门", R.drawable.item_330));
            }
            a(bmu, new ItemStack(107, 0, true, "栅栏门", R.drawable.item_107));
            a(bmu, new ItemStack(183, 0, true, "棕栅栏门", R.drawable.item_183));
            a(bmu, new ItemStack(96, 0, false, "活板门", R.drawable.item_96));
            a(bmp, new ItemStack(359, 0, false, "剪刀", R.drawable.item_359));
            a(bmu, new ItemStack(325, 0, false, "桶", R.drawable.item_325));
            a(bmu, new ItemStack(325, 1, false, "牛奶桶", R.drawable.item_325_1));
            a(bmu, new ItemStack(325, 8, false, "水桶", R.drawable.item_325_2));
            a(bmu, new ItemStack(325, 10, false, "岩浆桶", R.drawable.item_325_3));
            a(bmu, new ItemStack(50, 0, false, "火把", R.drawable.item_50));
            a(bmu, new ItemStack(51, 0, false, "火", R.drawable.item_51));
            a(bmu, new ItemStack(91, 0, false, "南瓜灯", R.drawable.item_91));
            a(bmu, new ItemStack(85, 0, false, "栅栏", R.drawable.item_85));
            a(bmu, new ItemStack(101, 0, false, "铁栏杆", R.drawable.item_101));
            a(bmr, new ItemStack(20, 0, false, "玻璃", R.drawable.item_20));
            a(bmu, new ItemStack(120, 0, true, "末地门", R.drawable.item_120));
            a(bmu, new ItemStack(247, 0, true, "反应核", R.drawable.item_247));
            a(bmu, new ItemStack(46, 0, true, "TNT", R.drawable.item_46));
            a(bmu, new ItemStack(405, 0, false, "地狱砖", R.drawable.item_405));
            a(bmu, new ItemStack(65, 0, false, "梯子", R.drawable.item_65));
            a(bmu, new ItemStack(31, 0, false, "枯灌木", R.drawable.item_31));
            a(bmu, new ItemStack(30, 0, true, "蜘蛛网", R.drawable.item_30));
            a(bmu, new ItemStack(280, 0, false, "木棍", R.drawable.item_280));
            a(bmu, new ItemStack(281, 0, false, "碗", R.drawable.item_281));
            a(bmu, new ItemStack(287, 0, false, "线", R.drawable.item_287));
            a(bmu, new ItemStack(n.avY, 0, false, "羽毛", R.drawable.item_288));
            a(bmu, new ItemStack(n.avZ, 0, false, "火药", R.drawable.item_289));
            a(bmu, new ItemStack(321, 0, false, "画", R.drawable.item_321));
            a(bmu, new ItemStack(328, 0, false, "矿车", R.drawable.item_328));
            a(bmu, new ItemStack(avutil.AV_PIX_FMT_YUV440P10LE, 0, false, "小船", R.drawable.item_333));
            a(bmu, new ItemStack(346, 0, false, "钓鱼竿", R.drawable.item_346));
            a(bmu, new ItemStack(329, 0, false, "鞍", R.drawable.item_329));
            a(bmu, new ItemStack(avutil.AV_PIX_FMT_BAYER_GRBG16BE, 0, false, "雪球", R.drawable.item_332));
            a(bmu, new ItemStack(avutil.AV_PIX_FMT_YUV440P10BE, 0, false, "皮革", R.drawable.item_334));
            a(bmu, new ItemStack(339, 0, false, "纸", R.drawable.item_339));
            a(bmu, new ItemStack(340, 0, false, "书本", R.drawable.item_340));
            a(bmu, new ItemStack(341, 0, false, "粘液球", R.drawable.item_341));
            a(bmu, new ItemStack(344, 0, false, "鸡蛋", R.drawable.item_344));
            a(bmu, new ItemStack(345, 0, false, "指南针", R.drawable.item_345));
            a(bmu, new ItemStack(347, 0, false, "时钟", R.drawable.item_347));
            a(bmu, new ItemStack(352, 0, false, "骨头", R.drawable.item_352));
            if (c.Sg().Sh() == 0 || c.Sg().Sh() == 1) {
                a(bmu, new ItemStack(398, 0, false, "萝卜竿", R.drawable.item_398));
            }
            a(bmu, new ItemStack(406, 0, false, "下界石英", R.drawable.item_406));
            if (c.Sg().Sh() == 0 || c.Sg().Sh() == 2 || c.Sg().Sh() == 1) {
                a(bmu, new ItemStack(383, 15, false, "村民", R.drawable.item_383_15));
                a(bmu, new ItemStack(383, 10, false, "鸡", R.drawable.item_383_10));
                a(bmu, new ItemStack(383, 11, false, "牛", R.drawable.item_383_11));
                a(bmu, new ItemStack(383, 12, false, "猪", R.drawable.item_383_12));
                a(bmu, new ItemStack(383, 13, false, "羊", R.drawable.item_383_13));
                a(bmu, new ItemStack(383, 14, false, "狼", R.drawable.item_383_14));
                a(bmu, new ItemStack(383, 16, false, "哞菇", R.drawable.item_383_16));
                a(bmu, new ItemStack(383, 33, false, "爬行者", R.drawable.item_383_33));
                a(bmu, new ItemStack(383, 38, false, "末影人", R.drawable.item_383_38));
                a(bmu, new ItemStack(383, 39, false, "蠹虫", R.drawable.item_383_39));
                a(bmu, new ItemStack(383, 34, false, "骷髅", R.drawable.item_383_34));
                a(bmu, new ItemStack(383, 37, false, "史莱姆", R.drawable.item_383_37));
                a(bmu, new ItemStack(383, 35, false, "蜘蛛", R.drawable.item_383_35));
                a(bmu, new ItemStack(383, 32, false, "僵尸", R.drawable.item_383_32));
                a(bmu, new ItemStack(383, 36, false, "僵尸猪人", R.drawable.item_383_36));
            }
            if (c.Sg().Sh() == 1 || c.Sg().Sh() == 2) {
                a(bmu, new ItemStack(383, 17, false, "鱿鱼", R.drawable.item_383_17));
                a(bmu, new ItemStack(383, 40, false, "洞穴蜘蛛", R.drawable.item_383_40));
                a(bmu, new ItemStack(383, 42, false, "岩浆怪", R.drawable.item_383_42));
            }
            if (c.Sg().Sh() == 2) {
                a(bmu, new ItemStack(383, 43, false, "火焰幼体", R.drawable.item_383_43));
            }
            bmv = new ArrayList();
            bmw = new ArrayList();
            bmx = new ArrayList();
            bmy = new ArrayList();
            if (c.Sg().Sh() == 3 || c.Sg().Sh() == 4 || c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                Mr();
                Mt();
                if (c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                    Mq();
                    Ms();
                }
            }
            if (c.Sg().Sh() == 7) {
                a(bmv, new ItemStack(262, 13, false, "防火箭(0:22)", R.drawable.item_262_13));
                a(bmv, new ItemStack(262, 14, false, "防火箭(1:00)", R.drawable.item_262_14));
                a(bmv, new ItemStack(262, 6, false, "夜视箭(0:22)", R.drawable.item_262_6));
                a(bmv, new ItemStack(262, 7, false, "夜视箭(1:00)", R.drawable.item_262_7));
                a(bmv, new ItemStack(262, 31, false, "再生箭(0:02)", R.drawable.item_262_31));
                a(bmv, new ItemStack(262, 29, false, "再生箭(0:05)", R.drawable.item_262_29));
                a(bmv, new ItemStack(262, 30, false, "再生箭(0:15)", R.drawable.item_262_30));
                a(bmv, new ItemStack(262, 17, false, "迅捷箭(0:11)", R.drawable.item_262_17));
                a(bmv, new ItemStack(262, 15, false, "迅捷箭(0:22)", R.drawable.item_262_15));
                a(bmv, new ItemStack(262, 16, false, "迅捷箭(1:00)", R.drawable.item_262_16));
                a(bmv, new ItemStack(262, 19, false, "迟缓箭(0:30)", R.drawable.item_262_19));
                a(bmv, new ItemStack(262, 20, false, "水下呼吸箭(0:22)", R.drawable.item_262_20));
                a(bmv, new ItemStack(262, 21, false, "水下呼吸箭(1:00)", R.drawable.item_262_21));
                a(bmv, new ItemStack(262, 34, false, "力量箭(0:11)", R.drawable.item_262_34));
                a(bmv, new ItemStack(262, 32, false, "力量箭(0:22)", R.drawable.item_262_32));
                a(bmv, new ItemStack(262, 33, false, "力量箭(1:00)", R.drawable.item_262_33));
                a(bmv, new ItemStack(262, 28, false, "剧毒箭(0:02)", R.drawable.item_262_28));
                a(bmv, new ItemStack(262, 26, false, "剧毒箭(0:05)", R.drawable.item_262_26));
                a(bmv, new ItemStack(262, 27, false, "剧毒箭(0:15)", R.drawable.item_262_27));
                a(bmv, new ItemStack(262, 12, false, "跳跃箭(0:11)", R.drawable.item_262_12));
                a(bmv, new ItemStack(262, 10, false, "跳跃箭(0:22)", R.drawable.item_262_10));
                a(bmv, new ItemStack(262, 11, false, "跳跃箭(1:00)", R.drawable.item_262_11));
                a(bmv, new ItemStack(262, 22, false, "治疗箭", R.drawable.item_262_22));
                a(bmv, new ItemStack(262, 23, false, "治疗箭II", R.drawable.item_262_23));
                a(bmv, new ItemStack(262, 18, false, "迟缓箭(0:11)", R.drawable.item_262_18));
                a(bmv, new ItemStack(262, 36, false, "虚弱箭(0:30)", R.drawable.item_262_36));
                a(bmv, new ItemStack(262, 35, false, "虚弱箭(0:11)", R.drawable.item_262_35));
                a(bmv, new ItemStack(262, 24, false, "伤害箭", R.drawable.item_262_24));
                a(bmv, new ItemStack(262, 25, false, "伤害箭II", R.drawable.item_262_25));
                a(bmv, new ItemStack(262, 8, false, "隐身箭(0:22)", R.drawable.item_262_8));
                a(bmv, new ItemStack(262, 9, false, "隐身箭(1:00)", R.drawable.item_262_9));
                a(bmv, new ItemStack(398, 0, false, "萝卜钓竿", R.drawable.item_398_0));
                a(bmv, new ItemStack(385, 0, false, "火焰弹", R.drawable.item_385_0));
                a(bmv, new ItemStack(251, 0, false, "观察者", R.drawable.item_251_0));
                a(bmv, new ItemStack(383, 24, false, "生成驴", R.drawable.item_383_24));
                a(bmv, new ItemStack(383, 23, false, "生成马", R.drawable.item_383_3));
                a(bmv, new ItemStack(383, 25, false, "生成骡", R.drawable.item_383_25));
                a(bmv, new ItemStack(383, 48, false, "生成凋零骷髅", R.drawable.item_383_48));
                a(bmv, new ItemStack(383, 47, false, "生成剥皮者", R.drawable.item_383_47));
                a(bmv, new ItemStack(383, 27, false, "生成僵尸马", R.drawable.item_383_27));
                a(bmv, new ItemStack(383, 26, false, "生成骷髅马", R.drawable.item_383_26));
                a(bmv, new ItemStack(383, 46, false, "生成流浪者", R.drawable.item_383_46));
                a(bmv, new ItemStack(HttpStatus.SC_EXPECTATION_FAILED, 0, false, "铁马铠", R.drawable.item_417_0));
                a(bmv, new ItemStack(420, 0, false, "拴绳", R.drawable.item_420_0));
                a(bmv, new ItemStack(HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, 0, false, "钻石马铠", R.drawable.item_419_0));
                a(bmv, new ItemStack(418, 0, false, "金马铠", R.drawable.item_418_0));
                a(bmv, new ItemStack(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, 0, false, "皮马铠", R.drawable.item_416_0));
                a(bmv, new ItemStack(33, 1, false, "活塞", R.drawable.item_33_1));
                a(bmv, new ItemStack(421, 0, false, "命名牌", R.drawable.item_421_0));
                a(bmv, new ItemStack(29, 1, false, "粘性活塞", R.drawable.item_29_1));
                a(bmv, new ItemStack(HttpStatus.SC_LOCKED, 0, false, "生羊肉", R.drawable.item_423_0));
                a(bmv, new ItemStack(HttpStatus.SC_FAILED_DEPENDENCY, 0, false, "熟羊肉", R.drawable.item_424_0));
            }
            if (c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                a(bmv, new ItemStack(439, 0, false, "照相机", R.drawable.item_439_0));
            }
            a(bmv, new ItemStack(90, 0, false, "地狱门方块", R.drawable.item_90_0));
            if (c.Sg().Sh() == 3 || c.Sg().Sh() == 4 || c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                a(bmv, new ItemStack(430, 0, false, "金合欢木门", R.drawable.item_430_0));
                a(bmv, new ItemStack(428, 0, false, "白桦木门", R.drawable.item_428_0));
                a(bmv, new ItemStack(431, 0, false, "深色橡木门", R.drawable.item_431_0));
                a(bmv, new ItemStack(429, 0, false, "丛林木门", R.drawable.item_429_0));
                a(bmv, new ItemStack(427, 0, false, "云杉木门", R.drawable.item_427_0));
                a(bmv, new ItemStack(167, 0, false, "铁活板门", R.drawable.item_167_0));
                a(bmv, new ItemStack(330, 0, false, "铁门", R.drawable.item_330));
                a(bmv, new ItemStack(HttpStatus.SC_LENGTH_REQUIRED, 0, false, "生兔肉", R.drawable.item_411_0));
                a(bmv, new ItemStack(HttpStatus.SC_PRECONDITION_FAILED, 0, false, "熟兔肉", R.drawable.item_412_0));
                a(bmv, new ItemStack(HttpStatus.SC_REQUEST_TOO_LONG, 0, false, "兔肉煲", R.drawable.item_413_0));
                a(bmv, new ItemStack(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, 0, false, "兔子皮", R.drawable.item_415_0));
            }
            a(bmv, new ItemStack(116, 0, false, "附魔台", R.drawable.item_116_0));
            a(bmv, new ItemStack(379, 0, false, "酿造台", R.drawable.item_379_0));
            a(bmv, new ItemStack(145, 0, false, "铁砧", R.drawable.item_145_0));
            a(bmv, new ItemStack(369, 0, false, "烈焰棒", R.drawable.item_369_00));
            a(bmv, new ItemStack(397, 0, false, "骷髅头颅", R.drawable.item_397_0));
            a(bmv, new ItemStack(397, 1, false, "凋零骷髅头颅", R.drawable.item_397_1));
            a(bmv, new ItemStack(397, 2, false, "僵尸的头", R.drawable.item_397_2));
            a(bmv, new ItemStack(397, 3, false, "头", R.drawable.item_397_3));
            a(bmv, new ItemStack(397, 4, false, "爬行者的头", R.drawable.item_397_4));
            a(bmv, new ItemStack(371, 0, false, "金粒", R.drawable.item_371_00));
            a(bmv, new ItemStack(396, 0, false, "金萝卜", R.drawable.item_396_00));
            a(bmv, new ItemStack(370, 0, false, "恶魂之泪", R.drawable.item_370_00));
            a(bmv, new ItemStack(377, 0, false, "烈焰粉", R.drawable.item_377_00));
            a(bmv, new ItemStack(372, 0, false, "地狱疣", R.drawable.item_372_00));
            a(bmv, new ItemStack(375, 0, false, "蜘蛛眼", R.drawable.item_375_00));
            a(bmv, new ItemStack(376, 0, false, "发酵蛛眼", R.drawable.item_376_00));
            a(bmw, new ItemStack(b.HD, 0, false, "附魔之瓶", R.drawable.item_384_00));
            a(bmw, new ItemStack(374, 0, false, "玻璃瓶", R.drawable.item_374_00));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 0, false, "水瓶", R.drawable.item_373_00));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 1, false, "平凡药水", R.drawable.item_373_00));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 2, false, "平凡药水", R.drawable.item_373_00));
            a(bmv, new ItemStack(263, 1, false, "木炭", R.drawable.item_263_01));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 3, false, "浓稠药水", R.drawable.item_373_00));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 4, false, "粗制药水", R.drawable.item_373_00));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 5, false, "夜视(3)", R.drawable.item_373_05));
            a(bmw, new ItemStack(AVStream.MAX_STD_TIMEBASES, 6, false, "夜视(8)", R.drawable.item_373_05));
            a(bmw, new ItemStack(31016, 17049, false, "隐身(3)", R.drawable.item_31016_17049, 41));
            a(bmv, new ItemStack(31181, 17044, false, "兔子腿", R.drawable.item_31181_17044, 31));
            a(bmw, new ItemStack(31035, 17031, false, "隐身(8)", R.drawable.item_31016_17049, 26));
            a(bmw, new ItemStack(31029, 17032, false, "跳跃(3)", R.drawable.item_31029_17032, 12));
            a(bmw, new ItemStack(31009, 17055, false, "跳跃(3)", R.drawable.item_31029_17032, 32));
            a(bmw, new ItemStack(31028, 17033, false, "跳跃(II)", R.drawable.item_31029_17032, 13));
            a(bmv, new ItemStack(31039, 17026, false, "闪烁的西瓜", R.drawable.item_31039_17026, 13));
            a(bmw, new ItemStack(31010, 17044, false, "抗火(3)", R.drawable.item_31010_17044, 35));
            a(bmw, new ItemStack(31223, 17102, false, "抗火(8)", R.drawable.item_31010_17044, 78));
            a(bmw, new ItemStack(31033, 17027, false, "迅捷(3)", R.drawable.item_31033_17027, 24));
            a(bmw, new ItemStack(31027, 17032, false, "迅捷(8)", R.drawable.item_31033_17027, 18));
            a(bmw, new ItemStack(FrontiaError.Error_BCS, 17045, false, "迅捷(II)", R.drawable.item_31033_17027, 16));
            a(bmw, new ItemStack(31009, 17028, false, "迟缓(1.30)", R.drawable.item_31009_17028, 32));
            a(bmw, new ItemStack(31230, 17118, false, "迟缓(4)", R.drawable.item_31009_17028, 87));
            a(bmw, new ItemStack(31029, 17042, false, "水肺(3)", R.drawable.item_31029_17042, 12));
            a(bmw, new ItemStack(31026, 17052, false, "水肺(8)", R.drawable.item_31029_17042, 19));
            a(bmv, new ItemStack(31092, 17015, false, "金苹果", R.drawable.item_31092_17015, 2));
            a(bmw, new ItemStack(31005, 17084, false, "治疗(I)", R.drawable.item_31005_17084, 52));
            a(bmw, new ItemStack(31029, 17047, false, "治疗(II)", R.drawable.item_31005_17084, 12));
            a(bmw, new ItemStack(31038, 17051, false, "伤害(I)", R.drawable.item_31038_17051, 23));
            a(bmw, new ItemStack(FrontiaError.Error_User_Not_Active, 17007, false, "伤害(II)", R.drawable.item_31038_17051, 2));
            a(bmw, new ItemStack(31053, 16992, false, "剧毒(0.45)", R.drawable.item_31053_16992, 4));
            a(bmw, new ItemStack(31054, 16998, false, "剧毒(2)", R.drawable.item_31053_16992, 7));
            a(bmv, new ItemStack(FrontiaError.Error_Tag_Param, 17020, false, "金苹果", R.drawable.item_31102_17020, 8));
            a(bmw, new ItemStack(FrontiaError.Error_User_Not_Login, 16995, false, "剧毒(II)", R.drawable.item_31053_16992, 3));
            a(bmw, new ItemStack(31054, 16992, false, "再生(0.45)", R.drawable.item_31054_16992, 7));
            a(bmw, new ItemStack(FrontiaError.Error_User_Not_Login, 16997, false, "再生(2)", R.drawable.item_31054_16992, 3));
            a(bmw, new ItemStack(30996, 17084, false, "再生(II)", R.drawable.item_31054_16992, 45));
            a(bmw, new ItemStack(31011, 17032, false, "力量(3)", R.drawable.item_31011_17032, 34));
            a(bmw, new ItemStack(31029, 17057, false, "力量(8)", R.drawable.item_31011_17032, 12));
            a(bmw, new ItemStack(30978, 17049, false, "力量(II)", R.drawable.item_31011_17032, 67));
            a(bmw, new ItemStack(30996, 17024, false, "虚弱(1.30)", R.drawable.item_30996_17024, 45));
            a(bmw, new ItemStack(31224, 17133, false, "虚弱(4)", R.drawable.item_30996_17024, 89));
            a(bmw, new ItemStack(31227, 17038, false, "喷溅型水瓶", R.drawable.item_31227_17038, 25));
            a(bmw, new ItemStack(FrontiaError.Error_Put_Object_Data, 17048, false, "喷溅型平凡", R.drawable.item_31227_17038, 36));
            a(bmw, new ItemStack(31226, 17039, false, "喷溅型平凡", R.drawable.item_31227_17038, 24));
            a(bmw, new ItemStack(31223, 17025, false, "喷溅型浓稠", R.drawable.item_31227_17038, 13));
            a(bmw, new ItemStack(31200, 17043, false, "喷溅型粗制", R.drawable.item_31227_17038, 34));
            a(bmw, new ItemStack(31194, 17064, false, "喷溅型夜视(3)", R.drawable.item_31194_17064, 56));
            a(bmw, new ItemStack(31035, 17096, false, "喷溅型夜视(8)", R.drawable.item_31194_17064, 89));
            a(bmw, new ItemStack(31194, 17066, false, "喷溅型隐身(3)", R.drawable.item_31194_17066, 56));
            a(bmw, new ItemStack(31198, 17057, false, "喷溅型隐身(8)", R.drawable.item_31194_17066, 52));
            a(bmw, new ItemStack(FrontiaError.Error_ACL_Put, 17055, false, "喷溅型跳跃(3)", R.drawable.item_31203_17055, 33));
            a(bmw, new ItemStack(31229, 17030, false, "喷溅型跳跃(3)", R.drawable.item_31203_17055, 23));
            a(bmv, new ItemStack(FrontiaError.Error_File_Not_Authorized, 17022, false, "曲奇", R.drawable.item_31064_17022, 9));
            a(bmv, new ItemStack(31142, 17015, false, "南瓜派", R.drawable.item_31142_17015, 2));
            a(bmv, new ItemStack(31055, 17014, false, "岩浆膏", R.drawable.item_31055_17014, 1));
            a(bmw, new ItemStack(31118, 17010, false, "喷溅型跳跃(II)", R.drawable.item_31203_17055, 4));
            a(bmw, new ItemStack(31117, 17008, false, "喷溅型抗火(3)", R.drawable.item_31117_17008, 7));
            a(bmw, new ItemStack(FrontiaError.Error_Object_Not_Exist, 17048, false, "喷溅型抗火(8)", R.drawable.item_31117_17008, 32));
            a(bmw, new ItemStack(31191, 17068, false, "喷溅型迅捷(3)", R.drawable.item_31191_17068, 45));
            a(bmw, new ItemStack(31194, 17058, false, "喷溅型迅捷(8)", R.drawable.item_31191_17068, 56));
            a(bmw, new ItemStack(31169, 17064, false, "喷溅型迅捷(II)", R.drawable.item_31191_17068, 67));
            a(bmv, new ItemStack(30978, 17043, false, "木船", R.drawable.item_30978_17043, 27));
            a(bmv, new ItemStack(30987, 17027, false, "相思船", R.drawable.item_30987_17027, 18));
            a(bmv, new ItemStack(30986, 17037, false, "黑橡木船", R.drawable.item_30986_17037, 19));
            a(bmw, new ItemStack(31222, 17040, false, "喷溅型迟缓(1.30)", R.drawable.item_31222_17040, 12));
            a(bmw, new ItemStack(31229, 17054, false, "喷溅型迟缓(4)", R.drawable.item_31222_17040, 23));
            a(bmw, new ItemStack(31191, 17073, false, "喷溅型水肺(3)", R.drawable.item_31191_17073, 45));
            a(bmw, new ItemStack(31194, 17081, false, "喷溅型水肺(8)", R.drawable.item_31191_17073, 56));
            a(bmw, new ItemStack(31169, 17069, false, "喷溅型治疗(I)", R.drawable.item_31169_17069, 67));
            a(bmw, new ItemStack(31200, 17025, false, "喷溅型治疗(II)", R.drawable.item_31169_17069, 34));
            a(bmw, new ItemStack(31115, 17001, false, "喷溅型伤害(I)", R.drawable.item_31115_17001, 9));
            a(bmw, new ItemStack(31118, 16993, false, "喷溅型伤害(II)", R.drawable.item_31115_17001, 4));
            a(bmw, new ItemStack(31222, 17048, false, "喷溅型剧毒(0.45)", R.drawable.item_31222_17048, 12));
            a(bmw, new ItemStack(31222, 17051, false, "喷溅型剧毒(2)", R.drawable.item_31222_17048, 12));
            a(bmw, new ItemStack(31229, 17047, false, "喷溅型剧毒(II)", R.drawable.item_31222_17048, 23));
            a(bmv, new ItemStack(30888, 17010, false, "严重损坏的铁砧", R.drawable.item_30888_17010, 5));
            a(bmw, new ItemStack(31222, 17053, false, "喷溅型再生(0.45)", R.drawable.item_31222_17053, 12));
            a(bmw, new ItemStack(31200, 17034, false, "喷溅型再生(2)", R.drawable.item_31222_17053, 34));
            a(bmw, new ItemStack(31194, 17075, false, "喷溅型再生(II)", R.drawable.item_31222_17053, 56));
            a(bmw, new ItemStack(31200, 17032, false, "喷溅型力量(3)", R.drawable.item_31200_17032, 34));
            a(bmw, new ItemStack(31229, 17068, false, "喷溅型力量(8)", R.drawable.item_31200_17032, 23));
            a(bmw, new ItemStack(31194, 17036, false, "喷溅型力量(II)", R.drawable.item_31200_17032, 56));
            a(bmw, new ItemStack(31227, 17068, false, "喷溅型虚弱(1.30)", R.drawable.item_31227_17068, 25));
            a(bmw, new ItemStack(FrontiaError.Error_Object_Not_Exist, 17078, false, "喷溅型虚弱(4)", R.drawable.item_31227_17068, 32));
            a(bmv, new ItemStack(31095, 17019, false, "橡木船", R.drawable.item_31095_17019, 6));
            a(bmv, new ItemStack(31088, 17023, false, "杉木船", R.drawable.item_31088_17023, 9));
            a(bmv, new ItemStack(30997, 17051, false, "桦木船", R.drawable.item_30997_17051, 36));
            a(bmv, new ItemStack(30886, 17020, false, "轻微损坏的铁砧", R.drawable.item_30886_17020, 3));
            a(bmv, new ItemStack(31178, 17037, false, "花盆", R.drawable.item_31178_17037, 24));
            a(bmv, new ItemStack(30921, 17041, false, "下界石英矿石", R.drawable.item_30921_17041, 28));
            a(bmv, new ItemStack(88, 0, false, "灵魂沙", R.drawable.item_88_0));
            a(bmv, new ItemStack(113, 0, false, "地狱砖栅栏", R.drawable.item_113_0));
        }
    }

    private static void Mv() {
        bmE = new HashMap();
        bmE.put(Integer.valueOf(10), "鸡");
        bmE.put(Integer.valueOf(11), "牛");
        bmE.put(Integer.valueOf(12), "猪");
        bmE.put(Integer.valueOf(13), "羊");
        bmE.put(Integer.valueOf(14), "狼");
        bmE.put(Integer.valueOf(15), "村民");
        bmE.put(Integer.valueOf(16), "哞菇");
        bmE.put(Integer.valueOf(32), "僵尸");
        bmE.put(Integer.valueOf(33), "爬行者");
        bmE.put(Integer.valueOf(34), "骷髅");
        bmE.put(Integer.valueOf(35), "蜘蛛");
        bmE.put(Integer.valueOf(36), "僵尸猪人");
        bmE.put(Integer.valueOf(37), "史莱姆");
        bmE.put(Integer.valueOf(38), "末影人");
        bmE.put(Integer.valueOf(39), "蠹虫");
        bmE.put(Integer.valueOf(63), "PLAYER_63");
        bmE.put(Integer.valueOf(64), "ITEM_64");
        bmE.put(Integer.valueOf(65), "PRIMED_TNT_65");
        bmE.put(Integer.valueOf(66), "FALLING_BLOCK_66");
        bmE.put(Integer.valueOf(80), "ARROW_80");
        bmE.put(Integer.valueOf(81), "SNOWBALL_81");
        bmE.put(Integer.valueOf(82), "EGG_82");
        bmE.put(Integer.valueOf(83), "PAINTING_83");
        bmE.put(Integer.valueOf(84), "MINECART_84");
    }

    public static List<EntityItem> Mw() {
        List<EntityItem> itemList = new ArrayList();
        for (Entry<Integer, String> entry : bmE.entrySet()) {
            Entity entity = new Entity();
            entity.setEntityTypeId(((Integer) entry.getKey()).intValue());
            itemList.add(new EntityItem(1, entity));
        }
        return itemList;
    }
}
