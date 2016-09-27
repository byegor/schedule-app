package com.egor.schedule.app.utils;

/**
 * Created by Egor on 02.07.2016.
 */
public class ImageUtils {

    private static final String BASE_TEAM_URL = "http://ec2-52-43-235-181.us-west-2.compute.amazonaws.com/image/team/";
    private static final String BASE_ITEM_URL = "http://ec2-52-43-235-181.us-west-2.compute.amazonaws.com/image/item/";

    public static String getTeamUrl(String logoUuid){
        return BASE_TEAM_URL + logoUuid;
    }

    public static String getItemUrl(String itemName){
        return BASE_ITEM_URL + itemName;
    }
}
