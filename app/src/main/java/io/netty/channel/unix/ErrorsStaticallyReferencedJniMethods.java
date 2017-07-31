package io.netty.channel.unix;

final class ErrorsStaticallyReferencedJniMethods {
    static native int errnoEAGAIN();

    static native int errnoEBADF();

    static native int errnoECONNRESET();

    static native int errnoEINPROGRESS();

    static native int errnoENOTCONN();

    static native int errnoEPIPE();

    static native int errnoEWOULDBLOCK();

    static native int errorEALREADY();

    static native int errorECONNREFUSED();

    static native int errorEISCONN();

    static native int errorENETUNREACH();

    static native String strError(int i);

    private ErrorsStaticallyReferencedJniMethods() {
    }
}
