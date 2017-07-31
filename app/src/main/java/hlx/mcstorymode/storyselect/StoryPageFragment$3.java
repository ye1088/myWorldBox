package hlx.mcstorymode.storyselect;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import hlx.ui.a;

class StoryPageFragment$3 implements OnItemClickListener {
    final /* synthetic */ StoryPageFragment bXx;

    StoryPageFragment$3(StoryPageFragment this$0) {
        this.bXx = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        a item = (a) StoryPageFragment.e(this.bXx).get(position);
        if (!item.bXf) {
            switch (StoryPageFragment.b(this.bXx)) {
                case 0:
                    if (item.bXi) {
                        a.u(StoryPageFragment.a(this.bXx), item.bXg);
                        StoryPageFragment.b(this.bXx, item.bXg);
                        return;
                    }
                    StoryPageFragment.c(this.bXx, item.bXg);
                    StoryPageFragment.f(this.bXx);
                    return;
                case 1:
                    if (item.bXi) {
                        StoryPageFragment.a(this.bXx, item);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
