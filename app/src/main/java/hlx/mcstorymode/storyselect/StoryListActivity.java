package hlx.mcstorymode.storyselect;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.MCWorld.ui.base.HTBaseThemeActivity;

public class StoryListActivity extends HTBaseThemeActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(16908290) == null) {
            fm.beginTransaction().add(16908290, StoryPageFragment.TM()).commit();
        }
    }
}
