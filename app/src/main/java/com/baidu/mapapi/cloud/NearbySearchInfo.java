package com.baidu.mapapi.cloud;

import android.content.ContentValues;
import com.baidu.mapapi.GeoPoint;
import io.netty.util.internal.StringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

public class NearbySearchInfo {
    public String ak;
    public ContentValues filter = new ContentValues();
    public GeoPoint location;
    public int pageIndex = 0;
    public int pageSize = 10;
    public String queryWords;
    public int radius = 1000;
    public int scope = 1;
    public String sn;
    public int timeStamp = 0;

    String a() {
        if (this.queryWords == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("?");
        if (this.queryWords != null) {
            try {
                stringBuilder.append("q=").append(URLEncoder.encode(this.queryWords, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (this.location != null) {
            stringBuilder.append("&location=").append(((double) this.location.getLatitudeE6()) / 1000000.0d).append(StringUtil.COMMA).append(((double) this.location.getLongitudeE6()) / 1000000.0d);
        }
        stringBuilder.append("&radius=").append(this.radius);
        if (this.filter.size() > 0) {
            stringBuilder.append("&filter=");
            Iterator it = this.filter.valueSet().iterator();
            if (it.hasNext()) {
                Entry entry = (Entry) it.next();
                String str = (String) entry.getKey();
                stringBuilder.append(str).append(':').append(entry.getValue());
                while (it.hasNext()) {
                    entry = (Entry) it.next();
                    str = (String) entry.getKey();
                    stringBuilder.append('|').append(str).append(':').append(entry.getValue());
                }
            }
        }
        stringBuilder.append("&page_index=").append(this.pageIndex);
        stringBuilder.append("&scope=").append(this.scope);
        stringBuilder.append("&page_size=").append(this.pageSize);
        if (this.ak != null) {
            stringBuilder.append("&ak=").append(this.ak);
        }
        if (this.sn != null) {
            stringBuilder.append("&sn=").append(this.sn);
            stringBuilder.append("&timestamp=").append(this.timeStamp);
        }
        return stringBuilder.toString();
    }
}
