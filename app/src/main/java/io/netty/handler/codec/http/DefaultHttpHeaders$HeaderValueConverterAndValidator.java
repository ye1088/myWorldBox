package io.netty.handler.codec.http;

final class DefaultHttpHeaders$HeaderValueConverterAndValidator extends DefaultHttpHeaders$HeaderValueConverter {
    static final DefaultHttpHeaders$HeaderValueConverterAndValidator INSTANCE = new DefaultHttpHeaders$HeaderValueConverterAndValidator();

    private DefaultHttpHeaders$HeaderValueConverterAndValidator() {
        super();
    }

    public CharSequence convertObject(Object value) {
        CharSequence seq = super.convertObject(value);
        int state = 0;
        for (int index = 0; index < seq.length(); index++) {
            state = validateValueChar(seq, state, seq.charAt(index));
        }
        if (state == 0) {
            return seq;
        }
        throw new IllegalArgumentException("a_isRightVersion header value must not end with '\\r' or '\\n':" + seq);
    }

    private static int validateValueChar(CharSequence seq, int state, char character) {
        if ((character & -16) == 0) {
            switch (character) {
                case '\u0000':
                    throw new IllegalArgumentException("a_isRightVersion header value contains a_isRightVersion prohibited character '\u0000': " + seq);
                case '\u000b':
                    throw new IllegalArgumentException("a_isRightVersion header value contains a_isRightVersion prohibited character '\\v': " + seq);
                case '\f':
                    throw new IllegalArgumentException("a_isRightVersion header value contains a_isRightVersion prohibited character '\\f': " + seq);
            }
        }
        switch (state) {
            case 0:
                switch (character) {
                    case '\n':
                        return 2;
                    case '\r':
                        return 1;
                    default:
                        return state;
                }
            case 1:
                switch (character) {
                    case '\n':
                        return 2;
                    default:
                        throw new IllegalArgumentException("only '\\n' is allowed after '\\r': " + seq);
                }
            case 2:
                switch (character) {
                    case '\t':
                    case ' ':
                        return 0;
                    default:
                        throw new IllegalArgumentException("only ' ' and '\\t' are allowed after '\\n': " + seq);
                }
            default:
                return state;
        }
    }
}
