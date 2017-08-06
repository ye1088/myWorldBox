package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.b;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class f implements OnCompletionListener, OnErrorListener, OnPreparedListener, h {
    public String a = null;
    Handler b = new Handler();
    Runnable c = null;
    boolean d = true;
    private a e = a.MEDIA_NONE;
    private String f = null;
    private int g = 1000;
    private MediaPlayer h = null;
    private int i = 0;
    private NativeToJsMessageQueue j;

    public enum a {
        MEDIA_NONE,
        MEDIA_STARTING,
        MEDIA_RUNNING,
        MEDIA_PAUSED,
        MEDIA_STOPPED
    }

    public f(Context context) {
        e.e("PushSelfShowLog", "init AudioPlayer");
    }

    private void a(a aVar) {
        this.e = aVar;
    }

    private boolean j() {
        Throwable th;
        int ordinal = this.e.ordinal();
        if (ordinal != a.MEDIA_NONE.ordinal()) {
            return ordinal != a.MEDIA_STARTING.ordinal();
        } else {
            if (this.h == null) {
                this.h = new MediaPlayer();
                this.h.setOnErrorListener(this);
                this.h.setOnPreparedListener(this);
                this.h.setOnCompletionListener(this);
            }
            FileInputStream fileInputStream = null;
            try {
                if (b.a(this.f)) {
                    this.h.setDataSource(this.f);
                    this.h.setAudioStreamType(3);
                    a(a.MEDIA_STARTING);
                    this.h.prepareAsync();
                } else {
                    File file = new File(this.f);
                    if (file.exists()) {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        try {
                            this.h.setDataSource(fileInputStream2.getFD());
                            a(a.MEDIA_STARTING);
                            this.h.prepare();
                            fileInputStream = fileInputStream2;
                        } catch (RuntimeException e) {
                            fileInputStream = fileInputStream2;
                            try {
                                e.e("PushSelfShowLog", "prepareAsync/prepare error");
                                a(a.MEDIA_NONE);
                                if (fileInputStream != null) {
                                    return false;
                                }
                                try {
                                    fileInputStream.close();
                                    return false;
                                } catch (Exception e2) {
                                    e.e("PushSelfShowLog", "close fileInputStream error");
                                    return false;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception e3) {
                                        e.e("PushSelfShowLog", "close fileInputStream error");
                                    }
                                }
                                throw th;
                            }
                        } catch (FileNotFoundException e4) {
                            fileInputStream = fileInputStream2;
                            e.e("PushSelfShowLog", "prepareAsync/prepare error");
                            a(a.MEDIA_NONE);
                            if (fileInputStream != null) {
                                return false;
                            }
                            try {
                                fileInputStream.close();
                                return false;
                            } catch (Exception e5) {
                                e.e("PushSelfShowLog", "close fileInputStream error");
                                return false;
                            }
                        } catch (IOException e6) {
                            fileInputStream = fileInputStream2;
                            e.e("PushSelfShowLog", "prepareAsync/prepare error");
                            a(a.MEDIA_NONE);
                            if (fileInputStream != null) {
                                return false;
                            }
                            try {
                                fileInputStream.close();
                                return false;
                            } catch (Exception e7) {
                                e.e("PushSelfShowLog", "close fileInputStream error");
                                return false;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            fileInputStream = fileInputStream2;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw th;
                        }
                    }
                }
                if (fileInputStream == null) {
                    return false;
                }
                try {
                    fileInputStream.close();
                    return false;
                } catch (Exception e8) {
                    e.e("PushSelfShowLog", "close fileInputStream error");
                    return false;
                }
            } catch (RuntimeException e9) {
                e.e("PushSelfShowLog", "prepareAsync/prepare error");
                a(a.MEDIA_NONE);
                if (fileInputStream != null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            } catch (FileNotFoundException e10) {
                e.e("PushSelfShowLog", "prepareAsync/prepare error");
                a(a.MEDIA_NONE);
                if (fileInputStream != null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            } catch (IOException e11) {
                e.e("PushSelfShowLog", "prepareAsync/prepare error");
                a(a.MEDIA_NONE);
                if (fileInputStream != null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            }
        }
    }

    private float k() {
        try {
            return ((float) this.h.getDuration()) / 1000.0f;
        } catch (Exception e) {
            e.e("PushSelfShowLog", "getDurationInSeconds error ");
            return -1.0f;
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a() {
        if (j() && this.h != null) {
            h();
        }
    }

    public void a(int i) {
        try {
            if (j()) {
                this.h.seekTo(i);
                e.a("PushSelfShowLog", "Send a_isRightVersion onStatus update for the new seek");
                return;
            }
            this.i = i;
        } catch (IllegalStateException e) {
            e.a("PushSelfShowLog", "seekToPlaying failed");
        } catch (Exception e2) {
            e.a("PushSelfShowLog", "seekToPlaying failed");
        }
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            e.a("PushSelfShowLog", "jsMessageQueue is null while run into Audio Player exec");
            return;
        }
        this.j = nativeToJsMessageQueue;
        if ("preparePlaying".equals(str)) {
            d();
            if (str2 != null) {
                this.a = str2;
                a(jSONObject);
                return;
            }
            e.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if ("startPlaying".equals(str)) {
            a();
        } else if ("seekToPlaying".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("milliseconds")) {
                        a(jSONObject.getInt("milliseconds"));
                    }
                } catch (JSONException e) {
                    e.a("PushSelfShowLog", "seekto error");
                }
            }
        } else if ("pausePlaying".equals(str)) {
            e();
        } else if ("stopPlaying".equals(str)) {
            f();
        } else if ("getPlayingStatus".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("frequently")) {
                        int i = jSONObject.getInt("frequently");
                        if (i > this.g) {
                            this.g = i;
                        }
                    }
                } catch (JSONException e2) {
                    e.a("PushSelfShowLog", "seekto error");
                }
            }
            e.e("PushSelfShowLog", "this.frequently is " + this.g);
            g();
        } else {
            nativeToJsMessageQueue.a(str2, com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
        }
    }

    public void a(JSONObject jSONObject) {
        e.e("PushSelfShowLog", " run into Audio player createAudio");
        if (jSONObject == null || !jSONObject.has("url")) {
            this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
        } else {
            try {
                String string = jSONObject.getString("url");
                String a = b.a(this.j.a(), string);
                if (a == null || a.length() <= 0) {
                    e.e("PushSelfShowLog", string + "File not exist");
                    this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_ONLY_SUPPORT_HTTP, DownloadRecord.COLUMN_ERROR, null);
                } else {
                    this.f = a;
                    this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", null);
                }
                if (jSONObject.has("pauseOnActivityPause")) {
                    this.d = jSONObject.getBoolean("pauseOnActivityPause");
                }
            } catch (Throwable e) {
                e.d("PushSelfShowLog", "startPlaying failed ", e);
                this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, DownloadRecord.COLUMN_ERROR, null);
            }
        }
        e.e("PushSelfShowLog", " this.audioFile = " + this.f);
    }

    public void b() {
        e.e("PushSelfShowLog", "Audio onResume");
    }

    public void c() {
        e.b("PushSelfShowLog", "Audio onPause and pauseOnActivityPause is %s  this.player is %s", new Object[]{Boolean.valueOf(this.d), this.h});
        d();
    }

    public void d() {
        e.e("PushSelfShowLog", "Audio reset/Destory");
        try {
            this.d = true;
            if (this.h != null) {
                if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
                    this.h.stop();
                }
                this.h.release();
                this.h = null;
            }
            this.f = null;
            a(a.MEDIA_NONE);
            this.g = 1000;
            this.i = 0;
            if (this.c != null) {
                this.b.removeCallbacks(this.c);
            }
            this.c = null;
        } catch (IllegalStateException e) {
            e.a("PushSelfShowLog", "reset music error");
        } catch (Exception e2) {
            e.a("PushSelfShowLog", "reset music error");
        }
    }

    public void e() {
        if (this.e != a.MEDIA_RUNNING || this.h == null) {
            e.a("PushSelfShowLog", "AudioPlayer Error: pausePlaying() called during invalid state: " + this.e.ordinal());
            return;
        }
        this.h.pause();
        a(a.MEDIA_PAUSED);
    }

    public void f() {
        if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
            this.h.pause();
            this.h.seekTo(0);
            e.a("PushSelfShowLog", "stopPlaying is calling stopped");
            a(a.MEDIA_STOPPED);
            return;
        }
        e.a("PushSelfShowLog", "AudioPlayer Error: stopPlaying() called during invalid state: " + this.e.ordinal());
    }

    public void g() {
        e.e("PushSelfShowLog", "getPlayingStatusRb is " + this.c);
        if (this.c == null) {
            this.c = new g(this);
        } else {
            try {
                this.b.removeCallbacks(this.c);
            } catch (Exception e) {
                e.e("PushSelfShowLog", "getPlayingStatus error,handler.removeCallbacks");
            }
        }
        this.b.postDelayed(this.c, (long) this.g);
        e.e("PushSelfShowLog", "handler.postDelayed " + this.g);
    }

    public void h() {
        try {
            this.h.start();
            a(a.MEDIA_RUNNING);
            this.i = 0;
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "play() error ", e);
        }
    }

    public long i() {
        return (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) ? (long) (this.h.getCurrentPosition() / 1000) : -1;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        e.a("PushSelfShowLog", "on completion is calling stopped");
        a(a.MEDIA_STOPPED);
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        e.a("PushSelfShowLog", "AudioPlayer.onError(" + i + ", " + i2 + ")");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.f);
            this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_PLAY_ERROR, DownloadRecord.COLUMN_ERROR, jSONObject);
        } catch (JSONException e) {
            e.e("PushSelfShowLog", "onError error");
        }
        d();
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        a(this.i);
        h();
    }
}
