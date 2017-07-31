package com.huluxia.ui.profile;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.j;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.data.profile.b;
import com.huluxia.t;
import com.huluxia.utils.aw;
import java.util.List;

public class ProfileItemAdapter extends ArrayAdapter<b> {
    private ProfileInfo aKG;
    private OnClickListener aRN = new OnClickListener(this) {
        final /* synthetic */ ProfileItemAdapter bgG;

        {
            this.bgG = this$0;
        }

        public void onClick(View v) {
            if (!j.ep().ey()) {
                t.an(this.bgG.context);
            } else if (this.bgG.aKG != null) {
                switch (Integer.valueOf(String.valueOf(v.getTag())).intValue()) {
                    case 0:
                        t.f(this.bgG.context, this.bgG.aKG.getUserID());
                        return;
                    case 1:
                        t.g(this.bgG.context, this.bgG.aKG.getUserID());
                        return;
                    case 2:
                        boolean isOther = j.ep().getUserid() != this.bgG.aKG.getUserID();
                        if (isOther && this.bgG.aKG.getPhotos().isEmpty()) {
                            t.l(this.bgG.context, "该用户还没有上传照片");
                            return;
                        } else if (isOther) {
                            t.a(this.bgG.context, this.bgG.aKG, isOther);
                            return;
                        } else {
                            t.as(this.bgG.context);
                            return;
                        }
                    case 3:
                        t.h(this.bgG.context, this.bgG.aKG.getUserID());
                        return;
                    case 4:
                        t.i(this.bgG.context, this.bgG.aKG.getUserID());
                        return;
                    case 5:
                        t.j(this.bgG.context, this.bgG.aKG.getUserID());
                        return;
                    case 6:
                        t.a(this.bgG.context, this.bgG.aKG, ProfileScoreActivity.bhd);
                        return;
                    case 7:
                        t.a(this.bgG.context, this.bgG.aKG, ProfileScoreActivity.bhe);
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private boolean bgD;
    private Context context;

    public ProfileItemAdapter(Context context, List<b> objects, ProfileInfo info, boolean isOther) {
        super(context, i.item_profile, g.title, objects);
        this.context = context;
        this.aKG = info;
        this.bgD = isOther;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        b data = (b) getItem(position);
        ImageView logo = (ImageView) v.findViewById(g.icon);
        TextView count = (TextView) v.findViewById(g.count);
        ((TextView) v.findViewById(g.title)).setText(data.getName());
        v.setTag(Integer.valueOf(data.getIndex()));
        v.setOnClickListener(this.aRN);
        if (data.getIndex() == 0) {
            logo.setImageResource(f.ic_space_topic);
        } else if (data.getIndex() == 1) {
            logo.setImageResource(f.ic_space_comment);
        } else if (data.getIndex() == 2) {
            if (this.bgD) {
                logo.setImageResource(f.ic_space_album);
            } else {
                logo.setImageResource(f.ic_space_history);
            }
        } else if (data.getIndex() == 3) {
            logo.setImageResource(f.ic_space_favor);
        } else if (data.getIndex() == 4) {
            logo.setImageResource(f.ic_space_idol);
            count.setText(aw.W(String.valueOf(this.aKG == null ? 0 : this.aKG.getFollowingCount()), 5));
            count.setVisibility(0);
        } else if (data.getIndex() == 5) {
            logo.setImageResource(f.ic_space_fans);
            count.setText(aw.W(String.valueOf(this.aKG == null ? 0 : this.aKG.getFollowerCount()), 5));
            count.setVisibility(0);
        } else if (data.getIndex() == 6) {
            logo.setImageResource(f.ic_space_jifen2);
            count.setText(aw.W(String.valueOf(this.aKG == null ? 0 : this.aKG.getIntegral()), 5));
            count.setVisibility(0);
        } else if (data.getIndex() == 7) {
            logo.setImageResource(f.ic_space_hulu2);
            count.setText(aw.W(String.valueOf(this.aKG == null ? 0 : this.aKG.getCredits()), 5));
            count.setVisibility(0);
        }
        return v;
    }
}
