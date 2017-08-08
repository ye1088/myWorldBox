package com.MCWorld.version;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hlx_version_info")
/* compiled from: VersionDbInfo */
public class d implements Parcelable {
    public static final String CHANNEL = "channel";
    public static final String CONTENT = "content";
    public static final Creator<d> CREATOR = new 1();
    public static final String DOWNLOADSTATUS = "downloadstatus";
    public static final String FILEPATH = "filepath";
    public static final String FILESIZE = "filesize";
    public static final String MD5 = "md5";
    public static final String NAME = "name";
    public static final String PACKNAME = "packname";
    public static final String RESERVE1 = "reserve1";
    public static final String RESERVE2 = "reserve2";
    public static final String RESERVE3 = "reserve3";
    public static final String RESERVE4 = "reserve4";
    public static final String RESERVE5 = "reserve5";
    public static final String RESERVE6 = "reserve6";
    public static final String RESTYPE = "restype";
    public static final String SIZE = "size";
    public static final String TABLE = "hlx_version_info";
    public static final String URL = "url";
    public static final String VERSIONCODE = "versioncode";
    public static final String VERSIONNAME = "versionname";
    @DatabaseField(columnName = "channel")
    public String channel;
    @DatabaseField(columnName = "content")
    public String content;
    @DatabaseField(columnName = "downloadstatus")
    public int downloadStatus;
    @DatabaseField(columnName = "filepath")
    public String filePath;
    @DatabaseField(columnName = "filesize")
    public long fileSize;
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "md5")
    public String md5;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(columnName = "packname")
    public String packname;
    @DatabaseField(columnName = "reserve1")
    public int reserve1;
    @DatabaseField(columnName = "reserve2")
    public int reserve2;
    @DatabaseField(columnName = "reserve3")
    public int reserve3;
    @DatabaseField(columnName = "reserve4")
    public String reserve4;
    @DatabaseField(columnName = "reserve5")
    public String reserve5;
    @DatabaseField(columnName = "reserve6")
    public String reserve6;
    @DatabaseField(columnName = "restype")
    public int restype;
    @DatabaseField(columnName = "size")
    public long size;
    @DatabaseField(canBeNull = false, columnName = "url", index = true, unique = true)
    public String url;
    @DatabaseField(columnName = "versioncode")
    public long versioncode;
    @DatabaseField(columnName = "versionname")
    public String versionname;

    public d(Parcel source) {
        this();
        this.url = source.readString();
        this.size = source.readLong();
        this.md5 = source.readString();
        this.versionname = source.readString();
        this.versioncode = source.readLong();
        this.content = source.readString();
        this.packname = source.readString();
        this.channel = source.readString();
        this.downloadStatus = source.readInt();
        this.fileSize = source.readLong();
        this.filePath = source.readString();
        this.name = source.readString();
        this.restype = source.readInt();
        this.reserve1 = source.readInt();
        this.reserve2 = source.readInt();
        this.reserve3 = source.readInt();
        this.reserve4 = source.readString();
        this.reserve5 = source.readString();
        this.reserve6 = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeLong(this.size);
        dest.writeString(this.md5);
        dest.writeString(this.versionname);
        dest.writeLong(this.versioncode);
        dest.writeString(this.content);
        dest.writeString(this.packname);
        dest.writeString(this.channel);
        dest.writeInt(this.downloadStatus);
        dest.writeLong(this.fileSize);
        dest.writeString(this.filePath);
        dest.writeString(this.name);
        dest.writeInt(this.restype);
        dest.writeInt(this.reserve1);
        dest.writeInt(this.reserve3);
        dest.writeInt(this.reserve3);
        dest.writeString(this.reserve4);
        dest.writeString(this.reserve5);
        dest.writeString(this.reserve6);
    }

    public static d getDbInfo(e source, boolean isPatch, int resType) {
        d dest = new d();
        dest.url = source.url;
        dest.size = source.size;
        if (isPatch) {
            dest.url = source.patchurl;
            dest.size = source.patchsize;
            dest.md5 = source.patchmd5;
        }
        dest.md5 = source.md5;
        dest.restype = resType;
        dest.versionname = source.versionname;
        dest.versioncode = source.versioncode;
        dest.content = source.content;
        dest.packname = source.packname;
        dest.channel = source.channel;
        dest.name = source.name;
        return dest;
    }

    public static e getInfo(d source) {
        e dest = new e();
        dest.url = source.url;
        dest.size = source.size;
        dest.md5 = source.md5;
        dest.versionname = source.versionname;
        dest.versioncode = source.versioncode;
        dest.content = source.content;
        dest.packname = source.packname;
        dest.channel = source.channel;
        dest.name = source.name;
        return dest;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return ((d) o).url.equals(((d) o).url);
    }

    public String getFinalFileName() {
        return this.reserve4;
    }
}
