package com.MCWorld.widget.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;
import com.MCWorld.framework.R;

public class PopupDialog extends PopupWindow {
    private Button bxO = ((Button) this.bxR.findViewById(R.id.btn_advanced));
    private Button bxP = ((Button) this.bxR.findViewById(R.id.btn_normal));
    private Button bxQ = ((Button) this.bxR.findViewById(R.id.btn_cancel));
    private View bxR;

    public PopupDialog(Activity context, OnClickListener itemsOnClick) {
        super(context);
        this.bxR = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.dialog_alert, null);
        this.bxQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PopupDialog bxS;

            {
                this.bxS = this$0;
            }

            public void onClick(View v) {
                this.bxS.dismiss();
            }
        });
        this.bxO.setOnClickListener(itemsOnClick);
        this.bxP.setOnClickListener(itemsOnClick);
        setContentView(this.bxR);
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setAnimationStyle(R.style.PopupAnimation);
        setBackgroundDrawable(new ColorDrawable(-1342177280));
        this.bxR.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ PopupDialog bxS;

            {
                this.bxS = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                int height = this.bxS.bxR.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == 1 && y < height) {
                    this.bxS.dismiss();
                }
                return true;
            }
        });
    }
}
