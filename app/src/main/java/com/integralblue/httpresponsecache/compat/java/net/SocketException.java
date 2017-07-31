package com.integralblue.httpresponsecache.compat.java.net;

import com.tencent.mm.sdk.platformtools.SpecilApiUtil;

public class SocketException extends java.net.SocketException {
    public SocketException(String detailMessage) {
        super(detailMessage);
    }

    public SocketException(String detailMessage, Throwable cause) {
        super(detailMessage + SpecilApiUtil.LINE_SEP + cause.toString());
    }
}
