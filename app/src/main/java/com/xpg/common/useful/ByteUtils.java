package com.xpg.common.useful;

import android.support.v4.view.MotionEventCompat;

public class ByteUtils {
    public static String Bytes2HexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & MotionEventCompat.ACTION_MASK);
            if (toHexString.length() == 1) {
                toHexString = "0" + toHexString;
            }
            str = new StringBuilder(String.valueOf(str)).append(toHexString.toUpperCase()).append(" ").toString();
        }
        return str;
    }

    public static byte[] HexString2Bytes(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        byte[] bytes = str.getBytes();
        for (int i = 0; i < length; i++) {
            bArr[i] = uniteBytes(bytes[i * 2], bytes[(i * 2) + 1]);
        }
        return bArr;
    }

    public static boolean getBitFromShort(int i, int i2) {
        return ((i >> i2) & 1) > 0;
    }

    public static String int2HaxString(int i) {
        String toHexString = Integer.toHexString(i);
        return toHexString.length() == 1 ? "0" + toHexString : toHexString;
    }

    public static void printHexString(String str, byte[] bArr) {
        System.out.print(str);
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & MotionEventCompat.ACTION_MASK);
            if (toHexString.length() == 1) {
                toHexString = "0" + toHexString;
            }
            System.out.print(toHexString.toUpperCase() + " ");
        }
        System.out.println("");
    }

    public static byte uniteBytes(byte b, byte b2) {
        return (byte) (((byte) (Byte.decode("0x" + new String(new byte[]{b})).byteValue() << 4)) ^ Byte.decode("0x" + new String(new byte[]{b2})).byteValue());
    }
}
