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
import com.MCWorld.mojang.Mojang;
import com.MCWorld.mojang.entity.Entity;
import com.MCWorld.mojang.entity.EntityItem;
import com.MCWorld.mojang.entity.EntityType;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.map.AddEntityItemListAdapter;
import com.MCWorld.utils.aj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapEntityAddActivity extends HTBaseActivity implements OnClickListener {
    private Activity aMn;
    private ListView aZh;
    private Button aZk;
    private AddEntityItemListAdapter aZt = null;
    private HashMap<Integer, Entity> aZu = new HashMap();
    private BroadcastReceiver aZv;
    private ArrayList<Object> arrayList = new ArrayList();

    private class a extends BroadcastReceiver {
        final /* synthetic */ MapEntityAddActivity aZw;

        private a(MapEntityAddActivity mapEntityAddActivity) {
            this.aZw = mapEntityAddActivity;
        }

        public void onReceive(Context ctx, Intent in) {
            Entity item = (Entity) in.getSerializableExtra("item");
            int type = in.getIntExtra("type", 0);
            if (item != null) {
                if (type == 0) {
                    this.aZw.aZu.remove(Integer.valueOf(item.getEntityTypeId()));
                } else {
                    this.aZw.aZu.put(Integer.valueOf(item.getEntityTypeId()), item);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_addpack);
        ej("添加生物");
        this.aMn = this;
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.aZt = new AddEntityItemListAdapter(this, this.arrayList);
        this.aZh.setAdapter(this.aZt);
        this.aZk = (Button) findViewById(R.id.btn_add);
        this.aZk.setOnClickListener(this);
        this.aZv = new a();
        i.q(this.aZv);
        init();
    }

    public void onDestroy() {
        super.onDestroy();
        i.unregisterReceiver(this.aZv);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_add && !this.aZu.isEmpty()) {
            Map<EntityType, Integer> result = new HashMap();
            for (Entry<Integer, Entity> entry : this.aZu.entrySet()) {
                result.put(((Entity) entry.getValue()).getEntityType(), Integer.valueOf(1));
            }
            Mojang.instance().asynBatchAddEntity(result);
            this.aMn.setResult(100);
            this.aMn.finish();
        }
    }

    private void init() {
        List<EntityItem> items = aj.Mw();
        List<EntityItem> result = new ArrayList();
        if (Mojang.instance().getLevel() != null) {
            List<Entity> entities = Mojang.instance().getLevel().getEntities();
            if (entities != null) {
                for (EntityItem item : items) {
                    boolean containes = true;
                    for (Entity entity : entities) {
                        if (entity.getEntityTypeId() == item.getEntity().getEntityTypeId()) {
                            containes = false;
                            break;
                        }
                    }
                    if (containes && EntityType.shouldUsed(item.getEntity().getEntityType())) {
                        result.add(item);
                    }
                }
            }
        }
        this.arrayList.addAll(result);
        this.aZt.notifyDataSetChanged();
    }
}
