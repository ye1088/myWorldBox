package hlx.ui.recommendapp.downloadmanager;

/* compiled from: AppDownloadInfo */
public class a {
    public static final int cfn = 0;
    public static final int cfo = 1;
    public static final int cfp = 2;
    public static final int cfq = 3;
    public String cfr;
    public String cfs;
    public int cft;
    public String mUrl;

    public a(String url, String packName, String md5) {
        this.mUrl = url;
        this.cfr = packName;
        this.cfs = md5;
        this.cft = 0;
    }

    public a(String url, String packName, String md5, int downloadStatus) {
        this.mUrl = url;
        this.cfr = packName;
        this.cfs = md5;
        this.cft = downloadStatus;
    }
}
