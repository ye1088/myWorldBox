package com.MCWorld.utils;

import android.graphics.Bitmap;
import android.support.annotation.y;
import android.support.annotation.z;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.video.recorder.b;
import java.io.File;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

/* compiled from: VideoConverter */
public class be {
    private static final String TAG = "VideoConverter";

    public static String a(File input, long start, long duration) {
        Exception e;
        Throwable th;
        FFmpegFrameGrabber frameGrabber = null;
        try {
            FFmpegFrameGrabber frameGrabber2 = new FFmpegFrameGrabber(input);
            try {
                FFmpegFrameRecorder fFmpegFrameRecorder;
                frameGrabber2.start();
                double videoFramerate = frameGrabber2.getFrameRate();
                frameGrabber2.setTimestamp(start);
                long videoLength = frameGrabber2.getLengthInTime();
                if (start + duration > videoLength) {
                    duration = videoLength - start;
                }
                int width = frameGrabber2.getImageWidth();
                int height = frameGrabber2.getImageHeight();
                if (width <= 0) {
                    width = 320;
                }
                if (height <= 0) {
                    height = b.bpd;
                }
                if (width > 320) {
                    height = (height * 320) / width;
                    width = 320;
                }
                if (height > 240) {
                    width = (width * b.bpd) / height;
                    height = b.bpd;
                }
                int videoCodec = frameGrabber2.getVideoCodec();
                int videoBitrate = frameGrabber2.getVideoBitrate();
                int audioCodec = frameGrabber2.getAudioCodec();
                int audioChannels = frameGrabber2.getAudioChannels();
                int audioBitrate = frameGrabber2.getAudioBitrate();
                int sampleRate = frameGrabber2.getSampleRate();
                int sampleFormat = frameGrabber2.getSampleFormat();
                HLog.warn(TAG, "start " + start + " , duration " + duration + ", videoLength " + videoLength + ", video codec " + videoCodec + " , audio codec " + audioCodec + ", audio channels " + audioChannels + ", videoFramerate " + videoFramerate, new Object[0]);
                String str = d.Kn() + File.separator + aw(input.getName(), "_edited");
                FFmpegFrameRecorder fFmpegFrameRecorder2 = new FFmpegFrameRecorder(str, frameGrabber2.getImageWidth(), frameGrabber2.getImageHeight(), audioChannels);
                if (!(videoCodec == 28 || videoCodec == 0 || videoCodec == 13)) {
                    HLog.warn(TAG, "reset video code old %d", new Object[]{Integer.valueOf(videoCodec)});
                    fFmpegFrameRecorder2.setVideoCodec(13);
                }
                fFmpegFrameRecorder2.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
                fFmpegFrameRecorder2.setFormat("mp4");
                fFmpegFrameRecorder2.setImageHeight(height);
                fFmpegFrameRecorder2.setImageWidth(width);
                if (videoBitrate <= 0 || videoBitrate >= 10000000) {
                    fFmpegFrameRecorder2.setVideoBitrate(b.bpf);
                } else {
                    fFmpegFrameRecorder2.setVideoBitrate(videoBitrate);
                }
                if (audioBitrate <= 0 || audioBitrate >= 64000) {
                    fFmpegFrameRecorder2.setAudioBitrate(b.bpk);
                } else {
                    fFmpegFrameRecorder2.setAudioBitrate(audioBitrate);
                }
                if (audioChannels <= 0 || audioChannels >= 3) {
                    fFmpegFrameRecorder2.setAudioChannels(1);
                } else {
                    fFmpegFrameRecorder2.setAudioChannels(audioChannels);
                }
                fFmpegFrameRecorder2.setSampleFormat(-1);
                if (sampleRate <= 0 || sampleRate >= 44100) {
                    fFmpegFrameRecorder2.setSampleRate(b.bpj);
                } else {
                    fFmpegFrameRecorder2.setSampleRate(sampleRate);
                }
                fFmpegFrameRecorder2.start();
                long grabberStartTs = 0;
                while (true) {
                    try {
                        Frame captured_frame = frameGrabber2.grabFrame();
                        long grabberTs = frameGrabber2.getTimestamp();
                        if (grabberTs <= 0) {
                            HLog.warn(TAG, "grab frame ts " + grabberTs, new Object[0]);
                        } else {
                            long recorderTs;
                            if (grabberStartTs != 0) {
                                recorderTs = grabberTs - grabberStartTs;
                            } else {
                                grabberStartTs = grabberTs;
                                recorderTs = 0;
                            }
                            HLog.debug(TAG, "grabberts %d, recoderTs %d, captured_frame %s", new Object[]{Long.valueOf(grabberTs), Long.valueOf(recorderTs), captured_frame});
                            fFmpegFrameRecorder2.setTimestamp(recorderTs);
                            fFmpegFrameRecorder2.record(captured_frame);
                            if (captured_frame == null) {
                                break;
                            } else if (recorderTs > duration) {
                                HLog.debug(TAG, "recorder duration... duration %d, current ts %d", new Object[]{Long.valueOf(duration), Long.valueOf(recorderTs)});
                                break;
                            } else {
                                continue;
                            }
                            fFmpegFrameRecorder2.stop();
                            fFmpegFrameRecorder2.release();
                            if (frameGrabber2 != null) {
                                try {
                                    frameGrabber2.stop();
                                } catch (Exception e2) {
                                    HLog.error(TAG, "cut video grabber stop ex " + e2, new Object[0]);
                                }
                            }
                            fFmpegFrameRecorder = fFmpegFrameRecorder2;
                            frameGrabber = frameGrabber2;
                            return str;
                        }
                    } catch (Exception e3) {
                        try {
                            HLog.error(TAG, "video cut error", e3, new Object[0]);
                        } catch (Exception e4) {
                            e3 = e4;
                            fFmpegFrameRecorder = fFmpegFrameRecorder2;
                            frameGrabber = frameGrabber2;
                        } catch (Throwable th2) {
                            th = th2;
                            fFmpegFrameRecorder = fFmpegFrameRecorder2;
                            frameGrabber = frameGrabber2;
                        }
                    } catch (Throwable th22) {
                        th = th22;
                        fFmpegFrameRecorder = fFmpegFrameRecorder2;
                        frameGrabber = frameGrabber2;
                    }
                }
                HLog.debug(TAG, "!!! Failed cvQueryFrame, end...", new Object[0]);
                fFmpegFrameRecorder2.stop();
                fFmpegFrameRecorder2.release();
                if (frameGrabber2 != null) {
                    frameGrabber2.stop();
                }
                fFmpegFrameRecorder = fFmpegFrameRecorder2;
                frameGrabber = frameGrabber2;
                return str;
            } catch (Exception e5) {
                e3 = e5;
                frameGrabber = frameGrabber2;
                try {
                    HLog.error(TAG, "cut video ex " + e3, new Object[0]);
                    if (frameGrabber != null) {
                        try {
                            frameGrabber.stop();
                        } catch (Exception e22) {
                            HLog.error(TAG, "cut video grabber stop ex " + e22, new Object[0]);
                        }
                    }
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    if (frameGrabber != null) {
                        try {
                            frameGrabber.stop();
                        } catch (Exception e222) {
                            HLog.error(TAG, "cut video grabber stop ex " + e222, new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                frameGrabber = frameGrabber2;
                if (frameGrabber != null) {
                    frameGrabber.stop();
                }
                throw th;
            }
        } catch (Exception e6) {
            e3 = e6;
            HLog.error(TAG, "cut video ex " + e3, new Object[0]);
            if (frameGrabber != null) {
                frameGrabber.stop();
            }
            return null;
        }
    }

    private static String aw(@y String origin, @y String insert) {
        String suffix = origin.substring(origin.lastIndexOf("."));
        return origin.replace(suffix, insert + suffix);
    }

    @z
    public static Bitmap getVideoThumbnail(String videoPath) {
        Exception e;
        Throwable th;
        FFmpegFrameGrabber grabber = null;
        Bitmap thumbnail = null;
        try {
            FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(videoPath);
            try {
                grabber2.start();
                grabber2.setFrameNumber(5);
                thumbnail = new AndroidFrameConverter().convert(grabber2.grabKeyFrame());
                if (grabber2 != null) {
                    try {
                        grabber2.stop();
                        grabber2.release();
                    } catch (Exception e2) {
                        HLog.warn(TAG, "edit video stop grab frames", new Object[]{e2});
                        grabber = grabber2;
                    }
                }
                grabber = grabber2;
            } catch (Exception e3) {
                e = e3;
                grabber = grabber2;
                try {
                    HLog.error(TAG, "edit video grab frames", e, new Object[0]);
                    if (grabber != null) {
                        try {
                            grabber.stop();
                            grabber.release();
                        } catch (Exception e22) {
                            HLog.warn(TAG, "edit video stop grab frames", new Object[]{e22});
                        }
                    }
                    return thumbnail;
                } catch (Throwable th2) {
                    th = th2;
                    if (grabber != null) {
                        try {
                            grabber.stop();
                            grabber.release();
                        } catch (Exception e222) {
                            HLog.warn(TAG, "edit video stop grab frames", new Object[]{e222});
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                grabber = grabber2;
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            HLog.error(TAG, "edit video grab frames", e, new Object[0]);
            if (grabber != null) {
                grabber.stop();
                grabber.release();
            }
            return thumbnail;
        }
        return thumbnail;
    }

    @z
    public static Bitmap j(String videoPath, long ts) {
        Exception e;
        Throwable th;
        FFmpegFrameGrabber fFmpegFrameGrabber = null;
        Bitmap thumbnail = null;
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
            try {
                grabber.start();
                if (ts < 200000) {
                    grabber.setFrameNumber(5);
                } else {
                    grabber.setTimestamp(ts);
                }
                thumbnail = new AndroidFrameConverter().convert(grabber.grab());
                if (grabber != null) {
                    try {
                        grabber.stop();
                        grabber.release();
                    } catch (Exception e2) {
                        HLog.warn(TAG, "edit video stop grab frames", new Object[]{e2});
                        fFmpegFrameGrabber = grabber;
                    }
                }
                fFmpegFrameGrabber = grabber;
            } catch (Exception e3) {
                e = e3;
                fFmpegFrameGrabber = grabber;
                try {
                    HLog.error(TAG, "edit video grab frames", e, new Object[0]);
                    if (fFmpegFrameGrabber != null) {
                        try {
                            fFmpegFrameGrabber.stop();
                            fFmpegFrameGrabber.release();
                        } catch (Exception e22) {
                            HLog.warn(TAG, "edit video stop grab frames", new Object[]{e22});
                        }
                    }
                    return thumbnail;
                } catch (Throwable th2) {
                    th = th2;
                    if (fFmpegFrameGrabber != null) {
                        try {
                            fFmpegFrameGrabber.stop();
                            fFmpegFrameGrabber.release();
                        } catch (Exception e222) {
                            HLog.warn(TAG, "edit video stop grab frames", new Object[]{e222});
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fFmpegFrameGrabber = grabber;
                if (fFmpegFrameGrabber != null) {
                    fFmpegFrameGrabber.stop();
                    fFmpegFrameGrabber.release();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            HLog.error(TAG, "edit video grab frames", e, new Object[0]);
            if (fFmpegFrameGrabber != null) {
                fFmpegFrameGrabber.stop();
                fFmpegFrameGrabber.release();
            }
            return thumbnail;
        }
        return thumbnail;
    }
}
