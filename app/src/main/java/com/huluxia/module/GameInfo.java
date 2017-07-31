package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameInfo implements Parcelable {
    public static final Creator<GameInfo> CREATOR = new Creator<GameInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aE(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return iZ(i);
        }

        public GameInfo aE(Parcel source) {
            return new GameInfo(source);
        }

        public GameInfo[] iZ(int size) {
            return new GameInfo[size];
        }
    };
    public String actualName;
    public String appauthorization;
    public String appcrackdesc;
    public String appcrc;
    public String appdesc;
    public List<e> appdownul;
    public long appid;
    public String applanguage;
    public String applogo;
    public String appsize;
    public String apptags;
    private String apptitle;
    public String apptype;
    public String appversion;
    public String backgroundColor;
    public String backgroundColorQuote;
    public int businessType;
    public String categoryalias;
    public String categoryname;
    public List<e> clouddownlist;
    public String coverImage;
    public String dataDownUrl;
    public int downFileType;
    public String downloadingUrl;
    public int encode;
    public String extract360;
    public String filename;
    public String fontColor1st;
    public String fontColor2nd;
    public String imageParams;
    public int imageResourceDirection;
    public List<String> imageresource;
    public int isGift;
    public e localurl;
    public String md5;
    public boolean needlogin;
    public e onlineurl;
    public List<e> onlineurllist;
    public int openCloudType;
    public int opentype;
    public String packname;
    public long pageSize;
    public String separatorColor;
    public boolean share;
    public String shareurl;
    public String shortdesc;
    public int storagePath;
    public String system;
    public String tongjiPage;
    public long tooldown;
    public String username;
    public int versionCode;
    public int viewCustomized;

    public enum DownloadType {
        BAIDU_CLOUD("http://pan.baidu.com/", 0),
        HUAWEI_CLOUD("http://dl.vmall.com/", 1),
        QIHU_CLOUD("http://yunpan.cn/", 2),
        LOCAL("LOCAL", 3),
        YOUKU_VIDEO("http://v.youku.com/", 4),
        SOHU_VIDEO("http://my.tv.sohu.com/", 5),
        QQ_VIDEO("http://v.qq.com/", 6),
        NONE("NONE", 7);
        
        private int index;
        private String location;

        private DownloadType(String location, int index) {
            this.location = location;
            this.index = index;
        }

        public String getLocation() {
            return this.location;
        }

        public int getIndex() {
            return this.index;
        }
    }

    public GameInfo() {
        this.pageSize = 0;
        this.imageresource = new ArrayList();
        this.appdownul = new ArrayList();
        this.clouddownlist = new ArrayList();
        this.onlineurllist = new ArrayList();
    }

    public GameInfo(Parcel source) {
        boolean z = true;
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
        this.share = source.readInt() == 1;
        this.username = source.readString();
        this.system = source.readString();
        this.versionCode = source.readInt();
        this.storagePath = source.readInt();
        this.businessType = source.readInt();
        this.appcrackdesc = source.readString();
        if (source.readInt() != 1) {
            z = false;
        }
        this.needlogin = z;
        source.readStringList(this.imageresource);
        source.readTypedList(this.appdownul, e.CREATOR);
        source.readTypedList(this.clouddownlist, e.CREATOR);
        this.downloadingUrl = source.readString();
        this.extract360 = source.readString();
        this.pageSize = source.readLong();
        this.actualName = source.readString();
        this.md5 = source.readString();
        this.openCloudType = source.readInt();
        this.downFileType = source.readInt();
        this.categoryalias = source.readString();
        this.localurl = (e) source.readParcelable(e.class.getClassLoader());
        this.onlineurl = (e) source.readParcelable(e.class.getClassLoader());
        source.readTypedList(this.onlineurllist, e.CREATOR);
        this.isGift = source.readInt();
        this.tongjiPage = source.readString();
        this.tooldown = source.readLong();
        this.shortdesc = source.readString();
        this.filename = source.readString();
        this.encode = source.readInt();
        this.viewCustomized = source.readInt();
        this.coverImage = source.readString();
        this.backgroundColor = source.readString();
        this.fontColor1st = source.readString();
        this.fontColor2nd = source.readString();
        this.separatorColor = source.readString();
        this.backgroundColorQuote = source.readString();
        this.imageResourceDirection = source.readInt();
        this.dataDownUrl = source.readString();
        this.imageParams = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 1;
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
        dest.writeInt(this.share ? 1 : 0);
        dest.writeString(this.username);
        dest.writeString(this.system);
        dest.writeInt(this.versionCode);
        dest.writeInt(this.storagePath);
        dest.writeInt(this.businessType);
        dest.writeString(this.appcrackdesc);
        if (!this.needlogin) {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeStringList(this.imageresource);
        dest.writeTypedList(this.appdownul);
        dest.writeTypedList(this.clouddownlist);
        dest.writeString(this.downloadingUrl);
        dest.writeString(this.extract360);
        dest.writeLong(this.pageSize);
        dest.writeString(this.actualName);
        dest.writeString(this.md5);
        dest.writeInt(this.openCloudType);
        dest.writeInt(this.downFileType);
        dest.writeString(this.categoryalias);
        dest.writeParcelable(this.localurl, 0);
        dest.writeParcelable(this.onlineurl, 0);
        dest.writeTypedList(this.onlineurllist);
        dest.writeInt(this.isGift);
        dest.writeString(this.tongjiPage);
        dest.writeLong(this.tooldown);
        dest.writeString(this.shortdesc);
        dest.writeString(this.filename);
        dest.writeInt(this.encode);
        dest.writeInt(this.viewCustomized);
        dest.writeString(this.coverImage);
        dest.writeString(this.backgroundColor);
        dest.writeString(this.fontColor1st);
        dest.writeString(this.fontColor2nd);
        dest.writeString(this.separatorColor);
        dest.writeString(this.backgroundColorQuote);
        dest.writeInt(this.imageResourceDirection);
        dest.writeString(this.dataDownUrl);
        dest.writeString(this.imageParams);
    }

    public String toString() {
        return "GameInfo{appid=" + this.appid + ", downFileType=" + this.downFileType + ", localurl=" + this.localurl + ", onlineurl=" + this.onlineurl + '}';
    }

    public int getApptype() {
        int type = 2;
        if (this.apptype != null && this.apptype.length() > 0) {
            try {
                type = Integer.parseInt(this.apptype);
            } catch (Exception e) {
                HLog.error(this, "apptype format error", e, new Object[0]);
            }
        }
        return type;
    }

    public String getAppTitle() {
        return this.apptitle;
    }

    public void setAppTitle(String title) {
        this.apptitle = title;
    }

    public List<String> getTagList() {
        List<String> list = new ArrayList();
        if (this.apptags != null && this.apptags.length() > 0) {
            for (String tag : Arrays.asList(this.apptags.split(MiPushClient.ACCEPT_TIME_SEPARATOR))) {
                list.add(tag);
            }
        }
        return list;
    }

    public String getCategoryname() {
        if (this.categoryname == null || this.categoryname.length() <= 0) {
            this.categoryname = "默认";
        }
        return this.categoryname;
    }

    public String getDownloadUrl() {
        if (UtilsFunction.empty(this.appdownul)) {
            return null;
        }
        return ((e) this.appdownul.get(0)).url;
    }

    public DownloadType getDownloadType(String url) {
        if (UtilsFunction.empty(url)) {
            return DownloadType.NONE;
        }
        if (url.startsWith(DownloadType.BAIDU_CLOUD.getLocation())) {
            return DownloadType.BAIDU_CLOUD;
        }
        if (url.contains(DownloadType.HUAWEI_CLOUD.getLocation())) {
            return DownloadType.HUAWEI_CLOUD;
        }
        if (url.startsWith(DownloadType.QIHU_CLOUD.getLocation())) {
            return DownloadType.QIHU_CLOUD;
        }
        return DownloadType.LOCAL;
    }

    public static String getFileName(GameInfo info) {
        return info.apptitle + "_" + String.valueOf(info.appid);
    }

    public static boolean openCloudDirect(GameInfo info) {
        return info.openCloudType == 2;
    }
}
