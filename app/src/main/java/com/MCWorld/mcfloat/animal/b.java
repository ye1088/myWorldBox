package com.MCWorld.mcfloat.animal;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.r;
import com.MCWorld.widget.EditTextWithDelete;

/* compiled from: AnimalStrengthen */
public class b {
    private View Pu;
    private OnSeekBarChangeListener Qn = new OnSeekBarChangeListener(this) {
        final /* synthetic */ b aaF;

        {
            this.aaF = this$0;
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.aaF.aaj.setText(String.valueOf(progress + 1));
            this.aaF.aat = progress + 1;
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    private boolean aaA = false;
    private a aaB;
    private OnClickListener aaC = new OnClickListener(this) {
        final /* synthetic */ b aaF;

        {
            this.aaF = this$0;
        }

        public void onClick(View v) {
            ((GradientDrawable) this.aaF.aai.getBackground()).setColor(c.aaG[((Integer) v.getTag()).intValue()]);
            this.aaF.aag.setTextColor(c.aaG[((Integer) v.getTag()).intValue()]);
            if (((TextView) v).getText().toString().length() > 0) {
                this.aaF.aas = null;
                this.aaF.aag.setTextColor(-16777216);
                this.aaF.aai.setText("无");
            } else {
                this.aaF.aai.setText("");
                this.aaF.aas = c.aaH[((Integer) v.getTag()).intValue()];
            }
            this.aaF.aak.setVisibility(8);
            this.aaF.aaA = false;
        }
    };
    private OnClickListener aaD = new OnClickListener(this) {
        final /* synthetic */ b aaF;

        {
            this.aaF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvAnimalStrengthenHelmet:
                    this.aaF.aam.setBackgroundResource(R.drawable.bg_animal_strengthen_green_box);
                    this.aaF.aaB.a(c.ub(), 1, true);
                    this.aaF.aaB.showAsDropDown(this.aaF.aal, 0, 10);
                    return;
                case R.id.tvAnimalStrengthenArmors:
                    this.aaF.aan.setBackgroundResource(R.drawable.bg_animal_strengthen_green_box);
                    this.aaF.aaB.a(c.uc(), 2, true);
                    this.aaF.aaB.showAsDropDown(this.aaF.aal, 0, 10);
                    return;
                case R.id.tvAnimalStrengthenPants:
                    this.aaF.aao.setBackgroundResource(R.drawable.bg_animal_strengthen_green_box);
                    this.aaF.aaB.a(c.ud(), 3, true);
                    this.aaF.aaB.showAsDropDown(this.aaF.aal, 0, 10);
                    return;
                case R.id.tvAnimalStrengthenShoes:
                    this.aaF.aap.setBackgroundResource(R.drawable.bg_animal_strengthen_green_box);
                    this.aaF.aaB.a(c.ue(), 4, true);
                    this.aaF.aaB.showAsDropDown(this.aaF.aal, 0, 10);
                    return;
                case R.id.tvHandEquipment:
                    this.aaF.aaq.setBackgroundResource(R.drawable.bg_animal_strengthen_green_box);
                    this.aaF.aaB.a(c.uf(), 5, true);
                    int[] location = new int[]{0, 0};
                    this.aaF.Pu.getLocationInWindow(location);
                    this.aaF.aaB.showAtLocation(this.aaF.Pu, 3, location[0], location[1]);
                    return;
                default:
                    return;
            }
        }
    };
    private com.MCWorld.mcfloat.animal.a.a aaE = new com.MCWorld.mcfloat.animal.a.a(this) {
        final /* synthetic */ b aaF;

        {
            this.aaF = this$0;
        }

        public void a(int equipmentIndex, ItemStack equipmentItem) {
            switch (equipmentIndex) {
                case 1:
                    this.aaF.aav = equipmentItem.getItemId();
                    this.aaF.aam.setImageDrawable(this.aaF.mContext.getResources().getDrawable(this.aaF.aav == 0 ? R.drawable.ico_animal_strengthen_helmet : equipmentItem.getItemImgId()));
                    return;
                case 2:
                    this.aaF.aaw = equipmentItem.getItemId();
                    this.aaF.aan.setImageDrawable(this.aaF.mContext.getResources().getDrawable(this.aaF.aaw == 0 ? R.drawable.ico_animal_strengthen_armors : equipmentItem.getItemImgId()));
                    return;
                case 3:
                    this.aaF.aax = equipmentItem.getItemId();
                    this.aaF.aao.setImageDrawable(this.aaF.mContext.getResources().getDrawable(this.aaF.aax == 0 ? R.drawable.ico_animal_strengthen_pants : equipmentItem.getItemImgId()));
                    return;
                case 4:
                    this.aaF.aay = equipmentItem.getItemId();
                    this.aaF.aap.setImageDrawable(this.aaF.mContext.getResources().getDrawable(this.aaF.aay == 0 ? R.drawable.ico_animal_strengthen_shoes : equipmentItem.getItemImgId()));
                    return;
                case 5:
                    this.aaF.aau = equipmentItem.getItemId();
                    this.aaF.aaq.setImageDrawable(this.aaF.mContext.getResources().getDrawable(this.aaF.aau == 0 ? R.drawable.ico_animal_strengthen_sword : equipmentItem.getItemImgId()));
                    return;
                default:
                    return;
            }
        }

        public void onDismiss() {
            this.aaF.ua();
        }
    };
    private ViewAnimator aaf;
    private EditTextWithDelete aag;
    private EditTextWithDelete aah;
    private TextView aai;
    private TextView aaj;
    private LinearLayout aak;
    private View aal;
    private ImageView aam;
    private ImageView aan;
    private ImageView aao;
    private ImageView aap;
    private ImageView aaq;
    private ItemStack aar;
    private String aas;
    private int aat = 1;
    private int aau;
    private int aav;
    private int aaw;
    private int aax;
    private int aay;
    private a aaz;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ b aaF;

        {
            this.aaF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvAnimalStrengthen:
                    this.aaF.aaf.setDisplayedChild(0);
                    return;
                case R.id.tvAnimalEquipment:
                    this.aaF.aaf.setDisplayedChild(1);
                    return;
                case R.id.tvAnimalNameColor:
                    this.aaF.tZ();
                    return;
                case R.id.tvAnimalStrengthenAdd:
                    this.aaF.tW();
                    return;
                case R.id.tvAnimalStrengthenCancle:
                    this.aaF.tX();
                    return;
                default:
                    return;
            }
        }
    };
    private Context mContext;

    /* compiled from: AnimalStrengthen */
    public interface a {
        void qS();
    }

    public b(View view, Context context, a cb) {
        this.Pu = view;
        this.mContext = context;
        this.aaz = cb;
        init();
    }

    public void a(ItemStack animal) {
        this.aar = animal;
        this.aag.setText(this.aar.getItemName());
        boolean isCanWearEquipment = false;
        for (int id : c.aaI) {
            if (animal.getItemId() == id) {
                isCanWearEquipment = true;
                this.Pu.findViewById(R.id.llyAnimalStrengthenMenu).setVisibility(0);
                break;
            }
        }
        if (isCanWearEquipment) {
            this.Pu.findViewById(R.id.llyStrengthenSlidingTabStripLeft).setVisibility(0);
            this.Pu.findViewById(R.id.llyStrengthenSlidingTabStripRight).setVisibility(0);
        } else {
            this.Pu.findViewById(R.id.llyAnimalStrengthenMenu).setVisibility(8);
            this.Pu.findViewById(R.id.llyStrengthenSlidingTabStripLeft).setVisibility(8);
            this.Pu.findViewById(R.id.llyStrengthenSlidingTabStripRight).setVisibility(8);
        }
        this.aaf.setDisplayedChild(0);
    }

    private void init() {
        this.aay = 0;
        this.aax = 0;
        this.aaw = 0;
        this.aav = 0;
        this.aau = 0;
        this.Pu.findViewById(R.id.tvAnimalStrengthen).setOnClickListener(this.mClickListener);
        this.Pu.findViewById(R.id.tvAnimalEquipment).setOnClickListener(this.mClickListener);
        this.Pu.findViewById(R.id.tvAnimalStrengthenAdd).setOnClickListener(this.mClickListener);
        this.Pu.findViewById(R.id.tvAnimalStrengthenCancle).setOnClickListener(this.mClickListener);
        this.aaf = (ViewAnimator) this.Pu.findViewById(R.id.animatorStrengthen);
        this.aag = (EditTextWithDelete) this.Pu.findViewById(R.id.etStrengthenAnimalName);
        this.aah = (EditTextWithDelete) this.Pu.findViewById(R.id.etStrengthenBloodValue);
        this.aai = (TextView) this.Pu.findViewById(R.id.tvAnimalNameColor);
        this.aai.setOnClickListener(this.mClickListener);
        this.aaj = (TextView) this.Pu.findViewById(R.id.tvStrengthenNumber);
        ((SeekBar) this.Pu.findViewById(R.id.SeekStrengthenNumber)).setOnSeekBarChangeListener(this.Qn);
        tT();
        tU();
        this.aaB = new a(this.mContext, this.aaE);
    }

    private void tT() {
        this.aak = (LinearLayout) this.Pu.findViewById(R.id.llyColorList);
        LinearLayout ll = tV();
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        LayoutParams params = new LayoutParams(UtilsScreen.dipToPx(this.mContext, 40), UtilsScreen.dipToPx(this.mContext, 40));
        int lrMargin = UtilsScreen.dipToPx(this.mContext, 5);
        int tdMargin = UtilsScreen.dipToPx(this.mContext, 2);
        params.setMargins(lrMargin, tdMargin, lrMargin, tdMargin);
        int i = 0;
        while (i < c.aaG.length) {
            TextView textView = (TextView) inflater.inflate(R.layout.view_float_animal_strengthen_color_circle, null);
            ((GradientDrawable) textView.getBackground()).setColor(c.aaG[i]);
            textView.setGravity(17);
            textView.setTag(Integer.valueOf(i));
            if (i == c.aaG.length - 1) {
                textView.setText("无");
            }
            textView.setOnClickListener(this.aaC);
            textView.setLayoutParams(params);
            ll.addView(textView);
            if (ll.getChildCount() == 5 || i == c.aaG.length - 1) {
                this.aak.addView(ll, -1, -2);
                ll = tV();
            }
            i++;
        }
    }

    private void tU() {
        this.aam = (ImageView) this.Pu.findViewById(R.id.tvAnimalStrengthenHelmet);
        this.aan = (ImageView) this.Pu.findViewById(R.id.tvAnimalStrengthenArmors);
        this.aao = (ImageView) this.Pu.findViewById(R.id.tvAnimalStrengthenPants);
        this.aap = (ImageView) this.Pu.findViewById(R.id.tvAnimalStrengthenShoes);
        this.aaq = (ImageView) this.Pu.findViewById(R.id.tvHandEquipment);
        this.aam.setOnClickListener(this.aaD);
        this.aan.setOnClickListener(this.aaD);
        this.aao.setOnClickListener(this.aaD);
        this.aap.setOnClickListener(this.aaD);
        this.aaq.setOnClickListener(this.aaD);
        this.aal = this.Pu.findViewById(R.id.tvAnimalStrengthenLoricaeText);
    }

    private LinearLayout tV() {
        LinearLayout ll = new LinearLayout(this.mContext);
        ll.setOrientation(0);
        int padding = UtilsScreen.dipToPx(this.mContext, 3);
        ll.setPadding(0, padding, 0, padding);
        ll.setGravity(3);
        return ll;
    }

    private void tW() {
        int bloodValue;
        String name = this.aag.getText().toString();
        if (this.aas == null) {
            if (name.equals(this.aar.getItemName())) {
                name = null;
            }
        } else if (name.length() == 0) {
            name = this.aas + this.aar.getItemName();
        } else {
            name = this.aas + name;
        }
        String strBloodValue = this.aah.getText().toString();
        try {
            if (strBloodValue.contains(".")) {
                strBloodValue = strBloodValue.substring(0, strBloodValue.indexOf(46));
            }
            bloodValue = Integer.parseInt(strBloodValue);
        } catch (Exception e) {
            HLog.verbose("Exception", e.getMessage(), new Object[0]);
            bloodValue = 8;
        }
        h.a(this.aar.getItemId(), this.aat, name, bloodValue, this.aav, this.aaw, this.aax, this.aay, this.aau);
        r.ck().j(hlx.data.tongji.a.bOD, String.valueOf(this.aar.getItemId()));
        if (this.aav != 0) {
            r.ck().j(hlx.data.tongji.a.bOE, String.valueOf(this.aav));
        }
        if (this.aaw != 0) {
            r.ck().j(hlx.data.tongji.a.bOF, String.valueOf(this.aaw));
        }
        if (this.aax != 0) {
            r.ck().j(hlx.data.tongji.a.bOG, String.valueOf(this.aax));
        }
        if (this.aay != 0) {
            r.ck().j(hlx.data.tongji.a.bOH, String.valueOf(this.aay));
        }
        if (this.aau != 0) {
            r.ck().j(hlx.data.tongji.a.bOI, String.valueOf(this.aau));
        }
        if (this.aaz != null) {
            this.aaz.qS();
        }
        tY();
    }

    private void tX() {
        if (this.aaz != null) {
            this.aaz.qS();
        }
        tY();
    }

    private void tY() {
        ((GradientDrawable) this.aai.getBackground()).setColor(c.aaG[c.aaG.length - 1]);
        this.aai.setText("无");
        this.aag.setTextColor(-16777216);
        this.aas = null;
        this.aay = 0;
        this.aax = 0;
        this.aaw = 0;
        this.aav = 0;
        this.aau = 0;
        this.aam.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_animal_strengthen_helmet));
        this.aan.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_animal_strengthen_armors));
        this.aao.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_animal_strengthen_pants));
        this.aap.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_animal_strengthen_shoes));
        this.aaq.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ico_animal_strengthen_sword));
    }

    private void tZ() {
        this.aaA = !this.aaA;
        if (this.aaA) {
            this.aak.setVisibility(0);
        } else {
            this.aak.setVisibility(8);
        }
    }

    private void ua() {
        this.aam.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
        this.aan.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
        this.aao.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
        this.aap.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
        this.aaq.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
    }
}
