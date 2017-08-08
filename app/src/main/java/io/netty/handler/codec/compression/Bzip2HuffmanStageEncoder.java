package io.netty.handler.codec.compression;

import com.MCWorld.mcfloat.InstanceZones.e;
import com.MCWorld.module.h;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.tools.zip.UnixStat;

final class Bzip2HuffmanStageEncoder {
    private static final int HUFFMAN_HIGH_SYMBOL_COST = 15;
    private final int[][] huffmanCodeLengths;
    private final int[][] huffmanMergedCodeSymbols;
    private final int mtfAlphabetSize;
    private final char[] mtfBlock;
    private final int mtfLength;
    private final int[] mtfSymbolFrequencies;
    private final byte[] selectors;
    private final Bzip2BitWriter writer;

    Bzip2HuffmanStageEncoder(Bzip2BitWriter writer, char[] mtfBlock, int mtfLength, int mtfAlphabetSize, int[] mtfSymbolFrequencies) {
        this.writer = writer;
        this.mtfBlock = mtfBlock;
        this.mtfLength = mtfLength;
        this.mtfAlphabetSize = mtfAlphabetSize;
        this.mtfSymbolFrequencies = mtfSymbolFrequencies;
        int totalTables = selectTableCount(mtfLength);
        this.huffmanCodeLengths = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, mtfAlphabetSize});
        this.huffmanMergedCodeSymbols = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, mtfAlphabetSize});
        this.selectors = new byte[(((mtfLength + 50) - 1) / 50)];
    }

    private static int selectTableCount(int mtfLength) {
        if (mtfLength >= 2400) {
            return 6;
        }
        if (mtfLength >= e.Wz) {
            return 5;
        }
        if (mtfLength >= h.arp) {
            return 4;
        }
        if (mtfLength >= 200) {
            return 3;
        }
        return 2;
    }

    private static void generateHuffmanCodeLengths(int alphabetSize, int[] symbolFrequencies, int[] codeLengths) {
        int i;
        int[] mergedFrequenciesAndIndices = new int[alphabetSize];
        int[] sortedFrequencies = new int[alphabetSize];
        for (i = 0; i < alphabetSize; i++) {
            mergedFrequenciesAndIndices[i] = (symbolFrequencies[i] << 9) | i;
        }
        Arrays.sort(mergedFrequenciesAndIndices);
        for (i = 0; i < alphabetSize; i++) {
            sortedFrequencies[i] = mergedFrequenciesAndIndices[i] >>> 9;
        }
        Bzip2HuffmanAllocator.allocateHuffmanCodeLengths(sortedFrequencies, 20);
        for (i = 0; i < alphabetSize; i++) {
            codeLengths[mergedFrequenciesAndIndices[i] & UnixStat.DEFAULT_LINK_PERM] = sortedFrequencies[i];
        }
    }

    private void generateHuffmanOptimisationSeeds() {
        int[][] huffmanCodeLengths = this.huffmanCodeLengths;
        int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
        int mtfAlphabetSize = this.mtfAlphabetSize;
        int totalTables = huffmanCodeLengths.length;
        int remainingLength = this.mtfLength;
        int lowCostEnd = -1;
        int i = 0;
        while (i < totalTables) {
            int targetCumulativeFrequency = remainingLength / (totalTables - i);
            int lowCostStart = lowCostEnd + 1;
            int actualCumulativeFrequency = 0;
            int lowCostEnd2 = lowCostEnd;
            while (actualCumulativeFrequency < targetCumulativeFrequency && lowCostEnd2 < mtfAlphabetSize - 1) {
                lowCostEnd = lowCostEnd2 + 1;
                actualCumulativeFrequency += mtfSymbolFrequencies[lowCostEnd];
                lowCostEnd2 = lowCostEnd;
            }
            if (lowCostEnd2 <= lowCostStart || i == 0 || i == totalTables - 1 || ((totalTables - i) & 1) != 0) {
                lowCostEnd = lowCostEnd2;
            } else {
                lowCostEnd = lowCostEnd2 - 1;
                actualCumulativeFrequency -= mtfSymbolFrequencies[lowCostEnd2];
            }
            int[] tableCodeLengths = huffmanCodeLengths[i];
            int j = 0;
            while (j < mtfAlphabetSize) {
                if (j < lowCostStart || j > lowCostEnd) {
                    tableCodeLengths[j] = 15;
                }
                j++;
            }
            remainingLength -= actualCumulativeFrequency;
            i++;
        }
    }

    private void optimiseSelectorsAndHuffmanTables(boolean storeSelectors) {
        byte i;
        char[] mtfBlock = this.mtfBlock;
        byte[] selectors = this.selectors;
        int[][] huffmanCodeLengths = this.huffmanCodeLengths;
        int mtfLength = this.mtfLength;
        int mtfAlphabetSize = this.mtfAlphabetSize;
        byte totalTables = huffmanCodeLengths.length;
        int[][] tableFrequencies = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, mtfAlphabetSize});
        int groupStart = 0;
        int selectorIndex = 0;
        while (groupStart < mtfLength) {
            int i2;
            int selectorIndex2;
            int groupEnd = Math.min(groupStart + 50, mtfLength) - 1;
            short[] cost = new short[totalTables];
            for (i2 = groupStart; i2 <= groupEnd; i2++) {
                int value = mtfBlock[i2];
                for (byte j = (byte) 0; j < totalTables; j++) {
                    cost[j] = (short) (cost[j] + huffmanCodeLengths[j][value]);
                }
            }
            byte bestTable = (byte) 0;
            int bestCost = cost[0];
            for (i = (byte) 1; i < totalTables; i = (byte) (i + 1)) {
                int tableCost = cost[i];
                if (tableCost < bestCost) {
                    bestCost = tableCost;
                    bestTable = i;
                }
            }
            int[] bestGroupFrequencies = tableFrequencies[bestTable];
            for (i2 = groupStart; i2 <= groupEnd; i2++) {
                char c = mtfBlock[i2];
                bestGroupFrequencies[c] = bestGroupFrequencies[c] + 1;
            }
            if (storeSelectors) {
                selectorIndex2 = selectorIndex + 1;
                selectors[selectorIndex] = bestTable;
            } else {
                selectorIndex2 = selectorIndex;
            }
            groupStart = groupEnd + 1;
            selectorIndex = selectorIndex2;
        }
        for (i = (byte) 0; i < totalTables; i++) {
            generateHuffmanCodeLengths(mtfAlphabetSize, tableFrequencies[i], huffmanCodeLengths[i]);
        }
    }

    private void assignHuffmanCodeSymbols() {
        int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
        int[][] huffmanCodeLengths = this.huffmanCodeLengths;
        int mtfAlphabetSize = this.mtfAlphabetSize;
        int totalTables = huffmanCodeLengths.length;
        for (int i = 0; i < totalTables; i++) {
            int j;
            int[] tableLengths = huffmanCodeLengths[i];
            int minimumLength = 32;
            int maximumLength = 0;
            for (j = 0; j < mtfAlphabetSize; j++) {
                int length = tableLengths[j];
                if (length > maximumLength) {
                    maximumLength = length;
                }
                if (length < minimumLength) {
                    minimumLength = length;
                }
            }
            int code = 0;
            for (j = minimumLength; j <= maximumLength; j++) {
                for (int k = 0; k < mtfAlphabetSize; k++) {
                    if ((huffmanCodeLengths[i][k] & 255) == j) {
                        huffmanMergedCodeSymbols[i][k] = (j << 24) | code;
                        code++;
                    }
                }
                code <<= 1;
            }
        }
    }

    private void writeSelectorsAndHuffmanTables(ByteBuf out) {
        Bzip2BitWriter writer = this.writer;
        byte[] selectors = this.selectors;
        int totalSelectors = selectors.length;
        int[][] huffmanCodeLengths = this.huffmanCodeLengths;
        int totalTables = huffmanCodeLengths.length;
        int mtfAlphabetSize = this.mtfAlphabetSize;
        writer.writeBits(out, 3, (long) totalTables);
        writer.writeBits(out, 15, (long) totalSelectors);
        Bzip2MoveToFrontTable selectorMTF = new Bzip2MoveToFrontTable();
        for (byte selector : selectors) {
            writer.writeUnary(out, selectorMTF.valueToFront(selector));
        }
        for (int[] tableLengths : huffmanCodeLengths) {
            int currentLength = tableLengths[0];
            writer.writeBits(out, 5, (long) currentLength);
            for (int j = 0; j < mtfAlphabetSize; j++) {
                int codeLength = tableLengths[j];
                int value = currentLength < codeLength ? 2 : 3;
                int delta = Math.abs(codeLength - currentLength);
                while (true) {
                    int delta2 = delta - 1;
                    if (delta <= 0) {
                        break;
                    }
                    writer.writeBits(out, 2, (long) value);
                    delta = delta2;
                }
                writer.writeBoolean(out, false);
                currentLength = codeLength;
            }
        }
    }

    private void writeBlockData(ByteBuf out) {
        Bzip2BitWriter writer = this.writer;
        int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
        byte[] selectors = this.selectors;
        char[] mtf = this.mtfBlock;
        int mtfLength = this.mtfLength;
        int mtfIndex = 0;
        int selectorIndex = 0;
        while (mtfIndex < mtfLength) {
            int groupEnd = Math.min(mtfIndex + 50, mtfLength) - 1;
            int selectorIndex2 = selectorIndex + 1;
            int[] tableMergedCodeSymbols = huffmanMergedCodeSymbols[selectors[selectorIndex]];
            int mtfIndex2 = mtfIndex;
            while (mtfIndex2 <= groupEnd) {
                mtfIndex = mtfIndex2 + 1;
                int mergedCodeSymbol = tableMergedCodeSymbols[mtf[mtfIndex2]];
                writer.writeBits(out, mergedCodeSymbol >>> 24, (long) mergedCodeSymbol);
                mtfIndex2 = mtfIndex;
            }
            mtfIndex = mtfIndex2;
            selectorIndex = selectorIndex2;
        }
    }

    void encode(ByteBuf out) {
        generateHuffmanOptimisationSeeds();
        int i = 3;
        while (i >= 0) {
            optimiseSelectorsAndHuffmanTables(i == 0);
            i--;
        }
        assignHuffmanCodeSymbols();
        writeSelectorsAndHuffmanTables(out);
        writeBlockData(out);
    }
}
