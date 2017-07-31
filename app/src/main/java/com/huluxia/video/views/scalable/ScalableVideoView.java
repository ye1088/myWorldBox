package com.huluxia.video.views.scalable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.support.annotation.ab;
import android.support.annotation.y;
import android.support.annotation.z;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.huluxia.video.b.j;
import com.huluxia.video.views.StateMediaPlayer;
import com.huluxia.video.views.StateMediaPlayer.State;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

@TargetApi(14)
public class ScalableVideoView extends TextureView implements OnCompletionListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceTextureListener, com.huluxia.video.views.a {
    private static final String TAG = "ScalableVideoView";
    protected StateMediaPlayer bqE;
    protected ScalableType bqF;
    private com.huluxia.video.views.a.a bqG;
    private OnCompletionListener bqH;
    private OnSeekCompleteListener bqI;

    public interface a {
        void HU();
    }

    public ScalableVideoView(Context context) {
        this(context, null);
    }

    public ScalableVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bqF = ScalableType.NONE;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, j.scaleStyle, 0, 0);
            if (a != null) {
                int scaleType = a.getInt(j.scaleStyle_scalableType, ScalableType.NONE.ordinal());
                a.recycle();
                this.bqF = ScalableType.values()[scaleType];
            }
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Surface surface = new Surface(surfaceTexture);
        if (this.bqE != null) {
            this.bqE.setSurface(surface);
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (this.bqG != null) {
            this.bqG.HU();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.bqE != null) {
            if (isPlaying()) {
                stop();
            }
            release();
            this.bqE = null;
        }
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        aH(width, height);
    }

    private void aH(int videoWidth, int videoHeight) {
        if (videoWidth != 0 && videoHeight != 0) {
            Matrix matrix = new a(new b(getWidth(), getHeight()), new b(videoWidth, videoHeight)).a(this.bqF);
            if (matrix != null) {
                setTransform(matrix);
            }
        }
    }

    private void Ny() {
        if (this.bqE == null) {
            this.bqE = new StateMediaPlayer();
            this.bqE.setOnVideoSizeChangedListener(this);
            setSurfaceTextureListener(this);
            this.bqE.setOnCompletionListener(this);
            this.bqE.setOnSeekCompleteListener(this);
            return;
        }
        this.bqE.reset();
    }

    public void setRawData(@ab int id) throws IOException {
        setDataSource(getResources().openRawResourceFd(id));
    }

    public void setAssetData(@y String assetName) throws IOException {
        setDataSource(getContext().getAssets().openFd(assetName));
    }

    public void setDataSource(@y AssetFileDescriptor afd) throws IOException {
        setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        afd.close();
    }

    public void setDataSource(@y String path) throws IOException {
        Ny();
        this.bqE.setDataSource(path);
    }

    public void setDataSource(@y Context context, @y Uri uri, @z Map<String, String> headers) throws IOException {
        Ny();
        this.bqE.setDataSource(context, uri, (Map) headers);
    }

    public void setDataSource(@y Context context, @y Uri uri) throws IOException {
        Ny();
        this.bqE.setDataSource(context, uri);
    }

    public void setDataSource(@y FileDescriptor fd, long offset, long length) throws IOException {
        Ny();
        this.bqE.setDataSource(fd, offset, length);
    }

    public void setDataSource(@y FileDescriptor fd) throws IOException {
        Ny();
        this.bqE.setDataSource(fd);
    }

    public void setScalableType(ScalableType scalableType) {
        this.bqF = scalableType;
        aH(getVideoWidth(), getVideoHeight());
    }

    public void a(@z OnPreparedListener listener) throws IOException, IllegalStateException {
        this.bqE.setOnPreparedListener(listener);
        this.bqE.prepare();
    }

    public void b(@z OnPreparedListener listener) throws IllegalStateException {
        this.bqE.setOnPreparedListener(listener);
        this.bqE.prepareAsync();
    }

    public void prepare() throws IOException, IllegalStateException {
        a(null);
    }

    public void prepareAsync() throws IllegalStateException {
        b(null);
    }

    public int getCurrentPosition() {
        if (this.bqE == null) {
            return 0;
        }
        return this.bqE.getCurrentPosition();
    }

    public int getDuration() {
        if (this.bqE == null) {
            return 0;
        }
        return this.bqE.getDuration();
    }

    public int getVideoHeight() {
        if (this.bqE == null) {
            return 0;
        }
        return this.bqE.getVideoHeight();
    }

    public int getVideoWidth() {
        if (this.bqE == null) {
            return 0;
        }
        return this.bqE.getVideoWidth();
    }

    public boolean isLooping() {
        if (this.bqE == null) {
            return false;
        }
        return this.bqE.isLooping();
    }

    public boolean isPlaying() {
        if (this.bqE == null) {
            return false;
        }
        return this.bqE.isPlaying();
    }

    public void pause() {
        if (this.bqE != null) {
            this.bqE.pause();
        }
    }

    public void seekTo(int msec) {
        if (this.bqE != null) {
            this.bqE.seekTo(msec);
        }
    }

    public void setLooping(boolean looping) {
        if (this.bqE != null) {
            this.bqE.setLooping(looping);
        }
    }

    public void setCompletionListener(OnCompletionListener completionListener) {
        this.bqH = completionListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.bqI = onSeekCompleteListener;
    }

    public void setVolume(float leftVolume, float rightVolume) {
        if (this.bqE != null) {
            this.bqE.setVolume(leftVolume, rightVolume);
        }
    }

    public void start() {
        if (this.bqE != null) {
            this.bqE.start();
        }
    }

    public void stop() {
        if (this.bqE != null) {
            this.bqE.stop();
        }
    }

    public void release() {
        if (this.bqE != null) {
            this.bqE.reset();
            this.bqE.release();
        }
    }

    public boolean Nw() {
        if (this.bqE != null && this.bqE.ND() == State.PAUSED) {
            return true;
        }
        return false;
    }

    public void setSurfaceTextureUpdatedListener(com.huluxia.video.views.a.a surfaceTextureUpdatedListener) {
        this.bqG = surfaceTextureUpdatedListener;
    }

    public void onCompletion(MediaPlayer mp) {
        if (this.bqH != null) {
            this.bqH.onCompletion(mp);
        }
    }

    public void onSeekComplete(MediaPlayer mp) {
        if (this.bqI != null) {
            this.bqI.onSeekComplete(mp);
        }
    }
}
