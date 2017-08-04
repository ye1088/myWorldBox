package com.huluxia.ui.bbs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.n;
import com.huluxia.data.HTUploadInfo;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsCamera;
import com.huluxia.http.base.d;
import com.huluxia.http.base.f;
import com.huluxia.http.bbs.topic.l;
import com.huluxia.http.other.h;
import com.huluxia.module.topic.k;
import com.huluxia.module.w;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseThemeActivity;
import com.huluxia.ui.crop.CropImageActivity;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.huluxia.utils.r;
import com.huluxia.widget.photowall.PhotoWall;
import com.huluxia.widget.photowall.PhotoWall.a;
import java.util.ArrayList;
import java.util.List;

public class PublishTopicWishActivity extends HTBaseThemeActivity implements f, a {
    private static int aMo = 1;
    private View aJc;
    private TextView aJd;
    private PaintView aLA;
    private EditText aLB;
    private RelativeLayout aLH;
    protected h aLT = new h();
    protected l aLW = new l();
    private EditText aLw;
    private PhotoWall aMl;
    private Button aMm;
    private Activity aMn;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ PublishTopicWishActivity aMp;

        {
            this.aMp = this$0;
        }

        @MessageHandler(message = 660)
        public void onPostCreate(boolean succ, w info) {
            this.aMp.cs(false);
            this.aMp.aMm.setEnabled(true);
            if (info == null) {
                t.n(this.aMp.aMn, "请求失败, 网络问题");
            } else if (info.status == 1) {
                this.aMp.aMn.setResult(-1);
                if (info.code == 201) {
                    t.show_toast(this.aMp.aMn, info.msg);
                    this.aMp.aMn.finish();
                    return;
                }
                t.o(this.aMp.aMn, info.msg);
                this.aMp.aMn.finish();
            } else {
                t.show_toast(this.aMp.aMn, info.msg);
                if (info.code == 106) {
                    this.aMp.Gk();
                }
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ PublishTopicWishActivity aMp;

        {
            this.aMp = this$0;
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == g.iv_close) {
                this.aMp.aMn.finish();
            } else if (id == g.btn_sendwish) {
                this.aMp.Gm();
            } else if (id != g.title_Text) {
            }
        }
    };
    private long sH = 4501;
    private long sm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_wish);
        this.aMn = this;
        this.aLT.bb(1);
        this.sm = getIntent().getLongExtra("cat_id", 0);
        this.sH = getIntent().getLongExtra("tag_id", 0);
        this.aMl = (PhotoWall) findViewById(g.photo_container);
        this.aMl.setAddPhotoClickListener(this);
        this.aMl.setMaxPhotoNum(1);
        this.aLw = (EditText) findViewById(g.content_text);
        findViewById(g.iv_close).setOnClickListener(this.mClickListener);
        this.aMm = (Button) findViewById(g.btn_sendwish);
        this.aMm.setOnClickListener(this.mClickListener);
        this.aJc = findViewById(g.loading);
        this.aJc.setVisibility(8);
        this.aJd = (TextView) findViewById(g.progressTxt);
        Gk();
        EventNotifyCenter.add(com.huluxia.module.h.class, this.mCallback);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onResume() {
        super.onResume();
    }

    private void Gk() {
        this.aLH = (RelativeLayout) findViewById(g.rly_patcha);
        this.aLA = (PaintView) findViewById(g.iv_patch);
        this.aLB = (EditText) findViewById(g.tv_patch);
        this.aMm.setEnabled(false);
        this.aLW.a(new f(this) {
            final /* synthetic */ PublishTopicWishActivity aMp;

            {
                this.aMp = this$0;
            }

            public void a(d response) {
            }

            public void b(d response) {
                this.aMp.Gl();
            }

            public void c(d response) {
                if (response.getStatus() == 1) {
                    this.aMp.en((String) response.getData());
                    this.aMp.aMm.setEnabled(true);
                    return;
                }
                this.aMp.Gl();
            }
        });
        this.aLW.execute();
        this.aLA.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicWishActivity aMp;

            {
                this.aMp = this$0;
            }

            public void onClick(View v) {
                this.aMp.Gk();
            }
        });
    }

    private void en(String url) {
        if (url.length() > 0) {
            this.aLH.setVisibility(0);
            this.aLA.setUri(Uri.parse(url)).placeHolder(com.simple.colorful.d.isDayMode() ? b.f.place_holder_normal : b.f.place_holder_night_normal).setImageLoader(com.huluxia.l.cb().getImageLoader());
        }
    }

    private void Gl() {
        t.n(this, "网络问题\n验证失败，不能发贴\n请重试");
        finish();
    }

    private void Gm() {
        if (this.aLw.getText().toString().trim().length() < 5) {
            t.n(this, "填写内容不能少于5个字符");
        } else if (this.aLH.getVisibility() != 0 || this.aLB.getText().toString().length() > 1) {
            this.aMm.setEnabled(false);
            at.hideInputMethod(this.aLw);
            ks(0);
        } else {
            t.n(this, "验证码不能为空");
        }
    }

    protected void a(int index, HTUploadInfo info) {
        List<PhotoWall.b> photos = this.aMl.getPhotos();
        ((PhotoWall.b) photos.get(index)).setUrl(info.getUrl());
        ((PhotoWall.b) photos.get(index)).setFid(info.getFid());
    }

    protected void ks(int index) {
        List<PhotoWall.b> photos = this.aMl.getPhotos();
        boolean isUploadFinished = false;
        if (index < photos.size()) {
            PhotoWall.b photo = (PhotoWall.b) photos.get(index);
            if (photo.getId() == -1 || photo.getUrl() != null) {
                isUploadFinished = true;
            } else {
                this.aLT.setIndex(index);
                this.aLT.setFilename(photo.getLocalPath());
                this.aLT.a(this);
                this.aLT.eY();
            }
        } else {
            isUploadFinished = true;
        }
        if (isUploadFinished) {
            FR();
        }
    }

    public void Gn() {
        r.showPhotoMenu(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String cropPath = UtilsCamera.onPickResultToCrop(resultCode, requestCode, data, this, CropImageActivity.class, null, false);
            if (UtilsFile.isExist(cropPath)) {
                PhotoWall.b pn = new PhotoWall.b();
                pn.setLocalPath(cropPath);
                this.aMl.a(pn);
            }
        }
    }

    public void a(d response) {
        if (response.fe() == 1) {
            ek("上传图片");
        } else if (response.fe() == 2) {
            ek("提交内容");
        }
        cs(true);
    }

    public void b(d response) {
        t.n(this, "提交失败，网络错误");
        this.aMm.setEnabled(true);
        cs(false);
    }

    public void c(d response) {
        cs(false);
        if (response.fe() == 1) {
            a(this.aLT.getIndex(), (HTUploadInfo) response.getData());
            ks(this.aLT.getIndex() + 1);
        } else if (response.fe() == 2) {
            this.aMm.setEnabled(true);
            if (response.getStatus() == 1) {
                setResult(-1);
                if (response.getCode() == 201) {
                    t.show_toast(this.aMn, (String) response.getData());
                    finish();
                    return;
                }
                t.o(this, (String) response.getData());
                finish();
                return;
            }
            t.show_toast(this.aMn, response.fh());
            if (response.fg() == 106) {
                Gk();
            }
        }
    }

    public void Go() {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(this.aLw.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.aLB.getWindowToken(), 0);
    }

    public void FR() {
        String detail = this.aLw.getText().toString();
        String patcha = this.aLB.getText().toString();
        String title = aw.W("【许愿】" + detail, 32);
        List<String> images = new ArrayList();
        for (PhotoWall.b photo : this.aMl.getPhotos()) {
            if (photo.getFid() != null) {
                images.add(photo.getFid());
            }
        }
        this.aMm.setEnabled(false);
        cs(true);
        k.Ej().a(title, detail, this.sm, this.sH, aMo, patcha, null, images, null);
    }

    private void cs(boolean show) {
        if (this.aJc != null) {
            if (show) {
                this.aJc.setVisibility(0);
            } else {
                this.aJc.setVisibility(8);
            }
        }
    }

    private void ek(String txt) {
        this.aJd.setText(txt);
    }

    protected int AN() {
        return n.AppDialog;
    }

    protected int AO() {
        return n.AppDialogNight;
    }
}
