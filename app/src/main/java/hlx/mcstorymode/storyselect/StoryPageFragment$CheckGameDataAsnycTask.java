package hlx.mcstorymode.storyselect;

import android.os.AsyncTask;
import hlx.mcstorymode.e;

public class StoryPageFragment$CheckGameDataAsnycTask extends AsyncTask<String, Integer, String> {
    final /* synthetic */ StoryPageFragment bXx;

    public StoryPageFragment$CheckGameDataAsnycTask(StoryPageFragment this$0) {
        this.bXx = this$0;
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return c((String[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        ce((String) obj);
    }

    protected void onPreExecute() {
        StoryPageFragment.j(this.bXx).setVisibility(0);
    }

    protected String c(String... params) {
        String retVal = "success";
        if (StoryPageFragment.e(this.bXx) != null) {
            for (a _tmp : StoryPageFragment.e(this.bXx)) {
                if (e.nf(_tmp.bXg)) {
                    _tmp.bXi = true;
                } else if (e.mZ(_tmp.bXg)) {
                    _tmp.bXi = true;
                } else {
                    _tmp.bXi = false;
                }
            }
        }
        return retVal;
    }

    protected void ce(String result) {
        StoryPageFragment.j(this.bXx).setVisibility(0);
        StoryPageFragment.c(this.bXx).notifyDataSetChanged();
    }
}
