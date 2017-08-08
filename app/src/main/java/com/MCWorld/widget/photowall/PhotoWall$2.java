package com.MCWorld.widget.photowall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.t;
import com.MCWorld.widget.photowall.PhotoWall.b;
import java.io.File;
import java.util.List;

class PhotoWall$2 extends ArrayAdapter<b> {
    final /* synthetic */ PhotoWall bCq;

    PhotoWall$2(PhotoWall this$0, Context x0, int x1, int x2, List x3) {
        this.bCq = this$0;
        super(x0, x1, x2, x3);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup vg = (ViewGroup) super.getView(position, convertView, parent);
        b photo = (b) getItem(position);
        PaintView iv = (PaintView) vg.findViewById(g.avatar_imageview);
        iv.setTag(Integer.valueOf(position));
        if (photo.getId() == -1) {
            iv.setImageResource(f.icon_add_image);
            iv.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PhotoWall$2 bCr;

                {
                    this.bCr = this$1;
                }

                public void onClick(View v) {
                    if (PhotoWall.b(this.bCr.bCq) != null) {
                        PhotoWall.b(this.bCr.bCq).Gn();
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
                    Bitmap roundBitmap = UtilsImage.getRoundedCornerBitmap(newBitMap, 5.0f);
                    newBitMap.recycle();
                    iv.setImageBitmap(roundBitmap);
                }
            }
            iv.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PhotoWall$2 bCr;

                {
                    this.bCr = this$1;
                }

                public void onClick(View v) {
                    PhotoWall.a(this.bCr.bCq, v);
                }
            });
            iv.setOnLongClickListener(new OnLongClickListener(this) {
                final /* synthetic */ PhotoWall$2 bCr;

                {
                    this.bCr = this$1;
                }

                public boolean onLongClick(View v) {
                    if (!PhotoWall.c(this.bCr.bCq)) {
                        PhotoWall.b(this.bCr.bCq, v);
                    }
                    return true;
                }
            });
        }
        return vg;
    }
}
