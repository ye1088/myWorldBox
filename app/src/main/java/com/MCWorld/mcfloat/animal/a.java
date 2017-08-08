package com.MCWorld.mcfloat.animal;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.utils.at;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AnimalEquipmentPopWin */
public class a {
    private OnItemClickListener Pz = new OnItemClickListener(this) {
        final /* synthetic */ a aae;

        {
            this.aae = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (this.aae.ZY != null) {
                this.aae.ZY.dismiss();
            }
            if (this.aae.ZX != null && position >= 0 && position < this.aae.aab.size()) {
                this.aae.ZX.a(this.aae.aac, (ItemStack) this.aae.aab.get(position));
            }
        }
    };
    private a ZX;
    private PopupWindow ZY;
    private GridView ZZ;
    private PopEquipmentAdapter aaa;
    private List<ItemStack> aab;
    private int aac;
    private OnDismissListener aad = new OnDismissListener(this) {
        final /* synthetic */ a aae;

        {
            this.aae = this$0;
        }

        public void onDismiss() {
            if (this.aae.ZX != null) {
                this.aae.ZX.onDismiss();
            }
        }
    };
    private Context mContext;
    private View mView;

    /* compiled from: AnimalEquipmentPopWin */
    public interface a {
        void a(int i, ItemStack itemStack);

        void onDismiss();
    }

    public a(Context context, a cb) {
        this.mContext = context;
        this.ZX = cb;
        this.aab = new ArrayList();
    }

    public void a(List<ItemStack> data, int equipmentIndex, boolean isClear) {
        if (isClear) {
            this.aab.clear();
        }
        this.aac = equipmentIndex;
        this.aab.addAll(data);
    }

    public void showAtLocation(View parent, int gravity, int xOffset, int yOffset) {
        tS();
        this.ZY.showAtLocation(parent, gravity, xOffset, yOffset);
        this.aaa.c(this.aab, true);
    }

    public void showAsDropDown(View parent, int xOffset, int yOffset) {
        tS();
        this.ZY.showAsDropDown(parent, xOffset, yOffset);
        this.aaa.c(this.aab, true);
    }

    private void tS() {
        if (this.ZY == null) {
            this.mView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.lyt_pop_animal_strengthen_equipment, null);
            this.ZZ = (GridView) this.mView.findViewById(R.id.gridVAnimalPopEquipment);
            this.aaa = new PopEquipmentAdapter(this.mContext);
            this.ZZ.setAdapter(this.aaa);
            this.ZZ.setOnItemClickListener(this.Pz);
            this.ZY = new PopupWindow(this.mView, at.dipToPx(this.mContext, 280), -2);
            this.ZY.setOnDismissListener(this.aad);
        }
        this.ZY.setFocusable(true);
        this.ZY.setOutsideTouchable(true);
        this.ZY.setBackgroundDrawable(new BitmapDrawable());
    }
}
