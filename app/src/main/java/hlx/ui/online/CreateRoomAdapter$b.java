package hlx.ui.online;

import android.view.View;
import android.widget.TextView;
import com.huluxia.framework.R;

public class CreateRoomAdapter$b {
    public TextView aUm;
    public View ccs;
    public TextView name;

    public CreateRoomAdapter$b(View rootView) {
        this.ccs = rootView;
        this.name = (TextView) rootView.findViewById(R.id.name);
        this.aUm = (TextView) rootView.findViewById(R.id.detail);
    }
}
