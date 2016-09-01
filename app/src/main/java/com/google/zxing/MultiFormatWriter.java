package com.google.zxing;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter implements Writer {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$BarcodeFormat;

    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$BarcodeFormat() {
        int[] iArr = $SWITCH_TABLE$com$google$zxing$BarcodeFormat;
        if (iArr == null) {
            iArr = new int[BarcodeFormat.values().length];
            try {
                iArr[BarcodeFormat.AZTEC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[BarcodeFormat.CODABAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[BarcodeFormat.CODE_128.ordinal()] = 5;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[BarcodeFormat.CODE_39.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[BarcodeFormat.CODE_93.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[BarcodeFormat.DATA_MATRIX.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[BarcodeFormat.EAN_13.ordinal()] = 8;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[BarcodeFormat.EAN_8.ordinal()] = 7;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[BarcodeFormat.ITF.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[BarcodeFormat.MAXICODE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[BarcodeFormat.PDF_417.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[BarcodeFormat.QR_CODE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[BarcodeFormat.RSS_14.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[BarcodeFormat.RSS_EXPANDED.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[BarcodeFormat.UPC_A.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[BarcodeFormat.UPC_E.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[BarcodeFormat.UPC_EAN_EXTENSION.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            $SWITCH_TABLE$com$google$zxing$BarcodeFormat = iArr;
        }
        return iArr;
    }

    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) throws WriterException {
        return encode(str, barcodeFormat, i, i2, null);
    }

    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        Writer aztecWriter;
        switch ($SWITCH_TABLE$com$google$zxing$BarcodeFormat()[barcodeFormat.ordinal()]) {
            case 1:
                aztecWriter = new AztecWriter();
                break;
            case 2:
                aztecWriter = new CodaBarWriter();
                break;
            case 3:
                aztecWriter = new Code39Writer();
                break;
            case 5:
                aztecWriter = new Code128Writer();
                break;
            case 6:
                aztecWriter = new DataMatrixWriter();
                break;
            case MotionEventCompat.ACTION_HOVER_MOVE /*7*/:
                aztecWriter = new EAN8Writer();
                break;
            case 8:
                aztecWriter = new EAN13Writer();
                break;
            case 9:
                aztecWriter = new ITFWriter();
                break;
            case 11:
                aztecWriter = new PDF417Writer();
                break;
            case 12:
                aztecWriter = new QRCodeWriter();
                break;
            case ViewDragHelper.EDGE_ALL /*15*/:
                aztecWriter = new UPCAWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + barcodeFormat);
        }
        return aztecWriter.encode(str, barcodeFormat, i, i2, map);
    }
}
