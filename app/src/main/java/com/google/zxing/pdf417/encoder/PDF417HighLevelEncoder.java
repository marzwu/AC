package com.google.zxing.pdf417.encoder;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED = new byte[128];
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION = new byte[128];
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW;
    private static final byte[] TEXT_PUNCTUATION_RAW;

    static {
        byte b;
        byte b2 = (byte) 0;
        r0 = new byte[30];
        TEXT_MIXED_RAW = r0;
        r0 = new byte[30];
        r0[0] = (byte) 59;
        r0[1] = (byte) 60;
        r0[2] = (byte) 62;
        r0[3] = (byte) 64;
        r0[4] = (byte) 91;
        r0[5] = (byte) 92;
        r0[6] = (byte) 93;
        r0[7] = (byte) 95;
        r0[8] = (byte) 96;
        r0[9] = (byte) 126;
        r0[10] = (byte) 33;
        r0[11] = (byte) 13;
        r0[12] = (byte) 9;
        r0[13] = (byte) 44;
        r0[14] = (byte) 58;
        r0[15] = (byte) 10;
        r0[16] = (byte) 45;
        r0[17] = (byte) 46;
        r0[18] = (byte) 36;
        r0[19] = (byte) 47;
        r0[20] = (byte) 34;
        r0[21] = (byte) 124;
        r0[22] = (byte) 42;
        r0[23] = (byte) 40;
        r0[24] = (byte) 41;
        r0[25] = (byte) 63;
        r0[26] = (byte) 123;
        r0[27] = (byte) 125;
        r0[28] = (byte) 39;
        TEXT_PUNCTUATION_RAW = r0;
        Arrays.fill(MIXED, (byte) -1);
        for (b = (byte) 0; b < TEXT_MIXED_RAW.length; b = (byte) (b + 1)) {
            byte b3 = TEXT_MIXED_RAW[b];
            if (b3 > (byte) 0) {
                MIXED[b3] = b;
            }
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (b2 < TEXT_PUNCTUATION_RAW.length) {
            b = TEXT_PUNCTUATION_RAW[b2];
            if (b > (byte) 0) {
                PUNCTUATION[b] = b2;
            }
            b2 = (byte) (b2 + 1);
        }
    }

    private PDF417HighLevelEncoder() {
    }

    private static int determineConsecutiveBinaryCount(CharSequence charSequence, byte[] bArr, int i) throws WriterException {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(r1)) {
                i3++;
                int i4 = i2 + i3;
                if (i4 >= length) {
                    break;
                }
                charAt = charSequence.charAt(i4);
            }
            if (i3 >= 13) {
                return i2 - i;
            }
            char charAt2 = charSequence.charAt(i2);
            if (bArr[i2] != (byte) 63 || charAt2 == '?') {
                i2++;
            } else {
                throw new WriterException("Non-encodable character detected: " + charAt2 + " (Unicode: " + charAt2 + ')');
            }
        }
        return i2 - i;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            char charAt = charSequence.charAt(i);
            while (isDigit(charAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    charAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(r2) && i2 < length) {
                i3++;
                int i4 = i2 + 1;
                if (i4 < length) {
                    charAt = charSequence.charAt(i4);
                    i2 = i4;
                } else {
                    i2 = i4;
                }
            }
            if (i3 < 13) {
                if (i3 <= 0) {
                    if (!isText(charSequence.charAt(i2))) {
                        break;
                    }
                    i2++;
                }
            } else {
                return (i2 - i) - i3;
            }
        }
        return i2 - i;
    }

    private static void encodeBinary(byte[] bArr, int i, int i2, int i3, StringBuilder stringBuilder) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            stringBuilder.append('Α');
        } else {
            if ((i2 % 6 == 0 ? 1 : null) != null) {
                stringBuilder.append('Μ');
            } else {
                stringBuilder.append('΅');
            }
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                int i5;
                long j = 0;
                for (i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + ((long) (bArr[i4 + i5] & MotionEventCompat.ACTION_MASK));
                }
                for (i5 = 0; i5 < 5; i5++) {
                    cArr[i5] = (char) ((int) (j % 900));
                    j /= 900;
                }
                for (i5 = cArr.length - 1; i5 >= 0; i5--) {
                    stringBuilder.append(cArr[i5]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            stringBuilder.append((char) (bArr[i4] & MotionEventCompat.ACTION_MASK));
            i4++;
        }
    }

    static String encodeHighLevel(String str, Compaction compaction, Charset charset) throws WriterException {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        if (charset == null) {
            charset = DEFAULT_ENCODING;
        } else if (!DEFAULT_ENCODING.equals(charset)) {
            CharacterSetECI characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name());
            if (characterSetECIByName != null) {
                encodingECI(characterSetECIByName.getValue(), stringBuilder);
            }
        }
        int length = str.length();
        byte[] bArr = null;
        if (compaction == Compaction.TEXT) {
            encodeText(str, 0, length, stringBuilder, 0);
        } else if (compaction == Compaction.BYTE) {
            bArr = str.getBytes(charset);
            encodeBinary(bArr, 0, bArr.length, 1, stringBuilder);
        } else if (compaction == Compaction.NUMERIC) {
            stringBuilder.append('Ά');
            encodeNumeric(str, 0, length, stringBuilder);
        } else {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i3 < length) {
                int determineConsecutiveDigitCount = determineConsecutiveDigitCount(str, i3);
                if (determineConsecutiveDigitCount >= 13) {
                    stringBuilder.append('Ά');
                    i = 2;
                    encodeNumeric(str, i3, determineConsecutiveDigitCount, stringBuilder);
                    i3 += determineConsecutiveDigitCount;
                    i2 = 0;
                } else {
                    int determineConsecutiveTextCount = determineConsecutiveTextCount(str, i3);
                    if (determineConsecutiveTextCount >= 5 || determineConsecutiveDigitCount == length) {
                        if (i != 0) {
                            stringBuilder.append('΄');
                            i = 0;
                            i2 = 0;
                        }
                        i2 = encodeText(str, i3, determineConsecutiveTextCount, stringBuilder, i2);
                        i3 += determineConsecutiveTextCount;
                    } else {
                        if (bArr == null) {
                            bArr = str.getBytes(charset);
                        }
                        determineConsecutiveDigitCount = determineConsecutiveBinaryCount(str, bArr, i3);
                        if (determineConsecutiveDigitCount == 0) {
                            determineConsecutiveDigitCount = 1;
                        }
                        if (determineConsecutiveDigitCount == 1 && i == 0) {
                            encodeBinary(bArr, i3, 1, 0, stringBuilder);
                        } else {
                            encodeBinary(bArr, i3, determineConsecutiveDigitCount, i, stringBuilder);
                            i = 1;
                            i2 = 0;
                        }
                        i3 += determineConsecutiveDigitCount;
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private static void encodeNumeric(String str, int i, int i2, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900);
        BigInteger valueOf2 = BigInteger.valueOf(0);
        int i3 = 0;
        while (i3 < i2 - 1) {
            stringBuilder2.setLength(0);
            int min = Math.min(44, i2 - i3);
            BigInteger bigInteger = new BigInteger(new StringBuilder(String.valueOf('1')).append(str.substring(i + i3, (i + i3) + min)).toString());
            do {
                stringBuilder2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = stringBuilder2.length() - 1; length >= 0; length--) {
                stringBuilder.append(stringBuilder2.charAt(length));
            }
            i3 += min;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int encodeText(java.lang.CharSequence r9, int r10, int r11, java.lang.StringBuilder r12, int r13) {
        /*
        r8 = 28;
        r7 = 27;
        r2 = 1;
        r6 = 29;
        r1 = 0;
        r5 = new java.lang.StringBuilder;
        r5.<init>(r11);
        r0 = r1;
    L_0x000e:
        r3 = r10 + r0;
        r3 = r9.charAt(r3);
        switch(r13) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0077;
            case 2: goto L_0x00b7;
            default: goto L_0x0017;
        };
    L_0x0017:
        r4 = isPunctuation(r3);
        if (r4 == 0) goto L_0x0108;
    L_0x001d:
        r4 = PUNCTUATION;
        r3 = r4[r3];
        r3 = (char) r3;
        r5.append(r3);
    L_0x0025:
        r0 = r0 + 1;
        if (r0 < r11) goto L_0x000e;
    L_0x0029:
        r6 = r5.length();
        r3 = r1;
        r4 = r1;
    L_0x002f:
        if (r3 < r6) goto L_0x010e;
    L_0x0031:
        r0 = r6 % 2;
        if (r0 == 0) goto L_0x003d;
    L_0x0035:
        r0 = r4 * 30;
        r0 = r0 + 29;
        r0 = (char) r0;
        r12.append(r0);
    L_0x003d:
        return r13;
    L_0x003e:
        r4 = isAlphaUpper(r3);
        if (r4 == 0) goto L_0x0055;
    L_0x0044:
        r4 = 32;
        if (r3 != r4) goto L_0x004e;
    L_0x0048:
        r3 = 26;
        r5.append(r3);
        goto L_0x0025;
    L_0x004e:
        r3 = r3 + -65;
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x0055:
        r4 = isAlphaLower(r3);
        if (r4 == 0) goto L_0x0060;
    L_0x005b:
        r5.append(r7);
        r13 = r2;
        goto L_0x000e;
    L_0x0060:
        r4 = isMixed(r3);
        if (r4 == 0) goto L_0x006b;
    L_0x0066:
        r13 = 2;
        r5.append(r8);
        goto L_0x000e;
    L_0x006b:
        r5.append(r6);
        r4 = PUNCTUATION;
        r3 = r4[r3];
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x0077:
        r4 = isAlphaLower(r3);
        if (r4 == 0) goto L_0x008e;
    L_0x007d:
        r4 = 32;
        if (r3 != r4) goto L_0x0087;
    L_0x0081:
        r3 = 26;
        r5.append(r3);
        goto L_0x0025;
    L_0x0087:
        r3 = r3 + -97;
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x008e:
        r4 = isAlphaUpper(r3);
        if (r4 == 0) goto L_0x009e;
    L_0x0094:
        r5.append(r7);
        r3 = r3 + -65;
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x009e:
        r4 = isMixed(r3);
        if (r4 == 0) goto L_0x00aa;
    L_0x00a4:
        r13 = 2;
        r5.append(r8);
        goto L_0x000e;
    L_0x00aa:
        r5.append(r6);
        r4 = PUNCTUATION;
        r3 = r4[r3];
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x00b7:
        r4 = isMixed(r3);
        if (r4 == 0) goto L_0x00c7;
    L_0x00bd:
        r4 = MIXED;
        r3 = r4[r3];
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x00c7:
        r4 = isAlphaUpper(r3);
        if (r4 == 0) goto L_0x00d3;
    L_0x00cd:
        r5.append(r8);
        r13 = r1;
        goto L_0x000e;
    L_0x00d3:
        r4 = isAlphaLower(r3);
        if (r4 == 0) goto L_0x00df;
    L_0x00d9:
        r5.append(r7);
        r13 = r2;
        goto L_0x000e;
    L_0x00df:
        r4 = r10 + r0;
        r4 = r4 + 1;
        if (r4 >= r11) goto L_0x00fb;
    L_0x00e5:
        r4 = r10 + r0;
        r4 = r4 + 1;
        r4 = r9.charAt(r4);
        r4 = isPunctuation(r4);
        if (r4 == 0) goto L_0x00fb;
    L_0x00f3:
        r13 = 3;
        r3 = 25;
        r5.append(r3);
        goto L_0x000e;
    L_0x00fb:
        r5.append(r6);
        r4 = PUNCTUATION;
        r3 = r4[r3];
        r3 = (char) r3;
        r5.append(r3);
        goto L_0x0025;
    L_0x0108:
        r5.append(r6);
        r13 = r1;
        goto L_0x000e;
    L_0x010e:
        r0 = r3 % 2;
        if (r0 == 0) goto L_0x0125;
    L_0x0112:
        r0 = r2;
    L_0x0113:
        if (r0 == 0) goto L_0x0127;
    L_0x0115:
        r0 = r4 * 30;
        r4 = r5.charAt(r3);
        r0 = r0 + r4;
        r0 = (char) r0;
        r12.append(r0);
    L_0x0120:
        r3 = r3 + 1;
        r4 = r0;
        goto L_0x002f;
    L_0x0125:
        r0 = r1;
        goto L_0x0113;
    L_0x0127:
        r0 = r5.charAt(r3);
        goto L_0x0120;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    private static void encodingECI(int i, StringBuilder stringBuilder) throws WriterException {
        if (i >= 0 && i < LATCH_TO_TEXT) {
            stringBuilder.append('Ο');
            stringBuilder.append((char) i);
        } else if (i < 810900) {
            stringBuilder.append('Ξ');
            stringBuilder.append((char) ((i / LATCH_TO_TEXT) - 1));
            stringBuilder.append((char) (i % LATCH_TO_TEXT));
        } else if (i < 811800) {
            stringBuilder.append('Ν');
            stringBuilder.append((char) (810900 - i));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was " + i);
        }
    }

    private static boolean isAlphaLower(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    private static boolean isAlphaUpper(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isMixed(char c) {
        return MIXED[c] != (byte) -1;
    }

    private static boolean isPunctuation(char c) {
        return PUNCTUATION[c] != (byte) -1;
    }

    private static boolean isText(char c) {
        return c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= '~');
    }
}
