package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

public interface Attribute extends HttpData {
    Attribute copy();

    Attribute duplicate();

    String getValue() throws IOException;

    Attribute replace(ByteBuf byteBuf);

    Attribute retain();

    Attribute retain(int i);

    Attribute retainedDuplicate();

    void setValue(String str) throws IOException;

    Attribute touch();

    Attribute touch(Object obj);
}
