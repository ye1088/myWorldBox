package com.MCWorld.studio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.y;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.j;
import com.MCWorld.data.profile.e;
import com.MCWorld.data.profile.e.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.MCWorld.module.h;
import com.MCWorld.module.w;
import com.MCWorld.module.z;
import com.MCWorld.studio.adapter.StudioMembersAdapter;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.c;
import com.simple.colorful.d;
import com.simple.colorful.setter.k;
import java.util.ArrayList;

public class StudioMembersActivity extends HTBaseLoadingActivity implements OnClickListener {
    public static final int PAGE_SIZE = 20;
    private static final String TAG = "StudioMembersActivity";
    public static final String aDW = "STUDIO_ID";
    public static final int aFb = 257;
    public static final int aFc = 258;
    public static final int aFd = 259;
    private int aDX;
    private c aEV;
    private StudioMembersAdapter aEW;
    private e aEX;
    private boolean aEY = false;
    OnItemClickListener aEZ = new OnItemClickListener(this) {
        final /* synthetic */ StudioMembersActivity aFf;

        {
            this.aFf = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            UserBaseInfo user = ((a) this.aFf.aEX.studioUserList.get(position - 1)).user;
            t.a(this.aFf.mContext, user.getUserID(), user);
        }
    };
    private PullToRefreshListView aEq;
    private CommonMenuDialog aFa;
    private CommonMenuDialogListener aFe = new CommonMenuDialogListener(this) {
        final /* synthetic */ StudioMembersActivity aFf;

        {
            this.aFf = this$0;
        }

        public void pressMenuById(int inIndex, Object object) {
            this.aFf.aFa.dismissDialog();
            this.aFf.aEY = !this.aFf.aEY;
            this.aFf.aIT.setVisibility(0);
            this.aFf.aIQ.setVisibility(8);
            if (inIndex == 257) {
                this.aFf.aEW.b(this.aFf.aEY, 257);
            } else if (inIndex == 258) {
                this.aFf.aEW.b(this.aFf.aEY, 258);
            } else if (inIndex == 259) {
                this.aFf.aEW.b(this.aFf.aEY, 259);
            }
        }
    };
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ StudioMembersActivity aFf;

        {
            this.aFf = this$0;
        }

        @MessageHandler(message = 12402)
        public void onRecStudioMembers(boolean succ, e info, int sid) {
            if (this.aFf.aDX == sid) {
                this.aFf.aEq.onRefreshComplete();
                this.aFf.aEV.onLoadComplete();
                if (!succ || info == null) {
                    if (info != null) {
                        t.n(this.aFf.mContext, info.msg);
                    }
                    if (this.aFf.aEX == null || this.aFf.aEX.studioUserList.size() == 0) {
                        this.aFf.FB();
                        return;
                    }
                    return;
                }
                this.aFf.FC();
                if (info.start > 20) {
                    this.aFf.aEX.start = info.start;
                    this.aFf.aEX.more = info.more;
                    this.aFf.aEX.studioUserList.addAll(info.studioUserList);
                    this.aFf.aEW.c(info.studioUserList, false);
                    return;
                }
                this.aFf.aEX = info;
                this.aFf.aEW.c(info.studioUserList, true);
                this.aFf.b(this.aFf.aEX);
            }
        }

        @MessageHandler(message = 777)
        public void onRecRemoveMembers(boolean succ, w info, a user, int studioId) {
            if (this.aFf.aDX == studioId) {
                if (!succ || info == null) {
                    t.n(this.aFf.mContext, info != null ? info.msg : this.aFf.getString(R.string.remove_failure));
                    return;
                }
                t.o(this.aFf.mContext, this.aFf.getString(R.string.remove_members));
                this.aFf.aEW.a(user);
            }
        }

        @MessageHandler(message = 809)
        public void recvSetViceLeaderResult(int sid, boolean succ, w info, a studioUser) {
            if (studioUser == null || this.aFf.aDX != sid) {
                return;
            }
            if (succ) {
                studioUser.isStudio = 2;
                this.aFf.aEW.notifyDataSetChanged();
                return;
            }
            t.n(this.aFf.mContext, info != null ? info.msg : "操作失败，请重试");
        }

        @MessageHandler(message = 816)
        public void recvCancelViceLeaderResult(int sid, boolean succ, w info, a studioUser) {
            if (studioUser == null || this.aFf.aDX != sid) {
                return;
            }
            if (succ) {
                studioUser.isStudio = 0;
                this.aFf.aEW.notifyDataSetChanged();
                return;
            }
            t.n(this.aFf.mContext, info != null ? info.msg : "操作失败，请重试");
        }

        @MessageHandler(message = 817)
        public void recvStudioTransferResult(int sid, boolean succ, w info) {
            if (this.aFf.aDX != sid) {
                return;
            }
            if (succ) {
                this.aFf.aEY = !this.aFf.aEY;
                this.aFf.aIT.setVisibility(8);
                this.aFf.aIQ.setVisibility(8);
                this.aFf.aEq.setOnItemClickListener(this.aFf.aEZ);
                ((ListView) this.aFf.aEq.getRefreshableView()).setSelector(d.r(this.aFf.mContext, R.attr.listview_item));
                this.aFf.aEW.b(this.aFf.aEY, 0);
                this.aFf.reload();
                return;
            }
            t.n(this.aFf.mContext, info != null ? info.msg : "操作失败，请重试");
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        if (savedInstanceState == null) {
            this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
        } else {
            this.aDX = savedInstanceState.getInt("STUDIO_ID", 0);
        }
        setContentView((int) R.layout.activity_studio_members);
        EventNotifyCenter.add(h.class, this.aky);
        Fd();
        Fa();
        reload();
        Fy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STUDIO_ID", this.aDX);
    }

    @y
    private void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        this.aEW = new StudioMembersAdapter(this);
        this.aEq.setAdapter(this.aEW);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ StudioMembersActivity aFf;

            {
                this.aFf = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aFf.reload();
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new c.a(this) {
            final /* synthetic */ StudioMembersActivity aFf;

            {
                this.aFf = this$0;
            }

            public void onLoadData() {
                if (this.aFf.aEX != null) {
                    this.aFf.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.aFf.aEX == null) {
                    this.aFf.aEV.onLoadComplete();
                    return false;
                } else if (this.aFf.aEX.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        ((ListView) this.aEq.getRefreshableView()).setOnItemClickListener(this.aEZ);
        this.aEq.setOnScrollListener(this.aEV);
    }

    public boolean Fb() {
        return super.Fb();
    }

    private void Fc() {
        z.DO();
        z.a(this.aEX.start, 20, this.aDX, j.ep().getUserid(), 258);
    }

    private void reload() {
        z.DO();
        z.a(0, 20, this.aDX, j.ep().getUserid(), 258);
    }

    public void cn(boolean isLoading) {
        ek(getString(R.string.submit_data));
        cs(isLoading);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    private void Fd() {
        ej(getString(R.string.studio_members));
        this.aIs.setVisibility(8);
        this.aIT.setText(R.string.finish);
        this.aIT.setOnClickListener(this);
        this.aIQ.setImageResource(d.r(this, R.attr.ic_more_option));
        this.aIQ.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.sys_header_right) {
            this.aEY = !this.aEY;
            this.aIT.setVisibility(8);
            this.aIQ.setVisibility(0);
            this.aEq.setOnItemClickListener(this.aEZ);
            ((ListView) this.aEq.getRefreshableView()).setSelector(d.r(this.mContext, R.attr.listview_item));
            this.aEW.b(this.aEY, 0);
        } else if (v.getId() == R.id.sys_header_right_img) {
            this.aEq.setOnItemClickListener(null);
            ((ListView) this.aEq.getRefreshableView()).setSelector(R.drawable.transparent);
            ArrayList<Object> mSortArrayList = new ArrayList();
            mSortArrayList.add(new ResMenuItem("设置副室长", 257, 0));
            mSortArrayList.add(new ResMenuItem("转让工作室", 258, 0));
            mSortArrayList.add(new ResMenuItem("删除成员", 259, R.color.studio_me_in_member_list));
            this.aFa = new CommonMenuDialog(this.mContext, this.aFe, d.RB(), 1);
            this.aFa.setMenuItems(mSortArrayList);
            this.aFa.showMenu(null, null);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void b(e info) {
        for (a studioUser : info.studioUserList) {
            if (studioUser.isStudio == 1 && studioUser.user.getUserID() == j.ep().getUserid()) {
                if (this.aEY) {
                    this.aIQ.setVisibility(8);
                    this.aIT.setVisibility(0);
                    return;
                }
                this.aIQ.setVisibility(0);
                this.aIT.setVisibility(8);
                return;
            }
        }
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aEW != null) {
            this.aEW.notifyDataSetChanged();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (!(this.aEW == null || this.aEq == null)) {
            k setter = new com.simple.colorful.setter.j(this.aEq);
            setter.a(this.aEW);
            builder.a(setter);
        }
        builder.aY(R.id.view, R.attr.backgroundDefault).a(this.aIR, R.attr.back, 1).i(this.aIU, R.attr.backgroundTitleBar).a(this.aIR, R.attr.backText).a(this.aIT, R.attr.backText).c(this.aIQ, R.attr.ic_more_option);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
