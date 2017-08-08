package com.MCWorld.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.HTUploadInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.http.base.d;
import com.MCWorld.http.other.h;
import com.MCWorld.http.profile.m;
import com.MCWorld.http.profile.n;
import com.MCWorld.module.picture.b;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.ab;
import com.MCWorld.widget.photowall.PhotoWall2;
import com.simple.colorful.a.a;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UploadPhotoActivity extends HTBaseActivity {
    public static final String beh = "EXTRA_PHOTOS";
    private h aLT = new h();
    private ArrayList<b> aOE;
    private PhotoWall2 beo;
    private boolean bgD;
    private m bhp = new m();
    private n biZ = new n();
    private List<b> bja = new ArrayList();
    private Set<String> bjb = new HashSet();
    private long pW;

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView(i.activity_upload_photo);
        this.pW = getIntent().getLongExtra("userId", 0);
        this.aOE = getIntent().getParcelableArrayListExtra("photos");
        this.bgD = getIntent().getBooleanExtra("isOther", true);
        this.aIs.setVisibility(8);
        this.beo = (PhotoWall2) findViewById(g.photowall2);
        this.beo.setEnableAdd(!this.bgD);
        PhotoWall2 photoWall2 = this.beo;
        if (this.bgD) {
            z = false;
        } else {
            z = true;
        }
        photoWall2.dp(z);
        photoWall2 = this.beo;
        if (this.bgD) {
            z = false;
        } else {
            z = true;
        }
        photoWall2.setShowText(z);
        this.beo.setItemClickListener(new PhotoWall2.b(this) {
            final /* synthetic */ UploadPhotoActivity bjc;

            {
                this.bjc = this$0;
            }

            public void a(b unit, int position) {
                if (this.bjc.bgD) {
                    this.bjc.beo.mk(position);
                } else {
                    this.bjc.beo.d(unit, position);
                }
            }

            public void Gq() {
                if (!this.bjc.bgD) {
                    this.bjc.beo.PG();
                }
            }
        });
        this.beo.e(this.aOE, true);
        this.aIT.setVisibility(0);
        if (this.bgD) {
            ej("用户相册");
            this.aIT.setVisibility(8);
            return;
        }
        ej("编辑相册");
        this.aIT.setVisibility(0);
        this.aIT.setText("提交");
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UploadPhotoActivity bjc;

            {
                this.bjc = this$0;
            }

            public void onClick(View v) {
                this.bjc.Gm();
            }
        });
    }

    private void Gm() {
        this.aIT.setEnabled(false);
        ek("正在提交");
        cs(true);
        Kd();
        Ke();
        Kb();
    }

    private void a(int index, HTUploadInfo info) {
        ((b) this.bja.get(index)).url = info.getUrl();
        ((b) this.bja.get(index)).fid = info.getFid();
    }

    public void Kb() {
        if (UtilsFunction.empty(this.bjb)) {
            ks(0);
            return;
        }
        this.bhp.fV().clear();
        this.bhp.b(this.bjb);
        this.bhp.bb(3);
        this.bhp.a(this);
        this.bhp.eY();
    }

    private void ks(int index) {
        boolean isUploadFinished = false;
        if (index < this.bja.size()) {
            String path = UtilsImage.ScaleBitmapFile(((b) this.bja.get(index)).localPath);
            this.aLT.bb(1);
            this.aLT.setIndex(index);
            this.aLT.setFilename(path);
            this.aLT.a(this);
            this.aLT.eY();
        } else {
            isUploadFinished = true;
        }
        if (isUploadFinished) {
            FR();
        }
    }

    private void FR() {
        if (UtilsFunction.empty(this.bja) && UtilsFunction.empty(this.bjb)) {
            cs(false);
            this.aIT.setEnabled(true);
            t.show_toast(this, getString(com.MCWorld.bbs.b.m.album_no_modified));
            return;
        }
        this.biZ.getImages().clear();
        for (b photo : this.bja) {
            if (!UtilsFunction.empty(photo.fid)) {
                this.biZ.getImages().add(photo.fid);
            }
        }
        this.biZ.bb(2);
        this.biZ.a(this);
        this.biZ.eY();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.beo.onActivityResult(requestCode, resultCode, data);
    }

    public void a(d response) {
        ek("提交内容");
        cs(true);
    }

    public void b(d response) {
        cs(false);
        t.n(this, "提交失败，网络错误");
        this.aIT.setEnabled(true);
    }

    public void c(d response) {
        cs(false);
        if (response.fe() == 1) {
            a(this.aLT.getIndex(), (HTUploadInfo) response.getData());
            ks(this.aLT.getIndex() + 1);
        }
        if (response.fe() == 2) {
            this.aIT.setEnabled(true);
            if (response.getStatus() != 1) {
                g(ab.n(response.fg(), response.fh()), false);
            } else if (response.getCode() == 201) {
                g("需要审核", true);
            } else {
                Kc();
            }
        }
        if (response.fe() == 3) {
            this.aIT.setEnabled(true);
            if (UtilsFunction.empty(this.bja)) {
                Kc();
            } else {
                ks(0);
            }
        }
    }

    private void Kc() {
        t.o(this, "修改相册成功");
        Intent intent = new Intent();
        intent.putExtra("EXTRA_PHOTOS", this.beo.getPhotos());
        setResult(-1, intent);
        finish();
    }

    private void g(String szMessage, final boolean finish) {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(szMessage);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UploadPhotoActivity bjc;

            public void onClick(View arg0) {
                dialog.dismiss();
                if (finish) {
                    this.bjc.finish();
                    com.MCWorld.service.i.EJ();
                }
            }
        });
    }

    private void Kd() {
        Iterator it = this.beo.getPhotos().iterator();
        while (it.hasNext()) {
            b unit = (b) it.next();
            if (!UtilsFunction.empty(unit.localPath)) {
                this.bja.add(unit);
            }
        }
    }

    private void Ke() {
        Iterator it = this.aOE.iterator();
        while (it.hasNext()) {
            this.bjb.add(String.valueOf(((b) it.next()).fid));
        }
        it = this.beo.getPhotos().iterator();
        while (it.hasNext()) {
            b unit = (b) it.next();
            if (!UtilsFunction.empty(unit.fid)) {
                this.bjb.remove(unit.fid);
            }
        }
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault);
    }
}
