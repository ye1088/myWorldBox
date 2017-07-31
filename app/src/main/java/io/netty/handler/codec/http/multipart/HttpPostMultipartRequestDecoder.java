package io.netty.handler.codec.http.multipart;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.open.SocialConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil.TransferEncodingMechanism;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HttpPostMultipartRequestDecoder implements InterfaceHttpPostRequestDecoder {
    private final List<InterfaceHttpData> bodyListHttpData;
    private int bodyListHttpDataRank;
    private final Map<String, List<InterfaceHttpData>> bodyMapHttpData;
    private Charset charset;
    private Attribute currentAttribute;
    private Map<CharSequence, Attribute> currentFieldAttributes;
    private FileUpload currentFileUpload;
    private MultiPartStatus currentStatus;
    private boolean destroyed;
    private int discardThreshold;
    private final HttpDataFactory factory;
    private boolean isLastChunk;
    private String multipartDataBoundary;
    private String multipartMixedBoundary;
    private final HttpRequest request;
    private ByteBuf undecodedChunk;

    public HttpPostMultipartRequestDecoder(HttpRequest request) {
        this(new DefaultHttpDataFactory((long) DefaultHttpDataFactory.MINSIZE), request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request) {
        this(factory, request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostMultipartRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
        this.bodyListHttpData = new ArrayList();
        this.bodyMapHttpData = new TreeMap(CaseIgnoringComparator.INSTANCE);
        this.currentStatus = MultiPartStatus.NOTSTARTED;
        this.discardThreshold = 10485760;
        if (factory == null) {
            throw new NullPointerException("factory");
        } else if (request == null) {
            throw new NullPointerException(SocialConstants.TYPE_REQUEST);
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.request = request;
            this.charset = charset;
            this.factory = factory;
            setMultipart(this.request.headers().get(HttpHeaderNames.CONTENT_TYPE));
            if (request instanceof HttpContent) {
                offer((HttpContent) request);
                return;
            }
            this.undecodedChunk = Unpooled.buffer();
            parseBody();
        }
    }

    private void setMultipart(String contentType) {
        String[] dataBoundary = HttpPostRequestDecoder.getMultipartDataBoundary(contentType);
        if (dataBoundary != null) {
            this.multipartDataBoundary = dataBoundary[0];
            if (dataBoundary.length > 1 && dataBoundary[1] != null) {
                this.charset = Charset.forName(dataBoundary[1]);
            }
        } else {
            this.multipartDataBoundary = null;
        }
        this.currentStatus = MultiPartStatus.HEADERDELIMITER;
    }

    private void checkDestroyed() {
        if (this.destroyed) {
            throw new IllegalStateException(HttpPostMultipartRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    public boolean isMultipart() {
        checkDestroyed();
        return true;
    }

    public void setDiscardThreshold(int discardThreshold) {
        if (discardThreshold < 0) {
            throw new IllegalArgumentException("discardThreshold must be >= 0");
        }
        this.discardThreshold = discardThreshold;
    }

    public int getDiscardThreshold() {
        return this.discardThreshold;
    }

    public List<InterfaceHttpData> getBodyHttpDatas() {
        checkDestroyed();
        if (this.isLastChunk) {
            return this.bodyListHttpData;
        }
        throw new NotEnoughDataDecoderException();
    }

    public List<InterfaceHttpData> getBodyHttpDatas(String name) {
        checkDestroyed();
        if (this.isLastChunk) {
            return (List) this.bodyMapHttpData.get(name);
        }
        throw new NotEnoughDataDecoderException();
    }

    public InterfaceHttpData getBodyHttpData(String name) {
        checkDestroyed();
        if (this.isLastChunk) {
            List<InterfaceHttpData> list = (List) this.bodyMapHttpData.get(name);
            if (list != null) {
                return (InterfaceHttpData) list.get(0);
            }
            return null;
        }
        throw new NotEnoughDataDecoderException();
    }

    public HttpPostMultipartRequestDecoder offer(HttpContent content) {
        checkDestroyed();
        ByteBuf buf = content.content();
        if (this.undecodedChunk == null) {
            this.undecodedChunk = buf.copy();
        } else {
            this.undecodedChunk.writeBytes(buf);
        }
        if (content instanceof LastHttpContent) {
            this.isLastChunk = true;
        }
        parseBody();
        if (this.undecodedChunk != null && this.undecodedChunk.writerIndex() > this.discardThreshold) {
            this.undecodedChunk.discardReadBytes();
        }
        return this;
    }

    public boolean hasNext() {
        checkDestroyed();
        if (this.currentStatus != MultiPartStatus.EPILOGUE || this.bodyListHttpDataRank < this.bodyListHttpData.size()) {
            return !this.bodyListHttpData.isEmpty() && this.bodyListHttpDataRank < this.bodyListHttpData.size();
        } else {
            throw new EndOfDataDecoderException();
        }
    }

    public InterfaceHttpData next() {
        checkDestroyed();
        if (!hasNext()) {
            return null;
        }
        List list = this.bodyListHttpData;
        int i = this.bodyListHttpDataRank;
        this.bodyListHttpDataRank = i + 1;
        return (InterfaceHttpData) list.get(i);
    }

    public InterfaceHttpData currentPartialHttpData() {
        if (this.currentFileUpload != null) {
            return this.currentFileUpload;
        }
        return this.currentAttribute;
    }

    private void parseBody() {
        if (this.currentStatus != MultiPartStatus.PREEPILOGUE && this.currentStatus != MultiPartStatus.EPILOGUE) {
            parseBodyMultipart();
        } else if (this.isLastChunk) {
            this.currentStatus = MultiPartStatus.EPILOGUE;
        }
    }

    protected void addHttpData(InterfaceHttpData data) {
        if (data != null) {
            List<InterfaceHttpData> datas = (List) this.bodyMapHttpData.get(data.getName());
            if (datas == null) {
                datas = new ArrayList(1);
                this.bodyMapHttpData.put(data.getName(), datas);
            }
            datas.add(data);
            this.bodyListHttpData.add(data);
        }
    }

    private void parseBodyMultipart() {
        if (this.undecodedChunk != null && this.undecodedChunk.readableBytes() != 0) {
            InterfaceHttpData data = decodeMultipart(this.currentStatus);
            while (data != null) {
                addHttpData(data);
                if (this.currentStatus != MultiPartStatus.PREEPILOGUE && this.currentStatus != MultiPartStatus.EPILOGUE) {
                    data = decodeMultipart(this.currentStatus);
                } else {
                    return;
                }
            }
        }
    }

    private InterfaceHttpData decodeMultipart(MultiPartStatus state) {
        switch (state) {
            case NOTSTARTED:
                throw new ErrorDataDecoderException("Should not be called with the current getStatus");
            case PREAMBLE:
                throw new ErrorDataDecoderException("Should not be called with the current getStatus");
            case HEADERDELIMITER:
                return findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
            case DISPOSITION:
                return findMultipartDisposition();
            case FIELD:
                Charset localCharset = null;
                Attribute charsetAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
                if (charsetAttribute != null) {
                    try {
                        localCharset = Charset.forName(charsetAttribute.getValue());
                    } catch (Throwable e) {
                        throw new ErrorDataDecoderException(e);
                    } catch (Throwable e2) {
                        throw new ErrorDataDecoderException(e2);
                    }
                }
                Attribute nameAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.NAME);
                if (this.currentAttribute == null) {
                    long size;
                    Attribute lengthAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
                    if (lengthAttribute != null) {
                        try {
                            size = Long.parseLong(lengthAttribute.getValue());
                        } catch (Throwable e22) {
                            throw new ErrorDataDecoderException(e22);
                        } catch (NumberFormatException e3) {
                            size = 0;
                        }
                    } else {
                        size = 0;
                    }
                    if (size > 0) {
                        try {
                            this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()), size);
                        } catch (Throwable e222) {
                            throw new ErrorDataDecoderException(e222);
                        } catch (Throwable e2222) {
                            throw new ErrorDataDecoderException(e2222);
                        } catch (Throwable e22222) {
                            throw new ErrorDataDecoderException(e22222);
                        }
                    }
                    this.currentAttribute = this.factory.createAttribute(this.request, cleanString(nameAttribute.getValue()));
                    if (localCharset != null) {
                        this.currentAttribute.setCharset(localCharset);
                    }
                }
                try {
                    loadFieldMultipart(this.multipartDataBoundary);
                    InterfaceHttpData finalAttribute = this.currentAttribute;
                    this.currentAttribute = null;
                    this.currentFieldAttributes = null;
                    this.currentStatus = MultiPartStatus.HEADERDELIMITER;
                    return finalAttribute;
                } catch (NotEnoughDataDecoderException e4) {
                    return null;
                }
            case FILEUPLOAD:
                return getFileUpload(this.multipartDataBoundary);
            case MIXEDDELIMITER:
                return findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
            case MIXEDDISPOSITION:
                return findMultipartDisposition();
            case MIXEDFILEUPLOAD:
                return getFileUpload(this.multipartMixedBoundary);
            case PREEPILOGUE:
                return null;
            case EPILOGUE:
                return null;
            default:
                throw new ErrorDataDecoderException("Shouldn't reach here.");
        }
    }

    void skipControlCharacters() {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            while (sao.pos < sao.limit) {
                byte[] bArr = sao.bytes;
                int i = sao.pos;
                sao.pos = i + 1;
                char c = (char) (bArr[i] & 255);
                if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                    sao.setReadPosition(1);
                    return;
                }
            }
            throw new NotEnoughDataDecoderException("Access out of bounds");
        } catch (SeekAheadNoBackArrayException e) {
            try {
                skipControlCharactersStandard();
            } catch (Throwable e1) {
                throw new NotEnoughDataDecoderException(e1);
            }
        }
    }

    void skipControlCharactersStandard() {
        while (true) {
            char c = (char) this.undecodedChunk.readUnsignedByte();
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                return;
            }
        }
    }

    private InterfaceHttpData findMultipartDelimiter(String delimiter, MultiPartStatus dispositionStatus, MultiPartStatus closeDelimiterStatus) {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            skipControlCharacters();
            skipOneLine();
            try {
                String newline = readDelimiter(delimiter);
                if (newline.equals(delimiter)) {
                    this.currentStatus = dispositionStatus;
                    return decodeMultipart(dispositionStatus);
                } else if (newline.equals(delimiter + "--")) {
                    this.currentStatus = closeDelimiterStatus;
                    if (this.currentStatus != MultiPartStatus.HEADERDELIMITER) {
                        return null;
                    }
                    this.currentFieldAttributes = null;
                    return decodeMultipart(MultiPartStatus.HEADERDELIMITER);
                } else {
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new ErrorDataDecoderException("No Multipart delimiter found");
                }
            } catch (NotEnoughDataDecoderException e) {
                this.undecodedChunk.readerIndex(readerIndex);
                return null;
            }
        } catch (NotEnoughDataDecoderException e2) {
            this.undecodedChunk.readerIndex(readerIndex);
            return null;
        }
    }

    private InterfaceHttpData findMultipartDisposition() {
        int readerIndex = this.undecodedChunk.readerIndex();
        if (this.currentStatus == MultiPartStatus.DISPOSITION) {
            this.currentFieldAttributes = new TreeMap(CaseIgnoringComparator.INSTANCE);
        }
        while (!skipOneLine()) {
            try {
                skipControlCharacters();
                String newline = readLine();
                String[] contents = splitMultipartHeader(newline);
                int i;
                Attribute attribute;
                if (HttpHeaderNames.CONTENT_DISPOSITION.contentEqualsIgnoreCase(contents[0])) {
                    boolean checkSecondArg;
                    if (this.currentStatus == MultiPartStatus.DISPOSITION) {
                        checkSecondArg = HttpHeaderValues.FORM_DATA.contentEqualsIgnoreCase(contents[1]);
                    } else {
                        checkSecondArg = HttpHeaderValues.ATTACHMENT.contentEqualsIgnoreCase(contents[1]) || HttpHeaderValues.FILE.contentEqualsIgnoreCase(contents[1]);
                    }
                    if (checkSecondArg) {
                        i = 2;
                        while (i < contents.length) {
                            String[] values = contents[i].split(SimpleComparison.EQUAL_TO_OPERATION, 2);
                            try {
                                String name = cleanString(values[0]);
                                String value = values[1];
                                if (HttpHeaderValues.FILENAME.contentEquals(name)) {
                                    value = value.substring(1, value.length() - 1);
                                } else {
                                    value = cleanString(value);
                                }
                                attribute = this.factory.createAttribute(this.request, name, value);
                                this.currentFieldAttributes.put(attribute.getName(), attribute);
                                i++;
                            } catch (Throwable e) {
                                throw new ErrorDataDecoderException(e);
                            } catch (Throwable e2) {
                                throw new ErrorDataDecoderException(e2);
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                } else if (HttpHeaderNames.CONTENT_TRANSFER_ENCODING.contentEqualsIgnoreCase(contents[0])) {
                    try {
                        this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_TRANSFER_ENCODING, this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_TRANSFER_ENCODING.toString(), cleanString(contents[1])));
                    } catch (Throwable e22) {
                        throw new ErrorDataDecoderException(e22);
                    } catch (Throwable e222) {
                        throw new ErrorDataDecoderException(e222);
                    }
                } else if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(contents[0])) {
                    try {
                        this.currentFieldAttributes.put(HttpHeaderNames.CONTENT_LENGTH, this.factory.createAttribute(this.request, HttpHeaderNames.CONTENT_LENGTH.toString(), cleanString(contents[1])));
                    } catch (Throwable e2222) {
                        throw new ErrorDataDecoderException(e2222);
                    } catch (Throwable e22222) {
                        throw new ErrorDataDecoderException(e22222);
                    }
                } else if (!HttpHeaderNames.CONTENT_TYPE.contentEqualsIgnoreCase(contents[0])) {
                    throw new ErrorDataDecoderException("Unknown Params: " + newline);
                } else if (!HttpHeaderValues.MULTIPART_MIXED.contentEqualsIgnoreCase(contents[1])) {
                    for (i = 1; i < contents.length; i++) {
                        if (contents[i].toLowerCase().startsWith(HttpHeaderValues.CHARSET.toString())) {
                            try {
                                this.currentFieldAttributes.put(HttpHeaderValues.CHARSET, this.factory.createAttribute(this.request, HttpHeaderValues.CHARSET.toString(), cleanString(StringUtil.substringAfter(contents[i], '='))));
                            } catch (Throwable e222222) {
                                throw new ErrorDataDecoderException(e222222);
                            } catch (Throwable e2222222) {
                                throw new ErrorDataDecoderException(e2222222);
                            }
                        }
                        try {
                            attribute = this.factory.createAttribute(this.request, cleanString(contents[0]), contents[i]);
                            this.currentFieldAttributes.put(attribute.getName(), attribute);
                        } catch (Throwable e22222222) {
                            throw new ErrorDataDecoderException(e22222222);
                        } catch (Throwable e222222222) {
                            throw new ErrorDataDecoderException(e222222222);
                        }
                    }
                    continue;
                } else if (this.currentStatus == MultiPartStatus.DISPOSITION) {
                    this.multipartMixedBoundary = "--" + StringUtil.substringAfter(contents[2], '=');
                    this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
                    return decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
                } else {
                    throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
                }
            } catch (NotEnoughDataDecoderException e3) {
                this.undecodedChunk.readerIndex(readerIndex);
                return null;
            }
        }
        Attribute filenameAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
        if (this.currentStatus == MultiPartStatus.DISPOSITION) {
            if (filenameAttribute != null) {
                this.currentStatus = MultiPartStatus.FILEUPLOAD;
                return decodeMultipart(MultiPartStatus.FILEUPLOAD);
            }
            this.currentStatus = MultiPartStatus.FIELD;
            return decodeMultipart(MultiPartStatus.FIELD);
        } else if (filenameAttribute != null) {
            this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
            return decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
        } else {
            throw new ErrorDataDecoderException("Filename not found");
        }
    }

    protected InterfaceHttpData getFileUpload(String delimiter) {
        Attribute encoding = (Attribute) this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        Charset localCharset = this.charset;
        TransferEncodingMechanism mechanism = TransferEncodingMechanism.BIT7;
        if (encoding != null) {
            try {
                String code = encoding.getValue().toLowerCase();
                if (code.equals(TransferEncodingMechanism.BIT7.value())) {
                    localCharset = CharsetUtil.US_ASCII;
                } else if (code.equals(TransferEncodingMechanism.BIT8.value())) {
                    localCharset = CharsetUtil.ISO_8859_1;
                    mechanism = TransferEncodingMechanism.BIT8;
                } else if (code.equals(TransferEncodingMechanism.BINARY.value())) {
                    mechanism = TransferEncodingMechanism.BINARY;
                } else {
                    throw new ErrorDataDecoderException("TransferEncoding Unknown: " + code);
                }
            } catch (Throwable e) {
                throw new ErrorDataDecoderException(e);
            }
        }
        Attribute charsetAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.CHARSET);
        if (charsetAttribute != null) {
            try {
                localCharset = Charset.forName(charsetAttribute.getValue());
            } catch (Throwable e2) {
                throw new ErrorDataDecoderException(e2);
            } catch (Throwable e22) {
                throw new ErrorDataDecoderException(e22);
            }
        }
        if (this.currentFileUpload == null) {
            long size;
            String contentType;
            Attribute filenameAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.FILENAME);
            Attribute nameAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderValues.NAME);
            Attribute contentTypeAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_TYPE);
            Attribute lengthAttribute = (Attribute) this.currentFieldAttributes.get(HttpHeaderNames.CONTENT_LENGTH);
            if (lengthAttribute != null) {
                try {
                    size = Long.parseLong(lengthAttribute.getValue());
                } catch (Throwable e222) {
                    throw new ErrorDataDecoderException(e222);
                } catch (NumberFormatException e3) {
                    size = 0;
                }
            } else {
                size = 0;
            }
            if (contentTypeAttribute != null) {
                try {
                    contentType = contentTypeAttribute.getValue();
                } catch (Throwable e2222) {
                    throw new ErrorDataDecoderException(e2222);
                } catch (Throwable e22222) {
                    throw new ErrorDataDecoderException(e22222);
                } catch (Throwable e222222) {
                    throw new ErrorDataDecoderException(e222222);
                }
            }
            contentType = "application/octet-stream";
            this.currentFileUpload = this.factory.createFileUpload(this.request, cleanString(nameAttribute.getValue()), cleanString(filenameAttribute.getValue()), contentType, mechanism.value(), localCharset, size);
        }
        try {
            readFileUploadByteMultipart(delimiter);
            if (!this.currentFileUpload.isCompleted()) {
                return null;
            }
            if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
                this.currentStatus = MultiPartStatus.HEADERDELIMITER;
                this.currentFieldAttributes = null;
            } else {
                this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
                cleanMixedAttributes();
            }
            FileUpload fileUpload = this.currentFileUpload;
            this.currentFileUpload = null;
            return fileUpload;
        } catch (NotEnoughDataDecoderException e4) {
            return null;
        }
    }

    public void destroy() {
        checkDestroyed();
        cleanFiles();
        this.destroyed = true;
        if (this.undecodedChunk != null && this.undecodedChunk.refCnt() > 0) {
            this.undecodedChunk.release();
            this.undecodedChunk = null;
        }
        for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); i++) {
            ((InterfaceHttpData) this.bodyListHttpData.get(i)).release();
        }
    }

    public void cleanFiles() {
        checkDestroyed();
        this.factory.cleanRequestHttpData(this.request);
    }

    public void removeHttpDataFromClean(InterfaceHttpData data) {
        checkDestroyed();
        this.factory.removeHttpDataFromClean(this.request, data);
    }

    private void cleanMixedAttributes() {
        this.currentFieldAttributes.remove(HttpHeaderValues.CHARSET);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_LENGTH);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        this.currentFieldAttributes.remove(HttpHeaderNames.CONTENT_TYPE);
        this.currentFieldAttributes.remove(HttpHeaderValues.FILENAME);
    }

    private String readLineStandard() {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            ByteBuf line = Unpooled.buffer(64);
            while (this.undecodedChunk.isReadable()) {
                byte nextByte = this.undecodedChunk.readByte();
                if (nextByte == (byte) 13) {
                    if (this.undecodedChunk.getByte(this.undecodedChunk.readerIndex()) == (byte) 10) {
                        this.undecodedChunk.readByte();
                        return line.toString(this.charset);
                    }
                    line.writeByte(13);
                } else if (nextByte == (byte) 10) {
                    return line.toString(this.charset);
                } else {
                    line.writeByte(nextByte);
                }
            }
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException();
        } catch (Throwable e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException(e);
        }
    }

    private String readLine() {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            int readerIndex = this.undecodedChunk.readerIndex();
            ByteBuf line = Unpooled.buffer(64);
            while (sao.pos < sao.limit) {
                byte[] bArr = sao.bytes;
                int i = sao.pos;
                sao.pos = i + 1;
                byte nextByte = bArr[i];
                if (nextByte == (byte) 13) {
                    if (sao.pos < sao.limit) {
                        bArr = sao.bytes;
                        i = sao.pos;
                        sao.pos = i + 1;
                        if (bArr[i] == (byte) 10) {
                            sao.setReadPosition(0);
                            return line.toString(this.charset);
                        }
                        sao.pos--;
                        line.writeByte(13);
                    } else {
                        try {
                            line.writeByte(nextByte);
                        } catch (Throwable e) {
                            this.undecodedChunk.readerIndex(readerIndex);
                            throw new NotEnoughDataDecoderException(e);
                        }
                    }
                } else if (nextByte == (byte) 10) {
                    sao.setReadPosition(0);
                    return line.toString(this.charset);
                } else {
                    line.writeByte(nextByte);
                }
            }
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException();
        } catch (SeekAheadNoBackArrayException e2) {
            return readLineStandard();
        }
    }

    private String readDelimiterStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        try {
            StringBuilder sb = new StringBuilder(64);
            int delimiterPos = 0;
            int len = delimiter.length();
            while (this.undecodedChunk.isReadable() && delimiterPos < len) {
                char nextByte = this.undecodedChunk.readByte();
                if (nextByte == delimiter.charAt(delimiterPos)) {
                    delimiterPos++;
                    sb.append((char) nextByte);
                } else {
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new NotEnoughDataDecoderException();
                }
            }
            if (this.undecodedChunk.isReadable()) {
                byte nextByte2 = this.undecodedChunk.readByte();
                if (nextByte2 == (byte) 13) {
                    if (this.undecodedChunk.readByte() == (byte) 10) {
                        return sb.toString();
                    }
                    this.undecodedChunk.readerIndex(readerIndex);
                    throw new NotEnoughDataDecoderException();
                } else if (nextByte2 == (byte) 10) {
                    return sb.toString();
                } else {
                    if (nextByte2 == (byte) 45) {
                        sb.append('-');
                        if (this.undecodedChunk.readByte() == (byte) 45) {
                            sb.append('-');
                            if (!this.undecodedChunk.isReadable()) {
                                return sb.toString();
                            }
                            nextByte2 = this.undecodedChunk.readByte();
                            if (nextByte2 == (byte) 13) {
                                if (this.undecodedChunk.readByte() == (byte) 10) {
                                    return sb.toString();
                                }
                                this.undecodedChunk.readerIndex(readerIndex);
                                throw new NotEnoughDataDecoderException();
                            } else if (nextByte2 == (byte) 10) {
                                return sb.toString();
                            } else {
                                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                                return sb.toString();
                            }
                        }
                    }
                }
            }
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException();
        } catch (Throwable e) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException(e);
        }
    }

    private String readDelimiter(String delimiter) {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            int readerIndex = this.undecodedChunk.readerIndex();
            int delimiterPos = 0;
            int len = delimiter.length();
            try {
                byte[] bArr;
                int i;
                StringBuilder sb = new StringBuilder(64);
                while (sao.pos < sao.limit && delimiterPos < len) {
                    bArr = sao.bytes;
                    i = sao.pos;
                    sao.pos = i + 1;
                    char nextByte = bArr[i];
                    if (nextByte == delimiter.charAt(delimiterPos)) {
                        delimiterPos++;
                        sb.append((char) nextByte);
                    } else {
                        this.undecodedChunk.readerIndex(readerIndex);
                        throw new NotEnoughDataDecoderException();
                    }
                }
                if (sao.pos < sao.limit) {
                    bArr = sao.bytes;
                    i = sao.pos;
                    sao.pos = i + 1;
                    byte nextByte2 = bArr[i];
                    if (nextByte2 == (byte) 13) {
                        if (sao.pos < sao.limit) {
                            bArr = sao.bytes;
                            i = sao.pos;
                            sao.pos = i + 1;
                            if (bArr[i] == (byte) 10) {
                                sao.setReadPosition(0);
                                return sb.toString();
                            }
                            this.undecodedChunk.readerIndex(readerIndex);
                            throw new NotEnoughDataDecoderException();
                        }
                        this.undecodedChunk.readerIndex(readerIndex);
                        throw new NotEnoughDataDecoderException();
                    } else if (nextByte2 == (byte) 10) {
                        sao.setReadPosition(0);
                        return sb.toString();
                    } else if (nextByte2 == (byte) 45) {
                        sb.append('-');
                        if (sao.pos < sao.limit) {
                            bArr = sao.bytes;
                            i = sao.pos;
                            sao.pos = i + 1;
                            if (bArr[i] == (byte) 45) {
                                sb.append('-');
                                if (sao.pos < sao.limit) {
                                    bArr = sao.bytes;
                                    i = sao.pos;
                                    sao.pos = i + 1;
                                    nextByte2 = bArr[i];
                                    if (nextByte2 == (byte) 13) {
                                        if (sao.pos < sao.limit) {
                                            bArr = sao.bytes;
                                            i = sao.pos;
                                            sao.pos = i + 1;
                                            if (bArr[i] == (byte) 10) {
                                                sao.setReadPosition(0);
                                                return sb.toString();
                                            }
                                            this.undecodedChunk.readerIndex(readerIndex);
                                            throw new NotEnoughDataDecoderException();
                                        }
                                        this.undecodedChunk.readerIndex(readerIndex);
                                        throw new NotEnoughDataDecoderException();
                                    } else if (nextByte2 == (byte) 10) {
                                        sao.setReadPosition(0);
                                        return sb.toString();
                                    } else {
                                        sao.setReadPosition(1);
                                        return sb.toString();
                                    }
                                }
                                sao.setReadPosition(0);
                                return sb.toString();
                            }
                        }
                    }
                }
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException();
            } catch (Throwable e) {
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException(e);
            }
        } catch (SeekAheadNoBackArrayException e2) {
            return readDelimiterStandard(delimiter);
        }
    }

    private void readFileUploadByteMultipartStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        boolean newLine = true;
        int index = 0;
        int lastPosition = this.undecodedChunk.readerIndex();
        boolean found = false;
        while (this.undecodedChunk.isReadable()) {
            byte nextByte = this.undecodedChunk.readByte();
            if (newLine) {
                if (nextByte == delimiter.codePointAt(index)) {
                    index++;
                    if (delimiter.length() == index) {
                        found = true;
                        break;
                    }
                } else {
                    newLine = false;
                    index = 0;
                    if (nextByte == (byte) 13) {
                        if (this.undecodedChunk.isReadable()) {
                            if (this.undecodedChunk.readByte() == (byte) 10) {
                                newLine = true;
                                index = 0;
                                lastPosition = this.undecodedChunk.readerIndex() - 2;
                            } else {
                                lastPosition = this.undecodedChunk.readerIndex() - 1;
                                this.undecodedChunk.readerIndex(lastPosition);
                            }
                        }
                    } else if (nextByte == (byte) 10) {
                        newLine = true;
                        index = 0;
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                    } else {
                        lastPosition = this.undecodedChunk.readerIndex();
                    }
                }
            } else if (nextByte == (byte) 13) {
                if (this.undecodedChunk.isReadable()) {
                    if (this.undecodedChunk.readByte() == (byte) 10) {
                        newLine = true;
                        index = 0;
                        lastPosition = this.undecodedChunk.readerIndex() - 2;
                    } else {
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                        this.undecodedChunk.readerIndex(lastPosition);
                    }
                }
            } else if (nextByte == (byte) 10) {
                newLine = true;
                index = 0;
                lastPosition = this.undecodedChunk.readerIndex() - 1;
            } else {
                lastPosition = this.undecodedChunk.readerIndex();
            }
        }
        ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
        if (found) {
            try {
                this.currentFileUpload.addContent(buffer, true);
                this.undecodedChunk.readerIndex(lastPosition);
                return;
            } catch (Throwable e) {
                throw new ErrorDataDecoderException(e);
            }
        }
        try {
            this.currentFileUpload.addContent(buffer, false);
            this.undecodedChunk.readerIndex(lastPosition);
            throw new NotEnoughDataDecoderException();
        } catch (Throwable e2) {
            throw new ErrorDataDecoderException(e2);
        }
    }

    private void readFileUploadByteMultipart(String delimiter) {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            int readerIndex = this.undecodedChunk.readerIndex();
            boolean newLine = true;
            int index = 0;
            int lastrealpos = sao.pos;
            boolean found = false;
            while (sao.pos < sao.limit) {
                byte[] bArr = sao.bytes;
                int i = sao.pos;
                sao.pos = i + 1;
                byte nextByte = bArr[i];
                if (newLine) {
                    if (nextByte == delimiter.codePointAt(index)) {
                        index++;
                        if (delimiter.length() == index) {
                            found = true;
                            break;
                        }
                    } else {
                        newLine = false;
                        index = 0;
                        if (nextByte == (byte) 13) {
                            if (sao.pos < sao.limit) {
                                bArr = sao.bytes;
                                i = sao.pos;
                                sao.pos = i + 1;
                                if (bArr[i] == (byte) 10) {
                                    newLine = true;
                                    index = 0;
                                    lastrealpos = sao.pos - 2;
                                } else {
                                    sao.pos--;
                                    lastrealpos = sao.pos;
                                }
                            }
                        } else if (nextByte == (byte) 10) {
                            newLine = true;
                            index = 0;
                            lastrealpos = sao.pos - 1;
                        } else {
                            lastrealpos = sao.pos;
                        }
                    }
                } else if (nextByte == (byte) 13) {
                    if (sao.pos < sao.limit) {
                        bArr = sao.bytes;
                        i = sao.pos;
                        sao.pos = i + 1;
                        if (bArr[i] == (byte) 10) {
                            newLine = true;
                            index = 0;
                            lastrealpos = sao.pos - 2;
                        } else {
                            sao.pos--;
                            lastrealpos = sao.pos;
                        }
                    }
                } else if (nextByte == (byte) 10) {
                    newLine = true;
                    index = 0;
                    lastrealpos = sao.pos - 1;
                } else {
                    lastrealpos = sao.pos;
                }
            }
            int lastPosition = sao.getReadPosition(lastrealpos);
            ByteBuf buffer = this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex);
            if (found) {
                try {
                    this.currentFileUpload.addContent(buffer, true);
                    this.undecodedChunk.readerIndex(lastPosition);
                    return;
                } catch (Throwable e) {
                    throw new ErrorDataDecoderException(e);
                }
            }
            try {
                this.currentFileUpload.addContent(buffer, false);
                this.undecodedChunk.readerIndex(lastPosition);
                throw new NotEnoughDataDecoderException();
            } catch (Throwable e2) {
                throw new ErrorDataDecoderException(e2);
            }
        } catch (SeekAheadNoBackArrayException e3) {
            readFileUploadByteMultipartStandard(delimiter);
        }
    }

    private void loadFieldMultipartStandard(String delimiter) {
        int readerIndex = this.undecodedChunk.readerIndex();
        boolean newLine = true;
        int index = 0;
        try {
            int lastPosition = this.undecodedChunk.readerIndex();
            boolean found = false;
            while (this.undecodedChunk.isReadable()) {
                byte nextByte = this.undecodedChunk.readByte();
                if (newLine) {
                    if (nextByte == delimiter.codePointAt(index)) {
                        index++;
                        if (delimiter.length() == index) {
                            found = true;
                            break;
                        }
                    } else {
                        newLine = false;
                        index = 0;
                        if (nextByte == (byte) 13) {
                            if (!this.undecodedChunk.isReadable()) {
                                lastPosition = this.undecodedChunk.readerIndex() - 1;
                            } else if (this.undecodedChunk.readByte() == (byte) 10) {
                                newLine = true;
                                index = 0;
                                lastPosition = this.undecodedChunk.readerIndex() - 2;
                            } else {
                                lastPosition = this.undecodedChunk.readerIndex() - 1;
                                this.undecodedChunk.readerIndex(lastPosition);
                            }
                        } else if (nextByte == (byte) 10) {
                            newLine = true;
                            index = 0;
                            lastPosition = this.undecodedChunk.readerIndex() - 1;
                        } else {
                            lastPosition = this.undecodedChunk.readerIndex();
                        }
                    }
                } else if (nextByte == (byte) 13) {
                    if (!this.undecodedChunk.isReadable()) {
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                    } else if (this.undecodedChunk.readByte() == (byte) 10) {
                        newLine = true;
                        index = 0;
                        lastPosition = this.undecodedChunk.readerIndex() - 2;
                    } else {
                        lastPosition = this.undecodedChunk.readerIndex() - 1;
                        this.undecodedChunk.readerIndex(lastPosition);
                    }
                } else if (nextByte == (byte) 10) {
                    newLine = true;
                    index = 0;
                    lastPosition = this.undecodedChunk.readerIndex() - 1;
                } else {
                    lastPosition = this.undecodedChunk.readerIndex();
                }
            }
            if (found) {
                this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
                this.undecodedChunk.readerIndex(lastPosition);
                return;
            }
            this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
            this.undecodedChunk.readerIndex(lastPosition);
            throw new NotEnoughDataDecoderException();
        } catch (Throwable e) {
            throw new ErrorDataDecoderException(e);
        } catch (Throwable e2) {
            throw new ErrorDataDecoderException(e2);
        } catch (Throwable e22) {
            this.undecodedChunk.readerIndex(readerIndex);
            throw new NotEnoughDataDecoderException(e22);
        }
    }

    private void loadFieldMultipart(String delimiter) {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            int readerIndex = this.undecodedChunk.readerIndex();
            boolean newLine = true;
            int index = 0;
            try {
                int lastrealpos = sao.pos;
                boolean found = false;
                while (sao.pos < sao.limit) {
                    byte[] bArr = sao.bytes;
                    int i = sao.pos;
                    sao.pos = i + 1;
                    byte nextByte = bArr[i];
                    if (newLine) {
                        if (nextByte == delimiter.codePointAt(index)) {
                            index++;
                            if (delimiter.length() == index) {
                                found = true;
                                break;
                            }
                        } else {
                            newLine = false;
                            index = 0;
                            if (nextByte == (byte) 13) {
                                if (sao.pos < sao.limit) {
                                    bArr = sao.bytes;
                                    i = sao.pos;
                                    sao.pos = i + 1;
                                    if (bArr[i] == (byte) 10) {
                                        newLine = true;
                                        index = 0;
                                        lastrealpos = sao.pos - 2;
                                    } else {
                                        sao.pos--;
                                        lastrealpos = sao.pos;
                                    }
                                }
                            } else if (nextByte == (byte) 10) {
                                newLine = true;
                                index = 0;
                                lastrealpos = sao.pos - 1;
                            } else {
                                lastrealpos = sao.pos;
                            }
                        }
                    } else if (nextByte == (byte) 13) {
                        if (sao.pos < sao.limit) {
                            bArr = sao.bytes;
                            i = sao.pos;
                            sao.pos = i + 1;
                            if (bArr[i] == (byte) 10) {
                                newLine = true;
                                index = 0;
                                lastrealpos = sao.pos - 2;
                            } else {
                                sao.pos--;
                                lastrealpos = sao.pos;
                            }
                        }
                    } else if (nextByte == (byte) 10) {
                        newLine = true;
                        index = 0;
                        lastrealpos = sao.pos - 1;
                    } else {
                        lastrealpos = sao.pos;
                    }
                }
                int lastPosition = sao.getReadPosition(lastrealpos);
                if (found) {
                    this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), true);
                    this.undecodedChunk.readerIndex(lastPosition);
                    return;
                }
                this.currentAttribute.addContent(this.undecodedChunk.copy(readerIndex, lastPosition - readerIndex), false);
                this.undecodedChunk.readerIndex(lastPosition);
                throw new NotEnoughDataDecoderException();
            } catch (Throwable e) {
                throw new ErrorDataDecoderException(e);
            } catch (Throwable e2) {
                throw new ErrorDataDecoderException(e2);
            } catch (Throwable e22) {
                this.undecodedChunk.readerIndex(readerIndex);
                throw new NotEnoughDataDecoderException(e22);
            }
        } catch (SeekAheadNoBackArrayException e3) {
            loadFieldMultipartStandard(delimiter);
        }
    }

    private static String cleanString(String field) {
        StringBuilder sb = new StringBuilder(field.length());
        for (int i = 0; i < field.length(); i++) {
            char nextChar = field.charAt(i);
            if (nextChar == ':') {
                sb.append(HttpConstants.SP_CHAR);
            } else if (nextChar == StringUtil.COMMA) {
                sb.append(HttpConstants.SP_CHAR);
            } else if (nextChar == '=') {
                sb.append(HttpConstants.SP_CHAR);
            } else if (nextChar == ';') {
                sb.append(HttpConstants.SP_CHAR);
            } else if (nextChar == '\t') {
                sb.append(HttpConstants.SP_CHAR);
            } else if (nextChar != StringUtil.DOUBLE_QUOTE) {
                sb.append(nextChar);
            }
        }
        return sb.toString().trim();
    }

    private boolean skipOneLine() {
        if (!this.undecodedChunk.isReadable()) {
            return false;
        }
        byte nextByte = this.undecodedChunk.readByte();
        if (nextByte == (byte) 13) {
            if (!this.undecodedChunk.isReadable()) {
                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
                return false;
            } else if (this.undecodedChunk.readByte() == (byte) 10) {
                return true;
            } else {
                this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
                return false;
            }
        } else if (nextByte == (byte) 10) {
            return true;
        } else {
            this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
            return false;
        }
    }

    private static String[] splitMultipartHeader(String sb) {
        ArrayList<String> headers = new ArrayList(1);
        int nameStart = HttpPostBodyUtil.findNonWhitespace(sb, 0);
        int nameEnd = nameStart;
        while (nameEnd < sb.length()) {
            char ch = sb.charAt(nameEnd);
            if (ch == ':' || Character.isWhitespace(ch)) {
                break;
            }
            nameEnd++;
        }
        int colonEnd = nameEnd;
        while (colonEnd < sb.length()) {
            if (sb.charAt(colonEnd) == ':') {
                colonEnd++;
                break;
            }
            colonEnd++;
        }
        int valueStart = HttpPostBodyUtil.findNonWhitespace(sb, colonEnd);
        int valueEnd = HttpPostBodyUtil.findEndOfString(sb);
        headers.add(sb.substring(nameStart, nameEnd));
        String svalue = sb.substring(valueStart, valueEnd);
        String[] values;
        if (svalue.indexOf(59) >= 0) {
            values = splitMultipartHeaderValues(svalue);
        } else {
            values = svalue.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        }
        for (String value : values) {
            headers.add(value.trim());
        }
        String[] array = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            array[i] = (String) headers.get(i);
        }
        return array;
    }

    private static String[] splitMultipartHeaderValues(String svalue) {
        List<String> values = InternalThreadLocalMap.get().arrayList(1);
        boolean inQuote = false;
        boolean escapeNext = false;
        int start = 0;
        for (int i = 0; i < svalue.length(); i++) {
            char c = svalue.charAt(i);
            if (inQuote) {
                if (escapeNext) {
                    escapeNext = false;
                } else if (c == '\\') {
                    escapeNext = true;
                } else if (c == StringUtil.DOUBLE_QUOTE) {
                    inQuote = false;
                }
            } else if (c == StringUtil.DOUBLE_QUOTE) {
                inQuote = true;
            } else if (c == ';') {
                values.add(svalue.substring(start, i));
                start = i + 1;
            }
        }
        values.add(svalue.substring(start));
        return (String[]) values.toArray(new String[values.size()]);
    }
}
