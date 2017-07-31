package com.huluxia.mcfloat;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.R;

/* compiled from: FloatManager */
class q$2 implements OnClickListener {
    final /* synthetic */ q VV;

    q$2(q this$0) {
        this.VV = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.logo_entry) {
            q.d(this.VV).sendMessage(q.d(this.VV).obtainMessage(256, 17, 17));
        } else if (id == R.id.float_mainview) {
            q.d(this.VV).sendMessage(q.d(this.VV).obtainMessage(256, 18, 18));
        }
    }
}
