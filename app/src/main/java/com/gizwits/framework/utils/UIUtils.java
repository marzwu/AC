package com.gizwits.framework.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UIUtils {
    public static final int UNCONSTRAINED = -1;
    private static Context mContext;
    private static LruCache<String, Bitmap> mMemoryCache;
    private static Resources mResources;

    class AnonymousClass1 extends LruCache<String, Bitmap> {
        AnonymousClass1(int i) {
            super(i);
        }

        protected int sizeOf(String str, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    }

    private static void addBitmapToMemoryCache(String str, Bitmap bitmap) {
        if (getBitmapFromMemCache(str) == null) {
            mMemoryCache.put(str, bitmap);
        }
    }

    public static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        i3 = Math.round(((float) i4) / ((float) i));
        return round < i3 ? round : i3;
    }

    private static int computeInitialSampleSize(Options options, int i, int i2) {
        double d = (double) options.outWidth;
        double d2 = (double) options.outHeight;
        int ceil = i2 == -1 ? 1 : (int) Math.ceil(Math.sqrt((d * d2) / ((double) i2)));
        int min = i == -1 ? 128 : (int) Math.min(Math.floor(d / ((double) i)), Math.floor(d2 / ((double) i)));
        return min < ceil ? ceil : (i2 == -1 && i == -1) ? 1 : i != -1 ? min : ceil;
    }

    public static int computeSampleSize(Options options, int i, int i2) {
        int computeInitialSampleSize = computeInitialSampleSize(options, i, i2);
        if (computeInitialSampleSize > 8) {
            return ((computeInitialSampleSize + 7) / 8) * 8;
        }
        int i3 = 1;
        while (i3 < computeInitialSampleSize) {
            i3 <<= 1;
        }
        return i3;
    }

    public static Bitmap decodeSampledBitmapFromResource(Context context, Resources resources, int i, int i2, int i3) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = calculateInSampleSize(options, i2, i3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(i), null, options);
    }

    public static int dip2px(Context context, float f) {
        return (int) (0.5f + (context.getResources().getDisplayMetrics().density * f));
    }

    public static Bitmap getBitmapByPath(String str, Options options, int i, int i2) throws FileNotFoundException {
        File file = new File(str);
        if (file.exists()) {
            InputStream fileInputStream = new FileInputStream(file);
            if (options != null) {
                Rect screenRegion = getScreenRegion(i, i2);
                int width = screenRegion.width();
                int height = screenRegion.height();
                options.inSampleSize = computeSampleSize(options, width > height ? width : height, width * height);
                options.inJustDecodeBounds = false;
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, null, options);
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return decodeStream;
        }
        throw new FileNotFoundException();
    }

    private static Bitmap getBitmapFromMemCache(String str) {
        return mMemoryCache != null ? (Bitmap) mMemoryCache.get(str) : null;
    }

    public static Options getOptions(String str) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return options;
    }

    private static Rect getScreenRegion(int i, int i2) {
        return new Rect(0, 0, i, i2);
    }

    public static void initCache(final Context context) {
        UIUtils.mContext = context.getApplicationContext();
        UIUtils.mResources = UIUtils.mContext.getResources();
        UIUtils.mMemoryCache = new LruCache<String, Bitmap>((int)(Runtime.getRuntime().maxMemory() / 1024L) / 8) {

            @Override
            protected int sizeOf(final String s, final Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static Bitmap loadBitmap(int i, int i2) {
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(String.valueOf(i));
        if (bitmapFromMemCache != null) {
            return bitmapFromMemCache;
        }
        bitmapFromMemCache = decodeSampledBitmapFromResource(mContext, mResources, i, i2, i2);
        addBitmapToMemoryCache(String.valueOf(i), bitmapFromMemCache);
        return bitmapFromMemCache;
    }

    public static Bitmap loadBitmap(int i, int i2, int i3, int i4, int i5) {
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(String.valueOf(i));
        if (bitmapFromMemCache != null) {
            return bitmapFromMemCache;
        }
        Bitmap decodeSampledBitmapFromResource = decodeSampledBitmapFromResource(mContext, mResources, i, i2, i3);
        bitmapFromMemCache = resizeImage(decodeSampledBitmapFromResource, i4, i5);
        addBitmapToMemoryCache(String.valueOf(i), bitmapFromMemCache);
        decodeSampledBitmapFromResource.recycle();
        return bitmapFromMemCache;
    }

    public static int px2dip(Context context, float f) {
        return (int) (0.5f + (f / context.getResources().getDisplayMetrics().density));
    }

    public static Bitmap resizeImage(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) i) / ((float) width);
        float f2 = ((float) i2) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
