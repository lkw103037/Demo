package me.rokevin.ropager.util;

import android.content.Context;

/**
 * Created by luokaiwen on 15/12/19.
 */
public class Util {

    public static int dp2px(Context context, float dipValue) {
        
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
