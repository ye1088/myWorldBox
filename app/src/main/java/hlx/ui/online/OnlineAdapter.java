package hlx.ui.online;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsString;
import com.huluxia.k;
import com.huluxia.l;
import com.huluxia.mconline.gameloc.http.g;
import com.huluxia.module.n;
import com.huluxia.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class OnlineAdapter extends BaseAdapter implements b {
    private List<g> akR = new ArrayList();
    private Map<Long, String> cct = null;
    private Queue<g> ccu = new LinkedList();
    private final int ccv = 30;
    private OnClickListener ccw = new OnClickListener(this) {
        final /* synthetic */ OnlineAdapter ccy;

        {
            this.ccy = this$0;
        }

        public void onClick(View view) {
            g data = (g) view.getTag();
            HLog.verbose("TAG", "DTPrint onItemClick " + data.online_ip + ":" + data.online_port, new Object[0]);
            HLog.verbose("TAG", "DTPrint onItemClick " + data.server_ip + ":" + data.server_port, new Object[0]);
            EventNotifyCenter.notifyEvent(n.class, n.axj, new Object[]{Boolean.valueOf(true)});
            com.huluxia.mconline.gamerole.a.Bf().a(this.ccy.mContext, data.online_ip, data.online_port, data.server_ip, data.server_port, data.room_no);
        }
    };
    private OnClickListener ccx = new OnClickListener(this) {
        final /* synthetic */ OnlineAdapter ccy;

        {
            this.ccy = this$0;
        }

        public void onClick(View view) {
            k.a(this.ccy.mContext, ((Long) view.getTag()).longValue(), null);
        }
    };
    private final Activity mContext;

    public static class a {
        public PaintView aFi;
        public TextView aWl;
        public TextView bZa;
        public View ccA;
        public TextView ccB;
        public TextView ccC;
        public View ccD;
        public TextView ccE;
        public TextView ccF;
        public TextView ccG;
        public View ccH;
        public TextView ccI;
        public ImageView ccJ;
        public RelativeLayout ccK;
        public View ccs;
        public View ccz;

        public a(View rootView) {
            this.ccs = rootView;
            this.ccz = rootView.findViewById(R.id.rly_root_view);
            this.aFi = (PaintView) rootView.findViewById(R.id.avatar);
            this.ccB = (TextView) rootView.findViewById(R.id.room_number);
            this.ccI = (TextView) rootView.findViewById(R.id.studio);
            this.ccC = (TextView) rootView.findViewById(R.id.introduce);
            this.ccD = rootView.findViewById(R.id.vertical_dividing_line);
            this.ccE = (TextView) rootView.findViewById(R.id.type);
            this.bZa = (TextView) rootView.findViewById(R.id.version);
            this.ccH = rootView.findViewById(R.id.vertical_dividing_line2);
            this.aWl = (TextView) rootView.findViewById(R.id.size);
            this.ccJ = (ImageView) rootView.findViewById(R.id.lock_room);
            this.ccG = (TextView) rootView.findViewById(R.id.number);
            this.ccK = (RelativeLayout) rootView.findViewById(R.id.rly_studio);
            this.ccA = rootView.findViewById(R.id.dividing_block);
            this.ccF = (TextView) rootView.findViewById(R.id.recent_join);
        }
    }

    public OnlineAdapter(Activity activity) {
        this.mContext = activity;
    }

    public int getCount() {
        return this.akR.size();
    }

    public Object getItem(int position) {
        return this.akR.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        String cateName;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_online, parent, false);
            holder = new a(convertView);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        g data = (g) getItem(position);
        holder.ccA.setVisibility(8);
        Drawable drawable = y.c(this.mContext, 60, d.getColor(this.mContext, R.attr.textDelete));
        Drawable studioDrawable = y.c(this.mContext, 40, d.getColor(this.mContext, R.attr.textDelete));
        Drawable rlyStudioDrawable = y.c(this.mContext, 40, d.getColor(this.mContext, R.attr.label));
        holder.ccG.setBackgroundDrawable(drawable);
        holder.ccG.setTextColor(d.getColor(this.mContext, R.attr.offline));
        holder.ccI.setBackgroundDrawable(studioDrawable);
        holder.ccK.setBackgroundDrawable(rlyStudioDrawable);
        holder.ccF.setVisibility(8);
        holder.ccJ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineAdapter ccy;

            {
                this.ccy = this$0;
            }

            public void onClick(View v) {
            }
        });
        holder.aFi.setUri(Uri.parse(data.admin_avatar)).oval().setImageLoader(l.cb().getImageLoader());
        holder.aFi.setTag(Long.valueOf(data.admin_id));
        holder.aFi.setOnClickListener(this.ccx);
        if (data.studio_id == 0 || UtilsFunction.empty(data.studio_name)) {
            holder.ccK.setVisibility(8);
        } else {
            holder.ccK.setVisibility(0);
            holder.ccI.setText(data.studio_name);
        }
        holder.ccB.setText(String.valueOf(data.room_align));
        holder.ccC.setText(data.server_name);
        if (this.cct == null || this.cct.get(Long.valueOf((long) data.game_type)) == null) {
            cateName = "未知分类";
        } else {
            cateName = (String) this.cct.get(Long.valueOf((long) data.game_type));
        }
        holder.ccE.setText(cateName);
        holder.bZa.setText("V" + data.game_version);
        holder.aWl.setText(UtilsString.humanReadableByteCount(data.game_size, false));
        int cur_player = data.server_host + data.server_player;
        if (cur_player <= 0) {
            holder.ccG.setText("离线");
        } else {
            holder.ccG.setText(cur_player + "/" + data.server_maxplayer);
        }
        holder.ccz.setTag(data);
        holder.ccz.setOnClickListener(this.ccw);
        b(data);
        return convertView;
    }

    public void c(List<g> infos, boolean clear) {
        if (clear) {
            this.akR.clear();
        }
        this.akR.addAll(infos);
        notifyDataSetChanged();
    }

    public void V(List<g> infos) {
        HLog.verbose("TAG", "refreshData  " + infos.size(), new Object[0]);
        Map<String, g> infoMap = new HashMap();
        for (g info : infos) {
            infoMap.put(info.online_ip + "_" + info.online_port + "_" + info.room_no, info);
        }
        for (g info2 : this.akR) {
            String key = info2.online_ip + "_" + info2.online_port + "_" + info2.room_no;
            if (infoMap.containsKey(key)) {
                g.setRoomInfo(info2, (g) infoMap.get(key));
                HLog.verbose("TAG", "refreshData " + key + ":", new Object[0]);
            }
        }
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bh(R.id.recent_join, R.attr.progressText).bf(R.id.recent_join, R.attr.recent_join).bf(R.id.join_dividing_line, R.attr.dividingLine).bf(R.id.rly_studio, R.attr.label).bh(R.id.room_number, R.attr.room_number).bh(R.id.introduce, R.attr.tabText).bh(R.id.studio, R.attr.label).bf(R.id.vertical_dividing_line, R.attr.dividingLine).bf(R.id.vertical_dividing_line2, R.attr.dividingLine).bh(R.id.type, R.attr.progressText).bh(R.id.version, R.attr.progressText).bh(R.id.size, R.attr.progressText).bf(R.id.dividing_block, R.attr.home_interval_bg).bg(R.id.rly_root_view, R.attr.itemBackground);
    }

    public Queue<g> Vd() {
        return this.ccu;
    }

    private void b(g info) {
        if (this.ccu.size() > 30) {
            this.ccu.poll();
        }
        this.ccu.offer(info);
    }

    public void e(Map<Long, String> cateMap) {
        this.cct = cateMap;
    }
}
