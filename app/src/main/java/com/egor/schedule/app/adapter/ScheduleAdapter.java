package com.egor.schedule.app.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.R;
import com.egor.schedule.app.adapter.schedule.ListAddapterItem;
import com.egor.schedule.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Егор on 06.02.2016.
 */
public class ScheduleAdapter extends ArrayAdapter<ListAddapterItem> {

    private Activity context;
    private List<ListAddapterItem> games;
    private DateFormat timeFormatter;

    public ScheduleAdapter(Activity context) {
        super(context, R.layout.schedule_list_item);
        this.context = context;
        this.games = new ArrayList<ListAddapterItem>();
        this.timeFormatter = android.text.format.DateFormat.getTimeFormat(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ListAddapterItem item = games.get(position);
        if (item.isHeader()) {
            View rowView = inflater.inflate(R.layout.schedule_list_header, null, true);
            TextView date = (TextView) rowView.findViewById(R.id.date);
            date.setText((String) item.getBody());
            return rowView;
        } else {
            View rowView = inflater.inflate(R.layout.schedule_list_item, null, true);
            GameBean game = (GameBean) item.getBody();

            TextView score = (TextView) rowView.findViewById(R.id.score);
            ImageView radiantImage = (ImageView) rowView.findViewById(R.id.radiant_logo);
            ImageView direImage = (ImageView) rowView.findViewById(R.id.dire_logo);

            Picasso.with(context).load(ImageUtils.getTeamUrl(game.getRadiant().getLogo())).into(radiantImage);
            Picasso.with(context).load(ImageUtils.getTeamUrl(game.getDire().getLogo())).into(direImage);

            TextView leagueName = (TextView) rowView.findViewById(R.id.league_name);
            leagueName.setText(game.getLeague().getName());

            TextView radiantName = (TextView) rowView.findViewById(R.id.radiant_team_name);
            String rname = game.getRadiant().getName();
            if(rname.equals("")){
                radiantName.setText("Unknown Team");
            }else{
                radiantName.setText(rname);
            }

            TextView direName = (TextView) rowView.findViewById(R.id.dire_team_name);
            String dname = game.getDire().getName();
            if(dname.equals("")){
                direName.setText("Unknown Team");
            }else{
                direName.setText(dname);
            }

            TextView seriesType = (TextView) rowView.findViewById(R.id.series_type);
            seriesType.setText(game.getSeriesType());

            TextView gameStatus = (TextView) rowView.findViewById(R.id.game_status);
            if (game.getGameStatus() == 0) {
                Date gameDate = new Date(game.getStartTime());
                gameStatus.setText(timeFormatter.format(gameDate));
            } else if (game.getGameStatus() == 1) {
                gameStatus.setText(R.string.game_status_live);
                gameStatus.setTextColor(Color.RED);
            } else {
                gameStatus.setText(R.string.game_status_finished);
                gameStatus.setTextColor(Color.WHITE);
            }

            score.setText(game.getRadiantWin() + ":" + game.getDireWin());

            return rowView;
        }

    }

    @Override

    public void addAll(Collection<? extends ListAddapterItem> collection) {
        if (collection != null) {
            games.addAll(collection);
            super.addAll(collection);
        } else {
            Toast.makeText(context, "No games found to display :(", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void clear() {
        games.clear();
        super.clear();
    }
}