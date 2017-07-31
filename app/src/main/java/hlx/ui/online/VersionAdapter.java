package hlx.ui.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.t;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import org.apache.tools.ant.util.JavaEnvUtils;

public class VersionAdapter extends BaseAdapter implements b {
    String[] cdi = new String[]{"全部版本", "我的版本", JavaEnvUtils.JAVA_1_5, "1.4", "1.3", "1.2"};
    private final Context mContext;

    private class a {
        TextView bZa;
        View ccs;
        final /* synthetic */ VersionAdapter cdk;

        private a(VersionAdapter versionAdapter) {
            this.cdk = versionAdapter;
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kA(i);
    }

    public VersionAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return this.cdi.length;
    }

    public String kA(int position) {
        return this.cdi[position];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        final String version = kA(position);
        if (convertView == null) {
            holder = new a();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_version, parent, false);
            holder.ccs = convertView.findViewById(R.id.root_view);
            holder.bZa = (TextView) convertView.findViewById(R.id.version);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.bZa.setText(version);
        holder.ccs.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ VersionAdapter cdk;

            public void onClick(View v) {
                t.n(this.cdk.mContext, version);
            }
        });
        return convertView;
    }

    public void a(j setter) {
        setter.bf(R.id.root_view, R.attr.label).bf(R.id.divideline, R.attr.dividingLine);
    }
}
