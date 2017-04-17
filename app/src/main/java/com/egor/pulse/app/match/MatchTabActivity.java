package com.egor.pulse.app.match;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.eb.schedule.shared.bean.Match;
import com.egor.pulse.app.R;
import com.egor.pulse.app.adapter.match.MatchFragmentAdapter;
import com.egor.pulse.app.data.MatchDataStorage;
import com.egor.pulse.app.services.ServiceGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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
    ObjectMapper mapper = new ObjectMapper();

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
            final int gamesCount = extras.getInt("gamesCount");
            adapter.setGamesCount(gamesCount);
            for (int i = 0; i < gamesCount; i++) {
                MatchWrapperFragment fragment = MatchWrapperFragment.newInstance(gameId, i);
                adapter.addFragment(fragment);
            }
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            getMatches(activity, gameId);
        }


        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    private void getMatches(final Activity activity, final int gameId) {
        Call<List<String>> games = ServiceGenerator.getGameService().getMatchesByGameId(gameId);
        games.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> matches = response.body();
                if (matches != null && !matches.isEmpty()) {
                    List<Match> parsedMatches = new ArrayList<Match>(matches.size());
                    for (String match : matches) {
                        try {
                            parsedMatches.add(mapper.readValue(match, Match.class));
                        } catch (IOException e) {
                            Log.e("API", "couldn't parse match by gameId: " + gameId + ", " + match, e);
                            parsedMatches.add(null);
                        }
                    }
                    MatchDataStorage.setData(gameId, parsedMatches);
                } else {
                    Toast.makeText(activity, "Sorry! Something wrong with API", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(activity, "Couldn't get match info. Try refresh", Toast.LENGTH_LONG).show();
                Log.e("API", "couldn't get matches by gameId: " + gameId, t);
            }
        });
    }


}
