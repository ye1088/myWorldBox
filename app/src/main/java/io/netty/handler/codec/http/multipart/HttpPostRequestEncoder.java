package io.netty.handler.codec.http.multipart;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.open.SocialConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.HttpPostBodyUtil.TransferEncodingMechanism;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class HttpPostRequestEncoder implements ChunkedInput<HttpContent> {
    private static final Map<Pattern, String> percentEncodings = new HashMap();
    private final List<InterfaceHttpData> bodyListDatas;
    private final Charset charset;
    private ByteBuf currentBuffer;
    private InterfaceHttpData currentData;
    private FileUpload currentFileUpload;
    private boolean duringMixedMode;
    private final EncoderMode encoderMode;
    private final HttpDataFactory factory;
    private long globalBodySize;
    private long globalProgress;
    private boolean headerFinalized;
    private boolean isChunked;
    private boolean isKey;
    private boolean isLastChunk;
    private boolean isLastChunkSent;
    private final boolean isMultipart;
    private ListIterator<InterfaceHttpData> iterator;
    String multipartDataBoundary;
    final List<InterfaceHttpData> multipartHttpDatas;
    String multipartMixedBoundary;
    private final HttpRequest request;

    private static class WrappedHttpRequest implements HttpRequest {
        private final HttpRequest request;

        WrappedHttpRequest(HttpRequest request) {
            this.request = request;
        }

        public HttpRequest setProtocolVersion(HttpVersion version) {
            this.request.setProtocolVersion(version);
            return this;
        }

        public HttpRequest setMethod(HttpMethod method) {
            this.request.setMethod(method);
            return this;
        }

        public HttpRequest setUri(String uri) {
            this.request.setUri(uri);
            return this;
        }

        public HttpMethod getMethod() {
            return this.request.method();
        }

        public HttpMethod method() {
            return this.request.method();
        }

        public String getUri() {
            return this.request.uri();
        }

        public String uri() {
            return this.request.uri();
        }

        public HttpVersion getProtocolVersion() {
            return this.request.protocolVersion();
        }

        public HttpVersion protocolVersion() {
            return this.request.protocolVersion();
        }

        public HttpHeaders headers() {
            return this.request.headers();
        }

        public DecoderResult decoderResult() {
            return this.request.decoderResult();
        }

        @Deprecated
        public DecoderResult getDecoderResult() {
            return this.request.getDecoderResult();
        }

        public void setDecoderResult(DecoderResult result) {
            this.request.setDecoderResult(result);
        }
    }

    static {
        percentEncodings.put(Pattern.compile("\\*"), "%2A");
        percentEncodings.put(Pattern.compile("\\+"), "%20");
        percentEncodings.put(Pattern.compile("%7E"), "~");
    }

    public HttpPostRequestEncoder(HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
        this(new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE), request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
        this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
    }

    public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
        this.isKey = true;
        if (factory == null) {
            throw new NullPointerException("factory");
        } else if (request == null) {
            throw new NullPointerException(SocialConstants.TYPE_REQUEST);
        } else if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            HttpMethod method = request.method();
            if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH) || method.equals(HttpMethod.OPTIONS)) {
                this.request = request;
                this.charset = charset;
                this.factory = factory;
                this.bodyListDatas = new ArrayList();
                this.isLastChunk = false;
                this.isLastChunkSent = false;
                this.isMultipart = multipart;
                this.multipartHttpDatas = new ArrayList();
                this.encoderMode = encoderMode;
                if (this.isMultipart) {
                    initDataMultipart();
                    return;
                }
                return;
            }
            throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");
        }
    }

    public void cleanFiles() {
        this.factory.cleanRequestHttpData(this.request);
    }

    public boolean isMultipart() {
        return this.isMultipart;
    }

    private void initDataMultipart() {
        this.multipartDataBoundary = getNewMultipartDelimiter();
    }

    private void initMixedMultipart() {
        this.multipartMixedBoundary = getNewMultipartDelimiter();
    }

    private static String getNewMultipartDelimiter() {
        return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase();
    }

    public List<InterfaceHttpData> getBodyListAttributes() {
        return this.bodyListDatas;
    }

    public void setBodyHttpDatas(List<InterfaceHttpData> datas) throws ErrorDataEncoderException {
        if (datas == null) {
            throw new NullPointerException("datas");
        }
        this.globalBodySize = 0;
        this.bodyListDatas.clear();
        this.currentFileUpload = null;
        this.duringMixedMode = false;
        this.multipartHttpDatas.clear();
        for (InterfaceHttpData data : datas) {
            addBodyHttpData(data);
        }
    }

    public void addBodyAttribute(String name, String value) throws ErrorDataEncoderException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        String svalue = value;
        if (value == null) {
            svalue = "";
        }
        addBodyHttpData(this.factory.createAttribute(this.request, name, svalue));
    }

    public void addBodyFileUpload(String name, File file, String contentType, boolean isText) throws ErrorDataEncoderException {
        if (name == null) {
            throw new NullPointerException("name");
        } else if (file == null) {
            throw new NullPointerException("file");
        } else {
            String scontentType = contentType;
            String contentTransferEncoding = null;
            if (contentType == null) {
                if (isText) {
                    scontentType = "text/plain";
                } else {
                    scontentType = "application/octet-stream";
                }
            }
            if (!isText) {
                contentTransferEncoding = TransferEncodingMechanism.BINARY.value();
            }
            FileUpload fileUpload = this.factory.createFileUpload(this.request, name, file.getName(), scontentType, contentTransferEncoding, null, file.length());
            try {
                fileUpload.setContent(file);
                addBodyHttpData(fileUpload);
            } catch (IOException e) {
                throw new ErrorDataEncoderException(e);
            }
        }
    }

    public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText) throws ErrorDataEncoderException {
        if (file.length == contentType.length || file.length == isText.length) {
            for (int i = 0; i < file.length; i++) {
                addBodyFileUpload(name, file[i], contentType[i], isText[i]);
            }
            return;
        }
        throw new NullPointerException("Different array length");
    }

    public void addBodyHttpData(InterfaceHttpData data) throws ErrorDataEncoderException {
        if (this.headerFinalized) {
            throw new ErrorDataEncoderException("Cannot add value once finalized");
        } else if (data == null) {
            throw new NullPointerException("data");
        } else {
            this.bodyListDatas.add(data);
            Attribute attribute;
            FileUpload fileUpload;
            if (this.isMultipart) {
                InternalAttribute internal;
                if (data instanceof Attribute) {
                    if (this.duringMixedMode) {
                        internal = new InternalAttribute(this.charset);
                        internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");
                        this.multipartHttpDatas.add(internal);
                        this.multipartMixedBoundary = null;
                        this.currentFileUpload = null;
                        this.duringMixedMode = false;
                    }
                    internal = new InternalAttribute(this.charset);
                    if (!this.multipartHttpDatas.isEmpty()) {
                        internal.addValue("\r\n");
                    }
                    internal.addValue("--" + this.multipartDataBoundary + "\r\n");
                    attribute = (Attribute) data;
                    internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + attribute.getName() + "\"\r\n");
                    internal.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + attribute.length() + "\r\n");
                    Charset localcharset = attribute.getCharset();
                    if (localcharset != null) {
                        internal.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + "text/plain" + "; " + HttpHeaderValues.CHARSET + '=' + localcharset.name() + "\r\n");
                    }
                    internal.addValue("\r\n");
                    this.multipartHttpDatas.add(internal);
                    this.multipartHttpDatas.add(data);
                    this.globalBodySize += attribute.length() + ((long) internal.size());
                } else if (data instanceof FileUpload) {
                    boolean localMixed;
                    fileUpload = (FileUpload) data;
                    internal = new InternalAttribute(this.charset);
                    if (!this.multipartHttpDatas.isEmpty()) {
                        internal.addValue("\r\n");
                    }
                    if (this.duringMixedMode) {
                        if (this.currentFileUpload == null || !this.currentFileUpload.getName().equals(fileUpload.getName())) {
                            internal.addValue("--" + this.multipartMixedBoundary + "--");
                            this.multipartHttpDatas.add(internal);
                            this.multipartMixedBoundary = null;
                            internal = new InternalAttribute(this.charset);
                            internal.addValue("\r\n");
                            localMixed = false;
                            this.currentFileUpload = fileUpload;
                            this.duringMixedMode = false;
                        } else {
                            localMixed = true;
                        }
                    } else if (this.encoderMode == EncoderMode.HTML5 || this.currentFileUpload == null || !this.currentFileUpload.getName().equals(fileUpload.getName())) {
                        localMixed = false;
                        this.currentFileUpload = fileUpload;
                        this.duringMixedMode = false;
                    } else {
                        initMixedMultipart();
                        InternalAttribute pastAttribute = (InternalAttribute) this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2);
                        this.globalBodySize -= (long) pastAttribute.size();
                        pastAttribute.setValue(new StringBuilder((((this.multipartDataBoundary.length() + 139) + (this.multipartMixedBoundary.length() * 2)) + fileUpload.getFilename().length()) + fileUpload.getName().length()).append("--").append(this.multipartDataBoundary).append("\r\n").append(HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append(HttpHeaderValues.FORM_DATA).append("; ").append(HttpHeaderValues.NAME).append("=\"").append(fileUpload.getName()).append("\"\r\n").append(HttpHeaderNames.CONTENT_TYPE).append(": ").append(HttpHeaderValues.MULTIPART_MIXED).append("; ").append(HttpHeaderValues.BOUNDARY).append('=').append(this.multipartMixedBoundary).append("\r\n\r\n").append("--").append(this.multipartMixedBoundary).append("\r\n").append(HttpHeaderNames.CONTENT_DISPOSITION).append(": ").append(HttpHeaderValues.ATTACHMENT).append("; ").append(HttpHeaderValues.FILENAME).append("=\"").append(fileUpload.getFilename()).append("\"\r\n").toString(), 1);
                        pastAttribute.setValue("", 2);
                        this.globalBodySize += (long) pastAttribute.size();
                        localMixed = true;
                        this.duringMixedMode = true;
                    }
                    if (localMixed) {
                        internal.addValue("--" + this.multipartMixedBoundary + "\r\n");
                        internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.ATTACHMENT + "; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n");
                    } else {
                        internal.addValue("--" + this.multipartDataBoundary + "\r\n");
                        internal.addValue(HttpHeaderNames.CONTENT_DISPOSITION + ": " + HttpHeaderValues.FORM_DATA + "; " + HttpHeaderValues.NAME + "=\"" + fileUpload.getName() + "\"; " + HttpHeaderValues.FILENAME + "=\"" + fileUpload.getFilename() + "\"\r\n");
                    }
                    internal.addValue(HttpHeaderNames.CONTENT_LENGTH + ": " + fileUpload.length() + "\r\n");
                    internal.addValue(HttpHeaderNames.CONTENT_TYPE + ": " + fileUpload.getContentType());
                    String contentTransferEncoding = fileUpload.getContentTransferEncoding();
                    if (contentTransferEncoding != null && contentTransferEncoding.equals(TransferEncodingMechanism.BINARY.value())) {
                        internal.addValue("\r\n" + HttpHeaderNames.CONTENT_TRANSFER_ENCODING + ": " + TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
                    } else if (fileUpload.getCharset() != null) {
                        internal.addValue("; " + HttpHeaderValues.CHARSET + '=' + fileUpload.getCharset().name() + "\r\n\r\n");
                    } else {
                        internal.addValue("\r\n\r\n");
                    }
                    this.multipartHttpDatas.add(internal);
                    this.multipartHttpDatas.add(data);
                    this.globalBodySize += fileUpload.length() + ((long) internal.size());
                }
            } else if (data instanceof Attribute) {
                attribute = (Attribute) data;
                try {
                    newattribute = this.factory.createAttribute(this.request, encodeAttribute(attribute.getName(), this.charset), encodeAttribute(attribute.getValue(), this.charset));
                    this.multipartHttpDatas.add(newattribute);
                    this.globalBodySize += (((long) (newattribute.getName().length() + 1)) + newattribute.length()) + 1;
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            } else if (data instanceof FileUpload) {
                fileUpload = (FileUpload) data;
                newattribute = this.factory.createAttribute(this.request, encodeAttribute(fileUpload.getName(), this.charset), encodeAttribute(fileUpload.getFilename(), this.charset));
                this.multipartHttpDatas.add(newattribute);
                this.globalBodySize += (((long) (newattribute.getName().length() + 1)) + newattribute.length()) + 1;
            }
        }
    }

    public HttpRequest finalizeRequest() throws ErrorDataEncoderException {
        if (this.headerFinalized) {
            throw new ErrorDataEncoderException("Header already encoded");
        }
        if (this.isMultipart) {
            InternalAttribute internal = new InternalAttribute(this.charset);
            if (this.duringMixedMode) {
                internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");
            }
            internal.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n");
            this.multipartHttpDatas.add(internal);
            this.multipartMixedBoundary = null;
            this.currentFileUpload = null;
            this.duringMixedMode = false;
            this.globalBodySize += (long) internal.size();
        }
        this.headerFinalized = true;
        HttpHeaders headers = this.request.headers();
        List<String> contentTypes = headers.getAll((CharSequence) HttpHeaderNames.CONTENT_TYPE);
        List<String> transferEncoding = headers.getAll((CharSequence) HttpHeaderNames.TRANSFER_ENCODING);
        if (contentTypes != null) {
            headers.remove((CharSequence) HttpHeaderNames.CONTENT_TYPE);
            for (Object contentType : contentTypes) {
                String lowercased = contentType.toLowerCase();
                if (!(lowercased.startsWith(HttpHeaderValues.MULTIPART_FORM_DATA.toString()) || lowercased.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()))) {
                    headers.add((CharSequence) HttpHeaderNames.CONTENT_TYPE, contentType);
                }
            }
        }
        if (this.isMultipart) {
            headers.add((CharSequence) HttpHeaderNames.CONTENT_TYPE, (Object) HttpHeaderValues.MULTIPART_FORM_DATA + "; " + HttpHeaderValues.BOUNDARY + '=' + this.multipartDataBoundary);
        } else {
            headers.add((CharSequence) HttpHeaderNames.CONTENT_TYPE, (Object) HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
        }
        long realSize = this.globalBodySize;
        if (this.isMultipart) {
            this.iterator = this.multipartHttpDatas.listIterator();
        } else {
            realSize--;
            this.iterator = this.multipartHttpDatas.listIterator();
        }
        headers.set((CharSequence) HttpHeaderNames.CONTENT_LENGTH, (Object) String.valueOf(realSize));
        if (realSize > 8096 || this.isMultipart) {
            this.isChunked = true;
            if (transferEncoding != null) {
                headers.remove((CharSequence) HttpHeaderNames.TRANSFER_ENCODING);
                for (CharSequence v : transferEncoding) {
                    if (!HttpHeaderValues.CHUNKED.contentEqualsIgnoreCase(v)) {
                        headers.add((CharSequence) HttpHeaderNames.TRANSFER_ENCODING, (Object) v);
                    }
                }
            }
            HttpUtil.setTransferEncodingChunked(this.request, true);
            return new WrappedHttpRequest(this.request);
        }
        HttpContent chunk = nextChunk();
        if (!(this.request instanceof FullHttpRequest)) {
            return new WrappedFullHttpRequest(this.request, chunk, null);
        }
        FullHttpRequest fullRequest = this.request;
        ByteBuf chunkContent = chunk.content();
        if (fullRequest.content() == chunkContent) {
            return fullRequest;
        }
        fullRequest.content().clear().writeBytes(chunkContent);
        chunkContent.release();
        return fullRequest;
    }

    public boolean isChunked() {
        return this.isChunked;
    }

    private String encodeAttribute(String s, Charset charset) throws ErrorDataEncoderException {
        if (s == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(s, charset.name());
            if (this.encoderMode != EncoderMode.RFC3986) {
                return encoded;
            }
            for (Entry<Pattern, String> entry : percentEncodings.entrySet()) {
                encoded = ((Pattern) entry.getKey()).matcher(encoded).replaceAll((String) entry.getValue());
            }
            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new ErrorDataEncoderException(charset.name(), e);
        }
    }

    private ByteBuf fillByteBuf() {
        if (this.currentBuffer.readableBytes() > HttpPostBodyUtil.chunkSize) {
            ByteBuf slice = this.currentBuffer.slice(this.currentBuffer.readerIndex(), HttpPostBodyUtil.chunkSize);
            this.currentBuffer.skipBytes(HttpPostBodyUtil.chunkSize);
            return slice;
        }
        slice = this.currentBuffer;
        this.currentBuffer = null;
        return slice;
    }

    private HttpContent encodeNextChunkMultipart(int sizeleft) throws ErrorDataEncoderException {
        if (this.currentData == null) {
            return null;
        }
        ByteBuf buffer;
        if (this.currentData instanceof InternalAttribute) {
            buffer = ((InternalAttribute) this.currentData).toByteBuf();
            this.currentData = null;
        } else {
            if (this.currentData instanceof Attribute) {
                try {
                    buffer = ((Attribute) this.currentData).getChunk(sizeleft);
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            }
            try {
                buffer = ((HttpData) this.currentData).getChunk(sizeleft);
            } catch (IOException e2) {
                throw new ErrorDataEncoderException(e2);
            }
            if (buffer.capacity() == 0) {
                this.currentData = null;
                return null;
            }
        }
        if (this.currentBuffer == null) {
            this.currentBuffer = buffer;
        } else {
            this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer);
        }
        if (this.currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
            return new DefaultHttpContent(fillByteBuf());
        }
        this.currentData = null;
        return null;
    }

    private HttpContent encodeNextChunkUrlEncoded(int sizeleft) throws ErrorDataEncoderException {
        if (this.currentData == null) {
            return null;
        }
        ByteBuf buffer;
        int size = sizeleft;
        if (this.isKey) {
            buffer = Unpooled.wrappedBuffer(this.currentData.getName().getBytes());
            this.isKey = false;
            if (this.currentBuffer == null) {
                this.currentBuffer = Unpooled.wrappedBuffer(buffer, Unpooled.wrappedBuffer(SimpleComparison.EQUAL_TO_OPERATION.getBytes()));
                size -= buffer.readableBytes() + 1;
            } else {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer, Unpooled.wrappedBuffer(SimpleComparison.EQUAL_TO_OPERATION.getBytes()));
                size -= buffer.readableBytes() + 1;
            }
            if (this.currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
                return new DefaultHttpContent(fillByteBuf());
            }
        }
        try {
            buffer = ((HttpData) this.currentData).getChunk(size);
            ByteBuf delimiter = null;
            if (buffer.readableBytes() < size) {
                this.isKey = true;
                if (this.iterator.hasNext()) {
                    delimiter = Unpooled.wrappedBuffer("&".getBytes());
                } else {
                    delimiter = null;
                }
            }
            if (buffer.capacity() == 0) {
                this.currentData = null;
                if (this.currentBuffer == null) {
                    this.currentBuffer = delimiter;
                } else if (delimiter != null) {
                    this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, delimiter);
                }
                if (this.currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
                    return new DefaultHttpContent(fillByteBuf());
                }
                return null;
            }
            if (this.currentBuffer == null) {
                if (delimiter != null) {
                    this.currentBuffer = Unpooled.wrappedBuffer(buffer, delimiter);
                } else {
                    this.currentBuffer = buffer;
                }
            } else if (delimiter != null) {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer, delimiter);
            } else {
                this.currentBuffer = Unpooled.wrappedBuffer(this.currentBuffer, buffer);
            }
            if (this.currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
                return new DefaultHttpContent(fillByteBuf());
            }
            this.currentData = null;
            this.isKey = true;
            return null;
        } catch (IOException e) {
            throw new ErrorDataEncoderException(e);
        }
    }

    public void close() throws Exception {
    }

    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception {
        return readChunk(ctx.alloc());
    }

    public HttpContent readChunk(ByteBufAllocator allocator) throws Exception {
        if (this.isLastChunkSent) {
            return null;
        }
        HttpContent nextChunk = nextChunk();
        this.globalProgress += (long) nextChunk.content().readableBytes();
        return nextChunk;
    }

    private HttpContent nextChunk() throws ErrorDataEncoderException {
        if (this.isLastChunk) {
            this.isLastChunkSent = true;
            return LastHttpContent.EMPTY_LAST_CONTENT;
        }
        int size = HttpPostBodyUtil.chunkSize;
        if (this.currentBuffer != null) {
            size = HttpPostBodyUtil.chunkSize - this.currentBuffer.readableBytes();
        }
        if (size <= 0) {
            return new DefaultHttpContent(fillByteBuf());
        }
        HttpContent chunk;
        if (this.currentData != null) {
            if (this.isMultipart) {
                chunk = encodeNextChunkMultipart(size);
                if (chunk != null) {
                    return chunk;
                }
            }
            chunk = encodeNextChunkUrlEncoded(size);
            if (chunk != null) {
                return chunk;
            }
            size = 8096 - this.currentBuffer.readableBytes();
        }
        if (this.iterator.hasNext()) {
            while (size > 0 && this.iterator.hasNext()) {
                this.currentData = (InterfaceHttpData) this.iterator.next();
                if (this.isMultipart) {
                    chunk = encodeNextChunkMultipart(size);
                } else {
                    chunk = encodeNextChunkUrlEncoded(size);
                }
                if (chunk != null) {
                    return chunk;
                }
                size = 8096 - this.currentBuffer.readableBytes();
            }
            this.isLastChunk = true;
            if (this.currentBuffer == null) {
                this.isLastChunkSent = true;
                return LastHttpContent.EMPTY_LAST_CONTENT;
            }
            ByteBuf buffer = this.currentBuffer;
            this.currentBuffer = null;
            return new DefaultHttpContent(buffer);
        }
        this.isLastChunk = true;
        buffer = this.currentBuffer;
        this.currentBuffer = null;
        return new DefaultHttpContent(buffer);
    }

    public boolean isEndOfInput() throws Exception {
        return this.isLastChunkSent;
    }

    public long length() {
        return this.isMultipart ? this.globalBodySize : this.globalBodySize - 1;
    }

    public long progress() {
        return this.globalProgress;
    }
}
