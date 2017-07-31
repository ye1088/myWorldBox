package com.huluxia.mcfloat.enchant;

import com.huluxia.framework.R;
import com.huluxia.image.base.imageutils.d;
import com.huluxia.module.n;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: EnchantMcMap */
public class e {
    public static final int abY = 50;
    private static List<c> abZ = null;

    public static List<c> ux() {
        return abZ;
    }

    public static boolean fM(int inputId) {
        uy();
        for (int i = 0; i < abZ.size(); i++) {
            if (((c) abZ.get(i)).getItemId() == inputId) {
                return true;
            }
        }
        return false;
    }

    public static c fN(int inputId) {
        uy();
        for (int i = 0; i < abZ.size(); i++) {
            if (((c) abZ.get(i)).getItemId() == inputId) {
                return (c) abZ.get(i);
            }
        }
        return null;
    }

    public static void uy() {
        if (abZ == null) {
            int[] supportAttrSword = new int[25];
            Arrays.fill(supportAttrSword, -1);
            supportAttrSword[0] = 9;
            supportAttrSword[1] = 10;
            supportAttrSword[2] = 11;
            supportAttrSword[3] = 12;
            supportAttrSword[4] = 13;
            supportAttrSword[5] = 14;
            supportAttrSword[6] = 17;
            int[] supportAttrAXE = new int[25];
            Arrays.fill(supportAttrAXE, -1);
            supportAttrAXE[0] = 15;
            supportAttrAXE[1] = 16;
            supportAttrAXE[2] = 17;
            supportAttrAXE[3] = 18;
            supportAttrAXE_Ex = new int[25];
            Arrays.fill(supportAttrAXE_Ex, -1);
            supportAttrAXE_Ex[0] = 9;
            supportAttrAXE_Ex[1] = 15;
            supportAttrAXE_Ex[2] = 16;
            supportAttrAXE_Ex[3] = 17;
            supportAttrAXE_Ex[4] = 18;
            supportAttrHoe = new int[25];
            Arrays.fill(supportAttrHoe, -1);
            supportAttrHoe[0] = 17;
            supportAttrFishingRod = new int[25];
            Arrays.fill(supportAttrFishingRod, -1);
            supportAttrFishingRod[0] = 17;
            supportAttrFishingRod[1] = 23;
            supportAttrFishingRod[2] = 24;
            supportAttrScissor = new int[25];
            Arrays.fill(supportAttrScissor, -1);
            supportAttrScissor[0] = 16;
            supportAttrScissor[1] = 17;
            supportAttrBow = new int[25];
            Arrays.fill(supportAttrBow, -1);
            supportAttrBow[0] = 17;
            supportAttrBow[1] = 19;
            supportAttrBow[2] = 20;
            supportAttrBow[3] = 21;
            supportAttrBow[4] = 22;
            supportAttrHelmet = new int[25];
            Arrays.fill(supportAttrHelmet, -1);
            supportAttrHelmet[0] = 0;
            supportAttrHelmet[1] = 1;
            supportAttrHelmet[2] = 3;
            supportAttrHelmet[3] = 4;
            supportAttrHelmet[4] = 6;
            supportAttrHelmet[5] = 8;
            supportAttrHelmet[6] = 5;
            supportAttrHelmet[7] = 17;
            int[] supportAttrShoes = new int[25];
            Arrays.fill(supportAttrShoes, -1);
            supportAttrShoes[0] = 0;
            supportAttrShoes[1] = 1;
            supportAttrShoes[2] = 2;
            supportAttrShoes[3] = 3;
            supportAttrShoes[4] = 4;
            supportAttrShoes[5] = 5;
            supportAttrShoes[6] = 7;
            supportAttrShoes[7] = 17;
            int[] supportAttrClothes = new int[25];
            Arrays.fill(supportAttrClothes, -1);
            supportAttrClothes[0] = 0;
            supportAttrClothes[1] = 1;
            supportAttrClothes[2] = 3;
            supportAttrClothes[3] = 4;
            supportAttrClothes[4] = 5;
            supportAttrClothes[5] = 17;
            int[] supportAttrAll = new int[25];
            for (int i = 0; i < 25; i++) {
                supportAttrAll[i] = i;
            }
            abZ = new ArrayList(50);
            abZ.clear();
            abZ.add(new c(276, 0, false, "钻石剑", R.drawable.item_276, 7, supportAttrSword));
            abZ.add(new c(283, 0, false, "金剑", R.drawable.item_283, 7, supportAttrSword));
            abZ.add(new c(267, 0, false, "铁剑", R.drawable.item_267, 7, supportAttrSword));
            abZ.add(new c(272, 0, false, "石剑", R.drawable.item_272, 7, supportAttrSword));
            abZ.add(new c(310, 0, false, "钻石盔", R.drawable.item_310, 8, supportAttrHelmet));
            abZ.add(new c(311, 0, false, "钻石甲", R.drawable.item_311, 6, supportAttrClothes));
            abZ.add(new c(312, 0, false, "钻石腿", R.drawable.item_312, 6, supportAttrClothes));
            abZ.add(new c(313, 0, false, "钻石鞋", R.drawable.item_313, 8, supportAttrShoes));
            abZ.add(new c(261, 0, false, "弓", R.drawable.item_261, 5, supportAttrBow));
            abZ.add(new c(346, 0, false, "钓鱼竿", R.drawable.item_346, 3, supportAttrFishingRod));
            abZ.add(new c(359, 0, false, "剪刀", R.drawable.item_359, 2, supportAttrScissor));
            abZ.add(new c(403, 0, false, "附魔书", R.drawable.item_340, 25, supportAttrAll));
            abZ.add(new c(277, 0, false, "钻石锹", R.drawable.item_277, 4, supportAttrAXE));
            abZ.add(new c(278, 0, false, "钻石镐", R.drawable.item_278, 4, supportAttrAXE));
            abZ.add(new c(279, 0, false, "钻石斧", R.drawable.item_279, 5, supportAttrAXE_Ex));
            abZ.add(new c(293, 0, false, "钻石锄", R.drawable.item_293, 1, supportAttrHoe));
            abZ.add(new c(314, 0, false, "金头盔", R.drawable.item_314, 8, supportAttrHelmet));
            abZ.add(new c(315, 0, false, "金胸甲", R.drawable.item_315, 6, supportAttrClothes));
            abZ.add(new c(316, 0, false, "金护腿", R.drawable.item_316, 6, supportAttrClothes));
            abZ.add(new c(317, 0, false, "金鞋子", R.drawable.item_317, 8, supportAttrShoes));
            abZ.add(new c(306, 0, false, "铁头盔", R.drawable.item_306, 8, supportAttrHelmet));
            abZ.add(new c(307, 0, false, "铁胸甲", R.drawable.item_307, 6, supportAttrClothes));
            abZ.add(new c(308, 0, false, "铁护腿", R.drawable.item_308, 6, supportAttrClothes));
            abZ.add(new c(309, 0, false, "铁鞋子", R.drawable.item_309, 8, supportAttrShoes));
            abZ.add(new c(302, 0, false, "铁链帽", R.drawable.item_302, 8, supportAttrHelmet));
            abZ.add(new c(303, 0, false, "铁链甲", R.drawable.item_303, 6, supportAttrClothes));
            abZ.add(new c(304, 0, false, "铁链腿", R.drawable.item_304, 6, supportAttrClothes));
            abZ.add(new c(305, 0, false, "铁链鞋", R.drawable.item_305, 8, supportAttrShoes));
            abZ.add(new c(298, 0, false, "皮革帽", R.drawable.item_298, 8, supportAttrHelmet));
            abZ.add(new c(299, 0, false, "皮外套", R.drawable.item_299, 6, supportAttrClothes));
            abZ.add(new c(300, 0, false, "皮裤子", R.drawable.item_300, 6, supportAttrClothes));
            abZ.add(new c(301, 0, false, "皮鞋子", R.drawable.item_301, 8, supportAttrShoes));
            abZ.add(new c(284, 0, false, "金锹", R.drawable.item_284, 4, supportAttrAXE));
            abZ.add(new c(285, 0, false, "金镐", R.drawable.item_285, 4, supportAttrAXE));
            abZ.add(new c(286, 0, false, "金斧", R.drawable.item_286, 5, supportAttrAXE_Ex));
            abZ.add(new c(294, 0, false, "金锄", R.drawable.item_294, 1, supportAttrHoe));
            abZ.add(new c(256, 0, false, "铁锹", R.drawable.item_256, 4, supportAttrAXE));
            abZ.add(new c(257, 0, false, "铁镐", R.drawable.item_257, 4, supportAttrAXE));
            abZ.add(new c(258, 0, false, "铁斧", R.drawable.item_258, 5, supportAttrAXE_Ex));
            abZ.add(new c(292, 0, false, "铁锄", R.drawable.item_292, 1, supportAttrHoe));
            abZ.add(new c(273, 0, false, "石锹", R.drawable.item_273, 4, supportAttrAXE));
            abZ.add(new c(d.xI, 0, false, "石镐", R.drawable.item_274, 4, supportAttrAXE));
            abZ.add(new c(275, 0, false, "石斧", R.drawable.item_275, 5, supportAttrAXE_Ex));
            abZ.add(new c(291, 0, false, "石锄", R.drawable.item_291, 1, supportAttrHoe));
            abZ.add(new c(269, 0, false, "木锹", R.drawable.item_269, 4, supportAttrAXE));
            abZ.add(new c(270, 0, false, "木镐", R.drawable.item_270, 4, supportAttrAXE));
            abZ.add(new c(271, 0, false, "木斧", R.drawable.item_271, 5, supportAttrAXE_Ex));
            abZ.add(new c(n.awa, 0, false, "木锄", R.drawable.item_290, 1, supportAttrHoe));
            abZ.add(new c(268, 0, false, "木剑", R.drawable.item_268, 7, supportAttrSword));
            abZ.add(new c(259, 0, false, "打火石", R.drawable.item_259, 1, supportAttrHoe));
        }
    }
}
