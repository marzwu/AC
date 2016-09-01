package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version.ECB;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (bArr.length != version.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        int i;
        int i2;
        ECBlocks eCBlocksForLevel = version.getECBlocksForLevel(errorCorrectionLevel);
        ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
        int i3 = 0;
        for (ECB count : eCBlocks) {
            i3 += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i3];
        int length = eCBlocks.length;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length) {
            ECB ecb = eCBlocks[i4];
            i3 = i5;
            i5 = 0;
            while (i5 < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                i = i3 + 1;
                dataBlockArr[i3] = new DataBlock(dataCodewords, new byte[(dataCodewords + eCBlocksForLevel.getECCodewordsPerBlock())]);
                i5++;
                i3 = i;
            }
            i4++;
            i5 = i3;
        }
        i = dataBlockArr[0].codewords.length;
        i3 = dataBlockArr.length - 1;
        while (i3 >= 0 && dataBlockArr[i3].codewords.length != i) {
            i3--;
        }
        length = i3 + 1;
        int eCCodewordsPerBlock = i - eCBlocksForLevel.getECCodewordsPerBlock();
        i3 = 0;
        for (i2 = 0; i2 < eCCodewordsPerBlock; i2++) {
            i4 = 0;
            while (i4 < i5) {
                i = i3 + 1;
                dataBlockArr[i4].codewords[i2] = bArr[i3];
                i4++;
                i3 = i;
            }
        }
        i4 = length;
        while (i4 < i5) {
            i = i3 + 1;
            dataBlockArr[i4].codewords[eCCodewordsPerBlock] = bArr[i3];
            i4++;
            i3 = i;
        }
        int length2 = dataBlockArr[0].codewords.length;
        i = eCCodewordsPerBlock;
        while (i < length2) {
            i4 = i3;
            i2 = 0;
            while (i2 < i5) {
                eCCodewordsPerBlock = i4 + 1;
                dataBlockArr[i2].codewords[i2 < length ? i : i + 1] = bArr[i4];
                i4 = eCCodewordsPerBlock;
                i2++;
            }
            i++;
            i3 = i4;
        }
        return dataBlockArr;
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
