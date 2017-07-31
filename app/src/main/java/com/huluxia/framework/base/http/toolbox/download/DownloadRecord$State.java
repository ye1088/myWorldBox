package com.huluxia.framework.base.http.toolbox.download;

public enum DownloadRecord$State {
    INIT(0),
    DOWNLOADING(2),
    COMPLETION(4);
    
    public int state;

    private DownloadRecord$State(int state) {
        this.state = state;
    }
}
