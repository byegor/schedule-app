package com.egor.schedule.app.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eb.schedule.shared.bean.GameBean;
import com.eb.schedule.shared.bean.Player;
import com.egor.schedule.app.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Егор on 06.02.2016.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {

    private Activity context;
    private List<Player> players;


    public PlayerAdapter(Activity context) {
        super(context, R.layout.mylist);
        this.context = context;
        this.players = new ArrayList<Player>();

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.player, null, true);

        ImageView heroImage = (ImageView) rowView.findViewById(R.id.hero_log);
        Player player = players.get(position);
        String heroLogo = "http://192.168.1.26:8085/image/" + player.getHero().getId();
        Log.i("PICASSO", heroLogo);
        Picasso.with(context).load(heroLogo).into(heroImage);

        return rowView;
    }

    @Override
    public void addAll(Collection<? extends Player> collection) {
        players.addAll(collection);
        super.addAll(collection);
    }

    @Override
    public void clear() {
        players.clear();
        super.clear();
    }
}