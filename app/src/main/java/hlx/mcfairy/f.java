package hlx.mcfairy;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.utils.UtilsScreen;

/* compiled from: FairyTipsDlgPop */
public class f {
    private PopupWindow ZY;
    private int bTB;
    private TextView bTK;
    private boolean bTL;
    private boolean bTM;
    private View bTN;
    private int[] bTO = new int[2];
    private int bTv;
    private Context mContext;
    private View mView;

    public f(Context context) {
        this.mContext = context;
    }

    public void a(String inputTipsText, View parent, int gravity, int xOffset, int yOffset) {
        if (parent != null) {
            boolean isShowLeft;
            boolean isShowTop;
            tS();
            this.bTK.setText(hf(inputTipsText));
            parent.getLocationOnScreen(this.bTO);
            int viewWidth = parent.getWidth();
            int viewHeigth = parent.getHeight();
            if (this.bTO[0] > this.bTv / 2) {
                isShowLeft = true;
            } else {
                isShowLeft = false;
            }
            this.bTL = isShowLeft;
            if (this.bTO[1] < this.bTB / 2) {
                isShowTop = true;
            } else {
                isShowTop = false;
            }
            this.bTM = isShowTop;
            this.bTN = parent;
            if (isShowLeft) {
                this.ZY.showAtLocation(parent, gravity, xOffset - viewWidth, (viewHeigth / 2) + yOffset);
                this.bTK.setBackgroundResource(R.drawable.bg_fairy_mess_left_box);
                return;
            }
            this.ZY.showAtLocation(parent, gravity, xOffset + viewWidth, (viewHeigth / 2) + yOffset);
            this.bTK.setBackgroundResource(R.drawable.bg_fairy_mess_right_box);
        }
    }

    private String hf(String inputText) {
        String tmp = "";
        while (inputText != null) {
            if (inputText.length() <= 10) {
                return tmp + inputText;
            }
            tmp = tmp + inputText.substring(0, 10) + '\n';
            inputText = inputText.substring(10);
        }
        return tmp;
    }

    private void tS() {
        if (this.ZY == null) {
            this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.lyt_pop_fairy_tips_dlg, null);
            this.bTK = (TextView) this.mView.findViewById(R.id.tvFairyTips);
            this.bTB = UtilsScreen.getScreenPixHeight(this.mContext);
            this.bTv = UtilsScreen.getScreenPixWidth(this.mContext);
            this.ZY = new PopupWindow(this.mView, -2, -2);
        }
        this.ZY.setFocusable(true);
        this.ZY.setOutsideTouchable(true);
        this.ZY.setBackgroundDrawable(new BitmapDrawable());
    }
}
