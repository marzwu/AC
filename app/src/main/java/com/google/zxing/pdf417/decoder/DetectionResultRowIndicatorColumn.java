package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ResultPoint;

final class DetectionResultRowIndicatorColumn extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean z) {
        super(boundingBox);
        this.isLeft = z;
    }

    private void removeIncorrectCodewords(Codeword[] codewordArr, BarcodeMetadata barcodeMetadata) {
        for (int i = 0; i < codewordArr.length; i++) {
            Codeword codeword = codewordArr[i];
            if (codewordArr[i] != null) {
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (rowNumber <= barcodeMetadata.getRowCount()) {
                    if (!this.isLeft) {
                        rowNumber += 2;
                    }
                    switch (rowNumber % 3) {
                        case 0:
                            if ((value * 3) + 1 == barcodeMetadata.getRowCountUpperPart()) {
                                break;
                            }
                            codewordArr[i] = null;
                            break;
                        case 1:
                            if (value / 3 != barcodeMetadata.getErrorCorrectionLevel() || value % 3 != barcodeMetadata.getRowCountLowerPart()) {
                                codewordArr[i] = null;
                                break;
                            }
                            break;
                            break;
                        case 2:
                            if (value + 1 == barcodeMetadata.getColumnCount()) {
                                break;
                            }
                            codewordArr[i] = null;
                            break;
                        default:
                            break;
                    }
                }
                codewordArr[i] = null;
            }
        }
    }

    int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        Codeword[] codewords = getCodewords();
        setRowNumbers();
        removeIncorrectCodewords(codewords, barcodeMetadata);
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int imageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int imageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = ((float) (imageRowToCodewordIndex2 - imageRowToCodewordIndex)) / ((float) barcodeMetadata.getRowCount());
        int i = -1;
        int i2 = imageRowToCodewordIndex;
        int i3 = 1;
        imageRowToCodewordIndex = 0;
        while (i2 < imageRowToCodewordIndex2) {
            if (codewords[i2] != null) {
                Codeword codeword = codewords[i2];
                int rowNumber = codeword.getRowNumber() - i;
                if (rowNumber == 0) {
                    imageRowToCodewordIndex++;
                } else if (rowNumber == 1) {
                    imageRowToCodewordIndex = Math.max(i3, imageRowToCodewordIndex);
                    i = codeword.getRowNumber();
                    i3 = imageRowToCodewordIndex;
                    imageRowToCodewordIndex = 1;
                } else if (rowNumber < 0 || codeword.getRowNumber() >= barcodeMetadata.getRowCount() || rowNumber > i2) {
                    codewords[i2] = null;
                } else {
                    int i4 = i3 > 2 ? rowNumber * (i3 - 2) : rowNumber;
                    Object obj = i4 >= i2 ? 1 : null;
                    for (int i5 = 1; i5 <= i4 && obj == null; i5++) {
                        obj = codewords[i2 - i5] != null ? 1 : null;
                    }
                    if (obj != null) {
                        codewords[i2] = null;
                    } else {
                        i = codeword.getRowNumber();
                        imageRowToCodewordIndex = 1;
                    }
                }
            }
            i2++;
        }
        return (int) (0.5d + ((double) rowCount));
    }

    int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int imageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int imageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = ((float) (imageRowToCodewordIndex2 - imageRowToCodewordIndex)) / ((float) barcodeMetadata.getRowCount());
        Codeword[] codewords = getCodewords();
        int i = -1;
        imageRowToCodewordIndex = 0;
        int i2 = 1;
        for (int i3 = imageRowToCodewordIndex; i3 < imageRowToCodewordIndex2; i3++) {
            if (codewords[i3] != null) {
                Codeword codeword = codewords[i3];
                codeword.setRowNumberAsRowIndicatorColumn();
                int rowNumber = codeword.getRowNumber() - i;
                if (rowNumber == 0) {
                    imageRowToCodewordIndex++;
                } else if (rowNumber == 1) {
                    imageRowToCodewordIndex = Math.max(i2, imageRowToCodewordIndex);
                    i = codeword.getRowNumber();
                    i2 = imageRowToCodewordIndex;
                    imageRowToCodewordIndex = 1;
                } else if (codeword.getRowNumber() >= barcodeMetadata.getRowCount()) {
                    codewords[i3] = null;
                } else {
                    i = codeword.getRowNumber();
                    imageRowToCodewordIndex = 1;
                }
            }
        }
        return (int) (0.5d + ((double) rowCount));
    }

    BarcodeMetadata getBarcodeMetadata() {
        Codeword[] codewords = getCodewords();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        BarcodeValue barcodeValue4 = new BarcodeValue();
        for (Codeword codeword : codewords) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (!this.isLeft) {
                    rowNumber += 2;
                }
                switch (rowNumber % 3) {
                    case 0:
                        barcodeValue2.setValue((value * 3) + 1);
                        break;
                    case 1:
                        barcodeValue4.setValue(value / 3);
                        barcodeValue3.setValue(value % 3);
                        break;
                    case 2:
                        barcodeValue.setValue(value + 1);
                        break;
                    default:
                        break;
                }
            }
        }
        if (barcodeValue.getValue().length == 0 || barcodeValue2.getValue().length == 0 || barcodeValue3.getValue().length == 0 || barcodeValue4.getValue().length == 0 || barcodeValue.getValue()[0] < 1 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] < 3 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] > 90) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = new BarcodeMetadata(barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0], barcodeValue4.getValue()[0]);
        removeIncorrectCodewords(codewords, barcodeMetadata);
        return barcodeMetadata;
    }

    int[] getRowHeights() throws FormatException {
        BarcodeMetadata barcodeMetadata = getBarcodeMetadata();
        if (barcodeMetadata == null) {
            return null;
        }
        adjustIncompleteIndicatorColumnRowNumbers(barcodeMetadata);
        int[] iArr = new int[barcodeMetadata.getRowCount()];
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                int rowNumber = codeword.getRowNumber();
                if (rowNumber >= iArr.length) {
                    throw FormatException.getFormatInstance();
                }
                iArr[rowNumber] = iArr[rowNumber] + 1;
            }
        }
        return iArr;
    }

    boolean isLeft() {
        return this.isLeft;
    }

    void setRowNumbers() {
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
            }
        }
    }

    public String toString() {
        return "IsLeft: " + this.isLeft + '\n' + super.toString();
    }
}
