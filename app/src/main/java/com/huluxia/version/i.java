package com.huluxia.version;

import com.huluxia.HTApplication;
import com.huluxia.module.ab;

/* compiled from: VersionUriProvider */
public class i extends ab {
    private static final String boD = "http://test.version.huluxia.com";
    protected static final String boE = (HTApplication.DEBUG ? boD : "http://version.huluxia.net");
    public static final String boF = (boE + "/new/version/ANDROID/1.0");
    public static final String boG = (boE + "/version/count/ANDROID/1.0");
    public static final String boH = "http://version.check.huluxia.com/hlx_mc/config_mc.txt";
}
