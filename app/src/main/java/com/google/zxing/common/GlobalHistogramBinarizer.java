package com.google.zxing.common;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

public class GlobalHistogramBinarizer extends Binarizer {
    private static final byte[] EMPTY = new byte[0];
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets = new int[32];
    private byte[] luminances = EMPTY;

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int estimateBlackPoint(int[] iArr) throws NotFoundException {
        int i = 0;
        int length = iArr.length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i2 < length) {
            int i6;
            if (iArr[i2] > i3) {
                i4 = iArr[i2];
                i3 = i2;
            } else {
                i6 = i3;
                i3 = i4;
                i4 = i6;
            }
            if (iArr[i2] > i5) {
                i5 = iArr[i2];
            }
            i2++;
            i6 = i4;
            i4 = i3;
            i3 = i6;
        }
        int i7 = 0;
        i3 = 0;
        while (i < length) {
            i2 = i - i4;
            i2 *= iArr[i] * i2;
            if (i2 > i7) {
                i3 = i2;
                i2 = i;
            } else {
                i2 = i3;
                i3 = i7;
            }
            i++;
            i7 = i3;
            i3 = i2;
        }
        if (i4 <= i3) {
            i6 = i3;
            i3 = i4;
            i4 = i6;
        }
        if (i4 - i3 <= length / 16) {
            throw NotFoundException.getNotFoundInstance();
        }
        length = i4 - 1;
        i7 = -1;
        i = i4 - 1;
        while (i > i3) {
            i2 = i - i3;
            i2 = ((i2 * i2) * (i4 - i)) * (i5 - iArr[i]);
            if (i2 > i7) {
                i7 = i;
            } else {
                i2 = i7;
                i7 = length;
            }
            i--;
            length = i7;
            i7 = i2;
        }
        return length << 3;
    }

    private void initArrays(int i) {
        if (this.luminances.length < i) {
            this.luminances = new byte[i];
        }
        for (int i2 = 0; i2 < 32; i2++) {
            this.buckets[i2] = 0;
        }
    }

    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        int i;
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        BitMatrix bitMatrix = new BitMatrix(width, height);
        initArrays(width);
        int[] iArr = this.buckets;
        for (i = 1; i < 5; i++) {
            int i2;
            byte[] row = luminanceSource.getRow((height * i) / 5, this.luminances);
            int i3 = (width * 4) / 5;
            for (i2 = width / 5; i2 < i3; i2++) {
                int i4 = (row[i2] & MotionEventCompat.ACTION_MASK) >> 3;
                iArr[i4] = iArr[i4] + 1;
            }
        }
        int estimateBlackPoint = estimateBlackPoint(iArr);
        byte[] matrix = luminanceSource.getMatrix();
        for (i = 0; i < height; i++) {
            int i5 = i * width;
            for (i2 = 0; i2 < width; i2++) {
                if ((matrix[i5 + i2] & MotionEventCompat.ACTION_MASK) < estimateBlackPoint) {
                    bitMatrix.set(i2, i);
                }
            }
        }
        return bitMatrix;
    }

    public BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException {
        int i2;
        int i3;
        int i4 = 1;
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        if (bitArray == null || bitArray.getSize() < width) {
            bitArray = new BitArray(width);
        } else {
            bitArray.clear();
        }
        initArrays(width);
        byte[] row = luminanceSource.getRow(i, this.luminances);
        int[] iArr = this.buckets;
        for (i2 = 0; i2 < width; i2++) {
            i3 = (row[i2] & MotionEventCompat.ACTION_MASK) >> 3;
            iArr[i3] = iArr[i3] + 1;
        }
        i3 = estimateBlackPoint(iArr);
        i2 = row[1] & MotionEventCompat.ACTION_MASK;
        int i5 = row[0] & MotionEventCompat.ACTION_MASK;
        while (i4 < width - 1) {
            int i6 = row[i4 + 1] & MotionEventCompat.ACTION_MASK;
            if ((((i2 * 4) - i5) - i6) / 2 < i3) {
                bitArray.set(i4);
            }
            i4++;
            i5 = i2;
            i2 = i6;
        }
        return bitArray;
    }
}
