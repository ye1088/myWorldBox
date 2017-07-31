package com.huluxia.mcgame;

import com.huluxia.mcinterface.j;
import com.huluxia.mcsdk.dtlib.h;
import com.mojang.minecraftpe.a;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.mozilla.javascript.Token;

/* compiled from: DTBuildQueue */
public class e {
    private Token adv = new Token();
    private boolean adw = false;
    private List<j> adx = null;
    private Queue<j> ady = new LinkedList();
    private boolean mIsAutoBuild = false;

    public static void vH() {
        a.bHY.vI();
        a.bHY.vJ();
    }

    public void vI() {
        if (this.adw) {
            synchronized (this.adv) {
                B(this.adx);
                this.adw = false;
                this.adx = null;
                this.adv.notify();
            }
        }
    }

    public boolean B(List<j> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (j item : list) {
            q.a(item);
        }
        return true;
    }

    public boolean C(List<j> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        synchronized (this.adv) {
            this.adx = list;
            this.adw = true;
            try {
                this.adv.wait();
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    public void vJ() {
        if (this.mIsAutoBuild) {
            j[] array = gf(200);
            if (array != null) {
                for (j item : array) {
                    if (item.Aa() == 0) {
                        if (item.getId() == 0) {
                            q.c(item);
                        } else if (item.getId() > 0) {
                            q.b(item);
                        }
                    } else if (1 == item.Aa() && item.getId() > 0) {
                        if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                            c.a(item.zW(), item.zX(), item.zY(), item.getId(), null, item.getMaxHealth());
                        } else {
                            c.a(item.zW(), item.zX(), item.zY(), item.getId(), null);
                        }
                    }
                }
            }
        }
    }

    public void D(List<j> list) {
        synchronized (this.ady) {
            for (j item : list) {
                this.ady.offer(item);
            }
            this.mIsAutoBuild = true;
        }
    }

    public int vK() {
        int size;
        synchronized (this.ady) {
            size = this.ady.size();
        }
        return size;
    }

    public boolean ge(int inputGameType) {
        boolean z = false;
        synchronized (this.ady) {
            if (this.ady.size() == 0) {
            } else {
                for (j _tmpMCMapItem : this.ady) {
                    if (_tmpMCMapItem.getGameType() == inputGameType) {
                        z = true;
                        break;
                    }
                }
            }
        }
        return z;
    }

    private j[] gf(int maxSize) {
        j[] jVarArr;
        synchronized (this.ady) {
            if (this.ady.size() == 0) {
                jVarArr = null;
            } else {
                if (this.ady.size() < maxSize) {
                    maxSize = this.ady.size();
                }
                jVarArr = new j[maxSize];
                for (int n = 0; n < maxSize; n++) {
                    jVarArr[n] = (j) this.ady.poll();
                }
                this.mIsAutoBuild = this.ady.size() > 0;
            }
        }
        return jVarArr;
    }
}
