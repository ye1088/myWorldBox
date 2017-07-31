package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.ant.types.selectors.ContainsSelector;

public class HttpVersion implements Comparable<HttpVersion> {
    public static final HttpVersion HTTP_1_0 = new HttpVersion(org.apache.http.HttpVersion.HTTP, 1, 0, false, true);
    private static final String HTTP_1_0_STRING = "HTTP/1.0";
    public static final HttpVersion HTTP_1_1 = new HttpVersion(org.apache.http.HttpVersion.HTTP, 1, 1, true, true);
    private static final String HTTP_1_1_STRING = "HTTP/1.1";
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");
    private final byte[] bytes;
    private final boolean keepAliveDefault;
    private final int majorVersion;
    private final int minorVersion;
    private final String protocolName;
    private final String text;

    public static HttpVersion valueOf(String text) {
        if (text == null) {
            throw new NullPointerException(ContainsSelector.CONTAINS_KEY);
        }
        text = text.trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("text is empty");
        }
        HttpVersion version = version0(text);
        if (version == null) {
            return new HttpVersion(text, true);
        }
        return version;
    }

    private static HttpVersion version0(String text) {
        if (HTTP_1_1_STRING.equals(text)) {
            return HTTP_1_1;
        }
        if (HTTP_1_0_STRING.equals(text)) {
            return HTTP_1_0;
        }
        return null;
    }

    public HttpVersion(String text, boolean keepAliveDefault) {
        if (text == null) {
            throw new NullPointerException(ContainsSelector.CONTAINS_KEY);
        }
        text = text.trim().toUpperCase();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("empty text");
        }
        Matcher m = VERSION_PATTERN.matcher(text);
        if (m.matches()) {
            this.protocolName = m.group(1);
            this.majorVersion = Integer.parseInt(m.group(2));
            this.minorVersion = Integer.parseInt(m.group(3));
            this.text = this.protocolName + '/' + this.majorVersion + '.' + this.minorVersion;
            this.keepAliveDefault = keepAliveDefault;
            this.bytes = null;
            return;
        }
        throw new IllegalArgumentException("invalid version format: " + text);
    }

    public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault) {
        this(protocolName, majorVersion, minorVersion, keepAliveDefault, false);
    }

    private HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault, boolean bytes) {
        if (protocolName == null) {
            throw new NullPointerException("protocolName");
        }
        protocolName = protocolName.trim().toUpperCase();
        if (protocolName.isEmpty()) {
            throw new IllegalArgumentException("empty protocolName");
        }
        int i = 0;
        while (i < protocolName.length()) {
            if (Character.isISOControl(protocolName.charAt(i)) || Character.isWhitespace(protocolName.charAt(i))) {
                throw new IllegalArgumentException("invalid character in protocolName");
            }
            i++;
        }
        if (majorVersion < 0) {
            throw new IllegalArgumentException("negative majorVersion");
        } else if (minorVersion < 0) {
            throw new IllegalArgumentException("negative minorVersion");
        } else {
            this.protocolName = protocolName;
            this.majorVersion = majorVersion;
            this.minorVersion = minorVersion;
            this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
            this.keepAliveDefault = keepAliveDefault;
            if (bytes) {
                this.bytes = this.text.getBytes(CharsetUtil.US_ASCII);
            } else {
                this.bytes = null;
            }
        }
    }

    public String protocolName() {
        return this.protocolName;
    }

    public int majorVersion() {
        return this.majorVersion;
    }

    public int minorVersion() {
        return this.minorVersion;
    }

    public String text() {
        return this.text;
    }

    public boolean isKeepAliveDefault() {
        return this.keepAliveDefault;
    }

    public String toString() {
        return text();
    }

    public int hashCode() {
        return (((protocolName().hashCode() * 31) + majorVersion()) * 31) + minorVersion();
    }

    public boolean equals(Object o) {
        if (!(o instanceof HttpVersion)) {
            return false;
        }
        HttpVersion that = (HttpVersion) o;
        if (minorVersion() == that.minorVersion() && majorVersion() == that.majorVersion() && protocolName().equals(that.protocolName())) {
            return true;
        }
        return false;
    }

    public int compareTo(HttpVersion o) {
        int v = protocolName().compareTo(o.protocolName());
        if (v != 0) {
            return v;
        }
        v = majorVersion() - o.majorVersion();
        if (v != 0) {
            return v;
        }
        return minorVersion() - o.minorVersion();
    }

    void encode(ByteBuf buf) {
        if (this.bytes == null) {
            HttpUtil.encodeAscii0(this.text, buf);
        } else {
            buf.writeBytes(this.bytes);
        }
    }
}
