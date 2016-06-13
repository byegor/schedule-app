package com.egor.schedule.app;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.adapter.ScheduleAdapter;
import com.egor.schedule.app.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    String[] itemname = {
            "Safari",
            "Safari",
            "Safari",
            "Safari",
            "Safari",
            "Camera"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView viewById = (ListView) findViewById(R.id.list);


        Call<List<GameBean>> games = ServiceGenerator.getGameService().currentGames();
        games.enqueue(new Callback<List<GameBean>>() {
            @Override
            public void onResponse(Call<List<GameBean>> call, Response<List<GameBean>> response) {
                viewById.setAdapter(new ScheduleAdapter(MainActivity.this, response.body()));
            }

            @Override
            public void onFailure(Call<List<GameBean>> call, Throwable t) {
                Log.e("CURRENT", "couldn't get games", t);
            }
        });

    }


}