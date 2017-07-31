package com.huluxia.ui.bbs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.http.bbs.category.b;
import com.huluxia.l;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.List;

public class CategoryDetailActivity extends HTBaseLoadingActivity {
    private TopicCategory aJO;
    private b aJP = new b();
    private GridView aJQ;
    private a aJR;
    private long sm = 0;

    private class a extends BaseAdapter implements com.simple.colorful.b {
        private OnClickListener aFT = new 1(this);
        final /* synthetic */ CategoryDetailActivity aJS;
        private boolean aJT;
        private int aJU;
        private List<UserBaseInfo> aab;
        private Context mContext;
        private LayoutInflater mInflater;

        public a(CategoryDetailActivity categoryDetailActivity, Context context) {
            this.aJS = categoryDetailActivity;
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        public void setData(List<UserBaseInfo> data) {
            this.aab = data;
            boolean z = data != null && data.size() > 8;
            this.aJT = z;
            notifyDataSetChanged();
        }

        public int getCount() {
            if (UtilsFunction.empty(this.aab)) {
                return 0;
            }
            return this.aJT ? 8 : this.aab.size();
        }

        public Object getItem(int position) {
            if (this.aJT && position == 7) {
                return null;
            }
            return this.aab.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_category_moderator, parent, false);
                holder = new a(this);
                holder.aFi = (PaintView) convertView.findViewById(g.avatar);
                holder.aJX = (EmojiTextView) convertView.findViewById(g.nick);
                holder.aJW = convertView.findViewById(g.avatar_container);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            UserBaseInfo item = (UserBaseInfo) getItem(position);
            if (item == null) {
                holder.aFi.setImageResource(d.isDayMode() ? f.img_more_moderator : f.img_more_moderator_night);
                holder.aJX.setText(m.more);
                holder.aJW.setTag(item);
                holder.aFi.setScaleType(ScaleType.FIT_XY);
            } else {
                t.b(holder.aFi, item.avatar, 0.0f);
                holder.aJX.setText(item.nick);
                holder.aJW.setTag(item);
                holder.aFi.setScaleType(ScaleType.CENTER_CROP);
            }
            holder.aJW.setOnClickListener(this.aFT);
            LayoutParams lp = (LayoutParams) holder.aFi.getLayoutParams();
            lp.height = this.aJU;
            holder.aFi.setLayoutParams(lp);
            return convertView;
        }

        private void FP() {
            if (this.aJT) {
                this.aJT = false;
                notifyDataSetChanged();
            }
        }

        public void kr(int height) {
            this.aJU = height;
        }

        public void a(j setter) {
            setter.bi(g.avatar, c.valBrightness).bh(g.nick, 16842808);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_category_detail);
        this.sm = getIntent().getLongExtra("cat_id", 0);
        this.aJP.v(this.sm);
        this.aJP.a(this);
        this.aJP.execute();
        this.aJQ = (GridView) findViewById(g.moderator_grid);
        this.aJR = new a(this, this);
        this.aJQ.setAdapter(this.aJR);
        this.aJQ.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ CategoryDetailActivity aJS;

            {
                this.aJS = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
        EP();
    }

    protected void EX() {
        super.EX();
        this.aJP.execute();
    }

    private void EP() {
        this.aIs.setVisibility(8);
    }

    private void setTopicCategory(TopicCategory data) {
        ((PaintView) findViewById(g.icon)).setUri(UtilUri.getUriOrNull(data.getIcon())).radius(8.0f).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        ((TextView) findViewById(g.title)).setText(data.getTitle());
        ((TextView) findViewById(g.forum_name)).setText(data.getForumname());
        ((TextView) findViewById(g.description)).setText(data.getDescription());
        this.aJR.setData(data.getModerator());
        ((EmojiTextView) findViewById(g.rules)).setText(data.getRule());
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.aJR.kr(((UtilsScreen.getScreenWidth(this) - UtilsScreen.dipToPx(this, 8)) / 4) - UtilsScreen.dipToPx(this, 8));
        }
    }

    public void a(com.huluxia.http.base.d response) {
        super.a(response);
        Fy();
    }

    public void b(com.huluxia.http.base.d response) {
        super.b(response);
        FB();
    }

    public void c(com.huluxia.http.base.d response) {
        super.c(response);
        if (response.getStatus() == 1) {
            this.aJO = (TopicCategory) response.getData();
            setTopicCategory(this.aJO);
            FC();
            return;
        }
        FB();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        k setter = new j(this.aJQ);
        setter.a(this.aJR);
        builder.aY(16908290, c.backgroundDefault).be(g.icon, c.valBrightness).ba(g.title, 16842806).ba(g.forum_name, 16842808).aY(g.split_header, c.splitColor).ba(g.desc_text, c.textColorGreen).ba(g.description, 16843282).aY(g.view_divider, c.splitColorDim).aY(g.view_divider2, c.splitColorDim).aY(g.block_split_top, c.splitColor).aY(g.block_split_top2, c.splitColor).aY(g.block_split_bottom, c.splitColor).aY(g.block_split_bottom2, c.splitColor).ba(g.moderator_text, c.textColorGreen).ba(g.rule_text, c.textColorGreen).ba(g.rules, 16843282).a(setter);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aJQ.setSelector(d.r(this, c.listSelector));
    }
}
