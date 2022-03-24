package com.example.yichengxuetang.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PixelTool {
    /**
     * dp转px
     */
    public static int dp2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px转dp
     */
    public static int px2dp(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context ctx, float spVal)

    {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, metrics);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context ctx, float pxVal) {
        return (pxVal / ctx.getResources().getDisplayMetrics().scaledDensity);
    }
}