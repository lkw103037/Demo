package me.rokevin.ropager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;

/**
 * Created by luokaiwen on 15/12/17.
 */
public abstract class RoPagerFragment<T> extends Fragment {

    protected static final String TAG = RoPagerFragment.class.getSimpleName();

    protected static final String POSTION = "position";
    protected static final String ADAPTER = "adapter";

    protected RoPagerFragmentAdapter mAdapter;

    protected int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getPosition();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View v = inflater.inflate(R.layout.ropager_fragment_pager, container, false);
        Log.e(TAG, "layoutId():" + layoutId());
        View v = inflater.inflate(layoutId(), container, false);

        ArrayList<T> dataList = mAdapter.getDataList();

        int size = dataList.size();

        T t;

        if (mAdapter.isLoop()) {
            if (position == 0) {
                t = dataList.get(size - 1);
            } else if (position == size + 1) {
                t = dataList.get(0);
            } else {
                t = dataList.get(position - 1);
            }
        } else {
            t = dataList.get(position);
        }


        onView(v, t, position);

        return v;
    }

    public abstract void onView(View view, T t, int position);

    public abstract int layoutId();

    public abstract int getPosition();

    public RoPagerFragmentAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RoPagerFragmentAdapter adapter) {
        mAdapter = adapter;
    }
}
