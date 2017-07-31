package io.netty.handler.codec.http;

import io.netty.handler.codec.DefaultHeaders.NameValidator;
import io.netty.util.AsciiString;
import io.netty.util.internal.PlatformDependent;

class DefaultHttpHeaders$2 implements NameValidator<CharSequence> {
    DefaultHttpHeaders$2() {
    }

    public void validateName(CharSequence name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("empty headers are not allowed [" + name + "]");
        } else if (name instanceof AsciiString) {
            try {
                ((AsciiString) name).forEachByte(DefaultHttpHeaders.access$100());
            } catch (Exception e) {
                PlatformDependent.throwException(e);
            }
        } else {
            for (int index = 0; index < name.length(); index++) {
                DefaultHttpHeaders.access$200(name.charAt(index));
            }
        }
    }
}
