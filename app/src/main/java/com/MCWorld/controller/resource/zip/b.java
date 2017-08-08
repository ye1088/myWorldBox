package com.MCWorld.controller.resource.zip;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.framework.base.utils.UtilsFunction;
import java.util.ArrayList;
import java.util.List;

/* compiled from: HpkFileList */
public class b implements Parcelable {
    public static final Creator<b> CREATOR = new Creator<b>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return G(i);
        }

        public b a(Parcel source) {
            return new b(source);
        }

        public b[] G(int size) {
            return new b[size];
        }
    };
    public String apkFile;
    public boolean fileExists;
    private List<String> files = new ArrayList();
    public String hpkFile;
    public String url;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeStringList(this.files);
        dest.writeString(this.hpkFile);
        dest.writeString(this.apkFile);
        dest.writeByte(this.fileExists ? (byte) 1 : (byte) 0);
    }

    protected b(Parcel in) {
        this.url = in.readString();
        this.files = in.createStringArrayList();
        this.hpkFile = in.readString();
        this.apkFile = in.readString();
        this.fileExists = in.readByte() != (byte) 0;
    }

    public void setApkFile(String apkFile) {
        if (!UtilsFunction.empty(this.url)) {
            this.apkFile = apkFile;
            c.ea().a(this.url, this);
        }
    }

    public void setFileExists(boolean fileExists) {
        if (!UtilsFunction.empty(this.url)) {
            this.fileExists = fileExists;
            c.ea().a(this.url, this);
        }
    }

    public void setHpkFile(String hpkFile) {
        if (!UtilsFunction.empty(this.url)) {
            this.hpkFile = hpkFile;
            c.ea().a(this.url, this);
        }
    }

    public void addFile(String path) {
        if (!UtilsFunction.empty(path) && !UtilsFunction.empty(this.url)) {
            this.files.add(path);
            c.ea().a(this.url, this);
        }
    }

    public String toString() {
        return "HpkFileList{files=" + this.files + '}';
    }

    public synchronized List<String> getFiles() {
        List<String> filesList;
        filesList = new ArrayList();
        for (String file : this.files) {
            filesList.add(file);
        }
        return filesList;
    }

    public synchronized void setFiles(List<String> files) {
        this.files = files;
    }
}
