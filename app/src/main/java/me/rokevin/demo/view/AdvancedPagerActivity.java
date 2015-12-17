package me.rokevin.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import me.rokevin.demo.R;
import me.rokevin.demo.model.User;

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
        user2.setAvatar(R.mipmap.icon_test3);

        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        // AdvancedPagerView cLoop = (AdvancedPagerView) findViewById(R.id.c_loop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
