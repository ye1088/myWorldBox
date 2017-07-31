package io.netty.handler.codec.compression;

final class Bzip2BlockDecompressor {
    private final int blockCRC;
    private final boolean blockRandomised;
    private final byte[] bwtBlock;
    private int bwtBlockLength;
    private final int[] bwtByteCounts = new int[256];
    private int bwtBytesDecoded;
    private int bwtCurrentMergedPointer;
    private int[] bwtMergedPointers;
    private final int bwtStartPointer;
    private final Crc32 crc = new Crc32();
    int huffmanEndOfBlockSymbol;
    int huffmanInUse16;
    final byte[] huffmanSymbolMap = new byte[256];
    private int mtfValue;
    private int randomCount = (Bzip2Rand.rNums(0) - 1);
    private int randomIndex;
    private final Bzip2BitReader reader;
    private int repeatCount;
    private int repeatIncrement = 1;
    private int rleAccumulator;
    private int rleLastDecodedByte = -1;
    private int rleRepeat;
    private final Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();

    Bzip2BlockDecompressor(int blockSize, int blockCRC, boolean blockRandomised, int bwtStartPointer, Bzip2BitReader reader) {
        this.bwtBlock = new byte[blockSize];
        this.blockCRC = blockCRC;
        this.blockRandomised = blockRandomised;
        this.bwtStartPointer = bwtStartPointer;
        this.reader = reader;
    }

    boolean decodeHuffmanData(Bzip2HuffmanStageDecoder huffmanDecoder) {
        Bzip2BitReader reader = this.reader;
        byte[] bwtBlock = this.bwtBlock;
        byte[] huffmanSymbolMap = this.huffmanSymbolMap;
        int streamBlockSize = this.bwtBlock.length;
        int huffmanEndOfBlockSymbol = this.huffmanEndOfBlockSymbol;
        int[] bwtByteCounts = this.bwtByteCounts;
        Bzip2MoveToFrontTable symbolMTF = this.symbolMTF;
        int bwtBlockLength = this.bwtBlockLength;
        int repeatCount = this.repeatCount;
        int repeatIncrement = this.repeatIncrement;
        int mtfValue = this.mtfValue;
        while (reader.hasReadableBits(23)) {
            int nextSymbol = huffmanDecoder.nextSymbol();
            if (nextSymbol == 0) {
                repeatCount += repeatIncrement;
                repeatIncrement <<= 1;
            } else if (nextSymbol == 1) {
                repeatCount += repeatIncrement << 1;
                repeatIncrement <<= 1;
            } else {
                int bwtBlockLength2;
                byte nextByte;
                int i;
                if (repeatCount <= 0) {
                    bwtBlockLength2 = bwtBlockLength;
                } else if (bwtBlockLength + repeatCount > streamBlockSize) {
                    throw new DecompressionException("block exceeds declared block size");
                } else {
                    nextByte = huffmanSymbolMap[mtfValue];
                    i = nextByte & 255;
                    bwtByteCounts[i] = bwtByteCounts[i] + repeatCount;
                    bwtBlockLength2 = bwtBlockLength;
                    while (true) {
                        repeatCount--;
                        if (repeatCount < 0) {
                            break;
                        }
                        bwtBlockLength = bwtBlockLength2 + 1;
                        bwtBlock[bwtBlockLength2] = nextByte;
                        bwtBlockLength2 = bwtBlockLength;
                    }
                    repeatCount = 0;
                    repeatIncrement = 1;
                }
                if (nextSymbol == huffmanEndOfBlockSymbol) {
                    this.bwtBlockLength = bwtBlockLength2;
                    initialiseInverseBWT();
                    bwtBlockLength = bwtBlockLength2;
                    return true;
                } else if (bwtBlockLength2 >= streamBlockSize) {
                    throw new DecompressionException("block exceeds declared block size");
                } else {
                    mtfValue = symbolMTF.indexToFront(nextSymbol - 1) & 255;
                    nextByte = huffmanSymbolMap[mtfValue];
                    i = nextByte & 255;
                    bwtByteCounts[i] = bwtByteCounts[i] + 1;
                    bwtBlockLength = bwtBlockLength2 + 1;
                    bwtBlock[bwtBlockLength2] = nextByte;
                }
            }
        }
        this.bwtBlockLength = bwtBlockLength;
        this.repeatCount = repeatCount;
        this.repeatIncrement = repeatIncrement;
        this.mtfValue = mtfValue;
        return false;
    }

    private void initialiseInverseBWT() {
        int bwtStartPointer = this.bwtStartPointer;
        byte[] bwtBlock = this.bwtBlock;
        int[] bwtMergedPointers = new int[this.bwtBlockLength];
        int[] characterBase = new int[256];
        if (bwtStartPointer < 0 || bwtStartPointer >= this.bwtBlockLength) {
            throw new DecompressionException("start pointer invalid");
        }
        int i;
        System.arraycopy(this.bwtByteCounts, 0, characterBase, 1, 255);
        for (i = 2; i <= 255; i++) {
            characterBase[i] = characterBase[i] + characterBase[i - 1];
        }
        for (i = 0; i < this.bwtBlockLength; i++) {
            int value = bwtBlock[i] & 255;
            int i2 = characterBase[value];
            characterBase[value] = i2 + 1;
            bwtMergedPointers[i2] = (i << 8) + value;
        }
        this.bwtMergedPointers = bwtMergedPointers;
        this.bwtCurrentMergedPointer = bwtMergedPointers[bwtStartPointer];
    }

    public int read() {
        while (this.rleRepeat < 1) {
            if (this.bwtBytesDecoded == this.bwtBlockLength) {
                return -1;
            }
            int nextByte = decodeNextBWTByte();
            if (nextByte != this.rleLastDecodedByte) {
                this.rleLastDecodedByte = nextByte;
                this.rleRepeat = 1;
                this.rleAccumulator = 1;
                this.crc.updateCRC(nextByte);
            } else {
                int i = this.rleAccumulator + 1;
                this.rleAccumulator = i;
                if (i == 4) {
                    int rleRepeat = decodeNextBWTByte() + 1;
                    this.rleRepeat = rleRepeat;
                    this.rleAccumulator = 0;
                    this.crc.updateCRC(nextByte, rleRepeat);
                } else {
                    this.rleRepeat = 1;
                    this.crc.updateCRC(nextByte);
                }
            }
        }
        this.rleRepeat--;
        return this.rleLastDecodedByte;
    }

    private int decodeNextBWTByte() {
        int mergedPointer = this.bwtCurrentMergedPointer;
        int nextDecodedByte = mergedPointer & 255;
        this.bwtCurrentMergedPointer = this.bwtMergedPointers[mergedPointer >>> 8];
        if (this.blockRandomised) {
            int i = this.randomCount - 1;
            this.randomCount = i;
            if (i == 0) {
                nextDecodedByte ^= 1;
                this.randomIndex = (this.randomIndex + 1) % 512;
                this.randomCount = Bzip2Rand.rNums(this.randomIndex);
            }
        }
        this.bwtBytesDecoded++;
        return nextDecodedByte;
    }

    public int blockLength() {
        return this.bwtBlockLength;
    }

    int checkCRC() {
        int computedBlockCRC = this.crc.getCRC();
        if (this.blockCRC == computedBlockCRC) {
            return computedBlockCRC;
        }
        throw new DecompressionException("block CRC error");
    }
}
