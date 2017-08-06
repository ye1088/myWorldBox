package hlx.home.fragment;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.huluxia.DownloadDialog;
import com.huluxia.data.banner.a.a;
import com.huluxia.k;
import com.huluxia.r;
import com.huluxia.t;

class HomeFragment$5 implements OnItemClickListener {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$5(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        a bannerItem = (a) parent.getAdapter().getItem(position);
        r.ck().K_umengEvent(hlx.data.tongji.a.bLU);
        switch (bannerItem.opentype) {
            case 1:
                t.a(HomeFragment.a(this.bQl), bannerItem.recommentid, false);
                return;
            case 2:
                if (bannerItem.recommenturl.endsWith(".apk")) {
                    DownloadDialog.bu().a(bannerItem.recommenturl, bannerItem.versionCode,
                            bannerItem.apptitle, bannerItem.md5, bannerItem.packname);
                    DownloadDialog.bu().show(((FragmentActivity) HomeFragment.a(this.bQl)).
                            getSupportFragmentManager(), null);
                    return;
                }
                t.a(HomeFragment.a(this.bQl), bannerItem.recommenturl);
                return;
            case 4:
                switch (bannerItem.mc_type) {
                    case 2:
                        k.c(HomeFragment.a(this.bQl), (long) bannerItem.openID, bannerItem.recommentid);
                        return;
                    case 3:
                        k.d(HomeFragment.a(this.bQl), (long) bannerItem.openID, bannerItem.recommentid);
                        return;
                    case 4:
                        k.e(HomeFragment.a(this.bQl), (long) bannerItem.openID, bannerItem.recommentid);
                        return;
                    default:
                        k.a(HomeFragment.a(this.bQl), (long) bannerItem.openID, bannerItem.recommentid, "default");
                        return;
                }
            default:
                return;
        }
    }
}
