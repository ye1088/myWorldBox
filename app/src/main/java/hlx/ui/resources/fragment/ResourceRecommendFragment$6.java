package hlx.ui.resources.fragment;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.r;
import hlx.data.tongji.a;

class ResourceRecommendFragment$6 implements OnCheckedChangeListener {
    final /* synthetic */ ResourceRecommendFragment cgl;

    ResourceRecommendFragment$6(ResourceRecommendFragment this$0) {
        this.cgl = this$0;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        HLog.info("ResourceRecommendFragment", checkedId + "", new Object[0]);
        ResourceRecommendFragment.a(this.cgl, checkedId);
        if (checkedId == R.id.all_version) {
            ResourceRecommendFragment.a(this.cgl, ResourceRecommendFragment.h(this.cgl));
        } else {
            ResourceRecommendFragment.a(this.cgl, ResourceRecommendFragment.i(this.cgl)[checkedId]);
        }
        String _tmpStrTongji = null;
        switch (ResourceRecommendFragment.j(this.cgl)) {
            case 2:
                _tmpStrTongji = a.bMM;
                break;
            case 3:
                _tmpStrTongji = a.bMO;
                break;
            case 4:
                _tmpStrTongji = a.bMN;
                break;
        }
        if (checkedId == R.id.all_version) {
            ResourceRecommendFragment.a(this.cgl, ResourceRecommendFragment.h(this.cgl));
            if (_tmpStrTongji != null) {
                r.ck().j(_tmpStrTongji, ResourceRecommendFragment.h(this.cgl));
            }
        } else if (_tmpStrTongji != null) {
            r.ck().j(_tmpStrTongji, ResourceRecommendFragment.i(this.cgl)[checkedId]);
        }
    }
}
