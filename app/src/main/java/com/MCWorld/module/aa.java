package com.MCWorld.module;

import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.data.j;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsMD5;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.r;
import com.MCWorld.utils.p;
import com.MCWorld.widget.Constants;
import com.tencent.open.SocialConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/* compiled from: UploadModule */
public class aa {
    private static boolean DEBUG = false;
    private static final String TAG = "UploadModule";
    private static aa axH = null;
    public static final long axI = 52428800;
    private Handler mHandler;
    private HandlerThread thread;

    public static synchronized aa DQ() {
        aa aaVar;
        synchronized (aa.class) {
            if (axH == null) {
                axH = new aa();
            }
            aaVar = axH;
        }
        return aaVar;
    }

    private void DR() {
        if (this.thread == null) {
            this.thread = new HandlerThread("file");
            this.thread.start();
            this.mHandler = new Handler(this.thread.getLooper());
        }
    }

    private String M(String filePath, String zipName) {
        String GenerateZipPath = null;
        if (filePath.trim().toLowerCase().endsWith(".zip")) {
            return filePath;
        }
        File file = new File(filePath);
        try {
            if (file.isFile()) {
                GenerateZipPath = p.b(filePath, file.getParent(), zipName + ".zip", false);
            } else {
                GenerateZipPath = p.b(filePath, file.getParent(), zipName + ".zip", true);
            }
        } catch (Exception e) {
            EventNotifyCenter.notifyEventUiThread(n.class, n.awx, Boolean.valueOf(false), "压缩文件失败", "", Integer.valueOf(0), null);
        }
        if (UtilsFunction.empty((CharSequence) GenerateZipPath)) {
            GenerateZipPath = null;
            EventNotifyCenter.notifyEventUiThread(n.class, n.awx, Boolean.valueOf(false), "压缩文件不存在", "", Integer.valueOf(0), null);
        }
        return GenerateZipPath;
    }

    private long dZ(String filePath) {
        long size = UtilsFile.fileSize(filePath);
        if (size <= axI) {
            return size;
        }
        HLog.error(TAG, "文件大小为：" + size + "K_umengEvent,大于50M", new Object[0]);
        EventNotifyCenter.notifyEventUiThread(n.class, n.awx, Boolean.valueOf(false), "不支持大于50M的文件", "", Long.valueOf(size), null);
        return -1;
    }

    private String ea(String filePath) {
        String _md5 = null;
        try {
            _md5 = UtilsMD5.getFileMd5String(filePath);
        } catch (IOException e) {
            EventNotifyCenter.notifyEventUiThread(n.class, n.awx, Boolean.valueOf(false), "获取文件MD5失败", "", Integer.valueOf(0), null);
        }
        return _md5;
    }

    public void N(String filePath, String fileName) {
        if (!UtilsFunction.empty((CharSequence) filePath)) {
            DR();
            this.mHandler.post(new 1(this, filePath, fileName));
        }
    }

    public void b(String title, String pageName, int resType) {
        String _tempURL = jt(resType);
        HashMap<String, String> params = new HashMap();
        params.put("title", title);
        params.put("page_name", pageName);
        HttpMgr.getInstance().performStringRequest(_tempURL, params, new 7(this, resType), new 8(this, resType));
    }

    private String jt(int resType) {
        switch (resType) {
            case 1:
                return ab.ayG;
            case 2:
                return ab.ayH;
            case 3:
                return ab.ayJ;
            case 4:
                return ab.ayI;
            default:
                return null;
        }
    }

    public void a(r content) {
        if (content != null) {
            if (this.thread == null) {
                this.thread = new HandlerThread("post");
                this.thread.start();
                this.mHandler = new Handler(this.thread.getLooper());
            }
            HashMap<String, String> params = new HashMap();
            String tempImage = "";
            int imageLength = content.images.size();
            int i = 0;
            while (i < imageLength) {
                tempImage = tempImage + ((String) content.images.get(i)) + (i != imageLength + -1 ? MiPushClient.ACCEPT_TIME_SEPARATOR : "");
                i++;
            }
            params.put("cat_id", String.valueOf(Constants.bsT));
            params.put("tag_id", String.valueOf(content.sm));
            params.put("title", content.title);
            params.put("images", tempImage);
            params.put(r.gQ, content.detail);
            params.put("type", String.valueOf(1));
            params.put("user_id", String.valueOf(j.ep().getUserid()));
            HttpMgr.getInstance().performPostStringRequest(ab.ayT, null, params, new 9(this), new 10(this), true, false, false, 50000);
        }
    }

    public void b(r content) {
        if (content != null) {
            if (this.thread == null) {
                this.thread = new HandlerThread("post");
                this.thread.start();
                this.mHandler = new Handler(this.thread.getLooper());
            }
            HashMap<String, String> params = new HashMap();
            String tempImage = "";
            int imageLength = content.images.size();
            int i = 0;
            while (i < imageLength) {
                tempImage = tempImage + ((String) content.images.get(i)) + (i != imageLength + -1 ? MiPushClient.ACCEPT_TIME_SEPARATOR : "");
                i++;
            }
            params.put("post_id", String.valueOf(content.id));
            params.put("title", content.title);
            params.put("images", tempImage);
            params.put(r.gQ, content.detail);
            params.put("tag_id", String.valueOf(content.sm));
            params.put("user_id", String.valueOf(j.ep().getUserid()));
            HttpMgr.getInstance().performPostStringRequest(ab.ayU, null, params, new 11(this), new 12(this), true, false, false, 50000);
        }
    }

    public void c(r content) {
        if (content != null) {
            if (this.thread == null) {
                this.thread = new HandlerThread("file");
                this.thread.start();
                this.mHandler = new Handler(this.thread.getLooper());
            }
            String _tempURL = ju(content.axr);
            HashMap<String, String> params = a(content, false);
            if (DEBUG) {
                String str = TAG;
                String str2 = "LSPrint UploadModule send ForumPost [params:%s]";
                Object[] objArr = new Object[1];
                objArr[0] = params == null ? "空" : params.toString();
                HLog.verbose(str, str2, objArr);
            }
            HttpMgr.getInstance().performPostStringRequest(_tempURL, null, params, new 13(this, content), new 14(this, content), true, false, false, 50000);
        }
    }

    private String ju(int resType) {
        switch (resType) {
            case 1:
                return ab.ayK;
            case 2:
                return ab.ayL;
            case 3:
                return ab.ayN;
            case 4:
                return ab.ayM;
            default:
                return null;
        }
    }

    private HashMap<String, String> a(r content, boolean isUpdate) {
        HashMap<String, String> params = new HashMap();
        params.put(r.gQ, content.detail);
        String tempImage = "";
        int imageLength = content.images.size();
        int i = 0;
        while (i < imageLength) {
            tempImage = tempImage + ((String) content.images.get(i)) + (i != imageLength + -1 ? MiPushClient.ACCEPT_TIME_SEPARATOR : "");
            i++;
        }
        params.put("images", tempImage);
        params.put("cat_id", String.valueOf(content.sm));
        params.put("res_url", content.url);
        params.put("map_size", String.valueOf(content.size));
        params.put("page_name", content.sX);
        params.put("title", content.title);
        params.put("author", content.author);
        params.put(SocialConstants.PARAM_SOURCE, content.source);
        params.put("version", content.version);
        if (content.axr == 2 && content.version.contains("0.13")) {
            params.put("subvsersion", content.subVersion);
        } else if (content.axr == 2) {
            params.put("subvsersion", "0");
        }
        if (content.axq != null) {
            params.put("matchResName", content.axq);
        }
        params.put("md5", content.md5);
        if (isUpdate) {
            params.put("id", String.valueOf(content.id));
        }
        return params;
    }

    public void d(r content) {
        if (content != null) {
            if (this.thread == null) {
                this.thread = new HandlerThread("file");
                this.thread.start();
                this.mHandler = new Handler(this.thread.getLooper());
            }
            String _tempURL = jv(content.axr);
            HashMap<String, String> params = a(content, true);
            if (DEBUG) {
                String str = TAG;
                String str2 = "LSPrint UploadModule Update ForumPost [params:%s]";
                Object[] objArr = new Object[1];
                objArr[0] = params == null ? "空" : params.toString();
                HLog.verbose(str, str2, objArr);
            }
            HttpMgr.getInstance().performPostStringRequest(_tempURL, null, params, new 2(this, content), new 3(this, content), true, false, false, 50000);
        }
    }

    private String jv(int resType) {
        switch (resType) {
            case 1:
                return ab.ayO;
            case 2:
                return ab.ayP;
            case 3:
                return ab.ayR;
            case 4:
                return ab.ayQ;
            default:
                return null;
        }
    }

    public void aN(long map_id) {
        HashMap<String, String> params = new HashMap();
        params.put("map_id", String.valueOf(map_id));
        if (this.thread == null) {
            this.thread = new HandlerThread("file");
            this.thread.start();
            this.mHandler = new Handler(this.thread.getLooper());
        }
        HttpMgr.getInstance().performPostStringRequest(ab.ayS, null, params, new 4(this), new 5(this), true, false, false);
    }

    public void a(int index, String filePath, Object context) {
        if (this.thread == null) {
            this.thread = new HandlerThread("file");
            this.thread.start();
            this.mHandler = new Handler(this.thread.getLooper());
        }
        this.mHandler.post(new 6(this, filePath, index, context));
    }
}
