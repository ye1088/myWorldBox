package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: CreatePowerInfo */
public class g extends a implements Parcelable {
    public static final Creator<g> CREATOR = new 1();
    public HashSet<Long> commentCats;
    public HashMap<Long, String> commentHours;
    public String commentTipMsg;
    public String commentTipTitle;
    public int ispower;
    public int isvideo;
    public String message;
    public HashSet<Long> topicCats;
    public HashMap<Long, String> topicHours;
    public String topicTipMsg;
    public String topicTipTitle;
    public String videosomd5;
    public String videosourl;

    public String toString() {
        return "ispower " + this.ispower + "; isvideo " + this.isvideo + "; message " + this.message + "; topicCats " + this.topicCats + "; commentCats " + this.commentCats + "; topicHours " + this.topicHours + "; commentHours " + this.commentHours + "; topicTipMsg " + this.topicTipMsg + "; commentTipMsg " + this.commentTipMsg + "; topicTipTitle " + this.topicTipTitle + "; commentTipTitle " + this.commentTipTitle + "; videosourl " + this.videosourl + "; videosomd5 " + this.videosomd5;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.ispower);
        dest.writeInt(this.isvideo);
        dest.writeString(this.message);
        dest.writeSerializable(this.topicCats);
        dest.writeSerializable(this.commentCats);
        dest.writeSerializable(this.topicHours);
        dest.writeSerializable(this.commentHours);
        dest.writeString(this.topicTipMsg);
        dest.writeString(this.commentTipMsg);
        dest.writeString(this.topicTipTitle);
        dest.writeString(this.commentTipTitle);
        dest.writeString(this.videosourl);
        dest.writeString(this.videosomd5);
    }

    public g() {
        this.topicCats = new HashSet();
        this.commentCats = new HashSet();
        this.topicHours = new HashMap();
        this.commentHours = new HashMap();
    }

    protected g(Parcel in) {
        super(in);
        this.ispower = in.readInt();
        this.isvideo = in.readInt();
        this.message = in.readString();
        this.topicCats = (HashSet) in.readSerializable();
        this.commentCats = (HashSet) in.readSerializable();
        this.topicHours = (HashMap) in.readSerializable();
        this.commentHours = (HashMap) in.readSerializable();
        this.topicTipMsg = in.readString();
        this.commentTipMsg = in.readString();
        this.topicTipTitle = in.readString();
        this.commentTipTitle = in.readString();
        this.videosourl = in.readString();
        this.videosomd5 = in.readString();
    }
}
