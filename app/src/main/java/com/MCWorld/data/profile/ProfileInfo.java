package com.MCWorld.data.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.Medal;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.module.a;
import com.MCWorld.module.profile.h;
import com.tencent.mm.sdk.plugin.BaseProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileInfo extends a implements Parcelable, Serializable {
    public static final Creator<ProfileInfo> CREATOR = new 1();
    private static final long serialVersionUID = -4710777205396083651L;
    private int age;
    private String avatar;
    private String avatarFid;
    private long birthday;
    public long commentCount;
    private String contact;
    private long credits;
    private int exp;
    public long favoriteCount;
    private long followerCount;
    public String followerCountFormated;
    private long followingCount;
    public String followingCountFormated;
    private int gender;
    private long identityColor;
    private String identityTitle;
    private long integral;
    public String integralNick;
    public int isvideo;
    private int level;
    private long levelColor;
    public List<Medal> medalList = new ArrayList();
    public int model;
    private int nextExp;
    private String nick;
    private List<PhotoInfo> photos = new ArrayList();
    public long postCount;
    private int role;
    public h space;
    private int state;
    private long userID;

    public ProfileInfo(JSONObject obj) throws JSONException {
        int i;
        this.userID = obj.optLong("userID");
        this.age = obj.optInt("age");
        this.avatar = obj.optString(BaseProfile.COL_AVATAR);
        this.avatarFid = obj.optString("avatarFid");
        this.birthday = obj.optLong("birthday");
        this.gender = obj.optInt("gender");
        this.nick = obj.optString("nick");
        this.role = obj.optInt("role");
        this.state = obj.optInt(DownloadRecord.COLUMN_STATE);
        this.credits = obj.optLong("credits");
        this.integral = obj.optLong("integral");
        this.level = obj.optInt("level");
        this.exp = obj.optInt("exp");
        this.nextExp = obj.optInt("nextExp");
        this.identityTitle = obj.optString("identityTitle");
        this.identityColor = obj.optLong("identityColor");
        this.levelColor = obj.optLong("levelColor");
        this.followingCount = obj.optLong("followingCount");
        this.followerCount = obj.optLong("followerCount");
        try {
            this.followingCountFormated = obj.getString("followingCountFormated");
            this.followerCountFormated = obj.getString("followerCountFormated");
        } catch (Exception e) {
        }
        this.contact = obj.optString("contact");
        JSONArray arr = obj.optJSONArray("photos");
        if (arr != null) {
            for (i = 0; i < arr.length(); i++) {
                this.photos.add(new PhotoInfo(arr.optJSONObject(i)));
            }
        }
        JSONArray medalArr = obj.optJSONArray("medalList");
        if (medalArr != null) {
            for (i = 0; i < medalArr.length(); i++) {
                this.medalList.add(new Medal(medalArr.optJSONObject(i)));
            }
        }
        this.postCount = obj.optLong("postCount", 0);
        this.commentCount = obj.optLong("commentCount", 0);
        this.favoriteCount = obj.optLong("favoriteCount", 0);
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getBirthday() {
        return this.birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAvatar_fid() {
        return this.avatarFid;
    }

    public void setAvatar_fid(String avatar_fid) {
        this.avatarFid = avatar_fid;
    }

    public long getCredits() {
        return this.credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public long getIntegral() {
        return this.integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextExp() {
        return this.nextExp;
    }

    public void setNextExp(int nextExp) {
        this.nextExp = nextExp;
    }

    public String getIdentityTitle() {
        return this.identityTitle;
    }

    public void setIdentityTitle(String identityTitle) {
        this.identityTitle = identityTitle;
    }

    public long getIdentityColor() {
        return this.identityColor;
    }

    public void setIdentityColor(long identityColor) {
        this.identityColor = identityColor;
    }

    public long getFollowingCount() {
        return this.followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getFollowerCount() {
        return this.followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public String getFollowerCountFormated() {
        return this.followerCountFormated;
    }

    public void setFollowerCountFormated(String followerCountFormated) {
        this.followerCountFormated = followerCountFormated;
    }

    public String getFollowingCountFormated() {
        return this.followingCountFormated;
    }

    public void setFollowingCountFormated(String followingCountFormated) {
        this.followingCountFormated = followingCountFormated;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getLevelColor() {
        return this.levelColor;
    }

    public void setLevelColor(long levelColor) {
        this.levelColor = levelColor;
    }

    public List<PhotoInfo> getPhotos() {
        return this.photos;
    }

    public void setPhoto(List<PhotoInfo> photo) {
        this.photos = photo;
    }

    public List<Medal> getMedalList() {
        return this.medalList;
    }

    public void setMedalList(List<Medal> medalList) {
        this.medalList = medalList;
    }

    public static UserBaseInfo getUserBaseInfo(ProfileInfo info) {
        UserBaseInfo baseInfo = new UserBaseInfo();
        baseInfo.userID = info.userID;
        baseInfo.nick = info.nick;
        baseInfo.age = info.age;
        baseInfo.gender = info.gender;
        baseInfo.avatar = info.avatar;
        baseInfo.role = info.role;
        baseInfo.experience = info.exp;
        baseInfo.credits = (int) info.credits;
        baseInfo.level = info.level;
        baseInfo.identityTitle = info.identityTitle;
        baseInfo.identityColor = info.identityColor;
        baseInfo.levelColor = info.levelColor;
        baseInfo.medalList = info.medalList;
        return baseInfo;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.userID);
        dest.writeInt(this.age);
        dest.writeString(this.avatar);
        dest.writeString(this.avatarFid);
        dest.writeLong(this.birthday);
        dest.writeInt(this.gender);
        dest.writeString(this.nick);
        dest.writeInt(this.role);
        dest.writeInt(this.state);
        dest.writeLong(this.credits);
        dest.writeLong(this.integral);
        dest.writeInt(this.level);
        dest.writeInt(this.exp);
        dest.writeInt(this.nextExp);
        dest.writeString(this.identityTitle);
        dest.writeLong(this.identityColor);
        dest.writeLong(this.levelColor);
        dest.writeLong(this.followingCount);
        dest.writeLong(this.followerCount);
        dest.writeString(this.followingCountFormated);
        dest.writeString(this.followerCountFormated);
        dest.writeString(this.contact);
        dest.writeTypedList(this.photos);
        dest.writeTypedList(this.medalList);
        dest.writeLong(this.postCount);
        dest.writeLong(this.commentCount);
        dest.writeLong(this.favoriteCount);
        dest.writeString(this.integralNick);
        dest.writeInt(this.isvideo);
        dest.writeInt(this.model);
        dest.writeParcelable(this.space, flags);
    }

    protected ProfileInfo(Parcel in) {
        super(in);
        this.userID = in.readLong();
        this.age = in.readInt();
        this.avatar = in.readString();
        this.avatarFid = in.readString();
        this.birthday = in.readLong();
        this.gender = in.readInt();
        this.nick = in.readString();
        this.role = in.readInt();
        this.state = in.readInt();
        this.credits = in.readLong();
        this.integral = in.readLong();
        this.level = in.readInt();
        this.exp = in.readInt();
        this.nextExp = in.readInt();
        this.identityTitle = in.readString();
        this.identityColor = in.readLong();
        this.levelColor = in.readLong();
        this.followingCount = in.readLong();
        this.followerCount = in.readLong();
        this.followingCountFormated = in.readString();
        this.followerCountFormated = in.readString();
        this.contact = in.readString();
        this.photos = in.createTypedArrayList(PhotoInfo.CREATOR);
        this.medalList = in.createTypedArrayList(Medal.CREATOR);
        this.postCount = in.readLong();
        this.commentCount = in.readLong();
        this.favoriteCount = in.readLong();
        this.integralNick = in.readString();
        this.isvideo = in.readInt();
        this.model = in.readInt();
        this.space = (h) in.readParcelable(h.class.getClassLoader());
    }
}
