package hlx.ui.personalstudio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.j;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.t;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import java.util.List;

class PersonalStudioHeaderLayout$a extends BaseAdapter implements b {
    private OnClickListener aFT = new OnClickListener(this) {
        final /* synthetic */ PersonalStudioHeaderLayout$a cdT;

        {
            this.cdT = this$1;
        }

        public void onClick(View v) {
            com.huluxia.data.profile.e.a info = (com.huluxia.data.profile.e.a) v.getTag();
            if (info != null) {
                t.a(this.cdT.mContext, info.user.userID, info.user);
            }
        }
    };
    private int aJU = 0;
    private List<com.huluxia.data.profile.e.a> aab;
    final /* synthetic */ PersonalStudioHeaderLayout cdR;
    private int cdS = 5;
    private Context mContext;
    private LayoutInflater mInflater;

    class a {
        PaintView aFi;
        View aJW;
        EmojiTextView aJX;
        final /* synthetic */ PersonalStudioHeaderLayout$a cdT;
        TextView cdU;

        a(PersonalStudioHeaderLayout$a this$1) {
            this.cdT = this$1;
        }
    }

    public PersonalStudioHeaderLayout$a(PersonalStudioHeaderLayout personalStudioHeaderLayout, Context context) {
        this.cdR = personalStudioHeaderLayout;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.aJU = ((UtilsScreen.getScreenWidth(this.mContext) - UtilsScreen.dipToPx(this.mContext, 16)) / 5) - UtilsScreen.dipToPx(this.mContext, 16);
    }

    public void setData(List<com.huluxia.data.profile.e.a> data) {
        this.aab = data;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (UtilsFunction.empty(this.aab)) {
            return 0;
        }
        return this.aab.size() > this.cdS ? this.cdS : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (com.huluxia.data.profile.e.a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_person_studio_member, parent, false);
            holder = new a(this);
            holder.aFi = (PaintView) convertView.findViewById(R.id.iv_avatar);
            holder.aJX = (EmojiTextView) convertView.findViewById(R.id.tv_nick);
            holder.cdU = (TextView) convertView.findViewById(R.id.tv_role);
            holder.aJW = convertView.findViewById(R.id.fl_avatar);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        com.huluxia.data.profile.e.a studio = (com.huluxia.data.profile.e.a) getItem(position);
        if (studio != null) {
            UserBaseInfo item = studio.user;
            if (item.userID == j.ep().getUserid()) {
                holder.cdU.setVisibility(0);
                holder.cdU.setText("我");
                holder.cdU.setTextColor(d.getColor(this.mContext, R.attr.studio_me_in_member_list));
            } else {
                switch (studio.isStudio) {
                    case 1:
                        holder.cdU.setVisibility(0);
                        holder.cdU.setText("室长");
                        break;
                    case 2:
                        holder.cdU.setVisibility(0);
                        holder.cdU.setText("副室长");
                        break;
                    default:
                        holder.cdU.setVisibility(4);
                        break;
                }
                holder.cdU.setTextColor(d.getColor(this.mContext, 16843282));
            }
            if (item != null) {
                t.b(holder.aFi, item.avatar, (float) t.dipToPx(this.mContext, 7));
                holder.aJX.setText(item.nick);
                holder.aJW.setTag(studio);
                holder.aFi.setScaleType(ScaleType.CENTER_CROP);
            }
            holder.aJW.setOnClickListener(this.aFT);
        }
        LayoutParams lp = (LayoutParams) holder.aFi.getLayoutParams();
        lp.height = this.aJU;
        holder.aFi.setLayoutParams(lp);
        return convertView;
    }

    public void a(com.simple.colorful.setter.j setter) {
        setter.bh(R.id.nick, 16842808).bh(R.id.tv_role, 16843282).bh(R.id.tv_role, R.attr.studio_me_in_member_list);
    }
}
