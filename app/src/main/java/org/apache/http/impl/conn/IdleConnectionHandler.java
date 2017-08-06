package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

@Deprecated
public class IdleConnectionHandler {
    private final Map<HttpConnection, TimeValues> connectionToTimes = new HashMap();
    private final Log log = LogFactory.getLog(getClass());

    public void add(HttpConnection connection, long validDuration, TimeUnit unit) {
        long timeAdded = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Adding connection at: " + timeAdded);
        }
        this.connectionToTimes.put(connection, new TimeValues(timeAdded, validDuration, unit));
    }

    public boolean remove(HttpConnection connection) {
        TimeValues times = (TimeValues) this.connectionToTimes.remove(connection);
        if (times == null) {
            this.log.warn("Removing a_isRightVersion connection that never existed!");
            return true;
        } else if (System.currentTimeMillis() > TimeValues.access$000(times)) {
            return false;
        } else {
            return true;
        }
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }

    public void closeIdleConnections(long idleTime) {
        long idleTimeout = System.currentTimeMillis() - idleTime;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for connections, idle timeout: " + idleTimeout);
        }
        for (Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection conn = (HttpConnection) entry.getKey();
            long connectionTime = TimeValues.access$100((TimeValues) entry.getValue());
            if (connectionTime <= idleTimeout) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing idle connection, connection time: " + connectionTime);
                }
                try {
                    conn.close();
                } catch (IOException ex) {
                    this.log.debug("I/O error closing connection", ex);
                }
            }
        }
    }

    public void closeExpiredConnections() {
        long now = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for expired connections, now: " + now);
        }
        for (Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet()) {
            HttpConnection conn = (HttpConnection) entry.getKey();
            TimeValues times = (TimeValues) entry.getValue();
            if (TimeValues.access$000(times) <= now) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection, expired @: " + TimeValues.access$000(times));
                }
                try {
                    conn.close();
                } catch (IOException ex) {
                    this.log.debug("I/O error closing connection", ex);
                }
            }
        }
    }
}
