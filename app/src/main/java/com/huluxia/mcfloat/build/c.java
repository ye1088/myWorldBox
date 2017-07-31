package com.huluxia.mcfloat.build;

import android.content.Context;
import com.huluxia.mcfloat.dialog.b.a;
import com.huluxia.mcfloat.dialog.b.b;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.r;
import com.huluxia.utils.ah;
import java.util.ArrayList;

/* compiled from: BuildLine */
public class c extends a {
    private static final String aba = "Float_BuildLineCnt";
    private static final String abb = "Float_BuildLineStep";
    private static final String abc = "Float_BuildLineOrientation";
    private e Qp;
    private j aaW = null;
    private b abd;
    private a abe = new a(this) {
        final /* synthetic */ c abg;

        {
            this.abg = this$0;
        }

        public void a(b info) {
            ah.KZ().Q(c.aba, info.Ov);
            ah.KZ().Q(c.abb, info.abG);
            ah.KZ().Q(c.abc, info.orientation);
            this.abg.abd.b(info);
            this.abg.Qp.cm(this.abg.uk());
            r.ck().K("float_build_addline");
        }
    };
    private boolean abf = false;
    private int mOrientation = -1;

    public c(e buildStatus) {
        this.Qp = buildStatus;
        this.abd = new b();
        this.abd.n(ah.KZ().P(aba, 5), ah.KZ().P(abb, 0), ah.KZ().P(abc, 0));
    }

    public void aO(Context context) {
        new com.huluxia.mcfloat.dialog.b(context).a(this.abd, this.abe);
    }

    public void um() {
        this.aaW = null;
        this.mOrientation = -1;
    }

    public void d(int id, int dmg, int x, int y, int z) {
        aS(true);
        if (this.abd.orientation != 0) {
            this.aaW = new j(id, dmg, x, y, z);
            this.mOrientation = this.abd.orientation;
        } else if (this.aaW == null) {
            this.aaW = new j(id, dmg, x, y, z);
        } else if (f(id, dmg, x, y, z)) {
            this.mOrientation = l(x, y, z);
        } else {
            this.aaW = new j(id, dmg, x, y, z);
        }
    }

    public void e(int id, int dmg, int x, int y, int z) {
        aS(false);
        if (this.abd.orientation != 0) {
            this.aaW = new j(id, dmg, x, y, z);
            this.mOrientation = this.abd.orientation;
        } else if (this.aaW == null) {
            this.aaW = new j(id, dmg, x, y, z);
        } else if (f(0, 0, x, y, z)) {
            this.mOrientation = l(x, y, z);
        } else {
            this.aaW = new j(id, dmg, x, y, z);
        }
    }

    public ArrayList<j> ui() {
        if (this.abd.Ov == 0 || this.mOrientation == -1) {
            return null;
        }
        ArrayList<j> oldList = aT(true);
        if (!h.B(oldList)) {
            return null;
        }
        ((j) oldList.get(0)).setId(this.abf ? 0 : this.aaW.getId());
        return oldList;
    }

    public ArrayList<j> uj() {
        if (this.abd.Ov == 0 || this.mOrientation == -1) {
            return null;
        }
        return aT(!this.abf);
    }

    public String uk() {
        String tmp = "";
        switch (this.abd.orientation) {
            case 0:
                tmp = "自动";
                break;
            case 1:
                tmp = "X增加";
                break;
            case 2:
                tmp = "X减小";
                break;
            case 3:
                tmp = "Y增加";
                break;
            case 4:
                tmp = "Y减小";
                break;
            case 5:
                tmp = "Z增加";
                break;
            case 6:
                tmp = "Z减小";
                break;
        }
        return String.format("直线放置/破坏 %s 数量:%s 间隔:%s", new Object[]{tmp, Integer.valueOf(this.abd.Ov), Integer.valueOf(this.abd.abG)});
    }

    private void aS(boolean isAddItem) {
        if (this.abf != isAddItem) {
            this.abf = isAddItem;
            this.aaW = null;
            this.mOrientation = -1;
        }
    }

    private boolean f(int id, int dmg, int x, int y, int z) {
        boolean z2 = true;
        if (id != 0 && id != this.aaW.getId()) {
            return false;
        }
        if (dmg != 0 && dmg != this.aaW.ql()) {
            return false;
        }
        if ((((int) Math.abs(this.aaW.zW() - ((float) x))) + ((int) Math.abs(this.aaW.zX() - ((float) y)))) + ((int) Math.abs(this.aaW.zY() - ((float) z))) != 1) {
            z2 = false;
        }
        return z2;
    }

    private int l(int x, int y, int z) {
        if (x > ((int) this.aaW.zW())) {
            return 1;
        }
        if (x < ((int) this.aaW.zW())) {
            return 2;
        }
        if (y > ((int) this.aaW.zX())) {
            return 3;
        }
        if (y < ((int) this.aaW.zX())) {
            return 4;
        }
        if (z > ((int) this.aaW.zY())) {
            return 5;
        }
        if (z < ((int) this.aaW.zY())) {
            return 6;
        }
        return 0;
    }

    public ArrayList<j> aT(boolean bEmptyId) {
        ArrayList<j> inList = new ArrayList();
        for (int n = 0; n < this.abd.Ov; n++) {
            int newX = (int) this.aaW.zW();
            int newZ = (int) this.aaW.zY();
            int newY = (int) this.aaW.zX();
            switch (this.mOrientation) {
                case 1:
                    newX += (this.abd.abG * n) + n;
                    break;
                case 2:
                    newX -= (this.abd.abG * n) + n;
                    break;
                case 3:
                    newY += (this.abd.abG * n) + n;
                    break;
                case 4:
                    newY -= (this.abd.abG * n) + n;
                    break;
                case 5:
                    newZ += (this.abd.abG * n) + n;
                    break;
                case 6:
                    newZ -= (this.abd.abG * n) + n;
                    break;
            }
            int id = 0;
            int dmg = 0;
            if (!bEmptyId) {
                id = this.aaW.getId();
                dmg = this.aaW.ql();
            }
            inList.add(new j(id, dmg, newX, newY, newZ));
        }
        return inList;
    }
}
