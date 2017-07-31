package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;

class TbsDownloadConfig {
    private static TbsDownloadConfig c;
    Map<String, Object> a = new HashMap();
    SharedPreferences b;

    public interface TbsConfigKey {
        public static final String KEY_APP_METADATA = "app_metadata";
        public static final String KEY_APP_VERSIONCODE = "app_versioncode";
        public static final String KEY_APP_VERSIONCODE_FOR_SWITCH = "app_versioncode_for_switch";
        public static final String KEY_APP_VERSIONNAME = "app_versionname";
        public static final String KEY_DEVICE_CPUABI = "device_cpuabi";
        public static final String KEY_DOWNLOAD_FAILED_MAX_RETRYTIMES = "tbs_download_failed_max_retrytimes";
        public static final String KEY_DOWNLOAD_FAILED_RETRYTIMES = "tbs_download_failed_retrytimes";
        public static final String KEY_DOWNLOAD_MAXFLOW = "tbs_download_maxflow";
        public static final String KEY_DOWNLOAD_MIN_FREE_SPACE = "tbs_download_min_free_space";
        public static final String KEY_DOWNLOAD_SINGLE_TIMEOUT = "tbs_single_timeout";
        public static final String KEY_DOWNLOAD_SUCCESS_MAX_RETRYTIMES = "tbs_download_success_max_retrytimes";
        public static final String KEY_DOWNLOAD_SUCCESS_RETRYTIMES = "tbs_download_success_retrytimes";
        public static final String KEY_IS_OVERSEA = "is_oversea";
        public static final String KEY_LAST_CHECK = "last_check";
        public static final String KEY_NEEDDOWNLOAD = "tbs_needdownload";
        public static final String KEY_RESPONSECODE = "tbs_responsecode";
        public static final String KEY_RETRY_INTERVAL = "retry_interval";
        public static final String KEY_TBSAPKFILESIZE = "tbs_apkfilesize";
        public static final String KEY_TBSAPK_MD5 = "tbs_apk_md5";
        public static final String KEY_TBSDOWNLOADURL = "tbs_downloadurl";
        public static final String KEY_TBSDOWNLOAD_FLOW = "tbs_downloadflow";
        public static final String KEY_TBSDOWNLOAD_STARTTIME = "tbs_downloadstarttime";
        public static final String KEY_TBS_DOWNLOAD_V = "tbs_download_version";
    }

    private TbsDownloadConfig(Context context) {
        this.b = context.getSharedPreferences("tbs_download_config", 4);
    }

    public static synchronized TbsDownloadConfig a(Context context) {
        TbsDownloadConfig tbsDownloadConfig;
        synchronized (TbsDownloadConfig.class) {
            if (c == null) {
                c = new TbsDownloadConfig(context);
            }
            tbsDownloadConfig = c;
        }
        return tbsDownloadConfig;
    }

    public synchronized long a() {
        return this.b.getLong(TbsConfigKey.KEY_RETRY_INTERVAL, 86400);
    }

    public synchronized void b() {
        Editor edit = this.b.edit();
        for (String str : this.a.keySet()) {
            Object obj = this.a.get(str);
            if (obj instanceof String) {
                edit.putString(str, (String) obj);
            } else {
                try {
                    if (obj instanceof Boolean) {
                        edit.putBoolean(str, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof Long) {
                        edit.putLong(str, ((Long) obj).longValue());
                    } else if (obj instanceof Integer) {
                        edit.putInt(str, ((Integer) obj).intValue());
                    } else if (obj instanceof Float) {
                        edit.putFloat(str, ((Float) obj).floatValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        edit.commit();
        this.a.clear();
        return;
    }
}
