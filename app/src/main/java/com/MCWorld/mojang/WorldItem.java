package com.MCWorld.mojang;

import java.io.File;
import java.io.Serializable;

public class WorldItem implements Serializable {
    private static final String EMPTY_NAME = "";
    private static final long serialVersionUID = 1999765797018607246L;
    public final File db = new File(this.folder, "db");
    private String fileName;
    public final File folder;
    public final File levelDat = new File(this.folder, "level.dat");
    private String size;

    public WorldItem(File paramFile) {
        this.folder = paramFile;
    }

    public WorldItem(File paramFile, String paramString) {
        this.folder = paramFile;
        this.size = paramString;
    }

    private String getFolderName() {
        if (this.folder == null) {
        }
        return null;
    }

    public File getFolder() {
        return this.folder;
    }

    public File getLevelDat() {
        return this.levelDat;
    }

    public File getDb() {
        return this.db;
    }

    public String getMapKey() {
        String str = getFolderName();
        if (str != null) {
            return str + "#" + this.folder.lastModified();
        }
        return str;
    }

    public String getName() {
        if (this.folder != null) {
        }
        return getFolderName();
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String paramString) {
        this.size = paramString;
    }

    public String toString() {
        return getFolderName();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
