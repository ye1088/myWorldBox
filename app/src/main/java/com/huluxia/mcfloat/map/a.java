package com.huluxia.mcfloat.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.utils.ae;
import com.huluxia.utils.j;
import com.huluxia.utils.v;

/* compiled from: MapBackupViewItem */
public class a {
    public static final String aco = "未备份地图";
    public static final String acp = "备份";
    public static final String acq = "删除";
    private int acf;
    private String acg;
    private String ach;
    private String aci;
    private Bitmap acj;
    private TextView ack;
    private ImageView acl;
    private TextView acm;
    private String acn;

    public a(int index, String backupMapName, String mapSnapshotName, String mapSnapshotName_old, String backupTime, Bitmap mapSnapshotBitmap, TextView timeTextView, ImageView snapshotImageView, TextView btnView) {
        this.acf = index;
        this.acg = backupMapName;
        this.ach = mapSnapshotName;
        this.acn = mapSnapshotName_old;
        this.aci = backupTime;
        this.acj = mapSnapshotBitmap;
        this.ack = timeTextView;
        this.acl = snapshotImageView;
        this.acm = btnView;
    }

    public void cq(String inputRootPath) {
        j.deleteFile(inputRootPath + this.acg);
        j.deleteFile(inputRootPath + this.ach);
    }

    public void a(View inputSelfView, String inputRootPath) {
        if (inputRootPath != null && inputSelfView != null) {
            if (j.isExist(inputRootPath + this.acg)) {
                if (this.acm != null) {
                    this.acm.setText("删除");
                }
                this.ack.setText(v.br(j.eR(inputRootPath + this.acg)));
                if (j.isExist(inputRootPath + this.ach)) {
                    this.acl.setImageBitmap(ae.getLocalBitMap(inputRootPath + this.ach));
                    return;
                }
                this.acl.setImageBitmap(BitmapFactory.decodeResource(inputSelfView.getContext().getResources(), R.drawable.backup_shot));
                return;
            }
            this.acl.setImageBitmap(BitmapFactory.decodeResource(inputSelfView.getContext().getResources(), R.drawable.backup_shot));
            this.ack.setText(aco);
            if (this.acm != null) {
                this.acm.setText(acp);
            }
        }
    }

    public int uA() {
        return this.acf;
    }

    public void fP(int mItemIndex) {
        this.acf = mItemIndex;
    }

    public String uB() {
        return this.acg;
    }

    public void cr(String mItemMapName) {
        this.acg = mItemMapName;
    }

    public String uC() {
        return this.ach;
    }

    public void cs(String mItemMapShot) {
        this.ach = mItemMapShot;
    }

    public String uD() {
        return this.aci;
    }

    public void ct(String mItemTime) {
        this.aci = mItemTime;
    }

    public Bitmap uE() {
        return this.acj;
    }

    public void o(Bitmap mItemBitMap) {
        this.acj = mItemBitMap;
    }

    public TextView uF() {
        return this.ack;
    }

    public void a(TextView mItemTimeTextView) {
        this.ack = mItemTimeTextView;
    }

    public ImageView uG() {
        return this.acl;
    }

    public void c(ImageView mItemImageView) {
        this.acl = mItemImageView;
    }

    public TextView uH() {
        return this.acm;
    }

    public void b(TextView mItemBtnView) {
        this.acm = mItemBtnView;
    }

    public String uI() {
        return this.acn;
    }

    public void cu(String mItemMapShot_old) {
        this.acn = mItemMapShot_old;
    }
}
