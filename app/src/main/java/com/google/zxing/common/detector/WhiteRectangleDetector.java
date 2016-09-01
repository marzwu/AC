package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 10;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i, int i2, int i3) throws NotFoundException {
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = bitMatrix.getWidth();
        int i4 = i / 2;
        this.leftInit = i2 - i4;
        this.rightInit = i2 + i4;
        this.upInit = i3 - i4;
        this.downInit = i4 + i3;
        if (this.upInit < 0 || this.leftInit < 0 || this.downInit >= this.height || this.rightInit >= this.width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = resultPoint2.getX();
        float y2 = resultPoint2.getY();
        float x3 = resultPoint3.getX();
        float y3 = resultPoint3.getY();
        float x4 = resultPoint4.getX();
        float y4 = resultPoint4.getY();
        if (x < ((float) this.width) / 2.0f) {
            return new ResultPoint[]{new ResultPoint(x4 - 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 + 1.0f), new ResultPoint(x3 - 1.0f, y3 - 1.0f), new ResultPoint(x + 1.0f, y - 1.0f)};
        }
        return new ResultPoint[]{new ResultPoint(x4 + 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 - 1.0f), new ResultPoint(x3 - 1.0f, y3 + 1.0f), new ResultPoint(x - 1.0f, y - 1.0f)};
    }

    private boolean containsBlackPoint(int i, int i2, int i3, boolean z) {
        if (z) {
            while (i <= i2) {
                if (this.image.get(i, i3)) {
                    return true;
                }
                i++;
            }
        } else {
            while (i <= i2) {
                if (this.image.get(i3, i)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f, float f2, float f3, float f4) {
        int round = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = (f3 - f) / ((float) round);
        float f6 = (f4 - f2) / ((float) round);
        for (int i = 0; i < round; i++) {
            int round2 = MathUtils.round((((float) i) * f5) + f);
            int round3 = MathUtils.round((((float) i) * f6) + f2);
            if (this.image.get(round2, round3)) {
                return new ResultPoint((float) round2, (float) round3);
            }
        }
        return null;
    }

    public ResultPoint[] detect() throws NotFoundException {
        ResultPoint resultPoint = null;
        boolean z = false;
        int i = 1;
        int i2 = this.leftInit;
        int i3 = this.rightInit;
        int i4 = this.upInit;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        int i5 = 1;
        int i6 = this.downInit;
        int i7 = i4;
        int i8 = i2;
        boolean z5 = false;
        int i9 = i3;
        boolean z6 = false;
        while (i5 != 0) {
            boolean z7 = z5;
            i3 = i9;
            z5 = false;
            boolean z8 = true;
            while (true) {
                if ((z8 || !z7) && i3 < this.width) {
                    z8 = containsBlackPoint(i7, i6, i3, false);
                    if (z8) {
                        z7 = true;
                        i3++;
                        z5 = true;
                    } else if (!z7) {
                        i3++;
                    }
                }
            }
            if (i3 >= this.width) {
                z = true;
                i9 = i6;
                i2 = i8;
                i8 = i7;
                break;
            }
            i9 = i6;
            boolean z9 = z6;
            z6 = z5;
            z5 = true;
            while (true) {
                if ((z5 || !z9) && i9 < this.height) {
                    z5 = containsBlackPoint(i8, i3, i9, true);
                    if (z5) {
                        z9 = true;
                        i9++;
                        z6 = true;
                    } else if (!z9) {
                        i9++;
                    }
                }
            }
            if (i9 >= this.height) {
                z = true;
                i2 = i8;
                i8 = i7;
                break;
            }
            i2 = i8;
            boolean z10 = z3;
            z3 = z6;
            z6 = true;
            while (true) {
                if ((z6 || !z10) && i2 >= 0) {
                    z6 = containsBlackPoint(i7, i9, i2, false);
                    if (z6) {
                        z10 = true;
                        i2--;
                        z3 = true;
                    } else if (!z10) {
                        i2--;
                    }
                }
            }
            if (i2 < 0) {
                z = true;
                i8 = i7;
                break;
            }
            i4 = i7;
            boolean z11 = z3;
            z3 = z2;
            z2 = true;
            while (true) {
                if ((z2 || !z3) && i4 >= 0) {
                    z2 = containsBlackPoint(i2, i3, i4, true);
                    if (z2) {
                        z11 = true;
                        i4--;
                        z3 = true;
                    } else if (!z3) {
                        i4--;
                    }
                }
            }
            if (i4 < 0) {
                z = true;
                i8 = i4;
                break;
            } else if (z11) {
                z2 = z3;
                z4 = true;
                z3 = z10;
                i8 = i2;
                z5 = z7;
                i5 = z11;
                i7 = i4;
                z6 = z9;
                i6 = i9;
                i9 = i3;
            } else {
                z2 = z3;
                z3 = z10;
                i8 = i2;
                z5 = z7;
                i5 = z11;
                i7 = i4;
                z6 = z9;
                i6 = i9;
                i9 = i3;
            }
        }
        i3 = i9;
        i2 = i8;
        i9 = i6;
        i8 = i7;
        if (z || !r13) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i10;
        i6 = i3 - i2;
        ResultPoint resultPoint2 = null;
        for (i10 = 1; i10 < i6; i10++) {
            resultPoint2 = getBlackPointOnSegment((float) i2, (float) (i9 - i10), (float) (i2 + i10), (float) i9);
            if (resultPoint2 != null) {
                break;
            }
        }
        ResultPoint resultPoint3 = resultPoint2;
        if (resultPoint3 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        resultPoint2 = null;
        for (i10 = 1; i10 < i6; i10++) {
            resultPoint2 = getBlackPointOnSegment((float) i2, (float) (i8 + i10), (float) (i2 + i10), (float) i8);
            if (resultPoint2 != null) {
                break;
            }
        }
        ResultPoint resultPoint4 = resultPoint2;
        if (resultPoint4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        resultPoint2 = null;
        for (i10 = 1; i10 < i6; i10++) {
            resultPoint2 = getBlackPointOnSegment((float) i3, (float) (i8 + i10), (float) (i3 - i10), (float) i8);
            if (resultPoint2 != null) {
                break;
            }
        }
        if (resultPoint2 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        while (i < i6) {
            resultPoint = getBlackPointOnSegment((float) i3, (float) (i9 - i), (float) (i3 - i), (float) i9);
            if (resultPoint != null) {
                break;
            }
            i++;
        }
        if (resultPoint != null) {
            return centerEdges(resultPoint, resultPoint3, resultPoint2, resultPoint4);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
