package com.huluxia.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.HTUploadInfo;
import com.huluxia.data.j;
import com.huluxia.data.profile.PhotoInfo;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsImage;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.http.base.d;
import com.huluxia.http.base.f;
import com.huluxia.http.other.h;
import com.huluxia.http.profile.m;
import com.huluxia.module.picture.b;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.utils.ab;
import com.huluxia.widget.photowall.PhotoWall2;
import com.huluxia.widget.photowall.PhotoWall2.a;
import java.util.ArrayList;
import java.util.List;

public class SpaceCustomStyleFragment extends BaseThemeFragment implements f {
    private static final int bhk = 1;
    private CommonMenuDialog aHI = null;
    private BaseLoadingLayout aIy;
    private View aJc;
    private TextView aJd;
    private ProfileInfo aKG;
    private h aLT = new h();
    private CallbackHandler aky = new 3(this);
    private CheckBox bhl;
    private PhotoWall2 bhm;
    private TextView bhn;
    private TextView bho;
    private m bhp = new m();
    private b bhq;
    private b bhr;
    private View mView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        this.mView = inflater.inflate(i.fragment_space_style_custom, container, false);
        this.aIy = (BaseLoadingLayout) this.mView.findViewById(g.loading_layout);
        this.aIy.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ SpaceCustomStyleFragment bhs;

            {
                this.bhs = this$0;
            }

            public void onRetryClick(View view) {
                this.bhs.reload();
            }
        });
        this.bho = (TextView) this.mView.findViewById(g.tv_use);
        this.bho.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SpaceCustomStyleFragment bhs;

            {
                this.bhs = this$0;
            }

            public void onClick(View v) {
                this.bhs.JC();
            }
        });
        this.bhl = (CheckBox) this.mView.findViewById(g.cb_slices);
        this.bhn = (TextView) this.mView.findViewById(g.photo_count);
        this.bhn.setText(getResources().getString(com.huluxia.bbs.b.m.format_photo_count, new Object[]{Integer.valueOf(0)}));
        n(this.mView);
        this.aJc = this.mView.findViewById(g.loading);
        this.aJc.setVisibility(8);
        this.aJd = (TextView) this.mView.findViewById(g.progressTxt);
        ek("正在提交");
        EventNotifyCenter.add(com.huluxia.module.h.class, this.aky);
        this.aIy.Fy();
        reload();
        return this.mView;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void reload() {
        if (j.ep().ey()) {
            com.huluxia.module.profile.g.Eb().aR(j.ep().getUserid());
        }
    }

    private void n(View view) {
        this.bhm = (PhotoWall2) view.findViewById(g.photowall);
        this.bhm.setItemClickListener(new PhotoWall2.b(this) {
            final /* synthetic */ SpaceCustomStyleFragment bhs;

            {
                this.bhs = this$0;
            }

            public void a(b unit, int position) {
                if (this.bhs.aJc.getVisibility() != 0) {
                    this.bhs.c(unit, position);
                }
            }

            public void Gq() {
                if (this.bhs.bhm.getPhotoCount() >= 8) {
                    t.l(this.bhs.getActivity(), this.bhs.getResources().getString(com.huluxia.bbs.b.m.album_photo_count_max, new Object[]{Integer.valueOf(8)}));
                } else if (this.bhs.aJc.getVisibility() != 0) {
                    t.a(this.bhs, 1, 1, null);
                }
            }
        });
        this.bhm.setPhotoCountChangedListener(new a(this) {
            final /* synthetic */ SpaceCustomStyleFragment bhs;

            {
                this.bhs = this$0;
            }

            public void kx(int count) {
                this.bhs.bhn.setText(this.bhs.getResources().getString(com.huluxia.bbs.b.m.format_photo_count, new Object[]{Integer.valueOf(count)}));
                if (count < 3) {
                    this.bhs.bhl.setChecked(false);
                    this.bhs.bhl.setEnabled(false);
                    return;
                }
                this.bhs.bhl.setEnabled(true);
            }
        });
    }

    private void JB() {
        if (this.aKG != null) {
            this.bhm.clear();
            for (PhotoInfo photo : this.aKG.getPhotos()) {
                b un = new b();
                un.url = photo.getUrl();
                un.fid = String.valueOf(photo.getFid());
                this.bhm.e(un);
            }
        }
    }

    private void JC() {
        if (this.aKG != null) {
            if (JG()) {
                this.bho.setEnabled(false);
                cs(true);
                return;
            }
            t.l(getActivity(), getResources().getString(com.huluxia.bbs.b.m.album_no_modified));
        }
    }

    private void a(int index, HTUploadInfo info) {
        this.bhq.url = info.getUrl();
        this.bhq.fid = info.getFid();
    }

    public boolean JD() {
        if (this.bhr == null || UtilsFunction.empty(this.bhr.fid)) {
            return false;
        }
        cs(true);
        this.bho.setEnabled(false);
        this.bhp.setId(this.bhr.fid);
        this.bhp.bb(3);
        this.bhp.a(this);
        this.bhp.eY();
        return true;
    }

    private boolean JE() {
        if (this.bhq == null || UtilsFunction.empty(this.bhq.localPath) || !UtilsFunction.empty(this.bhq.fid)) {
            return false;
        }
        cs(true);
        this.bho.setEnabled(false);
        String path = UtilsImage.ScaleBitmapFile(this.bhq.localPath, com.huluxia.module.h.arA, com.huluxia.module.h.arA);
        this.aLT.bb(1);
        this.aLT.setFilename(path);
        this.aLT.a(this);
        this.aLT.eY();
        return true;
    }

    private boolean JF() {
        if (this.bhq == null || UtilsFunction.empty(this.bhq.fid)) {
            return false;
        }
        cs(true);
        this.bho.setEnabled(false);
        com.huluxia.module.profile.g.Eb().ed(this.bhq.fid);
        return true;
    }

    private boolean JG() {
        int newModel;
        if (this.bhl.isChecked()) {
            newModel = 1;
        } else {
            newModel = 0;
        }
        if (this.aKG == null || this.aKG.model == newModel) {
            return false;
        }
        com.huluxia.module.profile.g.Eb().a(newModel, getActivity());
        return true;
    }

    public void a(d response) {
        ek("提交内容");
        cs(true);
    }

    public void b(d response) {
        cs(false);
        this.bho.setEnabled(true);
        t.n(getActivity(), "提交失败，网络错误");
    }

    public void c(d response) {
        cs(false);
        if (response.fe() == 1) {
            a(this.aLT.getIndex(), (HTUploadInfo) response.getData());
            JF();
        } else if (response.fe() == 3) {
            cs(false);
            this.bho.setEnabled(true);
            if (response.getStatus() == 1) {
                t.o(getActivity(), getResources().getString(com.huluxia.bbs.b.m.delete_album_photo_succ));
                this.bhm.gR(this.bhr.fid);
                this.bhr = null;
                EventNotifyCenter.notifyEvent(com.huluxia.module.profile.f.class, 3, new Object[]{this.bhm.getPhotos()});
                return;
            }
            t.n(getActivity(), ab.n(response.fg(), response.fh()));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.bhm != null && resultCode == -1 && requestCode == 1) {
            List<b> photo = data.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED");
            if (!UtilsFunction.empty(photo)) {
                this.bhq = (b) photo.get(0);
                JE();
            }
        }
    }

    protected void cs(boolean show) {
        if (this.aJc != null) {
            if (show) {
                this.aJc.setVisibility(0);
            } else {
                this.aJc.setVisibility(8);
            }
        }
    }

    private void c(final b unit, final int position) {
        ArrayList<Object> list = new ArrayList();
        list.add(new ResMenuItem("查看原图", 0, com.huluxia.bbs.b.d.locmgr_focus_btn_color));
        list.add(new ResMenuItem("删除", 1, com.huluxia.bbs.b.d.locmgr_focus_btn_color));
        this.aHI = new CommonMenuDialog(getActivity(), list, new CommonMenuDialogListener(this) {
            final /* synthetic */ SpaceCustomStyleFragment bhs;

            public void pressMenuById(int inIndex, Object object) {
                if (inIndex == 0) {
                    ArrayList<String> urls = new ArrayList();
                    for (com.huluxia.widget.banner.a info : this.bhs.aKG.getPhotos()) {
                        urls.add(info.url);
                    }
                    t.a(this.bhs.getActivity(), urls, position);
                } else if (inIndex == 1) {
                    this.bhs.bhr = unit;
                    this.bhs.JD();
                }
                this.bhs.aHI.dismissDialog();
            }
        }, com.simple.colorful.d.RB(), 1);
        this.aHI.showMenu(null, null);
    }

    protected void ek(String txt) {
        this.aJd.setText(txt);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(g.split1, c.splitColor).a((TextView) this.mView.findViewById(g.tv_slice_mode), 16842806).b(this.bhl, c.drawableCompoundButtonSetting).a((TextView) this.mView.findViewById(g.slice_hint), 16842906).a((TextView) this.mView.findViewById(g.photo), 16842806).a(this.bhn, 16842806).a(this.bho, 16842809).j(this.bho, c.backgroundButtonSolidGreen);
    }
}
