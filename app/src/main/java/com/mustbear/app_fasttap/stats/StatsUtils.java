package com.mustbear.app_fasttap.stats;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.mustbear.app_fasttap.R;

public class StatsUtils {

    public static Drawable getColoredArrow(Context activityContext, int color) {
        Drawable arrowDrawable = activityContext.getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        Drawable wrapped = DrawableCompat.wrap(arrowDrawable);

        if (arrowDrawable != null && wrapped != null) {
            // Esto evita que todas las flechas sean las que se pinten
            arrowDrawable.mutate();
            DrawableCompat.setTintList(wrapped, ColorStateList.valueOf(color));
        }

        return wrapped;
    }

}
