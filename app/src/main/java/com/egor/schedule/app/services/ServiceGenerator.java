package com.egor.schedule.app.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Egor on 26.05.2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://192.168.1.26:8080";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static GameService gameService = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static GameService getGameService() {
        if (gameService == null) {
            gameService = createService(GameService.class);
        }
        return gameService;
    }
}
