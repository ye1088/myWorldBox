package hlx.ui.mapseed;

import android.view.View;
import android.view.View.OnClickListener;
import hlx.ui.mapseed.b.a;

class SeedAdapter$2 implements OnClickListener {
    final /* synthetic */ a cbI;
    final /* synthetic */ SeedAdapter cbJ;

    SeedAdapter$2(SeedAdapter this$0, a aVar) {
        this.cbJ = this$0;
        this.cbI = aVar;
    }

    public void onClick(View v) {
        hlx.ui.a.startSeedTopicDetail(SeedAdapter.a(this.cbJ), (long) this.cbI.id, this.cbI.postID);
    }
}
