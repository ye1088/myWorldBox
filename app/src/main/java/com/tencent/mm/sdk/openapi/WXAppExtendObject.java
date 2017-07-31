package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.openapi.WXMediaMessage.IMediaObject;
import com.tencent.mm.sdk.platformtools.Log;
import java.io.File;
import org.apache.tools.tar.TarBuffer;

public class WXAppExtendObject implements IMediaObject {
    public String extInfo;
    public byte[] fileData;
    public String filePath;

    public WXAppExtendObject(String str, String str2) {
        this.extInfo = str;
        this.filePath = str2;
    }

    public WXAppExtendObject(String str, byte[] bArr) {
        this.extInfo = str;
        this.fileData = bArr;
    }

    public boolean checkArgs() {
        if ((this.extInfo == null || this.extInfo.length() == 0) && ((this.filePath == null || this.filePath.length() == 0) && (this.fileData == null || this.fileData.length == 0))) {
            Log.e("MicroMsg.SDK.WXAppExtendObject", "checkArgs fail, all arguments is null");
            return false;
        } else if (this.extInfo != null && this.extInfo.length() > 2048) {
            Log.e("MicroMsg.SDK.WXAppExtendObject", "checkArgs fail, extInfo is invalid");
            return false;
        } else if (this.filePath == null || this.filePath.length() <= TarBuffer.DEFAULT_BLKSIZE) {
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
                    Log.e("MicroMsg.SDK.WXAppExtendObject", "checkArgs fail, fileSize is too large");
                    return false;
                }
            }
            if (this.fileData == null || this.fileData.length <= 10485760) {
                return true;
            }
            Log.e("MicroMsg.SDK.WXAppExtendObject", "checkArgs fail, fileData is too large");
            return false;
        } else {
            Log.e("MicroMsg.SDK.WXAppExtendObject", "checkArgs fail, filePath is invalid");
            return false;
        }
    }

    public void serialize(Bundle bundle) {
        bundle.putString("_wxappextendobject_extInfo", this.extInfo);
        bundle.putByteArray("_wxappextendobject_fileData", this.fileData);
        bundle.putString("_wxappextendobject_filePath", this.filePath);
    }

    public int type() {
        return 7;
    }

    public void unserialize(Bundle bundle) {
        this.extInfo = bundle.getString("_wxappextendobject_extInfo");
        this.fileData = bundle.getByteArray("_wxappextendobject_fileData");
        this.filePath = bundle.getString("_wxappextendobject_filePath");
    }
}
