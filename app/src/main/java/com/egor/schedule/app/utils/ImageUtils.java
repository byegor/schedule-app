package com.egor.schedule.app.utils;

import com.egor.schedule.app.BuildConfig;

/**
 * Created by Egor on 02.07.2016.
 */
public class ImageUtils {

    private static final String BASE_TEAM_URL = BuildConfig.IMAGE_HOST + "/image/team/";
    private static final String BASE_ITEM_URL =  BuildConfig.IMAGE_HOST + "/image/item/";

    public static String getTeamUrl(String logoUuid){

        return BASE_TEAM_URL + logoUuid;
    }

    public static String getItemUrl(String itemName){
        return BASE_ITEM_URL + itemName;
    }
}
