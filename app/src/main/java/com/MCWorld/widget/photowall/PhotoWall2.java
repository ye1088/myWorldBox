package com.MCWorld.widget.photowall;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.e;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.bbs.b.o;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.widget.hlistview.HListView;
import com.MCWorld.l;
import com.MCWorld.module.h;
import com.MCWorld.t;
import com.MCWorld.ui.picture.PictureCropActivity;
import com.MCWorld.widget.picviewer.PictureViewerActivity;
import com.simple.colorful.d;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhotoWall2 extends LinearLayout {
    protected static final int bCs = 1;
    protected static final int bCt = 2;
    private int bCA;
    private int bCB;
    private int bCC;
    private boolean bCD = true;
    private boolean bCE = true;
    private b bCF;
    private a bCG;
    private int bCH;
    private boolean bCI = true;
    private String bCJ = null;
    private boolean bCK = false;
    private boolean bCL = false;
    private int bCM;
    private float bCN;
    private OnClickListener bCO = new 6(this);
    private TextView bCu;
    protected HListView bCv;
    protected c bCw;
    protected GridView bCx;
    protected d bCy;
    protected ArrayList<com.MCWorld.module.picture.b> bCz = new ArrayList();
    private Context mContext;

    public interface b {
        void Gq();

        void a(com.MCWorld.module.picture.b bVar, int i);
    }

    public interface a {
        void kx(int i);
    }

    public PhotoWall2(Context context) {
        super(context);
        init(context, null);
    }

    public PhotoWall2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(11)
    public PhotoWall2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        setOrientation(1);
        this.bCH = UtilsScreen.dipToPx(context, 80);
        LayoutInflater.from(context).inflate(i.photo_wall2, this, true);
        this.bCu = (TextView) findViewById(g.text_selection);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, o.PhotoWall2, 0, 0);
        try {
            this.bCA = a.getInteger(o.PhotoWall2_maxSelection, 8);
            this.bCB = a.getDimensionPixelSize(o.PhotoWall2_photoWidth, this.bCH);
            this.bCC = a.getDimensionPixelOffset(o.PhotoWall2_photoHeight, this.bCH);
            this.bCD = a.getBoolean(o.PhotoWall2_enableAdd, true);
            this.bCI = a.getBoolean(o.PhotoWall2_showPhotoText, true);
            this.bCJ = a.getString(o.PhotoWall2_customerPhotoText);
            this.bCK = a.getBoolean(o.PhotoWall2_showCoverFlag, false);
            this.bCB = Math.max(this.bCB, this.bCH);
            this.bCC = Math.max(this.bCC, this.bCH);
            this.bCL = a.getBoolean(o.PhotoWall2_gridMode, false);
            this.bCN = a.getFloat(o.PhotoWall2_gridRatio, 1.66f);
            this.bCM = a.getInteger(o.PhotoWall2_gridColumnsCount, 3);
            bJ(context);
            bI(context);
            if (this.bCL) {
                this.bCx.setVisibility(0);
                this.bCv.setVisibility(8);
            } else {
                this.bCx.setVisibility(8);
                this.bCv.setVisibility(0);
            }
            if (this.bCI) {
                String text;
                if (this.bCJ == null) {
                    text = context.getString(m.photo_selection, new Object[]{Integer.valueOf(0), Integer.valueOf(this.bCA)});
                } else {
                    text = this.bCJ;
                }
                this.bCu.setText(text);
                return;
            }
            this.bCu.setVisibility(8);
        } finally {
            a.recycle();
        }
    }

    private void bI(Context context) {
        this.bCx = (GridView) findViewById(g.grid_album);
        this.bCx.setNumColumns(this.bCM);
        this.bCy = new d(this, context, this.bCz);
        this.bCx.setAdapter(this.bCy);
        this.bCx.setOnItemClickListener(new 1(this));
        this.bCx.getViewTreeObserver().addOnGlobalLayoutListener(new 2(this));
    }

    private void bJ(Context context) {
        this.bCv = (HListView) findViewById(g.hlist);
        this.bCw = new c(this, context, this.bCz);
        this.bCv.setAdapter(this.bCw);
        this.bCv.setOnItemClickListener(new 3(this));
        this.bCv.setOnScrollListener(new 4(this, context));
        this.bCv.getViewTreeObserver().addOnGlobalLayoutListener(new 5(this));
    }

    public void PG() {
        t.a((Activity) this.mContext, 1, this.bCA, this.bCz);
    }

    public void d(com.MCWorld.module.picture.b unit, int position) {
        if (UtilsFile.isExist(unit.localPath) || !UtilsFunction.empty(unit.url)) {
            t.a((Activity) this.mContext, 2, this.bCz, (int) h.arp, h.arp, position);
        } else {
            t.n(this.mContext, this.mContext.getString(m.image_no_exist));
        }
    }

    private void mj(int position) {
        this.bCz.remove(position);
        if (!this.bCL) {
            this.bCw.notifyDataSetChanged();
        }
        PH();
    }

    public void e(List<com.MCWorld.module.picture.b> photo, boolean clear) {
        if (clear) {
            this.bCz.clear();
        }
        this.bCz.addAll(photo);
        PH();
        if (this.bCL) {
            this.bCy.notifyDataSetChanged();
        } else {
            this.bCw.notifyDataSetChanged();
        }
    }

    public void e(com.MCWorld.module.picture.b pn) {
        if (pn != null && this.bCz.size() < this.bCA) {
            this.bCz.add(pn);
            PH();
            if (this.bCL) {
                this.bCy.notifyDataSetChanged();
            } else {
                this.bCw.notifyDataSetChanged();
            }
        }
    }

    public int getPhotoNum() {
        if (this.bCz == null) {
            return 0;
        }
        return this.bCz.size();
    }

    public List<com.MCWorld.module.picture.b> getExistPhotos() {
        List<com.MCWorld.module.picture.b> photos = new ArrayList();
        Iterator it = this.bCz.iterator();
        while (it.hasNext()) {
            com.MCWorld.module.picture.b photo = (com.MCWorld.module.picture.b) it.next();
            if (!UtilsFunction.empty(photo.localPath) && UtilsFile.isExist(photo.localPath)) {
                photos.add(photo);
            }
        }
        return photos;
    }

    public void setItemClickListener(b listener) {
        this.bCF = listener;
    }

    public void setPhotoCountChangedListener(a listener) {
        this.bCG = listener;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (requestCode == 1) {
                e(data.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED"), true);
                return true;
            } else if (requestCode == 2) {
                ArrayList<String> paths = data.getStringArrayListExtra(PictureCropActivity.bei);
                int i = 0;
                while (i < this.bCz.size() && i < paths.size()) {
                    com.MCWorld.module.picture.b unit = (com.MCWorld.module.picture.b) this.bCz.get(i);
                    unit.localPath = (String) paths.get(i);
                    if (!(UtilsFunction.empty(unit.fid) || UtilsFunction.empty(unit.localPath))) {
                        unit.fid = null;
                    }
                    i++;
                }
                if (this.bCL) {
                    this.bCy.notifyDataSetChanged();
                } else {
                    this.bCw.notifyDataSetChanged();
                }
                return true;
            }
        }
        return false;
    }

    private void PH() {
        if (this.bCI) {
            String text;
            if (this.bCJ == null) {
                text = getContext().getString(m.photo_selection, new Object[]{Integer.valueOf(this.bCz.size()), Integer.valueOf(this.bCA - this.bCz.size())});
            } else {
                text = this.bCJ;
            }
            this.bCu.setText(text);
        }
        if (this.bCG != null) {
            this.bCG.kx(this.bCz.size());
        }
    }

    public void mk(int position) {
        List<String> paths = new ArrayList();
        Iterator it = this.bCz.iterator();
        while (it.hasNext()) {
            com.MCWorld.module.picture.b photo = (com.MCWorld.module.picture.b) it.next();
            if (!UtilsFunction.empty(photo.url)) {
                paths.add(photo.url);
            } else if (!UtilsFunction.empty(photo.localPath)) {
                paths.add(photo.localPath);
            }
        }
        Intent intent = new Intent(this.mContext, PictureViewerActivity.class);
        intent.putExtra("urlArray", (String[]) paths.toArray(new String[0]));
        intent.putExtra("index", position);
        this.mContext.startActivity(intent);
    }

    public ArrayList<com.MCWorld.module.picture.b> getPhotos() {
        return this.bCz;
    }

    public void clear() {
        this.bCz.clear();
        this.bCw.notifyDataSetChanged();
        this.bCy.notifyDataSetChanged();
    }

    public int getPhotoCount() {
        return this.bCz == null ? 0 : this.bCz.size();
    }

    public void gR(String fid) {
        if (this.bCz != null) {
            for (int i = 0; i < this.bCz.size(); i++) {
                if (fid.equals(((com.MCWorld.module.picture.b) this.bCz.get(i)).fid)) {
                    this.bCz.remove(i);
                    this.bCw.notifyDataSetChanged();
                    this.bCy.notifyDataSetChanged();
                    PH();
                    return;
                }
            }
        }
    }

    private void a(String url, PaintView imageView) {
        imageView.placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).scaleType(ScaleType.CENTER_CROP).resize(this.bCB, this.bCC).radiusDimen(e.indicator_internal_padding).setUri(UtilUri.getUriOrNull(url)).borderColor(this.mContext.getResources().getColor(com.MCWorld.bbs.b.d.category_gray), UtilsScreen.convertDpToPixel(1.0f, this.mContext)).tag(this.mContext).setImageLoader(l.cb().getImageLoader());
    }

    private void a(File localFile, PaintView imageView) {
        imageView.placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).scaleType(ScaleType.CENTER_CROP).resize(this.bCB, this.bCC).radiusDimen(e.indicator_internal_padding).setUri(UtilUri.getLocalFileUriOrNull(localFile)).borderColor(this.mContext.getResources().getColor(com.MCWorld.bbs.b.d.category_gray), UtilsScreen.convertDpToPixel(1.0f, this.mContext)).tag(this.mContext).setImageLoader(l.cb().getImageLoader());
    }

    protected Parcelable onSaveInstanceState() {
        e ss = new e(super.onSaveInstanceState());
        ss.photo = this.bCz;
        ss.showText = this.bCI;
        ss.enableDel = this.bCE;
        ss.enableAdd = this.bCD;
        ss.albumsColumn = this.bCM;
        ss.inGridMode = this.bCL;
        ss.gridRatio = this.bCN;
        return ss;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        e ss = (e) state;
        super.onRestoreInstanceState(ss.getSuperState());
        e(ss.photo, true);
        this.bCI = ss.showText;
        this.bCD = ss.enableAdd;
        this.bCE = ss.enableDel;
        this.bCM = ss.albumsColumn;
        this.bCL = ss.inGridMode;
        this.bCN = ss.gridRatio;
    }

    public void dp(boolean val) {
        this.bCE = val;
        if (!this.bCL) {
            this.bCw.notifyDataSetChanged();
        }
    }

    public void setEnableAdd(boolean enableAdd) {
        this.bCD = enableAdd;
        if (this.bCL) {
            this.bCy.notifyDataSetChanged();
        } else {
            this.bCw.notifyDataSetChanged();
        }
    }

    public void setShowText(boolean show) {
        this.bCI = show;
        if (show) {
            this.bCu.setVisibility(0);
        } else {
            this.bCu.setVisibility(8);
        }
    }

    public void setMaxSelection(int maxSelection) {
        this.bCA = maxSelection;
    }
}
