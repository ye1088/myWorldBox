package hlx.ui.redpacket;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.r;
import hlx.data.tongji.a;

public class SnatchRedDetailActivity extends Activity {
    private static String cfT = "<html><p><font color=\"#fcd94e\">无需root</font>即可在微信和QQ平台支持待机自动抢红包功能；</p><p>开启助手后，<font color=\"#fcd94e\">支持待机自动抢红包，</font>自动抢红包功能需要手机保持使用状态，<font color=\"#fcd94e\">建议手机屏幕处于开启状态；</font>如果设置了锁定方式，请保存屏幕常亮；</p><p>为了避免与聊天功能相冲突，开启自动抢红包功能时，<font color=\"#fcd94e\">手机需要处于非聊天状态，或者返回到桌面，</font>聊天状态不会自动抢红包；<font color=\"#fcd94e\">QQ在电脑端登陆</font>，自动抢红包功能<font color=\"#fcd94e\">失效。</font></p><p>开启助手后，请确保微信聊天设置里，<font color=\"#fcd94e\">消息免打扰功能处于关闭状态</font>；并且<font color=\"#fcd94e\">微信设置里的信息提醒，都处于开启状态。</font></p></html>";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_snatch_red_detail);
        ((TextView) findViewById(R.id.tvSnatchRedEnvelopDetail)).setText(Html.fromHtml(cfT));
        r.ck().K(a.bOs);
        findViewById(R.id.ivSnatchRedDetailBack).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SnatchRedDetailActivity cfU;

            {
                this.cfU = this$0;
            }

            public void onClick(View v) {
                this.cfU.finish();
            }
        });
    }
}
