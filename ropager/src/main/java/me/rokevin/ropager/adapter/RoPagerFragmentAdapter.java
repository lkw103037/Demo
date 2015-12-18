package me.rokevin.ropager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import me.rokevin.ropager.fragment.RoPagerFragment;

/**
 * Created by luokaiwen on 15/12/17.
 */
public class RoPagerFragmentAdapter<T> extends FragmentPagerAdapter implements Serializable {

    private ArrayList<T> mDataList = new ArrayList<>();

    private int mCount = 0;

    /**
     * 是否循环
     */
    private boolean isLoop;

    public RoPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        RoPagerFragment roPagerFragment = null;

        if (null != iRoPagerFragment) {
            roPagerFragment = iRoPagerFragment.getFragment(this, position);
        }

        return roPagerFragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setList(ArrayList<T> dateList) {

        if (null == mDataList && mDataList.size() == 0) {
            return;
        }

        mDataList = dateList;

        if (null != mDataList && mDataList.size() > 0) {

            mCount = mDataList.size();

            if (isLoop() && mCount > 1) {
                mCount += 2;
            }
        }

        notifyDataSetChanged();
    }

    public ArrayList<T> getDataList() {
        return mDataList;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    IRoPagerFragment iRoPagerFragment;

    public void setPagerFragment(IRoPagerFragment iRoPagerFragment) {
        this.iRoPagerFragment = iRoPagerFragment;
    }

    public interface IRoPagerFragment {

        RoPagerFragment getFragment(RoPagerFragmentAdapter adapter, int position);
    }
}
