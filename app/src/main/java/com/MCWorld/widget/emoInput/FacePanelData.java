package com.MCWorld.widget.emoInput;

import com.MCWorld.HTApplication;
import com.MCWorld.widget.emoInput.FaceItem.FACE_TYPE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FacePanelData extends ArrayList<FaceItem[]> {
    private static FacePanelData facePanelData = null;
    private static final long serialVersionUID = 1;

    public static FacePanelData getInstance() {
        if (facePanelData == null) {
            facePanelData = new FacePanelData();
        }
        return facePanelData;
    }

    private FacePanelData() {
        initDefault();
    }

    private void initDefault() {
        List<String> tagList = d.Ou().getTags();
        int size = tagList == null ? 0 : tagList.size();
        FaceItem[] data = new FaceItem[tagList.size()];
        for (int i = 0; i < size; i++) {
            data[i] = new FaceItem();
            data[i].bym = FACE_TYPE.TYPE_EMOJI;
            String code = (String) tagList.get(i);
            data[i].byn = d.Ou().gO(code);
            data[i].text = code;
        }
        add(data);
    }

    private void initPaopaobing() {
        try {
            InputStream fileStream = HTApplication.getAppContext().getResources().getAssets().open("emotion_paopaobing.json");
            String fileString = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                fileString = fileString + line;
            }
            fileStream.close();
            FaceItem[] data = parserJson(fileString);
            if (data != null) {
                add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FaceItem[] parserJson(String str) {
        FaceItem[] data = null;
        try {
            JSONObject json = (JSONObject) new JSONTokener(str).nextValue();
            data = new FaceItem[json.getInt("count")];
            JSONArray jsonArr = json.getJSONArray("emotions");
            for (int i = 0; i < jsonArr.length(); i++) {
                data[i] = new FaceItem();
                JSONObject item = jsonArr.getJSONObject(i);
                data[i].bym = FACE_TYPE.TYPE_GIF;
                data[i].name = item.optString("name");
                data[i].byo = item.optString("thumb");
                data[i].byp = item.optString("gif");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
