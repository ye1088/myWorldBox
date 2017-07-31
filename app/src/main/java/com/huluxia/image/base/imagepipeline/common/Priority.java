package com.huluxia.image.base.imagepipeline.common;

import javax.annotation.Nullable;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority getHigherPriority(@Nullable Priority priority1, @Nullable Priority priority2) {
        if (priority1 == null) {
            return priority2;
        }
        if (priority2 == null) {
            return priority1;
        }
        if (priority1.ordinal() > priority2.ordinal()) {
            return priority1;
        }
        return priority2;
    }
}
