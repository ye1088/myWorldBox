package hlx.ui.redpacket;

/* compiled from: DTRedpacketControl */
public class a {
    private static a cfy;
    private boolean cfA = false;
    private boolean cfz = false;

    public static synchronized a VJ() {
        a aVar;
        synchronized (a.class) {
            if (cfy == null) {
                cfy = new a();
            }
            aVar = cfy;
        }
        return aVar;
    }

    public void g(boolean inputWXFlag, boolean inputQQFlag) {
        dO(inputWXFlag);
        dO(inputQQFlag);
    }

    public boolean VK() {
        return this.cfz;
    }

    public void dO(boolean mOpenWXRedpacketFlag) {
        this.cfz = mOpenWXRedpacketFlag;
    }

    public boolean VL() {
        return this.cfA;
    }

    public void dP(boolean mOpenQQRedpacketFlag) {
        this.cfA = mOpenQQRedpacketFlag;
    }
}
