package com.huluxia.framework.base.utils;

import android.net.Uri;
import android.net.Uri.Builder;
import android.support.annotation.z;
import java.io.File;

public class UtilUri {
    public static final String DATA_SCHEME = "data";
    public static final String HTTPS_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";
    public static final String LOCAL_ASSET_SCHEME = "asset";
    public static final String LOCAL_CONTENT_SCHEME = "content";
    public static final String LOCAL_FILE_SCHEME = "file";
    public static final String LOCAL_RESOURCE_SCHEME = "res";

    public static boolean isNetworkUri(@z Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "https".equals(scheme) || "http".equals(scheme);
    }

    public static boolean isLocalFileUri(@z Uri uri) {
        return "file".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContentUri(@z Uri uri) {
        return "content".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalAssetUri(@z Uri uri) {
        return "asset".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalResourceUri(@z Uri uri) {
        return "res".equals(getSchemeOrNull(uri));
    }

    public static boolean isDataUri(@z Uri uri) {
        return "data".equals(getSchemeOrNull(uri));
    }

    @z
    public static String getSchemeOrNull(@z Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    public static Uri parseUriOrNull(@z String uriAsString) {
        return uriAsString != null ? Uri.parse(uriAsString) : null;
    }

    @z
    public static String getAuthorityOrNull(@z Uri uri) {
        return uri == null ? null : uri.getAuthority();
    }

    @z
    public static Uri getAssetResourceUriOrNull(String assetName) {
        if (assetName == null) {
            return null;
        }
        return new Builder().scheme("asset").path(assetName).build();
    }

    @z
    public static Uri getLocalFileUriOrNull(File file) {
        return file == null ? null : Uri.fromFile(file);
    }

    @z
    public static Uri getUriOrNull(String url) {
        return url == null ? null : Uri.parse(url);
    }
}
