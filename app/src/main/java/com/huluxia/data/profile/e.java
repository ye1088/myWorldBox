package com.huluxia.data.profile;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: StudioUserList */
public class e extends b {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return K(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return au(i);
        }

        public e K(Parcel source) {
            return new e(source);
        }

        public e[] au(int size) {
            return new e[size];
        }
    };
    public List<a> studioUserList = new ArrayList();

    /* compiled from: StudioUserList */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return L(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return av(i);
            }

            public a L(Parcel source) {
                return new a(source);
            }

            public a[] av(int size) {
                return new a[size];
            }
        };
        public int isStudio;
        public int sid;
        public UserBaseInfo user;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.sid);
            dest.writeParcelable(this.user, flags);
            dest.writeInt(this.isStudio);
        }

        protected a(Parcel in) {
            this.sid = in.readInt();
            this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
            this.isStudio = in.readInt();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.studioUserList);
    }

    protected e(Parcel in) {
        super(in);
        this.studioUserList = in.createTypedArrayList(a.CREATOR);
    }
}
