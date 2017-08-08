package com.MCWorld.ui.bbs;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.l;
import com.MCWorld.t;
import com.MCWorld.utils.at;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ResTopicDetailTitle$a extends BaseAdapter {
    private OnTouchListener QP = new OnTouchListener(this) {
        final /* synthetic */ ResTopicDetailTitle$a aOI;

        {
            this.aOI = this$0;
        }

        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView) v;
            int pos = ((Integer) v.getTag()).intValue();
            switch (event.getAction() & 255) {
                case 0:
                    if (view.getDrawable() != null) {
                        view.getDrawable().setColorFilter(1996488704, Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    break;
                case 1:
                    t.a(this.aOI.mContext, this.aOI.aOG, this.aOI.aOF, pos, this.aOI.mOrientation);
                    break;
                case 3:
                    break;
            }
            if (view.getDrawable() != null) {
                view.getDrawable().clearColorFilter();
                view.invalidate();
            }
            return true;
        }
    };
    private ArrayList<String> aOE = new ArrayList();
    private ArrayList<String> aOF = new ArrayList();
    private ArrayList<String> aOG = new ArrayList();
    private String aOH;
    private Context mContext;
    private int mOrientation;

    private class a {
        final /* synthetic */ ResTopicDetailTitle$a aOI;
        PaintView aOJ;

        private a(ResTopicDetailTitle$a resTopicDetailTitle$a) {
            this.aOI = resTopicDetailTitle$a;
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kA(i);
    }

    public ResTopicDetailTitle$a(Context context) {
        this.mContext = context;
    }

    public void a(List<String> photos, int imageResourceDirection, String imageParams) {
        this.aOE.clear();
        this.mOrientation = imageResourceDirection;
        this.aOH = imageParams;
        if (!UtilsFunction.empty(photos)) {
            this.aOE.addAll(photos);
            GP();
        }
        notifyDataSetChanged();
    }

    private void GP() {
        if (!UtilsFunction.empty(this.aOE)) {
            Iterator it = this.aOE.iterator();
            while (it.hasNext()) {
                String imageUrl;
                String imageUrlWithSuffix;
                String url = (String) it.next();
                if (this.mOrientation == 1) {
                    imageUrl = String.format("%s_180x0.jpeg", new Object[]{url});
                    imageUrlWithSuffix = url;
                } else {
                    imageUrl = String.format("%s_360x0.jpeg", new Object[]{url});
                    imageUrlWithSuffix = url + (UtilsFunction.empty(this.aOH) ? "" : this.aOH);
                }
                this.aOG.add(imageUrlWithSuffix);
                this.aOF.add(imageUrl);
            }
        }
    }

    public int getCount() {
        return this.aOF.size();
    }

    public String kA(int position) {
        return (String) this.aOF.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(i.item_detail_photo, null);
            holder = new a();
            holder.aOJ = (PaintView) convertView.findViewById(g.photo);
            LayoutParams llp = (LayoutParams) holder.aOJ.getLayoutParams();
            llp.width = at.dipToPx(this.mContext, 150);
            llp.height = at.dipToPx(this.mContext, 100);
            holder.aOJ.setLayoutParams(llp);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.aOJ.setUri(UtilUri.getUriOrNull(kA(position))).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        holder.aOJ.setTag(Integer.valueOf(position));
        holder.aOJ.setOnTouchListener(this.QP);
        return convertView;
    }
}
