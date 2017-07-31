package com.tencent.smtt.export.external.extension.interfaces;

import java.util.Map;

public interface IX5WebSettingsExtension {
    String GetApplicationLocale();

    boolean getPageSolarEnableFlag();

    boolean getWebTranslation();

    boolean isFitScreen();

    boolean isReadModeWebView();

    boolean isWapSitePreferred();

    boolean isWebViewInBackground();

    void setAcceptCookie(boolean z);

    void setAdditionalHttpHeaders(Map<String, String> map);

    void setContentCacheEnable(boolean z);

    void setDayOrNight(boolean z);

    void setEnableUnderLine(boolean z);

    void setFitScreen(boolean z);

    void setImgAsDownloadFile(boolean z);

    void setOnContextMenuEnable(boolean z);

    void setOnlyDomTreeBuild(boolean z);

    void setPageCacheCapacity(int i);

    void setPageSolarEnableFlag(boolean z);

    void setPreFectch(boolean z);

    void setPreFectchEnableWhenHasMedia(boolean z);

    void setReadModeWebView(boolean z);

    void setRecordRequestEnabled(boolean z);

    void setRememberScaleValue(boolean z);

    void setShouldTrackVisitedLinks(boolean z);

    void setWapSitePreferred(boolean z);

    void setWebTranslationEnabled(boolean z);

    void setWebViewInBackground(boolean z);
}
