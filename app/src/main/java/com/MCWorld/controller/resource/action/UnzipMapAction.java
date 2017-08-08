package com.MCWorld.controller.resource.action;

import com.MCWorld.utils.bc;
import com.MCWorld.utils.bc.a;
import java.io.File;

public class UnzipMapAction implements b {
    private a callback;
    private String desPath;
    private File file;

    public UnzipMapAction(File file, String desPath, a callback) {
        this.file = file;
        this.callback = callback;
        this.desPath = desPath;
    }

    public boolean run() {
        File dir = new File(this.desPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            bc.a(this.file.getAbsolutePath(), this.desPath, this.callback);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
