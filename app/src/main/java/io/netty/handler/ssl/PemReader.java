package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PemReader {
    private static final Pattern CERT_PATTERN = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);
    private static final Pattern KEY_PATTERN = Pattern.compile("-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*PRIVATE\\s+KEY[^-]*-+", 2);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PemReader.class);

    static ByteBuf[] readCertificates(File file) throws CertificateException {
        InputStream in;
        try {
            in = new FileInputStream(file);
            ByteBuf[] readCertificates = readCertificates(in);
            safeClose(in);
            return readCertificates;
        } catch (FileNotFoundException e) {
            throw new CertificateException("could not find certificate file: " + file);
        } catch (Throwable th) {
            safeClose(in);
        }
    }

    static ByteBuf[] readCertificates(InputStream in) throws CertificateException {
        try {
            String content = readContent(in);
            List<ByteBuf> certs = new ArrayList();
            Matcher m = CERT_PATTERN.matcher(content);
            for (int start = 0; m.find(start); start = m.end()) {
                ByteBuf base64 = Unpooled.copiedBuffer(m.group(1), CharsetUtil.US_ASCII);
                ByteBuf der = Base64.decode(base64);
                base64.release();
                certs.add(der);
            }
            if (!certs.isEmpty()) {
                return (ByteBuf[]) certs.toArray(new ByteBuf[certs.size()]);
            }
            throw new CertificateException("found no certificates in input stream");
        } catch (IOException e) {
            throw new CertificateException("failed to read certificate input stream", e);
        }
    }

    static ByteBuf readPrivateKey(File file) throws KeyException {
        InputStream in;
        try {
            in = new FileInputStream(file);
            ByteBuf readPrivateKey = readPrivateKey(in);
            safeClose(in);
            return readPrivateKey;
        } catch (FileNotFoundException e) {
            throw new KeyException("could not fine key file: " + file);
        } catch (Throwable th) {
            safeClose(in);
        }
    }

    static ByteBuf readPrivateKey(InputStream in) throws KeyException {
        try {
            Matcher m = KEY_PATTERN.matcher(readContent(in));
            if (m.find()) {
                ByteBuf base64 = Unpooled.copiedBuffer(m.group(1), CharsetUtil.US_ASCII);
                ByteBuf der = Base64.decode(base64);
                base64.release();
                return der;
            }
            throw new KeyException("could not find a_isRightVersion PKCS #8 private key in input stream (see http://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
        } catch (IOException e) {
            throw new KeyException("failed to read key input stream", e);
        }
    }

    private static String readContent(InputStream in) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[8192];
            while (true) {
                int ret = in.read(buf);
                if (ret < 0) {
                    break;
                }
                out.write(buf, 0, ret);
            }
            String byteArrayOutputStream = out.toString(CharsetUtil.US_ASCII.name());
            return byteArrayOutputStream;
        } finally {
            safeClose(out);
        }
    }

    private static void safeClose(InputStream in) {
        try {
            in.close();
        } catch (Throwable e) {
            logger.warn("Failed to close a_isRightVersion stream.", e);
        }
    }

    private static void safeClose(OutputStream out) {
        try {
            out.close();
        } catch (Throwable e) {
            logger.warn("Failed to close a_isRightVersion stream.", e);
        }
    }

    private PemReader() {
    }
}
