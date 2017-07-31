package com.huluxia.mcfloat.enchant;

import java.util.ArrayList;
import java.util.List;

/* compiled from: EnchantAttrList */
public class b {
    public static final int abN = 25;
    public static List<a> abO = null;

    public static String fG(int inputIndex) {
        if (abO == null) {
            ut();
        }
        return ((a) abO.get(inputIndex)).us();
    }

    public static int fH(int inputIndex) {
        if (abO == null) {
            ut();
        }
        return ((a) abO.get(inputIndex)).uq();
    }

    public static int fI(int inputIndex) {
        if (abO == null) {
            ut();
        }
        return ((a) abO.get(inputIndex)).ur();
    }

    public static void ut() {
        if (abO == null) {
            abO = new ArrayList(25);
            abO.clear();
            abO.add(new a(0, 5, "保护", 0));
            abO.add(new a(1, 5, "火焰保护", 1));
            abO.add(new a(2, 5, "掉落保护", 2));
            abO.add(new a(3, 5, "爆炸保护", 3));
            abO.add(new a(4, 5, "弹射物保护", 4));
            abO.add(new a(5, 5, "荆棘", 5));
            abO.add(new a(6, 5, "水下呼吸", 6));
            abO.add(new a(7, 5, "深海探索者", 7));
            abO.add(new a(8, 5, "水下速掘", 8));
            abO.add(new a(9, 5, "锋利", 9));
            abO.add(new a(10, 5, "亡灵杀手", 10));
            abO.add(new a(11, 5, "节肢杀手", 11));
            abO.add(new a(12, 5, "击退", 12));
            abO.add(new a(13, 5, "火焰附加", 13));
            abO.add(new a(14, 5, "抢夺", 14));
            abO.add(new a(15, 5, "效率", 15));
            abO.add(new a(16, 5, "精准采集", 16));
            abO.add(new a(17, 5, "耐久", 17));
            abO.add(new a(18, 5, "时运", 18));
            abO.add(new a(19, 5, "力量", 19));
            abO.add(new a(20, 5, "冲击", 20));
            abO.add(new a(21, 5, "火矢", 21));
            abO.add(new a(22, 5, "无限", 22));
            abO.add(new a(23, 5, "海之眷顾", 23));
            abO.add(new a(24, 5, "饵钓", 24));
        }
    }
}
