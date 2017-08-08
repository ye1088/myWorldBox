package com.microsoft.xbox.idp.interop;

import android.app.Activity;
import android.util.Log;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.widget.x5web.a;
import org.mozilla.javascript.Context;

public class Interop {
    private static final String TAG = "Interop";

    public interface ErrorCallback {
        void onError(int i, int i2, String str);
    }

    public interface Callback extends ErrorCallback {
        void onXTokenAcquired(long j);
    }

    public interface EventInitializationCallback extends ErrorCallback {
        void onSuccess();
    }

    public interface XBLoginCallback extends ErrorCallback {
        void onLogin(long j, boolean z);
    }

    public interface XBLogoutCallback {
        void onLoggedOut();
    }

    private static native void auth_flow_callback(long j, int i, String str);

    public static native boolean deinitializeInterop();

    private static native String get_supporting_x_token_callback(String str);

    private static native String get_title_telemetry_device_id();

    private static native String get_title_telemetry_session_id();

    public static native String get_uploader_x_token_callback(boolean z);

    public static native boolean initializeInterop(Context context);

    private static native void invoke_event_initialization(long j, String str, EventInitializationCallback eventInitializationCallback);

    public static native void invoke_x_token_acquisition(long j, Callback callback);

    public static native void invoke_xb_login(long j, String str, XBLoginCallback xBLoginCallback);

    public static native void invoke_xb_logout(long j, XBLogoutCallback xBLogoutCallback);

    private static native void notificiation_registration_callback(String str, boolean z);

    private static native void sign_out_callback();

    private static native void ticket_callback(String str, int i, int i2, String str2);

    public static void ClearIntent() {
    }

    public static void InitCLL(android.content.Context context, String iKey) {
        Log.i(TAG, "Init CLL");
    }

    public static String GetLiveXTokenCallback(boolean forceRefresh) {
        return "";
    }

    public static String GetLocalStoragePath(Context context) {
        return "";
    }

    public static String GetXTokenCallback(String xuid) {
        return "";
    }

    public static void InvokeAuthFlow(long userPtr, Activity activity, boolean isProd) {
        Log.d(TAG, "InvokeAuthFlow");
    }

    public static void InvokeBrokeredMSA(Context context, boolean isProd) {
        Log.d(TAG, "InvokeAuthFlow");
    }

    public static void InvokeEventInitialization(long userPtr, String rpsTicket, Object callback) {
        Log.d(TAG, "InvokeEventInitialization");
    }

    public static void InvokeLatestIntent(Activity activity, Object intentObject) {
        Log.i(TAG, "InvokeLatestIntent");
    }

    public static void InvokeMSA(Context context, int requestCode, boolean isProd, String cid) {
        Log.i(TAG, "Invoking MSA");
    }

    public static void InvokeXBLogin(long userPtr, String rpsTicket, Object callback) {
        Log.d(TAG, "InvokeXBLogin");
    }

    public static void InvokeXBLogout(long userPtr, Object callback) {
        Log.d(TAG, "InvokeSignOut");
    }

    public static void InvokeXTokenCallback(long userPtr, Object callback) {
        Log.i(TAG, "InvokeXTokenCallback");
    }

    public static void LogCLL(String xuid, String eventName, String eventData) {
        Log.i(TAG, "Log CLL");
    }

    public static void MSACallback(String rpsTicket, int state, int errorCode, String errorMessage) {
        Log.i(TAG, "MSA Callback");
    }

    public static void NotificationRegisterCallback(String regId, boolean isCached) {
        Log.i(TAG, a.bHm);
    }

    public static String ReadConfigFile() {
        HLog.verbose(TAG, "DTPrint ReadConfigFile VOID === ", new Object[0]);
        return "";
    }

    public static String ReadConfigFile(Object paramContext) {
        return "";
    }

    public static String ReadConfigFile(android.content.Context inputContext) {
        return "";
    }

    public static void RegisterWithGNS(Context context) {
        Log.i(TAG, "trying to register..");
    }

    public static Context getApplicationContext() {
        return null;
    }

    public static String getLocale() {
        return "";
    }

    public static String getSystemProxy() {
        return "";
    }

    public static String getTitleDeviceId() {
        return get_title_telemetry_device_id();
    }

    public static String getTitleSessionId() {
        return get_title_telemetry_session_id();
    }
}
