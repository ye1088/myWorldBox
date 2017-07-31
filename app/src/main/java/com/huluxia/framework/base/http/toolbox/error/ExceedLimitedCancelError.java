package com.huluxia.framework.base.http.toolbox.error;

public class ExceedLimitedCancelError extends CancelError {
    public ExceedLimitedCancelError(String message) {
        super(message);
    }

    public int getErrorId() {
        return 36;
    }
}
