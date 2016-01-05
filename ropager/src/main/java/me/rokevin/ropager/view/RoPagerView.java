package me.rokevin.ropager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import me.rokevin.ropager.R;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;
import me.rokevin.ropager.constant.Type;
import me.rokevin.ropager.indicate.Dot;
import me.rokevin.ropager.indicate.IndicateView;
import me.rokevin.ropager.util.Util;

/**
 * Created by luokaiwen on 15/12/18.
 * <p/>
 * 扩展ViewPagar、可控制无线循环和自动循环
 */
public class RoPagerView<T> extends LinearLayout {

    private static final String TAG = RoPagerView.class.getSimpleName();

    /**
     * 默认自动循环间隔
     */
    private final int DEFAULT_LOOP_TIME = 5000;

    private RelativeLayout rlContainer;
    private ViewPager vpPager;
    private IndicateView indicateView;

    /**
     * 是否需要指示器
     */
    private boolean isIndicate;

    /**
     * 指示器的样式 默认样式为原点
     */
    private String indicateStyle;

    /**
     * 指示器的选中颜色
     */
    private Drawable indicateSelectRes;

    /**
     * 指示器的没有选中的颜色
     */
    private Drawable indicateRes;

    /**
     * 是否是Fragment
     */
    private boolean isFragment;

    /**
     * 是否自动循环
     */
    private boolean isAutoLoop;

    /**
     * 自动循环间隔
     */
    private int loopTime = DEFAULT_LOOP_TIME;

    /**
     * 自动循环定时器
     */
    private CountDownTimer mCountDownTimer;

    /**
     * 是否取消自动循环
     */
    private boolean isCancelAutoLoop;

    /**
     * 是否处于空闲状态
     */
    private boolean isIdle = true;

    private RoPagerFragmentAdapter fragmentAdapter;

    /**
     * 页面索引
     */
    private int pageIndex;

    public RoPagerView(Context context) {
        super(context);
    }

    public RoPagerView(Context context, AttributeSet attrs) {

        super(context, attrs);

        fragmentAdapter = new RoPagerFragmentAdapter(((FragmentActivity) context).getSupportFragmentManager());

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.roPager);

        // 是否显示指示器
        isIndicate = typedArray.getBoolean(R.styleable.roPager_isIndicate, false);

        // 配置指示器的样式
        indicateStyle = typedArray.getString(R.styleable.roPager_indicateStyle);

        // 指示器选中颜色
        indicateSelectRes = typedArray.getDrawable(R.styleable.roPager_indicateSelectRes);

        // 指示器默认颜色
        indicateRes = typedArray.getDrawable(R.styleable.roPager_indicateRes);

        if (TextUtils.isEmpty(indicateStyle)) {
            indicateStyle = Type.DOT;
        }

        // 是否自动循环
        isAutoLoop = typedArray.getBoolean(R.styleable.roPager_isAutoLoop, false);

        // 自动循环间隔
        loopTime = typedArray.getInt(R.styleable.roPager_loopTime, DEFAULT_LOOP_TIME);

        // 是否无线循环
        final boolean isLoop = typedArray.getBoolean(R.styleable.roPager_isLoop, false);

        typedArray.recycle();

        fragmentAdapter.setLoop(isLoop);

        View view = LayoutInflater.from(context).inflate(R.layout.ropager_custom_pager, this);

        rlContainer = (RelativeLayout) view.findViewById(R.id.rl_container);
        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);

        if (isIndicate) {

            drawIndicate(context);
        }

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (null != mIRoPagerListener) {
                    mIRoPagerListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (null != mIRoPagerListener) {
                    mIRoPagerListener.onPageSelected(position);
                }

                if (null != indicateView) {

                    indicateView.onPageSelected(position);
                }

                ArrayList dataList = fragmentAdapter.getDataList();

                int size = dataList.size();

                pageIndex = position;

                if (isLoop) {

                    if (position == 0) {
                        // 当视图在第一个时，将页面号设置为图片的最后一张。
                        pageIndex = size;
                    } else if (position == size + 1) {
                        // 当视图在最后一个是,将页面号设置为图片的第一张。
                        pageIndex = 1;
                    }

                } else {
                    pageIndex = position;
                }

                if (position != pageIndex) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            vpPager.setCurrentItem(pageIndex, false);
                        }
                    }, 250);

                    return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (null != mIRoPagerListener) {
                    mIRoPagerListener.onPageScrollStateChanged(state);
                }

                isIdle = (state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        vpPager.setAdapter(fragmentAdapter);

        if (isAutoLoop && null == mCountDownTimer) {

            mCountDownTimer = new CountDownTimer(5000000, loopTime) {

                @Override
                public void onTick(long millisUntilFinished) {

                    // 如果处于手机碰触状态则不执行
                    if (!isIdle) {
                        return;
                    }

                    if (null != vpPager && !isCancelAutoLoop) {

                        int currentItem = vpPager.getCurrentItem() + 1;

                        if (currentItem == vpPager.getAdapter().getCount()) {

                            try {

                                // 滑到最右边回归到第一个位置
                                vpPager.setCurrentItem(0);

                            } catch (Exception e) {

                            }

                        } else {

                            try {

                                // 向右滑
                                //viewPager.arrowScroll(2);
                                vpPager.setCurrentItem(currentItem);

                            } catch (Exception e) {

                            }
                        }
                    } else {
                        isCancelAutoLoop = true;
                        cancel();
                    }
                }

                @Override
                public void onFinish() {

                    if (!isCancelAutoLoop) {
                        mCountDownTimer.start();
                    }
                }
            };
        }
    }

    public void setDataList(ArrayList<T> dateList) {

        if (null == dateList || dateList.size() == 0) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }

        fragmentAdapter.setList(dateList);

        if (fragmentAdapter.isLoop()) {
            vpPager.setCurrentItem(1, false);
        } else {
            vpPager.setCurrentItem(0, false);
        }

        if (isAutoLoop && null != mCountDownTimer) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mCountDownTimer.start();

                }
            }, loopTime);
        }

        if (null != indicateView) {

            indicateView.createIndicators();
        }
    }

    /**
     * 设置banner高度
     *
     * @param height
     */
    public void setHeight(int height) {

        getLayoutParams().height = height;
    }

    /**
     * 清除轮询
     */
    public void destroyPoll() {

        isCancelAutoLoop = true;

        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
    }

    public RoPagerFragmentAdapter getAdapter() {

        return fragmentAdapter;
    }

    /**
     * 画指示器
     */
    public void drawIndicate(Context context) {

        // 如果配置了适配器则显示
        switch (indicateStyle) {

            case Type.DOT:

                indicateView = new Dot(context);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.bottomMargin = Util.dp2px(context, 2);
                indicateView.setLayoutParams(lp);
                indicateView.setAdatper(fragmentAdapter);
                indicateView.setBackgroundRes(indicateRes);
                indicateView.setBackgroundSelectRes(indicateSelectRes);
                rlContainer.addView(indicateView);
                break;
        }
    }

    private IRoPagerListener mIRoPagerListener;

    public void setIRoPagerListener(IRoPagerListener iRoPagerListener) {
        mIRoPagerListener = iRoPagerListener;
    }

    public interface IRoPagerListener {

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
}
