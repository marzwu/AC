package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int[] blackWhiteRange(int r5, int r6, int r7, int r8, boolean r9) {
        /*
        r4 = this;
        r0 = r7 + r8;
        r2 = r0 / 2;
        r0 = r2;
    L_0x0005:
        if (r0 >= r7) goto L_0x001a;
    L_0x0007:
        r3 = r0 + 1;
        r1 = r2;
    L_0x000a:
        if (r1 < r8) goto L_0x004f;
    L_0x000c:
        r1 = r1 + -1;
        if (r1 <= r3) goto L_0x0084;
    L_0x0010:
        r0 = 2;
        r0 = new int[r0];
        r2 = 0;
        r0[r2] = r3;
        r2 = 1;
        r0[r2] = r1;
    L_0x0019:
        return r0;
    L_0x001a:
        if (r9 == 0) goto L_0x0027;
    L_0x001c:
        r1 = r4.image;
        r1 = r1.get(r0, r5);
        if (r1 == 0) goto L_0x002f;
    L_0x0024:
        r0 = r0 + -1;
        goto L_0x0005;
    L_0x0027:
        r1 = r4.image;
        r1 = r1.get(r5, r0);
        if (r1 != 0) goto L_0x0024;
    L_0x002f:
        r1 = r0;
    L_0x0030:
        r1 = r1 + -1;
        if (r1 < r7) goto L_0x003e;
    L_0x0034:
        if (r9 == 0) goto L_0x0046;
    L_0x0036:
        r3 = r4.image;
        r3 = r3.get(r1, r5);
        if (r3 == 0) goto L_0x0030;
    L_0x003e:
        r3 = r0 - r1;
        if (r1 < r7) goto L_0x0007;
    L_0x0042:
        if (r3 > r6) goto L_0x0007;
    L_0x0044:
        r0 = r1;
        goto L_0x0005;
    L_0x0046:
        r3 = r4.image;
        r3 = r3.get(r5, r1);
        if (r3 == 0) goto L_0x0030;
    L_0x004e:
        goto L_0x003e;
    L_0x004f:
        if (r9 == 0) goto L_0x005c;
    L_0x0051:
        r0 = r4.image;
        r0 = r0.get(r1, r5);
        if (r0 == 0) goto L_0x0064;
    L_0x0059:
        r1 = r1 + 1;
        goto L_0x000a;
    L_0x005c:
        r0 = r4.image;
        r0 = r0.get(r5, r1);
        if (r0 != 0) goto L_0x0059;
    L_0x0064:
        r0 = r1;
    L_0x0065:
        r0 = r0 + 1;
        if (r0 >= r8) goto L_0x0073;
    L_0x0069:
        if (r9 == 0) goto L_0x007b;
    L_0x006b:
        r2 = r4.image;
        r2 = r2.get(r0, r5);
        if (r2 == 0) goto L_0x0065;
    L_0x0073:
        r2 = r0 - r1;
        if (r0 >= r8) goto L_0x000c;
    L_0x0077:
        if (r2 > r6) goto L_0x000c;
    L_0x0079:
        r1 = r0;
        goto L_0x000a;
    L_0x007b:
        r2 = r4.image;
        r2 = r2.get(r5, r0);
        if (r2 == 0) goto L_0x0065;
    L_0x0083:
        goto L_0x0073;
    L_0x0084:
        r0 = 0;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.blackWhiteRange(int, int, int, int, boolean):int[]");
    }

    private ResultPoint findCornerFromCenter(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) throws NotFoundException {
        int i10 = i;
        int i11 = i5;
        int[] iArr = null;
        while (i11 < i8 && i11 >= i7 && i10 < i4 && i10 >= i3) {
            int[] blackWhiteRange = i2 == 0 ? blackWhiteRange(i11, i9, i3, i4, true) : blackWhiteRange(i10, i9, i7, i8, false);
            if (blackWhiteRange != null) {
                i10 += i2;
                iArr = blackWhiteRange;
                i11 += i6;
            } else if (iArr == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (i2 == 0) {
                int i12 = i11 - i6;
                if (iArr[0] >= i) {
                    return new ResultPoint((float) iArr[1], (float) i12);
                }
                if (iArr[1] <= i) {
                    return new ResultPoint((float) iArr[0], (float) i12);
                }
                return new ResultPoint((float) (i6 > 0 ? iArr[0] : iArr[1]), (float) i12);
            } else {
                i11 = i10 - i2;
                if (iArr[0] >= i5) {
                    return new ResultPoint((float) i11, (float) iArr[1]);
                }
                if (iArr[1] <= i5) {
                    return new ResultPoint((float) i11, (float) iArr[0]);
                }
                return new ResultPoint((float) i11, (float) (i2 < 0 ? iArr[0] : iArr[1]));
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i = height / 2;
        int i2 = width / 2;
        int max = Math.max(1, height / 256);
        int max2 = Math.max(1, width / 256);
        int y = ((int) findCornerFromCenter(i2, 0, 0, width, i, -max, 0, height, i2 / 2).getY()) - 1;
        ResultPoint findCornerFromCenter = findCornerFromCenter(i2, -max2, 0, width, i, 0, y, height, i / 2);
        int x = ((int) findCornerFromCenter.getX()) - 1;
        ResultPoint findCornerFromCenter2 = findCornerFromCenter(i2, max2, x, width, i, 0, y, height, i / 2);
        width = ((int) findCornerFromCenter2.getX()) + 1;
        ResultPoint findCornerFromCenter3 = findCornerFromCenter(i2, 0, x, width, i, max, y, height, i2 / 2);
        r15 = new ResultPoint[4];
        r15[0] = findCornerFromCenter(i2, 0, x, width, i, -max, y, ((int) findCornerFromCenter3.getY()) + 1, i2 / 4);
        r15[1] = findCornerFromCenter;
        r15[2] = findCornerFromCenter2;
        r15[3] = findCornerFromCenter3;
        return r15;
    }
}
