package hlx.ui.publishres;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.data.HTUploadInfo;
import com.huluxia.data.j;
import com.huluxia.data.map.MapProfileInfo.MapProfileItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsBitmap;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.module.aa;
import com.huluxia.module.h;
import com.huluxia.module.o;
import com.huluxia.module.topic.k;
import com.huluxia.module.topic.l;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.MyMapCateItem;
import com.huluxia.ui.itemadapter.TagAdapter;
import com.huluxia.ui.itemadapter.TagAdapter.a;
import com.huluxia.utils.ad;
import com.huluxia.utils.ah;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.huluxia.utils.y;
import com.huluxia.widget.listview.GridViewNotScroll;
import com.huluxia.widget.photowall.PhotoWall2;
import com.huluxia.widget.photowall.PhotoWall2.b;
import com.simple.colorful.d;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class PublishResourceBaseActivity extends HTBaseActivity implements a {
    private static final String TAG = "PublishResourceBaseActivity";
    public static final String cef = "PARA_INDEX";
    protected static final int ceg = 1;
    protected static final int ceh = 0;
    protected static final int cei = 1;
    protected static final int cej = 2;
    protected static final int cek = 3;
    protected static final int cel = 8;
    protected RelativeLayout aLJ;
    protected Button aLP;
    protected GridViewNotScroll aLR;
    protected TagAdapter aLS;
    protected PhotoWall2 bhm;
    protected RadioButton ceA;
    protected RadioButton ceB;
    protected RadioButton ceC;
    protected RadioButton ceD;
    protected com.huluxia.widget.a ceE;
    protected RadioGroup ceF;
    protected TextView ceG;
    protected RelativeLayout ceH;
    protected LinearLayout ceI;
    protected LinearLayout ceJ;
    protected String ceK;
    protected EditText ceL;
    protected EditText ceM;
    protected EditText ceN;
    protected EditText ceO;
    protected PaintView ceP;
    protected ImageView ceQ;
    protected TextView ceR;
    protected TextView ceS;
    protected TextView ceT;
    private PublishResourceBaseActivity ceU;
    private int ceV;
    protected OnCheckedChangeListener ceW = new OnCheckedChangeListener(this) {
        final /* synthetic */ PublishResourceBaseActivity ceZ;

        {
            this.ceZ = this$0;
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String strResType = null;
            switch (checkedId) {
                case R.id.rtnPublishResMap:
                    this.ceZ.cem = 1;
                    strResType = "map_cate";
                    break;
                case R.id.rtnPublishResJs:
                    this.ceZ.cem = 2;
                    strResType = "js_cate";
                    break;
                case R.id.rtnPublishResWood:
                    this.ceZ.cem = 4;
                    strResType = "wood_cate";
                    break;
                case R.id.rtnPublishResSkin:
                    this.ceZ.cem = 3;
                    strResType = "skin_cate";
                    break;
            }
            if (this.ceZ.ceq == null) {
                o.DH();
            } else if (strResType != null) {
                this.ceZ.W((List) this.ceZ.ceq.get(strResType));
            }
            this.ceZ.VB();
        }
    };
    private OnClickListener ceX = new OnClickListener(this) {
        final /* synthetic */ PublishResourceBaseActivity ceZ;

        {
            this.ceZ = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_patch:
                    k.Ej().at(this.ceZ.ceU);
                    return;
                case R.id.img_photo:
                    if (!this.ceZ.cer) {
                        if (this.ceZ.ceJ.isShown()) {
                            this.ceZ.ceJ.setVisibility(8);
                        } else if (this.ceZ.bhm.getPhotoNum() > 0 || !this.ceZ.aIT.isEnabled()) {
                            this.ceZ.ceJ.setVisibility(0);
                        } else {
                            this.ceZ.bhm.PG();
                        }
                        this.ceZ.aLJ.setVisibility(8);
                        this.ceZ.Go();
                        return;
                    }
                    return;
                case R.id.btn_select:
                    if (!this.ceZ.cer) {
                        if (this.ceZ.aLJ.isShown()) {
                            this.ceZ.aLJ.setVisibility(8);
                        } else {
                            this.ceZ.aLJ.setVisibility(0);
                        }
                        this.ceZ.ceJ.setVisibility(8);
                        this.ceZ.Go();
                        return;
                    }
                    return;
                case R.id.sys_header_right:
                    this.ceZ.Go();
                    this.ceZ.ceJ.setVisibility(8);
                    this.ceZ.aLJ.setVisibility(8);
                    if (!j.ep().ey()) {
                        t.an(this.ceZ.ces);
                        return;
                    } else if (this.ceZ.Vq()) {
                        this.ceZ.cer = true;
                        if (this.ceZ.cen == 0) {
                            this.ceZ.Vr();
                            return;
                        }
                        this.ceZ.dN(false);
                        if (this.ceZ.ceK.startsWith("http://")) {
                            this.ceZ.ks(0);
                            return;
                        } else {
                            this.ceZ.Vs();
                            return;
                        }
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private OnTouchListener ceY = new OnTouchListener(this) {
        final /* synthetic */ PublishResourceBaseActivity ceZ;

        {
            this.ceZ = this$0;
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 1:
                    this.ceZ.ceJ.setVisibility(8);
                    this.ceZ.aLJ.setVisibility(8);
                    break;
            }
            return false;
        }
    };
    protected int cem;
    protected int cen;
    protected String ceo;
    protected MapProfileItem cep;
    protected Map<String, ArrayList<MyMapCateItem>> ceq;
    protected boolean cer;
    private Context ces;
    protected RadioButton cet;
    protected RadioButton ceu;
    protected RadioButton cev;
    protected RadioButton cew;
    protected RadioButton cex;
    protected RadioButton cey;
    protected RadioButton cez;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ PublishResourceBaseActivity ceZ;

        {
            this.ceZ = this$0;
        }

        @MessageHandler(message = 772)
        public void onGetVcodeRsp(l info, Object context) {
            HLog.debug(PublishResourceBaseActivity.TAG, "onGetVcodeRsp " + info, new Object[0]);
            if (!context.equals(this.ceZ.ceU)) {
                return;
            }
            if (info == null || !info.isSucc()) {
                this.ceZ.Gl();
                return;
            }
            this.ceZ.en(info.url);
            this.ceZ.aIT.setEnabled(true);
        }

        @MessageHandler(message = 771)
        public void onUploadImageRsp(String response, int index, Object context) {
            HLog.debug(PublishResourceBaseActivity.TAG, "onUploadImageRsp " + response, new Object[0]);
            if (context.equals(this.ceZ.ceU)) {
                this.ceZ.cer = false;
                this.ceZ.cs(false);
                this.ceZ.dN(true);
                if (response != null) {
                    HTUploadInfo info = null;
                    try {
                        info = (HTUploadInfo) Json.parseJsonObject(response, HTUploadInfo.class);
                    } catch (Exception e) {
                        HLog.error(PublishResourceBaseActivity.TAG, "onUploadImageRsp response: " + response + "; error " + e.toString(), new Object[0]);
                    }
                    if (info == null || !info.isSucc() || info.getFid() == null || info.getFid().length() <= 10) {
                        this.ceZ.cer = false;
                        this.ceZ.VD();
                        t.n(this.ceZ.ceU, this.ceZ.ceU.getString(R.string.submit_image_error));
                        return;
                    }
                    this.ceZ.a(index, info);
                    this.ceZ.ks(index + 1);
                }
            }
        }
    };
    protected long sH = 0;

    public abstract void FR();

    public abstract boolean Vq();

    public abstract void Vr();

    public abstract void Vs();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_publish_res);
        ej("发布资源");
        this.ceU = this;
        Vv();
        Vw();
        Vx();
        Vy();
        Vz();
        EventNotifyCenter.add(h.class, this.mCallback);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Vv() {
        this.ces = this;
        this.cem = getIntent().getIntExtra("resType", 1);
        this.cep = (MapProfileItem) getIntent().getSerializableExtra("map");
        int state = getIntent().getIntExtra(DownloadRecord.COLUMN_STATE, 2);
        if (this.cep == null) {
            this.cen = 0;
        } else if (state == 0) {
            this.cen = 2;
        } else if (state == 1) {
            this.cen = 1;
        } else {
            this.cen = 3;
        }
        this.cer = false;
    }

    private void Vw() {
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(0);
        this.aIT.setText("提交");
        this.aIT.setOnClickListener(this.ceX);
    }

    private void Vx() {
        int radius_1 = at.dipToPx(this.ceU, 1);
        int radius_60 = at.dipToPx(this.ceU, 60);
        int colorNormal = d.q(this.ceU, R.color.trasnparent);
        int colorPressed = d.getColor(this.ceU, R.attr.colorPressed);
        int colorStroke = d.q(this.ceU, R.color.text_color_tertiary);
        int colorChecked = d.getColor(this.ceU, R.attr.colorChecked);
        this.cet = (RadioButton) findViewById(R.id.rtnPublishResMap);
        this.ceu = (RadioButton) findViewById(R.id.rtnPublishResJs);
        this.cev = (RadioButton) findViewById(R.id.rtnPublishResWood);
        this.cew = (RadioButton) findViewById(R.id.rtnPublishResSkin);
        this.cex = (RadioButton) findViewById(R.id.rtn_v10);
        this.cey = (RadioButton) findViewById(R.id.rtn_v11);
        this.cez = (RadioButton) findViewById(R.id.rtn_v12);
        this.ceA = (RadioButton) findViewById(R.id.rtn_v13);
        this.ceB = (RadioButton) findViewById(R.id.rtn_v14);
        this.ceC = (RadioButton) findViewById(R.id.rtn_v15);
        this.ceD = (RadioButton) findViewById(R.id.rtn_v16);
        Drawable drawableRadioBg10 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg11 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg12 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg13 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg14 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg15 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        Drawable drawableRadioBg16 = y.b(this.ceU, colorNormal, colorChecked, colorStroke, radius_60);
        if (VERSION.SDK_INT > 16) {
            this.cex.setBackground(drawableRadioBg10);
            this.cey.setBackground(drawableRadioBg11);
            this.cez.setBackground(drawableRadioBg12);
            this.ceA.setBackground(drawableRadioBg13);
            this.ceB.setBackground(drawableRadioBg14);
            this.ceC.setBackground(drawableRadioBg15);
            this.ceD.setBackground(drawableRadioBg16);
        } else {
            this.cex.setBackgroundDrawable(drawableRadioBg10);
            this.cey.setBackgroundDrawable(drawableRadioBg11);
            this.cez.setBackgroundDrawable(drawableRadioBg12);
            this.ceA.setBackgroundDrawable(drawableRadioBg13);
            this.ceB.setBackgroundDrawable(drawableRadioBg14);
            this.ceC.setBackgroundDrawable(drawableRadioBg15);
            this.ceD.setBackgroundDrawable(drawableRadioBg16);
        }
        this.ceF = (RadioGroup) findViewById(R.id.rdogrpPublishResType);
        this.ceF.setOnCheckedChangeListener(this.ceW);
        this.ceE = new com.huluxia.widget.a(new com.huluxia.widget.a.a(this) {
            final /* synthetic */ PublishResourceBaseActivity ceZ;

            {
                this.ceZ = this$0;
            }

            public void a(CompoundButton button, boolean chedked) {
            }
        });
        this.ceE.a(this.cex, this.cey, this.cez);
        this.ceE.a(this.ceA, this.ceB, this.ceC);
        this.ceE.a(this.ceD);
        this.ceG = (TextView) findViewById(R.id.btn_file);
        this.ceH = (RelativeLayout) findViewById(R.id.rly_patcha);
        this.ceH.setOnClickListener(this.ceX);
        this.ceI = (LinearLayout) findViewById(R.id.radios_version);
        this.ceJ = (LinearLayout) findViewById(R.id.ly_photo_ctx);
        this.ceL = (EditText) findViewById(R.id.text_author);
        this.ceL.setOnTouchListener(this.ceY);
        this.ceM = (EditText) findViewById(R.id.text_source);
        this.ceM.setOnTouchListener(this.ceY);
        this.ceN = (EditText) findViewById(R.id.content_text);
        this.ceN.setOnTouchListener(this.ceY);
        this.ceO = (EditText) findViewById(R.id.tv_patch);
        this.ceO.setOnTouchListener(this.ceY);
        this.ceO.setOnClickListener(this.ceX);
        this.ceP = (PaintView) findViewById(R.id.iv_patch);
        this.ceP.setOnClickListener(this.ceX);
        this.ceQ = (ImageView) findViewById(R.id.img_photo);
        this.ceQ.setOnClickListener(this.ceX);
        this.bhm = (PhotoWall2) findViewById(R.id.photowall2);
        this.bhm.setShowText(true);
        this.bhm.setItemClickListener(new b(this) {
            final /* synthetic */ PublishResourceBaseActivity ceZ;

            {
                this.ceZ = this$0;
            }

            public void a(com.huluxia.module.picture.b unit, int position) {
                if (this.ceZ.aIT.isEnabled()) {
                    this.ceZ.bhm.d(unit, position);
                }
            }

            public void Gq() {
                if (this.ceZ.aIT.isEnabled()) {
                    this.ceZ.bhm.PG();
                }
            }
        });
        this.aLJ = (RelativeLayout) findViewById(R.id.rl_tag_ctx);
        this.aLR = (GridViewNotScroll) findViewById(R.id.grid_tag);
        this.aLP = (Button) findViewById(R.id.btn_select);
        this.aLP.setOnClickListener(this.ceX);
        this.aLS = new TagAdapter(this);
        this.aLR.setAdapter(this.aLS);
        this.aLS.a((a) this);
        Drawable drawableBg = y.a(this.ceU, colorNormal, colorPressed, colorStroke, radius_60);
        if (VERSION.SDK_INT > 16) {
            this.ceG.setBackground(drawableBg);
        } else {
            this.ceG.setBackgroundDrawable(drawableBg);
        }
        colorNormal = d.getColor(this.ceU, R.attr.colorNormalWhite);
        colorPressed = d.getColor(this.ceU, R.attr.colorPressed2);
        colorStroke = d.getColor(this.ceU, R.attr.colorChecked);
        Drawable drawableBtnRes = y.a(this.ceU, colorNormal, colorPressed, colorStroke, radius_1);
        Drawable drawableBtnCopyright = y.a(this.ceU, colorNormal, colorPressed, colorStroke, radius_1);
        Drawable drawableBtnPub = y.a(this.ceU, colorNormal, colorPressed, colorStroke, radius_1);
        this.ceR = (TextView) findViewById(R.id.tvPublishResTopic1);
        this.ceS = (TextView) findViewById(R.id.tvPublishResTopic2);
        this.ceT = (TextView) findViewById(R.id.tvPublishRes);
        if (VERSION.SDK_INT > 16) {
            this.ceR.setBackground(drawableBtnRes);
            this.ceS.setBackground(drawableBtnCopyright);
            this.ceT.setBackground(drawableBtnPub);
            return;
        }
        this.ceR.setBackgroundDrawable(drawableBtnRes);
        this.ceS.setBackgroundDrawable(drawableBtnCopyright);
        this.ceT.setBackgroundDrawable(drawableBtnPub);
    }

    private void Vy() {
        switch (this.cem) {
            case 1:
                this.cet.setChecked(true);
                break;
            case 2:
                this.ceu.setChecked(true);
                break;
            case 3:
                this.cew.setChecked(true);
                break;
            case 4:
                this.cev.setChecked(true);
                break;
        }
        if (this.cen == 0) {
            this.cet.setChecked(true);
            ej(getString(R.string.publish_new_res));
            ER();
            this.ceL.setText(j.ep().getNick());
            this.ceM.setText("葫芦侠");
        } else if (this.cen == 3) {
            ej(getString(R.string.republish_res));
            VA();
            this.cet.setEnabled(false);
            this.ceu.setEnabled(false);
            this.cev.setEnabled(false);
            this.cew.setEnabled(false);
        } else if (this.cen == 1) {
            ej(getString(R.string.pass_review_res));
            VA();
            this.aIT.setVisibility(8);
            dN(false);
        } else {
            ej(getString(R.string.in_review_res));
            VA();
            this.aIT.setVisibility(8);
            dN(false);
        }
    }

    private void Vz() {
        o.DH();
        k.Ej().at(this.ceU);
    }

    private void VA() {
        boolean isNewVer = false;
        String str = this.cep.version;
        boolean z = true;
        switch (str.hashCode()) {
            case 1475741:
                if (str.equals(hlx.data.localstore.a.bJr)) {
                    z = false;
                    break;
                }
                break;
            case 1475742:
                if (str.equals(hlx.data.localstore.a.bJs)) {
                    z = true;
                    break;
                }
                break;
            case 1475743:
                if (str.equals(hlx.data.localstore.a.bJt)) {
                    z = true;
                    break;
                }
                break;
            case 1475744:
                if (str.equals("0.13")) {
                    z = true;
                    break;
                }
                break;
            case 1475745:
                if (str.equals("0.14")) {
                    z = true;
                    break;
                }
                break;
            case 1475746:
                if (str.equals(hlx.data.localstore.a.bJy)) {
                    z = true;
                    break;
                }
                break;
            case 1475747:
                if (str.equals(hlx.data.localstore.a.bJz)) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                this.cex.setChecked(true);
                break;
            case true:
                this.cey.setChecked(true);
                break;
            case true:
                this.cez.setChecked(true);
                break;
            case true:
                this.ceA.setChecked(true);
                isNewVer = true;
                break;
            case true:
                this.ceB.setChecked(true);
                isNewVer = true;
                break;
            case true:
                this.ceC.setChecked(true);
                isNewVer = true;
                break;
            case true:
                this.ceD.setChecked(true);
                isNewVer = true;
                break;
        }
        if (this.cem == 1 && isNewVer) {
            this.ceI.setVisibility(8);
            this.ceA.setVisibility(8);
            this.ceD.setVisibility(0);
        } else {
            this.ceI.setVisibility(0);
            this.ceA.setVisibility(0);
            this.ceD.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.cep.name)) {
            this.ceo = this.cep.name;
            this.ceG.setText(aw.W(this.ceo, 8));
        }
        if (!TextUtils.isEmpty(this.cep.downUrl)) {
            this.ceK = this.cep.downUrl;
        }
        if (!TextUtils.isEmpty(this.cep.author)) {
            this.ceL.setText(this.cep.author);
        }
        if (!TextUtils.isEmpty(this.cep.source)) {
            this.ceM.setText(this.cep.source);
        }
        if (!TextUtils.isEmpty(this.cep.mapDesc)) {
            this.ceN.setText(this.cep.mapDesc);
        }
        if (!TextUtils.isEmpty(this.cep.cateName)) {
            a(this.ceU, this.aLP, this.cep.cateName, false);
        }
        Iterator it = this.cep.resourceList.iterator();
        while (it.hasNext()) {
            String szUrl = (String) it.next();
            com.huluxia.module.picture.b pn = new com.huluxia.module.picture.b();
            pn.url = szUrl;
            try {
                String path = new URL(szUrl).getPath();
                if (path != null && path.length() > 1 && path.startsWith("/")) {
                    path = path.substring(1);
                }
                pn.fid = path;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.bhm.e(pn);
        }
    }

    private void en(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.ceH.setVisibility(0);
            this.ceP.setUri(Uri.parse(url)).placeHolder(d.isDayMode() ? R.drawable.place_holder_normal : R.drawable.place_holder_night_normal).setImageLoader(com.huluxia.l.cb().getImageLoader());
        }
    }

    private void Gl() {
        t.n(this, "网络问题\n验证失败，不能发贴\n请重试");
    }

    protected void dN(boolean bEnable) {
        this.aIT.setClickable(bEnable);
        this.ceL.setClickable(bEnable);
        this.ceL.setEnabled(bEnable);
        this.ceM.setClickable(bEnable);
        this.ceM.setEnabled(bEnable);
        this.ceN.setClickable(bEnable);
        this.ceN.setEnabled(bEnable);
        this.ceG.setClickable(bEnable);
        this.cex.setClickable(bEnable);
        this.cey.setClickable(bEnable);
        this.cez.setClickable(bEnable);
        this.ceA.setClickable(bEnable);
        this.ceB.setClickable(bEnable);
        this.ceC.setClickable(bEnable);
        this.ceD.setClickable(bEnable);
        this.cet.setClickable(bEnable);
        this.ceu.setClickable(bEnable);
        this.cev.setClickable(bEnable);
        this.cew.setClickable(bEnable);
        this.aLP.setEnabled(bEnable);
        if (!bEnable) {
            this.aLJ.setVisibility(8);
            this.ceJ.setVisibility(8);
            Go();
        }
    }

    public void Go() {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(this.ceL.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.ceM.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.ceN.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(this.ceO.getWindowToken(), 0);
    }

    protected void W(List<MyMapCateItem> inputCates) {
        this.aLS.setData(inputCates);
    }

    private void VB() {
        this.ceK = "";
        this.ceo = "";
        this.ceG.setText(this.ceU.getResources().getString(R.string.select_upload_file));
        this.ceE.dh(false);
        this.ceI.setVisibility(this.cem == 1 ? 8 : 0);
        if (this.cem == 1) {
            this.ceA.setVisibility(8);
            this.ceD.setVisibility(0);
        } else {
            this.ceA.setVisibility(0);
            this.ceD.setVisibility(8);
        }
        this.sH = 0;
        a(this.ceU, this.aLP, "", true);
        this.aLS.clear();
    }

    protected void ks(int index) {
        HLog.debug("ETPrint", "submitImage index is " + index, new Object[0]);
        List<com.huluxia.module.picture.b> photos = this.bhm.getPhotos();
        boolean isPass = true;
        if (index < photos.size()) {
            com.huluxia.module.picture.b photo = (com.huluxia.module.picture.b) photos.get(index);
            String path = UtilsBitmap.compressFileBitmapToDisk(photo.localPath, null, 300000);
            if (photo.id != -1 && UtilsFunction.empty(photo.url) && UtilsFile.isExist(path)) {
                ek(String.format("提交第%s张图片", new Object[]{Integer.valueOf(index + 1)}));
                cs(true);
                aa.DQ().a(index, path, this.ceU);
                return;
            }
            isPass = false;
        }
        if (isPass) {
            FR();
            return;
        }
        this.cer = false;
        VD();
        t.n(this.ceU, this.ceU.getString(R.string.submit_image_error));
    }

    protected void a(int index, HTUploadInfo info) {
        List<com.huluxia.module.picture.b> photos = this.bhm.getPhotos();
        ((com.huluxia.module.picture.b) photos.get(index)).url = info.getUrl();
        ((com.huluxia.module.picture.b) photos.get(index)).fid = info.getFid();
    }

    protected void VC() {
        final DialogManager dialogManager = new DialogManager(this.ceU);
        dialogManager.showOkCancelDialog("分类信息为空，点击确定重新获取", hlx.data.localstore.a.bKC, null, new OkCancelDialogListener(this) {
            final /* synthetic */ PublishResourceBaseActivity ceZ;

            public void onCancel() {
                dialogManager.dismissDialog();
            }

            public void onOk() {
                o.DH();
            }
        });
    }

    protected void ES() {
        if (j.ep().ey()) {
            long userId = j.ep().getUserid();
            String author = this.ceL.getText().toString();
            String source = this.ceM.getText().toString();
            String detail = this.ceN.getText().toString();
            List<com.huluxia.module.picture.b> images = new ArrayList();
            Collection photos = this.bhm.getPhotos();
            if (!ad.empty(photos)) {
                images = photos;
            }
            ah.KZ().a(new com.huluxia.data.map.j(userId, author, source, detail, images));
        }
    }

    protected void ER() {
        if (j.ep().ey()) {
            long userId = j.ep().getUserid();
            com.huluxia.data.map.j draft = ah.KZ().LV();
            if (draft != null && draft.pW == userId) {
                this.ceL.setText(draft.author);
                this.ceM.setText(draft.source);
                this.ceN.setText(draft.detail);
                if (!ad.empty(draft.images)) {
                    for (com.huluxia.module.picture.b unit : draft.images) {
                        if (!TextUtils.isEmpty(unit.localPath) && com.huluxia.utils.UtilsFile.isExist(unit.localPath)) {
                            this.bhm.e(unit);
                        }
                    }
                }
            }
        }
    }

    protected void ET() {
        if (j.ep().ey()) {
            long userId = j.ep().getUserid();
            com.huluxia.data.map.j draft = ah.KZ().LV();
            if (draft != null && draft.pW == userId) {
                ah.KZ().LW();
            }
        }
    }

    private void VD() {
        ek(getString(R.string.is_loading));
        Iterator it = this.bhm.getPhotos().iterator();
        while (it.hasNext()) {
            com.huluxia.module.picture.b photo = (com.huluxia.module.picture.b) it.next();
            photo.fid = null;
            photo.url = null;
        }
    }

    public void j(long id, String name) {
        this.sH = id;
        a(this.ceU, this.aLP, name, false);
        HLog.debug(TAG, "tag_id is " + id, new Object[0]);
    }

    private void a(Context context, Button btn, String name, boolean reset) {
        if (reset) {
            btn.setText("分类");
            if (VERSION.SDK_INT > 16) {
                btn.setBackground(d.o(context, R.attr.backgroundButtonGrayTag));
            } else {
                btn.setBackgroundDrawable(d.o(context, R.attr.backgroundButtonGrayTag));
            }
            btn.setTextColor(d.getColor(context, R.attr.textColorGray));
            return;
        }
        btn.setText(name);
        if (VERSION.SDK_INT > 16) {
            btn.setBackground(d.o(context, R.attr.backgroundButtonTag));
        } else {
            btn.setBackgroundDrawable(d.o(context, R.attr.backgroundButtonTag));
        }
        btn.setTextColor(d.getColor(context, 16842809));
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(R.id.root_view, R.attr.backgroundDefault).aY(R.id.rly_selector, R.attr.backgroundDim4).aY(R.id.split_footer, R.attr.splitColorDim).ba(R.id.tv_tip, 16843282).ba(R.id.tv_version, 16842808).ba(R.id.tv_author, 16842808).ba(R.id.tv_source, 16842808).ba(R.id.tv_content, 16842808).bd(R.id.content_text, 16843282).aY(R.id.split1, R.attr.splitColor).aY(R.id.split2, R.attr.splitColor).aZ(R.id.img_photo, R.attr.drawableTopicCamera);
        if (this.sH == 0) {
            builder.j(this.aLP, R.attr.backgroundButtonGrayTag).a(this.aLP, R.attr.textColorGray);
        } else {
            builder.j(this.aLP, R.attr.backgroundButtonTag).a(this.aLP, 16842809);
        }
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void VE() {
        this.aLJ.setVisibility(0);
        this.ceJ.setVisibility(8);
        Go();
    }

    protected void VF() {
        this.aLJ.setVisibility(8);
        this.ceJ.setVisibility(0);
        Go();
    }
}
