package com.xtremeprog.xpgconnect;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;

import java.io.UnsupportedEncodingException;

public class XPGWifiBinary {
    private static byte[] base64DecodeChars;
    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    static {
        byte[] bArr = new byte[128];
        bArr[0] = (byte) -1;
        bArr[1] = (byte) -1;
        bArr[2] = (byte) -1;
        bArr[3] = (byte) -1;
        bArr[4] = (byte) -1;
        bArr[5] = (byte) -1;
        bArr[6] = (byte) -1;
        bArr[7] = (byte) -1;
        bArr[8] = (byte) -1;
        bArr[9] = (byte) -1;
        bArr[10] = (byte) -1;
        bArr[11] = (byte) -1;
        bArr[12] = (byte) -1;
        bArr[13] = (byte) -1;
        bArr[14] = (byte) -1;
        bArr[15] = (byte) -1;
        bArr[16] = (byte) -1;
        bArr[17] = (byte) -1;
        bArr[18] = (byte) -1;
        bArr[19] = (byte) -1;
        bArr[20] = (byte) -1;
        bArr[21] = (byte) -1;
        bArr[22] = (byte) -1;
        bArr[23] = (byte) -1;
        bArr[24] = (byte) -1;
        bArr[25] = (byte) -1;
        bArr[26] = (byte) -1;
        bArr[27] = (byte) -1;
        bArr[28] = (byte) -1;
        bArr[29] = (byte) -1;
        bArr[30] = (byte) -1;
        bArr[31] = (byte) -1;
        bArr[32] = (byte) -1;
        bArr[33] = (byte) -1;
        bArr[34] = (byte) -1;
        bArr[35] = (byte) -1;
        bArr[36] = (byte) -1;
        bArr[37] = (byte) -1;
        bArr[38] = (byte) -1;
        bArr[39] = (byte) -1;
        bArr[40] = (byte) -1;
        bArr[41] = (byte) -1;
        bArr[42] = (byte) -1;
        bArr[43] = (byte) 62;
        bArr[44] = (byte) -1;
        bArr[45] = (byte) -1;
        bArr[46] = (byte) -1;
        bArr[47] = (byte) 63;
        bArr[48] = (byte) 52;
        bArr[49] = (byte) 53;
        bArr[50] = (byte) 54;
        bArr[51] = (byte) 55;
        bArr[52] = (byte) 56;
        bArr[53] = (byte) 57;
        bArr[54] = (byte) 58;
        bArr[55] = (byte) 59;
        bArr[56] = (byte) 60;
        bArr[57] = (byte) 61;
        bArr[58] = (byte) -1;
        bArr[59] = (byte) -1;
        bArr[60] = (byte) -1;
        bArr[61] = (byte) -1;
        bArr[62] = (byte) -1;
        bArr[63] = (byte) -1;
        bArr[64] = (byte) -1;
        bArr[66] = (byte) 1;
        bArr[67] = (byte) 2;
        bArr[68] = (byte) 3;
        bArr[69] = (byte) 4;
        bArr[70] = (byte) 5;
        bArr[71] = (byte) 6;
        bArr[72] = (byte) 7;
        bArr[73] = (byte) 8;
        bArr[74] = (byte) 9;
        bArr[75] = (byte) 10;
        bArr[76] = (byte) 11;
        bArr[77] = (byte) 12;
        bArr[78] = (byte) 13;
        bArr[79] = (byte) 14;
        bArr[80] = (byte) 15;
        bArr[81] = (byte) 16;
        bArr[82] = (byte) 17;
        bArr[83] = (byte) 18;
        bArr[84] = (byte) 19;
        bArr[85] = (byte) 20;
        bArr[86] = (byte) 21;
        bArr[87] = (byte) 22;
        bArr[88] = (byte) 23;
        bArr[89] = (byte) 24;
        bArr[90] = (byte) 25;
        bArr[91] = (byte) -1;
        bArr[92] = (byte) -1;
        bArr[93] = (byte) -1;
        bArr[94] = (byte) -1;
        bArr[95] = (byte) -1;
        bArr[96] = (byte) -1;
        bArr[97] = (byte) 26;
        bArr[98] = (byte) 27;
        bArr[99] = (byte) 28;
        bArr[100] = (byte) 29;
        bArr[101] = (byte) 30;
        bArr[102] = (byte) 31;
        bArr[103] = (byte) 32;
        bArr[104] = (byte) 33;
        bArr[105] = (byte) 34;
        bArr[106] = (byte) 35;
        bArr[107] = (byte) 36;
        bArr[108] = (byte) 37;
        bArr[109] = (byte) 38;
        bArr[110] = (byte) 39;
        bArr[111] = (byte) 40;
        bArr[112] = (byte) 41;
        bArr[113] = (byte) 42;
        bArr[114] = (byte) 43;
        bArr[115] = (byte) 44;
        bArr[116] = (byte) 45;
        bArr[117] = (byte) 46;
        bArr[118] = (byte) 47;
        bArr[119] = (byte) 48;
        bArr[120] = (byte) 49;
        bArr[121] = (byte) 50;
        bArr[122] = (byte) 51;
        bArr[123] = (byte) -1;
        bArr[124] = (byte) -1;
        bArr[125] = (byte) -1;
        bArr[TransportMediator.KEYCODE_MEDIA_PLAY] = (byte) -1;
        bArr[TransportMediator.KEYCODE_MEDIA_PAUSE] = (byte) -1;
        base64DecodeChars = bArr;
    }

    private XPGWifiBinary() {
    }

    public static byte[] decode(String paramString)
            throws UnsupportedEncodingException {
        StringBuffer localStringBuffer = new StringBuffer();
        byte[] arrayOfByte1 = paramString.getBytes("US-ASCII");
        int i = arrayOfByte1.length;
        int k = 0;
        for (int j = 0; ; j = k) {
            if (j >= i) {
            }
            int i4;
            int i7;
            do {
                int i1;
                do {
                    int m;
                    do {
                        do {
//                            return localStringBuffer.toString().getBytes("iso8859-1");
                            do {
                                j = k;
                                byte[] arrayOfByte2 = base64DecodeChars;
                                k = j + 1;
                                m = arrayOfByte2[arrayOfByte1[j]];
                            } while ((k < i) && (m == -1));
                        } while (m == -1);
                        do {
                            int n = k;
                            byte[] arrayOfByte3 = base64DecodeChars;
                            k = n + 1;
                            i1 = arrayOfByte3[arrayOfByte1[n]];
                        } while ((k < i) && (i1 == -1));
                    } while (i1 == -1);
                    localStringBuffer.append((char) (m << 2 | (i1 & 0x30) >>> 4));
                    do {
                        int i2 = k;
                        k = i2 + 1;
                        int i3 = arrayOfByte1[i2];
                        if (i3 == 61) {
                            byte[] arrayOfByte5 = localStringBuffer.toString().getBytes("iso8859-1");
                            return arrayOfByte5;
                        }
                        i4 = base64DecodeChars[i3];
                    } while ((k < i) && (i4 == -1));
                } while (i4 == -1);
                localStringBuffer.append((char) ((i1 & 0xF) << 4 | (i4 & 0x3C) >>> 2));
                do {
                    int i5 = k;
                    k = i5 + 1;
                    int i6 = arrayOfByte1[i5];
                    if (i6 == 61) {
                        byte[] arrayOfByte4 = localStringBuffer.toString().getBytes("iso8859-1");
                        return arrayOfByte4;
                    }
                    i7 = base64DecodeChars[i6];
                } while ((k < i) && (i7 == -1));
            } while (i7 == -1);
            localStringBuffer.append((char) (i7 | (i4 & 0x3) << 6));
        }
    }

    public static String encode(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            int i3 = bArr[i] & MotionEventCompat.ACTION_MASK;
            if (i2 == length) {
                stringBuffer.append(base64EncodeChars[i3 >>> 2]);
                stringBuffer.append(base64EncodeChars[(i3 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i4 = i2 + 1;
            i2 = bArr[i2] & MotionEventCompat.ACTION_MASK;
            if (i4 == length) {
                stringBuffer.append(base64EncodeChars[i3 >>> 2]);
                stringBuffer.append(base64EncodeChars[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
                stringBuffer.append(base64EncodeChars[(i2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            i = i4 + 1;
            i4 = bArr[i4] & MotionEventCompat.ACTION_MASK;
            stringBuffer.append(base64EncodeChars[i3 >>> 2]);
            stringBuffer.append(base64EncodeChars[((i3 & 3) << 4) | ((i2 & 240) >>> 4)]);
            stringBuffer.append(base64EncodeChars[((i2 & 15) << 2) | ((i4 & 192) >>> 6)]);
            stringBuffer.append(base64EncodeChars[i4 & 63]);
        }
        return stringBuffer.toString();
    }
}
