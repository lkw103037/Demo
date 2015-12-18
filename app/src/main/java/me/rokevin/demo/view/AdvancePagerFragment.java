package me.rokevin.demo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.rokevin.demo.R;
import me.rokevin.demo.model.User;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;
import me.rokevin.ropager.fragment.RoPagerFragment;

/**
 * Created by luokaiwen on 15/12/18.
 */
public class AdvancePagerFragment extends RoPagerFragment<User> {

    public static AdvancePagerFragment newInstance(RoPagerFragmentAdapter adapter, int position) {

        AdvancePagerFragment fragment = new AdvancePagerFragment();

        Bundle args = new Bundle();
        args.putInt(POSTION, position);
        args.putSerializable(ADAPTER, adapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onView(View view, User user, int position) {

        ImageView ivTest = (ImageView) view.findViewById(R.id.iv_test);
        ivTest.setImageResource(user.getAvatar());
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    public int getPosition() {
        return getArguments().getInt(POSTION);
    }

    @Override
    public RoPagerFragmentAdapter getAdapter() {
        return (RoPagerFragmentAdapter) getArguments().getSerializable(ADAPTER);
    }
}
