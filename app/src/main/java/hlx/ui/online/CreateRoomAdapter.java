package hlx.ui.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.huluxia.framework.R;
import java.util.HashMap;

public class CreateRoomAdapter extends BaseAdapter {
    public static final String ccj = "选择地图";
    public static final String cck = "房间名";
    public static final String ccl = "地图类型";
    public static final String ccm = "房间人数";
    public static final String ccn = "游戏模式";
    public static final String cco = "开发范围";
    String[] beK = new String[]{ccj, cck, ccl, ccm, ccn, cco};
    HashMap<String, String> ccp = new HashMap();
    private final Context mContext;

    public /* synthetic */ Object getItem(int i) {
        return kA(i);
    }

    public CreateRoomAdapter(Context context) {
        this.mContext = context;
        Vc();
    }

    private void Vc() {
        this.ccp.put(ccj, "唯美乡村");
        this.ccp.put(cck, "二次元领域");
        this.ccp.put(ccl, "经典建筑");
        this.ccp.put(ccm, "6人");
        this.ccp.put(ccn, "生存模式");
        this.ccp.put(cco, "全部玩家");
    }

    public int getCount() {
        return this.beK.length;
    }

    public String kA(int position) {
        return this.beK[position];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        String item = kA(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_create_room, parent, false);
            holder = new b(convertView);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        holder.name.setText(item);
        holder.aUm.setText((CharSequence) this.ccp.get(item));
        holder.ccs.setOnClickListener(new a(this, item));
        return convertView;
    }
}
