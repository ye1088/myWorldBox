package com.integralblue.httpresponsecache.compat.javax.net.ssl;

import com.integralblue.httpresponsecache.compat.Strings;
import com.integralblue.httpresponsecache.compat.java.net.InetAddress;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class DefaultHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;

    public final boolean verify(String host, SSLSession session) {
        try {
            return verify(host, (X509Certificate) session.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean verify(String host, X509Certificate certificate) {
        return InetAddress.isNumeric(host) ? verifyIpAddress(host, certificate) : verifyHostName(host, certificate);
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        for (String altName : getSubjectAltNames(certificate, 7)) {
            if (ipAddress.equalsIgnoreCase(altName)) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyHostName(String hostName, X509Certificate certificate) {
        hostName = hostName.toLowerCase(Locale.US);
        boolean hasDns = false;
        for (String altName : getSubjectAltNames(certificate, 2)) {
            hasDns = true;
            if (verifyHostName(hostName, altName)) {
                return true;
            }
        }
        if (!hasDns) {
            String cn = new DistinguishedNameParser(certificate.getSubjectX500Principal()).find("cn");
            if (cn != null) {
                return verifyHostName(hostName, cn);
            }
        }
        return false;
    }

    private List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            Iterator i$ = subjectAltNames.iterator();
            while (i$.hasNext()) {
                List<?> entry = (List) i$.next();
                if (entry != null && entry.size() >= 2) {
                    Integer altNameType = (Integer) entry.get(0);
                    if (altNameType != null && altNameType.intValue() == type) {
                        String altName = (String) entry.get(1);
                        if (altName != null) {
                            result.add(altName);
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    public boolean verifyHostName(String hostName, String cn) {
        if (hostName == null || Strings.isEmpty(hostName) || cn == null || Strings.isEmpty(cn)) {
            return false;
        }
        cn = cn.toLowerCase(Locale.US);
        if (!cn.contains(WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD)) {
            return hostName.equals(cn);
        }
        if (cn.startsWith("*.") && hostName.regionMatches(0, cn, 2, cn.length() - 2)) {
            return true;
        }
        int asterisk = cn.indexOf(42);
        if (asterisk > cn.indexOf(46)) {
            return false;
        }
        if (!hostName.regionMatches(0, cn, 0, asterisk)) {
            return false;
        }
        int suffixLength = cn.length() - (asterisk + 1);
        int suffixStart = hostName.length() - suffixLength;
        if (hostName.indexOf(46, asterisk) < suffixStart && !hostName.endsWith(".clients.google.com")) {
            return false;
        }
        if (hostName.regionMatches(suffixStart, cn, asterisk + 1, suffixLength)) {
            return true;
        }
        return false;
    }
}
