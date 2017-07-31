package com.huluxia.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.profile.PhotoInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.widget.photowall.PhotoWall2;
import com.huluxia.widget.photowall.PhotoWall2.b;
import com.simple.colorful.a.a;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserAlbumsActivity extends HTBaseActivity {
    private static final int bjd = 1;
    private ArrayList<PhotoInfo> aOE;
    private PhotoWall2 beo;
    private boolean bgD;
    private UserAlbumsActivity bje;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ UserAlbumsActivity bjf;

        {
            this.bjf = this$0;
        }

        @MessageHandler(message = 600)
        public void onCompliant(boolean succ, String msg) {
            this.bjf.bje.cs(false);
            if (succ) {
                t.o(this.bjf.bje, msg);
            } else {
                t.n(this.bjf.bje, msg);
            }
        }
    };
    private long pW;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bje = this;
        setContentView(i.activity_user_albums);
        EventNotifyCenter.add(h.class, this.mCallback);
        this.pW = getIntent().getLongExtra("userId", 0);
        this.aOE = getIntent().getParcelableArrayListExtra("photos");
        this.bgD = getIntent().getBooleanExtra("isOther", true);
        this.aIs.setVisibility(8);
        Kf();
        this.aIT.setVisibility(0);
        ej("用户相册");
        if (this.bgD) {
            this.aIT.setText("举报");
            this.aIT.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ UserAlbumsActivity bjf;

                {
                    this.bjf = this$0;
                }

                public void onClick(View v) {
                    this.bjf.Kg();
                }
            });
            return;
        }
        ej("用户相册");
        this.aIT.setText("编辑");
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UserAlbumsActivity bjf;

            {
                this.bjf = this$0;
            }

            public void onClick(View v) {
                t.a(this.bjf, this.bjf.pW, this.bjf.beo.getPhotos(), this.bjf.bgD, 1);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            this.beo.e(data.getParcelableArrayListExtra("EXTRA_PHOTOS"), true);
            com.huluxia.service.i.EJ();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Kf() {
        this.beo = (PhotoWall2) findViewById(g.photowall2);
        this.beo.setItemClickListener(new b(this) {
            final /* synthetic */ UserAlbumsActivity bjf;

            {
                this.bjf = this$0;
            }

            public void a(com.huluxia.module.picture.b unit, int position) {
                ArrayList urls = new ArrayList();
                Iterator it = this.bjf.beo.getPhotos().iterator();
                while (it.hasNext()) {
                    urls.add(((com.huluxia.module.picture.b) it.next()).url);
                }
                t.a(this.bjf, urls, position);
            }

            public void Gq() {
            }
        });
        a(this.beo, this.aOE);
    }

    private void a(PhotoWall2 photoWall, List<PhotoInfo> photos) {
        if (photos != null && photos.size() > 0) {
            photoWall.setVisibility(0);
            for (PhotoInfo photo : photos) {
                com.huluxia.module.picture.b un = new com.huluxia.module.picture.b();
                un.url = photo.getUrl();
                un.fid = String.valueOf(photo.getFid());
                photoWall.e(un);
            }
        }
    }

    private void Kg() {
        final Dialog dialog = new Dialog(this, d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText("是否举报该用户图片违规");
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UserAlbumsActivity bjf;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UserAlbumsActivity bjf;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.bjf.bje.ek("提交内容");
                this.bjf.bje.cs(true);
                com.huluxia.module.profile.g.Eb().aT(this.bjf.pW);
            }
        });
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault);
    }
}
