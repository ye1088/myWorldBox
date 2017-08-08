package com.MCWorld.video.recorder;

import android.os.Build.VERSION;
import org.bytedeco.javacpp.avcodec;

/* compiled from: RecorderParameters */
public class d {
    private static boolean bpL = (VERSION.SDK_INT >= 10);
    private int audioBitrate;
    private int audioCodec;
    private int bpM = 24;
    private int bpN;
    private int bpO;
    private String bpP;
    private int videoBitrate;
    private int videoCodec = 13;
    private int videoFrameRate = 30;

    public d() {
        String str;
        this.audioCodec = bpL ? avcodec.AV_CODEC_ID_AAC : avcodec.AV_CODEC_ID_AMR_NB;
        this.bpN = 1;
        this.audioBitrate = 96000;
        this.videoBitrate = 1000000;
        this.bpO = bpL ? b.bpj : 8000;
        if (bpL) {
            str = "mp4";
        } else {
            str = "3gp";
        }
        this.bpP = str;
    }

    public static boolean Np() {
        return bpL;
    }

    public static void dg(boolean aAC_SUPPORTED) {
        bpL = aAC_SUPPORTED;
    }

    public String Nq() {
        return this.bpP;
    }

    public void gI(String videoOutputFormat) {
        this.bpP = videoOutputFormat;
    }

    public int Nr() {
        return this.bpO;
    }

    public void setAudioSamplingRate(int audioSamplingRate) {
        this.bpO = audioSamplingRate;
    }

    public int getVideoCodec() {
        return this.videoCodec;
    }

    public void setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
    }

    public int Ns() {
        return this.videoFrameRate;
    }

    public void setVideoFrameRate(int videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public int Nt() {
        return this.bpM;
    }

    public void lG(int videoQuality) {
        this.bpM = videoQuality;
    }

    public int getAudioCodec() {
        return this.audioCodec;
    }

    public void setAudioCodec(int audioCodec) {
        this.audioCodec = audioCodec;
    }

    public int Nu() {
        return this.bpN;
    }

    public void lH(int audioChannel) {
        this.bpN = audioChannel;
    }

    public int getAudioBitrate() {
        return this.audioBitrate;
    }

    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public int getVideoBitrate() {
        return this.videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public static d lI(int currentResolution) {
        d parameters = new d();
        if (currentResolution == 2) {
            parameters.setAudioBitrate(128000);
            parameters.lG(0);
        } else if (currentResolution == 1) {
            parameters.setAudioBitrate(128000);
            parameters.lG(20);
        } else if (currentResolution == 0) {
            parameters.setAudioBitrate(96000);
            parameters.lG(32);
        }
        return parameters;
    }
}
