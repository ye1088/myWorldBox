package com.huluxia.image.core.common.util;

import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.ContactsContract;
import android.provider.MediaStore.Images.Media;
import javax.annotation.Nullable;

/* compiled from: UriUtil */
public class f {
    public static final String DATA_SCHEME = "data";
    public static final String HTTPS_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";
    public static final String LOCAL_ASSET_SCHEME = "asset";
    public static final String LOCAL_CONTENT_SCHEME = "content";
    public static final String LOCAL_FILE_SCHEME = "file";
    public static final String LOCAL_RESOURCE_SCHEME = "res";
    private static final String yS = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();
    public static final String yT = "fs_images";
    public static final String yU = "authority_picture";
    public static final String yV = "authority_thumbnails";
    public static final String yW = "authority_picture_path";
    public static final String yX = "authority_app_icon";
    public static final String yY = "authority_user_icon";
    public static final String yZ = "authority_video_path";

    @javax.annotation.Nullable
    public static java.lang.String a(android.content.ContentResolver r9, android.net.Uri r10) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x002d in list [B:13:0x002a]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = 0;
        r0 = isLocalContentUri(r10);
        if (r0 == 0) goto L_0x0035;
    L_0x0007:
        r6 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r0 = r9;
        r1 = r10;
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x002e }
        if (r6 == 0) goto L_0x0028;	 Catch:{ all -> 0x002e }
    L_0x0014:
        r0 = r6.moveToFirst();	 Catch:{ all -> 0x002e }
        if (r0 == 0) goto L_0x0028;	 Catch:{ all -> 0x002e }
    L_0x001a:
        r0 = "_data";	 Catch:{ all -> 0x002e }
        r7 = r6.getColumnIndex(r0);	 Catch:{ all -> 0x002e }
        r0 = -1;	 Catch:{ all -> 0x002e }
        if (r7 == r0) goto L_0x0028;	 Catch:{ all -> 0x002e }
    L_0x0024:
        r8 = r6.getString(r7);	 Catch:{ all -> 0x002e }
    L_0x0028:
        if (r6 == 0) goto L_0x002d;
    L_0x002a:
        r6.close();
    L_0x002d:
        return r8;
    L_0x002e:
        r0 = move-exception;
        if (r6 == 0) goto L_0x0034;
    L_0x0031:
        r6.close();
    L_0x0034:
        throw r0;
    L_0x0035:
        r0 = isLocalFileUri(r10);
        if (r0 == 0) goto L_0x002d;
    L_0x003b:
        r8 = r10.getPath();
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.core.common.util.f.a(android.content.ContentResolver, android.net.Uri):java.lang.String");
    }

    public static boolean isNetworkUri(@Nullable Uri uri) {
        String scheme = getSchemeOrNull(uri);
        return "https".equals(scheme) || "http".equals(scheme);
    }

    public static boolean isLocalFileUri(@Nullable Uri uri) {
        return "file".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalContentUri(@Nullable Uri uri) {
        return "content".equals(getSchemeOrNull(uri));
    }

    public static boolean d(Uri uri) {
        return isLocalContentUri(uri) && "com.android.contacts".equals(uri.getAuthority()) && !uri.getPath().startsWith(yS);
    }

    public static boolean e(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(Media.EXTERNAL_CONTENT_URI.toString()) || uriString.startsWith(Media.INTERNAL_CONTENT_URI.toString());
    }

    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        return "asset".equals(getSchemeOrNull(uri));
    }

    public static boolean isLocalResourceUri(@Nullable Uri uri) {
        return "res".equals(getSchemeOrNull(uri));
    }

    public static boolean isDataUri(@Nullable Uri uri) {
        return "data".equals(getSchemeOrNull(uri));
    }

    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    public static Uri parseUriOrNull(@Nullable String uriAsString) {
        return uriAsString != null ? Uri.parse(uriAsString) : null;
    }

    public static String getAuthorityOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getAuthority();
    }

    public static Uri by(int resourceId) {
        return new Builder().scheme("res").path(String.valueOf(resourceId)).build();
    }

    public static boolean f(@Nullable Uri uri) {
        return yT.equals(getSchemeOrNull(uri)) && yX.equals(getAuthorityOrNull(uri));
    }

    public static boolean g(@Nullable Uri uri) {
        String scheme = getSchemeOrNull(uri);
        String authority = getAuthorityOrNull(uri);
        return yT.equals(scheme) && ("authority_picture".equals(authority) || "authority_thumbnails".equals(authority) || "authority_picture_path".equals(authority));
    }

    public static boolean h(@Nullable Uri uri) {
        return yT.equals(getSchemeOrNull(uri)) && "authority_picture_path".equals(getAuthorityOrNull(uri));
    }
}
