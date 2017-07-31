package com.huluxia.framework.base.widget.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.ImeUtil;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.dialog.CustomTitlePopupDialog.TitleBinder;
import hlx.data.localstore.a;
import java.util.List;

public class DialogManager {
    public static final boolean GENDER_FEMALE = true;
    public static final boolean GENDER_MALE = false;
    protected Builder mBuilder;
    private boolean mCanceledOnClickBackKey = true;
    private boolean mCanceledOnClickOutside = true;
    private Context mContext;
    protected Dialog mDialog;
    private boolean mIsFemale = false;
    private int mProgressMax = 0;
    private boolean mReCreate = true;
    private String mTip;

    public interface OkCancelDialogListener {
        void onCancel();

        void onOk();
    }

    public static class StyleHolder {
        public int colorButton = 0;
        public int colorMessage = 0;
        public int colorTtle = 0;
        public int colorUnMarkedButton = 0;
        public Drawable drawableBg;
    }

    public DialogManager(Context context) {
        this.mContext = context;
        this.mBuilder = new Builder(context);
        this.mDialog = this.mBuilder.create();
    }

    public DialogManager(Context context, boolean canceledOnClickBackKey, boolean canceledOnClickOutside) {
        this.mContext = context;
        this.mBuilder = new Builder(context);
        this.mDialog = this.mBuilder.create();
        this.mCanceledOnClickBackKey = canceledOnClickBackKey;
        this.mCanceledOnClickOutside = canceledOnClickOutside;
    }

    @TargetApi(11)
    public DialogManager(Context context, int themeResId) {
        this.mContext = context;
        this.mBuilder = new Builder(context, themeResId);
        this.mDialog = this.mBuilder.create();
    }

    @TargetApi(17)
    public boolean checkActivityValid() {
        if (this.mContext == null) {
            HLog.warn(this, "Fragment " + this + " not attached to Activity", new Object[0]);
            return false;
        } else if (this.mDialog != null && this.mDialog.getWindow() == null) {
            HLog.warn(this, "window null", new Object[0]);
            return false;
        } else if (((Activity) this.mContext).isFinishing()) {
            HLog.warn(this, "activity is finishing", new Object[0]);
            return false;
        } else if (VERSION.SDK_INT < 17 || !((Activity) this.mContext).isDestroyed()) {
            return true;
        } else {
            HLog.warn(this, "activity is isDestroyed", new Object[0]);
            return false;
        }
    }

    public void dismissDialog() {
        if (this.mContext != null && this.mDialog != null && this.mDialog.getWindow() != null) {
            if (!(this.mContext instanceof Activity)) {
                this.mDialog.dismiss();
            } else if (!this.mContext.isFinishing()) {
                this.mDialog.dismiss();
            }
        }
    }

    public boolean isDialogShowing() {
        if (this.mDialog != null) {
            return this.mDialog.isShowing();
        }
        return false;
    }

    public void setCanceledOnClickBackKey(boolean cancelable) {
        this.mCanceledOnClickBackKey = cancelable;
    }

    public void setCanceledOnClickOutside(boolean cancelable) {
        this.mCanceledOnClickOutside = cancelable;
    }

    public void showOkCancellabelColorDialog(String message, String tips, String okLabel, int okLabelColor, String cancelLabel, int cancelLabelColor, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_cancel_label_dialog);
        ((TextView) window.findViewById(R.id.message)).setText(message);
        TextView message_tips = (TextView) window.findViewById(R.id.message_tips);
        if (!(tips == null && tips == "")) {
            message_tips.setText(tips);
        }
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (okLabelColor != 0) {
            ok.setTextColor(okLabelColor);
        }
        ok.setText(okLabel);
        ok.setOnClickListener(new 1(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        if (cancelLabelColor != 0) {
            cancel.setTextColor(cancelLabelColor);
        }
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 2(this, l));
    }

    public void showOkCancellabelColorDialog(SpannableString message, String tips, String okLabel, int okLabelColor, String cancelLabel, int cancelLabelColor, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_cancel_label_dialog);
        ((TextView) window.findViewById(R.id.message)).setText(message);
        TextView message_tips = (TextView) window.findViewById(R.id.message_tips);
        if (!(tips == null && tips == "")) {
            message_tips.setText(tips);
        }
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (okLabelColor != 0) {
            ok.setTextColor(okLabelColor);
        }
        ok.setText(okLabel);
        ok.setOnClickListener(new 3(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        if (cancelLabelColor != 0) {
            cancel.setTextColor(cancelLabelColor);
        }
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 4(this, l));
    }

    public void showImVoiceDialog(Context context, String msg) {
        showImVoiceDialog(context, msg, this.mCanceledOnClickBackKey, null);
    }

    public void showImVoiceDialog(Context context, String msg, boolean cancelable, OnDismissListener listener) {
        if (checkActivityValid()) {
            if (this.mDialog.isShowing()) {
                this.mDialog.hide();
            }
            if (this.mReCreate) {
                this.mDialog = this.mBuilder.create();
            }
            this.mDialog.setCancelable(cancelable);
            if (this.mContext != null) {
                this.mDialog.show();
            }
            this.mDialog.setContentView(R.layout.toast_layout_imvoice);
            ((TextView) this.mDialog.findViewById(R.id.tv_text)).setText(msg);
            if (listener != null) {
                this.mDialog.setOnDismissListener(listener);
            }
        }
    }

    public void showOkDialog(String message, OkDialogListener l) {
        showOkDialog(message, this.mCanceledOnClickBackKey, l);
    }

    public void showOkDialog(String message, boolean cancelable, OkDialogListener l) {
        showOkDialog(message, cancelable, l, false);
    }

    public void showOkDialog(String message, boolean cancelable, boolean canceledOnClickOutside, OkDialogListener l) {
        showOkDialog(message, cancelable, canceledOnClickOutside, l, false);
    }

    public void showOkDialog(String message, boolean cancelable, OkDialogListener l, boolean IsHtmlText) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_dialog);
        TextView msg = (TextView) window.findViewById(R.id.tv_msg);
        msg.setVisibility(0);
        window.findViewById(R.id.custom_container).setVisibility(8);
        if (IsHtmlText) {
            msg.setText(Html.fromHtml(message));
            msg.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            msg.setText(message);
        }
        ((TextView) window.findViewById(R.id.btn_ok)).setOnClickListener(new 5(this, l));
    }

    public void showOkDialog(String message, boolean cancelable, boolean canceledOnClickOutside, OkDialogListener l, boolean IsHtmlText) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(canceledOnClickOutside);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_dialog);
        TextView msg = (TextView) window.findViewById(R.id.tv_msg);
        msg.setVisibility(0);
        window.findViewById(R.id.custom_container).setVisibility(8);
        if (IsHtmlText) {
            msg.setText(Html.fromHtml(message));
            msg.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            msg.setText(message);
        }
        ((TextView) window.findViewById(R.id.btn_ok)).setOnClickListener(new 6(this, l));
    }

    public void showOkOrCancelDialogWithCustomView(String title, boolean cancelable, boolean isOk, View customView, OkDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(R.id.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        ((TextView) window.findViewById(R.id.tv_msg)).setVisibility(8);
        LinearLayout customContainer = (LinearLayout) window.findViewById(R.id.custom_container);
        customContainer.setVisibility(0);
        customContainer.addView(customView, new LayoutParams(-1, -2));
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (!isOk) {
            ok.setText(a.bKB);
            ok.setTextColor(-16777216);
        }
        ok.setOnClickListener(new 7(this, l));
    }

    public void showSingleOptionTip(String title, boolean cancelable, String message, String optionStr, OkDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(R.id.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        TextView tip = (TextView) window.findViewById(R.id.tv_msg);
        if (message != null) {
            tip.setText(message);
        } else {
            tip.setText("");
        }
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (optionStr != null) {
            ok.setText(optionStr);
        } else {
            ok.setText(a.bKC);
        }
        ok.setOnClickListener(new 8(this, l));
    }

    public void showOkAndLabelDialog(String message, String okLabel, boolean cancelable, boolean canceledOnClickOutside, boolean isHtmlText, boolean isUrl, OkDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(canceledOnClickOutside);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_dialog);
        TextView msg = (TextView) window.findViewById(R.id.tv_msg);
        if (isHtmlText) {
            msg.setText(Html.fromHtml(message));
        } else {
            msg.setText(message);
        }
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (!UtilsFunction.empty((CharSequence) okLabel)) {
            ok.setText(okLabel);
        }
        ok.setOnClickListener(new 9(this, l));
    }

    public void showCommonPopupDialog(List<ButtonItem> btnItems) {
        showCommonPopupDialog(null, (List) btnItems, "");
    }

    public void showCommonPopupDialog(String cancelBtnText) {
        showCommonPopupDialog(null, null, cancelBtnText);
    }

    public void showCommonPopupDialog(ButtonItem bottomButton) {
        showCommonPopupDialog(null, null, bottomButton);
    }

    public void showCommonPopupDialog(String title, List<ButtonItem> btnItems) {
        showCommonPopupDialog(title, (List) btnItems, "");
    }

    public void showCommonPopupDialog(List<ButtonItem> btnItems, String cancelBtnText) {
        showCommonPopupDialog(null, (List) btnItems, cancelBtnText);
    }

    public void showCommonPopupDialog(List<ButtonItem> btnItems, ButtonItem bottomButton) {
        showCommonPopupDialog(null, (List) btnItems, bottomButton);
    }

    public void showCommonPopupDialog(String title, List<ButtonItem> btnItems, ButtonItem bottomButton) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CommonPopupDialog(this.mContext, title, btnItems, bottomButton);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
    }

    public void showCommonPopupDialog(String title, List<ButtonItem> btnItems, String cancelBtnText) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CommonPopupDialog(this.mContext, title, btnItems, cancelBtnText);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
    }

    public void showCustomPopupDialog(String title, List<ButtonItem> btnItems) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CustomPopupDialog(this.mContext, title, btnItems);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
    }

    public void showCustomTitlePopupDialog(int title, TitleBinder binder, List<ButtonItem> btnItems) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CustomTitlePopupDialog(this.mContext, title, binder, btnItems);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
    }

    public void showCustomViewPopupDialog(int layoutId) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        showCustomViewPopupDialog(LayoutInflater.from(this.mContext).inflate(layoutId, null, false));
    }

    public void showCustomViewPopupDialog(View customView) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CustomViewPopupDialog(this.mContext, customView);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
    }

    public void showDefaultViewPopupDialog(int layoutId) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        showDefaultViewPopupDialog(LayoutInflater.from(this.mContext).inflate(layoutId, null, false));
    }

    public void showDefaultViewPopupDialog(View customView) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = new CustomViewPopupDialog(this.mContext, customView, false);
        this.mDialog.setCancelable(this.mCanceledOnClickBackKey);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        if (!(this.mContext instanceof Activity)) {
            this.mDialog.show();
        } else if (!((Activity) this.mContext).isFinishing()) {
            this.mDialog.show();
        }
    }

    public void showOkCancelDialog(String message, boolean cancelable, OkCancelDialogListener l) {
        showOkCancelDialog(null, (CharSequence) message, a.bKC, a.bKB, cancelable, l);
    }

    public void showOkCancelDialog(String message, String okLabel, String cancelLabel, OkCancelDialogListener l) {
        showOkCancelDialog(null, (CharSequence) message, (CharSequence) okLabel, (CharSequence) cancelLabel, this.mCanceledOnClickBackKey, l);
    }

    public void showOkCancelDialog(String title, CharSequence message, boolean cancelable, OkCancelDialogListener l) {
        showOkCancelDialog(title, message, a.bKC, a.bKB, cancelable, l);
    }

    public void showOkCancelDialog(String title, CharSequence message, CharSequence okLabel, CharSequence cancelLabel, boolean cancelable, OkCancelDialogListener l) {
        ViewHolder holder = new ViewHolder();
        holder.layout = R.layout.layout_ok_cancel_dialog;
        holder.root_view = R.id.root_view;
        holder.title_container = R.id.title_container;
        holder.title = R.id.title;
        holder.message = R.id.message;
        holder.custom_container = R.id.custom_container;
        holder.btn_cancel = R.id.btn_cancel;
        holder.btn_ok = R.id.btn_ok;
        showOkCancelDialog(title, message, okLabel, cancelLabel, cancelable, l, holder, null);
    }

    public void showOkCancelDialog(String title, CharSequence message, CharSequence okLabel, CharSequence cancelLabel, boolean cancelable, OkCancelDialogListener l, StyleHolder styleHolder) {
        ViewHolder holder = new ViewHolder();
        holder.layout = R.layout.layout_ok_cancel_dialog;
        holder.root_view = R.id.root_view;
        holder.title_container = R.id.title_container;
        holder.title = R.id.title;
        holder.message = R.id.message;
        holder.custom_container = R.id.custom_container;
        holder.btn_cancel = R.id.btn_cancel;
        holder.btn_ok = R.id.btn_ok;
        showOkCancelDialog(title, message, okLabel, cancelLabel, cancelable, l, holder, styleHolder);
    }

    public void showOkCancelDialog(String title, CharSequence message, CharSequence okLabel, CharSequence cancelLabel, boolean cancelable, OkCancelDialogListener l, ViewHolder holder, StyleHolder styleHolder) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(holder.layout);
        View titleContainer = window.findViewById(holder.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            TextView titleview = (TextView) window.findViewById(holder.title);
            titleview.setText(title);
            titleview.setGravity(17);
            if (!(styleHolder == null || styleHolder.colorTtle == 0)) {
                titleview.setTextColor(styleHolder.colorTtle);
            }
            titleContainer.setVisibility(0);
        }
        TextView tvMsg = (TextView) window.findViewById(holder.message);
        tvMsg.setText(message);
        if (!(styleHolder == null || styleHolder.colorMessage == 0)) {
            tvMsg.setTextColor(styleHolder.colorMessage);
        }
        TextView ok = (TextView) window.findViewById(holder.btn_ok);
        ok.setText(okLabel);
        if (!(styleHolder == null || styleHolder.colorButton == 0)) {
            ok.setTextColor(styleHolder.colorButton);
        }
        ok.setOnClickListener(new 10(this, l));
        TextView cancel = (TextView) window.findViewById(holder.btn_cancel);
        cancel.setText(cancelLabel);
        if (!(styleHolder == null || styleHolder.colorUnMarkedButton == 0)) {
            cancel.setTextColor(styleHolder.colorUnMarkedButton);
        }
        cancel.setOnClickListener(new 11(this, l));
    }

    public void showOkCancelColorDialog(String title, SpannableString message, String okLabel, int okLabelColor, String cancelLabel, int cancelLabelColor, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_cancel_color_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(R.id.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        TextView tip = (TextView) window.findViewById(R.id.message);
        tip.setText(message);
        tip.setTextColor(-16777216);
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (okLabelColor != 0) {
            ok.setTextColor(okLabelColor);
        }
        ok.setText(okLabel);
        ok.setOnClickListener(new 12(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        if (cancelLabelColor != 0) {
            cancel.setTextColor(cancelLabelColor);
        }
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 13(this, l));
    }

    public void showOkCancelColorDialog(CharSequence title, CharSequence message, CharSequence okLabel, int okLabelColor, CharSequence cancelLabel, int cancelLabelColor, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setBackgroundDrawableResource(R.color.trasnparent);
        window.setContentView(R.layout.layout_ok_cancel_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty(title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(R.id.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        TextView tip = (TextView) window.findViewById(R.id.message);
        tip.setText(Html.fromHtml(message.toString()));
        tip.setMovementMethod(LinkMovementMethod.getInstance());
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (okLabelColor != 0) {
            ok.setTextColor(okLabelColor);
        }
        ok.setText(okLabel);
        ok.setOnClickListener(new 14(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        if (cancelLabelColor != 0) {
            cancel.setTextColor(cancelLabelColor);
        }
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 15(this, l));
    }

    public void showOkCancelDialog(String title, SpannableString message, String okLabel, String cancelLabel, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_cancel_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(R.id.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        TextView tip = (TextView) window.findViewById(R.id.message);
        tip.setText(message);
        tip.setGravity(1);
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText(okLabel);
        ok.setOnClickListener(new 16(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 17(this, l));
    }

    public void showOkOrCancleColorDialog(CharSequence title, int titleColor, View customView, CharSequence okOrCancleLable, int okOrCancleLableColor, boolean cancelable, OkDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_or_cancel_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty(title)) {
            titleContainer.setVisibility(8);
        } else {
            TextView titleView = (TextView) window.findViewById(R.id.title);
            titleView.setText(title);
            if (titleColor != 0) {
                titleView.setTextColor(titleColor);
            }
            titleContainer.setVisibility(0);
        }
        ((TextView) window.findViewById(R.id.message)).setVisibility(8);
        LinearLayout customContainer = (LinearLayout) window.findViewById(R.id.custom_container);
        customContainer.setVisibility(0);
        customContainer.addView(customView, new LayoutParams(-1, -2));
        TextView ok = (TextView) window.findViewById(R.id.tv_ok_or_cancle);
        if (okOrCancleLableColor != 0) {
            ok.setTextColor(okOrCancleLableColor);
        }
        ok.setText(okOrCancleLable);
        ok.setOnClickListener(new 18(this, l));
    }

    public void showOkCancelColorDialog(CharSequence title, int titleColor, View customView, CharSequence okLabel, int okLabelColor, CharSequence cancelLabel, int cancelLabelColor, boolean cancelable, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_ok_cancel_dialog);
        View titleContainer = window.findViewById(R.id.title_container);
        if (UtilsFunction.empty(title)) {
            titleContainer.setVisibility(8);
        } else {
            TextView titleView = (TextView) window.findViewById(R.id.title);
            titleView.setText(title);
            if (titleColor != 0) {
                titleView.setTextColor(titleColor);
            }
            titleContainer.setVisibility(0);
        }
        ((TextView) window.findViewById(R.id.message)).setVisibility(8);
        LinearLayout customContainer = (LinearLayout) window.findViewById(R.id.custom_container);
        customContainer.setVisibility(0);
        customContainer.addView(customView, new LayoutParams(-1, -2));
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        if (okLabelColor != 0) {
            ok.setTextColor(okLabelColor);
        }
        ok.setText(okLabel);
        ok.setOnClickListener(new 19(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        if (cancelLabelColor != 0) {
            cancel.setTextColor(cancelLabelColor);
        }
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 20(this, l));
    }

    public void setReCreate(boolean reCreate) {
        this.mReCreate = reCreate;
    }

    public void showProgressDialog(Context context, String msg) {
        showProgressDialog(context, msg, this.mCanceledOnClickBackKey);
    }

    public void showProgressDialog(Context context, String msg, boolean cancelable) {
        showProgressDialog(context, msg, this.mCanceledOnClickBackKey, null);
    }

    public void showProgressDialog(Context context, String msg, boolean cancelable, OnDismissListener listener) {
        if (checkActivityValid()) {
            if (this.mDialog.isShowing()) {
                this.mDialog.hide();
            }
            if (this.mReCreate) {
                this.mDialog = this.mBuilder.create();
            }
            this.mDialog.setCancelable(cancelable);
            this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnClickOutside);
            if (this.mContext != null) {
                this.mDialog.show();
            }
            this.mDialog.setContentView(R.layout.layout_progress_dialog);
            TextView tvTip = (TextView) this.mDialog.findViewById(R.id.tv_tip);
            tvTip.setText(msg);
            if (UtilsFunction.empty((CharSequence) msg)) {
                tvTip.setVisibility(8);
            } else {
                tvTip.setVisibility(0);
            }
            if (listener != null) {
                this.mDialog.setOnDismissListener(listener);
                return;
            }
            return;
        }
        HLog.info(this, "showProgressDialog ActivityInvalid", new Object[0]);
    }

    public void showDownUpdateingDialog(Context context, String msg, boolean cancelable) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        this.mDialog.setContentView(R.layout.layout_progress_dialog);
        ((TextView) this.mDialog.findViewById(R.id.tv_tip)).setText(msg);
    }

    public void setText(String text) {
        this.mTip = text;
    }

    public void setMax(int max) {
        this.mProgressMax = max;
    }

    public void setProgress(int progress) {
        if (this.mDialog != null && this.mDialog.isShowing() && this.mProgressMax > 0) {
            ((TextView) this.mDialog.findViewById(R.id.tv_tip)).setText(this.mTip + ((progress * 100) / this.mProgressMax) + "%");
        }
    }

    public void showInputDialog(String message, boolean cancelable, boolean autoShowIme, boolean autoHideIme, InputDialogListener listener) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_input_dialog);
        window.clearFlags(131072);
        window.setSoftInputMode(4);
        ((TextView) window.findViewById(R.id.tv_title)).setText(message);
        EditText etInput = (EditText) window.findViewById(R.id.et_input_text);
        if (autoShowIme) {
            ImeUtil.showIMEDelay((Activity) this.mContext, etInput, 200);
        }
        window.findViewById(R.id.btn_ok).setOnClickListener(new 21(this, listener, etInput, autoHideIme));
        window.findViewById(R.id.btn_cancel).setOnClickListener(new 22(this, listener, autoHideIme, etInput));
    }

    public void showInputDialogWithHint(String message, String hint, boolean cancelable, boolean autoShowIme, boolean autoHideIme, InputDialogListener listener) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_input_dialog);
        window.clearFlags(131072);
        window.setSoftInputMode(4);
        ((TextView) window.findViewById(R.id.tv_title)).setText(message);
        EditText etInput = (EditText) window.findViewById(R.id.et_input_text);
        etInput.setText(hint);
        if (autoShowIme) {
            ImeUtil.showIMEDelay((Activity) this.mContext, etInput, 200);
        }
        window.findViewById(R.id.btn_ok).setOnClickListener(new 23(this, listener, etInput, autoHideIme));
        window.findViewById(R.id.btn_cancel).setOnClickListener(new 24(this, listener, autoHideIme, etInput));
    }

    public void hideProgressDialog() {
        if (this.mDialog != null) {
            this.mDialog.hide();
        }
    }

    public void showSelectGenderDialog(boolean selectedGender, boolean cancelable, SelectGenderDialogListener l) {
        showSelectGenderDialog(selectedGender, cancelable, true, l);
    }

    public void showSelectGenderDialog(boolean selectedGender, boolean cancelable, boolean canceledOnTouchOutside, SelectGenderDialogListener l) {
        showSelectGenderDialog(selectedGender, a.bKC, a.bKB, cancelable, canceledOnTouchOutside, l);
    }

    public void showSelectGenderDialog(boolean selectedGender, String okLabel, String cancelLabel, boolean cancelable, boolean canceledOnTouchOutside, SelectGenderDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(R.layout.layout_select_gender_dialog);
        this.mIsFemale = selectedGender;
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText(okLabel);
        ok.setOnClickListener(new 25(this, l));
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 26(this, l));
        RadioGroup group = (RadioGroup) window.findViewById(R.id.radioGroup);
        group.check(this.mIsFemale ? R.id.radio_female : R.id.radio_male);
        group.setOnCheckedChangeListener(new 27(this));
    }
}
