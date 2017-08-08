package com.MCWorld;

import android.os.SystemClock;
import android.telephony.TelephonyManager;
import com.MCWorld.data.profile.a;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.http.other.f;
import com.MCWorld.widget.Constants.FeedBackType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: CrashCollector */
class e$1 implements Runnable {
    final /* synthetic */ String eJ;
    final /* synthetic */ e eK;

    e$1(e this$0, String str) {
        this.eK = this$0;
        this.eJ = str;
    }

    public void run() {
        File file = new File(new File(HLog.getLogOutputPaths().currentLogFile).getParent() + File.separator + "crash-" + String.valueOf(SystemClock.elapsedRealtime()) + ".txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(this.eJ.getBytes());
            outputStream.flush();
            outputStream.close();
            HttpMgr.getInstance().performUpload("http://upload.huluxia.net/upload/file", file.getAbsolutePath(), null, new Listener<String>(this) {
                final /* synthetic */ e$1 eL;

                {
                    this.eL = this$1;
                }

                public void onResponse(String response) {
                    HLog.info("CrashCollector", "upload crash response %s", new Object[]{response});
                    String logUrl = "";
                    if (!UtilsFunction.empty(response)) {
                        try {
                            List<e$a> filters = new ArrayList();
                            filters.add(new e$d());
                            filters.add(new e$b());
                            filters.add(new e$c());
                            logUrl = new JSONObject(response).getString("url");
                            f feedbackRequest = new f();
                            feedbackRequest.bb(2);
                            feedbackRequest.setFlag(FeedBackType.MCBUG.Value());
                            feedbackRequest.setExt(e.a(this.eL.eK, "mobile:" + ((TelephonyManager) AppConfig.getInstance().getAppContext().getSystemService(a.qe)).getLine1Number()));
                            StringBuilder detail = new StringBuilder("【自动反馈】\n");
                            if (!UtilsFunction.empty(logUrl)) {
                                detail = detail.append("   <a_isRightVersion href=" + logUrl + ">反馈日志</a_isRightVersion>");
                            }
                            for (e$a filter : filters) {
                                if (filter.t(this.eL.eJ)) {
                                    detail.append("【" + filter.getText() + "】");
                                }
                            }
                            feedbackRequest.setText(detail.toString());
                            feedbackRequest.eY();
                        } catch (Exception e) {
                            HLog.error("CrashCollector", "upload crash reponse json err " + e, new Object[0]);
                        }
                    }
                }
            }, new ErrorListener(this) {
                final /* synthetic */ e$1 eL;

                {
                    this.eL = this$1;
                }

                public void onErrorResponse(VolleyError error) {
                    HLog.info("CrashCollector", "onErrorResponse %s", new Object[]{error});
                }
            }, null, null, false);
        } catch (IOException e) {
            HLog.error("CrashCollector", "create file failed " + e, new Object[0]);
        }
    }
}
