package hlx.ui.mapseed;

import android.view.View;
import android.view.View.OnClickListener;
import hlx.gameoperator.b;
import hlx.launch.game.d;
import hlx.ui.mapseed.b.a;

class SeedAdapter$1 implements OnClickListener {
    final /* synthetic */ a cbI;
    final /* synthetic */ SeedAdapter cbJ;

    SeedAdapter$1(SeedAdapter this$0, a aVar) {
        this.cbJ = this$0;
        this.cbI = aVar;
    }

    public void onClick(View v) {
        b.RI().a(SeedAdapter.a(this.cbJ), (long) this.cbI.id, this.cbI.name, this.cbI.seedValue, this.cbI.SpawnX, this.cbI.SpawnY, this.cbI.SpawnZ);
        d.bR(SeedAdapter.a(this.cbJ));
    }
}
