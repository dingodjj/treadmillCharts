package com.nl.treadmilllauncher.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class CommonUtils {

    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /*
     * 时间格式化
     */
    public static String getDuration(long time){
        int hour = 0, min = 0, sec = 0;
        hour = (int)(time / 3600);
        min = (int)((time - 3600 * hour) / 60);
        sec = (int)(time - 3600 * hour - 60 * min);
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public static String getDurationX(long time){
        int min = 0, sec = 0;
        min = (int)(time / 60);
        sec = (int)(time - 60 * min);
        return String.format("%02d:%02d", min, sec);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(double v, int scale) {
        if (scale < 0) {
            return 0.0f;
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static float round(float v, int scale) {
        if (scale < 0) {
            return 0.0f;
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static Typeface getTfByPath(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
}
