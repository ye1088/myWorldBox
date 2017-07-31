package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLException;
import javax.net.ssl.X509KeyManager;
import javax.security.auth.x500.X500Principal;
import org.apache.tomcat.jni.CertificateRequestedCallback.KeyMaterial;
import org.apache.tomcat.jni.SSL;

class OpenSslKeyMaterialManager {
    private static final Map<String, String> KEY_TYPES = new HashMap();
    static final String KEY_TYPE_DH_RSA = "DH_RSA";
    static final String KEY_TYPE_EC = "EC";
    static final String KEY_TYPE_EC_EC = "EC_EC";
    static final String KEY_TYPE_EC_RSA = "EC_RSA";
    static final String KEY_TYPE_RSA = "RSA";
    private final X509KeyManager keyManager;
    private final String password;

    static {
        KEY_TYPES.put("RSA", "RSA");
        KEY_TYPES.put("DHE_RSA", "RSA");
        KEY_TYPES.put("ECDHE_RSA", "RSA");
        KEY_TYPES.put("ECDHE_ECDSA", KEY_TYPE_EC);
        KEY_TYPES.put("ECDH_RSA", KEY_TYPE_EC_RSA);
        KEY_TYPES.put("ECDH_ECDSA", KEY_TYPE_EC_EC);
        KEY_TYPES.put(KEY_TYPE_DH_RSA, KEY_TYPE_DH_RSA);
    }

    OpenSslKeyMaterialManager(X509KeyManager keyManager, String password) {
        this.keyManager = keyManager;
        this.password = password;
    }

    void setKeyMaterial(ReferenceCountedOpenSslEngine engine) throws SSLException {
        long ssl = engine.sslPointer();
        String[] authMethods = SSL.authenticationMethods(ssl);
        Set<String> aliases = new HashSet(authMethods.length);
        for (String authMethod : authMethods) {
            String type = (String) KEY_TYPES.get(authMethod);
            if (type != null) {
                String alias = chooseServerAlias(engine, type);
                if (alias != null && aliases.add(alias)) {
                    setKeyMaterial(ssl, alias);
                }
            }
        }
    }

    KeyMaterial keyMaterial(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) throws SSLException {
        String alias = chooseClientAlias(engine, keyTypes, issuer);
        long keyBio = 0;
        long pkey = 0;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
            if (certificates == null || certificates.length == 0) {
                ReferenceCountedOpenSslContext.freeBio(0);
                ReferenceCountedOpenSslContext.freeBio(0);
                SSL.freePrivateKey(0);
                SSL.freeX509Chain(0);
                return null;
            }
            PrivateKey key = this.keyManager.getPrivateKey(alias);
            long keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(certificates);
            long certChain = SSL.parseX509Chain(keyCertChainBio);
            if (key != null) {
                keyBio = ReferenceCountedOpenSslContext.toBIO(key);
                pkey = SSL.parsePrivateKey(keyBio, this.password);
            }
            KeyMaterial material = new KeyMaterial(certChain, pkey);
            certChain = 0;
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            SSL.freePrivateKey(0);
            SSL.freeX509Chain(certChain);
            return material;
        } catch (SSLException e) {
            throw e;
        } catch (Exception e2) {
            throw new SSLException(e2);
        } catch (Throwable th) {
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            SSL.freePrivateKey(0);
            SSL.freeX509Chain(0);
        }
    }

    private void setKeyMaterial(long ssl, String alias) throws SSLException {
        long keyBio = 0;
        long keyCertChainBio = 0;
        long keyCertChainBio2 = 0;
        PemEncoded encoded;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
            if (certificates == null || certificates.length == 0) {
                ReferenceCountedOpenSslContext.freeBio(0);
                ReferenceCountedOpenSslContext.freeBio(0);
                ReferenceCountedOpenSslContext.freeBio(0);
                return;
            }
            PrivateKey key = this.keyManager.getPrivateKey(alias);
            encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, certificates);
            keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            keyCertChainBio2 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            if (key != null) {
                keyBio = ReferenceCountedOpenSslContext.toBIO(key);
            }
            SSL.setCertificateBio(ssl, keyCertChainBio, keyBio, this.password);
            SSL.setCertificateChainBio(ssl, keyCertChainBio2, false);
            encoded.release();
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
        } catch (SSLException e) {
            try {
                throw e;
            } catch (Throwable th) {
                ReferenceCountedOpenSslContext.freeBio(keyBio);
                ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
                ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
            }
        } catch (Exception e2) {
            throw new SSLException(e2);
        } catch (Throwable th2) {
            encoded.release();
        }
    }

    protected String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        return this.keyManager.chooseClientAlias(keyTypes, issuer, null);
    }

    protected String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type) {
        return this.keyManager.chooseServerAlias(type, null, null);
    }
}
