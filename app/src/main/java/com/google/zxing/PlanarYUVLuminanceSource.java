package com.google.zxing;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;

public final class PlanarYUVLuminanceSource extends LuminanceSource {
    private static final int THUMBNAIL_SCALE_FACTOR = 2;
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final int top;
    private final byte[] yuvData;

    public PlanarYUVLuminanceSource(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        super(i5, i6);
        if (i3 + i5 > i || i4 + i6 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.yuvData = bArr;
        this.dataWidth = i;
        this.dataHeight = i2;
        this.left = i3;
        this.top = i4;
        if (z) {
            reverseHorizontal(i5, i6);
        }
    }

    private void reverseHorizontal(int i, int i2) {
        byte[] bArr = this.yuvData;
        int i3 = this.left + (this.top * this.dataWidth);
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 + (i / 2);
            int i6 = (i3 + i) - 1;
            int i7 = i3;
            while (i7 < i5) {
                byte b = bArr[i7];
                bArr[i7] = bArr[i6];
                bArr[i6] = b;
                i7++;
                i6--;
            }
            i3 += this.dataWidth;
        }
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + i, this.top + i2, i3, i4, false);
    }

    public byte[] getMatrix() {
        int i = 0;
        int width = getWidth();
        int height = getHeight();
        if (width == this.dataWidth && height == this.dataHeight) {
            return this.yuvData;
        }
        int i2 = width * height;
        Object obj = new byte[i2];
        int i3 = (this.top * this.dataWidth) + this.left;
        if (width == this.dataWidth) {
            System.arraycopy(this.yuvData, i3, obj, 0, i2);
            return obj;
        }
        Object obj2 = this.yuvData;
        while (i < height) {
            System.arraycopy(obj2, i3, obj, i * width, width);
            i3 += this.dataWidth;
            i++;
        }
        return obj;
    }

    public byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + i);
        }
        Object obj;
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            obj = new byte[width];
        }
        System.arraycopy(this.yuvData, ((this.top + i) * this.dataWidth) + this.left, obj, 0, width);
        return obj;
    }

    public int getThumbnailHeight() {
        return getHeight() / 2;
    }

    public int getThumbnailWidth() {
        return getWidth() / 2;
    }

    public boolean isCropSupported() {
        return true;
    }

    public int[] renderThumbnail() {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int[] iArr = new int[(width * height)];
        byte[] bArr = this.yuvData;
        int i = (this.top * this.dataWidth) + this.left;
        for (int i2 = 0; i2 < height; i2++) {
            int i3 = i2 * width;
            for (int i4 = 0; i4 < width; i4++) {
                iArr[i3 + i4] = ((bArr[(i4 * 2) + i] & MotionEventCompat.ACTION_MASK) * 65793) | ViewCompat.MEASURED_STATE_MASK;
            }
            i += this.dataWidth * 2;
        }
        return iArr;
    }
}
