package com.MCWorld.data.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.UserBaseInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.types.selectors.ContainsSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SysMsgContent implements Parcelable, Serializable {
    public static final Creator<SysMsgContent> CREATOR = new Creator<SysMsgContent>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return B(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return al(i);
        }

        public SysMsgContent B(Parcel source) {
            return new SysMsgContent(source);
        }

        public SysMsgContent[] al(int size) {
            return new SysMsgContent[size];
        }
    };
    private static final long serialVersionUID = 755723556646792574L;
    private List<String> images = new ArrayList();
    private String text;
    private UserBaseInfo user = null;

    public SysMsgContent(JSONObject obj) throws JSONException {
        this.text = obj.optString(ContainsSelector.CONTAINS_KEY);
        JSONArray arr = obj.optJSONArray("images");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                this.images.add(arr.optString(i));
            }
        }
        if (!obj.isNull("user")) {
            this.user = new UserBaseInfo(obj.optJSONObject("user"));
        }
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserBaseInfo getUserInfo() {
        return this.user;
    }

    public void setUserInfo(UserBaseInfo userInfo) {
        this.user = userInfo;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeStringList(this.images);
        dest.writeParcelable(this.user, flags);
    }

    protected SysMsgContent(Parcel in) {
        this.text = in.readString();
        this.images = in.createStringArrayList();
        this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
    }
}
