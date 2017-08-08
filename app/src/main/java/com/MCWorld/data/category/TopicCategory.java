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

public class TopicCategory implements Parcelable, Serializable {
    public static final Creator<TopicCategory> CREATOR = new Creator<TopicCategory>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return p(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return Z(i);
        }

        public TopicCategory p(Parcel source) {
            return new TopicCategory(source);
        }

        public TopicCategory[] Z(int size) {
            return new TopicCategory[size];
        }
    };
    private static final long serialVersionUID = -2593292999955061070L;
    public long categoryID;
    public String description;
    public String forumname;
    public int hasChild;
    public String icon;
    public int isGood;
    public int isSearch = 1000;
    public int isSubscribe = 0;
    public int isVoted;
    public int model;
    public List<UserBaseInfo> moderator = new ArrayList();
    public long postCount;
    public String postCountFormated;
    public String rule;
    public int subscribeType = 0;
    public List<TagInfo> tags = new ArrayList();
    public long tipMsg;
    public String title;
    public int totle;
    public int type;
    public long viewCount;
    public String viewCountFormated;
    public long voteCount;
    public String wap = null;
    public int zoneId;

    public TopicCategory(int type) {
        this.type = type;
    }

    public TopicCategory(JSONObject obj) throws JSONException {
        if (obj != null) {
            int i;
            this.categoryID = obj.optLong("categoryID");
            this.title = obj.optString("title");
            this.icon = obj.optString("icon");
            this.postCount = obj.optLong("postCount");
            this.viewCount = obj.optLong("viewCount");
            try {
                this.postCountFormated = obj.getString("postCountFormated");
                this.viewCountFormated = obj.getString("viewCountFormated");
            } catch (Exception e) {
            }
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
            this.hasChild = obj.optInt("hasChild");
            this.totle = obj.optInt("totle");
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

    public String getViewCountFormated() {
        return this.viewCountFormated;
    }

    public void setViewCountFormated(String viewCountFormated) {
        this.viewCountFormated = viewCountFormated;
    }

    public String getPostCountFormated() {
        return this.postCountFormated;
    }

    public void setPostCountFormated(String postCountFormated) {
        this.postCountFormated = postCountFormated;
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.categoryID);
        dest.writeString(this.title);
        dest.writeString(this.icon);
        dest.writeLong(this.postCount);
        dest.writeLong(this.viewCount);
        dest.writeString(this.postCountFormated);
        dest.writeString(this.viewCountFormated);
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
        dest.writeInt(this.hasChild);
        dest.writeInt(this.totle);
        dest.writeTypedList(this.moderator);
        dest.writeTypedList(this.tags);
    }

    protected TopicCategory(Parcel in) {
        this.categoryID = in.readLong();
        this.title = in.readString();
        this.icon = in.readString();
        this.postCount = in.readLong();
        this.viewCount = in.readLong();
        this.postCountFormated = in.readString();
        this.viewCountFormated = in.readString();
        this.description = in.readString();
        this.model = in.readInt();
        this.isGood = in.readInt();
        this.isSubscribe = in.readInt();
        this.subscribeType = in.readInt();
        this.isVoted = in.readInt();
        this.voteCount = in.readLong();
        this.forumname = in.readString();
        this.rule = in.readString();
        this.type = in.readInt();
        this.isSearch = in.readInt();
        this.wap = in.readString();
        this.zoneId = in.readInt();
        this.tipMsg = in.readLong();
        this.hasChild = in.readInt();
        this.totle = in.readInt();
        this.moderator = in.createTypedArrayList(UserBaseInfo.CREATOR);
        this.tags = in.createTypedArrayList(TagInfo.CREATOR);
    }
}
