package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = null;
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900 = new BigInteger[16];
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', '-', '.', '$', '/', '+', '%', '*', '=', '^'};
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS = new char[]{';', '<', '>', '@', '[', '\\', ']', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', '-', '.', '$', '/', '\"', '|', '*', '(', ')', '?', '{', '}', '\''};
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    private enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode() {
        int[] iArr = $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode;
        if (iArr == null) {
            iArr = new int[Mode.values().length];
            try {
                iArr[Mode.ALPHA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Mode.ALPHA_SHIFT.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Mode.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Mode.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Mode.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[Mode.PUNCT_SHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = iArr;
        }
        return iArr;
    }

    static {
        EXP900[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900);
        EXP900[1] = valueOf;
        for (int i = 2; i < EXP900.length; i++) {
            EXP900[i] = EXP900[i - 1].multiply(valueOf);
        }
    }

    private DecodedBitStreamParser() {
    }

    private static int byteCompaction(int i, int[] iArr, Charset charset, int i2, StringBuilder stringBuilder) {
        int i3;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i4;
        long j;
        int i5;
        int i6;
        if (i == BYTE_COMPACTION_MODE_LATCH) {
            i4 = 0;
            j = 0;
            int[] iArr2 = new int[6];
            Object obj = null;
            i5 = i2 + 1;
            int i7 = iArr[i2];
            i3 = i5;
            while (i3 < iArr[0] && r3 == null) {
                i5 = i4 + 1;
                iArr2[i4] = i7;
                j = (j * 900) + ((long) i7);
                int i8 = i3 + 1;
                i7 = iArr[i3];
                if (i7 == TEXT_COMPACTION_MODE_LATCH || i7 == BYTE_COMPACTION_MODE_LATCH || i7 == NUMERIC_COMPACTION_MODE_LATCH || i7 == BYTE_COMPACTION_MODE_LATCH_6 || i7 == 928 || i7 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD || i7 == MACRO_PDF417_TERMINATOR) {
                    obj = 1;
                    i3 = i8 - 1;
                    i4 = i5;
                } else if (i5 % 5 != 0 || i5 <= 0) {
                    i4 = i5;
                    i3 = i8;
                } else {
                    for (i4 = 0; i4 < 6; i4++) {
                        byteArrayOutputStream.write((byte) ((int) (j >> ((5 - i4) * 8))));
                    }
                    j = 0;
                    i4 = 0;
                    i3 = i8;
                }
            }
            if (i3 == iArr[0] && i7 < TEXT_COMPACTION_MODE_LATCH) {
                i6 = i4 + 1;
                iArr2[i4] = i7;
                i4 = i6;
            }
            for (i7 = 0; i7 < i4; i7++) {
                byteArrayOutputStream.write((byte) iArr2[i7]);
            }
        } else if (i == BYTE_COMPACTION_MODE_LATCH_6) {
            i6 = 0;
            j = 0;
            Object obj2 = null;
            i3 = i2;
            while (i3 < iArr[0] && r2 == null) {
                i4 = i3 + 1;
                i5 = iArr[i3];
                if (i5 < TEXT_COMPACTION_MODE_LATCH) {
                    i6++;
                    j = (j * 900) + ((long) i5);
                    i3 = i4;
                } else if (i5 == TEXT_COMPACTION_MODE_LATCH || i5 == BYTE_COMPACTION_MODE_LATCH || i5 == NUMERIC_COMPACTION_MODE_LATCH || i5 == BYTE_COMPACTION_MODE_LATCH_6 || i5 == 928 || i5 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD || i5 == MACRO_PDF417_TERMINATOR) {
                    i3 = i4 - 1;
                    obj2 = 1;
                } else {
                    i3 = i4;
                }
                if (i6 % 5 == 0 && i6 > 0) {
                    for (i6 = 0; i6 < 6; i6++) {
                        byteArrayOutputStream.write((byte) ((int) (j >> ((5 - i6) * 8))));
                    }
                    j = 0;
                    i6 = 0;
                }
            }
        } else {
            i3 = i2;
        }
        stringBuilder.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return i3;
    }

    static DecoderResult decode(int[] iArr, String str) throws FormatException {
        StringBuilder stringBuilder = new StringBuilder(iArr.length * 2);
        Charset charset = DEFAULT_ENCODING;
        int i = 2;
        int i2 = iArr[1];
        PDF417ResultMetadata pDF417ResultMetadata = new PDF417ResultMetadata();
        while (i < iArr[0]) {
            switch (i2) {
                case TEXT_COMPACTION_MODE_LATCH /*900*/:
                    i2 = textCompaction(iArr, i, stringBuilder);
                    break;
                case BYTE_COMPACTION_MODE_LATCH /*901*/:
                case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                    i2 = byteCompaction(i2, iArr, charset, i, stringBuilder);
                    break;
                case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                    i2 = numericCompaction(iArr, i, stringBuilder);
                    break;
                case MODE_SHIFT_TO_BYTE_COMPACTION_MODE /*913*/:
                    i2 = i + 1;
                    stringBuilder.append((char) iArr[i]);
                    break;
                case MACRO_PDF417_TERMINATOR /*922*/:
                case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                    throw FormatException.getFormatInstance();
                case ECI_USER_DEFINED /*925*/:
                    i2 = i + 1;
                    break;
                case ECI_GENERAL_PURPOSE /*926*/:
                    i2 = i + 2;
                    break;
                case ECI_CHARSET /*927*/:
                    i2 = i + 1;
                    charset = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(iArr[i]).name());
                    break;
                case 928:
                    i2 = decodeMacroBlock(iArr, i, pDF417ResultMetadata);
                    break;
                default:
                    i2 = textCompaction(iArr, i - 1, stringBuilder);
                    break;
            }
            if (i2 < iArr.length) {
                i = i2 + 1;
                i2 = iArr[i2];
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (stringBuilder.length() == 0) {
            throw FormatException.getFormatInstance();
        }
        DecoderResult decoderResult = new DecoderResult(null, stringBuilder.toString(), null, str);
        decoderResult.setOther(pDF417ResultMetadata);
        return decoderResult;
    }

    private static String decodeBase900toBase10(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf((long) iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }

    private static int decodeMacroBlock(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i + 2 > iArr[0]) {
            throw FormatException.getFormatInstance();
        }
        int[] iArr2 = new int[2];
        int i2 = 0;
        while (i2 < 2) {
            iArr2[i2] = iArr[i];
            i2++;
            i++;
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(iArr2, 2)));
        StringBuilder stringBuilder = new StringBuilder();
        int textCompaction = textCompaction(iArr, i, stringBuilder);
        pDF417ResultMetadata.setFileId(stringBuilder.toString());
        if (iArr[textCompaction] == BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
            i2 = textCompaction + 1;
            int[] iArr3 = new int[(iArr[0] - i2)];
            int i3 = 0;
            textCompaction = i2;
            i2 = 0;
            while (textCompaction < iArr[0] && r0 == 0) {
                int i4 = textCompaction + 1;
                int i5 = iArr[textCompaction];
                if (i5 < TEXT_COMPACTION_MODE_LATCH) {
                    textCompaction = i3 + 1;
                    iArr3[i3] = i5;
                    i3 = textCompaction;
                    textCompaction = i4;
                } else {
                    switch (i5) {
                        case MACRO_PDF417_TERMINATOR /*922*/:
                            pDF417ResultMetadata.setLastSegment(true);
                            textCompaction = i4 + 1;
                            i2 = true;
                            break;
                        default:
                            throw FormatException.getFormatInstance();
                    }
                }
            }
            pDF417ResultMetadata.setOptionalData(Arrays.copyOf(iArr3, i3));
            return textCompaction;
        } else if (iArr[textCompaction] != MACRO_PDF417_TERMINATOR) {
            return textCompaction;
        } else {
            pDF417ResultMetadata.setLastSegment(true);
            return textCompaction + 1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void decodeTextCompaction(int[] r12, int[] r13, int r14, java.lang.StringBuilder r15) {
        /*
        r10 = 913; // 0x391 float:1.28E-42 double:4.51E-321;
        r9 = 900; // 0x384 float:1.261E-42 double:4.447E-321;
        r8 = 29;
        r7 = 26;
        r1 = 0;
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r4 = r1;
    L_0x000e:
        if (r4 < r14) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r0 = r12[r4];
        r5 = $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode();
        r6 = r3.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x002a;
            case 2: goto L_0x005e;
            case 3: goto L_0x0095;
            case 4: goto L_0x00db;
            case 5: goto L_0x00fc;
            case 6: goto L_0x0112;
            default: goto L_0x0020;
        };
    L_0x0020:
        r0 = r1;
    L_0x0021:
        if (r0 == 0) goto L_0x0026;
    L_0x0023:
        r15.append(r0);
    L_0x0026:
        r0 = r4 + 1;
        r4 = r0;
        goto L_0x000e;
    L_0x002a:
        if (r0 >= r7) goto L_0x0030;
    L_0x002c:
        r0 = r0 + 65;
        r0 = (char) r0;
        goto L_0x0021;
    L_0x0030:
        if (r0 != r7) goto L_0x0035;
    L_0x0032:
        r0 = 32;
        goto L_0x0021;
    L_0x0035:
        r5 = 27;
        if (r0 != r5) goto L_0x003d;
    L_0x0039:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER;
        r0 = r1;
        goto L_0x0021;
    L_0x003d:
        r5 = 28;
        if (r0 != r5) goto L_0x0045;
    L_0x0041:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED;
        r0 = r1;
        goto L_0x0021;
    L_0x0045:
        if (r0 != r8) goto L_0x004e;
    L_0x0047:
        r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
        r0 = r1;
        r11 = r3;
        r3 = r2;
        r2 = r11;
        goto L_0x0021;
    L_0x004e:
        if (r0 != r10) goto L_0x0058;
    L_0x0050:
        r0 = r13[r4];
        r0 = (char) r0;
        r15.append(r0);
        r0 = r1;
        goto L_0x0021;
    L_0x0058:
        if (r0 != r9) goto L_0x0020;
    L_0x005a:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x005e:
        if (r0 >= r7) goto L_0x0064;
    L_0x0060:
        r0 = r0 + 97;
        r0 = (char) r0;
        goto L_0x0021;
    L_0x0064:
        if (r0 != r7) goto L_0x0069;
    L_0x0066:
        r0 = 32;
        goto L_0x0021;
    L_0x0069:
        r5 = 27;
        if (r0 != r5) goto L_0x0074;
    L_0x006d:
        r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA_SHIFT;
        r0 = r1;
        r11 = r3;
        r3 = r2;
        r2 = r11;
        goto L_0x0021;
    L_0x0074:
        r5 = 28;
        if (r0 != r5) goto L_0x007c;
    L_0x0078:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED;
        r0 = r1;
        goto L_0x0021;
    L_0x007c:
        if (r0 != r8) goto L_0x0085;
    L_0x007e:
        r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
        r0 = r1;
        r11 = r3;
        r3 = r2;
        r2 = r11;
        goto L_0x0021;
    L_0x0085:
        if (r0 != r10) goto L_0x008f;
    L_0x0087:
        r0 = r13[r4];
        r0 = (char) r0;
        r15.append(r0);
        r0 = r1;
        goto L_0x0021;
    L_0x008f:
        if (r0 != r9) goto L_0x0020;
    L_0x0091:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x0095:
        r5 = 25;
        if (r0 >= r5) goto L_0x009e;
    L_0x0099:
        r5 = MIXED_CHARS;
        r0 = r5[r0];
        goto L_0x0021;
    L_0x009e:
        r5 = 25;
        if (r0 != r5) goto L_0x00a7;
    L_0x00a2:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT;
        r0 = r1;
        goto L_0x0021;
    L_0x00a7:
        if (r0 != r7) goto L_0x00ad;
    L_0x00a9:
        r0 = 32;
        goto L_0x0021;
    L_0x00ad:
        r5 = 27;
        if (r0 != r5) goto L_0x00b6;
    L_0x00b1:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER;
        r0 = r1;
        goto L_0x0021;
    L_0x00b6:
        r5 = 28;
        if (r0 != r5) goto L_0x00bf;
    L_0x00ba:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x00bf:
        if (r0 != r8) goto L_0x00c9;
    L_0x00c1:
        r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
        r0 = r1;
        r11 = r3;
        r3 = r2;
        r2 = r11;
        goto L_0x0021;
    L_0x00c9:
        if (r0 != r10) goto L_0x00d4;
    L_0x00cb:
        r0 = r13[r4];
        r0 = (char) r0;
        r15.append(r0);
        r0 = r1;
        goto L_0x0021;
    L_0x00d4:
        if (r0 != r9) goto L_0x0020;
    L_0x00d6:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x00db:
        if (r0 >= r8) goto L_0x00e3;
    L_0x00dd:
        r5 = PUNCT_CHARS;
        r0 = r5[r0];
        goto L_0x0021;
    L_0x00e3:
        if (r0 != r8) goto L_0x00ea;
    L_0x00e5:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x00ea:
        if (r0 != r10) goto L_0x00f5;
    L_0x00ec:
        r0 = r13[r4];
        r0 = (char) r0;
        r15.append(r0);
        r0 = r1;
        goto L_0x0021;
    L_0x00f5:
        if (r0 != r9) goto L_0x0020;
    L_0x00f7:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x00fc:
        if (r0 >= r7) goto L_0x0104;
    L_0x00fe:
        r0 = r0 + 65;
        r0 = (char) r0;
        r3 = r2;
        goto L_0x0021;
    L_0x0104:
        if (r0 != r7) goto L_0x010b;
    L_0x0106:
        r0 = 32;
        r3 = r2;
        goto L_0x0021;
    L_0x010b:
        if (r0 != r9) goto L_0x0135;
    L_0x010d:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x0112:
        if (r0 >= r8) goto L_0x011b;
    L_0x0114:
        r3 = PUNCT_CHARS;
        r0 = r3[r0];
        r3 = r2;
        goto L_0x0021;
    L_0x011b:
        if (r0 != r8) goto L_0x0122;
    L_0x011d:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x0122:
        if (r0 != r10) goto L_0x012e;
    L_0x0124:
        r0 = r13[r4];
        r0 = (char) r0;
        r15.append(r0);
        r0 = r1;
        r3 = r2;
        goto L_0x0021;
    L_0x012e:
        if (r0 != r9) goto L_0x0135;
    L_0x0130:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r0 = r1;
        goto L_0x0021;
    L_0x0135:
        r0 = r1;
        r3 = r2;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decodeTextCompaction(int[], int[], int, java.lang.StringBuilder):void");
    }

    private static int numericCompaction(int[] iArr, int i, StringBuilder stringBuilder) throws FormatException {
        int[] iArr2 = new int[15];
        int i2 = 0;
        int i3 = 0;
        while (i < iArr[0] && r0 == 0) {
            int i4 = i + 1;
            int i5 = iArr[i];
            if (i4 == iArr[0]) {
                i2 = 1;
            }
            if (i5 < TEXT_COMPACTION_MODE_LATCH) {
                iArr2[i3] = i5;
                i3++;
                i = i4;
            } else if (i5 == TEXT_COMPACTION_MODE_LATCH || i5 == BYTE_COMPACTION_MODE_LATCH || i5 == BYTE_COMPACTION_MODE_LATCH_6 || i5 == 928 || i5 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD || i5 == MACRO_PDF417_TERMINATOR) {
                i = i4 - 1;
                i2 = 1;
            } else {
                i = i4;
            }
            if ((i3 % 15 == 0 || i5 == NUMERIC_COMPACTION_MODE_LATCH || r0 != 0) && i3 > 0) {
                stringBuilder.append(decodeBase900toBase10(iArr2, i3));
                i3 = 0;
            }
        }
        return i;
    }

    private static int textCompaction(int[] iArr, int i, StringBuilder stringBuilder) {
        int[] iArr2 = new int[((iArr[0] - i) * 2)];
        int[] iArr3 = new int[((iArr[0] - i) * 2)];
        int i2 = 0;
        int i3 = 0;
        while (i < iArr[0] && r0 == 0) {
            int i4 = i + 1;
            int i5 = iArr[i];
            if (i5 >= TEXT_COMPACTION_MODE_LATCH) {
                switch (i5) {
                    case TEXT_COMPACTION_MODE_LATCH /*900*/:
                        i5 = i3 + 1;
                        iArr2[i3] = TEXT_COMPACTION_MODE_LATCH;
                        i3 = i5;
                        i = i4;
                        break;
                    case BYTE_COMPACTION_MODE_LATCH /*901*/:
                    case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                    case MACRO_PDF417_TERMINATOR /*922*/:
                    case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                    case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                    case 928:
                        i = i4 - 1;
                        i2 = 1;
                        break;
                    case MODE_SHIFT_TO_BYTE_COMPACTION_MODE /*913*/:
                        iArr2[i3] = MODE_SHIFT_TO_BYTE_COMPACTION_MODE;
                        i = i4 + 1;
                        iArr3[i3] = iArr[i4];
                        i3++;
                        break;
                    default:
                        i = i4;
                        break;
                }
            }
            iArr2[i3] = i5 / 30;
            iArr2[i3 + 1] = i5 % 30;
            i3 += 2;
            i = i4;
        }
        decodeTextCompaction(iArr2, iArr3, i3, stringBuilder);
        return i;
    }
}
