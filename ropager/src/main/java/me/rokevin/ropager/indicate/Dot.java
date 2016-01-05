package me.rokevin.ropager.indicate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.rokevin.ropager.R;

/**
 * Created by luokaiwen on 15/12/19.
 * <p/>
 * 原点指示器
 */
public class Dot extends IndicateView {

    private static final String TAG = Dot.class.getSimpleName();

    public Dot(Context context) {
        super(context);
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Drawable getBackgroudRes() {

        if (mBackgroudRes == null) {
            mBackgroudRes = getResources().getDrawable(R.drawable.ropager_bg_solid_ring_white);
        }
        return mBackgroudRes;
    }

    @Override
    public Drawable getBackgroudSelectRes() {

        if (mBackgroundSelectRes == null) {
            mBackgroundSelectRes = getResources().getDrawable(R.drawable.ropager_bg_solid_ring_gray);
        }
        return mBackgroundSelectRes;
    }

    @Override
    public void drawChildView(int count) {

        for (int i = 0; i < count; i++) {

            View indicator = new View(getContext());
            indicator.setBackgroundDrawable(mBackgroudRes);
            addView(indicator, mIndicatorWidth, mIndicatorHeight);
            LayoutParams lp = (LayoutParams) indicator.getLayoutParams();
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            indicator.setLayoutParams(lp);

            Log.e(TAG, "indicator height:" + indicator.getHeight());
        }
    }
}
