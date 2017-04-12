package com.egor.pulse.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.eb.schedule.shared.bean.HeroBean;
import com.egor.pulse.app.BuildConfig;
import com.squareup.picasso.Picasso;

/**
 * Created by Egor on 02.07.2016.
 */
public class ImageUtils {

    private static final String BASE_TEAM_URL = BuildConfig.IMAGE_HOST + "/image/team/";
    private static final String BASE_ITEM_URL = BuildConfig.IMAGE_HOST + "/image/item/";
    private static final String BASE_HERO_URL = BuildConfig.IMAGE_HOST + "/image/hero/";

    public static String getTeamUrl(String logoUuid) {

        return BASE_TEAM_URL + logoUuid;
    }

    public static String getItemUrl(String itemName) {
        return BASE_ITEM_URL + itemName;
    }

    public static void loadHeroImage(Context context, HeroBean hero, ImageView heroImage) {
        int drawableId = context.getResources().getIdentifier("h_" + hero.getId(), "drawable", context.getPackageName());
        if (drawableId != 0) {
            Picasso.with(context).load(drawableId).into(heroImage);
        } else {
            Picasso.with(context).load(BASE_HERO_URL + hero.getName().replace(" ", "_")).into(heroImage);
        }
    }
}
