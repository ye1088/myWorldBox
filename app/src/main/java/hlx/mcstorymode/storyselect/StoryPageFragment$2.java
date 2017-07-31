package hlx.mcstorymode.storyselect;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.R;
import com.huluxia.k;
import hlx.data.localstore.a;

class StoryPageFragment$2 implements OnClickListener {
    final /* synthetic */ StoryPageFragment bXx;

    StoryPageFragment$2(StoryPageFragment this$0) {
        this.bXx = this$0;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivStoryPageBack:
                if (StoryPageFragment.a(this.bXx) != null && !StoryPageFragment.a(this.bXx).isFinishing()) {
                    StoryPageFragment.a(this.bXx).finish();
                    return;
                }
                return;
            case R.id.ivStoryResDel:
                StoryPageFragment.a(this.bXx, (StoryPageFragment.b(this.bXx) + 1) % 2);
                StoryPageFragment.c(this.bXx).ni(StoryPageFragment.b(this.bXx));
                if (StoryPageFragment.b(this.bXx) == 0) {
                    StoryPageFragment.d(this.bXx).setBackgroundResource(R.mipmap.ico_story_page_trash);
                    return;
                } else if (StoryPageFragment.b(this.bXx) == 1) {
                    StoryPageFragment.d(this.bXx).setBackgroundResource(R.mipmap.ico_story_page_del_confirm);
                    return;
                } else {
                    return;
                }
            case R.id.ivStoryContribute:
                k.a(StoryPageFragment.a(this.bXx), a.bLi, false);
                return;
            case R.id.ivStoryFeedback:
                k.a(StoryPageFragment.a(this.bXx), a.bLh, false);
                return;
            default:
                return;
        }
    }
}
