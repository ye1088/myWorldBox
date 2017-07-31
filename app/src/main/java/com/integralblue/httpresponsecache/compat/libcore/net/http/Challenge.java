package com.integralblue.httpresponsecache.compat.libcore.net.http;

public final class Challenge {
    final String realm;
    final String scheme;

    public Challenge(String scheme, String realm) {
        this.scheme = scheme;
        this.realm = realm;
    }

    public boolean equals(Object o) {
        return (o instanceof Challenge) && ((Challenge) o).scheme.equals(this.scheme) && ((Challenge) o).realm.equals(this.realm);
    }

    public int hashCode() {
        return this.scheme.hashCode() + (this.realm.hashCode() * 31);
    }

    public String toString() {
        return "Challenge[" + this.scheme + " " + this.realm + "]";
    }
}
