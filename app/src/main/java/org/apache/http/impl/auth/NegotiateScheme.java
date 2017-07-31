package org.apache.http.impl.auth;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class NegotiateScheme extends AuthSchemeBase {
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    private GSSContext gssContext;
    private final Log log;
    private Oid negotiationOid;
    private final SpnegoTokenGenerator spengoGenerator;
    private State state;
    private final boolean stripPort;
    private byte[] token;

    public NegotiateScheme(SpnegoTokenGenerator spengoGenerator, boolean stripPort) {
        this.log = LogFactory.getLog(getClass());
        this.gssContext = null;
        this.negotiationOid = null;
        this.state = State.UNINITIATED;
        this.spengoGenerator = spengoGenerator;
        this.stripPort = stripPort;
    }

    public NegotiateScheme(SpnegoTokenGenerator spengoGenerator) {
        this(spengoGenerator, false);
    }

    public NegotiateScheme() {
        this(null, false);
    }

    public boolean isComplete() {
        return this.state == State.TOKEN_GENERATED || this.state == State.FAILED;
    }

    public String getSchemeName() {
        return "Negotiate";
    }

    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest request) throws AuthenticationException {
        return authenticate(credentials, request, null);
    }

    protected GSSManager getManager() {
        return GSSManager.getInstance();
    }

    public Header authenticate(Credentials credentials, HttpRequest request, HttpContext context) throws AuthenticationException {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        } else if (this.state != State.CHALLENGE_RECEIVED) {
            throw new IllegalStateException("Negotiation authentication process has not been initiated");
        } else {
            boolean tryKerberos;
            try {
                String key;
                if (isProxy()) {
                    key = ExecutionContext.HTTP_PROXY_HOST;
                } else {
                    key = ExecutionContext.HTTP_TARGET_HOST;
                }
                HttpHost host = (HttpHost) context.getAttribute(key);
                if (host == null) {
                    throw new AuthenticationException("Authentication host is not set in the execution context");
                }
                String authServer;
                if (this.stripPort || host.getPort() <= 0) {
                    authServer = host.getHostName();
                } else {
                    authServer = host.toHostString();
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("init " + authServer);
                }
                this.negotiationOid = new Oid(SPNEGO_OID);
                tryKerberos = false;
                GSSManager manager = getManager();
                this.gssContext = manager.createContext(manager.createName("HTTP@" + authServer, GSSName.NT_HOSTBASED_SERVICE).canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
                this.gssContext.requestMutualAuth(true);
                this.gssContext.requestCredDeleg(true);
                if (tryKerberos) {
                    this.log.debug("Using Kerberos MECH 1.2.840.113554.1.2.2");
                    this.negotiationOid = new Oid(KERBEROS_OID);
                    manager = getManager();
                    this.gssContext = manager.createContext(manager.createName("HTTP@" + authServer, GSSName.NT_HOSTBASED_SERVICE).canonicalize(this.negotiationOid), this.negotiationOid, null, 0);
                    this.gssContext.requestMutualAuth(true);
                    this.gssContext.requestCredDeleg(true);
                }
                if (this.token == null) {
                    this.token = new byte[0];
                }
                this.token = this.gssContext.initSecContext(this.token, 0, this.token.length);
                if (this.token == null) {
                    this.state = State.FAILED;
                    throw new AuthenticationException("GSS security context initialization failed");
                }
                if (this.spengoGenerator != null && this.negotiationOid.toString().equals(KERBEROS_OID)) {
                    this.token = this.spengoGenerator.generateSpnegoDERObject(this.token);
                }
                this.state = State.TOKEN_GENERATED;
                String tokenstr = new String(Base64.encodeBase64(this.token, false));
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Sending response '" + tokenstr + "' back to the auth server");
                }
                return new BasicHeader("Authorization", "Negotiate " + tokenstr);
            } catch (GSSException ex) {
                if (ex.getMajor() == 2) {
                    this.log.debug("GSSException BAD_MECH, retry with Kerberos MECH");
                    tryKerberos = true;
                } else {
                    throw ex;
                }
            } catch (IOException ex2) {
                this.state = State.FAILED;
                throw new AuthenticationException(ex2.getMessage());
            } catch (GSSException gsse) {
                this.state = State.FAILED;
                if (gsse.getMajor() == 9 || gsse.getMajor() == 8) {
                    throw new InvalidCredentialsException(gsse.getMessage(), gsse);
                } else if (gsse.getMajor() == 13) {
                    throw new InvalidCredentialsException(gsse.getMessage(), gsse);
                } else if (gsse.getMajor() == 10 || gsse.getMajor() == 19 || gsse.getMajor() == 20) {
                    throw new AuthenticationException(gsse.getMessage(), gsse);
                } else {
                    throw new AuthenticationException(gsse.getMessage());
                }
            }
        }
    }

    public String getParameter(String name) {
        if (name != null) {
            return null;
        }
        throw new IllegalArgumentException("Parameter name may not be null");
    }

    public String getRealm() {
        return null;
    }

    public boolean isConnectionBased() {
        return true;
    }

    protected void parseChallenge(CharArrayBuffer buffer, int beginIndex, int endIndex) throws MalformedChallengeException {
        String challenge = buffer.substringTrimmed(beginIndex, endIndex);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Received challenge '" + challenge + "' from the auth server");
        }
        if (this.state == State.UNINITIATED) {
            this.token = new Base64().decode(challenge.getBytes());
            this.state = State.CHALLENGE_RECEIVED;
            return;
        }
        this.log.debug("Authentication already attempted");
        this.state = State.FAILED;
    }
}
