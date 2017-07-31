package com.huluxia.studio;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.data.topic.c;
import com.huluxia.framework.R;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.pref.b;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.simple.colorful.a.a;

public class EditAnnounceActivity extends HTBaseLoadingActivity {
    private static final String CONTENT = "CONTENT";
    private static String TAG = "EditAnnounceActivity";
    private static final String TITLE = "TITLE";
    public static final String aDW = "STUDIO_ID";
    private int aDX;
    protected EditText aEf;
    protected EditText aEg;
    protected String aEh;
    protected TextView aEi;
    protected final int aEj = 500;
    protected final int aEk = 10;
    private boolean aEl = false;
    private boolean aEm = true;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ EditAnnounceActivity aEn;

        {
            this.aEn = this$0;
        }

        @MessageHandler(message = 805)
        public void recvIssueResult(int tag, boolean succ, w info) {
            if (tag != this.aEn.aDX) {
                return;
            }
            if (succ) {
                t.o(this.aEn.mContext, "发布成功");
                this.aEn.aEl = true;
                this.aEn.finish();
                return;
            }
            t.n(this.aEn.mContext, info != null ? info.msg : "发布失败，请重试");
        }
    };
    protected Context mContext;
    protected String mTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_edit_announce);
        this.mContext = this;
        EventNotifyCenter.add(h.class, this.aky);
        if (savedInstanceState != null) {
            this.aDX = savedInstanceState.getInt("STUDIO_ID", 0);
            this.mTitle = savedInstanceState.getString(TITLE);
            this.aEh = savedInstanceState.getString(CONTENT);
        } else {
            this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
            ER();
        }
        initView();
        EP();
        EQ();
    }

    protected void onResume() {
        super.onResume();
        if (this.aEm) {
            UtilsScreen.showInputMethod(this.aEf, 1000);
            this.aEm = false;
        }
    }

    protected void onDestroy() {
        ES();
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void initView() {
        this.aEf = (EditText) findViewById(R.id.edt_announce_title);
        this.aEg = (EditText) findViewById(R.id.edt_announce_content);
        this.aEi = (TextView) findViewById(R.id.tv_content_count_tip);
    }

    private void EP() {
        ej("编辑公告");
        this.aIs.setVisibility(8);
        this.aIT.setText("发布");
        this.aIT.setVisibility(0);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ EditAnnounceActivity aEn;

            {
                this.aEn = this$0;
            }

            public void onClick(View v) {
                this.aEn.EU();
            }
        });
    }

    private void EQ() {
        if (!UtilsFunction.empty(this.mTitle)) {
            this.aEf.setText(this.mTitle);
            this.aEf.setSelection(this.mTitle.length());
            this.aEf.requestFocus();
        }
        if (!UtilsFunction.empty(this.aEh)) {
            this.aEg.setText(this.aEh);
        }
        this.aEg.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ EditAnnounceActivity aEn;
            private CharSequence aEo;
            private int aEp;
            private int selectionEnd;
            private int selectionStart;

            {
                this.aEn = this$0;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                this.aEo = s;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                this.selectionStart = this.aEn.aEg.getSelectionStart();
                this.selectionEnd = this.aEn.aEg.getSelectionEnd();
                if (this.aEo.length() > 500) {
                    this.aEp = 0;
                } else {
                    this.aEp = 500 - this.aEo.length();
                }
                if (this.aEo.length() > 10) {
                    this.aEn.aEi.setText("还可以输入" + String.valueOf(this.aEp) + "个字符");
                    this.aEn.aEi.setVisibility(0);
                } else {
                    this.aEn.aEi.setVisibility(4);
                }
                if (this.aEo.length() > 500) {
                    s.delete(this.selectionStart - 1, this.selectionEnd);
                    int tempSelection = this.selectionStart;
                    this.aEn.aEg.setTextKeepState(s);
                    this.aEn.aEg.setText(s);
                    this.aEn.aEg.setSelection(tempSelection);
                }
            }
        });
    }

    private void ER() {
        try {
            if (b.Em().contains(com.huluxia.utils.t.bkR)) {
                c draft = (c) Json.parseJsonObject(b.Em().getString(com.huluxia.utils.t.bkR), c.class);
                if (draft != null) {
                    this.mTitle = draft.getTitle();
                    this.aEh = draft.eE();
                }
            }
        } catch (Exception e) {
            ET();
        }
    }

    private void ES() {
        if (this.aEl) {
            ET();
            return;
        }
        c draft = new c();
        CharSequence title = this.aEf.getText().toString();
        CharSequence content = this.aEg.getText().toString();
        if (UtilsFunction.empty(title) && UtilsFunction.empty(content)) {
            ET();
            return;
        }
        draft.setTitle(title);
        draft.aC(content);
        b.Em().putString(com.huluxia.utils.t.bkR, Json.toJson(draft));
    }

    private void ET() {
        if (b.Em().contains(com.huluxia.utils.t.bkR)) {
            b.Em().remove(com.huluxia.utils.t.bkR);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STUDIO_ID", this.aDX);
        outState.putString(TITLE, this.aEf.getText().toString());
        outState.putString(CONTENT, this.aEg.getText().toString());
    }

    protected void EU() {
        CharSequence errorToast = EV();
        if (UtilsFunction.empty(errorToast)) {
            z.DO().a(this.aDX, this.aEf.getText().toString(), this.aEg.getText().toString());
        } else {
            t.n(this.mContext, errorToast);
        }
    }

    private String EV() {
        CharSequence title = this.aEf.getText().toString();
        CharSequence content = this.aEg.getText().toString();
        if (UtilsFunction.empty(title)) {
            return "标题不能为空";
        }
        if (title.length() < 4) {
            return "标题字数不足";
        }
        if (title.length() > 20) {
            return "标题不能超过20个字符";
        }
        if (UtilsFunction.empty(content)) {
            return "正文不能为空";
        }
        if (content.length() < 20) {
            return "内容字数不足";
        }
        if (content.length() > 500) {
            return "内容不能超过500个字符";
        }
        return null;
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, R.attr.backgroundDefault).ba(R.id.edt_announce_title, 16842808).bd(R.id.edt_announce_title, R.attr.studio_introduce_hint).ba(R.id.edt_announce_content, 16842808).bd(R.id.edt_announce_content, R.attr.studio_introduce_hint);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
