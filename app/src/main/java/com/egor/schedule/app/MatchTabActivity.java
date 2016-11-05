package com.egor.schedule.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eb.schedule.shared.bean.GameBean;
import com.eb.schedule.shared.bean.Match;
import com.egor.schedule.app.adapter.MatchFragmentAdapter;
import com.egor.schedule.app.fragment.MatchWrapperFragment;
import com.egor.schedule.app.services.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Egor on 22.06.2016.
 */
public class MatchTabActivity extends FragmentActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe);


        final Activity activity = this;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        final MatchFragmentAdapter adapter = new MatchFragmentAdapter(getSupportFragmentManager());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int gameId = extras.getInt("gameId");
            Call<List<Match>> games = ServiceGenerator.getGameService().getMatchesByGameId(gameId);
            games.enqueue(new Callback<List<Match>>() {
                @Override
                public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                    List<Match> matches = response.body();
                    if (matches != null) {
                        if(matches.isEmpty()){
                            Toast.makeText(activity, "Sorry! Something wrong with API", Toast.LENGTH_LONG).show();
                        }else {
                            for (Match m : matches) {
                                adapter.addFragment(MatchWrapperFragment.newInstance(m));
                            }
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Match>> call, Throwable t) {
                    Toast.makeText(activity, "Couldn't get match info. Try refresh", Toast.LENGTH_LONG).show();
                    Log.e("API", "couldn't get matches by gameId: " + gameId, t);
                }
            });
        }

//        viewPager.setAdapter(adapter);


        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }


}
