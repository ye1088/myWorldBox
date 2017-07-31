package com.huluxia.data.map;

import com.huluxia.module.b;
import java.io.Serializable;
import java.util.ArrayList;

public class MapProfileInfo extends b implements Serializable {
    public ArrayList<MapProfileItem> mapList = new ArrayList();

    public static class MapProfileItem implements Serializable {
        public String author;
        public String cateName;
        public long createTime;
        public long downCount;
        public String downUrl;
        public String downUrl1;
        public String homeImage;
        public String icon;
        public int id;
        public String instruct;
        public int isRecommend;
        public String language;
        public String mapDesc;
        public long mapSize;
        public String md5;
        public String name;
        public String pageName;
        public long postID;
        public ArrayList<String> resourceList;
        public String source;
        public int status;
        public String version;
    }
}
