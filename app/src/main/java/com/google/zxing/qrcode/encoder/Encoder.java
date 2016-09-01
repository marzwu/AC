package com.google.zxing.qrcode.encoder;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class Encoder {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$qrcode$decoder$Mode = null;
    private static final int[] ALPHANUMERIC_TABLE;
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$qrcode$decoder$Mode() {
        int[] iArr = $SWITCH_TABLE$com$google$zxing$qrcode$decoder$Mode;
        if (iArr == null) {
            iArr = new int[Mode.values().length];
            try {
                iArr[Mode.ALPHANUMERIC.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Mode.BYTE.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Mode.ECI.ordinal()] = 6;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Mode.FNC1_FIRST_POSITION.ordinal()] = 8;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Mode.FNC1_SECOND_POSITION.ordinal()] = 9;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[Mode.HANZI.ordinal()] = 10;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[Mode.KANJI.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[Mode.NUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[Mode.STRUCTURED_APPEND.ordinal()] = 4;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[Mode.TERMINATOR.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            $SWITCH_TABLE$com$google$zxing$qrcode$decoder$Mode = iArr;
        }
        return iArr;
    }

    static {
        int[] iArr = new int[96];
        iArr[0] = -1;
        iArr[1] = -1;
        iArr[2] = -1;
        iArr[3] = -1;
        iArr[4] = -1;
        iArr[5] = -1;
        iArr[6] = -1;
        iArr[7] = -1;
        iArr[8] = -1;
        iArr[9] = -1;
        iArr[10] = -1;
        iArr[11] = -1;
        iArr[12] = -1;
        iArr[13] = -1;
        iArr[14] = -1;
        iArr[15] = -1;
        iArr[16] = -1;
        iArr[17] = -1;
        iArr[18] = -1;
        iArr[19] = -1;
        iArr[20] = -1;
        iArr[21] = -1;
        iArr[22] = -1;
        iArr[23] = -1;
        iArr[24] = -1;
        iArr[25] = -1;
        iArr[26] = -1;
        iArr[27] = -1;
        iArr[28] = -1;
        iArr[29] = -1;
        iArr[30] = -1;
        iArr[31] = -1;
        iArr[32] = 36;
        iArr[33] = -1;
        iArr[34] = -1;
        iArr[35] = -1;
        iArr[36] = 37;
        iArr[37] = 38;
        iArr[38] = -1;
        iArr[39] = -1;
        iArr[40] = -1;
        iArr[41] = -1;
        iArr[42] = 39;
        iArr[43] = 40;
        iArr[44] = -1;
        iArr[45] = 41;
        iArr[46] = 42;
        iArr[47] = 43;
        iArr[49] = 1;
        iArr[50] = 2;
        iArr[51] = 3;
        iArr[52] = 4;
        iArr[53] = 5;
        iArr[54] = 6;
        iArr[55] = 7;
        iArr[56] = 8;
        iArr[57] = 9;
        iArr[58] = 44;
        iArr[59] = -1;
        iArr[60] = -1;
        iArr[61] = -1;
        iArr[62] = -1;
        iArr[63] = -1;
        iArr[64] = -1;
        iArr[65] = 10;
        iArr[66] = 11;
        iArr[67] = 12;
        iArr[68] = 13;
        iArr[69] = 14;
        iArr[70] = 15;
        iArr[71] = 16;
        iArr[72] = 17;
        iArr[73] = 18;
        iArr[74] = 19;
        iArr[75] = 20;
        iArr[76] = 21;
        iArr[77] = 22;
        iArr[78] = 23;
        iArr[79] = 24;
        iArr[80] = 25;
        iArr[81] = 26;
        iArr[82] = 27;
        iArr[83] = 28;
        iArr[84] = 29;
        iArr[85] = 30;
        iArr[86] = 31;
        iArr[87] = 32;
        iArr[88] = 33;
        iArr[89] = 34;
        iArr[90] = 35;
        iArr[91] = -1;
        iArr[92] = -1;
        iArr[93] = -1;
        iArr[94] = -1;
        iArr[95] = -1;
        ALPHANUMERIC_TABLE = iArr;
    }

    private Encoder() {
    }

    static void append8BitBytes(String str, BitArray bitArray, String str2) throws WriterException {
        try {
            for (byte appendBits : str.getBytes(str2)) {
                bitArray.appendBits(appendBits, 8);
            }
        } catch (Throwable e) {
            throw new WriterException(e);
        }
    }

    static void appendAlphanumericBytes(CharSequence charSequence, BitArray bitArray) throws WriterException {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int alphanumericCode = getAlphanumericCode(charSequence.charAt(i));
            if (alphanumericCode == -1) {
                throw new WriterException();
            } else if (i + 1 < length) {
                int alphanumericCode2 = getAlphanumericCode(charSequence.charAt(i + 1));
                if (alphanumericCode2 == -1) {
                    throw new WriterException();
                }
                bitArray.appendBits((alphanumericCode * 45) + alphanumericCode2, 11);
                i += 2;
            } else {
                bitArray.appendBits(alphanumericCode, 6);
                i++;
            }
        }
    }

    static void appendBytes(String str, Mode mode, BitArray bitArray, String str2) throws WriterException {
        switch ($SWITCH_TABLE$com$google$zxing$qrcode$decoder$Mode()[mode.ordinal()]) {
            case 2:
                appendNumericBytes(str, bitArray);
                return;
            case 3:
                appendAlphanumericBytes(str, bitArray);
                return;
            case 5:
                append8BitBytes(str, bitArray, str2);
                return;
            case MotionEventCompat.ACTION_HOVER_MOVE /*7*/:
                appendKanjiBytes(str, bitArray);
                return;
            default:
                throw new WriterException("Invalid mode: " + mode);
        }
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitArray bitArray) {
        bitArray.appendBits(Mode.ECI.getBits(), 4);
        bitArray.appendBits(characterSetECI.getValue(), 8);
    }

    static void appendKanjiBytes(String str, BitArray bitArray) throws WriterException {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            for (int i = 0; i < length; i += 2) {
                int i2 = ((bytes[i] & MotionEventCompat.ACTION_MASK) << 8) | (bytes[i + 1] & MotionEventCompat.ACTION_MASK);
                i2 = (i2 < 33088 || i2 > 40956) ? (i2 < 57408 || i2 > 60351) ? -1 : i2 - 49472 : i2 - 33088;
                if (i2 == -1) {
                    throw new WriterException("Invalid byte sequence");
                }
                bitArray.appendBits((i2 & MotionEventCompat.ACTION_MASK) + ((i2 >> 8) * 192), 13);
            }
        } catch (Throwable e) {
            throw new WriterException(e);
        }
    }

    static void appendLengthInfo(int i, Version version, Mode mode, BitArray bitArray) throws WriterException {
        int characterCountBits = mode.getCharacterCountBits(version);
        if (i >= (1 << characterCountBits)) {
            throw new WriterException(new StringBuilder(String.valueOf(i)).append(" is bigger than ").append((1 << characterCountBits) - 1).toString());
        }
        bitArray.appendBits(i, characterCountBits);
    }

    static void appendModeInfo(Mode mode, BitArray bitArray) {
        bitArray.appendBits(mode.getBits(), 4);
    }

    static void appendNumericBytes(CharSequence charSequence, BitArray bitArray) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int charAt = charSequence.charAt(i) - 48;
            if (i + 2 < length) {
                bitArray.appendBits(((charAt * 100) + ((charSequence.charAt(i + 1) - 48) * 10)) + (charSequence.charAt(i + 2) - 48), 10);
                i += 3;
            } else if (i + 1 < length) {
                bitArray.appendBits((charAt * 10) + (charSequence.charAt(i + 1) - 48), 7);
                i += 2;
            } else {
                bitArray.appendBits(charAt, 4);
                i++;
            }
        }
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return ((MaskUtil.applyMaskPenaltyRule1(byteMatrix) + MaskUtil.applyMaskPenaltyRule2(byteMatrix)) + MaskUtil.applyMaskPenaltyRule3(byteMatrix)) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    private static int chooseMaskPattern(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, ByteMatrix byteMatrix) throws WriterException {
        int i = Integer.MAX_VALUE;
        int i2 = -1;
        int i3 = 0;
        while (i3 < 8) {
            MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, version, i3, byteMatrix);
            int calculateMaskPenalty = calculateMaskPenalty(byteMatrix);
            if (calculateMaskPenalty < i) {
                i2 = i3;
            } else {
                calculateMaskPenalty = i;
            }
            i3++;
            i = calculateMaskPenalty;
        }
        return i2;
    }

    public static Mode chooseMode(String str) {
        return chooseMode(str, null);
    }

    private static Mode chooseMode(String str, String str2) {
        int i = 0;
        if ("Shift_JIS".equals(str2)) {
            return isOnlyDoubleByteKanji(str) ? Mode.KANJI : Mode.BYTE;
        } else {
            int i2 = 0;
            int i3 = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (charAt >= '0' && charAt <= '9') {
                    i3 = 1;
                } else if (getAlphanumericCode(charAt) == -1) {
                    return Mode.BYTE;
                } else {
                    i2 = 1;
                }
                i++;
            }
            return i2 != 0 ? Mode.ALPHANUMERIC : i3 != 0 ? Mode.NUMERIC : Mode.BYTE;
        }
    }

    private static Version chooseVersion(int i, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i2 = 1; i2 <= 40; i2++) {
            Version versionForNumber = Version.getVersionForNumber(i2);
            if (versionForNumber.getTotalCodewords() - versionForNumber.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() >= (i + 7) / 8) {
                return versionForNumber;
            }
        }
        throw new WriterException("Data too big");
    }

    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return encode(str, errorCorrectionLevel, null);
    }

    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> map) throws WriterException {
        String str2 = map == null ? null : (String) map.get(EncodeHintType.CHARACTER_SET);
        if (str2 == null) {
            str2 = DEFAULT_BYTE_MODE_ENCODING;
        }
        Mode chooseMode = chooseMode(str, str2);
        BitArray bitArray = new BitArray();
        if (chooseMode == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(str2)) {
            CharacterSetECI characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(str2);
            if (characterSetECIByName != null) {
                appendECI(characterSetECIByName, bitArray);
            }
        }
        appendModeInfo(chooseMode, bitArray);
        BitArray bitArray2 = new BitArray();
        appendBytes(str, chooseMode, bitArray2, str2);
        Version chooseVersion = chooseVersion((chooseMode.getCharacterCountBits(chooseVersion((bitArray.getSize() + chooseMode.getCharacterCountBits(Version.getVersionForNumber(1))) + bitArray2.getSize(), errorCorrectionLevel)) + bitArray.getSize()) + bitArray2.getSize(), errorCorrectionLevel);
        BitArray bitArray3 = new BitArray();
        bitArray3.appendBitArray(bitArray);
        appendLengthInfo(chooseMode == Mode.BYTE ? bitArray2.getSizeInBytes() : str.length(), chooseVersion, chooseMode, bitArray3);
        bitArray3.appendBitArray(bitArray2);
        ECBlocks eCBlocksForLevel = chooseVersion.getECBlocksForLevel(errorCorrectionLevel);
        int totalCodewords = chooseVersion.getTotalCodewords() - eCBlocksForLevel.getTotalECCodewords();
        terminateBits(totalCodewords, bitArray3);
        BitArray interleaveWithECBytes = interleaveWithECBytes(bitArray3, chooseVersion.getTotalCodewords(), totalCodewords, eCBlocksForLevel.getNumBlocks());
        QRCode qRCode = new QRCode();
        qRCode.setECLevel(errorCorrectionLevel);
        qRCode.setMode(chooseMode);
        qRCode.setVersion(chooseVersion);
        int dimensionForVersion = chooseVersion.getDimensionForVersion();
        ByteMatrix byteMatrix = new ByteMatrix(dimensionForVersion, dimensionForVersion);
        dimensionForVersion = chooseMaskPattern(interleaveWithECBytes, errorCorrectionLevel, chooseVersion, byteMatrix);
        qRCode.setMaskPattern(dimensionForVersion);
        MatrixUtil.buildMatrix(interleaveWithECBytes, errorCorrectionLevel, chooseVersion, dimensionForVersion, byteMatrix);
        qRCode.setMatrix(byteMatrix);
        return qRCode;
    }

    static byte[] generateECBytes(byte[] bArr, int i) {
        int i2 = 0;
        int length = bArr.length;
        int[] iArr = new int[(length + i)];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & MotionEventCompat.ACTION_MASK;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(iArr, i);
        byte[] bArr2 = new byte[i];
        while (i2 < i) {
            bArr2[i2] = (byte) iArr[length + i2];
            i2++;
        }
        return bArr2;
    }

    static int getAlphanumericCode(int i) {
        return i < ALPHANUMERIC_TABLE.length ? ALPHANUMERIC_TABLE[i] : -1;
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2) throws WriterException {
        if (i4 >= i3) {
            throw new WriterException("Block ID too large");
        }
        int i5 = i % i3;
        int i6 = i3 - i5;
        int i7 = i / i3;
        int i8 = i7 + 1;
        int i9 = i2 / i3;
        int i10 = i9 + 1;
        i7 -= i9;
        i8 -= i10;
        if (i7 != i8) {
            throw new WriterException("EC bytes mismatch");
        } else if (i3 != i6 + i5) {
            throw new WriterException("RS blocks mismatch");
        } else {
            if (i != (i5 * (i10 + i8)) + ((i9 + i7) * i6)) {
                throw new WriterException("Total bytes mismatch");
            } else if (i4 < i6) {
                iArr[0] = i9;
                iArr2[0] = i7;
            } else {
                iArr[0] = i10;
                iArr2[0] = i8;
            }
        }
    }

    static BitArray interleaveWithECBytes(BitArray bitArray, int i, int i2, int i3) throws WriterException {
        if (bitArray.getSizeInBytes() != i2) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        Collection<BlockPair> arrayList = new ArrayList(i3);
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i4 < i3) {
            int[] iArr = new int[1];
            int[] iArr2 = new int[1];
            getNumDataBytesAndNumECBytesForBlockID(i, i2, i3, i4, iArr, iArr2);
            int i8 = iArr[0];
            byte[] bArr = new byte[i8];
            bitArray.toBytes(i7 * 8, bArr, 0, i8);
            byte[] generateECBytes = generateECBytes(bArr, iArr2[0]);
            arrayList.add(new BlockPair(bArr, generateECBytes));
            int max = Math.max(i6, i8);
            i4++;
            i5 = Math.max(i5, generateECBytes.length);
            i6 = max;
            i7 = iArr[0] + i7;
        }
        if (i2 != i7) {
            throw new WriterException("Data bytes does not match offset");
        }
        BitArray bitArray2 = new BitArray();
        for (max = 0; max < i6; max++) {
            for (BlockPair dataBytes : arrayList) {
                byte[] dataBytes2 = dataBytes.getDataBytes();
                if (max < dataBytes2.length) {
                    bitArray2.appendBits(dataBytes2[max], 8);
                }
            }
        }
        for (max = 0; max < i5; max++) {
            for (BlockPair dataBytes3 : arrayList) {
                dataBytes2 = dataBytes3.getErrorCorrectionBytes();
                if (max < dataBytes2.length) {
                    bitArray2.appendBits(dataBytes2[max], 8);
                }
            }
        }
        if (i == bitArray2.getSizeInBytes()) {
            return bitArray2;
        }
        throw new WriterException("Interleaving error: " + i + " and " + bitArray2.getSizeInBytes() + " differ.");
    }

    private static boolean isOnlyDoubleByteKanji(String str) {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < length; i += 2) {
                int i2 = bytes[i] & MotionEventCompat.ACTION_MASK;
                if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    static void terminateBits(int i, BitArray bitArray) throws WriterException {
        int i2 = i * 8;
        if (bitArray.getSize() > i2) {
            throw new WriterException("data bits cannot fit in the QR Code" + bitArray.getSize() + " > " + i2);
        }
        int i3;
        for (i3 = 0; i3 < 4 && bitArray.getSize() < i2; i3++) {
            bitArray.appendBit(false);
        }
        i3 = bitArray.getSize() & 7;
        if (i3 > 0) {
            while (i3 < 8) {
                bitArray.appendBit(false);
                i3++;
            }
        }
        int sizeInBytes = i - bitArray.getSizeInBytes();
        for (i3 = 0; i3 < sizeInBytes; i3++) {
            bitArray.appendBits((i3 & 1) == 0 ? 236 : 17, 8);
        }
        if (bitArray.getSize() != i2) {
            throw new WriterException("Bits size does not equal capacity");
        }
    }
}
