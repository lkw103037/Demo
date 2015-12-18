package me.rokevin.ropager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rokevin.ropager.R;

/**
 * Created by luokaiwen on 15/12/17.
 */
public class RoPagerFragment extends Fragment {

    private static final String POSTION = "position";

    private int position;

    public static RoPagerFragment newInstance(int position) {
        RoPagerFragment fragment = new RoPagerFragment();
        Bundle args = new Bundle();
        args.putInt(POSTION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public RoPagerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pager, container, false);

        return v;
    }
}
