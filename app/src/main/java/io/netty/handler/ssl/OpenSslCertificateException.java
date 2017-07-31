package io.netty.handler.ssl;

import java.security.cert.CertificateException;

public final class OpenSslCertificateException extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int errorCode) {
        this((String) null, errorCode);
    }

    public OpenSslCertificateException(String msg, int errorCode) {
        super(msg);
        this.errorCode = checkErrorCode(errorCode);
    }

    public OpenSslCertificateException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = checkErrorCode(errorCode);
    }

    public OpenSslCertificateException(Throwable cause, int errorCode) {
        this(null, cause, errorCode);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int checkErrorCode(int errorCode) {
        if (errorCode >= 0 && errorCode <= 65) {
            return errorCode;
        }
        throw new IllegalArgumentException("errorCode must be 0 => 65. See https://www.openssl.org/docs/manmaster/apps/verify.html .");
    }
}
