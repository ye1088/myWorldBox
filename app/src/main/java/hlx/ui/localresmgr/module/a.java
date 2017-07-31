package hlx.ui.localresmgr.module;

/* compiled from: MapBackupItem */
public class a {
    private String bat;
    private String cbf;
    private String cbg;
    private int cbh;
    private boolean cbi;

    public a(String mapName, String mapImgPath, String mapLastBackupTime, int mapBackupNumber) {
        this.bat = mapName;
        this.cbf = mapImgPath;
        this.cbg = mapLastBackupTime;
        this.cbh = mapBackupNumber;
    }

    public String UU() {
        return this.cbf;
    }

    public void hG(String mapImgPath) {
        this.cbf = mapImgPath;
    }

    public String UV() {
        return this.cbg;
    }

    public void hH(String mapLastBackupTime) {
        this.cbg = mapLastBackupTime;
    }

    public int UW() {
        return this.cbh;
    }

    public void nG(int mapBackupNumber) {
        this.cbh = mapBackupNumber;
    }

    public String UX() {
        return this.bat;
    }

    public void hI(String mapName) {
        this.bat = mapName;
    }

    public boolean isDel() {
        return this.cbi;
    }

    public void dM(boolean isDel) {
        this.cbi = isDel;
    }

    public void UY() {
        this.cbi = !this.cbi;
    }
}
