package com.MCWorld.framework;

import android.content.Context;
import com.MCWorld.framework.base.utils.Supplier;

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
