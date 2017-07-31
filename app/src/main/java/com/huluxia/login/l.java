package com.huluxia.login;

import com.huluxia.module.ab;
import java.util.Locale;

/* compiled from: UriProvider */
public class l {
    public static boolean DEBUG = false;
    private static String HOST = (DEBUG ? "http://test.bbs.huluxia.net" : ab.axS);
    public static final String Nu = String.format(Locale.getDefault(), "%s/account/register", new Object[]{HOST});
    public static final String Nv = String.format(Locale.getDefault(), "%s/account/login", new Object[]{HOST});
    public static final String Nw = String.format(Locale.getDefault(), "%s/user/exit", new Object[]{HOST});
}
