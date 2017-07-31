package com.huluxia.controller.resource.bean;

import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;

public class ResTaskInfo extends b implements Comparable {
    public String dataDownUrl;
    public String dir;
    public int encodeType = -1;
    public String filename;
    public int mK;
    public int mM;
    public DownloadRecord mN;
    public float mO;
    public ProgressInfo mP;
    public String mR;
    public String mS;
    public String mT;
    public boolean mU = true;
    public String mV;
    public String mW;
    public a mZ;
    public boolean na = false;
    public boolean nb = true;
    public int state = State.INIT.ordinal();

    public enum State {
        INIT,
        WAITING,
        PREPARE,
        DOWNLOAD_START,
        DOWNLOAD_PROGRESS,
        DOWNLOAD_PAUSE,
        DOWNLOAD_COMPLETE,
        DOWNLOAD_ERROR,
        DOWNLOAD_ERROR_RETRY,
        UNZIP_NOT_START,
        UNZIP_START,
        UNZIP_PROGRESSING,
        UNZIP_ERROR,
        UNZIP_COMPLETE,
        ERROR,
        XOR_DECODE_START,
        XOR_ERROR,
        FILE_DELETE,
        SUCC
    }

    public String toString() {
        return "ResTaskInfo{resourceType=" + this.mM + ", dir='" + this.dir + '\'' + ", filename='" + this.filename + '\'' + ", state=" + this.state + ", record=" + this.mN + ", donwloadRate=" + this.mO + ", unzipProgress=" + this.mP + ", unzipApk='" + this.mR + '\'' + ", notificationText='" + this.mS + '\'' + ", notificationIcon=" + this.mK + ", notificationIntentClass='" + this.mT + '\'' + ", renameFile=" + this.mU + ", finalFileName='" + this.mV + '\'' + ", encodeType=" + this.encodeType + ", cookie='" + this.mW + '\'' + ", dataDownUrl='" + this.dataDownUrl + '\'' + ", table=" + this.mZ + '}';
    }

    public int compareTo(Object another) {
        return 0;
    }
}
