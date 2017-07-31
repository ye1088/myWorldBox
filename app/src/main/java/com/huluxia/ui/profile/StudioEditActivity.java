package com.huluxia.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.HTUploadInfo;
import com.huluxia.data.profile.c;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsCamera;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsImage;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.l;
import com.huluxia.module.aa;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.crop.CropImageActivity;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.r;
import com.huluxia.utils.y;
import com.huluxia.widget.dialog.i;
import com.huluxia.widget.listview.GridViewNotScroll;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import com.xiaomi.mipush.sdk.MiPushClient;
import hlx.module.resources.b;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class StudioEditActivity extends HTBaseLoadingActivity implements OnClickListener {
    private static final String TAG = "StudioEditActivity";
    public static final String bie = "STUDIO_INFO";
    public static final String bif = "IS_CREATE_STUDIO";
    private PaintView aGa;
    private DialogManager aSB;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ StudioEditActivity biL;

        {
            this.biL = this$0;
        }

        @MessageHandler(message = 771)
        public void onUploadImageRsp(String response, int index, Object context) {
            this.biL.cs(false);
            if (context.equals(this.biL.mContext) && response != null) {
                HTUploadInfo info = null;
                try {
                    info = (HTUploadInfo) Json.parseJsonObject(response, HTUploadInfo.class);
                } catch (Exception e) {
                    HLog.error(StudioEditActivity.TAG, "onUploadImageRsp response: " + response + "; error " + e.toString(), new Object[0]);
                }
                if (info == null || !info.isSucc() || info.getFid() == null || info.getFid().length() <= 10) {
                    this.biL.aIT.setEnabled(true);
                    t.n(this.biL.mContext, "图片上传失败");
                } else if (index == 0) {
                    this.biL.biB = info.getUrl();
                    this.biL.la(index + 1);
                } else if (1 == index) {
                    this.biL.biC = info.getUrl();
                    this.biL.la(index + 1);
                }
            }
        }

        @MessageHandler(message = 785)
        public void onUpdateStudioInfo(boolean succ, w info, int sid) {
            this.biL.aIT.setEnabled(true);
            this.biL.cs(false);
            if (succ) {
                t.o(this.biL.mContext, "修改工作室信息成功");
                this.biL.setResult(3);
                this.biL.finish();
                return;
            }
            this.biL.bip = false;
            if (info == null || info.code != 104) {
                t.n(this.biL.mContext, info == null ? this.biL.getString(m.str_network_not_capable) : info.msg);
                return;
            }
            i dia = new i(this.biL.mContext, null);
            dia.az(this.biL.getString(m.dialog_title_nick_change_comfirm), info.msg);
            dia.gK(this.biL.getString(m.btn_confirm));
            dia.showDialog();
        }

        @MessageHandler(message = 791)
        public void recviveResourceCate(boolean succ, b info) {
            if (succ && info != null) {
                this.biL.biF = info;
                switch (this.biL.axr) {
                    case 2:
                        this.biL.biE.b(info.jsCategoryList, true, false);
                        break;
                    case 3:
                        this.biL.biE.b(info.skinCategoryList, true, false);
                        break;
                    case 4:
                        this.biL.biE.b(info.woodCategoryList, true, false);
                        break;
                    default:
                        this.biL.biE.b(info.mapCategoryList, true, false);
                        break;
                }
                if (!this.biL.biK) {
                    this.biL.bin.fullScroll(33);
                    this.biL.biK = true;
                }
                this.biL.FC();
            } else if (this.biL.biF == null) {
                this.biL.FB();
            }
        }

        @MessageHandler(message = 804)
        public void recvCreateStudioMgs(boolean succ, w info) {
            if (info != null) {
                this.biL.cs(false);
                if (succ) {
                    this.biL.aIT.setEnabled(true);
                    if (UtilsFunction.empty(info.msg)) {
                        t.o(this.biL.mContext, "审核中，请耐心等待");
                    } else {
                        t.o(this.biL.mContext, info.msg);
                    }
                    this.biL.finish();
                    return;
                }
                this.biL.aIT.setEnabled(true);
                if (UtilsFunction.empty(info.msg)) {
                    t.n(this.biL.mContext, "提交失败，请重试");
                    return;
                } else {
                    t.n(this.biL.mContext, info.msg);
                    return;
                }
            }
            this.biL.cs(false);
            this.biL.aIT.setEnabled(true);
            t.n(this.biL.mContext, "提交失败，请重试");
        }
    };
    private int axr = 1;
    private int biA = 0;
    private String biB = "";
    private String biC = "";
    private GridViewNotScroll biD;
    private a biE;
    private b biF;
    private CommonMenuDialog biG = null;
    private CommonMenuDialogListener biH = new CommonMenuDialogListener(this) {
        final /* synthetic */ StudioEditActivity biL;

        {
            this.biL = this$0;
        }

        public void pressMenuById(int inIndex, Object object) {
            this.biL.biG.dismissDialog();
            if (inIndex != 1 || this.biL.biF == null) {
                if (inIndex != 2 || this.biL.biF == null) {
                    if (inIndex != 4 || this.biL.biF == null) {
                        if (inIndex == 3 && this.biL.biF != null && this.biL.axr != 3) {
                            this.biL.axr = 3;
                            this.biL.biE.b(this.biL.biF.skinCategoryList, true, true);
                            this.biL.bim.setText(m.skin);
                        }
                    } else if (this.biL.axr != 4) {
                        this.biL.axr = 4;
                        this.biL.biE.b(this.biL.biF.woodCategoryList, true, true);
                        this.biL.bim.setText(m.wood);
                    }
                } else if (this.biL.axr != 2) {
                    this.biL.axr = 2;
                    this.biL.biE.b(this.biL.biF.jsCategoryList, true, true);
                    this.biL.bim.setText(m.js);
                }
            } else if (this.biL.axr != 1) {
                this.biL.axr = 1;
                this.biL.bim.setText(m.map);
                this.biL.biE.b(this.biL.biF.mapCategoryList, true, true);
            }
        }
    };
    private OnLongClickListener biI = new OnLongClickListener(this) {
        final /* synthetic */ StudioEditActivity biL;

        {
            this.biL = this$0;
        }

        public boolean onLongClick(View v) {
            if (v.getId() == g.fl_studio_cover && !UtilsFunction.empty(this.biL.biy)) {
                this.biL.biA = 1;
                Uri fromUri = Uri.fromFile(new File(this.biL.biy));
                this.biL.bix = UtilsFile.getTempFileName();
                UtilsCamera.cropImage(this.biL.mContext, CropImageActivity.class, fromUri, Uri.fromFile(new File(this.biL.bix)), h.arp, h.arp, false);
            }
            return true;
        }
    };
    private String biJ = "[1-9][0-9]{0,12}";
    private boolean biK = false;
    private c big;
    private boolean bih = false;
    private EditText bii;
    private EditText bij;
    private EditText bik;
    private EditText bil;
    private TextView bim;
    private ScrollView bin;
    private PaintView bio;
    private boolean bip = false;
    private String biq = "";
    private String bir = "";
    private String bis = "";
    private String bit = "";
    private String biu = "";
    private Set<Long> biv = new HashSet();
    private String biw = "";
    private String bix;
    private String biy;
    private Activity mContext;

    private static class a extends BaseAdapter implements com.simple.colorful.b {
        private List<hlx.module.resources.a> biM = new ArrayList();
        private Set<Long> biN = new HashSet();
        private OnClickListener mClickListener = new 1(this);
        private Context mContext;
        private LayoutInflater mInflater;

        public a(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        public void b(List<hlx.module.resources.a> cateInfo, boolean clear, boolean clearSet) {
            if (clear) {
                this.biM.clear();
            }
            this.biM.addAll(cateInfo);
            if (clearSet) {
                this.biN.clear();
            }
            notifyDataSetChanged();
        }

        public void a(Set<Long> tags, boolean clear) {
            if (clear) {
                this.biN.clear();
            }
            this.biN.addAll(tags);
            notifyDataSetChanged();
        }

        public Set<Long> JX() {
            return this.biN;
        }

        public int getCount() {
            return UtilsFunction.empty(this.biM) ? 0 : this.biM.size();
        }

        public Object getItem(int position) {
            return UtilsFunction.empty(this.biM) ? null : (hlx.module.resources.a) this.biM.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                holder = new a(null);
                convertView = this.mInflater.inflate(com.huluxia.bbs.b.i.item_studio_edit_resource, null);
                holder.biP = (TextView) convertView.findViewById(g.tv_resource_type);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            hlx.module.resources.a item = (hlx.module.resources.a) getItem(position);
            if (this.biN.contains(Long.valueOf(item.cateid))) {
                Drawable drawable = y.c(this.mContext, item.catename, UtilsScreen.dipToPx(this.mContext, 40));
                holder.biP.setTextColor(d.getColor(this.mContext, com.huluxia.bbs.b.c.colorNormalWhite));
                holder.biP.setBackgroundDrawable(drawable);
            } else {
                holder.biP.setTextColor(d.getColor(this.mContext, 16842808));
                holder.biP.setBackgroundDrawable(d.o(this.mContext, com.huluxia.bbs.b.c.studio_resource_type));
            }
            holder.biP.setText(item.catename);
            holder.biP.setTag(item);
            holder.biP.setOnClickListener(this.mClickListener);
            return convertView;
        }

        public void a(j setter) {
            setter.bh(g.tv_resource_type, 16842808).bg(g.tv_resource_type, com.huluxia.bbs.b.c.studio_resource_type);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(com.huluxia.bbs.b.i.activity_edit_studio);
        EventNotifyCenter.add(h.class, this.aky);
        if (savedInstanceState != null) {
            this.big = (c) savedInstanceState.getParcelable(bie);
            this.bih = savedInstanceState.getBoolean(bif, false);
        } else {
            this.big = (c) getIntent().getParcelableExtra(bie);
            this.bih = getIntent().getBooleanExtra(bif, false);
        }
        Fd();
        initView();
        JM();
        EZ();
        Fy();
        this.aSB = new DialogManager(this);
    }

    private void JM() {
        if (!this.bih && this.big != null && this.big.studioInfo != null) {
            this.axr = this.big.studioInfo.typeid;
            switch (this.axr) {
                case 2:
                    this.bim.setText(m.js);
                    break;
                case 3:
                    this.bim.setText(m.skin);
                    break;
                case 4:
                    this.bim.setText(m.wood);
                    break;
                default:
                    this.bim.setText(m.map);
                    break;
            }
            this.bil.setText(this.big.studioInfo.name);
            this.bik.setText(this.big.studioInfo.qq);
            this.bij.setText(this.big.studioInfo.qqgroup);
            this.bii.setText(this.big.studioInfo.description);
            this.aGa.setUri(UtilUri.getUriOrNull(this.big.studioInfo.icon)).oval().placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
            this.biq = this.big.studioInfo.name;
            this.bir = this.bil.getText().toString().trim();
            this.bis = this.big.studioInfo.qq;
            this.bit = this.big.studioInfo.qqgroup;
            this.biu = this.big.studioInfo.description;
            this.bio.setUri(UtilUri.getUriOrNull(this.big.studioInfo.coverImg)).radius((float) UtilsScreen.dipToPx(this.mContext, 6)).placeHolder(d.r(this.mContext, com.huluxia.bbs.b.c.ic_picture_plus)).setImageLoader(l.cb().getImageLoader());
            if (!UtilsFunction.empty(this.big.studioInfo.coverImg)) {
                this.bio.setScaleType(ScaleType.FIT_XY);
            }
            Collection<hlx.module.resources.a> cates = this.big.cates;
            if (!UtilsFunction.empty((Collection) cates)) {
                for (hlx.module.resources.a item : cates) {
                    this.biv.add(Long.valueOf(item.cateid));
                }
                this.biE.a(this.biv, true);
            }
        }
    }

    private void initView() {
        this.aGa = (PaintView) findViewById(g.image);
        this.bil = (EditText) findViewById(g.studio);
        this.bik = (EditText) findViewById(g.qq);
        this.bij = (EditText) findViewById(g.qq_group);
        this.bii = (EditText) findViewById(g.studio_description);
        this.bim = (TextView) findViewById(g.tv_resource_type_name);
        this.bin = (ScrollView) findViewById(g.root_view);
        this.bio = (PaintView) findViewById(g.iv_studio_cover);
        this.biD = (GridViewNotScroll) findViewById(g.gv_resource_list);
        this.biE = new a(this.mContext);
        this.biD.setAdapter(this.biE);
        this.aGa.setOnClickListener(this);
        this.bil.setOnClickListener(this);
        this.bik.setOnClickListener(this);
        this.bij.setOnClickListener(this);
        this.bii.setOnClickListener(this);
        if (this.bih) {
            this.bim.setOnClickListener(this);
        }
        findViewById(g.fl_studio_cover).setOnClickListener(this);
        findViewById(g.fl_studio_cover).setOnLongClickListener(this.biI);
    }

    private void Fd() {
        if (this.bih) {
            ej(getString(m.create_studio));
        } else {
            ej(getString(m.edit_studio));
        }
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(0);
        this.aIT.setText(getString(m.submit));
        this.aIT.setOnClickListener(this);
    }

    private void EZ() {
        z.DO();
        z.DK();
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.sys_header_right) {
            JN();
            JO();
        } else if (id == g.image) {
            this.biA = 0;
            r.showPhotoMenu(this);
        } else if (id == g.studio_description || id == g.studio || id == g.qq || id == g.qq_group) {
            v.setFocusableInTouchMode(true);
            v.requestFocus();
            UtilsScreen.showInputMethod(v);
        } else if (id == g.fl_studio_cover) {
            t.e(this.mContext, 3);
        } else if (id == g.tv_resource_type_name && this.biF != null && this.bih) {
            ArrayList<Object> mSortArrayList = new ArrayList();
            mSortArrayList.add(new ResMenuItem("地图", 1, 0));
            mSortArrayList.add(new ResMenuItem("JS", 2, 0));
            mSortArrayList.add(new ResMenuItem("材质", 4, 0));
            mSortArrayList.add(new ResMenuItem("皮肤", 3, 0));
            this.biG = new CommonMenuDialog(this.mContext, this.biH, d.RB(), 1);
            this.biG.setMenuItems(mSortArrayList);
            this.biG.showMenu(null, null);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(bie, this.big);
        outState.putBoolean(bif, this.bih);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 3) {
                if (data != null) {
                    com.huluxia.module.picture.b photo = (com.huluxia.module.picture.b) data.getParcelableExtra("EXTRA_CURRENT_SELECTED");
                    if (photo != null) {
                        this.biy = photo.localPath;
                        this.bix = "";
                        HLog.verbose(TAG, "path--> " + this.biy, new Object[0]);
                        this.bio.setImageBitmap(BitmapFactory.decodeFile(photo.localPath));
                        this.bio.setScaleType(ScaleType.FIT_XY);
                    }
                }
            } else if (this.biA == 0) {
                if (requestCode == 258) {
                    this.biw = UtilsCamera.onPickResultToCrop(resultCode, requestCode, data, this, CropImageActivity.class, null, true);
                } else {
                    UtilsCamera.onPickResultToCrop(resultCode, requestCode, data, this, CropImageActivity.class, null, true);
                }
                HLog.verbose(TAG, "path--> " + this.biw, new Object[0]);
                if (UtilsFile.isExist(this.biw)) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(this.biw);
                    Bitmap newBitMap = Bitmap.createScaledBitmap(myBitmap, 160, 160, true);
                    myBitmap.recycle();
                    Bitmap roundBitmap = UtilsImage.getRoundedCornerBitmap(newBitMap, 5.0f);
                    newBitMap.recycle();
                    this.aGa.setImageBitmap(roundBitmap);
                }
            } else if (1 == this.biA) {
                HLog.verbose(TAG, "path--> " + this.bix, new Object[0]);
                if (UtilsFile.isExist(this.bix)) {
                    HLog.verbose(TAG, "path is exit--> " + this.bix, new Object[0]);
                    this.bio.setImageBitmap(BitmapFactory.decodeFile(this.bix));
                    this.bio.setScaleType(ScaleType.FIT_XY);
                }
            }
        }
    }

    private void JN() {
        View focusView = this.bin.findFocus();
        if (focusView != null) {
            UtilsScreen.hideInputMethod(focusView);
        }
    }

    private void JO() {
        CharSequence errorText;
        if (this.bih) {
            errorText = JU();
        } else {
            errorText = JS();
        }
        if (!UtilsFunction.empty(errorText)) {
            t.n(this, errorText);
        } else if (!this.bih && !this.bir.equals(this.bil.getText().toString())) {
            JP();
        } else if (JQ()) {
            la(0);
        } else {
            JR();
        }
    }

    private void JP() {
        this.aSB.showOkCancelDialog(getString(m.dialog_title_nick_change_comfirm), getString(m.rename_studio), true, new OkCancelDialogListener(this) {
            final /* synthetic */ StudioEditActivity biL;

            {
                this.biL = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.biL.bip = true;
                if (this.biL.JQ()) {
                    this.biL.la(0);
                } else {
                    this.biL.JR();
                }
            }
        });
    }

    private void la(int index) {
        if (index < 2) {
            if (index == 0) {
                if (TextUtils.isEmpty(this.biw) || !UtilsFile.isExist(this.biw)) {
                    index++;
                    la(index);
                } else {
                    aa.DQ().a(index, this.biw, this.mContext);
                }
                HLog.verbose(TAG, "index = 0 ", new Object[0]);
            } else if (1 == index) {
                if (TextUtils.isEmpty(this.biy) || !UtilsFile.isExist(this.biy)) {
                    HLog.verbose(TAG, "index = 1-1 ", new Object[0]);
                    index++;
                    la(index);
                } else if (TextUtils.isEmpty(this.bix) || !UtilsFile.isExist(this.bix)) {
                    aa.DQ().a(index, this.biy, this.mContext);
                } else {
                    aa.DQ().a(index, this.bix, this.mContext);
                }
                HLog.verbose(TAG, "index = 1-2 ", new Object[0]);
            }
            HLog.verbose(TAG, "position --> " + index, new Object[0]);
            this.aIT.setEnabled(false);
            ek(getString(m.upload_image));
            cs(true);
            return;
        }
        JR();
    }

    private boolean JQ() {
        boolean a;
        if (TextUtils.isEmpty(this.biw) || !UtilsFile.isExist(this.biw)) {
            a = false;
        } else {
            a = true;
        }
        boolean b;
        if (TextUtils.isEmpty(this.biy) || !UtilsFile.isExist(this.biy)) {
            b = false;
        } else {
            b = true;
        }
        if (a || b) {
            return true;
        }
        return false;
    }

    private void JR() {
        this.aIT.setEnabled(false);
        ek(getString(m.submit_data));
        cs(true);
        if (this.bih) {
            JV();
            return;
        }
        if (this.big == null || this.big.studioInfo == null) {
            cs(false);
            t.n(this.mContext, "提交失败");
            finish();
        }
        if (this.bip) {
            z.DO();
            z.a(this.big.studioInfo.id, this.bil.getText().toString(), this.bik.getText().toString(), this.bij.getText().toString(), this.bii.getText().toString(), this.biB, this.biC, JW());
            return;
        }
        z.DO();
        z.a(this.big.studioInfo.id, this.biq, this.bik.getText().toString(), this.bij.getText().toString(), this.bii.getText().toString(), this.biB, this.biC, JW());
    }

    public String JS() {
        String str = null;
        if (!(this.bih || JT())) {
            str = "没有修改工作室信息";
            t.l(this.mContext, str);
            finish();
        }
        if (this.bih && this.bil.getText().toString().trim().length() > 8) {
            return "工作室名称过长,8个字符以内";
        }
        if (!this.bih && this.bil.getText().toString().trim().length() > 8 && !this.bir.trim().equals(this.bil.getText().toString().trim())) {
            return "工作室名称过长,8个字符以内";
        }
        if (TextUtils.isEmpty(this.bil.getText().toString().trim())) {
            str = "工作室名称不能为空";
        } else if (TextUtils.isEmpty(this.bik.getText().toString().trim())) {
            str = "QQ账号不能为空";
        } else if (!Pattern.compile(this.biJ).matcher(this.bik.getText().toString().trim()).matches()) {
            str = "请输入正确QQ账号";
        } else if (TextUtils.isEmpty(this.bij.getText().toString().trim())) {
            str = "QQ群不能为空";
        } else if (!Pattern.compile(this.biJ).matcher(this.bij.getText().toString().trim()).matches()) {
            str = "请输入正确QQ群";
        } else if (TextUtils.isEmpty(this.bii.getText().toString().trim())) {
            str = "工作室介绍不能为空";
        } else if (this.bii.getText().toString().trim().length() > 100) {
            str = "工作室介绍过长,100个字符以内";
        }
        return str;
    }

    private boolean JT() {
        if (!this.bir.trim().equals(this.bil.getText().toString().trim()) || !this.bis.trim().equals(this.bik.getText().toString().trim()) || !this.bit.trim().equals(this.bij.getText().toString().trim()) || !this.biu.trim().equals(this.bii.getText().toString().trim()) || !UtilsFunction.empty(this.biw) || !UtilsFunction.empty(this.biy)) {
            return true;
        }
        Set<Long> tags = this.biE.JX();
        boolean a = this.biv.containsAll(tags);
        boolean b = tags.containsAll(this.biv);
        if (a && b) {
            return false;
        }
        return true;
    }

    private String JU() {
        String msg = JS();
        if (!UtilsFunction.empty((CharSequence) msg)) {
            return msg;
        }
        if (UtilsFunction.empty(this.biw)) {
            msg = "头像不能为空";
        } else if (UtilsFunction.empty(this.biy)) {
            msg = "封面不能为空";
        }
        return msg;
    }

    private void JV() {
        String tagsStr = JW();
        z.DO();
        z.a(this.bil.getText().toString(), this.bik.getText().toString(), this.bij.getText().toString(), this.bii.getText().toString(), this.biB, tagsStr, this.biC, this.axr);
    }

    private String JW() {
        String tagsStr = "";
        Collection<Long> tags = this.biE.JX();
        if (UtilsFunction.empty((Collection) tags)) {
            return tagsStr;
        }
        for (Long str : tags) {
            tagsStr = tagsStr + str + MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        return tagsStr.substring(0, tagsStr.lastIndexOf(MiPushClient.ACCEPT_TIME_SEPARATOR));
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.biE != null && (this.biE instanceof com.simple.colorful.b)) {
            k setter = new j(this.biD);
            setter.a(this.biE);
            builder.a(setter);
        }
        builder.aY(g.root_view, com.huluxia.bbs.b.c.backgroundDefault).bc(g.image, com.huluxia.bbs.b.c.default_discover_pic).aZ(g.rly_studio, com.huluxia.bbs.b.c.studio_edit_bg).ba(g.tv_studio, 16843282).ba(g.studio, 16842808).aZ(g.rly_qq, com.huluxia.bbs.b.c.studio_edit_bg).ba(g.tv_qq, 16843282).ba(g.qq, 16842808).aZ(g.rly_qq_group, com.huluxia.bbs.b.c.studio_edit_bg).ba(g.tv_group, 16843282).ba(g.qq_group, 16842808).aZ(g.studio_description, com.huluxia.bbs.b.c.studio_edit_bg).ba(g.studio_description, 16842808).bd(g.studio_description, com.huluxia.bbs.b.c.studio_introduce_hint).aZ(g.iv_studio_cover, com.huluxia.bbs.b.c.studio_edit_bg).bc(g.iv_studio_cover, com.huluxia.bbs.b.c.ic_picture_plus).ba(g.tv_studio_cover_explain, 16842808).aY(g.split, com.huluxia.bbs.b.c.splitColor).aY(g.split_interval, com.huluxia.bbs.b.c.studio_background_split).ba(g.tv_resource_type, 16842808).ba(g.tv_resource_type_name, 16843282).ab(g.tv_resource_type_name, com.huluxia.bbs.b.c.choice_arrow, 2).ba(g.tv_resource_cate_explain, 16842808);
    }
}
