package com.MCWorld.framework.base.log;

class HLog$2 implements Runnable {
    HLog$2() {
    }

    public void run() {
        if (HLog.access$000()) {
            LogToES.close();
        }
    }
}
