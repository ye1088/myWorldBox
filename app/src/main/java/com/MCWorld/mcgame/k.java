package com.MCWorld.mcgame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import net.zhuoweizhang.mcpelauncher.h;

/* compiled from: DTGameTexture */
public class k implements h {
    private static String aeE = null;
    private HashMap<String, ZipEntry> aeF = new HashMap();
    private ZipFile aeG;
    private File file;

    public static String wu() {
        return aeE;
    }

    public static void cM(String inputTexturePath) {
        aeE = inputTexturePath;
    }

    public void quit() {
        this.aeF.clear();
        this.aeF = null;
    }

    public k(File file) throws ZipException, IOException {
        this.file = file;
        this.aeG = new ZipFile(file);
        wv();
    }

    private void wv() {
        Enumeration<? extends ZipEntry> i = this.aeG.entries();
        while (i.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) i.nextElement();
            if (!entry.getName().contains("__MACOSX")) {
                this.aeF.put(cO(entry.getName()), entry);
            }
        }
    }

    public InputStream cN(String fileName) throws IOException {
        ZipEntry realEntry = getEntry(fileName);
        if (realEntry == null) {
            return null;
        }
        return this.aeG.getInputStream(realEntry);
    }

    public long getSize(String fileName) throws IOException {
        ZipEntry realEntry = getEntry(fileName);
        if (realEntry == null) {
            return -1;
        }
        return realEntry.getSize();
    }

    private ZipEntry getEntry(String fileName) {
        return (ZipEntry) this.aeF.get(cO(fileName));
    }

    public void close() throws IOException {
        this.aeG.close();
    }

    private static String cO(String location) {
        String[] segments = location.split("/");
        return segments[segments.length - 1];
    }

    public List<String> ww() throws IOException {
        ArrayList<String> localArrayList = new ArrayList();
        for (Entry entry : this.aeF.entrySet()) {
            localArrayList.add(String.valueOf(entry.getValue()));
        }
        return localArrayList;
    }
}
