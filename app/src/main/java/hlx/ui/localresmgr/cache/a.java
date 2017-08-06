package hlx.ui.localresmgr.cache;

import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.utils.j;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* compiled from: LocGameWoodOper */
public class a {
    private static final String TAG = "LocGameWoodOper";
    private static a bZF;
    private String bZD;
    private List<a> bZE = new ArrayList();
    private Runnable bZG = new Runnable(this) {
        final /* synthetic */ a bZH;

        {
            this.bZH = this$0;
        }

        public void run() {
            try {
                this.bZH.Ur();
            } catch (Exception e) {
            }
        }
    };
    private volatile boolean loadReocrd = false;

    /* compiled from: LocGameWoodOper */
    public static class a {
        String name;
        int order;

        /* compiled from: LocGameWoodOper */
        public static class a implements Comparator<a> {
            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((a) obj, (a) obj2);
            }

            public int a(a arg0, a arg1) {
                long ret = (long) (arg1.order - arg0.order);
                if (ret > 0) {
                    return 1;
                }
                if (ret == 0) {
                    return 0;
                }
                return -1;
            }
        }

        public a(String inName, int inOrder) {
            this.name = inName;
            this.order = inOrder;
        }
    }

    public static synchronized a Un() {
        a aVar;
        synchronized (a.class) {
            if (bZF == null) {
                bZF = new a();
            }
            aVar = bZF;
        }
        return aVar;
    }

    public void init() {
        this.bZD = j.Kr() + hlx.data.localstore.a.bKe;
        Uo();
    }

    private void Uo() {
        if (!this.loadReocrd) {
            AsyncTaskCenter.getInstance().execute(this.bZG, new RunnableCallback(this) {
                final /* synthetic */ a bZH;

                {
                    this.bZH = this$0;
                }

                public void onCallback() {
                    this.bZH.Up();
                }
            });
        }
    }

    private void Up() {
        this.loadReocrd = true;
    }

    private void Uq() {
        if (this.bZE != null) {
            synchronized (this.bZE) {
                int _tmpDataSize = this.bZE.size();
                for (int i = 0; i < _tmpDataSize; i++) {
                    if (((a) this.bZE.get(i)) != null) {
                        HLog.verbose(TAG, "DTPrint GameWood Item is [%s-%d]", new Object[]{((a) this.bZE.get(i)).name, Integer.valueOf(((a) this.bZE.get(i)).order)});
                    }
                }
            }
        }
    }

    public void hx(String woodName) {
        synchronized (this.bZE) {
            int _tmpDataSize = this.bZE.size();
            for (int i = 0; i < _tmpDataSize; i++) {
                a _tmpWoodItem = (a) this.bZE.get(i);
                if (_tmpWoodItem != null) {
                    _tmpWoodItem.order++;
                }
            }
            this.bZE.add(new a(woodName, 0));
            Collections.sort(this.bZE, new a());
        }
    }

    public void hy(final String woodName) {
        if (this.bZE != null && woodName.length() >= 1) {
            AsyncTaskCenter.getInstance().execute(new Runnable(this) {
                final /* synthetic */ a bZH;

                public void run() {
                    this.bZH.hx(woodName);
                    this.bZH.hB(this.bZH.bZD);
                }
            });
        }
    }

    private void hz(String woodName) {
        synchronized (this.bZE) {
            int i;
            int _tmpDataSize = this.bZE.size();
            int _tmpDelWoodOrder = -1;
            for (i = 0; i < _tmpDataSize; i++) {
                a _tmpWoodItem = (a) this.bZE.get(i);
                if (_tmpWoodItem != null && _tmpWoodItem.name.equals(woodName)) {
                    _tmpDelWoodOrder = _tmpWoodItem.order;
                    this.bZE.remove(_tmpWoodItem);
                    break;
                }
            }
            if (-1 == _tmpDelWoodOrder) {
                return;
            }
            _tmpDataSize = this.bZE.size();
            for (i = 0; i < _tmpDataSize; i++) {
                _tmpWoodItem = (a) this.bZE.get(i);
                if (_tmpWoodItem != null && _tmpWoodItem.order > _tmpDelWoodOrder) {
                    _tmpWoodItem.order--;
                }
            }
            Collections.sort(this.bZE, new a());
        }
    }

    public void hA(final String woodName) {
        if (this.bZE != null && woodName.length() >= 1) {
            AsyncTaskCenter.getInstance().execute(new Runnable(this) {
                final /* synthetic */ a bZH;

                public void run() {
                    this.bZH.hz(woodName);
                    this.bZH.hB(this.bZH.bZD);
                }
            });
        }
    }

    private void hB(String filePath) {
        if (this.bZE != null) {
            synchronized (this.bZE) {
                int _tmpDataSize = this.bZE.size();
                try {
                    FileWriter writer = new FileWriter(filePath);
                    BufferedWriter bw = new BufferedWriter(writer);
                    for (int i = _tmpDataSize - 1; i >= 0; i--) {
                        a _tmpWoodItem = (a) this.bZE.get(i);
                        if (_tmpWoodItem != null) {
                            bw.write(_tmpWoodItem.name);
                            bw.write(SpecilApiUtil.LINE_SEP);
                        }
                    }
                    bw.write(hlx.data.localstore.a.bKf);
                    bw.write(SpecilApiUtil.LINE_SEP);
                    bw.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Ur() {
        b.Us().Ut();
        hB(this.bZD);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void hC(java.lang.String r11) {
        /*
        r10 = this;
        r5 = 0;
        r1 = new java.io.File;
        r1.<init>(r11);
        r2 = 0;
        r3 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x006b }
        r7 = new java.io.FileReader;	 Catch:{ IOException -> 0x006b }
        r7.<init>(r1);	 Catch:{ IOException -> 0x006b }
        r3.<init>(r7);	 Catch:{ IOException -> 0x006b }
        r4 = 0;
        r8 = r10.bZE;	 Catch:{ IOException -> 0x004a, all -> 0x0064 }
        monitor-enter(r8);	 Catch:{ IOException -> 0x004a, all -> 0x0064 }
        r6 = r5;
    L_0x0016:
        r4 = r3.readLine();	 Catch:{ all -> 0x0046 }
        if (r4 == 0) goto L_0x003a;
    L_0x001c:
        r7 = r4.length();	 Catch:{ all -> 0x0046 }
        r9 = 1;
        if (r7 <= r9) goto L_0x0016;
    L_0x0023:
        r7 = "Minecraft";
        r7 = r4.equals(r7);	 Catch:{ all -> 0x0046 }
        if (r7 != 0) goto L_0x0016;
    L_0x002c:
        r7 = r10.bZE;	 Catch:{ all -> 0x0046 }
        r9 = new hlx.ui.localresmgr.cache.a_isRightVersion$a_isRightVersion;	 Catch:{ all -> 0x0046 }
        r5 = r6 + 1;
        r9.<init>(r4, r6);	 Catch:{ all -> 0x0071 }
        r7.add(r9);	 Catch:{ all -> 0x0071 }
        r6 = r5;
        goto L_0x0016;
    L_0x003a:
        monitor-exit(r8);	 Catch:{ all -> 0x0046 }
        r3.close();	 Catch:{ IOException -> 0x006d, all -> 0x0067 }
        if (r3 == 0) goto L_0x0073;
    L_0x0040:
        r3.close();	 Catch:{ IOException -> 0x0057 }
        r2 = r3;
        r5 = r6;
    L_0x0045:
        return;
    L_0x0046:
        r7 = move-exception;
        r5 = r6;
    L_0x0048:
        monitor-exit(r8);	 Catch:{ all -> 0x0071 }
        throw r7;	 Catch:{ IOException -> 0x004a, all -> 0x0064 }
    L_0x004a:
        r0 = move-exception;
        r2 = r3;
    L_0x004c:
        r0.printStackTrace();	 Catch:{ all -> 0x005b }
        if (r2 == 0) goto L_0x0045;
    L_0x0051:
        r2.close();	 Catch:{ IOException -> 0x0055 }
        goto L_0x0045;
    L_0x0055:
        r7 = move-exception;
        goto L_0x0045;
    L_0x0057:
        r7 = move-exception;
        r2 = r3;
        r5 = r6;
        goto L_0x0045;
    L_0x005b:
        r7 = move-exception;
    L_0x005c:
        if (r2 == 0) goto L_0x0061;
    L_0x005e:
        r2.close();	 Catch:{ IOException -> 0x0062 }
    L_0x0061:
        throw r7;
    L_0x0062:
        r8 = move-exception;
        goto L_0x0061;
    L_0x0064:
        r7 = move-exception;
        r2 = r3;
        goto L_0x005c;
    L_0x0067:
        r7 = move-exception;
        r2 = r3;
        r5 = r6;
        goto L_0x005c;
    L_0x006b:
        r0 = move-exception;
        goto L_0x004c;
    L_0x006d:
        r0 = move-exception;
        r2 = r3;
        r5 = r6;
        goto L_0x004c;
    L_0x0071:
        r7 = move-exception;
        goto L_0x0048;
    L_0x0073:
        r2 = r3;
        r5 = r6;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: hlx.ui.localresmgr.cache.a_isRightVersion.hC(java.lang.String):void");
    }

    public String hD(String filepath) throws Exception {
        ZipEntry ze;
        String line;
        int endIndex;
        int startIndex;
        String _retName = "";
        ZipFile zf = new ZipFile(filepath);
        ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(filepath)));
        while (true) {
            ze = zin.getNextEntry();
            if (ze != null) {
                if (!ze.isDirectory() && ze.getName().equals(hlx.data.localstore.a.bKg)) {
                    break;
                }
            } else {
                break;
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            } else if (line.contains(hlx.data.localstore.a.bKh)) {
                endIndex = line.lastIndexOf(34);
                if (endIndex > 1) {
                    startIndex = line.substring(0, endIndex).lastIndexOf("\"") + 1;
                    if (startIndex > 1) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        _retName = line.substring(startIndex, endIndex);
        br.close();
        zin.closeEntry();
        return _retName;
    }
}
