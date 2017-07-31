package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

public interface FileUpload extends HttpData {
    FileUpload copy();

    FileUpload duplicate();

    String getContentTransferEncoding();

    String getContentType();

    String getFilename();

    FileUpload replace(ByteBuf byteBuf);

    FileUpload retain();

    FileUpload retain(int i);

    FileUpload retainedDuplicate();

    void setContentTransferEncoding(String str);

    void setContentType(String str);

    void setFilename(String str);

    FileUpload touch();

    FileUpload touch(Object obj);
}
