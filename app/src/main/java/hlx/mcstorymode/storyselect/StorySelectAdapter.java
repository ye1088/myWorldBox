package hlx.mcstorymode.storyselect;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import hlx.mcstorymode.e;
import java.util.List;

public class StorySelectAdapter extends BaseAdapter {
    private List<a> aab;
    private int bXv = 0;
    private a bXw;
    private Activity mContext;

    public interface a {
        void TP();
    }

    public class b {
        public LinearLayout bXA;
        public ImageView bXB;
        public TextView bXC;
        public ImageView bXD;
        public ImageView bXE;
        public ImageView bXF;
        public ImageView bXG;
        public TextView bXH;
        final /* synthetic */ StorySelectAdapter bXI;
        public LinearLayout bXy;
        public ImageView bXz;

        public b(StorySelectAdapter this$0) {
            this.bXI = this$0;
        }
    }

    public StorySelectAdapter(Activity context, a cb) {
        this.mContext = context;
        this.bXw = cb;
    }

    public void setData(List<a> data) {
        this.aab = data;
    }

    public void ni(int showMode) {
        this.bXv = showMode;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.itm_story_seleted, null);
            holder = new b(this);
            holder.bXy = (LinearLayout) convertView.findViewById(R.id.llyStoryCate);
            holder.bXz = (ImageView) convertView.findViewById(R.id.ivStoryCateName);
            holder.bXD = (ImageView) convertView.findViewById(R.id.ivStoryNameDecorateLeft);
            holder.bXA = (LinearLayout) convertView.findViewById(R.id.llyStoryChapter);
            holder.bXB = (ImageView) convertView.findViewById(R.id.ivStoryNameDecorateRight);
            holder.bXH = (TextView) convertView.findViewById(R.id.tvStoryChapterNoRes);
            holder.bXC = (TextView) convertView.findViewById(R.id.tvStoryChapterName);
            holder.bXE = (ImageView) convertView.findViewById(R.id.ivStoryChapterUpdateFlag);
            holder.bXF = (ImageView) convertView.findViewById(R.id.ivStoryChapterDel);
            holder.bXG = (ImageView) convertView.findViewById(R.id.ivStoryChapterBeOverFlag);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(holder, (a) this.aab.get(position));
        return convertView;
    }

    public void a(b viewHolder, a item) {
        Drawable StoryCateDrawable;
        Drawable StoryNameDecorateDrawable;
        int i = 0;
        switch (item.bXe) {
            case 1:
                StoryCateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_official_story);
                StoryNameDecorateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_blue_diamond);
                break;
            case 2:
                StoryCateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_player_original);
                StoryNameDecorateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_green_diamond);
                break;
            case 3:
                StoryCateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_mc_story);
                StoryNameDecorateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_red_diamond);
                break;
            default:
                StoryCateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_mc_story);
                StoryNameDecorateDrawable = this.mContext.getResources().getDrawable(R.mipmap.ico_story_cate_red_diamond);
                break;
        }
        if (item.bXf) {
            viewHolder.bXy.setVisibility(0);
            viewHolder.bXz.setImageDrawable(StoryCateDrawable);
            viewHolder.bXA.setVisibility(8);
            return;
        }
        int i2;
        viewHolder.bXA.setVisibility(0);
        viewHolder.bXy.setVisibility(8);
        viewHolder.bXC.setText(e.nd(item.bXg));
        viewHolder.bXC.setTextColor(item.bXi ? -1 : -13355980);
        TextView textView = viewHolder.bXH;
        if (item.bXi) {
            i2 = 8;
        } else {
            i2 = 0;
        }
        textView.setVisibility(i2);
        viewHolder.bXD.setImageDrawable(StoryNameDecorateDrawable);
        viewHolder.bXB.setImageDrawable(StoryNameDecorateDrawable);
        ImageView imageView = viewHolder.bXE;
        if (item.bXh) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        imageView = viewHolder.bXF;
        if (this.bXv == 1 && item.bXi) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        ImageView imageView2 = viewHolder.bXG;
        if (!item.bXj) {
            i = 8;
        }
        imageView2.setVisibility(i);
    }

    public void update() {
        notifyDataSetChanged();
    }
}
