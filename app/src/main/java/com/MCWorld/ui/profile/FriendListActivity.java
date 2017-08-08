package com.MCWorld.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.framework.base.image.Config.NetFormat;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.hlistview.AdapterView;
import com.MCWorld.framework.base.widget.hlistview.AdapterView.OnItemClickListener;
import com.MCWorld.framework.base.widget.hlistview.HListView;
import com.MCWorld.l;
import com.MCWorld.module.h;
import com.MCWorld.module.profile.d;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.ui.itemadapter.profile.FriendItemAdapter;
import com.MCWorld.utils.aa;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FriendListActivity extends HTBaseLoadingActivity implements com.MCWorld.ui.itemadapter.profile.FriendItemAdapter.a {
    public static final String EXTRA_USER_ID = "userid";
    public static final String aLr = "EXTRA_CURRENT_SELECTED";
    public static final String aLs = "EXTRA_RESERVED_SELECTED";
    public static final String bfc = "EXTRA_DATA";
    private final int PAGE_SIZE = 20;
    private PullToRefreshListView aEq;
    protected aa aHb;
    private int aMe = 0;
    private Set<Long> aMf;
    private Activity aMn;
    private ArrayList<UserBaseInfo> aVc;
    private HListView bdH;
    private TextView bdJ;
    private boolean bdK;
    private d beY = null;
    private FriendItemAdapter beZ;
    private ArrayList<UserBaseInfo> bfd;
    private a bfe;
    private final int bff = 5;
    private View bfg;
    private UserBaseInfo bfh = new UserBaseInfo();
    private int bfi = 0;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ FriendListActivity bfj;

        {
            this.bfj = this$0;
        }

        @MessageHandler(message = 665)
        public void onRecvList(boolean succ, d info, int laststart, Context context) {
            if (context == this.bfj.aMn) {
                this.bfj.aEq.onRefreshComplete();
                this.bfj.cs(false);
                if (succ) {
                    this.bfj.aHb.onLoadComplete();
                    if (laststart > 20) {
                        this.bfj.beY.start = info.start;
                        this.bfj.beY.more = info.more;
                        this.bfj.beY.friendships.addAll(info.friendships);
                        this.bfj.beZ.c(info.friendships, false);
                    } else {
                        this.bfj.beY = info;
                        this.bfj.beZ.c(info.friendships, true);
                    }
                    if (UtilsFunction.empty(this.bfj.beY.friendships)) {
                        this.bfj.bfg.setVisibility(0);
                    } else {
                        this.bfj.bfg.setVisibility(8);
                    }
                    this.bfj.FC();
                } else if (this.bfj.getCurrentPage() == 0) {
                    this.bfj.FB();
                } else {
                    this.bfj.aHb.KN();
                    t.n(this.bfj.aMn, info == null ? this.bfj.getResources().getString(m.loading_failed_please_retry) : info.msg);
                }
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ FriendListActivity bfj;

        {
            this.bfj = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.btn_back) {
                this.bfj.finish();
            } else if (id == g.btn_ok) {
                Intent intent = new Intent();
                intent.putExtra("EXTRA_CURRENT_SELECTED", this.bfj.aVc);
                this.bfj.setResult(533, intent);
                this.bfj.finish();
            }
        }
    };
    private long userid = 0;

    private class a extends BaseAdapter {
        final /* synthetic */ FriendListActivity bfj;
        private Context mContext;

        public a(FriendListActivity friendListActivity, Context context) {
            this.bfj = friendListActivity;
            this.mContext = context;
        }

        public int getCount() {
            return 5;
        }

        public Object getItem(int position) {
            if (this.bfj.aVc == null) {
                return this.bfj.bfh;
            }
            if (position + 1 > this.bfj.aVc.size() || position + 1 > 5) {
                return this.bfj.bfh;
            }
            return this.bfj.aVc.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mContext).inflate(i.item_pic_preview, parent, false);
            }
            ImageView imageView = (PaintView) convertView;
            UserBaseInfo info = (UserBaseInfo) getItem(position);
            imageView.radius((float) t.dipToPx(this.mContext, 3)).borderColor(com.simple.colorful.d.getColor(this.mContext, c.backgroundDim5), (float) t.dipToPx(this.mContext, 1)).placeHolder(com.simple.colorful.d.isDayMode() ? f.ic_remind_default : f.ic_remind_default_night);
            if (info.userID == 0) {
                imageView.setImageResource(com.simple.colorful.d.isDayMode() ? f.ic_remind_default : f.ic_remind_default_night);
                this.bfj.a(imageView, 0);
            } else if (UtilsFunction.empty(this.bfj.aMf) || !this.bfj.aMf.contains(Long.valueOf(info.userID))) {
                imageView.setUri(null).setImageLoader(l.cb().getImageLoader());
                imageView.setUri(UtilUri.getUriOrNull(info.avatar), NetFormat.FORMAT_160).setImageLoader(l.cb().getImageLoader());
                this.bfj.a(imageView, this.bfj.bfi);
            } else {
                imageView.setUri(null).setImageLoader(l.cb().getImageLoader());
                imageView.setUri(UtilUri.getUriOrNull(info.avatar), NetFormat.FORMAT_160).setImageLoader(l.cb().getImageLoader());
                imageView.setColorFilter(this.bfj.aMe);
            }
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
        setContentView(i.activity_friendlist);
        if (savedInstanceState != null) {
            this.userid = savedInstanceState.getLong(EXTRA_USER_ID, 0);
            this.aVc = savedInstanceState.getParcelableArrayList("EXTRA_CURRENT_SELECTED");
            this.bfd = savedInstanceState.getParcelableArrayList(aLs);
            this.beY = (d) savedInstanceState.getParcelable(bfc);
        } else {
            Intent intent = getIntent();
            this.userid = intent.getLongExtra(EXTRA_USER_ID, 0);
            this.aVc = intent.getParcelableArrayListExtra("EXTRA_CURRENT_SELECTED");
            this.bfd = intent.getParcelableArrayListExtra(aLs);
        }
        if (this.aVc == null) {
            this.aVc = new ArrayList();
        }
        if (!UtilsFunction.empty(this.bfd)) {
            this.aMf = new HashSet();
            Iterator it = this.bfd.iterator();
            while (it.hasNext()) {
                this.aMf.add(Long.valueOf(((UserBaseInfo) it.next()).userID));
            }
        }
        this.bfh.userID = 0;
        this.bfi = com.simple.colorful.d.s(this, c.valBrightness);
        this.aMe = com.simple.colorful.d.getColor(this, c.bgColorMask);
        FN();
        Fo();
        IU();
        EventNotifyCenter.add(h.class, this.mCallback);
        Fy();
        reload();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(EXTRA_USER_ID, this.userid);
        outState.putParcelableArrayList("EXTRA_CURRENT_SELECTED", this.aVc);
        outState.putParcelable(bfc, this.beY);
        super.onSaveInstanceState(outState);
    }

    private void FN() {
        ej("选择联系人");
        this.aIs.setVisibility(8);
    }

    private void Fo() {
        this.aEq = (PullToRefreshListView) findViewById(g.list);
        this.beZ = new FriendItemAdapter(this, true, this);
        this.aEq.setAdapter(this.beZ);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ FriendListActivity bfj;

            {
                this.bfj = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bfj.reload();
            }
        });
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new com.MCWorld.utils.aa.a(this) {
            final /* synthetic */ FriendListActivity bfj;

            {
                this.bfj = this$0;
            }

            public void onLoadData() {
                this.bfj.Fc();
            }

            public boolean shouldLoadData() {
                if (this.bfj.beY == null) {
                    this.bfj.aHb.onLoadComplete();
                    return false;
                } else if (this.bfj.beY.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aHb);
        this.aEq.setOnItemClickListener(null);
        if (!(this.beY == null || UtilsFunction.empty(this.beY.friendships))) {
            this.beZ.c(this.beY.friendships, true);
        }
        this.bfg = findViewById(g.rly_nofriend);
    }

    protected void EX() {
        super.EX();
        com.MCWorld.module.profile.g.Eb().a(0, 20, this.aMn);
    }

    private void reload() {
        com.MCWorld.module.profile.g.Eb().a(0, 20, this.aMn);
    }

    private void Fc() {
        int start;
        int i = 0;
        if (this.beY != null) {
            start = this.beY.start;
        } else {
            start = 0;
        }
        com.MCWorld.module.profile.g Eb = com.MCWorld.module.profile.g.Eb();
        if (this.beY != null) {
            i = start;
        }
        Eb.a(i, 20, this.aMn);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.beZ != null) {
            this.beZ.notifyDataSetChanged();
        }
    }

    private void IU() {
        this.bdJ = (TextView) findViewById(g.btn_ok);
        this.bdJ.setOnClickListener(this.mClickListener);
        this.bdH = (HListView) findViewById(g.list_preview);
        this.bdH.setVisibility(0);
        this.bfe = new a(this, this);
        this.bdH.setAdapter(this.bfe);
        this.bdH.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ FriendListActivity bfj;

            {
                this.bfj = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.bfj.aVc != null && position + 1 <= this.bfj.aVc.size() && position + 1 <= 5) {
                    if (this.bfj.a(this.bfj.bfd, (UserBaseInfo) this.bfj.aVc.get(position)) == null) {
                        this.bfj.aVc.remove(position);
                        this.bfj.bfe.notifyDataSetChanged();
                        this.bfj.bdK = true;
                        this.bfj.beZ.e(this.bfj.aVc, this.bfj.bfd);
                        this.bfj.IV();
                        return;
                    }
                    t.show_toast(this.bfj.aMn, this.bfj.aMn.getResources().getString(m.reminds_cannont_remove));
                }
            }
        });
        this.beZ.e(this.aVc, this.bfd);
        IV();
    }

    private void IV() {
        int selectedCount = this.aVc == null ? 0 : this.aVc.size();
        this.bdJ.setText(String.format("完成(%d)", new Object[]{Integer.valueOf(selectedCount)}));
        this.bdJ.setEnabled(this.bdK);
    }

    public void a(UserBaseInfo info) {
        this.bdK = true;
        if (this.aVc == null) {
            this.aVc = new ArrayList();
        }
        if (a(this.aVc, info) == null) {
            this.aVc.add(info);
            this.bfe.notifyDataSetChanged();
        }
        IV();
    }

    public void b(UserBaseInfo info) {
        this.bdK = true;
        if (this.aVc == null) {
            this.aVc = new ArrayList();
        }
        UserBaseInfo item = a(this.aVc, info);
        if (item != null) {
            this.aVc.remove(item);
            this.bfe.notifyDataSetChanged();
        }
        IV();
    }

    public boolean HC() {
        if (this.aVc == null || this.aVc.size() < 5) {
            return false;
        }
        t.show_toast(this, "只能同时@5位好友哦");
        return true;
    }

    public boolean c(UserBaseInfo info) {
        if (a(this.bfd, info) == null) {
            return false;
        }
        t.show_toast(this, this.aMn.getResources().getString(m.reminds_cannont_remove));
        return true;
    }

    private UserBaseInfo a(ArrayList<UserBaseInfo> list, UserBaseInfo info) {
        if (list == null || info == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserBaseInfo item = (UserBaseInfo) it.next();
            if (item.userID == info.userID) {
                return item;
            }
        }
        return null;
    }

    private void a(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }
}
