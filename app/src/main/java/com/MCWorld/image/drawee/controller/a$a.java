package com.MCWorld.image.drawee.controller;

/* compiled from: AbstractDraweeController */
class a$a<INFO> extends e<INFO> {
    private a$a() {
    }

    public static <INFO> a$a<INFO> a(c<? super INFO> listener1, c<? super INFO> listener2) {
        a$a<INFO> forwarder = new a$a();
        forwarder.e(listener1);
        forwarder.e(listener2);
        return forwarder;
    }
}
