package com.MCWorld.data.profile;

import java.io.Serializable;
import java.util.HashMap;

public class ExchangeRecord implements Serializable {
    public String createTime;
    public HashMap<String, String> ext = new HashMap();
    public String icon;
    public long id;
    public String statusDesc;
}
