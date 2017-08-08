package com.MCWorld.widget.photowall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.ae;
import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.o;
import com.MCWorld.widget.listview.GridViewNotScroll;
import com.MCWorld.widget.picviewer.PictureViewerActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfilePhotoWall extends GridViewNotScroll {
    public static final long bCo = -1;
    private Set<String> bCT;
    private List<b> bCU;
    private a bCV;
    private ArrayAdapter<b> bCm;
    private int bCn = 1;
    private Context context;
    private List<b> photos;
    private boolean readOnly = false;

    public interface a {
        void Gn();
    }

    public static class b {
        private String fid;
        private long id;
        private String localPath;
        private String url;

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFid() {
            return this.fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocalPath() {
            return this.localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }
    }

    public ProfilePhotoWall(Context context) {
        super(context);
        init(context);
    }

    public ProfilePhotoWall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.photos = new ArrayList();
        this.bCT = new HashSet();
        this.bCU = new ArrayList();
        PF();
        PE();
    }

    public void a(b pn) {
        int lastIndex = 0;
        if (this.photos.size() > 0) {
            lastIndex = this.photos.size() - 1;
        }
        this.photos.set(lastIndex, pn);
        if (pn.fid == null) {
            this.bCU.add(pn);
        }
        PE();
    }

    public void PD() {
        this.photos.clear();
        PE();
    }

    public void PE() {
        if (this.photos.size() < this.bCn) {
            b unit;
            if (this.photos.size() == 0) {
                unit = new b();
                unit.setId(-1);
                this.photos.add(unit);
            } else if (((b) this.photos.get(this.photos.size() - 1)).getId() != -1) {
                unit = new b();
                unit.setId(-1);
                this.photos.add(unit);
            }
        }
        this.bCm.notifyDataSetChanged();
    }

    public List<b> getPhotos() {
        return this.photos;
    }

    public List<b> getAddingPhotos() {
        return this.bCU;
    }

    public Set<String> getDelFids() {
        return this.bCT;
    }

    private void E(View v) {
        List<String> paths = new ArrayList();
        for (b photo : this.photos) {
            if (photo.getUrl() != null) {
                paths.add(photo.getUrl());
            } else if (photo.getLocalPath() != null) {
                paths.add(photo.getLocalPath());
            }
        }
        Intent intent = new Intent(this.context, PictureViewerActivity.class);
        intent.putExtra("urlArray", (String[]) paths.toArray(new String[0]));
        intent.putExtra("index", (Integer) v.getTag());
        this.context.startActivity(intent);
    }

    private void F(View v) {
        List<o> menuList = new ArrayList();
        menuList.add(new o(Integer.valueOf(1), "删除该图片"));
        final n dialogMenu = new n(this.context, "选择操作");
        dialogMenu.setMenuItems(menuList);
        final int index = ((Integer) v.getTag()).intValue();
        dialogMenu.a(new com.MCWorld.widget.dialog.n.a(this) {
            final /* synthetic */ ProfilePhotoWall bCW;

            public void a(o m) {
                if (((Integer) m.getTag()).intValue() == 1) {
                    dialogMenu.dismiss();
                    b delPhoto = (b) this.bCW.photos.get(index);
                    if (!(delPhoto == null || delPhoto.getFid() == null)) {
                        this.bCW.bCT.add(delPhoto.getFid());
                    }
                    this.bCW.photos.remove(index);
                    this.bCW.PE();
                }
            }
        });
        dialogMenu.show();
    }

    private void PF() {
        this.bCm = new ArrayAdapter<b>(this, this.context, i.include_photos_item, g.tv_id, this.photos) {
            final /* synthetic */ ProfilePhotoWall bCW;

            public View getView(int position, View convertView, ViewGroup parent) {
                ViewGroup vg = (ViewGroup) super.getView(position, convertView, parent);
                b photo = (b) getItem(position);
                PaintView iv = (PaintView) vg.findViewById(g.avatar_imageview);
                iv.setTag(Integer.valueOf(position));
                if (photo.getId() == -1) {
                    iv.setImageResource(f.icon_add_image);
                    iv.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ AnonymousClass2 bCX;

                        {
                            this.bCX = this$1;
                        }

                        public void onClick(View v) {
                            if (this.bCX.bCW.bCV != null) {
                                this.bCX.bCW.bCV.Gn();
                            }
                        }
                    });
                    iv.setOnLongClickListener(null);
                } else {
                    if (photo.getUrl() != null) {
                        t.a(iv, photo.getUrl(), 0.0f);
                    } else if (photo.getLocalPath() != null) {
                        File imgFile = new File(photo.getLocalPath());
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            Bitmap newBitMap = Bitmap.createScaledBitmap(myBitmap, 160, 160, true);
                            myBitmap.recycle();
                            Bitmap roundBitmap = ae.getRoundedCornerBitmap(newBitMap, 5.0f);
                            newBitMap.recycle();
                            iv.setImageBitmap(roundBitmap);
                        }
                    }
                    iv.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ AnonymousClass2 bCX;

                        {
                            this.bCX = this$1;
                        }

                        public void onClick(View v) {
                            this.bCX.bCW.E(v);
                        }
                    });
                    iv.setOnLongClickListener(new OnLongClickListener(this) {
                        final /* synthetic */ AnonymousClass2 bCX;

                        {
                            this.bCX = this$1;
                        }

                        public boolean onLongClick(View v) {
                            if (!this.bCX.bCW.readOnly) {
                                this.bCX.bCW.F(v);
                            }
                            return true;
                        }
                    });
                }
                return vg;
            }
        };
        setAdapter(this.bCm);
    }

    public a getAddPhotoClickListener() {
        return this.bCV;
    }

    public void setAddPhotoClickListener(a addPhotoClickListener) {
        this.bCV = addPhotoClickListener;
    }

    public int getMaxPhotoNum() {
        return this.bCn;
    }

    public void setMaxPhotoNum(int maxPhotoNum) {
        this.bCn = maxPhotoNum;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
