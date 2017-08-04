package hlx.ui.publishres;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.toolbox.entity.utils.TextUtils;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.k;
import com.huluxia.module.aa;
import com.huluxia.module.n;
import com.huluxia.module.r;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.MojangMessage;
import com.huluxia.t;
import com.huluxia.ui.itemadapter.MyMapCateItem;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ah;
import com.huluxia.utils.aw;
import com.huluxia.utils.j;
import com.huluxia.utils.q;
import com.huluxia.widget.dialog.e;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PublishResourceActivity extends PublishResourceBaseActivity {
    private static final String TAG = "PublishResourceActivity";
    private static CallbackHandler akz;
    private static CallbackHandler cec;
    private PublishResourceActivity cdV;
    private String cdW;
    private r cdX;
    private boolean cdY;
    private boolean cdZ;
    private boolean cea;
    private OnClickListener ceb = new OnClickListener(this) {
        final /* synthetic */ PublishResourceActivity ced;

        {
            this.ced = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvPublishResTopic1:
                    t.a(this.ced.cdV, 6877897, false);
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNJ);
                    return;
                case R.id.tvPublishResTopic2:
                    t.a(this.ced.cdV, 12817312, false);
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNK);
                    return;
                case R.id.tvPublishRes:
                    if (this.ced.mDrawerLayout.isDrawerOpen(8388611)) {
                        this.ced.mDrawerLayout.closeDrawer(8388611);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ PublishResourceActivity ced;

        {
            this.ced = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_file:
                    k.b(this.ced.cdV, this.ced.cem);
                    this.ced.ceJ.setVisibility(8);
                    return;
                default:
                    return;
            }
        }
    };
    private DrawerLayout mDrawerLayout = null;

    static class a extends CallbackHandler {
        WeakReference<PublishResourceActivity> akD;

        a(PublishResourceActivity activity) {
            this.akD = new WeakReference(activity);
        }

        @MessageHandler(message = 263)
        public void onRecvLevelData(boolean succ, Object object) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity != null) {
                activity.cs(false);
                activity.dN(true);
                if (succ) {
                    String datName = Mojang.instance().getLevel().getLevelName();
                    if (datName == null) {
                        activity.a("获取不到地图名字，请先到首页我的资源处重命名！", true, false);
                        activity.Vt();
                        HLog.info(PublishResourceActivity.TAG, "Publish Map get the Level Name fail Null.", new Object[0]);
                        return;
                    } else if (datName.length() < 2) {
                        activity.a(activity.getString(R.string.map_name_noless_tips) + "(重命名请确定)", false, true);
                        return;
                    } else if (datName.length() > 15) {
                        activity.a(activity.getString(R.string.map_name_nomore_tips) + "(重命名请确定)", false, true);
                        return;
                    } else if (activity.aP(datName, activity.cdW)) {
                        activity.cdX.sX = datName;
                        String tmpPath = j.Kq() + datName;
                        activity.ceo = datName;
                        activity.ceG.setText(aw.W(datName, 8));
                        activity.ceK = tmpPath;
                        return;
                    } else {
                        activity.Vt();
                        return;
                    }
                }
                t.n(activity, activity.getString(R.string.map_name_read_fails));
                activity.Vt();
                HLog.info(PublishResourceActivity.TAG, "Publish Map get the map fail.", new Object[0]);
            }
        }
    }

    static class b extends CallbackHandler {
        WeakReference<PublishResourceActivity> akD;

        b(PublishResourceActivity activity) {
            this.akD = new WeakReference(activity);
        }

        @MessageHandler(message = 291)
        public void onRecvHomeCateListInfo(boolean succ, Map<String, ArrayList<MyMapCateItem>> info) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity == null) {
                return;
            }
            if (!succ || info == null) {
                t.n(activity, "惹~获取分类失败！");
                return;
            }
            activity.ceq = info;
            switch (activity.cem) {
                case 1:
                    activity.W((List) info.get("map_cate"));
                    return;
                case 2:
                    activity.W((List) info.get("js_cate"));
                    return;
                case 3:
                    activity.W((List) info.get("skin_cate"));
                    return;
                case 4:
                    activity.W((List) info.get("wood_cate"));
                    return;
                default:
                    return;
            }
        }

        @MessageHandler(message = 2322)
        public void onRecvCheckResult(boolean succ, String msg) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity == null) {
                return;
            }
            if (succ) {
                activity.Vs();
                return;
            }
            activity.cs(false);
            activity.dN(true);
            activity.cer = false;
            if (msg.contains("名字重复")) {
                activity.a(msg + "(重命名请确定)", false, true);
            } else if (msg.contains("已经上传过")) {
                activity.a(msg + "(一天只能上传一个同类资源哟)", false, false);
            } else if (msg.contains("权限")) {
                activity.a(msg + "(您的等级有些低哟，快去签到升级吧)", false, false);
            } else {
                activity.a(msg, false, false);
            }
            com.huluxia.r.ck().K(hlx.data.tongji.a.bNz);
        }

        @MessageHandler(message = 2321)
        public void onUploadFile(boolean succ, String msg, String md5, long size, String fileUrl) {
            HLog.debug("ETPrint", "onUploadFile issucc " + succ + "msg " + msg, new Object[0]);
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity == null) {
                HLog.debug("ETPrint", "onUploadFile activity is null ", new Object[0]);
            } else if (succ) {
                activity.cdX.url = fileUrl;
                activity.cdX.md5 = md5;
                activity.cdX.size = size;
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNB);
                activity.ks(0);
            } else {
                activity.cs(false);
                activity.dN(true);
                activity.cer = false;
                t.n(activity, msg);
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNC);
            }
        }

        @MessageHandler(message = 2562)
        public void onUpdateFileUploadProgress(boolean flag, String msg, float percent) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity != null && flag) {
                activity.ek(msg + percent + "%");
            }
        }

        @MessageHandler(message = 2560)
        public void onUploadForumPost(boolean success, String msg) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity != null) {
                activity.cs(false);
                activity.dN(true);
                activity.cer = false;
                if (success) {
                    t.o(activity, activity.getString(R.string.res_upload_success));
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNE);
                    ah.KZ().LW();
                    activity.cdY = true;
                    activity.finish();
                    return;
                }
                activity.a(msg, false, false);
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNF);
            }
        }

        @MessageHandler(message = 2561)
        public void onUpdatePost(boolean success, String msg) {
            PublishResourceActivity activity = (PublishResourceActivity) this.akD.get();
            if (activity != null) {
                activity.cs(false);
                activity.dN(true);
                activity.cer = false;
                if (success) {
                    t.o(activity, activity.getString(R.string.res_update_success));
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNH);
                    ah.KZ().LW();
                    activity.finish();
                    return;
                }
                activity.a(msg, false, false);
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNI);
            }
        }
    }

    private class c implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ PublishResourceActivity ced;

        private c(PublishResourceActivity publishResourceActivity) {
            this.ced = publishResourceActivity;
        }

        public void rb() {
        }

        public void cf(String msg) {
            msg = msg.trim();
            if (msg.length() >= 1) {
                String tmpFileName = this.ced.ceK;
                String tmpNewFileName = "";
                File tmpFile = new File(tmpFileName);
                boolean tmpRenameSuc = false;
                switch (this.ced.cem) {
                    case 1:
                        tmpRenameSuc = hlx.gameoperator.c.j(this.ced.cdV, tmpFile.getName(), msg);
                        break;
                    case 2:
                        tmpRenameSuc = UtilsFile.rename(tmpFileName, tmpFile.getParent() + File.separator + msg + hlx.data.localstore.a.bJY);
                        tmpNewFileName = tmpFile.getParent() + File.separator + msg + hlx.data.localstore.a.bJY;
                        break;
                    case 3:
                        tmpRenameSuc = UtilsFile.rename(tmpFileName, tmpFile.getParent() + File.separator + msg + hlx.data.localstore.a.bKa);
                        tmpNewFileName = tmpFile.getParent() + File.separator + msg + hlx.data.localstore.a.bKa;
                        break;
                    case 4:
                        tmpRenameSuc = UtilsFile.rename(tmpFileName, tmpFile.getParent() + File.separator + msg + ".zip");
                        tmpNewFileName = tmpFile.getParent() + File.separator + msg + ".zip";
                        break;
                }
                this.ced.ceK = tmpNewFileName;
                this.ced.ceo = msg;
                this.ced.ceG.setText(aw.W(this.ced.ceo, 8));
                if (!tmpRenameSuc) {
                    return;
                }
                if (this.ced.cem == 1) {
                    try {
                        this.ced.cdW = msg;
                        Mojang.instance().init(msg, 0, null);
                        return;
                    } catch (Exception e) {
                        HLog.verbose(PublishResourceActivity.TAG, "Mojang %s", e.getMessage());
                        t.n(this.ced.cdV, "读取地图异常，请尝试进入该存档或改名！");
                        this.ced.ceK = "";
                        return;
                    }
                }
                if (this.ced.cem != 4) {
                    tmpFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf("."));
                    tmpNewFileName = tmpNewFileName.substring(0, tmpNewFileName.lastIndexOf("."));
                }
                UtilsFile.rename(tmpFileName + hlx.data.localstore.a.bKb, tmpNewFileName + hlx.data.localstore.a.bKb);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sw();
        Vo();
        Vp();
        Va();
    }

    private void Sw() {
        this.cdV = this;
        this.cdX = new r();
        this.cdY = false;
        this.cer = false;
        cec = new b(this);
        akz = new a(this);
        this.cdZ = false;
        this.cea = false;
        if (this.cep == null) {
            boolean j = ah.KZ().j(hlx.data.localstore.a.bLy, true);
            this.cdZ = j;
            this.cea = j;
        }
        if (this.cen == 3) {
            this.cdX.id = (long) this.cep.id;
            this.cdX.url = this.cep.downUrl;
            this.cdX.md5 = this.cep.md5;
            this.cdX.size = this.cep.mapSize;
            this.cdX.sX = this.cep.pageName;
            this.cdX.version = this.cep.version;
        }
    }

    private void Vo() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutPublishRes);
        this.mDrawerLayout.setFocusableInTouchMode(true);
        findViewById(R.id.tvPublishRes).setOnClickListener(this.ceb);
        findViewById(R.id.tvPublishResTopic1).setOnClickListener(this.ceb);
        findViewById(R.id.tvPublishResTopic2).setOnClickListener(this.ceb);
        CheckBox cbAutoShow = (CheckBox) findViewById(R.id.cbClosePublishTips);
        cbAutoShow.setChecked(!this.cdZ);
        cbAutoShow.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
            final /* synthetic */ PublishResourceActivity ced;

            {
                this.ced = this$0;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                this.ced.cdZ = !this.ced.cdZ;
                ah.KZ().k(hlx.data.localstore.a.bLy, this.ced.cdZ);
            }
        });
    }

    private void Vp() {
        this.ceG.setOnClickListener(this.mClickListener);
    }

    private void Va() {
        EventNotifyCenter.add(n.class, cec);
        EventNotifyCenter.add(MojangMessage.class, akz);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && this.cea && !this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.mDrawerLayout.openDrawer(8388611);
            this.cea = false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (data != null) {
                String filePath = data.getStringExtra("filepath");
                if (filePath != null) {
                    switch (this.cem) {
                        case 1:
                            hL(new File(filePath).getName());
                            break;
                        case 2:
                        case 3:
                        case 4:
                            this.cdX.sX = hM(filePath);
                            this.ceo = this.cdX.sX;
                            this.ceG.setText(aw.W(this.ceo, 8));
                            break;
                    }
                    this.ceK = filePath;
                    return;
                }
            }
            String cropPath = q.a(resultCode, requestCode, data, (Activity) this, null, false);
            if (this.bhm.onActivityResult(requestCode, resultCode, data)) {
                this.ceJ.setVisibility(0);
                super.ET();
            }
        }
    }

    private void hL(String inputMapDirName) {
        cs(true);
        dN(false);
        try {
            this.cdW = inputMapDirName;
            Mojang.instance().init(inputMapDirName, 0, null);
        } catch (Exception e) {
            HLog.error(hlx.data.localstore.a.bKf, "refreshMapName error %s", e.getMessage());
            cs(false);
            dN(true);
        }
    }

    private String hM(String path) {
        String _temp = path;
        while (_temp.contains(File.separator)) {
            _temp = _temp.substring(_temp.indexOf(File.separator) + 1);
        }
        return _temp.substring(0, _temp.lastIndexOf(46));
    }

    public boolean Vq() {
        if (!com.huluxia.data.j.ep().ey()) {
            t.an(this.cdV);
            return false;
        } else if (UtilsFunction.empty(this.ceq)) {
            com.huluxia.r.ck().K(hlx.data.tongji.a.bNw);
            VC();
            return false;
        } else if (this.sH == 0) {
            t.show_toast(this.cdV, this.cdV.getString(R.string.update_category_tips));
            super.VE();
            return false;
        } else {
            String filePath = this.ceK;
            if (TextUtils.isEmpty(filePath)) {
                t.show_toast(this.cdV, this.cdV.getString(R.string.upload_file_tips));
                return false;
            }
            if (!filePath.startsWith("http://")) {
                File file = new File(filePath);
                if (!file.exists()) {
                    t.n(this.cdV, this.cdV.getString(R.string.please_upload_res_file));
                    return false;
                } else if (this.cem == 1) {
                    if (com.huluxia.framework.base.utils.UtilsFile.getFileSizes(file) > aa.axI) {
                        t.n(this.cdV, this.cdV.getString(R.string.file_too_large_tips));
                        com.huluxia.r.ck().K(hlx.data.tongji.a.bNy);
                        return false;
                    }
                } else if (file.length() > aa.axI) {
                    t.n(this.cdV, this.cdV.getString(R.string.file_too_large_tips));
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNy);
                    return false;
                }
            }
            if (this.cem == 1 && !filePath.startsWith("http://")) {
                if (!this.ceo.equals(this.cdX.sX)) {
                    t.n(this.cdV, "地图名字出错.请修改名字后再投稿!");
                    return false;
                } else if (!new File(filePath).getName().equals(this.cdX.sX)) {
                    t.n(this.cdV, "地图名字出错..请修改名字后再投稿!");
                    return false;
                }
            }
            if (((RadioButton) this.ceE.NP()) == null) {
                t.n(this.cdV, this.cdV.getString(R.string.please_choose_version));
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNx);
                return false;
            } else if (this.ceo.endsWith(" ") || this.ceo.startsWith(" ")) {
                t.n(this.cdV, "抱歉！资源文件不能以空格开头或结尾，请重新命名！");
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNs);
                Vu();
                return false;
            } else if (this.ceo.trim().length() < 2) {
                t.n(this.cdV, this.cdV.getString(R.string.title_noless_tips));
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNs);
                Vu();
                return false;
            } else if (this.ceo.trim().length() > 15) {
                t.n(this.cdV, this.cdV.getString(R.string.title_nomore_tips));
                com.huluxia.r.ck().K(hlx.data.tongji.a.bNs);
                Vu();
                return false;
            } else {
                String author = aw.gw(this.ceL.getText().toString());
                this.ceL.setText(author);
                if (author.trim().length() < 2) {
                    t.n(this.cdV, this.cdV.getString(R.string.author_noless_tips));
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNt);
                    return false;
                } else if (author.trim().length() > 8) {
                    t.n(this.cdV, this.cdV.getString(R.string.author_nomore_tips));
                    com.huluxia.r.ck().K(hlx.data.tongji.a.bNt);
                    return false;
                } else {
                    String source = aw.gw(this.ceM.getText().toString());
                    this.ceM.setText(source);
                    if (source.trim().length() < 2) {
                        t.n(this.cdV, this.cdV.getString(R.string.source_noless_tips));
                        com.huluxia.r.ck().K(hlx.data.tongji.a.bNu);
                        return false;
                    } else if (source.trim().length() > 8) {
                        t.n(this.cdV, this.cdV.getString(R.string.source_nomore_tips));
                        com.huluxia.r.ck().K(hlx.data.tongji.a.bNu);
                        return false;
                    } else {
                        String detail = aw.gw(this.ceN.getText().toString());
                        this.ceN.setText(detail);
                        if (detail.trim().length() < 5) {
                            t.n(this.cdV, this.cdV.getString(R.string.detail_noless_five));
                            com.huluxia.r.ck().K(hlx.data.tongji.a.bNv);
                            return false;
                        } else if (this.bhm.getPhotos().size() < 4) {
                            t.n(this.cdV, this.cdV.getString(R.string.upload_images_tips));
                            super.VF();
                            return false;
                        } else if (this.ceH.getVisibility() != 0 || this.ceO.getText().toString().length() > 1) {
                            return true;
                        } else {
                            t.n(this.cdV, this.cdV.getString(R.string.please_enter_patcha));
                            return false;
                        }
                    }
                }
            }
        }
    }

    public void Vr() {
        String pageName;
        cs(true);
        dN(false);
        File file = new File(this.ceK);
        if (this.cem == 1) {
            pageName = file.getName();
        } else {
            pageName = hM(this.ceK);
        }
        aa.DQ().b(this.ceo, pageName, this.cem);
    }

    public void Vs() {
        cs(true);
        String path = this.ceK;
        String fileName = new File(path).getName();
        switch (this.cem) {
            case 2:
            case 3:
            case 4:
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                break;
        }
        com.huluxia.r.ck().K(hlx.data.tongji.a.bNA);
        aa.DQ().N(path, fileName);
    }

    public void FR() {
        String title = this.ceo;
        String filePath = this.ceK;
        String author = this.ceL.getText().toString();
        String source = this.ceM.getText().toString();
        String detail = this.ceN.getText().toString();
        String patcha = this.ceO.getText().toString();
        this.cdX.images.clear();
        List<com.huluxia.module.picture.b> photos = this.bhm.getPhotos();
        if (photos.size() < 4) {
            this.cer = false;
            t.n(this.cdV, "提交图片少于4张，请重新提交");
            return;
        }
        for (com.huluxia.module.picture.b photo : photos) {
            if (photo.fid != null) {
                this.cdX.images.add(photo.fid);
            } else {
                this.cdX.images.add("huluxia");
            }
        }
        RadioButton radioButton = (RadioButton) this.ceE.NP();
        if (radioButton != null) {
            this.cdX.version = radioButton.getText().toString();
        }
        this.cdX.subVersion = "0.13.1";
        this.cdX.sm = this.sH;
        this.cdX.title = title;
        this.cdX.author = author;
        this.cdX.source = source;
        this.cdX.detail = detail;
        this.cdX.sx = patcha;
        File file = new File(filePath);
        String tmp = file.exists() ? file.getName() : "";
        HLog.info(TAG, "1.%s;2.%s;3.%d.", title, this.cdX.sX, tmp);
        this.cdX.axr = this.cem;
        if (this.cen == 0) {
            com.huluxia.r.ck().K(hlx.data.tongji.a.bND);
            aa.DQ().c(this.cdX);
            return;
        }
        com.huluxia.r.ck().K(hlx.data.tongji.a.bNG);
        aa.DQ().d(this.cdX);
        aa.DQ().aN(this.cdX.id);
    }

    private void Vt() {
        this.ceo = "";
        this.ceG.setText(getResources().getString(R.string.select_upload_file));
        this.ceK = "";
    }

    protected void a(String szMessage, final boolean finish, final boolean isRename) {
        final DialogManager dialogManager = new DialogManager(this.cdV);
        dialogManager.showOkCancelDialog(szMessage, hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, new OkCancelDialogListener(this) {
            final /* synthetic */ PublishResourceActivity ced;

            public void onCancel() {
                dialogManager.dismissDialog();
            }

            public void onOk() {
                if (finish) {
                    this.ced.cdV.finish();
                } else if (isRename) {
                    this.ced.Vu();
                }
            }
        });
    }

    private boolean aP(String mapDatName, String mapDirName) {
        if (Pattern.compile("[\\/:*?\"<>|]").matcher(mapDatName).find()) {
            a("同步地图目录名字失败（地图名带有特殊字符，重命名请确定）", false, true);
            return false;
        }
        if (!mapDatName.equals(mapDirName)) {
            String oldDir = j.Kq() + mapDirName;
            String newDir = j.Kq() + mapDatName;
            if (j.isExist(newDir)) {
                a("同步地图目录名字失败（已存在同名目录\"" + mapDatName + "\"，重命名请确定）", false, true);
                HLog.info(TAG, "Sync map folder name fail (exist namesake folder).", new Object[0]);
                return false;
            } else if (!UtilsFile.rename(oldDir, newDir)) {
                a("同步地图目录名字操作失败，重命名请确定）", false, true);
                HLog.info(TAG, "Sync map folder name fail (operate fail).", new Object[0]);
                return false;
            }
        }
        hlx.gameoperator.c.aC(mapDatName, mapDatName);
        return true;
    }

    private void Vu() {
        String tmpFileName = UtilsFile.eV(this.ceK);
        switch (this.cem) {
            case 1:
                if (Mojang.instance().getLevel() == null) {
                    return;
                }
                break;
            case 2:
            case 3:
            case 4:
                tmpFileName = tmpFileName.substring(0, tmpFileName.lastIndexOf("."));
                break;
        }
        e dialog = new e(this.cdV, new c());
        dialog.aA(this.cdV.getString(R.string.local_resmgr_dlg_cancle), this.cdV.getString(R.string.local_resmgr_dlg_save));
        dialog.setText(tmpFileName);
        dialog.showDialog();
    }

    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(findViewById(R.id.rlyResPublishTips))) {
            this.mDrawerLayout.closeDrawers();
        } else if (this.cep != null) {
            super.onBackPressed();
        } else if (this.cer) {
            a("抱歉！正在提交资源，无法退出!", false, false);
        } else {
            a(this.cdV.getString(R.string.giveup_resource_contribute_tips), true, false);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
        if (this.cep == null && !this.cdY) {
            ES();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(akz);
        EventNotifyCenter.remove(cec);
    }
}
