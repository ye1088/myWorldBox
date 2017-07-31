package com.tencent.mm.sdk.plugin;

import android.net.Uri;
import com.tencent.mm.sdk.storage.IAutoDBItem.MAutoDBInfo;

public class Profile extends BaseProfile {
    public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/profile");
    protected static MAutoDBInfo bO = BaseProfile.initAutoDBInfo(Profile.class);
    public static String[] columns = new String[]{"username", BaseProfile.COL_BINDQQ, BaseProfile.COL_BINDMOBILE, BaseProfile.COL_BINDEMAIL, BaseProfile.COL_ALIAS, BaseProfile.COL_NICKNAME, BaseProfile.COL_SIGNATURE, BaseProfile.COL_PROVINCE, BaseProfile.COL_CITY, BaseProfile.COL_WEIBO, BaseProfile.COL_AVATAR};

    protected MAutoDBInfo getDBInfo() {
        return bO;
    }
}
