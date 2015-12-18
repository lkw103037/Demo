package me.rokevin.ropager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import me.rokevin.ropager.R;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;

/**
 * Created by luokaiwen on 15/12/18.
 * <p/>
 * 扩展ViewPagar、可控制无线循环和自动循环
 */
public class RoPagerView<T> extends LinearLayout {

    private ViewPager vpPager;

    /**
     * 是否需要指示器
     */
    private boolean isIndicate;

    /**
     * 指示器的选中颜色
     */
    private int indicateSelectColor;

    /**
     * 指示器的没有选中的颜色
     */
    private int indicateColor;

    /**
     * 指示器的样式
     */
    private String indicateStyle;

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
    private int loopTime;

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

        isIndicate = typedArray.getBoolean(R.styleable.roPager_isIndicate, false);

        // 是否无线循环
        final boolean isLoop = typedArray.getBoolean(R.styleable.roPager_isLoop, false);

        typedArray.recycle();

        fragmentAdapter.setLoop(isLoop);

        View view = LayoutInflater.from(context).inflate(R.layout.custom_pager, this);

        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

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
                    }, 300);

                    return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpPager.setAdapter(fragmentAdapter);
    }

    public void setDataList(ArrayList<T> dateList) {

        fragmentAdapter.setList(dateList);

        if (fragmentAdapter.isLoop()) {
            vpPager.setCurrentItem(1, false);
        } else {
            vpPager.setCurrentItem(0, false);
        }
    }

    public RoPagerFragmentAdapter getAdapter() {

        return fragmentAdapter;
    }

}
