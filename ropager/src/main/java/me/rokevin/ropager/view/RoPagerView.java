package me.rokevin.ropager.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import me.rokevin.ropager.R;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;

/**
 * Created by luokaiwen on 15/12/18.
 * <p/>
 * 扩展ViewPagar、可控制无线循环和自动循环
 */
public class RoPagerView extends LinearLayout {

    public RoPagerView(Context context) {
        super(context);
    }

    public RoPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.custom_pager, this);

        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vp_pager);

        FragmentPagerAdapter fragmentAdapter = new RoPagerFragmentAdapter(((FragmentActivity) context).getSupportFragmentManager());

        vpPager.setAdapter(fragmentAdapter);
    }
}
