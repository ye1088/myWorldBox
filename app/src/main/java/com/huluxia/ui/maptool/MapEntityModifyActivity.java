package com.huluxia.ui.maptool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.entity.Animal;
import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.EntityItem;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.aj;
import java.io.IOException;

public class MapEntityModifyActivity extends HTBaseActivity implements OnClickListener, OnSeekBarChangeListener {
    private Activity aMn;
    private ImageView aZA;
    private TextView aZB;
    private TextView aZC;
    private TextView aZD;
    private TextView aZE;
    private Button aZF;
    private EntityItem aZG;
    private Button aZx;
    private Button aZy;
    private SeekBar aZz;
    private int cur;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_modpack);
        ej("生物修改");
        this.aMn = this;
        this.aZG = (EntityItem) getIntent().getSerializableExtra("EntityItem");
        this.aZx = (Button) findViewById(R.id.btn_size);
        this.aZx.setOnClickListener(this);
        this.aZy = (Button) findViewById(R.id.btn_color);
        this.aZy.setOnClickListener(this);
        this.aZz = (SeekBar) findViewById(R.id.seekbar);
        this.aZz.setOnSeekBarChangeListener(this);
        this.aZA = (ImageView) findViewById(R.id.iv_logo);
        this.aZB = (TextView) findViewById(R.id.tv_title);
        this.aZC = (TextView) findViewById(R.id.tv_number_val);
        this.aZE = (TextView) findViewById(R.id.tv_color);
        this.aZD = (TextView) findViewById(R.id.tv_size);
        this.aZC.setText(String.valueOf(this.aZG.getNum()));
        this.aZF = (Button) findViewById(R.id.btn_save);
        this.aZF.setOnClickListener(this);
        init();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        int i = 1;
        int id = v.getId();
        if (id == R.id.btn_size) {
            if (this.aZx.getText().toString().equals(this.aMn.getResources().getString(R.string.be_big))) {
                this.aZx.setText(this.aMn.getResources().getString(R.string.be_small));
                try {
                    Mojang.instance().growup(this.aZG.getEntity().getEntityType(), false);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            this.aZx.setText(this.aMn.getResources().getString(R.string.be_big));
            try {
                Mojang.instance().growup(this.aZG.getEntity().getEntityType(), true);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else if (id != R.id.btn_color && id == R.id.btn_save) {
            if (this.cur >= 1) {
                i = this.cur;
            }
            this.cur = i;
            Mojang.instance().asynSetEntity(this.aZG.getEntity().getEntityType(), this.cur);
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

    private void init() {
        Entity entity = this.aZG.getEntity();
        this.cur = this.aZG.getNum();
        this.aZC.setText(String.valueOf(this.cur));
        this.aZz.setProgress(this.cur);
        String title = aj.lt(entity.getEntityTypeId());
        String logoName = aj.lu(entity.getEntityTypeId());
        this.aZB.setText(title);
        int logoID = this.aMn.getResources().getIdentifier(logoName, "drawable", this.aMn.getPackageName());
        if (logoID != 0) {
            this.aZA.setImageResource(logoID);
        }
        if (entity instanceof Animal) {
            this.aZD.setVisibility(0);
            this.aZx.setVisibility(0);
        }
    }
}
