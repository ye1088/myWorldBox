package io.netty.handler.codec.http.multipart;

import io.netty.util.ReferenceCounted;

public interface InterfaceHttpData extends ReferenceCounted, Comparable<InterfaceHttpData> {

    public enum HttpDataType {
        Attribute,
        FileUpload,
        InternalAttribute
    }

    HttpDataType getHttpDataType();

    String getName();

    InterfaceHttpData retain();

    InterfaceHttpData retain(int i);

    InterfaceHttpData touch();

    InterfaceHttpData touch(Object obj);
}
