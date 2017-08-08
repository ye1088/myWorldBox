package com.MCWorld.mcgame;

import android.content.Context;
import com.MCWorld.mcsdk.DTSDKManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.zhuoweizhang.mcpelauncher.h;

/* compiled from: DTTextureOverrides */
public class af implements h {
    private Context context;

    public af(Context ctx) {
        this.context = ctx;
    }

    public InputStream cN(String fileName) throws IOException {
        if (DTSDKManager.androidContext == null) {
            return null;
        }
        File file = DTSDKManager.getTextureOverrideFile(fileName);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        return null;
    }

    public long getSize(String paramString) throws IOException {
        return 0;
    }

    public void close() throws IOException {
    }

    public List<String> ww() throws IOException {
        return null;
    }
}
