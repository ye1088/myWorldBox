package com.MCWorld.data.home;

import com.MCWorld.data.map.MapSubjectItem;
import com.MCWorld.data.map.f;
import com.MCWorld.framework.base.json.Json;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: HomeResourceInfo */
public class a {
    public f pE;
    public List<MapSubjectItem> pF;
    public f pG;
    public List<MapSubjectItem> pH;
    public f pI;
    public List<MapSubjectItem> pJ;
    public f pK;
    public List<MapSubjectItem> pL;

    public void aA(String response) throws JSONException {
        JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
        JSONObject map = json.getJSONObject("map");
        JSONObject map_recommend = map.getJSONObject("recommend");
        this.pF = MapSubjectItem.parseJsonStr(map.getJSONArray("subject"));
        this.pE = (f) Json.parseJsonObject(map_recommend.toString(), f.class);
        JSONObject js = json.getJSONObject("js");
        JSONObject js_recommend = js.getJSONObject("recommend");
        this.pH = MapSubjectItem.parseJsonStr(js.getJSONArray("subject"));
        this.pG = (f) Json.parseJsonObject(js_recommend.toString(), f.class);
        JSONObject skin = json.getJSONObject("skin");
        JSONObject skin_recommend = skin.getJSONObject("recommend");
        this.pJ = MapSubjectItem.parseJsonStr(skin.getJSONArray("subject"));
        this.pI = (f) Json.parseJsonObject(skin_recommend.toString(), f.class);
        JSONObject wood = json.getJSONObject("wood");
        JSONObject wood_recommend = wood.getJSONObject("recommend");
        this.pL = MapSubjectItem.parseJsonStr(wood.getJSONArray("subject"));
        this.pK = (f) Json.parseJsonObject(wood_recommend.toString(), f.class);
    }
}
