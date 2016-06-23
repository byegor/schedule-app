package com.egor.schedule.app;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.adapter.ScheduleAdapter;
import com.egor.schedule.app.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends Activity {

    private SwipeRefreshLayout swipeContainer;
    ScheduleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView viewById = (ListView) findViewById(R.id.list);
        adapter = new ScheduleAdapter(MainActivity.this);
        viewById.setAdapter(adapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<List<GameBean>> games = ServiceGenerator.getGameService().currentGames();
                games.enqueue(getCallBack());
            }
        });

        Call<List<GameBean>> games = ServiceGenerator.getGameService().currentGames();
        games.enqueue(getCallBack());

    }

    private Callback<List<GameBean>> getCallBack() {
        return new Callback<List<GameBean>>() {
            @Override
            public void onResponse(Call<List<GameBean>> call, Response<List<GameBean>> response) {
                adapter.clear();
                adapter.addAll(response.body());
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GameBean>> call, Throwable t) {
                Log.e("CURRENT", "couldn't get games", t);
            }
        };
    }


}