package com.huluxia.ui.maptool;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.huluxia.framework.R;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.converter.InventorySlot;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.AddStockListAdapter;
import com.huluxia.utils.aj;
import com.huluxia.utils.at;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class MapPackAddActivity extends HTBaseActivity implements OnClickListener {
    private Activity aMn;
    private AddStockListAdapter aZT = null;
    private BroadcastReceiver aZU;
    private List<ItemStack> aZV = new ArrayList();
    private ListView aZh;
    private Button aZk;
    private HashMap<String, ItemStack> aZu = new HashMap();
    private ArrayList<Object> arrayList = new ArrayList();

    private class a extends BroadcastReceiver {
        final /* synthetic */ MapPackAddActivity aZW;

        private a(MapPackAddActivity mapPackAddActivity) {
            this.aZW = mapPackAddActivity;
        }

        public void onReceive(Context ctx, Intent in) {
            ItemStack item = (ItemStack) in.getSerializableExtra("item");
            int type = in.getIntExtra("type", 0);
            if (item != null) {
                String key = item.getTypeId() + "_" + item.getDurability();
                if (type == 0) {
                    this.aZW.aZu.remove(key);
                } else {
                    this.aZW.aZu.put(key, item);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_addpack);
        ct(true);
        this.aMn = this;
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.aZT = new AddStockListAdapter(this, this.arrayList);
        this.aZh.setAdapter(this.aZT);
        this.aZk = (Button) findViewById(R.id.btn_add);
        this.aZk.setOnClickListener(this);
        this.aZU = new a();
        i.p(this.aZU);
        at.hideInputMethod(findViewById(R.id.edtSearch));
        init();
        Ik();
    }

    public void onDestroy() {
        super.onDestroy();
        i.unregisterReceiver(this.aZU);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add) {
            if (!this.aZu.isEmpty()) {
                try {
                    List<InventorySlot> slots = Mojang.instance().readInventory();
                    if (slots != null) {
                        HashSet<Byte> slotSet = new HashSet();
                        for (InventorySlot slot : slots) {
                            slotSet.add(Byte.valueOf(slot.getSlot()));
                        }
                        byte maxslot = new Integer(127).byteValue();
                        List<InventorySlot> newSlots = new ArrayList();
                        for (Entry<String, ItemStack> entry : this.aZu.entrySet()) {
                            for (byte b = (byte) 0; b <= maxslot; b = (byte) (b + 1)) {
                                if (!slotSet.contains(Byte.valueOf(b))) {
                                    newSlots.add(new InventorySlot(b, (ItemStack) entry.getValue()));
                                    slotSet.add(Byte.valueOf(b));
                                    break;
                                }
                            }
                        }
                        Mojang.instance().asynAddInventory(newSlots);
                        this.aMn.setResult(100);
                        this.aMn.finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (id == R.id.imgClear) {
            clear();
        } else if (id == R.id.imgSearch) {
            Il();
        }
    }

    private void init() {
        this.aZV = aj.Mi();
        this.arrayList.addAll(this.aZV);
        this.aZT.notifyDataSetChanged();
    }

    private void Ik() {
        this.aIZ.setOnClickListener(this);
        this.aJa.setOnClickListener(this);
        this.aJa.setClickable(false);
        this.aIY.addTextChangedListener(new TextWatcher(this) {
            private CharSequence aEo;
            final /* synthetic */ MapPackAddActivity aZW;

            {
                this.aZW = this$0;
            }

            public void afterTextChanged(Editable s) {
                if (this.aEo.length() > 0) {
                    this.aZW.aJa.setImageResource(R.drawable.btn_search_selector);
                    this.aZW.aJa.setClickable(true);
                    this.aZW.aIY.setTextColor(this.aZW.aMn.getResources().getColor(R.color.black));
                    this.aZW.aIZ.setVisibility(0);
                    return;
                }
                this.aZW.aJa.setImageResource(R.drawable.ic_search_unactive);
                this.aZW.aJa.setClickable(false);
                this.aZW.aIY.setTextColor(this.aZW.aMn.getResources().getColor(R.color.search_bar_gray_txt));
                this.aZW.aIZ.setVisibility(4);
                at.hideInputMethod(this.aZW.aIY);
            }

            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                this.aEo = s;
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    private void Il() {
        String key = this.aIY.getText().toString().trim();
        if (key.length() < 1) {
            t.l(this, "搜索条件不能为空");
        } else if (this.aZV != null) {
            this.arrayList.clear();
            List<ItemStack> resultList = new ArrayList();
            for (ItemStack item : this.aZV) {
                if (aj.aD(item.getTypeId(), item.getDurability()).contains(key)) {
                    resultList.add(item);
                }
            }
            if (resultList.size() > 0) {
                this.arrayList.addAll(resultList);
            }
            this.aZT.notifyDataSetChanged();
        }
    }

    private void clear() {
        this.aIY.getEditableText().clear();
        this.aIY.getEditableText().clearSpans();
        this.aIY.setText("");
        this.aJa.setImageResource(R.drawable.ic_search_unactive);
        at.hideInputMethod(this.aIY);
        if (this.arrayList != null && this.aZV != null && this.arrayList.size() != this.aZV.size()) {
            this.arrayList.clear();
            this.arrayList.addAll(this.aZV);
            this.aZT.notifyDataSetChanged();
        }
    }
}
