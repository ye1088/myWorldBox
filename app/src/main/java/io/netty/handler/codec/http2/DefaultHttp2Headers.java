package io.netty.handler.codec.http2;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeaders.HeaderEntry;
import io.netty.handler.codec.DefaultHeaders.NameValidator;
import io.netty.handler.codec.ValueConverter;
import io.netty.handler.codec.http2.Http2Headers.PseudoHeaderName;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2Headers extends DefaultHeaders<CharSequence, CharSequence, Http2Headers> implements Http2Headers {
    private static final NameValidator<CharSequence> HTTP2_NAME_VALIDATOR = new NameValidator<CharSequence>() {
        public void validateName(CharSequence name) {
            if (name == null || name.length() == 0) {
                PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "empty headers are not allowed [%s]", name));
            }
            if (name instanceof AsciiString) {
                try {
                    if (((AsciiString) name).forEachByte(DefaultHttp2Headers.HTTP2_NAME_VALIDATOR_PROCESSOR) != -1) {
                        PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", name));
                        return;
                    }
                    return;
                } catch (Http2Exception e) {
                    PlatformDependent.throwException(e);
                    return;
                } catch (Throwable t) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, t, "unexpected error. invalid header name [%s]", name));
                    return;
                }
            }
            for (int i = 0; i < name.length(); i++) {
                if (AsciiString.isUpperCase(name.charAt(i))) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", name));
                }
            }
        }
    };
    private static final ByteProcessor HTTP2_NAME_VALIDATOR_PROCESSOR = new ByteProcessor() {
        public boolean process(byte value) throws Exception {
            return !AsciiString.isUpperCase(value);
        }
    };
    private HeaderEntry<CharSequence, CharSequence> firstNonPseudo;

    private final class Http2HeaderEntry extends HeaderEntry<CharSequence, CharSequence> {
        protected Http2HeaderEntry(int hash, CharSequence key, CharSequence value, HeaderEntry<CharSequence, CharSequence> next) {
            super(hash, key);
            this.value = value;
            this.next = next;
            if (key.length() == 0 || key.charAt(0) != ':') {
                this.after = DefaultHttp2Headers.this.head;
                this.before = DefaultHttp2Headers.this.head.before();
                if (DefaultHttp2Headers.this.firstNonPseudo == DefaultHttp2Headers.this.head) {
                    DefaultHttp2Headers.this.firstNonPseudo = this;
                }
            } else {
                this.after = DefaultHttp2Headers.this.firstNonPseudo;
                this.before = DefaultHttp2Headers.this.firstNonPseudo.before();
            }
            pointNeighborsToThis();
        }

        protected void remove() {
            if (this == DefaultHttp2Headers.this.firstNonPseudo) {
                DefaultHttp2Headers.this.firstNonPseudo = DefaultHttp2Headers.this.firstNonPseudo.after();
            }
            super.remove();
        }
    }

    public DefaultHttp2Headers() {
        this(true);
    }

    public DefaultHttp2Headers(boolean validate) {
        NameValidator nameValidator;
        HashingStrategy hashingStrategy = AsciiString.CASE_SENSITIVE_HASHER;
        ValueConverter valueConverter = CharSequenceValueConverter.INSTANCE;
        if (validate) {
            nameValidator = HTTP2_NAME_VALIDATOR;
        } else {
            nameValidator = NameValidator.NOT_NULL;
        }
        super(hashingStrategy, valueConverter, nameValidator);
        this.firstNonPseudo = this.head;
    }

    public DefaultHttp2Headers(boolean validate, int arraySizeHint) {
        NameValidator nameValidator;
        HashingStrategy hashingStrategy = AsciiString.CASE_SENSITIVE_HASHER;
        ValueConverter valueConverter = CharSequenceValueConverter.INSTANCE;
        if (validate) {
            nameValidator = HTTP2_NAME_VALIDATOR;
        } else {
            nameValidator = NameValidator.NOT_NULL;
        }
        super(hashingStrategy, valueConverter, nameValidator, arraySizeHint);
        this.firstNonPseudo = this.head;
    }

    public Http2Headers clear() {
        this.firstNonPseudo = this.head;
        return (Http2Headers) super.clear();
    }

    public boolean equals(Object o) {
        if (o instanceof Http2Headers) {
            return equals((Http2Headers) o, AsciiString.CASE_SENSITIVE_HASHER);
        }
        return false;
    }

    public int hashCode() {
        return hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    public Http2Headers method(CharSequence value) {
        set(PseudoHeaderName.METHOD.value(), value);
        return this;
    }

    public Http2Headers scheme(CharSequence value) {
        set(PseudoHeaderName.SCHEME.value(), value);
        return this;
    }

    public Http2Headers authority(CharSequence value) {
        set(PseudoHeaderName.AUTHORITY.value(), value);
        return this;
    }

    public Http2Headers path(CharSequence value) {
        set(PseudoHeaderName.PATH.value(), value);
        return this;
    }

    public Http2Headers status(CharSequence value) {
        set(PseudoHeaderName.STATUS.value(), value);
        return this;
    }

    public CharSequence method() {
        return (CharSequence) get(PseudoHeaderName.METHOD.value());
    }

    public CharSequence scheme() {
        return (CharSequence) get(PseudoHeaderName.SCHEME.value());
    }

    public CharSequence authority() {
        return (CharSequence) get(PseudoHeaderName.AUTHORITY.value());
    }

    public CharSequence path() {
        return (CharSequence) get(PseudoHeaderName.PATH.value());
    }

    public CharSequence status() {
        return (CharSequence) get(PseudoHeaderName.STATUS.value());
    }

    protected final HeaderEntry<CharSequence, CharSequence> newHeaderEntry(int h, CharSequence name, CharSequence value, HeaderEntry<CharSequence, CharSequence> next) {
        return new Http2HeaderEntry(h, name, value, next);
    }
}
