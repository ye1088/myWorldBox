package com.google.protobuf;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

class CodedInputStream$SkippedDataSink implements CodedInputStream$RefillCallback {
    private ByteArrayOutputStream byteArrayStream;
    private int lastPos = CodedInputStream.access$000(this.this$0);
    final /* synthetic */ CodedInputStream this$0;

    private CodedInputStream$SkippedDataSink(CodedInputStream codedInputStream) {
        this.this$0 = codedInputStream;
    }

    public void onRefill() {
        if (this.byteArrayStream == null) {
            this.byteArrayStream = new ByteArrayOutputStream();
        }
        this.byteArrayStream.write(CodedInputStream.access$100(this.this$0), this.lastPos, CodedInputStream.access$000(this.this$0) - this.lastPos);
        this.lastPos = 0;
    }

    ByteBuffer getSkippedData() {
        if (this.byteArrayStream == null) {
            return ByteBuffer.wrap(CodedInputStream.access$100(this.this$0), this.lastPos, CodedInputStream.access$000(this.this$0) - this.lastPos);
        }
        this.byteArrayStream.write(CodedInputStream.access$100(this.this$0), this.lastPos, CodedInputStream.access$000(this.this$0));
        return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
    }
}
