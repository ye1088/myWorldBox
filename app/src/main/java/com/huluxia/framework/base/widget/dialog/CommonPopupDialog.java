package com.huluxia.framework.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.R$style;
import java.util.List;

public class CommonPopupDialog extends Dialog implements OnClickListener {
    private static final int BUTTON_ITEM_ID = 135798642;
    private TextView mCancelBtn;
    private ViewGroup mContentView;
    private TextView mMessageTv;
    private ViewGroup mRootView;
    private View v2;

    public CommonPopupDialog(Context context, String title, List<ButtonItem> buttons, String cancelBtnText) {
        this(context, title, (List) buttons, new ButtonItem(cancelBtnText, null));
    }

    public CommonPopupDialog(Context context, String title, List<ButtonItem> buttons, final ButtonItem bottomButton) {
        super(context, R$style.Dialog_Fullscreen);
        this.mRootView = (ViewGroup) View.inflate(getContext(), R.layout.layout_common_popup_dialog, null);
        this.mContentView = (ViewGroup) this.mRootView.findViewById(R.id.ll_more);
        this.mMessageTv = (TextView) this.mRootView.findViewById(R.id.tv_message);
        this.mCancelBtn = (TextView) this.mRootView.findViewById(R.id.btn_cancel);
        this.mCancelBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!(bottomButton == null || bottomButton.mClickListener == null)) {
                    bottomButton.mClickListener.onClick();
                }
                CommonPopupDialog.this.dismiss();
            }
        });
        setContentView(this.mRootView);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.height = -2;
        window.setGravity(80);
        window.setAttributes(params);
        window.setWindowAnimations(R$style.DialogAnimation);
        if (buttons != null && buttons.size() > 0) {
            if (!(title == null || title.isEmpty())) {
                setMessage(title);
            }
            this.mContentView.setVisibility(0);
            for (int i = 0; i < buttons.size(); i++) {
                if (i != 0) {
                    addDivider();
                } else if (!(title == null || title.isEmpty())) {
                    addDivider();
                }
                addItem((ButtonItem) buttons.get(i));
            }
        }
        if (bottomButton != null && bottomButton.mText != null && !bottomButton.mText.isEmpty()) {
            this.mCancelBtn.setVisibility(0);
            this.mCancelBtn.setText(bottomButton.mText);
        }
    }

    public CommonPopupDialog setMessage(String text) {
        this.mMessageTv.setVisibility(0);
        this.mMessageTv.setText(text);
        return this;
    }

    public void addItem(final ButtonItem buttonItem) {
        TextView item = (TextView) LayoutInflater.from(getContext()).inflate(buttonItem.resourceID, this.mContentView, false);
        item.setText(buttonItem.mText);
        item.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                buttonItem.mClickListener.onClick();
                CommonPopupDialog.this.dismiss();
            }
        });
        item.setId(BUTTON_ITEM_ID + this.mContentView.getChildCount());
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
