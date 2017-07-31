package com.xiaomi.smack.packet;

public class h$a {
    public static final h$a a = new h$a("internal-server-error");
    public static final h$a b = new h$a("forbidden");
    public static final h$a c = new h$a("bad-request");
    public static final h$a d = new h$a("conflict");
    public static final h$a e = new h$a("feature-not-implemented");
    public static final h$a f = new h$a("gone");
    public static final h$a g = new h$a("item-not-found");
    public static final h$a h = new h$a("jid-malformed");
    public static final h$a i = new h$a("not-acceptable");
    public static final h$a j = new h$a("not-allowed");
    public static final h$a k = new h$a("not-authorized");
    public static final h$a l = new h$a("payment-required");
    public static final h$a m = new h$a("recipient-unavailable");
    public static final h$a n = new h$a("redirect");
    public static final h$a o = new h$a("registration-required");
    public static final h$a p = new h$a("remote-server-error");
    public static final h$a q = new h$a("remote-server-not-found");
    public static final h$a r = new h$a("remote-server-timeout");
    public static final h$a s = new h$a("resource-constraint");
    public static final h$a t = new h$a("service-unavailable");
    public static final h$a u = new h$a("subscription-required");
    public static final h$a v = new h$a("undefined-condition");
    public static final h$a w = new h$a("unexpected-request");
    public static final h$a x = new h$a("request-timeout");
    private String y;

    public h$a(String str) {
        this.y = str;
    }

    public String toString() {
        return this.y;
    }
}
