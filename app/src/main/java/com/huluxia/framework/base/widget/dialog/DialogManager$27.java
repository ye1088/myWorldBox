package com.huluxia.framework.base.widget.dialog;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.huluxia.framework.R;

class DialogManager$27 implements OnCheckedChangeListener {
    final /* synthetic */ DialogManager this$0;

    DialogManager$27(DialogManager this$0) {
        this.this$0 = this$0;
    }

    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        DialogManager.access$102(this.this$0, arg0.getCheckedRadioButtonId() == R.id.radio_female);
    }
}
