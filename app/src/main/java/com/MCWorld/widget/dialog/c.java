package com.MCWorld.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.dialog.DialogManager;

/* compiled from: AvatarDialogManager */
public class c extends DialogManager {
    private Context mContext;

    public c(Context context) {
        super(context);
        this.mContext = context;
    }

    public void a(String title, CharSequence message, CharSequence okLabel, CharSequence cancelLabel, boolean cancelable, String avatar, String nick, OkCancelDialogListener l) {
        if (this.mDialog.isShowing()) {
            this.mDialog.hide();
        }
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCancelable(cancelable);
        this.mDialog.setCanceledOnTouchOutside(cancelable);
        this.mDialog.show();
        Window window = this.mDialog.getWindow();
        window.setContentView(i.layout_avatar_dialog);
        View titleContainer = window.findViewById(g.title_container);
        if (UtilsFunction.empty((CharSequence) title)) {
            titleContainer.setVisibility(8);
        } else {
            ((TextView) window.findViewById(g.title)).setText(title);
            titleContainer.setVisibility(0);
        }
        ((TextView) window.findViewById(g.message)).setText(message);
        ((PaintView) window.findViewById(g.iv_avatar)).setUri(UtilUri.getUriOrNull(avatar)).oval().borderColor(this.mContext.getResources().getColor(d.DarenBackground), 2.0f).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        ((TextView) window.findViewById(g.tv_nick)).setText(nick);
        TextView ok = (TextView) window.findViewById(g.btn_ok);
        ok.setText(okLabel);
        ok.setOnClickListener(new 1(this, l));
        TextView cancel = (TextView) window.findViewById(g.btn_cancel);
        cancel.setText(cancelLabel);
        cancel.setOnClickListener(new 2(this, l));
    }
}
