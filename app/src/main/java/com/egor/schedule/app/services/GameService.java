package com.egor.schedule.app.services;

import com.eb.schedule.shared.bean.GameBean;
import com.eb.schedule.shared.bean.Match;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Egor on 26.05.2016.
 */
public interface GameService {

    @GET("/games/current")
//    @GET("/debug/games/1")
    Call<Map<String, List<GameBean>>> currentGames();

    @GET("/games/game/{gameId}")
    Call<Map<String, String>> getMatchesByGameId(@Path("gameId")int gameId);

    @GET("/games/match/{matchId}")
    Call<Match> getMatchById(@Path("matchId")long matchId);
}
