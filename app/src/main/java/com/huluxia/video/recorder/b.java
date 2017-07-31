package com.huluxia.video.recorder;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.AudioRecord;
import android.media.CamcorderProfile;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.utils.d;
import com.huluxia.video.views.CameraPreviewView;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;
import org.bytedeco.javacv.FrameRecorder.Exception;

/* compiled from: HlxVideoRecorder */
public class b implements PreviewCallback, com.huluxia.video.views.CameraPreviewView.a {
    private static final String TAG = "InstantVideoRecorder";
    private static Exception boN = null;
    public static final int bpc = 320;
    public static final int bpd = 240;
    public static final int bpe = 30;
    public static final int bpf = 10000000;
    public static final long bpg = 30000;
    public static final int bph = 5000;
    public static final int bpj = 44100;
    public static final int bpk = 64000;
    Frame[] bpA;
    long[] bpB;
    ShortBuffer[] bpC;
    int bpD;
    int bpE;
    private Frame bpF = null;
    private FFmpegFrameFilter bpG;
    private CameraPreviewView bpH;
    private c bpI;
    private String bpJ;
    private int bpi = 30;
    private final String bpl;
    private String bpm;
    private int bpn = 320;
    private int bpo = bpd;
    private int bpq = 2;
    private int bpr = bpf;
    private AudioRecord bps;
    private a bpt;
    private Thread bpu;
    volatile boolean bpv = true;
    private volatile FFmpegFrameRecorder bpw;
    private long bpx;
    private boolean bpy;
    final int bpz = 0;
    private int imageHeight = bpd;
    private int imageWidth = 320;
    private final Context mContext;
    private long maxTime = bpg;
    private long startTime;

    /* compiled from: HlxVideoRecorder */
    class a implements Runnable {
        final /* synthetic */ b bpK;

        a(b this$0) {
            this.bpK = this$0;
        }

        public void run() {
            Process.setThreadPriority(-19);
            int bufferSize = AudioRecord.getMinBufferSize(b.bpj, 16, 2);
            this.bpK.bps = new AudioRecord(1, b.bpj, 16, 2, bufferSize);
            ShortBuffer audioData = ShortBuffer.allocate(bufferSize);
            Log.d(b.TAG, "audioRecord.startRecording()");
            this.bpK.bps.startRecording();
            while (this.bpK.bpv) {
                int bufferReadResult = this.bpK.bps.read(audioData.array(), 0, audioData.capacity());
                audioData.limit(bufferReadResult);
                if (bufferReadResult > 0) {
                    Log.v(b.TAG, "bufferReadResult: " + bufferReadResult);
                    if (this.bpK.bpy) {
                        try {
                            this.bpK.bpw.recordSamples(new Buffer[]{audioData});
                        } catch (Exception e) {
                            Log.v(b.TAG, e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
            Log.v(b.TAG, "AudioThread Finished, release audioRecord");
            if (this.bpK.bps != null) {
                this.bpK.bps.stop();
                this.bpK.bps.release();
                this.bpK.bps = null;
                Log.v(b.TAG, "audioRecord released");
            }
        }
    }

    /* compiled from: HlxVideoRecorder */
    public interface b {
        void Nm();
    }

    public static void tryLoad() throws Exception {
        if (boN != null) {
            throw boN;
        }
        try {
            System.loadLibrary("avutil");
            System.loadLibrary("postproc");
            System.loadLibrary("swresample");
            System.loadLibrary("swscale");
            System.loadLibrary("avcodec");
            System.loadLibrary("avformat");
            System.loadLibrary("avfilter");
            Class clz = Loader.class.getClass();
            System.loadLibrary("jniavutil");
            System.loadLibrary("jniavcodec");
            System.loadLibrary("jniavformat");
            System.loadLibrary("jnipostproc");
            System.loadLibrary("jniswresample");
            System.loadLibrary("jniswscale");
            System.loadLibrary("jniavfilter");
        } catch (Throwable t) {
            if (t instanceof Exception) {
                boN = (Exception) t;
            } else {
                boN = new Exception("Failed to load ffmpeg lib so", t);
            }
        }
    }

    public b(Context context, String folder) {
        this.mContext = context;
        this.bpl = folder;
        this.bps = new AudioRecord(1, bpj, 16, 2, AudioRecord.getMinBufferSize(bpj, 16, 2));
        this.bps.startRecording();
        this.bps.stop();
        this.bps.release();
        this.bps = null;
    }

    public boolean Na() {
        return this.bpy;
    }

    public void aF(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public void lD(int frameRate) {
        Log.d(TAG, "frameRate " + frameRate);
        this.bpi = frameRate;
    }

    public void aG(int width, int height) {
        Log.d(TAG, "outsize " + width + ", height " + height);
        this.bpn = width;
        this.bpo = height;
    }

    public void lE(int qualityValue) {
        Log.d(TAG, "quality " + qualityValue);
        this.bpq = qualityValue;
    }

    public void lF(int bitRate) {
        Log.d(TAG, "bitRate " + bitRate);
        this.bpr = bitRate;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long Nb() {
        return this.bpx;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public long Nc() {
        return this.maxTime;
    }

    public void a(c playCallback) {
        this.bpI = playCallback;
    }

    private void Nd() {
        Log.w(TAG, "init recorder");
        if (this.bpF == null) {
            this.bpF = new Frame(this.imageWidth, this.imageHeight, 8, 2);
            Log.i(TAG, "create yuvImage");
        }
        d recorderParameters = d.lI(this.bpq);
        this.bpm = d.o(this.bpl, null, String.format("%d-%d-%d-%d-%d-%s", new Object[]{Integer.valueOf(this.bpn), Integer.valueOf(this.bpo), Integer.valueOf(this.bpi), Integer.valueOf(this.bpr), Integer.valueOf(this.bpq), Long.toString(System.currentTimeMillis())}));
        if (UtilsFunction.empty(this.bpm)) {
            HLog.error(TAG, "create file failed %s", new Object[]{this.bpl});
            return;
        }
        this.bpw = new FFmpegFrameRecorder(this.bpm, this.bpn, this.bpo, 1);
        this.bpw.setFormat(recorderParameters.Nq());
        this.bpw.setFrameRate((double) this.bpi);
        this.bpw.setVideoQuality((double) recorderParameters.Nt());
        this.bpw.setVideoBitrate(this.bpr);
        this.bpw.setAudioBitrate(bpk);
        this.bpw.setSampleRate(bpj);
        Log.i(TAG, "recorder initialize success");
        this.bpt = new a(this);
        this.bpu = new Thread(this.bpt);
        this.bpv = true;
    }

    public void setFilters(String filters) {
        this.bpJ = filters;
    }

    public static String b(int w, int h, int x, int y, String transpose) {
        String filters = String.format("crop=w=%d:h=%d:x=%d:y=%d,transpose=%s", new Object[]{Integer.valueOf(w), Integer.valueOf(h), Integer.valueOf(x), Integer.valueOf(y), transpose});
        Log.d(TAG, "filters " + filters);
        return filters;
    }

    private void Ne() {
        if (TextUtils.isEmpty(this.bpJ)) {
            String transpose;
            int cropX;
            int cropY;
            if (com.huluxia.video.camera.a.lB(this.bpH.getCameraId())) {
                transpose = Values.CLOCK;
                cropX = 0;
                cropY = 0;
            } else {
                transpose = "3";
                CamcorderProfile camcorderProfile = CamcorderProfile.get(this.bpH.getCameraId());
                cropX = camcorderProfile.videoFrameWidth - this.bpn;
                cropY = camcorderProfile.videoFrameHeight - this.bpo;
            }
            this.bpJ = b((int) (((1.0f * ((float) this.bpo)) / ((float) this.bpn)) * ((float) this.imageHeight)), this.imageHeight, cropX, cropY, transpose);
        }
        this.bpG = new FFmpegFrameFilter(this.bpJ, this.imageWidth, this.imageHeight);
        this.bpG.setPixelFormat(26);
    }

    private void Nf() {
        if (this.bpG != null) {
            try {
                this.bpG.release();
            } catch (FrameFilter.Exception e) {
                e.printStackTrace();
            }
        }
        this.bpG = null;
    }

    public String Ng() {
        return this.bpm;
    }

    public void init() {
        try {
            FFmpegFrameFilter.tryLoad();
        } catch (FrameFilter.Exception e) {
            e.printStackTrace();
        }
        try {
            FFmpegFrameRecorder.tryLoad();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean Nh() {
        Log.d(TAG, "start recording init");
        Nd();
        Ne();
        Log.d(TAG, "start recording init end");
        try {
            this.bpw.start();
            this.bpG.start();
            this.startTime = System.currentTimeMillis();
            this.bpy = true;
            this.bpu.start();
            Log.d(TAG, "start recording....");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void Ni() {
        if (this.bpy) {
            this.bpx = System.currentTimeMillis();
            this.bpv = false;
            try {
                this.bpu.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.bpt = null;
            this.bpu = null;
            if (this.bpw != null && this.bpy) {
                this.bpy = false;
                Log.v(TAG, "Finishing recording, calling stop and release on recorder");
                try {
                    this.bpw.stop();
                    this.bpw.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.bpw = null;
                Nf();
                if (this.bpI != null) {
                    this.bpI.Nn();
                }
            }
        }
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            if (this.bpF != null && this.bpy) {
                ((ByteBuffer) this.bpF.image[0].position(0)).put(data);
                Log.v(TAG, "Writing Frame");
                long pastTime = System.currentTimeMillis() - this.startTime;
                if (pastTime >= this.maxTime) {
                    if (this.bpI != null) {
                        this.bpI.No();
                    }
                    camera.addCallbackBuffer(data);
                    return;
                }
                long t = 1000 * pastTime;
                if (t > this.bpw.getTimestamp()) {
                    if (this.bpI != null) {
                        this.bpI.j(this.startTime, pastTime);
                    }
                    this.bpw.setTimestamp(t);
                }
                a(this.bpF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            camera.addCallbackBuffer(data);
        }
        camera.addCallbackBuffer(data);
    }

    private void a(Frame frame) throws Exception, FrameFilter.Exception {
        this.bpG.push(frame);
        Log.d(TAG, "record frame width " + frame.imageWidth + ", frame height " + frame.imageHeight);
        while (true) {
            Frame filteredFrame = this.bpG.pull();
            if (filteredFrame != null) {
                this.bpw.record(filteredFrame);
            } else {
                return;
            }
        }
    }

    public void a(CameraPreviewView cameraPreviewView) {
        this.bpH = cameraPreviewView;
        this.bpH.a(this);
        this.bpH.setViewWHRatio((1.0f * ((float) this.bpn)) / ((float) this.bpo));
    }

    public void Nj() {
        Camera camera = this.bpH.getCamera();
        Parameters parameters = camera.getParameters();
        Size size = parameters.getPreviewSize();
        aF(size.width, size.height);
        camera.setPreviewCallbackWithBuffer(this);
        camera.addCallbackBuffer(new byte[(((size.width * size.height) * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat())) / 8)]);
    }

    public void Nk() {
    }

    public void Nl() {
    }

    public void df(boolean success) {
    }
}
