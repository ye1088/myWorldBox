package io.netty.handler.codec.http;

import io.netty.handler.codec.CharSequenceValueConverter;
import java.util.Calendar;
import java.util.Date;

class DefaultHttpHeaders$HeaderValueConverter extends CharSequenceValueConverter {
    static final DefaultHttpHeaders$HeaderValueConverter INSTANCE = new DefaultHttpHeaders$HeaderValueConverter();

    private DefaultHttpHeaders$HeaderValueConverter() {
    }

    public CharSequence convertObject(Object value) {
        if (value instanceof CharSequence) {
            return (CharSequence) value;
        }
        if (value instanceof Date) {
            return HttpHeaderDateFormat.get().format((Date) value);
        }
        if (value instanceof Calendar) {
            return HttpHeaderDateFormat.get().format(((Calendar) value).getTime());
        }
        return value.toString();
    }
}
