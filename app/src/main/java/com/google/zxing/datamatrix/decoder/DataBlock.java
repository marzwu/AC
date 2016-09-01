package com.google.zxing.datamatrix.decoder;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version) {
        int i;
        int i2;
        int i3;
        ECBlocks eCBlocks = version.getECBlocks();
        ECB[] eCBlocks2 = eCBlocks.getECBlocks();
        int i4 = 0;
        for (ECB count : eCBlocks2) {
            i4 += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i4];
        i4 = 0;
        for (ECB ecb : eCBlocks2) {
            i = 0;
            while (i < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                i3 = i4 + 1;
                dataBlockArr[i4] = new DataBlock(dataCodewords, new byte[(dataCodewords + eCBlocks.getECCodewords())]);
                i++;
                i4 = i3;
            }
        }
        int length = dataBlockArr[0].codewords.length - eCBlocks.getECCodewords();
        int i5 = length - 1;
        int i6 = 0;
        i3 = 0;
        while (i6 < i5) {
            i = i3;
            i2 = 0;
            while (i2 < i4) {
                i3 = i + 1;
                dataBlockArr[i2].codewords[i6] = bArr[i];
                i2++;
                i = i3;
            }
            i6++;
            i3 = i;
        }
        i5 = version.getVersionNumber() == 24 ? 1 : 0;
        i = i5 != 0 ? 8 : i4;
        i2 = i3;
        i6 = 0;
        while (i6 < i) {
            i3 = i2 + 1;
            dataBlockArr[i6].codewords[length - 1] = bArr[i2];
            i6++;
            i2 = i3;
        }
        int length2 = dataBlockArr[0].codewords.length;
        i = i2;
        i3 = length;
        while (i3 < length2) {
            i2 = i;
            i6 = 0;
            while (i6 < i4) {
                i = (i5 == 0 || i6 <= 7) ? i3 : i3 - 1;
                length = i2 + 1;
                dataBlockArr[i6].codewords[i] = bArr[i2];
                i2 = length;
                i6++;
            }
            i3++;
            i = i2;
        }
        if (i == bArr.length) {
            return dataBlockArr;
        }
        throw new IllegalArgumentException();
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
