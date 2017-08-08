package hlx.ui.recommendapp;

import com.MCWorld.controller.resource.bean.ResTaskInfo;

class RecommendAppAdapter$1 implements Runnable {
    final /* synthetic */ ResTaskInfo aTr;
    final /* synthetic */ RecommendAppAdapter$b cfa;
    final /* synthetic */ RecommendAppAdapter cfb;

    RecommendAppAdapter$1(RecommendAppAdapter this$0, RecommendAppAdapter$b recommendAppAdapter$b, ResTaskInfo resTaskInfo) {
        this.cfb = this$0;
        this.cfa = recommendAppAdapter$b;
        this.aTr = resTaskInfo;
    }

    public void run() {
        this.cfa.cfj.setMaxProgress((int) this.aTr.mN.total);
        this.cfa.cfj.setProgressNotInUiThread((int) this.aTr.mN.progress);
    }
}
