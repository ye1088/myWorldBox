package com.huluxia.mcfloat.build;

import android.content.Context;
import com.huluxia.mcfloat.dialog.c;
import com.huluxia.mcfloat.dialog.c.a;
import com.huluxia.mcfloat.dialog.c.b;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.r;
import com.huluxia.utils.ah;
import java.util.ArrayList;

/* compiled from: BuildRect */
public class d extends a {
    private static final String aaS = "Float_BuildRectAddX";
    private static final String aaT = "Float_BuildRectAddY";
    private static final String aaU = "Float_BuildRectAddZ";
    private static final String aaV = "Float_BuildRectAddType";
    private e Qp;
    private j aaW = null;
    private b abh = null;
    private a abi = new a(this) {
        final /* synthetic */ d abj;

        {
            this.abj = this$0;
        }

        public void a(b info) {
            ah.KZ().Q(d.aaS, info.x);
            ah.KZ().Q(d.aaT, info.y);
            ah.KZ().Q(d.aaU, info.z);
            ah.KZ().Q(d.aaV, info.type);
            this.abj.abh.b(info);
            this.abj.Qp.cm(this.abj.uk());
            r.ck().K("float_build_addrect");
        }
    };

    public d(e buildStatus) {
        this.Qp = buildStatus;
        this.abh = new b();
        int rectX = ah.KZ().get_config_sp_intVal(aaS, 5);
        int rectY = ah.KZ().get_config_sp_intVal(aaT, 5);
        int rectZ = ah.KZ().get_config_sp_intVal(aaU, 5);
        this.abh.d(ah.KZ().get_config_sp_intVal(aaV, 0), rectX, rectY, rectZ);
    }

    public void aO(Context context) {
        new c(context).a(this.abh, this.abi);
    }

    public void d(int id, int dmg, int x, int y, int z) {
        this.aaW = new j(id, dmg, x, y, z);
        ul();
    }

    public void e(int id, int dmg, int x, int y, int z) {
    }

    public ArrayList<j> ui() {
        ArrayList<j> oldList = null;
        if (this.abh.type == 0) {
            oldList = aQ(true);
        }
        if (this.abh.type == 1) {
            oldList = aP(true);
        }
        if (this.abh.type == 2) {
            oldList = aR(true);
        }
        if (h.B(oldList)) {
            return oldList;
        }
        return null;
    }

    public ArrayList<j> uj() {
        ArrayList<j> newList = null;
        if (this.abh.type == 0) {
            newList = aQ(false);
        }
        if (this.abh.type == 1) {
            newList = aP(false);
        }
        if (this.abh.type == 2) {
            return aR(false);
        }
        return newList;
    }

    public String uk() {
        String tmp = "";
        switch (this.abh.type) {
            case 0:
                tmp = "空心";
                break;
            case 1:
                tmp = "实心";
                break;
            case 2:
                tmp = "边线";
                break;
        }
        return String.format("建造立方体 %s X长:%s Z长:%s 高:%s", new Object[]{tmp, Integer.valueOf(this.abh.x), Integer.valueOf(this.abh.z), Integer.valueOf(this.abh.y)});
    }

    private void ul() {
        int selfX = (int) h.zF();
        int selfY = (int) h.zG();
        int selfZ = (int) h.zH();
        int startX = (int) this.aaW.zW();
        int startY = (int) this.aaW.zX();
        int startZ = (int) this.aaW.zY();
        int endX = startX + this.abh.x;
        int endY = startY + this.abh.y;
        int endZ = startZ + this.abh.z;
        if (selfX >= startX && selfX <= endX && selfY >= startY && selfY <= endY && selfZ >= startZ && selfZ <= endZ) {
            h.b((float) selfX, (float) (endY + 2), (float) selfZ);
        }
    }

    private ArrayList<j> aP(boolean bEmptyId) {
        ArrayList<j> inList = new ArrayList();
        for (int nx = 0; nx < this.abh.x; nx++) {
            for (int ny = 0; ny < this.abh.y; ny++) {
                for (int nz = 0; nz < this.abh.z; nz++) {
                    int newX = ((int) this.aaW.zW()) + nx;
                    int newY = ((int) this.aaW.zX()) + ny;
                    int newZ = ((int) this.aaW.zY()) + nz;
                    int id = 0;
                    int dmg = 0;
                    if (!bEmptyId) {
                        id = this.aaW.getId();
                        dmg = this.aaW.ql();
                    }
                    inList.add(new j(id, dmg, newX, newY, newZ));
                }
            }
        }
        return inList;
    }

    private ArrayList<j> aQ(boolean bEmptyId) {
        ArrayList<j> inList = new ArrayList();
        int nx = 0;
        while (nx < this.abh.x) {
            int ny = 0;
            while (ny < this.abh.y) {
                int nz = 0;
                while (nz < this.abh.z) {
                    boolean isAddItem = true;
                    if (!(ny == 0 || ny + 1 == this.abh.y || nx == 0 || nx + 1 == this.abh.x || nz == 0 || nz + 1 == this.abh.z)) {
                        isAddItem = false;
                    }
                    if (isAddItem) {
                        int newX = ((int) this.aaW.zW()) + nx;
                        int newY = ((int) this.aaW.zX()) + ny;
                        int newZ = ((int) this.aaW.zY()) + nz;
                        int id = 0;
                        int dmg = 0;
                        if (!bEmptyId) {
                            id = this.aaW.getId();
                            dmg = this.aaW.ql();
                        }
                        inList.add(new j(id, dmg, newX, newY, newZ));
                    }
                    nz++;
                }
                ny++;
            }
            nx++;
        }
        return inList;
    }

    private ArrayList<j> aR(boolean bEmptyId) {
        ArrayList<j> inList = new ArrayList();
        int nx = 0;
        while (nx < this.abh.x) {
            int ny = 0;
            while (ny < this.abh.y) {
                int nz = 0;
                while (nz < this.abh.z) {
                    boolean isAddItem = true;
                    if (!(ny == 0 || ny + 1 == this.abh.y || ((nx == 0 && nz == 0) || ((nx == 0 && nz + 1 == this.abh.z) || ((nz == 0 && nx + 1 == this.abh.x) || (nx + 1 == this.abh.x && nz + 1 == this.abh.z)))))) {
                        isAddItem = false;
                    }
                    if (isAddItem) {
                        int newX = ((int) this.aaW.zW()) + nx;
                        int newY = ((int) this.aaW.zX()) + ny;
                        int newZ = ((int) this.aaW.zY()) + nz;
                        int id = 0;
                        int dmg = 0;
                        if (!bEmptyId) {
                            id = this.aaW.getId();
                            dmg = this.aaW.ql();
                        }
                        inList.add(new j(id, dmg, newX, newY, newZ));
                    }
                    nz++;
                }
                ny++;
            }
            nx++;
        }
        return inList;
    }
}
