package hlx.ui.online;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import com.huluxia.framework.R;
import com.huluxia.ui.base.HTBaseActivity;

public class CreateRoomActivity extends HTBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_create_room);
        EP();
        initView();
    }

    private void initView() {
        ((ScrollListView) findViewById(R.id.list_view)).setAdapter(new CreateRoomAdapter(this));
        CheckBox hasPassword = (CheckBox) findViewById(R.id.password);
    }

    private void EP() {
        this.aIs.setVisibility(8);
        ej("创建房间");
        this.aIT.setVisibility(0);
        this.aIT.setText("完成");
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CreateRoomActivity cci;

            {
                this.cci = this$0;
            }

            public void onClick(View v) {
                this.cci.finish();
            }
        });
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
