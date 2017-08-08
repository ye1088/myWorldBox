package hlx.ui.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.l;
import com.MCWorld.r;
import com.MCWorld.t;
import com.simple.colorful.c;
import com.simple.colorful.d;
import hlx.module.news.b;
import hlx.widget.news.FlipperView;
import hlx.widget.news.FlipperView.a;
import java.util.ArrayList;
import java.util.List;

public class NewsImageTitle extends FrameLayout implements c {
    private FlipperView cbR;
    private FlipperView cbS;
    private FlipperView cbT;
    private List<PaintView> cbU;
    private List<PaintView> cbV;
    private List<PaintView> cbW;
    private b cbX;
    private a cbY = new a(this) {
        final /* synthetic */ NewsImageTitle cbZ;

        {
            this.cbZ = this$0;
        }

        public void onClick(View v) {
            hlx.module.news.a headerImage = (hlx.module.news.a) v.getTag();
            if (headerImage != null) {
                switch (headerImage.opentype) {
                    case 1:
                        if (headerImage.recommentid == 1) {
                            hlx.ui.a.ck(this.cbZ.getContext());
                            r.ck().K_umengEvent(hlx.data.tongji.a.bOu);
                            return;
                        }
                        t.a(this.cbZ.getContext(), headerImage.recommentid, false);
                        r.ck().j(hlx.data.tongji.a.bOt, String.valueOf(headerImage.recommentid));
                        return;
                    case 2:
                        t.q(this.cbZ.getContext(), headerImage.recommenturl);
                        r.ck().j(hlx.data.tongji.a.bOv, headerImage.recommenturl);
                        return;
                    case 3:
                        if (headerImage.recommenturl != null && headerImage.recommenturl.endsWith("heroslist")) {
                            hlx.ui.a.cd(this.cbZ.mContext);
                            r.ck().K_umengEvent(hlx.data.tongji.a.bOn);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private Context mContext;

    public NewsImageTitle(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public NewsImageTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context);
    }

    public NewsImageTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        addView(LayoutInflater.from(context).inflate(R.layout.view_story_image_title, null));
        this.cbR = (FlipperView) findViewById(R.id.flipper1);
        this.cbS = (FlipperView) findViewById(R.id.flipper2);
        this.cbT = (FlipperView) findViewById(R.id.flipper3);
        this.cbR.setListener(this.cbY);
        this.cbS.setListener(this.cbY);
        this.cbT.setListener(this.cbY);
        this.cbU = new ArrayList();
        this.cbU.add((PaintView) this.cbR.findViewById(R.id.flipper1_image1));
        this.cbU.add((PaintView) this.cbR.findViewById(R.id.flipper1_image2));
        this.cbV = new ArrayList();
        this.cbV.add((PaintView) this.cbS.findViewById(R.id.flipper2_image1));
        this.cbV.add((PaintView) this.cbS.findViewById(R.id.flipper2_image2));
        this.cbW = new ArrayList();
        this.cbW.add((PaintView) this.cbT.findViewById(R.id.flipper3_image1));
        this.cbW.add((PaintView) this.cbT.findViewById(R.id.flipper3_image2));
    }

    public void startAnimation() {
        if (this.cbX != null) {
            if (this.cbX.bigList != null && this.cbX.bigList.size() > 1) {
                this.cbR.startFlipping();
            }
            if (this.cbX.topList != null && this.cbX.topList.size() > 1) {
                this.cbS.startFlipping();
            }
            if (this.cbX.bottomList != null && this.cbX.bottomList.size() > 1) {
                this.cbT.startFlipping();
            }
        }
    }

    public void Gi() {
        if (this.cbX != null) {
            this.cbR.stopFlipping();
            this.cbS.stopFlipping();
            this.cbT.stopFlipping();
        }
    }

    public void setData(b data) {
        this.cbX = data;
        if (this.cbX != null) {
            f(data.bigList, this.cbU);
            f(data.topList, this.cbV);
            f(data.bottomList, this.cbW);
        }
    }

    private void f(List<hlx.module.news.a> list, List<PaintView> imgList) {
        if (!UtilsFunction.empty(list)) {
            int i = 0;
            while (i < imgList.size() && i < list.size()) {
                hlx.module.news.a headerImage = (hlx.module.news.a) list.get(i);
                if (headerImage.icon.equals("local:story")) {
                    ((PaintView) imgList.get(i)).setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_news_header_stroy));
                } else {
                    ((PaintView) imgList.get(i)).setUri(UtilUri.getUriOrNull(headerImage.icon)).borderColor(getResources().getColor(R.color.border), 1.0f).placeHolder(d.isDayMode() ? R.drawable.place_holder_normal : R.drawable.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
                }
                ((PaintView) imgList.get(i)).setTag(headerImage);
                i++;
            }
        }
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        builder.aY(R.id.story_title, R.attr.splitColorDim3).aY(R.id.split_bottom, R.attr.splitColor);
        return builder;
    }

    public void FG() {
    }
}
