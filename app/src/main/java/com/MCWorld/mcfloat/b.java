package com.MCWorld.mcfloat;

import com.MCWorld.mcinterface.c;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.utils.ah;
import com.MCWorld.utils.aj;
import com.MCWorld.utils.aw;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FloatGameItemManager */
public class b {
    private static List<c> Pe = new ArrayList(36);
    private static List<ItemStack> Pf = new ArrayList(36);
    private static List<ItemStack> Pg = new ArrayList(36);
    private static b Ph = null;
    private static List<ItemStack> Pl = new ArrayList();
    private static List<ItemStack> Pm = new ArrayList();
    private static int[] Po = null;
    private static List<c> Pp = new ArrayList();
    private String Pi = "";
    private String Pj = "";
    private String Pk = "gagsavelist";
    private boolean Pn = false;

    public static synchronized b qy() {
        b bVar;
        synchronized (b.class) {
            if (Ph == null) {
                Ph = new b();
            }
            bVar = Ph;
        }
        return bVar;
    }

    public b() {
        ItemStack emptyItem = new ItemStack(0, 0, false, "", 0);
        for (int n = 0; n < 36; n++) {
            Pg.add(emptyItem);
            Pe.add(new c());
            Pf.add(new ItemStack(0, 0, false, "", 0));
        }
    }

    public void cb(String name) {
        if (name != null && name.length() != 0) {
            this.Pi = String.format("baglist-%d", new Object[]{Integer.valueOf(name.hashCode())});
            this.Pj = String.format("armorlist-%d", new Object[]{Integer.valueOf(name.hashCode())});
            qK();
            qM();
            qC();
        }
    }

    public List<ItemStack> qz() {
        return Pl;
    }

    public void qA() {
        if (Pm.size() > 0) {
            Pm.clear();
        }
    }

    public boolean v(int id, int dmg) {
        for (ItemStack item : Pm) {
            if (item.getItemId() == id && item.getItemDmg() == dmg) {
                return false;
            }
        }
        Pm.add(aj.aC(id, dmg));
        return true;
    }

    public void qB() {
        for (ItemStack _delItem : Pm) {
            if (_delItem != null && _delItem.getItemId() > 0) {
                x(_delItem.getItemId(), _delItem.getItemDmg());
            }
        }
    }

    public boolean w(int id, int dmg) {
        for (ItemStack item : Pl) {
            if (item.getItemId() == id && item.getItemDmg() == dmg) {
                return false;
            }
        }
        Pl.add(aj.aC(id, dmg));
        qD();
        return true;
    }

    public void x(int id, int dmg) {
        for (ItemStack item : Pl) {
            if (item.getItemId() == id && item.getItemDmg() == dmg) {
                Pl.remove(item);
                qD();
                return;
            }
        }
    }

    private void qC() {
        if (Pl.size() <= 0) {
            String result = ah.KZ().aj(this.Pk, "");
            if (result.length() != 0) {
                int[] array = aw.au(result, "#");
                for (int n = 0; n < array.length; n++) {
                    Pl.add(aj.aC(array[n] / 100, array[n] % 100));
                }
            }
        }
    }

    private void qD() {
        String result = "";
        for (ItemStack item : Pl) {
            result = (result + Integer.toString((item.getItemId() * 100) + item.getItemDmg())) + "#";
        }
        if (result.length() != 0) {
            ah.KZ().ak(this.Pk, result);
        }
    }

    public List<ItemStack> qE() {
        synchronized (Pe) {
            if (h.A(Pe)) {
                for (int n = 0; n < 36; n++) {
                    ((ItemStack) Pf.get(n)).clearItemInfo();
                    c temp = (c) Pe.get(n);
                    if (temp.getId() != 0) {
                        ItemStack oldItem = (ItemStack) Pf.get(n);
                        oldItem.setItemInfo(aj.aC(temp.getId(), temp.ql()));
                        oldItem.setItemCount(temp.getCount());
                        oldItem.setItemBagIdx(temp.zk());
                        oldItem.setmInGameBagIndex(temp.zl());
                    }
                }
                return Pf;
            }
            List<ItemStack> list = Pg;
            return list;
        }
    }

    public void ax(boolean is) {
        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            h.bU(is);
            this.Pn = false;
            return;
        }
        this.Pn = is;
    }

    public void qF() {
        if (this.Pn) {
            qJ();
            qL();
        }
    }

    public void qG() {
        if (h.zx() != 3 && h.zx() != 5 && h.zx() != 7) {
            Po = null;
            if (this.Pn) {
                ay(false);
                Po = h.va();
                h.vj();
            }
        }
    }

    public void qH() {
        if (this.Pn) {
            qI();
            h.c(Po);
        }
    }

    public void qI() {
        if (!Pp.isEmpty() && Pp.size() <= 36) {
            if (h.zx() == 2) {
                int[] generalsAttrs = new int[25];
                int[] generalsTypesChoose = new int[25];
                for (int i = 0; i < Pp.size(); i++) {
                    int tmpId = ((c) Pp.get(i)).getId();
                    int tmpDmg = ((c) Pp.get(i)).ql();
                    int tmpCount = ((c) Pp.get(i)).getCount();
                }
            }
            h.F(Pp);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ay(boolean r7) {
        /*
        r6 = this;
        r3 = Pe;
        monitor-enter(r3);
        r2 = Pp;	 Catch:{ all -> 0x0037 }
        r2.clear();	 Catch:{ all -> 0x0037 }
        r0 = 0;
        r2 = 1;
        if (r7 != r2) goto L_0x0016;
    L_0x000c:
        r2 = Pe;	 Catch:{ all -> 0x0037 }
        r0 = com.huluxia.mcinterface.h.A(r2);	 Catch:{ all -> 0x0037 }
    L_0x0012:
        if (r0 != 0) goto L_0x001d;
    L_0x0014:
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
    L_0x0015:
        return;
    L_0x0016:
        r2 = Pe;	 Catch:{ all -> 0x0037 }
        r0 = com.huluxia.mcinterface.h.z(r2);	 Catch:{ all -> 0x0037 }
        goto L_0x0012;
    L_0x001d:
        r2 = Pe;	 Catch:{ all -> 0x0037 }
        r2 = r2.iterator();	 Catch:{ all -> 0x0037 }
    L_0x0023:
        r4 = r2.hasNext();	 Catch:{ all -> 0x0037 }
        if (r4 == 0) goto L_0x0035;
    L_0x0029:
        r1 = r2.next();	 Catch:{ all -> 0x0037 }
        r1 = (com.huluxia.mcinterface.c) r1;	 Catch:{ all -> 0x0037 }
        r4 = r1.getId();	 Catch:{ all -> 0x0037 }
        if (r4 != 0) goto L_0x003a;
    L_0x0035:
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
        goto L_0x0015;
    L_0x0037:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0037 }
        throw r2;
    L_0x003a:
        r4 = Pp;	 Catch:{ all -> 0x0037 }
        r5 = new com.huluxia.mcinterface.c;	 Catch:{ all -> 0x0037 }
        r5.<init>(r1);	 Catch:{ all -> 0x0037 }
        r4.add(r5);	 Catch:{ all -> 0x0037 }
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.mcfloat.b.ay(boolean):void");
    }

    public void qJ() {
        if (this.Pi.length() != 0) {
            String result = "";
            for (c cache : Pp) {
                if (cache.getId() == 0) {
                    break;
                }
                result = result + String.format("%d_%d_%d#", new Object[]{Integer.valueOf(cache.getId()), Integer.valueOf(cache.getCount()), Integer.valueOf(cache.ql())});
            }
            ah.KZ().ak(this.Pi, result);
        }
    }

    private void qK() {
        if (this.Pi.length() != 0) {
            String[] array = ah.KZ().aj(this.Pi, "").split("#");
            if (array != null && array.length != 0) {
                synchronized (Pe) {
                    Pp.clear();
                    for (String text : array) {
                        int[] values = aw.au(text, "_");
                        if (values.length == 3) {
                            Pp.add(new c(0, values[0], values[1], values[2], 0));
                        }
                    }
                }
            }
        }
    }

    private void qL() {
        if (this.Pj.length() != 0 && Po != null && Po.length != 0) {
            String result = "";
            for (int num : Po) {
                result = (result + Integer.toString(num)) + "#";
            }
            if (result.length() != 0) {
                ah.KZ().ak(this.Pj, result);
            }
        }
    }

    private void qM() {
        Po = null;
        if (this.Pj.length() != 0) {
            String result = ah.KZ().aj(this.Pj, "");
            if (result.length() != 0) {
                int[] array = aw.au(result, "#");
                if (array != null && array.length != 0 && array.length <= 4) {
                    Po = array;
                }
            }
        }
    }
}
