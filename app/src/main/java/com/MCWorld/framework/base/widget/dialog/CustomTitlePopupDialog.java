package com.MCWorld.framework.base.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.List;

public class CustomTitlePopupDialog extends AlertDialog implements OnClickListener {
    private TextView mCancelBtn;
    private ViewGroup mContentView = ((ViewGroup) this.mRootView.findViewById(R.id.ll_more));
    private ViewGroup mRootView = ((ViewGroup) View.inflate(getContext(), R.layout.layout_custom_title_popup_dialog, null));
    private View v2;

    public interface TitleBinder {
        void bind(View view);
    }

    public CustomTitlePopupDialog(Context context, int title, TitleBinder binder, List<ButtonItem> buttons) {
        super(context);
        show();
        View titleView = LayoutInflater.from(context).inflate(title, null, false);
        this.mContentView.addView(titleView);
        binder.bind(titleView);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.height = -2;
        window.setGravity(17);
        window.setAttributes(params);
        window.setContentView(this.mRootView);
        if (buttons != null && buttons.size() > 0) {
            this.mContentView.setVisibility(0);
            for (ButtonItem b : buttons) {
                addDivider();
                addItem(b);
            }
        }
    }

    public CustomTitlePopupDialog setMessage(String text) {
        return this;
    }

    public void addItem(final ButtonItem buttonItem) {
        TextView item = (TextView) LayoutInflater.from(getContext()).inflate(buttonItem.resourceID, this.mContentView, false);
        item.setText(buttonItem.mText);
        if (buttonItem.mButtonType == 1) {
            item.setTextColor(getContext().getResources().getColorStateList(R.color.color_gray_black));
        }
        if (buttonItem.mClickListener != null) {
            item.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    buttonItem.mClickListener.onClick();
                    CustomTitlePopupDialog.this.dismiss();
                }
            });
        } else {
            item.setTextColor(getContext().getResources().getColor(R.color.common_color_1));
            item.setGravity(3);
            item.setText("　　" + buttonItem.mText);
        }
        this.mContentView.addView(item, this.mContentView.getChildCount());
    }

    public void addDivider() {
        View v2 = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_popup_dialog_divider, this.mContentView, false);
        v2.setVisibility(0);
        this.mContentView.addView(v2, this.mContentView.getChildCount());
    }

    public void onClick(View v) {
    }
}
