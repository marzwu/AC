package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[((boundingBox.getMaxY() - boundingBox.getMinY()) + 1)];
    }

    final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    final Codeword getCodeword(int i) {
        return this.codewords[imageRowToCodewordIndex(i)];
    }

    final Codeword getCodewordNearby(int i) {
        Codeword codeword = getCodeword(i);
        if (codeword != null) {
            return codeword;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            int imageRowToCodewordIndex = imageRowToCodewordIndex(i) - i2;
            if (imageRowToCodewordIndex >= 0) {
                codeword = this.codewords[imageRowToCodewordIndex];
                if (codeword != null) {
                    return codeword;
                }
            }
            imageRowToCodewordIndex = imageRowToCodewordIndex(i) + i2;
            if (imageRowToCodewordIndex < this.codewords.length) {
                codeword = this.codewords[imageRowToCodewordIndex];
                if (codeword != null) {
                    return codeword;
                }
            }
        }
        return null;
    }

    final Codeword[] getCodewords() {
        return this.codewords;
    }

    final int imageRowToCodewordIndex(int i) {
        return i - this.boundingBox.getMinY();
    }

    final void setCodeword(int i, Codeword codeword) {
        this.codewords[imageRowToCodewordIndex(i)] = codeword;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        Codeword[] codewordArr = this.codewords;
        int length = codewordArr.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            int i3;
            Codeword codeword = codewordArr[i2];
            if (codeword == null) {
                Object[] objArr = new Object[1];
                i3 = i + 1;
                objArr[0] = Integer.valueOf(i);
                formatter.format("%3d:    |   %n", objArr);
            } else {
                r8 = new Object[3];
                i3 = i + 1;
                r8[0] = Integer.valueOf(i);
                r8[1] = Integer.valueOf(codeword.getRowNumber());
                r8[2] = Integer.valueOf(codeword.getValue());
                formatter.format("%3d: %3d|%3d%n", r8);
            }
            i2++;
            i = i3;
        }
        String formatter2 = formatter.toString();
        formatter.close();
        return formatter2;
    }
}
