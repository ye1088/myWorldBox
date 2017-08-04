package com.huluxia.ui.bbs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.HTUploadInfo;
import com.huluxia.data.TagInfo;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.j;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.http.toolbox.entity.utils.TextUtils;
import com.huluxia.framework.base.image.Config.NetFormat;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsBitmap;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.framework.base.widget.hlistview.AdapterView;
import com.huluxia.framework.base.widget.hlistview.AdapterView.OnItemClickListener;
import com.huluxia.framework.base.widget.hlistview.HListView;
import com.huluxia.http.bbs.topic.l;
import com.huluxia.http.other.h;
import com.huluxia.http.other.i;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.TagAdapter;
import com.huluxia.utils.ah;
import com.huluxia.utils.at;
import com.huluxia.utils.r;
import com.huluxia.video.camera.VideoLibLoader;
import com.huluxia.widget.emoInput.FaceItem;
import com.huluxia.widget.emoInput.FaceItem.FACE_TYPE;
import com.huluxia.widget.emoInput.ThemedFacePanelView;
import com.huluxia.widget.listview.GridViewNotScroll;
import com.huluxia.widget.photowall.PhotoWall2;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class PublishTopicBaseActivity extends HTBaseActivity implements OnClickListener, OnTouchListener, com.huluxia.ui.itemadapter.TagAdapter.a, com.huluxia.widget.emoInput.FacePanelView.a, com.huluxia.widget.photowall.PhotoWall.a {
    private static final String TAG = "PublishTopicBaseActivity";
    private static final String aLn = "draft";
    private static final String aLo = "cat_id";
    private static final String aLp = "video_power";
    private static final String aLq = "taglist";
    private static final String aLr = "EXTRA_CURRENT_SELECTED";
    protected static final String aLs = "EXTRA_RESERVED_SELECTED";
    protected final int aEj = 2000;
    protected final int aEk = 10;
    protected boolean aEl = false;
    protected PaintView aLA;
    protected EditText aLB;
    protected LinearLayout aLC;
    protected LinearLayout aLD;
    protected LinearLayout aLE;
    protected LinearLayout aLF;
    protected RelativeLayout aLG;
    protected RelativeLayout aLH;
    protected RelativeLayout aLI;
    protected RelativeLayout aLJ;
    protected ThemedFacePanelView aLK;
    protected ImageView aLL;
    protected ImageView aLM;
    protected ImageView aLN;
    protected ImageView aLO;
    protected Button aLP;
    protected RadioGroup aLQ;
    protected GridViewNotScroll aLR;
    protected TagAdapter aLS;
    protected h aLT = new h();
    protected h aLU = new h();
    protected i aLV = new i();
    protected l aLW = new l();
    private boolean aLX = false;
    protected PhotoWall2 aLY;
    protected ArrayList<UserBaseInfo> aLZ = new ArrayList();
    protected int aLt = 0;
    protected ArrayList<TagInfo> aLu = null;
    protected EditText aLv;
    protected EditText aLw;
    protected EditText aLx;
    protected TextView aLy;
    private DialogManager aLz;
    protected ArrayList<UserBaseInfo> aMa;
    private a aMb;
    private HListView aMc;
    private int aMd = 0;
    private int aMe = 0;
    private Set<Long> aMf;
    private Activity mContext;
    protected long sH = 0;
    protected long sm;

    private class a extends BaseAdapter {
        final /* synthetic */ PublishTopicBaseActivity aMg;
        private Context mContext;

        public a(PublishTopicBaseActivity publishTopicBaseActivity, Context context) {
            this.aMg = publishTopicBaseActivity;
            this.mContext = context;
        }

        public int getCount() {
            return this.aMg.aLZ.size();
        }

        public Object getItem(int position) {
            return this.aMg.aLZ.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mContext).inflate(b.i.item_pic_preview, parent, false);
            }
            ImageView imageView = (PaintView) convertView;
            imageView.radius((float) t.dipToPx(this.mContext, 3)).borderColor(d.getColor(this.mContext, c.backgroundDim5), (float) t.dipToPx(this.mContext, 1)).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal);
            UserBaseInfo info = (UserBaseInfo) getItem(position);
            if (UtilsFunction.empty(this.aMg.aMf) || !this.aMg.aMf.contains(Long.valueOf(info.userID))) {
                imageView.setUri(null).setImageLoader(com.huluxia.l.cb().getImageLoader());
                imageView.setUri(Uri.parse(info.avatar), NetFormat.FORMAT_160).setImageLoader(com.huluxia.l.cb().getImageLoader());
                this.aMg.a(imageView, this.aMg.aMd);
            } else {
                imageView.setUri(null).setImageLoader(com.huluxia.l.cb().getImageLoader());
                imageView.setUri(Uri.parse(info.avatar), NetFormat.FORMAT_160).setImageLoader(com.huluxia.l.cb().getImageLoader());
                imageView.setColorFilter(this.aMg.aMe);
            }
            return convertView;
        }
    }

    public abstract void FR();

    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.aLz = new DialogManager(this);
        setContentView(b.i.activity_publish_topic);
        ej("发布新话题");
        this.aLT.bb(1);
        this.aLU.bb(11);
        this.aLV.bb(12);
        if (savedInstanceState == null) {
            this.sm = getIntent().getLongExtra(aLo, 0);
            this.aLt = getIntent().getIntExtra(aLp, 0);
            if (getIntent().getExtras() != null) {
                this.aLu = getIntent().getExtras().getParcelableArrayList(aLq);
            }
            this.aLZ = getIntent().getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED");
            this.aMa = getIntent().getParcelableArrayListExtra("EXTRA_RESERVED_SELECTED");
        } else {
            this.sm = savedInstanceState.getLong(aLo, 0);
            this.aLt = savedInstanceState.getInt(aLp, 0);
            this.aLu = savedInstanceState.getParcelableArrayList(aLq);
            this.aLZ = savedInstanceState.getParcelableArrayList("EXTRA_CURRENT_SELECTED");
            this.aMa = savedInstanceState.getParcelableArrayList("EXTRA_RESERVED_SELECTED");
        }
        if (this.aLZ == null) {
            this.aLZ = new ArrayList();
        }
        if (!UtilsFunction.empty(this.aMa)) {
            this.aMf = new HashSet();
            Iterator it = this.aMa.iterator();
            while (it.hasNext()) {
                this.aMf.add(Long.valueOf(((UserBaseInfo) it.next()).userID));
            }
        }
        this.aMd = d.s(this, c.valBrightness);
        this.aMe = d.getColor(this, c.bgColorMask);
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(0);
        this.aIT.setText("提交");
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            {
                this.aMg = this$0;
            }

            public void onClick(View v) {
                this.aMg.Gm();
            }
        });
        this.aLJ = (RelativeLayout) findViewById(g.rl_tag_ctx);
        this.aLR = (GridViewNotScroll) findViewById(g.grid_tag);
        this.aLP = (Button) findViewById(g.btn_select);
        this.aLP.setOnClickListener(this);
        if (this.aLu == null || this.aLu.size() <= 0) {
            this.aLP.setVisibility(8);
        } else {
            this.aLP.setVisibility(0);
        }
        this.aLv = (EditText) findViewById(g.title_Text);
        this.aLy = (TextView) findViewById(g.hint_text);
        this.aLw = (EditText) findViewById(g.content_text);
        this.aLx = (EditText) findViewById(g.contact_Text);
        this.aLv.setOnClickListener(this);
        this.aLv.setOnTouchListener(this);
        this.aLw.setOnClickListener(this);
        this.aLw.setOnTouchListener(this);
        this.aLx.setOnClickListener(this);
        this.aLx.setOnTouchListener(this);
        this.aLw.addTextChangedListener(new TextWatcher(this) {
            private CharSequence aEo;
            private int aEp;
            final /* synthetic */ PublishTopicBaseActivity aMg;
            private int selectionEnd;
            private int selectionStart;

            {
                this.aMg = this$0;
            }

            public void afterTextChanged(Editable s) {
                this.selectionStart = this.aMg.aLw.getSelectionStart();
                this.selectionEnd = this.aMg.aLw.getSelectionEnd();
                if (this.aEo.length() > 2000) {
                    this.aEp = 0;
                } else {
                    this.aEp = 2000 - this.aEo.length();
                }
                if (this.aEo.length() > 10) {
                    this.aMg.aLy.setText("还可以输入" + String.valueOf(this.aEp) + "个字符");
                    this.aMg.aLy.setVisibility(0);
                } else if (this.aMg.aLu == null || this.aMg.aLu.size() <= 0) {
                    this.aMg.aLy.setVisibility(4);
                } else {
                    this.aMg.aLy.setVisibility(4);
                }
                if (this.aEo.length() > 2000) {
                    s.delete(this.selectionStart - 1, this.selectionEnd);
                    int tempSelection = this.selectionStart;
                    this.aMg.aLw.setTextKeepState(s);
                    this.aMg.aLw.setText(s);
                    this.aMg.aLw.setSelection(tempSelection);
                }
            }

            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                this.aEo = s;
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        Gk();
        Gp();
        this.aLC = (LinearLayout) findViewById(g.ly_title);
        this.aLG = (RelativeLayout) findViewById(g.rly_content);
        this.aLD = (LinearLayout) findViewById(g.ly_photo_ctx);
        this.aLE = (LinearLayout) findViewById(g.ly_video_ctx);
        this.aLF = (LinearLayout) findViewById(g.ly_remind);
        this.aLK = (ThemedFacePanelView) findViewById(g.facepanel);
        this.aLI = (RelativeLayout) findViewById(g.rl_voice_ctx);
        this.aLL = (ImageView) findViewById(g.img_photo);
        this.aLM = (ImageView) findViewById(g.img_emotion);
        this.aLN = (ImageView) findViewById(g.img_video);
        this.aLO = (ImageView) findViewById(g.img_remind);
        this.aLO.setVisibility(0);
        this.aLN.setVisibility(8);
        if (this.aLt == 1) {
            this.aLC.setOnClickListener(this);
            this.aLG.setOnClickListener(this);
            this.aLL.setOnClickListener(this);
            this.aLM.setOnClickListener(this);
            this.aLO.setOnClickListener(this);
            this.aLN.setOnClickListener(this);
            this.aLK.setOnItemFaceClick(this);
            this.aLQ = (RadioGroup) findViewById(g.radios_bug);
            this.aLY = (PhotoWall2) findViewById(g.photowall2);
            this.aLY.setShowText(true);
            this.aLY.setItemClickListener(new PhotoWall2.b(this) {
                final /* synthetic */ PublishTopicBaseActivity aMg;

                {
                    this.aMg = this$0;
                }

                public void a(com.huluxia.module.picture.b unit, int position) {
                    if (this.aMg.aIT.isEnabled()) {
                        this.aMg.aLY.d(unit, position);
                    }
                }

                public void Gq() {
                    if (this.aMg.aIT.isEnabled()) {
                        this.aMg.aLY.PG();
                    }
                }
            });
            this.aLY.setPhotoCountChangedListener(new com.huluxia.widget.photowall.PhotoWall2.a(this) {
                final /* synthetic */ PublishTopicBaseActivity aMg;

                {
                    this.aMg = this$0;
                }

                public void kx(int count) {
                    if (this.aMg.aLt != 1 || count > 0 || this.aMg.aLN != null) {
                    }
                }
            });
            this.aLS = new TagAdapter(this);
            this.aLR.setAdapter(this.aLS);
            this.aLS.a((com.huluxia.ui.itemadapter.TagAdapter.a) this);
        } else {
            this.aLC.setOnClickListener(this);
            this.aLG.setOnClickListener(this);
            this.aLL.setOnClickListener(this);
            this.aLM.setOnClickListener(this);
            this.aLO.setOnClickListener(this);
            this.aLN.setOnClickListener(this);
            this.aLK.setOnItemFaceClick(this);
            this.aLQ = (RadioGroup) findViewById(g.radios_bug);
            this.aLY = (PhotoWall2) findViewById(g.photowall2);
            this.aLY.setShowText(true);
            this.aLY.setItemClickListener(/* anonymous class already generated */);
            this.aLY.setPhotoCountChangedListener(/* anonymous class already generated */);
            this.aLS = new TagAdapter(this);
            this.aLR.setAdapter(this.aLS);
            this.aLS.a((com.huluxia.ui.itemadapter.TagAdapter.a) this);
        }
    }

    protected void onResume() {
        super.onResume();
        if (!this.aLX) {
            this.aLD.setVisibility(8);
        }
        this.aLK.setVisibility(8);
        this.aLI.setVisibility(8);
        this.aLX = false;
        if (UtilsFunction.empty(this.aLZ) && UtilsFunction.empty(this.aMa)) {
            this.aLF.setVisibility(8);
        } else {
            this.aLF.setVisibility(0);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(aLo, this.sm);
        outState.putInt(aLp, this.aLt);
        outState.putParcelableArrayList(aLq, this.aLu);
        outState.putParcelableArrayList("EXTRA_CURRENT_SELECTED", this.aLZ);
        outState.putParcelableArrayList("EXTRA_RESERVED_SELECTED", this.aMa);
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            this.sm = savedInstanceState.getLong(aLo);
            this.aLt = savedInstanceState.getInt(aLp);
            this.aLu = savedInstanceState.getParcelableArrayList(aLq);
        }
    }

    protected void onStart() {
        super.onStart();
        ER();
    }

    protected void onStop() {
        super.onStop();
        ES();
    }

    protected void Gk() {
        this.aLH = (RelativeLayout) findViewById(g.rly_patcha);
        this.aLA = (PaintView) findViewById(g.iv_patch);
        this.aLB = (EditText) findViewById(g.tv_patch);
        this.aLB.setOnClickListener(this);
        this.aLB.setOnTouchListener(this);
        this.aLH.setOnClickListener(this);
        this.aIT.setEnabled(false);
        this.aLW.a(new com.huluxia.http.base.f(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            {
                this.aMg = this$0;
            }

            public void a(com.huluxia.http.base.d response) {
            }

            public void b(com.huluxia.http.base.d response) {
                this.aMg.Gl();
            }

            public void c(com.huluxia.http.base.d response) {
                if (response.getStatus() == 1) {
                    this.aMg.en((String) response.getData());
                    this.aMg.aIT.setEnabled(true);
                    return;
                }
                this.aMg.Gl();
            }
        });
        this.aLW.execute();
        this.aLA.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            {
                this.aMg = this$0;
            }

            public void onClick(View v) {
                this.aMg.Gk();
            }
        });
    }

    private void en(String url) {
        if (url.length() > 0) {
            this.aLH.setVisibility(0);
            this.aLA.setUri(Uri.parse(url)).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(com.huluxia.l.cb().getImageLoader());
        }
    }

    private void Gl() {
        t.n(this, "网络问题\n验证失败，不能发贴\n请重试");
        finish();
    }

    protected void Gm() {
        String title = this.aLv.getText().toString();
        String contact = this.aLx.getText().toString();
        String detail = this.aLw.getText().toString();
        if (this.aLu == null || this.aLu.size() <= 0 || this.sH != 0) {
            if (this.aLv.getVisibility() == 0) {
                if (title.trim().length() < 5) {
                    t.download_toast(this, "标题不能少于5个字符");
                    return;
                } else if (title.trim().length() > 32) {
                    t.download_toast(this, "标题不能多于32个字符");
                    return;
                }
            }
            if (this.aLx.getVisibility() == 0 && (contact.trim().length() < 5 || contact.trim().length() > 50)) {
                t.download_toast(this, "联系方式为5到50个字符。准确填写可以方便我们更好地为您解决问题。");
                return;
            } else if (detail.trim().length() < 5) {
                t.download_toast(this, "内容不能少于5个字符");
                return;
            } else if (this.aLH.getVisibility() != 0 || this.aLB.getText().toString().length() > 1) {
                this.aIT.setEnabled(false);
                at.hideInputMethod(this.aLw);
                ks(0);
                return;
            } else {
                t.download_toast(this, "验证码不能为空");
                return;
            }
        }
        t.download_toast(this, "请在底部选择帖子标签");
        if (this.aLP != null) {
            this.aLP.performClick();
        }
    }

    protected void a(int index, HTUploadInfo info) {
        List<com.huluxia.module.picture.b> photos = this.aLY.getExistPhotos();
        HLog.verbose(TAG, "setImageFid(%s)", info.getFid());
        ((com.huluxia.module.picture.b) photos.get(index)).url = info.getUrl();
        ((com.huluxia.module.picture.b) photos.get(index)).fid = info.getFid();
    }

    protected void ks(int index) {
        List<com.huluxia.module.picture.b> photos = this.aLY.getExistPhotos();
        boolean isUploadFinished = false;
        if (index < photos.size()) {
            com.huluxia.module.picture.b photo = (com.huluxia.module.picture.b) photos.get(index);
            String path = UtilsBitmap.compressFileBitmapToDisk(photo.localPath, null, 300000);
            if (photo.id != -1 && UtilsFunction.empty(photo.url) && UtilsFile.isExist(path)) {
                this.aLT.setIndex(index);
                this.aLT.setFilename(path);
                this.aLT.a(this);
                this.aLT.eY();
            } else {
                isUploadFinished = true;
            }
        } else {
            isUploadFinished = true;
        }
        if (isUploadFinished) {
            FR();
        }
    }

    protected void b(int index, HTUploadInfo info) {
    }

    protected void kv(int index) {
    }

    protected void c(int index, HTUploadInfo info) {
    }

    protected void kw(int index) {
    }

    public void Gn() {
        r.showPhotoMenu(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 532 && resultCode == 533) {
            if (data != null && data.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED") != null && this.aLZ != null && this.aMb != null) {
                ArrayList<UserBaseInfo> infos = data.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED");
                this.aLZ.clear();
                this.aLZ.addAll(infos);
                this.aMb.notifyDataSetChanged();
                if (UtilsFunction.empty(this.aLZ) && UtilsFunction.empty(this.aMa)) {
                    this.aLF.setVisibility(8);
                } else {
                    this.aLF.setVisibility(0);
                }
                ET();
            }
        } else if (resultCode == -1 && this.aLY.onActivityResult(requestCode, resultCode, data)) {
            this.aLD.setVisibility(0);
            this.aLX = true;
            if (this.aLY.getExistPhotos() == null || this.aLY.getExistPhotos().size() <= 0) {
                this.aLL.setVisibility(0);
                if (1 == this.aLt) {
                }
            } else {
                this.aLL.setVisibility(0);
                this.aLN.setVisibility(8);
            }
            ET();
        }
    }

    public void a(com.huluxia.http.base.d response) {
        ek("提交中..");
        cs(true);
    }

    public void b(com.huluxia.http.base.d response) {
        cs(false);
        t.n(this, "提交失败，网络错误");
        this.aIT.setEnabled(true);
    }

    public void c(com.huluxia.http.base.d response) {
        super.c(response);
        if (response.fe() == 1) {
            a(this.aLT.getIndex(), (HTUploadInfo) response.getData());
            ks(this.aLT.getIndex() + 1);
        } else if (response.fe() == 11) {
            b(this.aLU.getIndex(), (HTUploadInfo) response.getData());
            kv(this.aLU.getIndex() + 1);
        } else if (response.fe() == 12) {
            c(this.aLV.getIndex(), (HTUploadInfo) response.getData());
            kw(this.aLV.getIndex() + 1);
        }
    }

    protected void f(String szMessage, final boolean finish) {
        final Dialog dialog = new Dialog(this, d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(szMessage);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            public void onClick(View arg0) {
                dialog.dismiss();
                if (finish) {
                    this.aMg.finish();
                }
            }
        });
    }

    protected void g(String szMessage, final boolean finish) {
        final Dialog dialog = new Dialog(this, d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(szMessage);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            public void onClick(View arg0) {
                dialog.dismiss();
                if (finish) {
                    this.aMg.finish();
                }
            }
        });
    }

    public void Go() {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(this.aLv.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.aLw.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.aLB.getWindowToken(), 0);
    }

    public boolean onTouch(View v, MotionEvent ev) {
        switch (ev.getAction()) {
            case 1:
                this.aLD.setVisibility(8);
                this.aLK.setVisibility(8);
                this.aLI.setVisibility(8);
                break;
        }
        return false;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.img_photo) {
            if (this.aLD.getVisibility() != 8) {
                this.aLD.setVisibility(8);
            } else if (this.aLY.getPhotoNum() > 0 || !this.aIT.isEnabled()) {
                this.aLD.setVisibility(0);
            } else {
                this.aLY.PG();
            }
            this.aLK.setVisibility(8);
            this.aLE.setVisibility(8);
            this.aLI.setVisibility(8);
            this.aLJ.setVisibility(8);
            Go();
        } else if (id == g.img_emotion) {
            if (this.aLK.getVisibility() == 0) {
                this.aLK.setVisibility(8);
            } else {
                this.aLK.setVisibility(0);
            }
            this.aLD.setVisibility(8);
            this.aLE.setVisibility(8);
            this.aLI.setVisibility(8);
            this.aLJ.setVisibility(8);
            Go();
        } else if (id == g.img_remind) {
            t.a((Activity) this, j.ep().getUserid(), this.aLZ, this.aMa);
        } else if (id == g.title_Text || id == g.content_text || id == g.tv_patch || id == g.ly_title || id == g.rly_content || id == g.rly_patcha) {
            this.aLK.setVisibility(8);
            this.aLD.setVisibility(8);
            this.aLE.setVisibility(8);
            this.aLI.setVisibility(8);
            this.aLJ.setVisibility(8);
        } else if (id == g.btn_select) {
            if (this.aLJ.getVisibility() == 0) {
                this.aLJ.setVisibility(8);
            } else {
                this.aLJ.setVisibility(0);
            }
            this.aLD.setVisibility(8);
            this.aLE.setVisibility(8);
            this.aLK.setVisibility(8);
            this.aLI.setVisibility(8);
            this.aLS.setData(this.aLu);
            Go();
        }
    }

    private void a(final Runnable runnable, final boolean statis) {
        com.huluxia.data.g userInfo = j.ep().ew();
        if (userInfo != null) {
            final com.huluxia.module.topic.g info = ah.KZ().bu(userInfo.userID);
            if (info != null && UtilsFunction.empty(info.videosourl)) {
                info.videosourl = HTApplication.fn + "/game/2016/06/21/video-lib.zip";
            }
            if (info != null && !UtilsFunction.empty(info.videosourl)) {
                int value = VideoLibLoader.MZ().gG(info.videosourl);
                HLog.info(TAG, "video so file is valid " + value, new Object[0]);
                if (value != 2) {
                    if (value != 0 || UtilsNetwork.isWifiConnected(AppConfig.getInstance().getAppContext())) {
                        this.aLz.setCanceledOnClickBackKey(true);
                        this.aLz.setCanceledOnClickOutside(false);
                        this.aLz.showProgressDialog(this, "加载视频插件中...");
                    } else {
                        this.aLz.setCanceledOnClickBackKey(true);
                        this.aLz.setCanceledOnClickOutside(true);
                        this.aLz.showOkCancelDialog("需要下载视频插件，需要耗费大概9M流量，是否确定下载?", hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, new OkCancelDialogListener(this) {
                            final /* synthetic */ PublishTopicBaseActivity aMg;

                            public void onCancel() {
                                if (statis) {
                                    this.aMg.aLz.dismissDialog();
                                } else {
                                    this.aMg.aLz.dismissDialog();
                                }
                            }

                            public void onOk() {
                                this.aMg.aLz.setCanceledOnClickBackKey(true);
                                this.aMg.aLz.setCanceledOnClickOutside(false);
                                this.aMg.aLz.showProgressDialog(this.aMg, "加载视频插件中...", false);
                                VideoLibLoader.MZ().a(info.videosourl, info.videosomd5, new 1(this));
                            }
                        });
                        return;
                    }
                }
                VideoLibLoader.MZ().a(info.videosourl, info.videosomd5, new com.huluxia.video.camera.VideoLibLoader.a(this) {
                    final /* synthetic */ PublishTopicBaseActivity aMg;

                    public void cw(boolean succ) {
                        HLog.info(PublishTopicBaseActivity.TAG, "1 load video plugin succ " + succ, new Object[0]);
                        if (statis) {
                            this.aMg.aLz.dismissDialog();
                        } else {
                            this.aMg.aLz.dismissDialog();
                        }
                        if (succ) {
                            runnable.run();
                        } else {
                            Toast.makeText(this.aMg, "加载视频插件失败", 0).show();
                        }
                    }
                });
            }
        }
    }

    public void a(FaceItem faceItem) {
        if (faceItem.bym != FACE_TYPE.TYPE_EMOJI) {
            return;
        }
        if (com.huluxia.widget.emoInput.c.byf.equals(faceItem.text)) {
            this.aLw.onKeyDown(67, new KeyEvent(0, 67));
            return;
        }
        String text = this.aLw.getText().toString() + faceItem.text;
        int expressCount = com.huluxia.widget.emoInput.d.Ou().gN(text);
        Log.d("[Emoji Click]", text);
        if (expressCount <= 15) {
            int selStart = this.aLw.getSelectionStart();
            this.aLw.getText().insert(selStart, faceItem.text);
            this.aLw.setText(com.huluxia.widget.emoInput.d.Ou().c(this.aLw.getContext(), this.aLw.getText().toString(), at.dipToPx(this, 22), 0));
            this.aLw.setSelection(faceItem.text.length() + selStart);
            return;
        }
        t.download_toast(this, "一次最多发送15个表情噢～");
    }

    protected String FS() {
        return null;
    }

    protected void a(com.huluxia.data.topic.c draft) {
    }

    protected void b(com.huluxia.data.topic.c draft) {
    }

    protected void cv(boolean succ) {
        this.aEl = succ;
    }

    private void ES() {
        if (!TextUtils.isEmpty(FS())) {
            if (this.aEl) {
                ET();
                return;
            }
            com.huluxia.data.topic.c draft = new com.huluxia.data.topic.c();
            a(draft);
            String title = draft.getTitle();
            String detail = draft.eE();
            String contact = draft.getContact();
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(contact) && TextUtils.isEmpty(detail) && UtilsFunction.empty(draft.getPhotos()) && UtilsFunction.empty(draft.getRemindUsers())) {
                ET();
            } else {
                getSharedPreferences(aLn, 0).edit().putString(FS(), Json.toJson(draft)).commit();
            }
        }
    }

    private void ER() {
        SharedPreferences prefs = getSharedPreferences(aLn, 0);
        String draftKey = FS();
        try {
            if (prefs.contains(draftKey)) {
                com.huluxia.data.topic.c draft = (com.huluxia.data.topic.c) Json.parseJsonObject(prefs.getString(draftKey, ""), com.huluxia.data.topic.c.class);
                if (draft != null) {
                    b(draft);
                }
            }
        } catch (Exception e) {
            ET();
        }
    }

    private void ET() {
        String key = FS();
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences prefs = getSharedPreferences(aLn, 0);
            if (prefs.contains(key)) {
                prefs.edit().remove(key).commit();
            }
        }
    }

    public void j(long id, String name) {
        this.aLP.setText(name);
        this.sH = id;
        this.aLP.setTextColor(d.getColor(this.mContext, c.textColorGreen));
        if (VERSION.SDK_INT > 16) {
            this.aLP.setBackground(d.o(this.mContext, c.backgroundButtonTag));
        } else {
            this.aLP.setBackgroundDrawable(d.o(this.mContext, c.backgroundButtonTag));
        }
        this.aLP.setTextColor(d.getColor(this.mContext, 16842809));
        HLog.info(TAG, "tag_id is " + id, new Object[0]);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(g.root_view, c.backgroundDefault).aY(g.rly_selector, c.backgroundDim4).aY(g.split_footer, c.splitColorDim).a(this.aIT, 16842809).a(this.aLv, 16842806).a(this.aLw, 16842806).b(this.aLv, 16842906).b(this.aLw, 16842906).aY(g.split_title, c.splitColor).aY(g.split_content, c.splitColor).aZ(g.img_emotion, c.drawableTopicEmotion).aZ(g.img_photo, c.drawableTopicCamera).aZ(g.img_video, c.drawableTopicVideo);
        if (this.sH == 0) {
            builder.j(this.aLP, c.backgroundButtonGrayTag).a(this.aLP, c.textColorGray);
        } else {
            builder.j(this.aLP, c.backgroundButtonTag).a(this.aLP, 16842809);
        }
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aLK != null) {
            this.aLK.FG();
        }
    }

    private void Gp() {
        this.aMc = (HListView) findViewById(g.list_reminds);
        this.aMc.setVisibility(0);
        this.aMb = new a(this, this);
        this.aMc.setAdapter(this.aMb);
        this.aMc.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ PublishTopicBaseActivity aMg;

            {
                this.aMg = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.aMg.a(this.aMg.aMa, (UserBaseInfo) this.aMg.aLZ.get(position)) == null) {
                    this.aMg.aLZ.remove(position);
                    this.aMg.aMb.notifyDataSetChanged();
                    if (UtilsFunction.empty(this.aMg.aLZ) && UtilsFunction.empty(this.aMg.aMa)) {
                        this.aMg.aLF.setVisibility(8);
                        return;
                    } else {
                        this.aMg.aLF.setVisibility(0);
                        return;
                    }
                }
                t.download_toast(this.aMg.mContext, this.aMg.mContext.getResources().getString(m.reminds_cannont_remove));
            }
        });
    }

    private UserBaseInfo a(ArrayList<UserBaseInfo> list, UserBaseInfo info) {
        if (list == null || info == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserBaseInfo item = (UserBaseInfo) it.next();
            if (item.userID == info.userID) {
                return item;
            }
        }
        return null;
    }

    private void a(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }
}
