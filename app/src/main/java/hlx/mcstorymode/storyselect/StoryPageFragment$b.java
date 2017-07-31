package hlx.mcstorymode.storyselect;

import com.huluxia.utils.UtilsDownloadFile;
import hlx.mcstorymode.e;
import hlx.utils.c.a;

class StoryPageFragment$b implements a {
    final /* synthetic */ StoryPageFragment bXx;

    private StoryPageFragment$b(StoryPageFragment storyPageFragment) {
        this.bXx = storyPageFragment;
    }

    public void ra() {
    }

    public void rb() {
    }

    public void sM() {
    }

    public void rd() {
        String url = e.nb(StoryPageFragment.h(this.bXx));
        new UtilsDownloadFile(StoryPageFragment.a(this.bXx), e.mY(StoryPageFragment.h(this.bXx)), true, new StoryPageFragment$a(this.bXx)).execute(new String[]{url});
    }
}
