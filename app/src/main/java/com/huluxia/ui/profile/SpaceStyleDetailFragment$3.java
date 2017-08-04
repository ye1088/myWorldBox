package com.huluxia.ui.profile;

import android.content.Context;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.profile.f;
import com.huluxia.module.profile.g;
import com.huluxia.module.w;
import com.huluxia.t;

class SpaceStyleDetailFragment$3 extends CallbackHandler {
    final /* synthetic */ SpaceStyleDetailFragment bhZ;

    SpaceStyleDetailFragment$3(SpaceStyleDetailFragment this$0) {
        this.bhZ = this$0;
    }

    @MessageHandler(message = 629)
    public void onRecvUseSpaceStyle(boolean succ, w info, int id, Context context) {
        if (id == SpaceStyleDetailFragment.g(this.bhZ).id && context == this.bhZ.getActivity()) {
            this.bhZ.cs(false);
            SpaceStyleDetailFragment.e(this.bhZ).setEnabled(true);
            if (succ) {
                t.show_toast(this.bhZ.getActivity(), context.getString(m.apply_space_style_succ));
                SpaceStyleDetailFragment.g(this.bhZ).model = 2;
                if (SpaceStyleDetailFragment.h(this.bhZ)) {
                    t.at(this.bhZ.getActivity());
                } else {
                    t.au(this.bhZ.getActivity());
                }
                EventNotifyCenter.notifyEvent(f.class, 1, SpaceStyleDetailFragment.g(this.bhZ));
                return;
            }
            t.n(this.bhZ.getActivity(), info == null ? context.getString(m.apply_space_style_failed) : info.msg);
        }
    }

    @MessageHandler(message = 630)
    public void onRecvBuySpaceStyle(boolean succ, w info, int id, Context context) {
        if (id == SpaceStyleDetailFragment.g(this.bhZ).id && context == this.bhZ.getActivity()) {
            this.bhZ.cs(false);
            SpaceStyleDetailFragment.e(this.bhZ).setEnabled(true);
            if (succ) {
                t.show_toast(this.bhZ.getActivity(), context.getString(m.buy_space_style_succ));
                SpaceStyleDetailFragment.g(this.bhZ).isuse = 1;
                this.bhZ.cs(true);
                g.Eb().b(SpaceStyleDetailFragment.g(this.bhZ).id, this.bhZ.getActivity());
                return;
            }
            t.n(this.bhZ.getActivity(), info == null ? context.getString(m.buy_space_style_failed) : info.msg);
        }
    }

    @MessageHandler(message = 631)
    public void onRecvExchangedSpaceStyle(boolean succ, w info, int id, Context context) {
        if (id == SpaceStyleDetailFragment.g(this.bhZ).id && context == this.bhZ.getActivity()) {
            this.bhZ.cs(false);
            SpaceStyleDetailFragment.e(this.bhZ).setEnabled(true);
            if (succ) {
                SpaceStyleDetailFragment.i(this.bhZ).setDisplayedChild(0);
                SpaceStyleDetailFragment.g(this.bhZ).isuse = 1;
                t.show_toast(this.bhZ.getActivity(), context.getString(m.exchanged_space_style_succ));
                return;
            }
            SpaceStyleDetailFragment.j(this.bhZ).setEnabled(true);
            t.n(this.bhZ.getActivity(), info == null ? context.getString(m.exchanged_space_style_failed) : info.msg);
        }
    }
}
