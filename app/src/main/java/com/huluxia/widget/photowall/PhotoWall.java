package com.huluxia.widget.photowall;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.t;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.o;
import com.huluxia.widget.listview.GridViewNotScroll;
import java.util.ArrayList;
import java.util.List;

public class PhotoWall extends GridViewNotScroll {
    public static final long bCo = -1;
    private ArrayAdapter<b> bCm;
    private int bCn = 1;
    private a bCp;
    private Context context;
    private List<b> photos;
    private boolean readOnly = false;

    public interface a {
        void Gn();
    }

    public static class b implements Parcelable {
        public static final Creator<b> CREATOR = new 1();
        private String fid;
        private long id;
        private String localPath;
        private String url;

        public b(Parcel source) {
            this.id = source.readLong();
            this.fid = source.readString();
            this.url = source.readString();
            this.localPath = source.readString();
        }

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

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.fid);
            dest.writeString(this.url);
            dest.writeString(this.localPath);
        }
    }

    public PhotoWall(Context context) {
        super(context);
        init(context);
    }

    public PhotoWall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.photos = new ArrayList();
        PF();
        PE();
    }

    public void a(b pn) {
        int lastIndex = 0;
        if (this.photos.size() > 0) {
            lastIndex = this.photos.size() - 1;
        }
        this.photos.set(lastIndex, pn);
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

    private void E(View v) {
        ArrayList paths = new ArrayList();
        for (b photo : this.photos) {
            if (photo.getUrl() != null) {
                paths.add(photo.getUrl());
            } else if (photo.getLocalPath() != null) {
                paths.add(photo.getLocalPath());
            }
        }
        t.a(this.context, paths, ((Integer) v.getTag()).intValue());
    }

    private void F(View v) {
        List<o> menuList = new ArrayList();
        menuList.add(new o(Integer.valueOf(1), "删除该图片"));
        n dialogMenu = new n(this.context, "选择操作");
        dialogMenu.setMenuItems(menuList);
        dialogMenu.a(new 1(this, dialogMenu, ((Integer) v.getTag()).intValue()));
        dialogMenu.show();
    }

    private void PF() {
        this.bCm = new 2(this, this.context, i.include_photos_item, g.tv_id, this.photos);
        setAdapter(this.bCm);
    }

    public a getAddPhotoClickListener() {
        return this.bCp;
    }

    public void setAddPhotoClickListener(a addPhotoClickListener) {
        this.bCp = addPhotoClickListener;
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
