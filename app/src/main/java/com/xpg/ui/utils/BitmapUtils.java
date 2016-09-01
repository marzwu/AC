package com.xpg.ui.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;

public class BitmapUtils {
    private static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        i3 = Math.round(((float) i4) / ((float) i));
        return round < i3 ? round : i3;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int i, int i2, int i3) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(resources.openRawResource(i), null, options);
    }

    public static Bitmap getTransparentBitmap(Bitmap bitmap, int i) {
        int i2 = 0;
        int[] iArr = new int[(bitmap.getWidth() * bitmap.getHeight())];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int i3 = (i * MotionEventCompat.ACTION_MASK) / 100;
        while (i2 < iArr.length) {
            if (iArr[i2] != 0) {
                iArr[i2] = (i3 << 24) | (ViewCompat.MEASURED_SIZE_MASK & iArr[i2]);
            }
            i2++;
        }
        return Bitmap.createBitmap(iArr, bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    }
}
