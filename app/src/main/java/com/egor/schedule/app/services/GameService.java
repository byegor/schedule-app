package com.egor.schedule.app.services;

import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.model.Game;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by Egor on 26.05.2016.
 */
public interface GameService {

    @GET("/games/current")
    Call<List<GameBean>> currentGames();
}
