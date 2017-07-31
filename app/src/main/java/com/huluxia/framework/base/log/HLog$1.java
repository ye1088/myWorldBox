package com.huluxia.framework.base.log;

class HLog$1 implements Runnable {
    HLog$1() {
    }

    public void run() {
        LogToES.flush();
    }
}
