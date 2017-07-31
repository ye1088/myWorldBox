package io.netty.handler.codec.http;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeaders.NameValidator;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class CombinedHttpHeaders extends DefaultHttpHeaders {

    private static final class CombinedHttpHeadersImpl extends DefaultHeaders<CharSequence, CharSequence, CombinedHttpHeadersImpl> {
        private static final int VALUE_LENGTH_ESTIMATE = 10;
        private CsvValueEscaper<CharSequence> charSequenceEscaper;
        private CsvValueEscaper<Object> objectEscaper;

        private interface CsvValueEscaper<T> {
            CharSequence escape(T t);
        }

        private CsvValueEscaper<Object> objectEscaper() {
            if (this.objectEscaper == null) {
                this.objectEscaper = new CsvValueEscaper<Object>() {
                    public CharSequence escape(Object value) {
                        return StringUtil.escapeCsv((CharSequence) CombinedHttpHeadersImpl.this.valueConverter().convertObject(value));
                    }
                };
            }
            return this.objectEscaper;
        }

        private CsvValueEscaper<CharSequence> charSequenceEscaper() {
            if (this.charSequenceEscaper == null) {
                this.charSequenceEscaper = new CsvValueEscaper<CharSequence>() {
                    public CharSequence escape(CharSequence value) {
                        return StringUtil.escapeCsv(value);
                    }
                };
            }
            return this.charSequenceEscaper;
        }

        public CombinedHttpHeadersImpl(HashingStrategy<CharSequence> nameHashingStrategy, ValueConverter<CharSequence> valueConverter, NameValidator<CharSequence> nameValidator) {
            super(nameHashingStrategy, valueConverter, nameValidator);
        }

        public List<CharSequence> getAll(CharSequence name) {
            List<CharSequence> values = super.getAll(name);
            if (values.isEmpty()) {
                return values;
            }
            if (values.size() == 1) {
                return StringUtil.unescapeCsvFields((CharSequence) values.get(0));
            }
            throw new IllegalStateException("CombinedHttpHeaders should only have one value");
        }

        public CombinedHttpHeadersImpl add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                throw new IllegalArgumentException("can't add to itself.");
            }
            Iterator i$;
            Entry<? extends CharSequence, ? extends CharSequence> header;
            if (!(headers instanceof CombinedHttpHeadersImpl)) {
                i$ = headers.iterator();
                while (i$.hasNext()) {
                    header = (Entry) i$.next();
                    add((CharSequence) header.getKey(), (CharSequence) header.getValue());
                }
            } else if (isEmpty()) {
                addImpl(headers);
            } else {
                i$ = headers.iterator();
                while (i$.hasNext()) {
                    header = (Entry) i$.next();
                    addEscapedValue((CharSequence) header.getKey(), (CharSequence) header.getValue());
                }
            }
            return this;
        }

        public CombinedHttpHeadersImpl set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            clear();
            return add((Headers) headers);
        }

        public CombinedHttpHeadersImpl setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            for (CharSequence key : headers.names()) {
                remove(key);
            }
            return add((Headers) headers);
        }

        public CombinedHttpHeadersImpl add(CharSequence name, CharSequence value) {
            return addEscapedValue(name, StringUtil.escapeCsv(value));
        }

        public CombinedHttpHeadersImpl add(CharSequence name, CharSequence... values) {
            return addEscapedValue(name, commaSeparate(charSequenceEscaper(), (Object[]) values));
        }

        public CombinedHttpHeadersImpl add(CharSequence name, Iterable<? extends CharSequence> values) {
            return addEscapedValue(name, commaSeparate(charSequenceEscaper(), (Iterable) values));
        }

        public CombinedHttpHeadersImpl addObject(CharSequence name, Iterable<?> values) {
            return addEscapedValue(name, commaSeparate(objectEscaper(), (Iterable) values));
        }

        public CombinedHttpHeadersImpl addObject(CharSequence name, Object... values) {
            return addEscapedValue(name, commaSeparate(objectEscaper(), values));
        }

        public CombinedHttpHeadersImpl set(CharSequence name, CharSequence... values) {
            super.set((Object) name, commaSeparate(charSequenceEscaper(), (Object[]) values));
            return this;
        }

        public CombinedHttpHeadersImpl set(CharSequence name, Iterable<? extends CharSequence> values) {
            super.set((Object) name, commaSeparate(charSequenceEscaper(), (Iterable) values));
            return this;
        }

        public CombinedHttpHeadersImpl setObject(CharSequence name, Object value) {
            super.set((Object) name, commaSeparate(objectEscaper(), value));
            return this;
        }

        public CombinedHttpHeadersImpl setObject(CharSequence name, Object... values) {
            super.set((Object) name, commaSeparate(objectEscaper(), values));
            return this;
        }

        public CombinedHttpHeadersImpl setObject(CharSequence name, Iterable<?> values) {
            super.set((Object) name, commaSeparate(objectEscaper(), (Iterable) values));
            return this;
        }

        private CombinedHttpHeadersImpl addEscapedValue(CharSequence name, CharSequence escapedValue) {
            CharSequence currentValue = (CharSequence) super.get(name);
            if (currentValue == null) {
                super.add((Object) name, (Object) escapedValue);
            } else {
                super.set((Object) name, commaSeparateEscapedValues(currentValue, escapedValue));
            }
            return this;
        }

        private static <T> CharSequence commaSeparate(CsvValueEscaper<T> escaper, T... values) {
            StringBuilder sb = new StringBuilder(values.length * 10);
            if (values.length > 0) {
                int end = values.length - 1;
                for (int i = 0; i < end; i++) {
                    sb.append(escaper.escape(values[i])).append(StringUtil.COMMA);
                }
                sb.append(escaper.escape(values[end]));
            }
            return sb;
        }

        private static <T> CharSequence commaSeparate(CsvValueEscaper<T> escaper, Iterable<? extends T> values) {
            StringBuilder sb = values instanceof Collection ? new StringBuilder(((Collection) values).size() * 10) : new StringBuilder();
            Iterator<? extends T> iterator = values.iterator();
            if (iterator.hasNext()) {
                T next = iterator.next();
                while (iterator.hasNext()) {
                    sb.append(escaper.escape(next)).append(StringUtil.COMMA);
                    next = iterator.next();
                }
                sb.append(escaper.escape(next));
            }
            return sb;
        }

        private CharSequence commaSeparateEscapedValues(CharSequence currentValue, CharSequence value) {
            return new StringBuilder((currentValue.length() + 1) + value.length()).append(currentValue).append(StringUtil.COMMA).append(value);
        }
    }

    public CombinedHttpHeaders(boolean validate) {
        super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, valueConverter(validate), nameValidator(validate)));
    }
}
