package com.egor.schedule.app.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Егор on 06.02.2016.
 */
public class ScheduleAdapter extends ArrayAdapter<GameBean> {

    private Activity context;
    private List<GameBean> games;
    private DateFormat timeFormatter;

    public ScheduleAdapter(Activity context) {
        super(context, R.layout.mylist);
        this.context = context;
        this.games = new ArrayList<GameBean>();
        this.timeFormatter = android.text.format.DateFormat.getTimeFormat(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);


        TextView score = (TextView) rowView.findViewById(R.id.score);
        ImageView radiantImage = (ImageView)rowView.findViewById(R.id.radiant_logo);
        ImageView direImage = (ImageView)rowView.findViewById(R.id.dire_logo);
        GameBean game = games.get(position);
        String radiantLogo = "http://192.168.1.26:8085/image/" + game.getRadiant().getLogo();
        Log.i("PICASSO",radiantLogo);
        Picasso.with(context).load(radiantLogo).resize(55, 55).centerCrop().into(radiantImage);
        Picasso.with(context).load("http://192.168.1.26:8085/image/" + game.getDire().getLogo()).resize(55, 55).centerCrop().into(direImage);

        TextView gameStatus = (TextView) rowView.findViewById(R.id.game_status);
        if(game.gameStatus == 0){
            Date gameDate = new Date(game.getStartTime());
            gameStatus.setText(timeFormatter.format(gameDate));
        }else if(game.gameStatus == 1){
            gameStatus.setText("LIVE");
        }else{
            gameStatus.setText("FINISHED");
        }

        score.setTextSize(15);
        score.setText(game.getRadiantWin() + ":" + game.getDireWin());

        return rowView;
    }

    @Override
    public void addAll(Collection<? extends GameBean> collection) {
        games.addAll(collection);
        super.addAll(collection);
    }

    @Override
    public void clear() {
        games.clear();
        super.clear();
    }
}