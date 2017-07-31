package com.huluxia.framework.base.http.toolbox.download;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hlx_download_record")
public class DownloadRecord implements Parcelable, Cloneable {
    public static final String COLUMN_DIR = "dir";
    public static final String COLUMN_ERROR = "error";
    public static final String COLUMN_HTTP_STATUS_CODE = "http_status_code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PAUSE = "pause";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_URL = "url";
    public static final Creator<DownloadRecord> CREATOR = new 1();
    public static final String NO_INTEGRITY = "no_integrity";
    public static final String RESERVE1 = "reserve1";
    public static final String RESERVE2 = "reserve2";
    public static final String RESERVE3 = "reserve3";
    public static final String RESERVE4 = "reserve4";
    public static final String RESERVE5 = "reserve5";
    public static final String RESERVE6 = "reserve6";
    public static final String TABLE = "hlx_download_record";
    @DatabaseField(columnName = "dir")
    public String dir;
    @DatabaseField(columnName = "error")
    public int error = -1;
    @DatabaseField(columnName = "http_status_code")
    public int httpstatuscode;
    @DatabaseField(columnName = "name")
    public String name;
    public boolean needRestart = false;
    @DatabaseField(columnName = "no_integrity")
    public boolean noIntegrity = true;
    @DatabaseField(columnName = "pause")
    public boolean pause = false;
    @DatabaseField(columnName = "progress")
    public long progress;
    @DatabaseField(columnName = "reserve1")
    public int reserve1 = -1;
    @DatabaseField(columnName = "reserve2")
    public int reserve2 = -1;
    @DatabaseField(columnName = "reserve3")
    public int reserve3 = -1;
    @DatabaseField(columnName = "reserve4")
    public String reserve4 = "";
    @DatabaseField(columnName = "reserve5")
    public String reserve5 = "";
    @DatabaseField(columnName = "reserve6")
    public String reserve6 = "";
    @DatabaseField(columnName = "state")
    public int state = State.INIT.state;
    @DatabaseField(columnName = "total")
    public long total;
    @DatabaseField(columnName = "url", id = true)
    public String url;

    public DownloadRecord(Parcel source) {
        boolean z;
        boolean z2 = true;
        this.url = source.readString();
        this.progress = source.readLong();
        this.total = source.readLong();
        this.dir = source.readString();
        this.name = source.readString();
        this.state = source.readInt();
        this.error = source.readInt();
        this.pause = source.readInt() == 1;
        this.httpstatuscode = source.readInt();
        if (source.readInt() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.noIntegrity = z;
        this.reserve1 = source.readInt();
        this.reserve2 = source.readInt();
        this.reserve3 = source.readInt();
        this.reserve4 = source.readString();
        this.reserve5 = source.readString();
        this.reserve6 = source.readString();
        if (source.readInt() != 1) {
            z2 = false;
        }
        this.needRestart = z2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.url);
        dest.writeLong(this.progress);
        dest.writeLong(this.total);
        dest.writeString(this.dir);
        dest.writeString(this.name);
        dest.writeInt(this.state);
        dest.writeInt(this.error);
        dest.writeInt(this.pause ? 1 : 0);
        dest.writeInt(this.httpstatuscode);
        if (this.noIntegrity) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeInt(this.reserve1);
        dest.writeInt(this.reserve2);
        dest.writeInt(this.reserve3);
        dest.writeString(this.reserve4);
        dest.writeString(this.reserve5);
        dest.writeString(this.reserve6);
        if (!this.needRestart) {
            i2 = 0;
        }
        dest.writeInt(i2);
    }

    public String toString() {
        return "DownloadRecord{url='" + this.url + '\'' + ", progress=" + this.progress + ", total=" + this.total + ", dir='" + this.dir + '\'' + ", name='" + this.name + '\'' + ", state=" + this.state + ", error=" + this.error + ", pause=" + this.pause + ", httpstatuscode=" + this.httpstatuscode + ", noIntegrity=" + this.noIntegrity + ", reserver1=" + this.reserve1 + ", reserver2=" + this.reserve2 + ", reserver3=" + this.reserve3 + ", reserver4=" + this.reserve4 + ", reserver5=" + this.reserve5 + ", reserver6=" + this.reserve6 + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DownloadRecord that = (DownloadRecord) o;
        if (this.url != null) {
            if (this.url.equals(that.url)) {
                return true;
            }
        } else if (that.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.url != null ? this.url.hashCode() : 0;
    }

    public void resetError() {
        this.error = -1;
    }

    public DownloadRecord clone() {
        DownloadRecord newRecord = new DownloadRecord();
        newRecord.url = this.url;
        newRecord.progress = this.progress;
        newRecord.total = this.total;
        newRecord.dir = this.dir;
        newRecord.name = this.name;
        newRecord.state = this.state;
        newRecord.error = this.error;
        newRecord.pause = this.pause;
        newRecord.httpstatuscode = this.httpstatuscode;
        newRecord.noIntegrity = this.noIntegrity;
        newRecord.reserve1 = this.reserve1;
        newRecord.reserve2 = this.reserve2;
        newRecord.reserve3 = this.reserve3;
        newRecord.reserve4 = this.reserve4;
        newRecord.reserve5 = this.reserve5;
        newRecord.reserve6 = this.reserve6;
        newRecord.needRestart = this.needRestart;
        return newRecord;
    }
}
