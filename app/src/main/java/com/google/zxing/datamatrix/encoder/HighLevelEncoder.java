package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.util.Arrays;

public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = 'þ';
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = 'î';
    static final char LATCH_TO_BASE256 = 'ç';
    static final char LATCH_TO_C40 = 'æ';
    static final char LATCH_TO_EDIFACT = 'ð';
    static final char LATCH_TO_TEXT = 'ï';
    private static final char MACRO_05 = 'ì';
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = 'í';
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = '';
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = 'ë';
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = 'þ';

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
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

    public static String encodeHighLevel(String str) {
        return encodeHighLevel(str, SymbolShapeHint.FORCE_NONE, null, null);
    }

    public static String encodeHighLevel(String str, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        int i = 0;
        Encoder[] encoderArr = new Encoder[]{new ASCIIEncoder(), new C40Encoder(), new TextEncoder(), new X12Encoder(), new EdifactEncoder(), new Base256Encoder()};
        EncoderContext encoderContext = new EncoderContext(str);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (str.startsWith(MACRO_05_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_05);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += MACRO_05_HEADER.length();
        } else if (str.startsWith(MACRO_06_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_06);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += MACRO_06_HEADER.length();
        }
        while (encoderContext.hasMoreCharacters()) {
            encoderArr[i].encode(encoderContext);
            if (encoderContext.getNewEncoding() >= 0) {
                i = encoderContext.getNewEncoding();
                encoderContext.resetEncoderSignal();
            }
        }
        int codewordCount = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity();
        if (!(codewordCount >= dataCapacity || i == 0 || i == 5)) {
            encoderContext.writeCodeword('þ');
        }
        StringBuilder codewords = encoderContext.getCodewords();
        if (codewords.length() < dataCapacity) {
            codewords.append(PAD);
        }
        while (codewords.length() < dataCapacity) {
            codewords.append(randomize253State(PAD, codewords.length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }

    private static int findMinimums(float[] fArr, int[] iArr, int i, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        int i2 = i;
        for (int i3 = 0; i3 < 6; i3++) {
            iArr[i3] = (int) Math.ceil((double) fArr[i3]);
            int i4 = iArr[i3];
            if (i2 > i4) {
                Arrays.fill(bArr, (byte) 0);
                i2 = i4;
            }
            if (i2 == i4) {
                bArr[i3] = (byte) (bArr[i3] + 1);
            }
        }
        return i2;
    }

    private static int getMinimumCount(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i < 6) {
            i2 += bArr[i];
            i++;
        }
        return i2;
    }

    static void illegalCharacter(char c) {
        String toHexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + new StringBuilder(String.valueOf("0000".substring(0, 4 - toHexString.length()))).append(toHexString).toString() + ')');
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean isExtendedASCII(char c) {
        return c >= '' && c <= 'ÿ';
    }

    private static boolean isNativeC40(char c) {
        return c == ' ' || ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z'));
    }

    private static boolean isNativeEDIFACT(char c) {
        return c >= ' ' && c <= '^';
    }

    private static boolean isNativeText(char c) {
        return c == ' ' || ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z'));
    }

    private static boolean isNativeX12(char c) {
        return isX12TermSep(c) || c == ' ' || ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z'));
    }

    private static boolean isSpecialB256(char c) {
        return false;
    }

    private static boolean isX12TermSep(char c) {
        return c == '\r' || c == '*' || c == '>';
    }

    static int lookAheadTest(CharSequence charSequence, int i, int i2) {
        if (i >= charSequence.length()) {
            return i2;
        }
        float[] fArr;
        int[] iArr;
        int i3;
        if (i2 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            fArr = new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr[i2] = 0.0f;
        }
        int i4 = 0;
        while (i + i4 != charSequence.length()) {
            char charAt = charSequence.charAt(i + i4);
            i4++;
            if (isDigit(charAt)) {
                fArr[0] = (float) (0.5d + ((double) fArr[0]));
            } else if (isExtendedASCII(charAt)) {
                fArr[0] = (float) ((int) Math.ceil((double) fArr[0]));
                fArr[0] = 2.0f + fArr[0];
            } else {
                fArr[0] = (float) ((int) Math.ceil((double) fArr[0]));
                fArr[0] = 1.0f + fArr[0];
            }
            if (isNativeC40(charAt)) {
                fArr[1] = 0.6666667f + fArr[1];
            } else if (isExtendedASCII(charAt)) {
                fArr[1] = 2.6666667f + fArr[1];
            } else {
                fArr[1] = 1.3333334f + fArr[1];
            }
            if (isNativeText(charAt)) {
                fArr[2] = 0.6666667f + fArr[2];
            } else if (isExtendedASCII(charAt)) {
                fArr[2] = 2.6666667f + fArr[2];
            } else {
                fArr[2] = 1.3333334f + fArr[2];
            }
            if (isNativeX12(charAt)) {
                fArr[3] = 0.6666667f + fArr[3];
            } else if (isExtendedASCII(charAt)) {
                fArr[3] = 4.3333335f + fArr[3];
            } else {
                fArr[3] = 3.3333333f + fArr[3];
            }
            if (isNativeEDIFACT(charAt)) {
                fArr[4] = 0.75f + fArr[4];
            } else if (isExtendedASCII(charAt)) {
                fArr[4] = 4.25f + fArr[4];
            } else {
                fArr[4] = 3.25f + fArr[4];
            }
            if (isSpecialB256(charAt)) {
                fArr[5] = 4.0f + fArr[5];
            } else {
                fArr[5] = 1.0f + fArr[5];
            }
            if (i4 >= 4) {
                iArr = new int[6];
                byte[] bArr = new byte[6];
                findMinimums(fArr, iArr, Integer.MAX_VALUE, bArr);
                int minimumCount = getMinimumCount(bArr);
                if (iArr[0] < iArr[5] && iArr[0] < iArr[1] && iArr[0] < iArr[2] && iArr[0] < iArr[3] && iArr[0] < iArr[4]) {
                    return 0;
                }
                if (iArr[5] < iArr[0] || ((bArr[1] + bArr[2]) + bArr[3]) + bArr[4] == 0) {
                    return 5;
                }
                if (minimumCount == 1 && bArr[4] > (byte) 0) {
                    return 4;
                }
                if (minimumCount == 1 && bArr[2] > (byte) 0) {
                    return 2;
                }
                if (minimumCount == 1 && bArr[3] > (byte) 0) {
                    return 3;
                }
                if (iArr[1] + 1 < iArr[0] && iArr[1] + 1 < iArr[5] && iArr[1] + 1 < iArr[4] && iArr[1] + 1 < iArr[2]) {
                    if (iArr[1] < iArr[3]) {
                        return 1;
                    }
                    if (iArr[1] == iArr[3]) {
                        i3 = (i + i4) + 1;
                        while (i3 < charSequence.length()) {
                            char charAt2 = charSequence.charAt(i3);
                            if (!isX12TermSep(charAt2)) {
                                if (!isNativeX12(charAt2)) {
                                    break;
                                }
                                i3++;
                            } else {
                                return 3;
                            }
                        }
                        return 1;
                    }
                }
            }
        }
        byte[] bArr2 = new byte[6];
        iArr = new int[6];
        i3 = findMinimums(fArr, iArr, Integer.MAX_VALUE, bArr2);
        int minimumCount2 = getMinimumCount(bArr2);
        return iArr[0] != i3 ? (minimumCount2 != 1 || bArr2[5] <= (byte) 0) ? (minimumCount2 != 1 || bArr2[4] <= (byte) 0) ? (minimumCount2 != 1 || bArr2[2] <= (byte) 0) ? (minimumCount2 != 1 || bArr2[3] <= (byte) 0) ? 1 : 3 : 2 : 4 : 5 : 0;
    }

    private static char randomize253State(char c, int i) {
        int i2 = (((i * 149) % 253) + 1) + c;
        return i2 <= 254 ? (char) i2 : (char) (i2 - 254);
    }
}
