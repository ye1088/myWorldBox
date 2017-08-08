package hlx.ui.recommendapp;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.framework.R;
import com.MCWorld.module.q;
import com.MCWorld.r;
import com.simple.colorful.d;
import hlx.ui.recommendapp.a.a;
import hlx.ui.recommendapp.downloadmanager.b;
import hlx.utils.g;
import java.io.File;

class RecommendAppAdapter$a implements OnClickListener {
    final /* synthetic */ RecommendAppAdapter cfb;
    private a cfc;
    private RecommendAppAdapter$b cfd;

    public RecommendAppAdapter$a(RecommendAppAdapter recommendAppAdapter, RecommendAppAdapter$b paraholder, a parainfo) {
        this.cfb = recommendAppAdapter;
        this.cfd = paraholder;
        this.cfc = parainfo;
    }

    public void onClick(View view) {
        Q(view);
    }

    private void Q(View view) {
        if (g.b(RecommendAppAdapter.a(this.cfb).getPackageManager(), this.cfc.packname, this.cfc.versionCode)) {
            ResTaskInfo rinfo = ResourceCtrl.getInstance().getTaskInfo(this.cfc.appdownurl, 1000008);
            if (rinfo == null || rinfo.state != State.SUCC.ordinal()) {
                int color;
                b.VH().a(RecommendAppAdapter.a(this.cfb), this.cfc);
                Resources resources = RecommendAppAdapter.a(this.cfb).getResources();
                TextView textView = this.cfd.cfh;
                if (d.isDayMode()) {
                    color = resources.getColor(R.color.studio_text_color);
                } else {
                    color = resources.getColor(R.color.item_resource_tab_default_night);
                }
                textView.setTextColor(color);
                this.cfd.cfh.setText("请稍候");
                view.setClickable(false);
                return;
            }
            hlx.ui.recommendapp.statisticsrecord.a.VI().a(this.cfc);
            g.hP(rinfo.dir + File.separator + rinfo.filename);
            r.ck().j(hlx.data.tongji.a.bOO, String.valueOf(this.cfc.id));
            q.aI((long) this.cfc.id);
            return;
        }
        g.hQ(this.cfc.packname);
        r.ck().j(hlx.data.tongji.a.bOQ, String.valueOf(this.cfc.id));
        q.aK((long) this.cfc.id);
    }
}
