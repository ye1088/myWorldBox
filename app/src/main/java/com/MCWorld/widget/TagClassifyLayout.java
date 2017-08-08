package com.MCWorld.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.utils.at;
import java.util.ArrayList;

public class TagClassifyLayout extends LinearLayout {
    private a buA;
    private RadioButton buB;
    private Button buy;
    private RelativeLayout buz;
    private Class cls;
    private Context ctx;
    private boolean initialized;

    public interface a {
        void bI(long j);
    }

    public TagClassifyLayout(Context context) {
        this(context, null);
    }

    public TagClassifyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.buy = null;
        this.buz = null;
        this.cls = null;
        this.initialized = false;
        init(context);
    }

    private void init(Context context) {
        this.ctx = context;
        LayoutInflater.from(context).inflate(i.include_classify, this);
    }

    public void setJumpActivity(Class cls) {
        this.cls = cls;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void a(ArrayList<String> titles, ArrayList<Long> tags) {
        a(titles, tags, false);
    }

    public void a(ArrayList<String> titles, ArrayList<Long> tags, boolean showAll) {
        this.initialized = true;
        RadioGroup radios = (RadioGroup) findViewById(g.cat_radios);
        int dp_15 = at.dipToPx(this.ctx, 15);
        LayoutParams buttonparam = new LinearLayout.LayoutParams(-2, -1, 1.0f);
        int i = 0;
        while (i < tags.size() && i < titles.size()) {
            RadioButton btn = new RadioButton(this.ctx);
            btn.setId(((Long) tags.get(i)).intValue());
            btn.setGravity(17);
            btn.setBackgroundResource(f.tag_item_selector);
            btn.setTextColor(getResources().getColor(d.home_gray_txt));
            btn.setLayoutParams(buttonparam);
            btn.setText((CharSequence) titles.get(i));
            btn.setPadding(dp_15, 0, dp_15, 0);
            btn.setChecked(false);
            btn.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ TagClassifyLayout buC;

                {
                    this.buC = this$0;
                }

                public void onClick(View v) {
                }
            });
            if (i == 0) {
                btn.setTextColor(getResources().getColor(d.text_color_green));
                btn.setChecked(true);
                btn.setButtonDrawable(17170445);
            } else {
                btn.setButtonDrawable(f.ic_tag_split);
            }
            radios.addView(btn);
            i++;
        }
        radios.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
            final /* synthetic */ TagClassifyLayout buC;

            {
                this.buC = this$0;
            }

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);
                    btn.setTextColor(this.buC.getResources().getColor(d.category_header_text_color));
                    if (btn.getId() == checkedId) {
                        btn.setTextColor(this.buC.getResources().getColor(d.tag_green));
                    }
                }
                if (this.buC.buA != null) {
                    this.buC.buA.bI((long) checkedId);
                }
            }
        });
        if (showAll && this.cls != null) {
            this.buz = (RelativeLayout) findViewById(g.ly_show_all);
            this.buz.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ TagClassifyLayout buC;

                {
                    this.buC = this$0;
                }

                public void onClick(View arg0) {
                    Intent in = new Intent();
                    in.setClass(this.buC.ctx, this.buC.cls);
                    this.buC.ctx.startActivity(in);
                }
            });
            this.buz.setVisibility(0);
        }
    }

    public a getOnTagSelected() {
        return this.buA;
    }

    public void setOnTagSelected(a onTagSelected) {
        this.buA = onTagSelected;
    }
}
