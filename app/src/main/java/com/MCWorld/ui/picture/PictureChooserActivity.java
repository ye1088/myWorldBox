package com.MCWorld.ui.picture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.e;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.widget.hlistview.AdapterView;
import com.MCWorld.framework.base.widget.hlistview.AdapterView.OnItemClickListener;
import com.MCWorld.framework.base.widget.hlistview.HListView;
import com.MCWorld.l;
import com.MCWorld.module.picture.PictureInfo;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.ui.picture.PictureChooserFragment.b;
import java.io.File;
import java.util.ArrayList;

public class PictureChooserActivity extends HTBaseThemeActivity implements b {
    public static final String ACTION_PICK = "com.huluxia.ACTION_PICK";
    public static final String aLr = "EXTRA_CURRENT_SELECTED";
    public static final String bdD = "EXTRA_MAX_SELECTED";
    public static final String bdE = "EXTRA_SHOW_CAMERA";
    public static final String bdF = "com.huluxia.ACTION_MULTI_CHOICE";
    private boolean aUZ;
    private int aVb = 8;
    private ArrayList<com.MCWorld.module.picture.b> aVc;
    private PictureChooserFragment bdG;
    private HListView bdH;
    private a bdI;
    private TextView bdJ;
    private boolean bdK;
    private String mAction;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ PictureChooserActivity bdL;

        {
            this.bdL = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.btn_back) {
                this.bdL.finish();
            } else if (id == g.btn_ok) {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_CURRENT_SELECTED", this.bdL.aVc);
                this.bdL.setResult(-1, intent);
                PictureInfo.getInstance().clear();
                this.bdL.finish();
            }
        }
    };

    private class a extends BaseAdapter {
        final /* synthetic */ PictureChooserActivity bdL;
        private Context mContext;

        public a(PictureChooserActivity pictureChooserActivity, Context context) {
            this.bdL = pictureChooserActivity;
            this.mContext = context;
        }

        public int getCount() {
            return this.bdL.aVc.size();
        }

        public Object getItem(int position) {
            return this.bdL.aVc.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mContext).inflate(i.item_pic_preview, parent, false);
            }
            PaintView imageView = (PaintView) convertView;
            com.MCWorld.module.picture.b unit = (com.MCWorld.module.picture.b) getItem(position);
            if (!UtilsFunction.empty(unit.url)) {
                a(unit.url, imageView);
            } else if (!UtilsFunction.empty(unit.localPath)) {
                File imageFile = new File(unit.localPath);
                if (imageFile.exists()) {
                    a(imageFile, imageView);
                }
            }
            convertView.setTag(unit);
            return convertView;
        }

        private void a(String url, PaintView imageView) {
            imageView.resizeDimen(e.item_pic_preview, e.item_pic_preview).setUri(UtilUri.getUriOrNull(url)).scaleType(ScaleType.CENTER_CROP).radius(UtilsScreen.convertDpToPixel(3.0f, this.mContext)).borderColor(this.mContext.getResources().getColor(d.background_dim5), 1.0f).setImageLoader(l.cb().getImageLoader());
        }

        private void a(File localFile, PaintView imageView) {
            imageView.resizeDimen(e.item_pic_preview, e.item_pic_preview).setUri(UtilUri.getLocalFileUriOrNull(localFile)).scaleType(ScaleType.CENTER_CROP).radius(UtilsScreen.convertDpToPixel(3.0f, this.mContext)).borderColor(this.mContext.getResources().getColor(d.background_dim5), 1.0f).setImageLoader(l.cb().getImageLoader());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_picture_chooser);
        Intent intent = getIntent();
        this.mAction = intent.getAction();
        this.aVb = intent.getIntExtra(bdD, 0);
        this.aUZ = intent.getBooleanExtra(bdE, false);
        this.aVc = intent.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED");
        if (this.aVc == null) {
            this.aVc = new ArrayList();
        }
        FragmentManager fm = getSupportFragmentManager();
        this.bdG = (PictureChooserFragment) fm.findFragmentById(g.fragment_container);
        if (this.bdG == null) {
            int choiceMode;
            if (this.mAction.equals(bdF)) {
                choiceMode = 1;
            } else {
                choiceMode = 0;
            }
            this.bdG = PictureChooserFragment.a(choiceMode, this.aVb, this.aUZ, this.aVc);
            fm.beginTransaction().add(g.fragment_container, this.bdG).commit();
        }
        this.bdJ = (TextView) findViewById(g.btn_ok);
        this.bdJ.setOnClickListener(this.mClickListener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.bdG.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        PictureInfo.getInstance().clear();
    }

    public void b(com.MCWorld.module.picture.b unit) {
        this.bdI.notifyDataSetChanged();
        this.bdK = true;
        IV();
    }

    public void c(com.MCWorld.module.picture.b unit) {
        this.bdI.notifyDataSetChanged();
        this.bdK = true;
        IV();
    }

    public void d(com.MCWorld.module.picture.b unit) {
        if (this.mAction.equals(ACTION_PICK) && unit != null) {
            Intent intent = new Intent();
            intent.putExtra("EXTRA_CURRENT_SELECTED", unit);
            setResult(-1, intent);
            finish();
        }
    }

    public void IS() {
    }

    public void IT() {
        this.aVc = this.bdG.Jd();
        IU();
    }

    public void kX(int mMaxSelected) {
        t.n(this, String.format("最多选择%d张图片", new Object[]{Integer.valueOf(mMaxSelected)}));
    }

    private void IU() {
        this.bdH = (HListView) findViewById(g.list_preview);
        this.bdI = new a(this, this);
        this.bdH.setAdapter(this.bdI);
        this.bdH.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ PictureChooserActivity bdL;

            {
                this.bdL = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                this.bdL.aVc.remove(position);
                this.bdL.bdI.notifyDataSetChanged();
                this.bdL.bdK = true;
                this.bdL.IV();
                this.bdL.bdG.Je();
            }
        });
        IV();
    }

    private void IV() {
        int selectedCount = this.aVc.size();
        this.bdJ.setText(String.format("完成(%d)", new Object[]{Integer.valueOf(selectedCount)}));
        this.bdJ.setEnabled(this.bdK);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault).aY(g.split, c.splitColor).aY(g.bottom_bar, c.backgroundDim);
    }
}
