package com.MCWorld.controller.resource.action;

import com.MCWorld.controller.resource.zip.d;
import com.MCWorld.controller.resource.zip.e.a;
import java.io.File;

/* compiled from: UnzipHpkAction */
public class e implements b {
    private String desPath;
    private File file;
    private a mI;

    public e(File file, String desPath, a callback) {
        this.file = file;
        this.mI = callback;
        this.desPath = desPath;
    }

    public boolean run() {
        File dir = new File(this.desPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        new d(this.file.getAbsolutePath(), this.desPath).a(this.mI);
        return false;
    }
}
