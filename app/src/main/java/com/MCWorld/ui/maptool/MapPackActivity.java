package com.MCWorld.ui.maptool;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.mcmap.c;
import com.MCWorld.mojang.Mojang;
import com.MCWorld.mojang.MojangMessage;
import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.map.StockListAdapter;
import io.netty.handler.codec.http2.Http2CodecUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapPackActivity extends HTBaseActivity implements OnClickListener {
    private Activity aMn;
    private Boolean aTY = Boolean.valueOf(false);
    private StockListAdapter aZQ = null;
    private a aZR;
    private ListView aZh;
    private Button aZj;
    private Button aZk;
    private String aZo;
    private String aZp;
    private String aZq;
    private CallbackHandler aZr = new CallbackHandler(this) {
        final /* synthetic */ MapPackActivity aZS;

        {
            this.aZS = this$0;
        }

        @MessageHandler(message = 260)
        public void onRecvInventory(boolean succ) {
            this.aZS.cs(false);
            if (succ && Mojang.instance().getLevel() != null) {
                List<InventorySlot> slots = Mojang.instance().getLevel().getPlayer().getInventory();
                if (slots != null) {
                    for (InventorySlot slot : slots) {
                        if (slot.getContents().getTypeId() != Http2CodecUtil.MAX_UNSIGNED_BYTE) {
                            this.aZS.arrayList.add(slot);
                        }
                    }
                    this.aZS.aZQ.notifyDataSetChanged();
                }
            }
        }

        @MessageHandler(message = 259)
        public void onWriteInventory(boolean succ) {
            if (succ) {
                this.aZS.refresh();
            }
        }

        @MessageHandler(message = 261)
        public void onDelInventory(boolean succ) {
            if (succ) {
                this.aZS.refresh();
            }
        }
    };
    private ArrayList<Object> arrayList = new ArrayList();

    private class a extends BroadcastReceiver {
        final /* synthetic */ MapPackActivity aZS;

        private a(MapPackActivity mapPackActivity) {
            this.aZS = mapPackActivity;
        }

        public void onReceive(Context ctx, Intent in) {
            byte id = in.getByteExtra("id", new Integer(0).byteValue());
            Iterator it = this.aZS.arrayList.iterator();
            while (it.hasNext()) {
                InventorySlot obj = it.next();
                if (obj.getSlot() == id) {
                    this.aZS.arrayList.remove(obj);
                    break;
                }
            }
            this.aZS.aZQ.notifyDataSetChanged();
            try {
                Mojang.instance().deleteInventory(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(MojangMessage.class, this.aZr);
        setContentView((int) R.layout.activity_map_pack);
        ej("背包");
        this.aMn = this;
        this.aZo = this.aMn.getResources().getString(R.string.map_del_slot);
        this.aZp = this.aMn.getResources().getString(R.string.map_add_slot);
        this.aZq = this.aMn.getResources().getString(R.string.cancle);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.aZQ = new StockListAdapter(this, this.arrayList);
        this.aZh.setAdapter(this.aZQ);
        this.aZj = (Button) findViewById(R.id.btn_del);
        this.aZk = (Button) findViewById(R.id.btn_add);
        this.aZj.setOnClickListener(this);
        this.aZk.setOnClickListener(this);
        this.aZj.setText(this.aZo);
        this.aZk.setText(this.aZp);
        this.aZR = new a();
        i.o(this.aZR);
        init();
    }

    public void onDestroy() {
        super.onDestroy();
        i.unregisterReceiver(this.aZR);
        EventNotifyCenter.remove(this.aZr);
    }

    public void onPause() {
        super.onPause();
        this.aTY = Boolean.valueOf(false);
        this.aZj.setText(this.aZo);
        this.aZQ.b(Boolean.valueOf(false));
        this.aZQ.notifyDataSetChanged();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_del) {
            Ii();
        } else if (id == R.id.btn_add) {
            c.j(this.aMn);
        }
    }

    private void init() {
        cs(true);
        Mojang.instance().asynReadInventory();
    }

    private void refresh() {
        this.arrayList.clear();
        List<InventorySlot> slots = Mojang.instance().getPlayer().getInventory();
        if (slots != null) {
            for (InventorySlot slot : slots) {
                if (slot.getContents().getTypeId() != Http2CodecUtil.MAX_UNSIGNED_BYTE) {
                    this.arrayList.add(slot);
                }
            }
        }
        this.aZQ.notifyDataSetChanged();
    }

    private void Ii() {
        this.aTY = Boolean.valueOf(!this.aTY.booleanValue());
        if (this.aZj.getText().toString().equals(this.aZo)) {
            this.aZj.setText(this.aZq);
        } else {
            this.aZj.setText(this.aZo);
        }
        if (this.aZQ != null) {
            this.aZQ.b(this.aTY);
        }
        this.aZQ.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode) {
            refresh();
        }
    }
}
