package com.huluxia.studio;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.data.studio.b.a;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.d;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnnounceDetailActivity extends HTBaseLoadingActivity {
    public static String aDU = "STUDIO_ANNOUNCE_DATA";
    public static String aDV = StudioAnnounceActivity.aDV;
    public static final String aDW = "STUDIO_ID";
    private String TAG = "AnnounceDetailActivity";
    protected EmojiTextView aDQ;
    protected EmojiTextView aDR;
    protected TextView aDS;
    protected EmojiTextView aDT;
    private int aDX;
    private int aDY = 3;
    private a aDZ;
    private int aEa;
    private CommonMenuDialog aEb = null;
    private final int aEc = 257;
    private CommonMenuDialogListener aEd = new CommonMenuDialogListener(this) {
        final /* synthetic */ AnnounceDetailActivity aEe;

        {
            this.aEe = this$0;
        }

        public void pressMenuById(int inIndex, Object object) {
            this.aEe.aEb.dismissDialog();
            if (257 == inIndex && this.aEe.aDZ != null) {
                z.DO().js(this.aEe.aEa);
            }
        }
    };
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ AnnounceDetailActivity aEe;

        {
            this.aEe = this$0;
        }

        @MessageHandler(message = 807)
        public void delAnnounceDetail(int tag, boolean succ, w info) {
            if (tag != this.aEe.aEa) {
                return;
            }
            if (!succ || info == null) {
                t.n(this.aEe.mContext, info != null ? info.msg : "删除失败，请重试");
                return;
            }
            t.show_toast(this.aEe.mContext, "删除成功");
            EventNotifyCenter.notifyEventUiThread(h.class, h.asv, Integer.valueOf(this.aEe.aDX), this.aEe.aDZ);
            this.aEe.finish();
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_announce_detail);
        this.mContext = this;
        EventNotifyCenter.add(h.class, this.aky);
        if (savedInstanceState == null) {
            this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
            this.aDZ = (a) getIntent().getParcelableExtra(aDU);
            this.aDY = getIntent().getIntExtra(aDV, 0);
        } else {
            this.aDX = savedInstanceState.getInt("STUDIO_ID", 0);
            this.aDZ = (a) savedInstanceState.getParcelable(aDU);
            this.aDY = savedInstanceState.getInt(aDV);
        }
        initView();
        EP();
        EQ();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STUDIO_ID", this.aDX);
        outState.putParcelable(aDU, this.aDZ);
        outState.putInt(aDV, this.aDY);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void initView() {
        this.aDQ = (EmojiTextView) findViewById(R.id.tv_announce_title);
        this.aDR = (EmojiTextView) findViewById(R.id.tv_announcer);
        this.aDS = (TextView) findViewById(R.id.tv_publish_time);
        this.aDT = (EmojiTextView) findViewById(R.id.tv_announce_content);
    }

    private void EP() {
        ej("公告详情");
        this.aIs.setVisibility(8);
        if (this.aDY == 1 || this.aDY == 2) {
            this.aIQ.setVisibility(0);
        } else {
            this.aIQ.setVisibility(8);
        }
        this.aIQ.setImageResource(d.r(this.mContext, R.attr.ic_announce_delete));
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AnnounceDetailActivity aEe;

            {
                this.aEe = this$0;
            }

            public void onClick(View v) {
                ArrayList<Object> mSortArrayList = new ArrayList();
                mSortArrayList.add(new ResMenuItem("删除", 257, R.color.studio_me_in_member_list));
                this.aEe.aEb = new CommonMenuDialog(this.aEe.mContext, this.aEe.aEd, d.RB(), 1);
                this.aEe.aEb.setMenuItems(mSortArrayList);
                this.aEe.aEb.showMenu(null, null);
            }
        });
    }

    private void EQ() {
        if (this.aDZ != null) {
            this.aEa = this.aDZ.id;
            this.aDQ.setText(this.aDZ.title);
            this.aDR.setText(this.aDZ.announcer);
            this.aDS.setText(new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(this.aDZ.createTime)));
            this.aDT.setText(this.aDZ.content);
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, R.attr.backgroundDefault).ba(R.id.tv_announce_title, R.attr.room_number).ba(R.id.tv_announcer, 16843282).ba(R.id.tv_publish_time, 16843282).aY(R.id.split, R.attr.splitColor).ba(R.id.tv_announce_content, 16842808);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aIQ.setImageResource(d.r(this.mContext, R.attr.ic_announce_delete));
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
