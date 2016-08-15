package ru.test.weatherapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Alexey Antonchik on 14.08.16.
 */
public class ViewHelper {
    private static final long MIN_CLICK_INTERVAL = 1 * 500;

    public static float dpToPx(Context context, int dp)
    {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    public static void preventDoubleClick(final View view) {
        view.setEnabled(false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, MIN_CLICK_INTERVAL);
    }
}