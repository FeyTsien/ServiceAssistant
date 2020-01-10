package com.dt.serviceassistant.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;

/**
 * Created by Vipul Asri on 28/12/16.
 */

public class VectorDrawableUtils {

    public static Drawable getDrawable( Context context, int drawableResId){
        return VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
    }

    public static Drawable getDrawable(Context context, int drawableResId, int colorFilter){
        Drawable drawable = getDrawable(context, drawableResId);
        drawable.setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

}
