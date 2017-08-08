package hlx.ui.online;

import com.MCWorld.HTApplication;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.mconline.gameloc.http.d;
import com.MCWorld.mconline.gameloc.http.h;
import hlx.module.resources.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

class OnlineFragment$9 extends CallbackHandler {
    final /* synthetic */ OnlineFragment cde;

    OnlineFragment$9(OnlineFragment this$0) {
        this.cde = this$0;
    }

    @MessageHandler(message = 3333)
    public void onRecvResCate(boolean succ, ArrayList<a> cateList) {
        OnlineFragment.j(this.cde).onRefreshComplete();
        OnlineFragment.i(this.cde).onLoadComplete();
        if (succ && !UtilsFunction.empty((Collection) cateList)) {
            cateList.add(0, new a(-1, "全部分类", ""));
            this.cde.setCategory(cateList);
            Iterator it = cateList.iterator();
            while (it.hasNext()) {
                a item = (a) it.next();
                HTApplication.b(item.cateid, item.catename);
            }
            OnlineFragment.k(this.cde).e(HTApplication.bX());
        }
    }

    @MessageHandler(message = 3346)
    public void onGuestRoom(boolean succ, long start, h info) {
        OnlineFragment.j(this.cde).onRefreshComplete();
        OnlineFragment.i(this.cde).onLoadComplete();
        if (succ && OnlineFragment.k(this.cde) != null && info != null && info.isSucc()) {
            if (this.cde.getCurrentPage() == 0) {
                this.cde.FC();
            }
            if (start != 0) {
                OnlineFragment.g(this.cde).start = info.start;
                OnlineFragment.g(this.cde).more = info.more;
                OnlineFragment.g(this.cde).room_infos.addAll(info.room_infos);
            } else {
                OnlineFragment.a(this.cde, info);
            }
            OnlineFragment.k(this.cde).c(OnlineFragment.g(this.cde).room_infos, true);
        } else if (this.cde.getCurrentPage() == 0) {
            this.cde.FB();
        }
    }

    @MessageHandler(message = 3347)
    public void onRefreshRoom(boolean succ, h info) {
        if (succ && OnlineFragment.k(this.cde) != null && info != null && info.isSucc() && !UtilsFunction.empty(info.room_infos)) {
            OnlineFragment.k(this.cde).V(info.room_infos);
        }
    }

    @MessageHandler(message = 3349)
    public void onRefreshRoom(boolean succ, d info) {
        if (!succ || OnlineFragment.k(this.cde) == null || info == null || !info.isSucc() || UtilsFunction.empty(info.notice)) {
            OnlineFragment.l(this.cde).setVisibility(8);
            return;
        }
        OnlineFragment.l(this.cde).setText(info.notice);
        OnlineFragment.l(this.cde).setSelected(true);
        OnlineFragment.l(this.cde).setVisibility(0);
    }

    @MessageHandler(message = 3348)
    public void onShowLoading(boolean show) {
        this.cde.cs(show);
    }
}
