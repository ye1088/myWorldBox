package io.netty.handler.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.Properties;
import org.apache.tomcat.Apr;

class OpenSsl$2 implements PrivilegedAction<Boolean> {
    OpenSsl$2() {
    }

    public Boolean run() {
        Boolean valueOf;
        boolean z = false;
        InputStream is = null;
        try {
            is = Apr.class.getResourceAsStream("/org/apache/tomcat/apr.properties");
            Properties props = new Properties();
            props.load(is);
            String info = props.getProperty("tcn.info");
            if (info != null && info.startsWith("netty-tcnative")) {
                z = true;
            }
            valueOf = Boolean.valueOf(z);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                }
            }
        }
        return valueOf;
    }
}
