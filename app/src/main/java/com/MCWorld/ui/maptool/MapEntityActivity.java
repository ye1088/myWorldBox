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
import com.MCWorld.mojang.entity.Entity;
import com.MCWorld.mojang.entity.EntityItem;
import com.MCWorld.mojang.entity.EntityType;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.map.EntityItemListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapEntityActivity extends HTBaseActivity implements OnClickListener {
    private Activity aMn;
    private Boolean aTY = Boolean.valueOf(false);
    private ListView aZh;
    private EntityItemListAdapter aZi = null;
    private Button aZj;
    private Button aZk;
    private Map<Integer, List<Entity>> aZl = new HashMap();
    private a aZm;
    private List<EntityType> aZn = new ArrayList();
    private String aZo;
    private String aZp;
    private String aZq;
    private CallbackHandler aZr = new CallbackHandler(this) {
        final /* synthetic */ MapEntityActivity aZs;

        {
            this.aZs = this$0;
        }

        @MessageHandler(message = 256)
        public void onRecvEntity(boolean succ) {
            this.aZs.cs(false);
            if (succ) {
                try {
                    List<EntityItem> entityItems = this.aZs.getItems();
                    if (entityItems.size() != 0) {
                        this.aZs.arrayList.clear();
                        this.aZs.arrayList.addAll(entityItems);
                        this.aZs.aZi.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @MessageHandler(message = 257)
        public void onWriteEntity(boolean succ) {
            if (succ) {
                this.aZs.refresh();
            }
        }

        @MessageHandler(message = 258)
        public void onDelEntity(boolean succ) {
            this.aZs.cs(false);
            if (succ) {
                this.aZs.refresh();
            }
        }
    };
    private ArrayList<Object> arrayList = new ArrayList();

    private class a extends BroadcastReceiver {
        final /* synthetic */ MapEntityActivity aZs;

        private a(MapEntityActivity mapEntityActivity) {
            this.aZs = mapEntityActivity;
        }

        public void onReceive(Context ctx, Intent in) {
            EntityItem item = (EntityItem) in.getSerializableExtra("item");
            Iterator it = this.aZs.arrayList.iterator();
            while (it.hasNext()) {
                EntityItem obj = it.next();
                if (obj.getEntity().getEntityTypeId() == item.getEntity().getEntityTypeId()) {
                    this.aZs.arrayList.remove(obj);
                    break;
                }
            }
            this.aZs.aZi.notifyDataSetChanged();
            this.aZs.aZn.add(item.getEntity().getEntityType());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(MojangMessage.class, this.aZr);
        setContentView((int) R.layout.activity_map_pack);
        ej("生物");
        this.aMn = this;
        this.aZo = this.aMn.getResources().getString(R.string.map_del_entity);
        this.aZp = this.aMn.getResources().getString(R.string.map_add_entity);
        this.aZq = this.aMn.getResources().getString(R.string.cancle);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.aZi = new EntityItemListAdapter(this, this.arrayList);
        this.aZh.setAdapter(this.aZi);
        this.aZj = (Button) findViewById(R.id.btn_del);
        this.aZk = (Button) findViewById(R.id.btn_add);
        this.aZj.setOnClickListener(this);
        this.aZk.setOnClickListener(this);
        this.aZj.setText(this.aZo);
        this.aZk.setText(this.aZp);
        this.aZm = new a();
        i.r(this.aZm);
        init();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aZr);
        i.unregisterReceiver(this.aZm);
    }

    public void onPause() {
        super.onPause();
        this.aTY = Boolean.valueOf(false);
        this.aZj.setText(this.aZo);
        this.aZi.b(Boolean.valueOf(false));
        this.aZi.notifyDataSetChanged();
        if (!this.aZn.isEmpty()) {
            Mojang.instance().asynBatchDeleteEntity(this.aZn);
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_del) {
            Ii();
        } else if (id == R.id.btn_add) {
            c.k(this.aMn);
        }
    }

    private void init() {
        cs(true);
        Mojang.instance().asynReadEntityData();
    }

    private void refresh() {
        this.arrayList.clear();
        try {
            List<EntityItem> entityItems = getItems();
            if (entityItems.size() != 0) {
                this.arrayList.addAll(entityItems);
            }
            this.aZi.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<EntityItem> getItems() throws Exception {
        this.aZl.clear();
        if (Mojang.instance().getLevel() == null) {
            throw new Exception("get level null！");
        }
        for (Entity entity : Mojang.instance().getLevel().getEntities()) {
            Integer key = Integer.valueOf(entity.getEntityTypeId());
            if (this.aZl.containsKey(key)) {
                ((List) this.aZl.get(key)).add(entity);
            } else {
                List<Entity> newTypeList = new ArrayList();
                newTypeList.add(entity);
                this.aZl.put(Integer.valueOf(entity.getEntityTypeId()), newTypeList);
            }
        }
        List<EntityItem> entityItems = new ArrayList();
        for (Entry<Integer, List<Entity>> entry : this.aZl.entrySet()) {
            List<Entity> vals = (List) entry.getValue();
            entityItems.add(new EntityItem(vals.size(), (Entity) vals.get(0)));
        }
        return entityItems;
    }

    private void Ii() {
        this.aTY = Boolean.valueOf(!this.aTY.booleanValue());
        if (this.aZj.getText().toString().equals(this.aZo)) {
            this.aZj.setText(this.aZq);
        } else {
            this.aZj.setText(this.aZo);
        }
        if (this.aZi != null) {
            this.aZi.b(this.aTY);
        }
        this.aZi.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode) {
            refresh();
        }
    }
}
