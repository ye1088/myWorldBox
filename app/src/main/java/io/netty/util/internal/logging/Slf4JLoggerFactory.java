package io.netty.util.internal.logging;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

public class Slf4JLoggerFactory extends InternalLoggerFactory {
    static final /* synthetic */ boolean $assertionsDisabled = (!Slf4JLoggerFactory.class.desiredAssertionStatus());
    public static final InternalLoggerFactory INSTANCE = new Slf4JLoggerFactory();

    Slf4JLoggerFactory(boolean failIfNOP) {
        if ($assertionsDisabled || failIfNOP) {
            StringBuffer buf = new StringBuffer();
            PrintStream err = System.err;
            try {
                System.setErr(new PrintStream(new 1(this, buf), true, "US-ASCII"));
                try {
                    if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
                        throw new NoClassDefFoundError(buf.toString());
                    }
                    err.print(buf);
                    err.flush();
                    return;
                } finally {
                    System.setErr(err);
                }
            } catch (UnsupportedEncodingException e) {
                throw new Error(e);
            }
        }
        throw new AssertionError();
    }

    public InternalLogger newInstance(String name) {
        return new Slf4JLogger(LoggerFactory.getLogger(name));
    }
}
