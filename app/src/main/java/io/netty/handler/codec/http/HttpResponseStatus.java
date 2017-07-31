package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import org.apache.http.HttpStatus;
import org.apache.tools.zip.UnixStat;

public class HttpResponseStatus implements Comparable<HttpResponseStatus> {
    public static final HttpResponseStatus ACCEPTED = newStatus(202, "Accepted");
    public static final HttpResponseStatus BAD_GATEWAY = newStatus(HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
    public static final HttpResponseStatus BAD_REQUEST = newStatus(400, "Bad Request");
    public static final HttpResponseStatus CONFLICT = newStatus(HttpStatus.SC_CONFLICT, "Conflict");
    public static final HttpResponseStatus CONTINUE = newStatus(100, "Continue");
    public static final HttpResponseStatus CREATED = newStatus(201, "Created");
    public static final HttpResponseStatus EXPECTATION_FAILED = newStatus(HttpStatus.SC_EXPECTATION_FAILED, "Expectation Failed");
    public static final HttpResponseStatus FAILED_DEPENDENCY = newStatus(HttpStatus.SC_FAILED_DEPENDENCY, "Failed Dependency");
    public static final HttpResponseStatus FORBIDDEN = newStatus(403, "Forbidden");
    public static final HttpResponseStatus FOUND = newStatus(302, "Found");
    public static final HttpResponseStatus GATEWAY_TIMEOUT = newStatus(504, "Gateway Timeout");
    public static final HttpResponseStatus GONE = newStatus(HttpStatus.SC_GONE, "Gone");
    public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = newStatus(HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED, "HTTP Version Not Supported");
    public static final HttpResponseStatus INSUFFICIENT_STORAGE = newStatus(HttpStatus.SC_INSUFFICIENT_STORAGE, "Insufficient Storage");
    public static final HttpResponseStatus INTERNAL_SERVER_ERROR = newStatus(500, "Internal Server Error");
    public static final HttpResponseStatus LENGTH_REQUIRED = newStatus(HttpStatus.SC_LENGTH_REQUIRED, "Length Required");
    public static final HttpResponseStatus LOCKED = newStatus(HttpStatus.SC_LOCKED, "Locked");
    public static final HttpResponseStatus METHOD_NOT_ALLOWED = newStatus(405, "Method Not Allowed");
    public static final HttpResponseStatus MISDIRECTED_REQUEST = newStatus(421, "Misdirected Request");
    public static final HttpResponseStatus MOVED_PERMANENTLY = newStatus(301, "Moved Permanently");
    public static final HttpResponseStatus MULTIPLE_CHOICES = newStatus(300, "Multiple Choices");
    public static final HttpResponseStatus MULTI_STATUS = newStatus(207, "Multi-Status");
    public static final HttpResponseStatus NETWORK_AUTHENTICATION_REQUIRED = newStatus(UnixStat.DEFAULT_LINK_PERM, "Network Authentication Required");
    public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = newStatus(203, "Non-Authoritative Information");
    public static final HttpResponseStatus NOT_ACCEPTABLE = newStatus(406, "Not Acceptable");
    public static final HttpResponseStatus NOT_EXTENDED = newStatus(510, "Not Extended");
    public static final HttpResponseStatus NOT_FOUND = newStatus(404, "Not Found");
    public static final HttpResponseStatus NOT_IMPLEMENTED = newStatus(HttpStatus.SC_NOT_IMPLEMENTED, "Not Implemented");
    public static final HttpResponseStatus NOT_MODIFIED = newStatus(304, "Not Modified");
    public static final HttpResponseStatus NO_CONTENT = newStatus(204, "No Content");
    public static final HttpResponseStatus OK = newStatus(200, "OK");
    public static final HttpResponseStatus PARTIAL_CONTENT = newStatus(206, "Partial Content");
    public static final HttpResponseStatus PAYMENT_REQUIRED = newStatus(402, "Payment Required");
    public static final HttpResponseStatus PRECONDITION_FAILED = newStatus(HttpStatus.SC_PRECONDITION_FAILED, "Precondition Failed");
    public static final HttpResponseStatus PRECONDITION_REQUIRED = newStatus(428, "Precondition Required");
    public static final HttpResponseStatus PROCESSING = newStatus(102, "Processing");
    public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = newStatus(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED, "Proxy Authentication Required");
    public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = newStatus(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, "Requested Range Not Satisfiable");
    public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = newStatus(HttpStatus.SC_REQUEST_TOO_LONG, "Request Entity Too Large");
    public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = newStatus(431, "Request Header Fields Too Large");
    public static final HttpResponseStatus REQUEST_TIMEOUT = newStatus(HttpStatus.SC_REQUEST_TIMEOUT, "Request Timeout");
    public static final HttpResponseStatus REQUEST_URI_TOO_LONG = newStatus(HttpStatus.SC_REQUEST_URI_TOO_LONG, "Request-URI Too Long");
    public static final HttpResponseStatus RESET_CONTENT = newStatus(205, "Reset Content");
    public static final HttpResponseStatus SEE_OTHER = newStatus(303, "See Other");
    public static final HttpResponseStatus SERVICE_UNAVAILABLE = newStatus(HttpStatus.SC_SERVICE_UNAVAILABLE, "Service Unavailable");
    public static final HttpResponseStatus SWITCHING_PROTOCOLS = newStatus(101, "Switching Protocols");
    public static final HttpResponseStatus TEMPORARY_REDIRECT = newStatus(307, "Temporary Redirect");
    public static final HttpResponseStatus TOO_MANY_REQUESTS = newStatus(429, "Too Many Requests");
    public static final HttpResponseStatus UNAUTHORIZED = newStatus(401, "Unauthorized");
    public static final HttpResponseStatus UNORDERED_COLLECTION = newStatus(425, "Unordered Collection");
    public static final HttpResponseStatus UNPROCESSABLE_ENTITY = newStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY, "Unprocessable Entity");
    public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = newStatus(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
    public static final HttpResponseStatus UPGRADE_REQUIRED = newStatus(426, "Upgrade Required");
    public static final HttpResponseStatus USE_PROXY = newStatus(305, "Use Proxy");
    public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = newStatus(506, "Variant Also Negotiates");
    private final byte[] bytes;
    private final int code;
    private final AsciiString codeAsText;
    private HttpStatusClass codeClass;
    private final String reasonPhrase;

    private static final class HttpStatusLineProcessor implements ByteProcessor {
        private static final byte ASCII_SPACE = (byte) 32;
        private int i;
        private int state;
        private HttpResponseStatus status;
        private final AsciiString string;

        public HttpStatusLineProcessor(AsciiString string) {
            this.string = string;
        }

        public boolean process(byte value) {
            switch (this.state) {
                case 0:
                    if (value == (byte) 32) {
                        this.state = 1;
                        break;
                    }
                    break;
                case 1:
                    parseStatus(this.i);
                    this.state = 2;
                    return false;
            }
            this.i++;
            return true;
        }

        private void parseStatus(int codeEnd) {
            int code = this.string.parseInt(0, codeEnd);
            this.status = HttpResponseStatus.valueOf(code);
            if (codeEnd < this.string.length()) {
                String actualReason = this.string.toString(codeEnd + 1, this.string.length());
                if (!this.status.reasonPhrase().contentEquals(actualReason)) {
                    this.status = new HttpResponseStatus(code, actualReason);
                }
            }
        }

        public HttpResponseStatus status() {
            if (this.state <= 1) {
                parseStatus(this.string.length());
                this.state = 3;
            }
            return this.status;
        }
    }

    private static HttpResponseStatus newStatus(int statusCode, String reasonPhrase) {
        return new HttpResponseStatus(statusCode, reasonPhrase, true);
    }

    public static HttpResponseStatus valueOf(int code) {
        switch (code) {
            case 100:
                return CONTINUE;
            case 101:
                return SWITCHING_PROTOCOLS;
            case 102:
                return PROCESSING;
            case 200:
                return OK;
            case 201:
                return CREATED;
            case 202:
                return ACCEPTED;
            case 203:
                return NON_AUTHORITATIVE_INFORMATION;
            case 204:
                return NO_CONTENT;
            case 205:
                return RESET_CONTENT;
            case 206:
                return PARTIAL_CONTENT;
            case 207:
                return MULTI_STATUS;
            case 300:
                return MULTIPLE_CHOICES;
            case 301:
                return MOVED_PERMANENTLY;
            case 302:
                return FOUND;
            case 303:
                return SEE_OTHER;
            case 304:
                return NOT_MODIFIED;
            case 305:
                return USE_PROXY;
            case 307:
                return TEMPORARY_REDIRECT;
            case 400:
                return BAD_REQUEST;
            case 401:
                return UNAUTHORIZED;
            case 402:
                return PAYMENT_REQUIRED;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_FOUND;
            case 405:
                return METHOD_NOT_ALLOWED;
            case 406:
                return NOT_ACCEPTABLE;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /*407*/:
                return PROXY_AUTHENTICATION_REQUIRED;
            case HttpStatus.SC_REQUEST_TIMEOUT /*408*/:
                return REQUEST_TIMEOUT;
            case HttpStatus.SC_CONFLICT /*409*/:
                return CONFLICT;
            case HttpStatus.SC_GONE /*410*/:
                return GONE;
            case HttpStatus.SC_LENGTH_REQUIRED /*411*/:
                return LENGTH_REQUIRED;
            case HttpStatus.SC_PRECONDITION_FAILED /*412*/:
                return PRECONDITION_FAILED;
            case HttpStatus.SC_REQUEST_TOO_LONG /*413*/:
                return REQUEST_ENTITY_TOO_LARGE;
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /*414*/:
                return REQUEST_URI_TOO_LONG;
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /*415*/:
                return UNSUPPORTED_MEDIA_TYPE;
            case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE /*416*/:
                return REQUESTED_RANGE_NOT_SATISFIABLE;
            case HttpStatus.SC_EXPECTATION_FAILED /*417*/:
                return EXPECTATION_FAILED;
            case 421:
                return MISDIRECTED_REQUEST;
            case HttpStatus.SC_UNPROCESSABLE_ENTITY /*422*/:
                return UNPROCESSABLE_ENTITY;
            case HttpStatus.SC_LOCKED /*423*/:
                return LOCKED;
            case HttpStatus.SC_FAILED_DEPENDENCY /*424*/:
                return FAILED_DEPENDENCY;
            case 425:
                return UNORDERED_COLLECTION;
            case 426:
                return UPGRADE_REQUIRED;
            case 428:
                return PRECONDITION_REQUIRED;
            case 429:
                return TOO_MANY_REQUESTS;
            case 431:
                return REQUEST_HEADER_FIELDS_TOO_LARGE;
            case 500:
                return INTERNAL_SERVER_ERROR;
            case HttpStatus.SC_NOT_IMPLEMENTED /*501*/:
                return NOT_IMPLEMENTED;
            case HttpStatus.SC_BAD_GATEWAY /*502*/:
                return BAD_GATEWAY;
            case HttpStatus.SC_SERVICE_UNAVAILABLE /*503*/:
                return SERVICE_UNAVAILABLE;
            case 504:
                return GATEWAY_TIMEOUT;
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /*505*/:
                return HTTP_VERSION_NOT_SUPPORTED;
            case 506:
                return VARIANT_ALSO_NEGOTIATES;
            case HttpStatus.SC_INSUFFICIENT_STORAGE /*507*/:
                return INSUFFICIENT_STORAGE;
            case 510:
                return NOT_EXTENDED;
            case UnixStat.DEFAULT_LINK_PERM /*511*/:
                return NETWORK_AUTHENTICATION_REQUIRED;
            default:
                return new HttpResponseStatus(code);
        }
    }

    public static HttpResponseStatus parseLine(CharSequence line) {
        String status = line.toString();
        try {
            int space = status.indexOf(32);
            if (space == -1) {
                return valueOf(Integer.parseInt(status));
            }
            int code = Integer.parseInt(status.substring(0, space));
            String reasonPhrase = status.substring(space + 1);
            HttpResponseStatus responseStatus = valueOf(code);
            return !responseStatus.reasonPhrase().contentEquals(reasonPhrase) ? new HttpResponseStatus(code, reasonPhrase) : responseStatus;
        } catch (Exception e) {
            throw new IllegalArgumentException("malformed status line: " + status, e);
        }
    }

    public static HttpResponseStatus parseLine(AsciiString line) {
        try {
            HttpStatusLineProcessor processor = new HttpStatusLineProcessor(line);
            line.forEachByte(processor);
            HttpResponseStatus status = processor.status();
            if (status != null) {
                return status;
            }
            throw new IllegalArgumentException("unable to get status after parsing input");
        } catch (Exception e) {
            throw new IllegalArgumentException("malformed status line: " + line, e);
        }
    }

    private HttpResponseStatus(int code) {
        this(code, HttpStatusClass.valueOf(code).defaultReasonPhrase() + " (" + code + ')', false);
    }

    public HttpResponseStatus(int code, String reasonPhrase) {
        this(code, reasonPhrase, false);
    }

    private HttpResponseStatus(int code, String reasonPhrase, boolean bytes) {
        if (code < 0) {
            throw new IllegalArgumentException("code: " + code + " (expected: 0+)");
        } else if (reasonPhrase == null) {
            throw new NullPointerException("reasonPhrase");
        } else {
            int i = 0;
            while (i < reasonPhrase.length()) {
                switch (reasonPhrase.charAt(i)) {
                    case '\n':
                    case '\r':
                        throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase);
                    default:
                        i++;
                }
            }
            this.code = code;
            this.codeAsText = new AsciiString(Integer.toString(code));
            this.reasonPhrase = reasonPhrase;
            if (bytes) {
                this.bytes = (code + " " + reasonPhrase).getBytes(CharsetUtil.US_ASCII);
            } else {
                this.bytes = null;
            }
        }
    }

    public int code() {
        return this.code;
    }

    public AsciiString codeAsText() {
        return this.codeAsText;
    }

    public String reasonPhrase() {
        return this.reasonPhrase;
    }

    public HttpStatusClass codeClass() {
        HttpStatusClass type = this.codeClass;
        if (type != null) {
            return type;
        }
        type = HttpStatusClass.valueOf(this.code);
        this.codeClass = type;
        return type;
    }

    public int hashCode() {
        return code();
    }

    public boolean equals(Object o) {
        if ((o instanceof HttpResponseStatus) && code() == ((HttpResponseStatus) o).code()) {
            return true;
        }
        return false;
    }

    public int compareTo(HttpResponseStatus o) {
        return code() - o.code();
    }

    public String toString() {
        return new StringBuilder(this.reasonPhrase.length() + 5).append(this.code).append(HttpConstants.SP_CHAR).append(this.reasonPhrase).toString();
    }

    void encode(ByteBuf buf) {
        if (this.bytes == null) {
            HttpUtil.encodeAscii0(String.valueOf(code()), buf);
            buf.writeByte(32);
            HttpUtil.encodeAscii0(String.valueOf(reasonPhrase()), buf);
            return;
        }
        buf.writeBytes(this.bytes);
    }
}
