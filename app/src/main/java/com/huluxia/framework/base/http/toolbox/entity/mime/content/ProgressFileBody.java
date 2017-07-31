package com.huluxia.framework.base.http.toolbox.entity.mime.content;

import com.huluxia.framework.base.http.io.impl.request.UploadRequest;
import com.huluxia.framework.base.http.toolbox.entity.ContentType;
import java.io.File;

public class ProgressFileBody extends FileBody {
    private UploadRequest request;
    private String url;

    public ProgressFileBody(String url, File file) {
        super(file);
        this.url = url;
    }

    public ProgressFileBody(String url, File file, ContentType contentType, String filename) {
        super(file, contentType, filename);
        this.url = url;
    }

    public ProgressFileBody(String url, File file, ContentType contentType) {
        super(file, contentType);
        this.url = url;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeTo(java.io.OutputStream r11) throws java.io.IOException {
        /*
        r10 = this;
        r7 = new java.io.FileInputStream;
        r0 = r10.file;
        r7.<init>(r0);
        r0 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r9 = new byte[r0];	 Catch:{ all -> 0x0060 }
    L_0x000b:
        r8 = r7.read(r9);	 Catch:{ all -> 0x0060 }
        r0 = -1;
        if (r8 == r0) goto L_0x003d;
    L_0x0012:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        if (r0 == 0) goto L_0x0044;
    L_0x0016:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        r0 = r0.isCanceled();	 Catch:{ all -> 0x0060 }
        if (r0 == 0) goto L_0x0044;
    L_0x001e:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        r0 = r0.getCancelListener();	 Catch:{ all -> 0x0060 }
        if (r0 == 0) goto L_0x0044;
    L_0x0026:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        r0 = r0.getCancelListener();	 Catch:{ all -> 0x0060 }
        r0.onCancel();	 Catch:{ all -> 0x0060 }
        r0 = "file body upload cancel, url %s ";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x0060 }
        r2 = 0;
        r3 = r10.url;	 Catch:{ all -> 0x0060 }
        r1[r2] = r3;	 Catch:{ all -> 0x0060 }
        com.huluxia.framework.base.http.toolbox.HttpLog.d(r0, r1);	 Catch:{ all -> 0x0060 }
    L_0x003d:
        r11.flush();	 Catch:{ all -> 0x0060 }
        r7.close();
        return;
    L_0x0044:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        r0 = r0.getFileBodyProgress();	 Catch:{ all -> 0x0060 }
        if (r0 == 0) goto L_0x005b;
    L_0x004c:
        r0 = r10.request;	 Catch:{ all -> 0x0060 }
        r0 = r0.getFileBodyProgress();	 Catch:{ all -> 0x0060 }
        r1 = r10.url;	 Catch:{ all -> 0x0060 }
        r2 = 0;
        r4 = (long) r8;	 Catch:{ all -> 0x0060 }
        r6 = 0;
        r0.onProgress(r1, r2, r4, r6);	 Catch:{ all -> 0x0060 }
    L_0x005b:
        r0 = 0;
        r11.write(r9, r0, r8);	 Catch:{ all -> 0x0060 }
        goto L_0x000b;
    L_0x0060:
        r0 = move-exception;
        r7.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.framework.base.http.toolbox.entity.mime.content.ProgressFileBody.writeTo(java.io.OutputStream):void");
    }

    public void setRequest(UploadRequest request) {
        this.request = request;
    }

    public UploadRequest getRequest() {
        return this.request;
    }
}
