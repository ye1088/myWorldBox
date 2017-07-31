package io.netty.handler.codec.http.multipart;

import com.tencent.open.SocialConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HttpPostStandardRequestDecoder implements InterfaceHttpPostRequestDecoder {
    private final List<InterfaceHttpData> bodyListHttpData;
    private int bodyListHttpDataRank;
    private final Map<String, List<InterfaceHttpData>> bodyMapHttpData;
    private final Charset charset;
    private Attribute currentAttribute;
    private MultiPartStatus currentStatus;
    private boolean destroyed;
    private int discardThreshold;
    private final HttpDataFactory factory;
    private boolean isLastChunk;
    private final HttpRequest request;
    private ByteBuf undecodedChunk;

    public HttpPostStandardRequestDecoder(HttpRequest request) {
        this(new DefaultHttpDataFactory((long) DefaultHttpDataFactory.MINSIZE), request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory factory, HttpRequest request) {
        this(factory, request, HttpConstants.DEFAULT_CHARSET);
    }

    public HttpPostStandardRequestDecoder(HttpDataFactory factory, HttpRequest request, Charset charset) {
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
            if (request instanceof HttpContent) {
                offer((HttpContent) request);
                return;
            }
            this.undecodedChunk = Unpooled.buffer();
            parseBody();
        }
    }

    private void checkDestroyed() {
        if (this.destroyed) {
            throw new IllegalStateException(HttpPostStandardRequestDecoder.class.getSimpleName() + " was destroyed already");
        }
    }

    public boolean isMultipart() {
        checkDestroyed();
        return false;
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

    public HttpPostStandardRequestDecoder offer(HttpContent content) {
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
        return this.currentAttribute;
    }

    private void parseBody() {
        if (this.currentStatus != MultiPartStatus.PREEPILOGUE && this.currentStatus != MultiPartStatus.EPILOGUE) {
            parseBodyAttributes();
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

    private void parseBodyAttributesStandard() {
        int firstpos = this.undecodedChunk.readerIndex();
        int currentpos = firstpos;
        if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
            this.currentStatus = MultiPartStatus.DISPOSITION;
        }
        boolean contRead = true;
        while (this.undecodedChunk.isReadable() && contRead) {
            try {
                char read = (char) this.undecodedChunk.readUnsignedByte();
                currentpos++;
                switch (this.currentStatus) {
                    case DISPOSITION:
                        if (read != '=') {
                            if (read != '&') {
                                break;
                            }
                            this.currentStatus = MultiPartStatus.DISPOSITION;
                            this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(firstpos, (currentpos - 1) - firstpos, this.charset), this.charset));
                            this.currentAttribute.setValue("");
                            addHttpData(this.currentAttribute);
                            this.currentAttribute = null;
                            firstpos = currentpos;
                            contRead = true;
                            break;
                        }
                        this.currentStatus = MultiPartStatus.FIELD;
                        this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(firstpos, (currentpos - 1) - firstpos, this.charset), this.charset));
                        firstpos = currentpos;
                        break;
                    case FIELD:
                        if (read == '&') {
                            this.currentStatus = MultiPartStatus.DISPOSITION;
                            setFinalBuffer(this.undecodedChunk.copy(firstpos, (currentpos - 1) - firstpos));
                            firstpos = currentpos;
                            contRead = true;
                            break;
                        } else if (read == StringUtil.CARRIAGE_RETURN) {
                            if (!this.undecodedChunk.isReadable()) {
                                currentpos--;
                                break;
                            }
                            currentpos++;
                            if (((char) this.undecodedChunk.readUnsignedByte()) == '\n') {
                                this.currentStatus = MultiPartStatus.PREEPILOGUE;
                                setFinalBuffer(this.undecodedChunk.copy(firstpos, (currentpos - 2) - firstpos));
                                firstpos = currentpos;
                                contRead = false;
                                break;
                            }
                            throw new ErrorDataDecoderException("Bad end of line");
                        } else if (read == '\n') {
                            this.currentStatus = MultiPartStatus.PREEPILOGUE;
                            setFinalBuffer(this.undecodedChunk.copy(firstpos, (currentpos - 1) - firstpos));
                            firstpos = currentpos;
                            contRead = false;
                            break;
                        } else {
                            continue;
                        }
                    default:
                        contRead = false;
                        break;
                }
            } catch (ErrorDataDecoderException e) {
                this.undecodedChunk.readerIndex(firstpos);
                throw e;
            } catch (Throwable e2) {
                this.undecodedChunk.readerIndex(firstpos);
                throw new ErrorDataDecoderException(e2);
            }
        }
        if (!this.isLastChunk || this.currentAttribute == null) {
            if (contRead) {
                if (this.currentAttribute != null) {
                    if (this.currentStatus == MultiPartStatus.FIELD) {
                        this.currentAttribute.addContent(this.undecodedChunk.copy(firstpos, currentpos - firstpos), false);
                        firstpos = currentpos;
                    }
                    this.undecodedChunk.readerIndex(firstpos);
                    return;
                }
            }
            this.undecodedChunk.readerIndex(firstpos);
            return;
        }
        int ampersandpos = currentpos;
        if (ampersandpos > firstpos) {
            setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
        } else if (!this.currentAttribute.isCompleted()) {
            setFinalBuffer(Unpooled.EMPTY_BUFFER);
        }
        firstpos = currentpos;
        this.currentStatus = MultiPartStatus.EPILOGUE;
        this.undecodedChunk.readerIndex(firstpos);
    }

    private void parseBodyAttributes() {
        try {
            SeekAheadOptimize sao = new SeekAheadOptimize(this.undecodedChunk);
            int firstpos = this.undecodedChunk.readerIndex();
            int currentpos = firstpos;
            if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
                this.currentStatus = MultiPartStatus.DISPOSITION;
            }
            boolean contRead = true;
            while (sao.pos < sao.limit) {
                try {
                    int ampersandpos;
                    byte[] bArr = sao.bytes;
                    int i = sao.pos;
                    sao.pos = i + 1;
                    char read = (char) (bArr[i] & 255);
                    currentpos++;
                    switch (this.currentStatus) {
                        case DISPOSITION:
                            if (read != '=') {
                                if (read != '&') {
                                    break;
                                }
                                this.currentStatus = MultiPartStatus.DISPOSITION;
                                this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(firstpos, (currentpos - 1) - firstpos, this.charset), this.charset));
                                this.currentAttribute.setValue("");
                                addHttpData(this.currentAttribute);
                                this.currentAttribute = null;
                                firstpos = currentpos;
                                contRead = true;
                                break;
                            }
                            this.currentStatus = MultiPartStatus.FIELD;
                            this.currentAttribute = this.factory.createAttribute(this.request, decodeAttribute(this.undecodedChunk.toString(firstpos, (currentpos - 1) - firstpos, this.charset), this.charset));
                            firstpos = currentpos;
                            continue;
                        case FIELD:
                            if (read != '&') {
                                if (read != StringUtil.CARRIAGE_RETURN) {
                                    if (read != '\n') {
                                        break;
                                    }
                                    this.currentStatus = MultiPartStatus.PREEPILOGUE;
                                    ampersandpos = currentpos - 1;
                                    sao.setReadPosition(0);
                                    setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
                                    firstpos = currentpos;
                                    contRead = false;
                                    break;
                                } else if (sao.pos >= sao.limit) {
                                    if (sao.limit <= 0) {
                                        break;
                                    }
                                    currentpos--;
                                    break;
                                } else {
                                    bArr = sao.bytes;
                                    i = sao.pos;
                                    sao.pos = i + 1;
                                    currentpos++;
                                    if (((char) (bArr[i] & 255)) == '\n') {
                                        this.currentStatus = MultiPartStatus.PREEPILOGUE;
                                        ampersandpos = currentpos - 2;
                                        sao.setReadPosition(0);
                                        setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
                                        firstpos = currentpos;
                                        contRead = false;
                                        break;
                                    }
                                    sao.setReadPosition(0);
                                    throw new ErrorDataDecoderException("Bad end of line");
                                }
                            }
                            this.currentStatus = MultiPartStatus.DISPOSITION;
                            setFinalBuffer(this.undecodedChunk.copy(firstpos, (currentpos - 1) - firstpos));
                            firstpos = currentpos;
                            contRead = true;
                            continue;
                        default:
                            sao.setReadPosition(0);
                            contRead = false;
                            break;
                    }
                    if (this.isLastChunk || this.currentAttribute == null) {
                        if (contRead) {
                            if (this.currentAttribute != null) {
                                if (this.currentStatus == MultiPartStatus.FIELD) {
                                    this.currentAttribute.addContent(this.undecodedChunk.copy(firstpos, currentpos - firstpos), false);
                                    firstpos = currentpos;
                                }
                                this.undecodedChunk.readerIndex(firstpos);
                                return;
                            }
                        }
                        this.undecodedChunk.readerIndex(firstpos);
                    }
                    ampersandpos = currentpos;
                    if (ampersandpos > firstpos) {
                        setFinalBuffer(this.undecodedChunk.copy(firstpos, ampersandpos - firstpos));
                    } else if (!this.currentAttribute.isCompleted()) {
                        setFinalBuffer(Unpooled.EMPTY_BUFFER);
                    }
                    firstpos = currentpos;
                    this.currentStatus = MultiPartStatus.EPILOGUE;
                    this.undecodedChunk.readerIndex(firstpos);
                    return;
                } catch (ErrorDataDecoderException e) {
                    this.undecodedChunk.readerIndex(firstpos);
                    throw e;
                } catch (Throwable e2) {
                    this.undecodedChunk.readerIndex(firstpos);
                    throw new ErrorDataDecoderException(e2);
                } catch (Throwable e22) {
                    this.undecodedChunk.readerIndex(firstpos);
                    throw new ErrorDataDecoderException(e22);
                }
            }
            if (this.isLastChunk) {
            }
            if (contRead) {
                if (this.currentAttribute != null) {
                    if (this.currentStatus == MultiPartStatus.FIELD) {
                        this.currentAttribute.addContent(this.undecodedChunk.copy(firstpos, currentpos - firstpos), false);
                        firstpos = currentpos;
                    }
                    this.undecodedChunk.readerIndex(firstpos);
                    return;
                }
            }
            this.undecodedChunk.readerIndex(firstpos);
        } catch (SeekAheadNoBackArrayException e3) {
            parseBodyAttributesStandard();
        }
    }

    private void setFinalBuffer(ByteBuf buffer) throws IOException {
        this.currentAttribute.addContent(buffer, true);
        this.currentAttribute.setValue(decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset));
        addHttpData(this.currentAttribute);
        this.currentAttribute = null;
    }

    private static String decodeAttribute(String s, Charset charset) {
        try {
            return QueryStringDecoder.decodeComponent(s, charset);
        } catch (IllegalArgumentException e) {
            throw new ErrorDataDecoderException("Bad string: '" + s + '\'', e);
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
            } catch (Throwable e2) {
                throw new NotEnoughDataDecoderException(e2);
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
}
