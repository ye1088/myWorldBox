package com.MCWorld.video.views;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.support.annotation.ab;
import android.support.annotation.y;
import android.support.annotation.z;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.video.b.j;
import com.MCWorld.video.views.a.a;
import com.MCWorld.video.views.scalable.ScalableType;
import com.MCWorld.video.views.scalable.ScalableVideoView;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

public class VideoView extends FrameLayout implements a {
    private a brw;

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null && context.obtainStyledAttributes(attrs, j.scaleStyle, 0, 0) != null) {
            if (UtilsVersion.hasICS()) {
                this.brw = new ScalableVideoView(context, attrs, defStyleAttr);
                addView((ScalableVideoView) this.brw, new LayoutParams(-1, -1));
                ((ScalableVideoView) this.brw).setFocusable(true);
            } else {
                this.brw = new ResizeVideoView(context, attrs, defStyleAttr);
                addView((ResizeVideoView) this.brw, new FrameLayout.LayoutParams(-1, -1, 1));
            }
            setDescendantFocusability(262144);
        }
    }

    public void setRawData(@ab int id) throws IOException {
        this.brw.setRawData(id);
    }

    public void setAssetData(@y String assetName) throws IOException {
        this.brw.setAssetData(assetName);
    }

    public void setDataSource(@y AssetFileDescriptor afd) throws IOException {
        this.brw.setDataSource(afd);
    }

    public void setDataSource(@y String path) throws IOException {
        this.brw.setDataSource(path);
    }

    public void setDataSource(@y Context context, @y Uri uri, @z Map<String, String> headers) throws IOException {
        this.brw.setDataSource(context, uri, (Map) headers);
    }

    public void setDataSource(@y Context context, @y Uri uri) throws IOException {
        this.brw.setDataSource(context, uri);
    }

    public void setDataSource(@y FileDescriptor fd, long offset, long length) throws IOException {
        this.brw.setDataSource(fd, offset, length);
    }

    public void setDataSource(@y FileDescriptor fd) throws IOException {
        this.brw.setDataSource(fd);
    }

    public void setScalableType(ScalableType scalableType) {
        this.brw.setScalableType(scalableType);
    }

    public void a(@z OnPreparedListener listener) throws IOException, IllegalStateException {
        this.brw.a(listener);
    }

    public void b(@z OnPreparedListener listener) throws IllegalStateException {
        this.brw.b(listener);
    }

    public void prepare() throws IOException, IllegalStateException {
        this.brw.prepare();
    }

    public void prepareAsync() throws IllegalStateException {
        this.brw.prepareAsync();
    }

    public int getCurrentPosition() {
        return this.brw.getCurrentPosition();
    }

    public int getDuration() {
        return this.brw.getDuration();
    }

    public int getVideoHeight() {
        return this.brw.getVideoHeight();
    }

    public int getVideoWidth() {
        return this.brw.getVideoWidth();
    }

    public boolean isLooping() {
        return this.brw.isLooping();
    }

    public boolean isPlaying() {
        return this.brw.isPlaying();
    }

    public void pause() {
        this.brw.pause();
    }

    public void seekTo(int msec) {
        this.brw.seekTo(msec);
    }

    public void setLooping(boolean looping) {
        this.brw.setLooping(looping);
    }

    public void setCompletionListener(OnCompletionListener completionListener) {
        this.brw.setCompletionListener(completionListener);
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.brw.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    public void setVolume(float leftVolume, float rightVolume) {
        this.brw.setVolume(leftVolume, rightVolume);
    }

    public void start() {
        this.brw.start();
    }

    public void stop() {
        this.brw.stop();
    }

    public void release() {
        this.brw.release();
    }

    public boolean Nw() {
        return this.brw.Nw();
    }

    public void setSurfaceTextureUpdatedListener(a surfaceTextureUpdatedListener) {
        this.brw.setSurfaceTextureUpdatedListener(surfaceTextureUpdatedListener);
    }
}
