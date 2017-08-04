package com.huluxia.ui.profile;

import android.content.Context;
import android.widget.CheckBox;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.profile.a;
import com.huluxia.module.profile.f;
import com.huluxia.module.w;
import com.huluxia.t;

class SpaceCustomStyleFragment$3 extends CallbackHandler {
    final /* synthetic */ SpaceCustomStyleFragment bhs;

    SpaceCustomStyleFragment$3(SpaceCustomStyleFragment this$0) {
        this.bhs = this$0;
    }

    @MessageHandler(message = 550)
    public void onRecvProfileInfo(boolean succ, ProfileInfo info, long userId) {
        boolean z = true;
        if (j.ep().ey() && j.ep().getUserid() == userId) {
            this.bhs.cs(false);
            if (succ && info != null) {
                SpaceCustomStyleFragment.c(this.bhs).FC();
                SpaceCustomStyleFragment.a(this.bhs, info);
                SpaceCustomStyleFragment.d(this.bhs);
                CheckBox f = SpaceCustomStyleFragment.f(this.bhs);
                if (SpaceCustomStyleFragment.e(this.bhs).model != 1) {
                    z = false;
                }
                f.setChecked(z);
            } else if (SpaceCustomStyleFragment.c(this.bhs).getCurrentPage() == 0) {
                SpaceCustomStyleFragment.c(this.bhs).FB();
            } else {
                t.n(this.bhs.getActivity(), this.bhs.getActivity().getResources().getString(m.refresh_profile_failed));
            }
        }
    }

    @MessageHandler(message = 627)
    public void onRecvNewSpaceModel(boolean succ, w info, int model, Context context) {
        if (context == this.bhs.getActivity()) {
            SpaceCustomStyleFragment.g(this.bhs).setEnabled(true);
            this.bhs.cs(false);
            if (succ) {
                t.show_toast(this.bhs.getActivity(), this.bhs.getResources().getString(m.update_space_succ));
                SpaceCustomStyleFragment.e(this.bhs).model = model;
                this.bhs.getActivity().finish();
                Class cls = f.class;
                Object[] objArr = new Object[2];
                objArr[0] = Boolean.valueOf(model == 1);
                objArr[1] = SpaceCustomStyleFragment.h(this.bhs).getPhotos();
                EventNotifyCenter.notifyEvent(cls, 2, objArr);
                return;
            }
            t.show_toast(this.bhs.getActivity(), info == null ? this.bhs.getResources().getString(m.update_space_failded) : info.msg);
        }
    }

    @MessageHandler(message = 628)
    public void onRecvAddPhoto(boolean succ, a info, String fid) {
        this.bhs.cs(false);
        SpaceCustomStyleFragment.g(this.bhs).setEnabled(true);
        if (succ) {
            SpaceCustomStyleFragment.i(this.bhs).localPath = null;
            SpaceCustomStyleFragment.i(this.bhs).fid = String.valueOf(info.photoId);
            SpaceCustomStyleFragment.h(this.bhs).e(SpaceCustomStyleFragment.i(this.bhs));
            SpaceCustomStyleFragment.a(this.bhs, null);
            t.o(this.bhs.getActivity(), "修改相册成功");
            EventNotifyCenter.notifyEvent(f.class, 3, SpaceCustomStyleFragment.h(this.bhs).getPhotos());
            return;
        }
        t.n(this.bhs.getActivity(), "提交失败，网络错误");
    }
}
