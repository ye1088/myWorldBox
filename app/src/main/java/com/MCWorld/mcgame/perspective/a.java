package com.MCWorld.mcgame.perspective;

import com.MCWorld.mcgame.g;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcinterface.j;
import com.MCWorld.mcjavascript.DTNativeBlockApi;
import com.MCWorld.mcjavascript.DTNativeLevelApi;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DTPerspective */
public class a {
    private static boolean agU = false;
    private static boolean agV = false;
    private static final int[] agW = new int[]{14, 15, 16, 21, 56, 73, 129};
    private static final int[] agX = new int[]{3, 12, 13, 24, 46};

    private static void init() {
        if (!agU) {
            for (int tmpBlockId : agW) {
                DTNativeBlockApi.setLightLevel(tmpBlockId, 15);
                DTNativeBlockApi.setShape(tmpBlockId, 1.0E-5d, 1.0E-5d, 1.0E-5d, 0.99999d, 0.99999d, 0.99999d, 0);
            }
            agU = true;
        }
    }

    public static boolean zc() {
        return agV;
    }

    public static void e(boolean isPerspective, boolean isUpdate) {
        if (g.wc() == 1) {
            init();
            int shapeLength = isPerspective ? 0 : 1;
            int lightLevel = isPerspective ? 15 : 0;
            for (int tmpBlockId : agX) {
                DTNativeBlockApi.setShape(tmpBlockId, 0.0d, 0.0d, 0.0d, (double) shapeLength, (double) shapeLength, (double) shapeLength, 0);
            }
            for (int tmpBlockId2 : agW) {
                DTNativeBlockApi.setLightLevel(tmpBlockId2, lightLevel);
                DTNativeBlockApi.setShape(tmpBlockId2, 1.0E-5d, 1.0E-5d, 1.0E-5d, 0.99999d, 0.99999d, 0.99999d, 0);
            }
            if (agV != isPerspective && isUpdate) {
                List<j> list = new ArrayList();
                list.add(new j(2, 0, (int) h.zF(), (int) h.zG(), (int) h.zH()));
                list.add(new j(0, 0, (int) h.zF(), (int) h.zG(), (int) h.zH()));
                h.D(list);
            }
            agV = isPerspective;
        }
    }

    public static void zd() {
        int posX = (int) h.zF();
        int posY = (int) h.zG();
        int posZ = (int) h.zH();
        if (agV) {
            int i = 0;
            while (i < 3) {
                int j = 0;
                while (j < 5) {
                    int k = 0;
                    while (k < 3) {
                        if (!(i == 0 && j == 0 && k == 0) && (!(i == 0 && j == 0 && k == 1) && DTNativeLevelApi.getTile((posX - 1) + i, (posY - 2) + j, (posZ - 1) + k) == 46)) {
                            DTNativeLevelApi.setTile((posX - 1) + i, (posY - 2) + j, (posZ - 1) + k, 3, 0);
                        }
                        k++;
                    }
                    j++;
                }
                i++;
            }
            int tmpTile = DTNativeLevelApi.getTile(posX, posY, posZ);
            if (tmpTile == 3 || tmpTile == 12 || tmpTile == 13 || tmpTile == 24) {
                DTNativeLevelApi.setTile(posX, posY, posZ, 46, 0);
            }
            if (tmpTile == 3 || tmpTile == 12 || tmpTile == 13 || tmpTile == 24) {
                DTNativeLevelApi.setTile(posX, posY, posZ + 1, 46, 0);
            }
        }
    }
}
