package com.google.zxing.common;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i2, i});
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 <= i7) {
                i7 = i6;
            }
            int i8 = 0;
            while (i8 < i) {
                int i9 = i8 << 3;
                i6 = i3 - 8;
                if (i9 <= i6) {
                    i6 = i9;
                }
                int i10 = 0;
                int i11 = MotionEventCompat.ACTION_MASK;
                int i12 = 0;
                i9 = 0;
                i6 += i7 * i3;
                while (i9 < 8) {
                    int i13 = 0;
                    int i14 = i10;
                    while (i13 < 8) {
                        i10 = bArr[i6 + i13] & MotionEventCompat.ACTION_MASK;
                        int i15 = i14 + i10;
                        if (i10 < i11) {
                            i11 = i10;
                        }
                        i13++;
                        i12 = i10 > i12 ? i10 : i12;
                        i14 = i15;
                    }
                    if (i12 - i11 > MIN_DYNAMIC_RANGE) {
                        i9++;
                        i6 += i3;
                        while (i9 < 8) {
                            i10 = i14;
                            for (i14 = 0; i14 < 8; i14++) {
                                i10 += bArr[i6 + i14] & MotionEventCompat.ACTION_MASK;
                            }
                            i9++;
                            i6 += i3;
                            i14 = i10;
                        }
                    }
                    i9++;
                    i6 += i3;
                    i10 = i14;
                }
                i6 = i10 >> 6;
                if (i12 - i11 <= MIN_DYNAMIC_RANGE) {
                    i9 = i11 / 2;
                    if (i5 > 0 && i8 > 0) {
                        i6 = ((iArr[i5 - 1][i8] + (iArr[i5][i8 - 1] * 2)) + iArr[i5 - 1][i8 - 1]) / 4;
                        if (i11 < i6) {
                        }
                    }
                    i6 = i9;
                }
                iArr[i5][i8] = i6;
                i8++;
            }
        }
        return iArr;
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 <= i7) {
                i7 = i6;
            }
            for (int i8 = 0; i8 < i; i8++) {
                i6 = i8 << 3;
                int i9 = i3 - 8;
                if (i6 <= i9) {
                    i9 = i6;
                }
                int cap = cap(i8, 2, i - 3);
                int cap2 = cap(i5, 2, i2 - 3);
                int i10 = 0;
                for (i6 = -2; i6 <= 2; i6++) {
                    int[] iArr2 = iArr[cap2 + i6];
                    i10 += iArr2[cap + 2] + (((iArr2[cap - 2] + iArr2[cap - 1]) + iArr2[cap]) + iArr2[cap + 1]);
                }
                thresholdBlock(bArr, i9, i7, i10 / 25, i3, bitMatrix);
            }
        }
    }

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & MotionEventCompat.ACTION_MASK) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) {
            return this.matrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < MINIMUM_DIMENSION || height < MINIMUM_DIMENSION) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = height >> 3;
            if ((height & 7) != 0) {
                i2++;
            }
            int[][] calculateBlackPoints = calculateBlackPoints(matrix, i, i2, width, height);
            BitMatrix bitMatrix = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i, i2, width, height, calculateBlackPoints, bitMatrix);
            this.matrix = bitMatrix;
        }
        return this.matrix;
    }
}
