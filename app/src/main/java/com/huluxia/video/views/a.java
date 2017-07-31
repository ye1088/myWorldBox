package com.huluxia.video.views;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.support.annotation.ab;
import android.support.annotation.y;
import android.support.annotation.z;
import com.huluxia.video.views.scalable.ScalableType;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

/* compiled from: IVideoView */
public interface a {

    /* compiled from: IVideoView */
    public interface a {
        void HU();
    }

    boolean Nw();

    void a(@z OnPreparedListener onPreparedListener) throws IOException, IllegalStateException;

    void b(@z OnPreparedListener onPreparedListener) throws IllegalStateException;

    int getCurrentPosition();

    int getDuration();

    int getVideoHeight();

    int getVideoWidth();

    boolean isLooping();

    boolean isPlaying();

    void pause();

    void prepare() throws IOException, IllegalStateException;

    void prepareAsync() throws IllegalStateException;

    void release();

    void seekTo(int i);

    void setAssetData(@y String str) throws IOException;

    void setCompletionListener(OnCompletionListener onCompletionListener);

    void setDataSource(@y Context context, @y Uri uri) throws IOException;

    void setDataSource(@y Context context, @y Uri uri, @z Map<String, String> map) throws IOException;

    void setDataSource(@y AssetFileDescriptor assetFileDescriptor) throws IOException;

    void setDataSource(@y FileDescriptor fileDescriptor) throws IOException;

    void setDataSource(@y FileDescriptor fileDescriptor, long j, long j2) throws IOException;

    void setDataSource(@y String str) throws IOException;

    void setLooping(boolean z);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setRawData(@ab int i) throws IOException;

    void setScalableType(ScalableType scalableType);

    void setSurfaceTextureUpdatedListener(a aVar);

    void setVolume(float f, float f2);

    void start();

    void stop();
}
