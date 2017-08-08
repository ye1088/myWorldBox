package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.TagInfo;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.category.TopicCategory;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BbsZoneSubCategoryItem */
public class d implements Parcelable {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bk(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jY(i);
        }

        public d bk(Parcel source) {
            return new d(source);
        }

        public d[] jY(int size) {
            return new d[size];
        }
    };
    public int appId;
    public String cateRule;
    public int categoryID;
    public String description;
    public int forumid;
    public int hasChild;
    public int hide;
    public String icon;
    public int isGood;
    public int isSubscribe;
    public int model;
    public List<e> moderator = new ArrayList();
    public int pid;
    public long postCount;
    public String postCountFormated;
    public int seq;
    public int subscribeType;
    public List<f> tags = new ArrayList();
    public String title;
    public long viewCount;
    public String viewCountFormated;
    public String wap;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.categoryID != ((d) o).categoryID) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.categoryID;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryID);
        dest.writeInt(this.model);
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeString(this.description);
        dest.writeLong(this.postCount);
        dest.writeLong(this.viewCount);
        dest.writeString(this.postCountFormated);
        dest.writeString(this.viewCountFormated);
        dest.writeInt(this.isGood);
        dest.writeInt(this.isSubscribe);
        dest.writeInt(this.hide);
        dest.writeInt(this.seq);
        dest.writeInt(this.subscribeType);
        dest.writeInt(this.forumid);
        dest.writeString(this.cateRule);
        dest.writeString(this.wap);
        dest.writeInt(this.appId);
        dest.writeInt(this.hasChild);
        dest.writeInt(this.pid);
        dest.writeTypedList(this.moderator);
        dest.writeTypedList(this.tags);
    }

    protected d(Parcel in) {
        this.categoryID = in.readInt();
        this.model = in.readInt();
        this.title = in.readString();
        this.icon = in.readString();
        this.description = in.readString();
        this.postCount = in.readLong();
        this.viewCount = in.readLong();
        this.postCountFormated = in.readString();
        this.viewCountFormated = in.readString();
        this.isGood = in.readInt();
        this.isSubscribe = in.readInt();
        this.hide = in.readInt();
        this.seq = in.readInt();
        this.subscribeType = in.readInt();
        this.forumid = in.readInt();
        this.cateRule = in.readString();
        this.wap = in.readString();
        this.appId = in.readInt();
        this.hasChild = in.readInt();
        this.pid = in.readInt();
        this.moderator = in.createTypedArrayList(e.CREATOR);
        this.tags = in.createTypedArrayList(f.CREATOR);
    }

    public TopicCategory convert() {
        TopicCategory topicCategory = new TopicCategory(2);
        topicCategory.categoryID = (long) this.categoryID;
        topicCategory.model = this.model;
        topicCategory.title = this.title;
        topicCategory.icon = this.icon;
        topicCategory.description = this.description;
        topicCategory.postCount = this.postCount;
        topicCategory.viewCount = this.viewCount;
        topicCategory.postCountFormated = this.postCountFormated;
        topicCategory.viewCountFormated = this.viewCountFormated;
        topicCategory.isGood = this.isGood;
        topicCategory.isSubscribe = this.isSubscribe;
        topicCategory.voteCount = 0;
        topicCategory.isVoted = 0;
        topicCategory.subscribeType = this.subscribeType;
        topicCategory.rule = this.cateRule;
        topicCategory.wap = this.wap;
        topicCategory.hasChild = this.hasChild;
        topicCategory.zoneId = this.appId;
        topicCategory.type = 2;
        topicCategory.isSearch = 0;
        List<UserBaseInfo> moderator = new ArrayList();
        for (e m : this.moderator) {
            UserBaseInfo baseInfo = new UserBaseInfo();
            baseInfo.userID = (long) m.userID;
            baseInfo.nick = m.nick;
            baseInfo.avatar = m.avatar;
            baseInfo.identityTitle = m.identityTitle;
            baseInfo.levelColor = (long) m.leveColor;
            moderator.add(baseInfo);
        }
        topicCategory.moderator = moderator;
        List<TagInfo> tagInfos = new ArrayList();
        for (f tag : this.tags) {
            TagInfo tagInfo = new TagInfo();
            tagInfo.ID = (long) tag.ID;
            tagInfo.name = tag.name;
            tagInfos.add(tagInfo);
        }
        topicCategory.tags = tagInfos;
        return topicCategory;
    }
}
