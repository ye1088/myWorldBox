package hlx.mcstorymode.storyselect;

import com.MCWorld.utils.UtilsDownloadFile.a;

class StoryPageFragment$a implements a {
    final /* synthetic */ StoryPageFragment bXx;

    private StoryPageFragment$a(StoryPageFragment storyPageFragment) {
        this.bXx = storyPageFragment;
    }

    public void au(int cur, int max) {
    }

    public void h(int state, String path) {
        if (state == 3 && StoryPageFragment.i(this.bXx) != null) {
            StoryPageFragment.i(this.bXx).TP();
        }
    }
}
