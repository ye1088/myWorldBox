package com.huluxia.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.tencent.mm.sdk.plugin.BaseProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserBaseInfo implements Parcelable, Serializable {
    public static final Creator<UserBaseInfo> CREATOR = new Creator<UserBaseInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return k(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return T(i);
        }

        public UserBaseInfo k(Parcel source) {
            return new UserBaseInfo(source);
        }

        public UserBaseInfo[] T(int size) {
            return new UserBaseInfo[size];
        }
    };
    private static final long serialVersionUID = 3062779465472967710L;
    public int age;
    public String avatar;
    public int credits;
    public int experience;
    public int gender;
    public long identityColor;
    public String identityTitle;
    public String integralNick;
    public int level;
    public long levelColor;
    public List<Medal> medalList = new ArrayList();
    public String nick;
    public int role;
    public int state;
    public long userID;

    public UserBaseInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.userID = obj.optLong("userID");
            this.nick = obj.optString("nick");
            this.age = obj.optInt("age");
            this.gender = obj.optInt("gender");
            this.avatar = obj.optString(BaseProfile.COL_AVATAR);
            this.role = obj.optInt("role");
            this.experience = obj.optInt("experience");
            this.credits = obj.optInt("credits");
            this.level = obj.optInt("level");
            this.identityTitle = obj.optString("identityTitle");
            this.identityColor = obj.optLong("identityColor");
            this.levelColor = obj.optLong("levelColor");
            this.integralNick = obj.optString("integralNick");
            this.state = obj.optInt(DownloadRecord.COLUMN_STATE);
            JSONArray medalArr = obj.optJSONArray("medalList");
            if (medalArr != null) {
                for (int i = 0; i < medalArr.length(); i++) {
                    this.medalList.add(new Medal(medalArr.optJSONObject(i)));
                }
            }
        }
    }

    public UserBaseInfo(Parcel source) {
        this.userID = source.readLong();
        this.nick = source.readString();
        this.age = source.readInt();
        this.gender = source.readInt();
        this.avatar = source.readString();
        this.role = source.readInt();
        this.experience = source.readInt();
        this.credits = source.readInt();
        this.level = source.readInt();
        this.identityTitle = source.readString();
        this.identityColor = source.readLong();
        this.levelColor = source.readLong();
        this.integralNick = source.readString();
        this.state = source.readInt();
        source.readTypedList(this.medalList, Medal.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userID);
        dest.writeString(this.nick);
        dest.writeInt(this.age);
        dest.writeInt(this.gender);
        dest.writeString(this.avatar);
        dest.writeInt(this.role);
        dest.writeInt(this.experience);
        dest.writeInt(this.credits);
        dest.writeInt(this.level);
        dest.writeString(this.identityTitle);
        dest.writeLong(this.identityColor);
        dest.writeLong(this.levelColor);
        dest.writeString(this.integralNick);
        dest.writeInt(this.state);
        dest.writeTypedList(this.medalList);
    }

    public long getUserID() {
        return this.userID;
    }

    public String getNick() {
        return this.nick;
    }

    public int getAge() {
        return this.age;
    }

    public int getGender() {
        return this.gender;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public int getRole() {
        return this.role;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getLevel() {
        return this.level;
    }

    public String getIdentityTitle() {
        return this.identityTitle;
    }

    public int getIdentityColor() {
        return (int) this.identityColor;
    }

    public long getLevelColor() {
        return this.levelColor;
    }

    public String getIntegralNick() {
        return this.integralNick;
    }

    public List<Medal> getMedalList() {
        return this.medalList;
    }
}
