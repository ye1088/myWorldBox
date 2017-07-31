package com.huluxia.mconline.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mconline.gameloc.http.g;
import com.huluxia.mconline.gamerole.a;
import java.util.ArrayList;
import java.util.List;

public class RoomItemAdapter extends BaseAdapter {
    private List<g> akQ = new ArrayList();
    private List<Object> akR = new ArrayList();
    private Context context;
    private LayoutInflater mInflater = null;

    public RoomItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
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

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.mconline_listitem_room, null);
        }
        final g data = (g) getItem(position);
        ((TextView) convertView.findViewById(R.id.tv_roomno)).setText("房间号:" + a(data));
        ((TextView) convertView.findViewById(R.id.tv_name)).setText(data.server_name);
        ((TextView) convertView.findViewById(R.id.tv_game_type)).setText("游戏类型:" + data.game_type);
        ((TextView) convertView.findViewById(R.id.tv_game_mode)).setText("游戏模式:" + data.game_mode);
        ((TextView) convertView.findViewById(R.id.tv_game_ver)).setText("游戏版本:" + data.game_version);
        ((TextView) convertView.findViewById(R.id.tv_ip)).setText(data.online_ip);
        ((TextView) convertView.findViewById(R.id.tv_cur)).setText(data.server_player + "/" + data.server_maxplayer);
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RoomItemAdapter akT;

            public void onClick(View view) {
                HLog.verbose("TAG", "DTPrint onItemClick " + data.online_ip + ":" + data.online_port, new Object[0]);
                HLog.verbose("TAG", "DTPrint onItemClick " + data.server_ip + ":" + data.server_port, new Object[0]);
                a.Bf().a(this.akT.context, data.online_ip, data.online_port, data.server_ip, data.server_port, data.room_no);
            }
        });
        return convertView;
    }

    public void c(List<g> infos, boolean clear) {
        if (clear) {
            this.akQ.clear();
        }
        this.akQ.addAll(infos);
        this.akR.clear();
        this.akR.addAll(this.akQ);
        notifyDataSetChanged();
    }

    private String a(g info) {
        if (info == null) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        if (info.online_ip != null) {
            sb.append(info.online_ip.replace(".", ""));
        }
        sb.append(info.online_port).append(info.room_no);
        return sb.toString();
    }
}
