package com.baidu.android.pushservice;

import android.content.Context;
import android.content.Intent;
import com.baidu.android.pushservice.apiproxy.BridgePushConstants;
import com.baidu.frontia.base.util.Base64;
import com.huluxia.framework.base.utils.UtilsRSA;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class PushConstants {
    public static final String ACTION_FB_MESSAGE = "com.baidu.android.pushservice.action.FB_MESSAGE";
    public static final String ACTION_LAPP_MESSAGE = "com.baidu.android.pushservice.action.LAPP_MESSAGE";
    public static final String ACTION_LAPP_RECEIVE = "com.baidu.android.pushservice.action.lapp.RECEIVE";
    public static final String ACTION_MESSAGE = "com.baidu.android.pushservice.action.MESSAGE";
    public static final String ACTION_METHOD = "com.baidu.android.pushservice.action.METHOD";
    public static final String ACTION_RECEIVE = "com.baidu.android.pushservice.action.RECEIVE";
    public static final String ACTION_RECEIVER_NOTIFICATION_CLICK = "com.baidu.android.pushservice.action.notification.CLICK";
    public static final String ACTION_SDK_MESSAGE = "com.baidu.android.pushservice.action.SDK_MESSAGE";
    public static final String ACTION_SDK_RECEIVE = "com.baidu.android.pushservice.action.sdk.RECEIVE";
    public static final String ACTION_WEB_RECEIVE = "com.baidu.android.pushservice.action.web.RECEIVE";
    public static final int BIND_STATUS_OFFLINE = 1;
    public static final int BIND_STATUS_ONLINE = 0;
    public static final int ERROR_AIDL_FAIL = 40001;
    public static final String ERROR_AIDL_FAIL_EXCEPTION = "aidl_error: INTERNAL_EXCEPTION";
    public static final String ERROR_AIDL_FAIL_NO_PUSHSERVICE = "aidl_error: NO BINDED PUSHSERVICE";
    public static final int ERROR_AUTHENTICATION_FAILED = 30603;
    public static final int ERROR_BIND_NOT_EXIST = 30608;
    public static final int ERROR_BIND_OVERLOAD = 30609;
    public static final int ERROR_CHANNEL_TOKEN_TIMEOUT = 30607;
    public static final int ERROR_DATA_NOT_FOUND = 30605;
    public static final int ERROR_METHOD_ERROR = 30601;
    public static final int ERROR_NETWORK_ERROR = 10001;
    public static final int ERROR_PARAMS_ERROR = 30602;
    public static final int ERROR_QUOTA_USE_UP = 30604;
    public static final int ERROR_SERVER_INTERNAL_ERROR = 30600;
    public static final int ERROR_SERVICE_NOT_AVAILABLE = 10002;
    public static final int ERROR_SERVICE_NOT_AVAILABLE_TEMP = 10003;
    public static final int ERROR_SUCCESS = 0;
    public static final int ERROR_TIME_EXPIRES = 30606;
    public static final int ERROR_UNKNOWN = 20001;
    public static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_API_KEY = "secret_key";
    public static final String EXTRA_APP = "app";
    public static final String EXTRA_APP_ID = "app_id";
    public static final String EXTRA_BIND_NAME = "bind_name";
    public static final String EXTRA_BIND_STATUS = "bind_status";
    public static final String EXTRA_CB_URL = "cb_url";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_ERROR_CODE = "error_msg";
    public static final String EXTRA_EXTRA = "extra_extra_custom_content";
    public static final String EXTRA_FETCH_NUM = "fetch_num";
    public static final String EXTRA_FETCH_TYPE = "fetch_type";
    public static final String EXTRA_GID = "gid";
    public static final String EXTRA_GROUP_FETCH_NUM = "group_fetch_num";
    public static final String EXTRA_GROUP_FETCH_TYPE = "group_fetch_type";
    public static final String EXTRA_HASHCODE = "hash_code";
    public static final String EXTRA_LAPP_BIND_STATE = "lapp_bind_state";
    public static final String EXTRA_METHOD = "method";
    public static final String EXTRA_MSG = "push_ msg";
    public static final String EXTRA_MSGID = "msgid";
    public static final String EXTRA_MSG_IDS = "msg_ids";
    public static final String EXTRA_MSG_KEY = "push_ msg_key";
    public static final String EXTRA_NOTIFICATION_CONTENT = "notification_content";
    public static final String EXTRA_NOTIFICATION_TITLE = "notification_title";
    public static final String EXTRA_OPENTYPE = "open_type";
    public static final String EXTRA_PUSH_MESSAGE = "message";
    public static final String EXTRA_PUSH_MESSAGE_STRING = "message_string";
    public static final String EXTRA_PUSH_SDK_VERSION = "push_sdk_version";
    public static final String EXTRA_TAGS = "tags";
    public static final String EXTRA_TAGS_LIST = "tags_list";
    public static final String EXTRA_TIMESTAMP = "time_stamp";
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_WEB_BIND_API_KEY = "com.baidu.pushservice.webapp.apikey";
    public static final int LOGIN_TYPE_ACCESS_TOKEN = 1;
    public static final int LOGIN_TYPE_API_KEY = 0;
    public static final int LOGIN_TYPE_BDUSS = 2;
    public static final int LOGIN_TYPE_LIGHT_APP_API_KEY = 4;
    public static final int LOGIN_TYPE_UNKNOWN = 9;
    public static final String METHOD_APPSTART = "method_appstart";
    public static final String METHOD_APPSTOP = "method_appstop";
    public static final String METHOD_BIND = "method_bind";
    public static final String METHOD_COUNT = "method_count";
    public static final String METHOD_COUNTGMSG = "method_countgmsg";
    public static final String METHOD_DELETE = "method_delete";
    public static final String METHOD_DEL_LAPP_TAGS = "method_del_lapp_tags";
    public static final String METHOD_DEL_TAGS = "method_del_tags";
    public static final String METHOD_FETCH = "method_fetch";
    public static final String METHOD_FETCHGMSG = "method_fetchgmsg";
    public static final String METHOD_GBIND = "method_gbind";
    public static final String METHOD_GET_LAPP_BIND_STATE = "method_get_lapp_bind_state";
    public static final String METHOD_GINFO = "method_ginfo";
    public static final String METHOD_GLIST = "method_glist";
    public static final String METHOD_GUNBIND = "method_gunbind";
    public static final String METHOD_LAPP_BIND_INTENT = "method_deal_lapp_bind_intent";
    public static final String METHOD_LAPP_UNBIND = "method_lapp_unbind";
    public static final String METHOD_LISTTAGS = "method_listtags";
    public static final String METHOD_LIST_LAPP_TAGS = "method_list_lapp_tags";
    public static final String METHOD_ONLINE = "method_online";
    public static final String METHOD_SEND = "method_send";
    public static final String METHOD_SEND_MSG_TO_SERVER = "method_send_msg_to_server";
    public static final String METHOD_SEND_MSG_TO_USER = "method_send_msg_to_user";
    public static final String METHOD_SET_LAPP_TAGS = "method_set_lapp_tags";
    public static final String METHOD_SET_TAGS = "method_set_tags";
    public static final String METHOD_UNBIND = "method_unbind";
    public static final int OPEN_BY_NOTIFICATION = 1;
    public static final int OPEN_BY_USER = 0;
    public static final short ORIGINAL_VERSION = (short) 23;
    public static final String PACKAGE_NAME = "pkg_name";
    private static final int a = 1024;
    private static final String b = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/7VlVn9LIrZ71PL2RZMbK/Yxc\r\ndb046w/cXVylxS7ouPY06namZUFVhdbUnNRJzmGUZlzs3jUbvMO3l+4c9cw/n9aQ\r\nrm/brgaRDeZbeSrQYRZv60xzJIimuFFxsRM+ku6/dAyYmXiQXlRbgvFQ0MsVng4j\r\nv+cXhtTis2Kbwb8mQwIDAQAB\r\n";

    private static byte[] a(byte[] bArr, String str, int i) throws Exception {
        Key generatePublic = KeyFactory.getInstance(UtilsRSA.KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes())));
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, generatePublic);
        int i2 = i / 8;
        int i3 = i2 - 11;
        int length = bArr.length;
        Object obj = new byte[((((length + i3) - 1) / i3) * i2)];
        int i4 = 0;
        int i5 = 0;
        while (i5 < length) {
            int i6 = length - i5;
            if (i3 < i6) {
                i6 = i3;
            }
            Object obj2 = new byte[i6];
            System.arraycopy(bArr, i5, obj2, 0, i6);
            i5 += i6;
            System.arraycopy(instance.doFinal(obj2), 0, obj, i4, i2);
            i4 += i2;
        }
        return obj;
    }

    public static Intent createMethodIntent(Context context) {
        return !LoadExecutor.loadPush(context) ? new Intent() : BridgePushConstants.createMethodIntent(context);
    }

    public static String getErrorMsg(int i) {
        String str = "Unknown";
        switch (i) {
            case 0:
                return "Success";
            case 10001:
                return "Network Problem";
            case ERROR_SERVICE_NOT_AVAILABLE /*10002*/:
                return "Service not available";
            case ERROR_SERVICE_NOT_AVAILABLE_TEMP /*10003*/:
                return "Service not available temporary";
            case ERROR_SERVER_INTERNAL_ERROR /*30600*/:
                return "Internal Server Error";
            case ERROR_METHOD_ERROR /*30601*/:
                return "Method Not Allowed";
            case ERROR_PARAMS_ERROR /*30602*/:
                return "Request Params Not Valid";
            case ERROR_AUTHENTICATION_FAILED /*30603*/:
                return "Authentication Failed";
            case ERROR_QUOTA_USE_UP /*30604*/:
                return "Quota Use Up Payment Required";
            case ERROR_DATA_NOT_FOUND /*30605*/:
                return "Data Required Not Found";
            case ERROR_TIME_EXPIRES /*30606*/:
                return "Request Time Expires Timeout";
            case ERROR_CHANNEL_TOKEN_TIMEOUT /*30607*/:
                return "Channel Token Timeout";
            case ERROR_BIND_NOT_EXIST /*30608*/:
                return "Bind Relation Not Found";
            case ERROR_BIND_OVERLOAD /*30609*/:
                return "Bind Number Too Many";
            default:
                return "Unknown";
        }
    }

    public static void restartPushService(Context context) {
        if (LoadExecutor.loadPush(context)) {
            BridgePushConstants.restartPushService(context);
        }
    }

    public static String rsaEncrypt(String str) {
        String str2 = null;
        try {
            str2 = Base64.encode(a(str.getBytes(), b, 1024), "utf-8");
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e2) {
        }
        return str2;
    }

    public static void startPushService(Context context) {
        if (LoadExecutor.loadPush(context)) {
            BridgePushConstants.startPushService(context);
        }
    }
}
