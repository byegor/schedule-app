package com.egor.schedule.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Егор on 06.02.2016.
 */
public class ScheduleAdapter extends ArrayAdapter<GameBean> {

    private Activity context;
    private List<GameBean> games;
    private DateFormat dateFormatter;
    private DateFormat timeFormatter;

    public ScheduleAdapter(Activity context, List<GameBean> games) {
        super(context, R.layout.mylist, games);
        this.context = context;
        this.games = games;
        this.dateFormatter = android.text.format.DateFormat.getDateFormat(context);
        this.timeFormatter = android.text.format.DateFormat.getTimeFormat(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView time = (TextView) rowView.findViewById(R.id.time);
        TextView score = (TextView) rowView.findViewById(R.id.score);
        GameBean game = games.get(position);
        Date gameDate = new Date(game.getStartTime());

        date.setText(dateFormatter.format(gameDate));
        time.setText(timeFormatter.format(gameDate));
        score.setText(game.getRadiantWin() + ":" + game.getDireWin());

        return rowView;
    }


}