package hlx.launch.game;

import hlx.launch.game.MCPreLauncherActivity.a;

class MCPreLauncherActivity$a$1 implements Runnable {
    final /* synthetic */ MCPreLauncherActivity bRn;
    final /* synthetic */ a bRo;

    MCPreLauncherActivity$a$1(a this$0, MCPreLauncherActivity mCPreLauncherActivity) {
        this.bRo = this$0;
        this.bRn = mCPreLauncherActivity;
    }

    public void run() {
        this.bRn.Sv().setVisibility(0);
    }
}
