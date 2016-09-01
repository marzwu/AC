package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class EAN13Reader extends UPCEANReader {
    static final int[] FIRST_DIGIT_ENCODINGS;
    private final int[] decodeMiddleCounters = new int[4];

    static {
        int[] iArr = new int[10];
        iArr[1] = 11;
        iArr[2] = 13;
        iArr[3] = 14;
        iArr[4] = 19;
        iArr[5] = 25;
        iArr[6] = 28;
        iArr[7] = 21;
        iArr[8] = 22;
        iArr[9] = 26;
        FIRST_DIGIT_ENCODINGS = iArr;
    }

    private static void determineFirstDigit(StringBuilder stringBuilder, int i) throws NotFoundException {
        for (int i2 = 0; i2 < 10; i2++) {
            if (i == FIRST_DIGIT_ENCODINGS[i2]) {
                stringBuilder.insert(0, (char) (i2 + 48));
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder stringBuilder) throws NotFoundException {
        int i;
        int[] iArr2 = this.decodeMiddleCounters;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i2 = iArr[1];
        int i3 = 0;
        int i4 = 0;
        while (i3 < 6 && i2 < size) {
            int decodeDigit = UPCEANReader.decodeDigit(bitArray, iArr2, i2, L_AND_G_PATTERNS);
            stringBuilder.append((char) ((decodeDigit % 10) + 48));
            i = i2;
            for (int i5 : iArr2) {
                i += i5;
            }
            if (decodeDigit >= 10) {
                i4 |= 1 << (5 - i3);
            }
            i3++;
            i2 = i;
        }
        determineFirstDigit(stringBuilder, i4);
        i4 = UPCEANReader.findGuardPattern(bitArray, i2, true, MIDDLE_PATTERN)[1];
        i = 0;
        while (i < 6 && i4 < size) {
            stringBuilder.append((char) (UPCEANReader.decodeDigit(bitArray, iArr2, i4, L_PATTERNS) + 48));
            i2 = i4;
            for (int decodeDigit2 : iArr2) {
                i2 += decodeDigit2;
            }
            i++;
            i4 = i2;
        }
        return i4;
    }

    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_13;
    }
}
