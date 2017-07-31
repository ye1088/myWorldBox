package io.netty.handler.codec.stomp;

import io.netty.handler.codec.Headers;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public interface StompHeaders extends Headers<CharSequence, CharSequence, StompHeaders> {
    public static final AsciiString ACCEPT_VERSION = new AsciiString((CharSequence) "accept-version");
    public static final AsciiString ACK = new AsciiString((CharSequence) "ack");
    public static final AsciiString CONTENT_LENGTH = new AsciiString((CharSequence) "content-length");
    public static final AsciiString CONTENT_TYPE = new AsciiString((CharSequence) "content-type");
    public static final AsciiString DESTINATION = new AsciiString(Values.DESTINATION);
    public static final AsciiString HEART_BEAT = new AsciiString((CharSequence) "heart-beat");
    public static final AsciiString HOST = new AsciiString((CharSequence) "host");
    public static final AsciiString ID = new AsciiString((CharSequence) "id");
    public static final AsciiString LOGIN = new AsciiString((CharSequence) "login");
    public static final AsciiString MESSAGE = new AsciiString((CharSequence) "message");
    public static final AsciiString MESSAGE_ID = new AsciiString((CharSequence) "message-id");
    public static final AsciiString PASSCODE = new AsciiString((CharSequence) "passcode");
    public static final AsciiString RECEIPT = new AsciiString((CharSequence) "receipt");
    public static final AsciiString RECEIPT_ID = new AsciiString((CharSequence) "receipt-id");
    public static final AsciiString SERVER = new AsciiString((CharSequence) "server");
    public static final AsciiString SESSION = new AsciiString((CharSequence) "session");
    public static final AsciiString SUBSCRIPTION = new AsciiString((CharSequence) "subscription");
    public static final AsciiString TRANSACTION = new AsciiString((CharSequence) "transaction");
    public static final AsciiString VERSION = new AsciiString((CharSequence) "version");

    boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z);

    List<String> getAllAsString(CharSequence charSequence);

    String getAsString(CharSequence charSequence);

    Iterator<Entry<String, String>> iteratorAsString();
}
