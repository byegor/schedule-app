package com.egor.schedule.app.services;

import com.eb.schedule.shared.bean.GameBean;
import com.eb.schedule.shared.bean.Match;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by Egor on 26.05.2016.
 */
public interface GameService {

    @GET("/games/current")
    Call<List<GameBean>> currentGames();

    @GET("/games/game/{gameId}")
    Call<List<Match>> getMatchesByGameId(@Path("gameId")int gameId);
}
