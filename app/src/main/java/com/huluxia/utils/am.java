package com.huluxia.utils;

import android.net.Uri;
import android.provider.MediaStore.Images.Media;

/* compiled from: UtilsPicture */
public class am {
    public static final Uri bmF = Media.EXTERNAL_CONTENT_URI;

    public static void bs(android.content.Context r25) throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x015d in list []
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
        r4 = 7;
        r6 = new java.lang.String[r4];
        r4 = 0;
        r5 = "_id";
        r6[r4] = r5;
        r4 = 1;
        r5 = "_data";
        r6[r4] = r5;
        r4 = 2;
        r5 = "date_added";
        r6[r4] = r5;
        r4 = 3;
        r5 = "_display_name";
        r6[r4] = r5;
        r4 = 4;
        r5 = "_size";
        r6[r4] = r5;
        r4 = 5;
        r5 = "bucket_id";
        r6[r4] = r5;
        r4 = 6;
        r5 = "bucket_display_name";
        r6[r4] = r5;
        r24 = "date_added DESC";
        r20 = 0;
        r23 = com.huluxia.module.picture.PictureInfo.getInstance();
        r4 = r25.getContentResolver();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r5 = bmF;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r7 = 0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r8 = 0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r9 = "date_added DESC";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r20 = r4.query(r5, r6, r7, r8, r9);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r20 == 0) goto L_0x015e;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x0047:
        r4 = r20.getCount();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 <= 0) goto L_0x015e;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x004d:
        r4 = r20.moveToNext();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 == 0) goto L_0x015e;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x0053:
        r4 = "_data";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndexOrThrow(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r10 = r0.getString(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "_display_name";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r13 = r0.getString(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "_id";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r8 = (long) r4;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "_size";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r14 = r0.getLong(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "date_added";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r11 = r0.getLong(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "bucket_id";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = (long) r4;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r18 = r0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = "bucket_display_name";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r20;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r17 = r0.getString(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r22 = new java.io.File;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r22;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0.<init>(r10);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r22.exists();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 == 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00cd:
        r4 = r22.canRead();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 == 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00d3:
        r4 = 0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1));	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 != 0) goto L_0x00dd;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00d9:
        r14 = r22.length();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00dd:
        r4 = 0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1));	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 <= 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00e3:
        r4 = com.huluxia.framework.base.utils.UtilsFunction.empty(r13);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 != 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00e9:
        r21 = r13.toLowerCase();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = ".jpg";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r21;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.endsWith(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 != 0) goto L_0x010e;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x00f8:
        r4 = ".jpeg";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r21;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.endsWith(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 != 0) goto L_0x010e;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x0103:
        r4 = ".png";	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r21;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.endsWith(r4);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 == 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x010e:
        r7 = new com.huluxia.module.picture.b;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r7.<init>(r8, r10, r11, r13, r14);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r23;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.allPictures;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4.add(r7);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = com.huluxia.framework.base.utils.UtilsFunction.empty(r17);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r4 != 0) goto L_0x004d;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x0120:
        r0 = r23;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r1 = r18;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r16 = r0.getBucket(r1);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        if (r16 != 0) goto L_0x0148;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x012a:
        r16 = new com.huluxia.module.picture.a;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r16.<init>();	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r17;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r1 = r16;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r1.bucketName = r0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r18;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r2 = r16;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r2.bucketId = r0;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r23;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.pictureBucket;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r5 = java.lang.Long.valueOf(r18);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r0 = r16;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4.put(r5, r0);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
    L_0x0148:
        r0 = r16;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4 = r0.pictures;	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        r4.add(r7);	 Catch:{ Exception -> 0x0151, all -> 0x016a }
        goto L_0x004d;
    L_0x0151:
        r4 = move-exception;
        if (r20 == 0) goto L_0x015d;
    L_0x0154:
        r4 = r20.isClosed();
        if (r4 != 0) goto L_0x015d;
    L_0x015a:
        r20.close();
    L_0x015d:
        return;
    L_0x015e:
        if (r20 == 0) goto L_0x015d;
    L_0x0160:
        r4 = r20.isClosed();
        if (r4 != 0) goto L_0x015d;
    L_0x0166:
        r20.close();
        goto L_0x015d;
    L_0x016a:
        r4 = move-exception;
        if (r20 == 0) goto L_0x0176;
    L_0x016d:
        r5 = r20.isClosed();
        if (r5 != 0) goto L_0x0176;
    L_0x0173:
        r20.close();
    L_0x0176:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.utils.am.bs(android.content.Context):void");
    }
}
