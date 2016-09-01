package com.google.zxing.qrcode.decoder;

final class FormatInformation {
    private static final int[] BITS_SET_IN_HALF_BYTE;
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;

    static {
        r0 = new int[32][];
        int[] iArr = new int[]{FORMAT_INFO_MASK_QR, iArr};
        r0[1] = new int[]{20773, 1};
        r0[2] = new int[]{24188, 2};
        r0[3] = new int[]{23371, 3};
        r0[4] = new int[]{17913, 4};
        r0[5] = new int[]{16590, 5};
        r0[6] = new int[]{20375, 6};
        r0[7] = new int[]{19104, 7};
        r0[8] = new int[]{30660, 8};
        r0[9] = new int[]{29427, 9};
        r0[10] = new int[]{32170, 10};
        r0[11] = new int[]{30877, 11};
        r0[12] = new int[]{26159, 12};
        r0[13] = new int[]{25368, 13};
        r0[14] = new int[]{27713, 14};
        r0[15] = new int[]{26998, 15};
        r0[16] = new int[]{5769, 16};
        r0[17] = new int[]{5054, 17};
        r0[18] = new int[]{7399, 18};
        r0[19] = new int[]{6608, 19};
        r0[20] = new int[]{1890, 20};
        r0[21] = new int[]{597, 21};
        r0[22] = new int[]{3340, 22};
        r0[23] = new int[]{2107, 23};
        r0[24] = new int[]{13663, 24};
        r0[25] = new int[]{12392, 25};
        r0[26] = new int[]{16177, 26};
        r0[27] = new int[]{14854, 27};
        r0[28] = new int[]{9396, 28};
        r0[29] = new int[]{8579, 29};
        r0[30] = new int[]{11994, 30};
        r0[31] = new int[]{11245, 31};
        FORMAT_INFO_DECODE_LOOKUP = r0;
        int[] iArr2 = new int[16];
        iArr2[1] = 1;
        iArr2[2] = 1;
        iArr2[3] = 2;
        iArr2[4] = 1;
        iArr2[5] = 2;
        iArr2[6] = 2;
        iArr2[7] = 3;
        iArr2[8] = 1;
        iArr2[9] = 2;
        iArr2[10] = 2;
        iArr2[11] = 3;
        iArr2[12] = 2;
        iArr2[13] = 3;
        iArr2[14] = 3;
        iArr2[15] = 4;
        BITS_SET_IN_HALF_BYTE = iArr2;
    }

    private FormatInformation(int i) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits((i >> 3) & 3);
        this.dataMask = (byte) (i & 7);
    }

    static FormatInformation decodeFormatInformation(int i, int i2) {
        FormatInformation doDecodeFormatInformation = doDecodeFormatInformation(i, i2);
        return doDecodeFormatInformation != null ? doDecodeFormatInformation : doDecodeFormatInformation(i ^ FORMAT_INFO_MASK_QR, i2 ^ FORMAT_INFO_MASK_QR);
    }

    private static FormatInformation doDecodeFormatInformation(int i, int i2) {
        int i3 = Integer.MAX_VALUE;
        int[][] iArr = FORMAT_INFO_DECODE_LOOKUP;
        int length = iArr.length;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length) {
            int[] iArr2 = iArr[i4];
            int i6 = iArr2[0];
            if (i6 == i || i6 == i2) {
                return new FormatInformation(iArr2[1]);
            }
            int i7;
            int numBitsDiffering = numBitsDiffering(i, i6);
            if (numBitsDiffering < i3) {
                i3 = iArr2[1];
            } else {
                numBitsDiffering = i3;
                i3 = i5;
            }
            if (i != i2) {
                i5 = numBitsDiffering(i2, i6);
                if (i5 < numBitsDiffering) {
                    i3 = iArr2[1];
                    i4++;
                    i7 = i3;
                    i3 = i5;
                    i5 = i7;
                }
            }
            i5 = numBitsDiffering;
            i4++;
            i7 = i3;
            i3 = i5;
            i5 = i7;
        }
        return i3 <= 3 ? new FormatInformation(i5) : null;
    }

    static int numBitsDiffering(int i, int i2) {
        int i3 = i ^ i2;
        return BITS_SET_IN_HALF_BYTE[(i3 >>> 28) & 15] + ((((((BITS_SET_IN_HALF_BYTE[i3 & 15] + BITS_SET_IN_HALF_BYTE[(i3 >>> 4) & 15]) + BITS_SET_IN_HALF_BYTE[(i3 >>> 8) & 15]) + BITS_SET_IN_HALF_BYTE[(i3 >>> 12) & 15]) + BITS_SET_IN_HALF_BYTE[(i3 >>> 16) & 15]) + BITS_SET_IN_HALF_BYTE[(i3 >>> 20) & 15]) + BITS_SET_IN_HALF_BYTE[(i3 >>> 24) & 15]);
    }

    public boolean equals(Object obj) {
        if (obj instanceof FormatInformation) {
            FormatInformation formatInformation = (FormatInformation) obj;
            if (this.errorCorrectionLevel == formatInformation.errorCorrectionLevel && this.dataMask == formatInformation.dataMask) {
                return true;
            }
        }
        return false;
    }

    byte getDataMask() {
        return this.dataMask;
    }

    ErrorCorrectionLevel getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    public int hashCode() {
        return (this.errorCorrectionLevel.ordinal() << 3) | this.dataMask;
    }
}
