package me.rokevin.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import me.rokevin.demo.view.BaseActivity;
import me.rokevin.demo.view.AdvancedPagerActivity;

public class MainActivity extends BaseActivity {

    private Button btnPager;
    private KBaseAdapter<Activity> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnPager = (Button) findViewById(R.id.btn_entry_circle_pager);

        btnPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(mContext, AdvancedPagerActivity.class));
            }
        });
    }

}
