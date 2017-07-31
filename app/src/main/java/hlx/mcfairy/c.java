package hlx.mcfairy;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import com.huluxia.framework.R;
import com.huluxia.framework.base.utils.UtilsScreen;

/* compiled from: FairyMenuPop */
public class c {
    private PopupWindow ZY;
    private a bTA;
    private int bTB;
    private int bTv;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ c bTC;

        {
            this.bTC = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvCloseFairy:
                    if (this.bTC.bTA != null) {
                        this.bTC.bTA.SX();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private Context mContext;
    private View mView;

    /* compiled from: FairyMenuPop */
    public interface a {
        void SX();
    }

    public c(Context context, a cb) {
        this.mContext = context;
        this.bTA = cb;
    }

    public void showAtLocation(View parent, int gravity, int xOffset, int yOffset) {
        boolean isShowLeft;
        tS();
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int viewWidth = parent.getWidth();
        int viewHeigth = parent.getHeight();
        if (location[0] > this.bTv / 2) {
            isShowLeft = true;
        } else {
            isShowLeft = false;
        }
        if (location[1] < this.bTB / 2) {
            boolean isShowTop = true;
        } else {
            int i = 0;
        }
        if (isShowLeft) {
            this.ZY.showAtLocation(parent, gravity, xOffset - viewWidth, ((viewHeigth / 2) + yOffset) + (this.mView.getHeight() / 2));
        } else {
            this.ZY.showAtLocation(parent, gravity, xOffset + viewWidth, ((viewHeigth / 2) + yOffset) + (this.mView.getHeight() / 2));
        }
    }

    public void showAsDropDown(View parent, int xOffset, int yOffset) {
        tS();
        this.ZY.showAsDropDown(parent, xOffset, yOffset);
    }

    private void tS() {
        if (this.ZY == null) {
            this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.lyt_pop_fairy_menu, null);
            this.mView.findViewById(R.id.tvCloseFairy).setOnClickListener(this.mClickListener);
            this.bTB = UtilsScreen.getScreenPixHeight(this.mContext);
            this.bTv = UtilsScreen.getScreenPixWidth(this.mContext);
            this.ZY = new PopupWindow(this.mView, -2, -2);
        }
        this.ZY.setFocusable(true);
        this.ZY.setOutsideTouchable(true);
        this.ZY.setBackgroundDrawable(new BitmapDrawable());
    }
}
