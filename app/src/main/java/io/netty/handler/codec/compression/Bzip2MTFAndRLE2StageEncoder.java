package io.netty.handler.codec.compression;

final class Bzip2MTFAndRLE2StageEncoder {
    private int alphabetSize;
    private final int[] bwtBlock;
    private final int bwtLength;
    private final boolean[] bwtValuesPresent;
    private final char[] mtfBlock;
    private int mtfLength;
    private final int[] mtfSymbolFrequencies = new int[258];

    Bzip2MTFAndRLE2StageEncoder(int[] bwtBlock, int bwtLength, boolean[] bwtValuesPresent) {
        this.bwtBlock = bwtBlock;
        this.bwtLength = bwtLength;
        this.bwtValuesPresent = bwtValuesPresent;
        this.mtfBlock = new char[(bwtLength + 1)];
    }

    void encode() {
        int i;
        int bwtLength = this.bwtLength;
        boolean[] bwtValuesPresent = this.bwtValuesPresent;
        int[] bwtBlock = this.bwtBlock;
        char[] mtfBlock = this.mtfBlock;
        int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
        byte[] huffmanSymbolMap = new byte[256];
        Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();
        int totalUniqueValues = 0;
        for (i = 0; i < huffmanSymbolMap.length; i++) {
            if (bwtValuesPresent[i]) {
                int totalUniqueValues2 = totalUniqueValues + 1;
                huffmanSymbolMap[i] = (byte) totalUniqueValues;
                totalUniqueValues = totalUniqueValues2;
            }
        }
        int endOfBlockSymbol = totalUniqueValues + 1;
        int mtfIndex = 0;
        int repeatCount = 0;
        int totalRunAs = 0;
        int totalRunBs = 0;
        for (i = 0; i < bwtLength; i++) {
            int mtfIndex2;
            int mtfPosition = symbolMTF.valueToFront(huffmanSymbolMap[bwtBlock[i] & 255]);
            if (mtfPosition == 0) {
                repeatCount++;
            } else {
                if (repeatCount > 0) {
                    repeatCount--;
                    mtfIndex2 = mtfIndex;
                    while (true) {
                        if ((repeatCount & 1) == 0) {
                            mtfIndex = mtfIndex2 + 1;
                            mtfBlock[mtfIndex2] = '\u0000';
                            totalRunAs++;
                        } else {
                            mtfIndex = mtfIndex2 + 1;
                            mtfBlock[mtfIndex2] = '\u0001';
                            totalRunBs++;
                        }
                        if (repeatCount <= 1) {
                            break;
                        }
                        repeatCount = (repeatCount - 2) >>> 1;
                        mtfIndex2 = mtfIndex;
                    }
                    repeatCount = 0;
                }
                mtfIndex2 = mtfIndex + 1;
                mtfBlock[mtfIndex] = (char) (mtfPosition + 1);
                int i2 = mtfPosition + 1;
                mtfSymbolFrequencies[i2] = mtfSymbolFrequencies[i2] + 1;
                mtfIndex = mtfIndex2;
            }
        }
        if (repeatCount > 0) {
            repeatCount--;
            mtfIndex2 = mtfIndex;
            while (true) {
                if ((repeatCount & 1) == 0) {
                    mtfIndex = mtfIndex2 + 1;
                    mtfBlock[mtfIndex2] = '\u0000';
                    totalRunAs++;
                } else {
                    mtfIndex = mtfIndex2 + 1;
                    mtfBlock[mtfIndex2] = '\u0001';
                    totalRunBs++;
                }
                if (repeatCount <= 1) {
                    break;
                }
                repeatCount = (repeatCount - 2) >>> 1;
                mtfIndex2 = mtfIndex;
            }
        }
        mtfBlock[mtfIndex] = (char) endOfBlockSymbol;
        mtfSymbolFrequencies[endOfBlockSymbol] = mtfSymbolFrequencies[endOfBlockSymbol] + 1;
        mtfSymbolFrequencies[0] = mtfSymbolFrequencies[0] + totalRunAs;
        mtfSymbolFrequencies[1] = mtfSymbolFrequencies[1] + totalRunBs;
        this.mtfLength = mtfIndex + 1;
        this.alphabetSize = endOfBlockSymbol + 1;
    }

    char[] mtfBlock() {
        return this.mtfBlock;
    }

    int mtfLength() {
        return this.mtfLength;
    }

    int mtfAlphabetSize() {
        return this.alphabetSize;
    }

    int[] mtfSymbolFrequencies() {
        return this.mtfSymbolFrequencies;
    }
}
