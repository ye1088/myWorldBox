package com.tencent.smtt.sdk;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient.FileChooserParams;
import com.tencent.smtt.utils.m;

public abstract class WebChromeClient$FileChooserParams extends FileChooserParams {
    public static final int MODE_OPEN = 0;
    public static final int MODE_OPEN_FOLDER = 2;
    public static final int MODE_OPEN_MULTIPLE = 1;
    public static final int MODE_SAVE = 3;

    public static Uri[] parseResult(int i, Intent intent) {
        try {
            i a = i.a(false);
            if (a != null && a.b()) {
                return a.a().a(i, intent);
            }
            Object a2 = m.a(Class.forName("com.android.webview.chromium.FileChooserParamsAdapter"), "parseFileChooserResult", new Class[]{Integer.TYPE, Intent.class}, Integer.valueOf(i), intent);
            return a2 == null ? null : (Uri[]) a2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract Intent createIntent();

    public abstract String[] getAcceptTypes();

    public abstract String getFilenameHint();

    public abstract int getMode();

    public abstract CharSequence getTitle();

    public abstract boolean isCaptureEnabled();
}
