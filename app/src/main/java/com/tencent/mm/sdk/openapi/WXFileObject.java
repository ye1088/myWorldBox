package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.openapi.WXMediaMessage.IMediaObject;
import com.tencent.mm.sdk.platformtools.Log;
import java.io.File;

public class WXFileObject implements IMediaObject {
    public byte[] fileData;
    public String filePath;

    public WXFileObject() {
        this.fileData = null;
        this.filePath = null;
    }

    public WXFileObject(String str) {
        this.filePath = str;
    }

    public WXFileObject(byte[] bArr) {
        this.fileData = bArr;
    }

    public boolean checkArgs() {
        if ((this.fileData == null || this.fileData.length == 0) && (this.filePath == null || this.filePath.length() == 0)) {
            Log.e("MicroMsg.SDK.WXFileObject", "checkArgs fail, both arguments is null");
            return false;
        } else if (this.fileData == null || this.fileData.length <= 10485760) {
            if (this.filePath != null) {
                int i;
                String str = this.filePath;
                if (str == null || str.length() == 0) {
                    i = 0;
                } else {
                    File file = new File(str);
                    i = !file.exists() ? 0 : (int) file.length();
                }
                if (i > 10485760) {
                    Log.e("MicroMsg.SDK.WXFileObject", "checkArgs fail, fileSize is too large");
                    return false;
                }
            }
            return true;
        } else {
            Log.e("MicroMsg.SDK.WXFileObject", "checkArgs fail, fileData is too large");
            return false;
        }
    }

    public void serialize(Bundle bundle) {
        bundle.putByteArray("_wxfileobject_fileData", this.fileData);
        bundle.putString("_wxfileobject_filePath", this.filePath);
    }

    public void setFileData(byte[] bArr) {
        this.fileData = bArr;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public int type() {
        return 6;
    }

    public void unserialize(Bundle bundle) {
        this.fileData = bundle.getByteArray("_wxfileobject_fileData");
        this.filePath = bundle.getString("_wxfileobject_filePath");
    }
}
