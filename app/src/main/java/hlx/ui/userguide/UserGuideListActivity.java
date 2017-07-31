package hlx.ui.userguide;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.BaseActivity;
import hlx.data.tongji.a;

public class UserGuideListActivity extends BaseActivity {
    private View cgD;
    private View cgE;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ UserGuideListActivity cgF;

        {
            this.cgF = this$0;
        }

        public void onClick(View v) {
            if (v.getId() == g.tv_app_guide) {
                t.h(this.cgF, 0);
                r.ck().K(a.bOy);
                return;
            }
            t.h(this.cgF, 1);
            r.ck().K(a.bOz);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_user_guide_list);
        this.cgD = findViewById(g.tv_app_guide);
        this.cgE = findViewById(g.tv_mc_guide);
        this.cgD.setOnClickListener(this.mClickListener);
        this.cgE.setOnClickListener(this.mClickListener);
    }
}
