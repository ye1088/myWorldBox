package com.google.protobuf;

import java.io.IOException;

public class CodedOutputStream$OutOfSpaceException extends IOException {
    private static final long serialVersionUID = -6947486886997889499L;

    CodedOutputStream$OutOfSpaceException() {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.");
    }
}
