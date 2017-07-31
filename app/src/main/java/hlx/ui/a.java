package hlx.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.huluxia.studio.PastRankActivity;
import com.huluxia.studio.StudioActivity;
import com.huluxia.t;
import com.huluxia.ui.mctool.JsImportActivity;
import com.huluxia.ui.mctool.MapImportActivity;
import com.huluxia.ui.mctool.ServerListActivity;
import com.huluxia.ui.mctool.ServerListExActivity;
import com.huluxia.ui.mctool.SkinImportActivity;
import com.huluxia.ui.mctool.WoodImportActivity;
import hlx.home.main.HomeActivity;
import hlx.launch.game.MCLauncherActivity105;
import hlx.launch.game.MCLauncherActivity110;
import hlx.launch.game.MCLauncherActivity121;
import hlx.launch.game.MCLauncherActivity130;
import hlx.launch.game.MCLauncherActivity143;
import hlx.launch.game.MCPreLauncherActivity;
import hlx.launch.game.c;
import hlx.launch.ui.MCVersionSelect;
import hlx.launch.ui.MCVersionSelectGuide;
import hlx.mcstorymode.MCStoryModeActivity;
import hlx.mcstorymode.storyselect.StoryListActivity;
import hlx.ui.heroslist.HerosListActivity;
import hlx.ui.localresmgr.LocResListActivity;
import hlx.ui.localresmgr.backupmanager.MapBackupManagerActivity;
import hlx.ui.localresmgr.backupmanager.SpecifyMapBackupManagerActivity;
import hlx.ui.mapseed.MapSeedDetatilActivity;
import hlx.ui.mapseed.MapSeedDownActivity;
import hlx.ui.recommendapp.RecommendAppListActivity;
import hlx.ui.redpacket.SnatchRedActivity;
import hlx.ui.redpacket.SnatchRedDetailActivity;
import hlx.ui.resources.ResourceDownloadActivity;

/* compiled from: MCUISwitch */
public class a extends t {
    public static void bV(Context context) {
        Intent in = new Intent();
        in.setClass(context, HomeActivity.class);
        context.startActivity(in);
    }

    public static void bW(Context context) {
        Intent in = new Intent();
        in.setClass(context, MCVersionSelectGuide.class);
        context.startActivity(in);
    }

    public static void e(Context context, boolean firstLaunchApp) {
        Intent in = new Intent();
        in.putExtra("firstLaunch", firstLaunchApp);
        in.setClass(context, MCVersionSelect.class);
        context.startActivity(in);
    }

    public static void a(Context context, boolean isPast, String currentMonth, String pastMonth) {
        Intent intent = new Intent();
        intent.putExtra(StudioActivity.aEG, isPast);
        intent.putExtra(StudioActivity.aEF, currentMonth);
        intent.putExtra(StudioActivity.aEE, pastMonth);
        intent.setClass(context, StudioActivity.class);
        context.startActivity(intent);
    }

    public static void bX(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PastRankActivity.class);
        context.startActivity(intent);
    }

    public static void bY(Context context) {
        Intent intent = new Intent();
        intent.putExtra(ResourceDownloadActivity.cfV, 1);
        intent.setClass(context, ResourceDownloadActivity.class);
        context.startActivity(intent);
    }

    public static void bZ(Context context) {
        Intent intent = new Intent();
        intent.putExtra(ResourceDownloadActivity.cfV, 2);
        intent.setClass(context, ResourceDownloadActivity.class);
        context.startActivity(intent);
    }

    public static void ca(Context context) {
        Intent intent = new Intent();
        intent.putExtra(ResourceDownloadActivity.cfV, 4);
        intent.setClass(context, ResourceDownloadActivity.class);
        context.startActivity(intent);
    }

    public static void cb(Context context) {
        Intent intent = new Intent();
        intent.putExtra(ResourceDownloadActivity.cfV, 3);
        intent.setClass(context, ResourceDownloadActivity.class);
        context.startActivity(intent);
    }

    public static void cc(Context context) {
        Intent in = new Intent();
        in.setClass(context, LocResListActivity.class);
        context.startActivity(in);
    }

    public static void cd(Context context) {
        Intent in = new Intent();
        in.setClass(context, HerosListActivity.class);
        context.startActivity(in);
    }

    public static void F(Activity activity) {
        Intent in = new Intent();
        in.setClass(activity, MapImportActivity.class);
        activity.startActivityForResult(in, 0);
    }

    public static void G(Activity activity) {
        Intent in = new Intent();
        in.setClass(activity, JsImportActivity.class);
        activity.startActivityForResult(in, 1);
    }

    public static void H(Activity activity) {
        Intent in = new Intent();
        in.setClass(activity, WoodImportActivity.class);
        activity.startActivityForResult(in, 2);
    }

    public static void I(Activity activity) {
        Intent in = new Intent();
        in.setClass(activity, SkinImportActivity.class);
        activity.startActivityForResult(in, 3);
    }

    public static void J(Activity activity) {
        Intent in = new Intent();
        in.setClass(activity, MapBackupManagerActivity.class);
        activity.startActivity(in);
    }

    public static void R(Context context, String mapName) {
        Intent in = new Intent();
        in.putExtra("mapName", mapName);
        in.setClass(context, SpecifyMapBackupManagerActivity.class);
        context.startActivity(in);
    }

    public static void ce(Context context) {
        context.startActivity(new Intent(context, ServerListActivity.class));
    }

    public static void cf(Context context) {
        context.startActivity(new Intent(context, MapSeedDownActivity.class));
    }

    public static void cg(Context context) {
        context.startActivity(new Intent(context, ServerListExActivity.class));
    }

    public static void ch(Context context) {
        context.startActivity(new Intent(context, MCPreLauncherActivity.class));
    }

    public static void b(Activity context, boolean newTask) {
        Intent in = new Intent();
        in.setFlags(268435456);
        int tmpBootVersion = c.Sg().Sh();
        c.Sg().mI(tmpBootVersion);
        if (tmpBootVersion == 0) {
            in.setClass(context, MCLauncherActivity105.class);
        } else if (1 == tmpBootVersion) {
            in.setClass(context, MCLauncherActivity110.class);
        } else if (2 == tmpBootVersion) {
            in.setClass(context, MCLauncherActivity121.class);
        } else if (3 == tmpBootVersion || 4 == tmpBootVersion) {
            in.setClass(context, MCLauncherActivity130.class);
        } else if (7 == tmpBootVersion || 5 == tmpBootVersion) {
            in.setClass(context, MCLauncherActivity143.class);
        } else {
            return;
        }
        context.startActivity(in);
    }

    public static void u(Context context, int chapterIndex) {
        Intent in = new Intent();
        in.putExtra("storyIndex", chapterIndex);
        in.setClass(context, MCStoryModeActivity.class);
        context.startActivity(in);
    }

    public static void ci(Context context) {
        Intent in = new Intent();
        in.setClass(context, SnatchRedActivity.class);
        context.startActivity(in);
    }

    public static void cj(Context context) {
        Intent in = new Intent();
        in.setClass(context, SnatchRedDetailActivity.class);
        context.startActivity(in);
    }

    public static void ck(Context context) {
        Intent in = new Intent();
        in.setClass(context, StoryListActivity.class);
        context.startActivity(in);
    }

    public static void startSeedTopicDetail(Context context, long seedId, long postId) {
        Intent in = new Intent();
        in.putExtra("seedId", seedId);
        in.putExtra("postId", postId);
        in.setClass(context, MapSeedDetatilActivity.class);
        context.startActivity(in);
    }

    public static void cl(Context context) {
        Intent in = new Intent();
        in.setClass(context, RecommendAppListActivity.class);
        context.startActivity(in);
    }
}
