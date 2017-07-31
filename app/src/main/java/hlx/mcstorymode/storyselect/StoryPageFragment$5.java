package hlx.mcstorymode.storyselect;

import hlx.mcstorymode.storyselect.StorySelectAdapter.a;

class StoryPageFragment$5 implements a {
    final /* synthetic */ StoryPageFragment bXx;

    StoryPageFragment$5(StoryPageFragment this$0) {
        this.bXx = this$0;
    }

    public void TP() {
        new StoryPageFragment$CheckGameDataAsnycTask(this.bXx).execute(new String[0]);
    }
}
