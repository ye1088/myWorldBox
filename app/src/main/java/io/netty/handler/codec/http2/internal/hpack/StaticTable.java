package io.netty.handler.codec.http2.internal.hpack;

import io.netty.handler.codec.UnsupportedValueConverter;
import io.netty.handler.codec.http2.CharSequenceMap;
import io.netty.util.AsciiString;
import java.util.Arrays;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

final class StaticTable {
    private static final CharSequenceMap<Integer> STATIC_INDEX_BY_NAME = createMap();
    private static final List<HeaderField> STATIC_TABLE = Arrays.asList(new HeaderField[]{newEmptyHeaderField(":authority"), newHeaderField(":method", "GET"), newHeaderField(":method", "POST"), newHeaderField(":path", "/"), newHeaderField(":path", "/index.html"), newHeaderField(":scheme", "http"), newHeaderField(":scheme", "https"), newHeaderField(":status", "200"), newHeaderField(":status", "204"), newHeaderField(":status", "206"), newHeaderField(":status", "304"), newHeaderField(":status", "400"), newHeaderField(":status", "404"), newHeaderField(":status", "500"), newEmptyHeaderField("accept-charset"), newHeaderField("accept-encoding", "gzip, deflate"), newEmptyHeaderField("accept-language"), newEmptyHeaderField("accept-ranges"), newEmptyHeaderField("accept"), newEmptyHeaderField("access-control-allow-origin"), newEmptyHeaderField("age"), newEmptyHeaderField("allow"), newEmptyHeaderField("authorization"), newEmptyHeaderField("cache-control"), newEmptyHeaderField("content-disposition"), newEmptyHeaderField("content-encoding"), newEmptyHeaderField("content-language"), newEmptyHeaderField("content-length"), newEmptyHeaderField("content-location"), newEmptyHeaderField("content-range"), newEmptyHeaderField("content-type"), newEmptyHeaderField("cookie"), newEmptyHeaderField("date"), newEmptyHeaderField("etag"), newEmptyHeaderField("expect"), newEmptyHeaderField(ClientCookie.EXPIRES_ATTR), newEmptyHeaderField("from"), newEmptyHeaderField("host"), newEmptyHeaderField("if-match"), newEmptyHeaderField("if-modified-since"), newEmptyHeaderField("if-none-match"), newEmptyHeaderField("if-range"), newEmptyHeaderField("if-unmodified-since"), newEmptyHeaderField("last-modified"), newEmptyHeaderField("link"), newEmptyHeaderField("location"), newEmptyHeaderField("max-forwards"), newEmptyHeaderField("proxy-authenticate"), newEmptyHeaderField("proxy-authorization"), newEmptyHeaderField("range"), newEmptyHeaderField("referer"), newEmptyHeaderField("refresh"), newEmptyHeaderField("retry-after"), newEmptyHeaderField("server"), newEmptyHeaderField("set-cookie"), newEmptyHeaderField("strict-transport-security"), newEmptyHeaderField("transfer-encoding"), newEmptyHeaderField("user-agent"), newEmptyHeaderField("vary"), newEmptyHeaderField("via"), newEmptyHeaderField("www-authenticate")});
    static final int length = STATIC_TABLE.size();

    private static HeaderField newEmptyHeaderField(CharSequence name) {
        return newHeaderField(name, AsciiString.EMPTY_STRING);
    }

    private static HeaderField newHeaderField(CharSequence name, CharSequence value) {
        return new HeaderField(AsciiString.of(name), AsciiString.of(value));
    }

    static HeaderField getEntry(int index) {
        return (HeaderField) STATIC_TABLE.get(index - 1);
    }

    static int getIndex(CharSequence name) {
        Integer index = (Integer) STATIC_INDEX_BY_NAME.get(name);
        if (index == null) {
            return -1;
        }
        return index.intValue();
    }

    static int getIndex(CharSequence name, CharSequence value) {
        int index = getIndex(name);
        if (index == -1) {
            return -1;
        }
        while (index <= length) {
            HeaderField entry = getEntry(index);
            if (HpackUtil.equalsConstantTime(name, entry.name) == 0) {
                return -1;
            }
            if (HpackUtil.equalsConstantTime(value, entry.value) != 0) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static CharSequenceMap<Integer> createMap() {
        int length = STATIC_TABLE.size();
        CharSequenceMap<Integer> ret = new CharSequenceMap(true, UnsupportedValueConverter.instance(), length);
        for (int index = length; index > 0; index--) {
            ret.set(getEntry(index).name, Integer.valueOf(index));
        }
        return ret;
    }

    private StaticTable() {
    }
}
