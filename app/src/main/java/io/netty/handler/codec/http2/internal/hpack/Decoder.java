package io.netty.handler.codec.http2.internal.hpack;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.internal.ThrowableUtil;

public final class Decoder {
    private static final Http2Exception DECODE_DECOMPRESSION_EXCEPTION = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - decompression failure", new Object[0]), Decoder.class, "decode(...)"));
    private static final Http2Exception DECODE_ILLEGAL_INDEX_VALUE = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), Decoder.class, "decode(...)"));
    private static final Http2Exception DECODE_ULE_128_DECOMPRESSION_EXCEPTION = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - decompression failure", new Object[0]), Decoder.class, "decodeULE128(...)"));
    private static final Http2Exception INDEX_HEADER_ILLEGAL_INDEX_VALUE = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), Decoder.class, "indexHeader(...)"));
    private static final Http2Exception INVALID_MAX_DYNAMIC_TABLE_SIZE = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - invalid max dynamic table size", new Object[0]), Decoder.class, "setDynamicTableSize(...)"));
    private static final Http2Exception MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - max dynamic table size change required", new Object[0]), Decoder.class, "decode(...)"));
    private static final byte READ_HEADER_REPRESENTATION = (byte) 0;
    private static final byte READ_INDEXED_HEADER = (byte) 2;
    private static final byte READ_INDEXED_HEADER_NAME = (byte) 3;
    private static final byte READ_LITERAL_HEADER_NAME = (byte) 6;
    private static final byte READ_LITERAL_HEADER_NAME_LENGTH = (byte) 5;
    private static final byte READ_LITERAL_HEADER_NAME_LENGTH_PREFIX = (byte) 4;
    private static final byte READ_LITERAL_HEADER_VALUE = (byte) 9;
    private static final byte READ_LITERAL_HEADER_VALUE_LENGTH = (byte) 8;
    private static final byte READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX = (byte) 7;
    private static final byte READ_MAX_DYNAMIC_TABLE_SIZE = (byte) 1;
    private static final Http2Exception READ_NAME_ILLEGAL_INDEX_VALUE = ((Http2Exception) ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), Decoder.class, "readName(...)"));
    private final DynamicTable dynamicTable;
    private int encoderMaxDynamicTableSize;
    private final HuffmanDecoder huffmanDecoder;
    private int maxDynamicTableSize;
    private boolean maxDynamicTableSizeChangeRequired = false;
    private final int maxHeadersLength;

    public Decoder(int maxHeadersLength, int maxHeaderTableSize, int initialHuffmanDecodeCapacity) {
        this.dynamicTable = new DynamicTable(maxHeaderTableSize);
        this.maxHeadersLength = maxHeadersLength;
        this.maxDynamicTableSize = maxHeaderTableSize;
        this.encoderMaxDynamicTableSize = maxHeaderTableSize;
        this.huffmanDecoder = new HuffmanDecoder(initialHuffmanDecodeCapacity);
    }

    public void decode(ByteBuf in, Http2Headers headers) throws Http2Exception {
        int index = 0;
        int headersLength = 0;
        int nameLength = 0;
        int valueLength = 0;
        byte state = (byte) 0;
        boolean huffmanEncoded = false;
        CharSequence name = null;
        IndexType indexType = IndexType.NONE;
        while (in.isReadable()) {
            byte b;
            switch (state) {
                case (byte) 0:
                    b = in.readByte();
                    if (!this.maxDynamicTableSizeChangeRequired || (b & 224) == 32) {
                        if (b >= (byte) 0) {
                            if ((b & 64) != 64) {
                                if ((b & 32) != 32) {
                                    indexType = (b & 16) == 16 ? IndexType.NEVER : IndexType.NONE;
                                    index = b & 15;
                                    switch (index) {
                                        case 0:
                                            state = (byte) 4;
                                            break;
                                        case 15:
                                            state = (byte) 3;
                                            break;
                                        default:
                                            name = readName(index);
                                            state = (byte) 7;
                                            break;
                                    }
                                }
                                index = b & 31;
                                if (index != 31) {
                                    setDynamicTableSize(index);
                                    state = (byte) 0;
                                    break;
                                }
                                state = (byte) 1;
                                break;
                            }
                            indexType = IndexType.INCREMENTAL;
                            index = b & 63;
                            switch (index) {
                                case 0:
                                    state = (byte) 4;
                                    break;
                                case 63:
                                    state = (byte) 3;
                                    break;
                                default:
                                    name = readName(index);
                                    state = (byte) 7;
                                    break;
                            }
                        }
                        index = b & 127;
                        switch (index) {
                            case 0:
                                throw DECODE_ILLEGAL_INDEX_VALUE;
                            case 127:
                                state = (byte) 2;
                                break;
                            default:
                                headersLength = indexHeader(index, headers, headersLength);
                                break;
                        }
                    }
                    throw MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED;
                case (byte) 1:
                    int maxSize = decodeULE128(in) + index;
                    if (maxSize >= 0) {
                        setDynamicTableSize(maxSize);
                        state = (byte) 0;
                        break;
                    }
                    throw DECODE_DECOMPRESSION_EXCEPTION;
                case (byte) 2:
                    int headerIndex = decodeULE128(in) + index;
                    if (headerIndex >= 0) {
                        headersLength = indexHeader(headerIndex, headers, headersLength);
                        state = (byte) 0;
                        break;
                    }
                    throw DECODE_DECOMPRESSION_EXCEPTION;
                case (byte) 3:
                    int nameIndex = decodeULE128(in) + index;
                    if (nameIndex >= 0) {
                        name = readName(nameIndex);
                        state = (byte) 7;
                        break;
                    }
                    throw DECODE_DECOMPRESSION_EXCEPTION;
                case (byte) 4:
                    b = in.readByte();
                    huffmanEncoded = (b & 128) == 128;
                    index = b & 127;
                    if (index != 127) {
                        if (index > this.maxHeadersLength - headersLength) {
                            maxHeaderSizeExceeded();
                        }
                        nameLength = index;
                        state = (byte) 6;
                        break;
                    }
                    state = (byte) 5;
                    break;
                case (byte) 5:
                    nameLength = decodeULE128(in) + index;
                    if (nameLength >= 0) {
                        if (nameLength > this.maxHeadersLength - headersLength) {
                            maxHeaderSizeExceeded();
                        }
                        state = (byte) 6;
                        break;
                    }
                    throw DECODE_DECOMPRESSION_EXCEPTION;
                case (byte) 6:
                    if (in.readableBytes() >= nameLength) {
                        name = readStringLiteral(in, nameLength, huffmanEncoded);
                        state = (byte) 7;
                        break;
                    }
                    throw notEnoughDataException(in);
                case (byte) 7:
                    b = in.readByte();
                    huffmanEncoded = (b & 128) == 128;
                    index = b & 127;
                    switch (index) {
                        case 0:
                            headersLength = insertHeader(headers, name, AsciiString.EMPTY_STRING, indexType, headersLength);
                            state = (byte) 0;
                            break;
                        case 127:
                            state = (byte) 8;
                            break;
                        default:
                            if (((long) index) + ((long) nameLength) > ((long) (this.maxHeadersLength - headersLength))) {
                                maxHeaderSizeExceeded();
                            }
                            valueLength = index;
                            state = (byte) 9;
                            break;
                    }
                case (byte) 8:
                    valueLength = decodeULE128(in) + index;
                    if (valueLength >= 0) {
                        if (((long) valueLength) + ((long) nameLength) > ((long) (this.maxHeadersLength - headersLength))) {
                            maxHeaderSizeExceeded();
                        }
                        state = (byte) 9;
                        break;
                    }
                    throw DECODE_DECOMPRESSION_EXCEPTION;
                case (byte) 9:
                    if (in.readableBytes() >= valueLength) {
                        headersLength = insertHeader(headers, name, readStringLiteral(in, valueLength, huffmanEncoded), indexType, headersLength);
                        state = (byte) 0;
                        break;
                    }
                    throw notEnoughDataException(in);
                default:
                    throw new Error("should not reach here state: " + state);
            }
        }
    }

    public void setMaxHeaderTableSize(int maxHeaderTableSize) {
        this.maxDynamicTableSize = maxHeaderTableSize;
        if (this.maxDynamicTableSize < this.encoderMaxDynamicTableSize) {
            this.maxDynamicTableSizeChangeRequired = true;
            this.dynamicTable.setCapacity(this.maxDynamicTableSize);
        }
    }

    public int getMaxHeaderTableSize() {
        return this.dynamicTable.capacity();
    }

    int length() {
        return this.dynamicTable.length();
    }

    int size() {
        return this.dynamicTable.size();
    }

    HeaderField getHeaderField(int index) {
        return this.dynamicTable.getEntry(index + 1);
    }

    private void setDynamicTableSize(int dynamicTableSize) throws Http2Exception {
        if (dynamicTableSize > this.maxDynamicTableSize) {
            throw INVALID_MAX_DYNAMIC_TABLE_SIZE;
        }
        this.encoderMaxDynamicTableSize = dynamicTableSize;
        this.maxDynamicTableSizeChangeRequired = false;
        this.dynamicTable.setCapacity(dynamicTableSize);
    }

    private CharSequence readName(int index) throws Http2Exception {
        if (index <= StaticTable.length) {
            return StaticTable.getEntry(index).name;
        }
        if (index - StaticTable.length <= this.dynamicTable.length()) {
            return this.dynamicTable.getEntry(index - StaticTable.length).name;
        }
        throw READ_NAME_ILLEGAL_INDEX_VALUE;
    }

    private int indexHeader(int index, Http2Headers headers, int headersLength) throws Http2Exception {
        HeaderField headerField;
        if (index <= StaticTable.length) {
            headerField = StaticTable.getEntry(index);
            return addHeader(headers, headerField.name, headerField.value, headersLength);
        } else if (index - StaticTable.length <= this.dynamicTable.length()) {
            headerField = this.dynamicTable.getEntry(index - StaticTable.length);
            return addHeader(headers, headerField.name, headerField.value, headersLength);
        } else {
            throw INDEX_HEADER_ILLEGAL_INDEX_VALUE;
        }
    }

    private int insertHeader(Http2Headers headers, CharSequence name, CharSequence value, IndexType indexType, int headerSize) throws Http2Exception {
        headerSize = addHeader(headers, name, value, headerSize);
        switch (indexType) {
            case NONE:
            case NEVER:
                break;
            case INCREMENTAL:
                this.dynamicTable.add(new HeaderField(name, value));
                break;
            default:
                throw new Error("should not reach here");
        }
        return headerSize;
    }

    private int addHeader(Http2Headers headers, CharSequence name, CharSequence value, int headersLength) throws Http2Exception {
        long newHeadersLength = (((long) headersLength) + ((long) name.length())) + ((long) value.length());
        if (newHeadersLength > ((long) this.maxHeadersLength)) {
            maxHeaderSizeExceeded();
        }
        headers.add(name, value);
        return (int) newHeadersLength;
    }

    private void maxHeaderSizeExceeded() throws Http2Exception {
        throw Http2Exception.connectionError(Http2Error.ENHANCE_YOUR_CALM, "Header size exceeded max allowed bytes (%d)", Integer.valueOf(this.maxHeadersLength));
    }

    private CharSequence readStringLiteral(ByteBuf in, int length, boolean huffmanEncoded) throws Http2Exception {
        if (huffmanEncoded) {
            return this.huffmanDecoder.decode(in, length);
        }
        byte[] buf = new byte[length];
        in.readBytes(buf);
        return new AsciiString(buf, false);
    }

    private static IllegalArgumentException notEnoughDataException(ByteBuf in) {
        return new IllegalArgumentException("decode only works with an entire header block! " + in);
    }

    private static int decodeULE128(ByteBuf in) throws Http2Exception {
        int writerIndex = in.writerIndex();
        int readerIndex = in.readerIndex();
        int shift = 0;
        int result = 0;
        while (readerIndex < writerIndex) {
            byte b = in.getByte(readerIndex);
            if (shift == 28 && (b & 248) != 0) {
                in.readerIndex(readerIndex + 1);
                break;
            } else if ((b & 128) == 0) {
                in.readerIndex(readerIndex + 1);
                return ((b & 127) << shift) | result;
            } else {
                result |= (b & 127) << shift;
                readerIndex++;
                shift += 7;
            }
        }
        throw DECODE_ULE_128_DECOMPRESSION_EXCEPTION;
    }
}
