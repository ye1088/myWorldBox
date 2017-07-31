package hlx.ui.recommendapp.statisticsrecord;

import com.huluxia.module.q;
import com.huluxia.r;
import java.util.LinkedList;
import java.util.List;

/* compiled from: RecomAppStatisticRecord */
public class a {
    private static a cfv;
    private List<hlx.ui.recommendapp.a.a> cfx = new LinkedList();

    public static synchronized a VI() {
        a aVar;
        synchronized (a.class) {
            if (cfv == null) {
                cfv = new a();
            }
            aVar = cfv;
        }
        return aVar;
    }

    public boolean b(String packName, boolean isRemovePackName, boolean isSendStatic) {
        boolean tmpFlag = false;
        if (packName == null) {
            return false;
        }
        for (int i = 0; i < this.cfx.size(); i++) {
            hlx.ui.recommendapp.a.a item = (hlx.ui.recommendapp.a.a) this.cfx.get(i);
            if (packName.equals(item.packname)) {
                if (isSendStatic) {
                    r.ck().j(hlx.data.tongji.a.bOP, String.valueOf(item.id));
                    q.aJ((long) item.id);
                }
                tmpFlag = true;
                if (isRemovePackName) {
                    this.cfx.remove(i);
                }
            }
        }
        return tmpFlag;
    }

    public void a(hlx.ui.recommendapp.a.a item) {
        this.cfx.add(item);
    }
}
