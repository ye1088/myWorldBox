package io.netty.handler.codec;

import io.netty.handler.codec.DefaultHeaders.HeaderDateFormat;
import io.netty.util.AsciiString;
import io.netty.util.internal.PlatformDependent;
import java.text.ParseException;

public class CharSequenceValueConverter implements ValueConverter<CharSequence> {
    public static final CharSequenceValueConverter INSTANCE = new CharSequenceValueConverter();

    public CharSequence convertObject(Object value) {
        if (value instanceof CharSequence) {
            return (CharSequence) value;
        }
        return value.toString();
    }

    public CharSequence convertInt(int value) {
        return String.valueOf(value);
    }

    public CharSequence convertLong(long value) {
        return String.valueOf(value);
    }

    public CharSequence convertDouble(double value) {
        return String.valueOf(value);
    }

    public CharSequence convertChar(char value) {
        return String.valueOf(value);
    }

    public CharSequence convertBoolean(boolean value) {
        return String.valueOf(value);
    }

    public CharSequence convertFloat(float value) {
        return String.valueOf(value);
    }

    public boolean convertToBoolean(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseBoolean();
        }
        return Boolean.parseBoolean(value.toString());
    }

    public CharSequence convertByte(byte value) {
        return String.valueOf(value);
    }

    public byte convertToByte(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).byteAt(0);
        }
        return Byte.parseByte(value.toString());
    }

    public char convertToChar(CharSequence value) {
        return value.charAt(0);
    }

    public CharSequence convertShort(short value) {
        return String.valueOf(value);
    }

    public short convertToShort(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseShort();
        }
        return Short.parseShort(value.toString());
    }

    public int convertToInt(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseInt();
        }
        return Integer.parseInt(value.toString());
    }

    public long convertToLong(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseLong();
        }
        return Long.parseLong(value.toString());
    }

    public CharSequence convertTimeMillis(long value) {
        return String.valueOf(value);
    }

    public long convertToTimeMillis(CharSequence value) {
        try {
            return HeaderDateFormat.get().parse(value.toString());
        } catch (ParseException e) {
            PlatformDependent.throwException(e);
            return 0;
        }
    }

    public float convertToFloat(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseFloat();
        }
        return Float.parseFloat(value.toString());
    }

    public double convertToDouble(CharSequence value) {
        if (value instanceof AsciiString) {
            return ((AsciiString) value).parseDouble();
        }
        return Double.parseDouble(value.toString());
    }
}
