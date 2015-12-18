package me.rokevin.ropager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.rokevin.ropager.fragment.RoPagerFragment;

/**
 * Created by luokaiwen on 15/12/17.
 */
public class RoPagerFragmentAdapter extends FragmentPagerAdapter {

    public RoPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RoPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
