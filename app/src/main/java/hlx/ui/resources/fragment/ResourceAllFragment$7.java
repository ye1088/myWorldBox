package hlx.ui.resources.fragment;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.r;
import hlx.data.tongji.a;

class ResourceAllFragment$7 implements OnCheckedChangeListener {
    final /* synthetic */ ResourceAllFragment cgj;

    ResourceAllFragment$7(ResourceAllFragment this$0) {
        this.cgj = this$0;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        HLog.info("ResourceAllFragment", checkedId + "", new Object[0]);
        ResourceAllFragment.a(this.cgj, checkedId);
        if (checkedId == R.id.all_version) {
            ResourceAllFragment.a(this.cgj, ResourceAllFragment.m(this.cgj));
        } else {
            ResourceAllFragment.a(this.cgj, ResourceAllFragment.n(this.cgj)[checkedId]);
        }
        String _tmpStrTongji = null;
        switch (ResourceAllFragment.i(this.cgj)) {
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
            ResourceAllFragment.a(this.cgj, ResourceAllFragment.m(this.cgj));
            if (_tmpStrTongji != null) {
                r.ck().j(_tmpStrTongji, ResourceAllFragment.m(this.cgj));
            }
        } else if (_tmpStrTongji != null) {
            r.ck().j(_tmpStrTongji, ResourceAllFragment.n(this.cgj)[checkedId]);
        }
    }
}
