package com.nineoldandroids.animation;

import android.support.v4.view.MotionEventCompat;

public class ArgbEvaluator implements TypeEvaluator {
    public Object evaluate(float f, Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue();
        int i = intValue >> 24;
        int i2 = (intValue >> 16) & MotionEventCompat.ACTION_MASK;
        int i3 = (intValue >> 8) & MotionEventCompat.ACTION_MASK;
        intValue &= MotionEventCompat.ACTION_MASK;
        int intValue2 = ((Integer) obj2).intValue();
        return Integer.valueOf((intValue + ((int) (((float) ((intValue2 & MotionEventCompat.ACTION_MASK) - intValue)) * f))) | ((((i + ((int) (((float) ((intValue2 >> 24) - i)) * f))) << 24) | ((i2 + ((int) (((float) (((intValue2 >> 16) & MotionEventCompat.ACTION_MASK) - i2)) * f))) << 16)) | ((((int) (((float) (((intValue2 >> 8) & MotionEventCompat.ACTION_MASK) - i3)) * f)) + i3) << 8)));
    }
}
