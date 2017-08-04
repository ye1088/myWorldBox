package com.huluxia.mconline.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.data.j;
import com.huluxia.data.profile.c;
import com.huluxia.framework.R;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.mcinterface.h;
import com.huluxia.mconline.gameloc.http.f;
import com.huluxia.mconline.gameloc.http.g;
import com.huluxia.module.n;
import com.huluxia.module.o;
import com.huluxia.module.y;
import com.huluxia.module.z;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.MojangMessage;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.ab;
import hlx.ui.localresmgr.cache.b;
import hlx.utils.e;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RoomCreateActivity extends HTBaseActivity {
    public static final String TAG = "RoomCreateActivity";
    protected static Handler Vo = null;
    private static final String ajX = "ROOM_MAP_TYPE";
    private static final String ajY = "ROOM_MAX_PLAYER";
    private static final String ajZ = "ROOM_GAME_MODE";
    private static final String aka = "ROOM_ROOM_SCOPE";
    private static String akm;
    private static int akn;
    public static String pP;
    private Runnable akA = new Runnable(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        public void run() {
            try {
                this.akB.ako = b.Us().nF(3);
            } catch (Exception e) {
            }
        }
    };
    private RoomCreateActivity akb;
    private View akc;
    private View akd;
    private EditText ake;
    private TextView akf;
    private TextView akg;
    private TextView akh;
    private TextView aki;
    private TextView akj;
    private EditText akk;
    private CheckBox akl;
    private List<com.huluxia.data.map.b> ako;
    private Map<Integer, Object> akp = new HashMap();
    private Map<Integer, String> akq = new HashMap();
    private Map<Integer, Object> akr = new HashMap();
    private Map<Integer, String> aks = new HashMap();
    private Map<Integer, Object> akt = new HashMap();
    private Map<Integer, String> aku = new HashMap();
    com.huluxia.mconline.activity.widget.a.a akv = new com.huluxia.mconline.activity.widget.a.a(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        public void g(Object val, String name) {
            this.akB.map_type = ((Integer) val).intValue();
            this.akB.akg.setText(name);
        }

        public void rb() {
        }
    };
    com.huluxia.mconline.activity.widget.a.a akw = new com.huluxia.mconline.activity.widget.a.a(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        public void g(Object val, String name) {
            this.akB.max_player = ((Integer) val).intValue();
            this.akB.akh.setText(name);
        }

        public void rb() {
        }
    };
    com.huluxia.mconline.activity.widget.a.a akx = new com.huluxia.mconline.activity.widget.a.a(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        public void g(Object val, String name) {
            this.akB.game_mode = ((Integer) val).intValue();
            this.akB.aki.setText(name);
        }

        public void rb() {
        }
    };
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        @MessageHandler(message = 776)
        public void onRecvStudioId(boolean succ, y info, long userId, Object ctx) {
            if (!j.ep().ey() || j.ep().getUserid() != userId || ctx == null || !(ctx instanceof f)) {
                return;
            }
            if (!succ || info == null) {
                if (info != null) {
                    t.show_toast(this.akB.akb, ab.n(info.code, info.msg));
                } else {
                    t.show_toast(this.akB.akb, "查询工作室信息失败，请重试");
                }
            } else if (info.getSid() != 0) {
                z.DO();
                z.b(info.getSid(), ctx);
            } else {
                com.huluxia.mconline.module.a.Bl().ap(ctx);
            }
        }

        @MessageHandler(message = 784)
        public void acceptStudioInfo(boolean isSucc, c info, int studioId, Object ctx) {
            if (j.ep().ey() && ctx != null && (ctx instanceof f)) {
                f createItem = (f) ctx;
                if (!isSucc || createItem == null) {
                    if (info != null) {
                        t.show_toast(this.akB.akb, ab.n(info.code, info.msg));
                    } else {
                        t.show_toast(this.akB.akb, "查询工作室信息失败，请重试");
                    }
                } else if (info == null || info.studioInfo == null) {
                    createItem.fillStudio(0, null);
                    com.huluxia.mconline.module.a.Bl().ap(createItem);
                } else {
                    createItem.fillStudio((long) studioId, info.studioInfo.name);
                    com.huluxia.mconline.module.a.Bl().ap(createItem);
                }
            }
        }
    };
    private CallbackHandler akz = new CallbackHandler(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        @MessageHandler(message = 263)
        public void onRecvLevelData(boolean succ, Object object) {
            if (succ && object != null && (object instanceof f)) {
                try {
                    HLog.verbose(RoomCreateActivity.TAG, "DTPrint EVENT_RELOAD_LEVEL_DATA success !!!", new Object[0]);
                    HLog.verbose("TAG", "DTPrint getLastPlayed 00 is " + Mojang.instance().getLastPlayed(), new Object[0]);
                    long _tmpSerNewLastPlayed = System.currentTimeMillis() / 1000;
                    HLog.verbose("TAG", "DTPrint getLastPlayed _tmpSerNewLastPlayed is " + _tmpSerNewLastPlayed, new Object[0]);
                    Mojang.instance().setLastPlayed(_tmpSerNewLastPlayed);
                    HLog.verbose("TAG", "DTPrint getLastPlayed 01 is " + Mojang.instance().getLastPlayed(), new Object[0]);
                } catch (Exception e) {
                }
                f createItem = (f) object;
                HLog.verbose(RoomCreateActivity.TAG, "DTPrint 开始连接线上服务器: " + createItem.online_ip + ":" + createItem.online_port, new Object[0]);
                HLog.verbose("TAG", "DTPrint webHostAddress is " + createItem.client_ip, new Object[0]);
                com.huluxia.mconline.utils.a.dr(createItem.client_ip);
                h.zM().b(com.huluxia.mconline.gamerole.b.Bh());
                createItem.fillGame(this.akB.ake.getText().toString(), "0.15.4", 0, this.akB.map_type, RoomCreateActivity.akn, 0, this.akB.max_player);
                com.huluxia.mconline.gamerole.b.Bh().a(this.akB.akb, createItem.online_ip, createItem.online_port, createItem);
            }
        }
    };
    private int game_mode;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        @MessageHandler(message = 3333)
        public void onRecvResCate(boolean succ, ArrayList<hlx.module.resources.a> cateList) {
            if (succ && !UtilsFunction.empty((Collection) cateList)) {
                this.akB.m((ArrayList) cateList);
            }
        }

        @MessageHandler(message = 3345)
        public void onRecvResCate(boolean succ, com.huluxia.mconline.gameloc.http.h infos, Object ctx) {
            if (succ) {
                if (ctx != null && (ctx instanceof f)) {
                    f createItem = (f) ctx;
                    if (UtilsFunction.empty(infos.room_infos)) {
                        t.show_toast(this.akB.akb, "申请房间资源失败");
                        return;
                    }
                    g info = (g) infos.room_infos.get(0);
                    createItem.fillIp(info.online_ip, info.online_port, infos.client_ip, infos.client_ip, 1009);
                    this.akB.a(createItem);
                }
            } else if (infos != null) {
                t.show_toast(this.akB.akb, ab.n(infos.code, infos.msg));
            } else {
                t.show_toast(this.akB.akb, "网络错误");
            }
        }
    };
    OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ RoomCreateActivity akB;

        {
            this.akB = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.room_map_text:
                    new com.huluxia.mconline.activity.dialog.a(this.akB.akb, this.akB.ako, RoomCreateActivity.akm).show();
                    return;
                case R.id.map_type_text:
                    if (this.akB.akp.size() == 0) {
                        this.akB.AH();
                    }
                    new com.huluxia.mconline.activity.widget.a(this.akB.akb, Integer.valueOf(this.akB.map_type), this.akB.akp, this.akB.akq, this.akB.akv).show();
                    return;
                case R.id.max_player_text:
                    new com.huluxia.mconline.activity.widget.a(this.akB.akb, Integer.valueOf(this.akB.max_player), this.akB.akr, this.akB.aks, this.akB.akw).show();
                    return;
                case R.id.game_mode_text:
                    new com.huluxia.mconline.activity.widget.a(this.akB.akb, Integer.valueOf(this.akB.game_mode), this.akB.akt, this.akB.aku, this.akB.akx).show();
                    return;
                default:
                    return;
            }
        }
    };
    private int map_type;
    private int max_player;
    private int room_scope;

    static class a extends Handler {
        WeakReference<RoomCreateActivity> akD;

        a(RoomCreateActivity activity) {
            this.akD = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            RoomCreateActivity activity = (RoomCreateActivity) this.akD.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.akf.setText(RoomCreateActivity.pP);
                        activity.ake.setText(RoomCreateActivity.pP);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.akb = this;
        setContentView((int) R.layout.mconline_activity_createroom);
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(com.huluxia.module.h.class, this.aky);
        if (savedInstanceState != null) {
            this.map_type = savedInstanceState.getInt(ajX);
            this.max_player = savedInstanceState.getInt(ajY);
            this.game_mode = savedInstanceState.getInt(ajZ);
            this.room_scope = savedInstanceState.getInt(aka);
        }
        this.akc = findViewById(R.id.main_view);
        this.akd = findViewById(R.id.create_room);
        this.ake = (EditText) findViewById(R.id.room_name_edit);
        this.akf = (TextView) findViewById(R.id.room_map_text);
        this.akg = (TextView) findViewById(R.id.map_type_text);
        this.akh = (TextView) findViewById(R.id.max_player_text);
        this.aki = (TextView) findViewById(R.id.game_mode_text);
        this.akj = (TextView) findViewById(R.id.room_scope_text);
        this.akk = (EditText) findViewById(R.id.room_pwd_edit);
        this.akl = (CheckBox) findViewById(R.id.paswd_check_box);
        this.akd.setOnClickListener(new e(this) {
            final /* synthetic */ RoomCreateActivity akB;

            {
                this.akB = this$0;
            }

            public void c(View v) {
                this.akB.AK();
            }
        });
        this.akg.setOnClickListener(this.mClickListener);
        this.akh.setOnClickListener(this.mClickListener);
        this.aki.setOnClickListener(this.mClickListener);
        this.akj.setOnClickListener(this.mClickListener);
        this.akf.setOnClickListener(this.mClickListener);
        this.aIs.setVisibility(8);
        ej("创建房间");
        AH();
        AI();
        ce(true);
        Vo = new a(this);
        AL();
    }

    protected void onDestroy() {
        ce(false);
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aky);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ajX, this.map_type);
        outState.putInt(ajY, this.max_player);
        outState.putInt(ajZ, this.game_mode);
        outState.putInt(aka, this.room_scope);
    }

    private void AH() {
        if (UtilsFunction.empty(HTApplication.bX())) {
            o.DI();
            return;
        }
        int i = 0;
        for (Entry<Long, String> entry : HTApplication.bX().entrySet()) {
            i++;
            if (i == 1) {
                this.map_type = Integer.valueOf(((Long) entry.getKey()).toString()).intValue();
                this.akg.setText((CharSequence) entry.getValue());
            }
            this.akp.put(Integer.valueOf(i), Integer.valueOf(((Long) entry.getKey()).toString()));
            this.akq.put(Integer.valueOf(i), entry.getValue());
        }
    }

    private void m(ArrayList<hlx.module.resources.a> cateList) {
        int i = 0;
        Iterator it = cateList.iterator();
        while (it.hasNext()) {
            hlx.module.resources.a item = (hlx.module.resources.a) it.next();
            i++;
            if (i == 1) {
                this.map_type = Integer.valueOf(String.valueOf(item.cateid)).intValue();
                this.akg.setText(item.catename);
            }
            this.akp.put(Integer.valueOf(i), Integer.valueOf(String.valueOf(item.cateid)));
            this.akq.put(Integer.valueOf(i), item.catename);
        }
    }

    private void AI() {
        this.akr.put(Integer.valueOf(2), Integer.valueOf(2));
        this.akr.put(Integer.valueOf(3), Integer.valueOf(3));
        this.akr.put(Integer.valueOf(4), Integer.valueOf(4));
        this.akr.put(Integer.valueOf(5), Integer.valueOf(5));
        this.aks.put(Integer.valueOf(2), this.akb.getResources().getString(R.string.player_two));
        this.aks.put(Integer.valueOf(3), this.akb.getResources().getString(R.string.player_three));
        this.aks.put(Integer.valueOf(4), this.akb.getResources().getString(R.string.player_four));
        this.aks.put(Integer.valueOf(5), this.akb.getResources().getString(R.string.player_five));
        this.akt.put(Integer.valueOf(1), Integer.valueOf(1));
        this.akt.put(Integer.valueOf(2), Integer.valueOf(2));
        this.aku.put(Integer.valueOf(1), this.akb.getResources().getString(R.string.mode_create));
        this.aku.put(Integer.valueOf(2), this.akb.getResources().getString(R.string.mode_survial));
        this.max_player = 5;
        this.akh.setText(this.akb.getResources().getString(R.string.player_five));
    }

    private void AJ() {
    }

    public void ce(boolean init) {
        if (init) {
            EventNotifyCenter.add(MojangMessage.class, this.akz);
        } else {
            EventNotifyCenter.remove(this.akz);
        }
    }

    private void AK() {
        if (!j.ep().ey()) {
            t.an(this.akb);
        } else if (this.ake.getText().toString().trim().length() < 2) {
            t.show_toast(this.akb, "房间名称长度不能小于2");
        } else if (this.akf.getText().toString().trim().length() < 1) {
            t.show_toast(this.akb, "地图名称长度不能小于1");
        } else {
            Object createItem = new f();
            createItem.fillAdmin(j.ep().getUserid(), j.ep().getNick(), j.ep().getAvatar());
            z.DO();
            z.b(j.ep().getUserid(), createItem);
        }
    }

    private void a(f createItem) {
        try {
            Mojang.instance().init(akm, 0, createItem);
        } catch (Exception e) {
            HLog.verbose(TAG, "DTPrint 地图无法打开 " + akm, new Object[0]);
        }
    }

    public static void f(com.huluxia.data.map.b inputStartMapItem) {
        akm = inputStartMapItem.name;
        pP = inputStartMapItem.pP;
        akn = (int) inputStartMapItem.size;
        Vo.sendMessage(Vo.obtainMessage(1));
    }

    private void AL() {
        AsyncTaskCenter.getInstance().execute(this.akA, new RunnableCallback(this) {
            final /* synthetic */ RoomCreateActivity akB;

            {
                this.akB = this$0;
            }

            public void onCallback() {
                this.akB.akb.runOnUiThread(new 1(this));
            }
        });
    }

    private void AM() {
        if (this.ako != null && this.ako.size() > 1) {
            com.huluxia.data.map.b _tmpFileItem = (com.huluxia.data.map.b) this.ako.get(0);
            if (_tmpFileItem != null) {
                f(_tmpFileItem);
            }
        }
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
