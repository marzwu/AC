package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS = new int[][]{new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    private static int decodeCode(BitArray bitArray, int[] iArr, int i) throws NotFoundException {
        OneDReader.recordPattern(bitArray, i, iArr);
        float f = MAX_AVG_VARIANCE;
        int i2 = -1;
        for (int i3 = 0; i3 < CODE_PATTERNS.length; i3++) {
            float patternMatchVariance = OneDReader.patternMatchVariance(iArr, CODE_PATTERNS[i3], MAX_INDIVIDUAL_VARIANCE);
            if (patternMatchVariance < f) {
                i2 = i3;
                f = patternMatchVariance;
            }
        }
        if (i2 >= 0) {
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Object obj = new int[6];
        int length = obj.length;
        int i = nextSet;
        int i2 = nextSet;
        int i3 = 0;
        nextSet = 0;
        while (i < size) {
            if ((bitArray.get(i) ^ nextSet) != 0) {
                obj[i3] = obj[i3] + 1;
            } else {
                if (i3 == length - 1) {
                    float f = MAX_AVG_VARIANCE;
                    int i4 = -1;
                    int i5 = CODE_START_A;
                    while (i5 <= CODE_START_C) {
                        float patternMatchVariance = OneDReader.patternMatchVariance(obj, CODE_PATTERNS[i5], MAX_INDIVIDUAL_VARIANCE);
                        if (patternMatchVariance < f) {
                            i4 = i5;
                        } else {
                            patternMatchVariance = f;
                        }
                        i5++;
                        f = patternMatchVariance;
                    }
                    if (i4 < 0 || !bitArray.isRange(Math.max(0, i2 - ((i - i2) / 2)), i2, false)) {
                        i2 += obj[0] + obj[1];
                        System.arraycopy(obj, 2, obj, 0, length - 2);
                        obj[length - 2] = null;
                        obj[length - 1] = null;
                        i3--;
                    } else {
                        return new int[]{i2, i, i4};
                    }
                }
                i3++;
                obj[i3] = 1;
                nextSet = nextSet != 0 ? 0 : 1;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.zxing.Result decodeRow(int r24, com.google.zxing.common.BitArray r25, java.util.Map<com.google.zxing.DecodeHintType, ?> r26) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
        r23 = this;
        if (r26 == 0) goto L_0x002f;
    L_0x0002:
        r2 = com.google.zxing.DecodeHintType.ASSUME_GS1;
        r0 = r26;
        r2 = r0.containsKey(r2);
        if (r2 == 0) goto L_0x002f;
    L_0x000c:
        r2 = 1;
    L_0x000d:
        r16 = findStartPattern(r25);
        r3 = 2;
        r5 = r16[r3];
        r17 = new java.util.ArrayList;
        r3 = 20;
        r0 = r17;
        r0.<init>(r3);
        r3 = (byte) r5;
        r3 = java.lang.Byte.valueOf(r3);
        r0 = r17;
        r0.add(r3);
        switch(r5) {
            case 103: goto L_0x0031;
            case 104: goto L_0x0081;
            case 105: goto L_0x0084;
            default: goto L_0x002a;
        };
    L_0x002a:
        r2 = com.google.zxing.FormatException.getFormatInstance();
        throw r2;
    L_0x002f:
        r2 = 0;
        goto L_0x000d;
    L_0x0031:
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0033:
        r14 = 0;
        r10 = 0;
        r18 = new java.lang.StringBuilder;
        r4 = 20;
        r0 = r18;
        r0.<init>(r4);
        r4 = 0;
        r12 = r16[r4];
        r4 = 1;
        r11 = r16[r4];
        r4 = 6;
        r0 = new int[r4];
        r19 = r0;
        r13 = 0;
        r9 = 0;
        r4 = 0;
        r8 = 1;
        r7 = 0;
        r6 = 0;
        r15 = r10;
        r10 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r8;
        r8 = r13;
        r13 = r9;
        r9 = r14;
        r22 = r11;
        r11 = r12;
        r12 = r22;
    L_0x005c:
        if (r9 == 0) goto L_0x0087;
    L_0x005e:
        r2 = r12 - r11;
        r0 = r25;
        r6 = r0.getNextUnset(r12);
        r7 = r25.getSize();
        r9 = r6 - r11;
        r9 = r9 / 2;
        r9 = r9 + r6;
        r7 = java.lang.Math.min(r7, r9);
        r9 = 0;
        r0 = r25;
        r6 = r0.isRange(r6, r7, r9);
        if (r6 != 0) goto L_0x02f8;
    L_0x007c:
        r2 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r2;
    L_0x0081:
        r3 = 100;
        goto L_0x0033;
    L_0x0084:
        r3 = 99;
        goto L_0x0033;
    L_0x0087:
        r0 = r25;
        r1 = r19;
        r14 = decodeCode(r0, r1, r12);
        r8 = (byte) r14;
        r8 = java.lang.Byte.valueOf(r8);
        r0 = r17;
        r0.add(r8);
        r8 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r14 == r8) goto L_0x009e;
    L_0x009d:
        r5 = 1;
    L_0x009e:
        r8 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r14 == r8) goto L_0x00a7;
    L_0x00a2:
        r3 = r3 + 1;
        r8 = r3 * r14;
        r4 = r4 + r8;
    L_0x00a7:
        r0 = r19;
        r0 = r0.length;
        r20 = r0;
        r8 = 0;
        r11 = r12;
    L_0x00ae:
        r0 = r20;
        if (r8 < r0) goto L_0x00d4;
    L_0x00b2:
        switch(r14) {
            case 103: goto L_0x00db;
            case 104: goto L_0x00db;
            case 105: goto L_0x00db;
            default: goto L_0x00b5;
        };
    L_0x00b5:
        r8 = 0;
        switch(r10) {
            case 99: goto L_0x0276;
            case 100: goto L_0x01ba;
            case 101: goto L_0x00e0;
            default: goto L_0x00b9;
        };
    L_0x00b9:
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
    L_0x00c0:
        if (r15 == 0) goto L_0x037a;
    L_0x00c2:
        r15 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r5 != r15) goto L_0x02f4;
    L_0x00c6:
        r5 = 100;
    L_0x00c8:
        r15 = r9;
        r9 = r10;
        r10 = r5;
        r5 = r8;
        r8 = r13;
        r13 = r14;
        r22 = r11;
        r11 = r12;
        r12 = r22;
        goto L_0x005c;
    L_0x00d4:
        r21 = r19[r8];
        r11 = r11 + r21;
        r8 = r8 + 1;
        goto L_0x00ae;
    L_0x00db:
        r2 = com.google.zxing.FormatException.getFormatInstance();
        throw r2;
    L_0x00e0:
        r8 = 64;
        if (r14 >= r8) goto L_0x0103;
    L_0x00e4:
        if (r6 != r7) goto L_0x00f8;
    L_0x00e6:
        r6 = r14 + 32;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
    L_0x00ee:
        r8 = 0;
        r6 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x00f8:
        r6 = r14 + 32;
        r6 = r6 + 128;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
        goto L_0x00ee;
    L_0x0103:
        r8 = 96;
        if (r14 >= r8) goto L_0x0124;
    L_0x0107:
        if (r6 != r7) goto L_0x011b;
    L_0x0109:
        r6 = r14 + -64;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
    L_0x0111:
        r8 = 0;
        r6 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x011b:
        r6 = r14 + 64;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
        goto L_0x0111;
    L_0x0124:
        r8 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r14 == r8) goto L_0x0129;
    L_0x0128:
        r5 = 0;
    L_0x0129:
        r8 = 0;
        switch(r14) {
            case 96: goto L_0x00b9;
            case 97: goto L_0x00b9;
            case 98: goto L_0x0136;
            case 99: goto L_0x01a3;
            case 100: goto L_0x0197;
            case 101: goto L_0x016c;
            case 102: goto L_0x0141;
            case 103: goto L_0x012d;
            case 104: goto L_0x012d;
            case 105: goto L_0x012d;
            case 106: goto L_0x01af;
            default: goto L_0x012d;
        };
    L_0x012d:
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0136:
        r8 = 1;
        r10 = 100;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0141:
        r8 = 0;
        if (r2 == 0) goto L_0x00b9;
    L_0x0144:
        r8 = r18.length();
        if (r8 != 0) goto L_0x015b;
    L_0x014a:
        r8 = "]C1";
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x015b:
        r8 = 29;
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x016c:
        if (r7 != 0) goto L_0x017c;
    L_0x016e:
        if (r6 == 0) goto L_0x017c;
    L_0x0170:
        r7 = 1;
        r8 = 0;
        r6 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x017c:
        if (r7 == 0) goto L_0x018c;
    L_0x017e:
        if (r6 == 0) goto L_0x018c;
    L_0x0180:
        r8 = 0;
        r6 = 0;
        r7 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x018c:
        r6 = 1;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0197:
        r10 = 100;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01a3:
        r10 = 99;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01af:
        r9 = 1;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01ba:
        r8 = 96;
        if (r14 >= r8) goto L_0x01de;
    L_0x01be:
        if (r6 != r7) goto L_0x01d3;
    L_0x01c0:
        r6 = r14 + 32;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
    L_0x01c8:
        r8 = 0;
        r6 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01d3:
        r6 = r14 + 32;
        r6 = r6 + 128;
        r6 = (char) r6;
        r0 = r18;
        r0.append(r6);
        goto L_0x01c8;
    L_0x01de:
        r8 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r14 == r8) goto L_0x01e3;
    L_0x01e2:
        r5 = 0;
    L_0x01e3:
        r8 = 0;
        switch(r14) {
            case 96: goto L_0x00b9;
            case 97: goto L_0x00b9;
            case 98: goto L_0x01f1;
            case 99: goto L_0x025f;
            case 100: goto L_0x0228;
            case 101: goto L_0x0253;
            case 102: goto L_0x01fd;
            case 103: goto L_0x01e7;
            case 104: goto L_0x01e7;
            case 105: goto L_0x01e7;
            case 106: goto L_0x026b;
            default: goto L_0x01e7;
        };
    L_0x01e7:
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01f1:
        r8 = 1;
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x01fd:
        r8 = 0;
        if (r2 == 0) goto L_0x00b9;
    L_0x0200:
        r8 = r18.length();
        if (r8 != 0) goto L_0x0217;
    L_0x0206:
        r8 = "]C1";
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0217:
        r8 = 29;
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0228:
        if (r7 != 0) goto L_0x0238;
    L_0x022a:
        if (r6 == 0) goto L_0x0238;
    L_0x022c:
        r7 = 1;
        r8 = 0;
        r6 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0238:
        if (r7 == 0) goto L_0x0248;
    L_0x023a:
        if (r6 == 0) goto L_0x0248;
    L_0x023c:
        r8 = 0;
        r6 = 0;
        r7 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0248:
        r6 = 1;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0253:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x025f:
        r10 = 99;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x026b:
        r9 = 1;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0276:
        r8 = 100;
        if (r14 >= r8) goto L_0x0294;
    L_0x027a:
        r8 = 10;
        if (r14 >= r8) goto L_0x0285;
    L_0x027e:
        r8 = 48;
        r0 = r18;
        r0.append(r8);
    L_0x0285:
        r0 = r18;
        r0.append(r14);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x0294:
        r8 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r14 == r8) goto L_0x0299;
    L_0x0298:
        r5 = 0;
    L_0x0299:
        switch(r14) {
            case 100: goto L_0x02a6;
            case 101: goto L_0x02dd;
            case 102: goto L_0x02b2;
            case 103: goto L_0x029c;
            case 104: goto L_0x029c;
            case 105: goto L_0x029c;
            case 106: goto L_0x02e9;
            default: goto L_0x029c;
        };
    L_0x029c:
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02a6:
        r10 = 100;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02b2:
        r8 = 0;
        if (r2 == 0) goto L_0x00b9;
    L_0x02b5:
        r8 = r18.length();
        if (r8 != 0) goto L_0x02cc;
    L_0x02bb:
        r8 = "]C1";
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02cc:
        r8 = 29;
        r0 = r18;
        r0.append(r8);
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02dd:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02e9:
        r9 = 1;
        r8 = 0;
        r22 = r5;
        r5 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r22;
        goto L_0x00c0;
    L_0x02f4:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x00c8;
    L_0x02f8:
        r3 = r3 * r8;
        r3 = r4 - r3;
        r3 = r3 % 103;
        if (r3 == r8) goto L_0x0304;
    L_0x02ff:
        r2 = com.google.zxing.ChecksumException.getChecksumInstance();
        throw r2;
    L_0x0304:
        r3 = r18.length();
        if (r3 != 0) goto L_0x030f;
    L_0x030a:
        r2 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r2;
    L_0x030f:
        if (r3 <= 0) goto L_0x031e;
    L_0x0311:
        if (r5 == 0) goto L_0x031e;
    L_0x0313:
        r4 = 99;
        if (r10 != r4) goto L_0x0360;
    L_0x0317:
        r4 = r3 + -2;
        r0 = r18;
        r0.delete(r4, r3);
    L_0x031e:
        r3 = 1;
        r3 = r16[r3];
        r4 = 0;
        r4 = r16[r4];
        r3 = r3 + r4;
        r3 = (float) r3;
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r4 = r3 / r4;
        r3 = (float) r11;
        r2 = (float) r2;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r2 = r2 / r5;
        r5 = r3 + r2;
        r6 = r17.size();
        r7 = new byte[r6];
        r2 = 0;
        r3 = r2;
    L_0x0339:
        if (r3 < r6) goto L_0x0368;
    L_0x033b:
        r2 = r18.toString();
        r3 = 2;
        r3 = new com.google.zxing.ResultPoint[r3];
        r6 = new com.google.zxing.ResultPoint;
        r0 = r24;
        r8 = (float) r0;
        r6.<init>(r4, r8);
        r4 = 0;
        r3[r4] = r6;
        r4 = new com.google.zxing.ResultPoint;
        r0 = r24;
        r6 = (float) r0;
        r4.<init>(r5, r6);
        r5 = 1;
        r3[r5] = r4;
        r4 = new com.google.zxing.Result;
        r5 = com.google.zxing.BarcodeFormat.CODE_128;
        r4.<init>(r2, r7, r3, r5);
        return r4;
    L_0x0360:
        r4 = r3 + -1;
        r0 = r18;
        r0.delete(r4, r3);
        goto L_0x031e;
    L_0x0368:
        r0 = r17;
        r2 = r0.get(r3);
        r2 = (java.lang.Byte) r2;
        r2 = r2.byteValue();
        r7[r3] = r2;
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0339;
    L_0x037a:
        r15 = r9;
        r9 = r10;
        r10 = r5;
        r5 = r8;
        r8 = r13;
        r13 = r14;
        r22 = r11;
        r11 = r12;
        r12 = r22;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
