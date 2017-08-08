package hlx.home.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.HTApplication;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.utils.UtilsTime;
import com.MCWorld.k;
import com.MCWorld.r;
import com.MCWorld.utils.ah;
import hlx.data.tongji.a;

class HomeFragment$7 implements OnClickListener {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$7(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeGameOptionImg:
                r.ck().K_umengEvent(a.bLV);
                k.Z(HomeFragment.a(this.bQl));
                return;
            case R.id.iv_studio_logo:
                int month = UtilsTime.getMonth(System.currentTimeMillis()) - 1;
                if (month == 0) {
                    month = 12;
                }
                String currentMonth = month + "月";
                r.ck().K_umengEvent(a.bLW);
                hlx.ui.a.a(HomeFragment.a(this.bQl), false, currentMonth, "");
                return;
            case R.id.homeMapImg:
                if (HTApplication.fF > 0) {
                    ah.KZ().setMapCount(HTApplication.fF);
                    HomeFragment.h(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bLX);
                hlx.ui.a.bY(this.bQl.getActivity());
                return;
            case R.id.homeJsImg:
                if (HTApplication.fG > 0) {
                    ah.KZ().setJsCount(HTApplication.fG);
                    HomeFragment.i(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bLY);
                hlx.ui.a.bZ(this.bQl.getActivity());
                return;
            case R.id.homeWoodImg:
                if (HTApplication.fH > 0) {
                    ah.KZ().setWoodCount(HTApplication.fH);
                    HomeFragment.j(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bLZ);
                hlx.ui.a.ca(this.bQl.getActivity());
                return;
            case R.id.homeSkinImg:
                if (HTApplication.fI > 0) {
                    ah.KZ().setSkinCount(HTApplication.fI);
                    HomeFragment.k(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bMa);
                hlx.ui.a.cb(this.bQl.getActivity());
                return;
            case R.id.homeServerImg:
                if (HTApplication.fJ > 0) {
                    ah.KZ().setServerCount(HTApplication.fJ);
                    HomeFragment.l(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bMb);
                hlx.ui.a.cg(this.bQl.getActivity());
                return;
            case R.id.homeMapSeedImg:
                if (HTApplication.fK > 0) {
                    ah.KZ().setSeedCount(HTApplication.fK);
                    HomeFragment.m(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bMc);
                hlx.ui.a.cf(HomeFragment.a(this.bQl));
                return;
            case R.id.homeRecommendImg:
                if (HTApplication.fL > 0) {
                    ah.KZ().setAdCount(HTApplication.fL);
                    HomeFragment.n(this.bQl).setVisibility(8);
                }
                r.ck().K_umengEvent(a.bOL);
                hlx.ui.a.cl(HomeFragment.a(this.bQl));
                return;
            default:
                return;
        }
    }
}
