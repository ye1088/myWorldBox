package com.huluxia.video.views;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.FrameLayout.LayoutParams;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.video.b.j;
import com.huluxia.video.views.StateMediaPlayer.State;
import com.huluxia.video.views.a.a;
import com.huluxia.video.views.scalable.ScalableType;
import com.huluxia.video.views.scalable.b;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

public class ResizeVideoView extends SurfaceView implements OnCompletionListener, OnSeekCompleteListener, OnVideoSizeChangedListener, Callback, a {
    private static final String TAG = "VideoView";
    private final Object bqC;
    private SurfaceHolder bqD;
    protected StateMediaPlayer bqE;
    protected ScalableType bqF;
    private a bqG;
    private OnCompletionListener bqH;
    private OnSeekCompleteListener bqI;
    private boolean bqJ;
    private Thread bqK;
    private Runnable bqL;
    private String mPath;

    public ResizeVideoView(Context context) {
        this(context, null);
    }

    public ResizeVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResizeVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bqC = new Object();
        this.bqF = ScalableType.NONE;
        this.bqL = new Runnable(this) {
            final /* synthetic */ ResizeVideoView bqM;

            {
                this.bqM = this$0;
            }

            public void run() {
                while (true) {
                    boolean playerCheck;
                    try {
                        if (this.bqM.bqE == null || !this.bqM.bqE.isPlaying() || this.bqM.Nw()) {
                            playerCheck = false;
                        } else {
                            playerCheck = true;
                        }
                    } catch (Exception e) {
                        playerCheck = false;
                        HLog.error(ResizeVideoView.TAG, "check play error " + e, new Object[0]);
                    }
                    if (playerCheck) {
                        this.bqM.getHandler().post(new Runnable(this) {
                            final /* synthetic */ AnonymousClass2 bqN;

                            {
                                this.bqN = this$1;
                            }

                            public void run() {
                                if (this.bqN.bqM.bqG != null) {
                                    this.bqN.bqM.bqG.HU();
                                }
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (Exception e2) {
                            HLog.error(ResizeVideoView.TAG, "progress sleep ex " + e2, new Object[0]);
                        }
                    } else {
                        synchronized (this.bqM.bqC) {
                            this.bqM.bqK = null;
                        }
                        return;
                    }
                }
            }
        };
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, j.scaleStyle, 0, 0);
            if (a != null) {
                int scaleType = a.getInt(j.scaleStyle_scalableType, ScalableType.NONE.ordinal());
                a.recycle();
                this.bqF = ScalableType.values()[scaleType];
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        HLog.debug(TAG, "surface created", new Object[0]);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        HLog.debug(TAG, "surfaceChanged %d, %d", new Object[]{Integer.valueOf(width), Integer.valueOf(height)});
        if (this.bqG != null) {
            this.bqG.HU();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        HLog.debug(TAG, "surfaceDestroyed", new Object[0]);
        this.bqE.setDisplay(null);
        this.bqD.removeCallback(this);
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

    private void aH(int width, int height) {
        if (width != 0 && height != 0 && this.bqE != null) {
            int videoHeight;
            int videoWidth;
            HLog.debug(TAG, "scaleVideoSize videowidth %d videoheight %d", new Object[]{Integer.valueOf(this.bqE.getVideoWidth()), Integer.valueOf(this.bqE.getVideoWidth())});
            b viewSize = new b(getWidth(), getHeight());
            b videoSize = new b(this.bqE.getVideoWidth(), this.bqE.getVideoHeight());
            float ratioVideo = ((float) videoSize.getHeight()) / ((float) videoSize.getWidth());
            if (ratioVideo > ((float) viewSize.getHeight()) / ((float) viewSize.getWidth())) {
                videoHeight = viewSize.getHeight();
                videoWidth = (int) (((float) videoHeight) / ratioVideo);
            } else {
                videoWidth = viewSize.getWidth();
                videoHeight = (int) (((float) videoWidth) * ratioVideo);
            }
            HLog.debug(TAG, "set fixed size video width %d, video height %d", new Object[]{Integer.valueOf(videoWidth), Integer.valueOf(videoHeight)});
            getHolder().setFixedSize(videoWidth, videoHeight);
            getLayoutParams().width = videoWidth;
            getLayoutParams().height = videoHeight;
            if (getLayoutParams() instanceof LayoutParams) {
                ((LayoutParams) getLayoutParams()).gravity = 1;
            }
            requestLayout();
        }
    }

    private void Ny() {
        if (this.bqE == null) {
            this.bqE = new StateMediaPlayer();
            this.bqE.setOnVideoSizeChangedListener(this);
            this.bqE.setOnCompletionListener(this);
            this.bqE.setOnSeekCompleteListener(this);
            this.bqD = getHolder();
            this.bqD.setType(3);
            this.bqD.addCallback(this);
            this.bqE.setDisplay(this.bqD);
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
        this.mPath = path;
        Ny();
        this.bqE.setDataSource(path);
    }

    public void setDataSource(@y Context context, @y Uri uri, @z Map<String, String> map) throws IOException {
        setDataSource(context, uri);
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
        this.bqE.setVolume(leftVolume, rightVolume);
    }

    public void start() {
        Surface surface = getHolder().getSurface();
        if (surface == null || !surface.isValid()) {
            this.bqJ = true;
            release();
            this.bqE = null;
            try {
                setDataSource(this.mPath);
                b(new OnPreparedListener(this) {
                    final /* synthetic */ ResizeVideoView bqM;

                    {
                        this.bqM = this$0;
                    }

                    public void onPrepared(MediaPlayer mp) {
                        this.bqM.Nz();
                    }
                });
                return;
            } catch (Exception e) {
                HLog.error(TAG, "set data source e " + e, new Object[0]);
                return;
            }
        }
        Nz();
    }

    private void Nz() {
        this.bqE.start();
        synchronized (this.bqC) {
            if (this.bqG != null && this.bqK == null) {
                this.bqK = new Thread(this.bqL);
                this.bqK.start();
            }
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
            try {
                synchronized (this.bqC) {
                    if (this.bqK != null) {
                        this.bqK.interrupt();
                    }
                }
                this.bqK = null;
            } catch (Exception e) {
                try {
                    HLog.error(TAG, "release progress thread error " + e, new Object[0]);
                } finally {
                    this.bqK = null;
                }
            }
        }
    }

    public boolean Nw() {
        if (this.bqE != null && this.bqE.ND() == State.PAUSED) {
            return true;
        }
        return false;
    }

    public void setSurfaceTextureUpdatedListener(a surfaceTextureUpdatedListener) {
        this.bqG = surfaceTextureUpdatedListener;
    }

    public void onCompletion(MediaPlayer mp) {
        if (!(this.bqH == null || this.bqJ)) {
            this.bqH.onCompletion(mp);
        }
        this.bqJ = false;
    }

    public void onSeekComplete(MediaPlayer mp) {
        if (this.bqI != null) {
            this.bqI.onSeekComplete(mp);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
