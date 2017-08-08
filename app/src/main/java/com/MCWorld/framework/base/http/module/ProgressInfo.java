package com.MCWorld.framework.base.http.module;

public class ProgressInfo {
    public long length;
    public long progress;
    public float speed;

    public ProgressInfo(long length, long progress, float speed) {
        this.length = length;
        this.progress = progress;
        this.speed = speed;
    }

    public String toString() {
        return "ProgressInfo{length=" + this.length + ", progress=" + this.progress + ", speed=" + this.speed + '}';
    }
}
