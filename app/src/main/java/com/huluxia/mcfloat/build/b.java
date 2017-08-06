package com.huluxia.mcfloat.build;

import android.content.Context;
import com.huluxia.mcfloat.dialog.a.a;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.r;
import com.huluxia.utils.ah;
import java.util.ArrayList;

/* compiled from: BuildEllipse */
public class b extends a {
    private static final String aaS = "Float_BuildRectAddX";
    private static final String aaT = "Float_BuildRectAddY";
    private static final String aaU = "Float_BuildRectAddZ";
    private static final String aaV = "Float_BuildRectAddType";
    private e Qp;
    private j aaW = null;
    private com.huluxia.mcfloat.dialog.a.b aaX = null;
    private a aaY = new a(this) {
        final /* synthetic */ b aaZ;

        {
            this.aaZ = this$0;
        }

        public void a(com.huluxia.mcfloat.dialog.a.b info) {
            ah.KZ().Q(b.aaS, info.x);
            ah.KZ().Q(b.aaT, info.y);
            ah.KZ().Q(b.aaU, info.z);
            ah.KZ().Q(b.aaV, info.type);
            this.aaZ.aaX.b(info);
            this.aaZ.Qp.cm(this.aaZ.uk());
            r.ck().K_umengEvent("float_build_addellipse");
        }
    };

    public b(e buildStatus) {
        this.Qp = buildStatus;
        this.aaX = new com.huluxia.mcfloat.dialog.a.b();
        int rectX = ah.KZ().get_config_sp_intVal(aaS, 5);
        int rectY = ah.KZ().get_config_sp_intVal(aaT, 5);
        int rectZ = ah.KZ().get_config_sp_intVal(aaU, 5);
        this.aaX.d(ah.KZ().get_config_sp_intVal(aaV, 0), rectX, rectY, rectZ);
    }

    public void aO(Context context) {
        new com.huluxia.mcfloat.dialog.a(context).a(this.aaX, this.aaY);
    }

    public void d(int id, int dmg, int x, int y, int z) {
        this.aaW = new j(id, dmg, x, y, z);
        ul();
    }

    public void e(int id, int dmg, int x, int y, int z) {
    }

    public ArrayList<j> ui() {
        ArrayList<j> oldList = null;
        if (this.aaX.type == 0) {
            oldList = aQ(true);
        }
        if (this.aaX.type == 1) {
            oldList = aP(true);
        }
        if (this.aaX.type == 2) {
            oldList = aR(true);
        }
        if (h.B(oldList)) {
            return oldList;
        }
        return null;
    }

    public ArrayList<j> uj() {
        ArrayList<j> newList = null;
        if (this.aaX.type == 0) {
            newList = aQ(false);
        }
        if (this.aaX.type == 1) {
            newList = aP(false);
        }
        if (this.aaX.type == 2) {
            return aR(false);
        }
        return newList;
    }

    public String uk() {
        String tmp = "";
        switch (this.aaX.type) {
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
        return String.format("建造椭圆柱 %s 长轴:%s 短轴:%s 高度:%s", new Object[]{tmp, Integer.valueOf(this.aaX.x), Integer.valueOf(this.aaX.z), Integer.valueOf(this.aaX.y)});
    }

    private void ul() {
        int selfX = (int) h.zF();
        int selfY = (int) h.zG();
        int selfZ = (int) h.zH();
        int startX = (int) this.aaW.zW();
        int startY = (int) this.aaW.zX();
        int startZ = (int) this.aaW.zY();
        int endX = startX + this.aaX.x;
        int endY = startY + this.aaX.y;
        int endZ = startZ + this.aaX.z;
        if (selfX >= startX && selfX <= endX && selfY >= startY && selfY <= endY && selfZ >= startZ && selfZ <= endZ) {
            h.b((float) selfX, (float) (endY + 2), (float) selfZ);
        }
    }

    private ArrayList<j> aP(boolean bEmptyId) {
        int id;
        int dmg = 0;
        ArrayList<j> inList = new ArrayList();
        if (bEmptyId) {
            id = 0;
        } else {
            id = this.aaW.getId();
        }
        if (!bEmptyId) {
            dmg = this.aaW.ql();
        }
        hlx.gameoperator.a.d(inList, (int) this.aaW.zW(), (int) this.aaW.zX(), (int) this.aaW.zY(), id, dmg, id, dmg, this.aaX.x, this.aaX.z, this.aaX.y);
        return inList;
    }

    private ArrayList<j> aQ(boolean bEmptyId) {
        int id;
        int dmg = 0;
        ArrayList<j> inList = new ArrayList();
        if (bEmptyId) {
            id = 0;
        } else {
            id = this.aaW.getId();
        }
        if (!bEmptyId) {
            dmg = this.aaW.ql();
        }
        hlx.gameoperator.a.d(inList, (int) this.aaW.zW(), (int) this.aaW.zX(), (int) this.aaW.zY(), id, dmg, this.aaX.x, this.aaX.z, this.aaX.y);
        return inList;
    }

    private ArrayList<j> aR(boolean bEmptyId) {
        int id;
        int dmg = 0;
        ArrayList<j> inList = new ArrayList();
        if (bEmptyId) {
            id = 0;
        } else {
            id = this.aaW.getId();
        }
        if (!bEmptyId) {
            dmg = this.aaW.ql();
        }
        hlx.gameoperator.a.c(inList, (int) this.aaW.zW(), (int) this.aaW.zX(), (int) this.aaW.zY(), id, dmg, this.aaX.x, this.aaX.z, this.aaX.y);
        return inList;
    }
}
