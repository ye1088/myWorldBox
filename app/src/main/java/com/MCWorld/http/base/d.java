package com.MCWorld.http.base;

/* compiled from: BaseResponse */
public class d {
    private int code;
    private Object data;
    private int rV;
    private boolean rY;
    private int sa;
    private String sb;
    private String sc;
    private String se;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int fg() {
        return this.sa;
    }

    public void bd(int error_code) {
        this.sa = error_code;
    }

    public String fh() {
        return this.sb;
    }

    public void aH(String error_msg) {
        this.sb = error_msg;
    }

    public String fi() {
        return this.se;
    }

    public void aI(String responseStr) {
        this.se = responseStr;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int fe() {
        return this.rV;
    }

    public void bb(int requestType) {
        this.rV = requestType;
    }

    public boolean eV() {
        return this.rY;
    }

    public void E(boolean showProgress) {
        this.rY = showProgress;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String fj() {
        return this.sc;
    }

    public void aJ(String error_title) {
        this.sc = error_title;
    }
}
