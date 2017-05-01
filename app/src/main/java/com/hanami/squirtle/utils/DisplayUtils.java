package com.hanami.squirtle.utils;

import android.content.Context;

/**
 * Created by hanami on 2017/4/17.
 */

public class DisplayUtils {

    public static int getScreenHeight(Context context) {

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {

        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int px2dip(Context context, int pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);
    }

}
