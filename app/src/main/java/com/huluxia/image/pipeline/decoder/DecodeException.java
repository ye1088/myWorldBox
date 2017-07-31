package com.huluxia.image.pipeline.decoder;

public class DecodeException extends RuntimeException {
    public DecodeException(String message) {
        super(message);
    }

    public DecodeException(String message, Throwable t) {
        super(message, t);
    }
}
