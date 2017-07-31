package com.huluxia.framework;

import android.content.Context;
import com.huluxia.framework.base.utils.Supplier;

public interface IConfig {
    Supplier<Integer> errorHolder();

    String getAppName();

    Supplier<Integer> getBrightness();

    Context getContext();

    BaseHttpMgr getHttp();

    String getLogDir();

    String getRootDir();

    Supplier<Integer> placeHolder();
}
