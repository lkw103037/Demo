package me.rokevin.ropager.indicate;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import me.rokevin.ropager.R;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;
import me.rokevin.ropager.util.Util;

/**
 * Created by luokaiwen on 15/12/18.
 * <p/>
 * 指示器
 */
public class IndicateView extends LinearLayout {

    private static final String TAG = IndicateView.class.getSimpleName();

    public IndicateView(Context context) {
        super(context);
        init(context, null);
    }

    public IndicateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    private final static int DEFAULT_INDICATOR_SIZE = 6;

    private RoPagerFragmentAdapter mPagerAdapter;

    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener;

    private int mIndicatorMargin;

    private int mIndicatorWidth;

    private int mIndicatorHeight;

    private int mIndicatorBackground = R.drawable.bg_solid_ring_white;
    private int mIndicatorBackgroundSelected = R.drawable.bg_solid_ring_red;

    private int mCurrentPosition = 0;

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {

//        if (attrs != null) {
//
//            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GuideCircleIndicator);
//            mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.GuideCircleIndicator_ci_width, -1);
//            mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.GuideCircleIndicator_ci_height, -1);
//            mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.GuideCircleIndicator_ci_margin, -1);
//            mAnimatorResId = typedArray.getResourceId(R.styleable.GuideCircleIndicator_ci_animator, R.anim.scale_with_alpha);
//            mIndicatorBackground = typedArray.getResourceId(R.styleable.GuideCircleIndicator_ci_drawable, mIndicatorBackground);
//            typedArray.recycle();
//        }
//
        int width = Util.dp2px(context, DEFAULT_INDICATOR_SIZE);
        int height = Util.dp2px(context, DEFAULT_INDICATOR_SIZE);
        int margin = Util.dp2px(context, DEFAULT_INDICATOR_SIZE);

        mIndicatorWidth = width;
        mIndicatorHeight = height;
        mIndicatorMargin = margin;

        // TODO: 15/12/19 自己设置指示器大小
//        mIndicatorWidth = (mIndicatorWidth == -1) ? width : mIndicatorWidth;
//        mIndicatorHeight = (mIndicatorHeight == -1) ? height : mIndicatorHeight;
//        mIndicatorMargin = (mIndicatorMargin == -1) ? margin : mIndicatorMargin;
    }

    public void setAdatper(RoPagerFragmentAdapter viewPager) {
        mPagerAdapter = viewPager;
    }

    public void onPageSelected(int position) {

        int size = mPagerAdapter.getCount();

        if (mPagerAdapter.isLoop()) {
            if (position == 0) {
                position = size - 1;
            } else if (position == size + 1) {
                position = 0;
            } else {
                position = position - 1;
            }
        }

        choicePoint(getChildAt(mCurrentPosition), mIndicatorBackground);
        choicePoint(getChildAt(position), mIndicatorBackgroundSelected);

        Log.e(TAG, "position:" + position);

        mCurrentPosition = position;
    }

    public void createIndicators() {
        removeAllViews();
        int count = mPagerAdapter.getCount();
        if (count <= 0) {
            return;
        }

        if (mPagerAdapter.isLoop() && count > 1) {
            count -= 2;
        }

        for (int i = 0; i < count; i++) {

            View indicator = new View(getContext());
            indicator.setBackgroundResource(mIndicatorBackground);
            addView(indicator, mIndicatorWidth, mIndicatorHeight);
            LayoutParams lp = (LayoutParams) indicator.getLayoutParams();
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
            indicator.setLayoutParams(lp);

            Log.e(TAG, "indicator height:" + indicator.getHeight());
        }

        choicePoint(getChildAt(mCurrentPosition), mIndicatorBackgroundSelected);
    }

    public void choicePoint(View view, int color) {

        if (null == view) {
            return;
        }

        view.setBackgroundResource(color);
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }
}
