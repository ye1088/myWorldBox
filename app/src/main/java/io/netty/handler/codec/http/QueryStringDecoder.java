package io.netty.handler.codec.http;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryStringDecoder {
    private static final int DEFAULT_MAX_PARAMS = 1024;
    private final Charset charset;
    private final boolean hasPath;
    private final int maxParams;
    private int nParams;
    private Map<String, List<String>> params;
    private String path;
    private final String uri;

    public QueryStringDecoder(String uri) {
        this(uri, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(String uri, boolean hasPath) {
        this(uri, HttpConstants.DEFAULT_CHARSET, hasPath);
    }

    public QueryStringDecoder(String uri, Charset charset) {
        this(uri, charset, true);
    }

    public QueryStringDecoder(String uri, Charset charset, boolean hasPath) {
        this(uri, charset, hasPath, 1024);
    }

    public QueryStringDecoder(String uri, Charset charset, boolean hasPath, int maxParams) {
        if (uri == null) {
            throw new NullPointerException("getUri");
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else if (maxParams <= 0) {
            throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
        } else {
            this.uri = uri;
            this.charset = charset;
            this.maxParams = maxParams;
            this.hasPath = hasPath;
        }
    }

    public QueryStringDecoder(URI uri) {
        this(uri, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringDecoder(URI uri, Charset charset) {
        this(uri, charset, 1024);
    }

    public QueryStringDecoder(URI uri, Charset charset, int maxParams) {
        if (uri == null) {
            throw new NullPointerException("getUri");
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else if (maxParams <= 0) {
            throw new IllegalArgumentException("maxParams: " + maxParams + " (expected: a positive integer)");
        } else {
            String rawPath = uri.getRawPath();
            if (rawPath != null) {
                this.hasPath = true;
            } else {
                rawPath = "";
                this.hasPath = false;
            }
            this.uri = rawPath + (uri.getRawQuery() == null ? "" : '?' + uri.getRawQuery());
            this.charset = charset;
            this.maxParams = maxParams;
        }
    }

    public String uri() {
        return this.uri;
    }

    public String path() {
        if (this.path == null) {
            if (this.hasPath) {
                int pathEndPos = this.uri.indexOf(63);
                this.path = decodeComponent(pathEndPos < 0 ? this.uri : this.uri.substring(0, pathEndPos), this.charset);
            } else {
                this.path = "";
            }
        }
        return this.path;
    }

    public Map<String, List<String>> parameters() {
        if (this.params == null) {
            if (this.hasPath) {
                int pathEndPos = this.uri.indexOf(63);
                if (pathEndPos < 0 || pathEndPos >= this.uri.length() - 1) {
                    this.params = Collections.emptyMap();
                } else {
                    decodeParams(this.uri.substring(pathEndPos + 1));
                }
            } else if (this.uri.isEmpty()) {
                this.params = Collections.emptyMap();
            } else {
                decodeParams(this.uri);
            }
        }
        return this.params;
    }

    private void decodeParams(String s) {
        Map<String, List<String>> params = new LinkedHashMap();
        this.params = params;
        this.nParams = 0;
        String name = null;
        int pos = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '=' && name == null) {
                if (pos != i) {
                    name = decodeComponent(s.substring(pos, i), this.charset);
                }
                pos = i + 1;
            } else if (c == '&' || c == ';') {
                if (name != null || pos == i) {
                    if (name != null) {
                        if (addParam(params, name, decodeComponent(s.substring(pos, i), this.charset))) {
                            name = null;
                        } else {
                            return;
                        }
                    }
                } else if (!addParam(params, decodeComponent(s.substring(pos, i), this.charset), "")) {
                    return;
                }
                pos = i + 1;
            }
            i++;
        }
        if (pos != i) {
            if (name == null) {
                addParam(params, decodeComponent(s.substring(pos, i), this.charset), "");
            } else {
                addParam(params, name, decodeComponent(s.substring(pos, i), this.charset));
            }
        } else if (name != null) {
            addParam(params, name, "");
        }
    }

    private boolean addParam(Map<String, List<String>> params, String name, String value) {
        if (this.nParams >= this.maxParams) {
            return false;
        }
        List<String> values = (List) params.get(name);
        if (values == null) {
            values = new ArrayList(1);
            params.put(name, values);
        }
        values.add(value);
        this.nParams++;
        return true;
    }

    public static String decodeComponent(String s) {
        return decodeComponent(s, HttpConstants.DEFAULT_CHARSET);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String decodeComponent(java.lang.String r11, java.nio.charset.Charset r12) {
        /*
        r10 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r9 = 37;
        if (r11 != 0) goto L_0x000b;
    L_0x0007:
        r11 = "";
    L_0x000a:
        return r11;
    L_0x000b:
        r7 = r11.length();
        r4 = 0;
        r3 = 0;
    L_0x0011:
        if (r3 >= r7) goto L_0x001e;
    L_0x0013:
        r1 = r11.charAt(r3);
        if (r1 == r9) goto L_0x001d;
    L_0x0019:
        r8 = 43;
        if (r1 != r8) goto L_0x0037;
    L_0x001d:
        r4 = 1;
    L_0x001e:
        if (r4 == 0) goto L_0x000a;
    L_0x0020:
        r0 = new byte[r7];
        r5 = 0;
        r3 = 0;
        r6 = r5;
    L_0x0025:
        if (r3 >= r7) goto L_0x00e2;
    L_0x0027:
        r1 = r11.charAt(r3);
        switch(r1) {
            case 37: goto L_0x0041;
            case 43: goto L_0x003a;
            default: goto L_0x002e;
        };
    L_0x002e:
        r5 = r6 + 1;
        r8 = (byte) r1;
        r0[r6] = r8;
    L_0x0033:
        r3 = r3 + 1;
        r6 = r5;
        goto L_0x0025;
    L_0x0037:
        r3 = r3 + 1;
        goto L_0x0011;
    L_0x003a:
        r5 = r6 + 1;
        r8 = 32;
        r0[r6] = r8;
        goto L_0x0033;
    L_0x0041:
        r8 = r7 + -1;
        if (r3 != r8) goto L_0x005f;
    L_0x0045:
        r8 = new java.lang.IllegalArgumentException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "unterminated escape sequence at end of string: ";
        r9 = r9.append(r10);
        r9 = r9.append(r11);
        r9 = r9.toString();
        r8.<init>(r9);
        throw r8;
    L_0x005f:
        r3 = r3 + 1;
        r1 = r11.charAt(r3);
        if (r1 != r9) goto L_0x006c;
    L_0x0067:
        r5 = r6 + 1;
        r0[r6] = r9;
        goto L_0x0033;
    L_0x006c:
        r8 = r7 + -1;
        if (r3 != r8) goto L_0x008a;
    L_0x0070:
        r8 = new java.lang.IllegalArgumentException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "partial escape sequence at end of string: ";
        r9 = r9.append(r10);
        r9 = r9.append(r11);
        r9 = r9.toString();
        r8.<init>(r9);
        throw r8;
    L_0x008a:
        r1 = decodeHexNibble(r1);
        r3 = r3 + 1;
        r8 = r11.charAt(r3);
        r2 = decodeHexNibble(r8);
        if (r1 == r10) goto L_0x009c;
    L_0x009a:
        if (r2 != r10) goto L_0x00dc;
    L_0x009c:
        r8 = new java.lang.IllegalArgumentException;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "invalid escape sequence `%";
        r9 = r9.append(r10);
        r10 = r3 + -1;
        r10 = r11.charAt(r10);
        r9 = r9.append(r10);
        r10 = r11.charAt(r3);
        r9 = r9.append(r10);
        r10 = "' at index ";
        r9 = r9.append(r10);
        r10 = r3 + -2;
        r9 = r9.append(r10);
        r10 = " of: ";
        r9 = r9.append(r10);
        r9 = r9.append(r11);
        r9 = r9.toString();
        r8.<init>(r9);
        throw r8;
    L_0x00dc:
        r8 = r1 * 16;
        r8 = r8 + r2;
        r1 = (char) r8;
        goto L_0x002e;
    L_0x00e2:
        r11 = new java.lang.String;
        r8 = 0;
        r11.<init>(r0, r8, r6, r12);
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.QueryStringDecoder.decodeComponent(java.lang.String, java.nio.charset.Charset):java.lang.String");
    }

    private static char decodeHexNibble(char c) {
        if ('0' <= c && c <= '9') {
            return (char) (c - 48);
        }
        if ('a' <= c && c <= 'f') {
            return (char) ((c - 97) + 10);
        }
        if ('A' > c || c > 'F') {
            return '￿';
        }
        return (char) ((c - 65) + 10);
    }
}
