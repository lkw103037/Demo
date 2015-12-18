package me.rokevin.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import me.rokevin.demo.R;
import me.rokevin.demo.model.User;
import me.rokevin.ropager.adapter.RoPagerFragmentAdapter;
import me.rokevin.ropager.fragment.RoPagerFragment;
import me.rokevin.ropager.view.RoPagerView;

public class AdvancedPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_pager);

        ArrayList<User> userList = new ArrayList<>();

        User user = new User();
        User user1 = new User();
        User user2 = new User();

        user.setAvatar(R.mipmap.icon_test1);
        user1.setAvatar(R.mipmap.icon_test2);
        user2.setAvatar(R.mipmap.icon_test3);

        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        RoPagerView cPager = (RoPagerView) findViewById(R.id.c_loop);
        cPager.setDataList(userList);
        cPager.getAdapter().setPagerFragment(new RoPagerFragmentAdapter.IRoPagerFragment() {
            @Override
            public RoPagerFragment getFragment(RoPagerFragmentAdapter adapter, int position) {

                RoPagerFragment roPagerFragment = AdvancePagerFragment.newInstance(adapter, position);

                return roPagerFragment;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
