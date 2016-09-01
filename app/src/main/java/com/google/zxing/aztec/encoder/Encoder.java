package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    private static int[] bitsToWords(BitArray bitArray, int i, int i2) {
        int[] iArr = new int[i2];
        int size = bitArray.getSize() / i;
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                i4 |= bitArray.get((i3 * i) + i5) ? 1 << ((i - i5) - 1) : 0;
            }
            iArr[i3] = i4;
        }
        return iArr;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 2) {
            for (int i4 = i - i3; i4 <= i + i3; i4++) {
                bitMatrix.set(i4, i - i3);
                bitMatrix.set(i4, i + i3);
                bitMatrix.set(i - i3, i4);
                bitMatrix.set(i + i3, i4);
            }
        }
        bitMatrix.set(i - i2, i - i2);
        bitMatrix.set((i - i2) + 1, i - i2);
        bitMatrix.set(i - i2, (i - i2) + 1);
        bitMatrix.set(i + i2, i - i2);
        bitMatrix.set(i + i2, (i - i2) + 1);
        bitMatrix.set(i + i2, (i + i2) - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean z, int i, BitArray bitArray) {
        int i2 = 0;
        int i3 = i / 2;
        int i4;
        if (z) {
            while (i2 < 7) {
                i4 = (i3 - 3) + i2;
                if (bitArray.get(i2)) {
                    bitMatrix.set(i4, i3 - 5);
                }
                if (bitArray.get(i2 + 7)) {
                    bitMatrix.set(i3 + 5, i4);
                }
                if (bitArray.get(20 - i2)) {
                    bitMatrix.set(i4, i3 + 5);
                }
                if (bitArray.get(27 - i2)) {
                    bitMatrix.set(i3 - 5, i4);
                }
                i2++;
            }
            return;
        }
        while (i2 < 10) {
            i4 = ((i3 - 5) + i2) + (i2 / 5);
            if (bitArray.get(i2)) {
                bitMatrix.set(i4, i3 - 7);
            }
            if (bitArray.get(i2 + 10)) {
                bitMatrix.set(i3 + 7, i4);
            }
            if (bitArray.get(29 - i2)) {
                bitMatrix.set(i4, i3 + 7);
            }
            if (bitArray.get(39 - i2)) {
                bitMatrix.set(i3 - 7, i4);
            }
            i2++;
        }
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    public static AztecCode encode(byte[] bArr, int i, int i2) {
        int abs;
        int i3;
        int i4;
        BitArray stuffBits;
        boolean z;
        int i5;
        int i6;
        int i7;
        BitArray encode = new HighLevelEncoder(bArr).encode();
        int size = ((encode.getSize() * i) / 100) + 11;
        int size2 = size + encode.getSize();
        int totalBitsInLayer;
        if (i2 != 0) {
            boolean z2 = i2 < 0;
            abs = Math.abs(i2);
            if (abs > (z2 ? 4 : 32)) {
                throw new IllegalArgumentException(String.format("Illegal value %s for layers", new Object[]{Integer.valueOf(i2)}));
            }
            totalBitsInLayer = totalBitsInLayer(abs, z2);
            i3 = WORD_SIZE[abs];
            i4 = totalBitsInLayer - (totalBitsInLayer % i3);
            stuffBits = stuffBits(encode, i3);
            if (stuffBits.getSize() + size > i4) {
                throw new IllegalArgumentException("Data to large for user specified layer");
            } else if (!z2 || stuffBits.getSize() <= i3 * 64) {
                int i8 = totalBitsInLayer;
                z = z2;
                i5 = i8;
            } else {
                throw new IllegalArgumentException("Data to large for user specified layer");
            }
        }
        totalBitsInLayer = 0;
        BitArray bitArray = null;
        i6 = 0;
        while (i6 <= 32) {
            boolean z3 = i6 <= 3;
            abs = z3 ? i6 + 1 : i6;
            i3 = totalBitsInLayer(abs, z3);
            if (size2 <= i3) {
                if (totalBitsInLayer != WORD_SIZE[abs]) {
                    totalBitsInLayer = WORD_SIZE[abs];
                    bitArray = stuffBits(encode, totalBitsInLayer);
                }
                int i9 = i3 - (i3 % totalBitsInLayer);
                if ((!z3 || bitArray.getSize() <= totalBitsInLayer * 64) && bitArray.getSize() + size <= i9) {
                    stuffBits = bitArray;
                    i5 = i3;
                    i3 = totalBitsInLayer;
                    z = z3;
                }
            }
            i6++;
        }
        throw new IllegalArgumentException("Data too large for an Aztec code");
        BitArray generateCheckWords = generateCheckWords(stuffBits, i5, i3);
        int size3 = stuffBits.getSize() / i3;
        BitArray generateModeMessage = generateModeMessage(z, abs, size3);
        i5 = z ? (abs * 4) + 11 : (abs * 4) + 14;
        int[] iArr = new int[i5];
        if (z) {
            for (i6 = 0; i6 < iArr.length; i6++) {
                iArr[i6] = i6;
            }
            i6 = i5;
        } else {
            i6 = (i5 + 1) + ((((i5 / 2) - 1) / 15) * 2);
            i4 = i5 / 2;
            i7 = i6 / 2;
            for (i3 = 0; i3 < i4; i3++) {
                size = (i3 / 15) + i3;
                iArr[(i4 - i3) - 1] = (i7 - size) - 1;
                iArr[i4 + i3] = (size + i7) + 1;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        size = 0;
        for (size2 = 0; size2 < abs; size2++) {
            i3 = z ? ((abs - size2) * 4) + 9 : ((abs - size2) * 4) + 12;
            for (i7 = 0; i7 < i3; i7++) {
                int i10 = i7 * 2;
                for (i4 = 0; i4 < 2; i4++) {
                    if (generateCheckWords.get((size + i10) + i4)) {
                        bitMatrix.set(iArr[(size2 * 2) + i4], iArr[(size2 * 2) + i7]);
                    }
                    if (generateCheckWords.get((((i3 * 2) + size) + i10) + i4)) {
                        bitMatrix.set(iArr[(size2 * 2) + i7], iArr[((i5 - 1) - (size2 * 2)) - i4]);
                    }
                    if (generateCheckWords.get((((i3 * 4) + size) + i10) + i4)) {
                        bitMatrix.set(iArr[((i5 - 1) - (size2 * 2)) - i4], iArr[((i5 - 1) - (size2 * 2)) - i7]);
                    }
                    if (generateCheckWords.get((((i3 * 6) + size) + i10) + i4)) {
                        bitMatrix.set(iArr[((i5 - 1) - (size2 * 2)) - i7], iArr[(size2 * 2) + i4]);
                    }
                }
            }
            size = (i3 * 8) + size;
        }
        drawModeMessage(bitMatrix, z, i6, generateModeMessage);
        if (z) {
            drawBullsEye(bitMatrix, i6 / 2, 5);
        } else {
            drawBullsEye(bitMatrix, i6 / 2, 7);
            i4 = 0;
            i3 = 0;
            while (i4 < (i5 / 2) - 1) {
                for (i7 = (i6 / 2) & 1; i7 < i6; i7 += 2) {
                    bitMatrix.set((i6 / 2) - i3, i7);
                    bitMatrix.set((i6 / 2) + i3, i7);
                    bitMatrix.set(i7, (i6 / 2) - i3);
                    bitMatrix.set(i7, (i6 / 2) + i3);
                }
                i4 += 15;
                i3 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z);
        aztecCode.setSize(i6);
        aztecCode.setLayers(abs);
        aztecCode.setCodeWords(size3);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }

    private static BitArray generateCheckWords(BitArray bitArray, int i, int i2) {
        int i3 = 0;
        int size = bitArray.getSize() / i2;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i2));
        int i4 = i / i2;
        int[] bitsToWords = bitsToWords(bitArray, i2, i4);
        reedSolomonEncoder.encode(bitsToWords, i4 - size);
        size = i % i2;
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, size);
        size = bitsToWords.length;
        while (i3 < size) {
            bitArray2.appendBits(bitsToWords[i3], i2);
            i3++;
        }
        return bitArray2;
    }

    static BitArray generateModeMessage(boolean z, int i, int i2) {
        BitArray bitArray = new BitArray();
        if (z) {
            bitArray.appendBits(i - 1, 2);
            bitArray.appendBits(i2 - 1, 6);
            return generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(i - 1, 5);
        bitArray.appendBits(i2 - 1, 11);
        return generateCheckWords(bitArray, 40, 4);
    }

    private static GenericGF getGF(int i) {
        switch (i) {
            case 4:
                return GenericGF.AZTEC_PARAM;
            case 6:
                return GenericGF.AZTEC_DATA_6;
            case 8:
                return GenericGF.AZTEC_DATA_8;
            case 10:
                return GenericGF.AZTEC_DATA_10;
            case 12:
                return GenericGF.AZTEC_DATA_12;
            default:
                return null;
        }
    }

    static BitArray stuffBits(BitArray bitArray, int i) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i2 = (1 << i) - 2;
        int i3 = 0;
        while (i3 < size) {
            int i4 = 0;
            int i5 = 0;
            while (i4 < i) {
                if (i3 + i4 >= size || bitArray.get(i3 + i4)) {
                    i5 |= 1 << ((i - 1) - i4);
                }
                i4++;
            }
            if ((i5 & i2) == i2) {
                bitArray2.appendBits(i5 & i2, i);
                i3--;
            } else if ((i5 & i2) == 0) {
                bitArray2.appendBits(i5 | 1, i);
                i3--;
            } else {
                bitArray2.appendBits(i5, i);
            }
            i3 += i;
        }
        return bitArray2;
    }

    private static int totalBitsInLayer(int i, boolean z) {
        return ((z ? 88 : 112) + (i * 16)) * i;
    }
}
