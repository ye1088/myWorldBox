package com.huluxia.video.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.support.annotation.y;
import android.support.annotation.z;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsVersion;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

public class StateMediaPlayer {
    private static final String TAG = "StateMediaPlayer";
    private OnSeekCompleteListener bqI;
    private MediaPlayer bqO = new MediaPlayer();
    private State bqP = State.IDLE;
    private OnVideoSizeChangedListener bqQ;
    private OnCompletionListener bqR;
    private OnPreparedListener bqS;
    private OnPreparedListener bqT = new OnPreparedListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public void onPrepared(MediaPlayer mp) {
            HLog.debug(StateMediaPlayer.TAG, "on prepared", new Object[0]);
            this.bra.bqP = State.PREPARED;
            this.bra.onPrepared(mp);
            this.bra.bqO.start();
            this.bra.bqP = State.STARTED;
        }
    };
    private OnCompletionListener bqU = new OnCompletionListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public void onCompletion(MediaPlayer mp) {
            HLog.debug(StateMediaPlayer.TAG, "on completion", new Object[0]);
            this.bra.bqP = State.PLAYBACK_COMPLETE;
            this.bra.onCompletion(mp);
        }
    };
    private OnBufferingUpdateListener bqV = new OnBufferingUpdateListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            HLog.debug(StateMediaPlayer.TAG, "on buffering update", new Object[0]);
            this.bra.onBufferingUpdate(mp, percent);
        }
    };
    private OnErrorListener bqW = new OnErrorListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            HLog.debug(StateMediaPlayer.TAG, "on error", new Object[0]);
            this.bra.bqP = State.ERROR;
            this.bra.onError(mp, what, extra);
            return false;
        }
    };
    private OnInfoListener bqX = new OnInfoListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            HLog.debug(StateMediaPlayer.TAG, "on info", new Object[0]);
            return this.bra.onInfo(mp, what, extra);
        }
    };
    private OnVideoSizeChangedListener bqY = new OnVideoSizeChangedListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            if (this.bra.bqQ != null) {
                this.bra.bqQ.onVideoSizeChanged(mp, width, height);
            }
        }
    };
    private OnSeekCompleteListener bqZ = new OnSeekCompleteListener(this) {
        final /* synthetic */ StateMediaPlayer bra;

        {
            this.bra = this$0;
        }

        public void onSeekComplete(MediaPlayer mp) {
            if (this.bra.bqI != null) {
                this.bra.bqI.onSeekComplete(mp);
            }
        }
    };

    public enum State {
        IDLE,
        ERROR,
        INITIALIZED,
        PREPARING,
        PREPARED,
        STARTED,
        STOPPED,
        PLAYBACK_COMPLETE,
        PAUSED
    }

    public StateMediaPlayer() {
        this.bqO.setOnPreparedListener(this.bqT);
        this.bqO.setOnCompletionListener(this.bqU);
        this.bqO.setOnBufferingUpdateListener(this.bqV);
        this.bqO.setOnErrorListener(this.bqW);
        this.bqO.setOnInfoListener(this.bqX);
        this.bqO.setOnVideoSizeChangedListener(this.bqY);
        this.bqO.setOnSeekCompleteListener(this.bqZ);
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.bqQ = onVideoSizeChangedListener;
    }

    public OnVideoSizeChangedListener NA() {
        return this.bqQ;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.bqR = onCompletionListener;
    }

    public OnCompletionListener NB() {
        return this.bqR;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.bqS = onPreparedListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.bqI = onSeekCompleteListener;
    }

    public OnPreparedListener NC() {
        return this.bqS;
    }

    public State ND() {
        return this.bqP;
    }

    public void setDataSource(String path) {
        if (UtilsFunction.empty(path)) {
            HLog.error(TAG, "state media player set data source is NULL", new Object[0]);
        } else if (this.bqP == State.IDLE || this.bqP == State.INITIALIZED) {
            try {
                this.bqO.setDataSource(path);
                this.bqP = State.INITIALIZED;
            } catch (IllegalArgumentException e) {
                HLog.error(TAG, "set data source " + path + " IllegalArgumentException " + e, new Object[0]);
            } catch (IllegalStateException e2) {
                HLog.error(TAG, "set data source " + path + " IllegalStateException " + e2, new Object[0]);
            } catch (IOException e3) {
                HLog.error(TAG, "set data source " + path + " IOException " + e3, new Object[0]);
            }
        } else {
            HLog.error(TAG, "set data source path state not right " + this.bqP, new Object[0]);
        }
    }

    @TargetApi(14)
    public void setDataSource(@y Context context, @y Uri uri, @z Map<String, String> headers) throws IOException {
        if (!UtilsVersion.hasICS()) {
            setDataSource(context, uri);
        } else if (this.bqP == State.IDLE || this.bqP == State.INITIALIZED) {
            try {
                this.bqO.setDataSource(context, uri, headers);
                this.bqP = State.INITIALIZED;
            } catch (IllegalArgumentException e) {
                HLog.error(TAG, "set data source uri with header " + uri + " IllegalArgumentException " + e, new Object[0]);
            } catch (IllegalStateException e2) {
                HLog.error(TAG, "set data source uri with header " + uri + " IllegalStateException " + e2, new Object[0]);
            } catch (IOException e3) {
                HLog.error(TAG, "set data source uri with header " + uri + " IOException " + e3, new Object[0]);
            }
        } else {
            HLog.error(TAG, "set data source path state not right " + this.bqP, new Object[0]);
        }
    }

    public void setDataSource(@y Context context, @y Uri uri) throws IOException {
        if (this.bqP == State.IDLE || this.bqP == State.INITIALIZED) {
            try {
                this.bqO.setDataSource(context, uri);
                this.bqP = State.INITIALIZED;
                return;
            } catch (IllegalArgumentException e) {
                HLog.error(TAG, "set data source uri no header " + uri + " IllegalArgumentException " + e, new Object[0]);
                return;
            } catch (IllegalStateException e2) {
                HLog.error(TAG, "set data source uri no header " + uri + " IllegalStateException " + e2, new Object[0]);
                return;
            } catch (IOException e3) {
                HLog.error(TAG, "set data source uri no header " + uri + " IOException " + e3, new Object[0]);
                return;
            }
        }
        HLog.error(TAG, "set data source path state not right " + this.bqP, new Object[0]);
    }

    public void setDataSource(@y FileDescriptor fd, long offset, long length) throws IOException {
        if (this.bqP == State.IDLE || this.bqP == State.INITIALIZED) {
            try {
                this.bqO.setDataSource(fd, offset, length);
                this.bqP = State.INITIALIZED;
                return;
            } catch (IllegalArgumentException e) {
                HLog.error(TAG, "set data source fd offset " + fd + " IllegalArgumentException " + e, new Object[0]);
                return;
            } catch (IllegalStateException e2) {
                HLog.error(TAG, "set data source fd offset " + fd + " IllegalStateException " + e2, new Object[0]);
                return;
            } catch (IOException e3) {
                HLog.error(TAG, "set data source fd offset " + fd + " IOException " + e3, new Object[0]);
                return;
            }
        }
        HLog.error(TAG, "set data source path state not right " + this.bqP, new Object[0]);
    }

    public void setDataSource(@y FileDescriptor fd) throws IOException {
        if (this.bqP == State.IDLE || this.bqP == State.INITIALIZED) {
            try {
                this.bqO.setDataSource(fd);
                this.bqP = State.INITIALIZED;
                return;
            } catch (IllegalArgumentException e) {
                HLog.error(TAG, "set data source fd " + fd + " IllegalArgumentException " + e, new Object[0]);
                return;
            } catch (IllegalStateException e2) {
                HLog.error(TAG, "set data source fd " + fd + " IllegalStateException " + e2, new Object[0]);
                return;
            } catch (IOException e3) {
                HLog.error(TAG, "set data source fd " + fd + " IOException " + e3, new Object[0]);
                return;
            }
        }
        HLog.error(TAG, "set data source path state not right " + this.bqP, new Object[0]);
    }

    public void prepare() {
        HLog.debug(TAG, "prepare()", new Object[0]);
        if (EnumSet.of(State.INITIALIZED, State.STOPPED).contains(this.bqP)) {
            try {
                this.bqP = State.PREPARING;
                this.bqO.prepare();
                this.bqP = State.PREPARED;
                return;
            } catch (IOException e) {
                HLog.error(TAG, "prepare error " + e, new Object[0]);
                return;
            }
        }
        HLog.error(TAG, "prepare state not right " + this.bqP, new Object[0]);
    }

    public void prepareAsync() throws IllegalStateException {
        HLog.debug(TAG, "prepareAsync()", new Object[0]);
        if (EnumSet.of(State.INITIALIZED, State.STOPPED).contains(this.bqP)) {
            this.bqO.prepareAsync();
            this.bqP = State.PREPARING;
            return;
        }
        HLog.error(TAG, "prepare state not right " + this.bqP, new Object[0]);
    }

    public boolean isPlaying() {
        if (this.bqP != State.ERROR) {
            return this.bqO.isPlaying();
        }
        HLog.error(TAG, "isPlaying state not right " + this.bqP, new Object[0]);
        return false;
    }

    public void seekTo(int msec) {
        HLog.debug(TAG, "seekTo()", new Object[0]);
        if (EnumSet.of(State.PREPARED, State.STARTED, State.PAUSED, State.PLAYBACK_COMPLETE).contains(this.bqP)) {
            this.bqO.seekTo(msec);
        } else {
            HLog.error(TAG, "seekTo state not right " + this.bqP, new Object[0]);
        }
    }

    public void pause() {
        HLog.debug(TAG, "pause()", new Object[0]);
        if (EnumSet.of(State.STARTED, State.PAUSED).contains(this.bqP)) {
            this.bqO.pause();
            this.bqP = State.PAUSED;
            return;
        }
        HLog.error(TAG, "pause state not right " + this.bqP, new Object[0]);
    }

    public void start() {
        HLog.debug(TAG, "start()", new Object[0]);
        if (EnumSet.of(State.PREPARED, State.STARTED, State.PAUSED, State.PLAYBACK_COMPLETE).contains(this.bqP)) {
            this.bqO.start();
            this.bqP = State.STARTED;
            return;
        }
        HLog.error(TAG, "start state not right " + this.bqP, new Object[0]);
    }

    public void stop() {
        HLog.debug(TAG, "stop()", new Object[0]);
        if (EnumSet.of(State.PREPARED, State.STARTED, State.STOPPED, State.PAUSED, State.PLAYBACK_COMPLETE).contains(this.bqP)) {
            this.bqO.stop();
            this.bqP = State.STOPPED;
            return;
        }
        HLog.error(TAG, "stop state not right " + this.bqP, new Object[0]);
    }

    public void reset() {
        HLog.debug(TAG, "reset()", new Object[0]);
        this.bqO.reset();
        this.bqP = State.IDLE;
    }

    public State NE() {
        HLog.debug(TAG, "getState()", new Object[0]);
        return this.bqP;
    }

    public void release() {
        HLog.debug(TAG, "release()", new Object[0]);
        this.bqO.release();
    }

    public void setLooping(boolean looping) {
        this.bqO.setLooping(looping);
    }

    public int getVideoHeight() {
        return this.bqO.getVideoHeight();
    }

    public int getVideoWidth() {
        return this.bqO.getVideoWidth();
    }

    public boolean isLooping() {
        return this.bqO.isLooping();
    }

    public void setVolume(float leftVolume, float rightVolume) {
        this.bqO.setVolume(leftVolume, rightVolume);
    }

    @TargetApi(14)
    public void setSurface(Surface surface) {
        this.bqO.setSurface(surface);
    }

    public void setDisplay(SurfaceHolder holder) {
        this.bqO.setDisplay(holder);
    }

    public void onPrepared(MediaPlayer mp) {
        if (this.bqS != null) {
            this.bqS.onPrepared(mp);
        }
    }

    public void onCompletion(MediaPlayer mp) {
        if (this.bqR != null) {
            this.bqR.onCompletion(mp);
        }
    }

    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public int getCurrentPosition() {
        if (this.bqP != State.ERROR) {
            return this.bqO.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (EnumSet.of(State.PREPARED, State.STARTED, State.PAUSED, State.STOPPED, State.PLAYBACK_COMPLETE).contains(this.bqP)) {
            return this.bqO.getDuration();
        }
        return 100;
    }
}
