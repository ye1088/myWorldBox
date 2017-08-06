package io.netty.handler.codec.compression;

import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.lang.reflect.Array;

final class Bzip2HuffmanStageDecoder {
    final int alphabetSize;
    private final int[][] codeBases;
    private final int[][] codeLimits;
    private final int[][] codeSymbols;
    int currentAlpha;
    int currentGroup;
    int currentLength = -1;
    int currentSelector;
    private int currentTable;
    private int groupIndex = -1;
    private int groupPosition = -1;
    private final int[] minimumLengths;
    boolean modifyLength;
    private final Bzip2BitReader reader;
    byte[] selectors;
    final byte[][] tableCodeLengths;
    final Bzip2MoveToFrontTable tableMTF = new Bzip2MoveToFrontTable();
    final int totalTables;

    Bzip2HuffmanStageDecoder(Bzip2BitReader reader, int totalTables, int alphabetSize) {
        this.reader = reader;
        this.totalTables = totalTables;
        this.alphabetSize = alphabetSize;
        this.minimumLengths = new int[totalTables];
        this.codeBases = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, 25});
        this.codeLimits = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, 24});
        this.codeSymbols = (int[][]) Array.newInstance(Integer.TYPE, new int[]{totalTables, 258});
        this.tableCodeLengths = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{totalTables, 258});
    }

    void createHuffmanDecodingTables() {
        int alphabetSize = this.alphabetSize;
        for (int table = 0; table < this.tableCodeLengths.length; table++) {
            int i;
            int[] tableBases = this.codeBases[table];
            int[] tableLimits = this.codeLimits[table];
            int[] tableSymbols = this.codeSymbols[table];
            byte[] codeLengths = this.tableCodeLengths[table];
            byte minimumLength = BinaryMemcacheOpcodes.QUITQ;
            byte maximumLength = (byte) 0;
            for (i = 0; i < alphabetSize; i++) {
                byte currLength = codeLengths[i];
                maximumLength = Math.max(currLength, maximumLength);
                minimumLength = Math.min(currLength, minimumLength);
            }
            this.minimumLengths[table] = minimumLength;
            for (i = 0; i < alphabetSize; i++) {
                int i2 = codeLengths[i] + 1;
                tableBases[i2] = tableBases[i2] + 1;
            }
            int b = tableBases[0];
            for (i = 1; i < 25; i++) {
                b += tableBases[i];
                tableBases[i] = b;
            }
            int code = 0;
            for (byte i3 = minimumLength; i3 <= maximumLength; i3++) {
                int base = code;
                code += tableBases[i3 + 1] - tableBases[i3];
                tableBases[i3] = base - tableBases[i3];
                tableLimits[i3] = code - 1;
                code <<= 1;
            }
            byte bitLength = minimumLength;
            int codeIndex = 0;
            while (bitLength <= maximumLength) {
                int symbol = 0;
                int codeIndex2 = codeIndex;
                while (symbol < alphabetSize) {
                    if (codeLengths[symbol] == bitLength) {
                        codeIndex = codeIndex2 + 1;
                        tableSymbols[codeIndex2] = symbol;
                    } else {
                        codeIndex = codeIndex2;
                    }
                    symbol++;
                    codeIndex2 = codeIndex;
                }
                bitLength++;
                codeIndex = codeIndex2;
            }
        }
        this.currentTable = this.selectors[0];
    }

    int nextSymbol() {
        int i = this.groupPosition + 1;
        this.groupPosition = i;
        if (i % 50 == 0) {
            this.groupIndex++;
            if (this.groupIndex == this.selectors.length) {
                throw new DecompressionException("error decoding block");
            }
            this.currentTable = this.selectors[this.groupIndex] & 255;
        }
        Bzip2BitReader reader = this.reader;
        int currentTable = this.currentTable;
        int[] tableLimits = this.codeLimits[currentTable];
        int[] tableBases = this.codeBases[currentTable];
        int[] tableSymbols = this.codeSymbols[currentTable];
        int codeLength = this.minimumLengths[currentTable];
        int codeBits = reader.readBits(codeLength);
        while (codeLength <= 23) {
            if (codeBits <= tableLimits[codeLength]) {
                return tableSymbols[codeBits - tableBases[codeLength]];
            }
            codeBits = (codeBits << 1) | reader.readBits(1);
            codeLength++;
        }
        throw new DecompressionException("a_isRightVersion valid code was not recognised");
    }
}
