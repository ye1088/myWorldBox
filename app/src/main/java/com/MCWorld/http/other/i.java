package com.MCWorld.http.other;

import com.MCWorld.data.HTUploadInfo;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.http.base.a;
import com.MCWorld.http.base.d;
import com.MCWorld.http.base.manager.b;
import java.io.File;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UploadVideoRequest */
public class i extends a {
    private String filename = "";
    private int index;

    public String eU() {
        return String.format("%s/upload/video", new Object[]{a.rU});
    }

    public void a(d response, JSONObject json) throws JSONException {
        if (json.getInt("status") == 1) {
            response.setData(new HTUploadInfo(json));
        }
    }

    public void g(List<NameValuePair> list) {
    }

    public void eY() {
        try {
            FileBody bin = new FileBody(new File(this.filename));
            StringBody key = new StringBody("key_10");
            String url = eU();
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", bin);
            reqEntity.addPart("_key", key);
            fa();
            b uploadRequest = new b(1, aE(url), reqEntity, this, this);
            uploadRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, 1.0f));
            com.MCWorld.http.base.manager.a.fk().a(uploadRequest);
            reqEntity.consumeContent();
        } catch (Exception e) {
            fb();
            e.printStackTrace();
        }
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
