package com.microsoft.xboxtcui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Interop {
    private static final String TAG = Interop.class.getSimpleName();

    public static native void tcui_completed_callback(int i);

    public static void ShowAddFriends(Context paramContext) {
        Log.i(TAG, "Deeplink - ShowAddFriends");
    }

    public static void ShowFriendFinder(Activity paramActivity, String paramString1, String paramString2) {
        Log.i(TAG, "TCUI- ShowFriendFinder - meXuid:" + paramString1);
        Log.i(TAG, "TCUI- ShowFriendFinder: privileges:" + paramString2);
    }

    public static void ShowProfileCardUI(Activity paramActivity, String paramString1, String paramString2, String paramString3) {
        Log.i(TAG, "TCUI- ShowProfileCardUI: meXuid:" + paramString1);
        Log.i(TAG, "TCUI- ShowProfileCardUI: targeProfileXuid:" + paramString2);
        Log.i(TAG, "TCUI- ShowProfileCardUI: privileges:" + paramString3);
    }

    public static void ShowTitleAchievements(Context paramContext, String paramString) {
        Log.i(TAG, "Deeplink - ShowTitleAchievements");
    }

    public static void ShowTitleHub(Context paramContext, String paramString) {
        Log.i(TAG, "Deeplink - ShowTitleHub");
    }

    public static void ShowUserProfile(Context paramContext, String paramString) {
        Log.i(TAG, "Deeplink - ShowUserProfile");
    }

    public static void ShowUserSettings(Context paramContext) {
        Log.i(TAG, "Deeplink - ShowUserSettings");
    }

    private static Activity getForegroundActivity() {
        return null;
    }

    public static String GetLiveXTokenCallback(boolean paramBoolean) {
        return null;
    }

    public static String GetXTokenCallback(String paramString) {
        return null;
    }

    public static void NotificationRegisterCallback(Intent paramIntent, boolean bool2) {
    }
}
