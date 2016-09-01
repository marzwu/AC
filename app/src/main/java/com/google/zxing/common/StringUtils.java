package com.google.zxing.common;

import android.support.v4.view.MotionEventCompat;
import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        boolean z = (SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) ? true : ASSUME_SHIFT_JIS;
        ASSUME_SHIFT_JIS = z;
    }

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        if (map != null) {
            String str = (String) map.get(DecodeHintType.CHARACTER_SET);
            if (str != null) {
                return str;
            }
        }
        int length = bArr.length;
        Object obj = 1;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        Object obj2 = (bArr.length > 3 && bArr[0] == (byte) -17 && bArr[1] == (byte) -69 && bArr[2] == (byte) -65) ? 1 : null;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        Object obj3 = 1;
        Object obj4 = 1;
        int i12 = 0;
        while (i12 < length && (obj4 != null || obj3 != null || obj != null)) {
            int i13;
            int i14 = bArr[i12] & MotionEventCompat.ACTION_MASK;
            if (obj != null) {
                if (i > 0) {
                    if ((i14 & 128) == 0) {
                        obj = null;
                    } else {
                        i--;
                    }
                } else if ((i14 & 128) != 0) {
                    if ((i14 & 64) == 0) {
                        obj = null;
                    } else {
                        i++;
                        if ((i14 & 32) == 0) {
                            i2++;
                        } else {
                            i++;
                            if ((i14 & 16) == 0) {
                                i3++;
                            } else {
                                i++;
                                if ((i14 & 8) == 0) {
                                    i4++;
                                } else {
                                    obj = null;
                                }
                            }
                        }
                    }
                }
            }
            if (obj4 != null) {
                if (i14 > 127 && i14 < 160) {
                    obj4 = null;
                } else if (i14 > 159 && (i14 < 192 || i14 == 215 || i14 == 247)) {
                    i6++;
                }
            }
            if (obj3 != null) {
                if (i11 > 0) {
                    if (i14 < 64 || i14 == 127 || i14 > 252) {
                        obj3 = null;
                        i13 = i5;
                        i5 = i8;
                        i8 = i13;
                    } else {
                        i11--;
                        i13 = i5;
                        i5 = i8;
                        i8 = i13;
                    }
                } else if (i14 == 128 || i14 == 160 || i14 > 239) {
                    obj3 = null;
                    i13 = i5;
                    i5 = i8;
                    i8 = i13;
                } else if (i14 > 160 && i14 < 224) {
                    i10++;
                    i8 = i9 + 1;
                    if (i8 > i5) {
                        i5 = 0;
                        i9 = i8;
                    } else {
                        i13 = i5;
                        i5 = 0;
                        i9 = i8;
                        i8 = i13;
                    }
                } else if (i14 > 127) {
                    i11++;
                    i8++;
                    i9 = 0;
                    if (i8 > i7) {
                        i9 = 0;
                        i7 = i8;
                        i13 = i8;
                        i8 = i5;
                        i5 = i13;
                    }
                } else {
                    i9 = 0;
                    i13 = i5;
                    i5 = 0;
                    i8 = i13;
                }
                i12++;
                i13 = i8;
                i8 = i5;
                i5 = i13;
            }
            i13 = i5;
            i5 = i8;
            i8 = i13;
            i12++;
            i13 = i8;
            i8 = i5;
            i5 = i13;
        }
        if (obj != null && i > 0) {
            obj = null;
        }
        if (obj3 != null && i11 > 0) {
            obj3 = null;
        }
        return (obj == null || (obj2 == null && (i2 + i3) + i4 <= 0)) ? (obj3 == null || (!ASSUME_SHIFT_JIS && i5 < 3 && i7 < 3)) ? (obj4 == null || obj3 == null) ? obj4 != null ? ISO88591 : obj3 != null ? SHIFT_JIS : obj != null ? UTF8 : PLATFORM_DEFAULT_ENCODING : (!(i5 == 2 && i10 == 2) && i6 * 10 < length) ? ISO88591 : SHIFT_JIS : SHIFT_JIS : UTF8;
    }
}
