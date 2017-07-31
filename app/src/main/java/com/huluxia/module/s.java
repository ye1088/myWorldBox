package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hlx_res_info")
/* compiled from: ResDbInfo */
public class s implements Parcelable {
    public static final String ACTUALNAME = "actualname";
    public static final String APPAUTHORIZATION = "appauthorization";
    public static final String APPCRACKDESC = "appcrackdesc";
    public static final String APPCRC = "appcrc";
    public static final String APPDESC = "appdesc";
    public static final String APPID = "appid";
    public static final String APPLANGUAGE = "applanguage";
    public static final String APPLOGO = "applogo";
    public static final String APPSIZE = "appsize";
    public static final String APPTAGS = "apptags";
    public static final String APPTITLE = "apptitle";
    public static final String APPTYPE = "apptype";
    public static final String APPVERSION = "appversion";
    public static final String BUSINESSTYPE = "businesstype";
    public static final String CATEGORYALIAS = "categoryalias";
    public static final String CATEGORYNAME = "categoryname";
    public static final String CDNURL = "cdnurl";
    public static final String CHKSUM = "chksum";
    public static final Creator<s> CREATOR = new 1();
    public static final String DATADOWNURL = "datadownurl";
    public static final String DOWNFILETYPE = "downfiletype";
    public static final String DOWNLOADINGURL = "downloadingurl";
    public static final String DOWNLOADSTATUS = "downloadstatus";
    public static final String EXTRACT360 = "extract360";
    public static final String FILEPATH = "filepath";
    public static final String FROMWAP = "fromwap";
    public static final String MD5 = "md5";
    public static final String NEEDLOGIN = "needlogin";
    public static final String OPENCLOUDTYPE = "opencloudtype";
    public static final String OPENTYPE = "opentype";
    public static final String PACKNAME = "packname";
    public static final String PAGESIZE = "pagesize";
    public static final String RESERVE1 = "reserve1";
    public static final String RESERVE2 = "reserve2";
    public static final String RESERVE3 = "reserve3";
    public static final String RESERVE4 = "reserve4";
    public static final String RESERVE5 = "reserve5";
    public static final String RESERVE6 = "reserve6";
    public static final String SHARE = "share";
    public static final String SHAREURL = "shareurl";
    public static final String STORAGEPATH = "storagepath";
    public static final String SYSTEM = "system";
    public static final String TABLE = "hlx_res_info";
    public static final String URLS = "urls";
    public static final String USERNAME = "username";
    public static final String VERSIONCODE = "versioncode";
    @DatabaseField(columnName = "actualname")
    public String actualName;
    @DatabaseField(columnName = "appauthorization")
    public String appauthorization;
    @DatabaseField(columnName = "appcrackdesc")
    public String appcrackdesc;
    @DatabaseField(columnName = "appcrc")
    public String appcrc;
    @DatabaseField(columnName = "appdesc")
    public String appdesc;
    @DatabaseField(columnName = "appid", id = true)
    public long appid;
    @DatabaseField(columnName = "applanguage")
    public String applanguage;
    @DatabaseField(columnName = "applogo")
    public String applogo;
    @DatabaseField(columnName = "appsize")
    public String appsize;
    @DatabaseField(columnName = "apptags")
    public String apptags;
    @DatabaseField(columnName = "apptitle")
    public String apptitle;
    @DatabaseField(columnName = "apptype")
    public String apptype;
    @DatabaseField(columnName = "appversion")
    public String appversion;
    @DatabaseField(columnName = "businesstype")
    public int businessType;
    @DatabaseField(columnName = "categoryalias")
    public String categoryalias;
    @DatabaseField(columnName = "categoryname")
    public String categoryname;
    @DatabaseField(columnName = "cdnurl")
    public String cdnUrl;
    @DatabaseField(columnName = "chksum")
    public long chksum;
    @DatabaseField(columnName = "datadownurl")
    public String dataDownUrl;
    @DatabaseField(columnName = "downfiletype")
    public int downFileType;
    @DatabaseField(columnName = "downloadstatus")
    public int downloadStatus;
    @DatabaseField(columnName = "downloadingurl")
    public String downloadingUrl;
    @DatabaseField(columnName = "extract360")
    public String extract360;
    @DatabaseField(columnName = "filepath")
    public String filePath;
    @DatabaseField(columnName = "fromwap")
    public int fromWap;
    @DatabaseField(columnName = "md5")
    public String md5;
    @DatabaseField(columnName = "needlogin")
    public int needlogin;
    @DatabaseField(columnName = "opencloudtype")
    public int openCloudType;
    @DatabaseField(columnName = "opentype")
    public int opentype;
    @DatabaseField(columnName = "packname")
    public String packname;
    @DatabaseField(columnName = "pagesize")
    public long pageSize;
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
    @DatabaseField(columnName = "share")
    public int share;
    @DatabaseField(columnName = "shareurl")
    public String shareurl;
    @DatabaseField(columnName = "storagepath")
    public int storagePath;
    @DatabaseField(columnName = "system")
    public String system;
    @DatabaseField(columnName = "urls")
    public String urls;
    @DatabaseField(columnName = "username")
    public String username;
    @DatabaseField(columnName = "versioncode")
    public int versionCode;

    public s(Parcel source) {
        this();
        this.appid = source.readLong();
        this.apptitle = source.readString();
        this.appdesc = source.readString();
        this.appsize = source.readString();
        this.applogo = source.readString();
        this.apptags = source.readString();
        this.applanguage = source.readString();
        this.appversion = source.readString();
        this.categoryname = source.readString();
        this.appauthorization = source.readString();
        this.opentype = source.readInt();
        this.apptype = source.readString();
        this.packname = source.readString();
        this.appcrc = source.readString();
        this.shareurl = source.readString();
        this.share = source.readInt();
        this.username = source.readString();
        this.system = source.readString();
        this.versionCode = source.readInt();
        this.storagePath = source.readInt();
        this.businessType = source.readInt();
        this.appcrackdesc = source.readString();
        this.needlogin = source.readInt();
        this.downloadingUrl = source.readString();
        this.extract360 = source.readString();
        this.downloadStatus = source.readInt();
        this.filePath = source.readString();
        this.actualName = source.readString();
        this.pageSize = source.readLong();
        this.fromWap = source.readInt();
        this.md5 = source.readString();
        this.chksum = source.readLong();
        this.urls = source.readString();
        this.downFileType = source.readInt();
        this.openCloudType = source.readInt();
        this.categoryalias = source.readString();
        this.cdnUrl = source.readString();
        this.dataDownUrl = source.readString();
        this.reserve4 = source.readString();
        this.reserve5 = source.readString();
        this.reserve6 = source.readString();
        this.reserve1 = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.appid);
        dest.writeString(this.apptitle);
        dest.writeString(this.appdesc);
        dest.writeString(this.appsize);
        dest.writeString(this.applogo);
        dest.writeString(this.apptags);
        dest.writeString(this.applanguage);
        dest.writeString(this.appversion);
        dest.writeString(this.categoryname);
        dest.writeString(this.appauthorization);
        dest.writeInt(this.opentype);
        dest.writeString(this.apptype);
        dest.writeString(this.packname);
        dest.writeString(this.appcrc);
        dest.writeString(this.shareurl);
        dest.writeInt(this.share);
        dest.writeString(this.username);
        dest.writeString(this.system);
        dest.writeInt(this.versionCode);
        dest.writeInt(this.storagePath);
        dest.writeInt(this.businessType);
        dest.writeString(this.appcrackdesc);
        dest.writeInt(this.needlogin);
        dest.writeString(this.downloadingUrl);
        dest.writeString(this.extract360);
        dest.writeInt(this.downloadStatus);
        dest.writeString(this.filePath);
        dest.writeString(this.actualName);
        dest.writeLong(this.pageSize);
        dest.writeInt(this.fromWap);
        dest.writeString(this.md5);
        dest.writeLong(this.chksum);
        dest.writeString(this.urls);
        dest.writeInt(this.downFileType);
        dest.writeInt(this.openCloudType);
        dest.writeString(this.categoryalias);
        dest.writeString(this.cdnUrl);
        dest.writeString(this.dataDownUrl);
        dest.writeString(this.reserve4);
        dest.writeString(this.reserve5);
        dest.writeString(this.reserve6);
        dest.writeInt(this.reserve1);
    }

    public static s getDbInfo(GameInfo info) {
        int i = 1;
        s dbInfo = new s();
        dbInfo.appid = info.appid;
        dbInfo.apptitle = info.getAppTitle();
        dbInfo.appdesc = info.appdesc;
        dbInfo.appsize = info.appsize;
        dbInfo.applogo = info.applogo;
        dbInfo.apptags = info.apptags;
        dbInfo.applanguage = info.applanguage;
        dbInfo.appversion = info.appversion;
        dbInfo.categoryname = info.categoryname;
        dbInfo.appauthorization = info.appauthorization;
        dbInfo.opentype = info.opentype;
        dbInfo.apptype = info.apptype;
        dbInfo.packname = info.packname;
        dbInfo.appcrc = info.appcrc;
        dbInfo.shareurl = info.shareurl;
        dbInfo.share = info.share ? 1 : 0;
        dbInfo.username = info.username;
        dbInfo.system = info.system;
        dbInfo.versionCode = info.versionCode;
        dbInfo.storagePath = info.storagePath;
        dbInfo.businessType = info.businessType;
        dbInfo.appcrackdesc = info.appcrackdesc;
        if (!info.needlogin) {
            i = 0;
        }
        dbInfo.needlogin = i;
        dbInfo.downloadingUrl = info.downloadingUrl;
        dbInfo.extract360 = info.extract360;
        dbInfo.pageSize = info.pageSize;
        dbInfo.actualName = info.actualName;
        dbInfo.md5 = info.md5;
        dbInfo.chksum = 0;
        dbInfo.downFileType = info.downFileType;
        dbInfo.openCloudType = info.openCloudType;
        dbInfo.categoryalias = info.categoryalias;
        dbInfo.reserve4 = info.filename;
        dbInfo.reserve5 = info.shortdesc;
        dbInfo.reserve6 = "";
        dbInfo.reserve1 = info.encode;
        if (info.localurl != null) {
            dbInfo.cdnUrl = info.localurl.url;
        }
        dbInfo.dataDownUrl = info.dataDownUrl;
        return dbInfo;
    }

    public static GameInfo getInfo(s info) {
        boolean z = true;
        GameInfo dbInfo = new GameInfo();
        dbInfo.appid = info.appid;
        dbInfo.setAppTitle(info.apptitle);
        dbInfo.appdesc = info.appdesc;
        dbInfo.appsize = info.appsize;
        dbInfo.applogo = info.applogo;
        dbInfo.apptags = info.apptags;
        dbInfo.applanguage = info.applanguage;
        dbInfo.appversion = info.appversion;
        dbInfo.categoryname = info.categoryname;
        dbInfo.appauthorization = info.appauthorization;
        dbInfo.opentype = info.opentype;
        dbInfo.apptype = info.apptype;
        dbInfo.packname = info.packname;
        dbInfo.appcrc = info.appcrc;
        dbInfo.shareurl = info.shareurl;
        dbInfo.share = info.share == 1;
        dbInfo.username = info.username;
        dbInfo.system = info.system;
        dbInfo.versionCode = info.versionCode;
        dbInfo.storagePath = info.storagePath;
        dbInfo.businessType = info.businessType;
        dbInfo.appcrackdesc = info.appcrackdesc;
        if (info.needlogin != 1) {
            z = false;
        }
        dbInfo.needlogin = z;
        dbInfo.downloadingUrl = info.downloadingUrl;
        dbInfo.extract360 = info.extract360;
        dbInfo.pageSize = info.pageSize;
        dbInfo.actualName = info.actualName;
        dbInfo.md5 = info.md5;
        dbInfo.downFileType = info.downFileType;
        dbInfo.openCloudType = info.openCloudType;
        dbInfo.categoryalias = info.categoryalias;
        dbInfo.filename = info.reserve4;
        dbInfo.shortdesc = info.reserve5;
        dbInfo.encode = info.reserve1;
        dbInfo.dataDownUrl = info.dataDownUrl;
        return dbInfo;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (((s) o).appid != this.appid) {
            return false;
        }
        return true;
    }

    public String getFinalFileName() {
        return this.reserve4;
    }

    public int getEncodeType() {
        return this.reserve1;
    }
}
