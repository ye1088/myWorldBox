package hlx.ui.personalstudio;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;

class PersonalStudioHeaderLayout$1 implements OnClickListener {
    final /* synthetic */ PersonalStudioHeaderLayout cdR;

    PersonalStudioHeaderLayout$1(PersonalStudioHeaderLayout this$0) {
        this.cdR = this$0;
    }

    public void onClick(View v) {
        if (PersonalStudioHeaderLayout.a(this.cdR) != null && PersonalStudioHeaderLayout.a(this.cdR).studioInfo != null) {
            k.d(PersonalStudioHeaderLayout.b(this.cdR), PersonalStudioHeaderLayout.a(this.cdR).studioInfo.id);
        }
    }
}
