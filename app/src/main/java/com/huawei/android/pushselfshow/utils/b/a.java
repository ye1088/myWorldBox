package com.huawei.android.pushselfshow.utils.b;

import com.huawei.android.pushagent.c.a.e;
import java.io.File;

public class a {
    private String a;
    private String b;

    public a(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static File a(String str, String str2) {
        File file;
        String[] split = str2.split("/");
        File file2 = new File(str);
        int i = 0;
        while (i < split.length - 1) {
            try {
                i++;
                file2 = new File(file2, new String(split[i].getBytes("8859_1"), "GB2312"));
            } catch (Exception e) {
                Exception exception = e;
                file = file2;
                Exception exception2 = exception;
            }
        }
        e.a("PushSelfShowLog", "file1 = " + file2);
        if (!(file2.exists() || file2.mkdirs())) {
            e.a("PushSelfShowLog", "ret.mkdirs faild");
        }
        String str3 = new String(split[split.length - 1].getBytes("8859_1"), "GB2312");
        e.a("PushSelfShowLog", "substr = " + str3);
        file = new File(file2, str3);
        try {
            e.a("PushSelfShowLog", "file2 = " + file);
        } catch (Exception e2) {
            exception2 = e2;
            exception2.printStackTrace();
            return file;
        }
        return file;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
        r12 = this;
        r2 = 0;
        r0 = r12.b;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = "/";
        r0 = r0.endsWith(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        if (r0 != 0) goto L_0x0024;
    L_0x000c:
        r0 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0.<init>();	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = r12.b;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = r0.append(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = "/";
        r0 = r0.append(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r12.b = r0;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
    L_0x0024:
        r6 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = new java.io.File;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = r12.a_isRightVersion;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0.<init>(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r6.<init>(r0);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r7 = r6.entries();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r8 = new byte[r0];	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0038:
        r0 = r7.hasMoreElements();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r0 == 0) goto L_0x05f9;
    L_0x003e:
        r0 = r7.nextElement();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = (java.util.zip.ZipEntry) r0;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r0.isDirectory();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x00b3;
    L_0x004a:
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "ze.getName() = ";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r3.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r12.b;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = new java.lang.String;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "8859_1";
        r1 = r1.getBytes(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "GB2312";
        r3.<init>(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = "str = ";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = new java.io.File;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1.<init>(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.mkdir();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 != 0) goto L_0x0038;
    L_0x00b3:
        r1 = r12.b;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = a_isRightVersion(r1, r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.isDirectory();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x00e9;
    L_0x00c3:
        if (r6 == 0) goto L_0x00c8;
    L_0x00c5:
        r6.close();	 Catch:{ IOException -> 0x00c9 }
    L_0x00c8:
        return;
    L_0x00c9:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x00e9:
        r1 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = "ze.getName() = ";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = ",output file :";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = r3.getAbsolutePath();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x0150;
    L_0x0120:
        r0 = "PushSelfShowLog";
        r1 = "ze.getName() is empty= ";
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r0, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r6 == 0) goto L_0x00c8;
    L_0x012b:
        r6.close();	 Catch:{ IOException -> 0x012f }
        goto L_0x00c8;
    L_0x012f:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x0150:
        r1 = r6.getInputStream(r0);	 Catch:{ IOException -> 0x068f, IllegalStateException -> 0x03e9, IndexOutOfBoundsException -> 0x04a4, all -> 0x055f }
        r5 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0696, IllegalStateException -> 0x067d, IndexOutOfBoundsException -> 0x066b, all -> 0x0659 }
        r5.<init>(r3);	 Catch:{ IOException -> 0x0696, IllegalStateException -> 0x067d, IndexOutOfBoundsException -> 0x066b, all -> 0x0659 }
        r4 = new java.io.BufferedOutputStream;	 Catch:{ IOException -> 0x069c, IllegalStateException -> 0x0683, IndexOutOfBoundsException -> 0x0671, all -> 0x065f }
        r4.<init>(r5);	 Catch:{ IOException -> 0x069c, IllegalStateException -> 0x0683, IndexOutOfBoundsException -> 0x0671, all -> 0x065f }
        r3 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x06a1, IllegalStateException -> 0x0688, IndexOutOfBoundsException -> 0x0676, all -> 0x0664 }
        r3.<init>(r1);	 Catch:{ IOException -> 0x06a1, IllegalStateException -> 0x0688, IndexOutOfBoundsException -> 0x0676, all -> 0x0664 }
    L_0x0163:
        r0 = 0;
        r9 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r3.read(r8, r0, r9);	 Catch:{ IOException -> 0x0172, IllegalStateException -> 0x068c, IndexOutOfBoundsException -> 0x067a }
        r9 = -1;
        if (r0 == r9) goto L_0x0210;
    L_0x016d:
        r9 = 0;
        r4.write(r8, r9, r0);	 Catch:{ IOException -> 0x0172, IllegalStateException -> 0x068c, IndexOutOfBoundsException -> 0x067a }
        goto L_0x0163;
    L_0x0172:
        r0 = move-exception;
    L_0x0173:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x0196;
    L_0x0193:
        r1.close();	 Catch:{ IOException -> 0x0386, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0196:
        if (r3 == 0) goto L_0x019b;
    L_0x0198:
        r3.close();	 Catch:{ IOException -> 0x03a7, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x019b:
        if (r4 == 0) goto L_0x01a0;
    L_0x019d:
        r4.close();	 Catch:{ IOException -> 0x03c8, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x01a0:
        if (r5 == 0) goto L_0x0038;
    L_0x01a2:
        r5.close();	 Catch:{ IOException -> 0x01a7, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x01a7:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x01c8:
        r0 = move-exception;
        r2 = r6;
    L_0x01ca:
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0646 }
        r3.<init>();	 Catch:{ all -> 0x0646 }
        r4 = "upZipFile error:";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0646 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0646 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0646 }
        r0 = r0.toString();	 Catch:{ all -> 0x0646 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ all -> 0x0646 }
        if (r2 == 0) goto L_0x00c8;
    L_0x01ea:
        r2.close();	 Catch:{ IOException -> 0x01ef }
        goto L_0x00c8;
    L_0x01ef:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x0210:
        if (r1 == 0) goto L_0x0215;
    L_0x0212:
        r1.close();	 Catch:{ IOException -> 0x028e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0215:
        if (r3 == 0) goto L_0x021a;
    L_0x0217:
        r3.close();	 Catch:{ IOException -> 0x02f6, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x021a:
        if (r4 == 0) goto L_0x021f;
    L_0x021c:
        r4.close();	 Catch:{ IOException -> 0x035e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x021f:
        if (r5 == 0) goto L_0x0038;
    L_0x0221:
        r5.close();	 Catch:{ IOException -> 0x0226, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0226:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0247:
        r0 = move-exception;
    L_0x0248:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x0268:
        r6.close();	 Catch:{ IOException -> 0x026d }
        goto L_0x00c8;
    L_0x026d:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x028e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0215;
    L_0x02af:
        r0 = move-exception;
    L_0x02b0:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x02d0:
        r6.close();	 Catch:{ IOException -> 0x02d5 }
        goto L_0x00c8;
    L_0x02d5:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x02f6:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x021a;
    L_0x0317:
        r0 = move-exception;
    L_0x0318:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x0338:
        r6.close();	 Catch:{ IOException -> 0x033d }
        goto L_0x00c8;
    L_0x033d:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x035e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x021f;
    L_0x037f:
        r0 = move-exception;
    L_0x0380:
        if (r6 == 0) goto L_0x0385;
    L_0x0382:
        r6.close();	 Catch:{ IOException -> 0x0621 }
    L_0x0385:
        throw r0;
    L_0x0386:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0196;
    L_0x03a7:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x019b;
    L_0x03c8:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x01a0;
    L_0x03e9:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x03ee:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x0411;
    L_0x040e:
        r1.close();	 Catch:{ IOException -> 0x0443, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0411:
        if (r3 == 0) goto L_0x0416;
    L_0x0413:
        r3.close();	 Catch:{ IOException -> 0x0463, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0416:
        if (r4 == 0) goto L_0x041b;
    L_0x0418:
        r4.close();	 Catch:{ IOException -> 0x0483, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x041b:
        if (r5 == 0) goto L_0x0038;
    L_0x041d:
        r5.close();	 Catch:{ IOException -> 0x0422, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0422:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0443:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0411;
    L_0x0463:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0416;
    L_0x0483:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x041b;
    L_0x04a4:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x04a9:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x04cc;
    L_0x04c9:
        r1.close();	 Catch:{ IOException -> 0x04fe, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04cc:
        if (r3 == 0) goto L_0x04d1;
    L_0x04ce:
        r3.close();	 Catch:{ IOException -> 0x051e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04d1:
        if (r4 == 0) goto L_0x04d6;
    L_0x04d3:
        r4.close();	 Catch:{ IOException -> 0x053e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04d6:
        if (r5 == 0) goto L_0x0038;
    L_0x04d8:
        r5.close();	 Catch:{ IOException -> 0x04dd, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x04dd:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x04fe:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04cc;
    L_0x051e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04d1;
    L_0x053e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04d6;
    L_0x055f:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x0564:
        if (r1 == 0) goto L_0x0569;
    L_0x0566:
        r1.close();	 Catch:{ IOException -> 0x0579, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0569:
        if (r3 == 0) goto L_0x056e;
    L_0x056b:
        r3.close();	 Catch:{ IOException -> 0x0599, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x056e:
        if (r4 == 0) goto L_0x0573;
    L_0x0570:
        r4.close();	 Catch:{ IOException -> 0x05b9, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0573:
        if (r5 == 0) goto L_0x0578;
    L_0x0575:
        r5.close();	 Catch:{ IOException -> 0x05d9, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0578:
        throw r0;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0579:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r7 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r7.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r8 = "zFileIn.close error:";
        r7 = r7.append(r8);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r7.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0569;
    L_0x0599:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r7 = "is.close error:";
        r3 = r3.append(r7);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x056e;
    L_0x05b9:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0573;
    L_0x05d9:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0578;
    L_0x05f9:
        if (r6 == 0) goto L_0x00c8;
    L_0x05fb:
        r6.close();	 Catch:{ IOException -> 0x0600 }
        goto L_0x00c8;
    L_0x0600:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r1, r0);
        goto L_0x00c8;
    L_0x0621:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "zfile.close error:";
        r3 = r3.append(r4);
        r1 = r1.getMessage();
        r1 = r3.append(r1);
        r1 = r1.toString();
        com.huawei.android.pushagent.c.a_isRightVersion.e.a_isRightVersion(r2, r1);
        goto L_0x0385;
    L_0x0642:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0380;
    L_0x0646:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0380;
    L_0x064a:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0318;
    L_0x064e:
        r0 = move-exception;
        r6 = r2;
        goto L_0x02b0;
    L_0x0652:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0248;
    L_0x0656:
        r0 = move-exception;
        goto L_0x01ca;
    L_0x0659:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0564;
    L_0x065f:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x0564;
    L_0x0664:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0564;
    L_0x0668:
        r0 = move-exception;
        goto L_0x0564;
    L_0x066b:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x04a9;
    L_0x0671:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x04a9;
    L_0x0676:
        r0 = move-exception;
        r3 = r2;
        goto L_0x04a9;
    L_0x067a:
        r0 = move-exception;
        goto L_0x04a9;
    L_0x067d:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x03ee;
    L_0x0683:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x03ee;
    L_0x0688:
        r0 = move-exception;
        r3 = r2;
        goto L_0x03ee;
    L_0x068c:
        r0 = move-exception;
        goto L_0x03ee;
    L_0x068f:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0173;
    L_0x0696:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0173;
    L_0x069c:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x0173;
    L_0x06a1:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0173;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.b.a_isRightVersion.a_isRightVersion():void");
    }
}
