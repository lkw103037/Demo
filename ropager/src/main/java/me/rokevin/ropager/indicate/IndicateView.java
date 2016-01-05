package me.rokevin.ropager.indicate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;
import me.rokevin.ropager.util.Util;

/**
 * Created by luokaiwen on 15/12/18.
 * <p/>
 * 指示器
 */
public abstract class IndicateView extends LinearLayout {

    private static final String TAG = IndicateView.class.getSimpleName();

    public IndicateView(Context context) {
        super(context);
        init(context, null);
    }

    public IndicateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    protected final static int DEFAULT_INDICATOR_SIZE = 6;

    protected RoPagerFragmentAdapter mPagerAdapter;

    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener;

    protected int mIndicatorMargin;

    protected int mIndicatorWidth;

    protected int mIndicatorHeight;

    protected Drawable mBackgroudRes;

    protected Drawable mBackgroundSelectRes;

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
//            mBackgroudRes = typedArray.getResourceId(R.styleable.GuideCircleIndicator_ci_drawable, mBackgroudRes);
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

        choicePoint(getChildAt(mCurrentPosition), getBackgroudRes());
        choicePoint(getChildAt(position), getBackgroudSelectRes());

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

        if (count == 1) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }

        drawChildView(count);

        choicePoint(getChildAt(mCurrentPosition), getBackgroudSelectRes());
    }

    public void choicePoint(View view, Drawable drawable) {

        if (null == view) {
            return;
        }

        if (null == drawable) {
            return;
        }

        view.setBackgroundDrawable(drawable);
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    /**
     * xml中配置背景资源文件
     *
     * @param backgroudRes
     */
    public void setBackgroundRes(Drawable backgroudRes) {

        mBackgroudRes = backgroudRes;
    }

    /**
     * xml中配置选中背景资源文件
     *
     * @param backgroudRes
     */
    public void setBackgroundSelectRes(Drawable backgroudRes) {

        mBackgroundSelectRes = backgroudRes;
    }

    public abstract Drawable getBackgroudRes();

    public abstract Drawable getBackgroudSelectRes();

    public abstract void drawChildView(int count);
}
