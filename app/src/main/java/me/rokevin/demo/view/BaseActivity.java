package me.rokevin.demo.view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

/**
 * Created by luokaiwen on 15/12/14.
 * <p/>
 * 基类
 */
public class BaseActivity extends FragmentActivity {

    protected Context mContext = BaseActivity.this;

    public String getTag() {

        return getClass().getSimpleName();
    }
}
