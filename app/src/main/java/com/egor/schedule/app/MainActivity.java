package com.egor.schedule.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.adapter.ScheduleAdapter;
import com.egor.schedule.app.adapter.schedule.ListAddapterItem;
import com.egor.schedule.app.adapter.schedule.ListHeader;
import com.egor.schedule.app.adapter.schedule.ListItem;
import com.egor.schedule.app.services.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//todo what if new patch bring new hero so i need to download hero image, the same for items
//todo show view and then load info
public class MainActivity extends Activity {

    private SwipeRefreshLayout swipeContainer;
    ScheduleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list);
        adapter = new ScheduleAdapter(MainActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!adapter.isEmpty()) {
                    ListAddapterItem item = adapter.getItem(position);
                    if(!item.isHeader()) {
                        Intent launchActivity = new Intent(MainActivity.this, MatchTabActivity.class);
                        launchActivity.putExtra("gameId", ((GameBean)item.getBody()).getId());
                        startActivity(launchActivity);
                    }
                }
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<Map<String, List<GameBean>>> games = ServiceGenerator.getGameService().currentGames();
                games.enqueue(getCallBack());
            }
        });

        Call<Map<String, List<GameBean>>> games = ServiceGenerator.getGameService().currentGames();
        games.enqueue(getCallBack());

    }

    private Callback<Map<String, List<GameBean>>> getCallBack() {
        return new Callback<Map<String, List<GameBean>>>() {
            @Override
            public void onResponse(Call<Map<String, List<GameBean>>> call, Response<Map<String, List<GameBean>>> response) {
                adapter.clear();
                List<ListAddapterItem> listEntries = new ArrayList<ListAddapterItem>();
                Map<String, List<GameBean>> body = response.body();
                for (Map.Entry<String, List<GameBean>> entry : body.entrySet()) {
                    listEntries.add(new ListHeader(entry.getKey()));
                    for (GameBean gameBean : entry.getValue()) {
                        listEntries.add(new ListItem(gameBean));
                    }
                }

                adapter.addAll(listEntries);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Map<String, List<GameBean>>> call, Throwable t) {
                Toast.makeText(null, "Sorry! Something wrong with API", Toast.LENGTH_LONG).show();
                Log.e("API", "couldn't get games", t);
            }
        };
    }


}