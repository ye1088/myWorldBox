package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpConnection.Address;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class HttpConnectionPool {
    public static final HttpConnectionPool INSTANCE = new HttpConnectionPool();
    private final HashMap<Address, List<HttpConnection>> connectionPool = new HashMap();
    private final int maxConnections;

    private HttpConnectionPool() {
        String keepAlive = System.getProperty("http.keepAlive");
        if (keepAlive == null || Boolean.parseBoolean(keepAlive)) {
            String maxConnectionsString = System.getProperty("http.maxConnections");
            this.maxConnections = maxConnectionsString != null ? Integer.parseInt(maxConnectionsString) : 5;
            return;
        }
        this.maxConnections = 0;
    }

    public HttpConnection get(Address address, int connectTimeout) throws IOException {
        synchronized (this.connectionPool) {
            List<HttpConnection> connections = (List) this.connectionPool.get(address);
            while (connections != null) {
                HttpConnection connection = (HttpConnection) connections.remove(connections.size() - 1);
                if (connections.isEmpty()) {
                    this.connectionPool.remove(address);
                    connections = null;
                }
                if (connection.isEligibleForRecycling()) {
                    return connection;
                }
            }
            return address.connect(connectTimeout);
        }
    }

    public void recycle(HttpConnection connection) {
        if (this.maxConnections > 0 && connection.isEligibleForRecycling()) {
            Address address = connection.getAddress();
            synchronized (this.connectionPool) {
                List<HttpConnection> connections = (List) this.connectionPool.get(address);
                if (connections == null) {
                    connections = new ArrayList();
                    this.connectionPool.put(address, connections);
                }
                if (connections.size() < this.maxConnections) {
                    connection.setRecycled();
                    connections.add(connection);
                    return;
                }
            }
        }
        connection.closeSocketAndStreams();
    }
}
