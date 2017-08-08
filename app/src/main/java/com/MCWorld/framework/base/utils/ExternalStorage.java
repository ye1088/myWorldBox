package com.MCWorld.framework.base.utils;

import android.os.Environment;
import android.support.v4.content.ContextCompat;
import com.MCWorld.framework.AppConfig;
import java.io.File;
import java.io.IOException;

public class ExternalStorage {
    public static final String TAG = "ExternalStorage";

    public static class Sdcard {
        public boolean canWork;
        public String path;

        public Sdcard(String path, boolean canWork) {
            this.path = path;
            this.canWork = canWork;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sdcard sdcard = (Sdcard) o;
            if (this.path != null) {
                if (this.path.equals(sdcard.path)) {
                    return true;
                }
            } else if (sdcard.path == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.path != null ? this.path.hashCode() : 0;
        }
    }

    public static boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isWritable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static String defaultDownloadPath(File sdcard) {
        boolean isInternal = sdcard.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath());
        ContextCompat.getExternalFilesDirs(AppConfig.getInstance().getAppContext(), null);
        if (isInternal) {
            return AppConfig.getInstance().getRootDir().getAbsolutePath();
        }
        return sdcard.getAbsolutePath() + File.separator + "Android" + File.separator + "data" + File.separator + AppConfig.getInstance().getAppContext().getPackageName() + File.separator + "downloads";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.MCWorld.framework.base.utils.ExternalStorage.Sdcard> getSdcardLocations() {
        /*
        r13 = new java.util.ArrayList;
        r13.<init>();
        r11 = new java.util.HashSet;
        r11.<init>();
        r14 = java.lang.Runtime.getRuntime();
        r12 = 0;
        r19 = "df";
        r0 = r19;
        r12 = r14.exec(r0);	 Catch:{ IOException -> 0x01b6 }
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x01b6 }
        r19 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x01b6 }
        r20 = r12.getInputStream();	 Catch:{ IOException -> 0x01b6 }
        r19.<init>(r20);	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r2.<init>(r0);	 Catch:{ IOException -> 0x01b6 }
        r8 = r2.readLine();	 Catch:{ IOException -> 0x01b6 }
    L_0x002c:
        r19 = com.huluxia.framework.base.utils.UtilsFunction.empty(r8);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x01c2;
    L_0x0032:
        r19 = " ";
        r0 = r19;
        r9 = r8.split(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r9 == 0) goto L_0x016a;
    L_0x003d:
        r0 = r9.length;	 Catch:{ IOException -> 0x01b6 }
        r19 = r0;
        if (r19 <= 0) goto L_0x016a;
    L_0x0042:
        r19 = 0;
        r19 = r9[r19];	 Catch:{ IOException -> 0x01b6 }
        r10 = r19.trim();	 Catch:{ IOException -> 0x01b6 }
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01b6 }
        r19.<init>();	 Catch:{ IOException -> 0x01b6 }
        r20 = java.io.File.separator;	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r20 = "mnt";
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r19 = r10.startsWith(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x00d8;
    L_0x0068:
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01b6 }
        r19.<init>();	 Catch:{ IOException -> 0x01b6 }
        r20 = java.io.File.separator;	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r20 = "storage";
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r19 = r10.startsWith(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x0086:
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01b6 }
        r19.<init>();	 Catch:{ IOException -> 0x01b6 }
        r20 = java.io.File.separator;	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r20 = "/mnt/asec";
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r19 = r10.startsWith(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x016a;
    L_0x00a4:
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01b6 }
        r19.<init>();	 Catch:{ IOException -> 0x01b6 }
        r20 = java.io.File.separator;	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r20 = "/mnt/secure";
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r19 = r10.startsWith(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x016a;
    L_0x00c2:
        r19 = "legacy";
        r0 = r19;
        r19 = r10.contains(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x016a;
    L_0x00cd:
        r19 = "Android/obb";
        r0 = r19;
        r19 = r10.contains(r0);	 Catch:{ IOException -> 0x01b6 }
        if (r19 != 0) goto L_0x016a;
    L_0x00d8:
        r6 = new java.io.File;	 Catch:{ IOException -> 0x01b6 }
        r6.<init>(r10);	 Catch:{ IOException -> 0x01b6 }
        r19 = r6.exists();	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x00e3:
        r20 = com.huluxia.framework.base.utils.UtilsFile.totalSpace(r10);	 Catch:{ IOException -> 0x01b6 }
        r22 = 0;
        r19 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
        if (r19 <= 0) goto L_0x016a;
    L_0x00ed:
        r19 = r6.canRead();	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x00f3:
        r19 = r6.canWrite();	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x00f9:
        r19 = r6.isDirectory();	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x00ff:
        r19 = checkCanonical(r6);	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x016a;
    L_0x0105:
        r16 = 0;
        r4 = 0;
        r17 = 0;
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0170 }
        r19.<init>();	 Catch:{ Exception -> 0x0170 }
        r20 = "huluxia.test";
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x0170 }
        r20 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Exception -> 0x0170 }
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x0170 }
        r7 = r19.toString();	 Catch:{ Exception -> 0x0170 }
        r3 = new java.io.File;	 Catch:{ Exception -> 0x0170 }
        r19 = defaultDownloadPath(r6);	 Catch:{ Exception -> 0x0170 }
        r0 = r19;
        r3.<init>(r0);	 Catch:{ Exception -> 0x0170 }
        r19 = r3.exists();	 Catch:{ Exception -> 0x0170 }
        if (r19 != 0) goto L_0x0136;
    L_0x0133:
        r3.mkdirs();	 Catch:{ Exception -> 0x0170 }
    L_0x0136:
        r18 = new java.io.File;	 Catch:{ Exception -> 0x0170 }
        r0 = r18;
        r0.<init>(r3, r7);	 Catch:{ Exception -> 0x0170 }
        r19 = r18.exists();	 Catch:{ Exception -> 0x01fd }
        if (r19 == 0) goto L_0x0147;
    L_0x0143:
        r4 = r18.delete();	 Catch:{ Exception -> 0x01fd }
    L_0x0147:
        r16 = r18.createNewFile();	 Catch:{ Exception -> 0x01fd }
        r17 = r18;
    L_0x014d:
        r15 = new com.huluxia.framework.base.utils.ExternalStorage$Sdcard;	 Catch:{ IOException -> 0x01b6 }
        r15.<init>();	 Catch:{ IOException -> 0x01b6 }
        r19 = r6.getAbsolutePath();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r15.path = r0;	 Catch:{ IOException -> 0x01b6 }
        if (r16 == 0) goto L_0x01bb;
    L_0x015c:
        if (r17 == 0) goto L_0x0161;
    L_0x015e:
        r17.delete();	 Catch:{ IOException -> 0x01b6 }
    L_0x0161:
        r19 = 1;
        r0 = r19;
        r15.canWork = r0;	 Catch:{ IOException -> 0x01b6 }
    L_0x0167:
        r11.add(r15);	 Catch:{ IOException -> 0x01b6 }
    L_0x016a:
        r8 = r2.readLine();	 Catch:{ IOException -> 0x01b6 }
        goto L_0x002c;
    L_0x0170:
        r5 = move-exception;
    L_0x0171:
        r19 = "ExternalStorage";
        r20 = "sdcard create new file error, error %s, sdcard %s, dir create %b";
        r21 = 3;
        r0 = r21;
        r0 = new java.lang.Object[r0];	 Catch:{ IOException -> 0x01b6 }
        r21 = r0;
        r22 = 0;
        r23 = r5.getMessage();	 Catch:{ IOException -> 0x01b6 }
        r21[r22] = r23;	 Catch:{ IOException -> 0x01b6 }
        r22 = 1;
        r23 = defaultDownloadPath(r6);	 Catch:{ IOException -> 0x01b6 }
        r21[r22] = r23;	 Catch:{ IOException -> 0x01b6 }
        r22 = 2;
        r23 = java.lang.Boolean.valueOf(r4);	 Catch:{ IOException -> 0x01b6 }
        r21[r22] = r23;	 Catch:{ IOException -> 0x01b6 }
        com.huluxia.framework.base.log.HLog.error(r19, r20, r21);	 Catch:{ IOException -> 0x01b6 }
        if (r17 == 0) goto L_0x014d;
    L_0x019c:
        r19 = "ExternalStorage";
        r20 = "sdcard create new file error, test %s";
        r21 = 1;
        r0 = r21;
        r0 = new java.lang.Object[r0];	 Catch:{ IOException -> 0x01b6 }
        r21 = r0;
        r22 = 0;
        r23 = r17.getAbsolutePath();	 Catch:{ IOException -> 0x01b6 }
        r21[r22] = r23;	 Catch:{ IOException -> 0x01b6 }
        com.huluxia.framework.base.log.HLog.error(r19, r20, r21);	 Catch:{ IOException -> 0x01b6 }
        goto L_0x014d;
    L_0x01b6:
        r5 = move-exception;
        r5.printStackTrace();
    L_0x01ba:
        return r13;
    L_0x01bb:
        r19 = 0;
        r0 = r19;
        r15.canWork = r0;	 Catch:{ IOException -> 0x01b6 }
        goto L_0x0167;
    L_0x01c2:
        r12.destroy();	 Catch:{ IOException -> 0x01b6 }
        r19 = com.huluxia.framework.base.utils.UtilsFunction.empty(r11);	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x01f2;
    L_0x01cb:
        r15 = new com.huluxia.framework.base.utils.ExternalStorage$Sdcard;	 Catch:{ IOException -> 0x01b6 }
        r15.<init>();	 Catch:{ IOException -> 0x01b6 }
        r19 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ IOException -> 0x01b6 }
        r19 = r19.getAbsolutePath();	 Catch:{ IOException -> 0x01b6 }
        r0 = r19;
        r15.path = r0;	 Catch:{ IOException -> 0x01b6 }
        r19 = android.os.Environment.getExternalStorageState();	 Catch:{ IOException -> 0x01b6 }
        r20 = "mounted";
        r19 = r19.equals(r20);	 Catch:{ IOException -> 0x01b6 }
        if (r19 == 0) goto L_0x01f6;
    L_0x01e9:
        r19 = 1;
        r0 = r19;
        r15.canWork = r0;	 Catch:{ IOException -> 0x01b6 }
    L_0x01ef:
        r11.add(r15);	 Catch:{ IOException -> 0x01b6 }
    L_0x01f2:
        r13.addAll(r11);	 Catch:{ IOException -> 0x01b6 }
        goto L_0x01ba;
    L_0x01f6:
        r19 = 0;
        r0 = r19;
        r15.canWork = r0;	 Catch:{ IOException -> 0x01b6 }
        goto L_0x01ef;
    L_0x01fd:
        r5 = move-exception;
        r17 = r18;
        goto L_0x0171;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.utils.ExternalStorage.getSdcardLocations():java.util.List<com.huluxia.framework.base.utils.ExternalStorage$Sdcard>");
    }

    public static boolean checkCanonical(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (file.getParent() != null) {
            file = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return file.getCanonicalFile().equals(file.getAbsoluteFile());
    }
}
