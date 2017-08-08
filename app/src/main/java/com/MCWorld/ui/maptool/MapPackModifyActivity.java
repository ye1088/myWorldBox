package com.MCWorld.ui.maptool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.Mojang;
import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.aj;
import java.util.ArrayList;
import java.util.List;

public class MapPackModifyActivity extends HTBaseActivity implements OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener {
    private Activity aMn;
    private ImageView aZA;
    private TextView aZB;
    private TextView aZC;
    private Button aZF;
    private TextView aZX;
    private InventorySlot aZY;
    private Button aZy;
    private SeekBar aZz;
    private int cur;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_modpack);
        ej("背包物品修改");
        this.aMn = this;
        InventorySlot slot = (InventorySlot) getIntent().getSerializableExtra("InventorySlot");
        this.aZY = new InventorySlot(slot.getSlot(), new ItemStack(slot.getContents().getTypeId(), slot.getContents().getDurability(), slot.getContents().getAmount()));
        this.aZy = (Button) findViewById(R.id.btn_color);
        this.aZy.setOnClickListener(this);
        this.aZz = (SeekBar) findViewById(R.id.seekbar);
        this.aZz.setOnSeekBarChangeListener(this);
        this.aZA = (ImageView) findViewById(R.id.iv_logo);
        this.aZB = (TextView) findViewById(R.id.tv_title);
        this.aZX = (TextView) findViewById(R.id.tv_id);
        this.aZX.setVisibility(0);
        this.aZC = (TextView) findViewById(R.id.tv_number_val);
        this.aZF = (Button) findViewById(R.id.btn_save);
        this.aZF.setOnClickListener(this);
        init();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            int val = this.cur - this.aZY.getContents().getAmount();
            List<InventorySlot> slots = new ArrayList();
            this.aZY.getContents().setAmount(val);
            slots.add(this.aZY);
            Mojang.instance().asynAddInventory(slots);
            this.aMn.setResult(100);
            this.aMn.finish();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int arg1, boolean arg2) {
        if (seekBar.getId() == R.id.seekbar) {
            if (seekBar.getProgress() < 1) {
                seekBar.setProgress(1);
            }
            this.cur = seekBar.getProgress();
            this.aZC.setText(String.valueOf(this.cur));
        }
    }

    public void onStartTrackingTouch(SeekBar arg0) {
    }

    public void onStopTrackingTouch(SeekBar arg0) {
    }

    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
    }

    private void init() {
        this.cur = this.aZY.getContents().getAmount();
        this.aZC.setText(String.valueOf(this.cur));
        this.aZz.setProgress(this.cur);
        ItemStack item = this.aZY.getContents();
        String title = aj.aD(item.getTypeId(), item.getDurability());
        String logoName = aj.aE(item.getTypeId(), item.getDurability());
        this.aZB.setText(title);
        this.aZX.setText("[ID: " + item.getTypeId() + "]");
        int logoID = this.aMn.getResources().getIdentifier(logoName, "drawable", this.aMn.getPackageName());
        if (logoID != 0) {
            this.aZA.setImageResource(logoID);
        }
    }
}
