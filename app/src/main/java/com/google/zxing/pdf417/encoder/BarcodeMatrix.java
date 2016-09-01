package com.google.zxing.pdf417.encoder;

import java.lang.reflect.Array;

public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    BarcodeMatrix(int i, int i2) {
        this.matrix = new BarcodeRow[i];
        int length = this.matrix.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.matrix[i3] = new BarcodeRow(((i2 + 4) * 17) + 1);
        }
        this.width = i2 * 17;
        this.height = i;
        this.currentRow = -1;
    }

    BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public byte[][] getMatrix() {
        return getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int i, int i2) {
        int i3 = 0;
        byte[][] bArr = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{this.height * i2, this.width * i});
        int i4 = this.height * i2;
        while (i3 < i4) {
            bArr[(i4 - i3) - 1] = this.matrix[i3 / i2].getScaledRow(i);
            i3++;
        }
        return bArr;
    }

    void set(int i, int i2, byte b) {
        this.matrix[i2].set(i, b);
    }

    void startRow() {
        this.currentRow++;
    }
}
