package com.MCWorld.data.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.android.pushservice.PushConstants;
import com.MCWorld.data.TagInfo;
import com.MCWorld.data.UserBaseInfo;
import com.tencent.open.SocialConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class McTopicCategory implements Parcelable, Serializable {
    public static final Creator<McTopicCategory> CREATOR = new Creator<McTopicCategory>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return o(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return Y(i);
        }

        public McTopicCategory o(Parcel source) {
            return new McTopicCategory(source);
        }

        public McTopicCategory[] Y(int size) {
            return new McTopicCategory[size];
        }
    };
    private static final long serialVersionUID = -2593292999955061070L;
    public long categoryID;
    public String description;
    public String forumname;
    public boolean hasChild;
    public String icon;
    public int isGood;
    public int isSearch = 1000;
    public int isSubscribe = 0;
    public int isVoted;
    public int model;
    public List<UserBaseInfo> moderator = new ArrayList();
    public long postCount;
    public String rule;
    public int subscribeType = 0;
    public List<TagInfo> tags = new ArrayList();
    public long tipMsg;
    public String title;
    public int type;
    public long viewCount;
    public long voteCount;
    public String wap = null;
    public int zoneId;

    public McTopicCategory(int type) {
        this.type = type;
    }

    public McTopicCategory(JSONObject obj) throws JSONException {
        boolean z = true;
        if (obj != null) {
            int i;
            this.categoryID = obj.optLong("categoryID");
            this.title = obj.optString("title");
            this.icon = obj.optString("icon");
            this.postCount = obj.optLong("postCount");
            this.viewCount = obj.optLong("viewCount");
            this.description = obj.optString(SocialConstants.PARAM_COMMENT);
            this.model = obj.optInt("model");
            this.isGood = obj.optInt("isGood");
            this.isSubscribe = obj.optInt("isSubscribe");
            this.isVoted = obj.optInt("isVoted");
            this.voteCount = obj.optLong("voteCount");
            this.forumname = obj.optString("forumname");
            this.rule = obj.optString("rule");
            this.type = obj.optInt("type", 2);
            this.isSearch = obj.optInt("isSearch");
            this.subscribeType = obj.optInt("subscribeType", 0);
            this.wap = obj.optString("wap", null);
            this.zoneId = obj.optInt("appId", 0);
            this.tipMsg = obj.optLong("tipMsg", 0);
            if (obj.optInt("hasChild") != 1) {
                z = false;
            }
            this.hasChild = z;
            JSONArray arr = obj.optJSONArray("moderator");
            if (arr != null) {
                for (i = 0; i < arr.length(); i++) {
                    this.moderator.add(new UserBaseInfo(arr.optJSONObject(i)));
                }
            }
            JSONArray arrTag = obj.optJSONArray(PushConstants.EXTRA_TAGS);
            if (arrTag != null) {
                for (i = 0; i < arrTag.length(); i++) {
                    this.tags.add(new TagInfo(arrTag.optJSONObject(i)));
                }
            }
        }
    }

    public McTopicCategory(Parcel source) {
        boolean z = true;
        this.categoryID = source.readLong();
        this.title = source.readString();
        this.icon = source.readString();
        this.postCount = source.readLong();
        this.viewCount = source.readLong();
        this.description = source.readString();
        this.model = source.readInt();
        this.isGood = source.readInt();
        this.isSubscribe = source.readInt();
        this.subscribeType = source.readInt();
        this.isVoted = source.readInt();
        this.voteCount = source.readLong();
        this.forumname = source.readString();
        this.rule = source.readString();
        this.type = source.readInt();
        this.isSearch = source.readInt();
        this.wap = source.readString();
        this.zoneId = source.readInt();
        this.tipMsg = source.readLong();
        if (source.readInt() != 1) {
            z = false;
        }
        this.hasChild = z;
        source.readList(this.moderator, UserBaseInfo.class.getClassLoader());
        source.readList(this.tags, TagInfo.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.categoryID);
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeLong(this.postCount);
        dest.writeLong(this.viewCount);
        dest.writeString(this.description);
        dest.writeInt(this.model);
        dest.writeInt(this.isGood);
        dest.writeInt(this.isSubscribe);
        dest.writeInt(this.subscribeType);
        dest.writeInt(this.isVoted);
        dest.writeLong(this.voteCount);
        dest.writeString(this.forumname);
        dest.writeString(this.rule);
        dest.writeInt(this.type);
        dest.writeInt(this.isSearch);
        dest.writeString(this.wap);
        dest.writeInt(this.zoneId);
        dest.writeLong(this.tipMsg);
        dest.writeInt(this.hasChild ? 1 : 0);
        dest.writeList(this.moderator);
        dest.writeList(this.tags);
    }

    public long getCategoryID() {
        return this.categoryID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIcon() {
        return this.icon;
    }

    public long getPostCount() {
        return this.postCount;
    }

    public long getViewCount() {
        return this.viewCount;
    }

    public String getDescription() {
        return this.description;
    }

    public int getModel() {
        return this.model;
    }

    public int getIsGood() {
        return this.isGood;
    }

    public int getIsSubscribe() {
        return this.isSubscribe;
    }

    public int getSubscribeType() {
        return this.subscribeType;
    }

    public int getIsVoted() {
        return this.isVoted;
    }

    public long getVoteCount() {
        return this.voteCount;
    }

    public String getForumname() {
        return this.forumname;
    }

    public String getRule() {
        return this.rule;
    }

    public int getType() {
        return this.type;
    }

    public int getIsSearch() {
        return this.isSearch;
    }

    public String getWap() {
        return this.wap;
    }

    public int getZoneId() {
        return this.zoneId;
    }

    public List<UserBaseInfo> getModerator() {
        return this.moderator;
    }

    public List<TagInfo> getTags() {
        return this.tags;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public void setIsVoted(int isVoted) {
        this.isVoted = isVoted;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTipMsg() {
        return this.tipMsg;
    }

    public void setTipMsg(long tipMsg) {
        this.tipMsg = tipMsg;
    }

    public boolean isHasChild() {
        return this.hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
