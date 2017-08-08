package com.MCWorld.widget;

import android.content.Context;
import com.MCWorld.utils.j;
import java.util.Vector;

/* compiled from: Patcher */
public class e {
    public static final int TYPE_FILE = 1;
    public static final int btQ = 0;
    private static e btT = null;
    private Thread btR = null;
    private Vector<a> btS = null;
    private Runnable btU = new Runnable(this) {
        final /* synthetic */ e btV;

        {
            this.btV = this$0;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                this.btV.NU();
            } catch (InterruptedException e) {
            }
        }
    };
    private Context mContext = null;

    /* compiled from: Patcher */
    public static class a {
        String btW;
        String btX;
        String btY;
        int type;

        a(String parataskId, String parasrcPath, String paradestPath, int paratype) {
            this.btW = parataskId;
            this.btX = parasrcPath;
            this.btY = paradestPath;
            this.type = paratype;
        }
    }

    public static synchronized e NT() {
        e eVar;
        synchronized (e.class) {
            if (btT == null) {
                btT = new e();
            }
            eVar = btT;
        }
        return eVar;
    }

    public void bB(Context context) {
        this.mContext = context;
        this.btS = new Vector(20);
        this.btR = new Thread(this.btU);
        this.btR.start();
    }

    private void NU() {
        while (true) {
            synchronized (this.btS) {
                int nLastIdx = this.btS.size();
                if (nLastIdx == 0) {
                    try {
                        this.btS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (nLastIdx == 0) {
                } else {
                    a info = (a) this.btS.get(nLastIdx - 1);
                    this.btS.remove(nLastIdx - 1);
                    if (info != null) {
                        if (info.type == 0) {
                            j.ag(info.btX, info.btY);
                        } else {
                            j.af(info.btX, info.btY);
                        }
                    }
                }
            }
        }
    }

    public void b(String taskId, String srcPath, String destPath, int type) {
        a info = new a(taskId, srcPath, destPath, type);
        synchronized (this.btS) {
            this.btS.add(info);
            this.btS.notify();
        }
    }
}
