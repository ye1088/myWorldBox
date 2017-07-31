package io.netty.handler.codec.string;

import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public final class LineSeparator {
    public static final LineSeparator DEFAULT = new LineSeparator(StringUtil.NEWLINE);
    public static final LineSeparator UNIX = new LineSeparator(SpecilApiUtil.LINE_SEP);
    public static final LineSeparator WINDOWS = new LineSeparator("\r\n");
    private final String value;

    public LineSeparator(String lineSeparator) {
        this.value = (String) ObjectUtil.checkNotNull(lineSeparator, "lineSeparator");
    }

    public String value() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineSeparator)) {
            return false;
        }
        LineSeparator that = (LineSeparator) o;
        if (this.value != null) {
            return this.value.equals(that.value);
        }
        if (that.value != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.value != null ? this.value.hashCode() : 0;
    }

    public String toString() {
        return ByteBufUtil.hexDump(this.value.getBytes(CharsetUtil.UTF_8));
    }
}
