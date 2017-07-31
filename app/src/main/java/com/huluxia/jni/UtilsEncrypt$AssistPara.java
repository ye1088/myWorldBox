package com.huluxia.jni;

public class UtilsEncrypt$AssistPara {
    public String assist_1;
    public String assist_2;

    public UtilsEncrypt$AssistPara(int para) {
        int randomFactor = UtilsEncrypt.radomInt();
        int i1 = para ^ randomFactor;
        this.assist_2 = String.valueOf(randomFactor + 65);
        this.assist_1 = String.valueOf(i1);
    }
}
