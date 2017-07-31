package io.netty.handler.codec.http;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeaders.NameValidator;
import io.netty.handler.codec.DefaultHeadersImpl;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class DefaultHttpHeaders extends HttpHeaders {
    private static final ByteProcessor HEADER_NAME_VALIDATOR = new 1();
    private static final int HIGHEST_INVALID_VALUE_CHAR_MASK = -16;
    static final NameValidator<CharSequence> HttpNameValidator = new 2();
    private final DefaultHeaders<CharSequence, CharSequence, ?> headers;

    public DefaultHttpHeaders() {
        this(true);
    }

    public DefaultHttpHeaders(boolean validate) {
        this(validate, nameValidator(validate));
    }

    protected DefaultHttpHeaders(boolean validate, NameValidator<CharSequence> nameValidator) {
        this(new DefaultHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, valueConverter(validate), nameValidator));
    }

    protected DefaultHttpHeaders(DefaultHeaders<CharSequence, CharSequence, ?> headers) {
        this.headers = headers;
    }

    public HttpHeaders add(HttpHeaders headers) {
        if (!(headers instanceof DefaultHttpHeaders)) {
            return super.add(headers);
        }
        this.headers.add(((DefaultHttpHeaders) headers).headers);
        return this;
    }

    public HttpHeaders set(HttpHeaders headers) {
        if (!(headers instanceof DefaultHttpHeaders)) {
            return super.set(headers);
        }
        this.headers.set(((DefaultHttpHeaders) headers).headers);
        return this;
    }

    public HttpHeaders add(String name, Object value) {
        this.headers.addObject(name, value);
        return this;
    }

    public HttpHeaders add(CharSequence name, Object value) {
        this.headers.addObject(name, value);
        return this;
    }

    public HttpHeaders add(String name, Iterable<?> values) {
        this.headers.addObject(name, values);
        return this;
    }

    public HttpHeaders add(CharSequence name, Iterable<?> values) {
        this.headers.addObject(name, values);
        return this;
    }

    public HttpHeaders addInt(CharSequence name, int value) {
        this.headers.addInt(name, value);
        return this;
    }

    public HttpHeaders addShort(CharSequence name, short value) {
        this.headers.addShort(name, value);
        return this;
    }

    public HttpHeaders remove(String name) {
        this.headers.remove(name);
        return this;
    }

    public HttpHeaders remove(CharSequence name) {
        this.headers.remove(name);
        return this;
    }

    public HttpHeaders set(String name, Object value) {
        this.headers.setObject(name, value);
        return this;
    }

    public HttpHeaders set(CharSequence name, Object value) {
        this.headers.setObject(name, value);
        return this;
    }

    public HttpHeaders set(String name, Iterable<?> values) {
        this.headers.setObject(name, values);
        return this;
    }

    public HttpHeaders set(CharSequence name, Iterable<?> values) {
        this.headers.setObject(name, values);
        return this;
    }

    public HttpHeaders setInt(CharSequence name, int value) {
        this.headers.setInt(name, value);
        return this;
    }

    public HttpHeaders setShort(CharSequence name, short value) {
        this.headers.setShort(name, value);
        return this;
    }

    public HttpHeaders clear() {
        this.headers.clear();
        return this;
    }

    public String get(String name) {
        return get((CharSequence) name);
    }

    public String get(CharSequence name) {
        return HeadersUtils.getAsString(this.headers, name);
    }

    public Integer getInt(CharSequence name) {
        return this.headers.getInt(name);
    }

    public int getInt(CharSequence name, int defaultValue) {
        return this.headers.getInt(name, defaultValue);
    }

    public Short getShort(CharSequence name) {
        return this.headers.getShort(name);
    }

    public short getShort(CharSequence name, short defaultValue) {
        return this.headers.getShort(name, defaultValue);
    }

    public Long getTimeMillis(CharSequence name) {
        return this.headers.getTimeMillis(name);
    }

    public long getTimeMillis(CharSequence name, long defaultValue) {
        return this.headers.getTimeMillis(name, defaultValue);
    }

    public List<String> getAll(String name) {
        return getAll((CharSequence) name);
    }

    public List<String> getAll(CharSequence name) {
        return HeadersUtils.getAllAsString(this.headers, name);
    }

    public List<Entry<String, String>> entries() {
        if (isEmpty()) {
            return Collections.emptyList();
        }
        List<Entry<String, String>> entriesConverted = new ArrayList(this.headers.size());
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            entriesConverted.add((Entry) i$.next());
        }
        return entriesConverted;
    }

    @Deprecated
    public Iterator<Entry<String, String>> iterator() {
        return HeadersUtils.iteratorAsString(this.headers);
    }

    public Iterator<Entry<CharSequence, CharSequence>> iteratorCharSequence() {
        return this.headers.iterator();
    }

    public boolean contains(String name) {
        return contains((CharSequence) name);
    }

    public boolean contains(CharSequence name) {
        return this.headers.contains(name);
    }

    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    public int size() {
        return this.headers.size();
    }

    public boolean contains(String name, String value, boolean ignoreCase) {
        return contains((CharSequence) name, (CharSequence) value, ignoreCase);
    }

    public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
        return this.headers.contains(name, value, ignoreCase ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
    }

    public Set<String> names() {
        return HeadersUtils.namesAsString(this.headers);
    }

    public boolean equals(Object o) {
        if (o instanceof DefaultHttpHeaders) {
            return this.headers.equals(((DefaultHttpHeaders) o).headers, AsciiString.CASE_SENSITIVE_HASHER);
        }
        return false;
    }

    public int hashCode() {
        return this.headers.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    private static void validateHeaderNameElement(byte value) {
        switch (value) {
            case (byte) 0:
            case (byte) 9:
            case (byte) 10:
            case (byte) 11:
            case (byte) 12:
            case (byte) 13:
            case (byte) 32:
            case (byte) 44:
            case (byte) 58:
            case (byte) 59:
            case (byte) 61:
                throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + value);
            default:
                if (value < (byte) 0) {
                    throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + value);
                }
                return;
        }
    }

    private static void validateHeaderNameElement(char value) {
        switch (value) {
            case '\u0000':
            case '\t':
            case '\n':
            case '\u000b':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case ';':
            case '=':
                throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + value);
            default:
                if (value > '') {
                    throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + value);
                }
                return;
        }
    }

    static ValueConverter<CharSequence> valueConverter(boolean validate) {
        return validate ? HeaderValueConverterAndValidator.INSTANCE : HeaderValueConverter.INSTANCE;
    }

    static NameValidator<CharSequence> nameValidator(boolean validate) {
        return validate ? HttpNameValidator : NameValidator.NOT_NULL;
    }
}
