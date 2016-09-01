package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 8 || height > 144 || (height & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        this.version = readVersion(bitMatrix);
        this.mappingBitMatrix = extractDataRegion(bitMatrix);
        this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int symbolSizeRows = this.version.getSymbolSizeRows();
        int symbolSizeColumns = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != symbolSizeRows) {
            throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        }
        int dataRegionSizeRows = this.version.getDataRegionSizeRows();
        int dataRegionSizeColumns = this.version.getDataRegionSizeColumns();
        int i = symbolSizeRows / dataRegionSizeRows;
        int i2 = symbolSizeColumns / dataRegionSizeColumns;
        BitMatrix bitMatrix2 = new BitMatrix(i2 * dataRegionSizeColumns, i * dataRegionSizeRows);
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i3 * dataRegionSizeRows;
            for (int i5 = 0; i5 < i2; i5++) {
                int i6 = i5 * dataRegionSizeColumns;
                for (symbolSizeColumns = 0; symbolSizeColumns < dataRegionSizeRows; symbolSizeColumns++) {
                    int i7 = symbolSizeColumns + (((dataRegionSizeRows + 2) * i3) + 1);
                    int i8 = i4 + symbolSizeColumns;
                    for (symbolSizeRows = 0; symbolSizeRows < dataRegionSizeColumns; symbolSizeRows++) {
                        if (bitMatrix.get((((dataRegionSizeColumns + 2) * i5) + 1) + symbolSizeRows, i7)) {
                            bitMatrix2.set(i6 + symbolSizeRows, i8);
                        }
                    }
                }
            }
        }
        return bitMatrix2;
    }

    Version getVersion() {
        return this.version;
    }

    byte[] readCodewords() throws FormatException {
        int i = 0;
        byte[] bArr = new byte[this.version.getTotalCodewords()];
        int height = this.mappingBitMatrix.getHeight();
        int width = this.mappingBitMatrix.getWidth();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 4;
        while (true) {
            int i8;
            int i9;
            if (i7 == height && i6 == 0 && i5 == 0) {
                i8 = i + 1;
                bArr[i] = (byte) readCorner1(height, width);
                i = i6 + 2;
                i6 = i2;
                i2 = 1;
                i9 = i3;
                i3 = i;
                i = i8;
                i8 = i4;
                i4 = i7 - 2;
                i7 = i9;
            } else if (i7 == height - 2 && i6 == 0 && (width & 3) != 0 && i4 == 0) {
                i8 = i + 1;
                bArr[i] = (byte) readCorner2(height, width);
                i = i6 + 2;
                i6 = i2;
                i4 = i7 - 2;
                i7 = i3;
                i2 = i5;
                i3 = i;
                i = i8;
                i8 = 1;
            } else if (i7 == height + 4 && i6 == 2 && (width & 7) == 0 && i3 == 0) {
                i8 = i + 1;
                bArr[i] = (byte) readCorner3(height, width);
                i = i6 + 2;
                i6 = i2;
                i3 = i;
                i2 = i5;
                i = i8;
                i8 = i4;
                i4 = i7 - 2;
                i7 = 1;
            } else if (i7 == height - 2 && i6 == 0 && (width & 7) == 4 && i2 == 0) {
                i8 = i + 1;
                bArr[i] = (byte) readCorner4(height, width);
                i = i6 + 2;
                i6 = 1;
                i2 = i5;
                i9 = i3;
                i3 = i;
                i = i8;
                i8 = i4;
                i4 = i7 - 2;
                i7 = i9;
            } else {
                i8 = i7;
                i7 = i6;
                while (true) {
                    if (i8 >= height || i7 < 0 || this.readMappingMatrix.get(i7, i8)) {
                        i6 = i;
                    } else {
                        i6 = i + 1;
                        bArr[i] = (byte) readUtah(i8, i7, height, width);
                    }
                    i8 -= 2;
                    i = i7 + 2;
                    if (i8 < 0 || i >= width) {
                        i8++;
                        i7 = i + 3;
                    } else {
                        i7 = i;
                        i = i6;
                    }
                }
                i8++;
                i7 = i + 3;
                while (true) {
                    if (i8 < 0 || i7 >= width || this.readMappingMatrix.get(i7, i8)) {
                        i = i6;
                    } else {
                        i = i6 + 1;
                        bArr[i6] = (byte) readUtah(i8, i7, height, width);
                    }
                    i8 += 2;
                    i6 = i7 - 2;
                    if (i8 >= height || i6 < 0) {
                        i7 = i8 + 3;
                        i8 = i4;
                        i4 = i7;
                        i7 = i3;
                        i3 = i6 + 1;
                        i6 = i2;
                        i2 = i5;
                    } else {
                        i7 = i6;
                        i6 = i;
                    }
                }
                i7 = i8 + 3;
                i8 = i4;
                i4 = i7;
                i7 = i3;
                i3 = i6 + 1;
                i6 = i2;
                i2 = i5;
            }
            if (i4 >= height && i3 >= width) {
                break;
            }
            i5 = i2;
            i2 = i6;
            i6 = i3;
            i3 = i7;
            i7 = i4;
            i4 = i8;
        }
        if (i == this.version.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    int readCorner1(int i, int i2) {
        int i3 = (readModule(i + -1, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 1, 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(2, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(3, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    int readCorner2(int i, int i2) {
        int i3 = (readModule(i + -3, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 2, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 4, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(1, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    int readCorner3(int i, int i2) {
        int i3 = (readModule(i + -1, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(1, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    int readCorner4(int i, int i2) {
        int i3 = (readModule(i + -3, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 2, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(2, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(3, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    boolean readModule(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i < 0) {
            i5 = i + i3;
            i6 = (4 - ((i3 + 4) & 7)) + i2;
        } else {
            i6 = i2;
            i5 = i;
        }
        if (i6 < 0) {
            i6 += i4;
            i5 += 4 - ((i4 + 4) & 7);
        }
        this.readMappingMatrix.set(i6, i5);
        return this.mappingBitMatrix.get(i6, i5);
    }

    int readUtah(int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (readModule(i - 2, i2 - 2, i3, i4)) {
            i5 = 1;
        }
        i5 <<= 1;
        if (readModule(i - 2, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2 - 2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i, i2 - 2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        return readModule(i, i2, i3, i4) ? i5 | 1 : i5;
    }
}
