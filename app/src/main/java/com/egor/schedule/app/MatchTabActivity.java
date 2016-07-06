package com.egor.schedule.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.egor.schedule.app.adapter.MatchFragmentAdapter;
import com.egor.schedule.app.fragment.MatchWrapperFragment;

/**
 * Created by Egor on 22.06.2016.
 */
public class MatchTabActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        MatchFragmentAdapter adapter = new MatchFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new MatchWrapperFragment());
        viewPager.setAdapter(adapter);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }


}
