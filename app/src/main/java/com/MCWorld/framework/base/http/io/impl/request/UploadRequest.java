package com.MCWorld.framework.base.http.io.impl.request;

import com.MCWorld.framework.base.http.io.NetworkResponse;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.io.Response;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.toolbox.HttpHeaderParser;
import com.MCWorld.framework.base.http.toolbox.HttpLog;
import com.MCWorld.framework.base.http.toolbox.entity.ContentType;
import com.MCWorld.framework.base.http.toolbox.entity.mime.HttpMultipartMode;
import com.MCWorld.framework.base.http.toolbox.entity.mime.MultipartEntityBuilder;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.ContentBody;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.FileBody;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.StringBody;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import com.MCWorld.framework.base.utils.UtilsFunction;
import io.netty.handler.codec.http.HttpHeaders.Values;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UploadRequest extends Request<String> {
    private String boundary;
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    private ContentType contentType;
    private Map<String, FileBody> fileBodyParams = new HashMap();
    private ProgressListener fileBodyProgress = new ProgressListener() {
        public void onProgress(String url, long length, long progress, float speed) {
            UploadRequest.this.progressLength = UploadRequest.this.progressLength + progress;
            if (UploadRequest.this.mProgressListener != null) {
                UploadRequest.this.mProgressListener.onProgress(url, UploadRequest.this.uploadTotalLength, UploadRequest.this.progressLength, speed);
            }
        }
    };
    private CancelListener mCancelListener;
    private ErrorListener mErrorListener;
    private ProgressListener mProgressListener;
    private Listener<String> mSuccListener;
    Map<String, String> postParam = new HashMap();
    private long progressLength = 0;
    private Map<String, StringBody> stringBodyParams = new HashMap();
    private long uploadTotalLength = 0;

    public UploadRequest(String url, Listener<String> succListener, ErrorListener errorListener, ProgressListener progressListener, CancelListener cancelListener) {
        super(1, url, errorListener);
        setShouldCache(false);
        this.contentType = ContentType.create("application/octet-stream", Charset.forName("UTF-8"));
        this.builder.seContentType(this.contentType);
        this.builder.setCharset(Charset.forName("UTF-8"));
        this.mSuccListener = succListener;
        this.mProgressListener = progressListener;
        this.mErrorListener = errorListener;
        this.mCancelListener = cancelListener;
    }

    public Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    public void deliverResponse(String response) {
        if (this.mSuccListener != null) {
            this.mSuccListener.onResponse(response);
        }
    }

    public int compareTo(Request<String> other) {
        return super.compareTo(other);
    }

    public HttpEntity getPostBody() throws AuthFailureError {
        return getBody();
    }

    public HttpEntity getBody() throws AuthFailureError {
        this.builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (Entry<String, StringBody> entry : this.stringBodyParams.entrySet()) {
            this.builder.addPart((String) entry.getKey(), (ContentBody) entry.getValue());
        }
        for (Entry<String, FileBody> entry2 : this.fileBodyParams.entrySet()) {
            FileBody fileBody = (FileBody) entry2.getValue();
            this.builder.addPart((String) entry2.getKey(), fileBody);
            this.uploadTotalLength += fileBody.getFile().length();
        }
        this.boundary = MultipartEntityBuilder.generateBoundary();
        this.builder.setBoundary(this.boundary);
        return this.builder.build();
    }

    public ProgressListener getFileBodyProgress() {
        return this.fileBodyProgress;
    }

    public CancelListener getCancelListener() {
        return this.mCancelListener;
    }

    public void putFileBody(String name, FileBody fileBody) {
        if (name == null || name.length() <= 0) {
            HttpLog.e("put file body name is invalid", new Object[0]);
        } else if (fileBody != null) {
            this.fileBodyParams.put(name, fileBody);
        }
    }

    public void putStringBody(String name, StringBody stringBody) {
        if (name == null || name.length() <= 0) {
            HttpLog.e("put string body name is invalid", new Object[0]);
        } else if (stringBody != null) {
            this.stringBodyParams.put(name, stringBody);
        }
    }

    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    public String getBodyContentType() {
        NameValuePair boundaryValue = new BasicNameValuePair(Values.BOUNDARY, this.boundary);
        return ContentType.create(Values.MULTIPART_FORM_DATA, boundaryValue).toString();
    }

    public void setPostParam(Map<String, String> postParam) {
        if (!UtilsFunction.empty(postParam)) {
            for (String key : postParam.keySet()) {
                putStringBody(key, new StringBody((String) postParam.get(key), ContentType.MULTIPART_FORM_DATA));
            }
        }
    }
}
