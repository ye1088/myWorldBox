package com.MCWorld.mcjavascript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DTAtlasMeta */
public class b {
    public List<a> ajt = new ArrayList();
    public JSONArray aju;
    public boolean[] ajv;
    public int ajw;
    public int height;
    public Map<String, JSONObject> nameMap = new HashMap();
    public int width;

    public b(JSONArray paramJSONArray) {
        this.aju = paramJSONArray;
        try {
            Ae();
        } catch (JSONException localJSONException) {
            throw new RuntimeException(localJSONException);
        }
    }

    private void Ac() throws JSONException {
        for (int i = 0; i < this.aju.length(); i++) {
            JSONObject localJSONObject = this.aju.getJSONObject(i);
            this.nameMap.put(localJSONObject.getString("name"), localJSONObject);
            JSONArray localJSONArray1 = localJSONObject.getJSONArray("uvs");
            for (int j = 0; j < localJSONArray1.length(); j++) {
                JSONArray localJSONArray2 = localJSONArray1.getJSONArray(j);
                this.ajv[b(localJSONArray2.getDouble(0), localJSONArray2.getDouble(1))] = true;
            }
        }
    }

    public void Ad() throws JSONException {
        for (int i = 0; i < this.aju.length(); i++) {
            JSONObject localJSONObject = this.aju.getJSONObject(i);
            this.nameMap.put(localJSONObject.getString("name"), localJSONObject);
            JSONArray localJSONArray1 = localJSONObject.getJSONArray("uvs");
            for (int j = 0; j < localJSONArray1.length(); j++) {
                JSONArray localJSONArray2 = localJSONArray1.getJSONArray(j);
                this.ajv[b(localJSONArray2.getDouble(0), localJSONArray2.getDouble(1))] = true;
            }
        }
    }

    private void Ae() throws JSONException {
        JSONArray localJSONArray = this.aju.getJSONObject(0).getJSONArray("uvs").getJSONArray(0);
        this.width = localJSONArray.getInt(4);
        this.height = localJSONArray.getInt(5);
        this.ajw = (int) (0.5d + ((localJSONArray.getDouble(2) - localJSONArray.getDouble(0)) * ((double) this.width)));
        if (isPowerOfTwo(this.ajw)) {
            this.ajv = new boolean[((this.width / this.ajw) * (this.height / this.ajw))];
            Ac();
            return;
        }
        throw new RuntimeException("Non power of two value in icon width: " + this.ajw);
    }

    private JSONArray hy(int paramInt) {
        int i = paramInt % (this.width / this.ajw);
        int j = paramInt / (this.width / this.ajw);
        try {
            return new JSONArray().put((double) ((this.ajw * i) / this.width)).put((double) ((this.ajw * j) / this.height)).put((double) (((i + 1) * this.ajw) / this.width)).put((double) (((j + 1) * this.ajw) / this.height)).put(this.width).put(this.height);
        } catch (JSONException localJSONException) {
            throw new RuntimeException(localJSONException);
        }
    }

    private static boolean isPowerOfTwo(int paramInt) {
        return ((paramInt + -1) & paramInt) == 0;
    }

    private int b(double paramDouble1, double paramDouble2) {
        return ((int) (((((double) this.width) * paramDouble1) / ((double) this.ajw)) + 0.5d)) + (((int) (((((double) this.height) * paramDouble2) / ((double) this.ajw)) + 0.5d)) * (this.width / this.ajw));
    }

    public void a(a icon) {
        int emptyIndex = 0;
        while (emptyIndex < this.ajv.length && this.ajv[emptyIndex]) {
            emptyIndex++;
        }
        if (emptyIndex >= this.ajv.length) {
            throw new RuntimeException("No more space in the atlas! :(");
        }
        JSONArray uv = hy(emptyIndex);
        this.ajt.add(icon);
    }

    public boolean A(String paramString, int paramInt) {
        try {
            JSONObject localJSONObject = (JSONObject) this.nameMap.get(paramString);
            if (localJSONObject != null && paramInt < localJSONObject.getJSONArray("uvs").length()) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    public JSONArray B(String paramString, int paramInt) throws JSONException {
        JSONObject localJSONObject = (JSONObject) this.nameMap.get(paramString);
        if (localJSONObject == null) {
            localJSONObject = new JSONObject().put("name", paramString).put("uvs", new JSONArray());
            this.nameMap.put(paramString, localJSONObject);
            this.aju.put(localJSONObject);
        }
        JSONArray localJSONArray1 = localJSONObject.getJSONArray("uvs");
        if (!localJSONArray1.isNull(paramInt)) {
            return localJSONArray1.getJSONArray(paramInt);
        }
        int i = 0;
        while (i < this.ajv.length && this.ajv[i]) {
            i++;
        }
        if (i < this.ajv.length) {
            JSONArray localJSONArray2 = hy(i);
            localJSONArray1.put(paramInt, localJSONArray2);
            this.ajv[i] = true;
            return localJSONArray2;
        }
        throw new RuntimeException("No more space in texture atlas; can't add " + paramString + "_" + paramInt + " :(");
    }
}
